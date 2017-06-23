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
<script type="text/javascript" src="${CONTEXT_PATH}/js/app/dayTrading.js?version=20170407" charset="utf-8"></script>

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
			<label style="margin-left:10px">交易日期:</label>
				 <input id="startTransDate" name="startTransDate" type="text" class="easyui-datebox" style="width: 100px"
						data-options="formatter:myformatter,parser:myparser" /> 至
				 <input	id="endTransDate" name="endTransDate" type="text" class="easyui-datebox"	style="width: 100px"
						data-options="formatter:myformatter,parser:myparser" />
			<label style="margin-left:20px">交易类型:</label> 
				 <select id="transType" name="transType" class="easyui-combobox textbox combobox-f combo-f textbox-f" style="width: 100px;">
					<option value="">---请选择---</option>
					<option value="zr">转让</option>
					<option value="dc">代偿</option>
					<option value="sh">赎回</option>
					<option value="lp">理赔</option>
					<option value="hk">还款</option>
					<option value="fk">放款</option>
					<option value="hb">划拨</option>
				</select>
			<label style="margin-left:22px">交易主体:</label> 
				<select id="belong" name="belong" class="easyui-combobox textbox combobox-f combo-f textbox-f" style="width: 125px;">
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
			<label style="margin-left:22px">数据源:</label> 
				<select id="dataSource" name="dataSource" class="easyui-combobox textbox combobox-f combo-f textbox-f" style="width: 125px;">
					<option value="">---请选择---</option>
					<option value="as">安硕</option>
					<option value="cd">车贷</option>
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
							    <a href="#"	class="easyui-linkbutton"	data-options="iconCls:'icon-save',plain:'true'" onclick="exportExcel('${CONTEXT_PATH}')" >数据导出</a>
							</div> <!-- 按钮 end -->
						</td>
					</tr>
				</table>
		</div>
		<table id="clearanceCertificateGrid"
			class="easyui-datagrid" title="日交易数据表"
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
				<th data-options="field:'transDate'">交易日期</th>
				<th data-options="field:'transType'" formatter="dealTypeFormatter">交易类型</th>
				<th data-options="field:'transCode'">交易流水</th>
				<th data-options="field:'belong'" formatter="companyNameFormatter">交易主体</th>
				<th data-options="field:'dataSource'" formatter="companyNameFormatter">数据源</th>
				<th data-options="field:'principalamt'" formatter="thousandthFormatter">本金收入</th>
				<th data-options="field:'inteamt'" formatter="thousandthFormatter">利息收入</th>
				<th data-options="field:'payPrincipalamt'" formatter="thousandthFormatter">应付本金</th>
				<th data-options="field:'receivePrincipalamt'" formatter="thousandthFormatter">应收本金</th>
				<th data-options="field:'payInteamt'" formatter="thousandthFormatter">应付利息</th>
				<th data-options="field:'receiveInteamt'" formatter="thousandthFormatter">应收利息</th>
				<th data-options="field:'payPureoverflowsum'" formatter="thousandthFormatter">应付纯溢价</th>
				<th data-options="field:'pureoverflowsum'" formatter="thousandthFormatter">应收纯溢价</th>
				<th data-options="field:'a2'" formatter="thousandthFormatter">客户服务费</th>
				<th data-options="field:'a7'" formatter="thousandthFormatter">账户管理费</th>
				<th data-options="field:'a9'" formatter="thousandthFormatter">提前还款手续费</th>
				<th data-options="field:'a10'" formatter="thousandthFormatter">滞纳金</th>
				<th data-options="field:'a11'" formatter="thousandthFormatter">印花税</th>
				<th data-options="field:'a12'" formatter="thousandthFormatter">增值服务费</th>
				<th data-options="field:'a17'" formatter="thousandthFormatter">委外催收费</th>
				<th data-options="field:'a18'" formatter="thousandthFormatter">随心还服务费</th>
				<th data-options="field:'a19'" formatter="thousandthFormatter">提前委外费</th>
				<th data-options="field:'a22'" formatter="thousandthFormatter">佰保袋服务费</th>
				<th data-options="field:'isCheck'">是否核对</th>
				<th data-options="field:'createTime'" >创建时间</th>
				</tr>
			</thead>
		</table>
	</div>
</div>
