<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html >
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ include file="../common/include.jsp"%>
<title>菜单管理</title>
<script type="text/javascript" src="${CONTEXT_PATH}/js/app/menuResources.js"></script>
</head>
<body>

<div class="easyui-layout" data-options="fit:true">
   <!-- <div data-options="region:'north'" title=" " style="width:100%;height: 15%;">
   		<form id="filterFrm">
   		<div style="width:95%;margin: 5px;text-align: center;">
			<table >
				<tr>
					<td><span>系统名称：</span></td>
					<td><input type="text" name="sysCname" class="easyui-validatebox"   id="searchsysCname"  data-options="validType:'unnormal'" maxlength="20"/></td>
					
					<td >系统编码：</td>
					<td><input id="searchsysCode" class="easyui-validatebox"  name="sysCode" data-options="validType:'code'" maxlength="20"/></td>
					
					<td >系统分类：</td>
					<td><input style="width: 250px" id="fqsysTypeCname" class="easyui-validatebox"  name="sysTypeCode"/></td>
	
					<td><span >状态：</span></td>
					<td><input name="validInd"  id = "searchValidInd"/></td>				
					<td ><a id="button-search" href ="javascript:;" onclick="query()" class="easyui-linkbutton" data-options="iconCls:'icon-search'">搜索</a></td>
				</tr>
			</table>
		</div>
	  </form>
  </div> -->
   <div data-options="region:'center'">
   	   <table id="menuGrid" style="height: 100%"></table>
   </div>
</div>

	<div id="menuResourcesWin"  class="easyui-dialog" data-options="closed:true,minimizable:false,collapsible:false,maximizable:false">
		<!-- 菜单管理 -->
		<div class="easyui-layout" data-options="fit:true">
			<div data-options="region:'north',split:true"  style="height: 15%;padding: 5px" >
				<div style="margin: 5px;">
		           	<label >系统编码:</label>
		           	<input type="text" name="sysCode" id="showsysCode" disabled="disabled" class="easyui-validatebox" />
							
		           	<label>系统名称:</label>
		           	<input type="text" name="sysCname" id="showsysCname"  disabled="disabled" class="easyui-validatebox" />
	           	</div>
	          </div>
	           <div data-options="region:'center'" style="padding:10px;">
	               	<ul  id="menuResourcesTree" data-options="animate:true">
          		 	
          		 	</ul>
	           </div>
	      </div>
	</div>
	
	
	<!-- 菜单操作-->
	<div id="menuWin"  class="easyui-window" data-options="closed:true,minimizable:false,collapsible:false,maximizable:false" style="width: 350px;text-align:center;" >
		<form id="menuFrm" method="post">
			 <input  type="hidden" name="resourceId" id="resourceId" /> 
			 <table class="formTable">
			 	<tr>
			 		<th><samp>*</samp>名称:</th>
			 		<td>
			 			<input class="easyui-validatebox" type="text" name="resourceName"
						id="resourceName" data-options="required:true,validType:'unnormal'" maxlength="20"/>   
			 		</td>
			 	</tr>
			 	<tr>
			 		<th>编码:</th>
			 		<td>
			 			<input class="easyui-validatebox" type="text" name="resourceCode" data-options="validType:'code'" maxlength="20"/>   
			 		</td>
			 	</tr>
			 	<tr>
			 		<th>路径:</th>
			 		<td>
			 			<input class="easyui-validatebox" type="text" name="url" id="url" maxlength="100"/>    
			 		</td>
			 	</tr>
			 	<tr>
			 		<th>CSS:</th>
			 		<td>
			 			 <input class="easyui-validatebox" type="text" name="resourceCss" id="resourceCss" data-options="validType:'unnormal'" maxlength="20"/>  
			 		</td>
			 	</tr>
			 	<tr>
			 		<th>图标:</th>
			 		<td>
			 			<input type="text" id="resourceIcon"  class="easyui-validatebox"  name="resourceIcon" data-options="validType:'unnormal'" maxlength="50"/>  
			 		</td>
			 	</tr>
			 	<tr>
			 		<th><samp>*</samp>序号:</th>
			 		<td>
			 			 <input  type="text" id="menuIndex" name="menuIndex" class="easyui-validatebox"  data-options="required:true,validType:'number'"  maxlength="2" />   
			 		</td>
			 	</tr>
			 	<tr>
			 		<th><samp>*</samp>级别:</th>
			 		<td>
			 			 <input  type="text" id="menuLevel"  name="menuLevel" class="easyui-validatebox" data-options="required:true,validType:'number'" maxlength="2"  />     
			 		</td>
			 	</tr>
			 </table>  
		    <div style="padding:5px;text-align:center;">  
	            <a href="javascript:;" onClick = "saveMenu()" class="easyui-linkbutton" data-options="iconCls:'icon-ok'">保存</a>
	            <a href="javascript:;" onClick = "closeWin('menuWin')" class="easyui-linkbutton" data-options="iconCls:'icon-cancel'">取消</a>  
	     	</div>  
		</form>
	</div> 
	
	<div id="buttResourcesWin"  class="easyui-dialog" data-options="closed:true">
		<!-- 按钮管理 -->
		<div class="easyui-layout" data-options="fit:true">
			<div data-options="region:'north',split:true"  style="height: 18%;padding: 5px" >
				<div >
					<table >
						<tr>
							<th><span >系统名称：</span></th>
							<td><input type="text" name="sysCname" id="buttshowsysCname"  disabled="disabled" class="easyui-validatebox" /></td>
							
							<th >系统编码：</th>
							<td><input type="text" name="sysCode" id="buttshowsysCode" disabled="disabled" class="easyui-validatebox" /></td>
						</tr>
						<tr>
							<th >按钮名称：</th>
							<td><input id="searchResourceName" type="text"  class="easyui-validatebox" data-options="validType:'unnormal'" maxlength="20" /></td>
							<th >菜单：</th>
							<td><input id="searchMenuId" type="text" /></td>
							<th><span >状态：</span></th>
							<td><input name="validInd"  id = "searchButtValidInd"/></td>				
							<td ><a id="butt-search" href ="javascript:;" onclick="searchButt()" class="easyui-linkbutton" data-options="iconCls:'icon-search'">搜索</a></td>
						</tr>
					</table>
	           	</div>
	          </div>
	           <div data-options="region:'center'">
	               	<table id="buttResourcesGrid" style="height: 100%"></table>
	           </div>
	      </div>
	</div>
	
	
	<!-- 按钮操作-->
	<div id="buttWin"  class="easyui-window" data-options="closed:true,minimizable:false,collapsible:false,maximizable:false" style="width: 350px;text-align:center;" >
		<form id="buttFrm" method="post">
			 <input  type="hidden" name="resourceId" id="buttresourceId" /> 
			 <table class="formTable">
			 	<tr>
			 		<th ><samp>*</samp>菜单：</th>
					<td><input id="buttPMenuId" type="text" name="parentId" data-options="required:true" /></td>
			 	</tr>
			 	<tr>
			 		<th><samp>*</samp>名称:</th>
			 		<td> 
			 			<input class="easyui-validatebox" type="text" name="resourceName"
						 data-options="required:true,validType:'unnormal'" maxlength="20"/>   
			 		</td>
			 	</tr>
			 	<tr>
			 		<th><samp>*</samp>编码:</th>
			 		<td>
			 			<input class="easyui-validatebox" type="text" name="resourceCode" data-options="required:true,validType:'code'" maxlength="20" />   
			 		</td>
			 	</tr>
			 	<tr>
			 		<th>路径:</th>
			 		<td>
			 			<input class="easyui-validatebox" type="text" name="url" maxlength="100"/>    
			 		</td>
			 	</tr>
			 	<tr>
			 		<th>CSS:</th>
			 		<td>
			 			 <input class="easyui-validatebox" type="text" name="resourceCss" data-options="validType:'unnormal'" maxlength="50" />  
			 		</td>
			 	</tr>
			 	<tr>
			 		<th>图标:</th>
			 		<td>
			 			<input  type="text" name="resourceIcon" class="easyui-validatebox"  data-options="validType:'unnormal'" maxlength="50" />  
			 		</td>
			 	</tr>
			 	<tr>
			 		<th><samp>*</samp>序号:</th>
			 		<td>
			 			 <input  type="text" id="menuIndex" name="menuIndex"  class="easyui-validatebox"  data-options="required:true,validType:'number'" maxlength="2"/>   
			 		</td>
			 	</tr>
			 </table>  
		    <div style="padding:5px;text-align:center;">  
	            <a href="javascript:;" onClick = "saveButt()" class="easyui-linkbutton" data-options="iconCls:'icon-ok'">保存</a>
	            <a href="javascript:;" onClick = "closeWin('buttWin')" class="easyui-linkbutton" data-options="iconCls:'icon-cancel'">取消</a>  
	     	</div>  
		</form>
	</div> 
</body>
</html>