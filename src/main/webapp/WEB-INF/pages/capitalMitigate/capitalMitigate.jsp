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
<script type="text/javascript" src="${CONTEXT_PATH}/js/app/capitalMitigate.js" charset="utf-8"></script>

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
		<div style="margin-top:8px">
			<label style="margin-left: 10px">合同号:</label>
				<input id="contractNo" name="contractNo" type="text" class="easyui-textbox" style="width:110px;"/>
			<label style="margin-left: 20px">应还日期:</label>
				<input id="startPayDate" name="startPayDate" type="text" class="easyui-datebox" style="width:110px;"
					data-options="formatter:myformatter,parser:myparser" />至
				<input id="endPayDate" type="text" class="easyui-datebox"	style="width: 110px"
					data-options="formatter:myformatter,parser:myparser" />
			<a style="margin-left:20px;height:23px" id="button-search" class="easyui-linkbutton" data-options="iconCls:'icon-search'" onclick="query()" >搜索</a>
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
							<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-save',plain:'true'" onclick="dataExport()">数据导出</a>
						</div>
					</td>
				</tr>
			</table>
		</div>
		<table id="clearanceCertificateGrid"
			class="easyui-datagrid" title="应付资方减免表"
			style="width:100%; height: 95%;"
			data-options="
					id:'dg-dopts',
					rownumbers:true,
					loadMsg:'数据加载中,请稍等',
					pagination:true,
					fitColumns:true,
					singleSelect: true,
					toolbar: '#tb',
					pageList : [ 10, 20, 50, 100 ],
					data:{'total':0,'rows':[]},
					showFooter:true,
					method: 'post'
				">
			<thead>
				<tr>
					<th data-options="field:'contractNo'">合同号</th>
					<th data-options="field:'registrationDate'">注册日</th>
					<th data-options="field:'sno'">门店代码</th>
					<th data-options="field:'rno'">商户代码</th>
					<th data-options="field:'sa_id'">SA_ID</th>
					<th data-options="field:'suretype'">商品范畴</th>
					<th data-options="field:'businessmodel'">业务模式</th>
					<th data-options="field:'businessType'">贷款类型</th>
					<th data-options="field:'subProductType'">贷款子类型</th>
					<th data-options="field:'province'">省份</th>
					<th data-options="field:'city'">城市</th>
					<th data-options="field:'cityCode'">城市编码</th>
					<th data-options="field:'creditPersion'" formatter="companyNameFormatter">资金来源</th>
					<th data-options="field:'payDate'">应还日期</th>
					<th data-options="field:'seqId'">期次</th>
					<th data-options="field:'deliveryDay'">转让日期</th>
					<th data-options="field:'dcDate'">代偿日期</th>
					<th data-options="field:'shDate'">赎回日期</th>
					<th data-options="field:'lpDate'">理赔日期</th>
					<th data-options="field:'debours'" formatter="companyNameFormatter">代垫方</th>
					<th data-options="field:'assetBelong'" formatter="companyNameFormatter">资产所属方</th>
					<th data-options="field:'guaranteeParty'" formatter="companyNameFormatter">保证方</th>
					<th data-options="field:'waiveDate'">减免日期</th>
					<th data-options="field:'businessSum'" formatter="thousandthFormatter" width="120px">贷款本金</th>
					<th data-options="field:'isp2p'" formatter="isp2pFormatter">是否P2P</th>
					<th data-options="field:'waivetype'" formatter="waiveTypeFormatter">减免类型</th>
					<th data-options="field:'payprinciPalamt'" formatter="thousandthFormatter" width="120px">本金</th>
					<th data-options="field:'payInteamt'" formatter="thousandthFormatter" width="120px">减免利息</th>
				</tr>
			</thead>
		</table>
	</div>
</div>


