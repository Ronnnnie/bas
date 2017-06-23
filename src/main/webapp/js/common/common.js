
     function openOperateWindow(url,wtitle){
    	$("#iframe").attr("src",url);
    	$('#w').window({modal:true,title:wtitle});	            
    	$('#w').window('open');
    }

     //加载等待样式  msg : 正在加载，请稍后...
     function createWait(msg) {
    	 var h = document.body.clientHeight; 
		 $("<div class=\"datagrid-mask\"></div>").css({display:"block",width:"100%",height:h}).appendTo("body"); 
		 $("<div class=\"datagrid-mask-msg\"></div>").html(msg).appendTo("body").css({display:"block",
		  left:($(document.body).outerWidth(true) - 190) / 2,
		  top:(h - 45) / 2});    	 
     }
     
     //加载完成关闭
     function closeWait() {
    	 $('.datagrid-mask-msg').remove();
	     $('.datagrid-mask').remove();
     }
     
     function addTabs(title,url){
         parent.closeTab(title);
         parent.addTab(title,url);
     }     