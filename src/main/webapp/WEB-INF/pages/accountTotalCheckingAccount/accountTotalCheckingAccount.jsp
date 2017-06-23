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
<script type="text/javascript" src="${CONTEXT_PATH}/js/app/util.js?version=20170518.1" charset="utf-8"></script>
<script type="text/javascript" src="${CONTEXT_PATH}/js/app/accountTotalCheckingAccount.js?t=20170524" charset="utf-8"></script>

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
			<label style="margin-left:10px">对账日期:</label>
				 <input id="startcheckdate" name="startcheckdate" type="text" class="easyui-datebox" style="width: 100px"
						data-options="formatter:myformatter,parser:myparser" /> 至
				 <input	id="endcheckdate" name="endcheckdate" type="text" class="easyui-datebox"	style="width: 100px"
						data-options="formatter:myformatter,parser:myparser" />
			
			<label style="margin-left:22px">数据源:</label> 
				<select id="datasource" name="datasource" class="easyui-combobox textbox combobox-f combo-f textbox-f" style="width: 125px;">
					<option value="">---请选择---</option>
					<option value="CFC">哈消金-资方</option>
					<option value="AS_HXJ">佰仟哈消金</option>
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
			class="easyui-datagrid" title="总账对账表"
			style="width: 99%; height: 100%;"
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
				<th data-options="field:'checknumber'">对账编号</th>
				<th data-options="field:'checkdate'" formatter="dealTypeFormatter">对账日期</th>
				<th data-options="field:'normal_withhold_back'" formatter="thousandthFormatter">当日正常代扣回盘总金额</th>
				<th data-options="field:'pre_withhold_back'" formatter="thousandthFormatter">当日提前还款代扣回盘总金额</th>
				<th data-options="field:'temp_withhold_back'" formatter="thousandthFormatter">当日临时代扣回盘总金额</th>
				<th data-options="field:'temp_withhold_back_hang'" formatter="thousandthFormatter">当日临时代扣回盘挂账总金额</th>
				<th data-options="field:'active_deposit'" formatter="thousandthFormatter">当日主动存款总金额</th>
				<th data-options="field:'push_total'" formatter="thousandthFormatter">当日推送金额</th>
				<th data-options="field:'active_deposit_hang'" formatter="thousandthFormatter">当日主动存款挂账总金额</th>
				<th data-options="field:'tm1_deposit_balance'" formatter="thousandthFormatter">T－1日预存款余额</th>
				<th data-options="field:'today_deposit_balance'" formatter="thousandthFormatter">当日预存款余额</th>
				<th data-options="field:'datasource'" formatter="companyNameFormatter">数据来源</th>
				</tr>
			</thead>
		</table>
	</div>
</div>
