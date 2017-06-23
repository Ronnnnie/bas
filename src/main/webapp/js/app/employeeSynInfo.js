$(function() {

//	initSystem();
//	initValidInd();
	initSysRoleGrid();

});

function overTo(){
	var row = $("#employeeGrid").datagrid("getSelections");
	var parm = "";
	for(var i = 0; i < row.length;i++){
		parm += "'"+row[i].jobNo+"'";
		if(i === row.length-1){
			break;
		}
		parm +=",";
	}
	if (row.length == 0) {
		alert("请选择需要转入的员工");
		return;
	}
	// alert(row[0].jobNo);
	var jobNo = row[0].jobNo;
	var flag = 'U';
	
	$.messager.progress({ title: '员工转入', msg: '员工信息转入中，请稍等...'});
	$.ajax({
		async : true,
		url : contextPath + '/employee/queryEmployeeByJobNo.do',
		type : "POST",
		data : {
			jobNo : parm
		},
		success : function(data) {
			$.messager.progress('close');
			data = eval("(" + data + ")");
			// 本地数据中已经存在
			if (data.returnMsg == '1') {
				// 弹出提示框，问是否需要替换
				$.messager.confirm('确认', '薪酬系统中已存在员工号为'+data.returnMap.message+'的信息，是否需要覆盖？',
						function(r) {
							if (r) {
								//一个是需要更新的员工
								flag = 'U';
								transEmployee(parm,flag,data.returnMap.insertMessage);
							}
						});
			} else if (data.returnMsg == '0') {
				//没有就直接新增插入
				flag = 'I';
				transEmployee(parm,flag,null);
				row.length = 0;
			} else{
				$.messager.alert("操作提示", "转入员工失败","error");
			}
			// if(data.success){
			// $.messager.alert("操作提示", data.message,"info");
			// //关闭窗口
			// closeWin('addResourcesWin');
			// }else{
			// $.messager.alert("操作提示", data.message,"error");
			// }
			
		},
		error : function() {
			// $.messager.alert("转入员工失败");
			row.length = 0;
		}
	});

	// $("#importFrm").form('clear');
	// initAddForm("sysCodeCombobox","validInd");
}

function overToAll(){
		$.messager.progress({ title: '员工转入', msg: '员工信息转入中，请稍等...'});
		$.ajax({
			async : true,
			url : contextPath + '/employee/queryEmployeeUAll.do',
			type : "POST",
			data : {
//				jobNo : parm
			},
			success : function(data) {
				$.messager.progress('close');
				data = eval("(" + data + ")");
				// 本地数据中已经存在
				if (data.returnMsg == '1') {
					// 弹出提示框，问是否需要替换
					$.messager.confirm('确认', '薪酬系统中已存在员工号为'+data.returnMap.message+'的信息，是否需要覆盖？',
							function(r) {
								if (r) {
									//一个是需要更新的员工,data.returnMap.insertMessage有值得话他的insert是新增
								// 第一参数是所有的员工工号
									flag = 'U';
									transEmployeeAll(data.returnMap.allJobNo,flag,data.returnMap.insertMessage);
								}
							});
				} else if (data.returnMsg == '0') {
					//没有就直接新增插入
					flag = 'I';
					transEmployeeAll(data.returnMap.insertMessage,flag,null);
					row.length = 0;
				}else if (data.returnMsg == '2') {
					$.messager.alert("操作提示", "无待转入员工","info");
				}
				else{
					$.messager.alert("操作提示", "转入员工失败","error");
				}
				
			},
			error : function() {
				 $.messager.alert("转入员工失败");
			}
		});
}


// 初始化所有系统清单
function initSysRoleGrid() {
	// 不是超级管理员，没有操作按钮
//	if (isSuperAdmin) {
//	}

	$('#employeeGrid').datagrid({
//		width: function () { return document.body.clientWidth * 0.9 },//自动宽度
		title : '金蝶人事数据同步',
//		toolbar : toolbar,
		pagination : true,
		singleSelect : false,
		checkOnSelect : true,
		pageSize : 10,
		rownumbers : true,
		width :  function () { return document.body.clientWidth * 1.9 },
		idField : 'jobNo',
		url : contextPath + "/employee/queryEmployeeTempPage.do",
		frozenColumns : [ [ {
			field : 'ck',
			checkbox : true
		}, {
			field : 'jobNo',
			title : '员工工号',
//			width : '8%'
		}, {
			field : 'employeeName',
			title : '员工姓名'
		} ] ],

		columns : [ [

		{
			field : 'status',
			title : '状态'
		}, {
			field : 'employeeType',
			title : '员工类型'
		}, {
			field : 'positionName',
			title : '职位'
		}, {
			field : 'empCity',
			title : '所属城市'
		}, {
			field : 'empProvince',
			title : '所属省份'
		}, {
			field : 'entryDate',
			title : '入职日期'
		}, {
			field : 'confirmDate',
			title : '转正日期'
		}, {
			field : 'leaveDate',
			title : '离职日期'
		}, {
			field : 'contractEndDate',
			title : '合同到期日'
		}, {
			field : 'identityId',
			title : '身份证号'
		}, {
			field : 'bankCardId',
			title : '银行卡号'
		}, {
			field : 'bankName',
			title : '银行名称'
		}, {
			field : 'email',
			title : '公司邮箱'
		}, {
			field : 'iswork',
			title : '是否在职'
		},
		{field:'opr',title:'备注',width:'5%',
            formatter:function(value, row, index){
            	//不是超级管理员，没有操作按钮
            	if(!isSuperAdmin){
            		return;
            	}
            	return;
            }
        }
    ]],
		// {field:'opr',title:'操作',width:'20%',frozen:'true',
		// formatter:function(value, row, index){
		// //不是超级管理员，没有操作按钮
		// // if(!isSuperAdmin){
		// // return;
		// // }
		// var view = '<a class="viewcls" href="javascript:;"
		// onclick="openEmployeeInfo('+index+')" ></a>';
		// var edit = '<a class="editcls" href="javascript:;"
		// onclick="openUpdateSysRole('+index+')" ></a>';
		// var cancel = '<a class="cancelCss" href="javascript:;"
		// onclick="removeSysRole('+index+')" ></a>';
		// var ok = '<a class="okCss" href="javascript:;"
		// onclick="openSysRole('+index+')" ></a>';
		// if(row.validInd == '1'){
		// return view + edit + cancel;
		// }else{
		// return view + edit + ok;
		// }
		// }
		// }
	 onLoadSuccess:function(data){
	 $('.viewcls').linkbutton({text:'详情',plain:true,iconCls:'icon-edit'});
	 $('.editcls').linkbutton({text:'修改',plain:true,iconCls:'icon-edit'});
	 $('.cancelCss').linkbutton({text:'禁用',plain:true,iconCls:'icon-cancel'});
	 $('.okCss').linkbutton({text:'启用',plain:true,iconCls:'icon-ok'});
	 }
	});

	// 设置分页控件
	var p = $('#employeeGrid').datagrid('getPager');
	$(p).pagination({
		pageSize : 10,// 每页显示的记录条数，默认为10
		pageList : [ 5, 10, 15,50 ],// 可以设置每页记录条数的列表
		beforePageText : '第',// 页数文本框前显示的汉字
		afterPageText : '页    共 {pages} 页',
		displayMsg : '当前显示 {from} - {to} 条记录   共 {total} 条记录'
	});
	
	
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

function transEmployee(obj,flag,insertMsg) {
	$.ajax({
		url : contextPath + '/employee/transEmployee.do',
		type : "POST",
		dataType:"json",
		async: false,
		data : {
			jobNo : obj+"",
			iorU:flag,
			insertMsg : insertMsg+""
		},
		success : function(data) {
//			data = eval("(" + data + ")");
			$.messager.alert("操作提示", data.returnMsg, "info");
			$('#employeeGrid').datagrid('reload'); 
		},
		error : function() {
			$.messager.alert("操作提示", data.returnMsg, "error");
		}
	});
}

function transEmployeeAll(obj,flag,insertMsg) {
	$.ajax({
		url : contextPath + '/employee/transEmployeeAll.do',
		type : "POST",
		dataType:"json",
		async: false,
		data : {
			jobNo : obj+"",
			iorU:flag,
			insertMsg : insertMsg+""
		},
		success : function(data) {
//			data = eval("(" + data + ")");
			$.messager.alert("操作提示", data.returnMsg, "info");
			$('#employeeGrid').datagrid('reload'); 
		},
		error : function() {
			$.messager.alert("操作提示", data.returnMsg, "error");
		}
	});
}

function sysInfoManual(){
	$.messager.confirm('确认','确认进行数据同步吗？这可能会花上几分钟时间。',function(r){    
	    if (r){    
	$.messager.progress({ title: '数据同步', msg: '数据同步数据导入中...'});
	$.ajax({
		url : contextPath + '/employee/sysInfoManual.do',
		type : "POST",
		success : function(data) {
			$.messager.progress('close');
			data = eval("(" + data + ")");
			$.messager.alert("操作提示", data.returnMsg, "info");
		},
		error : function() {
			$.messager.alert("操作提示", data.returnMsg, "error");
		}
	});
	    }
	});
}

//查询角色
function query() {
	var jobNo = $("#searchJobNo").val();
	var employeeName = $("#searchEmployeeName").val();
	$('#employeeGrid').datagrid('load', {
		jobNo : jobNo,
		employeeName : employeeName
	});
}
