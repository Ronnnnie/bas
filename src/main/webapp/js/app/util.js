//version : 20170328
function myformatter(date) {
		var y = date.getFullYear();
		var m = date.getMonth() + 1;
		var d = date.getDate();
		return y + '/' + (m < 10 ? ('0' + m) : m) + '/'
				+ (d < 10 ? ('0' + d) : d);
	}

function myparser(s) {
	if (!s)
		return new Date();
	var ss = (s.split('/'));
	var y = parseInt(ss[0], 10);
	var m = parseInt(ss[1], 10);
	var d = parseInt(ss[2], 10);
	if (!isNaN(y) && !isNaN(m) && !isNaN(d)) {
		return new Date(y, m - 1, d);
		} else {
		return new Date();
	}
}


function getDate() {
    var date = new Date();
    var seperator1 = "/";
    var month = date.getMonth() + 1;
    var strDate = date.getDate();
    if (month >= 1 && month <= 9) {
        month = "0" + month;
    }
    if (strDate >= 0 && strDate <= 9) {
        strDate = "0" + strDate;
    }
    var currentdate = date.getFullYear() + seperator1 + month + seperator1 + strDate;
    return currentdate;
}
	
	
function closeWin(win) {
	$("#" + win).window("close");
}

function clearForm(formName){
	$('#'+formName).form('reset');
}

function thousandthFormatter (val,row) {
	if(row.inaccountdate == '<span style="color:red;">合计</span>' || row.contractNo == '<span style="color:red;">合计</span>' 
		|| row.keepaccountsDate == '<span style="color:red;">合计</span>' 
			|| row.keepAccountsTime == '<span style="color:red;">合计</span>' 
				|| row.inAccountDate == '<span style="color:red;">合计</span>' 
					|| row.keepAccountsDate == '<span style="color:red;">合计</span>'
						|| row.keepaccountsdate == '<span style="color:red;">合计</span>'
							|| row.transDate == '<span style="color:red;">合计</span>'
								|| row.accordmonth == '<span style="color:red;">合计</span>'){
		return '<span style="color:red;">'+ thousandthFormat(val == null ? 0 : val, 2) +'</span>';
	}else{		
		return '<span style="color:red;">'+ thousandthFormat(val == null ? 0 : val, 2) +'</span>';
	}
}

function thousandthFormatterNotNull (val,row) {
	if(val == null){
		return '';
	}
	if(row.inaccountdate == '<span style="color:red;">合计</span>' || row.contractNo == '<span style="color:red;">合计</span>' 
		|| row.keepaccountsDate == '<span style="color:red;">合计</span>' 
			|| row.keepAccountsTime == '<span style="color:red;">合计</span>' 
				|| row.inAccountDate == '<span style="color:red;">合计</span>' 
					|| row.keepAccountsDate == '<span style="color:red;">合计</span>'
						|| row.keepaccountsdate == '<span style="color:red;">合计</span>'
							|| row.transDate == '<span style="color:red;">合计</span>'){
		return '<span style="color:red;">'+ thousandthFormat(val == null ? 0 : val, 2) +'</span>';
	}else{		
		return '<span style="color:red;">'+ thousandthFormat(val == null ? 0 : val, 2) +'</span>';
	}
}

function thousandthFormatterShow (val) {
		return thousandthFormat(val == null ? 0 : val, 2);
}

//千分位格式化，支持小数和负数
function thousandthFormat(number, decimal) {
	decimal = decimal == null ? 2 : decimal;
    var n = parseFloat(number).toFixed(decimal);
    var re = /(\d{1,3})(?=(\d{3})+(?:\.))/g;
    return n.replace(re, "$1,");
}

function formatStatus(val,row){
	if (val == '1'){
		return '<span style="color:green;">已记账</span>';
	} else if(val == '2') {
		return '<span style="color:red;">已撤销</span>';
	} else if(val == '0'){
		return '<span style="color:blue;">未记账</span>';
	}
}

function approveStatusFormatter(val, row) {
	if (val == '' || val == null)
		return;
	else if (val == 0 || val == "0")
		return "未审核";
	else
		return "已审核";
}

function payStatusFormatter(val, row) {
	if (val == '' || val == null)
		return;
	else if (val == 0 || val == "0")
		return "未支付";
	else
		return "已支付";
}

function payTypeFormatter(val, row) {
	if(val!=null&&val!=''&&val!='0'&&typeof(val)!='undefined'){
		if(val=='0055'||val=='0055H'){
			return '提前还款'
		}else{
			return '正常还款';
		}
	}else{
		return '';
	}
}

function ZDPayTypeFormatter(val, row) {
	if (val != null && val != '' && val != '0' && typeof (val) != 'undefined') {
		if (val == '02') {
			return '提前还款'
		} else if (val == '03') {
			return '逾期还款';
		} else if (val == '01') {
			return '正常还款';
		}
	} else {
		return '';
	}
}

function cancelTypeFormatter(val, row) {
	if (val == "Y"){		
		return "是";
	}else if(row.inaccountdate != '<span style="color:red;">合计</span>'&&row.inAccountdate != '<span style="color:red;">合计</span>'&&row.keepaccountsDate != '<span style="color:red;">合计</span>'&&row.keepAccountsDate != '<span style="color:red;">合计</span>'&&typeof(val)!="undefined"&&(val=="N"||val==null||val=="")){
		return "否";
	}
}

function sffkFormatter(val, row) {
	if (val == 'Y'){		
		return "是";
	}else if(val == 'N'){
		return "否";
	}else{
		return '';
	}
}

function waiveTypeFormatter(val, row) {
	if (val == '3530'){		
		return "费用减免";
	}else if(val == '0090'){
		return "利息减免";
	}else{
		return '';
	}
}

function isp2pFormatter(val, row) {
	if (row.isp2p == '1'){		
		return "是";
	}else if(row.contractNo!=null&&row.contractNo!='合计'&&(row.isp2p=='0'||row.isp2p==''||row.isp2p==null)){
		return "否";
	}else{
		return '';
	}
}

function formatPutoutStatus(val, row) {
	if (val != '' && val !=null && row.serialNo!=null){		
		return '<span style="color:green;">已放款</span>';
	}else if(val==null&&row.serialNo!=null){
		return '<span style="color:red;">未放款</span>';
	}else{
		return '';
	}
}


function checkMoney(money){
	if(money == null || money == 0 || money=='' || typeof(money) == 'undefined'){
		return '0.00';
	}
	return money;
}

function dealTypeFormatter(val, row) {
	if (val == 'zr'){		
		return "转让";
	}else if(val == 'dc'){		
		return "代偿";
	}else if(val == 'sh'){		
		return "赎回";
	}else if(val == 'lp'){		
		return "理赔";
	}else if(val == 'hk'){		
		return "还款";
	}else if(val == 'fk'){		
		return "放款";
	}else if(val == 'hb'){		
		return "划拨";
	}else{
		return val;
	}
}

function companyNameFormatter(val, row) {
	if (val == 'BQJR'){		
		return "佰仟金融";
	}else if(val == 'GZBC'){		
		return "贵州佰诚";
	}else if(val == 'RMBX'){		
		return "中国人民财产保险";
	}else if(val == 'BR'){		
		return "佰融";
	}else if(val == 'HBYH'){		
		return "哈尔滨银行";
	}else if(val == 'JSZB'){		
		return "嘉实资本";
	}else if(val == 'HKYH'){		
		return "海口农商行";
	}else if(val == 'ZXXT'){		
		return "中信信托";
	}else if(val == 'ZTXT'){		
		return "中泰信托";
	}else if(val == 'ZHXT'){		
		return "中航信托";
	}else if(val == 'JYPH'){
		return "嘉银普惠";
	}else if(val == 'RXCH'){		
		return "宝安融兴村行";
	}else if(val == 'SZKK'){		
		return "深圳快快";
	}else if(val == 'XZHR'){		
		return "西藏惠融";
	}else if(val == 'BDGY'){		
		return "百度国元";
	}else if(val == 'as'){		
		return "安硕";
	}else if(val == 'cd'){		
		return "车贷";
	}else if(val == 'CFC'){		
		return "哈消金-资方";
	}else if(val == 'AS_HXJ'){		
		return "佰仟哈消金";
	}else if(val == 'DBZQ'){		
		return "德邦证券";
	}else if(row!=null){
		if(row.inaccountdate != '<span style="color:red;">合计</span>'){
			return val;
		}
	}else{
		return val;
	}
}


var scanTime = 1000;
var interval = 500;
//导出前添加进度条
function exportBefore(){
    $.messager.progress({
        title:'导出中,请等待...',
        msg:'导出进度：',
        interval: interval
    });
}
//导出后关闭进度条
function exportLater(){
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