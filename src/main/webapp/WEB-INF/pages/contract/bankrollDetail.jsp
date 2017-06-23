<%@ page pageEncoding="utf-8"%>
<%
	String _contextPath = request.getContextPath();
	request.setAttribute("CONTEXT_PATH", _contextPath);

	String _basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ _contextPath + "/";
	request.setAttribute("BASE_PATH", _basePath);
%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript"
	src="${CONTEXT_PATH}/js/jquery-easyui-1.4.2/jquery.min.js"
	charset="utf-8"></script>
<!--(指定编码方式，防止出现乱码)引入EasyUI中使用的Jquery版本-->
<script type="text/javascript"
	src="${CONTEXT_PATH}/js/jquery-easyui-1.4.2/jquery.easyui.min.js"
	charset="utf-8"></script>
<!--(指定编码方式，防止出现乱码)引入EasyUi文件-->
<script type="text/javascript"
	src="${CONTEXT_PATH}/js/jquery-easyui-1.4.2/locale/easyui-lang-zh_CN.js"></script>
<!--汉化-->

<script type="text/javascript" src="${CONTEXT_PATH}/js/app/include.js" charset="utf-8"></script>
<script type="text/javascript" src="${CONTEXT_PATH}/js/app/loading.js" charset="utf-8"></script>
<script type="text/javascript" src="${CONTEXT_PATH}/js/app/util.js" charset="utf-8"></script>

<link rel="stylesheet" type="text/css"
	href="${CONTEXT_PATH}/js/jquery-easyui-1.4.2/themes/bootstrap/easyui.css">
<!--引入CSS样式-->
<link rel="stylesheet" type="text/css"
	href="${CONTEXT_PATH}/js/jquery-easyui-1.4.2/themes/icon.css">
<!--Icon引入-->
<style type="text/css">
.formTable {
	padding: 1px;
}

.formTable th {
	text-align: right;
	width: 90px;
	padding: 5px 0px 5px 0;
}

.formTable td {
	padding: 5px 0 5px 0px;
}

.formTable samp {
	vertical-align: middle;
	color: #f00;
}

.datagrid-cell {
	line-height: 28px
}

.datagrid-cell-rownumber {
	height: 28px
}
</style>
</head>
<!-- 数据网格 -->

<div class="easyui-layout" data-options="fit:true"
	style="width: 100%; height: 100%">
	<div data-options="region:'north'" title=""
		style="width: 100%; height: 60px;">
		<form id="queryForm" method="post">
		<div style="width: 90%; margin: 5px;">
			<label>合同号:</label> 
				<input class="easyui-textbox" style="width: 100px" id="serialNo" name="serialNo" /> 
			<label style="margin-left: 20px">注册日:</label>
				 <input id="startBusinessDate" type="text" class="easyui-datebox" style="width: 100px"
						data-options="formatter:myformatter,parser:myparser" />至
				 <input	id="endBusinessDate" type="text" class="easyui-datebox"	style="width: 100px"
						data-options="formatter:myformatter,parser:myparser" />
			<label	style="margin-left: 20px">产品子类型 :</label>
				 <select id="productSubType" class="easyui-combobox textbox combobox-f combo-f textbox-f" name="productSubType" style="width: 100px;">
					<option value=""></option>
					<option value="POS">POS</option>
					<option value="信贷">信贷</option>
					<option value="车贷">车贷</option>
				</select><br /> 
			<label>资金方:</label>
				<select id="capitalSide" class="easyui-combobox" name="capitalSide" style="width: 100px;">
					<option value=""></option>
					<option value="中泰">中泰</option>
					<option value="招行">招行</option>
					<option value="佰仟">佰仟</option>
				</select>
			<label	style="margin-left: 20px">收款日:</label>
			 	 <input	id="startMakeCollectionsDate" type="text" class="easyui-datebox" style="width: 100px"
						data-options="formatter:myformatter,parser:myparser">至
				 <input	id="endMakeCollectionsDate" type="text" class="easyui-datebox"	style="width: 100px"
						data-options="formatter:myformatter,parser:myparser" />
			<a style="margin-left: 20px" id="button-search" href="javascript:;"	onclick="query()" class="easyui-linkbutton"	data-options="iconCls:'icon-search'">搜索</a>
			<a style="margin-left: 65px" id="button-search" href="javascript:;"	onclick="clearForm('queryForm')" class="easyui-linkbutton"	data-options="iconCls:'icon-reload'">重置</a>
			</div>
			</form>
	</div>

	<div data-options="region:'center'">
		<div id="tb" style="">
			<table
				style="width: 100%; height: 10px; border-left: none; border-bottom: none; border-right: none;">
				<tr>
					<td>
						<!-- 按钮 start -->
						<div id="tb" style="margin-left: 20px">
							<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:'true'" onclick='update();'>编辑</a>
							<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:'true'" onclick='payConfirm();'>付款确认</a>
							<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-save',plain:'true'" onclick="imports()">数据导入</a> 
							<a href="${CONTEXT_PATH}/contractServer/exportExcel.do" class="easyui-linkbutton" data-options="iconCls:'icon-save',plain:true">模板导出</a>
							<a href="#"	class="easyui-linkbutton" data-options="iconCls:'icon-tip',plain:'true'">Tips</a>
							 <label>
							 	<font size='2' color='red'>
							 		 共计匹配合同：
							 		<input	class="easyui-textbox" style="width: 50px" id="contractCount" name="contractCount" />条 
							 		共计放款本金：
							 		<input class="easyui-textbox" style="width: 80px" id="moneyCount" name="moneyCount" />元
								</font> 
							</label>
						</div> <!-- 按钮 end -->
					</td>
				</tr>
			</table>
		</div>
		<table id="clearanceCertificateGrid"
			class="easyui-datagrid" title="资金明细"
			style="width: 100%; height: 410px;"
			data-options="
					id:'dg-dopts',
					rownumbers:true,
					loadMsg:'数据加载中,请稍等',
					pagination:true,
					fitColumns:true,
					singleSelect: true,
					toolbar: '#tb',
					pageList : [ 10, 20, 50, 100 ],
					method: 'post'
				">
			<thead>
				<tr>
					<th data-options="field:'id',checkbox:true"></th>
					<th data-options="field:'serialNo'" width="150px">合同号</th>
					<th data-options="field:'businessDate'" width="150px">注册日</th>
					<th data-options="field:'keepAccountsDate'" width="150px">记账日期</th>
					<th data-options="field:'makeCollectionsDate'" width="150px">收款日期</th>
					<th data-options="field:'productSubType'" width="150px">产品子类型</th>
					<th data-options="field:'capitalSide'" width="150px">资金方</th>
					<th data-options="field:'businessSum'" width="150px">放款本金</th>
					<th data-options="field:'clientName'" width="150px">客户姓名</th>
					<th data-options="field:'city'" width="150px">城市</th>
				</tr>
			</thead>
		</table>
	</div>
</div>

<!-- 修改弹出框 -->
<div id="ediTdlg" data-options="iconCls:'icon-save'"
	style="width:600px;height:400px;padding:10px;text-align:center;display:none;">
	<form id="orgInfoEditForm" method="post">
		<table id="orgInfoAddTable">
			<tbody>
				<tr style="margin-top: 10px">
					<th style="text-align: right; font-size: 13"><samp
							style="color: red">*</samp>ID:</th>
					<td><input class="easyui-validatebox" type="text" name="id"
						id="id" data-options="required:true,validType:'code'"
						maxlength="20" /></td>
				</tr>
				<tr>
					<th style="text-align: right; font-size: 13"><samp
							style="color: red">*</samp>合同号:</th>
					<td><input class="easyui-validatebox" type="text"
						name="serialNo" id="serialNo"
						data-options="required:true,validType:'code'" maxlength="20" /></td>
				</tr>

				<tr>
					<th style="text-align: right; font-size: 13"><samp
							style="color: red">*</samp>注册日期:</th>
					<td><input class="easyui-datebox" type="text"
						name="businessDate" id="businessDate"
						data-options="formatter:myformatter,parser:myparser"
						maxlength="20" /></td>
				</tr>

				<tr>
					<th style="text-align: right; font-size: 13"><samp
							style="color: red">*</samp>记账日期:</th>
					<td><input class="easyui-datebox" type="text"
						name="keepAccountsDate" id="keepAccountsDate"
						data-options="formatter:myformatter,parser:myparser"
						maxlength="20" /></td>
				</tr>

				<tr>
					<th style="text-align: right; font-size: 13"><samp
							style="color: red">*</samp>收款日期:</th>
					<td><input class="easyui-datebox" type="text"
						name="makeCollectionsDate" id="makeCollectionsDate"
						data-options="formatter:myformatter,parser:myparser"
						maxlength="20" /></td>
				</tr>

				<tr>
					<th style="text-align: right; font-size: 13"><samp
							style="color: red">*</samp>产品子类型:</th>
					<td><select id="productSubType"
						class="easyui-combobox textbox combobox-f combo-f textbox-f"
						name="productSubType" style="width: 173px;">
							<option value=""></option>
							<option value="POS">POS</option>
							<option value="信贷">信贷</option>
							<option value="车贷">车贷</option>
					</select></td>
				</tr>

				<tr>
					<th style="text-align: right; font-size: 13"><samp
							style="color: red">*</samp>资金方:</th>
					<td><select id="capitalSide" class="easyui-combobox"
						name="capitalSide" style="width: 173px;">
							<option value=""></option>
							<option value="中泰">中泰</option>
							<option value="招行">招行</option>
							<option value="佰仟">佰仟</option>
					</select></td>
				</tr>

				<tr>
					<th style="text-align: right; font-size: 13"><samp
							style="color: red">*</samp>放款本金:</th>
					<td><input class="easyui-validatebox" type="text"
						name="businessSum" id="businessSum"
						data-options="validType:'code'" maxlength="20" /></td>
				</tr>

				<tr>
					<th style="text-align: right; font-size: 13"><samp
							style="color: red">*</samp>客户姓名:</th>
					<td><input class="easyui-validatebox" type="text"
						name="clientName" id="clientName" data-options=""
						maxlength="20" /></td>
				</tr>
			</tbody>
		</table>
		<div id="EditTop">
			<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-save'"   onclick="updateData()">Save</a>
			<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-cancel'" onClick="closeWin('ediTdlg')">Cencel</a>
		</div>
	</form>
</div>

<div id="payConfirmDlg" data-options="iconCls:'icon-save'"
	style="width: 560px; height: 400px; padding: 10px">
	<form id="payConfirmForm" method="post">
		<table id="orgInfoAddTable" style="margin:auto">
			<tbody>
				<tr style="line-height:30px;">
					<th style="text-align: right; font-size: 13">合同号:</th>
					<td><input class="easyui-validatebox" type="text" name="serialNo" id="serialNo"	 maxlength="20" /></td>
				</tr>

				<tr>
					<th style="text-align: right; font-size: 13">注册日(起):</th>
					<td><input class="easyui-datebox" type="text"
						name="startBusinessDate" id="startBusinessDate"
						data-options="formatter:myformatter,parser:myparser"
						maxlength="20" /></td>
				</tr>
				<tr>
					<th style="text-align: right; font-size: 13">注册日(止):</th>
					<td><input class="easyui-datebox" type="text"
						name="endBusinessDate" id="endBusinessDate"
						data-options="formatter:myformatter,parser:myparser"
						maxlength="20" /></td>
				</tr>

				<tr>
					<th style="text-align: right; font-size: 13">收款日(起):</th>
					<td><input class="easyui-datebox" type="text"
						name="startMakeCollectionsDate" id="startMakeCollectionsDate"
						data-options="formatter:myformatter,parser:myparser"
						maxlength="20" /></td>
				</tr>
				<tr>
					<th style="text-align: right; font-size: 13">收款日(止):</th>
					<td><input class="easyui-datebox" type="text"
						name="endMakeCollectionsDate" id="endMakeCollectionsDate"
						data-options="formatter:myformatter,parser:myparser"
						maxlength="20" /></td>
				</tr>

				<tr>
					<th style="text-align: right; font-size: 13">产品子类型:</th>
					<td><select id="productSubType"
						class="easyui-combobox textbox combobox-f combo-f textbox-f"
						name="productSubType" style="width: 173px;">
							<option value=""></option>
							<option value="POS">POS</option>
							<option value="信贷">信贷</option>
							<option value="车贷">车贷</option>
					</select></td>
				</tr>

				<tr>
					<th style="text-align: right; font-size: 13">资金方:</th>
					<td><select id="capitalSide" class="easyui-combobox"
						name="capitalSide" style="width: 173px;">
							<option value=""></option>
							<option value="中泰">中泰</option>
							<option value="招行">招行</option>
							<option value="佰仟">佰仟</option>
					</select></td>
				</tr>
				
				<tr style="line-left:100px;">
					<th style="text-align: right; font-size: 13"><samp
							style="color: red">*</samp>收款日期:</th>
					<td><input class="easyui-datebox" type="text"
						name="updateDate" id="updateDate"
						data-options="formatter:myformatter,parser:myparser,required:true"
						maxlength="20" />
					</td>
				</tr>
			</tbody>
		</table>
		<div id="EditTop" style="margin-top:20px">
			<a href="#" onClick = "payConfirmSubmit()" class="easyui-linkbutton" data-options="iconCls:'icon-ok'" style="margin-left:100px" >提交</a>
	        <a href="#" onClick = "closeWin('payConfirmDlg')" class="easyui-linkbutton" data-options="iconCls:'icon-cancel'" style="margin-left:10px">取消</a>  
		</div>
	</form>
</div>

	
<!-- 导入弹出框 -->
<div id="importdlg" data-options="iconCls:'icon-save'"
	style="width: 600px; height: 400px; padding: 10px; display:noon;">
	<label>数据上传：</label>
	<form id="uploadCntractExcelFrm" method="post"
		enctype="multipart/form-data">
		<input id="uploadContractExcel" name="file" class="easyui-filebox" style="width: 200px" data-options="prompt:'请选择文件...'" />
		<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-save'" onclick="importContract()">导入</a>
	</form>
</div>
<!-- 数据网格end -->

<script type="text/javascript">
	function query() {
		var serialNo = $("#serialNo").textbox('getValue');
		var startBusinessDate = $("#startBusinessDate").datebox('getValue');
		var endBusinessDate = $("#endBusinessDate").datebox('getValue');
		var startMakeCollectionsDate = $("#startMakeCollectionsDate").datebox('getValue');
		var endMakeCollectionsDate = $("#endMakeCollectionsDate").datebox('getValue');
		var capitalSide = $("#capitalSide").combobox('getValue');
		var productSubType = $("#productSubType").combobox('getValue');
		$.ajax({
			url : contextPath + "/contractServer/queryBusinessDetail.do",
			type : "POST",
			data : {
				rows: 10,
				page:1,
 				serialNo : serialNo,
				startBusinessDate : startBusinessDate,
				endBusinessDate : endBusinessDate,
				startMakeCollectionsDate : startMakeCollectionsDate,
				endMakeCollectionsDate : endMakeCollectionsDate,
				capitalSide : capitalSide,
				productSubType : productSubType 
			},
			success : function(data) {
				
				var jsondata = eval("(" + data + ")");
				//是否有数据
				if (jsondata.rows == "") {
					$.messager.alert('提示', '暂无匹配数据!请重新搜索..', 'warning');
					//清空表单数据
					$('#clearanceCertificateGrid').datagrid('loadData', {
						total : 0,
						rows : []
					});
					$("#contractCount").textbox('setValue',
 							"0");
 					$("#moneyCount").textbox('setValue', 
 							"0.0");
				}
				//重新给控件加载数据
  				if (jsondata.rows[0].contractCount != 0
 						&& jsondata.rows[0].moneyCount != null) {
 					$("#contractCount").textbox('setValue',
 							jsondata.rows[0].contractCount);
 					$("#moneyCount")
 							.textbox('setValue', jsondata.rows[0].moneyCount);
 				} 
				//重新给表格加载数据
 				$("#clearanceCertificateGrid").datagrid('loadData', jsondata);
				$("#clearanceCertificateGrid").datagrid({
					url : contextPath + "/contractServer/queryBusinessDetail.do"
				}); 
				$("#clearanceCertificateGrid").datagrid('loadExt',{
 	 					serialNo : serialNo,
						startBusinessDate : startBusinessDate,
						endBusinessDate : endBusinessDate,
						startMakeCollectionsDate : startMakeCollectionsDate,
						endMakeCollectionsDate : endMakeCollectionsDate,
						capitalSide : capitalSide,
						productSubType : productSubType  
				});
				var p = $('#clearanceCertificateGrid').datagrid('getPager'); 
				    $(p).pagination({ 
				        pageSize: 10,//每页显示的记录条数，默认为10 
				        pageList: [5,10,15],//可以设置每页记录条数的列表 
				        beforePageText: '第',//页数文本框前显示的汉字 
				        afterPageText: '页    共 {pages} 页', 
				        displayMsg: '当前显示 {from} - {to} 条记录   共 {total} 条记录' 
				}); 
			},
			error : function() {
				$.messager.alert("操作提示", "访问服务器失败", "error");
			}
		});
	}

	function update() {
		$("#ediTdlg").show();
		var selectedRow = $('#clearanceCertificateGrid').datagrid('getSelections');
		if(selectedRow.length >=1){
			$('#ediTdlg').dialog({
				title : '修改',
				width : 320,
				height : 400,
				cache : false,
				modal : true,
				resizable : true
			});
			$('#orgInfoEditForm').form('load',{
				id : selectedRow[0].id,
				serialNo : selectedRow[0].serialNo,
				businessDate : selectedRow[0].businessDate,
				keepAccountsDate : selectedRow[0].keepAccountsDate,
				makeCollectionsDate : selectedRow[0].makeCollectionsDate,
				productSubType : selectedRow[0].productSubType,
				capitalSide : selectedRow[0].capitalSide,
				businessSum : selectedRow[0].businessSum,
				clientName : selectedRow[0].clientName
			});
			//设置输入框为禁用
			$('#id').attr('readonly',true); 
		}else{
			$.messager.alert("操作提示", "请选择某行数据进行修改!", "warning");
		}
	};

	function updateData() {
		$('#orgInfoEditForm').form('submit', {
			url : contextPath + '/contractServer/updateBusinessDetail.do',
			success : function(data) {
				//关闭窗口
				$.messager.alert("操作提示", "updata Successful!", "info");
				closeWin('ediTdlg');
				queryChangeData(data);
			},
			onLoadError : function(data) {
				$.messager.alert("操作提示", data.message, "error");
			}
		});
	}
	
	function imports(){
		$('#importdlg').show();
		$('#importdlg').dialog({
		    title  : '导入',
		    width  : 300,
		    height : 300,
		    cache  : false,
		    modal  : true,	
		    hcenter :true,
		    resizable : true,
		    });
	};
	
	//合同信息上传
	function importContract() {   
		// 得到上传文件的全路径
		var fileName = $('#uploadContractExcel').filebox('getValue');
		// 获取题型
		if (fileName != "") {
			// 进行基本校验
			if (fileName == "") {
				$.messager.alert('提示', '请选择上传文件！', 'warning');
			} else {
				// 对文件格式进行校验
				var d1 = /\.[^\.]+$/.exec(fileName);
				if (d1 == ".xls" || d1 == ".xlsx") {
					$.messager.progress({ title: 'excel数据导入中', msg: '数据导入中...'});
					// 提交表单
					$("#uploadCntractExcelFrm").form("submit", {
						url : contextPath + '/contractServer/importContract.do',
						onSubmit : function() {
						},
						success : function(result) {
							$.messager.progress('close');
							var obj = jQuery.parseJSON(result);
							if(obj.success){
								$("#clearanceCertificateGrid").datagrid('loadData', obj.obj);
								$.messager.confirm('操作提示','请仔细核对数据无误后点击确定再次导入!',function(r){   
								    if (r){
								    	$.ajax({  
								    		url : contextPath + '/contractServer/importContractSave.do',
								    	    type: "POST",  
								    	    success: function (data) {  
								    	    	$.messager.alert("操作提示", "导入成功!", "info");
								    	    	queryChangeData(data);
								    	    },
											error : function() {
												$.messager.alert("操作提示", '导入失败,请稍后重试或尝试联系系统管理员!', "error");
											}  
								    	}); 
								    }
								});
								closeWin('importdlg');
							}else{
								$.messager.alert('提示','解析失败!请核对模板重新导入!', 'warning');
								closeWin('importdlg');
							}
						},
						error : function() {
							$.messager.alert("操作提示", '上传失败,请稍后重试或尝试联系系统管理员!', "error");
						}
					});
				} else {
					$.messager.alert('提示', '请选择xls格式文件！', 'warning');
					$('#uploadContractExcel').filebox('setValue', '');
				}
			}
		} else {
			$.messager.alert('提示', '请选择需上传的文件！', 'warning');
		}
		
	}

	function queryChangeData(data){
		var page = 1;
		var rows = 10;
		var id = data;
		$.ajax({
			url : contextPath + "/contractServer/queryBusinessDetail.do",
			type : "POST",
			data : {
				id:id,
				page:page,
				rows:rows
			},
			success : function(data2) {
				var jsondata = eval("(" + data2 + ")");
				//是否有数据
				if (jsondata.rows == "[]") {
					$.messager.alert('提示', '暂无匹配数据!请重新搜索..', 'warning');
					//清空表单数据
					$('#clearanceCertificateGrid').datagrid('loadData', {
						total : 0,
						rows : []
					});
 					$("#contractCount").textbox('setValue',
 							'0');
 					$("#moneyCount").textbox('setValue', '0.0');
				}
				//重新给控件加载数据
  				if (jsondata.rows[0].contractCount != 0
 						&& jsondata.rows[0].moneyCount != null) {
 					$("#contractCount").textbox('setValue',
 							jsondata.rows[0].contractCount);
 					$("#moneyCount")
 							.textbox('setValue', jsondata.rows[0].moneyCount);
 				} 
				//重新给表格加载数据
				$("#clearanceCertificateGrid").datagrid('loadExt',{
					id:id
				});
				$("#clearanceCertificateGrid").datagrid({
					url : contextPath + "/contractServer/queryBusinessDetail.do"
				}); 
 				$("#clearanceCertificateGrid").datagrid('loadData', jsondata.rows);
				var p = $('#clearanceCertificateGrid').datagrid('getPager'); 
				    $(p).pagination({ 
				        pageSize: 10,//每页显示的记录条数，默认为10 
				        pageList: [5,10,15],//可以设置每页记录条数的列表 
				        beforePageText: '第',//页数文本框前显示的汉字 
				        afterPageText: '页    共 {pages} 页', 
				        displayMsg: '当前显示 {from} - {to} 条记录   共 {total} 条记录' 
				}); 
			},
			error : function() {
				$.messager.alert("操作提示", "访问服务器失败", "error");
			}
		});
	}
	
	//窗口赋值
	function payConfirm() {
		$('#payConfirmDlg').dialog({
			title : '账务标记',
			width : 300,
			height : 400,
			closed : false,
			cache : false,
			modal : true,
			resizable : true
		});
		var serialNo = $("#serialNo").textbox('getValue');
		var startBusinessDate = $("#startBusinessDate").datebox('getValue');
		var endBusinessDate = $("#endBusinessDate").datebox('getValue');
		var startMakeCollectionsDate = $("#startMakeCollectionsDate").datebox('getValue');
		var endMakeCollectionsDate = $("#endMakeCollectionsDate").datebox('getValue');
		var capitalSide = $("#capitalSide").combobox('getValue');
		var productSubType = $("#productSubType").combobox('getValue');
		var updateDate = $("#updateDate").datebox('getValue');
		$('#payConfirmForm').form('load',{
			serialNo : serialNo,
			startBusinessDate :startBusinessDate,
			endBusinessDate : endBusinessDate,
			startMakeCollectionsDate : startMakeCollectionsDate,
			endMakeCollectionsDate : endMakeCollectionsDate,
			capitalSide : capitalSide,
			productSubType : productSubType,
			updateDate : updateDate
		});
	}
	
	function payConfirmSubmit() {
		$('#payConfirmForm').form('submit', {
			url : contextPath + '/contractServer/payConfirm.do',
			success : function(data) {
				closeWin('payConfirmDlg');
				var jsondata = eval("(" + data + ")");
				//是否有数据
				if (jsondata.obj == null) {
					$.messager.alert('提示', jsondata.msg, 'warning');
					//清空表单数据
					$('#clearanceCertificateGrid').datagrid('loadData', {
						total : 0,
						rows : []
					});
				}else{
					$.messager.alert('提示', jsondata.msg, 'info');
					queryChangeData(jsondata.obj);
				}
			},
			onLoadError : function(data) {
				closeWin('payConfirmDlg');
				$.messager.alert("操作提示", jsondata.msg, "error");
			}
		});
	}
	</script>
