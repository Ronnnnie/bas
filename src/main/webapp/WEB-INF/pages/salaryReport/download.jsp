<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ include file="../common/include.jsp"%>
<title>薪资报表</title>

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
	src="${CONTEXT_PATH}/js/app/download.js"></script>
</head>
<body>
<div class="easyui-layout" fit="true" >
 <div data-options="region:'north'" style="height:50px; padding:2px;">
    <form id="searchColums">       
		<span style="display:-moz-inline-box;display:inline-block;">
		<span style="display:-moz-inline-box;display:inline-block;width: 80px;text-align:right;" title="报表名称">报表名称：</span>	
		<input type="text" id="reportName" name="reportName"  style="width: 100px"/>
		</span>
		<span style="display:-moz-inline-box;display:inline-block;">
		<span style="margin-left:0px;">
			<a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="downloadSearch()">查询</a>
			<a href="#" class="easyui-linkbutton" iconCls="icon-reload" onclick="downloadSearchReset('searchColums')">重置</a>
		</span>	
		</span>
	</form>
 </div>
 <div data-options="region:'center'" >
	 <div id="downloadList" style="height: 100%"></div>
 </div>
</div>
</body>
</html>