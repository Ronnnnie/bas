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
<script type="text/javascript" src="${CONTEXT_PATH}/js/app/overdueGather.js" charset="utf-8"></script>

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
		<form id="queryForm">
		<div style="width: 97%; margin-top:7px;margin-left:20px">
		<label>记账日期:</label>
			<input id="startInAccountDate" type="text" class="easyui-datebox" style="width:110px;margin-top:50px;"
					data-options="formatter:myformatter,parser:myparser" />至
			<input id="endInAccountDate" type="text" class="easyui-datebox"	style="width: 110px"
					data-options="formatter:myformatter,parser:myparser" />
			<label style="margin-left:10px">业务模式:</label>
				<select id="businessModel" class="easyui-combobox" name="capitalSide" style="width: 150px;">
					<option value=""></option>
					<option value="P2P">P2P</option>
					<option value="助贷">助贷</option>
					<option value="信托">信托</option>
				</select>
			<label style="margin-left:10px">产品子类型:</label>
				<select id="subProductType" class="easyui-combobox" name="capitalSide" style="width: 120px;">
					<option value=""></option>
					<option value="普通POS贷">普通POS贷</option>
					<option value="预约现金贷">预约现金贷</option>
					<option value="无预约现金贷">无预约现金贷</option>
					<option value="车主现金贷">车主现金贷</option>
					<option value="成人教育贷">成人教育贷</option>
					<option value="学生教育贷">学生教育贷</option>
					<option value="小企业贷商户通">小企业贷商户通</option>
					<option value="学生消费贷">学生消费贷</option>
				</select>
			<label>城市:</label>
			<input id="city" type="text" class="easyui-textbox" style="width:100px;margin-top:50px;"/>
			<label style="margin-left: 10px">逾期描述:</label>
			<select id="overdueremark" class="easyui-combobox textbox combobox-f combo-f textbox-f" name="overdueremark" style="width: 100px;">
				<option value="">---请选择---</option>
				<option value="1个月">1个月</option>
				<option value="2个月">2个月</option>
				<option value="3个月">3个月</option>
				<option value="4个月">4个月</option>
				<option value="5个月">5个月</option>
				<option value="6个月">6个月</option>
				<option value="7个月">7个月</option>
				<option value="8个月">8个月</option>
				<option value="9个月">9个月</option>
				<option value="10个月">10个月</option>
				<option value="11个月">11个月</option>
				<option value="12个月">12个月</option>
				<option value="2年">2年</option>
				<option value="3年">3年</option>
				<option value="3年以上">3年以上</option>
			</select>
		</div>
		<div style="width: 97%; margin-top:7px;margin-left:20px">
			<label>五级分类:</label>
			<select id="classfy" class="easyui-combobox textbox combobox-f combo-f textbox-f" name="classfy" style="width: 100px;">
				<option value="">---请选择---</option>
				<option value="A">A</option>
				<option value="B">B</option>
				<option value="C">C</option>
				<option value="D">D</option>
				<option value="E">E</option>
			</select>
			<label style="margin-left: 10px">是否取消分期期次:</label>
			<select id="canceltype" class="easyui-combobox textbox combobox-f combo-f textbox-f" name="canceltype" style="width: 100px;">
				<option value="">---请选择---</option>
				<option value="Y">是</option>
				<option value="N">否</option>
			</select>
			<label style="margin-left:21px">资产所属方:</label>
			<select id="assetBelong" class="easyui-combobox" name="assetBelong" style="width: 150px;">
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
			<label style="margin-left:10px">保证方:</label>
			<select id="guaranteeParty" class="easyui-combobox" name="capitalSide" style="width: 150px;">
				<option value="">---请选择---</option>
				<option value="BQJR">佰仟金融</option>
				<option value="GZBC">贵州佰诚</option>
				<option value="RMBX">中国人民财产保险</option>
				<option value="HBYH">哈尔滨银行</option>
				<option value="JSZB">嘉实资本</option>
				<option value="HKYH">海口农商行</option>
				<option value="ZXXT">中信信托</option>
				<option value="ZTXT">中泰信托</option>
				<option value="ZHXT">中航信托</option>
				<option value="JYPH">嘉银普惠</option>
				<option value="RXCH">宝安融兴村行</option>
			</select>
			<a style="margin-left:17px;height:23px" id="button-search" class="easyui-linkbutton" data-options="iconCls:'icon-search'" onclick="query()" >搜索</a>
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
						    <a onclick="ExportExcel()" class="easyui-linkbutton" data-options="iconCls:'icon-save',plain:'true'">数据导出</a>
						</div> <!-- 按钮 end -->
					</td>
				</tr>
			</table>
		</div>
		<table id="clearanceCertificateGrid"
			class="easyui-datagrid" title="逾期表汇总"
			style="width:100%; height: 96%;"
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
					<th data-options="field:'id',checkbox:true"></th>
					<th data-options="field:'inAccountDate'">记账日期</th>
					<th data-options="field:'shouldAlsoDate'">应还日期</th>
					<th data-options="field:'accordDate'">统计日期</th>
					<th data-options="field:'sureType'">客户渠道</th>
					<th data-options="field:'businessModel'">业务模式</th>
					<th data-options="field:'subProductType'">贷款子类型</th>
					<th data-options="field:'province'">省份</th>
					<th data-options="field:'city'">城市</th>
					<th data-options="field:'cityCode'">城市编码</th>
					<th data-options="field:'overdueDays'">逾期天数</th>
					<th data-options="field:'overdueRemark'">逾期描述</th>
					<th data-options="field:'classFy'">五级分类</th>
					<th data-options="field:'canceltype'" formatter="cancelTypeFormatter">是否取消分期期次</th>
					<th data-options="field:'assetBelong'" formatter="companyNameFormatter">资产方</th>
					<th data-options="field:'guaranteeParty'" formatter="companyNameFormatter">保证方</th>
					<th data-options="field:'debours'" formatter="companyNameFormatter">代垫方</th>
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
				</tr>
			</thead>
		</table>
	</div>
</div>
