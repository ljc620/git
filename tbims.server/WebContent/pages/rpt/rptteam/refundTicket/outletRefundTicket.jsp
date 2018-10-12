<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="myel" uri="/elFunction"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sys" tagdir="/WEB-INF/tags/sys"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>网点退票统计报表</title>
<%@ include file="/common.jsp"%>
<script type="text/javascript" src="outletRefundTicket.js"></script>
</head>
<body>

	<div id="mytable-buttons">
		<div class="ss-bar">
			<form id="form">
				<ul class="ss-ul">
					<li style="width: 50px; padding-left: 10px;">统计日期</li>
					<li>
						<input id="refundTimeBegin" name="refundTimeBegin" class="easyui-datebox"  data-options="editable:false"  style="width: 180px;" value="true" />
						-
						<input id="refundTimeEnd" name="refundTimeEnd" class="easyui-datebox"  data-options="editable:false"  style="width: 180px;" value="true" />
					</li>
					<li>
						<a href="javascript:void(0)" onclick="javascript:query();" class="gray2-btn" style="width: 30px;">查询</a>
					</li>
					<li>
						<span class="col"><a class="indigo-btn" style="margin-left: 2px;" id="btnExcel" onclick="expExcel()">导出EXCEL</a></span>
					</li>
				</ul>
			</form>
		</div>
	</div>
	<div class="it-datagrid" style="height: 100%">
		<table id="mytable"></table>
	</div>
</body>
</html>