
var contextPath ;
var localhostPaht;
var imagesUrl;
var isSuperAdmin;
$(function(){
	//js获取项目根路径，如： http://localhost:8083/als_client
	    //获取当前网址，如： http://localhost:8083/als_client/pages/include.jsp
	    var curWwwPath=window.document.location.href;
	    //获取主机地址之后的目录，如： als_client/pages/include.jsp
	    var pathName=window.document.location.pathname;
	    var pos=curWwwPath.indexOf(pathName);
	    //获取主机地址，如： http://localhost:8083
	    localhostPaht = curWwwPath.substring(0,pos);
	    //获取带"/"的项目名，如：/als_client
	    contextPath = pathName.substring(0,pathName.substr(1).indexOf('/')+1);
	    
//	    imagesUrl();
	    
	    //获取用户是否是超级管理员
	    isSuperAdmin = $("#isSuperAdmin").val();
});

/**
 * 获取图片服务地址
 */
function imagesUrl(){
	$.ajax({
		url:contextPath+'/main/imagesUrl.do',
		type: "GET", 
        success: function (data) { 
        	var dataJson = eval("("+data+")");
        	if(dataJson){
        		imagesUrl = dataJson.imagesUrl;
			}
        }
	});
}

//扩展表单校验
$.extend($.fn.validatebox.defaults.rules, {
      ip : {// 验证IP地址
          validator : function(value) {
              return /\d+\.\d+\.\d+\.\d+/.test(value);
          },
          message : 'IP地址格式不正确'
      },
    mobile: {
      validator: function (value, param) {
        return /^(?:13\d|15\d|18\d)-?\d{5}(\d{3}|\*{3})$/.test(value);
      },
      message: '手机号码不正确'
    },
    promptDate: {
        validator: function (value, param) {
          return /^[0-9]{4}\/[0-9]{2}\/[0-9]{2}$/.test(value);
        },
        message: '格式:yyyy/mm/dd'
      },
    number: {
      validator: function (value, param) {
        return /^[0-9]+.?[0-9]*$/.test(value);
      },
      message: '请输入数字'
    },
    english : {// 验证英语
        validator : function(value) {
            return /^[A-Za-z\s]+$/i.test(value);
        },
        message : '请输入英文'
    },
    minLength:{
      validator:function(value,param){
        return value.length >=param[0];
      },
      message:'至少输入{0}个字'
    },
    maxLength:{
      validator:function(value,param){
        return value.length<=param[0];
      },
      message:'最多{0}个字'
    },
    idCode:{
      validator:function(value,param){
        return /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/.test(value);
      },
      message: '请输入正确的身份证号'
    },
    email:{
        validator:function(value,param){
          return /^[A-Za-zd]+([-_.][A-Za-zd]+)*@([A-Za-zd]+[-.])+[A-Za-zd]{2,5}$/.test(value);
        },
        message: '请输入正确的邮箱'
      },
    equalTo: {
      validator: function (value, param) {
        return value == $("#" + param[0]).val();
      },
      message: '两次输入的密码不一致'
    },
   //校验是否含有空格和非法字符
    unnormal:{
    	validator:function(value){
    		var pattern = /[%&',;=?？！!""‘“、；\^，￥”《< >》*{})(）#\\（$\x22]+/;
    		return !pattern.test(value);
    	},
    	message: '不能包含特殊字符'
    },
//用户账号验证(只能包括 _ 数字 字母)   
  account: {//param的值为[]中值  
      validator: function (value, param) {  
          if (value.length < param[0] || value.length > param[1]) {  
              $.fn.validatebox.defaults.rules.account.message = '密码长度必须在' + param[0] + '至' + param[1] + '范围';  
              return false;  
          } else { 
//        	  var r=/^(?=.*[0-9].*)(?=.*[A-Z].*)(?=.*[a-z].*)$/;
        	  var r=/^(\d*|[a-z]*|[A-Z]*|[0-9a-z]*|[0-9A-Z]*|[a-zA-Z]*)$/;
              if (r.test(value)) {  
                  $.fn.validatebox.defaults.rules.account.message = '密码必须包含大小写字母和数字的组合.';  
                  return false;  
              } else {  
                  return true;  
              }  
          }  
      }, message: ''  
  },
//用户账号验证(只能包括 _ 数字 字母)   
  code: {//param的值为[]中值  
      validator: function (value, param) {  
     
        var r=/^(\w|_)[a-zA-Z0-9_-]*$/;
        return r.test(value);  
      }, message: '帐号只能输入字母、数字和下划线'  
  },
  url:{
	  validator: function (value) {  
//		  var strRegex = "^((https|http|ftp|rtsp|mms)?:\/\/)" 
//			  + "?(([0-9a-z_!~*'().&=+$%-]+: )?[0-9a-z_!~*'().&=+$%-]+@)?" //ftp的user@ 
//			        + "(([0-9]{1,3}/.){3}[0-9]{1,3}" // IP形式的URL- 199.194.52.184 
//			        + "|" // 允许IP和DOMAIN（域名）
//			        + "([0-9a-z_!~*'()-]+/.)*" // 域名- www. 
//			        + "([0-9a-z][0-9a-z-]{0,61})?[0-9a-z]/." // 二级域名 
//			        + "[a-z]{2,6})" // first level domain- .com or .museum 
//			        + "(:[0-9]{1,4})?" // 端口- :80 
//			        + "((/?)|" // a slash isn't required if there is no file name 
//			        + "(/[0-9a-z_!~*'().;?:@&=+$,%#-]+)+/?)$"; 
		  var pattern = /https?:\/\/(\w*:\w*@)?[-\w.]+(:\d+)?(\/([\w\/_.]*(\?\S+)?)?)?/m;
//		    var re=new RegExp(strRegex); 
//		  var r =/(https|http|ftp|rtsp|igmp|file|rtspt|rtspu):\/\/((((25[0-5]|2[0-4]\d|1?\d?\d)\.){3}(25[0-5]|2[0-4]\d|1?\d?\d))|([0-9a-z_!~*'()-]*\.?))([0-9a-z][0-9a-z-]{0,61})?[0-9a-z]\.([a-z]{2,6})(:[0-9]{1,4})?([a-zA-Z/?_=]*)\.\w{1,5}/ ;
//		  alert(re.test(value));
	        return pattern.test(value);  
	      }, message: 'url不合法'  
	  
  }
//  ,
////唯一性校验
//  unique: {//param的值为[]中值  
//      validator: function (value, param) { 
//    	  var unique=false;
//    	  var paramNmae ='';
//    	  //0标识校验系统编码，1校验系统名称，2校验英文名称
//    	  if(param == 0){
//    		  paramNmae = 'sysCode';
//    	  }else if(param == 1){
//    		  paramNmae = 'sysCname';
//    	  }else if(param == 2){
//    		  paramNmae = 'sysEname';
//    	  }
//    	  $.ajax({
//    			url:contextPath+"/sysServer/checkUnique.do?"+paramNmae+"="+value,
//    			type: "GET", 
////    			async: false,
//    	        success: function (data) { 
//    	        	var dataJson = eval("("+data+")");
//    	        	if(dataJson.unique){
//    	        		unique= true;
//	    	      	}
//    	        }
//    		});
//    	   if(unique){
//      		  return true;
//	      	}else{
//	      		 //0标识校验系统编码，1校验系统名称，2校验英文名称
//	        	  if(param == 0){
//	        		  $.fn.validatebox.defaults.rules.account.message ='系统编码不能重复';
//	        	  }else if(param == 1){
//	        		  $.fn.validatebox.defaults.rules.account.message ='系统名称不能重复';
//	        		  return false;  
//	        	  }else if(param == 2){
//	        		  $.fn.validatebox.defaults.rules.account.message ='系统英文名称不能重复';
//	        	  }
//	      		return false;
//	      	}
//      }, message: ''  
//  }
 });

//扩展datagrid的方法
$.extend($.fn.datagrid.methods, {
    /**
     * 扩展加载方法，加载之前清空所有选中和勾选
     */
    loadExt : function(jq, param) {
        var oGrid = $(jq);
        //清空选中
        oGrid.datagrid('clearSelections');
        //清空所有勾选
        oGrid.datagrid('clearChecked');
        //加载数据
        oGrid.datagrid('load', param);
    },
    clearData:function(jq){
    	//清空表单数据
    	var oGrid = $(jq);
    	 //清空选中
        oGrid.datagrid('clearSelections');
        //清空所有勾选
        oGrid.datagrid('clearChecked');
    	oGrid.datagrid('loadData',{total:0,rows:[]});
    	return oGrid;
    }
   
});
//扩展form的方法
$.extend($.fn.form.methods, {
    /**
     * 扩展加载方法，加载之前清空表单中的所有数据
     */
    loadExt : function(jq, param) {
        var formId = $(jq);
        //清空easyui的表单
        formId.form('clear');
        //用jQuery清楚表单所有数据
        
        
        //加载数据
        formId.form('load',param);
    }
});

// session expire
var easyuiErrorFunction = function(XMLHttpRequest) {
	$.messager.progress('close');
	$.messager.alert("错误", "请刷新后重试");
};
$.fn.datagrid.defaults.onLoadError = easyuiErrorFunction;
$.fn.treegrid.defaults.onLoadError = easyuiErrorFunction;
$.fn.tree.defaults.onLoadError = easyuiErrorFunction;
$.fn.combogrid.defaults.onLoadError = easyuiErrorFunction;
$.fn.combobox.defaults.onLoadError = easyuiErrorFunction;
$.fn.form.defaults.onLoadError = easyuiErrorFunction;

$.ajaxSetup({
	type : 'POST',
	error : function(XMLHttpRequest, textStatus, errorThrown) {
		$.messager.progress('close');
		$.messager.alert("错误", "请刷新后重试");
	}
});