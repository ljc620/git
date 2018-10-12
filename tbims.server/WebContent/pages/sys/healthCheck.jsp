<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="myel" uri="/elFunction"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>票务系统健康检查</title>
<%@ include file="/common.jsp"%>
<link rel="stylesheet" href="${path}/resources/healthCheck.css" type="text/css">
<script type="text/javascript" src="healthCheck.js"></script>
</head>
<body>
	<div class="divHealthBlock">
		<div class="divHealthHead">网络代理回调错误计数  
			<div style="float:right;margin-right:12px;font-weight:normal;">
				<a href="javascript:void(0)" title="重置回调错误计数"  onclick="resetWLAgentError();" style="font-weight:bold;margin-right:10px;">重置计数</a>
				<a href="javascript:void(0)" onclick="refreshState('CallbackError');" style="font-weight:bold;">刷新</a>
			</div>
		</div>
		<div id="waErrCntContainer" class="divContainer"></div>
		<div style="font-size:9pt;color:red;padding:0px 4px 4px;text-align:right;font-weight:bold;">注：如果回调错误计数增长较快，请及时联系技术人员解决。</div>
	</div>
	<div class="divHealthBlock">
		<div class="divHealthHead">网络代理待回调核销门票数
			<div style="float:right;margin-right:12px;font-weight:normal;">
				<a href="javascript:void(0)" onclick="refreshState('CallbackTicket');" style="font-weight:bold;">刷新</a>
			</div>
		</div>
		<div id="waWaitCallbackCntContainer" class="divContainer"></div>
		<div style="font-size:9pt;color:red;padding:0px 4px 4px;text-align:right;font-weight:bold;">注：如果待回调门票数大于0，但检票时间较新可忽略。</div>
	</div>
	<div class="divHealthBlock">
		<div class="divHealthHead">服务器网络连接及服务运行状态
			<div style="float:right;margin-right:12px;font-weight:normal;">
				<a href="javascript:void(0)" onclick="refreshState('Server');" style="font-weight:bold;">刷新</a>
			</div>
		</div>
		<div id="srvStateContainer" class="divContainer"></div>
	</div>
	<div class="divHealthBlock">
		<div class="divHealthHead">闸机运行状态
			<div style="float:right;margin-right:12px;font-weight:normal;">
				<a href="javascript:void(0)" onclick="refreshState('Gate');" style="font-weight:bold;">刷新</a>
			</div>
		</div>
		<div id="gateStateContainer" class="divContainer"></div>
	</div>
	<div class="divHealthBlock">
		<div class="divHealthHead">自助售票机运行状态
			<div style="float:right;margin-right:12px;font-weight:normal;">
				<a href="javascript:void(0)" onclick="refreshState('SlfSrvMachine');" style="font-weight:bold;">刷新</a>
			</div>
		</div>
		<div id="slfSrvStateContainer" class="divContainer"></div>
	</div>
	
	<div id="waitCallbackTicketDialog" class="easyui-dialog" style="width: 850px; height: 400px;" modal="true" closed="true" buttons="#dlg-buttons">
		<div class="it-datagrid" style="height: 309px;width:848px;">
			<table id="waitCallbackTicketTable"></table>
		</div>
	</div>
	<div id="dlg-buttons" style="text-align: center; display: none;height:30px;">
		<a href="javascript:void(0)" id="close-button" class="gray-btn" style="width: 90px; margin-left: 10px;">关闭</a>
	</div>
</body>
</html>