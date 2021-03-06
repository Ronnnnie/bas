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
		var payprinciPalamt = '';
		var payInteamt = '';
		var payprinciPalamtSum = 0;
		var payInteamtSum = 0;
		for (var i = 0; i < selectedRow.length; i++) {
			payprinciPalamtSum += Number(selectedRow[i].payprinciPalamt);
			payInteamtSum += Number(selectedRow[i].payInteamt);
			if(i==selectedRow.length-1){
				serialNo += selectedRow[i].serialNo;
    			seqId += selectedRow[i].seqId;
    			assetBelong += selectedRow[i].assetBelong;
    			assetBelongShow += companyNameFormatter(selectedRow[i].assetBelong,null);
    			payprinciPalamt += checkMoney(selectedRow[i].payprinciPalamt);
    			payInteamt += checkMoney(selectedRow[i].payInteamt);
			}else{
				serialNo += selectedRow[i].serialNo+',';
    			seqId += selectedRow[i].seqId+',';
    			assetBelong += selectedRow[i].assetBelong+',';
    			assetBelongShow += companyNameFormatter(selectedRow[i].assetBelong,null)+',';
    			payprinciPalamt += checkMoney(selectedRow[i].payprinciPalamt)+',';
    			payInteamt += checkMoney(selectedRow[i].payInteamt)+',';
			}
		}
    	$('#onDBAccountingMarkForm').form('load',{
    		seqId : seqId,
			serialNo : serialNo,
			assetBelong : assetBelong,
			assetBelongShow : assetBelongShow,
			payprinciPalamt : payprinciPalamt,
			payInteamt : payInteamt,
			payprinciPalamtSum : payprinciPalamtSum,
			payInteamtSum : payInteamtSum
		});
	}else{
		$.messager.alert('提示', '请至少选择一行数据进行记账!', 'warning');
	}
}


function query() {
		var startInAccountDate = $("#startInAccountDate").datebox('getValue');
		var endInAccountDate = $("#endInAccountDate").datebox('getValue');
		var classfy = $("#classfy").combobox('getValue');
		var assetBelong = $("#assetBelong").combobox('getValue'); 
		var serialNo = $("#serialNo").textbox('getValue'); 
		var today = getDate();
		if(startInAccountDate<=today&&endInAccountDate<=today){
			$("#clearanceCertificateGrid").datagrid('options').url = contextPath + "/overdueServer/queryOverdueDetail.do";
			$("#clearanceCertificateGrid").datagrid('load',{
				serialNo : serialNo,
				startInAccountDate : startInAccountDate,
				endInAccountDate : endInAccountDate,
				classfy : classfy,
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
				url : contextPath + "/overdueServer/queryOverdueDetail.do",
				type : "POST",
				data : {
					rows: 10,
					page:1,
					serialNo : serialNo,
					startInAccountDate : startInAccountDate,
					endInAccountDate : endInAccountDate,
					classfy : classfy,
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
							inAccountDate : '<span style="color:red;">合计</span>',
							payprinciPalamt : '0.00',
							payInteamt : '0.00',
							a2 : '0.00',
							a7 : '0.00',
							a12 : '0.00',
							a18 : '0.00',
							a22 : '0.00',
							amount : '0.00'
						}]);
					}else{
						//重新给控件加载数据
						$('#clearanceCertificateGrid').datagrid('reloadFooter',[{
							inAccountDate : '<span style="color:red;">合计</span>',
							payprinciPalamt : jsondata.rows[0].payprinciPalamtSum,
							payInteamt : jsondata.rows[0].payInteamtSum,
							a2 : jsondata.rows[0].a2Sum,
							a7 : jsondata.rows[0].a7Sum,
							a12 : jsondata.rows[0].a12Sum,
							a18 : jsondata.rows[0].a18Sum,
							a22 : jsondata.rows[0].a22Sum,
							amount : jsondata.rows[0].amountSum
						}]);
					}
				},
				error : function() {
					$.messager.alert('提示', '系统错误,请稍后重试!', 'error');
				}
			});
		}else{
			$.messager.alert('提示', '选择日期不能大于当前日期,请重新选择!', 'error');
		}
	}

	
	//窗口赋值
	function accountingMark() {
		$('#accountingMarkdlg').dialog({
			title : '记账确认',
			width : 330,
			height : 430,
			closed : false,
			cache : false,
			modal : true,
			resizable : true
		});
		var serialNo = $("#serialNo").textbox('getValue'); 
		var startInAccountDate = $("#startInAccountDate").datebox('getValue');
		var endInAccountDate = $("#endInAccountDate").datebox('getValue');
		//var startRegistrationDate = $("#startRegistrationDate").datebox('getValue');
		//var endRegistrationDate = $("#endRegistrationDate").datebox('getValue');
		//var startShouldAlsoDate = $("#startShouldAlsoDate").datebox('getValue');
		//var endShouldAlsoDate = $("#endShouldAlsoDate").datebox('getValue');
		var classfy = $("#classfy").combobox('getValue');
		var assetBelong = $("#assetBelong").combobox('getValue');
		var assetBelongShow = companyNameFormatter(assetBelong,null);
		$('#accountingMarkForm').form('load',{
			serialNo : serialNo,
			startInAccountDate : startInAccountDate,
			endInAccountDate : endInAccountDate,
			//startRegistrationDate : startRegistrationDate,
			//endRegistrationDate : endRegistrationDate,
			//startShouldAlsoDate : startShouldAlsoDate,
			//endShouldAlsoDate : endShouldAlsoDate,
			classfy : classfy,
			assetBelong : assetBelong,
			assetBelongShow : assetBelongShow
		});
		$('#queryForm').form('submit', {
			url : contextPath + '/overdueServer/queryOverdueDetailCount.do',
			success : function(data) {
				var jsondata = eval("(" + data + ")");
				//重新给控件加载数据
  				if (jsondata.contractCount != 0
 						&& jsondata.moneyCount != null) {
 					$("#accountingMarkContractCount").textbox('setValue',jsondata.contractCount);
 					$("#accountingMarkMoneyCount").textbox('setValue', jsondata.moneyCount);
 					if(jsondata.moneyCount == 0){
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
					url : contextPath + '/overdueServer/queryOverdueContract.do',
					success : function(data) {
						closeWin(dlg);
						if(data=="1" ){
							$.messager.alert("操作提示", '已记账的合同禁止重复记账!', "error");
						}else{
							$.messager.confirm('操作提示','您确定要对该些数据进行记账确认吗?',function(r){   
								if (r){
									if(form=='accountingMarkForm'){
										accountMarking(dlg,form);
									}else{
										onDBAccountingMark(dlg,form)
									}
								}
								});
						}
					},
					onLoadError : function(data) {
						closeWin(dlg);
						$.messager.alert("操作提示", obj.msg, "error");
					}
				});
			}else{
				closeWin(dlg);
				$.messager.alert("操作提示", '记账日期不能大于当前日期!', "error");
			}
		}else{
			closeWin(dlg);
			$.messager.alert("操作提示", '记账日期不能为空!', "error");
		}
	}
	
	function accountMarking(dlg,form){
		$.messager.progress({ title: '正在操作...', msg: '数据记账中...'});
		$('#'+form).form('submit', {
			url : contextPath + '/overdueServer/accountingMark.do',
			success : function(data) {
				$.messager.progress('close');
				closeWin(dlg);
				var obj = jQuery.parseJSON(data);
				//是否有数据
				if (obj.obj == "") {
					$.messager.alert('提示', '暂无匹配数据可记账!请重新搜索..', 'warning');
					//清空表单数据
					$('#clearanceCertificateGrid').datagrid('loadData', {
						total : 0,
						rows : []
					});
					$('#clearanceCertificateGrid').datagrid('reloadFooter',[{
						inAccountDate : '<span style="color:red;">合计</span>',
						payprinciPalamt : '0.00',
						payInteamt : '0.00',
						a2 : '0.00',
						a7 : '0.00',
						a12 : '0.00',
						a18 : '0.00',
						a22 : '0.00',
						amount : '0.00'
					}]);
				}else{
					$.messager.alert('提示', obj.msg, 'info');
	 				$("#clearanceCertificateGrid").datagrid('loadData', obj.obj);
	 				$('#clearanceCertificateGrid').datagrid('reloadFooter',[{
						inAccountDate : '<span style="color:red;">合计</span>',
						payprinciPalamt : obj.obj.rows[0].payprinciPalamtSum,
						payInteamt : obj.obj.rows[0].payInteamtSum,
						a2 : obj.obj.rows[0].a2Sum,
						a7 : obj.obj.rows[0].a7Sum,
						a12 : obj.obj.rows[0].a12Sum,
						a18 : obj.obj.rows[0].a18Sum,
						a22 : obj.obj.rows[0].a22Sum,
						amount : obj.obj.rows[0].amountSum
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
	
	function onDBAccountingMark(dlg,form){
		$.messager.progress({ title: '正在操作...', msg: '数据记账中...'});
		$('#'+form).form('submit', {
			url : contextPath + '/overdueServer/selectAccountingMark.do',
			success : function(data) {
				$.messager.progress('close');
				closeWin(dlg);
				var obj = jQuery.parseJSON(data);
				//是否有数据
				if (obj.obj == "") {
					$.messager.alert('提示', '暂无匹配数据可记账!请重新搜索..', 'warning');
					//清空表单数据
					$('#clearanceCertificateGrid').datagrid('loadData', {
						total : 0,
						rows : []
					});
					$('#clearanceCertificateGrid').datagrid('reloadFooter',[{
						inAccountDate : '<span style="color:red;">合计</span>',
						payprinciPalamt : '0.00',
						payInteamt : '0.00',
						a2 : '0.00',
						a7 : '0.00',
						a12 : '0.00',
						a18 : '0.00',
						a22 : '0.00',
						amount : '0.00'
					}]);
				}else{
					$.messager.alert('提示', obj.msg, 'info');
					$("#clearanceCertificateGrid").datagrid('loadData', obj.obj);
					$('#clearanceCertificateGrid').datagrid('reloadFooter',[{
						inAccountDate : '<span style="color:red;">合计</span>',
						payprinciPalamt : obj.obj.rows[0].payprinciPalamtSum,
						payInteamt : obj.obj.rows[0].payInteamtSum,
						a2 : obj.obj.rows[0].a2Sum,
						a7 : obj.obj.rows[0].a7Sum,
						a12 : obj.obj.rows[0].a12Sum,
						a18 : obj.obj.rows[0].a18Sum,
						a22 : obj.obj.rows[0].a22Sum,
						amount : obj.obj.rows[0].amountSum
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
	
	
	//窗口赋值
	function cancelAccountingMark() {
		$('#cancelAccountingMarkdlg').dialog({
			title : '记账确认',
			width : 330,
			height : 430,
			closed : false,
			cache : false,
			modal : true,
			resizable : true
		});
		var serialNo = $("#serialNo").textbox('getValue'); 
		var startInAccountDate = $("#startInAccountDate").datebox('getValue');
		var endInAccountDate = $("#endInAccountDate").datebox('getValue');
		//var startRegistrationDate = $("#startRegistrationDate").datebox('getValue');
		//var endRegistrationDate = $("#endRegistrationDate").datebox('getValue');
		//var startShouldAlsoDate = $("#startShouldAlsoDate").datebox('getValue');
		//var endShouldAlsoDate = $("#endShouldAlsoDate").datebox('getValue');
		var classfy = $("#classfy").combobox('getValue');
		var assetBelong = $("#assetBelong").combobox('getValue');
		var assetBelongShow = companyNameFormatter(assetBelong,null);
		$('#cancelAccountingMarkForm').form('load',{
			serialNo : serialNo,
			startInAccountDate : startInAccountDate,
			endInAccountDate : endInAccountDate,
			//startRegistrationDate : startRegistrationDate,
			//endRegistrationDate : endRegistrationDate,
			//startShouldAlsoDate : startShouldAlsoDate,
			//endShouldAlsoDate : endShouldAlsoDate,
			classfy : classfy,
			assetBelong : assetBelong,
			assetBelongShow : assetBelongShow,
		});
		$('#queryForm').form('submit', {
			url : contextPath + '/overdueServer/queryOverdueDetailCount.do',
			success : function(data) {
				var jsondata = eval("(" + data + ")");
				//重新给控件加载数据
  				if (jsondata.contractCount != 0
 						&& jsondata.moneyCount != null) {
 					$("#cancelAccountingMarkContractCount").textbox('setValue',jsondata.contractCount);
 					$("#cancelAccountingMarkMoneyCount").textbox('setValue', jsondata.moneyCount);
 					if(jsondata.moneyCount == 0){
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
					url : contextPath + '/overdueServer/cancelAccountingMark.do',
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
									inAccountDate : '<span style="color:red;">合计</span>',
									payprinciPalamt : '0.00',
									payInteamt : '0.00',
									a2 : '0.00',
									a7 : '0.00',
									a12 : '0.00',
									a18 : '0.00',
									a22 : '0.00',
									amount : '0.00'
								}]);
							}else{
								$.messager.alert('提示', obj.msg, 'info');
				 				$("#clearanceCertificateGrid").datagrid('loadData', obj.obj);
								$('#clearanceCertificateGrid').datagrid('reloadFooter',[{
									inAccountDate : '<span style="color:red;">合计</span>',
									payprinciPalamt : obj.obj.rows[0].payprinciPalamtSum,
									payInteamt : obj.obj.rows[0].payInteamtSum,
									a2 : obj.obj.rows[0].a2Sum,
									a7 : obj.obj.rows[0].a7Sum,
									a12 : obj.obj.rows[0].a12Sum,
									a18 : obj.obj.rows[0].a18Sum,
									a22 : obj.obj.rows[0].a22Sum,
									amount : obj.obj.rows[0].amountSum
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
	
	function ExportExcel(path){
		var startInAccountDate = $("#startInAccountDate").datebox('getValue');
		var endInAccountDate = $("#endInAccountDate").datebox('getValue');
		//var startRegistrationDate = $("#startRegistrationDate").datebox('getValue');
		//var endRegistrationDate = $("#endRegistrationDate").datebox('getValue');
		//var startShouldAlsoDate = $("#startShouldAlsoDate").datebox('getValue');
		//var endShouldAlsoDate = $("#endShouldAlsoDate").datebox('getValue');
		var classfy = $("#classfy").combobox('getValue');
		var assetBelong = $("#assetBelong").combobox('getValue'); 
		var serialNo = $("#serialNo").textbox('getValue'); 
		$.ajax({
			url : contextPath + "/overdueServer/queryOverdueDetailCount.do",
			type : "POST",
			data : {
				serialNo : serialNo,
				startInAccountDate : startInAccountDate,
				endInAccountDate : endInAccountDate,
				//startRegistrationDate : startRegistrationDate,
				//endRegistrationDate : endRegistrationDate,
				//startShouldAlsoDate : startShouldAlsoDate,
				//endShouldAlsoDate : endShouldAlsoDate,
				classfy : classfy,
				assetBelong : assetBelong
			},
			success : function(data) {
				var jsondata = eval("(" + data + ")");
				//是否有数据
				if (jsondata.contractCount < 500000) {
					exportBefore();
					window.location.href=path+'/overdueServer/overdueDetailExcelExport.do?startInAccountDate='+startInAccountDate+
							'&endInAccountDate='+endInAccountDate+"&startRegistrationDate="+startRegistrationDate+
							"&endRegistrationDate="+endRegistrationDate+"&classfy="+classfy+"&assetBelong="+assetBelong+"&serialNo="+serialNo;
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