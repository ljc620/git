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


</head>
<body>
<object id="LODOP_OB" classid="clsid:2105C259-1E0C-4534-8141-A753534CB4CA" width="0" height="0">
		<embed id="LODOP_EM" type="application/x-print-lodop" width="0" height="0"></embed>
	</object>
        <div class="ss-bar" style="padding-top:2px">
		<a href="javascript:void(0)"  class="blue-btn" onclick="javascript:printInfo1()" style="width: 40px;padding-left:5px">打印</a>
		</div>
	<div id="printContent">
		<table class="report-table" style="width: 90%;">
		    <tr><td colspan="4" style="white-space: nowrap">签约社名称：${slTeamOrder.orgName}</td></tr>
			<tr>
				<td colspan="2" style="white-space: nowrap">预订单号：${slTeamOrder.applyId}
				</td>
				<td style="white-space: nowrap">预订日期：<fmt:formatDate  value="${slTeamOrder.applyTime}" type="both" pattern="yyyy-MM-dd" />
				</td>
				<td style="white-space: nowrap">入园日期：<fmt:formatDate  value="${slTeamOrder.inDt}" type="both" pattern="yyyy-MM-dd" />
				</td>
			</tr>
			<tr>
				<td style="white-space: nowrap">换票人姓名：${changeUserBean.changeUserName}
				</td>
				<td style="white-space: nowrap">换票人手机号：${changeUserBean.tel}
				</td>
				<td style="white-space: nowrap">证件类型：${changeUserBean.cardTypeName}
				</td>
				<td style="white-space: nowrap">证件号码：${changeUserBean.cardId}
				</td>
			</tr>
		</table>
	<table class="report-table" style="margin-top:3px">
	<tr>
					    <td align="center" style="white-space: nowrap">序号</td>
						<td align="center"  style="white-space: nowrap;">票种</td>
						<td align="center" style="white-space: nowrap;" >申请数量</td>
			            <td align="center" style="white-space: nowrap">审核数量</td>
					</tr>
	<c:forEach var="slTeamOrderDetail" items="${slTeamOrder.slTeamOrderDetails}" varStatus="status">
	                    <tr>
						<td align="center" style="white-space: nowrap">${ status.index + 1}</td>
						<td align="center" style="white-space: nowrap">${slTeamOrderDetail.ticketTypeName}</td>
						<td align="center" style="white-space: nowrap">${slTeamOrderDetail.applyNum}</td>
						<td align="center" style="white-space: nowrap">${slTeamOrderDetail.examNum}</td>
						</tr> 
						</c:forEach>
 </table>
	</div>
</body>
</html>