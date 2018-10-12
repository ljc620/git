<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="myel" uri="/elFunction"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>配送审核</title>
<%@ include file="/common.jsp"%>

<script type="text/javascript" src="${path}/pages/delivery/deliveryExam.js"></script>
</head>
<body>

	<div id="mytable-buttons">
	<form id="fm" method="post" class="bf-from">
		<table class="form-table" style="width: 90%;">
			<tr>
				<td colspan="2">申请编号：${deliveryApplyBean.applyId}
				<input type="hidden" id="applyId" name="applyId" value="${deliveryApplyBean.applyId}">
				</td>
			</tr>
			<tr>
				<td>申请人：${deliveryApplyBean.applyUserName}
				</td>
				<td>申请时间：<fmt:formatDate value="${deliveryApplyBean.applyTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
			</tr>
			<tr>
				<td>网点类型：${deliveryApplyBean.outletType}
				</td>
				<td>网点名称：${deliveryApplyBean.outletName}
				</td>
			</tr>
		</table>
		<input type="hidden" name="applyDetailStr" id="applyDetailStr">
		<input type="hidden" name="examStat" id="examStat">
		</form>
	</div>
	<div class="it-datagrid" style="height:300px">
		<table id="tickettable"></table>
	</div>
	<div style="font-size:12px;color:red;padding:5px">点击批准数量可编辑</div>
	<div id="dlg-buttons" style="text-align: center; ">
		<a href="javascript:void(0)" id="save-button" class="blue-btn" onclick="javascript:myExam('1')" style="width: 90px; margin-right: 10px;margin-left:90px;">同意</a>
		<a href="javascript:void(0)" class="gray-btn" onclick="javascript:myExam('5')" style="width: 90px; margin-left: 10px;">拒绝</a>
	</div>
</body>
</html>