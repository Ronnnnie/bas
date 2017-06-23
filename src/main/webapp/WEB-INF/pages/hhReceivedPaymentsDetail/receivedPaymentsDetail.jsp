<%@ page pageEncoding="utf-8"%>
<%
	String _contextPath = request.getContextPath();
	request.setAttribute("CONTEXT_PATH", _contextPath);

	String _basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ _contextPath + "/";
	request.setAttribute("BASE_PATH", _basePath);
%>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

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
<!-- 缓存用户是否是超级管理员 -->
<input type="hidden" id="isSuperAdmin" value="${isSuperAdmin}">
<!-- 数据网格 -->
<div class="easyui-layout" data-options="fit:true"
	style="width: 100%; height: 100%">
	<div data-options="region:'north'" title=""
		style="width: 100%; height:10%">
		<form id="queryForm" method="POST">
		<div style="width: 90%; margin-top:10px;margin-left:20px">
			<label>合同号:</label> 
				<input class="easyui-textbox" style="width:120px" id="serialNo" name="serialNo" />
			<label style="margin-left: 20px">应还日期:</label>
				 <input id="startPayDate" name="startPayDate" type="text" class="easyui-datebox" style="width: 100px"
						data-options="formatter:myformatter,parser:myparser" /> 至
				 <input	id="endPayDate" name="endPayDate" type="text" class="easyui-datebox" style="width: 100px"
						data-options="formatter:myformatter,parser:myparser" />
			<a style="margin-left:10px;height:24px" class="easyui-linkbutton" data-options="iconCls:'icon-search'" onclick="query()" >搜索</a>
			<a style="margin-left:10px;height:24px" class="easyui-linkbutton" data-options="iconCls:'icon-reload'" onclick="clearForm('queryForm')" >重置</a>
		</div>
		</form>
	</div>

	<div data-options="region:'center'" style="height:500px">
		<div id="tb">
			<table	style="width: 100%; height: 10px; border-left: none; border-bottom: none; border-right: none;">
				<tr>
					<td>
						<!-- 按钮 start -->
						<div id="tb" style="margin-left: 20px">
						    <a href="#"	class="easyui-linkbutton"	data-options="iconCls:'icon-edit',plain:'true'" onclick="accountingMark()" >确认记账</a>
						    <a href="#" class="easyui-linkbutton"	data-options="iconCls:'icon-tip',plain:'true'">Tips</a>
							<label>
								<font size='2' color='red'>
								 	共计匹配合同：
									<input class="easyui-textbox" style="width: 50px" id="contractCount" name="contractCount" />
									条 共计总额:
									<input class="easyui-textbox" style="width: 80px" id="moneyCount" name="moneyCount" />元
								</font>
							</label>
						</div> <!-- 按钮 end -->
					</td>
				</tr>
			</table>
		</div>
		<table id="clearanceCertificateGrid"
			class="easyui-datagrid" title="放款表业务明细"
			style="width: 100%; height: 100%;"
			data-options="
					id:'dg-dopts',
					rownumbers:true,
					loadMsg:'数据加载中,请稍等',
					pagination:true,
					fitColumns:true,
					singleSelect: true,
					toolbar: '#tb',
					pageList : [ 10, 20, 50, 100 ],
					data:{'total':0,'rows':[]},
					method: 'post'
				">
			<thead>
				<tr>
					<th data-options="field:'id',checkbox:true"></th>
					<th data-options="field:'serialNo'" width="80px">合同号期</th>
					<th data-options="field:'payDate'" width="80px">应还日期</th>
					<th data-options="field:'payType'" width="80px">还款类型</th>
					<th data-options="field:'payPrincipalamt'" width="80px">还款本金</th>
					<th data-options="field:'payInteamt'" width="80px">还款利息</th>
					<th data-options="field:'payAmt'" width="80px">总额</th>
					<th data-options="field:'allotDate'" width="60px">划拨日期</th>
				</tr>
			</thead>
		</table>
	</div>
</div>

<div id="accountingMarkdlg" data-options="iconCls:'icon-save'"
	style="width: 600px; height: 400px; padding: 10px">
	<form id="accountingMarkForm" method="post">
		<table id="orgInfoAddTable" style="margin:auto">
			<tbody>
				<tr style="line-height:30px;">
					<th style="text-align: right; font-size: 13">合同号:</th>
					<td><input class="easyui-validatebox" type="text" name="serialNo" id="serialNo"	 maxlength="20" /></td>
				</tr>

				<tr>
					<th style="text-align: right; font-size: 13">应还日期(起):</th>
					<td><input class="easyui-datebox" type="text"
						name="startPayDate" id="startPayDate"
						data-options="formatter:myformatter,parser:myparser"
						maxlength="20" /></td>
				</tr>
				<tr>
					<th style="text-align: right; font-size: 13">应还日期(止):</th>
					<td><input class="easyui-datebox" type="text"
						name="endPayDate" id="endPayDate"
						data-options="formatter:myformatter,parser:myparser"
						maxlength="20" /></td>
				</tr>

				<tr style="line-left:100px;">
					<th style="text-align: right; font-size: 13"><samp
							style="color: red">*</samp>划拨日期:</th>
					<td><input class="easyui-datebox" type="text"
						name="updateDate" id="updateDate"
						data-options="formatter:myformatter,parser:myparser,required:true"
						maxlength="20" />
					</td>
				</tr>
			</tbody>
		</table>
		<div id="EditTop">
			<a style="margin-left:100px" href="javascript:;" onClick = "accountingMarkSubmit()" class="easyui-linkbutton" data-options="iconCls:'icon-ok'">提交</a>
	        <a href="javascript:;" onClick = "closeWin('accountingMarkdlg')" class="easyui-linkbutton" data-options="iconCls:'icon-cancel'">取消</a>  
		</div>
	</form>
</div>

<!-- 数据网格end -->

<script type="text/javascript">
	function query() {
		var serialNo = $("#serialNo").textbox('getValue');
		var startPayDate = $("#startPayDate").datebox('getValue');
		var endPayDate = $("#endPayDate").datebox('getValue');
		
		$("#clearanceCertificateGrid").datagrid('options').url = contextPath + "/hhrRceivedPaymentsServer/queryReceivedPaymentsDetail.do";
		
		$("#clearanceCertificateGrid").datagrid('load',{
			rows : 10,
			page : 1,
			serialNo : serialNo,
			startPayDate : startPayDate,
			endPayDate : endPayDate
		});
		var p = $('#clearanceCertificateGrid').datagrid('getPager'); 
		    $(p).pagination({ 
		        pageSize: 10,//每页显示的记录条数，默认为10 
		        pageList: [5,10,15],//可以设置每页记录条数的列表 
		        beforePageText: '第',//页数文本框前显示的汉字 
		        afterPageText: '页    共 {pages} 页', 
		        displayMsg: '当前显示 {from} - {to} 条记录   共 {total} 条记录' 
		}); 
		
		$.ajax({
			url : contextPath + "/hhrRceivedPaymentsServer/queryReceivedPaymentsDetail.do",
			type : "POST",
			data : {
				rows: 10,
				page:1,
				serialNo : serialNo,
				startPayDate : startPayDate,
				endPayDate : endPayDate
			},
			success : function(data) {
				var jsondata = eval("(" + data + ")");
				//是否有数据
				if (jsondata.rows == "") {
					$.messager.alert('提示', '暂无匹配数据!请重新搜索..', 'warning');
					//清空表单数据
					$('#clearanceCertificateGrid').datagrid('loadData', {
						total : 0,
						rows : []
					});
					$("#contractCount").textbox('setValue',
 							"0");
 					$("#moneyCount").textbox('setValue', 
 							"0.0");
				}
				//重新给控件加载数据
  				if (jsondata.rows[0].contractCount != 0
 						&& jsondata.rows[0].moneyCount != null) {
 					$("#contractCount").textbox('setValue',
 							jsondata.rows[0].contractCount);
 					$("#moneyCount")
 							.textbox('setValue', jsondata.rows[0].moneyCount);
 				} 
				//重新给表格加载数据
				
			},
			error : function() {
				$.messager.alert("操作提示", '查询哈行回款明细失败,请稍后重试或尝试联系系统管理员!', "error");
			}
		});
	}

	//窗口赋值
	function accountingMark() {
		$('#accountingMarkdlg').dialog({
			title : '修改',
			width : 330,
			height : 230,
			closed : false,
			cache : false,
			modal : true,
			resizable : true
		});
		var serialNo = $("#serialNo").textbox('getValue');
		var startPayDate = $("#startPayDate").datebox('getValue');
		var endPayDate = $("#endPayDate").datebox('getValue');
		$('#accountingMarkForm').form('load',{
			serialNo : serialNo,
			startPayDate : startPayDate,
			endPayDate : endPayDate
		});
	}
	
	function accountingMarkSubmit() {
		$('#accountingMarkForm').form('submit', {
			url : contextPath + '/hhrRceivedPaymentsServer/accountingMark.do',
			success : function(data) {
				closeWin('accountingMarkdlg');
				var obj = jQuery.parseJSON(data);
				//是否有数据
				if (obj.obj == "") {
					$.messager.alert('提示', '暂无匹配数据可修改!请重新搜索..', 'warning');
					//清空表单数据
					$('#clearanceCertificateGrid').datagrid('loadData', {
						total : 0,
						rows : []
					});
					$("#contractCount").textbox('setValue',
 							"0");
 					$("#moneyCount").textbox('setValue', 
 							"0.0");
				}else{
					$.messager.alert('提示', obj.msg, 'warning');
 					//重新加载数据
	  				if (obj.obj[0].contractCount != 0
	 						&& obj.obj[0].moneyCount != null) {
	 					$("#contractCount").textbox('setValue',
	 							obj.obj[0].contractCount);
	 					$("#moneyCount")
	 							.textbox('setValue', obj.obj[0].moneyCount);
	 				} 
					//重新给表格加载数据
	 				$("#clearanceCertificateGrid").datagrid('loadData', obj.obj);
				}
			},
			onLoadError : function(data) {
				closeWin('accountingMarkdlg');
				$.messager.alert("操作提示", jsondata.msg, "error");
			}
		});
	}
</script>
