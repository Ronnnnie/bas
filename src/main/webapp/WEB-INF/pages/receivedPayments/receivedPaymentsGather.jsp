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
		<form id="queryForm">
		<div style="margin-top:10px;margin-left:20px">
			<label>记账日期:</label>
				<input id="startKeepAccountsDate" type="text" class="easyui-datebox" style="width:110px;margin-top:50px;"
					data-options="formatter:myformatter,parser:myparser" /> 至
				<input id="endKeepAccountsDate" type="text" class="easyui-datebox"	style="width: 110px"
					data-options="formatter:myformatter,parser:myparser" />
			<label style="margin-left:10px">业务模式:</label>
				<select id="businessModel" class="easyui-combobox" name="capitalSide" style="width: 110px;">
					<option value=""></option>
					<option value="P2P">P2P</option>
					<option value="信托">信托</option>
					<option value="助贷">助贷</option>
				</select>
			<label style="margin-left:10px">贷款子类型:</label>
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
		</div>
		<div style="margin-top:10px;margin-left:20px">
			<label>实还日期:</label>
				<input id="startShouldAlsoDate" type="text" class="easyui-datebox" style="width:110px;margin-top:50px;"
						data-options="formatter:myformatter,parser:myparser" /> 至
				<input id="endShouldAlsoDate" type="text" class="easyui-datebox" style="width: 110px" data-options="formatter:myformatter,parser:myparser" />
			<label style="margin-left:10px">保证方:</label>
			<select id="guaranteeParty" class="easyui-combobox" name="guaranteeParty" style="width: 150px;">
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
			<label style="margin-left:10px">是否取消分期期次:</label>
				<select id=cancelType class="easyui-combobox" name="cancelType" style="width: 110px;">
					<option value="">请选择</option>
					<option value="Y">是</option>
					<option value="N">否</option>
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
							<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-save',plain:'true'" onclick="ExportExcel('${CONTEXT_PATH}')">数据导出</a>
						</div> <!-- 按钮 end -->
					</td>
				</tr>
			</table>
		</div>
		<table id="clearanceCertificateGrid"
			class="easyui-datagrid" title="合同实还汇总表"
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
					<th data-options="field:'keepAccountsDate'">记账日期</th>
					<th data-options="field:'subProductType'">产品子类型</th>
					<th data-options="field:'city'">城市</th>
					<th data-options="field:'assetBelong'" formatter="companyNameFormatter">资产所属方</th>
					<th data-options="field:'creditperson'" formatter="companyNameFormatter">资金方</th>
					<th data-options="field:'guaranteeParty'" formatter="companyNameFormatter">保证方</th>
					<th data-options="field:'actualPayPrincipalAmt'" formatter="thousandthFormatter">实还本金</th>
					<th data-options="field:'actualPayinteAmt'" formatter="thousandthFormatter">实还利息</th>
					<th data-options="field:'bxhxDate'" >本息核销日期</th>
					<th data-options="field:'fyhxDate'" >费用核销日期</th>
					<th data-options="field:'a2'" formatter="thousandthFormatter">实还客户服务费</th>
					<th data-options="field:'a7'" formatter="thousandthFormatter">实还账户管理费</th>
					<th data-options="field:'a9'" formatter="thousandthFormatter">实还提前还款手续费</th>
					<th data-options="field:'a10'" formatter="thousandthFormatter">实还滞纳金</th>
					<th data-options="field:'a11'" formatter="thousandthFormatter">实还印花税</th>
					<th data-options="field:'a12'" formatter="thousandthFormatter">实还增值服务费</th>
					<th data-options="field:'a17'" formatter="thousandthFormatter">实还委外催收费</th>
					<th data-options="field:'a18'" formatter="thousandthFormatter">实还随心还服务费</th>
					<th data-options="field:'a19'" formatter="thousandthFormatter">实还委外费</th>
				</tr>
			</thead>
		</table>
	</div>
</div>


<script type="text/javascript">
	function query() {
		var startKeepAccountsDate = $("#startKeepAccountsDate").datebox('getValue');
		var endKeepAccountsDate = $("#endKeepAccountsDate").datebox('getValue');
		var businessModel = $("#businessModel").combobox('getValue');
		var subProductType = $("#subProductType").combobox('getValue');
		var assetBelong = $("#assetBelong").combobox('getValue');
		var guaranteeParty = $("#guaranteeParty").combobox('getValue');
		var city = $("#city").textbox('getValue');
		var cancelType = $("#cancelType").combobox('getValue');
		var startShouldAlsoDate = $("#startShouldAlsoDate").datebox('getValue');
		var endShouldAlsoDate = $("#endShouldAlsoDate").datebox('getValue');
		var today = getDate();
		if(startKeepAccountsDate <= today && endKeepAccountsDate <= today){
			$("#clearanceCertificateGrid").datagrid('options').url = contextPath + "/receivedPaymentsServer/queryReceivedPaymentsGather.do";
			$("#clearanceCertificateGrid").datagrid('load',{
				startKeepAccountsDate : startKeepAccountsDate,
				endKeepAccountsDate : endKeepAccountsDate,
				startShouldAlsoDate : startShouldAlsoDate,
				endShouldAlsoDate : endShouldAlsoDate,
				businessModel : businessModel,
				subProductType : subProductType,
				assetBelong : assetBelong,
				guaranteeParty : guaranteeParty,
				city : city,
				cancelType : cancelType
			});
			var p = $('#clearanceCertificateGrid').datagrid('getPager'); 
			    $(p).pagination({ 
			        pageSize: 10,//每页显示的记录条数，默认为10 
			        pageList: [5,10,15],//可以设置每页记录条数的列表 
			        beforePageText: '第',//页数文本框前显示的汉字 
			        afterPageText: '页    共 {pages} 页', 
			        displayMsg: '当前显示 {from} - {to} 条记录   共 {total} 条记录' 
			}); 
			
			$.ajax({
				url : contextPath + "/receivedPaymentsServer/queryReceivedPaymentsGather.do",
				type : "POST",
				data : {
					rows: 10,
					page:1,
					startKeepAccountsDate : startKeepAccountsDate,
					endKeepAccountsDate : endKeepAccountsDate,
					startShouldAlsoDate : startShouldAlsoDate,
					endShouldAlsoDate : endShouldAlsoDate,
					businessModel : businessModel,
					subProductType : subProductType,
					assetBelong : assetBelong,
					guaranteeParty : guaranteeParty,
					city : city,
					cancelType : cancelType
				},
				success : function(data) {
					var jsondata = eval("(" + data + ")");
					//是否有数据
					if (jsondata.rows == "") {
						$.messager.alert('提示', '暂无匹配数据!请重新搜索..', 'warning');
						//清空表单数据
						$('#clearanceCertificateGrid').datagrid('loadData', {
							total : 0,
							rows : []
						});
						$('#clearanceCertificateGrid').datagrid('reloadFooter',[{
							keepAccountsDate : '<span style="color:red;">合计</span>',
							actualPayPrincipalAmt : '0.00',
							actualPayinteAmt : '0.00',
							a2 : '0.00',
							a7 : '0.00',
							a9 : '0.00',
							a10 : '0.00',
							a11 : '0.00',
							a12 : '0.00',
							a17 : '0.00',
							a18 : '0.00'
						}]);
						return;
					}else{
						$('#clearanceCertificateGrid').datagrid('reloadFooter',[{
							keepAccountsDate : '<span style="color:red;">合计</span>',
							actualPayPrincipalAmt : jsondata.rows[0].actualPayPrincipalAmtSum,
							actualPayinteAmt : jsondata.rows[0].actualPayinteAmtSum,
							a2 : jsondata.rows[0].a2Sum,
							a7 : jsondata.rows[0].a7Sum,
							a9 : jsondata.rows[0].a9Sum,
							a10 : jsondata.rows[0].a10Sum,
							a11 : jsondata.rows[0].a11Sum,
							a12 : jsondata.rows[0].a12Sum,
							a17 : jsondata.rows[0].a17Sum,
							a18 : jsondata.rows[0].a18Sum
						}]);
					}
				},
				error : function() {
					$.messager.alert("操作提示", data.message, "error");
				}
			});
		}else{
			$.messager.alert("操作提示", '选择日期不能大于当前日期,请重新选择!', "error");
		}
	}
	
function ExportExcel(path){
		
	var startKeepAccountsDate = $("#startKeepAccountsDate").datebox('getValue');
	var endKeepAccountsDate = $("#endKeepAccountsDate").datebox('getValue');
	var businessModel = $("#businessModel").combobox('getValue');
	var subProductType = $("#subProductType").combobox('getValue');
	var assetBelong = $("#assetBelong").combobox('getValue');
	var guaranteeParty = $("#guaranteeParty").combobox('getValue');
	var city = $("#city").textbox('getValue');
	var cancelType = $("#cancelType").combobox('getValue');
	var startShouldAlsoDate = $("#startShouldAlsoDate").datebox('getValue');
	var endShouldAlsoDate = $("#endShouldAlsoDate").datebox('getValue');
		$.ajax({
			url : contextPath + "/receivedPaymentsServer/queryReceivedPaymentsGatherCount.do",
			type : "POST",
			data : {
				startKeepAccountsDate : startKeepAccountsDate,
				endKeepAccountsDate : endKeepAccountsDate,
				businessModel : businessModel,
				subProductType : subProductType,
				assetBelong : assetBelong,
				guaranteeParty : guaranteeParty,
				city : city,
				cancelType : cancelType,
				startShouldAlsoDate : startShouldAlsoDate,
				endShouldAlsoDate : endShouldAlsoDate
			},
			success : function(data) {
				var jsondata = eval("(" + data + ")");
				//是否有数据
				if (jsondata.contractCount < 500000) {
					window.location.href=path+'/receivedPaymentsServer/gatherDateExcelExport.do?startKeepAccountsDate='+startKeepAccountsDate+
							'&endKeepAccountsDate='+endKeepAccountsDate+"&businessModel="+businessModel+"&subProductType="+subProductType+"&assetBelong="+assetBelong+
							"&guaranteeParty="+guaranteeParty+"&city="+city+"&cancelType="+cancelType+"&startShouldAlsoDate="+startShouldAlsoDate+"&endShouldAlsoDate="+endShouldAlsoDate;
				}else{
					$.messager.alert('提示', '数据量过大!请更换条件...', 'warning');
				}
			},
			error : function() {
				
			}
		});
	}
	
	function checkMoney(money){
		if(money == null){
			return '0.00';
		}else if(money == 0){
			return '0.00';
		}
		return money;
	}
</script>
