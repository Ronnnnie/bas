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
<script type="text/javascript" src="${CONTEXT_PATH}/js/app/overdueDetail.js" charset="utf-8"></script>

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
		<div style="margin-top:10px;margin-left:10px">
			<label style="margin-left: 10px">记账日期:</label>
				<input id="startInAccountDate" name="startInAccountDate" type="text" class="easyui-datebox" style="width:110px;"
					data-options="formatter:myformatter,parser:myparser" />至
				<input id="endInAccountDate"  name="endInAccountDate" type="text" class="easyui-datebox"	style="width: 110px"
					data-options="formatter:myformatter,parser:myparser" />
			<label style="margin-left: 10px">资产所属方:</label>
				 <select id="assetBelong" class="easyui-combobox textbox combobox-f combo-f textbox-f" name="assetBelong" style="width: 130px;">
					<option value="">---请选择---</option>
					<option value="SZKK">深圳快快</option>
					<option value="XZHR">西藏惠融</option>
					<option value="BDGY">百度国元</option>
					<option value="BQJR">佰仟金融</option>
					<option value="GZBC">贵州佰诚</option>
					<option value="JSZB">嘉实资本</option>
					<option value="DBZQ">德邦证券</option>
					<option value="ZXXT">中信信托</option>
					<option value="ZTXT">中泰信托</option>
					<option value="ZHXT">中航信托</option>
					<option value="JYPH">嘉银普惠</option>
					<option value="HBYH">哈尔滨银行</option>
					<option value="HKYH">海口农商行</option>
					<option value="RXCH">宝安融兴村行</option>
					<option value="RMBX">中国人民财产保险</option>
				 </select>
			<label style="margin-left: 15px">合同号:</label> 
				<input style="width: 120px" class="easyui-textbox" id="serialNo" name="serialNo" />
			<label style="margin-left: 10px">五级分类:</label>
				 <select id="classfy" class="easyui-combobox textbox combobox-f combo-f textbox-f" name="classfy" style="width: 100px;">
					<option value="">---请选择---</option>
					<option value="A">A</option>
					<option value="B">B</option>
					<option value="C">C</option>
					<option value="D">D</option>
					<option value="E">E</option>
				 </select>
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
						    <a href="#"	class="easyui-linkbutton"	data-options="iconCls:'icon-ok',plain:'true'" onclick="accountingMark()" >记账确认</a>
						    <a href="#"	class="easyui-linkbutton"	data-options="iconCls:'icon-cancel',plain:'true'" onclick="cancelAccountingMark()" >记账撤销</a>
						    <a href="#"	class="easyui-linkbutton"	data-options="iconCls:'icon-edit',plain:'true'" onclick="edit()" >批量编辑</a>
						    <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-save',plain:'true'" onclick="ExportExcel('${CONTEXT_PATH}')">数据导出</a>
						</div> <!-- 按钮 end -->
					</td>
				</tr>
			</table>
		</div>
		<table id="clearanceCertificateGrid"
			class="easyui-datagrid" title="逾期明细"
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
					<th data-options="field:'inAccountDate'">记账日期</th>
					<th data-options="field:'accordDate'">统计日期</th>
					<th data-options="field:'serialNo'">合同号</th>
					<th data-options="field:'clientname'">客户名称</th>
					<th data-options="field:'registrationDate'">注册日期</th>
					<th data-options="field:'maturitydate'">合同到期日</th>
					<th data-options="field:'sno'">门店代码</th>
					<th data-options="field:'rno'">商户代码</th>
					<th data-options="field:'sureType'">客户渠道</th>
					<th data-options="field:'businessModel'">业务模式</th>
					<th data-options="field:'productId'">贷款类型</th>
					<th data-options="field:'subProductType'">贷款子类型</th>
					<th data-options="field:'province'">省份</th>
					<th data-options="field:'city'">城市</th>
					<th data-options="field:'cityCode'">市编码</th>
					<th data-options="field:'creditperson'" formatter="companyNameFormatter">资金方</th>
					<th data-options="field:'cdate'">强制日期</th>
					<th data-options="field:'overdueDays'">逾期天数</th>
					<th data-options="field:'overdueRemark'">逾期描述</th>
					<th data-options="field:'classFy'">五级分类</th>
					<th data-options="field:'payType'" formatter="payTypeFormatter">还款类型</th>
					<th data-options="field:'shouldAlsoDate'">应还日</th>
					<th data-options="field:'seqId'">期次</th>
					<th data-options="field:'deliveryDate'">转让日</th>
					<th data-options="field:'dcDate'">代偿日</th>
					<th data-options="field:'shDate'">赎回日</th>
					<th data-options="field:'tbDate'">保险投保日</th>
					<th data-options="field:'lpDate'">保险理赔日</th>
					<th data-options="field:'debours'" formatter="companyNameFormatter">代垫方</th>
					<th data-options="field:'assetBelong'" formatter="companyNameFormatter">资产所属方</th>
					<th data-options="field:'guaranteeParty'" formatter="companyNameFormatter">保证方</th>
					<th data-options="field:'bxhxDate'" >本息核销日期</th>
					<th data-options="field:'fyhxDate'" >费用核销日期</th>
					<th data-options="field:'payprinciPalamt'" formatter="thousandthFormatter">逾期本金</th>
					<th data-options="field:'payInteamt'" formatter="thousandthFormatter">逾期利息</th>
					<th data-options="field:'a2'" formatter="thousandthFormatter">逾期客户服务费</th>
					<th data-options="field:'a7'" formatter="thousandthFormatter">逾期账户管理费</th>
					<th data-options="field:'a12'" formatter="thousandthFormatter">逾期增值服务费</th>
					<th data-options="field:'a18'" formatter="thousandthFormatter">逾期实心还服务费</th>
					<th data-options="field:'a22'" formatter="thousandthFormatter">逾期佰保袋服务费</th>
					<th data-options="field:'amount'" formatter="thousandthFormatter">合计</th>
					<th data-options="field:'inAccountStatus'" formatter="formatStatus">状态</th>
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
			
				<tr style="line-height:10px;">
					<th style="text-align: right; font-size: 13">匹配合同数:</th>
					<td><input class="easyui-textbox" id="accountingMarkContractCount" data-options="editable:false" name="accountingMarkContractCount" style="width:150px" /></td>
				</tr>
				<tr style="line-height:15px;">
					<th style="text-align: right; font-size: 13">匹配合同总金额:</th>
					<td><input class="easyui-textbox" id="accountingMarkMoneyCount" data-options="editable:false" name="accountingMarkMoneyCount" style="width:150px" /></td>
				</tr>
				
				<tr style="line-height:15px;">
					<th style="text-align: right; font-size: 13">记账日期(起):</th>
					<td><input id="startInAccountDate" name="startInAccountDate" class="easyui-textbox" style="width:150px" data-options="editable:false"/></td>
				</tr>
				<tr style="line-height:10px;">
					<th style="text-align: right; font-size: 13">记账日期(止):</th>
					<td><input id="endInAccountDate" name="endInAccountDate" class="easyui-textbox" style="width:150px" data-options="editable:false"/></td>
				</tr>
				<tr>
					<th style="text-align: right; font-size: 13">合同号:</th>
					<td><input id="serialNo" name="serialNo" class="easyui-textbox" style="width:150px" data-options="editable:false"/></td>
				</tr>
				<tr>
					<th style="text-align: right; font-size: 13">资产所属方:</th>
					<td>
						<input id="assetBelong" name="assetBelong" type="hidden" style="width:150px" data-options="editable:false"/>
						<input id="assetBelongShow" name="assetBelongShow" class="easyui-textbox" style="width:150px" data-options="editable:false"/>
					</td>
				</tr>
				<tr>
					<th style="text-align: right; font-size: 13">五级分类:</th>
					<td><input id="classfy" name="classfy" class="easyui-textbox" style="width:150px" data-options="editable:false"/></td>
				</tr>

				<tr style="line-left:100px;">
					<th style="text-align: right; font-size: 13"><samp
							style="color: red">*</samp>请修改记账日期:</th>
					<td><input class="easyui-datebox" type="text"
						name="updateDate" id="jzUpdateDate"
						data-options="formatter:myformatter,parser:myparser,required:true,editable:false"
						maxlength="20"   style="width:150px"/>
					</td>
				</tr>
				<tr style="line-height:15px;">
					<th style="text-align: right; font-size: 13">请填写记账备注:</th>
					<td><input class="easyui-textbox" id="inAccountRemark" name="inAccountRemark"  style="width:150px"/></td>
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
	style="width: 600px; height: 400px; padding: 10px">
	<form id="onDBAccountingMarkForm" method="post">
		<table id="orgInfoAddTable">
			<tbody>
				<tr>
					<th style="text-align: right; font-size: 13">期次:</th>
					<td><input id="seqId" name="seqId" class="easyui-textbox" style="width:150px" data-options="editable:false,required:true"/></td>
				</tr>
				<tr>
					<th style="text-align: right; font-size: 13">合同号:</th>
					<td><input id="serialNo" name="serialNo" class="easyui-textbox" style="width:150px" data-options="editable:false,required:true"/></td>
				</tr>
				<tr>
					<th style="text-align: right; font-size: 13">资产所属方:</th>
					<td>
						<input id="assetBelong" name="assetBelong" type="hidden" style="width:150px" data-options="editable:false"/>
						<input id="assetBelongShow" name="assetBelongShow" class="easyui-textbox" style="width:150px" data-options="editable:false,required:true"/>
					</td>
				</tr>
				<tr style="line-height:15px;">
					<th style="text-align: right; font-size: 13">逾期本金:</th>
					<td>
						<input id="payprinciPalamt" name="payprinciPalamt" type="hidden" style="width:150px" data-options="editable:false"/>
						<input id="payprinciPalamtSum" name="payprinciPalamtSum" class="easyui-textbox" style="width:150px" data-options="editable:false,required:true"/>
					</td>
				</tr>
				<tr style="line-height:15px;">
					<th style="text-align: right; font-size: 13">逾期利息:</th>
					<td>
						<input id="payInteamt" name="payInteamt" type="hidden" style="width:150px" data-options="editable:false"/>
						<input id="payInteamtSum" name="payInteamtSum" class="easyui-textbox" style="width:150px" data-options="editable:false"/>
					</td>
				</tr>

				<tr style="line-left:100px;">
					<th style="text-align: right; font-size: 13"><samp
							style="color: red">*</samp>请修改记账日期:</th>
					<td><input class="easyui-datebox" type="text"
						name="updateDate" id="jzsUpdateDate"
						data-options="formatter:myformatter,parser:myparser"
						maxlength="20"   style="width:150px"/>
					</td>
				</tr>
				<tr style="line-height:15px;">
					<th style="text-align: right; font-size: 13">请填写记账备注:</th>
					<td><input class="easyui-textbox" id="inAccountRemark" name="inAccountRemark"  style="width:150px"/></td>
				</tr>
			</tbody>
		</table>
		<div id="EditTop" style="margin-top:10px">
			<a style="margin-left:50px" href="javascript:;" onClick = "accountingMarkSubmit('onDBAccountingMarkdlg','onDBAccountingMarkForm')" class="easyui-linkbutton" data-options="iconCls:'icon-ok'">记账确认</a>
			<a href="javascript:;" onClick = "cancelAccountingMarkSubmit('onDBAccountingMarkdlg','onDBAccountingMarkForm')" class="easyui-linkbutton" data-options="iconCls:'icon-ok'">记账撤销</a>
	        <a href="javascript:;" onClick = "closeWin('onDBAccountingMarkdlg')" class="easyui-linkbutton" data-options="iconCls:'icon-cancel'">取消</a>  
		</div>
	</form>
</div>

<!-- 记账撤销 -->
<div id="cancelAccountingMarkdlg" data-options="iconCls:'icon-save'"
	style="width: 600px; height: 400px; padding: 10px">
	<form id="cancelAccountingMarkForm" method="post">
		<table id="orgInfoAddTable">
			<tbody>
				<tr style="line-height:10px;">
					<th style="text-align: right; font-size: 13">匹配合同数:</th>
					<td><input class="easyui-textbox" id="cancelAccountingMarkContractCount" data-options="editable:false" name="accountingMarkContractCount" style="width:150px" /></td>
				</tr>
				<tr style="line-height:15px;">
					<th style="text-align: right; font-size: 13">匹配合同总金额:</th>
					<td><input class="easyui-textbox" id="cancelAccountingMarkMoneyCount" data-options="editable:false" name="accountingMarkMoneyCount" style="width:150px" /></td>
				</tr>
				
				<tr>
					<th style="text-align: right; font-size: 13">合同号:</th>
					<td><input id="serialNo" name="serialNo" class="easyui-textbox" style="width:150px" data-options="editable:false"/></td>
				</tr>
				<tr>
					<th style="text-align: right; font-size: 13">产品子类型:</th>
					<td><input id="subProductType" name="subProductType" class="easyui-textbox" style="width:150px" data-options="editable:false"/></td>
				</tr>
				<tr>
					<th style="text-align: right; font-size: 13">资产所属方:</th>
					<td>
						<input id="assetBelong" name="assetBelong" type="hidden" style="width:150px" data-options="editable:false"/>
						<input id="assetBelongShow" name="assetBelongShow" class="easyui-textbox" style="width:150px" data-options="editable:false"/>
					</td>
				</tr>
				<tr style="line-height:15px;">
					<th style="text-align: right; font-size: 13">注册日期(起):</th>
					<td><input id="startRegistrationDate" name="startRegistrationDate" class="easyui-textbox" style="width:150px" data-options="editable:false"/></td>
				</tr>
				<tr style="line-height:10px;">
					<th style="text-align: right; font-size: 13">注册日期(止):</th>
					<td><input id="endRegistrationDate" name="endRegistrationDate" class="easyui-textbox" style="width:150px" data-options="editable:false"/></td>
				</tr>
				<tr style="line-height:15px;">
					<th style="text-align: right; font-size: 13">记账日期(起):</th>
					<td><input id="startInAccountDate" name="startInAccountDate" class="easyui-textbox" style="width:150px" data-options="editable:false"/></td>
				</tr>
				
				<tr style="line-height:10px;">
					<th style="text-align: right; font-size: 13">记账日期(止):</th>
					<td><input id="endInAccountDate" name="endInAccountDate" class="easyui-textbox" style="width:150px" data-options="editable:false"/></td>
				</tr>

			</tbody>
		</table>
		<div id="EditTop" style="margin-top:10px">
			<a style="margin-left:100px" href="javascript:;" onClick = "cancelAccountingMarkSubmit('cancelAccountingMarkdlg','cancelAccountingMarkForm')" class="easyui-linkbutton" data-options="iconCls:'icon-ok'">撤销记账</a>
	        <a href="javascript:;" onClick = "closeWin('cancelAccountingMarkdlg')" class="easyui-linkbutton" data-options="iconCls:'icon-cancel'">取消</a>  
		</div>
	</form>
</div>
