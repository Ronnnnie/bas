$(function() {

	initProjectDataGrid();
	importFile();
	initInfo();

});

function initInfo(){
	$('.easyui-filebox').filebox({
	       buttonText:"选择文件",
	});
}

Date.prototype.Format = function (fmt) { //author: meizz 
    var o = {
        "M+": this.getMonth() + 1, //月份 
        "d+": this.getDate(), //日 
        "h+": this.getHours(), //小时 
        "m+": this.getMinutes(), //分 
        "s+": this.getSeconds(), //秒 
        "q+": Math.floor((this.getMonth() + 3) / 3), //季度 
        "S": this.getMilliseconds() //毫秒 
    };
    if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (var k in o)
    if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
    return fmt;
}


// 初始化所有系统清单
function initProjectDataGrid(columnsData,userCodeList) {
	// 不是超级管理员，没有操作按钮

	if(columnsData==null||columnsData==''){
		columnsData="{field:'proName',title:'项目名字',width:'15%'}";
	}
	$('#projectDataGrid').datagrid({
		title : '批量上传管理',
		pagination : true,
		singleSelect : true,
		checkOnSelect : true,
		pageSize : 10,
		rownumbers : true,
		width : 'auto',
		idField : 'roleCode',
		onLoadSuccess : function(data) {

		}
	});
	
	//fetchData(columnsData,userCodeList);  

	// 设置分页控件
	var p = $('#projectDataGrid').datagrid('getPager');
	$(p).pagination({
		pageSize : 10,// 每页显示的记录条数，默认为10
		pageList : [ 5, 10, 15 ],// 可以设置每页记录条数的列表
		beforePageText : '第',// 页数文本框前显示的汉字
		afterPageText : '页    共 {pages} 页',
		displayMsg : '当前显示 {from} - {to} 条记录   共 {total} 条记录'
	});
}

function fetchData(nj,userCodeList,proAliseList) {
	var uclist="";
	var paList="";
	  
	for (var i = 0; i < userCodeList.length; i++) {  
		uclist = uclist + "'" +userCodeList[i]+"',";  
	}
	
	for (var i = 0; i < proAliseList.length; i++) {  
		paList = paList + proAliseList[i]+",";  
	}
	
    var s = "";  
    s = "[[";  
      
    //lu todo 列的定义可从服务器获得  
    if (nj!=null||nj!='') {  
        s = s + nj;     
    }  
    s = s + "]]";  
    options={};
    options.url = contextPath + '/salary/project/queryProDataPage.do';  
    options.queryParams = {  
    	userCodeList:uclist,
    	proAliseList:paList
    }; 
    options.columns = eval(s);  
      
    $('#projectDataGrid').datagrid(options);  
      
}  

// 初始化系统信息
function initAddForm(sysCodeId, validIndId) {
	$("#" + sysCodeId).combobox({
		url : contextPath + "/sysServer/initSystem.do",
		panelHeight : 200,
		editable : false,
		valueField : 'sysCode',
		textField : 'sysCname'
	});
	$("#" + validIndId).combobox({
		data : [ {
			text : '有效',
			value : '1'
		}, {
			text : '失效',
			value : '0'
		}, ],
		panelHeight : 80,
		editable : false,
		valueField : 'value',
		textField : 'text',
		value : '1'
	});
}

function closeWin(win) {
	$("#" + win).window("close");
}

// 查询角色
function query() {
	var roleCname = $("#searchRoleCname").val();
	var sysCode = $("#sysCombox").combobox("getValue");
	var validInd = $("#searchValidInd").combobox("getValue");
	$('#projectDataGrid').datagrid('load', {
		roleCname : roleCname,
		sysCode : sysCode,
		validInd : validInd
	});
}

// 全局变量，缓存角色ID
var globalRoleCode;
var globalSysCode;

// 导入文件
function importFile() {
	$("#importFile").uploadify({
		buttonText : '上传',
		auto : true,
		progressData : 'speed',
		multi : false,
		height : 18,
		width : 40,
		successTimeout : 1800,
		fileTypeDesc : '文件格式:',
		queueID : 'afileQueue',
		fileObjName : 'file',
		fileTypeExts : '*.xlsx',
		swf : contextPath + '/js/uploadify/uploadify.swf',
		uploader : contextPath + '/salary/project/upload.do',
		onInit : function() {
		},
		onUploadSuccess : function(file, data, response) {
			var d = $.parseJSON(data);
			alert(d.msg + "上传成功!");
		}
	/*
	 * onComplete: function (event, ID, fileObj, response, data) {
	 * //每完成一次文件上传时触发一次 alert('There are ' + data.fileCount + ' files remaining
	 * in the queue.'); }
	 */
	});
}

function uploadFile() {
	$('#importFile').uploadify('upload', '*')()
}

function uploadProExcel() {
	
	var importCountDate = $("#importCountDate").val();
	var r = /^\d{6,}$/;
	
	
	
	if (importCountDate && typeof(importCountDate)!="undefined" && importCountDate!=0)
	{
	    if(!r.test(importCountDate)){
	    	$.messager.alert("操作提示", "起始时间输入有误，请输入数字且长度必须为6位","error");
	    	return;
	    }
	    var month = importCountDate.substring(4,6);
	    if(month>12){
	    	$.messager.alert("操作提示", "起始时间输入有误，月份不可大于12","error");
	    	return;
	    }
	    var nowTime = new Date().Format("yyyyMM");
	    if(importCountDate>nowTime){
	    	$.messager.alert("操作提示", "计算时间输入有误，不可超过当前月份","error");
	    	return;
	    }
	    if(nowTime-importCountDate>1){
	    	$.messager.alert("操作提示", "计算时间输入有误，只能输入当月或者上一个月","error");
	    	return;
	    }
	    
	}else{
		$.messager.alert("操作提示", "请输入计算时间！","error");
    	return;
	}
	
	// 得到上传文件的全路径
	var fileName = $('#uploadExcel').filebox('getValue');
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
				/*$("#questionTypesManage").form("submit", {
					url : contextPath + '/salary/project/upload.do',
					onSubmit : function() {
					},
					success : function(result) {
						$.messager.progress('close');
						var obj = jQuery.parseJSON(result);
						$.messager.alert("提示信息", obj.msg);
						//initSysRoleGrid(obj.attributes.columns,obj.attributes.userCodeList);
						fetchData(obj.attributes.columns,obj.attributes.userCodeList,obj.attributes.alis);
					}
				});*/
				
				//-------------------------------------
				$('#questionTypesManage').form({ 
					url : contextPath + '/salary/project/upload.do',
					onSubmit: function(){ 
					// 做某些检查 
					// 返回 false 来阻止提交 
					}, 
					success:function(data){ 
						$.messager.progress('close');
						var obj = jQuery.parseJSON(data);
						
						if(obj.success){
							$.messager.alert("提示信息", obj.msg);
							fetchData(obj.attributes.columns,obj.attributes.userCodeList,obj.attributes.alis);
	    				}else{
	    					var list;
							if(obj.attributes){
								if(obj.attributes.notExistJobNo){								
									list = obj.attributes.notExistJobNo;
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
								$.messager.alert("操作提示","下列员工号不存在：<br>"+ jobList,"error");
							}else{
								$.messager.alert("操作提示",obj.msg,"error");
							}
	    				}
					} 
					}); 
					$('#questionTypesManage').submit(); 
				//-------------------------------------
				
			} else {
				
				$.messager.alert('提示', '请选择xls格式文件！', 'info');
				$('#uploadExcel').filebox('setValue', '');
			}
		}
	} else {
		$.messager.alert('提示', '请选择需要上传的文件！', 'info');
	}

}
