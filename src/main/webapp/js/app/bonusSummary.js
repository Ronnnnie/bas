$(function() {
	initSalaryReportGrid();
});

function initSalaryReportGrid(){
	$('#bonusSummary').datagrid({
		title : '销售奖金汇总',
		pagination : true,
		singleSelect : true,
		checkOnSelect : true,
		fit : true,
		pageSize : 10,
		rownumbers : true,
		width : 'auto',
		idField:'WORKID',
	    url:contextPath + "/bonusSummary/queryBonusSummary.do",
	    columns:[[
	        {field:'BONUSMONTH',title:'月份'},
	        {field:'WORKID',title:'工号'},
	        {field:'SA_ID',title:'销售ID'},
	        {field:'SA_NAME',title:'姓名'},
	        {field:'CITY',title:'城市'},
	        {field:'ENTERDATE',title:'入职日期'},
	        {field:'LEFTDATE',title:'离职日期'},
	        {field:'PERSONSTATUS',title:'人事状态'},
	        {field:'ISPROTECTPERIOD',title:'是否保护期'},
	        {field:'ISPREPAREPERIOD',title:'是否城市筹备期'},
	        {field:'POSAMOUNT',title:'POS贷总额'},
	        {field:'POSCOUNT',title:'POS贷单数'},
	        {field:'POSBONUS',title:'POS贷提成额'},
	        {field:'CASHAMOUNT',title:'交叉现金贷总额'},
	        {field:'CASHCOUNT',title:'交叉现金贷单数'},
	        {field:'CASHBONUS',title:'交叉现金贷提成额'},
	        {field:'POSCASHCOUNT',title:'POS交叉现金贷单数'},
	        {field:'POSCASHAMOUNT',title:'POS交叉现金贷总额'},
	        {field:'WYYAMOUNT',title:'无预约现金贷总额'},
	        {field:'WYYCOUNT',title:'无预约现金贷单数'},
	        {field:'WYYBONUS',title:'无预约现金贷提成额'},
	        {field:'CARAMOUNT',title:'车主现金贷总额'},
	        {field:'CARCOUNT',title:'车主现金贷单数'},
	        {field:'CARBONUS',title:'车主现金贷提成额'},
	        {field:'ADULTAMOUNT',title:'成人教育贷总额'},
	        {field:'ADULTCOUNT',title:'成人教育贷单数'},
	        {field:'ADULTBONUS',title:'成人教育贷提成额'},
	        {field:'STUAMOUNT',title:'学生教育贷总额'},
	        {field:'STUCOUNT',title:'学生教育贷单数'},
	        {field:'STUBONUS',title:'学生教育贷提成额'},
	        {field:'TOTALAMOUNT',title:'总贷出额'},
	        {field:'TOTALCOUNT',title:'总单数'},
	        {field:'TOTALBONUS',title:'提成总额'},
	        {field:'KEYERRORS',title:'关键错误单数'},
	        {field:'NONKEYERRORS',title:'非关键错误单数'},
	        {field:'NOFINDS',title:'未出文件质量等级单数'},
	        {field:'UNQUALIFIEDS',title:'不合格单数'},
	        {field:'QUALIFIEDS',title:'合格单数'},
	        {field:'QUALIFYAMOUNT',title:'总文件质量扣款'},
	        {field:'POSQUALIFY',title:'POS贷扣款(包含教育贷)'},
	        {field:'CASHQUALIFY',title:'交叉现金贷扣款'},
	        {field:'WYYQUALIFY',title:'无预约现金贷扣款'},
	        {field:'CARQUALIFY',title:'车主现金贷扣款'},
	        {field:'UNITS_NOMAL',title:'普通产品风险倒扣单数'}
	     ]]
	});
	// 设置分页控件
	var p = $('#bonusSummary').datagrid('getPager');
	$(p).pagination({
		pageSize : 10,// 每页显示的记录条数，默认为10
		pageList : [ 5, 10, 15 ],// 可以设置每页记录条数的列表
		beforePageText : '第',// 页数文本框前显示的汉字
		afterPageText : '页    共 {pages} 页',
		displayMsg : '当前显示 {from} - {to} 条记录   共 {total} 条记录'
	});
}

function bonusSearch(){
	$("#bonusSummary").datagrid("clearSelections");
	var workid = $("#workid").val();
	var saId = $("#saId").val();
	var bonusmonth = $("#bonusmonth").datebox('getValue');
	$('#bonusSummary').datagrid('load',{
		workid:workid,
		saId:saId,
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
	var saId = $("#saId").val();
	var bonusmonth = $("#bonusmonth").datebox('getValue');
	if(workid=='' && saId=='' && bonusmonth==''){
		$.messager.alert('提示信息','请输入导出条件');
		return;
	}
	$.ajax({
		async : false,
		cache : false,
		type : 'GET',
		url : contextPath + '/bonusSummary/exportBonusSummary.do',// 请求的action路径
		data:{
			workid:workid,
			saId:saId,
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

