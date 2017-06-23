function query() {
		var startInputDate = $("#startInputDate").datebox('getValue');
		var endInputDate = $("#endInputDate").datebox('getValue');
		$("#clearanceCertificateGrid").datagrid('options').url = contextPath + "/bankWithholdServer/queryBankWithhold.do";
		$("#clearanceCertificateGrid").datagrid('load',{
			startInputDate : startInputDate,
			endInputDate : endInputDate
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
			url : contextPath + "/bankWithholdServer/queryBankWithhold.do",
			type : "POST",
			data : {
				rows: 10,
				page:1,
				startInputDate : startInputDate,
				endInputDate : endInputDate
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
						dep_bod : '0.00',
						unmatch_bod : '0.00',
						bank_bank : '0.00',
						bank_core : '0.00',
						bank_dif : '0.00',
						bank_match : '0.00',
						bank_unmatch : '0.00',
						bank_roll_hand : '0.00',
						bank_match_hand : '0.00',
						unmatch_eod : '0.00',
						ebu_bank : '0.00',
						ebu_core1: '0.00',
						ebu_core: '0.00',
						ebu_dif: '0.00',
						kft_bank: '0.00',
						kft_core: '0.00',
						kft_dif: '0.00',
						hbk_bank: '0.00',
						hbk_core: '0.00',
						hbk_dif: '0.00',
						kfts_bank: '0.00',
						kfts_core: '0.00',
						kfts_dif: '0.00',
						cft_bank: '0.00',
						check_cft_core: '0.00',
						cft_core: '0.00',
						cft_core_fee: '0.00',
						cft_dif: '0.00',
						refund: '0.00',
						pay: '0.00',
						prepay: '0.00',
						va_amt: '0.00',
						dep_eod: '0.00',
						unmatch_dif: '0.00',
						dep_dif: '0.00',
						loan_rep: '0.00'
					}]);
				}else{
					$('#clearanceCertificateGrid').datagrid('reloadFooter',[{
						inputdate : '<span style="color:red;">合计</span>',
						dep_bod : jsondata.rows[0].dep_bodsum,
						unmatch_bod : jsondata.rows[0].unmatch_bodsum,
						bank_bank : jsondata.rows[0].bank_banksum,
						bank_core : jsondata.rows[0].bank_coresum,
						bank_dif : jsondata.rows[0].bank_difsum,
						bank_match : jsondata.rows[0].bank_matchsum,
						bank_unmatch : jsondata.rows[0].bank_unmatchsum,
						bank_roll_hand : jsondata.rows[0].bank_roll_handsum,
						bank_match_hand : jsondata.rows[0].bank_match_handsum,
						unmatch_eod : jsondata.rows[0].unmatch_eodsum,
						ebu_bank : jsondata.rows[0].ebu_banksum,
						ebu_core1: jsondata.rows[0].ebu_core1sum,
						ebu_core: jsondata.rows[0].ebu_coresum,
						ebu_dif: jsondata.rows[0].ebu_difsum,
						kft_bank: jsondata.rows[0].kft_banksum,
						kft_core: jsondata.rows[0].kft_coresum,
						kft_dif: jsondata.rows[0].kft_difsum,
						hbk_bank: jsondata.rows[0].hbk_banksum,
						hbk_core: jsondata.rows[0].hbk_coresum,
						hbk_dif: jsondata.rows[0].hbk_difsum,
						kfts_bank: jsondata.rows[0].kfts_banksum,
						kfts_core: jsondata.rows[0].kfts_coresum,
						kfts_dif: jsondata.rows[0].kfts_difsum,
						cft_bank: jsondata.rows[0].cft_banksum,
						check_cft_core: jsondata.rows[0].check_cft_coresum,
						cft_core: jsondata.rows[0].cft_coresum,
						cft_core_fee: jsondata.rows[0].cft_core_feesum,
						cft_dif: jsondata.rows[0].cft_difsum,
						refund: jsondata.rows[0].refundsum,
						pay: jsondata.rows[0].paysum,
						prepay: jsondata.rows[0].prepaysum,
						va_amt: jsondata.rows[0].va_amtsum,
						dep_eod: jsondata.rows[0].dep_eodsum,
						unmatch_dif: jsondata.rows[0].unmatch_difsum,
						dep_dif: jsondata.rows[0].dep_difsum,
						loan_rep: jsondata.rows[0].loan_repsum
					}]);
				}
			},
			error : function() {
				$.messager.alert("操作提示", data.message, "error");
			}
		});
	}
	
	function exportExcel(path){
		var startInputDate = $("#startInputDate").datebox('getValue');
		var endInputDate = $("#endInputDate").datebox('getValue');
		$.ajax({
			url : contextPath + "/bankWithholdServer/queryBankWithholdCount.do",
			type : "POST",
			data : {
				startInputDate : startInputDate,
				endInputDate : endInputDate
			},
			success : function(data) {
				var jsondata = eval("(" + data + ")");
				//是否有数据
				if (jsondata.contractcount == 0) {
					$.messager.alert('提示', '暂无匹配数据,请更换查询条件!', 'warning');
				}else if (jsondata.contractcount < 500000) {
					window.location.href=path+'/bankWithholdServer/exportExcel.do?startInputDate='+startInputDate+
					'&endInputDate='+endInputDate;
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

	function parseSum(val){
		var a = '<span style="color:red;">'+ thousandthFormat(val == null ? 0 : val, 2) +'</span>';
		return a;
	}