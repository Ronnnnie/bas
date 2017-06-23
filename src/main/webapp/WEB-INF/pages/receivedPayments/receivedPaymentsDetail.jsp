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
<script type="text/javascript" src="${CONTEXT_PATH}/js/app/receivedPaymentsDetail.js?version=20170414" charset="utf-8"></script>

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
		<div style="width: 98%; margin-top:8px;margin-left:20px">
			<label style="margin-left: 0px">记账日期:</label>
				<input id="startKeepAccountsDate" type="text" class="easyui-datebox" style="width:110px;margin-top:50px;"
					data-options="formatter:myformatter,parser:myparser" /> 至
				<input id="endKeepAccountsDate" type="text" class="easyui-datebox"	style="width: 110px"
					data-options="formatter:myformatter,parser:myparser" />
			<label style="margin-left: 0px">实还日期:</label>
				<input id="startShouldAlsoDate" type="text" class="easyui-datebox" style="width:110px;margin-top:50px;"
					data-options="formatter:myformatter,parser:myparser" /> 至
				<input id="endShouldAlsoDate" type="text" class="easyui-datebox"	style="width: 110px"
					data-options="formatter:myformatter,parser:myparser" />
			<label>合同号:</label> 
				<input style="width: 120px" class="easyui-textbox" id="serialNo" name="serialNo" />
			<label>资产所属方:</label>
				<select id="assetBelong" class="easyui-combobox" name="assetBelong" style="width: 130px;">
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
			<a style="margin-left:5px;height:23px" id="button-search" class="easyui-linkbutton" data-options="iconCls:'icon-search'" onclick="query()" >搜索</a>
			<a style="margin-left:5px;height:23px" id="button-search" class="easyui-linkbutton" data-options="iconCls:'icon-reload'" onclick="clearForm('queryForm')" >重置</a>
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
			class="easyui-datagrid" title="合同实还明细表"
			style="width:100%; height: 96%;"
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
					<th data-options="field:'keepAccountsDate'" >记账日期</th>
					<th data-options="field:'accorddate'" >统计日期</th>
					<th data-options="field:'serialNo'" >合同号</th>
					<th data-options="field:'clientname'" >客户姓名</th>
					<th data-options="field:'registrationDate'" >注册日期</th>
					<th data-options="field:'maturitydate'" >合同到期日</th>
					<th data-options="field:'sno'" >门店代码</th>
					<th data-options="field:'rno'" >商户代码</th>
					<th data-options="field:'sa_id'" >SA_ID</th>
					<th data-options="field:'productcategory'" >商品范畴</th>
					<th data-options="field:'suretype'" >客户渠道</th>
					<th data-options="field:'businessmodel'" >业务模式</th>
					<th data-options="field:'producttype'" >贷款类型</th>
					<th data-options="field:'subProductType'">产品子类型</th>
					<th data-options="field:'province'">省份</th>
					<th data-options="field:'city'">城市</th>
					<th data-options="field:'citycode'">城市编码</th>
					<th data-options="field:'fundProviders'" formatter="companyNameFormatter">资金方</th>
					<th data-options="field:'contractlife'">合同轨迹</th>
					<th data-options="field:'cdate'">强制日期</th>
					<th data-options="field:'overduedays'">逾期天数</th>
					<th data-options="field:'payDate'">应还日期</th>
					<th data-options="field:'actualPayDate'">实还日期</th>
					<th data-options="field:'seqId'" >期数</th>
					<th data-options="field:'canceltype'" formatter="cancelTypeFormatter">是否取消分期期次</th>
					<th data-options="field:'transferDate'">转让日期</th>
					<th data-options="field:'dcDate'">代偿日期</th>
					<th data-options="field:'shDate'">赎回日期</th>
					<th data-options="field:'tbdate'">保险投保日</th>
					<th data-options="field:'lpdate'">保险理赔日</th>
					<th data-options="field:'debours'" formatter="companyNameFormatter">代垫方</th>
					<th data-options="field:'assetBelong'" formatter="companyNameFormatter">资产所属方</th>
					<th data-options="field:'guaranteeParty'" formatter="companyNameFormatter">保证方</th>
					<th data-options="field:'bxhxDate'" >本息核销日期</th>
					<th data-options="field:'fyhxDate'" >费用核销日期</th>
					<th data-options="field:'actualPayPrincipalAmt'" formatter="thousandthFormatter">实还本金</th>
					<th data-options="field:'actualPayinteAmt'" formatter="thousandthFormatter">实还利息</th>
					<th data-options="field:'a2'" formatter="thousandthFormatter">实还客户服务费</th>
					<th data-options="field:'a7'" formatter="thousandthFormatter">实还账户管理费</th>
					<th data-options="field:'a9'" formatter="thousandthFormatter">实还提前还款手续费</th>
					<th data-options="field:'a10'" formatter="thousandthFormatter">实还滞纳金</th>
					<th data-options="field:'a11'" formatter="thousandthFormatter">实还印花税</th>
					<th data-options="field:'a12'" formatter="thousandthFormatter">实还增值服务费</th>
					<th data-options="field:'a17'" formatter="thousandthFormatter">实还委外催收费</th>
					<th data-options="field:'a18'" formatter="thousandthFormatter">实还随心还服务费</th>
					<th data-options="field:'a19'" formatter="thousandthFormatter">实还委外费</th>
					<th data-options="field:'keepAccountsStatus'" formatter="formatStatus">状态</th>
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
				<tr>
					<th style="text-align: right; font-size: 13">合同号:</th>
					<td><input id="serialNo" name="serialNo" class="easyui-textbox" style="width:150px" data-options="editable:false"/></td>
				</tr>
				<tr style="line-height:15px;">
					<th style="text-align: right; font-size: 13">记账日期(起):</th>
					<td><input id="startKeepAccountsDate" name="startKeepAccountsDate" class="easyui-textbox" style="width:150px" data-options="editable:false"/></td>
				</tr>
				<tr style="line-height:10px;">
					<th style="text-align: right; font-size: 13">记账日期(止):</th>
					<td><input id="endKeepAccountsDate" name="endKeepAccountsDate" class="easyui-textbox" style="width:150px" data-options="editable:false"/></td>
				</tr>
				<tr style="line-height:15px;">
					<th style="text-align: right; font-size: 13">实还日期(起):</th>
					<td><input id="startShouldAlsoDate" name="startShouldAlsoDate" class="easyui-textbox" style="width:150px" data-options="editable:false"/></td>
				</tr>
				<tr style="line-height:10px;">
					<th style="text-align: right; font-size: 13">实还日期(止):</th>
					<td><input id="endShouldAlsoDate" name="endShouldAlsoDate" class="easyui-textbox" style="width:150px" data-options="editable:false"/></td>
				</tr>
				<tr style="line-height:10px;">
					<th style="text-align: right; font-size: 13">资产所属方:</th>
					<td>
						<input id="assetBelong" name="assetBelong" type="hidden" style="width:150px" data-options="editable:false"/>
						<input id="assetBelongShow" name="assetBelongShow" class="easyui-textbox" style="width:150px" data-options="editable:false"/>
					</td>
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
				<tr style="line-height:10px;">
					<th style="text-align: right; font-size: 13">资产所属方:</th>
					<td>
						<input id="assetBelong" name="assetBelong" type="hidden" style="width:150px" data-options="editable:false"/>
						<input id="assetBelongShow" name="assetBelongShow" class="easyui-textbox" style="width:150px" data-options="editable:false,required:true"/>
					</td>
				</tr>
				<tr style="line-height:15px;">
					<th style="text-align: right; font-size: 13">实还本金:</th>
					<td>
						<input id="actualPayPrincipalAmt" name="actualPayPrincipalAmt" type="hidden" style="width:150px" data-options="editable:false"/>
						<input id="keepDate" name="keepDate" type="hidden" style="width:150px" data-options="editable:false"/>
						<input id="actualPayPrincipalAmtSum" name="actualPayPrincipalAmtSum" class="easyui-textbox" style="width:150px" data-options="editable:false,required:true"/>
					</td>
				</tr>
				<tr style="line-height:10px;">
					<th style="text-align: right; font-size: 13">实还利息:</th>
					<td>
						<input id="actualPayinteAmt" name="actualPayinteAmt" type="hidden" style="width:150px" data-options="editable:false"/>
						<input id="actualPayinteAmtSum" name="actualPayinteAmtSum" class="easyui-textbox" style="width:150px" data-options="editable:false,required:true"/>
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
			</tbody>
		</table>
		<div id="EditTop" style="margin-top:10px">
			<a style="margin-left:50px" href="javascript:;" onClick = "selectAccountingMarkSubmit('onDBAccountingMarkdlg','onDBAccountingMarkForm')" class="easyui-linkbutton" data-options="iconCls:'icon-ok'">记账确认</a>
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
				<tr>
					<th style="text-align: right; font-size: 13">合同号:</th>
					<td><input id="serialNo" name="serialNo" class="easyui-textbox" style="width:150px" data-options="editable:false"/></td>
				</tr>
				<tr style="line-height:15px;">
					<th style="text-align: right; font-size: 13">记账日期(起):</th>
					<td><input id="startKeepAccountsDate" name="startKeepAccountsDate" class="easyui-textbox" style="width:150px" data-options="editable:false"/></td>
				</tr>
				<tr style="line-height:10px;">
					<th style="text-align: right; font-size: 13">记账日期(止):</th>
					<td><input id="endKeepAccountsDate" name="endKeepAccountsDate" class="easyui-textbox" style="width:150px" data-options="editable:false"/></td>
				</tr>
				<tr style="line-height:15px;">
					<th style="text-align: right; font-size: 13">实还日期(起):</th>
					<td><input id="startShouldAlsoDate" name="startShouldAlsoDate" class="easyui-textbox" style="width:150px" data-options="editable:false"/></td>
				</tr>
				<tr style="line-height:10px;">
					<th style="text-align: right; font-size: 13">实还日期(止):</th>
					<td><input id="endShouldAlsoDate" name="endShouldAlsoDate" class="easyui-textbox" style="width:150px" data-options="editable:false"/></td>
				</tr>
				<tr style="line-height:10px;">
					<th style="text-align: right; font-size: 13">资产所属方:</th>
					<td>
						<input id="assetBelong" name="assetBelong" type="hidden" style="width:150px" data-options="editable:false"/>
						<input id="assetBelongShow" name="assetBelongShow" class="easyui-textbox" style="width:150px" data-options="editable:false"/>
					</td>
				</tr>
			</tbody>
		</table>
		<div id="EditTop" style="margin-top:10px">
			<a style="margin-left:100px" href="javascript:;" onClick = "cancelAccountingMarkSubmit('cancelAccountingMarkdlg','cancelAccountingMarkForm')" class="easyui-linkbutton" data-options="iconCls:'icon-ok'">撤销</a>
	        <a href="javascript:;" onClick = "closeWin('cancelAccountingMarkdlg')" class="easyui-linkbutton" data-options="iconCls:'icon-cancel'">取消</a>  
		</div>
	</form>
</div>

<!-- 数据网格end -->


