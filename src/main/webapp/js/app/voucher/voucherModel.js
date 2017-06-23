//凭证模板信息上传
function importModel() {
	var fileName = $('#uploadModelExcel').filebox('getValue');
	if (fileName != "") {
		// 校验名称
		if (fileName == "") {
			$.messager.alert('提示', '请选择上传文件！', 'warning');
		} else {
			// 对文件格式进行校验
			var d1 = /\.[^\.]+$/.exec(fileName);
			if (d1 == ".xls" || d1 == ".xlsx") {
				$("#bolckdiv").css("display", "block");
				// 提交表单
				$("#uploadModelExcelFrm").form("submit", {
					url : contextPath + '/voucher/importVoucher.do',
					onSubmit : function() {
					},
					success : function(result) {
						$.messager.alert('下载提示','恭喜您,凭证模板上传成功','info');
						setTimeout(function(){
							 $("#bolckdiv").css("display", "none");
						 },2000);
					},
					error : function() {
						$.messager.alert("操作提示", '导入失败,请稍后重试或尝试联系系统管理员!', "error");
					}  
				});
			} else {
				$.messager.alert('提示', '请选择xls格式文件！', 'warning');
				$('#uploadModelExcel').filebox('setValue', '');
			}
		}
	} else {
		$.messager.alert('提示', '请选择需上传的文件！', 'warning');
	}
	
}