<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="myel" uri="/elFunction"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>审核详情</title>
<%@ include file="/common.jsp"%>
<script type="text/javascript">
</script>
</head>
<body>
	<div id="printContent">
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
	<table class="report-table">
	<tr>
					    <td align="center" style="white-space: nowrap">序号</td>
						<td align="center"  style="white-space: nowrap;">票种</td>
						<td align="center" style="white-space: nowrap;" >申请数量</td>
			            <td align="center" style="white-space: nowrap">批准数量</td>
					</tr>
	<c:forEach var="appBean" items="${examList}" varStatus="status">
	                    <tr>
						<td align="center">${ status.index + 1}</td>
						<td align="center">${appBean.ticketTypeName}</td>
						<td align="center">${appBean.applyNum}</td>
						<td align="center">${appBean.examNum}</td>
						</tr> 
						</c:forEach>
 </table>
	</div>
</body>
</html>