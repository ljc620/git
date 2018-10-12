<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="myel" uri="/elFunction"%>
<%@ taglib prefix="sys" tagdir="/WEB-INF/tags/sys" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<c:set var="ctx" value="${pageContext.request.contextPath}"></c:set>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>综合汇总</title>
<%@ include file="/common1.jsp"%>
<script type="text/javascript" src="<%=path%>/js/common/commcheck.js"></script>
<script type="text/javascript" src="${path}/resources/jquery-easyui/plugins/jquery.table.rowspan.js"></script>
<script type="text/javascript">

//查询
function query(){
	if (!$('#queryForm').form('validate')) {
		return false;
	}
	var startDate=$('#startDate').datebox('getValue');
	var endDate=$('#endDate').datebox('getValue');
	var newDate=formatterDate(new Date());
	if(startDate>=newDate||endDate>=newDate){
		alert("提示：起止日期范围应小于当日");
		return false;
	}
	var url="<%=path%>/rptsum/syntheticalSum.do";
	 $('#queryForm').attr("action", url).submit();
}
function expExcel(){
	if (!$('#queryForm').form('validate')) {
		return false;
	}
	var startDate=$('#startDate').datebox('getValue');
	var endDate=$('#endDate').datebox('getValue');
	var newDate=formatterDate(new Date());
	if(startDate>=newDate||endDate>=newDate){
		alert("提示：起止日期范围应小于当日");
		return false;
	}
	var url='<%=path%>/rptsum/expExcelSyntheticalSum.do';
	 $('#queryForm').attr("action", url).submit();
}
$(function(){
	$('#startDate').datebox('setValue','<fmt:formatDate value="${startDate}" pattern="yyyy-MM-dd" />');
	$('#endDate').datebox('setValue','<fmt:formatDate value="${endDate}" pattern="yyyy-MM-dd" />');
	$("#tbListTM").rowspan(0); //第一列合并
});
</script> 
</head>
<body style="background-color:#fff;">
				
				<div class="ss-bar">
				<form id="queryForm">
				<ul class="ss-ul">
					<li style="width: 50px; padding-left: 15px;">起止日期</li>
					<li><input id="startDate" name="startDate"
						class="easyui-datebox" style="width: 100px;" required="true"
						editable="false"></input> --<input id="endDate" name="endDate"
						class="easyui-datebox" style="width: 100px;"
						validType="checkDt['#startDate']" editable="false" required="true"></input></li>
					<li style="width: 30px; padding-left: 15px;">票种</li>
					<li><input id="ticketTypeId" name="ticketTypeId"
							class="easyui-combobox"
							data-options="url: '<%=path%>/orgMng/ticketTypeList.do?flag=a',   
						       method:'get',  
						       valueField:'ticketTypeId',  
						       textField:'ticketTypeName',  
						       multiple:false,  
						       editable:false, 
						       onLoadSuccess : function(e) {
						  },onLoadError : function(e) {
								getAjaxError(e);
											  },
							loadFilter : function(data) {
								var ticketLists = [ {
									ticketTypeId : '',
									ticketTypeName : '--全部--'
								} ];
								return $.merge(ticketLists, data);
							}"
							value="${ticketTypeId}" style="width: 140px;"></input></li>
							<li><a class="gray2-btn"  onclick="query();" >查询</a>
							</li>
							<li>
							<a class="indigo-btn" id="btnExcel"  id="btnExcel" onclick="expExcel()">导出EXCEL</a></li>
						</ul>
					</form>
				</div>
				<div  class="it-datagrid">
				
				<table class="report-table" id="tbListTM" style="width:100%;">
					<thead>
						<tr class="datagrid1-header">
							<th>统计日期</th>
							<th>票种名称</th>
						    <th>库存数量</th>
							<th>销售数量</th>
							<th>入园数量</th>
						</tr>
					</thead>
					<tbody class="datagrid1-body">
					    <c:set var="saleNum" value="0"/>
					     <c:set var="checkNum" value="0"/>
						<c:forEach var="bean" items="${syntheticalList}" varStatus="status">
							    <tr class="tbody">		
							     <td style="text-align:center"><fmt:formatDate value="${bean.rtpDate}"
									pattern="yyyy-MM-dd" /></td>
								<c:if test="${bean.ticketTypeName!='合计'}">
							     <td>${bean.ticketTypeName}</td>
							     <td align="right">${bean.ticketNum}</td> 
								<td align="right">${bean.saleNum}</td>
								<td align="right">${bean.checkNum}</td>
							     </c:if>
							     <c:if test="${bean.ticketTypeName=='合计'}">
								 <td style="font-weight:900;color:#000;text-align:right">${bean.ticketTypeName}</td>
								 <td style="font-weight:900;color:#000;text-align:right">${bean.ticketNum}</td> 
								 <td style="font-weight:900;color:#000;text-align:right">${bean.saleNum}</td>
								 <td style="font-weight:900;color:#000;text-align:right">${bean.checkNum}</td>
								</c:if>
							</tr>
							 <c:set var="saleNum" value="${saleNum+bean.saleNum}"/>
							 <c:set var="checkNum" value="${checkNum+bean.checkNum}"/>
						</c:forEach>
						<c:if test="${fn:length(syntheticalList)!=0}">
						 <tr class="tbody">		
							    <td colspan="3" style="font-weight:900;color:#000;text-align:right">总计</td>
								<td style="font-weight:900;color:#000;text-align:right"><fmt:formatNumber type="number"  value="${saleNum/2}" maxFractionDigits="0"></fmt:formatNumber></td>
								<td style="font-weight:900;color:#000;text-align:right"><fmt:formatNumber type="number"  value="${checkNum/2}" maxFractionDigits="0"></fmt:formatNumber></td>
							</tr>
						</c:if>
					</tbody>
				</table>
				<div style="color:red;">
					<span style="font-weight:bold;">说明：</span>
					<ul style="margin-left:25px;">
					<li>库存数量为中心库房未出库门票库存数量；</li>
					<li>销售数量包括现场销售、自营销售、团体销售、网络销售、代理销售等所有销售渠道销售的门票数；</li>
					<li>入园数量是按照入园人次统计（多次票会按照入园次数累计）。</li>
					</ul>
				</div>
</div>
</body>
</html>