<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ include file="../common/include.jsp"%>
<title>单个项目管理</title>

<style type="text/css">
	.formdiv {padding: 5px;}
	.required{color: red;}
</style>
<script type="text/javascript" src="${CONTEXT_PATH}/js/app/salaryProjectSingleMgr.js"></script>
</head>
<body>

<div class="easyui-layout" data-options="fit:true">
	<div data-options="region:'north'" title=" " style="width:100%;height: 15%;"> 
		<form>
			<div style="width:auto;margin: 5px;float: left;">
				<label>项目名字：</label>
				<input type="text" name=proName id="searchProName" class="easyui-validatebox" data-options="validType:'unnormal'"  maxlength="20"/>
						
				<a id="button-search" href ="javascript:;" onclick="query()" class="easyui-linkbutton" data-options="iconCls:'icon-search'">搜索</a>
			</div>
			
			<div style="float: left;margin-top:8px;">
				<a href="${CONTEXT_PATH}/salary/project/countTempleteDown.do">EXCEL计算公式参考下载(只用于在填写excel计算公式时作为参考)</a>
			</div>
		</form>
	</div>
	
	 <div data-options="region:'center'">
   	   <table id="projectGrid" style="height: 100%"></table>
    </div>
</div>	
	
    <!-- 新增项目 -->
	<div id="projectWin" style="width: 350px;text-align:center;display:none;" >
		<form id="projectFrm" method="post">
			<table class="formTable">
				
				<tr>
					<th><samp>*</samp>项目名字:</th>
					<td>
					 <input  type="text"  class="easyui-validatebox"
		        		id="proName" name="proName" data-options="required:true" />   
					</td>
				</tr>
				
				<tr>
					<th><samp>*</samp>项目类型:</th>
					<td>
					 <input class="easyui-validatebox" type="text" name="proType"
						id="proType" data-options="required:true" maxlength="20" /> 
					</td>
				</tr>
				
				<tr>
					<th><samp>*</samp>项目别名:</th>
					<td>
					<input  type="text" name="proAlise" 
						id="proAlise"  maxlength="20" /> 
					</td>
				</tr>
				
				<tr>
					<th><samp>*</samp>数据类型:</th>
					<td>
					 <input  type="text" class="easyui-validatebox"
		        		id="dataType" name="dataType" data-options="required:true" />   
					</td>
				</tr>
				<tr>
					<th><samp>*</samp>数据来源:</th>
					<td>
					 <input  type="text"  class="easyui-validatebox"
		        		id="dataSource" name="dataSource" data-options="required:true" />   
					</td>
				</tr>
				<tr>
					<th><samp>*</samp>是否有效:</th>
					<td>
					 <input  type="text"  class="easyui-validatebox"
		        		id="isused" name="isused" data-options="required:true" />   
					</td>
				</tr>
				<tr>
					<th>备注:</th>
					<td>
					  <textarea  name="remark" id="remark" maxlength="300"></textarea>   
					</td>
				</tr>
				
			</table>
		    <div style="padding:5px;text-align:center;">  
	            <a href="javascript:;" onClick = "addProjectInfo()" class="easyui-linkbutton" data-options="iconCls:'icon-ok'">保存</a>
	            <a href="javascript:;" onClick = "closeWin('projectWin')" class="easyui-linkbutton" data-options="iconCls:'icon-cancel'">取消</a>  
	     	</div>  
		</form>
	</div> 
	
	
    <!-- 修改项目 -->
	<div id="updateProjectWin" style="width: 350px;text-align:center;display: none;" >
		<form id="updateProjectFrm" method="post">
			<input class="easyui-validatebox" type="hidden" name="proId"
						id="updateProId"  />   
			<table class="formTable">
			
				<tr>
					<th>项目名字:</th>
					<td>
					  <input class="easyui-validatebox" type="text" name="proName"
						id="updateProName"  />   
					</td>
				</tr>
				<tr>
					<th><samp>*</samp>项目类型:</th>
					<td>
					 <input  type="text" 
		        	id="updateProType" name="proType" data-options="required:true"/>   
					</td>
				</tr>
				
				<tr>
					<th><samp>*</samp>数据类型:</th>
					<td>
					 <input  type="text" 
		        		id="updateDataType" name="dataType" data-options="required:true" />   
					</td>
				</tr>
				<tr>
					<th><samp>*</samp>数据来源:</th>
					<td>
					 <input  type="text" 
		        		id="updateDataSource" name="dataSource" data-options="required:true" />   
					</td>
				</tr>
				<tr>
					<th><samp>*</samp>是否有效:</th>
					<td>
					 <input  type="text" 
		        		id="updateIsused" name="isused" data-options="required:true" />   
					</td>
				</tr>
				<tr>
					<th>备注:</th>
					<td>
					  <textarea class="easyui-validatebox" name="remark" id="updateRemark" maxlength="300"></textarea>   
					</td>
				</tr>
			</table>
		    <div style="padding:5px;text-align:center;">  
	            <a href="javascript:;" onClick = "updateProject()" class="easyui-linkbutton" data-options="iconCls:'icon-ok'">保存</a>
	            <a href="javascript:;" onClick = "closeWin('updateProjectWin')" class="easyui-linkbutton" data-options="iconCls:'icon-cancel'">取消</a>  
	     	</div>  
		</form>
	</div> 
	 
	<div id="assignmentProjectWin" style="width: 350px;text-align:center;display: none;" >
		<form id="assignmentProjectFrm" method="post">
			<input class="easyui-validatebox" type="hidden" name="proId"
						id="assignmentProId"  />   
		    <input  type="hidden" name="projectFlag" value="isAssignment"/>
		    <input  type="hidden" name="proAlise" id="assignmentProAlise"/>   
			<table class="formTable">
			
				<tr>
					<th>数值:</th>
					<td>
					  <input class="easyui-validatebox" type="text" name="curValue"
						id="assignmentCurValue"  />   
					</td>
				</tr>
			</table>
		    <div style="padding:5px;text-align:center;">  
	            <a href="javascript:;" onClick = "assignmentProject()" class="easyui-linkbutton" data-options="iconCls:'icon-ok'">一键所有员工赋值</a>
	            <a href="javascript:;" onClick = "closeWin('assignmentProjectWin')" class="easyui-linkbutton" data-options="iconCls:'icon-cancel'">取消</a>  
	     	</div>  
		</form>
	</div>
	
	<div id="formulaProjectWin" style="width: 400px;text-align:center;display: none;" >
		<form id="formulaProjectFrm" method="post">
			<input class="easyui-validatebox" type="hidden" name="proId"
						id="formulaProId"  />   
		    <input  type="hidden" name="projectFlag" value="isFormula"/>
			<table class="formTable">
			
				<tr>
					<th>计算公式:</th>
					<td>
					  <textarea rows="3" cols="30" name="curValue" id="formulaCurValue">
					  </textarea>   
					</td>
				</tr>
			</table>
		    <div style="padding:5px;text-align:center;">  
	            <a href="javascript:;" onClick = "formulaProject()" class="easyui-linkbutton" data-options="iconCls:'icon-ok'">保存</a>
	            <a href="javascript:;" onClick = "closeWin('formulaProjectWin')" class="easyui-linkbutton" data-options="iconCls:'icon-cancel'">取消</a>  
	     	</div>  
		</form>
	</div> 
	
	</body>   
</html>