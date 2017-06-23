
$(document).ready(function() {
	$("#reset-button").click(function() {
		$("#fm1")[0].reset();
	});
	// 验证码更换图片事件
	/*$("#captchaimg").click(function(event) {
		this.src = './captcha.jpg?' + (+new Date);
	});

	$("#captcha").css({
		color : '#ccc'
	}).focusin(function(event) {
		$(this).css({
			color : '#000'
		});
		if ($(this).val() == "请输入验证码") {
			$(this).val("");
		}
	}).focusout(function(event) {
		if (!$(this).val()) {
			$(this).val("请输入验证码").css({
				color : '#ccc'
			});
		}
	});*/
});

//点击图片刷新验证码
/*function imgClick(obj){
	obj.src = './captcha.jpg?' + (+new Date().getTime());
}*/


//var isCommitted = false;//表单是否已经提交标识，默认为false
//提交表单
function submitForm() {
	/*var isSubmit = false;
	var cpa = $("#captcha"), v = cpa.val();
	if (cpa.length && (!v || "请输入验证码" == v)) {
		cpa.val("请输入验证码").css({
			color : '#ccc'
		});
		alert("请输入验证码");
		isSubmit = false;
	} else {
		isSubmit = true;
	}
	if (isSubmit && !isCommitted) {
		$("#fm1").submit();
		isCommitted = true;
	}*/
//	$("#fm1").submit();
	var submit = true;
	$("input[nullmsg]").each(function() {
		if ($("#" + this.name).val() == "") {
			showError($("#" + this.name).attr("nullmsg"), 500);
			setTimeout('hideTop()', 1000);
			submit = false;
			return false;
		}
	});
	if (submit) {
		hideTop();
		loading('核实中..', 1);
		setTimeout("unloading()", 1000);
		setTimeout("Login()", 1000);
	}
}

//登录处理函数
function Login() {
//	setCookie();
	var actionurl='main/menu.do';//提交路径
	var checkurl='login.do';//验证路径
	var formData = new Object();
	var data=$(":input").each(function() {
		 formData[this.name] =$("#"+this.name ).val();
	});
	$.ajax({
		async : false,
		cache : false,
		type : 'POST',
		url : checkurl,// 请求的action路径
		data : formData,
		error : function() {// 请求失败处理函数
			alert("系统错误，请联系系统管理员！");
		},
		success : function(e) {
			var doms = $.parseHTML( e );
//			 需要更改一下判断方式
			if (doms.length === 4 || doms.length === 1) {
				loginsuccess();
				setTimeout("window.location.href='"+actionurl+"'", 1000);
			} else {
				showError("用户名或者密码错误");
			}
		}
	});
}

//回车按钮提交
function keyDown(e) {

	var keycode = 0;
	// IE浏览器
	if (CheckBrowserIsIE()) {
		keycode = event.keyCode;
	} else {
		// 火狐浏览器
		keycode = e.which;
	}
	if (keycode == 13) // 回车键是13
	{
		// document.getElementById("login").click();
		/* document.getElementById("fm1").submit(); */
		submitForm();
	}
}

//验证通过加载动画
function loginsuccess()
{
	$("#login").animate({
		opacity : 1,
		top : '49%'
	}, 200, function() {
		$('.userbox').show().animate({
			opacity : 1
		}, 500);
		$("#login").animate({
			opacity : 0,
			top : '60%'
		}, 500, function() {
			$(this).fadeOut(200, function() {
				$(".text_success").slideDown();
				$("#successLogin").animate({
					opacity : 1,
					height : "200px"
				}, 1000);
			});
		});
	});
}

//加载信息
function loading(name, overlay) {
	$('body').append('<div id="overlay"></div><div id="preloader">' + name + '..</div>');
	if (overlay == 1) {
		$('#overlay').css('opacity', 0.1).fadeIn(function() {
			$('#preloader').fadeIn();
		});
		return false;
	$('#preloader').fadeIn();
	}
}

function hideTop() {
	$('#alertMessage').animate({
		opacity : 0,
		right : '-20'
	}, 500, function() {
		$(this).hide();
	});
}

function unloading() {
	$('#preloader').fadeOut('fast', function() {
		$('#overlay').fadeOut();
	});
}

function showError(str) {
	$('#alertMessage').addClass('error').html(str).stop(true, true).show().animate({
		opacity : 1,
		right : '0'
	}, 500);

}

// 判断访问者的浏览器是否是IE
function CheckBrowserIsIE() {
	var result = false;
	var browser = navigator.appName;
	if (browser == "Microsoft Internet Explorer") {
		result = true;
	}
	return result;
}
