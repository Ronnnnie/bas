<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ include file="../common/include.jsp"%>
<title>管理员管理</title>

<style type="text/css">
	.formdiv {padding: 5px;margin-left: 5px }
</style>
<script type="text/javascript" src="${CONTEXT_PATH}/js/app/sysUser.js"></script>
</head>
<body>
	<div class="easyui-layout" data-options="fit:true">
		<div data-options="region:'north'" title=" " style="width:100%;height: 15%;"> 
				<div style="width:90%;margin: 5px;">
					<label>用户帐号：</label>
					<input type="text" name="userCodeSear" id="userCodeSear" maxlength="20"/>
					
					<label>真实姓名：</label>
					<input id="userCnameSear" name="userCnameSear" maxlength="20"/>
		
					<label>状态：</label>
					<input name="validInd" id = "searchValidInd"/>			
					<a id="button-search" href ="javascript:;" onclick="query()" class="easyui-linkbutton" data-options="iconCls:'icon-search'">搜索</a>
				</div>
			</div>
		 <div data-options="region:'center'">
	   	   <div id="sysUserGrid" style="height: 100%"></div>
	    </div>
	</div>	
	
	<!-- 修改用户信息 -->
	<div id="updateSysUserWin" style="width: 350px;text-align:center;display:none;" >
		<form id="updateSysUserF" method="post">
			<table class="formTable">
				<tr>
					<th><samp>*</samp>用户帐号:</th>
					<td>
					 <input type="text" name="userCode" id="userCode" readonly="readonly" style="background-color:grey"/> 
					</td>
				</tr>
				
				<tr>
					<th><samp>*</samp>真实姓名:</th>
					<td>
					 <input class="easyui-validatebox" type="text" name="userCname"
						id="userCname" data-options="required:true,validType:'unnormal'" maxlength="20" /> 
					</td>
				</tr>
				
				<tr>
					<th><samp>*</samp>角色:</th>
					<td>
					 <input type="text" name="roleCode" id="roleCode" data-options="required:true" />  
					</td>
				</tr>
				<tr>
					<th>管理部门:</th>
					<td>
					 <input type="text" name="deptNo" id="depart" data-options="required:true"/>  
					</td>
				</tr>
				<tr>
					<th>联系电话:</th>
					<td>
					 <input class="easyui-validatebox" type="text" name="userPhone"
						id="userPhone" data-options="required:false,validType:'mobile'" maxlength="11" /> 
					</td>
				</tr>
				<tr>
					<th>常用邮箱:</th>
					<td>
					 <input class="easyui-validatebox" type="text" name="userEmail"
						id="userEmail" data-options="required:false,validType:'unnormal'" maxlength="30" /> 
					</td>
				</tr>
				<tr>
					<th><samp>*</samp>用户状态:</th>
					<td>
					  <input type="text" id="status" name="status" data-options="required:true" />   
					</td>
				</tr>
				<tr>
					<th>密码:</th>
					<td>
					 <input class="easyui-validatebox" type="password" name="pwd"
						id="updatePwd" data-options="required:false,validType:'unnormal'" maxlength="18" minlength="6" /> 
					</td>
				</tr>
				
				<tr>
					<th>重复密码:</th>
					<td>
					 <input class="easyui-validatebox" type="password" name="repwd"
						id="updateRepwd" data-options="required:false" validType="equalTo['updatePwd']"/> 
					</td>
				</tr>
			</table>
		    <div style="padding:5px;text-align:center;">  
	            <a href="javascript:;" onClick = "updateSysUser()" class="easyui-linkbutton" data-options="iconCls:'icon-ok'">保存</a>
	            <a href="javascript:;" onClick = "closeWin('updateSysUserWin')" class="easyui-linkbutton" data-options="iconCls:'icon-cancel'">取消</a>  
	     	</div>  
		</form>
	</div> 
	
	
	<!-- 新增用户 -->
	<div id="userWin" style="width: 350px;text-align:center;display:none;" >
		<form id="userFrm" method="post">
			<table class="formTable">
				<tr>
					<th><samp>*</samp>用户帐号:</th>
					<td>
					 <input class="easyui-validatebox" type="text" name="userCode"
						id="upUserCode" data-options="required:true,validType:'code'" maxlength="20" /> 
					</td>
				</tr>
				
				<tr>
					<th><samp>*</samp>真实姓名:</th>
					<td>
					 <input class="easyui-validatebox" type="text" name="userCname"
						id="upUserCname" data-options="required:true,validType:'unnormal'" maxlength="20" /> 
					</td>
				</tr>
				
				<tr>
					<th><samp>*</samp>密码:</th>
					<td>
					 <input class="easyui-validatebox" type="password" name="pwd"
						id="pwd" data-options="required:true,validType:'unnormal'" maxlength="18" minlength="6" /> 
					</td>
				</tr>
				
				<tr>
					<th><samp>*</samp>重复密码:</th>
					<td>
					 <input class="easyui-validatebox" type="password" name="repwd"
						id="repwd" data-options="required:true" validType="equalTo['pwd']"/> 
					</td>
				</tr>
				<tr>
					<th><samp>*</samp>角色:</th>
					<td>
					 <input type="text" name="upRoleCode" id="upRoleCode" data-options="required:true" />  
					</td>
				</tr>
				<tr>
					<th>管理部门:</th>
					<td>
					 <input type="text" name="deptNo" id="upDepart" data-options="required:true"/>  
					</td>
				</tr>
				<tr>
					<th>联系电话:</th>
					<td>
					 <input class="easyui-validatebox" type="text" name="userPhone"
						id="upUserPhone" data-options="required:false,validType:'mobile'" maxlength="11" /> 
					</td>
				</tr>
				<tr>
					<th>常用邮箱:</th>
					<td>
					 <input class="easyui-validatebox" type="text" name="userEmail"
						id="upUserEmail" data-options="required:false,validType:'unnormal'" maxlength="30" /> 
					</td>
				</tr>
				<tr>
					<th><samp>*</samp>用户状态:</th>
					<td>
					  <input  type="text" id="upStatus" name="status" data-options="required:true" />   
					</td>
				</tr>
			</table>
		    <div style="padding:5px;text-align:center;">  
	            <a href="javascript:;" onClick = "addUser()" class="easyui-linkbutton" data-options="iconCls:'icon-ok'">保存</a>
	            <a href="javascript:;" onClick = "closeWin('userWin')" class="easyui-linkbutton" data-options="iconCls:'icon-cancel'">取消</a>  
	     	</div>  
		</form>
	</div> 
	
	
    <!-- 修改用户 -->
	<!-- <div id="updateRoleWin" style="width: 350px;text-align:center;display: none;" >
		<form id="updateRoleFrm" method="post">
			
			<table class="formTable">
				<tr>
					<th>角色编码:</th>
					<td>
					  <input class="easyui-validatebox" type="text" name="roleCode"
						id="updateRoleCode" disabled="disabled" />   
					</td>
				</tr>
				<tr>
					<th><samp>*</samp>所属系统:</th>
					<td>
					 <input  type="text" 
		        	id="updateSysCodeCombobox" name="sysCode" data-options="required:true" disabled="disabled" />   
					</td>
				</tr>
				
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
	</div>  -->
</body>   
</html>