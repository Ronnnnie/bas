<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ include file="include.jsp"%>
<title>用户系统</title>

<style type="text/css">
	ul,li { padding:0; margin:0; list-style:none; }
	a:hover { color:#F00;}
	#sysList { width:80%; margin:0 auto; clear:both; padding:22px 0 0 0; height:176px;} 
	#sysList li { float:left; text-align:center; line-height:20px; margin:0 0 30px 40px; width:125px; white-space:nowrap; overflow:hidden; display:inline; } 
	#sysList li span { display:block; }
	#sysList li img { width:120px; height:120px; border:1px solid #b5b5b5; } 
	
</style>
<script type="text/javascript">
	function checkSystem(sysCode,sysUrl){
		$.ajax({
			url:contextPath+'/sysUser/checkSystem.do?sysCode='+sysCode,
			type: "POST", 
	        success: function (data) { 
	        	var jsonData = eval("("+data+")");
	        	if(jsonData.success){
					 //校验通过打开页签
					 window.open(sysUrl);
				}else{
					$.messager.alert("操作提示", jsonData.message,"error");
				}
	        }
		});
	}
	
	
</script>
</head>
<body>
	<div class="easyui-layout" data-options="fit:true">
		 <div data-options="region:'center'" title="欢迎使用财务业务账系统" style="text-align: center;">
		 在此插入欢迎图片
<!-- 		 	<ul id="sysList"> -->
<%-- 		 		<c:forEach items="${systems }" var="system"> --%>
<!-- 			 		<li> -->
<%-- 			 			<a href="javascript:;"  onclick="checkSystem('${system.sysCode}','${system.sysUrl }')"> --%>
<%-- 			 				<img alt="" src="${imagesUrl}${system.sysLogo }"/> --%>
<%-- 			 				<span>${system.sysCname}</span> --%>
<!-- 				 		</a> -->
<!-- 			 		</li> -->
<%-- 		 		</c:forEach> --%>
<!-- 		 	</ul> -->
	    </div>
	</div>	
	
</body>   
</html>