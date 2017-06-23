function edit(){
	var selectedRow = $('#clearanceCertificateGrid').datagrid('getSelections');
	if(selectedRow.length>0){
		$('#batchPaymentsAffirmdlg').dialog({
			title : '回款确认/批量',
			width : 400,
			height : 270,
			closed : false,
			cache : false,
			modal : true,
			resizable : true
		});
		var serialno = '';
		var seqid = '';
		for (var i = 0; i < selectedRow.length; i++) {
			if(i==selectedRow.length-1){
				serialno += isNullOrEmpty(selectedRow[i].serialno);
				seqid += isNullOrEmpty(selectedRow[i].seqid);
			}else{
				serialno += isNullOrEmpty(selectedRow[i].serialno)+',';
				seqid += isNullOrEmpty(selectedRow[i].seqid)+',';
			}
		}
    	$('#batchPaymentsAffirmForm').form('load',{
    		serialno : serialno,
    		seqid : seqid
		});
	}else{
		$.messager.alert('提示', '请至少选择一行数据进行回款!', 'warning');
	}
}

function query() {
	debugger;
		var accordmonth = $("#accordmonth").datebox('getValue');
		var startpaydate = $("#startpaydate").datebox('getValue');
		var endpaydate = $("#endpaydate").datebox('getValue');
		var starttransferpaypaymentday = $("#starttransferpaypaymentday").datebox('getValue');
		var endtransferpaypaymentday = $("#endtransferpaypaymentday").datebox('getValue');
		var startactualpaydate = $("#startactualpaydate").datebox('getValue');
		var endactualpaydate = $("#endactualpaydate").datebox('getValue');
		var startregistrationdate = $("#startregistrationdate").datebox('getValue');
		var endregistrationdate = $("#endregistrationdate").datebox('getValue');
		var subproducttype = $("#subproducttype").combobox('getValue');
		var paytype = $("#paytype").combobox('getValue');
		$("#clearanceCertificateGrid").datagrid('options').url = contextPath + "/xzxdPaymentsServer/queryXZXDPaymentsDetail.do";
		$("#clearanceCertificateGrid").datagrid('load',{
			accordmonth : accordmonth,
			startpaydate : startpaydate,
			endpaydate : endpaydate,
			starttransferpaypaymentday : starttransferpaypaymentday,
			endtransferpaypaymentday : endtransferpaypaymentday,
			startactualpaydate : startactualpaydate,
			endactualpaydate : endactualpaydate,
			startregistrationdate : startregistrationdate,
			endregistrationdate : endregistrationdate,
			subproducttype : subproducttype,
			paytype : paytype
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
			url : contextPath + "/xzxdPaymentsServer/queryXZXDPaymentsDetail.do",
			type : "POST",
			data : {
				rows: 10,
				page:1,
				accordmonth : accordmonth,
				startpaydate : startpaydate,
				endpaydate : endpaydate,
				starttransferpaypaymentday : starttransferpaypaymentday,
				endtransferpaypaymentday : endtransferpaypaymentday,
				startactualpaydate : startactualpaydate,
				endactualpaydate : endactualpaydate,
				startregistrationdate : startregistrationdate,
				endregistrationdate : endregistrationdate,
				subproducttype : subproducttype,
				paytype : paytype
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
					$('#clearanceCertificateGrid').datagrid('reloadFooter',[{
						accordmonth : '<span style="color:red;">合计</span>',
						businesssum : '0.00',
						payprincipalamt : '0.00',
						payinteamt : '0.00',
						sywhze : '0.00',
						payamt : '0.00',
						yhlxfx : '0.00',
						yhbjfx : '0.00'
					}]);
				}else{
					$('#clearanceCertificateGrid').datagrid('reloadFooter',[{
						accordmonth : '<span style="color:red;">合计</span>',
						businesssum : jsondata.rows[0].businesssumtotal,
						payprincipalamt : jsondata.rows[0].payprincipalamtsum,
						payinteamt : jsondata.rows[0].payinteamtsum,
						sywhze : jsondata.rows[0].sywhzesum,
						payamt : jsondata.rows[0].payamtsum,
						yhlxfx : jsondata.rows[0].yhlxfxsum,
						yhbjfx : jsondata.rows[0].yhbjfxsum
					}]);
				}
			},
			error : function() {
				$.messager.alert("操作提示", data.message, "error");
			}
		});
	}
	
	//窗口赋值
	function paymentsAffirm() {
		$('#paymentsAffirmdlg').dialog({
			title : '回款确认',
			width : 360,
			height : 350,
			closed : false,
			cache : false,
			modal : true,
			resizable : true
		});
		var accordmonth = $("#accordmonth").datebox('getValue');
		var startpaydate = $("#startpaydate").datebox('getValue');
		var endpaydate = $("#endpaydate").datebox('getValue');
		var starttransferpaypaymentday = $("#starttransferpaypaymentday").datebox('getValue');
		var endtransferpaypaymentday = $("#endtransferpaypaymentday").datebox('getValue');
		var startactualpaydate = $("#startactualpaydate").datebox('getValue');
		var endactualpaydate = $("#endactualpaydate").datebox('getValue');
		var startregistrationdate = $("#startregistrationdate").datebox('getValue');
		var endregistrationdate = $("#endregistrationdate").datebox('getValue');
		var subproducttype = $("#subproducttype").combobox('getValue');
		var paytype = $("#paytype").combobox('getValue');
		var paytypeshow = "";
		if(paytype!=""){
			if(paytype=="5"){
				paytypeshow="提前还款";
			}else{
				paytypeshow="正常还款";
			}
		}
		$('#paymentsAffirmForm').form('load',{
			accordmonth : accordmonth,
			startpaydate : startpaydate,
			endpaydate : endpaydate,
			starttransferpaypaymentday : starttransferpaypaymentday,
			endtransferpaypaymentday : endtransferpaypaymentday,
			startactualpaydate : startactualpaydate,
			endactualpaydate : endactualpaydate,
			startregistrationdate : startregistrationdate,
			endregistrationdate : endregistrationdate,
			subproducttype : subproducttype,
			paytype : paytype,
			paytypeshow : paytypeshow
		});
		$('#queryForm').form('submit', {
			url : contextPath + '/xzxdPaymentsServer/queryXZXDPaymentsCount.do',
			success : function(data) {
				var jsondata = eval("(" + data + ")");
				//重新给控件加载数据
  				if (jsondata.contractCount != 0) {
 					$("#paymentsAffirmContractCount").textbox('setValue',jsondata.contractCount);
 					$("#paymentsAffirmMoneyCount").textbox('setValue', jsondata.moneyCount);
 					if(jsondata.moneyCount==0){
 						$("#paymentsAffirmMoneyCount").textbox('setValue', '0.0');
 					}
 				}else{
 					$("#paymentsAffirmContractCount").textbox('setValue','0');
 					$("#paymentsAffirmMoneyCount").textbox('setValue', '0.0');
 					$.messager.alert("操作提示", '暂无数据,请更换查询条件!', "warning");
 				}
			},
			onLoadError : function(data) {
				$.messager.alert("操作提示", '查询异常,请稍后重试', "error");
			}
		});
	}
	
	function paymentsAffirmSubmit(dlg,form) {
		$('#'+form).form('submit', {
			url : contextPath + '/xzxdPaymentsServer/queryContractStatus.do',
			success : function(data) {
				//是否有数据
				if(data == "1"){
					$.messager.confirm('操作提示','当前包含已已回款合同,确认是否再次回款操作?',function(r){   
						if (r){
							if(form=='paymentsAffirmForm'){
								paymentsAffirmSubmits(dlg,form);
							}else{
								batchPaymentsAffirmSubmit(dlg,form);
							}
						}
					});
				}else{
					$.messager.confirm('操作提示','您确定要对该些数据进行回款确认吗?',function(r){   
						if (r){
							if(form=='paymentsAffirmForm'){
								paymentsAffirmSubmits(dlg,form);
							}else{
								batchPaymentsAffirmSubmit(dlg,form);
							}
						}
					});
				}
			},
			onLoadError : function(data) {
				$.messager.alert("操作提示", obj.msg, "error");
			}
		});
	}
	
	function paymentsAffirmSubmits(dlg,form){
		$.messager.progress({ title: '正在操作...', msg: '数据记账中...'});
		$('#'+form).form('submit', {
			url : contextPath + '/xzxdPaymentsServer/paymentsAffirm.do',
			success : function(data) {
				$.messager.progress('close');
				closeWin(dlg);
				var obj = jQuery.parseJSON(data);
				$('#clearanceCertificateGrid').datagrid('reloadFooter',[{
					accordmonth : '<span style="color:red;">合计</span>',
					businesssum : obj.obj.rows[0].businesssumtotal,
					payprincipalamt : obj.obj.rows[0].payprincipalamtsum,
					payinteamt : obj.obj.rows[0].payinteamtsum,
					sywhze : obj.obj.rows[0].sywhzesum,
					payamt : obj.obj.rows[0].payamtsum,
					yhlxfx : obj.obj.rows[0].yhlxfxsum,
					yhbjfx : obj.obj.rows[0].yhbjfxsum
				}]);
				//是否有数据
				if (obj.obj.rows == "") {
					$.messager.alert('提示', '暂无匹配数据可修改!请重新搜索..', 'warning');
					//清空表单数据
					$('#clearanceCertificateGrid').datagrid('loadData', {
						total : 0,
						rows : []
					});
				}else{
					$.messager.alert('提示', obj.msg, 'info');
	 				$("#clearanceCertificateGrid").datagrid('loadData', obj.obj);
				}
			},
			onLoadError : function(data) {
				$.messager.progress('close');
				closeWin(dlg);
				$.messager.alert("操作提示", obj.msg, "error");
			}
			});
	}
	
	function batchPaymentsAffirmSubmit(dlg,form){
		$.messager.progress({ title: '正在操作...', msg: '数据记账中...'});
		$('#'+form).form('submit', {
			url : contextPath + '/xzxdPaymentsServer/batchPaymentsAffirm.do',
			success : function(data) {
				$.messager.progress('close');
				closeWin(dlg);
				debugger;
				var obj = jQuery.parseJSON(data);
				alert(obj.obj.rows[0].businesssumtotal);
				$('#clearanceCertificateGrid').datagrid('reloadFooter',[{
					accordmonth : '<span style="color:red;">合计</span>',
					businesssum : obj.obj.rows[0].businesssumtotal,
					payprincipalamt : obj.obj.rows[0].payprincipalamtsum,
					payinteamt : obj.obj.rows[0].payinteamtsum,
					sywhze : obj.obj.rows[0].sywhzesum,
					payamt : obj.obj.rows[0].payamtsum,
					yhlxfx : obj.obj.rows[0].yhlxfxsum,
					yhbjfx : obj.obj.rows[0].yhbjfxsum
				}]);
				//是否有数据
				if (obj.obj.rows == "") {
					$.messager.alert('提示', '暂无匹配数据可修改!请重新搜索..', 'warning');
					//清空表单数据
					$('#clearanceCertificateGrid').datagrid('loadData', {
						total : 0,
						rows : []
					});
				}else{
					$.messager.alert('提示', obj.msg, 'info');
					$("#clearanceCertificateGrid").datagrid('loadData', obj.obj);
				}
			},
			onLoadError : function(data) {
				$.messager.progress('close');
				closeWin(dlg);
				$.messager.alert("操作提示", obj.msg, "error");
			}
		});
	}
	
	function ExportExcel(path){
		var accordmonth = $("#accordmonth").datebox('getValue');
		var startpaydate = $("#startpaydate").datebox('getValue');
		var endpaydate = $("#endpaydate").datebox('getValue');
		var starttransferpaypaymentday = $("#starttransferpaypaymentday").datebox('getValue');
		var endtransferpaypaymentday = $("#endtransferpaypaymentday").datebox('getValue');
		var startactualpaydate = $("#startactualpaydate").datebox('getValue');
		var endactualpaydate = $("#endactualpaydate").datebox('getValue');
		var startregistrationdate = $("#startregistrationdate").datebox('getValue');
		var endregistrationdate = $("#endregistrationdate").datebox('getValue');
		var subproducttype = $("#subproducttype").combobox('getValue');
		var paytype = $("#paytype").combobox('getValue');
		$.ajax({
			url : contextPath + "/xzxdPaymentsServer/queryXZXDPaymentsCount.do",
			type : "POST",
			data : {
				accordmonth : accordmonth,
				startpaydate : startpaydate,
				endpaydate : endpaydate,
				starttransferpaypaymentday : starttransferpaypaymentday,
				endtransferpaypaymentday : endtransferpaypaymentday,
				startactualpaydate : startactualpaydate,
				endactualpaydate : endactualpaydate,
				startregistrationdate : startregistrationdate,
				endregistrationdate : endregistrationdate,
				subproducttype : subproducttype,
				paytype : paytype
			},
			success : function(data) {
				var jsondata = eval("(" + data + ")");
				//是否有数据
				if (jsondata.contractCount < 500000) {
					exportBefore();
					window.location.href=path+'/xzxdPaymentsServer/excelExport.do?accordmonth='+accordmonth+
							'&starttransferpaypaymentday='+starttransferpaypaymentday+"&endtransferpaypaymentday="+endtransferpaypaymentday+
							'&startactualpaydate='+startactualpaydate+'&endactualpaydate='+endactualpaydate+
							'&startregistrationdate='+startregistrationdate+'&endregistrationdate='+endregistrationdate+
							'&subproducttype='+subproducttype+'&paytype='+paytype+'&startpaydate='+startpaydate+'&endpaydate='+endpaydate;
					exportLater();
				}else{
					$.messager.alert('提示', '数据量过大!请更换条件...', 'warning');
				}
			},
			error : function() {
				$.messager.alert('提示', '导出错误,请重试!', 'error');
			}
		});
	}
	
	function isNullOrEmpty(str){
		if(typeof(str) == 'undefined'){
			return '';
		}else{
			return str;
		}
	}
