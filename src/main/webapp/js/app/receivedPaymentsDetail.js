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
 			var serialNo = '';
     		var seqId = '';
     		var assetBelong = '';
     		var assetBelongShow = '';
     		var actualPayPrincipalAmt = '';
     		var actualPayinteAmt = '';
     		var actualPayPrincipalAmtSum = 0;
     		var actualPayinteAmtSum = 0;
     		var keepDate = 0;
     		for (var i = 0; i < selectedRow.length; i++) {
     			if(selectedRow[i].keepAccountsDate!=''){
     				keepDate=1;
     			}
     			actualPayPrincipalAmtSum += Number(selectedRow[i].actualPayPrincipalAmt);
     			actualPayinteAmtSum += Number(selectedRow[i].actualPayinteAmt);
     			if(i==selectedRow.length-1){
     				serialNo += selectedRow[i].serialNo;
         			seqId += selectedRow[i].seqId;
         			assetBelong += selectedRow[i].assetBelong;
         			assetBelongShow += companyNameFormatter(selectedRow[i].assetBelong,null);
         			actualPayPrincipalAmt += selectedRow[i].actualPayPrincipalAmt;
         			actualPayinteAmt += selectedRow[i].actualPayinteAmt;
     			}else{
     				serialNo += selectedRow[i].serialNo+',';
         			seqId += selectedRow[i].seqId+',';
         			assetBelong += selectedRow[i].assetBelong+',';
         			assetBelongShow += companyNameFormatter(selectedRow[i].assetBelong,null)+',';
         			actualPayPrincipalAmt += selectedRow[i].actualPayPrincipalAmt+',';
         			actualPayinteAmt += selectedRow[i].actualPayinteAmt+',';
     			}
 			}
         	$('#onDBAccountingMarkForm').form('load',{
         		seqId : seqId,
     			serialNo : serialNo,
     			assetBelong : assetBelong,
     			assetBelongShow : assetBelongShow,
     			actualPayPrincipalAmt : actualPayPrincipalAmt,
     			actualPayinteAmt : actualPayinteAmt,
     			actualPayPrincipalAmtSum : actualPayPrincipalAmtSum,
     			actualPayinteAmtSum : actualPayinteAmtSum,
     			keepDate : keepDate
     		});
 		}else{
 			$.messager.alert('提示', '请选择至少一条数据进行记账!', 'warning');
 		}
     }
	function query() {
		var serialNo = $("#serialNo").textbox('getValue');
		var startKeepAccountsDate = $("#startKeepAccountsDate").datebox('getValue');
		var endKeepAccountsDate = $("#endKeepAccountsDate").datebox('getValue');
		var startShouldAlsoDate = $("#startShouldAlsoDate").datebox('getValue');
		var endShouldAlsoDate = $("#endShouldAlsoDate").datebox('getValue');
		var assetBelong = $("#assetBelong").combobox('getValue');
		var today = getDate();
		if(startKeepAccountsDate <= today && endKeepAccountsDate <= today){
			$("#clearanceCertificateGrid").datagrid('options').url = contextPath + "/receivedPaymentsServer/queryReceivedPaymentsDetail.do";
			$("#clearanceCertificateGrid").datagrid('load',{
				serialNo : serialNo,
				startKeepAccountsDate : startKeepAccountsDate,
				endKeepAccountsDate : endKeepAccountsDate,
				startShouldAlsoDate : startShouldAlsoDate,
				endShouldAlsoDate : endShouldAlsoDate,
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
				url : contextPath + "/receivedPaymentsServer/queryReceivedPaymentsDetail.do",
				type : "POST",
				data : {
					rows: 10,
					page:1,
					serialNo : serialNo,
					startKeepAccountsDate : startKeepAccountsDate,
					endKeepAccountsDate : endKeepAccountsDate,
					startShouldAlsoDate : startShouldAlsoDate,
					endShouldAlsoDate : endShouldAlsoDate,
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
						//重新给控件加载数据
						$('#clearanceCertificateGrid').datagrid('reloadFooter',[{
							keepAccountsDate : '<span style="color:red;">合计</span>',
							actualPayPrincipalAmt : '0.00',
							actualPayinteAmt : '0.00',
							a2 : '0.00',
							a7 : '0.00',
							a9 : '0.00',
							a10 : '0.00',
							a11 : '0.00',
							a12 : '0.00',
							a17 : '0.00',
							a18 : '0.00'
						}]);
					}else{
						$('#clearanceCertificateGrid').datagrid('loadData',jsondata.rows);
						$('#clearanceCertificateGrid').datagrid('reloadFooter',[{
							keepAccountsDate : '<span style="color:red;">合计</span>',
							actualPayPrincipalAmt : jsondata.rows[0].actualPayPrincipalAmtSum,
							actualPayinteAmt : jsondata.rows[0].actualPayinteAmtSum,
							a2 : jsondata.rows[0].a2Sum,
							a7 : jsondata.rows[0].a7Sum,
							a9 : jsondata.rows[0].a9Sum,
							a10 : jsondata.rows[0].a10Sum,
							a11 : jsondata.rows[0].a11Sum,
							a12 : jsondata.rows[0].a12Sum,
							a17 : jsondata.rows[0].a17Sum,
							a18 : jsondata.rows[0].a18Sum,
							a19 : jsondata.rows[0].a19Sum
						}]);
					}
				},
				error : function() {
					$.messager.alert("操作提示", data.message, "error");
				}
			});
		}else{
			$.messager.alert("操作提示", '选择日期不能大于当前日期,请重新选择!', "error");
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
		var startKeepAccountsDate = $("#startKeepAccountsDate").datebox('getValue');
		var endKeepAccountsDate = $("#endKeepAccountsDate").datebox('getValue');
		var startShouldAlsoDate = $("#startShouldAlsoDate").datebox('getValue');
		var endShouldAlsoDate = $("#endShouldAlsoDate").datebox('getValue');
		var assetBelong = $("#assetBelong").combobox('getValue');
		$('#accountingMarkForm').form('load',{
			serialNo : serialNo,
			startKeepAccountsDate : startKeepAccountsDate,
			endKeepAccountsDate : endKeepAccountsDate,
			startShouldAlsoDate : startShouldAlsoDate,
			endShouldAlsoDate : endShouldAlsoDate,
			assetBelong : assetBelong
		});
	}
	
	function accountingMarkSubmit(dlg,form) {
		var update = null;
		update = $("#jzUpdateDate").datebox('getValue');
		if(update!=''){
			var today = getDate();
			if(udpate<=today){
				$('#'+form).form('submit', {
					url : contextPath + '/receivedPaymentsServer/queryContractStatus.do',
					success : function(data) {
						//是否有数据
						if(data == "1"){
							closeWin(dlg);
							$.messager.alert("操作提示", '已记账的合同禁止重复记账!', "error");
						}else{
							$('#'+form).form('submit', {
								url : contextPath + '/receivedPaymentsServer/accountingMark.do',
								success : function(data) {
									closeWin(dlg);
									var jsondata = jQuery.parseJSON(data);
									$('#clearanceCertificateGrid').datagrid('reloadFooter',[{
										keepAccountsDate : '<span style="color:red;">合计</span>',
										actualPayPrincipalAmt : jsondata.obj[0].actualPayPrincipalAmtSum,
										actualPayinteAmt : jsondata.obj[0].actualPayinteAmtSum,
										a2 : jsondata.obj[0].a2Sum,
										a7 : jsondata.obj[0].a7Sum,
										a9 : jsondata.obj[0].a9Sum,
										a10 : jsondata.obj[0].a10Sum,
										a11 : jsondata.obj[0].a11Sum,
										a12 : jsondata.obj[0].a12Sum,
										a17 : jsondata.obj[0].a17Sum,
										a18 : jsondata.obj[0].a18Sum
									}]);
									//是否有数据
									if (jsondata.obj == null) {
										$.messager.alert('提示', jsondata.msg, 'warning');
										//清空表单数据
										$('#clearanceCertificateGrid').datagrid('loadData', {
											total : 0,
											rows : []
										});
									}else{
										$("#clearanceCertificateGrid").datagrid('loadData', jsondata.obj);
										$.messager.alert('提示', jsondata.msg, 'info');
									}
								},
								onLoadError : function(data) {
									closeWin(dlg);
									$.messager.alert("操作提示", jsondata.msg, "error");
								}
							});
						}
					},
					onLoadError : function(data) {
						closeWin(dlg);
						$.messager.alert("操作提示", '系统错误,请稍后重试!', "error");
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
	
	function selectAccountingMarkSubmit(dlg,form) {
		var update = null;
		update = $("#jzsUpdateDate").datebox('getValue');
		if(update!=''){
			var today = getDate();
			if(update<=today){
				$('#'+form).form('submit', {
					url : contextPath + '/receivedPaymentsServer/queryContractStatus.do',
					success : function(data) {
						//是否有数据
						if(data == "1"){
							$.messager.alert("操作提示", '已记账的合同禁止重复记账!', "error");
						}else{
							$.messager.confirm('操作提示','您确定要对已记账的数据进行记账吗?',function(r){ 
								$('#'+form).form('submit', {
									url : contextPath + '/receivedPaymentsServer/selectAccountingMark.do',
									success : function(data) {
										closeWin(dlg);
										var jsondata = jQuery.parseJSON(data);
										$('#clearanceCertificateGrid').datagrid('reloadFooter',[{
											keepAccountsDate : '<span style="color:red;">合计</span>',
											actualPayPrincipalAmt : jsondata.obj[0].actualPayPrincipalAmtSum,
											actualPayinteAmt : jsondata.obj[0].actualPayinteAmtSum,
											a2 : jsondata.obj[0].a2Sum,
											a7 : jsondata.obj[0].a7Sum,
											a9 : jsondata.obj[0].a9Sum,
											a10 : jsondata.obj[0].a10Sum,
											a11 : jsondata.obj[0].a11Sum,
											a12 : jsondata.obj[0].a12Sum,
											a17 : jsondata.obj[0].a17Sum,
											a18 : jsondata.obj[0].a18Sum
										}]);
										//是否有数据
										if (jsondata.obj == null) {
											$.messager.alert('提示', jsondata.msg, 'warning');
											//清空表单数据
											$('#clearanceCertificateGrid').datagrid('loadData', {
												total : 0,
												rows : []
											});
										}else{
											$("#clearanceCertificateGrid").datagrid('loadData', jsondata.obj);
											$.messager.alert('提示', jsondata.msg, 'info');
										}				//重新给控件加载数据
									},
									onLoadError : function(data) {
										closeWin(dlg);
										$.messager.alert("操作提示", jsondata.msg, "error");
									}
								});
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
		var serialNo = $("#serialNo").textbox('getValue');
		var startKeepAccountsDate = $("#startKeepAccountsDate").datebox('getValue');
		var endKeepAccountsDate = $("#endKeepAccountsDate").datebox('getValue');
		var startShouldAlsoDate = $("#startShouldAlsoDate").datebox('getValue');
		var endShouldAlsoDate = $("#endShouldAlsoDate").datebox('getValue');
		var assetBelong = $("#assetBelong").combobox('getValue');
		$('#cancelAccountingMarkForm').form('load',{
			serialNo : serialNo,
			startKeepAccountsDate : startKeepAccountsDate,
			endKeepAccountsDate : endKeepAccountsDate,
			startShouldAlsoDate : startShouldAlsoDate,
			endShouldAlsoDate : endShouldAlsoDate,
			assetBelong : assetBelong
		});
	}
	
	
	function cancelAccountingMarkSubmit(dlg,form) {
		$.messager.confirm('操作提示','您确定要对该些数据进行记账撤销吗?',function(r){   
			if (r){
				$('#'+form).form('submit', {
					url : contextPath + '/receivedPaymentsServer/cancelAccountingMark.do',
					success : function(data) {
						closeWin(dlg);
						var obj = jQuery.parseJSON(data);
						$('#clearanceCertificateGrid').datagrid('reloadFooter',[{
							keepAccountsDate : '<span style="color:red;">合计</span>',
							actualPayPrincipalAmt : obj.obj[0].actualPayPrincipalAmtSum,
							actualPayinteAmt : obj.obj[0].actualPayinteAmtSum,
							a2 : obj.obj[0].a2Sum,
							a7 : obj.obj[0].a7Sum,
							a9 : obj.obj[0].a9Sum,
							a10 : obj.obj[0].a10Sum,
							a11 : obj.obj[0].a11Sum,
							a12 : obj.obj[0].a12Sum,
							a17 : obj.obj[0].a17Sum,
							a18 : obj.obj[0].a18Sum
						}]);
						//是否有数据
						$('#clearanceCertificateGrid').datagrid('loadData', {total : 0,rows : []});
						if (obj.obj == "") {
							$.messager.alert('提示', '暂无匹配数据可撤销记账!请重新搜索..', 'warning');
						}else{
							$.messager.alert('提示', obj.msg, 'info');
			 				$("#clearanceCertificateGrid").datagrid('loadData', obj.obj);
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
	
	function ExportExcel(path){
		
		var serialNo = $("#serialNo").textbox('getValue');
		var startKeepAccountsDate = $("#startKeepAccountsDate").datebox('getValue');
		var endKeepAccountsDate = $("#endKeepAccountsDate").datebox('getValue');
		var startShouldAlsoDate = $("#startShouldAlsoDate").datebox('getValue');
		var endShouldAlsoDate = $("#endShouldAlsoDate").datebox('getValue');
		var assetBelong = $("#assetBelong").combobox('getValue');
		$.ajax({
			url : contextPath + "/receivedPaymentsServer/queryReceivedPaymentsDetailCount.do",
			type : "POST",
			data : {
				serialNo : serialNo,
				startKeepAccountsDate : startKeepAccountsDate,
				endKeepAccountsDate : endKeepAccountsDate,
				startShouldAlsoDate : startShouldAlsoDate,
				endShouldAlsoDate : endShouldAlsoDate,
				assetBelong : assetBelong
			},
			success : function(data) {
				var jsondata = eval("(" + data + ")");
				//是否有数据
				if (jsondata.contractCount < 500000) {
					window.location.href=path+'/receivedPaymentsServer/detailDateExcelExport.do?serialNo='+serialNo+
							'&startKeepAccountsDate='+startKeepAccountsDate+"&endKeepAccountsDate="+endKeepAccountsDate+
							"&startShouldAlsoDate="+startShouldAlsoDate+"&endShouldAlsoDate="+endShouldAlsoDate+"&assetBelong="+assetBelong;
				}else{
					$.messager.alert('提示', '数据量过大!请更换条件...', 'warning');
				}
			},
			error : function() {
				
			}
		});
	}

	function checkMoney(money){
		if(money == null){
			return '0.0';
		}
		return money;
	}
