
$(function(){
	initEmailGrid();
	initStatus();
	initInfo();
});


//全局变量，缓存角色ID
var globalRoleCode;
var globalSysCode;
//资源分配

function initInfo(){
    $('.easyui-filebox').filebox({
       buttonText:"选择文件",
    });
}

function initEmailGrid(){
	$('#emailGrid').datagrid({
		title : '发送邮件配置',
		singleSelect : false,
		checkOnSelect : true,
		pageSize : 1,
		rownumbers : true,
		width :  function () { return document.body.clientWidth * 1.9 },
		url : contextPath + "/salary/report/queryEmailSenderPage.do",

		columns : [ [
		{field : 'emailUsername',title : '发送邮箱'},
		{field:'opr',title:'操作',width:'20%',
            formatter:function(value, row, index){
            	//不是超级管理员，没有操作按钮
            	/*if(!isSuperAdmin){
            		return;
            	}*/
            	var edit = '<a  class="editcls"  href="javascript:;" onclick="openEmailSender('+index+')" ></a>';
            	return edit;
            }
        }
    ]],
		
	 onLoadSuccess:function(data){
	 $('.editcls').linkbutton({text:'修改',plain:true,iconCls:'icon-edit'});
	 }
	});
	
	var toolbar='';
	
	//if(isSuperAdmin){
		toolbar = [{
			iconCls: 'icon-add',
			text:'重新发送',
			handler: function(){
				var row = $("#sendEmailGrid").datagrid("getSelections");
				var parm = "";
				for(var i = 0; i < row.length;i++){
					parm += row[i].jobNo;
					if(i === row.length-1){
						break;
					}
					parm +=",";
				}
				if (row.length == 0) {
					alert("请选择需要重新发送邮件的员工");
					return;
				}
				var jobNo = row[0].jobNo;
				
				$.messager.progress({ title: '邮件发送', msg: '邮件发送中，请稍等...'});
				$.ajax({
					async : true,
					url : contextPath + '/salary/report/sendEmailByJobNo.do',
					type : "POST",
					data : {
						jobNoArray : parm
					},
					success : function(data) {
						$.messager.progress('close');
						if(data.success){
			        		$.messager.alert("操作提示", data.msg,"info");
			        		fetchData(data.attributes.jobNoList);
						}else{
							$.messager.alert("操作提示", data.msg,"error");
						}
						
					},
					error : function() {
						row.length = 0;
					}
				});
			}
		},
		{
			iconCls: 'icon-add',
			text:'重新发送失败邮件',
			handler: function(){
				$.messager.confirm('确认','您确认重新发送失败邮件吗？',function(r){    
				    if (r){ 
						$.messager.progress({ title: '重新发送失败邮件', msg: '邮件发送中，请稍等...'});
						$.ajax({
							async : true,
							url : contextPath + '/salary/report/sendFailureEmail.do',
							type : "POST",
							success : function(data) {
								$.messager.progress('close');
								if(data.success){
					        		$.messager.alert("操作提示", data.msg,"info");
					        		fetchData(data.attributes.jobNoList);
								}else{
									
									var list;
									if(data.attributes){
										if(data.attributes.list){								
											list = data.attributes.list;
										}
									}
									
									var jobList = "";
									if(list!=null&&list!=""){		
										if(list.length>10){
											for (var int = 0; int < 10; int++) {
												jobList = jobList + list[int]+"<br>";
											}
										}else{									
											for (var int = 0; int < list.length; int++) {
												jobList = jobList + list[int]+"<br>";
											}
										}
									}
									$.messager.alert("操作提示", data.msg+"<br>"+jobList,"error");
									if(data.attributes){
										fetchData(data.attributes.jobNoList);
									}
								}
								
							}
						});
				    }
				});
			}
		},{
			iconCls: 'icon-remove',
			text:'删除邮件发送信息',
			handler: function(){
				deleteEmailStatus();
			}
		},{
			iconCls: 'icon-remove',
			text:'删除所有失败邮件发送信息',
			handler: function(){
				deleteFailedEmailStatus();
			}
		},{
			iconCls: 'icon-remove',
			text:'删除所有邮件发送信息',
			handler: function(){
				deleteAllEmailStatus();
			},
			
		},{
			iconCls: 'icon-redo',
			text:'导出',
			handler: function(){
		        
    			var form=$("<form>");//定义一个form表单
    			form.attr("style","display:none");
    			form.attr("target","");
    			form.attr("method","post");
    			form.attr("action",contextPath + '/salary/report/exportSendmailsStatus.do');
    			var input1=$("<input>");
    			input1.attr("type","hidden");
    			input1.attr("name","exportData");
    			input1.attr("value",(new Date()).getMilliseconds());
    			$("body").append(form);//将表单放置在web中
    			form.append(input1);
    			form.submit();//表单提交 
			}
		}
		
		];
	//}
	
	$('#sendEmailGrid').datagrid({
	    title:'发送邮件列表',
	    toolbar : toolbar,
	    pagination: true,
	    singleSelect:false,
	    checkOnSelect:true,
	    pageSize:10,
	    rownumbers:true,
	    width:'auto',
	    url : contextPath + "/salary/report/queryEmailSendingStatusPage.do",
	    columns:[[
	  	        {field:'ck',checkbox:true },
	  	        {field:'jobNo',title:'员工工号',width:'10%'},
	  	        {field:'status',title:'状态',width:'10%',
	  	        	formatter:function(value,row,index){
		        		if (value == 'S'){
		        			return '<samp style="color: green;">成功</samp>';
		        		}else if(value == 'F'){
		        			return '<samp style="color: red;">失败</samp>';
		        		}
		        	}
	  	        },
	  	        {field:'countDate',title:'工资日期',width:'10%'},
	  	        {field:'information',width:'20%',title:'信息'}
	  	    ]],
	    onLoadSuccess:function(data){
	    	$('.assignmentcls').linkbutton({text:'重新发送',plain:true,iconCls:'icon-edit'});
        }
	});
	
	//设置分页控件 
    var p = $('#sendEmailGrid').datagrid('getPager'); 
    $(p).pagination({ 
        pageSize: 10,//每页显示的记录条数，默认为10 
        pageList: [5,10,15],//可以设置每页记录条数的列表 
        beforePageText: '第',//页数文本框前显示的汉字 
        afterPageText: '页    共 {pages} 页', 
        displayMsg: '当前显示 {from} - {to} 条记录   共 {total} 条记录' 
    }); 

}

//发送单个邮件
function sendSingleEmail(){
	$.messager.progress({ title: '发送邮件', msg: '发送中...'});
	var singleUserNo = $('#singleUser').val();
	if(singleUserNo==null || singleUserNo==""){
		 $.messager.progress('close');
		 $.messager.alert("操作提示", "请选择输入员工工号","error");
		 return;
	}
	//ajax保存数据
	$.ajax({
		url:contextPath + '/salary/report/sendSingleEmail.do',
		type: "POST", 
		data:{
			userNo:singleUserNo
		},
        success: function (data) { 
        	$.messager.progress('close');
        	if(data.success){
        		$.messager.alert("操作提示", data.msg,"info");
        		fetchData(data.attributes.jobNoList);
			}else{
				$.messager.alert("操作提示", data.msg,"error");
				if(data.attributes){
					fetchData(data.attributes.jobNoList);
				}
			}
        },
		error: function () { 
			var obj = jQuery.parseJSON(data);
			$.messager.progress('close');
			$.messager.alert("操作提示", obj.msg,"error");
        }
	});
}

function sendAllEmail(){
	$.messager.progress({ title: '发送邮件', msg: '发送中...'});
	//ajax保存数据
	$.ajax({
		url:contextPath + '/salary/report/sendAllEmail.do',
		type: "POST", 
		data:{
		},
        success: function (data) { 
        	$.messager.progress('close');
        	if(data.success){
        		$.messager.alert("操作提示", data.msg,"info");
        		fetchData(data.attributes.jobNoList);
			}else{
				$.messager.alert("操作提示", data.msg,"error");
				if(data.attributes){
					fetchData(data.attributes.jobNoList);
				}
			}
        },
		error: function () { 
			var obj = jQuery.parseJSON(data);
			$.messager.progress('close');
			 $.messager.alert("操作提示", obj.msg,"error");
        }
	});
}


function confirm(control) {  
    $.messager.confirm("确认", "确认全员发送吗?", function (r) {  
        if (r) {  
        	sendAllEmail();  
        }  
    });  
    return false;  
}  


function sendEmailByUpload() {
	$.messager.confirm('确认','您确认发送上传工号的员工薪资邮件吗？',function(r){    
	    if (r){    
	    	// 得到上传文件的全路径
	    	var fileName = $('#uploadExcel').filebox('getValue');
	    	if (fileName != "") {
	    		// 进行基本校验
	    		if (fileName == "") {
	    			$.messager.alert('提示', '请选择上传文件！', 'info');
	    		} else {
	    			// 对文件格式进行校验
	    			var d1 = /\.[^\.]+$/.exec(fileName);
	    			if (d1 == ".xls" || d1 == ".xlsx") {
	    				$.messager.progress({ title: 'excel数据导入中', msg: '数据导入中...'});
	    				// 提交表单
	    				$("#uploadEmployeeNoFrm").form("submit", {
	    					url : contextPath + '/salary/report/sendEmailByUpload.do',
	    					onSubmit : function() {
	    					},
	    					success : function(result) {
	    						$.messager.progress('close');
	    						var obj = jQuery.parseJSON(result);  							    						
	    						if(obj.success){
	    			        		$.messager.alert("操作提示", obj.msg,"info");
	    			        		fetchData(obj.attributes.jobNoList);
	    						}else{
	    							var list;
	    							var jobList = "";
	    							if(obj.attributes){
		    							if(obj.attributes.list){								
		    								list = obj.attributes.list;
		    							}
		    						}
		    						if(list!=null&&list!=""){
		    							if(list.length>=10){
		    								for (var int = 0; int < 10; int++) {
		    									jobList = jobList + list[int]+"<br>";
		    								}
		    								jobList = jobList + "...";
		    							}else{
		    								for (var int = 0; int < list.length; int++) {
		    									jobList = jobList + list[int]+"<br>";
		    								}
		    							}
		    						}
	    							$.messager.alert("提示信息", obj.msg+jobList,"error");
	    						}
	    					}
	    				});
	    			} else {
	    				
	    				$.messager.alert('提示', '请选择xls格式文件！', 'info');
	    				$('#uploadExcel').filebox('setValue', '');
	    			}
	    		}
	    	} else {
	    		$.messager.alert('提示', '请选择上传的工号！', 'info');
	    	}
	    }    
	}); 

}


function openEmailSender(index){
	var row = $('#emailGrid').datagrid('getData').rows[index];
	if(row==null||row==''){
		row = $("#emailGrid").datagrid("getSelected");
	}
	$("#emailSenderWin").show();
	$win = $("#emailSenderWin").window({
		title:"修改发送邮箱配置",
		width:'400px',
		height:'200px',
		minimizable:false,
		collapsible:false,
		maximizable:false
	});
	$win.window("open");
	$("#emailSenderFrm").form("load",row);
}

function closeWin(win){
	$("#"+win).window("close"); 
}


//修改数据
function updateEmailSender(){
//	var roleCode = $("#updateRoleCode").val();
	$('#emailSenderFrm').form('submit', {
		url: contextPath + '/salary/report/updateEmailSender.do',
		onSubmit:function(param){
//			param.roleCode=roleCode;
			if($(this).form('validate')){
				$.messager.progress({ title: '修改EmailSender信息', msg: '保存数据...'});
			}
			return $(this).form('validate');
		},
		success: function(data){
			$.messager.progress('close');
			data = eval("("+data+")");
			if(data.success){
				$.messager.alert("操作提示", data.message,"info");
				 //重新加载表格数据
				initEmailGrid();
				 //关闭窗口
				 closeWin('emailSenderWin');
			}else{
				$.messager.alert("操作提示", data.message,"error");
			}
		},
		onLoadError:function(data){
			$.messager.alert("操作提示", data.message,"error");
		}
	});
}



function fetchData(jobNoList) {
	var jlist="";
	  
	  
    options={};
    options.url = contextPath + '/salary/report/queryEmailSendingStatusPage.do';  
    if(jobNoList){		
    	for (var i = 0; i < jobNoList.length; i++) {  
    		jlist = jlist +jobNoList[i]+",";  
    	}
    	options.queryParams = {  
    			jobNoArray:jlist,
    	}; 
    }
      
    $('#sendEmailGrid').datagrid(options);  
      
}  

function initStatus(){
	 $("#searchStatus").combobox({
			data : [ {text : '全部', value : ''},
			               {text : '成功', value : 'S'}, 
	                       {text : '失败', value : 'F'},
						 ],
			width:"90px",
			panelHeight:80,
			editable:false,
			valueField:'value',    
			textField:'text' 
		});
}

function query(){
	var searchStatus = $("#searchStatus").combobox("getValue");
	$('#sendEmailGrid').datagrid('load',{
		status:searchStatus
	});
}

function deleteEmailStatus() {
	var rows = $("#sendEmailGrid").datagrid("getSelections");
	var employeeNo = [];
	for (var int = 0; int < rows.length; int++) {
		employeeNo[int] = rows[int].jobNo;
	}

	if (0 === employeeNo.length) {
		$.messager.alert("操作提示", "请选择需要操作的信息", "info");
		return;
	}
	$.messager.confirm('确认', '您确认删除所选邮件发送信息吗？', function(r) {
		if (r) {

			$.ajax({
				url : contextPath + '/salary/report/deleteEmailSendingStatus.do',
				data : {
					"rows" : employeeNo
				},
				dataType : "json",
				type : "GET",
				async : true,
				traditional : true,// 这个设置为true，data:{"steps":["qwe","asd","zxc"]}会转换成steps=qwe&steps=asd&...
				success : function(data) {
					if (data.success) {
						$.messager.alert("操作提示", data.msg, "info");
						// 重新加载表格数据
						initStatus();
					} else {
						$.messager.alert("操作提示", data.msg, "error");
					}
					rows.length = 0;
				},
				error : function() {
					$.messager.alert("操作提示", data.msg, "error");
					rows.length = 0;
				}
			});
		}
	});
}

function deleteFailedEmailStatus() {
	$.messager.confirm('确认', '您确认删除所有错误邮件发送信息吗？', function(r) {
		if (r) {

			$.ajax({
				url : contextPath + '/salary/report/deleteEmailSendingStatus.do',
				data : {
					"type" : "failed"
				},
				dataType : "json",
				type : "GET",
				async : true,
				traditional : true,// 这个设置为true，data:{"steps":["qwe","asd","zxc"]}会转换成steps=qwe&steps=asd&...
				success : function(data) {
					if (data.success) {
						$.messager.alert("操作提示", data.msg, "info");
						fetchData(null);
					} else {
						$.messager.alert("操作提示", data.msg, "error");
					}
				},
				error : function() {
					$.messager.alert("操作提示", data.msg, "error");
				}
			});
		}
	});
}

function deleteAllEmailStatus() {
	$.messager.confirm('确认', '您确认删除所有邮件发送信息吗？', function(r) {
		if (r) {

			$.ajax({
				url : contextPath + '/salary/report/deleteEmailSendingStatus.do',
				data : {
					"type" : "all"
				},
				dataType : "json",
				type : "GET",
				async : true,
				traditional : true,// 这个设置为true，data:{"steps":["qwe","asd","zxc"]}会转换成steps=qwe&steps=asd&...
				success : function(data) {
					if (data.success) {
						$.messager.alert("操作提示", data.msg, "info");
						fetchData(null);
					} else {
						$.messager.alert("操作提示", data.msg, "error");
					}
				},
				error : function() {
					$.messager.alert("操作提示", data.msg, "error");
				}
			});
		}
	});
}

