
$(function(){

//	initSystem();
	initValidInd();
	initSysRoleGrid();

});

//初始化系统信息
//function initSystem(){
//	$('#sysCombox').combobox({    
//	    url: contextPath + "/sysServer/initSystem.do?needNull=" + true,    
//		panelHeight:200,
//		editable:false,
//	    valueField:'sysCode',    
//	    textField:'sysCname'
//	}); 
//}
//初始化查询条件中的状态
function initValidInd(){
	 $("#searchValidInd").combobox({
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
//初始化所有系统清单
function initSysRoleGrid(){
	var toolbar='';
	//不是超级管理员，没有操作按钮，暂时不需要。
//	if(isSuperAdmin){
		toolbar = [{
			iconCls: 'icon-add',
			text:'新增',
			handler: function(){
				$("#roleWin").show();
				$win = $("#roleWin").window({
					title:"新增角色",
					width:'400px',
					height:'300px',
					minimizable:false,
					collapsible:false,
					maximizable:false
				});
				$win.window("open");
				$("#roleFrm").form('clear');
//				initAddForm("sysCodeCombobox","validInd");
			}
		}];
//	}
	
	$('#roleGrid').datagrid({
	    title:'角色列表',
	    toolbar:toolbar,
	    pagination: true,
	    singleSelect:true,
	    checkOnSelect:true,
	    pageSize:10,
	    rownumbers:true,
	    width:'auto',
	    idField:'roleCode',
	    url:contextPath + "/role/queryRolePage.do",
	    columns:[[
	        {field:'roleCode',title:'角色编码',width:'20%'},
	        {field:'roleCname',title:'角色名称',width:'20%'},
	        {field:'roleDesc',title:'角色描述',width:'20%'},
	        {field:'validInd',title:'状态',width:'15%',align:'center',
	            formatter:function(value,row,index){
	                if (value == '1'){
	                    return '<samp style="color: green;">有效</samp>';
	                } else {
	                    return '<samp style="color: red;">失效</samp>';
	                }
	            }
	        },
	        {field:'opr',title:'操作',width:'25%',
                formatter:function(value, row, index){
                	
                	//不是超级管理员，没有操作按钮
//                	if(!isSuperAdmin){
//                		return;
//                	}
                	var edit = '<a  class="editcls"  href="javascript:;" onclick="openUpdateSysRole('+index+')" ></a>';
                	var cancel = '<a  class="cancelCss"  href="javascript:;" onclick="removeSysRole('+index+')" ></a>';
                	var ok = '<a  class="okCss"  href="javascript:;" onclick="openSysRole('+index+')" ></a>';
                	var addResCss = '<a  class="addResCss"  href="javascript:;" onclick="addResources('+index+')" ></a>';
                	var del = '<a  class="delCss"  href="javascript:;" onclick="delSysRole('+index+')" ></a>';
                	if(row.roleCode == 'swcs_super_admin'){
                		return addResCss;
                	}else if(row.validInd == '1'){
                		return edit + cancel + addResCss + del;
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
            $('.addResCss').linkbutton({text:'分配资源',plain:true,iconCls:'icon-add'});
            $('.delCss').linkbutton({text:'删除',plain:true,iconCls:'icon-cancel'});
        }
	});
	
	//设置分页控件 
    var p = $('#roleGrid').datagrid('getPager'); 
    $(p).pagination({ 
        pageSize: 10,//每页显示的记录条数，默认为10 
        pageList: [5,10,15],//可以设置每页记录条数的列表 
        beforePageText: '第',//页数文本框前显示的汉字 
        afterPageText: '页    共 {pages} 页', 
        displayMsg: '当前显示 {from} - {to} 条记录   共 {total} 条记录' 
    }); 
}

function initAddForm(sysCodeId,validIndId){
	/*$("#"+sysCodeId).combobox({    
	    url: contextPath + "/sysServer/initSystem.do",
		panelHeight:200,
		editable:false,
	    valueField:'sysCode',    
	    textField:'sysCname'
	});*/
	 $("#"+validIndId).combobox({
			data : [ 
		               {text : '有效', value : '1'}, 
                       {text : '失效', value : '0'},
					 ],
			panelHeight:80,
			editable:false,
			valueField:'value',    
			textField:'text' ,
			value:'1'
		});
}

function addSysRole(){
	
	$('#roleFrm').form('submit', {
		url: contextPath + '/role/saveSysRole.do',
		onSubmit:function(param){
			if($(this).form('validate')){
				$.messager.progress({ title: '新增角色', msg: '保存数据...'});
			}
			return $(this).form('validate');
		},
		success: function(data){
			$.messager.progress('close');
			data = eval("("+data+")");
			if(data.success){
				 $.messager.alert("操作提示", data.message,"info");
				 //重新加载表格数据
				 initSysRoleGrid();
				//关闭窗口
				closeWin('roleWin');
			}else{
				$.messager.alert("操作提示", data.message,"error");
			}
		},
		onLoadError:function(data){
			$.messager.alert("操作提示", data.message,"error");
		}
	});
}

function closeWin(win){
	$("#"+win).window("close"); 
}

//初始化修改窗口
function openUpdateSysRole(index){
	
	$("#roleGrid").datagrid("selectRow",index);
	var row = $("#roleGrid").datagrid("getSelected");
	$("#updateRoleWin").show();
	$win = $("#updateRoleWin").window({
		title:"修改角色",
		width:'400px',
		height:'300px',
		minimizable:false,
		collapsible:false,
		maximizable:false
	});
	$win.window("open");
	initAddForm("updateSysCodeCombobox","updateValidInd");
	$("#updateRoleFrm").form("load",row);
}

//修改数据
function updateSysRole(){
	
	var roleCode = $("#updateRoleCode").val();
	$('#updateRoleFrm').form('submit', {
		url: contextPath + '/role/updateSysRole.do',
		onSubmit:function(param){
			param.roleCode=roleCode;
			if($(this).form('validate')){
				$.messager.progress({ title: '修改角色', msg: '保存数据...'});
			}
			return $(this).form('validate');
		},
		success: function(data){
			$.messager.progress('close');
			data = eval("("+data+")");
			if(data.success){
				$.messager.alert("操作提示", data.message,"info");
				 //重新加载表格数据
				 initSysRoleGrid();
				 //关闭窗口
				 closeWin('updateRoleWin');
			}else{
				$.messager.alert("操作提示", data.message,"error");
			}
		},
		onLoadError:function(data){
			$.messager.alert("操作提示", data.message,"error");
		}
	});
}

//启用角色
function openSysRole(index){
	$.messager.confirm('确认','您确认启用该角色吗？',function(r){    
	    if (r){    
    	 $("#roleGrid").datagrid("selectRow",index);
    		var row = $("#roleGrid").datagrid("getSelected");
    		$.ajax({
    			url:contextPath + '/role/openRole.do?roleCode='+ row.roleCode,
    			type: "GET", 
    	        success: function (data) { 
    	        	data = eval("("+data+")");
    	        	if(data.success){
    					 $.messager.alert("操作提示", data.message,"info");
    					 //重新加载表格数据
    					 initSysRoleGrid();
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
//角色失效
function removeSysRole(index){

	$.messager.confirm('确认','您确认禁用该角色吗？',function(r){    
	    if (r){    
	    	 $("#roleGrid").datagrid("selectRow",index);
	    		var row = $("#roleGrid").datagrid("getSelected");
	    		$.ajax({
	    			url:contextPath + '/role/removeRole.do?roleCode='+ row.roleCode,
	    			type: "GET", 
	    	        success: function (data) { 
	    	        	data = eval("("+data+")");
	    	        	if(data.success){
	    					 $.messager.alert("操作提示", data.message,"info");
	    					 //重新加载表格数据
	    					 initSysRoleGrid();
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

function delSysRole(index){
	$.messager.confirm('确认','与此角色关联的用户将被置为失效哦！您确认要删除这个角色吗？',function(r){
		if(r){
			$("#roleGrid").datagrid("selectRow",index);
			var row = $("#roleGrid").datagrid("getSelected");
			$.ajax({
				url:contextPath + '/role/delRole.do?roleCode='+ row.roleCode,
    			type: "GET", 
    	        success: function (data) { 
    	        	data = eval("("+data+")");
    	        	if(data.success){
    					 $.messager.alert("操作提示", data.message,"info");
    					 //重新加载表格数据
    					 initSysRoleGrid();
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

//查询角色
function query(){
	var roleCode = $("#searchRoleCode").val();
	var roleCname = $("#searchRoleCname").val();
//	var sysCode = $("#sysCombox").combobox("getValue");
	var validInd = $("#searchValidInd").combobox("getValue");
	$('#roleGrid').datagrid('load',{
		roleCode:roleCode,
		roleCname:roleCname,
		validInd:validInd
	});
}

//全局变量，缓存角色ID
var globalRoleCode;
var globalSysCode;
//资源分配
function addResources(index){
	
	 $("#roleGrid").datagrid("selectRow",index);
	var role = $("#roleGrid").datagrid("getSelected");
	$("#showroleCname").attr('value',role.roleCname);
	$("#showroleCode").attr('value',role.roleCode);
	globalRoleCode = role.roleCode;
	globalSysCode = role.sysCode;
	//初始化搜索条件中的类型
	$('#addResourcesWin').dialog({    
	    title: '分配资源',    
	    width: '600px',    
	    height: '450px',
	    left:"15%", 
	    top:"5%",
	    closed: false
	   }); 
	//角色可分配菜单
	$("#roleResTree").tree({
		title:"资源",
		url:contextPath + "/resources/queryMenuBySysCode.do?sysCode=" + 
				role.sysCode + "&roleCode="+role.roleCode
	});
}

//保存分配的资源
function addRoleResources(){
	$.messager.progress({ title: '分配资源', msg: '保存数据...'});
	var nodes = $('#roleResTree').tree('getChecked');
	if(nodes && nodes.length == 0){
		 $.messager.alert("操作提示", "请选择未分配资源","error");
	}
	//获得实心节点
	var indeterminate = $('#roleResTree').tree('getChecked', 'indeterminate');
	//将实心节点数据添加到已选中数据中
	if(indeterminate){
		for(var j= 0; j < indeterminate.length; j++){
			nodes.push(indeterminate[j]);
		}
	}
	//已分配的资源ID
	var resourceIds = [];
	for(var i = 0 ; i < nodes.length; i++){
		resourceIds.push(nodes[i].id);
	}
	//ajax保存数据
	$.ajax({
		url:contextPath + '/resources/updateRoleMenus.do',
		type: "POST", 
		data:{
			roleCode:globalRoleCode,
			sysCode:globalSysCode,
			resourceIds:resourceIds.join(",")//将数组转换为字符串
		},
        success: function (data) { 
        	$.messager.progress('close');
        	data = eval("("+data+")");
        	if(data.success){
        		$.messager.alert("操作提示", data.message,"info");
        		//关闭窗口
    			closeWin('addResourcesWin');
			}else{
				$.messager.alert("操作提示", data.message,"error");
			}
        },
		error: function () { 
			 $.messager.alert("操作提示", data.message,"error");
        }
	});
}













