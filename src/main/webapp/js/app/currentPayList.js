function selectUpdateOrCancel(){  
        	var selectedRow = $('#currentPayGrid').datagrid('getSelections');
        	if(selectedRow.length>0){
        			
        			
        			
        			$('#multipleInaccountdate').datebox('setValue','');
                	$('#multipleInaccountremark').textbox('setValue',"");
        			
        			$('#currentPayMultipleDlg').dialog({
            			title : '记账确认/批量',
            			width : 330,
            			height : 430,
            			closed : false,
            			cache : false,
            			modal : true,
            			resizable : true
            		});
        			var serialno = '';
            		var seqid = '';
            		var assetbelong = '';
            		var assetbelongName = '';
            		var payprincipalamt = '';
            		var payinteamt = '';
            		var payprincipalamtAmount = 0;
            		var payinteamtAmount = 0;
            		for (var i = 0; i < selectedRow.length; i++) {
            			if(i==selectedRow.length-1){
            				serialno += selectedRow[i].serialno;
                			seqid += selectedRow[i].sequence;
                			assetbelong += selectedRow[i].assetbelong;
                			assetbelongName += companyNameFormatter(selectedRow[i].assetbelong,null);
                			payprincipalamt += selectedRow[i].payprincipalamt;
                			payinteamt += selectedRow[i].payinteamt;
                			payprincipalamtAmount +=selectedRow[i].payprincipalamt;
                			payinteamtAmount += selectedRow[i].payinteamt;
            			}else{
            				serialno += selectedRow[i].serialno+';';
                			seqid += selectedRow[i].sequence+';';
                			assetbelong += selectedRow[i].assetbelong+';';
                			assetbelongName += companyNameFormatter(selectedRow[i].assetbelong)+';';
                			payprincipalamt += selectedRow[i].payprincipalamt+';';
                			payinteamt += selectedRow[i].payinteamt+';';
                			payprincipalamtAmount +=selectedRow[i].payprincipalamt;
                			payinteamtAmount += selectedRow[i].payinteamt;
            			}
        			}
            		
            		payprincipalamtAmount = thousandthFormatterShow(payprincipalamtAmount);
            		payinteamtAmount = thousandthFormatterShow(payinteamtAmount);
            		
                	$('#currentPayMultipleForm').form('load',{
                		seqid : seqid,
            			serialno : serialno,
            			assetbelong : assetbelong,
            			assetbelongName : assetbelongName,
            			payprincipalamt : payprincipalamt,
            			payinteamt : payinteamt,
            			payprincipalamtAmount : payprincipalamtAmount,
            			payinteamtAmount : payinteamtAmount
            		});
        	}else{
    			$.messager.alert('提示', '请至少选择一行数据进行记账!', 'warning');
    		}
        	
        	
        	
    		
 }

function query() {
	var startImportDate = $("#startImportDate").datebox('getValue');
	var endImportDate = $("#endImportDate").datebox('getValue');
	var assetbelong = $("#assetbelong").combobox('getValue');
	var serialno = $("#searchSerialno").val();
	var startInaccountdate = $("#startInaccountdate").datebox('getValue');
	var endInaccountdate = $("#endInaccountdate").datebox('getValue');
	
	if (startImportDate != null&&startImportDate != undefined&&startImportDate != ''&&
			endImportDate != null&&endImportDate != undefined&&endImportDate != ''){
		if(startImportDate>endImportDate){
	    	$.messager.alert("操作提示", "合同到期还款日开始时间大于结束时间","info");
	    	$('#currentPayGrid').datagrid('loadData', {
				total : 0,
				rows : []
			});
	    	
	    	$('#currentPayGrid').datagrid('reloadFooter',[{
				inaccountdate : '<span style="color:red;">合计</span>',
				payprincipalamt : '0',
				payinteamt : '0',
				waiveintamt : '0',
				a11 : '0',
				amount : '0'

			}]);
			
	    	return;
	    }
	}
	
	if (startInaccountdate != null&&startInaccountdate != undefined&&startInaccountdate != ''&&
			endInaccountdate != null&&endInaccountdate != undefined&&endInaccountdate != ''){
		if(startInaccountdate>endInaccountdate){
	    	$.messager.alert("操作提示", "注册日期开始时间大于结束时间","info");
	    	$('#currentPayGrid').datagrid('loadData', {
				total : 0,
				rows : []
			});
	    	
	    	$('#currentPayGrid').datagrid('reloadFooter',[{
				inaccountdate : '<span style="color:red;">合计</span>',
				payprincipalamt : '0',
				payinteamt : '0',
				waiveintamt : '0',
				a11 : '0',
				amount : '0'

			}]);
			
	    	return;
	    }
	}
	
	if(assetbelong==null||assetbelong==''){
		$.messager.alert("操作提示", "请选择资产所属方","info");
		return;
	}
	
	$('#currentPayGrid').datagrid('options').url = contextPath + "/currentPayServer/queryCurrentPayList.do"; 
	
	$("#currentPayGrid").datagrid(
			'load',{
				startPayDate : startImportDate,
				endPayDate : endImportDate,
				assetbelong : assetbelong,
				serialno : serialno,
				startInaccountdate : startInaccountdate,
				endInaccountdate : endInaccountdate
			}
	);

	

	var p = $('#currentPayGrid').datagrid('getPager'); 
    $(p).pagination({ 
        pageSize: 10,//每页显示的记录条数，默认为10 
        pageList: [5,10,15],//可以设置每页记录条数的列表 
        beforePageText: '第',//页数文本框前显示的汉字 
        afterPageText: '页    共 {pages} 页', 
        displayMsg: '当前显示 {from} - {to} 条记录   共 {total} 条记录' 
	}); 
	
	$.ajax({
		url : contextPath + "/currentPayServer/queryCurrentPayListTotal.do",
		type : "POST",
		data : {
			rows: 10,
			page:1,
			startPayDate : startImportDate,
			endPayDate : endImportDate,
			assetbelong : assetbelong,
			serialno : serialno,
			startInaccountdate : startInaccountdate,
			endInaccountdate : endInaccountdate
		},
		success : function(data) {
			var jsondata = eval("(" + data + ")");
			//是否有数据
			if (jsondata.CONTRACTCOUNT == null||jsondata.CONTRACTCOUNT==0) {
				$.messager.alert('提示', '暂无匹配数据!请重新搜索..', 'warning');
				//清空表单数据
				$('#currentPayGrid').datagrid('loadData', {
					total : 0,
					rows : []
				});
				
				$('#currentPayGrid').datagrid('reloadFooter',[{
					inaccountdate : '<span style="color:red;">合计</span>',
					payprincipalamt : '0',
					payinteamt : '0',
					waiveintamt : '0',
					a11 : '0',
					amount : '0'

				}]);
				
				return;
			}
				
			if (jsondata.CONTRACTCOUNT != 0
					&& jsondata.MONEYCOUNT != null) {
				
			$('#currentPayGrid').datagrid('reloadFooter',[{
					inaccountdate : '<span style="color:red;">合计</span>',
					payprincipalamt : jsondata.PAYPRINCIPALAMTAMOUNT,
					payinteamt : jsondata.PAYINTEAMTAMOUNT,
					waiveintamt : jsondata.WAIVEINTAMTAMOUNT,
					a11 : jsondata.A11AMOUNT,
					amount : jsondata.MONEYCOUNT

				}]);
			} 
			
			
			
			},
			error: function () { 
				 $.messager.alert("操作提示", data.message,"error");
	        }
		}); 
}

function querySum() {
	var startImportDate = $("#startImportDate").datebox('getValue');
	var endImportDate = $("#endImportDate").datebox('getValue');
	var paybelong = $("#paybelong").combobox('getValue');
	var assetbelong = $("#assetbelong").combobox('getValue');
	
	var creditperson = $("#creditperson").combobox('getValue');
	var guaranteeparty = $("#guaranteeparty").combobox('getValue');
	var searchCity = $("#searchCity").val();
	var searchSub = $("#searchSub").combobox('getValue');
	var searchBusinessmodel = $("#searchBusinessmodel").combobox('getValue');
	
	var startInaccountdate = $("#startInaccountdate").datebox('getValue');
	var endInaccountdate = $("#endInaccountdate").datebox('getValue');
	
	var datasource = $("#datasource").combobox('getValue');
	
	var startActualpaydate = $("#startActualpaydate").datebox('getValue');
	var endActualpaydate = $("#endActualpaydate").datebox('getValue');

	$('#currentPaySumGrid').datagrid('reloadFooter',[{
		inaccountdate : '<span style="color:red;">合计</span>',
		payprincipalamt : '0',
		payinteamt : '0',
		waiveintamt : '0',
		a11 : '0',
		amount : '0'

	}]);
	if (startImportDate != null&&startImportDate != undefined&&startImportDate != ''&&
			endImportDate != null&&endImportDate != undefined&&endImportDate != ''){
		if(startImportDate>endImportDate){
	    	$.messager.alert("操作提示", "合同到期还款日开始时间大于结束时间","info");
	    	$('#currentPaySumGrid').datagrid('loadData', {
				total : 0,
				rows : []
			});
	    	return;
	    }
	}
	
	if (startInaccountdate != null&&startInaccountdate != undefined&&startInaccountdate != ''&&
			endInaccountdate != null&&endInaccountdate != undefined&&endInaccountdate != ''){
		if(startInaccountdate>endInaccountdate){
	    	$.messager.alert("操作提示", "记账日期开始时间大于结束时间","info");
	    	$('#currentPaySumGrid').datagrid('loadData', {
				total : 0,
				rows : []
			});
	    	return;
	    }
	}
	
	if (startActualpaydate != null&&startActualpaydate != undefined&&startActualpaydate != ''&&
			endActualpaydate != null&&endActualpaydate != undefined&&endActualpaydate != ''){
		if(startActualpaydate>endActualpaydate){
	    	$.messager.alert("操作提示", "合同到期还款日开始时间大于结束时间","info");
	    	$('#currentPaySumGrid').datagrid('loadData', {
				total : 0,
				rows : []
			});
	    	return;
	    }
	}
	
	if(paybelong==null||paybelong==''){
		$.messager.alert("操作提示", "请选择应付资产方","info");
		return;
	}
	
	$('#currentPaySumGrid').datagrid('options').url = contextPath + "/currentPayServer/queryCurrentPayListSum.do"; 
	
	$("#currentPaySumGrid").datagrid(
			'load',{
				startPayDate : startImportDate,
				endPayDate : endImportDate,
				paybelong : paybelong,
				assetbelong : assetbelong,
				startInaccountdate : startInaccountdate,
				endInaccountdate : endInaccountdate,
				creditperson : creditperson,
				guaranteeparty : guaranteeparty,
				city : searchCity,
				subproducttype : searchSub,
				businessmodel : searchBusinessmodel,
				datasource : datasource,
				startActualpaydate : startActualpaydate,
				endActualpaydate : endActualpaydate
			}
	);
	
	var p = $('#currentPaySumGrid').datagrid('getPager'); 
    $(p).pagination({ 
        pageSize: 10,//每页显示的记录条数，默认为10 
        pageList: [5,10,15],//可以设置每页记录条数的列表 
        beforePageText: '第',//页数文本框前显示的汉字 
        afterPageText: '页    共 {pages} 页', 
        displayMsg: '当前显示 {from} - {to} 条记录   共 {total} 条记录' 
	}); 
	
	$.ajax({
		url : contextPath + "/currentPayServer/queryCurrentPayListSumTotal.do",
		type : "POST",
		data : {
			rows: 10,
			page:1,
			startPayDate : startImportDate,
			endPayDate : endImportDate,
			paybelong : paybelong,
			assetbelong : assetbelong,
			startInaccountdate : startInaccountdate,
			endInaccountdate : endInaccountdate,
			creditperson : creditperson,
			guaranteeparty : guaranteeparty,
			city : searchCity,
			subproducttype : searchSub,
			businessmodel : searchBusinessmodel,
			datasource : datasource,
			startActualpaydate : startActualpaydate,
			endActualpaydate : endActualpaydate
		},
		success : function(data) {
			var jsondata = eval("(" + data + ")");
			//是否有数据
			if (jsondata.CONTRACTCOUNT == null||jsondata.CONTRACTCOUNT==0) {
				$.messager.alert('提示', '暂无匹配数据!请重新搜索..', 'warning');
				//清空表单数据
				$('#currentPaySumGrid').datagrid('loadData', {
					total : 0,
					rows : []
				});
				
			}
			//重新给控件加载数据
				
			if (jsondata.CONTRACTCOUNT != 0
					&& jsondata.MONEYCOUNT != null) {
				
			$('#currentPaySumGrid').datagrid('reloadFooter',[{
					inaccountdate : '<span style="color:red;">合计</span>',
					payprincipalamt : jsondata.PAYPRINCIPALAMTAMOUNT,
					payinteamt : jsondata.PAYINTEAMTAMOUNT,
					waiveintamt : jsondata.WAIVEINTAMTAMOUNT,
					a11 : jsondata.A11AMOUNT,
					amount : jsondata.MONEYCOUNT

				}]);
			} 
			
		},
		error: function () { 
			 $.messager.alert("操作提示", data.message,"error");
        }
	}); 
}

function exportXls() {
	// 获取选中数据
	var startImportDate = $("#startImportDate").datebox('getValue');
	var endImportDate = $("#endImportDate").datebox('getValue');
	var assetbelong = $("#assetbelong").combobox('getValue');
	var serialno = $("#searchSerialno").val();
	var startInaccountdate = $("#startInaccountdate").datebox('getValue');
	var endInaccountdate = $("#endInaccountdate").datebox('getValue');
	var url = contextPath+"/currentPayServer/excelExport.do?assetbelong="+assetbelong+"&startPayDate="+startImportDate+"&endPayDate="+endImportDate+"&serialno="+serialno
	+"&startInaccountdate="+startInaccountdate+"&endInaccountdate="+endInaccountdate;
	url = encodeURI(url);
	url = encodeURI(url);
	
	var data=$('#currentPayGrid').datagrid('getData');
	if(data.total==0){
		$.messager.alert("操作提示", "请先查询合同，或没有符合条件的合同","info");
    	return;
	}
	
	$.ajax({
		async : false,
		cache : false,
		type : "POST",
		url : contextPath + "/currentPayServer/excelCheck.do",
		data : {
			startPayDate : startImportDate,
			endPayDate : endImportDate,
			assetbelong : assetbelong,
			startInaccountdate : startInaccountdate,
			endInaccountdate : endInaccountdate,
			serialno : serialno
		},
		beforeSend : function() {
			$.messager.progress({
				text : "数据处理中，请稍后....",
				interval : 100
			});
		},
		error : function() {
			$.messager.progress("close");
			$.messager.alert("操作提示", "导出失败，请稍后再试","error");
		},
		success : function(data) {
			$.messager.progress("close");
			if(data.success){				
				window.open(url);
				/*$.ajax({
					async : true,
					cache : false,
					type : 'POST',
					url : contextPath + "/currentPayServer/exportReport.do",
					data : {
						startPayDate : startImportDate,
						endPayDate : endImportDate,
						assetbelong : assetbelong,
						startInaccountdate : startInaccountdate,
						endInaccountdate : endInaccountdate
					},
					beforeSend : function() {
						$.messager.progress({
							text : "数据处理中，请稍后....",
							interval : 100
						});
					},
					error : function() {
						$.messager.progress("close");
						$.messager.alert("操作提示", "导出失败，请稍后再试","error");
					},
					success : function() {
						//alert('后台导出报表中...，请稍后到报表下载页面下载');
						$.messager.progress('close');
						$.dialog.confirm('是否跳转到报表下载页面？', function(){
							$('#'+id).datagrid('reload');
							addTab('报表下载列表','cgExportExcelController.do?download','pencil');
							}, function(){
								$('#'+id).datagrid('reload');
							});
					}
				});*/
				
			}else{
				$.messager.alert("操作提示", data.msg,"info");
			}
		}
	});
	
}

function exportXlsSum() {
	// 获取选中数据
	var startImportDate = $("#startImportDate").datebox('getValue');
	var endImportDate = $("#endImportDate").datebox('getValue');
	var paybelong = $("#paybelong").combobox('getValue');
	var assetbelong = $("#assetbelong").combobox('getValue');
	
	var creditperson = $("#creditperson").combobox('getValue');
	var guaranteeparty = $("#guaranteeparty").combobox('getValue');
	var searchCity = $("#searchCity").val();
	var searchSub = $("#searchSub").combobox('getValue');
	var searchBusinessmodel = $("#searchBusinessmodel").combobox('getValue');
	
	var startInaccountdate = $("#startInaccountdate").datebox('getValue');
	var endInaccountdate = $("#endInaccountdate").datebox('getValue');
	
	var datasource = $("#datasource").combobox('getValue');
	
	var startActualpaydate = $("#startActualpaydate").datebox('getValue');
	var endActualpaydate = $("#endActualpaydate").datebox('getValue');
	
	var url = contextPath+"/currentPayServer/excelExportSum.do?paybelong="+paybelong+"&assetbelong="+assetbelong+"&startPayDate="+startImportDate+"&endPayDate="+endImportDate
	+"&creditperson="+creditperson+"&guaranteeparty="+guaranteeparty+"&city="+searchCity+"&subproducttype="+searchSub
	+"&businessmodel="+searchBusinessmodel+"&startInaccountdate="+startInaccountdate+"&endInaccountdate="+endInaccountdate
	+"&datasource="+datasource+"&startActualpaydate="+startActualpaydate+"&endActualpaydate="+endActualpaydate;
	url = encodeURI(url);
	url = encodeURI(url);
	
	var data=$('#currentPaySumGrid').datagrid('getData');
	if(data.total==0){
		$.messager.alert("操作提示", "请先查询合同，或没有符合条件的合同","info");
    	return;
	}
	exportBefore();
	$.ajax({
		async : false,
		cache : false,
		type : "POST",
		url : contextPath + "/currentPayServer/excelCheckSum.do",
		data : {
			startPayDate : startImportDate,
			endPayDate : endImportDate,
			paybelong : paybelong,
			assetbelong : assetbelong,
			startInaccountdate : startInaccountdate,
			endInaccountdate : endInaccountdate,
			creditperson : creditperson,
			guaranteeparty : guaranteeparty,
			city : searchCity,
			subproducttype : searchSub,
			businessmodel : searchBusinessmodel,
			datasource : datasource,
			startActualpaydate : startActualpaydate,
			endActualpaydate : endActualpaydate
		},
		beforeSend : function() {
			$.messager.progress({
				text : "数据处理中，请稍后....",
				interval : 100
			});
		},
		error : function() {
			$.messager.progress("close");
			$.messager.alert("操作提示", "导出失败，请稍后再试","error");
		},
		success : function(data) {
			$.messager.progress("close");
			if(data.success){				
				window.location.href=url;
			}else{
				$.messager.alert("操作提示", data.msg,"info");
			}
		}
	});
	exportLater();
}


function openUpdateCurrentPay(index){

	var startPayDate = $('#startImportDate').datebox('getValue'); 
	var endPayDate = $('#endImportDate').datebox('getValue');
	
	if (startPayDate != null&&startPayDate != undefined&&startPayDate != ''&&
			endPayDate != null&&endPayDate != undefined&&endPayDate != ''){
		if(startPayDate>endPayDate){
	    	$.messager.alert("操作提示", "合同到期还款日开始时间大于结束时间","info");
	    	return;
	    }
	}
	
	var startInaccountdate = $('#startInaccountdate').datebox('getValue'); 
	var endInaccountdate = $('#endInaccountdate').datebox('getValue');
	
	if (startInaccountdate != null&&startInaccountdate != undefined&&startInaccountdate != ''&&
			endInaccountdate != null&&endInaccountdate != undefined&&endInaccountdate != ''){
		if(startInaccountdate>endInaccountdate){
	    	$.messager.alert("操作提示", "注册日期开始时间大于结束时间","info");
	    	return;
	    }
	}
	
	var data=$('#currentPayGrid').datagrid('getData');
	if(data.total==0){
		$.messager.alert("操作提示", "请先查询合同，或没有符合条件的合同","info");
    	return;
	}
	
	$('#updateSerialno').textbox('setValue','');
	$('#updateAssetbelong').val('');
	$('#updateStartPayDate').textbox('setValue','');
	$('#updateEndPayDate').textbox('setValue','');
	$('#updateInaccountdate').datebox('setValue','');
	$('#updateInaccountremark').val('');
	$('#updateStartPayDate').textbox('setValue','');
	$('#updateEndPayDate').textbox('setValue','');
	
	
	var startImportDate = $("#startImportDate").datebox('getValue');
	var endImportDate = $("#endImportDate").datebox('getValue');
	var assetbelong = $("#assetbelong").combobox('getValue');
	var serialno = $("#searchSerialno").val();
	var startInaccountdate = $("#startInaccountdate").datebox('getValue');
	var endInaccountdate = $("#endInaccountdate").datebox('getValue');
	
	if ((startInaccountdate == null||startInaccountdate == undefined||startInaccountdate == '')&&
			(endInaccountdate == null||endInaccountdate == undefined||endInaccountdate == '')
			&&
			(startImportDate == null||startImportDate == undefined||startImportDate == '')&&
			(endImportDate == null||endImportDate == undefined||endImportDate == '')
			&&
			(assetbelong ==null||assetbelong == undefined||assetbelong == '')
			&&
			(serialno ==null||serialno == undefined||serialno == '')
			){
		
		$.messager.confirm("确认", "未选择任何搜索条件，记账会更新所有记录", function (r) { 
			if (r) { 
				toUpdateCurrentPay(startImportDate,endImportDate,assetbelong,serialno,startInaccountdate,endInaccountdate,'','','','');
			}else{
				return;
			}
		});  
	    
	}else{
		toUpdateCurrentPay(startImportDate,endImportDate,assetbelong,serialno,startInaccountdate,endInaccountdate,'','','','');
	}
	

}


function toUpdateCurrentPay(startImportDate,endImportDate,assetbelong,serialno,startInaccountdate,endInaccountdate,
		payDate,sequence,payprincipalamt,payinteamt){
	
	$.ajax({
		url : contextPath + "/currentPayServer/checkCurrentPay.do",
		type : "POST",
		data : {
			rows: 10,
			page:1,
			startPayDate : startImportDate,
			endPayDate : endImportDate,
			assetbelong : assetbelong,
			serialno : serialno,
			startInaccountdate : startInaccountdate,
			endInaccountdate : endInaccountdate,
			seqidStr : sequence,
			payprincipalamtStr : payprincipalamt,
			payinteamtStr : payinteamt
			
		},
		success : function(data) {
			$.messager.progress('close');
			data = eval("("+data+")");
			if(data.success){
				$("#updateCurrentPayWin").show();
				$win = $("#updateCurrentPayWin").window({
					title:"批量记账",
					width:'350px',
					height:'350px',
					minimizable:false,
					collapsible:false,
					maximizable:false,
					modal : true,
					resizable : true
				});
				$win.window("open");
				
				if(serialno !=null&&serialno != undefined&&serialno != ''){
					$('#updateSerialno').textbox('setValue',serialno);
				}else{
					$('#updateSerialno').textbox('setValue',$('#searchSerialno').val());
				}
				
				if(assetbelong !=null&&assetbelong != undefined&&assetbelong != ''){
					$('#updateAssetbelong').val($("#assetbelong").combobox('getValue'));
					$('#updateAssetbelongShow').textbox('setValue',$("#assetbelong").combobox('getText'));
				}else{
					$('#updateAssetbelong').val($("#assetbelong").combobox('getValue'));
					$('#updateAssetbelongShow').textbox('setValue',$("#assetbelong").combobox('getText'));
				}
				
				if(payDate !=null&&payDate != undefined&&payDate != ''){
					$('#updateStartPayDate').textbox('setValue',payDate);
					$('#updateEndPayDate').textbox('setValue',payDate);
				}else{
					$('#updateStartPayDate').textbox('setValue',$('#startImportDate').datebox('getValue'));
					$('#updateEndPayDate').textbox('setValue',$('#endImportDate').datebox('getValue'));
				}
				
				
				$('#updateStartInaccountdate').textbox('setValue',startInaccountdate);
				$('#updateEndInaccountdate').textbox('setValue',endInaccountdate);
				
				
			}else{
				$.messager.alert("操作提示", data.message,"error");
			}

		},
		error: function () { 
			 $.messager.alert("操作提示", data.message,"error");
        }
	}); 
}

function updateCurrentPay(){
	
	var updateInaccountdate = $('#updateInaccountdate').datebox('getValue');
	
	if (updateInaccountdate == null||updateInaccountdate == undefined||updateInaccountdate == ''){
	    $.messager.alert("操作提示", "记账日期不能为空","error");
	    return;
	}
	
	var d=new Date(Date.parse(updateInaccountdate.replace(/\-/g, "\/")));
	var curDate=new Date();
	if(d >curDate){
		$.messager.alert("操作提示", "记账日期不能为大于当前时间","error");
	    return;
	}
	
	$.messager.confirm("确认", "确认记账(记账数据为所选搜索条件下所有合同，如只对单个合同进行操作，请在输入框输入该合同的合同号)", function (r) { 
		if (r) { 
			var updateStartPayDate = $("#updateStartPayDate").val();
			var updateEndPayDate = $("#updateEndPayDate").val();
			var updateAssetbelong = $("#updateAssetbelong").val();
			var updateSerialno = $("#updateSerialno").val();
			var updateInaccountremark = $("#updateInaccountremark").val();
			var updateStartInaccountdate = $("#updateStartInaccountdate").val();
			var updateEndInaccountdate = $("#updateEndInaccountdate").val();
			
			var updateSeqid = $("#updateSeqidStr").val();
        	var updatePayprincipalamt = $("#updatePayprincipalamtStr").val();
        	var updatePayinteamt = $("#updatePayinteamtStr").val();
        	$.messager.progress({ title: '正在操作...', msg: '数据记账中...'});
			$.ajax({
				url : contextPath + "/currentPayServer/updateCurrentPay.do",
				type : "POST",
				data : {
					rows: 10,
					page:1,
					startPayDate : updateStartPayDate,
					endPayDate : updateEndPayDate,
					assetbelong : updateAssetbelong,
					serialno : updateSerialno,
					inaccountdate : updateInaccountdate,
					inaccountremark : updateInaccountremark,
					startInaccountdate : updateStartInaccountdate,
					endInaccountdate : updateEndInaccountdate,
					seqidStr : updateSeqid,
        			payprincipalamtStr : updatePayprincipalamt,
        			payinteamtStr : updatePayinteamt
					
				},
				success : function(data) {
					$.messager.progress('close');
					data = eval("("+data+")");
					if(data.success){
						$.messager.alert("操作提示", data.message,"info");
						 //重新加载表格数据
						 query();
						 //关闭窗口
						 closeWin('updateCurrentPayWin');
					}else{
						$.messager.progress('close');
						$.messager.alert("操作提示", data.message,"error");
					}

				},
				error: function () { 
					$.messager.progress('close');
					 $.messager.alert("操作提示", data.message,"error");
		        }
			}); 
		}
	});  
    return;
	
}

function closeWin(win){
	$("#"+win).window("close"); 
}

function formatPaytype(val,row){
	if (val == '0050'){
		return '<span style="color:green;">正常还款</span>';
	}else if(val == '0055') {
		return '<span style="color:blue;">提前还款</span>';
	}else if(val == '0090') {
		return '<span style="color:blue;">利息减免</span>';
	}else if(val == '3530') {
		return '<span style="color:blue;">利息减免</span>';
	}else if((val==null || val == '')&&row.inaccountdate !='<span style="color:red;">合计</span>') {
		return '<span style="color:blue;">无</span>';
	}else if(row.inaccountdate !='<span style="color:red;">合计</span>'){
		return '<span style="color:red;">'+val+'</span>';
	}
}


function openCancelCurrentPay(index){

	var startPayDate = $('#startImportDate').datebox('getValue'); 
	var endPayDate = $('#endImportDate').datebox('getValue');
	
	if (startPayDate != null&&startPayDate != undefined&&startPayDate != ''&&
			endPayDate != null&&endPayDate != undefined&&endPayDate != ''){
		if(startPayDate>endPayDate){
	    	$.messager.alert("操作提示", "合同到期还款日开始时间大于结束时间","info");
	    	return;
	    }
	}
	
	var startInaccountdate = $('#startInaccountdate').datebox('getValue'); 
	var endInaccountdate = $('#endInaccountdate').datebox('getValue');
	
	if (startInaccountdate != null&&startInaccountdate != undefined&&startInaccountdate != ''&&
			endInaccountdate != null&&endInaccountdate != undefined&&endInaccountdate != ''){
		if(startInaccountdate>endInaccountdate){
	    	$.messager.alert("操作提示", "注册日期开始时间大于结束时间","info");
	    	return;
	    }
	}
	
	var data=$('#currentPayGrid').datagrid('getData');
	if(data.total==0){
		$.messager.alert("操作提示", "请先查询合同，或没有符合条件的合同","info");
    	return;
	}
	

	$('#cancelSerialno').textbox('setValue','');
	$('#cancelAssetbelong').val('');
	$('#cancelAssetbelongShow').textbox('setValue','');
	$('#cancelStartPayDate').textbox('setValue','');
	$('#cancelEndPayDate').textbox('setValue','');
	$('#cancelInaccountdate').datebox('setValue','');
	$('#cancelInaccountremark').textbox('setValue','');
	$('#cancelStartPayDate').textbox('setValue','');
	$('#cancelEndPayDate').textbox('setValue','');
	
	var startImportDate = $("#startImportDate").datebox('getValue');
	var endImportDate = $("#endImportDate").datebox('getValue');
	var assetbelong = $("#assetbelong").combobox('getValue');
	var serialno = $("#searchSerialno").val();
	var startInaccountdate = $("#startInaccountdate").datebox('getValue');
	var endInaccountdate = $("#endInaccountdate").datebox('getValue');
	
	if(assetbelong==null||assetbelong==''){
		$.messager.alert("操作提示", "请选择资产所属方","info");
		return;
	}

	toCancelCurrentPay(startImportDate,endImportDate,assetbelong,serialno,startInaccountdate,endInaccountdate);


}

function toCancelCurrentPay(startImportDate,endImportDate,assetbelong,serialno,startInaccountdate,endInaccountdate){
	$.ajax({
		url : contextPath + "/currentPayServer/checkCancelCurrentPay.do",
		type : "POST",
		data : {
			rows: 10,
			page:1,
			startPayDate : startImportDate,
			endPayDate : endImportDate,
			assetbelong : assetbelong,
			serialno : serialno,
			startInaccountdate : startInaccountdate,
			endInaccountdate : endInaccountdate
			
		},
		success : function(data) {
			$.messager.progress('close');
			data = eval("("+data+")");
			if(data.success){
				$("#cancelCurrentPayWin").show();
				$win = $("#cancelCurrentPayWin").window({
					title:"批量撤销",
					width:'350px',
					height:'350px',
					minimizable:false,
					collapsible:false,
					maximizable:false,
					modal : true,
					resizable : true
				});
				$win.window("open");
				
				$('#cancelSerialno').textbox('setValue',$('#searchSerialno').val());
				$('#cancelAssetbelong').val($("#assetbelong").combobox('getValue'));
				$('#cancelAssetbelongShow').textbox('setValue',$("#assetbelong").combobox('getText'));
				$('#cancelStartPayDate').textbox('setValue',$('#startImportDate').datebox('getValue'));
				$('#cancelEndPayDate').textbox('setValue',$('#endImportDate').datebox('getValue'));
				$('#cancelStartInaccountdate').textbox('setValue',startInaccountdate);
				$('#cancelEndInaccountdate').textbox('setValue',endInaccountdate);
			}else{
				$.messager.alert("操作提示", data.message,"error");
			}

		},
		error: function () { 
			 $.messager.alert("操作提示", data.message,"error");
        }
	}); 
}

function cancelCurrentPay(){
	
	$.messager.confirm("确认", "确认撤销(撤销数据为所选搜索条件下所有合同，如只对单个合同进行操作，请在搜索框输入该合同的合同号)", function (r) {  
        if (r) {  
        	var cancelStartPayDate = $("#cancelStartPayDate").val();
        	var cancelEndPayDate = $("#cancelEndPayDate").val();
        	var cancelAssetbelong = $("#cancelAssetbelong").val();
        	var cancelSerialno = $("#cancelSerialno").val();
        	var cancelStartInaccountdate = $("#cancelStartInaccountdate").val();
        	var cancelEndInaccountdate = $("#cancelEndInaccountdate").val();
        	$.messager.progress({ title: '正在操作...', msg: '数据撤销中...'});
        	$.ajax({
        		url : contextPath + "/currentPayServer/cancelCurrentPay.do",
        		type : "POST",
        		data : {
        			rows: 10,
        			page:1,
        			startPayDate : cancelStartPayDate,
        			endPayDate : cancelEndPayDate,
        			assetbelong : cancelAssetbelong,
        			serialno : cancelSerialno,
        			startInaccountdate : cancelStartInaccountdate,
        			endInaccountdate : cancelEndInaccountdate
        			
        		},
        		success : function(data) {
        			$.messager.progress('close');
        			data = eval("("+data+")");
        			if(data.success){
        				$.messager.alert("操作提示", data.message,"info");
        				 //重新加载表格数据
        				 query();
        				 //关闭窗口
        				 closeWin('cancelCurrentPayWin');
        			}else{
        				$.messager.progress('close');
        				$.messager.alert("操作提示", data.message,"error");
        			}

        		},
        		error: function () { 
        			$.messager.progress('close');
        			 $.messager.alert("操作提示", data.message,"error");
                }
        	}); 
        }  
    });  
	
	
}


function currentPayMultipleUpdate(){
	
	var selectedRow = $('#currentPayGrid').datagrid('getSelections');
	
	for (var i = 0; i < selectedRow.length; i++) {
		if(selectedRow[i].inaccountstatus=='1'){
			$.messager.alert('提示', '所选合同包含已记账合同!', 'error');
			return;
		}
	}
	
	var multipleSeqid = $("#multipleSeqid").val();
	var multipleSerialno = $("#multipleSerialno").val();
	var multipleAssetbelong = $("#multipleAssetbelong").val();
	var multiplePayprincipalamt = $("#multiplePayprincipalamt").val();
	var multiplePayinteamt = $("#multiplePayinteamt").val();
	var multipleInaccountdate = $("#multipleInaccountdate").datebox('getValue');
	var multipleInaccountremark = $("#multipleInaccountremark").val();
	
	if (multipleInaccountdate == null||multipleInaccountdate == undefined||multipleInaccountdate == ''){
	    $.messager.alert("操作提示", "记账日期不能为空","error");
	    return;
	}
	
	var d=new Date(Date.parse(multipleInaccountdate.replace(/\-/g, "\/")));
	var curDate=new Date();
	if(d >curDate){
		$.messager.alert("操作提示", "记账日期不能为大于当前时间","error");
	    return;
	}
	
	$.messager.progress({ title: '正在操作...', msg: '数据记账中...'});
	$.ajax({
		url : contextPath + "/currentPayServer/updateCurrentPay.do",
		type : "POST",
		data : {
			seqidStr : multipleSeqid,
			serialno : multipleSerialno,
			assetbelong : multipleAssetbelong,
			payprincipalamtStr : multiplePayprincipalamt,
			payinteamtStr : multiplePayinteamt,
			inaccountdate : multipleInaccountdate,
			inaccountremark : multipleInaccountremark
			
		},
		success : function(data) {
			$.messager.progress('close');
			data = eval("("+data+")");
			if(data.success){
				$.messager.alert("操作提示", data.message,"info");
				 //重新加载表格数据
				 query();
				 //关闭窗口
				 closeWin('currentPayMultipleDlg');
			}else{
				$.messager.alert("操作提示", data.message,"error");
			}

		},
		error: function (data) { 
			 $.messager.alert("操作提示", data.message,"error");
        }
	}); 
}


function currentPayMultipleCancel(){
	
	var selectedRow = $('#currentPayGrid').datagrid('getSelections');
	
	for (var i = 0; i < selectedRow.length; i++) {
		if(selectedRow[i].inaccountstatus!='1'){
			$.messager.alert('提示', '所选合同包含未记账合同!', 'error');
			return;
		}
	}
	
	var multipleSeqid = $("#multipleSeqid").val();
	var multipleSerialno = $("#multipleSerialno").val();
	var multipleAssetbelong = $("#multipleAssetbelong").val();
	var multiplePayprincipalamt = $("#multiplePayprincipalamt").val();
	var multiplePayinteamt = $("#multiplePayinteamt").val();
	var multipleInaccountdate = $("#multipleInaccountdate").datebox('getValue');
	var multipleInaccountremark = $("#multipleInaccountremark").val();
	$.messager.progress({ title: '正在操作...', msg: '数据撤销中...'});
	$.ajax({
		url : contextPath + "/currentPayServer/cancelCurrentPay.do",
		type : "POST",
		data : {
			seqidStr : multipleSeqid,
			serialno : multipleSerialno,
			assetbelong : multipleAssetbelong,
			payprincipalamtStr : multiplePayprincipalamt,
			payinteamtStr : multiplePayinteamt,
			inaccountdate : multipleInaccountdate,
			inaccountremark : multipleInaccountremark
			
		},
		success : function(data) {
			$.messager.progress('close');
			data = eval("("+data+")");
			if(data.success){
				$.messager.alert("操作提示", data.message,"info");
				 //重新加载表格数据
				 query();
				 //关闭窗口
				 closeWin('currentPayMultipleDlg');
			}else{
				$.messager.alert("操作提示", data.message,"error");
			}

		},
		error: function (data) { 
			 $.messager.alert("操作提示", data.message,"error");
        }
	}); 
}

function formatInaccountStatus(val,row){
	if (val == '1'){
		return '<span style="color:green;">已记账</span>';
	} else if(val == '2') {
		return '<span style="color:red;">已撤销</span>';
	} else if(row.inaccountdate !='<span style="color:red;">合计</span>'){
		return '<span style="color:blue;">未记账</span>';
	}
}