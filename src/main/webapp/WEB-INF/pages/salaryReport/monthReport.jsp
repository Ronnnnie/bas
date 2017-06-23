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
</style>
<script type="text/javascript"
	src="${CONTEXT_PATH}/js/app/salaryReport.js"></script>
</head>
<body>
	<div class="easyui-layout" data-options="fit:true">
		<div data-options="region:'center'"
			style="margin-top: 5%; border: none; text-align: center;">
			<label style="font-size: 22px;">薪资报表导出</label> <br /> <br />
			<form id="reportForm" method="post" style="margin-left: 40%;">
				<table cellpadding="5">
					<tr>
						<td>年份:</td> 
						<td><input name="yearSelect" id="yearSelect" data-options="required:true" /></td> 
					</tr>
					<tr>
						<td>月份:</td>
						<td><input name="monthSelect" id="monthSelect" /></td>
					</tr>
					<tr>
						<td>成本中心:</td>
						<td><input name="costDep" id="costDep" /></td>
					</tr>
					<tr>
						<td>部门:</td>
						<td><input name="depart" id="depart" /></td>
					</tr>
					<tr>
						<td>城市:</td>
						<td><input name="city" id="city" /></td>
					</tr>
					<tr>
						<td>岗位:</td>
						<td><input name="posts" id="posts" /></td>
					</tr>
					<tr>
						<td>公司法人:</td>
						<td><input name="comPer" id="comPer" /></td>
					</tr>
				</table>
			</form>
			<div style="margin-bottom: 20px">
				<a href="#" class="easyui-linkbutton" onClick = "reportExport()"
					style="width: 30%; height: 32px">导出</a>
			</div>
			<div>
				<label>成功后显示导出的绝对路径</label>
			</div>
		</div>
</body>
</html>