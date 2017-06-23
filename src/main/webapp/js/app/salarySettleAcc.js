
$(function(){

	initSalaryCountGrid();
//	initValidInd();
});


//初始化查询条件中的状态
function changeStatus(index,index2){
	$.ajax({
		url:contextPath + '/salary/count/updateSettleStatus.do',
		dataType:"json",
		data:{
			userCode:index,
			status : $('#'+index).val()
		},
		type: "POST", 
		async : true,
        success: function (data) { 
        	if(!data.success){
				$.messager.alert("操作提示", data.msg,"error");
			}
        },
		error: function () { 
			 $.messager.alert("操作提示", data.msg,"error");
        }
	});
	
}
//初始化所有系统清单
function initSalaryCountGrid(){
	var toolbar='';
	//不是超级管理员，没有操作按钮
	
	
	$('#salaryCountGrid').datagrid({
	    title:'薪资计算列表',
	    toolbar:toolbar,
	    pagination: true,
	    singleSelect:true,
	    checkOnSelect:true,
	    pageSize:10,
	    fit:true,
	    rownumbers:true,
	    width:'auto',
	    idField:'userCode',
	    url:contextPath + "/salary/count/queryLockedSalaryDataPage.do",
	    columns:[[
	        {field:'userCode',title:'员工工号'},
	        {field:'userName',title:'姓名'},
	        {field:'taxtype',title:'计税类别'},
	        {field:'sattend',title:'应出勤天数'},
	        {field:'fattend',title:'实际出勤天数'},
	        {field:'bsalary',title:'基本工资'},
	        {field:'tsubsi',title:'综合补贴'},
	        {field:'mprizeb',title:'月度奖金基数'},
	        {field:'qprizeb',title:'季度奖金基数'},
	        {field:'yprizeb',title:'年度奖金基数'},
	        {field:'countDate',title:'核算日期'},
	        {field:'paidstatus',title:'发薪状态',
	        	 formatter:function(value, row, index){
	        		 var temp = "";
	        		 if("1" == value){
	        			 temp = "无效";
	        		 }else if("0" == value){
	        			 temp = "有效";
	        		 }
//	        		 var userCode = row.userCode;
	        		 return  "<input value="+value+" onblur=\"changeStatus('"+row.userCode+"','"+index+"')\"  id='"+row.userCode+"' name=\"state\" style=\"width:80px;\"></input>";
	        	 
	        	 }
	        },
	        {field:'opr',title:'操作',width:'220',
                formatter:function(value, row, index){
                	//不是超级管理员，没有操作按钮
//                	if(!isSuperAdmin){
//                		return;
//                	}
                	var view = '<a  class="viewcls"  href="javascript:;" onclick="openPersonSalary('+index+')" ></a>';
                	return view;
//                	}
                }
            }
	    ]],
	    onLoadSuccess:function(data){
	    	$('.viewcls').linkbutton({text:'详情',plain:true,iconCls:'icon-edit'});
        }
	});
	
	//设置分页控件 
    var p = $('#salaryCountGrid').datagrid('getPager'); 
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

function countPersonSalary(){
	
	$('#countFrm').form('submit', {
		url: contextPath + '/salary/count/countPersonSalary.do',
		onSubmit:function(param){
			if($(this).form('validate')){
				$.messager.progress({ title: '薪资计算', msg: '薪资计算中,请稍等......'});
			}
			return $(this).form('validate');
		},
		success: function(data){
			$.messager.progress('close');
			data = eval("("+data+")");
			if(data.success){
				
				 $.messager.alert("操作提示", data.message,"info");
//				 重新加载表格数据
				 initSalaryCountGrid();
//				关闭窗口
				closeWin('personCountWin');
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



//查询角色
function query(){
	var userCode = $("#searchUserCode").val();
	var startCountDate = $("#searchStartCountDate").val();
	var endCountDate = $("#searchEndCountDate").val();
	var r = /^\d{6,}$/;
	
	if (startCountDate && typeof(startCountDate)!="undefined" && startCountDate!=0)
	{
	    if(!r.test(startCountDate)){
	    	$.messager.alert("操作提示", "起始时间输入有误，请输入数字且长度必须为6位","error");
	    	return;
	    }
	    var month = startCountDate.substring(4,6);
	    if(month>12){
	    	$.messager.alert("操作提示", "起始时间输入有误，月份不可大于12","error");
	    	return;
	    }
	    
	}
	
	if (endCountDate && typeof(endCountDate)!="undefined" && endCountDate!=0)
	{
	    if(!r.test(endCountDate)){
	    	$.messager.alert("操作提示", "结束时间输入有误，请输入数字且长度必须为6位","error");
	    	return;
	    }
	    var month = endCountDate.substring(4,6);
	    if(month>12){
	    	$.messager.alert("操作提示", "起始时间输入有误，月份不可大于12","error");
	    	return;
	    }
	}
	
	if(startCountDate && typeof(startCountDate)!="undefined" && startCountDate!=0 
			&& endCountDate && typeof(endCountDate)!="undefined" && endCountDate!=0){
		if(startCountDate>endCountDate){
	    	$.messager.alert("操作提示", "结束时间不能小于起始时间","error");
	    	return;
	    }
	}
	
	$('#salaryCountGrid').datagrid('load',{
		userCode:userCode,
		startCountDate:startCountDate,
		endCountDate:endCountDate
	});
}

//全局变量，缓存角色ID
var globalRoleCode;
var globalSysCode;
//资源分配


function openPersonSalary(index){
	$("#salaryCountGrid").datagrid("selectRow",index);
	var row = $("#salaryCountGrid").datagrid("getSelected");
	$("#viewPersonSalaryWin").show();
	$win = $("#viewPersonSalaryWin").window({
		title:"查看薪资计算详情",
		width:'400px',
		height:'500px',
		minimizable:false,
		collapsible:false,
		maximizable:false
	});
	$win.window("open");
	$("#viewPersonSalaryFrm").form("load",row);
}











