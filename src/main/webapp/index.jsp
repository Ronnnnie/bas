
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<html>
<head>
<title>系统主页</title>
	<script type="text/javascript">
	window.location.href = "<%=path%>/main/menu.do";
	</script>
</head>
<body>

</body>
</html>
