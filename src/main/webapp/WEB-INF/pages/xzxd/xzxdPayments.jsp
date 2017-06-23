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
<script type="text/javascript" src="${CONTEXT_PATH}/js/app/xzxdPayments.js?version=20170526" charset="utf-8"></script>

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
		style="width: 100%; height:15%">
		<form id="queryForm" method="post">
		<div style="width: 100%; margin-top:8px">
			<label	style="margin-left: 35px">实付日期:</label>
			 	 <input	id="startactualpaydate" name="startactualpaydate" type="text" class="easyui-datebox" style="width: 110px"
						data-options="formatter:myformatter,parser:myparser"> 至
				 <input	id="endactualpaydate" name="endactualpaydate" type="text" class="easyui-datebox"	style="width: 110px"
						data-options="formatter:myformatter,parser:myparser" />
			<label style="margin-left:10px">统计月份:</label>
				 <input id="accordmonth" name="accordmonth" type="text" class="easyui-datebox" style="width: 110px"
						data-options="formatter:myformatter,parser:myparser" />
			<label style="margin-left:10px">应还款日:</label>
			 	 <input	id="startpaydate" name="startpaydate" type="text" class="easyui-datebox" style="width: 110px"
						data-options="formatter:myformatter,parser:myparser"> 至
				 <input	id="endpaydate" name="endpaydate" type="text" class="easyui-datebox"	style="width: 110px"
						data-options="formatter:myformatter,parser:myparser" />
			<label style="margin-left:10px">产品子类型:</label> 
				 <select id="subproducttype" name="subproducttype" class="easyui-combobox textbox combobox-f combo-f textbox-f" style="width: 110px;">
					<option value="">---请选择---</option>
					<option value="普通POS贷">普通POS贷</option>
					<option value="预约现金贷">预约现金贷</option>
					<option value="无预约现金贷">无预约现金贷</option>
					<option value="车主现金贷">车主现金贷</option>
					<option value="成人教育贷">成人教育贷</option>
					<option value="学生教育贷">学生教育贷</option>
					<option value="小企业贷商户通">小企业贷商户通</option>
					<option value="学生消费贷">学生消费贷</option>
				</select>
				<br/>
			<label style="margin-left: 10px">转让应还款日:</label>
				<input id="starttransferpaypaymentday" name="starttransferpaypaymentday" type="text" class="easyui-datebox" style="width:110px;"
					data-options="formatter:myformatter,parser:myparser" /> 至
				<input id="endtransferpaypaymentday"  name="endtransferpaypaymentday" type="text" class="easyui-datebox"	style="width: 110px"
					data-options="formatter:myformatter,parser:myparser" />	
			<label style="margin-left:10px">还款类型:</label> 
				 <select id="paytype" name="paytype" class="easyui-combobox textbox combobox-f combo-f textbox-f" style="width: 110px;">
					<option value="">---请选择---</option>
					<option value="5">提前还款</option>
					<option value="1">正常还款</option>
				</select>
			<label	style="margin-left: 10px">注册日期:</label>
			 	 <input	id="startregistrationdate" name="startregistrationdate" type="text" class="easyui-datebox" style="width: 110px"
						data-options="formatter:myformatter,parser:myparser"> 至
				 <input	id="endregistrationdate" name="endregistrationdate" type="text" class="easyui-datebox"	style="width: 110px"
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
						    <a href="#"	class="easyui-linkbutton"	data-options="iconCls:'icon-ok',plain:'true'" onclick="paymentsAffirm()" >回款确认</a>
						    <a href="#"	class="easyui-linkbutton"	data-options="iconCls:'icon-edit',plain:'true'" onclick="edit()" >批量回款</a>
							<a href="#" class="easyui-linkbutton" 	data-options="iconCls:'icon-save',plain:'true'" onclick="ExportExcel('${CONTEXT_PATH}')">数据导出</a>
						</div> <!-- 按钮 end -->
					</td>
				</tr>
			</table>
		</div>
		<table id="clearanceCertificateGrid"
			class="easyui-datagrid" title="放款明细"
			style="width: 100%; height: 100%;"
			data-options="
					id:'dg-dopts',
					rownumbers:true,
					loadMsg:'数据加载中,请稍等',
					pagination:true,
					fitColumns:true,
					singleSelect: false,
					toolbar: '#tb',
					pageList : [ 10, 20, 50, 100 ],
					data:{'payamt':0,'rows':[]},
					showFooter:true,
					method: 'post'
				">
			<thead>
				<tr>
				<th data-options="field:'loansno',checkbox:true"></th>
				<th data-options="field:'accordmonth'">统计月份</th>
				<th data-options="field:'serialno'">合同号</th>
				<th data-options="field:'suretype'">客户渠道</th>
				<th data-options="field:'businessmodel'">业务模式</th>
				<th data-options="field:'productid'">产品类型</th>
				<th data-options="field:'subproducttype'">产品子类型</th>
				<th data-options="field:'province'">省</th>
				<th data-options="field:'city'">市</th>
				<th data-options="field:'citycode'">城市编码</th>
				<th data-options="field:'seqid'" >期次</th>
				<th data-options="field:'registrationmonth'">注册月</th>
				<th data-options="field:'registrationdate'">注册日期</th>
				<th data-options="field:'paymonth'">应还款月</th>
				<th data-options="field:'paydate'" >应还款日</th>
				<th data-options="field:'deliverymonth'" >转让月</th>
				<th data-options="field:'deliverydate'" >转让日期</th>
				<th data-options="field:'transferpaypaymentmonth'" >转让应还款月</th>
				<th data-options="field:'transferpaypaymentday'" >转让应还款日</th>
				<th data-options="field:'canceltype'" >是否取消分期期次</th>
				<th data-options="field:'shmonth'" >赎回月</th>
				<th data-options="field:'lpmonth'" >理赔月</th>
				<th data-options="field:'paytype'">还款类型</th>
				<th data-options="field:'debours'" >代垫方</th>
				<th data-options="field:'assetBelong'" >资产所属方</th>
				<th data-options="field:'guaranteeparty'" >保证方</th>
				<th data-options="field:'sponsor'" >被担保方</th>
				<th data-options="field:'sponsortype'" >担保方式</th>
				<th data-options="field:'payprincipalamt'" formatter="thousandthFormatter">应还本金</th>
				<th data-options="field:'payinteamt'" formatter="thousandthFormatter">应还利息</th>
				<th data-options="field:'businesssum'" formatter="thousandthFormatter">贷款本金总额</th>
				<th data-options="field:'sywhze'" formatter="thousandthFormatter">剩余本金总额</th>
				<th data-options="field:'payamt'" formatter="thousandthFormatter">合计</th>
				<th data-options="field:'yhlxfx'" formatter="thousandthFormatter">应还利息罚息</th>
				<th data-options="field:'yhbjfx'" formatter="thousandthFormatter">应还本金罚息</th>
				<th data-options="field:'overduedays'" >逾期天数</th>
				<th data-options="field:'actualpaydate'" >实付日期</th>
				<th data-options="field:'actualpayer'" >实付人</th>
				<th data-options="field:'remark'" >备注</th>
				</tr>
			</thead>
		</table>
	</div>
</div>

<!-- 回款确认 -->
<div id="paymentsAffirmdlg" data-options="iconCls:'icon-save'"
	style="width: 600px; height: 400px; padding: 10px">
	<form id="paymentsAffirmForm" method="post">
		<table id="orgInfoAddTable">
			<tbody>
				<tr style="line-height:0px;">
					<th style="text-align: right; font-size: 13">统计月份:</th>
					<td><input class="easyui-textbox" id="accordmonth" data-options="editable:false" name="accordmonth" style="width:150px" /></td>
				</tr>
				<tr style="line-height:0px;">
				<tr>
					<th style="text-align: right; font-size: 13">应还款日起期:</th>
					<td><input class="easyui-textbox" id="startpaydate" data-options="editable:false" name="startpaydate" style="width:150px" /></td>
				</tr>
				<tr style="line-height:0px;">
					<th style="text-align: right; font-size: 13">应还款日止期:</th>
					<td><input class="easyui-textbox" id="endpaydate" data-options="editable:false" name="endpaydate" style="width:150px" /></td>
				</tr>
				<tr>
					<th style="text-align: right; font-size: 13">实付日期起期:</th>
					<td><input class="easyui-textbox" id="startactualpaydate" data-options="editable:false" name="startactualpaydate" style="width:150px" /></td>
				</tr>
				<tr style="line-height:0px;">
					<th style="text-align: right; font-size: 13">实付日期止期:</th>
					<td><input class="easyui-textbox" id="endactualpaydate" data-options="editable:false" name="endactualpaydate" style="width:150px" /></td>
				</tr>
				<tr style="line-height:0px;">
					<th style="text-align: right; font-size: 13">注册日期起期:</th>
					<td><input class="easyui-textbox" id="startregistrationdate" data-options="editable:false" name="startregistrationdate" style="width:150px" /></td>
				</tr>
				<tr style="line-height:0px;">
					<th style="text-align: right; font-size: 13">注册日期止期:</th>
					<td><input class="easyui-textbox" id="endregistrationdate" data-options="editable:false" name="endregistrationdate" style="width:150px" /></td>
				</tr>
				<tr style="line-height:0px;">
					<th style="text-align: right; font-size: 13">转让应还款日起期:</th>
					<td><input class="easyui-textbox" id="starttransferpaypaymentday" data-options="editable:false" name="starttransferpaypaymentday" style="width:150px" /></td>
				</tr>
				<tr style="line-height:0px;">
					<th style="text-align: right; font-size: 13">转让应还款日止期:</th>
					<td><input class="easyui-textbox" id="endtransferpaypaymentday" data-options="editable:false" name="endtransferpaypaymentday" style="width:150px" /></td>
				</tr>
				<tr style="line-height:20px;">
					<th style="text-align: right; font-size: 13">还款类型</th>
					<td>
						<input id="paytype" name="paytype" type="hidden" style="width:150px" data-options="editable:false"/>
						<input id="paytypeshow" name="paytypeshow" class="easyui-textbox" data-options="editable:false"  style="width:150px" />
					</td>
				</tr>
				<tr style="line-height:20px;">
					<th style="text-align: right; font-size: 13">产品子类型</th>
					<td>
						<input id="subproducttype" name="subproducttype" class="easyui-textbox" data-options="editable:false"  style="width:150px" />
					</td>
				</tr>
				<tr style="line-height:0px;">
					<th style="text-align: right; font-size: 13">匹配合同数:</th>
					<td><input class="easyui-textbox" id="paymentsAffirmContractCount" data-options="editable:false" name="paymentsAffirmContractCount" style="width:150px" /></td>
				</tr>
				<tr style="line-height:20px;">
					<th style="text-align: right; font-size: 13">合同总金额:</th>
					<td><input class="easyui-textbox" id="paymentsAffirmMoneyCount" data-options="editable:false"  name="paymentsAffirmMoneyCount"    style="width:150px" /></td>
				</tr>
				<tr style="line-height:20px;">
					<th style="text-align: right; font-size: 13">实付日期:</th>
					<td><input id="actualpaydate" name="actualpaydate" type="text" class="easyui-datebox" style="width: 150px"
						data-options="formatter:myformatter,parser:myparser,required:true" /></td>
				</tr>
				<tr style="line-height:20px;">
					<th style="text-align: right; font-size: 13">实付人</th>
					<td><input id="actualpayer" name="actualpayer" class="easyui-textbox" data-options="required:true"  style="width:150px" /></td>
				</tr>
				<tr style="line-height:15px;">
					<th style="text-align: right; font-size: 13">备注:</th>
					<td><input class="easyui-textbox" data-options="multiline:true" id="remark" name="remark"  style="width:150px;height:45px"/></td>
				</tr>
			</tbody>
		</table>
		<div id="EditTop" style="margin-top:5px">
			<a style="margin-left:80px" href="javascript:;" onClick = "paymentsAffirmSubmit('paymentsAffirmdlg','paymentsAffirmForm')" class="easyui-linkbutton" data-options="iconCls:'icon-ok'">回款确认</a>
	        <a href="javascript:;" onClick = "closeWin('paymentsAffirmdlg')" class="easyui-linkbutton" data-options="iconCls:'icon-cancel'">回款取消</a>  
		</div>
	</form>
</div>

<div id="batchPaymentsAffirmdlg" data-options="iconCls:'icon-save'"
	style="width: 600px; height: 400px; padding: 10px">
	<form id="batchPaymentsAffirmForm" method="post">
		<table id="orgInfoAddTable">
			<tbody>
				<tr>
					<th style="text-align: right;font-size: 14">期次:</th>
					<td style="text-align: center;"><input id="seqid" name="seqid" class="easyui-textbox" style="width:200px" data-options="editable:false,required:true"/></td>
				</tr>
				<tr>
					<th style="text-align: right; font-size: 13">合同号:</th>
					<td><input id="serialno" name="serialno" class="easyui-textbox" style="width:200px" data-options="editable:false,required:true"/></td>
				</tr>
				
				<tr style="line-height:15px;">
					<th style="text-align: right; font-size: 13">实付日期:</th>
					<td><input id="actualpaydate" name="actualpaydate" type="text" class="easyui-datebox" style="width: 200px"
						data-options="formatter:myformatter,parser:myparser,required:true" /></td>
				</tr>
				
				<tr style="line-height:15px;">
					<th style="text-align: right; font-size: 13">实付人:</th>
					<td><input class="easyui-textbox" id="actualpayer" name="actualpayer" data-options="required:true"  style="width:200px"/></td>
				</tr>
				<tr style="line-height:15px;">
					<th style="text-align: right; font-size: 13">回款备注:</th>
					<td><input class="easyui-textbox" data-options="multiline:true" id="remark" name="remark"  style="width:200px;height:45px"/></td>
				</tr>
			</tbody>
		</table>
		<div id="EditTop" style="margin-top:10px">
			<a style="margin-left:80px" href="javascript:;" onClick = "paymentsAffirmSubmit('batchPaymentsAffirmdlg','batchPaymentsAffirmForm')" class="easyui-linkbutton" data-options="iconCls:'icon-ok'">回款确认</a>
	        <a href="javascript:;" onClick = "closeWin('batchPaymentsAffirmdlg')" class="easyui-linkbutton" data-options="iconCls:'icon-cancel'">取消</a>  
		</div>
	</form>
</div>

<script>
$(function() {      
    $('#accordmonth').datebox({
          onShowPanel : function() {// 显示日趋选择对象后再触发弹出月份层的事件，初始化时没有生成月份层    
        	  debugger;
              span.trigger('click'); // 触发click事件弹出月份层    
              if (!tds)    
                  setTimeout(function() {// 延时触发获取月份对象，因为上面的事件触发和对象生成有时间间隔    
                      tds = p.find('div.calendar-menu-month-inner td');    
                      tds.click(function(e) {    
                          e.stopPropagation(); // 禁止冒泡执行easyui给月份绑定的事件    
                          var year = /\d{4}/.exec(span.html())[0]// 得到年份    
                          , month = parseInt($(this).attr('abbr'), 10) + 1; // 月份    
                          $('#accordmonth').datebox('hidePanel')// 隐藏日期对象    
                          .datebox('setValue', year + '-' + month); // 设置日期的值    
                      });    
                  }, 0);    
          },    
          parser : function(s) {// 配置parser，返回选择的日期    
              if (!s)    
                  return new Date();    
              var arr = s.split('-');    
              return new Date(parseInt(arr[0], 10), parseInt(arr[1], 10) - 1, 1);    
          },    
          formatter : function(d) {    
              var month = d.getMonth();  
              if(month<10){  
                  month = "0"+month;  
              }  
              if (d.getMonth() == 0) {    
                  return d.getFullYear()-1 + '/' + 12;    
              } else {    
                  return d.getFullYear() + '/' + month;    
              }    
          }// 配置formatter，只返回年月    
      });    
      var p = $('#accordmonth').datebox('panel'), // 日期选择对象    
      tds = false, // 日期选择对象中月份    
      span = p.find('span.calendar-text'); // 显示月份层的触发控件    
      
  });  
</script>
