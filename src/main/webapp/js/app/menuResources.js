
$(function(){
	
	initAllSystemGrid();
//	initValidInd("searchValidInd");
//	initSysType();
});

//初始化查询条件中的系统分类
/*function initSysType(){
	var validInd = '1';
	var needNull = true;
	$('#fqsysTypeCname').combotree({    
		url:contextPath+'/sysTypeServer/queryTypeByWhere.do?validInd='+validInd+"&needNull="+needNull,  
	    valueField:'sysTypeCode',    
	    textField:'sysTypeCname',
		panelHeight:200,
		editable:false
	});
}*/

//初始化所有系统清单
function initAllSystemGrid(){
	
	$('#menuGrid').datagrid({
	    title:'系统列表',
	    pagination: true,
	    checkOnSelect:false,
	    singleSelect:true,
	    pageSize:10,
	    rownumbers:true,
	    idField:'sysCode',
	    url:contextPath+"/menuServer/querySysByWhere.do",
	    columns:[[
			{field:'sysCname',title:'系统名称',width:"20%"},
	        {field:'sysCode',title:'系统编码',width:"10%"},
	        {field:'sysEname',title:'系统英文名',width:"20%"},
	        {field:'sysTypeCname',title:'系统分类',width:"15%"},
	        {field:'validInd',title:'状态',width:"10%",align:'center',
	            formatter:function(value,row,index){
	                if (value == '1'){
	                    return '<samp style="color: green;">有效</samp>';
	                }else {
	                    return '<samp style="color: red;">失效</samp>';
	                }
	            }
	        },
	        {field:'opr',title:'操作',width:"20%",
                formatter:function(value, row, index){
                	var menu = '';
                	var butt = '';
                	if(row.validInd == '1'){
                		menu = '<a  class="menuClass"  href="javascript:;" onclick="menuResources('+index+')" ></a>';
//                		butt = '<a  class="buttClass"  href="javascript:;" onclick="buttResources('+index+')" ></a>';
                	}
                    return menu + butt;
                }
            }
	    ]],
	    onLoadSuccess:function(data){
            $('.menuClass').linkbutton({text:'菜单管理',plain:true,iconCls:'icon-add'});
//            $('.buttClass').linkbutton({text:'按钮管理',plain:true,iconCls:'icon-add'});
        }
	});
	
	//设置分页控件 
    var p = $('#menuGrid').datagrid('getPager'); 
    $(p).pagination({ 
        pageSize: 10,//每页显示的记录条数，默认为10 
        pageList: [5,10,15],//可以设置每页记录条数的列表 
        beforePageText: '第',//页数文本框前显示的汉字 
        afterPageText: '页    共 {pages} 页', 
        displayMsg: '当前显示 {from} - {to} 条记录   共 {total} 条记录'
    }); 
}

//初始化查询条件中的状态
function initValidInd(validIndId){
	 $("#"+validIndId).combobox({
			data : [ {text : '全部', value : ''},
			               {text : '有效', value : '1'}, 
	                       {text : '失效', value : '0'},
						 ],
			width:"90px",
			panelHeight:80,
			editable:false,
			valueField:'value',    
			textField:'text' 
		});
}
//根据搜索条件查询系统
function query(){
	var sysCname = $("#searchsysCname").val();
	var sysCode = $("#searchsysCode").val();
	var sysTypeCode = $("#fqsysTypeCname").combobox("getValue");
	var validInd = $("#searchValidInd").combobox("getValue");
	$('#menuGrid').datagrid('load',{
		sysCname: sysCname,
		sysTypeCode: sysTypeCode,
		validInd:validInd,
		sysCode:sysCode
	});
}

var globalsysCode;
//系统的菜单管理
function menuResources(index){

	 $("#menuGrid").datagrid("selectRow",index);
	var row = $("#menuGrid").datagrid("getSelected");
	
	$("#showsysCname").attr("value",row.sysCname);
	$("#showsysCode").attr("value",row.sysCode);
	
//	var tool='';
	//不是超级管理员，没有操作按钮
//	if(isSuperAdmin){
	var	tool = toolbar;
//	}
	
	$('#menuResourcesWin').dialog({    
	    title: '菜单管理',    
	    width: '650px',    
	    height: '450px',
	    left:"15%", 
	    top:"5%",
	    closed: false,
	    toolbar:tool
	}); 
	globalsysCode = row.sysCode;
	queryMenuTree();
	
}

//加载该系统下的所有菜单
function queryMenuTree(){
	$("#menuResourcesTree").tree({
		title:"菜单",
		url:contextPath + "/resources/queryMenusSysCode.do?sysCode=" + globalsysCode
	});
}

var toolbar = [{
	text:'新增',
	iconCls:'icon-add',
	handler:function(){
		$("#menuWin").window({    
		    title: '新增菜单',    
		    width: '350px',    
		    height: '350px',
		    closed: false
		});
		//清空表单
		$("#menuFrm").form("clear");
		//清空隐藏域
		 $("#resourceId").attr("value","");
		 $("parentId").attr("value","");
	}
},"-",{
	text:'修改',
	iconCls:'icon-edit',
	handler:function(){
		var menu = $("#menuResourcesTree").tree("getSelected");
		if(menu){
			var resourceId = menu.id;
			$("#menuWin").window({    
			    title: '修改菜单',    
			    width: '350px',    
			    height: '350px',
			    closed: false
			});
			$("#menuFrm").form("loadExt",contextPath + "/resources/findResourceById.do?resourceId="+resourceId);
		}else{
			$.messager.alert("操作提示", "请选择菜单","info");
		}
	}
},"-",{
	text:'删除',
	iconCls:'icon-cancel',
	handler:function(){
		//将菜单建在选中的菜单下
		var menu = $("#menuResourcesTree").tree("getSelected");
		if(menu){
			var msg = "";
			if(menu.children){
				msg = "您确认要删除该菜单以及其子菜单吗？删除后不可恢复";
			}else{
				msg = "您确认要删除该菜单吗？删除后不可恢复";
			}
			$.messager.confirm('确认',msg,function(r){    
			    if (r){ 
			    	//将菜单和其子菜单设置为失效
			    	deleteMenu();
			    }    
			}); 
			
		}else{
			$.messager.alert("操作提示", "请选择菜单","info");
		}
	}
}];

//新增或修改菜单
function saveMenu(){
	var resourceId = $("#resourceId").val();
	//已经存在为修改
	if(resourceId){
		updateMenu();
	}else{
		addMenu();
	}
}

//新建菜单
function addMenu(){
	
	//将菜单建在选中的菜单下
	var menu = $("#menuResourcesTree").tree("getSelected");
	var parentId = "";
	if(menu){
		//将新建的菜单的父Id设置为选中菜单
		parentId = menu.id;
	}
	$('#menuFrm').form('submit', {
		method:"post",
		url: contextPath+'/resources/saveResource.do',
		onSubmit:function(param){
			param.parentId = parentId;
			param.sysCode = globalsysCode;
			param.resourceType = 1;
			if($(this).form('validate')){
				$.messager.progress({ title: '新增菜单', msg: '保存数据...'});
			}
			return $(this).form('validate');
		},
		success: function(data){
			$.messager.progress('close');
			data = eval("("+data+")");
			if(data.success){
				 $.messager.alert("操作提示", data.message,"info");
				 //重新加载表格数据
				 queryMenuTree();
				 //关闭窗口
				 closeWin("menuWin");
			}else{
				$.messager.alert("操作提示", data.message,"error");
			}
		},
		onLoadError:function(data){
			$.messager.alert("操作提示", data.message,"error");
		}
	});
}

function updateMenu(){
	
	$('#menuFrm').form('submit', {
		url: contextPath+'/resources/updateResource.do',
		onSubmit:function(){
			if($(this).form('validate')){
				$.messager.progress({ title: '修改菜单', msg: '保存数据...'});
			}
			return $(this).form('validate');
		},
		success: function(data){
			$.messager.progress('close');
			data = eval("("+data+")");
			if(data.success){
				 $.messager.alert("操作提示", data.message,"info");
				 //重新加载表格数据
				 queryMenuTree();
				//关闭窗口
				closeWin("menuWin");
			}else{
				$.messager.alert("操作提示", data.message,"error");
			}
		},
		onLoadError:function(data){
			$.messager.alert("操作提示", data.message,"error");
		}
	});
}

function deleteMenu(){
	
	//将菜单建在选中的菜单下
	var menu = $("#menuResourcesTree").tree("getSelected");
	var resourceIdsArr = [];
	if(menu){
		//获得选中接节点的数据，包括子节点
		resourceIdsArr.push(menu.id);
		if(menu.children){
			getMenus(menu.children,resourceIdsArr);
		}
	}
	var resourceIds = resourceIdsArr.join(",");
	$.ajax({
		url:contextPath + '/resources/updateMenuValidInd.do',
		type: "POST", 
		data:{
			resourceIds:resourceIds
		},
        success: function (data) { 
        	data = eval("("+data+")");
        	if(data.success){
				 $.messager.alert("操作提示", data.message,"info");
				 //重新加载表格数据
				 queryMenuTree();
			}else{
				$.messager.alert("操作提示", data.message,"error");
			}
        },
		error: function () { 
			 $.messager.alert("操作提示", data.message,"error");
        }
	});
}

//递归选中菜单的子，获得所选菜单和其子菜单
function getMenus(menus,resourceIdsArr){
	
	for(var i=0;i<menus.length;i++){
		resourceIdsArr.push(menus[i].id);
		var children = menus[i].children;
		if(children){
			getMenus(children,resourceIdsArr);
		}
	}
}

//关闭窗口
function closeWin(winId){
	$('#'+winId).window('close', true); 
}

//系统的按钮管理
function buttResources(index){

	 $("#menuGrid").datagrid("selectRow",index);
	var row = $("#menuGrid").datagrid("getSelected");
	
	$("#buttshowsysCname").attr("value",row.sysCname);
	$("#buttshowsysCode").attr("value",row.sysCode);
	
	$('#buttResourcesWin').dialog({    
	    title: '按钮管理',    
	    width: '750px',    
	    height: '500px',
	    left:"15%", 
	    top:"3%",
	    closed: false
	}); 
	globalsysCode = row.sysCode;
	queryButt(row.sysCode);
	initMenuCombotree("searchMenuId");
	initValidInd("searchButtValidInd");
}

//加载该系统下的所有菜单
function queryButt(){
	
//	var tool='';
	//不是超级管理员，没有操作按钮
//	if(isSuperAdmin){
	var	tool = toolbarButt;
//	}
	
	$('#buttResourcesGrid').datagrid({
	    title:'按钮列表',
	    checkOnSelect:false,
	    pagination: true,
	    singleSelect:true,
	    nowrap:false,
	    pageSize:10,
	    rownumbers:true,
	    toolbar:tool,
	    idField:'resourceId',
	    url:contextPath+"/resources/queryButt.do?sysCode="+globalsysCode,
	    columns:[[
	        {field:'resourceId',title:'resourceId',hidden:true},
			{field:'resourceName',title:'按钮名称',width:"15%"},
			{field:'parentName',title:'菜单',width:"38%"},
			{field:'resourceCode',title:'编码',width:"15%"},
	        {field:'validInd',title:'状态',width:"10%",align:'center',
	            formatter:function(value,row,index){
	                if (value == '1'){
	                    return '<samp style="color: green;">有效</samp>';
	                }else {
	                    return '<samp style="color: red;">失效</samp>';
	                }
	            }
	        },
	        {field:'opr',title:'操作',width:"20%",
                formatter:function(value, row, index){
                	//不是超级管理员，没有操作按钮
//                	if(!isSuperAdmin){
//                		return;
//                	}
                	var edit = '<a  class="editcls"  href="javascript:;" onclick="openUpdateButt('+index+')" ></a>';
                	var cancel = '<a  class="cancelCss"  href="javascript:;" onclick="deleteButt('+index+')" ></a>';
                	var ok = '<a  class="okCss"  href="javascript:;" onclick="openButt('+index+')" ></a>';
                	if(row.validInd == '1'){
                		return edit + cancel;
                	}else{
                		return edit + ok;
                	}
                }
            }
	    ]],
	    onLoadSuccess:function(data){
	    	$('.editcls').linkbutton({text:'修改',plain:true,iconCls:'icon-edit'});
            $('.cancelCss').linkbutton({text:'禁用',plain:true,iconCls:'icon-cancel'});
            $('.okCss').linkbutton({text:'启用',plain:true,iconCls:'icon-ok'});
        }
	});
	//设置分页控件 
    var p = $('#buttResourcesGrid').datagrid('getPager'); 
    $(p).pagination({ 
        pageSize: 10,//每页显示的记录条数，默认为10 
        pageList: [5,10,15],//可以设置每页记录条数的列表 
        beforePageText: '第',//页数文本框前显示的汉字 
        afterPageText: '页    共 {pages} 页', 
        displayMsg: '当前显示 {from} - {to} 条记录   共 {total} 条记录'
    });
}

//初始化菜单树
function initMenuCombotree(combotreeId){
	var needNull = false;
	if(combotreeId == 'searchMenuId'){
		needNull = true;
	}
	$('#'+combotreeId).combotree({    
	    url: contextPath + "/resources/queryMenusSysCode.do?sysCode="+globalsysCode+"&needNull="+needNull,    
	    multiple: false,
//	    width:'80px',
	    editable:false
	}); 
}

var toolbarButt = [{
	text:'新增',
	iconCls:'icon-add',
	handler:function(){
		$("#buttWin").window({    
		    title: '新增按钮',    
		    width: '350px',    
		    height: '350px',
		    closed: false
		});
		//清空表单
		$("#buttFrm").form("clear");
		//清空隐藏域
		 $("#buttresourceId").attr("value","");
		 initMenuCombotree("buttPMenuId");
	}
}];

//新增或修改菜单
function saveButt(){
	var resourceId = $("#buttresourceId").val();
	//已经存在为修改
	if(resourceId){
		updateButt();
	}else{
		addButt();
	}
}

//新建菜单
function addButt(){
	
	
	
	$('#buttFrm').form('submit', {
		method:"post",
		url: contextPath+'/resources/saveResource.do',
		onSubmit:function(param){
			param.sysCode = globalsysCode;
			param.resourceType = 2;
			if($(this).form('validate')){
				$.messager.progress({ title: '新增按钮', msg: '保存数据...'});
			}
			return $(this).form('validate');
		},
		success: function(data){
			$.messager.progress('close');
			data = eval("("+data+")");
			if(data.success){
				 $.messager.alert("操作提示", data.message,"info");
				 //重新加载表格数据
				 queryButt();
				//关闭窗口
				closeWin("buttWin");
			}else{
				$.messager.alert("操作提示", data.message,"error");
			}
		},
		onLoadError:function(data){
			$.messager.alert("操作提示", data.message,"error");
		}
	});
}

//弹出修改按钮窗口
function openUpdateButt(index){
		$("#buttResourcesGrid").datagrid("selectRow",index);
		var row = $("#buttResourcesGrid").datagrid("getSelected");
		$('#buttWin').dialog({    
			 title: '修改按钮',    
		     width: '350px',    
		     height: '350px',
		     closed: false
		}); 
		initMenuCombotree("buttPMenuId");
		$("#buttFrm").form("loadExt",contextPath + "/resources/findResourceById.do?resourceId="+row.resourceId);
		//清空隐藏域
		$("#buttresourceId").attr("value",row.resourceId);
}

function updateButt(){
	
	$('#buttFrm').form('submit', {
		url: contextPath+'/resources/updateResource.do',
		onSubmit:function(){
			if($(this).form('validate')){
				$.messager.progress({ title: '修改按钮', msg: '保存数据...'});
			}
			return $(this).form('validate');
		},
		success: function(data){
			$.messager.progress('close');
			data = eval("("+data+")");
			if(data.success){
				 $.messager.alert("操作提示", data.message,"info");
				 //重新加载表格数据
				 queryButt();
				//关闭窗口
				closeWin("buttWin");
			}else{
				$.messager.alert("操作提示", data.message,"error");
			}
		},
		onLoadError:function(data){
			$.messager.alert("操作提示", data.message,"error");
		}
	});
}
//按钮失效
function deleteButt(index){
	$.messager.confirm('确认',"确认禁用该按钮？",function(r){    
	    if (r){ 
	    	$("#buttResourcesGrid").datagrid("selectRow",index);
	    	var row = $("#buttResourcesGrid").datagrid("getSelected");
	    	$.ajax({
	    		url:contextPath + '/resources/updateButtValidInd.do',
	    		type: "POST", 
	    		data:{
	    			resourceId:row.resourceId,
	    			validInd:'0'
	    		},
	            success: function (data) { 
	            	data = eval("("+data+")");
	            	if(data.success){
	    				 $.messager.alert("操作提示", data.message,"info");
	    				 //重新加载表格数据
	    				 queryButt();
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
//开启按钮
function openButt(index){
	$.messager.confirm('确认',"确认启用该按钮？",function(r){    
	    if (r){ 
	    	$("#buttResourcesGrid").datagrid("selectRow",index);
	    	var row = $("#buttResourcesGrid").datagrid("getSelected");
	    	$.ajax({
	    		url:contextPath + '/resources/updateButtValidInd.do',
	    		type: "POST", 
	    		data:{
	    			resourceId:row.resourceId,
	    			validInd:'1'
	    		},
	            success: function (data) { 
	            	data = eval("("+data+")");
	            	if(data.success){
	    				 $.messager.alert("操作提示", data.message,"info");
	    				 //重新加载表格数据
	    				 queryButt();
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

//根据搜索条件查询按钮
function searchButt(){
	var resourceName = $("#searchResourceName").val();
	//菜单id
	var parentId = $("#searchMenuId").combotree("getValue");
	var validInd = $("#searchButtValidInd").combobox("getValue");
	$('#buttResourcesGrid').datagrid('load',{
		resourceName: resourceName,
		parentId: parentId,
		validInd:validInd
	});
}
