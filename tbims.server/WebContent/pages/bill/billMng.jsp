<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="myel" uri="/elFunction"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"></c:set>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>对账管理</title>
<%@ include file="/common.jsp"%>
<script type="text/javascript" src="billMng.js"></script>
</head>
<body>
	<div id="mytable-buttons">
		<div class="ss-bar">
			<ul class="ss-ul">
				<li style="width: 50px; padding-left: 15px;">对账状态</li>
				<li>
					<input id="q_stat" width="60px"></input>
				</li>
				<li style="width: 50px; padding-left: 15px;">开始日期</li>
				<li>
					<input id="q_billDateStart" class="easyui-datebox"  data-options="formatter:formatterDate2,parser:parserDate2,editable:false" width="60px"></input>
				</li>
				<li style="width: 50px; padding-left: 15px;">结束日期</li>
				<li>
					<input id="q_billDateEnd" class="easyui-datebox"  data-options="formatter:formatterDate2,parser:parserDate2,editable:false" width="60px"></input>
				</li>
				<li>
					<a href="javascript:void(0)" onclick="javascript:queryRole();" class="gray2-btn">&nbsp;查询&nbsp;</a>
				</li>
			</ul>
		</div>
	</div>
	<div class="it-datagrid" style="height: 100%">
		<table id="mytable"></table>
	</div>
</body>
</html>