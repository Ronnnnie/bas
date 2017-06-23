<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ include file="../common/include.jsp"%>
<script type="text/javascript" src="${CONTEXT_PATH}/js/app/sendMails.js"></script>
<title>邮件通知管理</title>

<style type="text/css">
.formdiv {
	padding: 5px;
}

.required {
	color: red;
}
</style>

</head>
<body>
	<div class="easyui-layout" data-options="fit:true">
		
			<div data-options="region:'north'" title=" "
				style="width: 100%; height: 25%;">
				<div style="margin: 5px; float: left;">
				
					<label>全员发送：</label>
					<a href="#" onclick="confirm()" class="easyui-linkbutton"
					style="width: 80%; height: 32px">全员发送</a>
				</div>
				<div style="margin: 5px; clear:right;">
					<label>批量发送邮件模板下载：</label>
					<a href="${CONTEXT_PATH}/salary/report/employeeEmailTempleteDown.do">下载</a>
				</div>
				
				<div style="margin: 5px; clear:left; float: left;">
					<label style="float: left;">批量发送：</label>
					<form id="uploadEmployeeNoFrm" method="post" style="float: left;"
						enctype="multipart/form-data">
						<input id="uploadExcel" name="file" class="easyui-filebox" style="width:200px" data-options="prompt:'请选择文件...'">  
						<a href="#" class="easyui-linkbutton"
							style="width: 30%; height: 32px" onclick="sendEmailByUpload()">发送</a>
					</form>
				</div>
					
				<div style="margin: 5px; float: left;">
					<label>单个发送：</label>
					<input id="singleUser"
					class="easyui-textbox" data-options="prompt:'请输入正确的员工工号'"
					style="width: 50%; height: 32px">
					<a onclick="sendSingleEmail()" class="easyui-linkbutton"
					style="width: 30%; height: 32px">发送</a> 
					
				</div>

			</div>
			<div data-options="region:'center'">
				<table id="emailGrid" style="height: 23%"></table>
				<form>
					<label>发送邮件状态：</label>
					<input name="status" id = "searchStatus"/>			
					<a id="button-search" href ="javascript:;" onclick="query()" class="easyui-linkbutton" data-options="iconCls:'icon-search'">搜索</a>
				</form>
				<table id="sendEmailGrid" style="height: 77%"></table>
			</div>

	</div>

	<!-- 查看详情 -->
	<div id="emailSenderWin" style="width: 350px;text-align:center;display: none;" >
		<form id="emailSenderFrm" method="post">
			
			<table class="formTable">
				<tr> <th>Host:</th> <td> <input name="emailHost" class="easyui-validatebox" type="text" data-options="required:true" readonly="true" /> </td> </tr> 
				<tr> <th>UserName:</th> <td> <input name="emailUsername" class="easyui-validatebox" data-options="required:true" type="text"/> </td> </tr>
				<tr> <th>Password:</th> <td> <input name="emailPassword" class="easyui-validatebox" data-options="required:true" type="password"/> </td> </tr>
			</table>
			<div style="padding:5px;text-align:center;">  
	            <a href="javascript:;" onClick = "updateEmailSender()" class="easyui-linkbutton" data-options="iconCls:'icon-ok'">保存</a>
	            <a href="javascript:;" onClick = "closeWin('emailSenderWin')" class="easyui-linkbutton" data-options="iconCls:'icon-cancel'">取消</a>  
	     	</div>  
		</form>
	</div> 

</body>
</html>