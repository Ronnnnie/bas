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
	src="${CONTEXT_PATH}/js/app/salaryReport.js"></script>
</head>
<body>
<div class="easyui-layout" fit="true" >
 <div data-options="region:'north'" style="height:80px; padding:2px;">
 <div style="height:10px;width:100%"></div>
    <form id="searchColums">       
		<span style="display:-moz-inline-box;display:inline-block;">
		<span style="display:-moz-inline-box;display:inline-block;width: 80px;text-align:right;" title="年份">查询年份：</span>	
		<input type="text" id="yearSelect" name="yearSelect" style="width: 80px"/>
		</span>	
		<span style="display:-moz-inline-box;display:inline-block;">
		<span style="display:-moz-inline-box;display:inline-block;width: 100px;text-align:right;" title="月份">查询月份：</span>	
		<input type="text" id="monthSelect" name="monthSelect" style="width: 80px"/>
		</span>
		<span style="display:-moz-inline-box;display:inline-block;">
		<span style="display:-moz-inline-box;display:inline-block;width: 100px;text-align:right;" title="成本中心">成本中心：</span>	
		<input type="text" id="costDep" name="costDep"  style="width: 100px"/>
		</span>	
		<span style="display:-moz-inline-box;display:inline-block;">
		<span style="display:-moz-inline-box;display:inline-block;width: 60px;text-align:right;" title="部门">部门：</span>	
		<input type="text" id="depart" name="depart"  style="width: 140px"/>
		</span>
		<span style="display:-moz-inline-box;display:inline-block;">
		<span style="display:-moz-inline-box;display:inline-block;width: 60px;text-align:right;" title="城市">城市：</span>	
		<input type="text" id="city" name="city"  style="width: 100px"/>
		</span>
		<span style="display:-moz-inline-box;display:inline-block;">
		<span style="display:-moz-inline-box;display:inline-block;width: 60px;text-align:right;" title="岗位">岗位：</span>	
		<input type="text" id="posts" name="posts"  style="width: 100px"/>
		</span>
		<span style="display:-moz-inline-box;display:inline-block;">
		<span style="display:-moz-inline-box;display:inline-block;width: 80px;text-align:right;" title="公司法人">公司法人：</span>	
		<input type="text" id="comPer" name="comPer"  style="width: 100px"/>
		</span>
		<span style="display:-moz-inline-box;display:inline-block;">
		<span style="display:-moz-inline-box;display:inline-block;width: 80px;text-align:right;" title="员工工号">员工工号：</span>	
		<input type="text" id="person" name="person"  style="width: 100px"/>
		</span>
		<span style="display:-moz-inline-box;display:inline-block;">
		<span style="margin-left:0px;">
			<a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="salarySearch()">查询</a>
			<a href="#" class="easyui-linkbutton" iconCls="icon-reload" onclick="salarySearchReset('searchColums')">重置</a>
			<a href="#" class="easyui-linkbutton" iconCls="icon-print" onclick="exportXls()">导出Excel</a> 		
		</span>	
		</span>
	</form>
  </div>
 <div data-options="region:'center'">
	 <div id="salaryReport" style="height: 100%"></div>
 </div>
</div>
</body>
</html>