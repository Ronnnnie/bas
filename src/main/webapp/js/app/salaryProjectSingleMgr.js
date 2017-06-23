
$(function(){

	initValidInd();
	initProjectGrid();
	$("#formulaCurValue").keydown(function(){
		var curLength=$("#formulaCurValue").val().length;	
		if(curLength>=255){
			var num=$("#formulaCurValue").val().substr(0,254);
			$("#formulaCurValue").val(num);
		}
	})
});


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

function formatterdate(val, row) {
	if (val != null) {
	var date = new Date(val);
	return date.getFullYear() + '-' + (date.getMonth() + 1) + '-'
	+ date.getDate()+" "+date.getHours() + ':'+date.getMinutes() + ':'+date.getSeconds() ;
	}
}
//初始化所有系统清单
function initProjectGrid(){
	var toolbar='';
	//不是超级管理员，没有操作按钮
//	if(isSuperAdmin){
		toolbar = [{
			iconCls: 'icon-add',
			text:'新增',
			handler: function(){
				$("#projectWin").show();
				$win = $("#projectWin").window({
					title:"新增项目",
					width:'400px',
					height:'300px',
					minimizable:false,
					collapsible:false,
					maximizable:false
				});
				$win.window("open");
				$("#projectFrm").form('clear');
				initAddForm("proType","proAlise","isused","dataType");
			}
		}];
//	}
	
	$('#projectGrid').datagrid({
	    title:'单个项目管理',
	    toolbar:toolbar,
	    pagination: true,
	    singleSelect:true,
	    checkOnSelect:true,
	    pageSize:10,
	    rownumbers:true,
	    width:'auto',
	    idField:'roleCode',
	    url:contextPath + "/salary/project/queryProInfoPage.do",
	    columns:[[
	        {field:'proName',title:'项目名字',width:'15%'},
	        {field:'proType',title:'项目类型',width:'10%',align:'center',
	        	formatter:function(value,row,index){
	        		if (value == '1'){
	        			return '<samp style="color: green;">基础信息类</samp>';
	        		}else if(value == '2'){
	        			return '<samp style="color: green;">计算类</samp>';
	        		}else if(value == '3'){
	        			return '<samp style="color: green;">上传类</samp>';
	        		} else if(value == '4'){
	        			return '<samp style="color: red;">其他</samp>';
	        		}
	        	}
	        },
	        {field:'dataType',title:'数据类型',width:'10%',align:'center',
	        	formatter:function(value,row,index){
	        		if (value == '1'){
	        			return '<samp style="color: green;">数字型</samp>';
	        		}else if(value == '2'){
	        			return '<samp style="color: green;">日期型</samp>';
	        		}else if(value == '3'){
	        			return '<samp style="color: green;">文本型</samp>';
	        		}
	        	}
	        },
	        {field:'isused',title:'是否有效',width:'10%',align:'center',
	        	formatter:function(value,row,index){
	        		if (value == '1'){
	        			return '<samp style="color: green;">正常</samp>';
	        		}else{
	        			return '<samp style="color: red;">停用</samp>';
	        		}
	        	}
	        },
	        {field:'remark',title:'备注',width:'15%'},
	        {field:'createby',title:'创建人',width:'15%'},
	        {field:'createtime',title:'创建时间',width:'15%',formatter:formatterdate},
	        {field:'opr',title:'操作',width:'20%',
                formatter:function(value, row, index){
                	//不是超级管理员，没有操作按钮
//                	if(!isSuperAdmin){
//                		return;
//                	}
                	var assignment = '<a  class="assignmentcls"  href="javascript:;" onclick="openAssignmentProject('+index+')" ></a>';
                	var edit = '<a  class="editcls"  href="javascript:;" onclick="openUpdateProject('+index+')" ></a>';
                	var cancel = '<a  class="cancelCss"  href="javascript:;" onclick="deleteProject('+index+')" ></a>';
                	var formula = '<a  class="formulacls"  href="javascript:;" onclick="openFormulaProject('+index+')" ></a>';
                	if(row.proType==1){
                		return assignment;
                	}else if(row.proType==2){
                		return formula;
                	}else{
                		return assignment + edit + cancel ;
                	}
                }
            }
	    ]],
	    onLoadSuccess:function(data){
	    	$('.assignmentcls').linkbutton({text:'一键赋值',plain:true,iconCls:'icon-edit'});
            $('.editcls').linkbutton({text:'修改',plain:true,iconCls:'icon-edit'});
            $('.cancelCss').linkbutton({text:'删除',plain:true,iconCls:'icon-cancel'});
            $('.formulacls').linkbutton({text:'填写公式',plain:true,iconCls:'icon-edit'});
        }
	});
	
	//设置分页控件 
    var p = $('#projectGrid').datagrid('getPager'); 
    $(p).pagination({ 
        pageSize: 10,//每页显示的记录条数，默认为10 
        pageList: [5,10,15],//可以设置每页记录条数的列表 
        beforePageText: '第',//页数文本框前显示的汉字 
        afterPageText: '页    共 {pages} 页', 
        displayMsg: '当前显示 {from} - {to} 条记录   共 {total} 条记录' 
    }); 
}

//初始化系统信息
function initAddForm(proType,proAlise,isused,dataType){
	$("#"+proType).combobox({    
		data : [ 
                   {text : '基础信息类', value : '1',disabled:true},
		           {text : '计算类', value : '2',disabled:true},
	               {text : '上传类', value : '3'},
	               {text : '其他', value : '4'},
				 ],
		panelHeight:80,
		editable:false,
		valueField:'value',    
		textField:'text' ,
		value:'3'
	});
	
	var url = contextPath +'/salary/project/queryBackUpValue.do';
	$.getJSON(url, function(json) {
	$("#"+proAlise).combobox({
	data : json,
	panelHeight:80,
	editable:false,
	valueField:'value',
	textField:'text'
	});
	});
	
	
	/*$("#"+proAlise).combobox({
			data : [ 
		               {text : 'BACKUP1', value : 'BACKUP1'}, 
                       {text : 'BACKUP2', value : 'BACKUP1'},
					 ],
			panelHeight:80,
			editable:false,
			valueField:'value',    
			textField:'text' ,
			value:''
		});*/
	
	 $("#"+isused).combobox({
			data : [ 
		               {text : '正常', value : '1'}, 
                       {text : '停用', value : '2'},
					 ],
			panelHeight:80,
			editable:false,
			valueField:'value',    
			textField:'text' ,
			value:'1'
		});
	 $("#"+dataType).combobox({
			data : [ 
		               {text : '数字型', value : '1'}, 
		               {text : '日期型', value : '2'},
		               {text : '文本型', value : '3'}
					 ],
			panelHeight:80,
			editable:false,
			valueField:'value',    
			textField:'text' ,
			value:'1'
		});
}

function addProjectInfo(){
	var alise = $("#proAlise").combobox('getValue');
	if(alise==null||alise==""){
		$.messager.alert("操作提示", "请选择别名","error");
		return;
	}
	$('#projectFrm').form('submit', {
		url: contextPath + '/salary/project/saveProjectBaseInfo.do',
		onSubmit:function(param){
			if($(this).form('validate')){
				$.messager.progress({ title: '新增项目', msg: '保存数据...'});
			}
			return $(this).form('validate');
		},
		success: function(data){
			$.messager.progress('close');
			data = eval("("+data+")");
			if(data.success){
				 $.messager.alert("操作提示", data.message,"info");
				 //重新加载表格数据
				 initProjectGrid();
				//关闭窗口
				closeWin('projectWin');
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
function openUpdateProject(index){
	
	$("#projectGrid").datagrid("selectRow",index);
	var row = $("#projectGrid").datagrid("getSelected");
	$("#updateProjectWin").show();
	$win = $("#updateProjectWin").window({
		title:"修改项目",
		width:'400px',
		height:'300px',
		minimizable:false,
		collapsible:false,
		maximizable:false
	});
	$win.window("open");
	initAddForm("updateProType","updateProAlise","updateIsused","updateDataType");
	$("#updateProjectFrm").form("load",row);
	if(row.proType==1||row.proType==2){
		$("#updateProType").combobox('disable');
	}else{
		$("#updateProType").combobox('enable');
	}
}

//修改数据
function updateProject(){
	
	$('#updateProjectFrm').form('submit', {
		url: contextPath + '/salary/project/updateProjectBaseInfo.do',
		onSubmit:function(param){
			if($(this).form('validate')){
				$.messager.progress({ title: '修改项目', msg: '保存数据...'});
			}
			return $(this).form('validate');
		},
		success: function(data){
			$.messager.progress('close');
			data = eval("("+data+")");
			if(data.success){
				$.messager.alert("操作提示", data.message,"info");
				 //重新加载表格数据
				 initProjectGrid();
				 //关闭窗口
				 closeWin('updateProjectWin');
			}else{
				$.messager.alert("操作提示", data.message,"error");
			}
		},
		onLoadError:function(data){
			$.messager.alert("操作提示", data.message,"error");
		}
	});
}


//角色失效
function deleteProject(index){

	$.messager.confirm('确认','您确认删除该项目吗？',function(r){    
	    if (r){    
	    	 $("#projectGrid").datagrid("selectRow",index);
	    		var row = $("#projectGrid").datagrid("getSelected");
	    		var param = "proId=" + row.proId +"&proAlise="+ row.proAlise;
	    		$.ajax({
	    			url:contextPath + '/salary/project/deleteProject.do',
	    			data: param,
	    			dataType:"json",
	    			type: "GET", 
	    			async : true,
	    	        success: function (data) { 
	    	        	if(data.success){
	    					 $.messager.alert("操作提示", data.msg,"info");
	    					 //重新加载表格数据
	    					 initProjectGrid();
	    				}else{
	    					$.messager.alert("操作提示", data.msg,"error");
	    				}
	    	        },
	    			error: function () { 
	    				 $.messager.alert("操作提示", data.msg,"error");
	    	        }
	    		});
	    }    
	}); 
}



//全局变量，缓存角色ID
var globalRoleCode;
var globalSysCode;



//查询角色
function query(){
	var proName = $("#searchProName").val();
	$('#projectGrid').datagrid('load',{
		proName:proName
	});
}


function openAssignmentProject(index){
	$("#assignmentProId").val("");
	$("#assignmentProAlise").val("");
	$("#assignmentCurValue").val("");
	$("#projectGrid").datagrid("selectRow",index);
	var row = $("#projectGrid").datagrid("getSelected");
	$("#assignmentProjectWin").show();
	$win = $("#assignmentProjectWin").window({
		title:"一键赋值",
		width:'300px',
		height:'150px',
		minimizable:false,
		collapsible:false,
		maximizable:false
	});
	$win.window("open");
	$("#assignmentProjectWin").form("load",row);
	
}

//修改数据
function assignmentProject(){
	    $.messager.confirm("确认","确认一键赋值吗?", function (r) {  
	        if (r) {  
	        	$('#assignmentProjectFrm').form('submit', {
	        		url: contextPath + '/salary/project/updateProjectBaseInfo.do',
	        		onSubmit:function(param){
	        			if($(this).form('validate')){
	        				$.messager.progress({ title: '一键赋值', msg: '保存数据...'});
	        			}
	        			return $(this).form('validate');
	        		},
	        		success: function(data){
	        			$.messager.progress('close');
	        			data = eval("("+data+")");
	        			if(data.success){
	        				$.messager.alert("操作提示", data.message,"info");
	        				 //重新加载表格数据
	        				 initProjectGrid();
	        				 //关闭窗口
	        				 closeWin('assignmentProjectWin');
	        			}else{
	        				$.messager.alert("操作提示", data.message,"error");
	        			}
	        		},
	        		onLoadError:function(data){
	        			$.messager.alert("操作提示", data.message,"error");
	        		}
	        	});
	        }else{	        	
	        	return false;  
	        }  
	    });  
}


function openFormulaProject(index){
	$("#formulaProId").val("");
	$("#formulaCurValue").val("");
	$("#projectGrid").datagrid("selectRow",index);
	var row = $("#projectGrid").datagrid("getSelected");
	$("#formulaProjectWin").show();
	$win = $("#formulaProjectWin").window({
		title:"计算公式",
		width:'400px',
		height:'200px',
		minimizable:false,
		collapsible:false,
		maximizable:false
	});
	$win.window("open");
	$("#formulaProjectWin").form("load",row);
	
}

//修改数据
function formulaProject(){
    	$('#formulaProjectFrm').form('submit', {
    		url: contextPath + '/salary/project/updateProjectBaseInfo.do',
    		onSubmit:function(param){
    			if($(this).form('validate')){
    				$.messager.progress({ title: '一键赋值', msg: '保存数据...'});
    			}
    			return $(this).form('validate');
    		},
    		success: function(data){
    			$.messager.progress('close');
    			data = eval("("+data+")");
    			if(data.success){
    				$.messager.alert("操作提示", data.message,"info");
    				 //重新加载表格数据
    				 initProjectGrid();
    				 //关闭窗口
    				 closeWin('formulaProjectWin');
    			}else{
    				$.messager.alert("操作提示", data.message,"error");
    			}
    		},
    		onLoadError:function(data){
    			$.messager.alert("操作提示", data.message,"error");
    		}
    	});
	         
}






