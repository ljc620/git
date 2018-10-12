<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="myel" uri="/elFunction"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<c:set var="ctx" value="${pageContext.request.contextPath}"></c:set>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>入园游客渠道来源统计</title>
<%@ include file="/common1.jsp"%>
<script type="text/javascript" src="<%=path%>/js/common/commcheck.js"></script>
<script type="text/javascript"
	src="${path}/resources/jquery-easyui/plugins/jquery.table.rowspan.js"></script>
<script type="text/javascript">
function dataDiff(beginDate,endDate){//格式2017-11-15
	
	var aDate,oBeginDate,oEndDate,iDays;
	aDate=beginDate.split("-");
	
	oBeginDate=new Date(aDate[1]+'/'+aDate[2]+'/'+aDate[0]);//转换为11-15-2017
	
	aDate=endDate.split("-");
	oEndDate=new Date(aDate[1]+'/'+aDate[2]+'/'+aDate[0]);//转换为11-15-2017
	
	iDays=parseInt(Math.abs(oEndDate-oBeginDate))/1000/60/60/24;

	return iDays;
}

//查询
function query(){
	if (!$('#queryForm').form('validate')) {
		return false;
	}
	
	var beginDate=$("#beginDate").datebox('getValue');
	var endDate=$("#endDate").datebox('getValue');
	var iDays=dataDiff(beginDate,endDate);
	if(iDays>14){
		$.messager.alert('系统提示','最多查询14天以内的数据');
		return false;
	}
	
	$.messager.progress({
		text : '正在处理,请稍候...'
	});
	var url="<%=path%>/rptsale/regionCheckTicketByChannel.do";
	 $('#queryForm').attr("action", url).submit();
}
function expExcel(){
	if (!$('#queryForm').form('validate')) {
		return false;
	}
	var url='<%=path%>/rptsale/expExlRegionCheckTicketByChannel.do';
		$('#queryForm').attr("action", url).submit();
	}

	function initLoad() {
		$("#tbListTM").rowspan(0); //第一列合并
		$("#tbListTM").rowspan(1);//第二列合并
		//  $("#tbListTM").rowspan(2);//第二列合并
	}
	$(document)
			.ready(
					function() {
						$("#beginDate")
								.datebox('setValue',
										'<fmt:formatDate value="${beginDate}" type="both" pattern="yyyy-MM-dd" />');
						$("#endDate")
								.datebox('setValue',
										'<fmt:formatDate value="${endDate}" type="both" pattern="yyyy-MM-dd" />');
						initLoad();
					});
</script>
</head>
<body style="background-color: #fff;">
	<div class="ss-bar">
		<form id="queryForm">
			<ul class="ss-ul">
				<li>统计日期</li>
				<li><input id="beginDate" name="beginDate"
					class="easyui-datebox" data-options="editable:false"
					style="width: 120px;" required="true"></input>- <input id="endDate"
					name="endDate" class="easyui-datebox"
					validType="checkDt['#beginDate']" data-options="editable:false"
					style="width: 120px;" required="true"></input></li>
				<li><a class="gray2-btn" onclick="query();">查询</a></li>
				<li><a class="indigo-btn" id="btnExcel" id="btnExcel"
					onclick="expExcel()">导出EXCEL</a></li>
			</ul>
		</form>
	</div>
	<div class="zcfg-panel-body">
		<table class="report-table" id="tbListTM" style="width: 100%;">
			<thead class="datagrid1-header">
				<tr>
					<th>日期</th>
					<th>门区</th>
					<th>售票类别</th>
					<th>出票数量</th>
				</tr>
			</thead>
			<tbody class="datagrid1-body">
				<c:set var="sumNum" value="0" />
				<c:forEach var="bean" items="${regionCheckTicketList}"
					varStatus="status">
					<tr class="tbody">
						<td align="center">${bean.rptDt}</td>
						<td align="center">${bean.regionName}</td>
						<td align="center">
							<c:if test="${bean.orderTypeDsec=='合计' || bean.orderTypeDsec=='小计'}">
								<span style="font-weight:900;color:#000;" >${bean.orderTypeDsec}</span>
							</c:if>
							<c:if test="${bean.orderTypeDsec!='合计' && bean.orderTypeDsec!='小计'}">
								${bean.orderTypeDsec}
							</c:if>
						</td>
						<td align="right">${bean.ejectTicketNum}</td>
						<c:if test="${bean.orderTypeDsec=='合计'}">
							<c:set var="sumNum" value="${sumNum+bean.ejectTicketNum}" />
						</c:if>
					</tr>
				</c:forEach>
				<c:if test="${sumNum !=0}">
					<tr class="tbody">
						<td colspan="3"
							style="font-weight: 900; color: #000; text-align: center">合计</td>
						<td align="right"><fmt:formatNumber type="number"
								value="${sumNum}" maxFractionDigits="0"></fmt:formatNumber></td>
				</c:if>
			</tbody>
		</table>
	</div>
</body>
</html>