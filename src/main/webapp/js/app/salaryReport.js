$(function() {
	initMsg();
	initSalaryReportGrid();
});

// 初始报表导出信息
function initMsg() {
	$("#depart").combobox({
	    url: contextPath + "/userServer/queryAllDepart.do",
		panelHeight:200,
		editable:false,
		textField:'departName',
	    valueField:'id',
	    value:'--请选择--'
	});
	$("#comPer").combobox({
		data:[
		      { text:'佰仟金融', value:'佰仟金融'},
		      { text:'聚诚财富', value:'聚诚财富'},
		     ],
	    panelHeight:80,
		editable:false,
		textField:'text' ,
		valueField:'value',
		value:'--请选择--'
	});
	
	$("#costDep").combobox({
		data:[
		      { text:'成本中心1', value:'1'},
		      { text:'成本中心2', value:'2'},
		     ],
	    panelHeight:80,
		editable:false,
		textField:'text' ,
		valueField:'value',
		value:'--请选择--'
	});
	$("#yearSelect").combobox({
		data:[
		      { text:'2014', value:'2014'},
		      { text:'2015', value:'2015'},
		      { text:'2016', value:'2016'},
		      { text:'2017', value:'2017'},
		      { text:'2018', value:'2018'},
		      { text:'2019', value:'2019'},
		     ],
	    panelHeight:120,
		editable:false,
		textField:'text',
		valueField:'value',
		value:'--请选择--'
	});
	$("#monthSelect").combobox({
		data:[
		      { text:'01', value:'01'},
		      { text:'02', value:'02'},
		      { text:'03', value:'03'},
		      { text:'04', value:'04'},
		      { text:'05', value:'05'},
		      { text:'06', value:'06'},
		      { text:'07', value:'07'},
		      { text:'08', value:'08'},
		      { text:'09', value:'09'},
		      { text:'10', value:'10'},
		      { text:'11', value:'11'},
		      { text:'12', value:'12'},
		     ],
	    panelHeight:120,
		editable:false,
		multiple:true,
		textField:'text' ,
		valueField:'value',
		value:''
	});
}

function initSalaryReportGrid(){
	$('#salaryReport').datagrid({
		title : '薪资列表',
		pagination : true,
		singleSelect : true,
		checkOnSelect : true,
		fit : true,
		pageSize : 10,
		rownumbers : true,
		width : 'auto',
		onLoadSuccess : function(data) {
		}
	});
	// 设置分页控件
	var p = $('#salaryReport').datagrid('getPager');
	$(p).pagination({
		pageSize : 10,// 每页显示的记录条数，默认为10
		pageList : [ 5, 10, 15 ],// 可以设置每页记录条数的列表
		beforePageText : '第',// 页数文本框前显示的汉字
		afterPageText : '页    共 {pages} 页',
		displayMsg : '当前显示 {from} - {to} 条记录   共 {total} 条记录'
	});
}

function salarySearch(){
	var yearSelect = $("#yearSelect").combobox('getValue');
	var monthSelect = $("#monthSelect").combobox('getValues');
	var costDep = $("#costDep").combobox('getValue');
	var depart = $("#depart").combobox('getValue');
	var city = $("#city").val();
	var posts = $("#posts").val();
	var comPer = $("#comPer").combobox('getValue');
	var person = $("#person").val();//
	if(monthSelect.length > 1){
		$.messager.alert("查询条件","只能进行单月查询，要看多月数据请选择导出");
		return;
	}else if(yearSelect == '--请选择--' || yearSelect == '' || monthSelect == '--请选择--' || monthSelect == ''){
		$.messager.alert("查询条件","请选择查询年月");
		return;
	}
	if(costDep == '--请选择--'){
		costDep = '';
	}
	if(depart == '--请选择--'){
		depart = '';
	}
	if(comPer == '--请选择--'){
		comPer = '';
	}
	
//	alert(depart);
	
	$('#searchColums').form('submit',{
		type: 'GET',
		url : contextPath + '/salary/report/queryColumn.do',
		success : function(data){
			var s = '[[';
			if(data != null && data != ''){
				s += data;
			}
			s += ']]';
			var options = {};
			options.url = contextPath + '/salary/report/queryData.do';
			options.columns = eval(s);
			options.queryParams={
				yearSelect:yearSelect,
				monthSelect:monthSelect,
				costDep:costDep,
				depart:depart,
				city:city,
				posts:posts,
				comPer:comPer,
				person:person
			};
			$('#salaryReport').datagrid(options);
		}
	});
}

//重置
function salarySearchReset(name){
	$("#yearSelect").combobox('setValue','');
	$("#yearSelect").combobox('setText','--请选择--');
	$("#monthSelect").combobox('clear');
	$("#monthSelect").combobox('reload');
	$("#costDep").combobox('setValue','');
	$("#costDep").combobox('setText','--请选择--');
	$("#depart").combobox('setValue','');
	$("#depart").combobox('setText','--请选择--');
	$("#city").val('');
	$("#posts").val('');
	$("#person").val('');
	$("#comPer").combobox('setValue','');
	$("#comPer").combobox('setText','--请选择--');
}

//导出
function exportXls() {
	var yearSelect = $("#yearSelect").combobox('getValue');
	var monthSelect = $("#monthSelect").combobox('getValues');
	var costDep = $("#costDep").combobox('getValue');
	var depart = $("#depart").combobox('getValue');
	var city = $("#city").val();
	var posts = $("#posts").val();
	var person = $("#person").val();
	var comPer = $("#comPer").combobox('getValue');
	if(yearSelect == '--请选择--' || yearSelect == ''){
		$.messager.alert("查询条件","请选择导出年份");
		return;
	}
	if(monthSelect == '--请选择--'){monthSelect = '';}
	if(costDep == '--请选择--'){costDep = '';}
	if(depart == '--请选择--'){depart = '';}
	if(comPer == '--请选择--'){comPer = '';}
	$.ajax({
		async : false,
		cache : false,
		type : 'GET',
		url : contextPath + '/salary/report/exportSalaryReport.do',// 请求的action路径
		data:{
			yearSelect:yearSelect,
			monthSelect:monthSelect,
			costDep:costDep,
			depart:depart,
			city:city,
			posts:posts,
			comPer:comPer,
			person:person
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

