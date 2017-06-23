<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ include file="../common/include.jsp"%>
<title>薪资明细导出</title>

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
<!-- 		<div data-options="region:'north'" title=" " -->
<!-- 			style="width: 100%; height: 15%;"> -->
<!-- 			<form> -->
<!-- 				<div style="width: 90%; margin: 5px;"> -->
<!-- 					<label>选择导出报表方式：</label> <input name="validInd" id="searchValidInd" /> -->
<!-- 				</div> -->
<!-- 			</form> -->
<!-- 		</div> -->

		<div id="de1" data-options="region:'center'"
			style="margin-top: 10%; border: none; text-align: center;">
			<label style="font-size: 22px;">薪资明细导出</label>
			<div style="margin-bottom: 20px; margin-top: 20px; margin-left: 0px;">
				<a href="#" class="easyui-linkbutton"
					style="width: 30%; height: 32px">全员导出</a>
			</div>
			<div style="margin-bottom: 20px">
				<a href="#" class="easyui-linkbutton"
					style="width: 30%; height: 32px">单个导出</a> <input
					class="easyui-textbox" data-options="prompt:'请输入正确的员工工号'"
					style="width: 30%; height: 32px">
			</div>
			<div>
				<label>成功后显示导出的绝对路径</label>
			</div>
		</div>

	</div>


</body>
</html>