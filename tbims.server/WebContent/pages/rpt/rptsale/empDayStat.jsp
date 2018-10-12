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
<title>员工入园日统计</title>
<%@ include file="/common1.jsp"%>
<script type="text/javascript" src="<%=path%>/js/common/commcheck.js"></script>
<script type="text/javascript" src="<%=path%>/js/common/initcombo.js"></script>
<link rel="stylesheet" href="${path}/resources/ztree_src/css/zTreeStyle/zTreeStyle.css" type="text/css"></link>
<script type="text/javascript" src="${path}/resources/ztree/js/jquery.ztree.core.min.js"></script>
<script type="text/javascript" src="${path}/resources/ztree/js/jquery.ztree.excheck.min.js"></script>
<script type="text/javascript" src="${path}/resources/jquery-easyui/plugins/jquery.table.rowspan.js"></script>
<script type="text/javascript">
$(function(){
	$('#startDate').datebox('setValue','<fmt:formatDate value="${startDate}" pattern="yyyy-MM-dd" />');
	$('#endDate').datebox('setValue','<fmt:formatDate value="${endDate}" pattern="yyyy-MM-dd" />');
	initSeletedCheckBox('<%=path%>',0,'${venueId}','${venueName}');
	$("#venueTree").appendTo($('#venueId').combo('panel'));
	initSeletedCheckBox('<%=path%>',1,'${regionId}','${regionName}');
	$("#regionTree").appendTo($('#regionId').combo('panel'));
	initSeletedCheckBox('<%=path%>',3,'${empId}','${empName}');
	$("#empTree").appendTo($('#empId').combo('panel'));
	$("#tbListTM").rowspan(0); //第一列合并
	$("#tbListTM").rowspan(1);//第二列合并
});
//查询
function query(){
	if (!$('#queryForm').form('validate')) {
		return false;
	} 
	$('#venueName').val($('#venueId').combobox('getText'));
	$('#regionName').val($('#regionId').combobox('getText'));
	$('#empName').val($('#empId').combobox('getText'));
	var url="<%=path%>/rptsale/listEmpInDay.do";
	 $('#queryForm').attr("action", url).submit();
}
function expExcel(){
	if (!$('#queryForm').form('validate')) {
		return false;
	} 
	var url='<%=path%>/rptsale/expExcelEmpFlowDay.do';
		$('#queryForm').attr("action", url).submit();
	}
</script>
</head>
<body style="background-color:#fff">
	<form id="queryForm" method="post">
	<input type="hidden"  name="venueName" id="venueName">
	<input type="hidden"  name="regionName" id="regionName">
	<input type="hidden"  name="empName" id="empName">
		<div class="ss-bar">
			<div>
				<ul class="ss-ul">
					<li style="width: 50px; padding-left: 15px;">统计日期</li>
					<li><input id="startDate" name="startDate"
						class="easyui-datebox"  style="width: 180px;" required="true"
						editable="false"></input> -<input id="endDate" name="endDate"
						class="easyui-datebox" style="width: 180px;"
						validType="checkDt['#startDate']" editable="false" required="true"></input></li>
					<li style="width: 50px; padding-left: 15px;">场馆名称</li>
				<li><span class="col" id="venuecss"> 
				
				<input id="venueId" name="venueId" class="easyui-combobox" style="width:191px;" data-options="panelHeight : 'auto',panelWidth : 'auto'" editable="false" />
				</span> </li>
				</ul>
				<ul class="ss-ul">
					<li style="width: 50px; padding-left: 15px;">区域名称</li>
						<li><span class="col" id="regioncss"> 
						<input id="regionId" name="regionId" class="easyui-combobox" data-options="panelHeight : 'auto',panelWidth : 'auto'" style="width:191px;" editable="false" />
						</span> </li>
					<li style="width: 50px; padding-left: 15px;">员工名称</li>
						<li><span class="col" id="empIdcss"> 
						<input id="empId" name="empId" class="easyui-combobox" data-options="panelHeight : 'auto',panelWidth : 'auto'" style="width:191px;" editable="false" />
						</span> </li>
					<li><a onclick="query();" class="gray2-btn"
						style="width: 35px;">查询</a></li>
					<li><a class="indigo-btn" id="btnExcel"
						style="margin-left: 15px;" onclick="expExcel()">导出EXCEL</a></li>
				</ul>
			</div>
		</div>
	</form>
	<div class="zcfg-panel-body">
		<table class="report-table" style="width: 100%;" id="tbListTM">
			<thead class="datagrid1-header">
				<tr class="theader">
					<th style="width: 150px;">统计日期</th>
					<th style="width: 150px;">场馆名称</th>
					<th style="width: 150px;">区域名称</th>
					<th style="width: 150px;">员工卡号</th>
					<th style="width: 150px;">员工姓名</th>
					<th style="width: 150px;">入园次数</th>
				</tr>
			</thead>
			<tbody class="datagrid1-body">
				<c:forEach var="bean" items="${list}" varStatus="status">
					<tr class="tbody">
							<td align="center"><fmt:formatDate value="${bean.rptDate}"
									pattern="yyyy-MM-dd" /></td>
							<td align="center">${bean.venueName}</td>
							<td align="center">${bean.regionName}</td>
							<td align="center">${bean.empId}</td>
							<td align="center">${bean.empName}</td>
							<td align="right"><fmt:formatNumber type="number"
									value="${bean.checkTicketNum}" maxFractionDigits="0"></fmt:formatNumber></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
	<div id="venueCon" class="orgContent" style="display: none; position: absolute;">
		<ul id="venueTree" class="ztree" style="margin-top: 0; width: 180px; height: 200px;"></ul>
	</div>
	<div id="regionCon" class="orgContent" style="display: none; position: absolute;">
		<ul id="regionTree" class="ztree" style="margin-top: 0; width: 180px; height: 200px;"></ul>
	</div>
	<div id="ticketTypeCon" class="orgContent" style="display: none; position: absolute;">
		<ul id="ticketTypeTree" class="ztree" style="margin-top: 0; width: 180px; height: 200px;"></ul>
	</div>
	<div id="empCon" class="orgContent" style="display: none; position: absolute;">
		<ul id="empTree" class="ztree" style="margin-top: 0; width: 180px; height: 200px;"></ul>
	</div>
</body>
</html>