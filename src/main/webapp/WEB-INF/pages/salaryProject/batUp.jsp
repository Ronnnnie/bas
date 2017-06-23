<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ include file="../common/include.jsp"%>
<title>项目批量上传</title>

<style type="text/css">
.formdiv {
	padding: 5px;
}

.required {
	color: red;
}
</style>
<style type="text/css">
#asbody {
	width: 100%;
	height: 100%;
}

#asbody #atitle {
	height: 15px;
	line-height: 15px;
	width: 100%;
	font-weight: bold;
}

#asbody #aup {
	width: 100%;
	height: 20%;;
	border-bottom: 1px solid #ccc;
	background: #eee;
}

#asbody #aupinner {
	width: 50%;
	padding: 10px 10px;
}
</style>
<link rel="stylesheet"
	href="${CONTEXT_PATH}/js/uploadify/css/uploadify.css" type="text/css"></link>
<script type="text/javascript"
	src="${CONTEXT_PATH}/js/uploadify/jquery.uploadify-3.1.js"></script>
<script type="text/javascript"
	src="${CONTEXT_PATH}/js/app/salaryProjectBatUp.js"></script>
</head>
<body>

	<div class="easyui-layout" data-options="fit:true">
		<div data-options="region:'north'" title=" "
			style="width: 100%; height: 20%;">
			<div style="margin: 5px; float: left;">
				<form id="questionTypesManage" method="post"
					enctype="multipart/form-data">
					<div>
					<label>核算薪资月份(YYYYMM)：</label>
					<input id="importCountDate" type="text" name="importCountDate" onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')" maxlength="6">
					</div>
					<br>
					<div>
					<label>项目批量上传：</label>
					<input id="uploadExcel" name="file" class="easyui-filebox"
						style="width: 200px" data-options="prompt:'请选择文件...'" /> <a
						href="#" class="easyui-linkbutton"
						style="width: 44px; height: 22px" onclick="uploadProExcel()">导入</a>
					</div>
				</form>

			</div>
			<div style="float: left;">
				<a href="${CONTEXT_PATH}/salary/project/projectTempleteDown.do">项目上传模板下载</a>
			</div>
		</div>
		<div data-options="region:'center'">
			<table id="projectDataGrid" style="height: 100%"></table>
		</div>
	</div>
</body>
</html>