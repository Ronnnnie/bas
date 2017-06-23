<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ include file="../common/include.jsp"%>
<title>奖金汇总</title>

<style type="text/css">
.formdiv {
	padding: 5px;
}

.required {
	color: red;
}
span {
	font-size:14px;
	margin:2px 0px;
}
</style>
<script type="text/javascript"
	src="${CONTEXT_PATH}/js/app/bonusSummary.js"></script>
</head>
<body>
<div class="easyui-layout" fit="true" >
 <div data-options="region:'north'" style="height:50px; padding:2px;">
 <div style="height:10px;width:100%"></div>
    <form id="searchColums">       
		<span style="display:-moz-inline-box;display:inline-block;">
		<span style="display:-moz-inline-box;display:inline-block;width: 80px;text-align:right;" title="员工工号">员工工号：</span>	
		<input type="text" id="workid" name="workid" style="width: 80px"/>
		</span>	
		<span style="display:-moz-inline-box;display:inline-block;">
		<span style="display:-moz-inline-box;display:inline-block;width: 60px;text-align:right;" title="销售ID">销售ID：</span>	
		<input type="text" id="saId" name="saId"  style="width: 140px"/>
		</span>
		<span style="display:-moz-inline-box;display:inline-block;">
		<span style="display:-moz-inline-box;display:inline-block;width: 100px;text-align:right;" title="月份">月份：</span>	
		<input type="text" id="bonusmonth" name="bonusmonth" class="easyui-datebox" data-options="formatter:myformatter,parser:myparser" style="width: 104px"/>
		</span>	
		<span style="display:-moz-inline-box;display:inline-block;">
		<span style="margin-left:0px;">
			<a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="bonusSearch()">查询</a>
			<a href="#" class="easyui-linkbutton" iconCls="icon-reload" onclick="bonusSearchReset('searchColums')">重置</a>
			<a href="#" class="easyui-linkbutton" iconCls="icon-print" onclick="exportXls()">导出Excel</a> 		
		</span>	
		</span>
	</form>
  </div>
 <div data-options="region:'center'">
	 <div id="bonusSummary" style="height: 100%"></div>
 </div>
</div>
</body>
</html>