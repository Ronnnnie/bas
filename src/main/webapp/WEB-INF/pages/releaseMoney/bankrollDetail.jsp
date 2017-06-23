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

<script type="text/javascript" src="${CONTEXT_PATH}/js/app/include.js" charset="utf-8"></script>  
<script type="text/javascript" src="${CONTEXT_PATH}/js/app/loading.js" charset="utf-8"></script>
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
	<div class="easyui-layout" data-options="fit:true" style="width:100%;height:98%">
		<div data-options="region:'north'" title="" style="width:100%;height: 72px;"> 
			<form id="queryForm">
				<div style="width:90%;margin: 5px;">
			<label>合同号:</label> 
				<input class="easyui-textbox" style="width: 100px" id="serialNo" name="serialNo" /> 
			<label style="margin-left: 20px">注册日期:</label>
				 <input id="startBusinessDate" type="text" class="easyui-datebox" style="width: 100px"
						data-options="formatter:myformatter,parser:myparser" />至
				 <input	id="endBusinessDate" type="text" class="easyui-datebox"	style="width: 100px"
						data-options="formatter:myformatter,parser:myparser" />
			<label	style="margin-left: 20px">产品子类型 :</label>
				 <select id="productSubType" class="easyui-combobox textbox combobox-f combo-f textbox-f" name="productSubType" style="width: 100px;">
					<option value=""></option>
					<option value="POS">POS</option>
					<option value="信贷">信贷</option>
					<option value="车贷">车贷</option>
				</select>
			</div>
			<div style="margin-top:5px">
			<label style="margin-left: 5px">资金方:</label>
				<select id="capitalSide" class="easyui-combobox" name="capitalSide" style="width: 100px;">
					<option value=""></option>
					<option value="中泰">中泰</option>
					<option value="招行">招行</option>
					<option value="佰仟">佰仟</option>
				</select>
			<label style="margin-left: 20px">付款日期:</label>
			 	 <input	id="startPayDate" type="text" class="easyui-datebox" style="width: 100px"
						data-options="formatter:myformatter,parser:myparser">至
				 <input	id="endPayDate" type="text" class="easyui-datebox"	style="width: 100px"
						data-options="formatter:myformatter,parser:myparser" />
			<a style="margin-left:20px" id="button-search" href="javascript:;"	onclick="query()" class="easyui-linkbutton"	data-options="iconCls:'icon-search'">搜索</a>
			<a style="margin-left:18px" id="button-search" href="javascript:;"	onclick="clearForm('queryForm')" class="easyui-linkbutton"	data-options="iconCls:'icon-reload'">重置</a>
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
							<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:'true'" onclick='edit();'>编辑</a>
							<a href="#"	class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:'true'" onclick="payConfirm()" >付款确认</a>
							<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-save',plain:'true'" onclick="imports()">数据导入</a>
							<a href="http://localhost:8080/bas/businessLoanServer/exportExcel.do" class="easyui-linkbutton" data-options="iconCls:'icon-save',plain:true">模板导出</a>
							<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-tip',plain:'true'">Tips</a>
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
		  <table id="clearanceCertificateGrid" class="easyui-datagrid" title="放款表资金明细" style="width:100%;height:415px;"
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
					<th data-options="field:'serialNo'">合同号</th>
					<th data-options="field:'businessDate'">注册日</th>
					<th data-options="field:'keepAccountsDate'">记账日期</th>
					<th data-options="field:'payDate'">付款日期</th>
					<th data-options="field:'productSubType'">产品子类型</th>
					<th data-options="field:'capitalSide'">资金方</th>
					<th data-options="field:'businessSum'">放款本金</th>
					<th data-options="field:'deductionKHServiceFee'">扣减客户服务费</th>
					<th data-options="field:'deductionSHServiceFee'">扣减商户服务费</th>
					<th data-options="field:'paySum'">支付金额</th>
					<th data-options="field:'khName'">客户姓名</th>
					<th data-options="field:'shName'">商户名称</th>
					<th data-options="field:'shId'">商户ID</th>
					<th data-options="field:'city'">城市</th>
					<th data-options="field:'makeCollectionsDate'">收款日期</th>
				</tr>
			</thead>
		</table>
	    </div>
	</div>
	
<!-- 增加 -->
<div id="dlg" style="width: 570px; height: 370px; padding: 10px; top: 100px;display:none">
	<form id="orgInfoAddForm" method="post">
		<table id="orgInfoAddTable">
			<tbody>
				<tr>
					<th style="text-align: right; font-size: 13"><samp
							style="color: red">*</samp>合同号:</th>
					<td><input class="easyui-validatebox" type="text"
						name="serialNo" id="serialNo"
						data-options="required:true,validType:'code'" maxlength="20" /></td>
				</tr>

				<tr>
					<th style="text-align: right; font-size: 13"><samp
							style="color: red">*</samp>注册日期:</th>
					<td><input class="easyui-datebox" type="text"
						name="businessDate" id="businessDate"
						data-options="formatter:myformatter,parser:myparser,required:true"
						maxlength="20" /></td>
				</tr>

				<tr>
					<th style="text-align: right; font-size: 13"><samp
							style="color: red">*</samp>记账日期:</th>
					<td><input class="easyui-datebox" type="text"
						name="keepAccountsDate" id="keepAccountsDate"
						data-options="formatter:myformatter,parser:myparser,required:true"
						maxlength="20" /></td>
				</tr>

				<tr>
					<th style="text-align: right; font-size: 13"><samp
							style="color: red">*</samp>付款日期:</th>
					<td><input class="easyui-datebox" type="text"
						name="payDate" id="payDate"
						data-options="formatter:myformatter,parser:myparser,required:true"
						maxlength="20" /></td>
				</tr>

				<tr>
					<th style="text-align: right; font-size: 13"><samp
							style="color: red">*</samp>产品子类型:</th>
					<td><select id="productSubType"
						class="easyui-combobox textbox combobox-f combo-f textbox-f"
						name="productSubType" style="width: 173px;">
							<option value=""></option>
							<option value="POS">POS</option>
							<option value="信贷">信贷</option>
							<option value="车贷">车贷</option>
					</select></td>
				</tr>

				<tr>
					<th style="text-align: right; font-size: 13"><samp
							style="color: red">*</samp>资金方:</th>
					<td><select id="capitalSide" class="easyui-combobox"
						name="capitalSide" style="width: 173px;">
							<option value=""></option>
							<option value="中泰">中泰</option>
							<option value="招行">招行</option>
							<option value="佰仟">佰仟</option>
					</select></td>
				</tr>

				<tr>
					<th style="text-align: right; font-size: 13"><samp
							style="color: red">*</samp>放款本金:</th>
					<td><input class="easyui-validatebox" type="text"
						name="businessSum" id="businessSum"
						data-options="required:true,validType:'code'" maxlength="20" /></td>
				</tr>

				<tr>
					<th style="text-align: right; font-size: 13"><samp
							style="color: red">*</samp>扣减客户服务费:</th>
					<td><input class="easyui-validatebox" type="text"
						name="deductionKHServiceFee" id="deductionKHServiceFee"
						data-options="required:true,validType:'code'" maxlength="20" /></td>
				</tr>

				<tr>
					<th style="text-align: right; font-size: 13"><samp
							style="color: red">*</samp>扣减商户服务费:</th>
					<td><input class="easyui-validatebox" type="text"
						name="deductionSHServiceFee" id="deductionSHServiceFee"
						data-options="required:true,validType:'code'" maxlength="20" /></td>
				</tr>

				<tr>
					<th style="text-align: right; font-size: 13"><samp
							style="color: red">*</samp>支付金额:</th>
					<td><input class="easyui-validatebox" type="text"
						name="paySum" id="paySum"
						data-options="required:true,validType:'code'" maxlength="20" /></td>
				</tr>
				
				<tr>
					<th style="text-align: right; font-size: 13"><samp
							style="color: red">*</samp>客户姓名:</th>
					<td><input class="easyui-validatebox" type="text"
						name="khName" id="khName" data-options="required:true"
						maxlength="20" /></td>
				</tr>
				
				<tr>
					<th style="text-align: right; font-size: 13"><samp
							style="color: red">*</samp>商户姓名:</th>
					<td><input class="easyui-validatebox" type="text"
						name="shName" id="shName" data-options="required:true"
						maxlength="20" /></td>
				</tr>
				
				<tr>
					<th style="text-align: right; font-size: 13"><samp
							style="color: red">*</samp>商户ID:</th>
					<td><input class="easyui-validatebox" type="text"
						name="shId" id="shId" data-options="required:true"
						maxlength="20" /></td>
				</tr>
				
				<tr>
					<th style="text-align: right; font-size: 13"><samp
							style="color: red">*</samp>城市:</th>
					<td><input class="easyui-validatebox" type="text"
						name="city" id="city" data-options="required:true"
						maxlength="20" /></td>
				</tr>

			</tbody>
		</table>
		<div id="submitTop">
			<a href="javascript:;" onClick = "createData()" class="easyui-linkbutton" data-options="iconCls:'icon-ok'">新增</a>
	        <a href="javascript:;" onClick = "closeWin('dlg')" class="easyui-linkbutton" data-options="iconCls:'icon-cancel'">取消</a>
		</div>
	</form>
</div>

<!-- 编辑弹出框 -->
<div id="ediTdlg" data-options="iconCls:'icon-save'"
	style="width: 600px; height: 400px; padding: 10px">
	<form id="orgInfoEditForm" method="post">
		<table id="orgInfoAddTable">
			<tbody>
				<tr>
					<th hidden="none" style="text-align: right; font-size: 13"><samp hidden="none"
							style="color: red">*</samp>ID:</th>
					<td><input type="hidden" class="easyui-validatebox" type="text" name="id"
						id="id" data-options="required:true,validType:'code'"
						maxlength="20" /></td>
				</tr>
				<tr>
					<th style="text-align: right; font-size: 13"><samp
							style="color: red">*</samp>合同号:</th>
					<td><input class="easyui-validatebox" type="text"
						name="serialNo" id="serialNo"
						data-options="required:true,validType:'code'" maxlength="20" /></td>
				</tr>

				<tr>
					<th style="text-align: right; font-size: 13"><samp
							style="color: red">*</samp>注册日期:</th>
					<td><input class="easyui-datebox" type="text"
						name="businessDate" id="businessDate"
						data-options="formatter:myformatter,parser:myparser,required:true"
						maxlength="20" /></td>
				</tr>

				<tr>
					<th style="text-align: right; font-size: 13"><samp
							style="color: red">*</samp>记账日期:</th>
					<td><input class="easyui-datebox" type="text"
						name="keepAccountsDate" id="keepAccountsDate"
						data-options="formatter:myformatter,parser:myparser"
						maxlength="20" /></td>
				</tr>

				<tr>
					<th style="text-align: right; font-size: 13"><samp
							style="color: red">*</samp>付款日期:</th>
					<td><input class="easyui-datebox" type="text"
						name="payDate" id="payDate"
						data-options="formatter:myformatter,parser:myparser,required:true"
						maxlength="20" /></td>
				</tr>
				
				<tr>
					<th style="text-align: right; font-size: 13"><samp
							style="color: red">*</samp>收款日期:</th>
					<td><input class="easyui-datebox" type="text"
						name="makeCollectionsDate" id="makeCollectionsDate"
						data-options="formatter:myformatter,parser:myparser"
						maxlength="20" /></td>
				</tr>

				<tr>
					<th style="text-align: right; font-size: 13"><samp
							style="color: red">*</samp>产品子类型:</th>
					<td><select id="productSubType"
						class="easyui-combobox textbox combobox-f combo-f textbox-f"
						name="productSubType" style="width: 173px;">
							<option value=""></option>
							<option value="POS">POS</option>
							<option value="信贷">信贷</option>
							<option value="车贷">车贷</option>
					</select></td>
				</tr>

				<tr>
					<th style="text-align: right; font-size: 13"><samp
							style="color: red">*</samp>资金方:</th>
					<td><select id="capitalSide" class="easyui-combobox"
						name="capitalSide" style="width: 173px;">
							<option value=""></option>
							<option value="中泰">中泰</option>
							<option value="招行">招行</option>
							<option value="佰仟">佰仟</option>
					</select></td>
				</tr>

				<tr>
					<th style="text-align: right; font-size: 13"><samp
							style="color: red">*</samp>放款本金:</th>
					<td><input class="easyui-validatebox" type="text"
						name="businessSum" id="businessSum"
						data-options="required:true,validType:'code'" maxlength="20" /></td>
				</tr>

				<tr>
					<th style="text-align: right; font-size: 13"><samp
							style="color: red">*</samp>扣减客户服务费:</th>
					<td><input class="easyui-validatebox" type="text"
						name="deductionKHServiceFee" id="deductionKHServiceFee"
						data-options="required:true,validType:'code'" maxlength="20" /></td>
				</tr>

				<tr>
					<th style="text-align: right; font-size: 13"><samp
							style="color: red">*</samp>扣减商户服务费:</th>
					<td><input class="easyui-validatebox" type="text"
						name="deductionSHServiceFee" id="deductionSHServiceFee"
						data-options="required:true,validType:'code'" maxlength="20" /></td>
				</tr>

				<tr>
					<th style="text-align: right; font-size: 13"><samp
							style="color: red">*</samp>支付金额:</th>
					<td><input class="easyui-validatebox" type="text"
						name="paySum" id="paySum"
						data-options="required:true,validType:'code'" maxlength="20" /></td>
				</tr>
				
				<tr>
					<th style="text-align: right; font-size: 13"><samp
							style="color: red">*</samp>客户姓名:</th>
					<td><input class="easyui-validatebox" type="text"
						name="khName" id="khName" data-options="required:true"
						maxlength="20" /></td>
				</tr>
				
				<tr>
					<th style="text-align: right; font-size: 13"><samp
							style="color: red">*</samp>商户姓名:</th>
					<td><input class="easyui-validatebox" type="text"
						name="shName" id="shName" data-options="required:true"
						maxlength="20" /></td>
				</tr>
				
				<tr>
					<th style="text-align: right; font-size: 13"><samp
							style="color: red">*</samp>商户ID:</th>
					<td><input class="easyui-validatebox" type="text"
						name="shId" id="shId" data-options="required:true"
						maxlength="20" /></td>
				</tr>
				
				<tr>
					<th style="text-align: right; font-size: 13"><samp
							style="color: red">*</samp>城市:</th>
					<td><input class="easyui-validatebox" type="text"
						name="city" id="city" data-options="required:true"
						maxlength="20" /></td>
				</tr>
			</tbody>
		</table>
		<div id="EditTop">
			<br/>
			<a href="javascript:;" onClick = "updateData()" class="easyui-linkbutton" data-options="iconCls:'icon-ok'" style="margin-left:50px">保存</a>
			<a href="javascript:;" onClick = "deleteData()" class="easyui-linkbutton" data-options="iconCls:'icon-cancel'">删除</a>
	        <a href="javascript:;" onClick = "closeWin('ediTdlg')" class="easyui-linkbutton" data-options="iconCls:'icon-no'">关闭</a>  
		</div>
	</form>
</div>

<!-- 导入弹出框 -->
<div id="importdlg" data-options="iconCls:'icon-save'"
style="width: 600px; height: 400px; padding: 10px">
	<label>更新信息上传：</label>
	<form id="uploadCntractExcelFrm" method="post"
		enctype="multipart/form-data">
		<input id="uploadContractExcel" name="file" class="easyui-filebox" style="width: 200px" data-options="prompt:'请选择文件...'" />
		<a	href="#" class="easyui-linkbutton" style="width: 44px; height: 22px" onclick="importContract()">导入</a>
	</form>
</div>

<div id="payConfirmDlg" data-options="iconCls:'icon-save'"
	style="width: 600px; height: 400px; padding: 10px">
	<form id="payConfirmForm" method="post">
		<table id="orgInfoAddTable" style="margin:auto">
			<tbody>
				<tr style="line-height:30px;">
					<th style="text-align: right; font-size: 13">合同号:</th>
					<td><input class="easyui-validatebox" type="text" name="serialNo" id="serialNo"	 maxlength="20" /></td>
				</tr>

				<tr>
					<th style="text-align: right; font-size: 13">注册日(起):</th>
					<td><input class="easyui-datebox" type="text"
						name="startBusinessDate" id="startBusinessDate"
						data-options="formatter:myformatter,parser:myparser"
						maxlength="20" /></td>
				</tr>
				<tr>
					<th style="text-align: right; font-size: 13">注册日(止):</th>
					<td><input class="easyui-datebox" type="text"
						name="endBusinessDate" id="endBusinessDate"
						data-options="formatter:myformatter,parser:myparser"
						maxlength="20" /></td>
				</tr>

				<tr>
					<th style="text-align: right; font-size: 13">记账日(起):</th>
					<td><input class="easyui-datebox" type="text"
						name="startKeepAccountsDate" id="startKeepAccountsDate"
						data-options="formatter:myformatter,parser:myparser"
						maxlength="20" /></td>
				</tr>
				<tr>
					<th style="text-align: right; font-size: 13">记账日(止):</th>
					<td><input class="easyui-datebox" type="text"
						name="endKeepAccountsDate" id="endKeepAccountsDate"
						data-options="formatter:myformatter,parser:myparser"
						maxlength="20" /></td>
				</tr>

				<tr>
					<th style="text-align: right; font-size: 13">付款日(起):</th>
					<td><input class="easyui-datebox" type="text"
						name="startPayDate" id="startPayDate"
						data-options="formatter:myformatter,parser:myparser"
						maxlength="20" /></td>
				</tr>
				<tr>
					<th style="text-align: right; font-size: 13">付款日(止):</th>
					<td><input class="easyui-datebox" type="text"
						name="endPayDate" id="endPayDate"
						data-options="formatter:myformatter,parser:myparser"
						maxlength="20" /></td>
				</tr>

				<tr>
					<th style="text-align: right; font-size: 13">产品子类型:</th>
					<td><select id="productSubType"
						class="easyui-combobox textbox combobox-f combo-f textbox-f"
						name="productSubType" style="width: 173px;">
							<option value=""></option>
							<option value="POS">POS</option>
							<option value="信贷">信贷</option>
							<option value="车贷">车贷</option>
					</select></td>
				</tr>

				<tr>
					<th style="text-align: right; font-size: 13">资金方:</th>
					<td><select id="capitalSide" class="easyui-combobox"
						name="capitalSide" style="width: 173px;">
							<option value=""></option>
							<option value="中泰">中泰</option>
							<option value="招行">招行</option>
							<option value="佰仟">佰仟</option>
					</select></td>
				</tr>

				<tr>
					<th style="text-align: right; font-size: 13">城市:</th>
					<td><input class="easyui-validatebox" type="text" name="city" id="city"
						maxlength="20" /></td>
				</tr>
				
				<tr style="line-left:100px;">
					<th style="text-align: right; font-size: 13"><samp
							style="color: red">*</samp>收款日期:</th>
					<td><input class="easyui-datebox" type="text"
						name="makeCollectionsDate" id="makeCollectionsDate"
						data-options="formatter:myformatter,parser:myparser,required:true"
						maxlength="20" />
					</td>
				</tr>
			</tbody>
		</table>
		<div id="EditTop">
			<a href="#" onClick = "payConfirmSubmit()" class="easyui-linkbutton" data-options="iconCls:'icon-ok'" style="margin-left:100px" >提交</a>
	        <a href="#" onClick = "closeWin('payConfirmDlg')" class="easyui-linkbutton" data-options="iconCls:'icon-no'">取消</a>  
		</div>
	</form>
</div>

<!-- 数据网格end -->

<script type="text/javascript">
	function query() {
		var serialNo = $("#serialNo").textbox('getValue');
		var startBusinessDate = $("#startBusinessDate").datebox('getValue');
		var endBusinessDate = $("#endBusinessDate").datebox('getValue');
		var startPayDate = $("#startPayDate").datebox('getValue');
		var endPayDate = $("#endPayDate").datebox('getValue');
		var capitalSide = $("#capitalSide").combobox('getValue');
		var productSubType = $("#productSubType").combobox('getValue');
		$.ajax({
			url : contextPath + "/businessLoanServer/queryBusinessDetail.do",
			type : "POST",
			data : {
				rows: 10,
				page:1,
				serialNo : serialNo,
				startBusinessDate : startBusinessDate,
				endBusinessDate : endBusinessDate,
				startPayDate : startPayDate,
				endPayDate : endPayDate,
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
					serialNo : serialNo,
					startBusinessDate : startBusinessDate,
					endBusinessDate : endBusinessDate,
					startPayDate : startPayDate,
					endPayDate : endPayDate,
					capitalSide : capitalSide,
					productSubType : productSubType
				});
				$("#clearanceCertificateGrid").datagrid({
					url : contextPath + "/businessLoanServer/queryBusinessDetail.do"
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
	
	function edit() {
		var selectedRow = $('#clearanceCertificateGrid').datagrid('getSelections');
		if(selectedRow.length >=1){
			$('#ediTdlg').dialog({
				title : '修改',
				width : 330,
				height : 450,
				closed : false,
				cache : false,
				modal : true,
				resizable : true
			});
			$('#orgInfoEditForm').form('load',{
				id : selectedRow[0].id,
				serialNo : selectedRow[0].serialNo,
				businessDate : selectedRow[0].businessDate/* .format('yyyy/MM/dd') */,
				keepAccountsDate : selectedRow[0].keepAccountsDate,
				payDate : selectedRow[0].payDate,
				productSubType : selectedRow[0].productSubType,
				capitalSide : selectedRow[0].capitalSide,
				businessSum : selectedRow[0].businessSum,
				deductionKHServiceFee: selectedRow[0].deductionKHServiceFee,
				deductionSHServiceFee: selectedRow[0].deductionSHServiceFee,
				paySum: selectedRow[0].paySum,
				khName : selectedRow[0].khName,
				shName: selectedRow[0].shName,
				shId: selectedRow[0].shId,
				city: selectedRow[0].city,
				makeCollectionsDate:selectedRow[0].makeCollectionsDate
			});
			//设置输入框为禁用
			$('#id').attr('readonly',true); 
		}else{
			$.messager.alert("操作提示", "请选择行数据进行编辑!", "warning");
		}
	}
	
	function updateData() {
		$('#orgInfoEditForm').form('submit', {
			url : contextPath + '/businessLoanServer/updateBusinessDetail.do',
			success : function(data) {
				//关闭窗口
				$.messager.alert("操作提示", "updata Successful!", "info");
				closeWin('ediTdlg');
				queryChangeData(data);
			},
			onLoadError : function(data) {
				$.messager.alert("操作提示", data.message, "error");
			}
		});
	}
	
	//窗口赋值
	function payConfirm() {
		$('#payConfirmDlg').dialog({
			title : '修改',
			width : 330,
			height : 430,
			closed : false,
			cache : false,
			modal : true,
			resizable : true
		});
		var serialNo = $("#serialNo").textbox('getValue');
		var startBusinessDate = $("#startBusinessDate").datebox('getValue');
		var endBusinessDate = $("#endBusinessDate").datebox('getValue');
		var startPayDate = $("#startPayDate").datebox('getValue');
		var endPayDate = $("#endPayDate").datebox('getValue');
		var capitalSide = $("#capitalSide").combobox('getValue');
		var productSubType = $("#productSubType").combobox('getValue');
		var makeCollectionsDate = $("#makeCollectionsDate").datebox('getValue');
		$('#payConfirmForm').form('load',{
			serialNo : serialNo,
			startBusinessDate :startBusinessDate,
			endBusinessDate : endBusinessDate,
			startPayDate : startPayDate,
			endPayDate : endPayDate,
			capitalSide : capitalSide,
			productSubType : productSubType,
			makeCollectionsDate : makeCollectionsDate
		});
	}
	
	function payConfirmSubmit() {
		$('#payConfirmForm').form('submit', {
			url : contextPath + '/businessLoanServer/payConfirm.do',
			success : function(data) {
				closeWin('payConfirmDlg');
				var jsondata = eval("(" + data + ")");
				//是否有数据
				if (data == "[]") {
					$.messager.alert('提示', '暂无匹配数据!付款确认标记修改失败!', 'warning');
					//清空表单数据
					$('#clearanceCertificateGrid').datagrid('loadData', {
						total : 0,
						rows : []
					});
				}else{
					$.messager.alert('提示', jsondata.msg, 'info');
					queryChangeData(jsondata.obj);
				}
			},
			onLoadError : function(data) {
				$.messager.alert("操作提示", data.message, "error");
			}
		});
	}
	
	function createData() {
		$('#orgInfoAddForm').form('submit', {
			url : contextPath + '/businessLoanServer/createContract.do',
			success : function(data) {
				$.messager.alert("操作提示", "Create Successful!", "info");
				closeWin('dlg');
				queryChangeData(data);
			},
			onLoadError : function(data) {
				$.messager.alert("操作提示", data.message, "error");
			}
		});
	}
	
	function deleteData() {
		$('#orgInfoEditForm').form('submit', {
			url : contextPath + '/businessLoanServer/deleteBusinessDetail.do',
			success : function(data) {
				//关闭窗口
				$.messager.alert("操作提示", "remove Successful!", "info");
				closeWin('ediTdlg');
				query();
			},
			onLoadError : function(data) {
				$.messager.alert("操作提示", data.message, "error");
			}
		}); 	 
	}
	
	function imports(){
		$('#importdlg').show();
		$('#importdlg').dialog({
		    title  : '导入',
		    width  : 300,
		    height : 300,
		    cache  : false,
		    modal  : true,	
		    hcenter :true,
		    resizable : true,
		    });
	};
	
	//合同信息上传
	function importContract() {   
		// 得到上传文件的全路径
		var fileName = $('#uploadContractExcel').filebox('getValue');
		// 获取题型
		if (fileName != "") {
			// 进行基本校验
			if (fileName == "") {
				$.messager.alert('提示', '请选择上传文件！', 'warning');
			} else {
				// 对文件格式进行校验
				var d1 = /\.[^\.]+$/.exec(fileName);
				if (d1 == ".xls" || d1 == ".xlsx") {
					$.messager.progress({ title: 'excel数据导入中', msg: '数据导入中...'});
					// 提交表单
					$("#uploadCntractExcelFrm").form("submit", {
						url : contextPath + '/businessLoanServer/importContract.do',
						onSubmit : function() {
						},
						success : function(result) {
							$.messager.progress('close');
							var obj = jQuery.parseJSON(result);
							if(obj.success){
								$("#clearanceCertificateGrid").datagrid('loadData', obj.obj);
								$.messager.confirm('操作提示','请仔细核对数据无误后点击确定再次导入!',function(r){   
								    if (r){
								    	$.ajax({  
								    		url : contextPath + '/businessLoanServer/importContractSave.do',
								    	    type: "POST",  
								    	    success: function (data) {  
								    	    	$.messager.alert("操作提示", "导入成功!", "info");
								    	    	queryChangeData(data);
								    	    },
											error : function() {
												$.messager.alert("操作提示", '导入失败,请稍后重试或尝试联系系统管理员!', "error");
											}  
								    	}); 
								    }
								});
								closeWin('importdlg');
							}else{
								$.messager.alert('提示','解析失败!请核对模板重新导入!', 'warning');
								closeWin('importdlg');
							}
						},
						error : function() {
							$.messager.alert("操作提示", '上传失败,请稍后重试或尝试联系系统管理员!', "error");
						}
					});
				} else {
					$.messager.alert('提示', '请选择xls格式文件！', 'warning');
					$('#uploadContractExcel').filebox('setValue', '');
				}
			}
		} else {
			$.messager.alert('提示', '请选择需上传的文件！', 'warning');
		}
		
	}

	function queryChangeData(data){
		var id;
		if(data.indexOf(":")>0){
			var jsondata1 = eval("("+data+")");
			id = jsondata1.id;
		}else{
			id = data;
		}
		$.ajax({
			url : contextPath + "/businessLoanServer/queryBusinessDetail.do",
			type : "POST",
			data : {
				page:1,
				rows:10,
				id:id
			},
			success : function(data2) {
				var jsondata = eval("(" + data2 + ")");
				//是否有数据
				if (jsondata.rows == "[]") {
					$.messager.alert('提示', '暂无匹配数据!请重新搜索..', 'warning');
					//清空表单数据
					$('#clearanceCertificateGrid').datagrid('loadData', {
						total : 0,
						rows : []
					});
 					$("#contractCount").textbox('setValue',
 							'0');
 					$("#moneyCount").textbox('setValue', 
 							'0.0');
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
					id:id
				});
				$("#clearanceCertificateGrid").datagrid({
					url : contextPath + "/businessLoanServer/queryBusinessDetail.do"
				}); 
 				$("#clearanceCertificateGrid").datagrid('loadData', jsondata.rows);
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
