<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="myel" uri="/elFunction"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>配送申请详情</title>
<%@ include file="/common.jsp"%>
<script type="text/javascript" src="${path}/pages/delivery/deliveryList.js"></script>
<script type="text/javascript">
	var applyId = '${param.applyId}';
	var examStat = '${param.examStat}';
	$(function() {
		createApplyExamTable(applyId);
		createDeliveryTable(applyId);
	});
</script>
</head>
<body>
	<div>
		<input type="hidden" id="applyId" name="applyId" value="${param.applyId}" />
		<input type="hidden" id="hexamStat" name="hexamStat" value="${param.examStat}" />
		<p class="item-title" style="height: 20px; line-height: 20px; margin-top: 2px">配送申请审核情况</p>
		<div class="it-datagrid" style="height: 160px;">
			<table id="applyExamtable"></table>
		</div>
		<p class="item-title" style="height: 20px; line-height: 20px; margin-top: 2px">配送情况</p>
		<div class="it-datagrid" style="height: 160px;">
			<table id="deliverytable"></table>
		</div>
		<div id="dlg-buttons" class="window-btn-bar" style="text-align: center; padding-top: 10px;">
			<a href="javascript:void(0)" class="red-btn" id="save-button" onclick="javascript:confirmReceive()" style="width: 60px; margin-right: 30px;">确认接收</a>
			<a href="javascript:void(0)" class="gray-btn" onclick="javascript:parent.$('#detailDialog').dialog('close');" style="width: 60px; margin-right: 50px;">关闭</a>
		</div>
	</div>
</body>
</html>