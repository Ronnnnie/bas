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
<script type="text/javascript" src="${CONTEXT_PATH}/js/app/releaseSlowDetail.js" charset="utf-8"></script>

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
		style="width: 100%; height:10%">
		<form id="queryForm" method="post">
		<div style="width: 100%; margin-top:8px">
			<label style="margin-left:10px">记账日期:</label>
				 <input id="startKeepAccountsDate" name="startKeepAccountsDate" type="text" class="easyui-datebox" style="width: 100px"
						data-options="formatter:myformatter,parser:myparser" /> 至
				 <input	id="endKeepAccountsDate" name="endKeepAccountsDate" type="text" class="easyui-datebox"	style="width: 100px"
						data-options="formatter:myformatter,parser:myparser" />
			<label style="margin-left:22px">合同号:</label> 
				<input id="serialNo" name="serialNo" class="easyui-textbox" style="width:100px" />
			<label style="margin-left:20px">是否放款:</label> 
				 <select id="sffk" name="sffk" class="easyui-combobox textbox combobox-f combo-f textbox-f" style="width: 100px;">
					<option value="">---请选择---</option>
					<option value="N">否</option>
					<option value="Y">是</option>
				</select>
			<a style="margin-left:10px;height:24px" class="easyui-linkbutton" data-options="iconCls:'icon-search'" onclick="query()" >搜索</a>
			<a style="margin-left:10px;height:24px" class="easyui-linkbutton" data-options="iconCls:'icon-reload'" onclick="clearForm('queryForm')" >重置</a>
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
						    <a href="#"	class="easyui-linkbutton"	data-options="iconCls:'icon-ok',plain:'true'" onclick="accountingMark()" >记账确认</a>
						    <a href="#"	class="easyui-linkbutton"	data-options="iconCls:'icon-cancel',plain:'true'" onclick="cancelAccountingMark()" >记账撤销</a>
						    <a href="#"	class="easyui-linkbutton"	data-options="iconCls:'icon-edit',plain:'true'" onclick="edit()" >批量编辑</a><br/>
						</div> <!-- 按钮 end -->
					</td>
				</tr>
			</table>
		</div>
		<table id="clearanceCertificateGrid"
			class="easyui-datagrid" title="放款明细"
			style="width: 100%; height: 100%;"
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
				<th data-options="field:'loansno',checkbox:true"></th>
				<th data-options="field:'keepAccountsDate'">记账日期</th>
				<th data-options="field:'accordDate'">统计日期</th>
				<th data-options="field:'serialNo'">合同号</th>
				<th data-options="field:'clientName'">客户姓名</th>
				<th data-options="field:'registrationDate'">合同注册日</th>
				<th data-options="field:'maturityDate'">合同到期日</th>
				<th data-options="field:'sureType'">客户渠道</th>
				<th data-options="field:'businessModel'">业务模式</th>
				<th data-options="field:'productId'">贷款类型</th>
				<th data-options="field:'subProductType'" >贷款子类型</th>
				<th data-options="field:'province'">省份</th>
				<th data-options="field:'city'">城市</th>
				<th data-options="field:'cityCode'">城市编码</th>
				<th data-options="field:'creditPerson'" formatter="companyNameFormatter">资金方</th>
				<th data-options="field:'businessSum'" formatter="thousandthFormatter">贷款本金</th>
				<th data-options="field:'keepAccountsStatus'" formatter="formatStatus" >记账状态</th>
				<th data-options="field:'putoutDate'" formatter="formatPutoutStatus" >放款状态</th>
				</tr>
			</thead>
		</table>
	</div>
</div>

<!-- 记账确认 -->
<div id="accountingMarkdlg" data-options="iconCls:'icon-save'"
	style="width: 600px; height: 400px; padding: 10px">
	<form id="accountingMarkForm" method="post">
		<table id="orgInfoAddTable">
			<tbody>
				<tr style="line-height:0px;">
					<th style="text-align: right; font-size: 13">匹配合同数:</th>
					<td><input class="easyui-textbox" id="accountingMarkContractCount" data-options="editable:false" name="accountingMarkContractCount" style="width:150px" /></td>
				</tr>
				<tr style="line-height:20px;">
					<th style="text-align: right; font-size: 13">匹配合同总金额:</th>
					<td><input id="accountingMarkMoneyCount" name="accountingMarkMoneyCount" class="easyui-textbox"  data-options="editable:false"  style="width:150px" /></td>
				</tr>
				<tr style="line-height:20px;">
					<th style="text-align: right; font-size: 13">合同号:</th>
					<td><input id="serialNo" name="serialNo" class="easyui-textbox" data-options="editable:false" style="width:150px"/></td>
				</tr>
				<tr style="line-height:20px;">
					<th style="text-align: right; font-size: 13">是否放款:</th>
					<td>
						<input id="sffk" name="sffk" type="hidden" data-options="editable:false" style="width:150px"/>
						<input id="sffkShow" name="sffkShow" class="easyui-textbox" data-options="editable:false" style="width:150px"/>
					</td>
				</tr>
				<tr style="line-height:20px;">
					<th style="text-align: right; font-size: 13">记账日期(起):</th>
					<td><input id="startKeepAccountsDate" name="startKeepAccountsDate" class="easyui-textbox" data-options="editable:false" style="width:150px" /></td>
				</tr>
				<tr style="line-height:20px;">
					<th style="text-align: right; font-size: 13">记账日期(止):</th>
					<td><input id="endKeepAccountsDate" name="endKeepAccountsDate" class="easyui-textbox" data-options="editable:false" style="width:150px" /></td>
				</tr>
				
				<tr style="line-left:100px;">
					<th style="text-align: right; font-size: 13"><samp
							style="color: red">*</samp>请选择记账日期:</th>
					<td><input id="jzUpdateDate" name="updateDate"  class="easyui-datebox" type="text"
						data-options="formatter:myformatter,parser:myparser,required:true,editable:false"
						maxlength="20"   style="width:150px"/>
					</td>
				</tr>
				
				<tr style="line-height:15px;">
					<th style="text-align: right; font-size: 13">记账备注:</th>
					<td><input id="keepAccountsRemark" name="keepAccountsRemark" class="easyui-textbox"  style="width:150px"/></td>
				</tr>
				
			</tbody>
		</table>
		<div id="EditTop" style="margin-top:5px">
			<a style="margin-left:100px" href="javascript:;" onClick = "accountingMarkSubmit('accountingMarkdlg','accountingMarkForm')" class="easyui-linkbutton" data-options="iconCls:'icon-ok'">提交</a>
	        <a href="javascript:;" onClick = "closeWin('accountingMarkdlg')" class="easyui-linkbutton" data-options="iconCls:'icon-cancel'">取消</a>  
		</div>
	</form>
</div>

<!-- 记账确认 -->
<div id="onDBAccountingMarkdlg" data-options="iconCls:'icon-save'"
	style="width: 620px; height: 400px; padding: 15px">
	<form id="onDBAccountingMarkForm" method="post">
		<table id="orgInfoAddTable" >
			<tbody>
				<tr style="line-height:20px;">
					<th style="text-align: right; font-size: 13">合同号:</th>
					<td><input id="serialNo" name="serialNo" class="easyui-textbox" data-options="editable:false" style="width:150px,required:true"/></td>
				</tr>
				<tr style="line-left:80px;">
					<th style="font-size: 13"><samp
							style="color: red">*</samp>请选择记账日期:</th>
					<td><input id="jzsUpdateDate" name="updateDate"  class="easyui-datebox" type="text"
						data-options="formatter:myformatter,parser:myparser"
						maxlength="20"   style="width:170px"/>
					</td>
				</tr>
				
				<tr style="line-height:15px;">
					<th style="text-align: right; font-size: 13">记账备注:</th>
					<td><input id="keepAccountsRemark" name="keepAccountsRemark" class="easyui-textbox"  style="width:170px"/></td>
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
		<table id="orgInfoAddTable" >
			<tbody>
				<tr style="line-height:0px;">
					<th style="text-align: right; font-size: 13">匹配合同数:</th>
					<td><input class="easyui-textbox" id="cancelAccountingMarkContractCount" name="cancelAccountingMarkContractCount" data-options="editable:false"  style="width:150px" /></td>
				</tr>
				<tr style="line-height:20px;">
					<th style="text-align: right; font-size: 13">匹配合同总金额:</th>
					<td><input id="cancelAccountingMarkMoneyCount" name="cancelAccountingMarkMoneyCount" class="easyui-textbox"  data-options="editable:false"  style="width:150px" /></td>
				</tr>
				<tr style="line-height:20px;">
					<th style="text-align: right; font-size: 13">合同号:</th>
					<td><input id="serialNo" name="serialNo" class="easyui-textbox" data-options="editable:false" style="width:150px"/></td>
				</tr>
				<tr style="line-height:20px;">
					<th style="text-align: right; font-size: 13">记账日期(起):</th>
					<td><input id="startKeepAccountsDate" name="startKeepAccountsDate" class="easyui-textbox" data-options="editable:false" style="width:150px" /></td>
				</tr>
				<tr style="line-height:20px;">
					<th style="text-align: right; font-size: 13">记账日期(止):</th>
					<td><input id="endKeepAccountsDate" name="endKeepAccountsDate" class="easyui-textbox" data-options="editable:false" style="width:150px" /></td>
				</tr>
				
				<tr style="line-height:20px;">
					<th style="text-align: right; font-size: 13">是否放款:</th>
					<td>
						<input id="sffk" name="sffk" type="hidden" data-options="editable:false" style="width:150px"/>
						<input id="sffkShow" name="sffkShow" class="easyui-textbox" data-options="editable:false" style="width:150px"/>
					</td>
				</tr>
			</tbody>
		</table>
		<div id="EditTop" style="margin-top:15px">
			<a style="margin-left:70px" href="javascript:;" onClick = "cancelAccountingMarkSubmit('cancelAccountingMarkdlg','cancelAccountingMarkForm')" class="easyui-linkbutton" data-options="iconCls:'icon-ok'">确认撤销</a>
	        <a href="javascript:;" onClick = "closeWin('cancelAccountingMarkdlg')" class="easyui-linkbutton" data-options="iconCls:'icon-cancel'">取消撤销</a>  
		</div>
	</form>
</div>
