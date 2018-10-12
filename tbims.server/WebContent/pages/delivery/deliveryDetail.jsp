<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="myel" uri="/elFunction"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>配送审核</title>
<%@ include file="/common.jsp"%>
<script type="text/javascript"
	src="${path}/resources/lodop/lodoopcomm.js"></script>
<script type="text/javascript"
	src="${path}/resources/lodop/LodopFuncs.js"></script>
<script type="text/javascript">
	function printInfo() {
		myPreview("35", "35", $("#printContent").html());
	}
</script>
<%@ include file="/pages/print.jsp"%>
</head>
<body>
	<object id="LODOP_OB"
		classid="clsid:2105C259-1E0C-4534-8141-A753534CB4CA" width="0"
		height="0">
		<embed id="LODOP_EM" type="application/x-print-lodop" width="0"
			height="0"></embed>
	</object>
	<table class="form-table">
		<tr>
			<td>
				<a href="javascript:void(0)" class="blue-btn"
					onclick="javascript:printInfo()" style="width: 30px;">打印</a>
			</td>
		</tr>
	</table>
	<div id="printContent">
	<div style="width: 649px; height: auto;margin:0 auto;">
			<table class="report-table" style="width: 100%;">
				<tr>
					<td colspan="6" align="center"
						style="font-size: 14px; font-weight: bold;">配送确认单</td>
				</tr>
				<tr>
					<td align="center">申请编号</td>
					<td>${deliveryApplyBean.applyId}</td>
					<td align="center">申请人</td>
					<td>${deliveryApplyBean.applyUserName}</td>
					<td align="center">申请时间</td>
					<td>
						<fmt:formatDate value="${deliveryApplyBean.applyTime}"
							pattern="yyyy-MM-dd HH:mm:ss" />
					</td>
				</tr>
				<tr>
					<td style="width: 150px;" align="center">网点类型</td>
					<td style="width: 150px;">${deliveryApplyBean.outletType}</td>
					<td align="center">网点名称</td>
					<td colspan="3">${deliveryApplyBean.outletName}</td>
				</tr>
			<tr>
				<td align="center">序号</td>
				<td align="center">票种</td>
				<td align="center">箱号</td>
				<td align="center">数量</td>
				<td align="center">起号<br>
				<td align="center">止号</td>
			</tr>
			<c:forEach var="chestBean" items="${detailList}" varStatus="status">
				<tr>
					<td align="center">${ status.index + 1}</td>
					<td align="center">${chestBean.ticketTypeName}</td>
					<td align="center">${chestBean.chestId}</td>
					<td align="center">${chestBean.ticketNum}</td>
					<td align="right">${chestBean.beginNo}</td>
					<td align="right">${chestBean.endNo}</td>
				</tr>
			</c:forEach>
		</table>
	</div>
	</div>
</body>
</html>