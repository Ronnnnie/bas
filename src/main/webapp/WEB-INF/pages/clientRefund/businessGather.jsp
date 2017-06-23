<%@ page pageEncoding="utf-8"%>
<%
  String _contextPath = request.getContextPath();
  request.setAttribute("CONTEXT_PATH", _contextPath);
  
  String _basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+_contextPath+"/";
  request.setAttribute("BASE_PATH", _basePath);
%>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<link rel="shortcut" href="<%=_contextPath%>/images/icon/logo.ico" type="image/icon" />
<link rel="icon" href="<%=_contextPath%>/images/icon/logo.ico" type="image/icon" />

<script  type="text/javascript" src="${CONTEXT_PATH}/js/jquery-easyui-1.4.2/jquery.min.js" charset="utf-8"></script>  
<!--(指定编码方式，防止出现乱码)引入EasyUI中使用的Jquery版本-->
<script  type="text/javascript" src="${CONTEXT_PATH}/js/jquery-easyui-1.4.2/jquery.easyui.min.js" charset="utf-8"></script> 
  <!--(指定编码方式，防止出现乱码)引入EasyUi文件-->
<script  type="text/javascript" src="${CONTEXT_PATH}/js/jquery-easyui-1.4.2/locale/easyui-lang-zh_CN.js"></script>  <!--汉化-->

<script  type="text/javascript" src="${CONTEXT_PATH}/js/app/include.js" charset="utf-8"></script>
<script  type="text/javascript" src="${CONTEXT_PATH}/js/app/loading.js" charset="utf-8"></script>
<script type="text/javascript" src="${CONTEXT_PATH}/js/app/util.js" charset="utf-8"></script> 

<link rel="stylesheet" type="text/css" href="${CONTEXT_PATH}/js/jquery-easyui-1.4.2/themes/bootstrap/easyui.css">   <!--引入CSS样式-->
<link rel="stylesheet" type="text/css" href="${CONTEXT_PATH}/js/jquery-easyui-1.4.2/themes/icon.css">   <!--Icon引入-->
<style type="text/css">
	.formTable{padding:1px;}
	.formTable th{text-align:right;width:90px;padding:5px 0px 5px 0;}
    .formTable td{padding:5px 0 5px 0px;}
	.formTable samp{vertical-align:middle;color:#f00;}
	.datagrid-cell{line-height:28px}
	.datagrid-cell-rownumber{height:28px}
	
</style>
<!-- 缓存用户是否是超级管理员 -->
<input type="hidden" id="isSuperAdmin" value="${isSuperAdmin}">
<!-- 数据网格 -->
	<div class="easyui-layout" data-options="fit:true" style="width:100%;height:100%">
		<div data-options="region:'north'" title="" style="width:100%;height: 60px;"> 
			<form id="queryForm">
			<div style="width:90%;margin: 5px;">
				<label style="margin-left:0px">记账日期:</label>
					<input id="startKeepAccountsDate" type="text" class="easyui-datebox" style="width: 100px"
						data-options="formatter:myformatter,parser:myparser" />至
					<input id="endKeepAccountsDate" type="text" class="easyui-datebox"	style="width: 100px"
						data-options="formatter:myformatter,parser:myparser" />
				<label	style="margin-left:20px">产品子类型 :</label>
					 <select id="productSubType" class="easyui-combobox textbox combobox-f combo-f textbox-f" name="productSubType" style="width: 100px;">
						<option value=""></option>
						<option value="POS">POS</option>
						<option value="信贷">信贷</option>
						<option value="车贷">车贷</option>
					</select>
				<label style="margin-left: 32px;">城市:</label>
					<input id="city" type="text" class="easyui-textbox" style="width: 100px; margin-top: 5px"><br/>
				<label	style="margin-left:0px">付款日期:</label>
				 	 <input	id="startPayDate" type="text" class="easyui-datebox" style="width: 100px"
							data-options="formatter:myformatter,parser:myparser">至
					 <input	id="endPayDate" type="text" class="easyui-datebox"	style="width: 100px"
							data-options="formatter:myformatter,parser:myparser" />
				<label style="margin-left: 50px;">资金方:</label>
					<select id="capitalSide" class="easyui-combobox" name="capitalSide" style="width: 100px;">
						<option value=""></option>
						<option value="中泰">中泰</option>
						<option value="招行">招行</option>
						<option value="佰仟">佰仟</option>
					</select>
				<a style="margin-left: 67px" id="button-search" href="javascript:;"	onclick="query()" class="easyui-linkbutton"	data-options="iconCls:'icon-search'">搜索</a>
				<a style="margin-left: 65px" id="button-search" href="javascript:;"	onclick="clearForm('queryForm')" class="easyui-linkbutton"	data-options="iconCls:'icon-reload'">重置</a>
			</div>
			</form>
		</div>
			
		<div data-options="region:'center'">
		<div id="tb" style="margin-left：100px">	
		      <table style="width:100%;height:10px;border-left:none;border-bottom:none;border-right:none;" >
		   		<tr>
		   			<td>
		   				<!-- 按钮 start -->
					    <div id="tb" style="margin-left:20px">
							<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-tip',plain:'true'">Tips</a>
							<label><font size='2' color='red'> 
									共计匹配合同：<input class="easyui-textbox" style="width: 50px" id="contractCount" name="contractCount" />条 
									共计放款本金：<input class="easyui-textbox" style="width: 80px" id="moneyCount" name="moneyCount" />元</font>
							</label>
						</div>
						<!-- 按钮 end -->
		   			</td>
		   		</tr>
		    </table>
		  </div>
		  <table id="clearanceCertificateGrid" class="easyui-datagrid" title="放款表业务汇总" style="width:100%;height:425px;"
			data-options="
					id:'dg-dopts',
					rownumbers:true,
					loadMsg:'数据加载中,请稍等',
					pagination:true,
					fitColumns:true,
					singleSelect: true,
					toolbar: '#tb',
					pageList : [ 10, 20, 50, 100 ],
					method: 'post'
				">
			<thead>
				<tr>
					<th data-options="field:'id',checkbox:true"></th>
					<th data-options="field:'keepAccountsDate'" width="80px">记账日期</th>
					<th data-options="field:'payDate'" width="80px">付款日期</th>
					<th data-options="field:'productSubType'" width="80px">产品子类型</th>
					<th data-options="field:'capitalSide'" width="60px">资金方</th>
					<th data-options="field:'businessSum'" width="60px">放款本金</th>
					<th data-options="field:'deductionKHServiceFee'" width="80px">扣减客户服务费</th>
					<th data-options="field:'deductionSHServiceFee'" width="80px">扣减商户服务费</th>
					<th data-options="field:'paySum'" width="50px">支付金额</th>
					<th data-options="field:'city'" width="50px">城市</th>
				</tr>
			</thead>
		</table>
	    </div>
	</div>
	
<!-- 数据网格end -->
<script type="text/javascript">
	function query() {
		var startKeepAccountsDate = $("#startKeepAccountsDate").datebox('getValue');
		var endKeepAccountsDate = $("#endKeepAccountsDate").datebox('getValue');
		var startPayDate = $("#startPayDate").datebox('getValue');
		var endPayDate = $("#endPayDate").datebox('getValue');
		var city = $("#city").val();
		var capitalSide = $("#capitalSide").combobox('getValue');
		var productSubType = $("#productSubType").combobox('getValue');
		$.ajax({
			url : contextPath + "/clientRefundServer/queryBusinessDetail.do",
			type : "POST",
			data : {
				rows: 10,
				page:1,
				startKeepAccountsDate : startKeepAccountsDate,
				endKeepAccountsDate : endKeepAccountsDate,
				startPayDate : startPayDate,
				endPayDate : endPayDate,
				city : city,
				capitalSide : capitalSide,
				productSubType : productSubType
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
					$("#contractCount").textbox('setValue',
 							"0");
 					$("#moneyCount").textbox('setValue', 
 							"0.0");
				}
				//重新给控件加载数据
  				if (jsondata.rows[0].contractCount != 0
 						&& jsondata.rows[0].moneyCount != null) {
 					$("#contractCount").textbox('setValue',
 							jsondata.rows[0].contractCount);
 					$("#moneyCount")
 							.textbox('setValue', jsondata.rows[0].moneyCount);
 				} 
				//重新给表格加载数据
				$("#clearanceCertificateGrid").datagrid('loadExt',{
					startKeepAccountsDate : startKeepAccountsDate,
					endKeepAccountsDate : endKeepAccountsDate,
					startPayDate : startPayDate,
					endPayDate : endPayDate,
					city : city,
					capitalSide : capitalSide,
					productSubType : productSubType
				});
				$("#clearanceCertificateGrid").datagrid({
					url : contextPath + "/clientRefundServer/queryBusinessDetail.do"
				}); 
				$("#clearanceCertificateGrid").datagrid('loadData', jsondata);
				var p = $('#clearanceCertificateGrid').datagrid('getPager'); 
				    $(p).pagination({ 
				        pageSize: 10,//每页显示的记录条数，默认为10 
				        pageList: [5,10,15],//可以设置每页记录条数的列表 
				        beforePageText: '第',//页数文本框前显示的汉字 
				        afterPageText: '页    共 {pages} 页', 
				        displayMsg: '当前显示 {from} - {to} 条记录   共 {total} 条记录' 
				}); 
			},
			error : function() {
				$.messager.alert("操作提示", data.message, "error");
			}
		});
	}
	</script>