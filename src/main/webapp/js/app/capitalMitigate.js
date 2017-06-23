function query() {
		var contractNo = $("#contractNo").textbox('getValue');
		var startPayDate = $("#startPayDate").datebox('getValue');
		var endPayDate = $("#endPayDate").datebox('getValue');
		$("#clearanceCertificateGrid").datagrid('options').url = contextPath + "/capitalMitigateServer/queryCapitalMitigate.do";
		$("#clearanceCertificateGrid").datagrid('load',{
			contractNo : contractNo,
			startPayDate : startPayDate,
			endPayDate : endPayDate
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
			url : contextPath + "/capitalMitigateServer/queryCapitalMitigate.do",
			type : "POST",
			data : {
				rows: 10,
				page:1,
				contractNo : contractNo,
				startPayDate : startPayDate,
				endPayDate : endPayDate
			},
			success : function(data) {
				var jsondata = eval("(" + data + ")");

				//是否有数据
				if (jsondata.rows == "") {
					$.messager.alert('提示', '暂无匹配数据!请重新搜索..', 'warning');
					//清空表单数据
					$('#clearanceCertificateGrid').datagrid('reloadFooter',[{
						contractNo : '合计',
						businessSum : '0.00',
						payprinciPalamt : '0.00',
						payInteamt : '0.00'
					}]);
					$('#clearanceCertificateGrid').datagrid('loadData', {
						total : 0,
						rows : []
					});
				}else{
					$('#clearanceCertificateGrid').datagrid('reloadFooter',[{
						contractNo : '合计',
						businessSum : jsondata.rows[0].businessSumCount,
						payprinciPalamt : jsondata.rows[0].payprinciPalamtSum,
						payInteamt : jsondata.rows[0].payInteamtSum
					}]);
				}
				//重新给表格加载数据
			},
			error : function() {
				$.messager.alert("操作提示", "查询失败,请稍后重试!", "error");
			}
		});
	}

function dataExport(){
	var contractNo = $("#contractNo").textbox('getValue');
	var startPayDate = $("#startPayDate").datebox('getValue');
	var endPayDate = $("#endPayDate").datebox('getValue');
	window.location.href=contextPath+'/capitalMitigateServer/capitalMitigateExcelExport.do?contractNo='+contractNo+'&startPayDate='+startPayDate+'&endPayDate='+endPayDate;
}