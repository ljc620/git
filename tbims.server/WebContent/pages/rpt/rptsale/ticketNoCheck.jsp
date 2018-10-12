<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="myel" uri="/elFunction"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"></c:set>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<!-- <meta http-equiv="refresh" content="10">-->
<title>门票未入园统计表</title>
<%@ include file="/common1.jsp"%> 
<script type="text/javascript" src="${path}/resources/jquery-easyui/plugins/jquery.table.rowspan.js"></script>
<script type="text/javascript">
	function expExcel(){
	var url='<%=path%>/rptsale/expTicketNoCheck.do';
		$('#queryForm').attr("action", url).submit();
	}
	function myrefresh(){
		window.location.reload();
	}
	setTimeout('myrefresh()',600000);
	
	$(function(){
		$("#tbListTM").rowspan(0); //第一列合并
		$("#tbListTM").rowspan(1); //第二列合并
	});
</script>
</head>
<body style="background-color:#fff">
	<form id="queryForm" method="post">
		<div class="ss-bar">
			<ul class="ss-ul">
				<li><a class="indigo-btn" id="btnExcel" style="margin-left: 15px;" onclick="expExcel()">导出EXCEL</a></li>
			</ul>
		</div>
	</form>
	<div class="zcfg-panel-body">
		<table class="report-table" id="tbListTM" style="width: 100%;">
			<thead class="datagrid1-header">
				<tr class="theader">
					<th style="width: 100px;">网点名称</th>
					<th style="width: 100px;">交易类型</th>
					<th style="width: 100px;">票种名称</th>
					<th style="width: 100px;">门票未入园数量</th>
				</tr>
			</thead>
			<tbody class="datagrid1-body">
			   <c:set var="sumNum" value="0"/>
				<c:forEach var="bean" items="${list}" varStatus="status">
					<tr class="tbody">
							<td align="center">${bean.outletName}</td>
							<td align="center">${bean.itemName}</td>
							<td align="center">${bean.ticketTypeName}</td>
							<td align="right">${bean.ticketNum}</td>
					</tr>
					<c:set var="sumNum" value="${sumNum+bean.ticketNum}"/>
				</c:forEach>
				<c:if test="${sumNum !=0}">
					<tr class="tbody">		
					    <td colspan="3" style="font-weight:900;color:#000;text-align:center">合计</td>
						<td style="font-weight:900;color:#000;text-align:right"><fmt:formatNumber type="number"  value="${sumNum}" maxFractionDigits="0"></fmt:formatNumber></td>
					</tr>
				</c:if>
			</tbody>
		</table>
	</div>
</body>
</html>