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
	var sequence = '';
	for (var i = 0; i < selectedRow.length; i++) {
		if(i==selectedRow.length-1){
			contractNo += isNullOrEmpty(selectedRow[i].contractNo);
			sequence += isNullOrEmpty(selectedRow[i].sequence);
		}else{
			contractNo += isNullOrEmpty(selectedRow[i].contractNo)+',';
			sequence += isNullOrEmpty(selectedRow[i].sequence)+',';
		}
	}
	$('#onDBAccountingMarkForm').form('load',{
		sequence : sequence,
		contracts : contractNo
	});
	}else{
		$.messager.alert('提示', '请至少选择一行数据进行记账!', 'warning');
	}
}

function myformatter(date) {
		var y = date.getFullYear();
		var m = date.getMonth() + 1;
		var d = date.getDate();
		return y + '/' + (m < 10 ? ('0' + m) : m) + '/'
				+ (d < 10 ? ('0' + d) : d);
	}
	function myparser(s) {
		if (!s)
			return new Date();
		var ss = (s.split('/'));
		var y = parseInt(ss[0], 10);
		var m = parseInt(ss[1], 10);
		var d = parseInt(ss[2], 10);
		if (!isNaN(y) && !isNaN(m) && !isNaN(d)) {
			return new Date(y, m - 1, d);
		} else {
			return new Date();
		}
	}

	function query() {
		var startKeepAccountsTime = $("#startKeepAccountsTime").datebox('getValue');
		var endKeepAccountsTime = $("#endKeepAccountsTime").datebox('getValue');
		var contracts = $("#contracts").textbox('getValue');
		var startRepayDate = $("#startRepayDate").datebox('getValue');
		var endRepayDate = $("#endRepayDate").datebox('getValue');
		var startRegistrationDate = $("#startRegistrationDate").datebox('getValue');
		var endRegistrationDate = $("#endRegistrationDate").datebox('getValue');
		var today = getDate();
		if(startKeepAccountsTime <= today && endKeepAccountsTime <= today){
			$("#clearanceCertificateGrid").datagrid('options').url = contextPath + "/jyReceivedPaymentsServer/queryJYReceivedPaymentsDetail.do";
			$("#clearanceCertificateGrid").datagrid('load',{
				startKeepAccountsTime : startKeepAccountsTime,
				endKeepAccountsTime : endKeepAccountsTime,
				contracts : contracts,
				startRepayDate : startRepayDate, 
				endRepayDate : endRepayDate,
				startRegistrationDate : startRegistrationDate,
				endRegistrationDate : endRegistrationDate
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
				url : contextPath + "/jyReceivedPaymentsServer/queryJYReceivedPaymentsDetail.do",
				type : "POST",
				data : {
					rows: 10,
					page:1,
					startKeepAccountsTime : startKeepAccountsTime,
					endKeepAccountsTime : endKeepAccountsTime,
					contracts : contracts,
					startRepayDate : startRepayDate, 
					endRepayDate : endRepayDate,
					startRegistrationDate : startRegistrationDate,
					endRegistrationDate : endRegistrationDate
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
							keepAccountsTime : '<span style="color:red;">合计</span>',
							payprinciPalamt : '0.00',
							payInteamt : '0.00',
							payment : '0.00',
							loanAmount : '0.00'
						}]);
					}else{
						$('#clearanceCertificateGrid').datagrid('reloadFooter',[{
							keepAccountsTime : '<span style="color:red;">合计</span>',
							payprinciPalamt : jsondata.rows[0].payprinciPalamtSum,
							payInteamt : jsondata.rows[0].payInteamtSum,
							payment : jsondata.rows[0].paymentSum,
							loanAmount : jsondata.rows[0].loanAmountSum
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
			width : 620,
			height : 410,
			closed : false,
			cache : false,
			modal : true,
			resizable : true
		});
		var startKeepAccountsTime = $("#startKeepAccountsTime").datebox('getValue');
		var endKeepAccountsTime = $("#endKeepAccountsTime").datebox('getValue');
		var contracts = $("#contracts").textbox('getValue');
		var startRepayDate = $("#startRepayDate").datebox('getValue');
		var endRepayDate = $("#endRepayDate").datebox('getValue');
		var startRegistrationDate = $("#startRegistrationDate").datebox('getValue');
		var endRegistrationDate = $("#endRegistrationDate").datebox('getValue');
		$('#accountingMarkForm').form('load',{
			startKeepAccountsTime : startKeepAccountsTime,
			endKeepAccountsTime : endKeepAccountsTime,
			contracts : contracts,
			startRepayDate : startRepayDate,
			endRepayDate : endRepayDate,
			startRegistrationDate : startRegistrationDate,
			endRegistrationDate : endRegistrationDate
		});
		
		$('#queryForm').form('submit', {
			url : contextPath + '/jyReceivedPaymentsServer/queryJYReceivedPaymentsCount.do',
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
			title : '合同审核',
			width : 620,
			height : 410,
			closed : false,
			cache : false,
			modal : true,
			resizable : true
		});
		var contracts = $("#contracts").textbox('getValue');
		var startRepayDate = $("#startRepayDate").datebox('getValue');
		var endRepayDate = $("#endRepayDate").datebox('getValue');
		var startRegistrationDate = $("#startRegistrationDate").datebox('getValue');
		var endRegistrationDate = $("#endRegistrationDate").datebox('getValue');
		$('#approveForm').form('load',{
			contracts : contracts,
			startRepayDate : startRepayDate,
			endRepayDate : endRepayDate,
			startRegistrationDate : startRegistrationDate,
			endRegistrationDate : endRegistrationDate
		});
		
		$('#queryForm').form('submit', {
			url : contextPath + '/jyReceivedPaymentsServer/queryJYReceivedPaymentsCount.do',
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
					url : contextPath + '/jyReceivedPaymentsServer/queryJYReceivedPaymentsContract.do',
					success : function(data) {
						//是否有数据
						if(data == "1"){
							$.messager.alert("操作提示", '已记账的数据禁止重复记账!', "error");
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
						$.messager.alert("操作提示", '记账失败,请稍后重试!', "error");
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
			$('#approveForm').form('submit', {
				url : contextPath + '/jyReceivedPaymentsServer/contractApprove.do',
				success : function(data) {
					closeWin('approvedlg');
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
							keepAccountsTime : '<span style="color:red;">合计</span>',
							payprinciPalamt : '0.00',
							payInteamt : '0.00',
							payment : '0.00',
							loanAmount : '0.00'
						}]);
					}else{
						$.messager.alert('提示', obj.msg, 'info');
		 				$("#clearanceCertificateGrid").datagrid('loadData', obj.obj);
						$('#clearanceCertificateGrid').datagrid('reloadFooter',[{
							keepAccountsTime : '<span style="color:red;">合计</span>',
							payprinciPalamt : obj.obj.rows[0].payprinciPalamtSum,
							payInteamt : obj.obj.rows[0].payInteamtSum,
							payment : obj.obj.rows[0].paymentSum,
							loanAmount : obj.obj.rows[0].loanAmountSum
						}]);
					}
				},
				onLoadError : function(data) {
					closeWin('approvedlg');
					$.messager.alert("操作提示", obj.msg, "error");
				}
			});
			}
		});
	}
	
	function accountMarking(dlg,form){
		$('#'+form).form('submit', {
			url : contextPath + '/jyReceivedPaymentsServer/accountingMark.do',
			success : function(data) {
				closeWin(dlg);
				var obj = jQuery.parseJSON(data);
				//是否有数据
				if (obj.obj.rows == "") {
					$.messager.alert('提示', '暂无匹配数据可修改!请重新搜索..', 'warning');
					//清空表单数据
					$('#clearanceCertificateGrid').datagrid('loadData', {
						total : 0,
						rows : []
					});
					$('#clearanceCertificateGrid').datagrid('reloadFooter',[{
						keepAccountsTime : '<span style="color:red;">合计</span>',
						payprinciPalamt : '0.00',
						payInteamt : '0.00',
						payment : '0.00',
						loanAmount : '0.00'
					}]);
				}else{
					$.messager.alert('提示', obj.msg, 'info');
	 				$("#clearanceCertificateGrid").datagrid('loadData', obj.obj);
					$('#clearanceCertificateGrid').datagrid('reloadFooter',[{
						keepAccountsTime : '<span style="color:red;">合计</span>',
						payprinciPalamt : obj.obj.rows[0].payprinciPalamtSum,
						payInteamt : obj.obj.rows[0].payInteamtSum,
						payment : obj.obj.rows[0].paymentSum,
						loanAmount : obj.obj.rows[0].loanAmountSum
					}]);
				}
			},
			onLoadError : function(data) {
				closeWin(dlg);
				$.messager.alert("操作提示", obj.msg, "error");
			}
				});
	}
	
	function selectAccountMarking(dlg,form){
		$('#'+form).form('submit', {
			url : contextPath + '/jyReceivedPaymentsServer/selectAccountingMark.do',
			success : function(data) {
				closeWin(dlg);
				var obj = jQuery.parseJSON(data);
				//是否有数据
				if (obj.obj.rows == "") {
					$.messager.alert('提示', '暂无匹配数据可修改!请重新搜索..', 'warning');
					//清空表单数据
					$('#clearanceCertificateGrid').datagrid('loadData', {
						total : 0,
						rows : []
					});
					$('#clearanceCertificateGrid').datagrid('reloadFooter',[{
						keepAccountsTime : '<span style="color:red;">合计</span>',
						payprinciPalamt : '0.00',
						payInteamt : '0.00',
						payment : '0.00',
						loanAmount : '0.00'
					}]);
				}else{
					$.messager.alert('提示', obj.msg, 'info');
					$("#clearanceCertificateGrid").datagrid('loadData', obj.obj);
					$('#clearanceCertificateGrid').datagrid('reloadFooter',[{
						keepAccountsTime : '<span style="color:red;">合计</span>',
						payprinciPalamt : obj.obj.rows[0].payprinciPalamtSum,
						payInteamt : obj.obj.rows[0].payInteamtSum,
						payment : obj.obj.rows[0].paymentSum,
						loanAmount : obj.obj.rows[0].loanAmountSum
					}]);
				}
			},
			onLoadError : function(data) {
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
		var startKeepAccountsTime = $("#startKeepAccountsTime").datebox('getValue');
		var endKeepAccountsTime = $("#endKeepAccountsTime").datebox('getValue');
		var contracts = $("#contracts").textbox('getValue');
		var startRepayDate = $("#startRepayDate").datebox('getValue');
		var endRepayDate = $("#endRepayDate").datebox('getValue');
		var startRegistrationDate = $("#startRegistrationDate").datebox('getValue');
		var endRegistrationDate = $("#endRegistrationDate").datebox('getValue');
		$('#cancelAccountingMarkForm').form('load',{
			startKeepAccountsTime : startKeepAccountsTime,
			endKeepAccountsTime : endKeepAccountsTime,
			contracts : contracts,
			startRepayDate : startRepayDate,
			endRepayDate : endRepayDate,
			startRegistrationDate : startRegistrationDate,
			endRegistrationDate : endRegistrationDate
		});
		
		$('#queryForm').form('submit', {
			url : contextPath + '/jyReceivedPaymentsServer/queryJYReceivedPaymentsCount.do',
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
				$('#'+form).form('submit', {
					url : contextPath + '/jyReceivedPaymentsServer/cancelAccountingMark.do',
					success : function(data) {
						closeWin(dlg);
						var obj = jQuery.parseJSON(data);
						//是否有数据
						if(obj.success){
							if (obj.obj == "") {
								$.messager.alert('提示', '暂无匹配数据可撤销记账!请重新搜索..', 'warning');
								//清空表单数据
								$('#clearanceCertificateGrid').datagrid('loadData', {
									total : 0,
									rows : []
								});
								$('#clearanceCertificateGrid').datagrid('reloadFooter',[{
									keepAccountsTime : '<span style="color:red;">合计</span>',
									payprinciPalamt : '0.00',
									payInteamt : '0.00',
									payment : '0.00',
									loanAmount : '0.00'
								}]);
							}else{
								$.messager.alert('提示', obj.msg, 'info');
				 				$("#clearanceCertificateGrid").datagrid('loadData', obj.obj);
								$('#clearanceCertificateGrid').datagrid('reloadFooter',[{
									keepAccountsTime : '<span style="color:red;">合计</span>',
									payprinciPalamt : obj.obj.rows[0].payprinciPalamtSum,
									payInteamt : obj.obj.rows[0].payInteamtSum,
									payment : obj.obj.rows[0].paymentSum,
									loanAmount : obj.obj.rows[0].loanAmountSum
								}]);
							}
						}else{
							$.messager.alert("操作提示", obj.message, "error");
						}
					},
					onLoadError : function(data) {
						closeWin(dlg);
						$.messager.alert("操作提示", obj.msg, "error");
					}
				});
			}
		});
	}
	
	function gatherExport(path){
		var scanTime = 1000;
		var interval = 500;
	    $.messager.progress({
	        title:'导出中,请等待...',
	        msg:'导出进度：',
	        interval: interval
	    });
		var startKeepAccountsTime = $("#startKeepAccountsTime").datebox('getValue');
		var endKeepAccountsTime = $("#endKeepAccountsTime").datebox('getValue');
		var contracts = $("#contracts").textbox('getValue');
		var startRepayDate = $("#startRepayDate").datebox('getValue');
		var endRepayDate = $("#endRepayDate").datebox('getValue');
		var startRegistrationDate = $("#startRegistrationDate").datebox('getValue');
		var endRegistrationDate = $("#endRegistrationDate").datebox('getValue');
		$.ajax({
			url : contextPath + "/jyReceivedPaymentsServer/queryGatherCount.do",
			type : "POST",
			data : {
				startKeepAccountsTime : startKeepAccountsTime,
				endKeepAccountsTime : endKeepAccountsTime,
				contracts : contracts,
				startRepayDate : startRepayDate, 
				endRepayDate : endRepayDate,
				startRegistrationDate : startRegistrationDate,
				endRegistrationDate : endRegistrationDate
			},
			success : function(data) {
				var jsondata = eval("(" + data + ")");
				//是否有数据
				if (jsondata.contractCount == 0) {
					$.messager.alert('提示', '暂无匹配数据,请更换查询条件!', 'warning');
				}else if (jsondata.contractCount < 500000) {
					window.location.href=path+'/jyReceivedPaymentsServer/gatherExport.do?startKeepAccountsTime='+startKeepAccountsTime+
					'&endKeepAccountsTime='+endKeepAccountsTime+"&contracts="+contracts+
					"&startRepayDate="+startRepayDate+"&endRepayDate="+endRepayDate+"&startRegistrationDate="+startRegistrationDate+"&endRegistrationDate="+endRegistrationDate;
				}else{
					$.messager.alert('提示', '数据量过大!请更换条件...', 'warning');
				}
			},
			error : function() {
				
			}
		});
		var timer = setInterval(function(){
	         $.ajax({
	                url: contextPath+'/MessageProgressServer/closeMessageProgress.do?id='+Math.random(),
	                success: function(data){
	                    console.log(data);
	                    if(data == "true"){
	                    	$.messager.progress('close');
	                    	$.messager.alert("导出提示","导出成功!","info");
	                    	clearInterval(timer);
	                    }
	                },
	                error:function(e){
	                	clearInterval(timer);
	                }
	            }); 
	          }, scanTime);
	}
	
	function detailExport(path){
		debugger;
		var scanTime = 1000;
		var interval = 500;
		$.messager.progress({
			title:'导出中,请等待...',
			msg:'导出进度：',
			interval: interval
		});
		var startKeepAccountsTime = $("#startKeepAccountsTime").datebox('getValue');
		var endKeepAccountsTime = $("#endKeepAccountsTime").datebox('getValue');
		var contracts = $("#contracts").textbox('getValue');
		var startRepayDate = $("#startRepayDate").datebox('getValue');
		var endRepayDate = $("#endRepayDate").datebox('getValue');
		var startRegistrationDate = $("#startRegistrationDate").datebox('getValue');
		var endRegistrationDate = $("#endRegistrationDate").datebox('getValue');
		$.ajax({
			url : contextPath + "/jyReceivedPaymentsServer/queryDetailCount.do",
			type : "POST",
			data : {
				startKeepAccountsTime : startKeepAccountsTime,
				endKeepAccountsTime : endKeepAccountsTime,
				contracts : contracts,
				startRepayDate : startRepayDate, 
				endRepayDate : endRepayDate,
				startRegistrationDate : startRegistrationDate,
				endRegistrationDate : endRegistrationDate
			},
			success : function(data) {
				var jsondata = eval("(" + data + ")");
				//是否有数据
				if (jsondata.contractCount == 0) {
					$.messager.alert('提示', '暂无匹配数据,请更换查询条件!', 'warning');
				}else if (jsondata.contractCount < 500000) {
					window.location.href=path+'/jyReceivedPaymentsServer/detailExport.do?startKeepAccountsTime='+startKeepAccountsTime+
					'&endKeepAccountsTime='+endKeepAccountsTime+"&contracts="+contracts+
					"&startRepayDate="+startRepayDate+"&endRepayDate="+endRepayDate+"&startRegistrationDate="+startRegistrationDate+"&endRegistrationDate="+endRegistrationDate;
				}else{
					$.messager.alert('提示', '数据量过大!请更换条件...', 'warning');
				}
			},
			error : function() {
				
			}
		});
		var timer = setInterval(function(){
			$.ajax({
				url: contextPath+'/MessageProgressServer/closeMessageProgress.do?id='+Math.random(),
				success: function(data){
					console.log(data);
					if(data == "true"){
						$.messager.progress('close');
						$.messager.alert("导出提示","导出成功!","info");
						clearInterval(timer);
					}
				},
				error:function(e){
					clearInterval(timer);
				}
			}); 
		}, scanTime);
	}
	
	function isNullOrEmpty(str){
		if(typeof(str) == 'undefined'){
			return '';
		}else{
			return str;
		}
	}