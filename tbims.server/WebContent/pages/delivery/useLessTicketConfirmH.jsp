<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="myel" uri="/elFunction"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="sys" tagdir="/WEB-INF/tags/sys"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>废票交回确认</title>
<%@ include file="/common.jsp"%>
<script type="text/javascript" src="${path}/pages/delivery/useLessTicketConfirmH.js"></script>
<script type="text/javascript" src="${path}/js/common/commcheck.js"></script>
<script type="text/javascript" src="${path}/js/main.js"></script>
<link rel="stylesheet" href="${path}/resources/ztree_src/css/zTreeStyle/zTreeStyle.css" type="text/css"></link>
<script type="text/javascript" src="${path}/resources/ztree/js/jquery.ztree.core.min.js"></script>
<script type="text/javascript" src="${path}/resources/ztree/js/jquery.ztree.excheck.min.js"></script>
</head>
<body>
	<div id="mytable-buttons">
		<form id="form">
			<div class="ss-bar">
				<ul class="ss-ul">
					<li style="width: 50px; padding-left: 15px;">作废日期</li>
					<li>
						<input id="startDate" name="startDate" class="easyui-datebox" data-options="editable:false" style="width: 180px;" value="true"></input>
						-
						<input id="endDate" name="endDate" class="easyui-datebox" data-options="editable:false" style="width: 180px;" value="true" validType="checkDt['#startDate']"></input>
					</li>
					<li style="width: 50px; padding-left: 15px;">网点名称</li>
					<li>
						<!-- 					<input id="outletId" name="outletId" class="easyui-combobox" style="width:200px;"></input>-->
						<sys:noptreeselect id="outletId" name="outletId" options="width:200,panelWidth:200" idField="outletId" textField="outletName" url="comm/listOutletTree.do" pidField="outletType"
							isParentLevel="false" />
					</li>
					<!-- <li style="width: 50px;padding-left:15px;">是否确认</li>
				<li>
					<input id="stat" name="stat" class="easyui-combobox" style="width:100px;"></input>
				</li> -->
					<li>
						<a href="javascript:void(0)" onclick="javascript:myQuery();" class="gray2-btn">&nbsp;查询&nbsp;</a>
					</li>
					<li>
						<span style="padding-left: 50px; font-weight: 700;"> 合计:</span><span id="totalNum" style="padding-left: 3px;"></span>
					</li>
				</ul>
			</div>
		</form>
	</div>
	<div class="it-datagrid" style="height: 100%">
		<table id="mytable"></table>
	</div>
</body>
</html>