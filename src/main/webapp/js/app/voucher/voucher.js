var flagInterval;
function getVoucherScrollBar() {
	$("#ProgressBars").css("display", "block");
	$.ajax({
		url : contextPath+'/voucher/voucherScrollBar.do',
		type : "post",
		data : "",
		success : function(result) {
			console.log("tanglin:"+result);
            if(result.obj.flag==false){
            	$("#rollContent").html("正在下载("+result.obj.currentNode+")");
    			$("#PBar").css("width",result.obj.percentage+"%");
    			$("#pencent").html(result.obj.percentage+"%");
    			$("#loading").html("凭证["+result.obj.node+".xls]正在导出中,请稍等.....");
            }			
			 if(result.obj==true){
				 clearInterval(flagInterval);
				 $("#PBar").css("width","100%");
				 $("#pencent").html("100%");
				 $.messager.alert('下载提示','恭喜您,凭证导出成功','info');
				 setTimeout(function(){
					 $("#bolckdiv").css("display", "none");
				 },2000);
			}
		}
	});
}