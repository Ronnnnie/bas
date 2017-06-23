function selectUpdateOrCancel(){   
        	var selectedRow = $('#predictcostGrid').datagrid('getSelections');
        	if(selectedRow.length>0){
        		
        			
        			
        			$('#multipleInaccountdate').datebox('setValue','');
                	$('#multipleInaccountremark').textbox('setValue',"");
        			
        			$('#predictcostMultipleDlg').dialog({
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
            				serialno += selectedRow[i].contractno;
                			seqid += selectedRow[i].seqid;
                			assetbelong += selectedRow[i].belong;
                			assetbelongName += companyNameFormatter(selectedRow[i].belong,null);
                			payprincipalamt += selectedRow[i].payprincipalamt;
                			payinteamt += selectedRow[i].payinteamt;
                			payprincipalamtAmount +=selectedRow[i].payprincipalamt;
                			payinteamtAmount += selectedRow[i].payinteamt;
            			}else{
            				serialno += selectedRow[i].contractno+';';
                			seqid += selectedRow[i].seqid+';';
                			assetbelong += selectedRow[i].belong+';';
                			assetbelongName += companyNameFormatter(selectedRow[i].belong,null)+';';
                			payprincipalamt += selectedRow[i].payprincipalamt+';';
                			payinteamt += selectedRow[i].payinteamt+';';
                			payprincipalamtAmount +=selectedRow[i].payprincipalamt;
                			payinteamtAmount += selectedRow[i].payinteamt;
            			}
        			}
            		
            		payprincipalamtAmount = thousandthFormatterShow(payprincipalamtAmount);
            		payinteamtAmount = thousandthFormatterShow(payinteamtAmount);
            		
                	$('#predictcostMultipleForm').form('load',{
                		seqid : seqid,
                		contractno : serialno,
            			belong : assetbelong,
            			belongName : assetbelongName,
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
	var belong = $("#belong").combobox('getValue');
	var contractno = $("#searchContractno").val();
	var startInaccountdate = $("#startInaccountdate").datebox('getValue');
	var endInaccountdate = $("#endInaccountdate").datebox('getValue');
	
	if (startImportDate != null&&startImportDate != undefined&&startImportDate != ''&&
			endImportDate != null&&endImportDate != undefined&&endImportDate != ''){
		if(startImportDate>endImportDate){
	    	$.messager.alert("操作提示", "合同到期还款日开始时间大于结束时间","info");
	    	$('#predictcostGrid').datagrid('loadData', {
				total : 0,
				rows : []
			});
	    	
	    	$('#predictcostGrid').datagrid('reloadFooter',[{
	    		inaccountdate : '<span style="color:red;">合计</span></span>',
				payprincipalamt : '0',
				payinteamt : '0',
				a2 : '0',
				a7 : '0',
				a12 : '0',
				a18 : '0',
				amount : '0'

			}]);
			
	    	return;
	    }
	}
	
	if (startInaccountdate != null&&startInaccountdate != undefined&&startInaccountdate != ''&&
			endInaccountdate != null&&endInaccountdate != undefined&&endInaccountdate != ''){
		if(startInaccountdate>endInaccountdate){
	    	$.messager.alert("操作提示", "记账日期开始时间大于结束时间","info");
	    	$('#predictcostGrid').datagrid('loadData', {
				total : 0,
				rows : []
			});
	    	
	    	$('#predictcostGrid').datagrid('reloadFooter',[{
	    		inaccountdate : '<span style="color:red;">合计</span>',
				payprincipalamt : '0',
				payinteamt : '0',
				a2 : '0',
				a7 : '0',
				a12 : '0',
				a18 : '0',
				amount : '0'

			}]);
			
	    	return;
	    }
	}
	
	$('#predictcostGrid').datagrid('options').url = contextPath + "/predictcostServer/queryPredictcostList.do"; 
	
	$("#predictcostGrid").datagrid(
			'load',{
				startPayDate : startImportDate,
				endPayDate : endImportDate,
				belong : belong,
				contractno : contractno,
				startInaccountdate : startInaccountdate,
				endInaccountdate : endInaccountdate
			}
	);
	

	var p = $('#predictcostGrid').datagrid('getPager'); 
    $(p).pagination({ 
        pageSize: 10,//每页显示的记录条数，默认为10 
        pageList: [5,10,15],//可以设置每页记录条数的列表 
        beforePageText: '第',//页数文本框前显示的汉字 
        afterPageText: '页    共 {pages} 页', 
        displayMsg: '当前显示 {from} - {to} 条记录   共 {total} 条记录' 
	}); 
	
	$.ajax({
		url : contextPath + "/predictcostServer/queryPredictcostTotal.do",
		type : "POST",
		data : {
			rows: 10,
			page:1,
			startPayDate : startImportDate,
			endPayDate : endImportDate,
			belong : belong,
			contractno : contractno,
			startInaccountdate : startInaccountdate,
			endInaccountdate : endInaccountdate
		},
		success : function(data) {
			var jsondata = eval("(" + data + ")");
			//是否有数据
			if (jsondata.CONTRACTCOUNT == null||jsondata.CONTRACTCOUNT==0) {
				$.messager.alert('提示', '暂无匹配数据!请重新搜索..', 'warning');
				//清空表单数据
				$('#predictcostGrid').datagrid('loadData', {
					total : 0,
					rows : []
				});
				
				$('#predictcostGrid').datagrid('reloadFooter',[{
					inaccountdate : '<span style="color:red;">合计</span>',
					payprincipalamt : '0',
					payinteamt : '0',
					a2 : '0',
					a7 : '0',
					a12 : '0',
					a18 : '0',
					amount : '0'

				}]);
				
				return;
			}
			//重新给控件加载数据
			
			//重新给表格加载数据
			if (jsondata.CONTRACTCOUNT != 0
						&& jsondata.MONEYCOUNT != null) {
					
					$('#predictcostGrid').datagrid('reloadFooter',[{
						inaccountdate : '<span style="color:red;">合计</span>',
						payprincipalamt : jsondata.PAYPRINCIPALAMTAMOUNT,
						payinteamt : jsondata.PAYINTEAMTAMOUNT,
						a2 : jsondata.A2AMOUNT,
						a7 : jsondata.A7AMOUNT,
						a12 : jsondata.A12AMOUNT,
						a18 : jsondata.A18AMOUNT,
						amount : jsondata.MONEYCOUNT

					}]);
			} 
			
			
		},
		error: function () { 
			 $.messager.alert("操作提示", "系统异常","error");
        }
	}); 
}

function querySum() {
	var startDueDate = $("#startDueDate").datebox('getValue');
	var endDueDate = $("#endDueDate").datebox('getValue');
	var belong = $("#belong").combobox('getValue');
	
	var guaranteeparty = $("#guaranteeparty").combobox('getValue');
	var searchCity = $("#searchCity").val();
	var subproducttype = $("#searchSub").combobox('getValue');
	var searchBusinessmodel = $("#searchBusinessmodel").combobox('getValue');
	
	var startInaccountdate = $("#startInaccountdate").datebox('getValue');
	var endInaccountdate = $("#endInaccountdate").datebox('getValue');
	
	var startPayDate = $("#startPayDate").datebox('getValue');
	var endPayDate = $("#endPayDate").datebox('getValue');
	
	var canceltype = $("#canceltype").combobox('getValue');
	
	var creditperson = $("#creditperson").combobox('getValue');
	
	if (startDueDate != null&&startDueDate != undefined&&startDueDate != ''&&
			endDueDate != null&&endDueDate != undefined&&endDueDate != ''){
		if(startDueDate>endDueDate){
	    	$.messager.alert("操作提示", "合同到期还款日开始时间大于结束时间","info");
	    	$('#predictcostSumGrid').datagrid('loadData', {
				total : 0,
				rows : []
			});
	    	
	    	$('#predictcostSumGrid').datagrid('reloadFooter',[{
	    		inaccountdate : '<span style="color:red;">合计</span>',
				payprincipalamt : '0',
				payinteamt : '0',
				a2 : '0',
				a7 : '0',
				a12 : '0',
				a18 : '0',
				amount : '0'

			}]);
	    	
	    	return;
	    }
	}
	
	if (startInaccountdate != null&&startInaccountdate != undefined&&startInaccountdate != ''&&
			endInaccountdate != null&&endInaccountdate != undefined&&endInaccountdate != ''){
		if(startInaccountdate>endInaccountdate){
	    	$.messager.alert("操作提示", "记账日期开始时间大于结束时间","info");
	    	$('#predictcostSumGrid').datagrid('loadData', {
				total : 0,
				rows : []
			});
	    	
	    	$('#predictcostSumGrid').datagrid('reloadFooter',[{
	    		inaccountdate : '<span style="color:red;">合计</span>',
				payprincipalamt : '0',
				payinteamt : '0',
				a2 : '0',
				a7 : '0',
				a12 : '0',
				a18 : '0',
				amount : '0'

			}]);
	    	
	    	return;
	    }
	}
	
	if (startPayDate != null&&startPayDate != undefined&&startPayDate != ''&&
			endPayDate != null&&endPayDate != undefined&&endPayDate != ''){
		if(startPayDate>endPayDate){
	    	$.messager.alert("操作提示", "应付日期开始时间大于结束时间","info");
	    	$('#predictcostSumGrid').datagrid('loadData', {
				total : 0,
				rows : []
			});
	    	
	    	$('#predictcostSumGrid').datagrid('reloadFooter',[{
	    		inaccountdate : '<span style="color:red;">合计</span>',
				payprincipalamt : '0',
				payinteamt : '0',
				a2 : '0',
				a7 : '0',
				a12 : '0',
				a18 : '0',
				amount : '0'

			}]);
	    	
	    	return;
	    }
	}
	
	$('#predictcostSumGrid').datagrid('options').url = contextPath + "/predictcostServer/queryPredictcostListSum.do"; 
	
	$("#predictcostSumGrid").datagrid(
			'load',{
				startDueDate : startDueDate,
				endDueDate : endDueDate,
				belong : belong,
				guaranteeparty : guaranteeparty,
				city : searchCity,
				subproducttype : subproducttype,
				businessmodel : searchBusinessmodel,
				startInaccountdate : startInaccountdate,
				endInaccountdate : endInaccountdate,
				startPayDate : startPayDate,
				endPayDate : endPayDate,
				canceltype : canceltype,
				creditperson : creditperson
				
			}
	);
	var p = $('#predictcostSumGrid').datagrid('getPager'); 
    $(p).pagination({ 
        pageSize: 10,//每页显示的记录条数，默认为10 
        pageList: [5,10,15],//可以设置每页记录条数的列表 
        beforePageText: '第',//页数文本框前显示的汉字 
        afterPageText: '页    共 {pages} 页', 
        displayMsg: '当前显示 {from} - {to} 条记录   共 {total} 条记录' 
	}); 
	
	$.ajax({
		url : contextPath + "/predictcostServer/queryPredictcostSumTotal.do",
		type : "POST",
		data : {
			rows: 10,
			page:1,
			startDueDate : startDueDate,
			endDueDate : endDueDate,
			belong : belong,
			guaranteeparty : guaranteeparty,
			city : searchCity,
			subproducttype : subproducttype,
			businessmodel : searchBusinessmodel,
			startInaccountdate : startInaccountdate,
			endInaccountdate : endInaccountdate,
			startPayDate : startPayDate,
			endPayDate : endPayDate,
			canceltype : canceltype,
			creditperson : creditperson
		},
		success : function(data) {
			var jsondata = eval("(" + data + ")");
			//是否有数据
			if (jsondata.CONTRACTCOUNT == null||jsondata.CONTRACTCOUNT==0) {
				$.messager.alert('提示', '暂无匹配数据!请重新搜索..', 'warning');
				//清空表单数据
				$('#predictcostSumGrid').datagrid('loadData', {
					total : 0,
					rows : []
				});
				
				$('#predictcostSumGrid').datagrid('reloadFooter',[{
					inaccountdate : '<span style="color:red;">合计</span>',
					payprincipalamt : '0',
					payinteamt : '0',
					a2 : '0',
					a7 : '0',
					a12 : '0',
					a18 : '0',
					amount : '0'

				}]);
				
				return;
			}
			
			//重新给表格加载数据
			if (jsondata.CONTRACTCOUNT != 0
					&& jsondata.MONEYCOUNT != null) {
				
				$('#predictcostSumGrid').datagrid('reloadFooter',[{
					inaccountdate : '<span style="color:red;">合计</span>',
					payprincipalamt : jsondata.PAYPRINCIPALAMTAMOUNT,
					payinteamt : jsondata.PAYINTEAMTAMOUNT,
					a2 : jsondata.A2AMOUNT,
					a7 : jsondata.A7AMOUNT,
					a12 : jsondata.A12AMOUNT,
					a18 : jsondata.A18AMOUNT,
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
	var belong = $("#belong").combobox('getValue');
	var contractno = $("#searchContractno").val();
	var startInaccountdate = $("#startInaccountdate").datebox('getValue');
	var endInaccountdate = $("#endInaccountdate").datebox('getValue');
	var url = contextPath+"/predictcostServer/excelExport.do?belong="+belong+"&startPayDate="+startImportDate+"&endPayDate="+endImportDate+"&contractno="+contractno
	+"&startInaccountdate="+startInaccountdate+"&endInaccountdate="+endInaccountdate;
	url = encodeURI(url);
	url = encodeURI(url);
	
	var data=$('#predictcostGrid').datagrid('getData');
	if(data.total==0){
		$.messager.alert("操作提示", "请先查询合同，或没有符合条件的合同","info");
    	return;
	}
	
	$.ajax({
		async : false,
		cache : false,
		type : "POST",
		url : contextPath + "/predictcostServer/excelCheck.do",
		data : {
			startPayDate : startImportDate,
			endPayDate : endImportDate,
			belong : belong,
			contractno : contractno,
			startInaccountdate: startInaccountdate,
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
		success : function(data) {
			$.messager.progress("close");
			if(data.success){				
				window.open(url);
			}else{
				$.messager.alert("操作提示", data.msg,"info");
			}
		}
	});
	
}

function exportXlsSum() {
	// 获取选中数据
	var startDueDate = $("#startDueDate").datebox('getValue');
	var endDueDate = $("#endDueDate").datebox('getValue');
	var belong = $("#belong").combobox('getValue');
	
	var guaranteeparty = $("#guaranteeparty").combobox('getValue');
	var searchCity = $("#searchCity").val();
	var subproducttype = $("#searchSub").combobox('getValue');
	var searchBusinessmodel = $("#searchBusinessmodel").combobox('getValue');
	
	var startInaccountdate = $("#startInaccountdate").datebox('getValue');
	var endInaccountdate = $("#endInaccountdate").datebox('getValue');
	
	var startPayDate = $("#startPayDate").datebox('getValue');
	var endPayDate = $("#endPayDate").datebox('getValue');
	
	var canceltype = $("#canceltype").combobox('getValue');
	
	var creditperson = $("#creditperson").combobox('getValue');
	
	var url = contextPath+"/predictcostServer/excelExportSum.do?belong="+belong+"&startDueDate="+startDueDate+"&endDueDate="+endDueDate
	+"&guaranteeparty="+guaranteeparty+"&city="+searchCity+"&subproducttype="+subproducttype+"&businessmodel="+searchBusinessmodel
	+"&startInaccountdate="+startInaccountdate+"&endInaccountdate="+endInaccountdate
	+"&startPayDate="+startPayDate+"&endPayDate="+endPayDate+"&canceltype="+canceltype+"&creditperson="+creditperson;
	url = encodeURI(url);
	url = encodeURI(url);
	
	var data=$('#predictcostSumGrid').datagrid('getData');
	if(data.total==0){
		$.messager.alert("操作提示", "请先查询合同，或没有符合条件的合同","info");
    	return;
	}
	
	$.ajax({
		async : false,
		cache : false,
		type : "POST",
		url : contextPath + "/predictcostServer/excelCheckSum.do",
		data : {
			startDueDate : startDueDate,
			endDueDate : endDueDate,
			belong : belong,
			guaranteeparty : guaranteeparty,
			city : searchCity,
			subproducttype : subproducttype,
			businessmodel : searchBusinessmodel,
			startInaccountdate : startInaccountdate,
			endInaccountdate : endInaccountdate,
			startPayDate : startPayDate,
			endPayDate : endPayDate,
			canceltype : canceltype,
			creditperson : creditperson
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
			}else{
				$.messager.alert("操作提示", data.msg,"info");
			}
		}
	});
	
}


function openUpdatePredictcost(index){

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
	    	$.messager.alert("操作提示", "记账日期开始时间大于结束时间","info");
	    	return;
	    }
	}
	
	var data=$('#predictcostGrid').datagrid('getData');
	if(data.total==0){
		$.messager.alert("操作提示", "请先查询合同，或没有符合条件的合同","info");
    	return;
	}
	

	$('#updateContractno').textbox('setValue','');
	$('#updateBelong').val('');
	$('#updateBelongShow').textbox('setValue','');
	$('#updateStartPayDate').textbox('setValue','');
	$('#updateEndPayDate').textbox('setValue','');
	$('#updateInaccountdate').datebox('setValue','');
	$('#updateKeepaccountsremark').val('');
	$('#updateStartPayDate').textbox('setValue','');
	$('#updateEndPayDate').textbox('setValue','');
	
	var startImportDate = $("#startImportDate").datebox('getValue');
	var endImportDate = $("#endImportDate").datebox('getValue');
	var belong = $("#belong").combobox('getValue');
	var contractno = $("#searchContractno").val();
	var startInaccountdate = $("#startInaccountdate").datebox('getValue');
	var endInaccountdate = $("#endInaccountdate").datebox('getValue');
	
	if ((startInaccountdate == null||startInaccountdate == undefined||startInaccountdate == '')&&
			(endInaccountdate == null||endInaccountdate == undefined||endInaccountdate == '')
			&&
			(startPayDate == null||startPayDate == undefined||startPayDate == '')&&
			(endPayDate == null||endPayDate == undefined||endPayDate == '')
			&&
			(belong ==null||belong == undefined||belong == '')
			&&
			(contractno ==null||contractno == undefined||contractno == '')
			){
		
		$.messager.confirm("确认", "未选择任何搜索条件，记账会更新所有记录", function (r) { 
			if (r) { 
				toUpdatePredictcost(startImportDate,endImportDate,belong,contractno,startInaccountdate,endInaccountdate,'','','','');
			}else{
				return;
			}
		});  
	    
	}else{
		toUpdatePredictcost(startImportDate,endImportDate,belong,contractno,startInaccountdate,endInaccountdate,'','','','');
	}
	
	

}

function toUpdatePredictcost(startImportDate,endImportDate,belong,contractno,startInaccountdate,endInaccountdate,
		payDate,seqid,payprincipalamt,payinteamt){
	
	$.ajax({
		url : contextPath + "/predictcostServer/checkPredictcost.do",
		type : "POST",
		data : {
			rows: 10,
			page:1,
			startPayDate : startImportDate,
			endPayDate : endImportDate,
			belong : belong,
			contractno : contractno,
			startInaccountdate : startInaccountdate,
			endInaccountdate : endInaccountdate,
			seqid : seqid,
			payprincipalamtStr : payprincipalamt,
			payinteamtStr : payinteamt
			
		},
		success : function(data) {
			$.messager.progress('close');
			data = eval("("+data+")");
			if(data.success){
				$("#updatePredictcostWin").show();
				$win = $("#updatePredictcostWin").window({
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
				
				if(contractno !=null&&contractno != undefined&&contractno != ''){
					$('#updateContractno').textbox('setValue',contractno);
				}else{
					$('#updateContractno').textbox('setValue',$('#searchContractno').val());
				}
				
				if(belong !=null&&belong != undefined&&belong != ''){
					$('#updateBelong').val(belong);
					$('#updateBelongShow').textbox('setValue',$("#belong").combobox('getText'));
				}else{
					$('#updateBelong').val($("#belong").combobox('getValue'));
					$('#updateBelongShow').textbox('setValue',$("#belong").combobox('getText'));
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

function updatePredictcost(){
	
	
	
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
	
	$.messager.confirm("确认", "确认记账(记账数据为所选搜索条件下所有合同，如只对单个合同进行操作，请在搜索框输入该合同的合同号)", function (r) {  
        if (r) {  
        	var updateStartPayDate = $("#updateStartPayDate").val();
        	var updateEndPayDate = $("#updateEndPayDate").val();
        	var updateBelong = $("#updateBelong").val();
        	var updateContractno = $("#updateContractno").val();
        	var updateKeepaccountsremark = $("#updateKeepaccountsremark").val();
        	var updateStartInaccountdate = $("#updateStartInaccountdate").val();
        	var updateEndInaccountdate = $("#updateEndInaccountdate").val();
        	
        	var updateSeqid = $("#updateSeqid").val();
        	var updatePayprincipalamt = $("#updatePayprincipalamtStr").val();
        	var updatePayinteamt = $("#updatePayinteamtStr").val();
        	$.messager.progress({ title: '正在操作...', msg: '数据记账中...'});
        	$.ajax({
        		url : contextPath + "/predictcostServer/updatePredictcost.do",
        		type : "POST",
        		data : {
        			rows: 10,
        			page:1,
        			startPayDate : updateStartPayDate,
        			endPayDate : updateEndPayDate,
        			belong : updateBelong,
        			contractno : updateContractno,
        			inaccountdate : updateInaccountdate,
        			inaccountremark : updateKeepaccountsremark,
        			startInaccountdate : updateStartInaccountdate,
        			endInaccountdate : updateEndInaccountdate,
        			seqid : updateSeqid,
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
        				 closeWin('updatePredictcostWin');
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
	if (val == '1'){
		return '<span style="color:green;">正常还款</span>';
	} else if(val == '5') {
		return '<span style="color:blue;">提前还款</span>';
	} else{
		return '<span style="color:red;">无</span>';
	}
}


function openCancelPredictcost(index){

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
	    	$.messager.alert("操作提示", "记账日期开始时间大于结束时间","info");
	    	return;
	    }
	}
	
	var data=$('#predictcostGrid').datagrid('getData');
	if(data.total==0){
		$.messager.alert("操作提示", "请先查询合同，或没有符合条件的合同","info");
    	return;
	}
	

	$('#cancelContractno').textbox('setValue','');
	$('#cancelBelong').val('');
	$('#cancelBelongShow').textbox('setValue','');
	$('#cancelStartPayDate').textbox('setValue','');
	$('#cancelEndPayDate').textbox('setValue','');
	$('#cancelInaccountdate').datebox('setValue','');
	$('#cancelStartPayDate').textbox('setValue','');
	$('#cancelEndPayDate').textbox('setValue','');
	
	var startImportDate = $("#startImportDate").datebox('getValue');
	var endImportDate = $("#endImportDate").datebox('getValue');
	var belong = $("#belong").combobox('getValue');
	var contractno = $("#searchContractno").val();
	var startInaccountdate = $("#startInaccountdate").datebox('getValue');
	var endInaccountdate = $("#endInaccountdate").datebox('getValue');
	
	if ((startInaccountdate == null||startInaccountdate == undefined||startInaccountdate == '')&&
			(endInaccountdate == null||endInaccountdate == undefined||endInaccountdate == '')
			&&
			(startPayDate == null||startPayDate == undefined||startPayDate == '')&&
			(endPayDate == null||endPayDate == undefined||endPayDate == '')
			&&
			(belong ==null||belong == undefined||belong == '')
			&&
			(contractno ==null||contractno == undefined||contractno == '')
			){
		
		$.messager.confirm("确认", "未选择任何搜索条件，撤销记账会更新所有记录", function (r) { 
			if (r) { 
				toCancelpredictcost(startImportDate,endImportDate,belong,contractno,startInaccountdate,endInaccountdate);
			}else{
				return;
			}
		});  
	    
	}else{
		toCancelpredictcost(startImportDate,endImportDate,belong,contractno,startInaccountdate,endInaccountdate);
	}
	
	

}

function toCancelpredictcost(startImportDate,endImportDate,belong,contractno,startInaccountdate,endInaccountdate){
	$.ajax({
		url : contextPath + "/predictcostServer/checkCancelPredictcost.do",
		type : "POST",
		data : {
			rows: 10,
			page:1,
			startPayDate : startImportDate,
			endPayDate : endImportDate,
			belong : belong,
			contractno : contractno,
			startInaccountdate : startInaccountdate,
			endInaccountdate : endInaccountdate
			
		},
		success : function(data) {
			$.messager.progress('close');
			data = eval("("+data+")");
			if(data.success){
				$("#cancelPredictcostWin").show();
				$win = $("#cancelPredictcostWin").window({
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
				
				$('#cancelContractno').textbox('setValue',$('#searchContractno').val());
				$('#cancelBelong').val($("#belong").combobox('getValue'));
				$('#cancelBelongShow').textbox('setValue',$("#belong").combobox('getText'));
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

function cancelPredictcost(){
	
	
	$.messager.confirm("确认", "确认撤销(撤销数据为所选搜索条件下所有合同，如只对单个合同进行操作，请在搜索框输入该合同的合同号)", function (r) {  
        if (r) {  
        	var cancelStartPayDate = $("#cancelStartPayDate").val();
        	var cancelEndPayDate = $("#cancelEndPayDate").val();
        	var cancelBelong = $("#cancelBelong").val();
        	var cancelContractno = $("#cancelContractno").val();
        	var cancelStartInaccountdate = $("#cancelStartInaccountdate").val();
        	var cancelEndInaccountdate = $("#cancelEndInaccountdate").val();
        	$.messager.progress({ title: '正在操作...', msg: '数据撤销中...'});
        	$.ajax({
        		url : contextPath + "/predictcostServer/cancelPredictcost.do",
        		type : "POST",
        		data : {
        			rows: 10,
        			page:1,
        			startPayDate : cancelStartPayDate,
        			endPayDate : cancelEndPayDate,
        			belong : cancelBelong,
        			contractno : cancelContractno,
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
        				 closeWin('cancelPredictcostWin');
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

function predictcostMultipleUpdate(){
	
	var selectedRow = $('#predictcostGrid').datagrid('getSelections');
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
		url : contextPath + "/predictcostServer/updatePredictcost.do",
		type : "POST",
		data : {
			seqid : multipleSeqid,
			contractno : multipleSerialno,
			belong : multipleAssetbelong,
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
				 closeWin('predictcostMultipleDlg');
			}else{
				$.messager.alert("操作提示", data.message,"error");
			}

		},
		error: function (data) { 
			 $.messager.alert("操作提示", data.message,"error");
        }
	}); 
}

function predictcostMultipleCancel(){
	
	var selectedRow = $('#predictcostGrid').datagrid('getSelections');
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
		url : contextPath + "/predictcostServer/cancelPredictcost.do",
		type : "POST",
		data : {
			seqid : multipleSeqid,
			contractno : multipleSerialno,
			belong : multipleAssetbelong,
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
				 closeWin('predictcostMultipleDlg');
			}else{
				$.messager.alert("操作提示", data.message,"error");
			}

		},
		error: function (data) { 
			 $.messager.alert("操作提示", data.message,"error");
        }
	}); 
}


function formatKeepaccountsStatus(val,row){
	if (val == '1'){
		return '<span style="color:green;">已记账</span>';
	} else if(val == '2') {
		return '<span style="color:red;">已撤销</span>';
	} else if(row.inaccountdate !='<span style="color:red;">合计</span>'){
		return '<span style="color:blue;">未记账</span>';
	}
}