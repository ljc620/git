<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="myel" uri="/elFunction"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>票种管理</title>
<%@ include file="/common.jsp"%>
<link rel="stylesheet" href="${path}/resources/ztree/css/zTreeStyle/zTreeStyle.css" type="text/css"></link>
<script type="text/javascript" src="${path}/resources/ztree/js/jquery.ztree.core.min.js"></script>
<script type="text/javascript" src="${path}/resources/ztree/js/jquery.ztree.excheck.min.js"></script>
<script type="text/javascript" src="${path}/resources/ztree/js/jquery.ztree.exhide.min.js"></script>
<script type="text/javascript" src="ticketTypeMng.js"></script>

</head>
<body>
	<div id="mytable-buttons">
		<div class="ss-bar2">
			<ul class="ss-ul">
			<c:if test="${myel:hasPriv('i2_sys_ticket_type_add',userSession.functionSet)}">
				<li>
					<a href="javascript:void(0)" class="red-btn icon-add" onclick="javascript:addTicketType();">
						<span class="btn-text">新增票种</span>
					</a>
				</li>
					<li>
					<a href="javascript:void(0)" class="red-btn icon-add" onclick="javascript:updateTicketStat('Y');">
						<span class="btn-text">启用</span>
					</a>
				</li>
				<li>
					<a href="javascript:void(0)" class="red-btn icon-add" onclick="javascript:updateTicketStat('N');">
						<span class="btn-text">禁用</span>
					</a>
				</li>
			</c:if>
			</ul>
		</div>
		
	</div>
	<div class="it-datagrid" style="height: 100%">
		<table id="mytable"></table>
	</div>
	<div id="ruleDialog" class="easyui-dialog" style="width: 800px; height: 600px; padding: 5px 10px" modal="true" closed="true" >
	</div>
	<div id="venueDialog" class="easyui-dialog" style="width: auto; height: auto; padding: 5px 10px" modal="false" closed="true" >
	</div>
	<div id="ticTypePriceDialog" class="easyui-dialog" style="width: 800px; height: 600px; padding: 5px 10px" modal="false" closed="true" >
	</div>
	<div id="updateTicketTypeDialog" class="easyui-dialog" style="width: 900px; height:300px; padding: 5px 10px" modal="true" closed="true" >
	</div>
</body>
</html>