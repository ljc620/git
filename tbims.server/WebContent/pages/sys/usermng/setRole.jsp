<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="myel" uri="/elFunction"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"></c:set>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>修改用户</title>
<%@ include file="/common.jsp"%>
<script type="text/javascript" src="${path}/pages/sys/usermng/setRole.js"></script>
</head>
<body>
	<form id="fm" method="post" class="bf-from">
		用户编号：${sysUser.userId}
		<input type="hidden" name="userId" id="roleUserId" value="${sysUser.userId}">
	</form>
	<div class="it-datagrid" style="height: 390px;width:530px;">
		<table id="roleTable1"></table>
	</div>
	<div id="dlg-buttons" style="text-align: center; padding-top: 10px">
		<a href="javascript:void(0)" class="red-btn" id="save-button" onclick="javascript:setRoleToUser()" style="width: 60px; margin-right: 10px;">提交</a>
		<a href="javascript:void(0)" class="gray-btn" onclick="javascript:parent.$('#addRoleDlg1').dialog('close');" style="width: 60px; margin-left: 10px;">关闭</a>
	</div>
</body>