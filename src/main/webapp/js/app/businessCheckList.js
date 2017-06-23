function query() {
	var startImportDate = $("#startImportDate").datebox('getValue');
	var endImportDate = $("#endImportDate").datebox('getValue');
	$.ajax({
		url : contextPath + "/businessCheckListServer/queryBusinessCheckList.do",
		type : "POST",
		data : {
			rows: 10,
			page:1,
			startImportDate : startImportDate,
			endImportDate : endImportDate
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
				$("#contractCount").textbox('setValue',
						"0");
				$("#moneyCount").textbox('setValue', 
						"0.0");
			}
			//重新给控件加载数据
			if (jsondata.rows[0].contractCount != 0
					&& jsondata.rows[0].moneyCount != null) {
				$("#contractCount").textbox('setValue',
						jsondata.rows[0].contractCount);
				$("#moneyCount")
						.textbox('setValue', jsondata.rows[0].moneyCount);
			} 
			//重新给表格加载数据
			$("#clearanceCertificateGrid").datagrid('loadData', jsondata);
			$("#clearanceCertificateGrid").datagrid({
				url : contextPath + "/businessCheckListServer/queryBusinessCheckList.do"
			});
			$("#clearanceCertificateGrid").datagrid('loadExt',{
				rows: 10,
				page:1,
				startImportDate : startImportDate,
				endImportDate : endImportDate
			});
			var p = $('#clearanceCertificateGrid').datagrid('getPager'); 
	        $(p).pagination({ 
		        pageSize: 10,//每页显示的记录条数，默认为10 
		        pageList: [5,10,15],//可以设置每页记录条数的列表 
		        beforePageText: '第',//页数文本框前显示的汉字 
		        afterPageText: '页    共 {pages} 页', 
		        displayMsg: '当前显示 {from} - {to} 条记录   共 {total} 条记录' 
			}); 
		},
		error: function () { 
			 $.messager.alert("操作提示", data.message,"error");
        }
	}); 
}

function imports(){
	$('#importdlg').show();
	$('#importdlg').dialog({
	    title  : '导入',
	    width  : 300,
	    height : 300,
	    cache  : false,
	    modal  : true,	
	    hcenter :true,
	    resizable : true,
	    });
};

//合同信息上传
function importContract() {   
	// 得到上传文件的全路径
	var fileName = $('#uploadContractExcel').filebox('getValue');
	// 获取题型
	if (fileName != "") {
		// 进行基本校验
		if (fileName == "") {
			$.messager.alert('提示', '请选择上传文件！', 'warning');
		} else {
			// 对文件格式进行校验
			var d1 = /\.[^\.]+$/.exec(fileName);
			if (d1 == ".xls" || d1 == ".xlsx") {
				$.messager.progress({ title: 'excel数据导入中', msg: '数据导入中...'});
				// 提交表单
				$("#uploadCntractExcelFrm").form("submit", {
					url : contextPath + '/businessCheckListServer/importContract.do',
					onSubmit : function() {
					},
					success : function(result) {
						$.messager.progress('close');
						alert(result);
						var jsondata = jQuery.parseJSON(result);;
						//重新给表格加载数据
		 				$("#clearanceCertificateGrid").datagrid('loadData', jsondata.obj);
		 				$("#importdlg").window("close");
		 				if(!jsondata.success){
		 					$.messager.confirm('操作提示','当前导入日期已存在,是否继续导入?',function(r){   
							    if (r){
							    	$.messager.confirm('操作提示','请仔细核对数据无误后点击确定再次导入!',function(r){   
										if(r){
									    	$.ajax({  
									    		url : contextPath + '/businessCheckListServer/saveImportData.do',
									    	    type: "POST",  
									    	    success: function (data) {  
									    	    	$.messager.alert("操作提示", "导入成功!", "info");
									    	    	queryChangeData(data);
									    	    },
												error : function() {
													$.messager.alert("操作提示", '导入失败,请稍后重试或尝试联系系统管理员!', "error");
												}  
									    	}); 
										}
									});
							    }
							});
		 				}else{
		 					$.messager.confirm('操作提示','请仔细核对数据无误后点击确定再次导入!',function(r){   
								if(r){
							    	$.ajax({  
							    		url : contextPath + '/BusinessCheckListServer/saveImportData.do',
							    	    type: "POST",  
							    	    success: function (data) {  
							    	    	$.messager.alert("操作提示", "导入成功!", "info");
							    	    	queryChangeData(data);
							    	    },
										error : function() {
											$.messager.alert("操作提示", '导入失败,请稍后重试或尝试联系系统管理员!', "error");
										}  
							    	}); 
								}
							});
		 				}
		 				
					}
				});
			} else {
				$.messager.alert('提示', '请选择xls格式文件！', 'warning');
				$('#uploadContractExcel').filebox('setValue', '');
			}
		}
	} else {
		$.messager.alert('提示', '请选择需上传的文件！', 'warning');
	}
	
}