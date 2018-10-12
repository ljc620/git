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
<title>销售统计按支付方式</title>
<%@ include file="/common1.jsp"%>
<script type="text/javascript" src="<%=path%>/js/common/commcheck.js"></script>
<script type="text/javascript" src="${path}/resources/jquery-easyui/plugins/jquery.table.rowspan.js"></script>
<link rel="stylesheet" href="${path}/resources/ztree_src/css/zTreeStyle/zTreeStyle.css" type="text/css"></link>
<script type="text/javascript" src="${path}/resources/ztree/js/jquery.ztree.core.min.js"></script>
<script type="text/javascript" src="${path}/resources/ztree/js/jquery.ztree.excheck.min.js"></script>
<script type="text/javascript">

//查询
function query(){
	if (!$('#queryForm').form('validate')) {
		return false;
	}
	$('#outletName').val($('#outletId').combobox('getText'));
	var url="<%=path%>/rptsum/payTypeSum.do";
	$('#queryForm').attr("action", url).submit();
	 
}
function expExcel(){
	if (!$('#queryForm').form('validate')) {
		return false;
	}
	var url='<%=path%>/rptsum/expExcelpayTypeSum.do';
	 $('#queryForm').attr("action", url).submit();
}
function initLoad() {
	$("#tbListTM").rowspan(0); //第一列合并
	$("#tbListTM").rowspan(1);//第二列合并
   
}
$(function(){
	$('#startDate').datebox('setValue','<fmt:formatDate value="${startDate}" pattern="yyyy-MM-dd" />');
	$('#endDate').datebox('setValue','<fmt:formatDate value="${endDate}" pattern="yyyy-MM-dd" />');
	$('#outletId').combobox('setValue','${outletId}');
	$('#outletId').combobox('setText','${outletName}');

	initLoad();
});
</script> 
</head>
<body style="background-color:#fff;">
				
				<div class="ss-bar">
				<form id="queryForm" method="post">
					<input type="hidden"  name="outletName" id="outletName">
						<ul class="ss-ul">
						<li style="width: 50px; padding-left: 15px;">起止日期</li>
					<li><input id="startDate" name="startDate"
						class="easyui-datebox" style="width: 100px;" required="true"
						editable="false"></input>-<input id="endDate" name="endDate"
						class="easyui-datebox" style="width: 100px;"
						validType="checkDt['#startDate']" editable="false" required="true"></input></li>
						<li style="width: 50px; padding-left: 15px;">网点名称</li>
					<li><%-- <input id="outletId" name="outletId" class="easyui-combobox"
						data-options="url :'<%=path%>/comm/listSaleOutlet.do',
								method : 'get',
								valueField : 'outletId',
								textField : 'outletName',
								multiple : false,
								editable : false,
								onLoadSuccess : function(e) {
								},
								onLoadError : function(e) {
									getAjaxError(e);
								} ,
								loadFilter : function(data) {
									var outletLists = [ {
										outletId : '',
										outletName : '--全部--'
									} ];
									return $.merge(outletLists, data);
								}"
						value="${outletId}" style="width: 170px;"></input> --%>
						<sys:noptreeselect id="outletId" name="outletId" options="width:200,panelWidth:200"  idField ="outletId"  textField="outletName" 
							url="comm/listOutletTree.do" pidField="outletType" isParentLevel="false" />
						</li>
						<li style="width: 50px; padding-left: 15px;">用户名称</li>
					<li>
					<sys:cascadeselect valueField="userId"  textField="userName" url="rptsum/userList.do" name="userId" id="userId"
						options="width:170,editable:false" paramName="outletId" parentId="outletId"  isParentLevel="false"   />
					<%-- <input id="userId" name="userId" class="easyui-combobox"
						data-options="url :'<%=path%>/rptsum/userList.do',
								method : 'get',
								valueField : 'userId',
								textField : 'userName',
								multiple : false,
								editable : false,
								onLoadSuccess : function(e) {
								},
								onLoadError : function(e) {
									getAjaxError(e);
								},loadFilter : function(data) {
									var userLists = [ {
										userId: '',
										userName : '--全部--'
									} ];
									return $.merge(userLists, data);
								} "
						value="${userId}" style="width: 140px;"></input> --%></li>
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
						 	<th>网点名称</th>
							<th>票种名称</th>
						    <th>用户名称</th>
							<th>现金</th>
							<th>POS付款</th>
							<th>微信</th>
							<th>支付宝</th>
							<th>代理</th>
							<th>合计</th>
						</tr>
					</thead>
					<tbody class="datagrid1-body">
					   <c:set var="money" value="0"/>
					    <c:set var="posMachine" value="0"/>
					     <c:set var="wechat" value="0"/>
					    <c:set var="alipay" value="0"/>
					     <c:set var="agents" value="0"/>
					    <c:set var="total" value="0"/>
						<c:forEach var="bean" items="${payTypeSumList}" varStatus="status">
							    <tr class="tbody">		
							     <td><fmt:formatDate value="${bean.rptDate}"
									pattern="yyyy-MM-dd" /></td>
							     <td >${bean.outletName}</td>
							    <td >${bean.ticketTypeName}</td>
							    <td >${bean.userName}</td>
								<td align="right">${bean.money}</td> 
								<td align="right">${bean.posMachine}</td>
								<td align="right">${bean.wechat}</td>
								<td align="right">${bean.alipay}</td>
								<td align="right">${bean.agents}</td>
								<td align="right">${bean.total}</td>
							</tr>
							 <c:set var="money" value="${money+bean.money}"/>
							 <c:set var="posMachine" value="${posMachine+bean.posMachine}"/>
							 <c:set var="wechat" value="${wechat+bean.wechat}"/>
							 <c:set var="alipay" value="${alipay+bean.alipay}"/>
							 <c:set var="agents" value="${agents+bean.agents}"/>
							 <c:set var="total" value="${total+bean.total}"/>
						</c:forEach>
						<c:if test="${fn:length(payTypeSumList)!=0}">
						 <tr class="tbody">		
							    <td colspan="4" style="font-weight:900;color:#000;text-align:center">合计</td>
								<td align="right"><fmt:formatNumber type="number"  value="${money}" maxFractionDigits="0"></fmt:formatNumber></td>
								<td align="right"><fmt:formatNumber type="number"  value="${posMachine}" maxFractionDigits="0"></fmt:formatNumber></td>
								<td align="right"><fmt:formatNumber type="number"  value="${wechat}" maxFractionDigits="0"></fmt:formatNumber></td>
								<td align="right"><fmt:formatNumber type="number"  value="${alipay}" maxFractionDigits="0"></fmt:formatNumber></td>
								<td align="right"><fmt:formatNumber type="number"  value="${agents}" maxFractionDigits="0"></fmt:formatNumber></td>
								<td align="right"><fmt:formatNumber type="number"  value="${total}" maxFractionDigits="0"></fmt:formatNumber></td>
							</tr>
						</c:if>
					</tbody>
				</table>
</div>
</body>
</html>