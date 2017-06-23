function edit(){
	var selectedRow = $('#clearanceCertificateGrid').datagrid('getSelections');
	if(selectedRow.length>0){
		$('#onDBAccountingMarkdlg').dialog({
			title : '记账确认/批量',
			width : 330,
			height : 430,
			closed : false,
			cache : false,
			modal : true,
			resizable : true
		});
		var serialNo = '';
		var seqId = '';
		var assetBelong = '';
		var assetBelongShow = '';
		var payPrincipalamt = '';
		var payInteAmt = '';
		var payPrincipalamtSum = 0;
		var payInteAmtSum = 0;
		for (var i = 0; i < selectedRow.length; i++) {
			payPrincipalamtSum += selectedRow[i].payPrincipalamt;
			payInteAmtSum += selectedRow[i].payInteAmt;
			if(i==selectedRow.length-1){
				serialNo += isNullOrEmpty(selectedRow[i].serialNo);
    			seqId += isNullOrEmpty(selectedRow[i].seqId);
    			assetBelong += isNullOrEmpty(selectedRow[i].assetBelong);
    			assetBelongShow += companyNameFormatter(isNullOrEmpty(selectedRow[i].assetBelong),null);
    			payPrincipalamt += checkMoney(selectedRow[i].payPrincipalamt);
    			payInteAmt += checkMoney(selectedRow[i].payInteAmt);
			}else{
				serialNo += isNullOrEmpty(selectedRow[i].serialNo)+',';
    			seqId += isNullOrEmpty(selectedRow[i].seqId)+',';
    			assetBelong += isNullOrEmpty(selectedRow[i].assetBelong)+',';
    			assetBelongShow += companyNameFormatter(isNullOrEmpty(selectedRow[i].assetBelong),null)+',';
    			payPrincipalamt += checkMoney(selectedRow[i].payPrincipalamt)+',';
    			payInteAmt += checkMoney(selectedRow[i].payInteAmt)+',';
			}
		}
		payPrincipalamtSum = checkMoney(payPrincipalamtSum);
		payInteAmtSum = checkMoney(payInteAmtSum);
    	$('#onDBAccountingMarkForm').form('load',{
    		seqId : seqId,
			serialNo : serialNo,
			assetBelong : assetBelong,
			assetBelongShow : assetBelongShow,
			payPrincipalamt : payPrincipalamt,
			payInteAmt : payInteAmt,
			payPrincipalamtSum : payPrincipalamtSum,
			payInteAmtSum : payInteAmtSum,
		});
	}else{
		$.messager.alert('提示', '请至少选择一行数据进行记账!', 'warning');
	}
}

function query() {
		var startDueDate = $("#startDueDate").datebox('getValue');
		var endDueDate = $("#endDueDate").datebox('getValue');
		var serialNo = $("#serialNo").textbox('getValue');
		var subProductType = $("#subProductType").combobox('getValue');
		var payType = $("#payType").combobox('getValue');
		var startPayDate = $("#startPayDate").datebox('getValue');
		var endPayDate = $("#endPayDate").datebox('getValue');
		var startRegistrationDate = $("#startRegistrationDate").datebox('getValue');
		var endRegistrationDate = $("#endRegistrationDate").datebox('getValue');
		var approveStatus = $("#approveStatus").combobox('getValue');
		var payStatus = $("#payStatus").combobox('getValue');
		var assetBelong = $("#assetBelong").combobox('getValue');
		var today = getDate();
		if(startDueDate <= today && endDueDate <= today){
			$("#clearanceCertificateGrid").datagrid('options').url = contextPath + "/repaymentDetailServer/queryRepaymentDetail.do";
			$("#clearanceCertificateGrid").datagrid('load',{
				startDueDate : startDueDate,
				endDueDate : endDueDate,
				serialNo : serialNo,
				subProductType : subProductType,
				payType : payType,
				startPayDate : startPayDate, 
				endPayDate : endPayDate,  
				startRegistrationDate : startRegistrationDate, 
				endRegistrationDate : endRegistrationDate, 
				approveStatus : approveStatus,
				payStatus : payStatus,
				assetBelong : assetBelong
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
				url : contextPath + "/repaymentDetailServer/queryRepaymentDetail.do",
				type : "POST",
				data : {
					rows: 10,
					page:1,
					startDueDate : startDueDate,
					endDueDate : endDueDate,
					serialNo : serialNo,
					subProductType : subProductType,
					payType : payType,
					startPayDate : startPayDate, 
					endPayDate : endPayDate, 
					startRegistrationDate : startRegistrationDate, 
					endRegistrationDate : endRegistrationDate, 
					approveStatus : approveStatus, 
					payStatus : payStatus,
					assetBelong : assetBelong
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
							keepaccountsDate : '<span style="color:red;">合计</span>',
							payPrincipalamt : '0.00',
							payInteAmt : '0.00',
							waiveintamt : '0.00',
							stampDuty : '0.00',
							payAmt : '0.00'
						}]);
					}else{
						$('#clearanceCertificateGrid').datagrid('reloadFooter',[{
							keepaccountsDate : '<span style="color:red;">合计</span>',
							payPrincipalamt : jsondata.rows[0].payPrincipalamtSum,
							payInteAmt : jsondata.rows[0].payInteAmtSum,
							waiveintamt : jsondata.rows[0].waiveintamtSum,
							stampDuty : jsondata.rows[0].stampDutySum,
							payAmt : jsondata.rows[0].payAmtSum
						}]);
					}
				},
				error : function() {
					$.messager.alert("操作提示", data.message, "error");
				}
			});
		}else{
			$.messager.alert("操作提示", "选择日期不能大于当前日期,请重新选择!", "error");
		}
	}
	
	//窗口赋值
	function accountingMark() {
		$('#accountingMarkdlg').dialog({
			title : '记账确认',
			width : 620,
			height : 430,
			closed : false,
			cache : false,
			modal : true,
			resizable : true
		});
		var startDueDate = $("#startDueDate").datebox('getValue');
		var endDueDate = $("#endDueDate").datebox('getValue');
		var serialNo = $("#serialNo").textbox('getValue');
		var subProductType = $("#subProductType").combobox('getValue');
		var payType = $("#payType").combobox('getValue');
		var startPayDate = $("#startPayDate").datebox('getValue');
		var endPayDate = $("#endPayDate").datebox('getValue');
		var startRegistrationDate = $("#startRegistrationDate").datebox('getValue');
		var endRegistrationDate = $("#endRegistrationDate").datebox('getValue');
		var approveStatus = $("#approveStatus").combobox('getValue');
		var assetBelong = $("#assetBelong").combobox('getValue');
		var assetBelongShow = companyNameFormatter(assetBelong,null);
		$('#accountingMarkForm').form('load',{
			startDueDate : startDueDate,
			endDueDate : endDueDate,
			serialNo : serialNo,
			subProductType : subProductType,
			payType : payType,
			startPayDate : startPayDate, 
			endPayDate : endPayDate, 
			startRegistrationDate : startRegistrationDate, 
			endRegistrationDate : endRegistrationDate, 
			approveStatus : approveStatus,
			assetBelong : assetBelong,
			assetBelongShow : assetBelongShow
		});
		$('#queryForm').form('submit', {
			url : contextPath + '/repaymentDetailServer/queryRepaymentDetailCount.do',
			success : function(data) {
				var jsondata = eval("(" + data + ")");
				//重新给控件加载数据
  				if (jsondata.contractCount != 0
 						&& jsondata.moneyCount != null) {
 					$("#accountingMarkContractCount").textbox('setValue',jsondata.contractCount);
 					$("#accountingMarkMoneyCount").textbox('setValue', jsondata.moneyCount);
 					if(jsondata.moneyCount==0){
 						$("#accountingMarkMoneyCount").textbox('setValue', '0.0');
 					}
 				}else{
 					$("#accountingMarkContractCount").textbox('setValue','0');
 					$("#accountingMarkMoneyCount").textbox('setValue', '0.0');
 					$.messager.alert("操作提示", '暂无数据,请更换查询条件!', "warning");
 				}
			},
			onLoadError : function(data) {
				$.messager.alert("操作提示", '查询异常,请稍后重试', "error");
			}
		});
	}
	
	function contractApprove() {
		$('#approvedlg').dialog({
			title : '修改',
			width : 620,
			height : 430,
			closed : false,
			cache : false,
			modal : true,
			resizable : true
		});
		var startDueDate = $("#startDueDate").datebox('getValue');
		var endDueDate = $("#endDueDate").datebox('getValue');
		var serialNo = $("#serialNo").textbox('getValue');
		var subProductType = $("#subProductType").combobox('getValue');
		var payType = $("#payType").combobox('getValue');
		var startPayDate = $("#startPayDate").datebox('getValue');
		var endPayDate = $("#endPayDate").datebox('getValue');
		var startRegistrationDate = $("#startRegistrationDate").datebox('getValue');
		var endRegistrationDate = $("#endRegistrationDate").datebox('getValue');
		var approveStatus = $("#approveStatus").combobox('getValue');
		var assetBelong = $("#assetBelong").combobox('getValue');
		var assetBelongShow = companyNameFormatter(assetBelong,null);
		$('#approveForm').form('load',{
			startDueDate : startDueDate,
			endDueDate : endDueDate,
			serialNo : serialNo,
			subProductType : subProductType,
			payType : payType,
			startPayDate : startPayDate, 
			endPayDate : endPayDate, 
			startRegistrationDate : startRegistrationDate, 
			endRegistrationDate : endRegistrationDate, 
			approveStatus : approveStatus,
			assetBelong : assetBelong,
			assetBelongShow : assetBelongShow
		});
		 $('#queryForm').form('submit', {
			url : contextPath + '/repaymentDetailServer/queryRepaymentDetailCount.do',
			success : function(data) {
				var jsondata = eval("(" + data + ")");
				//重新给控件加载数据
  				if (jsondata.contractCount != 0
 						&& jsondata.moneyCount != null) {
 					$("#approveContractCount").textbox('setValue',jsondata.contractCount);
 					$("#approveMoneyCount").textbox('setValue', jsondata.moneyCount);
 					if(jsondata.moneyCount==0){
 						$("#approveMoneyCount").textbox('setValue', '0.0');
 					}
 				}else{
 					$("#approveContractCount").textbox('setValue',jsondata.contractCount);
 					$("#approveMoneyCount").textbox('setValue', jsondata.moneyCount);
 					$.messager.alert("操作提示", '暂无数据,请更换查询条件!', "error");
 				}
			},
			onLoadError : function(data) {
				$.messager.alert("操作提示", '查询异常,请稍后重试', "error");
			}
		}); 
	}
	
	function accountingMarkSubmit(dlg,form) {
		var update = null;
		if(form == 'accountingMarkForm'){
			update = $("#jzUpdateDate").datebox('getValue');
		}else{
			update = $("#jzsUpdateDate").datebox('getValue');
		}
		if(update!=''){
			var today = getDate();
			if(update<=today){
				$('#'+form).form('submit', {
					url : contextPath + '/repaymentDetailServer/queryContractStatus.do',
					success : function(data) {
						//是否有数据
						if(data == "1"){
							$.messager.alert("操作提示", '已记账的合同禁止重复记账!', "error");
						}else{
							$.messager.confirm('操作提示','您确定要对该些数据进行记账确认吗?',function(r){   
								if (r){
									if(form=='accountingMarkForm'){
										accountMarking(dlg,form);
									}else{
										selectAccountMarking(dlg,form);
									}
								}
								});
						}
					},
					onLoadError : function(data) {
						$.messager.alert("操作提示", obj.msg, "error");
					}
				});
			}else{
				$.messager.alert("操作提示", '记账日期不能大于当前日期!', "error");
			}
		}else{
			$.messager.alert("操作提示", '记账日期不能为空!', "error");
		}
	}
	
	function approveSubmit() {
		var approvetime = $("#approveTime").datebox('getValue');
		if(approvetime=='' || approvetime==null) {
			$.messager.alert("操作提示", '审核日期不能为空!', "error");
			return;
		}
		$.messager.confirm('操作提示','您确定要对该些数据进行审核吗?',function(r){   
			if (r){
				$.messager.progress({ title: '正在操作...', msg: '数据审核中...'});
				$('#approveForm').form('submit', {
					url : contextPath + '/repaymentDetailServer/contractApprove.do',
					success : function(data) {
						$.messager.progress('close');
						closeWin('approvedlg');
						var obj = jQuery.parseJSON(data);
						$('#clearanceCertificateGrid').datagrid('reloadFooter',[{
							keepaccountsDate : '<span style="color:red;">合计</span>',
							payPrincipalamt : obj.obj.rows[0].payPrincipalamtSum,
							payInteAmt : obj.obj.rows[0].payInteAmtSum,
							waiveintamt : obj.obj.rows[0].waiveintamtSum,
							stampDuty : obj.obj.rows[0].stampDutySum,
							payAmt : obj.obj.rows[0].payAmtSum
						}]);
						//是否有数据
						if (obj.obj == "") {
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
						closeWin('approvedlg');
						$.messager.alert("操作提示", obj.msg, "error");
					}
				});
		    }
		});
	}
	
	function accountMarking(dlg,form){
		$.messager.progress({ title: '正在操作...', msg: '数据记账中...'});
		$('#'+form).form('submit', {
			url : contextPath + '/repaymentDetailServer/accountingMark.do',
			success : function(data) {
				$.messager.progress('close');
				closeWin(dlg);
				var obj = jQuery.parseJSON(data);
				$('#clearanceCertificateGrid').datagrid('reloadFooter',[{
					keepaccountsDate : '<span style="color:red;">合计</span>',
					payPrincipalamt : obj.obj.rows[0].payPrincipalamtSum,
					payInteAmt : obj.obj.rows[0].payInteAmtSum,
					waiveintamt : obj.obj.rows[0].waiveintamtSum,
					stampDuty : obj.obj.rows[0].stampDutySum,
					payAmt : obj.obj.rows[0].payAmtSum
				}]);
				//是否有数据
				debugger;
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
	
	function selectAccountMarking(dlg,form){
		$.messager.progress({ title: '正在操作...', msg: '数据记账中...'});
		$('#'+form).form('submit', {
			url : contextPath + '/repaymentDetailServer/selectAccountingMark.do',
			success : function(data) {
				$.messager.progress('close');
				closeWin(dlg);
				var obj = jQuery.parseJSON(data);
				
				$('#clearanceCertificateGrid').datagrid('reloadFooter',[{
					keepaccountsDate : '<span style="color:red;">合计</span>',
					payPrincipalamt : obj.obj.rows[0].payPrincipalamtSum,
					payInteAmt : obj.obj.rows[0].payInteAmtSum,
					waiveintamt : obj.obj.rows[0].waiveintamtSum,
					stampDuty : obj.obj.rows[0].stampDutySum,
					payAmt : obj.obj.rows[0].payAmtSum
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
	
	//窗口赋值
	function cancelAccountingMark() {
		$('#cancelAccountingMarkdlg').dialog({
			title : '记账撤销',
			width : 620,
			height : 430,
			closed : false,
			cache : false,
			modal : true,
			resizable : true
		});
		var startDueDate = $("#startDueDate").datebox('getValue');
		var endDueDate = $("#endDueDate").datebox('getValue');
		var serialNo = $("#serialNo").textbox('getValue');
		var subProductType = $("#subProductType").combobox('getValue');
		var payType = $("#payType").combobox('getValue');
		var startPayDate = $("#startPayDate").datebox('getValue');
		var endPayDate = $("#endPayDate").datebox('getValue');
		var startRegistrationDate = $("#startRegistrationDate").datebox('getValue');
		var endRegistrationDate = $("#endRegistrationDate").datebox('getValue');
		var approveStatus = $("#approveStatus").combobox('getValue');
		var assetBelong = $("#assetBelong").combobox('getValue');
		var assetBelongShow = companyNameFormatter(assetBelong,null);
		$('#cancelAccountingMarkForm').form('load',{
			startDueDate : startDueDate,
			endDueDate : endDueDate,
			serialNo : serialNo,
			subProductType : subProductType,
			payType : payType,
			startPayDate : startPayDate, 
			endPayDate : endPayDate, 
			startRegistrationDate : startRegistrationDate, 
			endRegistrationDate : endRegistrationDate, 
			approveStatus : approveStatus,
			assetBelong : assetBelong,
			assetBelongShow : assetBelongShow
		});
		$('#queryForm').form('submit', {
			url : contextPath + '/repaymentDetailServer/queryRepaymentDetailCount.do',
			success : function(data) {
				var jsondata = eval("(" + data + ")");
				//重新给控件加载数据
  				if (jsondata.contractCount != 0
 						&& jsondata.moneyCount != null) {
 					$("#cancelAccountingMarkContractCount").textbox('setValue',jsondata.contractCount);
 					$("#cancelAccountingMarkMoneyCount").textbox('setValue', jsondata.moneyCount);
 					if(jsondata.moneyCount==0){
 						$("#cancelAccountingMarkMoneyCount").textbox('setValue', '0.0');
 					}
 				}else{
 					$("#cancelAccountingMarkContractCount").textbox('setValue','0');
 					$("#cancelAccountingMarkMoneyCount").textbox('setValue', '0.0');
 					$.messager.alert("操作提示", '暂无数据,请更换查询条件!', "warning");
 				}
			},
			onLoadError : function(data) {
				$.messager.alert("操作提示", '查询异常,请稍后重试', "error");
			}
		});
	}
	
	
	function cancelAccountingMarkCheck(dlg,form) {
		if(dlg=='cancelAccountingMarkdlg'){
			cancelAccountingMarkSubmit(dlg,form);
		}else{
			batchCancelAccountingMarkSubmit(dlg,form);
		}
	}
	
	function cancelAccountingMarkSubmit(dlg,form){
		$.messager.confirm('操作提示','您确定要对该些数据进行记账撤销吗?',function(r){   
			if (r){
				$.messager.progress({ title: '正在操作...', msg: '数据记账撤销中...'});
				$('#'+form).form('submit', {
					url : contextPath + '/repaymentDetailServer/cancelAccountingMark.do',
					success : function(data) {
						debugger;
						$.messager.progress('close');
						closeWin(dlg);
						var obj = jQuery.parseJSON(data);
						if(obj.success){
							//是否有数据
							if (obj.obj == 0) {
								$.messager.alert('提示', '暂无匹配数据可撤销记账!请重新搜索..', 'warning');
							}else{
								$.messager.alert('提示', obj.msg, 'info');
								query();
							}
						}else{
							$.messager.alert("操作提示", obj.message, "error");
						}
					},
					onLoadError : function(data) {
						$.messager.progress('close');
						closeWin(dlg);
						$.messager.alert("操作提示", obj.msg, "error");
					}
				});
			}
		});
	}
	
	function batchCancelAccountingMarkSubmit(dlg,form){
		$.messager.confirm('操作提示','您确定要对该些数据进行记账撤销吗?',function(r){   
			if (r){
				$.messager.progress({ title: '正在操作...', msg: '数据记账撤销中...'});
				$('#'+form).form('submit', {
					url : contextPath + '/repaymentDetailServer/batchCancelAccountingMark.do',
					success : function(data) {
						debugger;
						$.messager.progress('close');
						closeWin(dlg);
						var obj = jQuery.parseJSON(data);
						$('#clearanceCertificateGrid').datagrid('reloadFooter',[{
							keepaccountsDate : '<span style="color:red;">合计</span>',
							payPrincipalamt : obj.obj.rows[0].payPrincipalamtSum,
							payInteAmt : obj.obj.rows[0].payInteAmtSum,
							waiveintamt : obj.obj.rows[0].waiveintamtSum,
							stampDuty : obj.obj.rows[0].stampDutySum,
							payAmt : obj.obj.rows[0].payAmtSum
						}]);
						if(obj.success){
							//是否有数据
							if (obj.obj == "") {
								$.messager.alert('提示', '暂无匹配数据可撤销记账!请重新搜索..', 'warning');
								//清空表单数据
								$('#clearanceCertificateGrid').datagrid('loadData', {
									total : 0,
									rows : []
								});
							}else{
								$.messager.alert('提示', obj.msg, 'info');
								$("#clearanceCertificateGrid").datagrid('loadData', obj.obj.rows);
							}
						}else{
							$.messager.alert("操作提示", obj.message, "error");
						}
					},
					onLoadError : function(data) {
						$.messager.progress('close');
						closeWin(dlg);
						$.messager.alert("操作提示", obj.msg, "error");
					}
				});
			}
		});
	}
	
	
	function exportExcel(path){
		var startDueDate = $("#startDueDate").datebox('getValue');
		var endDueDate = $("#endDueDate").datebox('getValue');
		var serialNo = $("#serialNo").textbox('getValue');
		var subProductType = $("#subProductType").combobox('getValue');
		var payType = $("#payType").combobox('getValue');
		var startPayDate = $("#startPayDate").datebox('getValue');
		var endPayDate = $("#endPayDate").datebox('getValue');
		var startRegistrationDate = $("#startRegistrationDate").datebox('getValue');
		var endRegistrationDate = $("#endRegistrationDate").datebox('getValue');
		var approveStatus = $("#approveStatus").combobox('getValue');
		var payStatus = $("#payStatus").combobox('getValue');
		var assetBelong = $("#assetBelong").combobox('getValue');
		$.ajax({
			url : contextPath + "/repaymentDetailServer/queryRepaymentDetailCount.do",
			type : "POST",
			data : {
				startDueDate : startDueDate,
				endDueDate : endDueDate,
				serialNo : serialNo,
				subProductType : subProductType,
				payType : payType,
				startPayDate : startPayDate, 
				endPayDate : endPayDate,  
				startRegistrationDate : startRegistrationDate, 
				endRegistrationDate : endRegistrationDate, 
				approveStatus : approveStatus,
				payStatus : payStatus,
				assetBelong : assetBelong
			},
			success : function(data) {
				var jsondata = eval("(" + data + ")");
				//是否有数据
				if (jsondata.contractCount == 0) {
					$.messager.alert('提示', '暂无匹配数据,请更换查询条件!', 'warning');
				}else if (jsondata.contractCount < 500000) {
					exportBefore();
					window.location.href=path+'/repaymentDetailServer/excelExport.do?startDueDate='+startDueDate+
					'&endDueDate='+endDueDate+"&serialNo="+serialNo+"&subProductType="+subProductType+
					"&payType="+payType+"&startPayDate="+startPayDate+"&endPayDate="+endPayDate+"&startRegistrationDate="+startRegistrationDate+
					"&endRegistrationDate="+endRegistrationDate+"&approveStatus="+approveStatus+"&payStatus="+payStatus+"&assetBelong="+assetBelong;
					exportLater();
				}else{
					$.messager.alert('提示', '数据量过大!请更换查询条件...', 'warning');
				}
			},
			error : function() {
				$.messager.alert('提示', '导出失败,请稍后再试...', 'error');
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