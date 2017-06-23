<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ include file="../common/include.jsp"%>
<title>信息同步</title>

<style type="text/css">
.formdiv {
	padding: 5px;
}

.required {
	color: red;
}
</style>
<script type="text/javascript"
	src="${CONTEXT_PATH}/js/app/employeeSynInfo.js"></script>
</head>
<body>

	<div class="easyui-layout" data-options="fit:true">
		<div data-options="region:'north'" title=" "
			style="width: 100%; height: 20%;">
			<form>
				<div style="width: 90%; margin: 5px; float: left;">
					<label>员工工号：</label> <input type="text" name="jobNo"
						id="searchJobNo" class="easyui-validatebox"
						data-options="validType:'unnormal'" maxlength="20" /> <label>员工姓名：</label>
					<input type="text" name="employeeName" id="searchEmployeeName"
						class="easyui-validatebox" data-options="validType:'unnormal'"
						maxlength="20" /> <a id="button-search" href="javascript:;"
						onclick="query()" class="easyui-linkbutton"
						data-options="iconCls:'icon-search'">搜索</a>
				</div>
				<div style="width: 90%; margin: 5px; float: left;">
					<a href="#" onclick="sysInfoManual()" class="easyui-linkbutton"
						style="width: 30%; height: 32px">数据同步</a>
				</div>
			</form>
		</div>
		<div data-options="region:'center'">
			<table id="employeeGrid" style="height: 100%" toolbar="#tb"></table>
		</div>
	</div>
	<div id="tb" style="padding: 5px; height: auto">
		<div style="margin-bottom: 5px">
			<a href="#" onclick="overTo()" class="easyui-linkbutton"
				iconCls="icon-undo">转入</a> <a href="#" onclick="overToAll()"
				class="easyui-linkbutton" iconCls="icon-undo">全部转入</a> <a
				href="${CONTEXT_PATH}/employee/toBeTransferredDown.do"
				class="easyui-linkbutton" iconCls="icon-redo" >导出</a>
		</div>
	</div>
</body>
</html>