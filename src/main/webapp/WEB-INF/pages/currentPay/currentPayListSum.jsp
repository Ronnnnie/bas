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
<script  type="text/javascript" src="${CONTEXT_PATH}/js/app/currentPayList.js" charset="utf-8"></script>

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
		<div data-options="region:'north'" title="" style="width:256%;height: 110px;"> 
			<form id="queryForm">
			<div style="margin-top:10px;">
				<label style="margin-left: 10px">记账日期:</label>
				<input id="startInaccountdate" type="text" class="easyui-datebox" style="width: 120px"
							data-options="formatter:myformatter,parser:myparser" />至
				<input	id="endInaccountdate" type="text" class="easyui-datebox"	style="width: 120px"
							data-options="formatter:myformatter,parser:myparser" />
				<label style="margin-left: 10px">应还日期:</label>
				<input id="startImportDate" type="text" class="easyui-datebox" style="width: 120px"
							data-options="formatter:myformatter,parser:myparser" />至
				<input	id="endImportDate" type="text" class="easyui-datebox"	style="width: 120px"
							data-options="formatter:myformatter,parser:myparser" />
				<label style="margin-left: 20px">实还日期:</label>
				<input id="startActualpaydate" type="text" class="easyui-datebox" style="width: 120px"
							data-options="formatter:myformatter,parser:myparser" />至
				<input	id="endActualpaydate" type="text" class="easyui-datebox"	style="width: 120px"
							data-options="formatter:myformatter,parser:myparser" />
			</div>
			
			<div style="margin-top:7px">
				<label style="margin-left: 10px">业务模式:</label>
				<select id="searchBusinessmodel" class="easyui-combobox" name="businessmodel" style="width: 120px;">
					<option value=""></option>
					<option value="P2P">P2P</option>
					<option value="信托">信托模式</option>
					<option value="助贷">助贷模式</option>
				</select>
				<label style="margin-left: 10px">贷款子类型:</label>
				<select id="searchSub" class="easyui-combobox" name="subproducttype" style="width: 120px;">
					<option value=""></option>
					<option value="普通POS贷">普通POS贷</option>
					<option value="学生消费贷">学生消费贷</option>
					<option value="成人教育贷">成人教育贷</option>
					<option value="学生教育贷">学生教育贷</option>
					<option value="小企业贷商户通">小企业贷商户通</option>
					<option value="预约现金贷">预约现金贷</option>
					<option value="无预约现金贷">无预约现金贷</option>
					<option value="车主现金贷">车主现金贷</option>
				</select>
				<label style="margin-left: 10px">城市:</label>
				<input class="easyui-textbox" name="city" id="searchCity" width="80px" class="easyui-validatebox" data-options="validType:'unnormal'"  maxlength="20"/>
				<label style="margin-left: 10px">应付资产方:</label> 
				<select id="paybelong" class="easyui-combobox" name="paybelong" style="width: 120px;">
					<option value=""></option>
					<option value="ZTXT">应付中泰</option>
					<option value="ZXXT">应付中信</option>
					<option value="HBYH">应付哈行</option>
					<option value="JYPH">应付嘉银</option>
					<option value="RMBX">应付人保</option>
					<option value="RXCH">应付村行</option>
				</select>
				<label style="margin-left: 10px">数据来源表单:</label>
				<select id="datasource" class="easyui-combobox" name="datasource" style="width: 120px;">
					<option value=""></option>
					<option value="到期表">到期表</option>
					<option value="实还表">实还表</option>
					<option value="减免表">减免表</option>
					<option value="哆啦回款表">哆啦回款表</option>
					<option value="助贷表">助贷表</option>
				</select>
				
			</div>
				
			<div style="margin-top:7px">
				<label style="margin-left: 10px">资金方:</label>
				<select id="creditperson" class="easyui-combobox" name="creditperson" style="width: 120px;">
					<option value=""></option>
					<option value="JYPH">嘉银普惠</option>
					<option value="ZTXT">中泰信托</option>
					<option value="ZXXT">中信信托</option>
					<option value="RXCH">宝安融兴村行</option>
				</select>
				<label style="margin-left: 10px">资产所属方:</label> 
				<select id="assetbelong" class="easyui-combobox" name="assetbelong" style="width: 120px;">
					<option value=""></option>
					<option value="ZTXT">中泰信托</option>
					<option value="ZXXT">中信信托</option>
					<option value="JYPH">嘉银普惠</option>
					<option value="HBYH">哈尔滨银行</option>
					<option value="GZBC">贵州佰诚</option>
					<option value="JC">聚诚</option>
					<option value="RMBX">中国人民财产保险</option>
					<option value="JSZB">嘉实资产</option>
					<option value="RXCH">宝安融兴村行</option>
				</select>
				<label style="margin-left: 10px">保证方:</label>
				<select id="guaranteeparty" class="easyui-combobox" name="guaranteeparty" style="width: 120px;">
					<option value=""></option>
					<option value="BQJR">佰仟金融</option>
					<option value="GZBC">贵州佰诚</option>
					<option value="JC">聚诚</option>
					<option value="RMBX">中国人民财产保险</option>
				</select>
				
				<a style="margin-left:20px;height:24px" class="easyui-linkbutton" data-options="iconCls:'icon-search'" onclick="querySum()" >搜索</a>
				<a style="margin-left:18px;height:24px" class="easyui-linkbutton" data-options="iconCls:'icon-reload'" onclick="clearForm('queryForm')" >重置</a>
			</div>
			
			<!-- <div style="width:90%;margin-top:7px;margin-left:20px">
				<label>资产所属方:</label> 
				<select id="assetbelong" class="easyui-combobox" name="assetbelong" style="width: 120px;">
					<option value=""></option>
					<option value="中泰信托">中泰信托有限责任公司</option>
					<option value="中信信托">中信信托有限责任公司</option>
					<option value="P2P">P2P</option>
					<option value="村行">村行</option>
					<option value="哈尔滨银行">哈尔滨银行</option>
				</select>
				<label>应还日期:</label>
				<input id="startImportDate" type="text" class="easyui-datebox" style="width: 120px"
							data-options="formatter:myformatter,parser:myparser" />至
				<input	id="endImportDate" type="text" class="easyui-datebox"	style="width: 120px"
							data-options="formatter:myformatter,parser:myparser" />
			<a style="margin-left:20px;height:24px" class="easyui-linkbutton" data-options="iconCls:'icon-search'" onclick="querySum()" >搜索</a>
			<a style="margin-left:18px;height:24px" class="easyui-linkbutton" data-options="iconCls:'icon-reload'" onclick="clearForm('queryForm')" >重置</a>
			</div> -->
			</form>
		</div>
			
		<div data-options="region:'center'">
		<div id="tb" style="margin-left：100px">	
		      <table style="width:100%;height:10px;border-left:none;border-bottom:none;border-right:none;" >
		   		<tr>
		   			<td>
		   				<!-- 按钮 start -->
					    <div id="tb" style="margin-left:20px">
							<a onclick="exportXlsSum()" class="easyui-linkbutton" data-options="iconCls:'icon-save',plain:'true'">数据导出</a>
							
						</div>
						
						<!-- 按钮 end -->
		   			</td>
		   		</tr>
		    </table>
		  </div>
		  <table id="currentPaySumGrid" class="easyui-datagrid" title="应付资产方汇总" style="width:100%;height:96%;"
			data-options="
					id:'dg-dopts',
					rownumbers:true,
					loadMsg:'数据加载中,请稍等',
					pagination:true,
					fitColumns:true,
					singleSelect: true,
					toolbar: '#tb',
					pageList : [ 10, 20, 50, 100 ],
					showFooter : true,
					data:{'total':0,'rows':[]},
					method: 'post'
				">
			<thead>
				<tr>
				<th data-options="field:'id',checkbox:true"></th>
				<th data-options="field:'inaccountdate'">记账日期</th>
				<th data-options="field:'accorddate'">统计日期</th>
				<th data-options="field:'suretype'">客户渠道</th>
				<th data-options="field:'businessmodel'">业务模式</th>
				<th data-options="field:'productid'" >贷款类型</th>
				<th data-options="field:'subproducttype'">贷款子类型</th>
				<th data-options="field:'city'">城市</th>
				<th data-options="field:'citycode'">城市编码</th>
				<th data-options="field:'creditperson'" formatter="companyNameFormatter">资金方</th>
				<th data-options="field:'paydate'">应还日</th>
				<th data-options="field:'waivedate'">减免日</th>
				<th data-options="field:'actualpaydate'">实还日</th>
				<th data-options="field:'debours'" formatter="companyNameFormatter">代垫方</th>
				<th data-options="field:'assetbelong'" formatter="companyNameFormatter">资产所属方</th>
				<th data-options="field:'guaranteeparty'" formatter="companyNameFormatter">保证方</th>
				<th data-options="field:'paybelong'" formatter="companyNameFormatter">应付资产方</th>
				<th data-options="field:'datasource'">数据来源表单</th>
				<th data-options="field:'payprincipalamt'" formatter="thousandthFormatter">应付本金</th>
				<th data-options="field:'payinteamt'" formatter="thousandthFormatter">应付利息</th>
				<th data-options="field:'a11'" formatter="thousandthFormatter">应付印花税</th>
				<th data-options="field:'amount'" formatter="thousandthFormatter">合计（此表单金额合计）</th>
				</tr>
			</thead>
		</table>
	    </div>
	</div>


