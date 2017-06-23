
$(function(){

//	initSystem();
	initValidInd();
	initSysEmployeeGrid();
	initInfo();
});

function initInfo(){
	$('.easyui-filebox').filebox({    
	    buttonText:"选择文件",
	});
}

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
function initSysEmployeeGrid(){
	var toolbar='';
	
	
	$('#employeeGrid').datagrid({
	    title:'批量上传列表',
	    toolbar:toolbar,
	    pagination: true,
	    singleSelect:true,
	    checkOnSelect:true,
	    pageSize:10,
	    rownumbers:true,
	    width:'auto',
	    idField:'jobNo',
	    columns:[[
	  	        {field:'ck',checkbox:true },
	  	        {field:'jobNo',title:'员工工号'},
	  	        {field:'employeeName',title:'员工姓名'},
	  	        {field:'status',title:'状态'},
	  	        {field:'employeeType',title:'员工类型'},
	  	        {field:'positionName',title:'职位'},
	  	        {field:'egressStandard',title:'外派津贴标准'},
	  	        {field:'baseSalary',title:'基本工资'},
	  	        {field:'synAllowance',title:'综合补贴'},
	  	        {field:'monthBonusBase',title:'月度奖金基数'},
	  	        {field:'quarterBonusBase',title:'季度奖金基数'},
	  	        {field:'yearBonusBase',title:'年度奖金基数'},
	  	        {field:'email',title:'邮箱'}
	  	        
	  	    ]],
	    onLoadSuccess:function(data){
	    	
        }
	});
	
	//设置分页控件 
    var p = $('#employeeGrid').datagrid('getPager'); 
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
				 initSysEmployeeGrid();
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



//查询角色
function query(){
	var roleCode = $("#searchRoleCode").val();
	var roleCname = $("#searchRoleCname").val();
//	var sysCode = $("#sysCombox").combobox("getValue");
	var validInd = $("#searchValidInd").combobox("getValue");
	$('#employeeGrid').datagrid('load',{
		roleCode:roleCode,
		roleCname:roleCname,
		validInd:validInd
	});
}

//全局变量，缓存角色ID
var globalRoleCode;
var globalSysCode;


function uploadDeleteExcel() {
	$.messager.confirm('确认','您确认删除上传工号的员工信息吗？',function(r){    
	    if (r){    
	    	// 得到上传文件的全路径
	    	var fileName = $('#uploadDeleteExcel').filebox('getValue');
	    	// 获取题型
	    	// var id= $('#questionType').combobox('getValue');
	    	// var questionTypes=encodeURI(id);
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
	    				$("#uploadDeleteExcelFrm").form("submit", {
	    					url : contextPath + '/employee/deleteUpload.do',
	    					onSubmit : function() {
	    					},
	    					success : function(result) {
	    						$.messager.progress('close');
	    						var obj = jQuery.parseJSON(result);
	    						$.messager.alert("提示信息", obj.msg);
	    						initSysEmployeeGrid();
	    						//fetchData(obj.attributes.columns,obj.attributes.userCodeList,obj.attributes.alis);
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

function checkEmployeeExcel() {   
	// 得到上传文件的全路径
	var fileName = $('#uploadExcel').filebox('getValue');
	// 获取题型
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
				$("#uploadExcelFrm").form("submit", {
					url : contextPath + '/employee/checkEmployeeUpload.do',
					onSubmit : function() {
					},
					success : function(result) {
						$.messager.progress('close');
						var obj = jQuery.parseJSON(result);
						var list;
						if(obj.attributes){
							if(obj.attributes.list){								
								list = obj.attributes.list;
							}
						}
						
						var jobList = "";
						if(list!=null&&list!=""){								
							for (var int = 0; int < list.length; int++) {
								jobList = jobList + list[int]+"<br>";
							}
						}
						if(obj.success){
							if(obj.obj==1){
								$.messager.confirm('确认',obj.msg +"<br>"+ jobList+'使用金蝶系统数据覆盖Excel数据吗？',function(r){    
								    if (r){
								    	uploadEmployeeExcel(true);
								    }else{
								    	uploadEmployeeExcel(false);
								    }    
								}); 
							}else if(obj.obj==0){
								uploadEmployeeExcel(false);
							}
						}else{
							$.messager.alert("提示信息", obj.msg +"<br>"+ jobList);
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

function uploadEmployeeExcel(flag){
		$.messager.progress({ title: 'excel数据导入中', msg: '数据导入中...'});
		$.ajax({
			url:contextPath + '/employee/employeeUpload.do?flag='+ flag,
			type: "POST", 
			dataType:'json', 
	        success: function (result) { 
	        	$.messager.progress('close');
				$.messager.alert("提示信息", result.msg);
				fetchData(result.attributes.jobNoList);
	        },
			error: function () { 
				 $.messager.alert("操作提示", obj.msg,"error");
	        }
		});
	        
}

function fetchData(jobNoList) {
	var uclist="";
	  
	for (var i = 0; i < jobNoList.length; i++) {  
		uclist = uclist + jobNoList[i]+",";  
	}

	
    options={};
    options.url = contextPath + '/employee/queryEmployeePageByExcel.do';  
    options.queryParams = {  
    	jobNoList:uclist
    }; 
    
    
      
    $('#employeeGrid').datagrid(options);  
    //$('#roleGrid').datagrid('reload');     
      
} 

