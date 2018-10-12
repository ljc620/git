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
<title>售换票分时统计</title>
<%@ include file="/common1.jsp"%>
<script type="text/javascript" src="${path}/resources/jquery-easyui/plugins/jquery.table.rowspan.js"></script>
<script type="text/javascript">

//查询
function query(){
	if (!$('#queryForm').form('validate')) {
		return false;
	}
	var url="<%=path%>/rptsale/listSaleChange.do";
	 $('#queryForm').attr("action", url).submit();
}
function expExcel(){
	if (!$('#queryForm').form('validate')) {
		return false;
	}
	var url='<%=path%>/rptsale/expExcelSaleChange.do';
	 $('#queryForm').attr("action", url).submit();
}
function initLoad() {
	$("#tbListTM").rowspan(0); //第一列合并
	    
        
        
        
       
  
}
$(document).ready(function () {
	$("#startDate").datebox('setValue','<fmt:formatDate value="${startDate}" type="both" pattern="yyyy-MM-dd" />');
	$("#endDate").datebox('setValue','<fmt:formatDate value="${endDate}" type="both" pattern="yyyy-MM-dd" />');
   initLoad();
});
</script> 
</head>
<body style="background-color:#fff;">
				
				<div class="ss-bar">
				<form id="queryForm">
						<ul class="ss-ul">
							<li>统计日期</li>
							<li>
								<input id="startDate" name="startDate" class="easyui-datebox"  data-options="editable:false"  style="width:120px;" required="true"></input>-
								<input id="endDate" name="endDate" class="easyui-datebox"  data-options="editable:false"  style="width:120px;" required="true"></input>
							</li>
							<li><a class="gray2-btn"  onclick="query();" >查询</a>
							</li>
							<li>
							<a class="indigo-btn" id="btnExcel"  id="btnExcel" onclick="expExcel()">导出EXCEL</a></li>
						</ul>
					</form>
				</div>
				<div  class="zcfg-panel-body">
				
				<table class="report-table" id="tbListTM" style="width:100%;">
					<thead class="datagrid1-header">
						<tr>
						   
							<th>售换票日期</th>
							<th>网点名称</th>
							<th>09:00之前</th>
							<th>09:00-10:00</th>
							<th>10:00-11:00</th>
							<th>11:00-12:00</th>
							<th>12:00-13:00</th>
							<th>13:00-14:00</th>
							<th>14:00-15:00</th>
							<th>15:00-16:00</th>
							<th>16:00-17:00</th>
							<th>17:00以后</th>
						</tr>
					</thead>
					<tbody class="datagrid1-body">
						<c:forEach var="bean" items="${saleChangeList}" varStatus="status">
							    <tr class="tbody">		
							   
								<td >${bean.saleTime}</td> 
								<c:if test="${bean.outletName=='总计'}">
								<td align="center" style="font-weight:bold;color:#000">${bean.outletName}</td>
								<td align="right" style="font-weight:bold;color:#000">${bean.c9}</td>
								<td align="right" style="font-weight:bold;color:#000">${bean.c10}</td>
								<td align="right" style="font-weight:bold;color:#000">${bean.c11}</td>
								<td align="right" style="font-weight:bold;color:#000">${bean.c12}</td>
								<td align="right" style="font-weight:bold;color:#000">${bean.c13}</td>
								<td align="right" style="font-weight:bold;color:#000">${bean.c14}</td>
								<td align="right" style="font-weight:bold;color:#000">${bean.c15}</td>
								<td align="right" style="font-weight:bold;color:#000">${bean.c16}</td>
								<td align="right" style="font-weight:bold;color:#000">${bean.c17}</td>
								<td align="right"style="font-weight:bold;color:#000">${bean.c18}</td>
								</c:if>
								<c:if test="${bean.outletName!='总计'}">
					            <td align="center" >${bean.outletName}</td>
								<td align="right" >${bean.c9}</td>
								<td align="right" >${bean.c10}</td>
								<td align="right" >${bean.c11}</td>
								<td align="right" >${bean.c12}</td>
								<td align="right" >${bean.c13}</td>
								<td align="right" >${bean.c14}</td>
								<td align="right" >${bean.c15}</td>
								<td align="right" >${bean.c16}</td>
								<td align="right" >${bean.c17}</td>
								<td align="right" >${bean.c18}</td>
								</c:if>
							</tr>
						</c:forEach>
					</tbody>
				</table>
</div>
</body>
</html>