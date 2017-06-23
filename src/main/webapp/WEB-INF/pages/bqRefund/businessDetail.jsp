<%@ page pageEncoding="utf-8"%>
<%
	String _contextPath = request.getContextPath();
	request.setAttribute("CONTEXT_PATH", _contextPath);

	String _basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ _contextPath + "/";
	request.setAttribute("BASE_PATH", _basePath);
%>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<link rel="shortcut" href="<%=_contextPath%>/images/icon/logo.ico"
	type="image/icon" />
<link rel="icon" href="<%=_contextPath%>/images/icon/logo.ico"
	type="image/icon" />

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

 tr {
	line-height:23px;
	margin-top:23px;
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
<!-- 缓存用户是否是超级管理员 -->
<input type="hidden" id="isSuperAdmin" value="${isSuperAdmin}">
<!-- 数据网格 -->
<div class="easyui-layout" data-options="fit:true"
	style="width: 100%; height: 100%">
	<div data-options="region:'north'" title=""
		style="width: 100%; height:17%">
		<form id="queryForm">
		<div style="width: 90%; margin-top:7px;margin-left:20px">
			<label>合同号:</label> 
				<input type="text" style="width: 120px" id="serialNo" name="serialNo" /> 
			<label style="margin-left: 20px">注册日:</label>
				 <input id="startBusinessDate" type="text" class="easyui-datebox" style="width: 110px"
						data-options="formatter:myformatter,parser:myparser" />至
				 <input	id="endBusinessDate" type="text" class="easyui-datebox"	style="width: 110px"
						data-options="formatter:myformatter,parser:myparser" />
			<label	style="margin-left: 20px">付款日:</label>
			 	 <input	id="startPayDate" type="text" class="easyui-datebox" style="width: 120px"
						data-options="formatter:myformatter,parser:myparser"> 至
				 <input	id="endPayDate" type="text" class="easyui-datebox"	style="width: 120px"
						data-options="formatter:myformatter,parser:myparser" />
			<label style="margin-left: 20px">产品子类型 :</label>
				 <select id="productSubType" class="easyui-combobox textbox combobox-f combo-f textbox-f" name="productSubType" style="width: 110px;">
					<option value=""></option>
					<option value="POS">POS</option>
					<option value="信贷">信贷</option>
					<option value="车贷">车贷</option>
				 </select>
		</div>
		<div style="margin-top:10px;margin-left:20px">
			<label style="margin-top:20px">资金方:</label>
				<select id="capitalSide" class="easyui-combobox" name="capitalSide" style="width: 120px;">
					<option value=""></option>
					<option value="中泰">中泰</option>
					<option value="招行">招行</option>
					<option value="佰仟">佰仟</option>
				</select>
			<label style="margin-left: 20px">记账日:</label>
				<input id="startKeepAccountsDate" type="text" class="easyui-datebox" style="width:110px;margin-top:50px;"
					data-options="formatter:myformatter,parser:myparser" />至
				<input id="endKeepAccountsDate" type="text" class="easyui-datebox"	style="width: 110px"
					data-options="formatter:myformatter,parser:myparser" />
			<label style="margin-left: 32px;margin-top:10px;">城市:</label>
				<input id="city" type="text" style="width: 120px; margin-top: 5px">
			<a style="margin-left:13px;height:23px" id="button-search" class="easyui-linkbutton" data-options="iconCls:'icon-search'" onclick="query()" >搜索</a>
			<a style="margin-left:20px;height:23px" id="button-search" class="easyui-linkbutton" data-options="iconCls:'icon-reload'" onclick="clearForm('queryForm')" >重置</a>
		</div>
		</form>
	</div>

	<div data-options="region:'center'" style="height:500px">
		<div id="tb">
			<table	style="width: 100%; height: 10px; border-left: none; border-bottom: none; border-right: none;">
				<tr>
					<td>
						<!-- 按钮 start -->
						<div id="tb" style="margin-left: 20px">
							<a href="#" class="easyui-linkbutton"	data-options="iconCls:'icon-edit',plain:'true'"	onclick='edit();'>编辑</a>
						    <a href="#"	class="easyui-linkbutton"	data-options="iconCls:'icon-edit',plain:'true'" onclick="accountingMark()" >账务标记</a>
						    <a href="#" class="easyui-linkbutton"	data-options="iconCls:'icon-tip',plain:'true'">Tips</a>
							<label>
								<font size='2' color='red'>
								 	共计匹配合同：
									<input class="easyui-textbox" style="width: 50px" id="contractCount" name="contractCount" />
									条 共计放款本金：
									<input class="easyui-textbox" style="width: 80px" id="moneyCount" name="moneyCount" />元
								</font>
							</label>
						</div> <!-- 按钮 end -->
					</td>
				</tr>
			</table>
		</div>
		<table id="clearanceCertificateGrid"
			class="easyui-datagrid" title="放款表业务明细"
			style="width: 100%; height: 100%;"
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
					<th data-options="field:'serialNo'" width="80px">合同号</th>
					<th data-options="field:'businessDate'" width="80px">注册日</th>
					<th data-options="field:'keepAccountsDate'" width="80px">记账日期</th>
					<th data-options="field:'payDate'" width="80px">付款日期</th>
					<th data-options="field:'makeCollectionsDate'" width="80px">收款日期</th>
					<th data-options="field:'productSubType'" width="80px">产品子类型</th>
					<th data-options="field:'capitalSide'" width="60px">资金方</th>
					<th data-options="field:'businessSum'" width="60px">放款本金</th>
					<th data-options="field:'deductionKHServiceFee'" width="100px">扣减客户服务费</th>
					<th data-options="field:'deductionSHServiceFee'" width="100px">扣减商户服务费</th>
					<th data-options="field:'returnSum'" width="70px">退还金额</th>
					<th data-options="field:'contractStatus'" width="70px">合同状态</th>
					<th data-options="field:'khName'" width="70px">客户姓名</th>
					<th data-options="field:'shName'" width="70px">商户名称</th>
					<th data-options="field:'shId'" width="50px">商户ID</th>
					<th data-options="field:'city'" width="50px">城市</th>
				</tr>
			</thead>
		</table>
	</div>
</div>

<!-- 编辑弹出框 -->
<div id="ediTdlg" data-options="iconCls:'icon-save'" style="text-align:center">
	<form id="orgInfoEditForm" method="post">
		<table id="orgInfoAddTable">
			<tbody>
				<tr>
					<th style="text-align:right; font-size: 13;display:none"><samp
							style="color: red">*</samp>ID:</th>
					<td><input class="easyui-validatebox" type="hidden" name="id"
						id="id" data-options="required:true,validType:'code'"
						maxlength="20" /></td>
				</tr>
				
				<tr>
					<th style="text-align:right; font-size: 13">
						<samp style="color:red">*</samp>合同号:
					</th>
					<td><input class="easyui-validatebox" type="text"
						name="periods" id="periods"
						data-options="required:true,validType:'code'" maxlength="20" />
					</td>
				</tr>
				
				<tr>
					<th style="text-align:right; font-size: 13"><samp
							style="color:red">*</samp>合同号:</th>
					<td><input class="easyui-validatebox" type="text"
						name="serialNo" id="serialNo"
						data-options="required:true,validType:'code'" maxlength="20" /></td>
				</tr>

				<tr>
					<th style="text-align: right; font-size: 13"><samp
							style="color: red">*</samp>注册日期:</th>
					<td><input class="easyui-datebox" type="text"
						name="businessDate" id="businessDate"
						data-options="formatter:myformatter,parser:myparser,required:true"
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
							style="color: red">*</samp>付款日期:</th>
					<td><input class="easyui-datebox" type="text"
						name="payDate" id="payDate"
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
						data-options="required:true" maxlength="20" /></td>
				</tr>

				<tr>
					<th style="text-align: right; font-size: 13"><samp
							style="color: red">*</samp>扣减客户服务费:</th>
					<td><input class="easyui-validatebox" type="text"
						name="deductionKHServiceFee" id="deductionKHServiceFee"
						data-options="required:true" maxlength="20" /></td>
				</tr>

				<tr>
					<th style="text-align: right; font-size: 13"><samp
							style="color: red">*</samp>扣减商户服务费:</th>
					<td><input class="easyui-validatebox" type="text"
						name="deductionSHServiceFee" id="deductionSHServiceFee"
						data-options="required:true" maxlength="20" /></td>
				</tr>

				<tr>
					<th style="text-align: right; font-size: 13"><samp
							style="color: red">*</samp>退还金额:</th>
					<td><input class="easyui-validatebox" type="text"
						name="returnSum" id="returnSum"
						data-options="required:true" maxlength="20" /></td>
				</tr>

				<tr>
					<th style="text-align: right; font-size: 13"><samp
							style="color: red">*</samp>合同状态:</th>
					<td><input class="easyui-validatebox" type="text"
						name="contractStatus" id="contractStatus"
						data-options="required:true" maxlength="20" /></td>
				</tr>
				
				<tr>
					<th style="text-align: right; font-size: 13"><samp
							style="color: red">*</samp>客户姓名:</th>
					<td><input class="easyui-validatebox" type="text"
						name="khName" id="khName" data-options="required:true"
						maxlength="20" /></td>
				</tr>
				
				<tr>
					<th style="text-align: right; font-size: 13"><samp
							style="color: red">*</samp>商户姓名:</th>
					<td><input class="easyui-validatebox" type="text"
						name="shName" id="shName" data-options="required:true"
						maxlength="20" /></td>
				</tr>
				
				<tr>
					<th style="text-align: right; font-size: 13"><samp
							style="color: red">*</samp>商户ID:</th>
					<td><input class="easyui-validatebox" type="text"
						name="shId" id="shId" data-options="required:true"
						maxlength="20" /></td>
				</tr>
				
				<tr>
					<th style="text-align: right; font-size: 13"><samp
							style="color: red">*</samp>城市:</th>
					<td><input class="easyui-validatebox" type="text"
						name="city" id="city" data-options="required:true"
						maxlength="20" /></td>
				</tr>
			</tbody>
		</table>
		<div id="EditTop">
			<a href="javascript:;" onClick = "updateData()" class="easyui-linkbutton" data-options="iconCls:'icon-ok'" style="margin-left:30px">保存</a>
			<a href="javascript:;" onClick = "deleteData()" class="easyui-linkbutton" data-options="iconCls:'icon-cancel'">删除</a>
	        <a href="javascript:;" onClick = "closeWin('ediTdlg')" class="easyui-linkbutton" data-options="iconCls:'icon-no'">取消</a>  
		</div>
	</form>
</div>

<div id="accountingMarkdlg" data-options="iconCls:'icon-save'"
	style="width: 600px; height: 400px; padding: 10px">
	<form id="accountingMarkForm" method="post">
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
					<th style="text-align: right; font-size: 13">记账日(起):</th>
					<td><input class="easyui-datebox" type="text"
						name="startKeepAccountsDate" id="startKeepAccountsDate"
						data-options="formatter:myformatter,parser:myparser"
						maxlength="20" /></td>
				</tr>
				<tr>
					<th style="text-align: right; font-size: 13">记账日(止):</th>
					<td><input class="easyui-datebox" type="text"
						name="endKeepAccountsDate" id="endKeepAccountsDate"
						data-options="formatter:myformatter,parser:myparser"
						maxlength="20" /></td>
				</tr>

				<tr>
					<th style="text-align: right; font-size: 13">付款日(起):</th>
					<td><input class="easyui-datebox" type="text"
						name="startPayDate" id="startPayDate"
						data-options="formatter:myformatter,parser:myparser"
						maxlength="20" /></td>
				</tr>
				<tr>
					<th style="text-align: right; font-size: 13">付款日(止):</th>
					<td><input class="easyui-datebox" type="text"
						name="endPayDate" id="endPayDate"
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

				<tr>
					<th style="text-align: right; font-size: 13">城市:</th>
					<td><input class="easyui-validatebox" type="text" name="city" id="city"	maxlength="20" /></td>
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
		<div id="EditTop">
			<a style="margin-left:100px" href="javascript:;" onClick = "accountingMarkSubmit()" class="easyui-linkbutton" data-options="iconCls:'icon-ok'">提交</a>
	        <a href="javascript:;" onClick = "closeWin('accountingMarkdlg')" class="easyui-linkbutton" data-options="iconCls:'icon-no'">取消</a>  
		</div>
	</form>
</div>

<!-- 数据网格end -->

<script type="text/javascript">
	function query() {
		var serialNo = $("#serialNo").val();
		var startBusinessDate = $("#startBusinessDate").datebox('getValue');
		var endBusinessDate = $("#endBusinessDate").datebox('getValue');
		var startKeepAccountsDate = $("#startKeepAccountsDate").datebox('getValue');
		var endKeepAccountsDate = $("#endKeepAccountsDate").datebox('getValue');
		var startPayDate = $("#startPayDate").datebox('getValue');
		var endPayDate = $("#endPayDate").datebox('getValue');
		var city = $("#city").val();
		var capitalSide = $("#capitalSide").combobox('getValue');
		var productSubType = $("#productSubType").combobox('getValue');
		$.ajax({
			url : contextPath + "/bqRefundServer/queryBusinessDetail.do",
			type : "POST",
			data : {
				rows: 10,
				page:1,
				serialNo : serialNo,
				startBusinessDate : startBusinessDate,
				endBusinessDate : endBusinessDate,
				startKeepAccountsDate : startKeepAccountsDate,
				endKeepAccountsDate : endKeepAccountsDate,
				startPayDate : startPayDate,
				endPayDate : endPayDate,
				city : city,
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
				$("#clearanceCertificateGrid").datagrid('loadExt',{
					serialNo : serialNo,
					startBusinessDate : startBusinessDate,
					endBusinessDate : endBusinessDate,
					startKeepAccountsDate : startKeepAccountsDate,
					endKeepAccountsDate : endKeepAccountsDate,
					startPayDate : startPayDate,
					endPayDate : endPayDate,
					city : city,
					capitalSide : capitalSide,
					productSubType : productSubType
				});
				$("#clearanceCertificateGrid").datagrid({
					url : contextPath + "/bqRefundServer/queryBusinessDetail.do"
				}); 
				//重新给表格加载数据
				$("#clearanceCertificateGrid").datagrid('loadData', jsondata);
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
				$.messager.alert("操作提示", data.message, "error");
			}
		});
	}
	
	function edit() {
		var selectedRow = $('#clearanceCertificateGrid').datagrid('getSelections');
		if(selectedRow.length >=1){
			$('#ediTdlg').dialog({
				title : '修改',
				width : 330,
				height : 450,
				closed : false,
				cache : false,
				modal : true,
				resizable : true
			});
			$('#orgInfoEditForm').form('load',{
				id : selectedRow[0].id,
				serialNo : selectedRow[0].serialNo,
				periods : selectedRow[0].periods,
				businessDate : selectedRow[0].businessDate,
				keepAccountsDate : selectedRow[0].keepAccountsDate,
				payDate : selectedRow[0].payDate,
				makeCollectionsDate : selectedRow[0].makeCollectionsDate,
				productSubType : selectedRow[0].productSubType,
				capitalSide : selectedRow[0].capitalSide,
				businessSum : selectedRow[0].businessSum,
				deductionKHServiceFee: selectedRow[0].deductionKHServiceFee,
				deductionSHServiceFee: selectedRow[0].deductionSHServiceFee,
				returnSum: selectedRow[0].returnSum,
				contractStatus: selectedRow[0].contractStatus,
				khName : selectedRow[0].khName,
				shName: selectedRow[0].shName,
				shId: selectedRow[0].shId,
				city: selectedRow[0].city
			});
			//设置输入框为禁用
			$('#id').attr('readonly',true); 
		}else{
			$.messager.alert("操作提示", "请选择行数据进行编辑!", "warning");
		}
	}
	
	

	function updateData() {
		$('#orgInfoEditForm').form('submit', {
			url : contextPath + '/bqRefundServer/updateBusinessDetail.do',
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

	//窗口赋值
	function accountingMark() {
		$('#accountingMarkdlg').dialog({
			title : '修改',
			width : 330,
			height : 430,
			closed : false,
			cache : false,
			modal : true,
			resizable : true
		});
		var serialNo = $("#serialNo").textbox('getValue');
		var startBusinessDate = $("#startBusinessDate").datebox('getValue');
		var endBusinessDate = $("#endBusinessDate").datebox('getValue');
		var startKeepAccountsDate = $("#startKeepAccountsDate").datebox('getValue');
		var endKeepAccountsDate = $("#endKeepAccountsDate").datebox('getValue');
		var startPayDate = $("#startPayDate").datebox('getValue');
		var endPayDate = $("#endPayDate").datebox('getValue');
		var city = $("#city").val();
		var capitalSide = $("#capitalSide").combobox('getValue');
		var productSubType = $("#productSubType").combobox('getValue');
		$('#accountingMarkForm').form('load',{
			serialNo : serialNo,
			startBusinessDate :startBusinessDate,
			endBusinessDate : endBusinessDate,
			startKeepAccountsDate : startKeepAccountsDate,
			endKeepAccountsDate : endKeepAccountsDate,
			startPayDate : startPayDate,
			endPayDate : endPayDate,
			city : city,
			capitalSide : capitalSide,
			productSubType : productSubType
		});
	}
	
	function accountingMarkSubmit() {
		$('#accountingMarkForm').form('submit', {
			url : contextPath + '/bqRefundServer/accountingMark.do',
			success : function(data) {
				closeWin('accountingMarkdlg');
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
				closeWin('accountingMarkdlg');
				$.messager.alert("操作提示", jsondata.msg, "error");
			}
		});
	}
	
	function createData() {
		$('#orgInfoAddForm').form('submit', {
			url : contextPath + '/bqRefundServer/createContract.do',
			success : function(data) {
				$.messager.alert("操作提示", "Create Successful!", "info");
				closeWin('dlg');
				queryChangeData(data);
			},
			onLoadError : function(data) {
				$.messager.alert("操作提示", data.message, "error");
			}
		});
	}
	
	function deleteData() {
		$.messager.confirm('操作提示','您确认要删除该用户？',function(r){    
		    if (r){    
		    	$('#orgInfoEditForm').form('submit', {
					url : contextPath + '/bqRefundServer/deleteBusinessDetail.do',
					success : function(data) {
						//关闭窗口
						$.messager.alert("操作提示", "remove Successful!", "info");
						closeWin('ediTdlg');
						query();
					},
					onLoadError : function(data) {
						$.messager.alert("操作提示", data.message, "error");
					}
				});
		    }    
		});
		 	 
	}

	function queryChangeData(data){
		var id;
		if(data.indexOf(":")>0){
			var jsondata1 = eval("("+data+")");
			id = jsondata1.id;
		}else{
			id = data;
		}
		$.ajax({
			url : contextPath + "/bqRefundServer/queryBusinessDetail.do",
			type : "POST",
			data : {
				page:1,
				rows:10,
				id:id
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
 					$("#moneyCount").textbox('setValue', 
 							'0.0');
				}
				//重新给控件加载数据
  				if (jsondata.rows[0].contractCount != 0
 						&& jsondata.rows[0].moneyCount != null) {
 					$("#contractCount").textbox('setValue',
 							jsondata.rows[0].contractCount);
 					$("#moneyCount")
 							.textbox('setValue', jsondata.rows[0].moneyCount);
 				} 
				$("#clearanceCertificateGrid").datagrid('loadExt',{
					id:id
				});
				 $("#clearanceCertificateGrid").datagrid({
					url : contextPath + "/bqRefundServer/queryBusinessDetail.do"
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
				$.messager.alert("操作提示", data.message, "error");
			}
		});
	}
</script>
