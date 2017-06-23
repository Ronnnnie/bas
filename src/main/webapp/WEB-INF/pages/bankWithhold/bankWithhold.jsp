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
<script type="text/javascript" src="${CONTEXT_PATH}/js/app/bankWithhold.js?version=20170525" charset="utf-8"></script>

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
			<label style="margin-left:10px">业务日期:</label>
				 <input id="startInputDate" name="startInputDate" type="text" class="easyui-datebox" style="width: 100px"
						data-options="formatter:myformatter,parser:myparser" /> 至
				 <input	id="endInputDate" name="endInputDate" type="text" class="easyui-datebox"	style="width: 100px"
						data-options="formatter:myformatter,parser:myparser" />
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
							<a href="#"	class="easyui-linkbutton"	data-options="iconCls:'icon-save',plain:'true'" onclick="exportExcel('${CONTEXT_PATH}')" >数据导出</a>
						</div> <!-- 按钮 end -->
					</td>
				</tr>
			</table>
		</div>
		<table id="clearanceCertificateGrid"
			class="easyui-datagrid" title="每日银行代扣对账表"
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
				<th data-options="field:'inputdate'">业务日期</th>
				<th data-options="field:'dep_bod'" formatter="thousandthFormatter">日初预存款</th>
				<th data-options="field:'unmatch_bod'"formatter="thousandthFormatter">日初未匹配</th>
				<th data-options="field:'bank_bank'" formatter="thousandthFormatter">银企直连网银</th>
				<th data-options="field:'bank_core'" formatter="thousandthFormatter">银企直连安硕</th>
				<th data-options="field:'bank_dif'" formatter="thousandthFormatter">银企直连差异</th>
				<th data-options="field:'bank_match'" formatter="thousandthFormatter">银企直连已匹配</th>
				<th data-options="field:'bank_unmatch'" formatter="thousandthFormatter">银企直连未匹配</th>
				<th data-options="field:'bank_roll_hand'" formatter="thousandthFormatter">手工分离</th>
				<th data-options="field:'bank_match_hand'" formatter="thousandthFormatter">手工匹配</th>
				<th data-options="field:'unmatch_eod'" formatter="thousandthFormatter">日终未匹配</th>
				<th data-options="field:'ebu_bank'" formatter="thousandthFormatter">易办事网银</th>
				<th data-options="field:'ebu_core1'" formatter="thousandthFormatter">易办事安硕1</th>
				<th data-options="field:'ebu_core'" formatter="thousandthFormatter">易办事安硕</th>
				<th data-options="field:'ebu_dif'" formatter="thousandthFormatter">易办事差异</th>
				<th data-options="field:'kft_bank'" formatter="thousandthFormatter">快付通网银</th>
				<th data-options="field:'kft_core'" formatter="thousandthFormatter">快付通安硕</th>
				<th data-options="field:'kft_dif'" formatter="thousandthFormatter">快付通差异</th>
				<th data-options="field:'hbk_bank'" formatter="thousandthFormatter">哈行网银</th>
				<th data-options="field:'hbk_core'" formatter="thousandthFormatter">哈行安硕</th>
				<th data-options="field:'hbk_dif'" formatter="thousandthFormatter">哈行差异</th>
				<th data-options="field:'kfts_bank'" formatter="thousandthFormatter">快付通实时网银</th>
				<th data-options="field:'kfts_core'" formatter="thousandthFormatter">快付通实时安硕</th>
				<th data-options="field:'kfts_dif'" formatter="thousandthFormatter">快付通实时差异</th>
				<th data-options="field:'cft_bank'" formatter="thousandthFormatter">微信支付网银</th>
				<th data-options="field:'check_cft_core'" formatter="thousandthFormatter">微信支付安硕后移一天</th>
				<th data-options="field:'cft_core'" formatter="thousandthFormatter">微信支付安硕</th>
				<th data-options="field:'cft_core_fee'"  formatter="thousandthFormatter">微信支付手续费</th>
				<th data-options="field:'cft_dif'" formatter="thousandthFormatter">微信支付差异</th>
				<th data-options="field:'refund'" formatter="thousandthFormatter">退款</th>
				<th data-options="field:'pay'" formatter="thousandthFormatter">普通还款</th>
				<th data-options="field:'prepay'" formatter="thousandthFormatter">提前还款</th>
				<th data-options="field:'va_amt'" formatter="thousandthFormatter">虚拟入账</th>
				<th data-options="field:'dep_eod'" formatter="thousandthFormatter">日终预存款</th>
				<th data-options="field:'unmatch_dif'" formatter="thousandthFormatter">未匹配差异</th>
				<th data-options="field:'dep_dif'" formatter="thousandthFormatter">预存款差异</th>
				<th data-options="field:'loan_rep'" formatter="thousandthFormatter">冲还款</th>
				</tr>
			</thead>
		</table>
	</div>
</div>
