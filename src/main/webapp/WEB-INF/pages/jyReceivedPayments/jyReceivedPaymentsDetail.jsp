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
<script type="text/javascript" src="${CONTEXT_PATH}/js/app/jyReceivedPayments.js?version=20170517" charset="utf-8"></script>

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
		style="width: 100%; height:15%">
		<form id="queryForm" method="post">
		<div style="margin-top:10px;margin-left:0px">
			<label style="margin-left: 5px">合同号:</label>
				<input id="contracts" name="contracts" type="text" class="easyui-textbox" style="width:110px;"/>
			<label style="margin-left: 10px">记账日:</label>
				<input id="startKeepAccountsTime" name="startKeepAccountsTime" type="text" class="easyui-datebox" style="width:100px;"
					data-options="formatter:myformatter,parser:myparser" />至
				<input id="endKeepAccountsTime" type="text" class="easyui-datebox"	style="width: 100px"
					data-options="formatter:myformatter,parser:myparser" />
			<label style="margin-left: 10px">回款日:</label>
				<input id="startRepayDate" name="startRepayDate" type="text" class="easyui-datebox" style="width:100px;"
					data-options="formatter:myformatter,parser:myparser" />至
				<input id="endRepayDate" name="endRepayDate" type="text" class="easyui-datebox"	style="width: 100px"
					data-options="formatter:myformatter,parser:myparser" />
			<label style="margin-left: 10px">注册日:</label>
				<input id="startRegistrationDate" name="startRegistrationDate" type="text" class="easyui-datebox" style="width:100px;"
					data-options="formatter:myformatter,parser:myparser" />至
				<input id="endRegistrationDate" name="endRegistrationDate" type="text" class="easyui-datebox"	style="width: 100px"
					data-options="formatter:myformatter,parser:myparser" />
			<a style="margin-left:10px;height:23px" id="button-search" class="easyui-linkbutton" data-options="iconCls:'icon-search'" onclick="query()" >搜索</a>
			<a style="margin-left:10px;height:23px" id="button-search" class="easyui-linkbutton" data-options="iconCls:'icon-reload'" onclick="clearForm('queryForm')" >重置</a>
		</div>
		</form>
	</div>

	<div data-options="region:'center'" style="height:520px">
		<div id="tb">
			<table	style="width: 100%; height: 10px; border-left: none; border-bottom: none; border-right: none;">
				<tr>
					<td>
						<!-- 按钮 start -->
						<div id="tb" style="margin-left: 20px">
						    <a href="#"	class="easyui-linkbutton"	data-options="iconCls:'icon-ok',plain:'true'" onclick="contractApprove()" >审核</a>
						    <a href="#"	class="easyui-linkbutton"	data-options="iconCls:'icon-ok',plain:'true'" onclick="accountingMark()" >记账确认</a>
						    <a href="#"	class="easyui-linkbutton"	data-options="iconCls:'icon-cancel',plain:'true'" onclick="cancelAccountingMark()" >记账撤销</a>
						    <a href="#"	class="easyui-linkbutton"	data-options="iconCls:'icon-edit',plain:'true'" onclick="edit()" >批量编辑</a>
						    <a href="#"	class="easyui-linkbutton"	data-options="iconCls:'icon-save',plain:'true'" onclick="detailExport('${CONTEXT_PATH}')" >明细导出</a>
						    <a href="#"	class="easyui-linkbutton"	data-options="iconCls:'icon-save',plain:'true'" onclick="gatherExport('${CONTEXT_PATH}')" >汇总导出</a>
						</div> <!-- 按钮 end -->
					</td>
				</tr>
			</table>
		</div>
		<table id="clearanceCertificateGrid"
			class="easyui-datagrid" title="嘉银回款"
			style="width:100%; height: 95%;"
			data-options="
					id:'dg-dopts',
					rownumbers:true,
					loadMsg:'数据加载中,请稍等',
					pagination:true,
					fitColumns:true,
					singleSelect: false,
					toolbar: '#tb',
					pageList : [ 10, 20, 50, 100 ],
					data:{'total':0,'rows':[]},
					showFooter:true,
					method: 'post'
				">
			<thead>
				<tr>
					<th data-options="field:'id',checkbox:true"></th>
					<th data-options="field:'keepAccountsTime'">记账日期</th>
					<th data-options="field:'contractNo'">合同号</th>
					<th data-options="field:'clientName'">客户姓名</th>
					<th data-options="field:'registrationDate'">注册日</th>
					<th data-options="field:'sno'">门店代码</th>
					<th data-options="field:'rno'">商户代码</th>
					<th data-options="field:'sa_id'">SA_ID</th>
					<th data-options="field:'productcategory'">商品范畴</th>
					<th data-options="field:'suretype'">客户渠道</th>
					<th data-options="field:'businessmodel'">业务模式</th>
					<th data-options="field:'productId'">产品类型</th>
					<th data-options="field:'subProductType'">产品子类型</th>
					<th data-options="field:'province'">省份</th>
					<th data-options="field:'city'">城市</th>
					<th data-options="field:'cityCode'">城市编码</th>
					<th data-options="field:'creditPerson'" formatter="companyNameFormatter">资金方</th>
					<th data-options="field:'cdate'">强制日期</th>
					<th data-options="field:'payDate'">应还日</th>
					<th data-options="field:'sequence'">期次</th>
					<th data-options="field:'payType'" formatter="payTypeFormatter">还款类型</th>
					<th data-options="field:'payprinciPalamt'" formatter="thousandthFormatter" width="120px">本金</th>
					<th data-options="field:'payInteamt'" formatter="thousandthFormatter" width="120px">利息</th>
					<th data-options="field:'payment'" formatter="thousandthFormatter" width="120px">本息</th>
					<th data-options="field:'loanAmount'" formatter="thousandthFormatter" width="120px">贷款本金</th>
					<th data-options="field:'repayDate'">回款日期</th>
					<th data-options="field:'guaranteeParty'" formatter="companyNameFormatter">保证方</th>
					<th data-options="field:'registrationDate'">登记日期</th>
					<th data-options="field:'assetBelong'" formatter="companyNameFormatter">资产所属方</th>
					<th data-options="field:'approveStatus'" formatter="approveStatusFormatter" >审核状态</th>
					<th data-options="field:'payStatus'" formatter="payStatusFormatter" >支付状态</th>
					<th data-options="field:'keepAccountsStatus'" formatter="formatStatus" >记账状态</th>
				</tr>
			</thead>
		</table>
	</div>
</div>

<!-- 审核 -->
<div id="approvedlg" data-options="iconCls:'icon-save'"
	style="width: 600px; height: 400px; padding: 10px">
	<form id="approveForm" method="post">
		<table id="orgInfoAddTable" style="margin:auto">
			<tbody style="font-size: 13">
				<tr style="line-height:10px;">
					<th style="text-align: right;">匹配合同数:</th>
					<td><input class="easyui-textbox" id="approveContractCount" name="approveContractCount" data-options="editable:false" style="width:150px" /></td>
				</tr>
				<tr style="line-height:10px;">
					<th style="text-align: right;">匹配合同总金额:</th>
					<td><input class="easyui-textbox" id="approveMoneyCount" name="approveMoneyCount" data-options="editable:false" style="width:150px" /></td>
				</tr>
				
				<tr style="line-height:10px;">
					<th style="text-align: right;">合同号:</th>
					<td><input id="serialNo" name="serialNo" data-options="editable:false" style="width:150px" class="easyui-textbox"/></td>
				</tr>
				
				<tr>
					<th style="text-align: right;">记账日期:</th>
					<td><input id="startKeepAccountsTime" name="startKeepAccountsTime" data-options="editable:false" style="width:150px" class="easyui-textbox"/>至
					<input id="endKeepAccountsTime" name="endKeepAccountsTime" data-options="editable:false" style="width:150px" class="easyui-textbox"/></td>
				</tr>
				
				<tr>
					<th style="text-align: right;">回款日期:</th>
					<td><input id="startRepayDate" name="startRepayDate" data-options="editable:false" style="width:150px" class="easyui-textbox"/>至
					<input id="endRepayDate" name="endRepayDate" data-options="editable:false" style="width:150px" class="easyui-textbox"/></td>
				</tr>
				<tr>
					<th style="text-align: right;">注册日期:</th>
					<td><input id="startRegistrationDate" name="startRegistrationDate" data-options="editable:false" style="width:150px" class="easyui-textbox"/>至
					<input id="endRegistrationDate" name="endRegistrationDate" data-options="editable:false" style="width:150px" class="easyui-textbox"/></td>
				</tr>
								
				<tr style="line-left:100px;" >
					<th style="text-align: right;"><samp style="color: red">*</samp>审核日期:</th>
					<td>
						<input id="approveTime" name="approveTime" type="text" class="easyui-datebox" style="width:100px;"
							data-options="formatter:myformatter,parser:myparser,required:true,editable:false" />
					</td>
				</tr>
								
				<tr style="line-left:100px;" >
					<th style="text-align: right;"><samp style="color: red">*</samp>审核结果:</th>
					<td>
						<select id="updateApproveStatus" data-options="editable:false" class="easyui-combobox textbox combobox-f combo-f textbox-f" name="updateApproveStatus" style="width: 150px;" >
							<option value="1">审核通过</option>
						</select>
					</td>
				</tr>
				
				<tr style="line-height:30px;">
					<th style="text-align: right;">请填写审核备注:</th>
					<td><input class="easyui-textbox" id="approveRemark" name="approveRemark" style="width:150px"/></td>
				</tr>
			</tbody>
		</table>
		<div id="EditTop" style="margin-top:10px">
			<a style="margin-left:100px" href="javascript:;" onClick = "approveSubmit()" class="easyui-linkbutton" data-options="iconCls:'icon-ok'">提交</a>
	        <a href="javascript:;" onClick = "closeWin('approvedlg')" class="easyui-linkbutton" data-options="iconCls:'icon-cancel'">取消</a>  
		</div>
	</form>
</div>

<!-- 记账确认 -->
<div id="accountingMarkdlg" data-options="iconCls:'icon-save'"
	style="width: 600px; height: 400px; padding: 10px">
	<form id="accountingMarkForm" method="post">
		<table id="orgInfoAddTable" style="margin:auto">
			<tbody style="font-size: 13">
				<tr style="line-height:10px;">
					<th style="text-align: right;">匹配合同数:</th>
					<td><input class="easyui-textbox" id="accountingMarkContractCount" data-options="editable:false" name="accountingMarkContractCount" style="width:150px" /></td>
				</tr>
				<tr style="line-height:15px;">
					<th style="text-align: right;">匹配合同总金额:</th>
					<td><input class="easyui-textbox" id="accountingMarkMoneyCount" data-options="editable:false" name="accountingMarkMoneyCount" style="width:150px" /></td>
				</tr>
				<tr style="line-height:15px;">
					<th style="text-align: right;">合同号:</th>
					<td><input id="contracts" name="contracts" class="easyui-textbox" style="width:150px" data-options="editable:false"/></td>
				</tr>
				<tr>
					<th style="text-align: right;">记账日期:</th>
					<td><input id="startKeepAccountsTime" name="startKeepAccountsTime" data-options="editable:false" style="width:150px" class="easyui-textbox"/>至
					<input id="endKeepAccountsTime" name="endKeepAccountsTime" data-options="editable:false" style="width:150px" class="easyui-textbox"/></td>
				</tr>
				
				<tr>
					<th style="text-align: right;">回款日期:</th>
					<td><input id="startRepayDate" name="startRepayDate" data-options="editable:false" style="width:150px" class="easyui-textbox"/>至
					<input id="endRepayDate" name="endRepayDate" data-options="editable:false" style="width:150px" class="easyui-textbox"/></td>
				</tr>
				<tr>
					<th style="text-align: right;">注册日期:</th>
					<td><input id="startRegistrationDate" name="startRegistrationDate" data-options="editable:false" style="width:150px" class="easyui-textbox"/>至
					<input id="endRegistrationDate" name="endRegistrationDate" data-options="editable:false" style="width:150px" class="easyui-textbox"/></td>
				</tr>

				<tr style="line-left:100px;">
					<th style="text-align: right;"><samp
							style="color: red">*</samp>请修改记账日期:</th>
					<td><input class="easyui-datebox" type="text"
						name="updateDate" id="jzUpdateDate"
						data-options="formatter:myformatter,parser:myparser,required:true,editable:false"
						maxlength="20"   style="width:150px"/>
					</td>
				</tr>
				<tr style="line-height:15px;">
					<th style="text-align: right;">请填写记账备注:</th>
					<td><input class="easyui-textbox" id="keepAccountsRemark" name="keepAccountsRemark"  style="width:150px"/></td>
				</tr>
			</tbody>
		</table>
		<div id="EditTop" style="margin-top:10px">
			<a style="margin-left:100px" href="javascript:;" onClick = "accountingMarkSubmit('accountingMarkdlg','accountingMarkForm')" class="easyui-linkbutton" data-options="iconCls:'icon-ok'">提交</a>
	        <a href="javascript:;" onClick = "closeWin('accountingMarkdlg')" class="easyui-linkbutton" data-options="iconCls:'icon-cancel'">取消</a>  
		</div>
	</form>
</div>

<!-- 记账确认 -->
<div id="onDBAccountingMarkdlg" data-options="iconCls:'icon-save'"
	style="width: 600px; height: 400px; padding: 15px">
	<form id="onDBAccountingMarkForm" method="post">
		<table id="orgInfoAddTable" style="margin:auto">
			<tbody style="font-size: 13">
				<tr style="line-height:15px;">
					<th style="text-align: right;">期次:</th>
					<td><input id="sequence" name="sequence" class="easyui-textbox" style="width:150px" data-options="editable:false,required:true"/></td>
				</tr>
				<tr style="line-height:15px;">
					<th style="text-align: right;">合同号:</th>
					<td><input id="contracts" name="contracts" class="easyui-textbox" style="width:150px" data-options="editable:false,required:true"/></td>
				</tr>

				<tr style="line-left:100px;">
					<th style="text-align: right;"><samp
							style="color: red">*</samp>请修改记账日期:</th>
					<td><input class="easyui-datebox" type="text"
						name="updateDate" id="jzsUpdateDate"
						data-options="formatter:myformatter,parser:myparser"
						maxlength="20"   style="width:150px"/>
					</td>
				</tr>
				<tr style="line-height:15px;">
					<th style="text-align: right;">请填写记账备注:</th>
					<td><input class="easyui-textbox" id="keepAccountsRemark" name="keepAccountsRemark"  style="width:150px"/></td>
				</tr>
			</tbody>
		</table>
		<div id="EditTop" style="margin-top:15px">
			<a style="margin-left:50px" href="javascript:;" onClick = "accountingMarkSubmit('onDBAccountingMarkdlg','onDBAccountingMarkForm')" class="easyui-linkbutton" data-options="iconCls:'icon-ok'">记账确认</a>
			<a href="javascript:;" onClick = "cancelAccountingMarkSubmit('onDBAccountingMarkdlg','onDBAccountingMarkForm')" class="easyui-linkbutton" data-options="iconCls:'icon-ok'">记账撤销</a>
	        <a href="javascript:;" onClick = "closeWin('onDBAccountingMarkdlg')" class="easyui-linkbutton" data-options="iconCls:'icon-cancel'">取消</a>  
		</div>
	</form>
</div>

<!-- 记账撤销 -->
<div id="cancelAccountingMarkdlg" data-options="iconCls:'icon-save'"
	style="width: 600px; height: 400px; padding: 15px">
	<form id="cancelAccountingMarkForm" method="post">
		<table id="orgInfoAddTable" style="margin:auto">
			<tbody style="font-size: 13">
				<tr style="line-height:10px;">
					<th style="text-align: right;">匹配合同数:</th>
					<td><input class="easyui-textbox" id="cancelAccountingMarkContractCount" data-options="editable:false" name="cancelAccountingMarkContractCount" style="width:150px" /></td>
				</tr>
				<tr style="line-height:15px;">
					<th style="text-align: right;">匹配合同总金额:</th>
					<td><input class="easyui-textbox" id="cancelAccountingMarkMoneyCount" data-options="editable:false" name="cancelAccountingMarkMoneyCount" style="width:150px" /></td>
				</tr>
				<tr style="line-height:15px;">
					<th style="text-align: right;">合同号:</th>
					<td><input id="contracts" name="contracts" class="easyui-textbox" style="width:150px" data-options="editable:false"/></td>
				</tr>
				<tr>
					<th style="text-align: right;">记账日期:</th>
					<td><input id="startKeepAccountsTime" name="startKeepAccountsTime" data-options="editable:false" style="width:150px" class="easyui-textbox"/>至
					<input id="endKeepAccountsTime" name="endKeepAccountsTime" data-options="editable:false" style="width:150px" class="easyui-textbox"/></td>
				</tr>
				
				<tr>
					<th style="text-align: right;">回款日期:</th>
					<td><input id="startRepayDate" name="startRepayDate" data-options="editable:false" style="width:150px" class="easyui-textbox"/>至
					<input id="endRepayDate" name="endRepayDate" data-options="editable:false" style="width:150px" class="easyui-textbox"/></td>
				</tr>
				<tr>
					<th style="text-align: right;">注册日期:</th>
					<td><input id="startRegistrationDate" name="startRegistrationDate" data-options="editable:false" style="width:150px" class="easyui-textbox"/>至
					<input id="endRegistrationDate" name="endRegistrationDate" data-options="editable:false" style="width:150px" class="easyui-textbox"/></td>
				</tr>
			</tbody>
		</table>
		<div id="EditTop" style="margin-top:15px">
			<a style="margin-left:70px" href="javascript:;" onClick = "cancelAccountingMarkSubmit('cancelAccountingMarkdlg','cancelAccountingMarkForm')" class="easyui-linkbutton" data-options="iconCls:'icon-ok'">确认撤销</a>
	        <a href="javascript:;" onClick = "closeWin('cancelAccountingMarkdlg')" class="easyui-linkbutton" data-options="iconCls:'icon-cancel'">取消撤销</a>  
		</div>
	</form>
</div>
