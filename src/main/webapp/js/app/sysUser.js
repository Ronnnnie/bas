
$(function(){

//	initSystem("sysCombox",true);
	initValidInd("searchValidInd");
	initSysUserGrid();
	
});

var	toolbar = [{
	iconCls: 'icon-add',
	text:'新增',
	handler: function(){
		$("#userWin").show();
		$win = $("#userWin").window({
			title:"新增用户",
			width:'500px',
			height:'400px',
			minimizable:false,
			collapsible:false,
			maximizable:false
		});
		$win.window("open");
		$("#userFrm").form('clear');
		initAddForm("upRoleCode","upDepart","upStatus");
	}
}];
//初始化用户信息
function initAddForm(sysRoleId,departId,validIndId){
	$("#"+sysRoleId).combobox({    
	    url: contextPath + "/role/queryAllRole.do",
		panelHeight:200,
		editable:false,
	    valueField:'roleCode',    
	    textField:'roleCname'
	});
	$("#"+departId).combobox({    
	    url: contextPath + "/userServer/queryAllDepart.do",
		panelHeight:200,
		editable:false,
		multiple:true,
	    valueField:'id',    
	    textField:'departName'
	});
	 $("#"+validIndId).combobox({
			data : [ 
		               {text : '启用', value : '1'}, 
                       {text : '禁用', value : '0'},
					 ],
			panelHeight:80,
			editable:false,
			valueField:'value',    
			textField:'text' ,
			value:'1'
		});
}
//新增用户
function addUser(){
	var roleCode = $("#upRoleCode").combobox('getValue');
	if(roleCode == "--请选择--" || roleCode == ""){
		alert("请选择角色！");
		return null;
	}
	$('#userFrm').form('submit', {
		url: contextPath + '/userServer/saveUser.do',
		onSubmit:function(param){
			if($(this).form('validate')){
				$.messager.progress({ title: '新增用户', msg: '保存数据...'});
			}
			return $(this).form('validate');
		},
		success: function(data){
			$.messager.progress('close');
			data = eval("("+data+")");
			if(data.success){
				 $.messager.alert("操作提示", data.message,"info");
				 //重新加载表格数据
				 initSysUserGrid();
				//关闭窗口
				closeWin('userWin');
			}else{
				$.messager.alert("操作提示", data.message,"error");
			}
		},
		onLoadError:function(data){
			$.messager.alert("操作提示", data.message,"error");
		}
	});
}


//初始化系统信息
/*function initSystem(sysCombox,needNull){
	$('#'+sysCombox).combobox({    
	    url: contextPath + "/sysServer/initSystem.do?needNull=" + needNull,    
		panelHeight:200,
		editable:false,
	    valueField:'sysCode',    
	    textField:'sysCname'
	}); 
}*/
//初始化查询条件中的状态
function initValidInd(validInd){
	var data = [ 
               {text : '启用', value : '1'}, 
               {text : '禁用', value : '0'},
  			 ];

	if(validInd=="searchValidInd"){
		data = [ {text : '全部', value : ''},
		         {text : '启用', value : '1'}, 
	             {text : '禁用', value : '0'},
			   ];
	}
	
	 $("#"+validInd).combobox({
			data : data,
			width:"90px",
			panelHeight:80,
			editable:false,
			valueField:'value',    
			textField:'text' 
		});
}
//初始化所有系统清单
function initSysUserGrid(){
	
	$('#sysUserGrid').datagrid({
	    title:'用户列表',
	    pagination: true,
	    singleSelect:true,
	    checkOnSelect:true,
	    toolbar:toolbar,
	    pageSize:10,
	    rownumbers:true,
	    width:'auto',
	    idField:'userCode',
	    url:contextPath + "/userServer/querySysUser.do",
	    columns:[[
	        {field:'userCode',title:'用户帐号',width:'10%'},
	        {field:'userCname',title:'真实姓名',width:'10%'},
	        {field:'departName',title:'管理部门',width:'15%'},
	        {field:'roleCname',title:'用户角色',width:'10%'},
	        {field:'userEmail',title:'常用邮箱',width:'15%'},
	        {field:'userPhone',title:'联系电话',width:'10%'},
	        {field:'status',title:'状态',width:'10%',align:'center',
	            formatter:function(value,row,index){
	                if (value == '1'){
	                    return '<samp style="color: green;">有效</samp>';
	                } else {
	                    return '<samp style="color: red;">失效</samp>';
	                }
	            }
	        },
	        {field:'opr',title:'操作',width:'20%',
                formatter:function(value, row, index){
                	var edit = '<a  class="editcls"  href="javascript:;" onclick="openUpdateSysUser('+index+')" ></a>';
                	var cancel = '<a  class="cancelCss"  href="javascript:;" onclick="removeSysUser('+index+')" ></a>';
                	var ok = '<a  class="okCss"  href="javascript:;" onclick="openSysUser('+index+')" ></a>';
                	var del = '<a  class="delCss"  href="javascript:;" onclick="deleteSysUser('+index+')" ></a>';
                	if(row.status == '1'){
                		return edit + cancel + del;
                	}else{
                		return edit + ok + del;
                	}
                }
            }
	    ]],
	    onLoadSuccess:function(data){
            $('.editcls').linkbutton({text:'修改',plain:true,iconCls:'icon-edit'});
            $('.cancelCss').linkbutton({text:'禁用',plain:true,iconCls:'icon-cancel'});
            $('.okCss').linkbutton({text:'启用',plain:true,iconCls:'icon-ok'});
            $('.delCss').linkbutton({text:'删除',plain:true,iconCls:'icon-cancel'});
        }
	});
	
	//设置分页控件 
    var p = $('#sysUserGrid').datagrid('getPager'); 
    $(p).pagination({ 
        pageSize: 10,//每页显示的记录条数，默认为10 
        pageList: [5,10,15],//可以设置每页记录条数的列表 
        beforePageText: '第',//页数文本框前显示的汉字 
        afterPageText: '页    共 {pages} 页', 
        displayMsg: '当前显示 {from} - {to} 条记录   共 {total} 条记录' 
    }); 
}

/*var toolbar = [{
	iconCls: 'icon-add',
	text:'批量分配系统用户',
	handler: function(){
		$('#addSysUserWin').window({    
		    title: '批量分配系统用户',    
		    width: '900px',    
		    height: '450px',
		    left:"10%", 
		    top:"5%",
		    closed: false
		   }); 
		//初始化系统和状态
		initSystemAddSysUser();
		initHrDept("departCombotree");
		initSysUser();
	}
}];*/

//增加系统用户时初始化系统信息
function initSystemAddSysUser(){
	var needNull = false;
	$('#addSysCode').combobox({    
	    url: contextPath + "/sysServer/initSystem.do?needNull=" + needNull,    
		panelHeight:200,
		editable:false,
	    valueField:'sysCode',    
	    textField:'sysCname',
	    onSelect:function(node){
	    	//移除表单中的数据，包括隐藏域的数据
	    	$("#addSysUserForm").form("clear");
	    	$("#exitsSysName").val(node.sysCname);
	    	//先清空数据
	    	$("#addSysUserExistGrid").datagrid("clearData");
	    	//只是新增，不需要展示系统原有的用户
	    	$("#addSysUserExistGrid").datagrid({
	    		idField:"userCode",
	    		singleSelect:false,
	    		pagination:true,
	    		url:contextPath + "/sysUser/querySysUser.do",
	    		columns: userColumns,
	    		queryParams:{
	    			sysCode:node.sysCode,
	    			validInd:'1'
	    		}
	    	});
	    	//设置分页控件 
	        var p = $('#addSysUserExistGrid').datagrid('getPager'); 
	    	//控制分页
	    	$(p).pagination({
				onSelectPage:function(pageNumber, pageSize){
					//获取删除的数据
					var removeUserCodes = $("#removeUserHidden").val();
					//获取新增的数据
					var addUserCodes = $("#addUserHidden").val();
					if(removeUserCodes || addUserCodes){
						 $.messager.alert("操作提示", "请先保存数据","info");
						 $(p).pagination({
							 pageNumber:1
						 });
						 return;
					}
					getData(pageNumber,pageSize,node.sysCode);
				}
			});
	    }
	}); 
}
//重新分页，到后台查询数据
 function getData (page, rows,sysCode) { 
	var userCname = $("#exitsUserCname").val();
	var userCode = $("#exitsUserCode").val();
	 $.ajax({
			url:contextPath + "/sysUser/querySysUser.do",
			type: "GET", 
			data:{
				sysCode:sysCode,
    			validInd:'1',
    			page:page,
    			rows:rows,
    			userCname: userCname,
    			userCode: userCode
			},
	        success: function (data) { 
	        	var jsondata = eval("("+data+")");
	        	//重新给表格加载数据
	        	$("#addSysUserExistGrid").datagrid('loadData',jsondata);
	        },
			error: function () { 
				 $.messager.alert("操作提示", data.message,"error");
	        }
		});
}

//初始化查询条件中的用户部门
function initHrDept(hrCombotreeId){
	var multiple = false;
	if(hrCombotreeId == 'departCombotree'){
		multiple = true;
	}
	$('#'+hrCombotreeId).combotree({    
	    url: contextPath + "/userServer/initHrDept.do",    
	    multiple: multiple,
	    width:"98%"
	}); 
}

//查询管理员
function query(){
	//查询前先清空所有选中行
	$("#sysUserGrid").datagrid("clearSelections");
	var userCode = $("#userCodeSear").val();
	var userCname = $("#userCnameSear").val();
	var validInd = $("#searchValidInd").combobox("getValue");
	$('#sysUserGrid').datagrid('load',{
		userCode:userCode,
		userCname:userCname,
		status:validInd
	});
}

//启用系统用户
function openSysUser(index){
	$.messager.confirm('操作提示','您确认要启用该用户吗？',function(r){    
	    if (r){    
    	 $("#sysUserGrid").datagrid("selectRow",index);
    		var row = $("#sysUserGrid").datagrid("getSelected");
    		$.ajax({
    			url:contextPath + '/userServer/openUser.do?userCodes='+ row.userCode,
    			type: "GET", 
    	        success: function (data) { 
    	        	data = eval("("+data+")");
    	        	if(data.success){
    					 $.messager.alert("操作提示", data.message,"info");
    					 //重新加载表格数据
    					 initSysUserGrid();
    				}else{
    					$.messager.alert("操作提示", data.message,"error");
    				}
    	        },
    			error: function () { 
    				 $.messager.alert("操作提示", data.message,"error");
    	        }
    		});
	    }    
	}); 
}
//系统用户失效
function removeSysUser(index){

	$.messager.confirm('操作提示','您确认要禁用该用户吗？',function(r){    
	    if (r){    
	    	 $("#sysUserGrid").datagrid("selectRow",index);
	    		var row = $("#sysUserGrid").datagrid("getSelected");
	    		$.ajax({
	    			url:contextPath + '/userServer/removeUser.do?userCodes='+ row.userCode,
	    			type: "GET", 
	    	        success: function (data) { 
	    	        	data = eval("("+data+")");
	    	        	if(data.success){
	    					 $.messager.alert("操作提示", data.message,"info");
	    					 //重新加载表格数据
	    					 initSysUserGrid();
	    				}else{
	    					$.messager.alert("操作提示", data.message,"error");
	    				}
	    	        },
	    			error: function () { 
	    				 $.messager.alert("操作提示", data.message,"error");
	    	        }
	    		});
	    }    
	}); 
}

function deleteSysUser(index){
	$.messager.confirm('操作提示','您确认要删除该用户吗？',function(r){
		if(r){
			$('#sysUserGrid').datagrid('selectRow',index);
			var row = $('#sysUserGrid').datagrid('getSelected');
			$.ajax({
				url: contextPath + '/userServer/deleteUser.do?userCode=' + row.userCode,
				type: 'GET',
				success: function(data){
					data = eval("("+data+")");
					if(data.success){
						$.messager.alert('操作提示', data.message, "info");
						initSysUserGrid();
					}else{
						$.messager.alert("操作提示", data.message,"error");
					}
				},
				error: function () { 
   				 	$.messager.alert("操作提示", data.message,"error");
				}
			});
		}
	});
}

//打开修改窗口
function openUpdateSysUser(index){
	$("#sysUserGrid").datagrid("selectRow",index);
	var row = $("#sysUserGrid").datagrid("getSelected");
	$("#updateSysUserF").form('clear');
	$("#updateSysUserWin").show();
	$win = $("#updateSysUserWin").window({
		title:"修改用户信息",
		width: '300px',    
	    height: '400px',
	    minimizable:false,
	    collapsible:false,
	    maximizable:false
	  });
	$win.window("open");
	//初始化系统和状态
	initAddForm("roleCode","depart","status");
	
//	initSystem("updateSysCode",false);
//	initValidInd("updateValidInd");
	$("#updateSysUserF").form("load",row);
}

function updateSysUser(){
	var roleCode = $("#roleCode").combobox('getValue');
	if(roleCode == "--请选择--"){
		alert("请选择角色！");
		return null;
	}
	if(!$("#updateSysUserF").form('validate')){
		return;
	}
	$.messager.progress({ title: '修改系统用户', msg: '保存数据...'});
	$('#updateSysUserF').form('submit', {
		url: contextPath + '/userServer/updateSysUser.do',
		success: function(data){
			$.messager.progress('close');
			data = eval("("+data+")");

			if(data.success){
				 $.messager.alert("操作提示", data.message,"info");
				 //重新加载表格数据
				 initSysUserGrid();			
				 //关闭窗口
				 closeWin('updateSysUserWin');
			}else{
				$.messager.alert("操作提示", data.message,"error");
			}
		},
		onLoadError:function(data){
			$.messager.alert("操作提示", data.message,"error");
		}
	});
}


//资源分配
/*function openSysRoleWin(index){
	$("#sysUserGrid").datagrid("selectRow",index);
	var sysUser = $("#sysUserGrid").datagrid("getSelected");
	$("#searchsysCname").attr('value',sysUser.sysCname);
	$("#searchsysCode").attr('value',sysUser.sysCode);
	$("#searchuserCode").attr('value',sysUser.userCode);
	
	$('#addSysRoleWin').window({    
	    title: '分配权限',    
	    width: '600px',    
	    height: '450px',
	    left:"15%", 
	    top:"5%",
	    closed: false
	   }); 

	//加载系统的可分配权限和用户的已分配权限
	querySysRole();
}*/

//加载系统的可分配权限和用户的已分配权限
/*function querySysRole(){
	
	var sysCode = $("#searchsysCode").val();
	var userCode = $("#searchuserCode").val();
	//先清空数据
	$("#userRoleGrid").datagrid("clearData");
	//可选权限的数据系统下的权限，除掉用户已有的权限
	$("#userRoleGrid").datagrid({
		idField:"roleCode",
		singleSelect:false,
		columns: roleColumns,
		url:contextPath + "/sysUser/queryRoleBySysCode.do?sysCode="+sysCode+"&userCode=" + userCode,
	});
	//先清空数据
	$("#userRoleExistGrid").datagrid("clearData");
	//加载已存在角色
	$("#userRoleExistGrid").datagrid({
		idField:"roleCode",
		singleSelect:false,
		columns: roleColumns,
		url:contextPath + "/sysUser/queryRoleExist.do?sysCode="+sysCode+"&userCode=" + userCode,
	});
}*/
var roleColumns =  [[
                     {field:'ck',checkbox:true }, 
                	{field:'roleCode',title:'角色编码',hidden:'true'},
                   	{field:'roleCname',title:'角色名称',width:'95%'},
               	   ]];

//选中数据点击右移键，给用户添加系统权限
function onRoleRight(){
	var roleCodes = $("#userRoleGrid").datagrid('getSelections');
	if(roleCodes.length == 0){
		 $.messager.alert("操作提示", "请选择可分配角色","error");
	}
	//防止删除时数据index变动，先拷贝一份
    var copyRows = [];
    for ( var j= 0; j < roleCodes.length; j++) {
    	copyRows.push(roleCodes[j]);
    }
	for(var i=0;i<copyRows.length;i++){
		//将数据添加到已选系统中
		$('#userRoleExistGrid').datagrid('appendRow',copyRows[i]);
		//将数据从可选系统中删除
	    var index = $('#userRoleGrid').datagrid('getRowIndex',copyRows[i]);
        $('#userRoleGrid').datagrid('deleteRow',index); 
	}
}
//选中数据点击左移键，将系统权限移除
function onRoleLeft(){
	var roleCodes = $("#userRoleExistGrid").datagrid('getSelections');
	if(roleCodes.length == 0){
		 $.messager.alert("操作提示", "请选择已分配角色","error");
	}
	//防止删除时数据index变动，先拷贝一份
    var copyRows = [];
    for ( var j= 0; j < roleCodes.length; j++) {
    	copyRows.push(roleCodes[j]);
    }
	for(var i=0;i<copyRows.length;i++){
		//将需要移除的系统添加到可选系统
		$("#userRoleGrid").datagrid('appendRow',copyRows[i]);
		//将需要移除权限的系统从已分配系统中移除
		var index = $("#userRoleExistGrid").datagrid('getRowIndex',copyRows[i]);
		$("#userRoleExistGrid").datagrid('deleteRow',index);
	}
}
//保存给用户分配的权限
function addUserRole(){
	$.messager.progress({ title: '授权', msg: '保存数据...'});
	var roleCodes = $("#userRoleExistGrid").datagrid('getData');
	var rows = roleCodes.rows;
	var roleCodeArr = [];
	for ( var j= 0; j < rows.length; j++) {
		roleCodeArr.push(rows[j].roleCode);
    }
	var roleCodeStr = roleCodeArr.join(',');
	var sysCode = $("#searchsysCode").val();
	var userCode = $("#searchuserCode").val();
	$.ajax({
		url:contextPath + '/sysUser/saveUserRole.do?roleCodes='+ roleCodeStr+'&userCode='+
			userCode + "&sysCode="+sysCode,
		type: "GET", 	
        success: function (data) { 
        	$.messager.progress('close');
        	data = eval("("+data+")");
        	if(data.success){
                
				 $.messager.alert("操作提示", data.message,"info");
				 closeWin('addSysRoleWin');
			}else{
				$.messager.alert("操作提示", data.message,"error");
			}
        },
		error: function () { 
			 $.messager.alert("操作提示", data.message,"error");
        }
	});
}

function closeWin(win){
	$("#"+win).window("close"); 
}

//初始化系统的可选用户和系统用户
function initSysUser(){
	//移除表单中的数据，包括隐藏域的数据
	$("#addSysUserForm").form("clear");
	//先清空数据
	$("#addSysUserGrid").datagrid("clearData");
	//初始化可选用户,暂时初始化的是系统中存在的所有用户，看需不需要加过滤条件
	$("#addSysUserGrid").datagrid({
		idField:"userCode",
		singleSelect:false,
		pagination:true,
		columns: userColumns,
		url:contextPath + "/userServer/initQueryUser.do",//只查询可以的用户
		queryParams:{
			status:'1'
		}
	});
	$("#addSysUserExistGrid").datagrid("clearData");
}
var userColumns =  [[
                     {field:'ck',checkbox:true }, 
                	{field:'userCode',title:'用户编码',width:'45%'},
                   	{field:'userCname',title:'用户名',width:'50%'}
               	   ]];


function queryUser(){
	//查询前先清空所有选中行
//	$("#addSysUserGrid").datagrid("clearSelections");
	var userCname = $("#searchuserCname").val();
	var userCode = $("#alsUserCode").val();
	var depart = $("#departCombotree").combotree("getValues");
	var fhrorgunitid = depart.join(",");
	$('#addSysUserGrid').datagrid('loadExt',{
		userCname: userCname,
		userCode: userCode,
		status:"1",
		fhrorgunitid:fhrorgunitid
	});
}

//查询已选用户
function queryExitsSysUser(){
	//获取删除的数据
	var removeUserCodes = $("#removeUserHidden").val();
	//获取新增的数据
	var addUserCodes = $("#addUserHidden").val();
	if(removeUserCodes || addUserCodes){
		 $.messager.alert("操作提示", "请先保存数据","info");
		 return;
	}
	var sysCode = $("#addSysCode").combobox("getValue");
	var userCname = $("#exitsUserCname").val();
	var userCode = $("#exitsUserCode").val();
	$('#addSysUserExistGrid').datagrid('loadExt',{
		userCname: userCname,
		userCode: userCode,
		sysCode:sysCode,
		validInd:"1"
	});
}

//选中数据点击右移键，给系统添加用户
function onUserRight(){
	var sysCode = $("#addSysCode").combobox("getValue");
	if(!sysCode){
		 $.messager.alert("操作提示", "请选择系统","info");
		 return;
	}
	var userCodes = $("#addSysUserGrid").datagrid('getSelections');
	var userCodesRight = $("#addSysUserExistGrid").datagrid('getRows');
	if(userCodes.length == 0){
		 $.messager.alert("操作提示", "请选择可选用户","error");
	}
	//防止删除时数据index变动，先拷贝一份
    var copyRows = [];
    for ( var j= 0; j < userCodes.length; j++) {
    	var isExist = false;
    	for ( var i= 0; i < userCodesRight.length; i++) {
    		//如果右边已经存在就不添加
    		if(userCodes[j].userCode == userCodesRight[i].userCode){
    			isExist = true;
    			break;
    		}
        }
    	if(!isExist){
    		copyRows.push(userCodes[j]);
    	}
    }
    //记录每次移动到右边的数据,记录添加的数据
    var addUserCodes = $("#addUserHidden").val();
    var newAddUser = [];
	for(var i=0;i<copyRows.length;i++){
		//将数据添加到已选系统中
		$('#addSysUserExistGrid').datagrid('appendRow',copyRows[i]);
		newAddUser.push(copyRows[i].userCode);
	}
	if(addUserCodes){
		//拼接为新的数据
		addUserCodes = addUserCodes +","+newAddUser.join(",");
	}else{
		addUserCodes = newAddUser.join(",");
	}
	//重新设置到隐藏域中
	$("#addUserHidden").val(addUserCodes);
}
//移除用户
function onUserLeft(){
	var userCodes = $("#addSysUserExistGrid").datagrid('getSelections');
	if(userCodes.length == 0){
		 $.messager.alert("操作提示", "请选择已选用户","error");
	}
	//防止删除时数据index变动，先拷贝一份
    var copyRows = [];
    for ( var j= 0; j < userCodes.length; j++) {
    	copyRows.push(userCodes[j]);
    }
    //记录每次移动到右边的数据,记录添加的数据
    var removeUserCodes = $("#removeUserHidden").val();
    var newRemoveUser = [];
	for(var i=0;i<copyRows.length;i++){
		//将需要移除的用户从已选用户中
		var index = $("#addSysUserExistGrid").datagrid('getRowIndex',copyRows[i]);
		$("#addSysUserExistGrid").datagrid('deleteRow',index);
		newRemoveUser.push(copyRows[i].userCode);
	}
	if(removeUserCodes){
		//拼接为新的数据
		removeUserCodes = removeUserCodes +","+newRemoveUser.join(",");
	}else{
		removeUserCodes = newRemoveUser.join(",");
	}
	//重新设置到隐藏域中
	$("#removeUserHidden").val(removeUserCodes);
}
//保存给系统新增的系统用户
function onAddSysUser(){
	
//	var userCodes = $("#addSysUserExistGrid").datagrid('getData');
//	var rows = userCodes.rows;
//	var userCodeArr = [];
//	for ( var j= 0; j < rows.length; j++) {
//		userCodeArr.push(rows[j].userCode);
//    }
//	var userCodeStr = userCodeArr.join(',');
	
	//获取删除的数据
	var removeUserCodes = $("#removeUserHidden").val();
	//获取新增的数据
	var addUserCodes = $("#addUserHidden").val();
	
	var sysCode = $("#addSysCode").combobox("getValue");
	if(!sysCode){
		 $.messager.alert("操作提示", "请选择系统","info");
		 return;
	}
	if(!removeUserCodes && !addUserCodes){
		 $.messager.alert("操作提示", "请分配用户","info");
		 return;
	}
	$.messager.progress({ title: '批量分配系统用户', msg: '保存数据...'});
	$.ajax({
		url:contextPath + '/sysUser/saveSysUser.do?removeUserCodes='+ removeUserCodes+ '&addUserCodes='+addUserCodes+ '&sysCode='+sysCode,
		type: "GET", 	
        success: function (data) { 
        	$.messager.progress('close');
        	data = eval("("+data+")");
        	if(data.success){
                closeWin('addSysUserWin');
                initSysUserGrid();
				 $.messager.alert("操作提示", data.message,"info");
			}else{
				$.messager.alert("操作提示", data.message,"error");
			}
        },
		error: function () { 
			 $.messager.alert("操作提示", data.message,"error");
        }
	});
}