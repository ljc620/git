<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="myel" uri="/elFunction"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<c:set var="ctx" value="${pageContext.request.contextPath}"></c:set>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>单票号查验</title>
<%@ include file="/common1.jsp"%>
<script type="text/javascript">

//查询
function query(){
	if (!$('#queryForm').form('validate')) {
		return false;
	}
	var url="<%=path%>/rptsale/saleCheckRpt.do";
	 $('#queryForm').attr("action", url).submit();
}
function expExcel(){
	if (!$('#queryForm').form('validate')) {
		return false;
	}
	var url='<%=path%>/rptsale/expExlSaleCheck.do';
		$('#queryForm').attr("action", url).submit();
	}
	$(document).ready(function() {
		$("#ticketId").textbox('setValue', '${ticketId}');
	});
</script>
</head>
<body style="background-color: #fff;">
	<div class="ss-bar" style="margin: 16px 35px">
		<form id="queryForm">
			<ul class="ss-ul">
				<li>票号</li>
				<li>
					<input id="ticketId" name="ticketId" class="easyui-numberbox" min="1000000000"
							max="9999999999" precision="0" style="width: 120px;" required="true"></input>
				</li>
				<li>
					<a class="gray2-btn" onclick="query();">查询</a>
				</li>
				<li>
					<a class="indigo-btn" id="btnExcel" id="btnExcel" onclick="expExcel()">导出EXCEL</a>
				</li>
			</ul>
		</form>
	</div>
	<div class="it-datagrid">
		<table class="report-table" style="width: 90%; margin: 20px 41px">
			<thead class="datagrid1-header">
				<tr>
					<th colspan="6" style="font-size: 14px; background: #b4b4b4;">基本信息</th>
				</tr>
				<tr class="theader">
					<th>票号</th>
					<th colspan="2">票据唯一号</th>
					<th>芯片ID</th>
					<th>票种名称</th>
					<th>是否黑名单</th>
				</tr>
			</thead>
			<tbody class="datagrid1-body">
				<tr style="height: 36px">
					<td align="center">${ticketBaseInfoBean.ticketId}</td>
					<td align="center" colspan="2">${ticketBaseInfoBean.ticketUid}</td>
					<td align="center">${ticketBaseInfoBean.chipId}</td>
					<td align="center">${ticketBaseInfoBean.ticketTypeName}</td>
					<td align="center">${ticketBaseInfoBean.backlistFlag}</td>
				</tr>
			</tbody>
		</table>
		<table class="report-table" style="width: 90%; margin: 20px 41px">
			<thead class="datagrid1-header">
				<tr>
					<th colspan="6" style="font-size: 14px; background: #b4b4b4;">配送信息</th>
				</tr>
				<tr class="theader">
					<th>配送单号</th>
					<th>配送人</th>
					<th>配送时间</th>
					<th>网点名称</th>
					<th>接收人</th>
					<th>接收时间</th>
				</tr>
			</thead>
			<tbody class="datagrid1-body">
				<tr style="height: 36px">
					<td align="center">${delBean.applyId}</td>
					<td align="center">${delBean.deliveryUserName}</td>
					<td align="center">${delBean.deliveryTime}</td>
					<td align="center">${delBean.outletName}</td>
					<td align="center">${delBean.signUserName}</td>
					<td align="center">${delBean.signTime}</td>
				</tr>
			</tbody>
		</table>
		<table class="report-table" style="width: 90%; margin: 20px 41px">
			<thead class="datagrid1-header">
				<tr>
					<th colspan="7" style="font-size: 14px; background: #b4b4b4;">销售信息</th>
				</tr>
				<tr class="theader" style="border-right: 1px">
					<th>销售单号</th>
					<th>出票时间</th>
					<th>出票网点</th>
					<th>销售价格</th>
					<th>销售人</th>
					<th width="60px;">是否作废</th>
					<th width="140px;">作废时间</th>
				</tr>
			</thead>
			<tbody class="datagrid1-body">
				<tr style="height: 36px">
					<td align="center">${ticketSaleInfoBean.orderId}</td>
					<td align="center">${ticketSaleInfoBean.ejectTicketTime}</td>
					<td align="center">${ticketSaleInfoBean.outletName}</td>
					<td align="center">${ticketSaleInfoBean.saleAmt}</td>
					<td align="center">${ticketSaleInfoBean.userName}</td>
					<td align="center">${ticketSaleInfoBean.uselessFlag}</td>
					<td align="center">${ticketSaleInfoBean.uselessTime}</td>
				</tr>
			</tbody>
		</table>
		<table class="report-table" style="width: 90%; margin: 20px 41px">
			<thead class="datagrid1-header">
				<tr>
					<th colspan="7" style="font-size: 14px; background: #b4b4b4; boder: 1px">检票信息</th>
				</tr>
				<tr class="theader">
					<th>检票时间</th>
					<th>场馆名称</th>
					<th>检票终端</th>
					<th>是否通过</th>
					<th>未通过原因</th>
					<th>剩余次数</th>
				</tr>
			</thead>
			<tbody class="datagrid1-body">
				<c:forEach var="bean" items="${checkList}" varStatus="status">
					<tr style="height: 36px">
						<td align="center">${bean.opeTime}</td>
						<td align="center">${bean.venueName}</td>
						<%-- <td align="center">${bean.venueId}</td> --%>
						<td align="center">${bean.clientName}</td>
						<%-- <td align="center">${bean.clientId}</td>  --%>
						<td align="center">${bean.passFlag}</td>
						<td align="center">${bean.nopassReason}</td>
						<td align="center">
							<c:if test="${bean.remainTimes !=-1}">${bean.remainTimes}</c:if>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
</body>
</html>