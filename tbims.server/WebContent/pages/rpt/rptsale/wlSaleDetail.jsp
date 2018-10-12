<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="myel" uri="/elFunction"%>
<%@ taglib prefix="sys" tagdir="/WEB-INF/tags/sys" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<c:set var="ctx" value="${pageContext.request.contextPath}"></c:set>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>网络代理商销售明细</title>
<%@ include file="/common.jsp"%>
<script type="text/javascript" src="wlSaleDetail.js"></script>
<link rel="stylesheet" href="${path}/resources/ztree_src/css/zTreeStyle/zTreeStyle.css" type="text/css"></link>
<script type="text/javascript" src="${path}/resources/ztree/js/jquery.ztree.core.min.js"></script>
<script type="text/javascript" src="${path}/resources/ztree/js/jquery.ztree.excheck.min.js"></script>
</head>
<body>
	<div id="mytable-buttons">
		<div class="ss-bar">
			<form id="queryForm">
				<ul class="ss-ul">
					<li style="width: 50px; padding-left: 15px;">统计日期</li>
					<li><input id="rptDt" name="rptDt" class="easyui-datebox"  
							style="width: 120px;" required="true" editable="false"></input></li>
					<!-- 
					<li style="width: 45px; padding-left: 15px;">时间段</li>
					<li><input class="easyui-timespinner" style="width: 80px"
							data-options="showSeconds:false,value:'00:00:00'" id="beginTime"
							name="beginTime" style="width: 130px;" required="true" /> --<input
							class="easyui-timespinner" style="width: 80px"
							data-options="showSeconds:false,value:'23:59:59'" id="endTime"
							name="endTime" required="true" /></li>
					-->
					<li style="width: 50px;">机构名称</li>
					<li>
						<input id="orgId" name="orgId"></input>
					</li>
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
							style="width: 140px;"></input></li>
					<li>销售单号</li>
					<li>
						<input id="orderId" name="orderId" class="easyui-textbox" style="width: 200px;"></input>
					</li>
					<li><a onclick="query();" class="gray2-btn"
						style="width: 35px;">查询</a></li>
					<li><a class="indigo-btn" id="btnExcel"
						style="margin-left: 15px;" onclick="expExcel()">导出EXCEL</a></li>
				</ul>
			</form>
		</div>
	</div>
	<div class="it-datagrid" style="height: 100%">
		<table id="mytable">
		</table>
	</div>
</body>
</html>