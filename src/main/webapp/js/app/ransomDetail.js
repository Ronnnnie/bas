function edit(){
	var selectedRow = $('#clearanceCertificateGrid').datagrid('getSelections');
	if(selectedRow.length>0){
	$('#onDBAccountingMarkdlg').dialog({
		title : '记账确认',
		width : 330,
		height : 430,
		closed : false,
		cache : false,
		modal : true,
		resizable : true
	});
	var contractNo = '';
	var seqId = '';
	for (var i = 0; i < selectedRow.length; i++) {
		if(i==selectedRow.length-1){
			contractNo += isNullOrEmpty(selectedRow[i].contractNo);
			seqId += isNullOrEmpty(selectedRow[i].seqId);
		}else{
			contractNo += isNullOrEmpty(selectedRow[i].contractNo)+',';
			seqId += isNullOrEmpty(selectedRow[i].seqId)+',';
		}
	}
	$('#onDBAccountingMarkForm').form('load',{
		seqId : seqId,
		contractNo : contractNo
	});
	}else{
		$.messager.alert('提示', '请至少选择一行数据进行记账!', 'warning');
	}
}


function query() {
		var startKeepaccountsDate = $("#startKeepaccountsDate").datebox('getValue');
		var endKeepaccountsDate = $("#endKeepaccountsDate").datebox('getValue');
		var startAtoneForDate = $("#startAtoneForDate").datebox('getValue');
		var endAtoneForDate = $("#endAtoneForDate").datebox('getValue');
		var contractNo = $("#contractNo").textbox('getValue');
		var today = getDate();
		if(startKeepaccountsDate <= today && endKeepaccountsDate <= today){
			$("#clearanceCertificateGrid").datagrid('options').url = contextPath + "/ransomServer/queryRepaymentDetail.do";
			$("#clearanceCertificateGrid").datagrid('load',{
				startKeepaccountsDate : startKeepaccountsDate,
				endKeepaccountsDate : endKeepaccountsDate,
				startAtoneForDate : startAtoneForDate, 
				endAtoneForDate : endAtoneForDate,
				contractNo : contractNo
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
				url : contextPath + "/ransomServer/queryRepaymentDetail.do",
				type : "POST",
				data : {
					rows: 10,
					page:1,
					startKeepaccountsDate : startKeepaccountsDate,
					endKeepaccountsDate : endKeepaccountsDate,
					startAtoneForDate : startAtoneForDate, 
					endAtoneForDate : endAtoneForDate,
					contractNo : contractNo
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
							principalRemainingSum : '0.00',
							overDuePrincipal : '0.00',
							overDueInterest : '0.00',
							totalPremium : '0.00',
							premium : '0.00',
							atoneForAmount : '0.00'
						}]);
					}else{
						$('#clearanceCertificateGrid').datagrid('reloadFooter',[{
							keepaccountsDate : '<span style="color:red;">合计</span>',
							principalRemainingSum : jsondata.rows[0].principalRemainingSumCount,
							overDuePrincipal : jsondata.rows[0].overDuePrincipalSum,
							overDueInterest : jsondata.rows[0].overDueInterestSum,
							totalPremium : jsondata.rows[0].totalPremiumSum,
							premium : jsondata.rows[0].premiumSum,
							atoneForAmount : jsondata.rows[0].atoneForAmountSum
						}]);
					}
				},
				error : function() {
					$.messager.alert("操作提示", "系统异常,请稍后重试!", "error");
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
			width : 320,
			height : 410,
			closed : false,
			cache : false,
			modal : true,
			resizable : true
		});
		var startKeepaccountsDate = $("#startKeepaccountsDate").datebox('getValue');
		var endKeepaccountsDate = $("#endKeepaccountsDate").datebox('getValue');
		var contractNo = $("#contractNo").textbox('getValue');
		var startAtoneForDate = $("#startAtoneForDate").datebox('getValue');
		var endAtoneForDate = $("#endAtoneForDate").datebox('getValue');
		$('#accountingMarkForm').form('load',{
			startKeepaccountsDate : startKeepaccountsDate,
			endKeepaccountsDate : endKeepaccountsDate,
			contractNo : contractNo,
			startAtoneForDate : startAtoneForDate, 
			endAtoneForDate : endAtoneForDate,
		});
		$('#queryForm').form('submit', {
			url : contextPath + '/ransomServer/queryRepaymentDetailCount.do',
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
			width : 320,
			height : 410,
			closed : false,
			cache : false,
			modal : true,
			resizable : true
		});
		var contractNo = $("#contractNo").textbox('getValue');
		var startAtoneForDate = $("#startAtoneForDate").datebox('getValue');
		var endAtoneForDate = $("#endAtoneForDate").datebox('getValue');
		$('#approveForm').form('load',{
			contractNo : contractNo,
			startAtoneForDate : startAtoneForDate, 
			endAtoneForDate : endAtoneForDate
		});
		 $('#approveForm').form('submit', {
				url : contextPath + '/ransomServer/queryRepaymentDetailCount.do',
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
					url : contextPath + '/ransomServer/queryContractStatus.do',
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
	$.messager.confirm('操作提示','您确定要对该些数据进行审核吗?',function(r){   
	if (r){
		$.messager.progress({ title: '正在操作...', msg: '数据审核中...'});
		$('#approveForm').form('submit', {
			url : contextPath + '/ransomServer/contractApprove.do',
			success : function(data) {
				$.messager.progress('close');
				closeWin('approvedlg');
				var obj = jQuery.parseJSON(data);
				$('#clearanceCertificateGrid').datagrid('reloadFooter',[{
					keepaccountsDate : '<span style="color:red;">合计</span>',
					principalRemainingSum : obj.obj.rows[0].principalRemainingSumCount,
					overDuePrincipal : obj.obj.rows[0].overDuePrincipalSum,
					overDueInterest : obj.obj.rows[0].overDueInterestSum,
					totalPremium : obj.obj.rows[0].totalPremiumSum,
					premium : obj.obj.rows[0].premiumSum,
					atoneForAmount : obj.obj.rows[0].atoneForAmountSum
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
			url : contextPath + '/ransomServer/accountingMark.do',
			success : function(data) {
				$.messager.progress('close');
				closeWin(dlg);
				var obj = jQuery.parseJSON(data);
				$('#clearanceCertificateGrid').datagrid('reloadFooter',[{
					keepaccountsDate : '<span style="color:red;">合计</span>',
					principalRemainingSum : obj.obj.rows[0].principalRemainingSumCount,
					overDuePrincipal : obj.obj.rows[0].overDuePrincipalSum,
					overDueInterest : obj.obj.rows[0].overDueInterestSum,
					totalPremium : obj.obj.rows[0].totalPremiumSum,
					premium : obj.obj.rows[0].premiumSum,
					atoneForAmount : obj.obj.rows[0].atoneForAmountSum
				}]);
				//是否有数据
				if (obj.obj == "") {
					$.messager.alert('提示', '暂无匹配数据可记账!请重新搜索..', 'warning');
					//清空表单数据
					$('#clearanceCertificateGrid').datagrid('loadData', {
						total : 0,
						rows : []
					});
				}else{
					$.messager.alert('提示', obj.msg, 'info');
	 				$("#clearanceCertificateGrid").datagrid('loadData', obj.obj);
					if(obj.obj.rows[0].moneyCount == 0){
						$("#moneyCount").textbox('setValue','0.0');
					}
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
			url : contextPath + '/ransomServer/selectAccountingMark.do',
			success : function(data) {
				$.messager.progress('close');
				closeWin(dlg);
				var obj = jQuery.parseJSON(data);
				$('#clearanceCertificateGrid').datagrid('reloadFooter',[{
					keepaccountsDate : '<span style="color:red;">合计</span>',
					principalRemainingSum : obj.obj.rows[0].principalRemainingSumCount,
					overDuePrincipal : obj.obj.rows[0].overDuePrincipalSum,
					overDueInterest : obj.obj.rows[0].overDueInterestSum,
					totalPremium : obj.obj.rows[0].totalPremiumSum,
					premium : obj.obj.rows[0].premiumSum,
					atoneForAmount : obj.obj.rows[0].atoneForAmountSum
				}]);
				//是否有数据
				if (obj.obj == "") {
					$.messager.alert('提示', '暂无匹配数据可记账!请重新搜索..', 'warning');
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
			width : 330,
			height : 430,
			closed : false,
			cache : false,
			modal : true,
			resizable : true
		});
		var startKeepaccountsDate = $("#startKeepaccountsDate").datebox('getValue');
		var endKeepaccountsDate = $("#endKeepaccountsDate").datebox('getValue');
		var contractNo = $("#contractNo").textbox('getValue');
		var startAtoneForDate = $("#startAtoneForDate").datebox('getValue');
		var endAtoneForDate = $("#endAtoneForDate").datebox('getValue');
		$('#cancelAccountingMarkForm').form('load',{
			startKeepaccountsDate : startKeepaccountsDate,
			endKeepaccountsDate : endKeepaccountsDate,
			contractNo : contractNo,
			startAtoneForDate : startAtoneForDate, 
			endAtoneForDate : endAtoneForDate,
		});
		$('#queryForm').form('submit', {
			url : contextPath + '/ransomServer/queryRepaymentDetailCount.do',
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
	
	
	function cancelAccountingMarkSubmit(dlg,form) {
		$.messager.confirm('操作提示','您确定要对该些数据进行记账撤销吗?',function(r){   
			if (r){
				$.messager.progress({ title: '正在操作...', msg: '数据记账撤销中...'});
				$('#'+form).form('submit', {
					url : contextPath + '/ransomServer/cancelAccountingMark.do',
					success : function(data) {
						$.messager.progress('close');
						closeWin(dlg);
						var obj = jQuery.parseJSON(data);
						$('#clearanceCertificateGrid').datagrid('reloadFooter',[{
							keepaccountsDate : '<span style="color:red;">合计</span>',
							principalRemainingSum : obj.obj.rows[0].principalRemainingSumCount,
							overDuePrincipal : obj.obj.rows[0].overDuePrincipalSum,
							overDueInterest : obj.obj.rows[0].overDueInterestSum,
							totalPremium : obj.obj.rows[0].totalPremiumSum,
							premium : obj.obj.rows[0].premiumSum,
							atoneForAmount : obj.obj.rows[0].atoneForAmountSum
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
				 				$("#clearanceCertificateGrid").datagrid('loadData', obj.obj);
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
	function isNullOrEmpty(str){
		if(typeof(str) == 'undefined'){
			return '';
		}else{
			return str;
		}
	}