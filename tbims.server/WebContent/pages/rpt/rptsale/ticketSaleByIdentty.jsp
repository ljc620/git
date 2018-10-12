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
<title>身份证查验</title>
<%@ include file="/common1.jsp"%>
<script type="text/javascript">

//查询
function query(){
	if (!$('#queryForm').form('validate')) {
		return false;
	}
	var url="<%=path%>/rptsale/listTicketInfoByIdentty.do";
	 $('#queryForm').attr("action", url).submit();
}
function expExcel(){
	if (!$('#queryForm').form('validate')) {
		return false;
	}
	var url='<%=path%>/rptsale/expExlListTicketInfoByIdentty.do';
		$('#queryForm').attr("action", url).submit();
	}
	$(document).ready(function() {
		$("#identtyId").textbox('setValue', '${identtyId}');
	});
</script>
</head>
<body style="background-color: #fff;">
	<div class="ss-bar" style="margin: 16px;">
		<form id="queryForm">
			<ul class="ss-ul">
				<li>身份证号</li>
				<li>
					<input id="identtyId" name="identtyId" class="easyui-textbox" style="width: 150px;" required="true"></input>
				</li>
				<li>
					<a class="gray2-btn enter" onclick="query();">查询</a>
				</li>
				<li>
					<a class="indigo-btn" id="btnExcel" id="btnExcel" onclick="expExcel()">导出EXCEL</a>
				</li>
			</ul>
		</form>
	</div>
	<div class="it-datagrid" style="padding:0px 10px; width:1506px;">
		<table class="report-table">
			<thead class="datagrid1-header">
				<tr>
					<th colspan="12" style="font-size: 14px; background: #b4b4b4;">销售信息</th>
				</tr>
				<tr class="theader" style="border-right: 1px">
					<th width="140px;">售票时间</th>
					<th width="240px;">销售单号</th>
					<th width="140px;">票种名称</th>
					<th width="60px;">是否检票</th>
					<th width="140px;">检票时间</th>
					<th width="60px;">支付方式</th>
					<th width="100px;">有效开始日期</th>
					<th width="100px;">有效结束日期</th>
					<th width="60px;">是否退款</th>
					<th width="140px;">退款时间</th>
					<th width="60px;">取票状态</th>
					<th width="140px;">取票时间</th>
				</tr>
			</thead>
			<tbody class="datagrid1-body">
				<c:forEach var="saleBean" items="${saleIdenttyList}" varStatus="status">
					<tr style="height: 36px">
						<td align="center">${saleBean.saleTime}</td>
						<td align="center">${saleBean.orderId}</td>
						<td align="center">${saleBean.ticketTypeName}</td>
						<td align="center">${saleBean.checkFlag}</td>
						<td align="center">${saleBean.checkTicketTime}</td>
						<td align="center">${saleBean.payType}</td>
						<td align="center">${saleBean.validStartDate}</td>
						<td align="center">${saleBean.validEndDate}</td>
						<td align="center">${saleBean.uselessFlag}</td>
						<td align="center">${saleBean.uselessTime}</td>
						<td align="center">${saleBean.ejectTicketStat}</td>
						<td align="center">${saleBean.ejectTicketTime}</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		<table class="report-table" style="width: 100%; margin: 10px 0px">
			<thead class="datagrid1-header">
				<tr>
					<th colspan="8" style="font-size: 14px; background: #b4b4b4; boder: 1px">检票信息</th>
				</tr>
				<tr class="theader">
					<th width="140px;">检票时间</th>
					<th width="240px;">销售单号</th>
					<th>票种名称</th>
					<th>场馆名称</th>
					<th>检票终端</th>
					<th>是否通过</th>
					<th>未通过原因</th>
					<th>剩余次数</th>
				</tr>
			</thead>
			<tbody class="datagrid1-body">
				<c:forEach var="checkBean" items="${checkIdenttyList}" varStatus="status">
					<tr style="height: 36px">
						<td align="center">${checkBean.opeTime}</td>
						<td align="center">${checkBean.orderId}</td>
						<td align="center">${checkBean.ticketTypeName}</td>
						<td align="center">${checkBean.venueName}</td>
						<%-- <td align="center">${bean.venueId}</td> --%>
						<td align="center">${checkBean.clientName}</td>
						<%-- <td align="center">${bean.clientId}</td>  --%>
						<td align="center">${checkBean.passFlag}</td>
						<td align="center">${checkBean.nopassReason}</td>
						<td align="center">
							<c:if test="${checkBean.remainTimes !=-1}">${checkBean.remainTimes}</c:if>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
</body>
</html>