<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ include file="../common/include.jsp"%>
<title>工资单管理</title>

<style type="text/css">
.formdiv {
	padding: 5px;
}

.required {
	color: red;
}
</style>
<script type="text/javascript"
	src="${CONTEXT_PATH}/js/app/salaryPayRoll.js"></script>
</head>
<body>
	<div class="easyui-layout" data-options="fit:true">
		<div data-options="region:'north'" title=" "
			style="width: 100%; height: 15%;">
			<div style="float: left; margin: 10px">
				<label>请选择工资单模板：</label> <input name="validInd" id="payrollTemplete" />
			</div>
			<div style="float: left; margin: 10px">
				<a id="preview" href="#">工资模板预览</a>
			</div>
			<div style="float: left; margin: 10px">
				<a href="${CONTEXT_PATH}/salary/report/buildPayrollTempleteDown.do"> 工资单工号上传模板</a>
			</div>
			<div style="float: left; margin: 10px">
				<form id="uploadExcelFrm" method="post"
					enctype="multipart/form-data">
					<input id="uploadExcel" name="file" class="easyui-filebox"
						style="width: 200px" data-options="prompt:'请选择文件...'" /> <a
						href="#" class="easyui-linkbutton"
						style="width: 44px; height: 22px" onclick="uploadJobNoExcel()">导入</a>
				</form>
			</div>
		<div style="float: left; margin: 10px">
			<a href="#" onclick="buildPayRolls()">工资单生成</a>
		</div>
		<div style="float: left; margin: 10px">
			<a id="download" href="${CONTEXT_PATH}/salary/report/downAllPayRolls.do">工资单下载</a>
		</div>
	</div>
	<div data-options="region:'center'">
		<table id="projectGrid" style="height: 100%"></table>
	</div>
	</div>

	<!-- 修改项目 -->
	<div id="updateProjectWin"
		style="width: 350px; text-align: center; display: none;">
		<form id="updateProjectFrm" method="post">
			<input class="easyui-validatebox" type="hidden" name="proId"
				id="updateProId" />
			<table class="formTable">
				<tr>
					<th>项目名字:</th>
					<td><input class="easyui-validatebox" type="text"
						name="proName" disabled="disabled" id="updateProName" /></td>
				</tr>
				<tr>
					<th><samp>*</samp>项目类型:</th>
					<td><input type="text" id="updateProType" name="proType"
						data-options="required:true" disabled="disabled" /></td>
				</tr>

				<tr>
					<th><samp>*</samp>薪资类型:</th>
					<td><input type="text" id="salarytype" name="salarytype"
						data-options="required:true" /></td>
				</tr>
				<tr>
					<th><samp>*</samp>明细类型:</th>
					<td><input type="text" id="salarydetails" name="salarydetails"
						data-options="required:true" /></td>
				</tr>
				<tr>
					<th><samp>*</samp>是否显示:</th>
					<td><input type="text" id="isdisplay" name="isdisplay"
						data-options="required:true" /></td>
				</tr>
			</table>
			<div style="padding: 5px; text-align: center;">
				<a href="javascript:;" onClick="updateProject()"
					class="easyui-linkbutton" data-options="iconCls:'icon-ok'">保存</a> <a
					href="javascript:;" onClick="closeWin('updateProjectWin')"
					class="easyui-linkbutton" data-options="iconCls:'icon-cancel'">取消</a>
			</div>
		</form>
	</div>

</body>
</html>