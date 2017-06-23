<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<!-- <link rel="stylesheet" href="css/reveal.css"> -->
<LINK rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/login.css">
	  		
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-easyui-1.4.2/jquery.min.js"></script>
<%-- <script type="text/javascript" src="<c:url value="/js/login/cas.js" />"></script> --%>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/login/login.js"></script>
<!-- <script type="text/javascript" src="js/login/jquery.reveal.js"></script> -->
<title>佰仟金融-财务业务账系统</title>
</head>
<body scroll="no"  class="logon_body" onkeydown="keyDown(event);">
<div id="alertMessage"></div>
<div id="successLogin"></div>
  <div class="text_success">
   <img src="images/logonimg/loader_green.gif" alt="Please wait" />
   <span>登录成功!请稍后....</span>
  </div>
	<form action="j_spring_security_check" method="post" id="fm1" >
		
			<div id="main">
				<div id="login">
					<div>
						用户名：
						<input type="text" id="userCode" name="userCode" title="用户名" value="" nullmsg="请输入用户名!"
						 style="width: 160px; height: 20px;" />
					</div>
					<div id="pswdiv">
						 &nbsp密码：
						<input type="password" id="pwd" name="pwd" title="密码" value="" nullmsg="请输入密码!"
						style="width: 160px; height: 20px;" />
					</div>
					<!-- <div id="codediv" >
						<span style="float: left; line-height: 22px; height: 22px; ">验证码：</span>
						<input style="width:80px;height: 20px; float: left; margin:0 10px 0 5px " class="required" tabindex="103" id="captcha"
								name="captcha"  type="text" autocomplete="false" value="" maxlength="4"/> 
						<img src="./captcha.jpg" id="captchaimg" style="float: left; cursor: pointer; width: 70px;height: 24px;" onclick="imgClick(this);" />
					</div> -->
					
					<%-- <c:if test="${not empty overideLogin}">
						<input name="overideLogin" value="true" tabindex="3"
								type="checkbox" /> 
						<label for="overideLogin"> 
							<spring:message code="screen.welcome.label.overideLogin" />
						</label>
					</c:if> --%>
					<%-- <div style="height: 30px;width:230px; margin-top: 10px;line-height: 10px;clear: both;">
					&nbsp;
					<form:errors path="*"  id="msg" cssClass="errors" element="div"  style="line-height: 20px"/>
					
					</div> --%>
					<div id="btndiv" >
						<div id="sub" onclick="submitForm()">登录</div>
						<div id="reset-button">重置</div>
						<!-- <a class="update-psw" href="javascript:openFrm()" >修改密码</a> -->
						<!-- <a href="#" class="update-psw" data-reveal-id="myModal">
							修改密码
						</a> -->	
					</div>
				</div>
			</div>
			<div id="copyright">系统支持:&nbsp;邮箱：ithotline@bqjr.cn|电话：0755-84379899</div>
			<%-- <input type="hidden" name="lt" value="${loginTicket}" /> 
			<input type="hidden" name="execution" value="${flowExecutionKey}" /> 
			<input type="hidden" name="_eventId" value="submit" />
			<input type="hidden"   id="contextPath" value="${pageContext.request.contextPath}" />
		 --%>
	</form>
		<%-- <div id="myModal" class="reveal-modal" >
			<%@ include file="casChangePwdView.jsp"%>
		</div> --%>
</body>
</html>
