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
<script  type="text/javascript" src="${CONTEXT_PATH}/js/app/util.js" charset="utf-8"></script>
<script  type="text/javascript" src="${CONTEXT_PATH}/js/app/businessCheckList.js" charset="utf-8"></script>
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
		<div data-options="region:'north'" title="" style="width:256%;height: 40px;"> 
			<form id="queryForm">
			<div style="width:90%;margin-top:7px;margin-left:20px">
				<label>日期:</label>
				<input id="startImportDate" type="text" class="easyui-datebox" style="width: 100px"
							data-options="formatter:myformatter,parser:myparser" />至
				<input	id="endImportDate" type="text" class="easyui-datebox"	style="width: 100px"
							data-options="formatter:myformatter,parser:myparser" />
			<a style="margin-left:20px;height:24px" class="easyui-linkbutton" data-options="iconCls:'icon-search'" onclick="query()" >搜索</a>
			<a style="margin-left:18px;height:24px" class="easyui-linkbutton" data-options="iconCls:'icon-reload'" onclick="clearForm('queryForm')" >重置</a>
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
							<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-save',plain:'true'" onclick="imports()">数据导入</a>
	   						<a href="${CONTEXT_PATH}/businessCheckListServer/excelExport.do" class="easyui-linkbutton" data-options="iconCls:'icon-save',plain:'true'">数据导出</a>
						</div>
						<!-- 按钮 end -->
		   			</td>
		   		</tr>
		    </table>
		  </div>
		  <table id="clearanceCertificateGrid" class="easyui-datagrid" title="放款表明细" style="width:256%;height:96%;"
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
				<th data-options="field:'importDate'">日期</th>
				<th data-options="field:'sunrisePreDeposit'">日出预存款</th>
				<th data-options="field:'sunriseUnmatch'">日出未匹配</th>
				<th data-options="field:'yqzlwyTotalRevenue'">银企直连网银总进账</th>
				<th data-options="field:'yqzlasTotalRevenue'">银企直连安硕总进账</th>
				<th data-options="field:'yqzlTotalRevenueDifference'" >银企直连总进账差异</th>
				<th data-options="field:'yqzldrAlreadymatch'">银企直连当日已匹配</th>
				<th data-options="field:'yqzkdrUnmatch'">银企直连当日未匹配</th>
				<th data-options="field:'handworkSeparate'">手工分离</th>
				<th data-options="field:'handworkMatch'">手工匹配</th>
				<th data-options="field:'rzUnmatch'">日终未匹配</th>
				<th data-options="field:'ebuWyTotalRevenue'">EBU网银总进账</th>
				<th data-options="field:'ebuAsTotalRevenues'">EBU安硕总进账后一天</th>
				<th data-options="field:'ebuAsTotalRevenue'">EBU安硕总进账</th>
				<th data-options="field:'ebuTotalRevenueDifference'">EBU总进账差异</th>
				<th data-options="field:'kftWyTotalRevenue'">KFT网银总入账</th>
				<th data-options="field:'kftAsTotalRevenue'">KFT安硕总入账</th>
				<th data-options="field:'kftTotalRevenueDifference'">KFT总入账差异</th>
				<th data-options="field:'hhWyTotalRevenue'">哈行网银总入账</th>
				<th data-options="field:'hhAsTotalRevenue'">哈行安硕总入账</th>
				<th data-options="field:'hhTotalRevenueDifference'">哈行总入账差异</th>
				<th data-options="field:'kftSswyTotalRevenue'">KFT实时网银总入账</th>
				<th data-options="field:'kftSsasTotalRevenue'">KFT实时安硕总入账</th>
				<th data-options="field:'kftSsTotalRevenueCy'">KFT实时总入账差异</th>
				<th data-options="field:'refund'">退款</th>
				<th data-options="field:'ordinaryRepayment'">普通还款</th>
				<th data-options="field:'earlierRepayment'">提前还款</th>
				<th data-options="field:'virtualAccount'">虚拟入账</th>
				<th data-options="field:'rzPreDeposit'">日终预存款</th>
				<th data-options="field:'unmatchDifference'">未匹配差额</th>
				<th data-options="field:'preDepositDifference'">预存款差额</th>
				</tr>
			</thead>
		</table>
	    </div>
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
