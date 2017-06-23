<%@ page pageEncoding="utf-8"%>
<%
	String _contextPath = request.getContextPath();
	request.setAttribute("CONTEXT_PATH", _contextPath);

	String _basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ _contextPath + "/";
	request.setAttribute("BASE_PATH", _basePath);
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>凭证底稿</title>
<link rel="shortcut" href="<%=_contextPath%>/images/icon/logo.ico"
	type="image/icon" />
<link rel="icon" href="<%=_contextPath%>/images/icon/logo.ico"
	type="image/icon" />

<script type="text/javascript"
	src="${CONTEXT_PATH}/js/jquery-easyui-1.4.2/jquery.min.js"
	charset="utf-8"></script>
<!--(指定编码方式，防止出现乱码)引入EasyUI中使用的Jquery版本-->
<script type="text/javascript"
	src="${CONTEXT_PATH}/js/jquery-easyui-1.4.2/jquery.easyui.min.js"
	charset="utf-8"></script>
<!--(指定编码方式，防止出现乱码)引入EasyUi文件-->
<script type="text/javascript"
	src="${CONTEXT_PATH}/js/jquery-easyui-1.4.2/locale/easyui-lang-zh_CN.js"></script>
<!--汉化-->

<script type="text/javascript" src="${CONTEXT_PATH}/js/app/include.js" charset="utf-8"></script>
<script type="text/javascript" src="${CONTEXT_PATH}/js/app/loading.js" charset="utf-8"></script>
<script type="text/javascript" src="${CONTEXT_PATH}/js/app/util.js" charset="utf-8"></script>
<script type="text/javascript" src="${CONTEXT_PATH}/js/app/voucher/voucherModel.js" charset="utf-8"></script>

<link rel="stylesheet" type="text/css"
	href="${CONTEXT_PATH}/js/jquery-easyui-1.4.2/themes/bootstrap/easyui.css">
<!--引入CSS样式-->
<link rel="stylesheet" type="text/css"
	href="${CONTEXT_PATH}/js/jquery-easyui-1.4.2/themes/icon.css">
<!--Icon引入-->
<style type="text/css">
.formTable {
	padding: 1px;
}

.formTable th {
	text-align: right;
	width: 90px;
	padding: 5px 0px 5px 0;
}

.formTable td {
	padding: 5px 0 5px 0px;
}

 tr {
	line-height:23px;
	margin-top:23px;
}

.formTable samp {
	vertical-align: middle;
	color: #f00;
}

.datagrid-cell {
	line-height: 28px
}

.datagrid-cell-rownumber {
	height: 28px
}
</style>
<script type="text/javascript">
</script>
</head>
<body>
  <div id="bolckdiv" style="display:none;text-align:center;-moz-opacity: 0.8; opacity:0.80; filter: alpha(opacity=80);width: 100%;height: 
  100%;z-index:1001; top: 0%; left: 0%;position: absolute;background-color: black;"><div id="loading" style="color:#ffffff;margin-top: 80px">
    凭证模板数据上传中,请稍等.....</div>
  </div> 
 <div class="easyui-layout" data-options="fit:true">
    <div data-options="region:'north'" style="text-align:center;margin:0 auto;width:100%;height:20%">
		<form id="uploadModelExcelFrm" method="post"
			enctype="multipart/form-data" style="margin:20 auto">
			<label>上传凭证模板信息：</label>
			<input id="uploadModelExcel" name="file" class="easyui-filebox" style="width: 200px" data-options="prompt:'请选择文件...'" />
			<a	href="#" class="easyui-linkbutton" style="width: 44px; height: 22px" onclick="importModel()">导入</a>
		</form>
    </div>
	 <div data-options="region:'center'">
   	   <table id="salaryCountGrid" style="height: 100%"></table>
    </div>
</div>	
</body>
</html>	