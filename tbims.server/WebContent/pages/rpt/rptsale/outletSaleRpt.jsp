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
<title>网点销售日统计</title>
<%@ include file="/common1.jsp"%>
<script type="text/javascript" src="${path}/resources/jquery-easyui/plugins/jquery.table.rowspan.js"></script>
<script type="text/javascript">

//查询
function query(){
	if (!$('#queryForm').form('validate')) {
		return false;
	}
	$.messager.progress({
		text : '正在处理,请稍候...'
	});
	var url="<%=path%>/rptsale/outletSaleRpt.do";
	 $('#queryForm').attr("action", url).submit();
}
function expExcel(){
	if (!$('#queryForm').form('validate')) {
		return false;
	}
	var url='<%=path%>/rptsale/expExceloutletSale.do';
	 $('#queryForm').attr("action", url).submit();
}
function initLoad() {
	$("#tbListTM").rowspan(0); //第一列合并
	$("#tbListTM").rowspan(1);//第二列合并
	  //  $("#tbListTM").rowspan(2);//第二列合并
	    
        
        
        
       
  
}
$(document).ready(function () {
	$("#rptDt").datebox('setValue','<fmt:formatDate value="${rptDt}" type="both" pattern="yyyy-MM-dd" />');
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
								<input id="rptDt" name="rptDt" class="easyui-datebox"  data-options="editable:false"  style="width:120px;" required="true"></input>
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
						   
							<th>网点名称</th>
							<th>交易类型（包括换票）</th>
							<th>支付方式</th>
							<th>票种名称</th>
							<th>销售数量</th>
							<th>销售总金额（不包括换票）</th>
						</tr>
					</thead>
					<tbody class="datagrid1-body">
					   <c:set var="sumNum" value="0"/>
					    <c:set var="sumAmt" value="0"/>
						<c:forEach var="bean" items="${outletSaleList}" varStatus="status">
							    <tr class="tbody">		
							   
								<td >${bean.outletName}</td> 
								<td align="center">${bean.orderTypeDesc}</td>
								<td align="center">${bean.payTypeDesc}</td>
								<td align="center">${bean.ticketTypeName}</td>
								<td align="right">${bean.saleSum}</td>
								<td align="right">${bean.saleAmt}</td>
							</tr>
							 <c:set var="sumNum" value="${sumNum+bean.saleSum}"/>
							 <c:set var="sumAmt" value="${sumAmt+bean.saleAmt}"/>
						</c:forEach>
						<c:if test="${sumNum !=0}">
						 <tr class="tbody">		
							    <td colspan="4" style="font-weight:900;color:#000;text-align:center">合计</td>
								<td align="right"><fmt:formatNumber type="number"  value="${sumNum/2}" maxFractionDigits="0"></fmt:formatNumber></td>
								<td align="right"><fmt:formatNumber type="number"  value="${sumAmt/2}" maxFractionDigits="0"></fmt:formatNumber></td>
							</tr>
						</c:if>
					</tbody>
				</table>
</div>
</body>
</html>