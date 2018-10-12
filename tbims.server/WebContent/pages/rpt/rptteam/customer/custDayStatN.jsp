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
<title>客流统计（按区域、票种）</title>
<%@ include file="/common1.jsp"%>
<script type="text/javascript" src="<%=path%>/js/common/commcheck.js"></script>
<script type="text/javascript" src="<%=path%>/js/common/initcombo.js"></script>
<link rel="stylesheet" href="${path}/resources/ztree_src/css/zTreeStyle/zTreeStyle.css" type="text/css"></link>
<script type="text/javascript" src="${path}/resources/ztree/js/jquery.ztree.core.min.js"></script>
<script type="text/javascript" src="${path}/resources/ztree/js/jquery.ztree.excheck.min.js"></script>
<script type="text/javascript">
$(function(){
	$('#startDate').datebox('setValue','<fmt:formatDate value="${startDate}" pattern="yyyy-MM-dd" />');
	$('#endDate').datebox('setValue','<fmt:formatDate value="${endDate}" pattern="yyyy-MM-dd" />');
	initSeletedCheckBox('<%=path%>',1,'${regionId}','${regionName}');
	$("#regionTree").appendTo($('#regionId').combo('panel'));
});
//查询
function query(){
	if (!$('#queryForm').form('validate')) {
		return false;
	} 
	$('#regionName').val($('#regionId').combobox('getText'));
	var url="<%=path%>/rptTeam/listCustFlowDayN.do";
	 $('#queryForm').attr("action", url).submit();
}
function expExcel(){
	if (!$('#queryForm').form('validate')) {
		return false;
	} 
	var url='<%=path%>/rptTeam/expExcelCustFlowDayN.do';
		$('#queryForm').attr("action", url).submit();
	}
</script>
</head>
<body style="background-color:#fff">
	<form id="queryForm" method="post">
	<input type="hidden"  name="regionName" id="regionName">
		<div class="ss-bar">
			<div>
				<ul class="ss-ul">
					<li style="width: 50px; padding-left: 15px;">统计日期</li>
					<li><input id="startDate" name="startDate"
						class="easyui-datebox" style="width: 180px;" required="true"
						editable="false"></input> --<input id="endDate" name="endDate"
						class="easyui-datebox" style="width: 180px;"
						validType="checkDt['#startDate']" editable="false" required="true"></input></li>
					<li style="width: 50px; padding-left: 15px;">区域名称</li>
					<%-- <li><input id="regionId" name="regionId"
						class="easyui-combobox"
						data-options="url :'<%=path%>/comm/listRegion.do',
								method : 'get',
								valueField : 'regionId',
								textField : 'regionName',
								multiple : true,
								editable : false,
								onLoadSuccess : function(e) {
								},
								onLoadError : function(e) {
									getAjaxError(e);
								} "
						value="${regionId}" style="width: 140px;"></input></li> --%>
					<li><span class="col" id="regioncss"> 
					<input id="regionId" name="regionId" class="easyui-combobox" style="width:191px;" data-options="panelHeight : 'auto',panelWidth : 'auto'" editable="false" />
				   	</span> </li>
					<%-- <li style="width: 30px; padding-left: 15px;">票种</li>
					<li><input id="ticketTypeId" name="ticketTypeId"
						class="easyui-combobox"
						data-options="url:'<%=path%>/orgMng/ticketTypeList.do?flag=a',   
						         method:'get',  
						         valueField:'ticketTypeId',  
						         textField:'ticketTypeName',  
						         multiple:true,  
						         editable:false,  
						         panelHeight:'auto',
								onLoadSuccess : function(e) {
								},
								onLoadError : function(e) {
									getAjaxError(e);
								} "
						value="${ticketTypeId}" style="width: 140px;"></input></li> --%>
					<li><a onclick="query();" class="gray2-btn"
						style="width: 35px;">查询</a></li>
					<li><a class="indigo-btn" id="btnExcel"
						style="margin-left: 15px;" onclick="expExcel()">导出EXCEL</a></li>
				</ul>
			</div>
		</div>
	</form> 
	<div class="zcfg-panel-body" style="padding:1px;">
		<table style="width:100%;" class="report-table">
			<thead class="datagrid1-header">
				<tr class="theader">
					<th align="center" nowrap="nowrap">序号</th>
					<th align="center" nowrap="nowrap">区域名称</th>
					<c:forEach items="${ticTypeColName}" var="list">
						<th align="center" nowrap="nowrap">${list}</th>
					</c:forEach>
					<th align="center" nowrap="nowrap" style="font-weight:bold;color:#000">合计</th>
				</tr>
				</thead>
				<tbody class="datagrid1-body">
				<c:forEach var="bean" items="${custList }" varStatus="a">
					<tr>
						<c:if test="${bean.regionName=='总计'}">
							<td class="data" align="right" colspan="2" nowrap="nowrap" style="font-weight:bold;">${bean.regionName}</td>
							<c:forEach items="${bean.ticTypeNum}" var="num">
								<td class="data" align="center" nowrap="nowrap" style="font-weight:bold;">${num}</td>
							</c:forEach>
						</c:if>
						<c:if test="${bean.regionName!='总计'}">
							<td class="data" align="center" nowrap="nowrap">${a.index+1}</td>
							<td class="data" align="center" nowrap="nowrap">${bean.regionName}</td>
							<c:forEach items="${bean.ticTypeNum}" var="num">
								<td class="data" align="center" nowrap="nowrap">${num}</td>
							</c:forEach>
						</c:if>
						<td class="data" align="center" nowrap="nowrap" style="font-weight:bold;">${bean.totalNum}</td>
					</tr>
				</c:forEach>
				</tbody>
		</table>
	</div>
	<div id="regionCon" class="orgContent" style="display: none; position: absolute;">
		<ul id="regionTree" class="ztree" style="margin-top: 0; width: 180px; height: 200px;"></ul>
	</div>
</body>
</html>