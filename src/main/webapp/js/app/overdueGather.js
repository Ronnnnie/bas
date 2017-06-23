function query() {
		var startInAccountDate = $("#startInAccountDate").datebox('getValue');
		var endInAccountDate = $("#endInAccountDate").datebox('getValue');
		var businessModel = $("#businessModel").combobox('getValue');
		var subProductType = $("#subProductType").combobox('getValue');
		var city = $("#city").textbox('getValue');
		var overdueremark = $("#overdueremark").combobox('getValue');
		var classfy = $("#classfy").combobox('getValue');
		var canceltype = $("#canceltype").combobox('getValue');
		var assetBelong = $("#assetBelong").combobox('getValue');
		var guaranteeParty = $("#guaranteeParty").combobox('getValue');
		var today = getDate();
		if(startInAccountDate <= today && endInAccountDate <= today){
			$("#clearanceCertificateGrid").datagrid('options').url = contextPath + "/overdueServer/queryOverdueGather.do";
			$("#clearanceCertificateGrid").datagrid('load',{
				startInAccountDate : startInAccountDate,
				endInAccountDate : endInAccountDate,
				businessModel : businessModel,
				subProductType : subProductType,
				city : city,
				overdueremark : overdueremark,
				classfy : classfy,
				canceltype : canceltype,
				assetBelong : assetBelong,
				guaranteeParty : guaranteeParty
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
				url : contextPath + "/overdueServer/queryOverdueGather.do",
				type : "POST",
				data : {
					rows: 10,
					page:1,
					startInAccountDate : startInAccountDate,
					endInAccountDate : endInAccountDate,
					businessModel : businessModel,
					subProductType : subProductType,
					city : city,
					overdueremark : overdueremark,
					classfy : classfy,
					canceltype : canceltype,
					assetBelong : assetBelong,
					guaranteeParty : guaranteeParty
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
					} else {
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
					$.messager.alert("操作提示", data.message, "error");
				}
			});
		}else{
			$.messager.alert("操作提示",'选择日期不能大于当前日期,请重新选择!', "error");
		}
	}

function ExportExcel(path){
	var startInAccountDate = $("#startInAccountDate").datebox('getValue');
	var endInAccountDate = $("#endInAccountDate").datebox('getValue');
	var businessModel = $("#businessModel").combobox('getValue');
	var subProductType = $("#subProductType").combobox('getValue');
	var city = $("#city").textbox('getValue');
	var overdueremark = $("#overdueremark").combobox('getValue');
	var classfy = $("#classfy").combobox('getValue');
	var canceltype = $("#canceltype").combobox('getValue');
	var assetBelong = $("#assetBelong").combobox('getValue');
	var guaranteeParty = $("#guaranteeParty").combobox('getValue');
	$.ajax({
		url : contextPath + "/overdueServer/queryOverdueGatherCount.do",
		type : "POST",
		data : {
			startInAccountDate : startInAccountDate,
			endInAccountDate : endInAccountDate,
			businessModel : businessModel,
			subProductType : subProductType,
			city : city,
			overdueremark : overdueremark,
			classfy : classfy,
			canceltype : canceltype,
			assetBelong : assetBelong,
			guaranteeParty : guaranteeParty
		},
		success : function(data) {
			var jsondata = eval("(" + data + ")");
			var url = contextPath+'/overdueServer/overdueGatherExcelExport.do?startInAccountDate='+startInAccountDate+
			'&endInAccountDate='+endInAccountDate+'&businessModel='+businessModel+'&subProductType='+subProductType+
			'&city='+city+'&overdueremark='+overdueremark+'&classfy='+classfy+
			'&canceltype='+canceltype+'&assetBelong='+assetBelong+'&guaranteeParty='+guaranteeParty;
			//是否有数据
			if (jsondata.contractCount < 500000) {
				window.location.href=url;
			}else{
				$.messager.alert('提示', '数据量过大!请更换条件...', 'warning');
			}
		},
		error : function() {
			$.messager.alert('提示', '导出错误,请重试!', 'error');
		}
	});
}

function checkMoney(money){
	if(money == null){
		return '0.0';
	}
	return money;
}