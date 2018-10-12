<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="myel" uri="/elFunction"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"></c:set>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>票务中心当日库存统计表</title>
<%@ include file="/common.jsp"%>
<script type="text/javascript" src="centerStoreRpt.js"></script>
</head>
<body>
	<div id="mytable-buttons">	
	<div class="ss-bar">	
	<form id="queryForm">
			<ul class="ss-ul">
					<li  style="width: 50px;padding-left:15px;">票种名称</li>
					<li>
						<input id="ticketTypeId" name="ticketTypeId" class="easyui-combobox" style="width:120px;"></input>
					</li>
				<li><a class="gray2-btn" href="javascript:void(0)"
					onclick="javascript:myQuery();">查询</a> 
				</li>
				<li>
				<a class="indigo-btn"
					id="btnExcel"  id="btnExcel"
					onclick="expExcel()">导出EXCEL</a>
					</li>
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