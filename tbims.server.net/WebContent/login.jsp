<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="utf-8"%>
<%@ taglib prefix="myel" uri="/elFunction"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%
	response.setHeader("Cache-Control", "no-cache");
	response.setHeader("Pragma", "no-cache");
	response.setDateHeader("Expires", -1);
	String path = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ request.getContextPath();
	pageContext.setAttribute("path", path);

	int number_1 = (int) (Math.random() * 65535) + 1;
	int number_2 = (int) (Math.random() * 65535) + 1;
	String s_rnd = (new Integer(number_1)).toString() + (new Integer(number_2)).toString();
	session.setAttribute("random", s_rnd);
%>
<title>票务综合管理系统</title>
<link rel="shortcut icon" href="${path}/css/icons/favicon.ico" >
<link rel="stylesheet" href="${path}/css/easyui.css" />
<link rel="stylesheet" href="${path}/css/mainstyle.css" type="text/css"></link>
<script src="${path}/resources/jquery-easyui/jquery.min.js"></script>
<script src="${path}/resources/jquery-easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${path}/resources/jquery-easyui/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="${path}/js/main.js"></script>

<!--定要包含有我们的UK单元包-->
<script type="text/javascript" src="${path}/js/ukey/Syunew6.js"></script>
<script type="text/javascript" src="${path}/js/ukey/ukey.js"></script>
<body onload="load();" class="easyui-layout">
	<div data-options="region:'north',border:false" class="login-north" style="height: 68px;"></div>
	<div data-options="region:'south',border:false" style="height: 60px; background: #fff; text-align: center; line-height: 60px;">
		中国印钞造币&nbsp;▪&nbsp;石家庄印钞有限公司&nbsp;&nbsp;&nbsp;版权所有&nbsp;©&nbsp;2017
		<!-- 	<OBJECT ID="Spindial1" WIDTH=3 HEIGHT=3 VISIBILITY="hidden" CLASSID="CLSID:469F3884-962C-4F42-8BCC-C6564386A4FA" CODEBASE="ypcab.CAB#Version=1.0.0.1"> </OBJECT> -->
		<!-- 		&nbsp;&nbsp;&nbsp;<a href="SetUp.exe">加密锁驱动下载</a> -->
	</div>
	<div data-options="region:'center',border:false" class="login-center">
		<form method="post" action="${path}/sys/login.do" id="loginform">
			<div class="login-body">
				<div class="login-bg">
					<ul class="login-box">
						<li>
							<input class="easyui-textbox" style="width: 222px; height: 28px;" data-options="iconCls:'login-icon1',iconAlign:'left',iconWidth:30,prompt:'请输入用户名',required:true" name="userId" id="userId" />
						</li>
						<li>
							<input class="easyui-textbox" type="password" style="width: 222px; height: 28px;" data-options="iconCls:'login-icon2',iconAlign:'left',iconWidth:30,prompt:'请输入密码...',required:true" name="userPassword" />
						</li>
						<li style="height: 10px;">
							<input type="hidden" id="KeyID" name="KeyID" name="KeyID" />
							<input type="hidden" id="random" name="random" value="${random}" />
							<input type="hidden" id="encDataClient" name="encDataClient" />
							<input type="hidden" id="encOpeDataClient" name="encOpeDataClient" />
						</li>
						<li style="height: 40px; margin: 0px;">
							<a href="javascript:void(0)" onclick="javascript:login_onclick();return false;" id="login" class="login-btn enter">登录</a>
						</li>
						<li style="height: 30px; margin: 0px;">
							<span class="bf-msg">${errorDescribe}</span>
						</li>
					</ul>
				</div>
			</div>
		</form>
	</div>
	<script type="text/javascript" language="JavaScript">
		$(function() {
			$("#userId").textbox("setValue", getCookie('tbims_userId'));
		});
	</script>
</body>
</html>
