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
<script  type="text/javascript" src="${CONTEXT_PATH}/js/app/util.js" charset="utf-8"></script>
<script  type="text/javascript" src="${CONTEXT_PATH}/js/app/predictcostList.js" charset="utf-8"></script>

<link rel="stylesheet" type="text/css" href="${CONTEXT_PATH}/js/jquery-easyui-1.4.2/themes/bootstrap/easyui.css">   <!--引入CSS样式-->
<link rel="stylesheet" type="text/css" href="${CONTEXT_PATH}/js/jquery-easyui-1.4.2/themes/icon.css">   <!--Icon引入-->
<style type="text/css">
	.formTable{padding:1px;}
	.formTable th{text-align:right;width:90px;padding:5px 0px 5px 0;}
    .formTable td{padding:5px 0 5px 0px;}
	.formTable samp{vertical-align:middle;color:#f00;}
	.datagrid-cell{line-height:28px}
	.datagrid-cell-rownumber{height:28px}
	.readonlyCss {
		color: rgb(84,84,84);
		background-color: rgb(235,235,228);
	}
</style>
<!-- 缓存用户是否是超级管理员 -->
<input type="hidden" id="isSuperAdmin" value="${isSuperAdmin}">
<!-- 数据网格 -->
	<div class="easyui-layout" data-options="fit:true" style="width:100%;height:98%">
		<div data-options="region:'north'" title="" style="width:256%;height: 60px;"> 
			<form id="queryForm">
			<div style="width:97%;margin-top:7px;margin-left:20px">
				<label>合同号:</label>
				<input class="easyui-textbox" name="contractno" id="searchContractno" width="80px" class="easyui-validatebox" data-options="validType:'unnormal'"  maxlength="20"/>
				<label>记账日期:</label>
				<input id="startInaccountdate" type="text" class="easyui-datebox" style="width: 100px"
							data-options="formatter:myformatter,parser:myparser" />至
				<input	id="endInaccountdate" type="text" class="easyui-datebox"	style="width: 100px"
							data-options="formatter:myformatter,parser:myparser" />
				<label>资产所属方:</label> 
				<select id="belong" class="easyui-combobox" name="belong" style="width: 100px;">
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
				<label>应还日期:</label>
				<input id="startImportDate" type="text" class="easyui-datebox" style="width: 100px"
							data-options="formatter:myformatter,parser:myparser" />至
				<input	id="endImportDate" type="text" class="easyui-datebox"	style="width: 100px"
							data-options="formatter:myformatter,parser:myparser" />
				
			<a style="margin-left:10px;height:24px" class="easyui-linkbutton" data-options="iconCls:'icon-search'" onclick="query()" >搜索</a>
			<a style="margin-left:10px;height:24px" class="easyui-linkbutton" data-options="iconCls:'icon-reload'" onclick="clearForm('queryForm')" >重置</a>
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
					    	<a href="javascript:;" onClick = "openUpdatePredictcost()" class="easyui-linkbutton" data-options="iconCls:'icon-ok',plain:'true'">记账确认</a>
					    	<a href="javascript:;" onClick = "openCancelPredictcost()" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:'true'">记账撤销</a>
	   						<a href="javascript:;" onClick = "selectUpdateOrCancel()" class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:'true'">批量编辑</a>
	   						<a onclick="exportXls()" class="easyui-linkbutton" data-options="iconCls:'icon-save',plain:'true'">数据导出</a>
	   						
						</div>
						
						<!-- 按钮 end -->
		   			</td>
		   		</tr>
		    </table>
		  </div>
		  <table id="predictcostGrid" class="easyui-datagrid" title="预提表明细" style="width:100%;height:96%;"
			data-options="
					id:'contractno',
					rownumbers:true,
					loadMsg:'数据加载中,请稍等',
					pagination:true,
					fitColumns:true,
					singleSelect: false,
					toolbar: '#tb',
					pageList : [ 10, 20, 50, 100 ],
					showFooter : true,
					data:{'total':0,'rows':[]},
					method: 'post'
				">
			<thead>
				<tr>
				<th data-options="field:'loansno',checkbox:true"></th>
				<th data-options="field:'inaccountdate'">记账日期</th>
				<th data-options="field:'duedate'">统计日期</th>
				<th data-options="field:'contractno'">合同号</th>
				<th data-options="field:'clientname'">客户姓名</th>
				<th data-options="field:'registrationdate'">合同记账日</th>
				<th data-options="field:'maturitydate'">合同到期日</th>
				<th data-options="field:'sno'" >门店代码</th>
				<th data-options="field:'rno'">商户代码</th>
				<th data-options="field:'sa_id'">SA_ID</th>
				<th data-options="field:'productcategory'">商品范畴</th>
				<th data-options="field:'suretype'">客户渠道</th>
				<th data-options="field:'businessmodel'">业务模式</th>
				<th data-options="field:'productid'">贷款类型</th>
				<th data-options="field:'subproducttype'">贷款子类型</th>
				<th data-options="field:'province'">省份</th>
				<th data-options="field:'city'">城市</th>
				<th data-options="field:'citycode'">城市编码</th>
				<th data-options="field:'creditperson'" formatter="companyNameFormatter">资金方</th>
				<th data-options="field:'belonglife'">合同轨迹</th>
				<th data-options="field:'cdate'">强制日期</th>
				<th data-options="field:'overduedays'">逾期天数</th>
				<th data-options="field:'describe'">逾期描述</th>
				<th data-options="field:'classfy'">五级分类</th>
				<th data-options="field:'paydate'">应还日</th>
				<th data-options="field:'seqid'">期次</th>
				<th data-options="field:'canceltype'" formatter="cancelTypeFormatter">是否取消分期期次</th>
				
				<th data-options="field:'deliverydate'">转让日</th>
				<th data-options="field:'dcdate'">代偿日</th>
				<th data-options="field:'shdate'">赎回日</th>
				<th data-options="field:'tbdate'">保险投保日</th>
				<th data-options="field:'lpdate'">保险理赔日</th>
				<th data-options="field:'debours'" formatter="companyNameFormatter">代垫方</th>
				<th data-options="field:'belong'" formatter="companyNameFormatter">资产所属方</th>
				<th data-options="field:'guaranteeparty'" formatter="companyNameFormatter">保证方</th>
				<th data-options="field:'payprincipalamt'" formatter="thousandthFormatter">预提本金</th>
				<th data-options="field:'payinteamt'" formatter="thousandthFormatter">预提利息</th>
				
				<th data-options="field:'a2'" formatter="thousandthFormatter">预提客户服务费</th>
				<th data-options="field:'a7'" formatter="thousandthFormatter">预提财务管理费</th>
				<th data-options="field:'a12'" formatter="thousandthFormatter">预提增值服务费</th>
				<th data-options="field:'a18'" formatter="thousandthFormatter">预提随心还服务费</th>
				<th data-options="field:'amount'" formatter="thousandthFormatter">合计（此表单金额合计）</th>
				<th data-options="field:'inaccountstatus'" formatter="formatKeepaccountsStatus">记账状态</th>
				</tr>
			</thead>
		</table>
	    </div>
	</div>
	
	
	<div id="updatePredictcostWin" style="width: 350px;text-align:center;display: none;padding: 15px;" >
		<form id="updatePredictcostFrm" method="post">
			<input class="easyui-validatebox" type="hidden" name="proId"
						id="updateProId"  />   
			<table class="formTable">
			
				<tr>
					<th style="text-align: right; font-size: 13">合同号:</th>
					<td>
					  <input  class="easyui-textbox" name="contractno" class="inputxt readonlyCss" readonly="readonly"
						id="updateContractno"  />   
					</td>
				</tr>
				<tr>
					<th style="text-align: right; font-size: 13">资产所属方:</th>
					<td>
					  <input id="updateBelong" type="hidden" name="belong"/>
					  <input  class="easyui-textbox" name="belong" class="inputxt readonlyCss" readonly="readonly"
						id="updateBelongShow"  />   
					</td>
				</tr>
				<tr>
					<th style="text-align: right; font-size: 13">付款开始日期:</th>
					<td>
					 <input  class="easyui-textbox" class="inputxt readonlyCss" readonly="readonly"
		        	id="updateStartPayDate" name="startPayDate" />   
					</td>
				</tr>
				
				<tr>
					<th style="text-align: right; font-size: 13">付款结束日期:</th>
					<td>
					 <input  class="easyui-textbox" class="inputxt readonlyCss" readonly="readonly"
		        		id="updateEndPayDate" name="endPayDate"  />   
					</td>
				</tr>
				
				<tr>
					<th style="text-align: right; font-size: 13">记账开始日期:</th>
					<td>
					 <input  class="easyui-textbox" class="inputxt readonlyCss" readonly="readonly"
		        		id="updateStartInaccountdate" name="startInaccountdate"  />   
					</td>
				</tr>
				
				<tr>
					<th style="text-align: right; font-size: 13">记账结束日期:</th>
					<td>
					 <input  class="easyui-textbox" class="inputxt readonlyCss" readonly="readonly"
		        		id="updateEndInaccountdate" name="endInaccountdate"  />   
					</td>
				</tr>
				
				
				
				<tr>
					<th style="text-align: right; font-size: 13"><samp>*</samp>记账日期:</th>
					<td>
					<input id="updateInaccountdate" type="text" class="easyui-datebox" style="width: 130px" name="inaccountdate"
							data-options="formatter:myformatter,parser:myparser,required:true" />
				</tr>
				
			
				<tr>
					<th style="text-align: right; font-size: 13">记账备注:</th>
					<td>
					  <textarea class="easyui-validatebox" name="keepaccountsremark" id="updateKeepaccountsremark" maxlength="300"></textarea>   
					</td>
				</tr>
			</table>
		    <div style="padding:5px;text-align:center;">  
	            <a style="margin-left:100px" href="javascript:;" onClick = "updatePredictcost()" class="easyui-linkbutton" data-options="iconCls:'icon-ok'">记账</a>
	            <a href="javascript:;" onClick = "closeWin('updatePredictcostWin')" class="easyui-linkbutton" data-options="iconCls:'icon-cancel'">取消</a>  
	     	</div>  
		</form>
	</div> 


	<div id="cancelPredictcostWin" style="width: 350px;text-align:center;display: none;padding: 15px;" >
		<form id="cancelPredictcostFrm" method="post">
			  
			<table class="formTable">
			
				<tr>
					<th style="text-align: right; font-size: 13">合同号:</th>
					<td>
					  <input  class="easyui-textbox" name="contractno" class="inputxt readonlyCss" readonly="readonly"
						id="cancelContractno"  />   
					</td>
				</tr>
				<tr>
					<th style="text-align: right; font-size: 13">资产所属方:</th>
					<td>
					  <input id="cancelBelong" type="hidden" name="belong"/>
					  <input  class="easyui-textbox" name="belong" class="inputxt readonlyCss" readonly="readonly"
						id="cancelBelongShow"  />   
					</td>
				</tr>
				<tr>
					<th style="text-align: right; font-size: 13">付款开始日期:</th>
					<td>
					 <input  class="easyui-textbox" class="inputxt readonlyCss" readonly="readonly"
		        	id="cancelStartPayDate" name="startPayDate" />   
					</td>
				</tr>
				
				<tr>
					<th style="text-align: right; font-size: 13">付款结束日期:</th>
					<td>
					 <input  class="easyui-textbox" class="inputxt readonlyCss" readonly="readonly"
		        		id="cancelEndPayDate" name="endPayDate"  />   
					</td>
				</tr>
				
				<tr>
					<th style="text-align: right; font-size: 13">记账开始日期:</th>
					<td>
					 <input  class="easyui-textbox" class="inputxt readonlyCss" readonly="readonly"
		        		id="cancelStartInaccountdate" name="startInaccountdate"  />   
					</td>
				</tr>
				
				<tr>
					<th style="text-align: right; font-size: 13">记账结束日期:</th>
					<td>
					 <input  class="easyui-textbox" class="inputxt readonlyCss" readonly="readonly"
		        		id="cancelEndInaccountdate" name="endInaccountdate"  />   
					</td>
				</tr>
				
				
				
			</table>
		    <div style="padding:5px;text-align:center;text-align:center;">  
	            <a href="javascript:;" onClick = "cancelPredictcost()" class="easyui-linkbutton" data-options="iconCls:'icon-ok'">撤销</a>
	            <a href="javascript:;" onClick = "closeWin('cancelPredictcostWin')" class="easyui-linkbutton" data-options="iconCls:'icon-cancel'">取消</a>  
	     	</div>  
		</form>
	</div> 

<div id="predictcostMultipleDlg" data-options="iconCls:'icon-save'"
	style="width: 600px; height: 400px; padding: 10px">
	<form id="predictcostMultipleForm" method="post">
		<table>
			<tbody>
				<tr>
					<th style="text-align: right; font-size: 13">期次:</th>
					<td><input id="multipleSeqid" name="seqid" class="easyui-textbox" style="width:150px" data-options="editable:false"/></td>
				</tr>
				<tr>
					<th style="text-align: right; font-size: 13">合同号:</th>
					<td><input id="multipleSerialno" name="contractno" class="easyui-textbox" style="width:150px" data-options="editable:false"/></td>
				</tr>
				<tr>
					<th style="text-align: right; font-size: 13">资产所属方:</th>
					<td>
					<input id="multipleAssetbelong" name="belong" type="hidden" style="width:150px" />
					<input id="multipleAssetbelongName" name="belongName" class="easyui-textbox" style="width:150px" data-options="editable:false"/>
					</td>
				</tr>
				<tr style="line-height:15px;">
					<th style="text-align: right; font-size: 13">逾期本金:</th>
					<td>
					<input id="multiplePayprincipalamt" name="payprincipalamt" type="hidden" style="width:150px" data-options="editable:false"/>
					<input id="multiplePayprincipalamtAmount" name="payprincipalamtAmount" class="easyui-textbox" style="width:150px" data-options="editable:false"/>
					</td>
				</tr>
				<tr style="line-height:15px;">
					<th style="text-align: right; font-size: 13">逾期利息:</th>
					<td>
					<input id="multiplePayinteamt" name="payinteamt" type="hidden" style="width:150px" data-options="editable:false"/>
					<input id="multiplePayinteamtAmount" name="payinteamtAmount" class="easyui-textbox" style="width:150px" data-options="editable:false"/>
					</td>
				</tr>

				<tr style="line-left:100px;">
					<th style="text-align: right; font-size: 13"><samp
							style="color: red">*</samp>请修改记账日期:</th>
					<td><input class="easyui-datebox" type="text"
						name="inaccountdate" id="multipleInaccountdate"
						data-options="formatter:myformatter,parser:myparser,required:true,editable:false"
						maxlength="20"   style="width:150px"/>
					</td>
				</tr>
				<tr style="line-height:15px;">
					<th style="text-align: right; font-size: 13">请填写记账备注:</th>
					<td><input class="easyui-textbox" id="multipleInaccountremark" name="keepaccountsremark"  style="width:150px"/></td>
				</tr>
			</tbody>
		</table>
		<div id="EditTop" style="margin-top:10px">
			<a style="margin-left:60px" href="javascript:;" onClick = "predictcostMultipleUpdate()" class="easyui-linkbutton" data-options="iconCls:'icon-ok'">记账</a>
	        <a href="javascript:;" onClick = "predictcostMultipleCancel()" class="easyui-linkbutton" data-options="iconCls:'icon-ok'">撤销</a>
	        <a href="javascript:;" onClick = "closeWin('predictcostMultipleDlg')" class="easyui-linkbutton" data-options="iconCls:'icon-cancel'">取消</a>  
		</div>
	</form>
</div>
