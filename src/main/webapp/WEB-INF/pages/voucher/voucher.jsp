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
<script type="text/javascript" src="${CONTEXT_PATH}/js/app/voucher/voucher.js" charset="utf-8"></script>

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
.ProgressBar {
    vertical-align:center;
    text-align:center;
    margin:0 auto;
	margin-top:3px;
	position: relative;
	width: 200px; /* 宽度 */
	border: 1px solid #B1D632;
	padding: 1px;
}

.ProgressBar div {
	display: block;
	position: relative;
	background: #B1D632;
	color: #333333;
	height: 20px; /* 高度 */
	line-height: 20px; /* 必须和高度一致，文本才能垂直居中 */
}

.ProgressBar div span {
	position: absolute;
	width: 200px; /* 宽度 */
	text-align: center;
	font-weight: bold;
}
</style>
<script type="text/javascript">
		$(function(){
			$('#cc').combo({
				required:true,
				editable:false
			});
			$('#sp').appendTo($('#cc').combo('panel'));
			$('#sp input').click(function(){
				var v = $(this).val();
				var s = $(this).next('span').text();
				$('#cc').combo('setValue', v).combo('setText', s).combo('hidePanel');
			});
		});
		function exportExcel(path) {
			var vouchers=$('#voucher').combobox('getValues');
			$("#bolckdiv").css("display", "block");
			//getVoucherScrollBar();
			flagInterval= setInterval('getVoucherScrollBar()',1000);
			window.location.href=path+'/voucher/exportVoucher.do?vouchers='+vouchers.toString();
		}
		/* function getAllVoucherModels(){
			$.ajax({
				url : contextPath + "/voucher/getAllVoucherModels.do",
				type : "POST",
				success : function(data) {
					console.log("tanglin"+date.obj);
				},
				error : function() {
					$.messager.alert("操作提示", data.message, "error");
				}
			});
		} */
	</script>
</head>
<body>
  <div id="bolckdiv" style="display:none;text-align:center;-moz-opacity: 0.7; opacity:0.70; filter: alpha(opacity=70);width: 100%;height: 
  100%;z-index:1001; top: 0%; left: 0%;position: absolute;background-color: black;"><div id="loading" style="color:#ffffff;margin-top: 10px">
    数据导出中,请稍等.....</div>
		<div id="ProgressBars" class="ProgressBar" style="display: none;width:80%;vertical-align: center;text-align: center">
			<div id="PBar" style="width:0;margin-top: 0px">
				<span id="pencent" style="width:15%;text-align:center;margin:0 auto;"></span>
			</div>
		</div>
		<div id="rollContent" style="margin-top:20px;font-size: 13px;color: blue;"></div>
  </div> 
  <div id="voucher-img" style="margin-left:530px;margin-top:30px;font-size: 13px;color: black;"></div>
 <div class="easyui-layout" data-options="fit:true">
	<div data-options="region:'north'" style="width:100%;height: 15%;"> 
		<div style="width:50%;text-align:center;margin:15 auto;">
		<label>凭证类型:</label>
			<!-- <select class="easyui-combobox" id="voucher" data-options="multiple:true,multiline:true" style="width:200px;height:30px">
				<option value="sh001">实还表凭证(1)</option>
				<option value="sh002">实还表凭证(2)</option>
			</select> -->
			<input class="easyui-combobox"  
			   width="100%"  
			   id="voucher"  
			   name="mallId"  
			   url="${CONTEXT_PATH}/voucher/getAllVoucherModels.do"   
			   valueField="modelNo"   
			   textField="modelName"   
			   multiple="false" 
			   multiline="false"   
			   panelHeight="auto"  
			   />
			<a id="button-search" href ="javascript:;" onclick="exportExcel('${CONTEXT_PATH}')" class="easyui-linkbutton" data-options="iconCls:'icon-redo'">导出excel</a>
		</div>
	</div>
	 <div data-options="region:'center'">
   	   <table id="salaryCountGrid" style="height: 100%"></table>
    </div>
</div>	
</body>
</html>