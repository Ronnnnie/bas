<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>财务业务账系统</title>
<%@ include file="include.jsp"%>
<script type="text/javascript">

	function changeFrame(url,obj){
		
		if ($('#tabs').tabs('exists', obj)){
			$('#tabs').tabs('select', obj);
		} else {
			var content = '<iframe scrolling="auto" frameborder="0"  src="'+url+'" style="width:100%;height:490px;"></iframe>';
			$('#tabs').tabs('add',{
				title:obj,
				content:content,
				//href:url,
				closable:true
			});
		}
		
// 		changeOtherColor();
// 		$('#contentFrame').attr("src",url);
// 		$(obj).attr("style","border: 1px solid #99A8BC;background-color: #C4D6EC");
	}
	 function changeOtherColor(){
     	$("#west-panel").find("a[menuA='true']").each(function(){
     		$(this).removeAttr("style");
     	});
     }
</script>
<style type="text/css">
	.header {float:left; background:url(../images/logobq.png) no-repeat left;width: 30%;height: 100%}
	.headbgbq {background:#E7F3FE url('../images/headbgbq.png') repeat; }
	.logout {float: right;padding-right: 20px;padding-top: 3%}
</style>

</head>
<body class="easyui-layout" >
	<div id="north" data-options="region:'north'"
		style="height: 15%;" class="headbgbq">
				<img src="${CONTEXT_PATH}/images/logobq.png" style="height: 95%;width: 355px"/>
				<%-- <a title="访问主页" href="${CONTEXT_PATH}/main/menu.do">
	   				<!-- <span class="header"></span> -->
	   				<img src="${CONTEXT_PATH}/images/logobq.png" style="height: 95%;width: 355px"/>
	   			</a> --%>
   			<span class="logout" >
   				用户:${currentUser} | 姓名：${userName} | 时间：${showDate} | <a href="${CONTEXT_PATH}/logout">安全退出</a>
   			</span>
	</div>

	<div id=west-panel data-options="region:'west',split:true,title:'财务业务账系统'"
		style="width: 15%; padding: 1px;background-color: #F8F8F8">
		<div class="easyui-accordion" data-options="border:false" >
               <c:forEach items="${fMenus}" var="fMenu">
	   			<div id="nav-panel-${fMenu.resourceId}"  title="${fMenu.resourceName}" >
   				<c:forEach items="${sMenus}" var="sMenu">
	   				<c:if test="${fMenu.resourceId == sMenu.key}">
						<c:forEach items="${sMenu.value}" var="spl">
						<!-- 
						<div style="padding: 5px" class="nav-item ${spl.resourceCss}" onclick="changeFrame('<%=_basePath%>${spl.url}',this);">${spl.resourceName}</div>
						 -->
							 &nbsp;&nbsp;
							 <a href="javascript:;" onclick="changeFrame('<%=_basePath%>${spl.url}','${spl.resourceName}');" class="easyui-linkbutton" data-options="plain:true">
							 	<samp>${spl.resourceName}</samp>
							 </a><br>
						</c:forEach>
					</c:if>
		   		</c:forEach>
		   		</div>
   			</c:forEach>
		</div>
	</div>
	
	<div id="center-panel" style="width: 100%;" data-options="region:'center'">
	<div id="tabs" class="easyui-tabs" style="width:auto;height:100%;">
<!-- 		<div title="Home" style="width:auto;height:auto;"> -->
<!-- 		</div> -->
	</div>
	</div>
</body>
</html>
