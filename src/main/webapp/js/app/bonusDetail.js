$(function() {
	initSalaryReportGrid();
});

function initSalaryReportGrid(){
	$('#bonusDetail').datagrid({
		title : '销售奖金明细',
		pagination : true,
		singleSelect : true,
		checkOnSelect : true,
		fit : true,
		pageSize : 10,
		rownumbers : true,
		width : 'auto',
		idField:'serialno',
	    url:contextPath + "/bonusDetail/queryBonusDetail.do",
	    columns:[[
	        {field:'bonusmonth',title:'月份'},
	        {field:'serialno',title:'合同号'},
	        {field:'inputdate',title:'提单日期'},
	        {field:'subproducttype',title:'产品子类型'},
	        {field:'operateuserid',title:'销售ID'},
	        {field:'workid',title:'工号'},
	        {field:'username',title:'销售代表姓名'},
	        {field:'creditcycle',title:'是否购买保险'},
	        {field:'bugpaypkgind',title:'是否购买随心还'},
	        {field:'isbbd',title:'是否购买佰保贷'},
	        {field:'businesssum',title:'贷款本金'},
	        {field:'newbonus',title:'提成'},
	        {field:'qualitygrade',title:'合同质量等级'},
	        {field:'qualifyamount',title:'文件质量扣款'}
	     ]]
	});
	// 设置分页控件
	var p = $('#bonusDetail').datagrid('getPager');
	$(p).pagination({
		pageSize : 10,// 每页显示的记录条数，默认为10
		pageList : [ 5, 10, 15 ],// 可以设置每页记录条数的列表
		beforePageText : '第',// 页数文本框前显示的汉字
		afterPageText : '页    共 {pages} 页',
		displayMsg : '当前显示 {from} - {to} 条记录   共 {total} 条记录'
	});
}

function bonusSearch(){
	$("#bonusDetail").datagrid("clearSelections");
	var workid = $("#workid").val();
	var operateuserid = $("#operateuserid").val();
	var serialno = $("#serialno").val();
	var bonusmonth = $("#bonusmonth").datebox('getValue');
	$('#bonusDetail').datagrid('load',{
		workid:workid,
		operateuserid:operateuserid,
		serialno:serialno,
		bonusmonth:bonusmonth
	});
}

//重置
function bonusSearchReset(name){
//	$("#"+name).find(":input").val("");
     $('#'+name).find(':input').each(
     	function(){
     		if ($(this).attr('name') == 'bonusmonth') {
     			$(this).datebox('setValue','');
     		}
     		else{
     			$(this).val("");
     		}
     });
}

//导出
function exportXls() {
	var workid = $("#workid").val();
	var operateuserid = $("#operateuserid").val();
	var serialno = $("#serialno").val();
	var bonusmonth = $("#bonusmonth").datebox('getValue');
	if(workid=='' && operateuserid=='' && serialno=='' && bonusmonth==''){
		$.messager.alert('提示信息','请输入导出条件');
		return;
	}
	$.ajax({
		async : false,
		cache : false,
		type : 'GET',
		url : contextPath + '/bonusDetail/exportBonusDetail.do',// 请求的action路径
		data:{
			workid:workid,
			operateuserid:operateuserid,
			serialno:serialno,
			bonusmonth:bonusmonth
		},
		error : function() {// 请求失败处理函数
		},
		success : function() {
		}
	});
	$.messager.confirm('操作提示','是否跳转到报表下载页面？', function(r){
		if(r){
			window.location.href= contextPath + "/reportDownload/downloadView.do";
		}
	});
}

function myformatter(date){
	var y = date.getFullYear();
	var m = date.getMonth()+1;
	return y+'/'+(m<10?('0'+m):m);
}

function myparser(s){
	if (!s) return new Date();
	var ss = (s.split('/'));
	var y = parseInt(ss[0],10);
	var m = parseInt(ss[1],10);
	if (!isNaN(y) && !isNaN(m)){
		return new Date(y,m-1);
	} else {
		return new Date();
	}
}

