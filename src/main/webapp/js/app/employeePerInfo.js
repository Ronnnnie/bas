$(function() {

	initSystem();
	initValidInd();
	initEmployeeGrid();
	
	
	$('#searchable').multiselect2side({
		selectedPosition : 'right',
		search : "搜索: ",
		labelsx : '待选区',
		labeldx : '已选区',
		labelTop : '居顶',
		labelBottom : '居底',
		labelUp : '上',
		labelDown : '下',
		labelSort : '排序',
	});
	// initMultiselect2side();

});

// 保存模板
function saveTemplete() {
	// searchablems2side__sx 待选区
	// var searchable = document.getElementById("searchablems2side__sx");
	// searchable.options
	// var sxValues;
	// var sxText;
	// for(var i = 0;i < searchable.options.length;i++){
	// }
	// searchablems2side__dx 已选区
	var dxValues = '';
	var dxText = '';

	var searchable1 = document.getElementById("searchablems2side__dx").options;
	if (0 === searchable1.length) {
		return;
	}
	for (var i = 0; i < searchable1.length; i++) {
		dxValues += searchable1[i].value + ',';
		dxText += searchable1[i].text + ',';
	}

	var templete = $('#templete option:selected').val();

	// $("#exportA").attr("href","www.xxx.com");
	$.ajax({
		url : contextPath + '/employee/saveEmployeeTemplete.do',
		dataType : "json",
		type : "POST",
		data : {
			dxValues : dxValues,
			dxText : dxText,
			templete : templete
		},
		async : false,
		success : function(data) {
			for (var i = 0; i < data.obj.length; i++) {
				for ( var key in data.obj[i]) {
					searchable.options.add(new Option(data.obj[i][key], key)); // 这个兼容IE与firefox
				}
			}
		},
		error : function() {
			$.messager.alert("操作提示", data.msg, "error");
		}
	});
}



function initMultiselect2side() {
	var searchable = document.getElementById("searchable");
	var sxs = document.getElementById("searchablems2side__sx");// 未选区
	var dxs = document.getElementById("searchablems2side__dx");// 已选区

	$("#searchablems2side__sx").find("option").remove();
	$("#searchablems2side__dx").find("option").remove();
	
//	if (0 === searchable.options.length) {
		// 获取展示列表
		$.ajax({
			url : contextPath + '/employee/queryShowAllcolumns.do',
			dataType : "json",
			type : "POST",
			async : false,
			success : function(data) {
				for (var i = 0; i < data.obj.length; i++) {
					for ( var key in data.obj[i]) {
						sxs.options
								.add(new Option(data.obj[i][key], key)); // 这个兼容IE与firefox
					}
				}
			},
			error : function() {
				$.messager.alert("操作提示", data.msg, "error");
			}
		});

	

//	}
	// 初始化导出链接
	var templete = $('#templete option:selected').val();
	if (templete != '') {
		document.getElementById("exportA").href = contextPath
				+ "/employee/exportEmployeeInfos.do?templete=" + templete;
	}

	// 添加一个选项

}

// 选择模板
function selectValue() {
	var templete = $('#templete option:selected').val();
	if (templete != '') {
		document.getElementById("exportA").href = contextPath
				+ "/employee/exportEmployeeInfos.do?templete=" + templete;
	}

	var sxs = document.getElementById("searchablems2side__sx");// 未选区
	var dxs = document.getElementById("searchablems2side__dx");// 已选区

	$("#searchablems2side__sx").find("option").remove();
	$("#searchablems2side__dx").find("option").remove();
//	dxs.options.add(new Option("2", "two"));

	// 获取模板
	$.ajax({
		url : contextPath + '/employee/querySelectTempelte.do',
		dataType : "json",
		type : "POST",
		data : {
			templete : templete
		},
		async : false,
		success : function(data) {
			for (var i = 0; i < data.obj.dx.length; i++) {
				for ( var key in data.obj.dx[i]) {
					dxs.options.add(new Option(data.obj.dx[i][key], key)); // 这个兼容IE与firefox
				}
			}
			
			for (var i = 0; i < data.obj.sx.length; i++) {
				for ( var key in data.obj.sx[i]) {
					sxs.options.add(new Option(data.obj.sx[i][key], key)); // 这个兼容IE与firefox
				}
			}
		},
		error : function() {
			$.messager.alert("操作提示", data.msg, "error");
		}
	});

// 去重复

}

// 初始化系统信息
function initSystem() {
	$("#quethirdOrgName").combobox({    
		url: contextPath + "/userServer/queryAllDepart.do",
		panelHeight:200,
		editable:false,
	    valueField:'id',    
	    textField:'departName'
	});
	$("#thirdOrgName").combobox({    
		url: contextPath + "/userServer/queryAllDepart.do",
		panelHeight:200,
		editable:false,
	    valueField:'id',    
	    textField:'departName'
	});
	$("#upthirdOrgName").combobox({    
		url: contextPath + "/userServer/queryAllDepart.do",
		panelHeight:200,
		editable:false,
	    valueField:'id',    
	    textField:'departName'
	});
}
// 初始化查询条件中的状态
function initValidInd() {
	$("#searchValidInd").combobox({
		data : [ {
			text : '全部',
			value : ''
		}, {
			text : '有效',
			value : '1'
		}, {
			text : '失效',
			value : '0'
		}, ],
		width : "90px",
		panelHeight : 80,
		editable : false,
		valueField : 'value',
		textField : 'text'
	});
}

// 新增
function addEmployee() {
	$("#employeeWin").show();
	$win = $("#employeeWin").window({
		title : "新增员工",
		width : '400px',
		height : '500px',
		minimizable : false,
		collapsible : false,
		maximizable : false
	});
	$win.window("open");
	$("#employeeWin").form('clear');
}

// 一键更新
function allUpdate() {
	// 一键更新。只显示更新相关项目
	$("#tt").css('display','none'); 
	$("#tt1").css('display','none'); 
	$("#uu").css('display','block'); 
	
	// 显示需要进行更新的项目
	initMultiselect2sideUp();
	// 初始化搜索条件中的类型
	$('#multSelected').dialog({
		title : '选择模板',
		width : '590px',
		height : '450px',
		left : "15%",
		top : "5%",
		closed : false,
	});
}

function updateAllByPro(){
	var searchable1 = document.getElementById("searchablems2side__dx").options;
	
	if (0 === searchable1.length) {
		return;
	}
	var dxValues = '';
	for (var i = 0; i < searchable1.length; i++) {
		dxValues += searchable1[i].value + ',';
//		dxText += searchable1[i].text + ',';
	}
	
	$.messager.confirm('一键更新',
			'此操作会将所选中的项目全部更新成金蝶系统中的最新数据，本系统中的数据将会被覆盖，确认是否更新？', function(r) {
				if (r) {
					$.messager.progress({
						title : '更新所有数据',
						msg : '更新数据中...'
					});
					$.ajax({
						url : contextPath
								+ '/employee/updateEmployeeAllFromTemp.do',
						data:{project:dxValues},
						dataType : "json",
						type : "POST",
						success : function(data) {
							$.messager.progress('close');
							if (data.success) {
								$.messager.alert("操作提示", data.msg, "info");
								// 重新加载表格数据
								initEmployeeGrid();
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

// 导出
function exportData() {
	
	$("#tt").css('display','block'); 
	$("#tt1").css('display','block'); 
	$("#uu").css('display','none'); 

	initMultiselect2side();
	// 初始化搜索条件中的类型
	$('#multSelected').dialog({
		title : '选择模板',
		width : '590px',
		height : '450px',
		left : "15%",
		top : "5%",
		closed : false,
	});
}

// 初始化所有系统清单
function initEmployeeGrid() {

	// if(isSuperAdmin){
	// }

	$('#employeeGrid')
			.datagrid(
					{
						title : '个人信息管理',
						pagination : true,
						singleSelect : false,
						checkOnSelect : true,
						pageSize : 10,
						fit : true,
						rownumbers : true,
						// ;collapsible:true,
						idField : 'jobNo',
						url : contextPath + "/employee/queryEmployeePage.do",
						columns : [ [
								{
									field : 'ck',
									checkbox : true
								},
								{
									field : 'jobNo',
									title : '员工工号'
								},
								{
									field : 'employeeName',
									title : '员工姓名'
								},
								{
									field : 'status',
									title : '状态'
								},
								{
									field : 'employeeType',
									title : '员工类型'
								},
								{
									field : 'positionName',
									title : '职位'
								},
								{
									field : 'egressStandard',
									title : '外派津贴标准'
								},
								{
									field : 'baseSalary',
									title : '基本工资'
								},
								{
									field : 'synAllowance',
									title : '综合补贴'
								},
								{
									field : 'monthBonusBase',
									title : '月度奖金基数'
								},
								{
									field : 'quarterBonusBase',
									title : '季度奖金基数'
								},
								{
									field : 'yearBonusBase',
									title : '年度奖金基数'
								},
								{
									field : 'opr',
									title : '操作',
									width : '220',
									formatter : function(value, row, index) {
										// 不是超级管理员，没有操作按钮
										// if(!isSuperAdmin){
										// return;
										// }
										var view = '<a  class="viewcls"  href="javascript:;" onclick="openEmployeeInfo('
												+ index + ')" ></a>';
										var edit = '<a  class="editcls"  href="javascript:;" onclick="openUpdateSysRole('
												+ index + ')" ></a>';
										var update = '<a  class="updatecls"  href="javascript:;" onclick="updateEmployeeFromTemp('
												+ index + ')" ></a>';

										return view + edit + update;
										// }
									}
								} ] ],
						onLoadSuccess : function(data) {
							$('.viewcls').linkbutton({
								text : '详情',
								plain : true,
								iconCls : 'icon-edit'
							});
							$('.editcls').linkbutton({
								text : '修改',
								plain : true,
								iconCls : 'icon-edit'
							});
							$('.updatecls').linkbutton({
								text : '更新',
								plain : true,
								iconCls : 'icon-edit'
							});
							// $('.okCss').linkbutton({text:'启用',plain:true,iconCls:'icon-ok'});
						}
					});

	// 设置分页控件
	var p = $('#employeeGrid').datagrid('getPager');
	$(p).pagination({
		pageSize : 10,// 每页显示的记录条数，默认为10
		pageList : [ 5, 10, 15 ],// 可以设置每页记录条数的列表
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

function saveEmployee() {
	if($('#thirdOrgName').combobox('getValue') == ''){
		$.messager.alert('提示信息','请选择第三级组织名称(部门)');
		return;
	}
	$('#employeeFrm').form('submit', {
		url : contextPath + '/employee/addEmployee.do',
		onSubmit : function(param) {
			if($(this).form('validate') == 'false'){
				$.messager.alert('提示信息','信息输入不正确！');
			}
			if ($(this).form('validate')) {
				$.messager.progress({
					title : '新增员工',
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
				initEmployeeGrid();
				// 关闭窗口
				closeWin('employeeWin');
			} else {
				$.messager.alert("操作提示", data.message, "error");
			}
		},
		onLoadError : function(data) {
			$.messager.alert("操作提示", data.message, "error");
		}
	});
}

function closeWin(win) {
	$("#" + win).window("close");
}

// 查看详情
function openEmployeeInfo(index) {
	$("#employeeGrid").datagrid("selectRow", index);
	var row = $("#employeeGrid").datagrid("getSelected");
	$("#employeeInfoWin").show();
	$win = $("#employeeInfoWin").window({
		title : "查看员工详情",
		width : '400px',
		height : '500px',
		minimizable : false,
		collapsible : false,
		maximizable : false
	});
	$win.window("open");
	$("#queryInfoFrm").form("load", row);
}

// 初始化修改窗口
function openUpdateSysRole(index) {

	$("#employeeGrid").datagrid("selectRow", index);
	var row = $("#employeeGrid").datagrid("getSelected");
	$("#updateEmployeeWin").show();
	$win = $("#updateEmployeeWin").window({
		title : "员工信息",
		width : '400px',
		height : '500px',
		minimizable : false,
		collapsible : false,
		maximizable : false
	});
	$win.window("open");
	// initAddForm("updateSysCodeCombobox","updateValidInd");
	$("#updateEmployeeFrm").form("load", row);
}

// 修改数据
function updateEmployee() {
	if($('#upthirdOrgName').combobox('getValue') == ''){
		$.messager.alert('提示信息','请选择第三级组织名称(部门)');
		return;
	}
	$('#updateEmployeeFrm').form('submit', {
		url : contextPath + '/employee/updateEmployeeInfo.do',
		onSubmit : function(param) {
			if($(this).form('validate') == 'false'){
				$.messager.alert('提示信息','信息输入不正确！');
			}
			if ($(this).form('validate')) {
				$.messager.progress({
					title : '修改员工信息',
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
				initEmployeeGrid();
				// 关闭窗口
				closeWin('updateEmployeeWin');
			} else {
				$.messager.alert("操作提示", data.message, "error");
			}
		},
		onLoadError : function(data) {
			$.messager.alert("操作提示", data.message, "error");
		}
	});
}

// 启用角色
function openSysRole(index) {
	$.messager.confirm('确认', '您确认启用该角色吗？', function(r) {
		if (r) {
			$("#employeeGrid").datagrid("selectRow", index);
			var row = $("#employeeGrid").datagrid("getSelected");
			$.ajax({
				url : contextPath + '/role/openRole.do?roleCode='
						+ row.roleCode,
				type : "GET",
				success : function(data) {
					data = eval("(" + data + ")");
					if (data.success) {
						$.messager.alert("操作提示", data.message, "info");
						// 重新加载表格数据
						initEmployeeGrid();
					} else {
						$.messager.alert("操作提示", data.message, "error");
					}
				},
				error : function() {
					$.messager.alert("操作提示", data.message, "error");
				}
			});
		}
	});
}
// 角色失效
function removeSysRole(index) {

	$.messager.confirm('确认', '您确认禁用该角色吗？', function(r) {
		if (r) {
			$("#employeeGrid").datagrid("selectRow", index);
			var row = $("#employeeGrid").datagrid("getSelected");
			$.ajax({
				url : contextPath + '/role/removeRole.do?roleCode='
						+ row.roleCode,
				type : "GET",
				success : function(data) {
					data = eval("(" + data + ")");
					if (data.success) {
						$.messager.alert("操作提示", data.message, "info");
						// 重新加载表格数据
						initEmployeeGrid();
					} else {
						$.messager.alert("操作提示", data.message, "error");
					}
				},
				error : function() {
					$.messager.alert("操作提示", data.message, "error");
				}
			});
		}
	});
}

// 查询角色
function query() {
	var userCode = $("#searchUserCode").val();
	var userName = $("#searchUserName").val();
	$('#employeeGrid').datagrid('load', {
		jobNo : userCode,
		employeeName : userName
	});
}

// 全局变量，缓存角色ID
var globalRoleCode;
var globalSysCode;
// 资源分配
function addResources(index) {

	$("#employeeGrid").datagrid("selectRow", index);
	var role = $("#employeeGrid").datagrid("getSelected");
	$("#showroleCname").attr('value', role.roleCname);
	$("#showsysCname").attr('value', role.sysCname);
	globalRoleCode = role.roleCode;
	globalSysCode = role.sysCode;
	// 初始化搜索条件中的类型
	$('#addResourcesWin').dialog({
		title : '分配资源',
		width : '600px',
		height : '450px',
		left : "15%",
		top : "5%",
		closed : false,
	});
	// 角色可分配菜单
	$("#roleResTree").tree(
			{
				title : "资源",
				url : contextPath + "/resources/queryMenuBySysCode.do?sysCode="
						+ role.sysCode + "&roleCode=" + role.roleCode
			});
}

// 保存分配的资源
function addRoleResources() {
	$.messager.progress({
		title : '分配资源',
		msg : '保存数据...'
	});
	var nodes = $('#roleResTree').tree('getChecked');
	if (nodes && nodes.length == 0) {
		$.messager.alert("操作提示", "请选择未分配资源", "error");
	}
	// 获得实心节点
	var indeterminate = $('#roleResTree').tree('getChecked', 'indeterminate');
	// 将实心节点数据添加到已选中数据中
	if (indeterminate) {
		for (var j = 0; j < indeterminate.length; j++) {
			nodes.push(indeterminate[j]);
		}
	}
	// 已分配的资源ID
	var resourceIds = [];
	for (var i = 0; i < nodes.length; i++) {
		resourceIds.push(nodes[i].id);
	}
	// ajax保存数据
	$.ajax({
		url : contextPath + '/resources/updateRoleMenus.do',
		type : "POST",
		data : {
			roleCode : globalRoleCode,
			sysCode : globalSysCode,
			resourceIds : resourceIds.join(",")
		// 将数组转换为字符串
		},
		success : function(data) {
			$.messager.progress('close');
			data = eval("(" + data + ")");
			if (data.success) {
				$.messager.alert("操作提示", data.message, "info");
				// 关闭窗口
				closeWin('addResourcesWin');
			} else {
				$.messager.alert("操作提示", data.message, "error");
			}
		},
		error : function() {
			$.messager.alert("操作提示", data.message, "error");
		}
	});
}

function deleteEmployeeInfo() {
	var rows = $("#employeeGrid").datagrid("getSelections");
	var employeeNo = [];
	for (var int = 0; int < rows.length; int++) {
		employeeNo[int] = rows[int].jobNo;
	}

	if (0 === employeeNo.length) {
		$.messager.alert("操作提示", "请选择需要操作的员工", "info");
		return;
	}
	$.messager.confirm('确认', '您确认删除所选员工信息吗？', function(r) {
		if (r) {

			$.ajax({
				url : contextPath + '/employee/deleteEmployeeInfo.do',
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
						initEmployeeGrid();
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

function updateEmployeeFromTemp() {
	var rows = $("#employeeGrid").datagrid("getSelections");
	if (rows.length > 1) {
		return $.messager.alert("操作提示", "只能选择单条数据更新！", "in fo");
	}
	var row = $("#employeeGrid").datagrid("getSelected");
	if (row) {
		$.messager.confirm('确认', '用户:' + row.employeeName
				+ '，确认从金蝶系统更新数据到薪资系统吗？', function(r) {
			if (r) {
				var param = "jobNo=" + row.jobNo;
				$.ajax({
					url : contextPath + '/employee/updateEmployeeFromTemp.do',
					data : param,
					dataType : "json",
					type : "POST",
					success : function(data) {
						if (data.success) {
							$.messager.alert("操作提示", data.msg, "info");
							// 重新加载表格数据
							initEmployeeGrid();
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
	} else {
		return $.messager.alert("操作提示", "请选择需要更新的数据！", "info");
	}
}



function initMultiselect2sideUp() {
	var searchable = document.getElementById("searchable");
	
	var sxs = document.getElementById("searchablems2side__sx");// 未选区
	var dxs = document.getElementById("searchablems2side__dx");// 已选区

	$("#searchablems2side__sx").find("option").remove();
	$("#searchablems2side__dx").find("option").remove();
	
		$.ajax({
			url : contextPath + '/employee/queryShowBaseColumns.do',
			dataType : "json",
			type : "POST",
			async : false,
			success : function(data) {
				for (var i = 0; i < data.obj.length; i++) {
					for ( var key in data.obj[i]) {
						sxs.options
								.add(new Option(data.obj[i][key], key)); // 这个兼容IE与firefox
					}
				}
			},
			error : function() {
				$.messager.alert("操作提示", data.msg, "error");
			}
		});
}