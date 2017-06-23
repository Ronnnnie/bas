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
		var actualPayPrincipalAmt = '';
		var actualPayinteAmt = '';
		var actualPayPrincipalAmtSum = 0;
		var actualPayinteAmtSum = 0;
		var keepAccountsStatusSum = '';
		for (var i = 0; i < selectedRow.length; i++) {
			actualPayPrincipalAmtSum += selectedRow[i].actualPayPrincipalAmt;
			actualPayinteAmtSum += selectedRow[i].actualPayinteAmt;
			keepAccountsStatusSum += selectedRow[i].keepAccountsStatus;
			if(i==selectedRow.length-1){
				serialNo += selectedRow[i].serialNo;
    			seqId += selectedRow[i].seqId;
    			actualPayPrincipalAmt += checkMoney(selectedRow[i].actualPayPrincipalAmt);
    			actualPayinteAmt += checkMoney(selectedRow[i].actualPayinteAmt);
			}else{
				serialNo += selectedRow[i].serialNo+',';
    			seqId += selectedRow[i].seqId+',';
    			actualPayPrincipalAmt += checkMoney(selectedRow[i].actualPayPrincipalAmt)+',';
    			actualPayinteAmt += checkMoney(selectedRow[i].actualPayinteAmt)+',';
			}
		}
    	$('#onDBAccountingMarkForm').form('load',{
    		seqId : seqId,
			serialNo : serialNo,
			actualPayPrincipalAmt : actualPayPrincipalAmt,
			actualPayinteAmt : actualPayinteAmt,
			actualPayPrincipalAmtSum : actualPayPrincipalAmtSum,
			actualPayinteAmtSum : actualPayinteAmtSum,
			keepAccountsStatusSum : keepAccountsStatusSum,
		});
	}else{
		$.messager.alert('提示', '请至少选择一行数据进行记账!', 'warning');
	}
}

function query() {
		var startKeepaccountsDate = $("#startKeepaccountsDate").datebox('getValue');
		var endKeepaccountsDate = $("#endKeepaccountsDate").datebox('getValue');
		var today = getDate();
		if(startKeepaccountsDate<=today&&endKeepaccountsDate<=today){
			var serialNo = $("#serialNo").textbox('getValue');
			var subProductType = $("#subProductType").combobox('getValue');
			var payType = $("#payType").combobox('getValue');
			var startActualPayDate = $("#startActualPayDate").datebox('getValue');
			var endActualPayDate = $("#endActualPayDate").datebox('getValue');
			var startRegistrationDate = $("#startRegistrationDate").datebox('getValue');
			var endRegistrationDate = $("#endRegistrationDate").datebox('getValue');
			var startApproveTime = $("#startApproveTime").datebox('getValue');
			var endApproveTime = $("#endApproveTime").datebox('getValue');
			var approveStatus = $("#approveStatus").combobox('getValue');
			
			$("#clearanceCertificateGrid").datagrid('options').url = contextPath + "/clientRepaymentServer/queryRepaymentDetail.do";
			
			$("#clearanceCertificateGrid").datagrid('load',{
				startKeepaccountsDate : startKeepaccountsDate,
				endKeepaccountsDate : endKeepaccountsDate,
				serialNo : serialNo,
				subProductType : subProductType,
				payType : payType,
				startActualPayDate : startActualPayDate, 
				endActualPayDate : endActualPayDate, 
				startApproveTime : startApproveTime, 
				endApproveTime : endApproveTime, 
				startRegistrationDate : startRegistrationDate, 
				endRegistrationDate : endRegistrationDate, 
				approveStatus : approveStatus
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
				url : contextPath + "/clientRepaymentServer/queryRepaymentDetail.do",
				type : "POST",
				data : {
					rows: 10,
					page:1,
					startKeepaccountsDate : startKeepaccountsDate,
					endKeepaccountsDate : endKeepaccountsDate,
					serialNo : serialNo,
					subProductType : subProductType,
					payType : payType,
					startActualPayDate : startActualPayDate, 
					endActualPayDate : endActualPayDate, 
					startApproveTime : startApproveTime, 
					endApproveTime : endApproveTime, 
					startRegistrationDate : startRegistrationDate, 
					endRegistrationDate : endRegistrationDate, 
					approveStatus : approveStatus
				},
				success : function(data) {
					var jsondata = eval("(" + data + ")");
					if (jsondata.rows == "") {
						$.messager.alert('提示', '暂无匹配数据!请重新搜索..', 'warning');
						//清空表单数据
						$('#clearanceCertificateGrid').datagrid('loadData', {
							total : 0,
							rows : []
						});
						$('#clearanceCertificateGrid').datagrid('reloadFooter',[{
							keepaccountsDate : '<span style="color:red;">合计</span>',
							actualPayPrincipalAmt : '0.00',
							actualPayinteAmt : '0.00',
							payAmt : '0.00'
						}]);
					}else{
						$('#clearanceCertificateGrid').datagrid('reloadFooter',[{
							keepaccountsDate : '<span style="color:red;">合计</span>',
							actualPayPrincipalAmt : jsondata.rows[0].actualPayPrincipalAmtSum,
							actualPayinteAmt : jsondata.rows[0].actualPayinteAmtSum,
							payAmt : jsondata.rows[0].payAmtSum
						}]);
					}
				},
				error : function() {
					$.messager.alert("操作提示", "系统异常,请稍后重试!", "error");
				}
			});
		}else{
			$.messager.alert("操作提示", "结束日期不能大于当天日期,请重试!", "error");
		}
	}
	
	//窗口赋值
	function accountingMark() {
		$('#accountingMarkdlg').dialog({
			title : '记账确认',
			width : 620,
			height : 420,
			closed : false,
			cache : false,
			modal : true,
			resizable : true
		});
		var startKeepaccountsDate = $("#startKeepaccountsDate").datebox('getValue');
		var endKeepaccountsDate = $("#endKeepaccountsDate").datebox('getValue');
		var serialNo = $("#serialNo").textbox('getValue');
		var subProductType = $("#subProductType").combobox('getValue');
		var payType = $("#payType").combobox('getValue');
		var startActualPayDate = $("#startActualPayDate").datebox('getValue');
		var endActualPayDate = $("#endActualPayDate").datebox('getValue');
		var startApproveTime = $("#startApproveTime").datebox('getValue');
		var endApproveTime = $("#endApproveTime").datebox('getValue');
		var startRegistrationDate = $("#startRegistrationDate").datebox('getValue');
		var endRegistrationDate = $("#endRegistrationDate").datebox('getValue');
		var approveStatus = $("#approveStatus").combobox('getValue');
		$('#accountingMarkForm').form('load',{
			startKeepaccountsDate : startKeepaccountsDate,
			endKeepaccountsDate : endKeepaccountsDate,
			serialNo : serialNo,
			subProductType : subProductType,
			payType : payType,
			startActualPayDate : startActualPayDate, 
			endActualPayDate : endActualPayDate, 
			startApproveTime : startApproveTime, 
			endApproveTime : endApproveTime, 
			startRegistrationDate : startRegistrationDate,
			endRegistrationDate : endRegistrationDate,
			approveStatus : approveStatus
		});
		$('#queryForm').form('submit', {
			url : contextPath + '/clientRepaymentServer/queryRepaymentDetailCount.do',
			success : function(data) {
				var jsondata = eval("(" + data + ")");
				//重新给控件加载数据
  				if (jsondata.contractCount != 0
 						&& jsondata.moneyCount != null) {
  					$("#accountingMarkContractCount").textbox('setValue',jsondata.contractCount);
  					if (jsondata.moneyCount!=0) {
  						$("#accountingMarkMoneyCount").textbox('setValue', jsondata.moneyCount);
					}else{
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
			height : 420,
			closed : false,
			cache : false,
			modal : true,
			resizable : true
		});
		var serialNo = $("#serialNo").textbox('getValue');
		var subProductType = $("#subProductType").combobox('getValue');
		var payType = $("#payType").combobox('getValue');
		var startActualPayDate = $("#startActualPayDate").datebox('getValue');
		var endActualPayDate = $("#endActualPayDate").datebox('getValue');
		var startRegistrationDate = $("#startRegistrationDate").datebox('getValue');
		var endRegistrationDate = $("#endRegistrationDate").datebox('getValue');
		var approveStatus = $("#approveStatus").combobox('getValue');
		$('#approveForm').form('load',{
			serialNo : serialNo,
			subProductType : subProductType,
			payType : payType,
			startActualPayDate : startActualPayDate, 
			endActualPayDate : endActualPayDate,
			startRegistrationDate : startRegistrationDate,
			endRegistrationDate : endRegistrationDate
		});
		$('#queryForm').form('submit', {
			url : contextPath + '/clientRepaymentServer/queryRepaymentDetailCount.do',
			success : function(data) {
				var jsondata = eval("(" + data + ")");
				//重新给控件加载数据
  				if (jsondata.contractCount != 0
 						&& jsondata.moneyCount != null) {
 					$("#approveContractCount").textbox('setValue',jsondata.contractCount);
 					$("#approveMoneyCount").textbox('setValue', jsondata.moneyCount);
 				}else{
 					$("#approveContractCount").textbox('setValue',jsondata.contractCount);
 					$("#approveMoneyCount").textbox('setValue', jsondata.moneyCount);
 					$.messager.alert("操作提示", '暂无数据,请更换查询条件!', "error");
 				}
			},
			onLoadError : function(data) {
				$.messager.progress('close');
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
					url : contextPath + '/clientRepaymentServer/queryContractStatus.do',
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
										selectAccounting(dlg,form)
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
				$.messager.alert("操作提示", '选择日期不能大于当前日期!', "error");
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
		if($("#updateApproveStatus").combobox('getValue')==""){
			$.messager.alert('提示', '审核状态为必填项!', 'error');
		}else{
			$.messager.progress({ title: '正在操作...', msg: '数据审核中...'});
			$('#approveForm').form('submit', {
				url : contextPath + '/clientRepaymentServer/contractApprove.do',
				success : function(data) {
					$.messager.progress('close');
					closeWin('approvedlg');
					var obj = jQuery.parseJSON(data);
					if (obj.obj == "") {
						$.messager.alert('提示', '暂无匹配数据可修改!请重新搜索..', 'warning');
						//清空表单数据
						$('#clearanceCertificateGrid').datagrid('loadData', {
							total : 0,
							rows : []
						});
						$('#clearanceCertificateGrid').datagrid('reloadFooter',[{
							keepaccountsDate : '<span style="color:red;">合计</span>',
							actualPayPrincipalAmt : '0.00',
							actualPayinteAmt : '0.00',
							payAmt : '0.00'
						}]);
					}else{
						$.messager.alert('提示', obj.msg, 'info');
		 				$("#clearanceCertificateGrid").datagrid('loadData', obj.obj);
						$('#clearanceCertificateGrid').datagrid('reloadFooter',[{
							keepaccountsDate : '<span style="color:red;">合计</span>',
							actualPayPrincipalAmt : obj.obj.rows[0].actualPayPrincipalAmtSum,
							actualPayinteAmt : obj.obj.rows[0].actualPayinteAmtSum,
							payAmt : obj.obj.rows[0].payAmtSum
						}]);
					}
				},
				onLoadError : function(data) {
					$.messager.progress('close');
					closeWin('approvedlg');
					$.messager.alert("操作提示", obj.msg, "error");
				}
					});
			    }
			}
		});
	}
	
	function accountMarking(dlg,form){
		$.messager.progress({ title: '正在操作...', msg: '数据记账中...'});
		$('#'+form).form('submit', {
			url : contextPath + '/clientRepaymentServer/accountingMark.do',
			success : function(data) {
				$.messager.progress('close');
				closeWin(dlg);
				var obj = jQuery.parseJSON(data);
				//是否有数据
				if (obj.obj == "") {
					$.messager.alert('提示', '暂无匹配数据可修改!请重新搜索..', 'warning');
					//清空表单数据
					$('#clearanceCertificateGrid').datagrid('loadData', {
						total : 0,
						rows : []
					});
					$('#clearanceCertificateGrid').datagrid('reloadFooter',[{
						keepaccountsDate : '<span style="color:red;">合计</span>',
						actualPayPrincipalAmt : '0.00',
						actualPayinteAmt : '0.00',
						payAmt : '0.00'
					}]);
				}else{
					$.messager.alert('提示', obj.msg, 'info');
	 				$("#clearanceCertificateGrid").datagrid('loadData', obj.obj);
					$('#clearanceCertificateGrid').datagrid('reloadFooter',[{
						keepaccountsDate : '<span style="color:red;">合计</span>',
						actualPayPrincipalAmt : obj.obj.rows[0].actualPayPrincipalAmtSum,
						actualPayinteAmt : obj.obj.rows[0].actualPayinteAmtSum,
						payAmt : obj.obj.rows[0].payAmtSum
					}]);
				}
			},
			onLoadError : function(data) {
				$.messager.progress('close');
				closeWin(dlg);
				$.messager.alert("操作提示", obj.msg, "error");
			}
				});
	}
	
	function selectAccounting(dlg,form){
		var keepAccountsStatusSum = $("#keepAccountsStatusSum").val(); 
		if(keepAccountsStatusSum.indexOf('1')!=-1){
			$.messager.alert('提示', '已记账的合同无法重复记账!请重新选择!', 'error');
			closeWin(dlg);
		}else{
			$.messager.progress({ title: '正在操作...', msg: '数据记账中...'});
			$('#'+form).form('submit', {
				url : contextPath + '/clientRepaymentServer/selectAccountingMark.do',
				success : function(data) {
					$.messager.progress('close');
					closeWin(dlg);
					var obj = jQuery.parseJSON(data);
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
						$('#clearanceCertificateGrid').datagrid('reloadFooter',[{
							keepaccountsDate : '<span style="color:red;">合计</span>',
							actualPayPrincipalAmt : obj.obj.rows[0].actualPayPrincipalAmtSum,
							actualPayinteAmt : obj.obj.rows[0].actualPayinteAmtSum,
							payAmt : obj.obj.rows[0].payAmtSum
						}]);
					}
				},
				onLoadError : function(data) {
					$.messager.progress('close');
					closeWin(dlg);
					$.messager.alert("操作提示", obj.msg, "error");
				}
			});
		}
	}
	
	//窗口赋值
	function cancelAccountingMark() {
		$('#cancelAccountingMarkdlg').dialog({
			title : '记账撤销',
			width : 620,
			height : 420,
			closed : false,
			cache : false,
			modal : true,
			resizable : true
		});
		var startKeepaccountsDate = $("#startKeepaccountsDate").datebox('getValue');
		var endKeepaccountsDate = $("#endKeepaccountsDate").datebox('getValue');
		var serialNo = $("#serialNo").textbox('getValue');
		var subProductType = $("#subProductType").combobox('getValue');
		var payType = $("#payType").combobox('getValue');
		var startActualPayDate = $("#startActualPayDate").datebox('getValue');
		var endActualPayDate = $("#endActualPayDate").datebox('getValue');
		var startApproveTime = $("#startApproveTime").datebox('getValue');
		var endApproveTime = $("#endApproveTime").datebox('getValue');
		var startRegistrationDate = $("#startRegistrationDate").datebox('getValue');
		var endRegistrationDate = $("#endRegistrationDate").datebox('getValue');
		var approveStatus = $("#approveStatus").combobox('getValue');
		$('#cancelAccountingMarkForm').form('load',{
			startKeepaccountsDate : startKeepaccountsDate,
			endKeepaccountsDate : endKeepaccountsDate,
			serialNo : serialNo,
			subProductType : subProductType,
			payType : payType,
			startActualPayDate : startActualPayDate, 
			endActualPayDate : endActualPayDate, 
			startApproveTime : startApproveTime, 
			endApproveTime : endApproveTime, 
			startRegistrationDate : startRegistrationDate,
			endRegistrationDate : endRegistrationDate,
			approveStatus : approveStatus
		});
		$('#queryForm').form('submit', {
			url : contextPath + '/clientRepaymentServer/queryRepaymentDetailCount.do',
			success : function(data) {
				var jsondata = eval("(" + data + ")");
				//重新给控件加载数据
  				if (jsondata.contractCount != 0
 						&& jsondata.moneyCount != null) {
  					$("#cancelAccountingMarkContractCount").textbox('setValue',jsondata.contractCount);
  					if (jsondata.moneyCount!=0) {
  						$("#cancelAccountingMarkMoneyCount").textbox('setValue', jsondata.moneyCount);
					}else{
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
	
	
	function cancelAccountingMarkSubmit(dlg,form) {
		$.messager.confirm('操作提示','您确定要对该些数据进行记账撤销吗?',function(r){   
			if (r){
				$.messager.progress({ title: '正在操作...', msg: '数据记账撤销中...'});
				$('#'+form).form('submit', {
					url : contextPath + '/clientRepaymentServer/cancelAccountingMark.do',
					success : function(data) {
						$.messager.progress('close');
						closeWin(dlg);
						var obj = jQuery.parseJSON(data);
						if(obj.success){
							//是否有数据
							if (obj.obj == "") {
								$.messager.alert('提示', '暂无匹配数据可撤销记账!请重新搜索..', 'warning');
								//清空表单数据
								$('#clearanceCertificateGrid').datagrid('loadData', {
									total : 0,
									rows : []
								});
								$('#clearanceCertificateGrid').datagrid('reloadFooter',[{
									keepaccountsDate : '<span style="color:red;">合计</span>',
									actualPayPrincipalAmt : '0.00',
									actualPayinteAmt : '0.00',
									payAmt : '0.00'
								}]);
							}else{
								$.messager.alert('提示', obj.msg, 'info');
				 				$("#clearanceCertificateGrid").datagrid('loadData', obj.obj);
								$('#clearanceCertificateGrid').datagrid('reloadFooter',[{
									keepaccountsDate : '<span style="color:red;">合计</span>',
									actualPayPrincipalAmt : obj.obj.rows[0].actualPayPrincipalAmtSum,
									actualPayinteAmt : obj.obj.rows[0].actualPayinteAmtSum,
									payAmt : obj.obj.rows[0].payAmtSum
								}]);
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
		var startKeepaccountsDate = $("#startKeepaccountsDate").datebox('getValue');
		var endKeepaccountsDate = $("#endKeepaccountsDate").datebox('getValue');
		var serialNo = $("#serialNo").textbox('getValue');
		var subProductType = $("#subProductType").combobox('getValue');
		var payType = $("#payType").combobox('getValue');
		var startActualPayDate = $("#startActualPayDate").datebox('getValue');
		var endActualPayDate = $("#endActualPayDate").datebox('getValue');
		var startApproveTime = $("#startApproveTime").datebox('getValue');
		var endApproveTime = $("#endApproveTime").datebox('getValue');
		var startRegistrationDate = $("#startRegistrationDate").datebox('getValue');
		var endRegistrationDate = $("#endRegistrationDate").datebox('getValue');
		var approveStatus = $("#approveStatus").combobox('getValue');
		$.ajax({
			url : contextPath + "/clientRepaymentServer/queryRepaymentDetailCount.do",
			type : "POST",
			data : {
				startKeepaccountsDate : startKeepaccountsDate,
				endKeepaccountsDate : endKeepaccountsDate,
				serialNo : serialNo,
				subProductType : subProductType,
				payType : payType,
				startActualPayDate : startActualPayDate, 
				endActualPayDate : endActualPayDate, 
				startApproveTime : startApproveTime, 
				endApproveTime : endApproveTime, 
				startRegistrationDate : startRegistrationDate,
				endRegistrationDate : endRegistrationDate,
				approveStatus : approveStatus
			},
			success : function(data) {
				var jsondata = eval("(" + data + ")");
				//是否有数据
				if (jsondata.contractCount == 0) {
					$.messager.alert('提示', '暂无匹配数据,请更换查询条件!', 'warning');
				}else if (jsondata.contractCount < 500000) {
					exportBefore();
					window.location.href=path+'/clientRepaymentServer/excelExport.do?startKeepaccountsDate='+startKeepaccountsDate+
					'&endKeepaccountsDate='+endKeepaccountsDate+"&serialNo="+serialNo+
					"&subProductType="+subProductType+"&payType="+payType+"&startActualPayDate="+startActualPayDate+"&endActualPayDate="+endActualPayDate
					+"&startApproveTime="+startApproveTime+"&endApproveTime="+endApproveTime+"&startRegistrationDate="+startRegistrationDate
					+"&endRegistrationDate="+endRegistrationDate+"&approveStatus="+approveStatus;
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
	