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
<script type="text/javascript">
</script>
<%@ include file="/pages/print.jsp"%>
</head>
<body>
	<div id="printContent">
		<table class="report-table" style="width: 90%;">
		    <tr><td colspan="4" style="white-space: nowrap">签约社名称：${slTeamOrder.orgName}</td></tr>
			<tr>
				<td style="white-space: nowrap">预订单号：${slTeamOrder.applyId}
				</td>
				<td style="white-space: nowrap">预订日期：<fmt:formatDate  value="${slTeamOrder.applyTime}" type="both" pattern="yyyy-MM-dd" />
				</td>
				<td style="white-space: nowrap">入园日期：<fmt:formatDate  value="${slTeamOrder.inDt}" type="both" pattern="yyyy-MM-dd" />
				</td>
				<td style="white-space: nowrap">扣减金额：${slTeamOrder.minusAdvanceAmt}
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
			            <td align="center" style="white-space: nowrap">出票数量</td>
			            <td align="center" style="white-space: nowrap">申请冻结额度</td>
			            <td align="center" style="white-space: nowrap">审核冻结额度</td>
			            <td align="center" style="white-space: nowrap">出票扣减额度</td>
			            <td align="center" style="white-space: nowrap">流单扣罚额度</td>
					</tr>
	<c:forEach var="slTeamOrderDetail" items="${slTeamOrder.slTeamOrderDetails}" varStatus="status">
	                    <tr>
						<td align="center" style="white-space: nowrap">${ status.index + 1}</td>
						<td align="center" style="white-space: nowrap">${slTeamOrderDetail.ticketTypeName}</td>
						<td align="center" style="white-space: nowrap">${slTeamOrderDetail.applyNum}</td>
						<td align="center" style="white-space: nowrap">${slTeamOrderDetail.examNum}</td>
						<td align="center" style="white-space: nowrap">${slTeamOrderDetail.changeNum}</td>
						<td align="center" style="white-space: nowrap">${slTeamOrderDetail.applyFrozenLimit}</td>
						<td align="center" style="white-space: nowrap">${slTeamOrderDetail.examFrozenLimit}</td>
						<td align="center" style="white-space: nowrap">${slTeamOrderDetail.minusLimit}</td>
						<td align="center" style="white-space: nowrap">${slTeamOrderDetail.flowLimit}</td>
						</tr> 
						</c:forEach>
 </table>
	</div>
</body>
</html>