<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ include file="../common/include.jsp"%>
<title>角色管理</title>

<style type="text/css">
	.formdiv {padding: 5px;}
	.required{color: red;}
</style>
<script type="text/javascript" src="${CONTEXT_PATH}/js/app/sysRole.js"></script>
</head>
<body>

<div class="easyui-layout" data-options="fit:true">
	<div data-options="region:'north'" title=" " style="width:100%;height: 15%;"> 
		<form>
			<div style="width:90%;margin: 5px;">
				<label>角色编码：</label>
				<input type="text" name="searchRoleCode" id="searchRoleCode" class="easyui-validatebox" data-options="validType:'unnormal'"  maxlength="20"/>
				
			
				<label>角色名称：</label>
				<input type="text" name="searchRoleCname" id="searchRoleCname" class="easyui-validatebox" data-options="validType:'unnormal'"  maxlength="20"/>
				
				<!-- <label>系统：</label>
				<input id="sysCombox" name="sysCode"/> -->
	
				<label>状态：</label>
				<input name="validInd" id = "searchValidInd"/>			
				<a id="button-search" href ="javascript:;" onclick="query()" class="easyui-linkbutton" data-options="iconCls:'icon-search'">搜索</a>
			</div>
		</form>
	</div>
	 <div data-options="region:'center'">
   	   <table id="roleGrid" style="height: 100%"></table>
    </div>
</div>	
	
    <!-- 新增角色 -->
	<div id="roleWin" style="width: 350px;text-align:center;display:none;" >
		<form id="roleFrm" method="post">
			<table class="formTable">
				
				<!-- <tr>
					<th><samp>*</samp>所属系统:</th>
					<td>
					 <input  type="text" 
		        		id="sysCodeCombobox" name="sysCode" data-options="required:true" />   
					</td>
				</tr> -->
				
				<tr>
					<th><samp>*</samp>角色编码:</th>
					<td>
					 <input class="easyui-validatebox" type="text" name="roleCode"
						id="roleCode" data-options="required:true,validType:'code'" maxlength="20" /> 
					</td>
				</tr>
				
				<tr>
					<th><samp>*</samp>角色名称:</th>
					<td>
					 <input class="easyui-validatebox" type="text" name="roleCname" id="roleCname"
		  			       data-options="required:true,validType:'unnormal'" maxlength="20" />  
					</td>
				</tr>
				<tr>
					<th>角色描述:</th>
					<td>
					  <textarea class="easyui-validatebox" name="roleDesc" id="roleDesc" maxlength="300"></textarea>   
					</td>
				</tr>
				<!-- <tr>
					<th><samp>*</samp>角色状态:</th>
					<td>
					  <input  type="text" id="validInd" name="validInd" data-options="required:true" />   
					</td>
				</tr> -->
			</table>
		    <div style="padding:5px;text-align:center;">  
	            <a href="javascript:;" onClick = "addSysRole()" class="easyui-linkbutton" data-options="iconCls:'icon-ok'">保存</a>
	            <a href="javascript:;" onClick = "closeWin('roleWin')" class="easyui-linkbutton" data-options="iconCls:'icon-cancel'">取消</a>  
	     	</div>  
		</form>
	</div> 
	
	
    <!-- 修改角色 -->
	<div id="updateRoleWin" style="width: 350px;text-align:center;display: none;" >
		<form id="updateRoleFrm" method="post">
			
			<table class="formTable">
				<tr>
					<th>角色编码:</th>
					<td>
					  <input class="easyui-validatebox" type="text" name="roleCode"
						id="updateRoleCode" disabled="disabled" />   
					</td>
				</tr>
				<!-- <tr>
					<th><samp>*</samp>所属系统:</th>
					<td>
					 <input  type="text" 
		        	id="updateSysCodeCombobox" name="sysCode" data-options="required:true" disabled="disabled" />   
					</td>
				</tr> -->
				
				<tr>
					<th><samp>*</samp>角色名称:</th>
					<td>
					 <input class="easyui-validatebox" type="text" name="roleCname" id="updateRoleCname"
		         		data-options="required:true,validType:'unnormal'" maxlength="20"/>   
					</td>
				</tr>
				<tr>
					<th>角色描述:</th>
					<td>
					   <textarea class="easyui-validatebox" name="roleDesc" id="updateRoleDesc" 
		       				 maxlength="300"></textarea>  
					</td>
				</tr>
				<tr>
					<th><samp>*</samp>角色状态:</th>
					<td>
					  <input  type="text" id="updateValidInd" name="validInd" data-options="required:true" />
					</td>
				</tr>
			</table>
		    <div style="padding:5px;text-align:center;">  
	            <a href="javascript:;" onClick = "updateSysRole()" class="easyui-linkbutton" data-options="iconCls:'icon-ok'">保存</a>
	            <a href="javascript:;" onClick = "closeWin('updateRoleWin')" class="easyui-linkbutton" data-options="iconCls:'icon-cancel'">取消</a>  
	     	</div>  
		</form>
	</div> 
	 
	
	<div id="addResourcesWin"  data-options="closed:true">
		<!-- 分配资源 -->
		<div class="easyui-layout" data-options="fit:true">
			<div data-options="region:'north'"  style="height: 10%;padding: 5px" >
				<label >角色编码:</label>
	           	<input type="text" name="roleCode" id="showroleCode" disabled="disabled" />
						
	           	<label >角色名称:</label>
	           	<input type="text" name="roleCname" id="showroleCname" disabled="disabled" />
						
	           	<!-- <label>系统名称:</label>
	           	<input type="text" name="sysCname" id="showsysCname" disabled="disabled" />
	           --></div>
			 <div data-options="region:'west'" title="资源" style="height: 60%" >
		           <ul  id="roleResTree" data-options="animate:true,checkbox:true">
          		 	
          		 	</ul>
	           </div>
	           
	           <div data-options="region:'south',border:false" style="text-align:right;padding:5px 0 0;">
	               <a class="easyui-linkbutton" data-options="iconCls:'icon-ok'" href="javascript:void(0)" onclick="addRoleResources()">保存</a>
	               <a class="easyui-linkbutton" data-options="iconCls:'icon-cancel'" href="javascript:void(0)" onclick="closeWin('addResourcesWin')">取消</a>
	           </div>
	      </div>
	</div>
	</body>   
</html>