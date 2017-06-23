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
<script type="text/javascript" src="${CONTEXT_PATH}/js/app/repaymentDetail.js?version=2" charset="utf-8"></script>

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
		style="width: 100%; height:16%">
		<form id="queryForm" method="post">
		<div style="width: 100%; margin-top:8px">
			<label style="margin-left:10px">交易日期:</label>
				 <input id="startDueDate" name="startDueDate" type="text" class="easyui-datebox" style="width: 100px"
						data-options="formatter:myformatter,parser:myparser" /> 至
				 <input	id="endDueDate" name="endDueDate" type="text" class="easyui-datebox"	style="width: 100px"
						data-options="formatter:myformatter,parser:myparser" />
			<label style="margin-left:22px">合同号:</label> 
				<input id="serialNo" name="serialNo" class="easyui-textbox" style="width:100px" />
			<label style="margin-left:20px">支付状态:</label> 
				 <select id="payStatus" name="payStatus" class="easyui-combobox textbox combobox-f combo-f textbox-f" style="width: 100px;">
					<option value="">---请选择---</option>
					<option value="0">未支付</option>
					<option value="1">已支付</option>
				</select>
			<label	style="margin-left: 40px">注册日期:</label>
			 	 <input	id="startRegistrationDate" name="startRegistrationDate" type="text" class="easyui-datebox" style="width: 100px"
						data-options="formatter:myformatter,parser:myparser"> 至
				 <input	id="endRegistrationDate" name="endRegistrationDate" type="text" class="easyui-datebox"	style="width: 100px"
						data-options="formatter:myformatter,parser:myparser" />

		</div>
		<div style="margin-top:9px">
			<label	style="margin-left: 10px">应还日期:</label>
			 	 <input	id="startPayDate" name="startPayDate" type="text" class="easyui-datebox" style="width: 100px"
						data-options="formatter:myformatter,parser:myparser"> 至
				 <input	id="endPayDate" name="endPayDate" type="text" class="easyui-datebox"	style="width: 100px"
						data-options="formatter:myformatter,parser:myparser" />
			<label style="margin-left:8px">审核状态:</label> 
				 <select id="approveStatus" name="approveStatus" class="easyui-combobox textbox combobox-f combo-f textbox-f" style="width: 100px;">
					<option value="">---请选择---</option>
					<option value="0">未审核</option>
					<option value="1">已审核</option>
				</select>
			<label style="margin-left:10px">产品子类型:</label> 
				 <select id="subProductType" name="subProductType" class="easyui-combobox textbox combobox-f combo-f textbox-f" style="width: 130px;">
					<option value="">---请选择---</option>
					<option value="普通POS贷">普通POS贷</option>
					<option value="预约现金贷">预约现金贷</option>
					<option value="无预约现金贷">无预约现金贷</option>
					<option value="车主现金贷">车主现金贷</option>
					<option value="成人教育贷">成人教育贷</option>
					<option value="学生教育贷">学生教育贷</option>
					<option value="小企业贷商户通">小企业贷商户通</option>
					<option value="学生消费贷">学生消费贷</option>
				</select>
			<label style="margin-left:10px">还款类型:</label> 
				 <select id="payType" name="payType" class="easyui-combobox textbox combobox-f combo-f textbox-f" style="width: 100px;">
					<option value="">---请选择---</option>
					<option value="0">提前还款</option>
					<option value="1">正常还款</option>
				</select>
			<label style="margin-left:10px">所属方:</label> 
				 <select id="assetBelong" name="assetBelong" class="easyui-combobox textbox combobox-f combo-f textbox-f" style="width: 100px;">
					<option value="">---请选择---</option>
					<option value="ZXXT">中信信托</option>
					<option value="ZTXT">中泰信托</option>
					<option value="DBZQ">德邦证券</option>
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
						    <a href="#"	class="easyui-linkbutton"	data-options="iconCls:'icon-ok',plain:'true'" onclick="contractApprove()" >审核</a>
						    <a href="#"	class="easyui-linkbutton"	data-options="iconCls:'icon-ok',plain:'true'" onclick="accountingMark()" >记账确认</a>
						    <a href="#"	class="easyui-linkbutton"	data-options="iconCls:'icon-cancel',plain:'true'" onclick="cancelAccountingMark()" >记账撤销</a>
						    <a href="#"	class="easyui-linkbutton"	data-options="iconCls:'icon-edit',plain:'true'" onclick="edit()" >批量编辑</a>
							<a href="#" class="easyui-linkbutton" 	data-options="iconCls:'icon-save',plain:'true'" onclick="exportExcel('${CONTEXT_PATH}')">数据导出</a>
						</div> <!-- 按钮 end -->
					</td>
				</tr>
			</table>
		</div>
		<table id="clearanceCertificateGrid"
			class="easyui-datagrid" title="信托回款"
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
					<th data-options="field:'id',checkbox:true"></th>
					<th data-options="field:'duedate'">交易日期</th>
					<th data-options="field:'accorddate'">统计日期</th>
					<th data-options="field:'serialNo'">合同号</th>
					<th data-options="field:'clientName'">客户姓名</th>
					<th data-options="field:'registrationDate'">注册日期</th>
					<th data-options="field:'maturitydate'">合同到期日</th>
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
					<th data-options="field:'citycode'">城市代码</th>
					<th data-options="field:'creditperson'" formatter="companyNameFormatter">资金方</th>
					<th data-options="field:'contractlife'">合同轨迹</th>
					<th data-options="field:'cdate'">强制日期</th>
					<th data-options="field:'payDate'">应还日期</th>
					<th data-options="field:'seqId'">期次</th>
					<th data-options="field:'canceltype'" formatter="cancelTypeFormatter">是否取消分期期次</th>
					<th data-options="field:'deliverydate'">转让日</th>
					<th data-options="field:'dcdate'">代偿日</th>
					<th data-options="field:'shdate'">赎回日</th>
					<th data-options="field:'tbdate'">保险投保日</th>
					<th data-options="field:'lpdate'">保险理赔日</th>
					<th data-options="field:'debours'" formatter="companyNameFormatter">代垫方</th>
					<th data-options="field:'assetBelong'" formatter="companyNameFormatter">资产所属方</th>
					<th data-options="field:'guaranteeParty'" formatter="companyNameFormatter">保证方</th>
					<th data-options="field:'payType'" formatter="payTypeFormatter">还款类型</th>
					<th data-options="field:'payPrincipalamt'" formatter="thousandthFormatter" width="120px">本金</th>
					<th data-options="field:'payInteAmt'" formatter="thousandthFormatter" width="120px">利息</th>
					<th data-options="field:'stampDuty'" formatter="thousandthFormatter" width="120px">印花税</th>
					<th data-options="field:'payAmt'" formatter="thousandthFormatter" width="120px">总额</th>
					<th data-options="field:'approveTime'">审核日期</th>
					<th data-options="field:'approveStatus'" formatter="approveStatusFormatter" >审核状态</th>
					<th data-options="field:'payStatus'" formatter="payStatusFormatter" >支付状态</th>
					<th data-options="field:'keepaccountsdate'">记账日期</th>
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
					<th style="text-align: right;">所属方:</th>
					<td>
						<input id="assetBelong" name="assetBelong" type="hidden" style="width:150px" data-options="editable:false"/>
						<input id="assetBelongShow" name="assetBelongShow" class="easyui-textbox" style="width:150px" data-options="editable:false"/>
					</td>
				</tr>
				<tr>
					<th style="text-align: right;">还款类型:</th>
					<td><input id="payType" name="payType" data-options="editable:false" style="width:150px" class="easyui-textbox"/></td>
				</tr>
				
				<tr>
					<th style="text-align: right;">产品子类型:</th>
					<td><input id="subProductType" name="subProductType" data-options="editable:false" style="width:150px" class="easyui-textbox"/></td>
				</tr>
				
				<tr>
					<th style="text-align: right;">交易日期:</th>
					<td><input id="startDueDate" name="startDueDate" class="easyui-textbox" data-options="editable:false" style="width:150px" />至
					<input id="endDueDate" name="endDueDate" class="easyui-textbox" data-options="editable:false" style="width:150px" /></td>
				</tr>
				
				<tr>
					<th style="text-align: right;">注册日期:</th>
					<td><input id="startRegistrationDate" name="startRegistrationDate" data-options="editable:false" style="width:150px" class="easyui-textbox"/>至
					<input id="endRegistrationDate" name="endRegistrationDate" data-options="editable:false" style="width:150px" class="easyui-textbox"/></td>
				</tr>
				
				<tr>
					<th style="text-align: right;">应还日期:</th>
					<td><input id="startPayDate" name="startPayDate" class="easyui-textbox" data-options="editable:false" style="width:150px" />至
					<input id="endPayDate" name="endPayDate" class="easyui-textbox" data-options="editable:false" style="width:150px" /></td>
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
				<tr style="line-height:0px;">
					<th style="text-align: right;">匹配合同数:</th>
					<td><input class="easyui-textbox" id="accountingMarkContractCount" data-options="editable:false" name="accountingMarkContractCount" style="width:150px" /></td>
				</tr>
				<tr style="line-height:20px;">
					<th style="text-align: right;">匹配合同总金额:</th>
					<td><input id="accountingMarkMoneyCount" name="accountingMarkMoneyCount" class="easyui-textbox"  data-options="editable:false"  style="width:150px" /></td>
				</tr>
				<tr style="line-height:20px;">
					<th style="text-align: right;">合同号:</th>
					<td><input id="serialNo" name="serialNo" class="easyui-textbox" data-options="editable:false" style="width:150px"/></td>
				</tr>
				<tr style="line-height:20px;">
					<th style="text-align: right;">所属方:</th>
					<td>
						<input id="assetBelong" name="assetBelong" type="hidden" style="width:150px" data-options="editable:false"/>
						<input id="assetBelongShow" name="assetBelongShow" class="easyui-textbox" style="width:150px" data-options="editable:false"/>
					</td>
				</tr>
				<tr>
					<th style="text-align: right;">还款类型:</th>
					<td><input id="payType" name="payType" class="easyui-textbox" data-options="editable:false" style="width:150px" /></td>
				</tr>
				
				<tr>
					<th style="text-align: right;">审核状态:</th>
					<td>
						 <select id="approveStatus" name="approveStatus" data-options="editable:false" class="easyui-combobox textbox combobox-f combo-f textbox-f" style="width:150px">
							<option value="1">已审核</option>
							<option value="0">未审核</option>
						</select>
					</td>
				</tr>
				
				<tr>
					<th style="text-align: right;">产品子类型:</th>
					<td><input id="subProductType" name="subProductType" class="easyui-textbox" data-options="editable:false" style="width:150px" /></td>
				</tr>
				
				<tr>
					<th style="text-align: right;">交易日期:</th>
					<td><input id="startDueDate" name="startDueDate" class="easyui-textbox" data-options="editable:false" style="width:150px" />至
					<input id="endDueDate" name="endDueDate" class="easyui-textbox" data-options="editable:false" style="width:150px" /></td>
				</tr>
				
				<tr>
					<th style="text-align: right;">注册日期:</th>
					<td><input id="startRegistrationDate" name="startRegistrationDate" data-options="editable:false" style="width:150px" class="easyui-textbox"/>至
					<input id="endRegistrationDate" name="endRegistrationDate" data-options="editable:false" style="width:150px" class="easyui-textbox"/></td>
				</tr>
				
				<tr>
					<th style="text-align: right;">应还日期:</th>
					<td><input id="startPayDate" name="startPayDate" class="easyui-textbox" data-options="editable:false" style="width:150px" />至
					<input id="endPayDate" name="endPayDate" class="easyui-textbox" data-options="editable:false" style="width:150px" /></td>
				</tr>

				<tr style="line-left:100px;">
					<th style="text-align: right;"><samp
							style="color: red">*</samp>请选择记账日期:</th>
					<td><input id="jzUpdateDate" name="keepaccountsdate"  class="easyui-datebox" type="text"
						data-options="formatter:myformatter,parser:myparser,required:true,editable:false"
						maxlength="20"   style="width:150px"/>
					</td>
				</tr>
				
				<tr style="line-height:15px;">
					<th style="text-align: right;">记账备注:</th>
					<td><input id="keepaccountsRemark" name="keepaccountsRemark" class="easyui-textbox"  style="width:150px"/></td>
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
	style="width: 600px; height: 400px; padding: 15px">
	<form id="onDBAccountingMarkForm" method="post">
		<table id="orgInfoAddTable" style="margin:auto">
			<tbody style="font-size: 13">
				<tr style="line-height:15px;">
					<th style="text-align: right;">期次:</th>
					<td><input id="seqId" name="seqId" class="easyui-textbox" style="width:150px" data-options="editable:false,required:true"/></td>
				</tr>
				<tr style="line-height:20px;">
					<th style="text-align: right;">合同号:</th>
					<td><input id="serialNo" name="serialNo" class="easyui-textbox" data-options="editable:false" style="width:150px,required:true"/></td>
				</tr>
				<tr style="line-height:15px;">
					<th style="text-align: right;">资产所属方:</th>
					<td>
						<input id="assetBelongShow" name="assetBelongShow" class="easyui-textbox" style="width:150px" data-options="editable:false,required:true"/>
						<input id="assetBelong" name="assetBelong" type="hidden" data-options="editable:false" style="width:150px"/>
					</td>
				</tr>
				<tr style="line-height:20px;">
					<th style="text-align: right;">共计本金:</th>
					<td>
						<input id="payPrincipalamt" name="payPrincipalamt" type="hidden" data-options="editable:false" style="width:150px"/>
						<input id="payPrincipalamtSum" name="payPrincipalamtSum" class="easyui-textbox" data-options="editable:false,required:true" style="width:150px"/>
					</td>
				</tr>
				<tr style="line-height:15px;">
					<th style="text-align: right;">共计利息:</th>
					<td>
						<input id="payInteAmt"    name="payInteAmt"   type="hidden" style="width:150px" data-options="editable:false"/>
						<input id="payInteAmtSum" name="payInteAmtSum" class="easyui-textbox" style="width:150px" data-options="editable:false,required:true"/>
					</td>
				</tr>

				<tr style="line-left:100px;">
					<th style="text-align: right;"><samp
							style="color: red">*</samp>请选择记账日期:</th>
					<td><input id="jzsUpdateDate" name="keepaccountsdate"  class="easyui-datebox" type="text"
						data-options="formatter:myformatter,parser:myparser"
						maxlength="20"   style="width:150px"/>
					</td>
				</tr>
				
				<tr style="line-height:15px;">
					<th style="text-align: right;">记账备注:</th>
					<td><input id="keepaccountsRemark" name="keepaccountsRemark" class="easyui-textbox"  style="width:150px"/></td>
				</tr>
				
			</tbody>
		</table>
		<div id="EditTop" style="margin-top:15px">
			<a style="margin-left:50px" href="javascript:;" onClick = "accountingMarkSubmit('onDBAccountingMarkdlg','onDBAccountingMarkForm')" class="easyui-linkbutton" data-options="iconCls:'icon-ok'">记账确认</a>
			<a href="javascript:;" onClick = "cancelAccountingMarkCheck('onDBAccountingMarkdlg','onDBAccountingMarkForm')" class="easyui-linkbutton" data-options="iconCls:'icon-ok'">记账撤销</a>
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
				<tr style="line-height:0px;">
					<th style="text-align: right;">匹配合同数:</th>
					<td><input class="easyui-textbox" id="cancelAccountingMarkContractCount" name="cancelAccountingMarkContractCount" data-options="editable:false"  style="width:150px" /></td>
				</tr>
				<tr style="line-height:20px;">
					<th style="text-align: right;">匹配合同总金额:</th>
					<td><input id="cancelAccountingMarkMoneyCount" name="cancelAccountingMarkMoneyCount" class="easyui-textbox"  data-options="editable:false"  style="width:150px" /></td>
				</tr>
				<tr style="line-height:20px;">
					<th style="text-align: right;">合同号:</th>
					<td><input id="serialNo" name="serialNo" class="easyui-textbox" data-options="editable:false" style="width:150px"/></td>
				</tr>
				<tr style="line-height:20px;">
					<th style="text-align: right;">所属方:</th>
					<td>
						<input id="assetBelong" name="assetBelong" type="hidden" style="width:150px" data-options="editable:false"/>
						<input id="assetBelongShow" name="assetBelongShow" class="easyui-textbox" style="width:150px" data-options="editable:false"/>
					</td>
				</tr>
				<tr>
					<th style="text-align: right;">还款类型:</th>
					<td><input id="payType" name="payType" class="easyui-textbox" data-options="editable:false" style="width:150px" /></td>
				</tr>
				
				<tr>
					<th style="text-align: right;">审核状态:</th>
					<td>
						 <select id="approveStatus" name="approveStatus" data-options="editable:false" class="easyui-combobox textbox combobox-f combo-f textbox-f" style="width:150px">
							<option value="1">已审核</option>
							<option value="0">未审核</option>
						</select>
					</td>
				</tr>
				
				<tr>
					<th style="text-align: right;">产品子类型:</th>
					<td><input id="subProductType" name="subProductType" class="easyui-textbox" data-options="editable:false" style="width:150px" /></td>
				</tr>
				
				<tr>
					<th style="text-align: right;">交易日期:</th>
					<td><input id="startDueDate" name="startDueDate" class="easyui-textbox" data-options="editable:false" style="width:150px" />至
					<input id="endDueDate" name="endDueDate" class="easyui-textbox" data-options="editable:false" style="width:150px" /></td>
				</tr>
				
				<tr>
					<th style="text-align: right;">注册日期:</th>
					<td><input id="startRegistrationDate" name="startRegistrationDate" data-options="editable:false" style="width:150px" class="easyui-textbox"/>至
					<input id="endRegistrationDate" name="endRegistrationDate" data-options="editable:false" style="width:150px" class="easyui-textbox"/></td>
				</tr>
				
				<tr>
					<th style="text-align: right;">应还日期:</th>
					<td><input id="startPayDate" name="startPayDate" class="easyui-textbox" data-options="editable:false" style="width:150px" />至
					<input id="endPayDate" name="endPayDate" class="easyui-textbox" data-options="editable:false" style="width:150px" /></td>
				</tr>
			</tbody>
		</table>
		<div id="EditTop" style="margin-top:15px">
			<a style="margin-left:70px" href="javascript:;" onClick = "cancelAccountingMarkCheck('cancelAccountingMarkdlg','cancelAccountingMarkForm')" class="easyui-linkbutton" data-options="iconCls:'icon-ok'">确认撤销</a>
	        <a href="javascript:;" onClick = "closeWin('cancelAccountingMarkdlg')" class="easyui-linkbutton" data-options="iconCls:'icon-cancel'">取消撤销</a>  
		</div>
	</form>
</div>

<!-- 数据网格end -->
