<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="myel" uri="/elFunction"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>额度明细</title>
<%@ include file="/common.jsp"%>
<script type="text/javascript" src="${path}/pages/sale/orgmng/limitMng.js"></script>
</head>
<body style="overflow-y:hidden">
	<div id="mytable-buttons">
		<div class="ss-bar">
			<ul class="ss-ul">
			    <li style="width:80px;">组织机构代码:</li>
			    <li style="width:150px;">
					${slOrg.orgId}
					<input type="hidden" id="orgId" name="orgId" value="${slOrg.orgId}">
				</li>
				
				<li style="width:60px;">机构名称:</li>
				<li style="width:120px;">
					${slOrg.orgName}
				</li>
				<li style="width:30px;">票种:</li>
				<li style="width:120px;">
					${sysTicketType.ticketTypeName}
					<input type="hidden" id="ticketTypeId" name="ticketTypeId" value="${sysTicketType.ticketTypeId}">
				</li>
			</ul>
		</div>
	</div>
	<div class="it-datagrid" style="height:100%">
		<table id="mytable"></table>
	</div>
</body>
</html>