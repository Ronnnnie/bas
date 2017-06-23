$(function() {
	
	initValidInd("payrollTemplete");
	initButtonInfo();
	initInfo();
});

function initInfo(){
	$('.easyui-filebox').filebox({    
	    buttonText:"导入工资单工号",
	});
}

function initButtonInfo(){
	document.getElementById("preview").onclick = function(){
		var h = this.href;
		if (!h || h === "" || (h.substring(h.length-1) === "#")){
			alert("请选择模板");
		return false;
		}
		return true;
		}
};

//初始化查询条件中的状态
function initValidInd(validInd){
	var data = [ 
               {text : '请选择工资单模板', value : ''}, 
               {text : '工资单模板1', value : 'payroll_templete_1'},
               {text : '工资单模板2', value : 'payroll_templete_2'},
               {text : '工资单模板3', value : 'payroll_templete_3'},
               {text : '工资单模板4', value : 'payroll_templete_4'},
               {text : '工资单模板5', value : 'payroll_templete_5'},
               {text : '工资单模板6', value : 'payroll_templete_6'},
               {text : '工资单模板7', value : 'payroll_templete_7'},
               {text : '工资单模板8', value : 'payroll_templete_8'},
  			 ];
	 $("#"+validInd).combobox({
			data : data,
			width:"130px",
			panelHeight:80,
			editable:false,
			valueField:'value',    
			textField:'text',
			onChange: function (n,o) {
				if(n == ''){
					$('#projectGrid').datagrid('loadData',{total:0,rows:[]});
					document.getElementById("preview").href = "#";
				}else{
					initProjectGrid(n);
					document.getElementById("preview").href = contextPath
					+ "/salary/report/buildPayRollTemplete.do?templete=" + n;
				}
				
			}
		});
	 //${CONTEXT_PATH}/salary/report/buildPayRollTemplete.do
}

function download(){
	
	document.getElementById("download").onclick = function(){
		var h = this.href;
		if (!h || h === "" || (h.substring(h.length-1) === "#")){
			alert("请选择模板");
		return false;
		}
		return true;
		}
}

// 生成工资单
function buildPayRolls() {
	var templete = $('#payrollTemplete').combobox('getValue');// 选中模板
	if(templete == ''){
		$.messager.alert("操作提示", "请选择模板，生成工资单将以选择模板来生成", "info");
		return;
	}
	$.messager.progress({
		title : '工资单生成',
		msg : '工资单生成中，请稍后......'
	});
	$.ajax({
		url : contextPath + '/salary/report/buildPayRolls.do?templete='+templete,
		data : {},
		dataType : "json",
		type : "post",
		// async : true,
		success : function(data) {
			$.messager.progress('close');
			if (data.success) {
				$.messager.alert("操作提示", data.msg, "info");
				// 重新加载表格数据
				// initProjectGrid();
			} else {
				$.messager.alert("操作提示", data.msg, "error");
			}
		},
		error : function(param) {
			$.messager.alert("操作提示", data.msg, "error");
		}
	});
}

// 初始化薪资显示项目
function initProjectGrid(n) {
	
	$('#projectGrid')
			.datagrid({
						title : '工资单管理',
						pagination : true,
						singleSelect : true,
						checkOnSelect : true,
						pageSize : 10,
						rownumbers : true,
						width : 'auto',
						idField : 'proId',
						url : contextPath
								+ "/salary/report/queryTempleteInfoPage.do?templete="+n,
						columns : [ [
								{
									field : 'proName',
									title : '项目名字',
									width : '15%'
								},
								{
									field : 'proType',
									title : '项目类型',
									width : '10%',
									align : 'center',
									formatter : function(value, row, index) {
										if (value == '1') {
											return '<samp style="color: green;">基础信息类</samp>';
										} else if (value == '2') {
											return '<samp style="color: green;">计算类</samp>';
										} else if (value == '3') {
											return '<samp style="color: green;">上传类</samp>';
										} else if (value == '4') {
											return '<samp style="color: red;">其他</samp>';
										}
									}
								},
								{
									field : 'salarytype',
									title : '薪资类型',
									width : '10%',
									align : 'center',
									formatter : function(value, row, index) {
										if (value == 'A') {
											return '<samp style="color: green;">基本信息</samp>';
										} else if (value == 'B') {
											return '<samp style="color: green;">工资明细</samp>';
										}
									},
								},
								{
									field : 'salarydetails',
									title : '明细类型',
									width : '10%',
									align : 'center',
									formatter : function(value, row, index) {
										if (value == 'A') {
											return '<samp style="color: green;">考勤项目</samp>';
										} else if (value == 'B') {
											return '<samp style="color: green;">实发项目</samp>';
										} else if (value == 'C') {
											return '<samp style="color: green;">扣款项目</samp>';
										} else if (value == 'D') {
											return '<samp style="color: green;">福利项目个人缴纳</samp>';
										} else if (value == 'E') {
											return '<samp style="color: green;">税后项目</samp>';
										} else if (value == 'F') {
											return '<samp style="color: green;">本月工资实发</samp>';
										} else if (value == 'G') {
											return '<samp style="color: green;">备注</samp>';
										}

									}
								},
								{
									field : 'isdisplay',
									title : '是否显示',
									width : '15%',
									align : 'center',
									formatter : function(value, row, index) {
										if (value == 'Y') {
											return '<samp style="color: green;">是</samp>';
										} else if (value == 'N') {
											return '<samp style="color: red;">否</samp>';
										}
									}
								},
								{
									field : 'opr',
									title : '操作',
									width : '20%',
									formatter : function(value, row, index) {
										// 不是超级管理员，没有操作按钮
//										if (!isSuperAdmin) {
//											return;
//										}
										var edit = '<a  class="editcls"  href="javascript:;" onclick="openUpdateProject('
												+ index + ')" ></a>';
										return edit;
										// }
									}
								} ] ],
						onLoadSuccess : function(data) {
							$('.editcls').linkbutton({
								text : '修改',
								plain : true,
								iconCls : 'icon-edit'
							});
						}
					});

	// 设置分页控件
	var p = $('#projectGrid').datagrid('getPager');
	$(p).pagination({
		pageSize : 10,// 每页显示的记录条数，默认为10
		pageList : [ 5, 10, 15 ],// 可以设置每页记录条数的列表
		beforePageText : '第',// 页数文本框前显示的汉字
		afterPageText : '页    共 {pages} 页',
		displayMsg : '当前显示 {from} - {to} 条记录   共 {total} 条记录'
	});
}

// 初始化系统信息
function initAddForm(proType, salarytype, salarydetails, isdisplay) {
	$("#" + proType).combobox({
		data : [ {
			text : '基础信息类',
			value : '1',
			disabled : true
		}, {
			text : '计算类',
			value : '2',
			disabled : true
		}, {
			text : '上传类',
			value : '3',
			disabled : true
		}, {
			text : '其他',
			value : '4',
			disabled : true
		}, ],
		panelHeight : 80,
		editable : false,
		valueField : 'value',
		textField : 'text',
		value : '1'
	});

	$("#" + salarytype).combobox({
		data : [ {
			text : '基本信息',
			value : 'A'
		}, {
			text : '工资明细',
			value : 'B'
		}, ],
		panelHeight : 80,
		editable : false,
		valueField : 'value',
		textField : 'text',

		onSelect : function(param) {
			if ('A' == $(this).combobox('getValue')) {
				$("#" + salarydetails).combobox('disable');
				$("#" + salarydetails).combobox('setValue', '');
			} else {
				$("#" + salarydetails).combobox({
					disabled : false
				});
			}
		}
	});


	$("#" + salarydetails).combobox({
		data : [ {
			text : '无',
			value : ''
		}, {
			text : '考勤项目',
			value : 'A'
		}, {
			text : '实发项目',
			value : 'B'
		}, {
			text : '扣款项目',
			value : 'C'
		}, {
			text : '福利项目个人缴纳',
			value : 'D'
		}, {
			text : '税后项目',
			value : 'E'
		}, {
			text : '本月工资实发',
			value : 'F'
		}, {
			text : '备注',
			value : 'G'
		}, ],
		panelHeight : 80,
		editable : false,
		valueField : 'value',
		textField : 'text',
		value : ''
	});
	$("#" + isdisplay).combobox({
		data : [ {
			text : '是',
			value : 'Y'
		}, {
			text : '否',
			value : 'N'
		}, ],
		panelHeight : 80,
		editable : false,
		valueField : 'value',
		textField : 'text'
	});
}

function closeWin(win) {
	$("#" + win).window("close");
}

// 初始化修改窗口
function openUpdateProject(index) {

	$("#projectGrid").datagrid("selectRow", index);
	var row = $("#projectGrid").datagrid("getSelected");
	$("#updateProjectWin").show();
	$win = $("#updateProjectWin").window({
		title : "修改工资单显示信息",
		width : '400px',
		height : '300px',
		minimizable : false,
		collapsible : false,
		maximizable : false
	});
	$win.window("open");
	initAddForm("updateProType", "salarytype", "salarydetails", "isdisplay");
	$("#updateProjectFrm").form("load", row);
	if ('A' == $("#salarytype").combobox('getValue')) {
		$("#salarydetails").combobox('disable')
	}
}

// 修改数据  注意
function updateProject(n) {
	var templete = $('#payrollTemplete').combobox('getValue');
	$('#updateProjectFrm').form('submit', {
		url : contextPath + '/salary/report/updateTempleteInfo.do?templete='+templete,
		onSubmit : function(param) {
			if ($(this).form('validate')) {
				$.messager.progress({
					title : '修改项目',
					msg : '保存数据...'
				});
			}
			return $(this).form('validate');
		},
		success : function(data) {
			$.messager.progress('close');
			data = eval("(" + data + ")");
			if (data.success) {
				$.messager.alert("操作提示", data.message, "info");
				// 重新加载表格数据
				initProjectGrid(templete);
				// 关闭窗口
				closeWin('updateProjectWin');
			} else {
				$.messager.alert("操作提示", data.message, "error");
			}
		},
		onLoadError : function(data) {
			$.messager.alert("操作提示", data.message, "error");
		}
	});
}

function uploadJobNoExcel() {   
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
					url : contextPath + '/salary/report/uploadJobNoExcel.do',
					onSubmit : function() {
					},
					success : function(result) {
						$.messager.progress('close');
						alert("success");
						var obj = jQuery.parseJSON(result);
						var list;
						
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

