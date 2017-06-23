function query() {
		var startTransDate = $("#startTransDate").datebox('getValue');
		var endTransDate = $("#endTransDate").datebox('getValue');
		var transType = $("#transType").combobox('getValue');
		var dataSource = $("#dataSource").combobox('getValue');
		var belong = $("#belong").textbox('getValue');
		$("#clearanceCertificateGrid").datagrid('options').url = contextPath + "/dayTradingServer/queryDayTradingDetail.do";
		$("#clearanceCertificateGrid").datagrid('load',{
			startTransDate : startTransDate,
			endTransDate : endTransDate,
			transType : transType,
			dataSource : dataSource,
			belong : belong
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
			url : contextPath + "/dayTradingServer/queryDayTradingDetail.do",
			type : "POST",
			data : {
				rows: 10,
				page:1,
				startTransDate : startTransDate,
				endTransDate : endTransDate,
				transType : transType,
				dataSource : dataSource,
				belong : belong
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
						transDate : '<span style="color:red;">合计</span>',
						principalamt : "0.00",
						inteamt : "0.00",
						payPrincipalamt : "0.00",
						receivePrincipalamt : "0.00",
						payInteamt : "0.00",
						receiveInteamt : "0.00",
						payPureoverflowsum : "0.00",
						pureoverflowsum : "0.00",
						a2 : "0.00",
						a7 : "0.00",
						a9 : "0.00",
						a10: "0.00",
						a11: "0.00",
						a12: "0.00",
						a17: "0.00",
						a18: "0.00",
						a19: "0.00",
						a22: "0.00"
					}]);
				}else{
					$('#clearanceCertificateGrid').datagrid('reloadFooter',[{
						transDate : '<span style="color:red;">合计</span>',
						principalamt : jsondata.rows[0].principalamtSum,
						inteamt : jsondata.rows[0].inteamtSum,
						payPrincipalamt : jsondata.rows[0].payPrincipalamtSum,
						receivePrincipalamt : jsondata.rows[0].receivePrincipalamtSum,
						payInteamt : jsondata.rows[0].payInteamtSum,
						receiveInteamt : jsondata.rows[0].receiveInteamtSum,
						payPureoverflowsum : jsondata.rows[0].payPureoverflowsumTotal,
						pureoverflowsum : jsondata.rows[0].pureoverflowsumTotal,
						a2 : jsondata.rows[0].a2Sum,
						a7 : jsondata.rows[0].a7Sum,
						a9 : jsondata.rows[0].a9Sum,
						a10: jsondata.rows[0].a10Sum,
						a11: jsondata.rows[0].a11Sum,
						a12: jsondata.rows[0].a12Sum,
						a17: jsondata.rows[0].a17Sum,
						a18: jsondata.rows[0].a18Sum,
						a19: jsondata.rows[0].a19Sum,
						a22: jsondata.rows[0].a22Sum
					}]);
				}
			},
			error : function() {
				$.messager.alert("操作提示", data.message, "error");
			}
		});
	}
	
	function exportExcel(path){
		var startTransDate = $("#startTransDate").datebox('getValue');
		var endTransDate = $("#endTransDate").datebox('getValue');
		var transType = $("#transType").combobox('getValue');
		var dataSource = $("#dataSource").combobox('getValue');
		var belong = $("#belong").textbox('getValue');
		$.ajax({
			url : contextPath + "/dayTradingServer/queryDayTradingDetailCount.do",
			type : "POST",
			data : {
				startTransDate : startTransDate,
				endTransDate : endTransDate,
				transType : transType,
				dataSource : dataSource,
				belong : belong
			},
			success : function(data) {
				var jsondata = eval("(" + data + ")");
				//是否有数据
				if (jsondata.contractCount == 0) {
					$.messager.alert('提示', '暂无匹配数据,请更换查询条件!', 'warning');
				}else if (jsondata.contractCount < 500000) {
					window.location.href=path+'/dayTradingServer/excelExport.do?startTransDate='+startTransDate+
					'&endTransDate='+endTransDate+"&transType="+transType+"&dataSource="+dataSource+"&belong="+belong
				}else{
					$.messager.alert('提示', '数据量过大!请更换查询条件...', 'warning');
				}
			},
			error : function() {
				$.messager.alert('提示', '导出失败,请稍后再试...', 'error');
			}
		})
	}
	
	function isNullOrEmpty(str){
		if(typeof(str) == 'undefined'){
			return '';
		}else{
			return str;
		}
	}
	
	function parseSum(val){
		var a = '<span style="color:red;">'+ thousandthFormat(val == null ? 0 : val, 2) +'</span>';
		return a;
	}