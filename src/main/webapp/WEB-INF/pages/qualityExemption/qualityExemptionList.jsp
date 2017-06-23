<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ include file="../common/include.jsp"%>
<title>薪资计算个人</title>

<style type="text/css">
	.formdiv {padding: 5px;}
	.required{color: red;}
</style>
<script type="text/javascript" src="${CONTEXT_PATH}/js/app/qualityExemption.js"></script>
<script type="text/javascript" src="${CONTEXT_PATH}/js/my97datepicker/WdatePicker.js"></script>
</head>
<body>

<div class="easyui-layout" data-options="fit:true">
	<div data-options="region:'north'" title=" " style="width:100%;height: 18%;"> 
		<form id="uploadForm" method="post"
					enctype="multipart/form-data">
			<div style="width:90%;margin: 5px;">
				<label>豁免月份：</label>
				<input style="width:146px" id="searchExeMonth" name="exeMonth" onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy/MM'})" class="Wdate"/ readonly="readonly"/>
				<label>合同号：</label>
				<input type="text" name="userCode" id="searchSerialno" class="easyui-validatebox" data-options="validType:'unnormal'"  maxlength="20"/>
				<label>创建人：</label>
				<input type="text" name="createby" id="searchCreateby" class="easyui-validatebox" data-options="validType:'unnormal'"  maxlength="20"/>
				<label>创建时间：</label>
				<input style="width:146px" id="searchCreatetime" name="createtime" />
				<a id="button-search" href ="javascript:;" onclick="query()" class="easyui-linkbutton" data-options="iconCls:'icon-search'">搜索</a>
				<a id="button-search" href ="javascript:;" onclick="clearSearch()" class="easyui-linkbutton" data-options="iconCls:'icon-remove'">清空</a>
				<br/>
				<label>批量导入：</label>
				<input id="uploadExcel" name="file" class="easyui-filebox"
					style="width: 200px" data-options="prompt:'请选择文件...'" /> <a
					href="#" class="easyui-linkbutton"
					style="width: 44px; height: 22px" onclick="uploadProExcel()">导入</a>
				<a href="${CONTEXT_PATH}/qualityExemption/qualityExemptionTempleteDown.do">上传模板下载</a>
			</div>
		</form>
	</div>
	 <div data-options="region:'center'">
   	   <table id="qualityExemptionGrid" style="height: 100%"></table>
    </div>
</div>	
	
    <!-- 个人查询 -->
	<div id="qualityExemptionWin"
		style="width: 350px; text-align: center; display: none;">
		<form id="qualityExemptionFrm" method="post">
			<table class="formTable">
				<tr>
					<th><samp>*</samp>豁免月份:</th>
					<td><input style="width:146px" id="saveExeMonth" name="exeMonth" onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy/MM'})" class="Wdate"/ readonly="readonly"/></td>
				</tr>
				<tr>
					<th><samp>*</samp>合同号:</th>
					<td><input id="saveSerialno" name="serialno" class="easyui-validatebox" data-options="required:true" maxlength="40"
						type="text" /></td>
				</tr>
				<tr>
					<th><samp>*</samp>创建人:</th>
					<td><input id="saveCreateby" name="createby" class="easyui-validatebox" data-options="required:true" maxlength="40"
						type="text" /></td>
				</tr>
				<tr>
					<th><samp>*</samp>创建时间:</th>
					<td><input style="width:146px" id="saveCreatetime" name="createtime" /></td>
				</tr>
				
			</table>
			<div style="padding: 5px; text-align: center;">
				<a href="javascript:;" onClick="saveQualityExemption()"
					class="easyui-linkbutton" data-options="iconCls:'icon-ok'">保存</a> <a
					href="javascript:;" onClick="closeWin('qualityExemptionWin')"
					class="easyui-linkbutton" data-options="iconCls:'icon-cancel'">取消</a>
			</div>
		</form>
	</div>

	
	
	 
	
	</body>   
</html>