function query() {
		var startcheckdate = $("#startcheckdate").datebox('getValue');
		var endcheckdate = $("#endcheckdate").datebox('getValue');
		var datasource = $("#datasource").combobox('getValue');
		$("#clearanceCertificateGrid").datagrid('options').url = contextPath + "/accountTotalCheckingAccountServer/queryAccountTotalCheckingAccount.do";
		$("#clearanceCertificateGrid").datagrid('load',{
			startcheckdate : startcheckdate,
			endcheckdate : endcheckdate,
			datasource : datasource
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
			url : contextPath + "/accountTotalCheckingAccountServer/queryAccountTotalCheckingAccount.do",
			type : "POST",
			data : {
				rows: 10,
				page:1,
				startcheckdate : startcheckdate,
				endcheckdate : endcheckdate,
				datasource : datasource
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
						checknumber : '<span style="color:red;">差异</span>',
						normal_withhold_back : "0.00",
						pre_withhold_back : "0.00",
						temp_withhold_back : "0.00",
						temp_withhold_back_hang : "0.00",
						active_deposit : "0.00",
						push_total : "0.00",
						active_deposit_hang : "0.00",
						tm1_deposit_balance : "0.00",
						today_deposit_balance : "0.00"
					}]);
				}else{
					$('#clearanceCertificateGrid').datagrid('reloadFooter',[{
						checknumber : '<span style="color:red;">差异</span>',
						normal_withhold_back : jsondata.rows[0].normal_withhold_backSum,
						pre_withhold_back : jsondata.rows[0].pre_withhold_backSum,
						temp_withhold_back : jsondata.rows[0].temp_withhold_backSum,
						temp_withhold_back_hang : jsondata.rows[0].temp_withhold_back_hangSum,
						active_deposit : jsondata.rows[0].active_depositSum,
						push_total : jsondata.rows[0].push_totalSum,
						active_deposit_hang : jsondata.rows[0].active_deposit_hangSum,
						tm1_deposit_balance : jsondata.rows[0].tm1_deposit_balanceSum,
						today_deposit_balance : jsondata.rows[0].today_deposit_balanceSum
					}]);
				}
			},
			error : function() {
				$.messager.alert("操作提示", data.message, "error");
			}
		});
	}
	
	function exportExcel(path){
		var startcheckdate = $("#startcheckdate").datebox('getValue');
		var endcheckdate = $("#endcheckdate").datebox('getValue');
		var datasource = $("#datasource").combobox('getValue');
		$.ajax({
			url : contextPath + "/accountTotalCheckingAccountServer/queryAccountTotalCheckingAccountTotal.do",
			type : "POST",
			data : {
				startcheckdate : startcheckdate,
				endcheckdate : endcheckdate,
				datasource : datasource
			},
			success : function(data) {
				var jsondata = eval("(" + data + ")");
				//是否有数据
				if (jsondata.contractCount == 0) {
					$.messager.alert('提示', '暂无匹配数据,请更换查询条件!', 'warning');
				}else if (jsondata.contractCount < 500000) {
					window.location.href=path+'/accountTotalCheckingAccountServer/exportExcel.do?startcheckdate='+startcheckdate+
					'&endcheckdate='+endcheckdate+"&datasource="+datasource
				}else{
					$.messager.alert('提示', '数据量过大!请更换查询条件...', 'warning');
				}
			},
			error : function() {
				$.messager.alert('提示', '导出失败,请稍后再试...', 'error');
			}
		})
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
	
	function parseSum(val){
		var a = '<span style="color:red;">'+ thousandthFormat(val == null ? 0 : val, 2) +'</span>';
		return a;
	}