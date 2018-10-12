<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="myel" uri="/elFunction"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sys" tagdir="/WEB-INF/tags/sys"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>网络代理商换票统计报表</title>
<%@ include file="/common.jsp"%>
<link rel="stylesheet" href="${path}/resources/ztree_src/css/zTreeStyle/zTreeStyle.css" type="text/css"></link>
<script type="text/javascript" src="${path}/resources/ztree/js/jquery.ztree.core.min.js"></script>
<script type="text/javascript" src="${path}/resources/ztree/js/jquery.ztree.excheck.min.js"></script>
<script type="text/javascript" src="rptTeamWlQuery.js"></script>
</head>
<body>

	<div id="mytable-buttons">
		<div class="ss-bar">
			<form id="form">
				<ul class="ss-ul">
					<li style="width: 50px; padding-left: 10px;">销售日期</li>
					<li>
						<input id="opeTime" name="opeTime" class="easyui-datebox" data-options="editable:false" style="width: 120px;" value="true" />-
						<input id="opeTime2" name="opeTime2" class="easyui-datebox" data-options="editable:false" style="width: 120px;" value="true" />
					</li>
					<li style="width: 50px; padding-left: 8px;">网点名称</li>
					<li>
						<!-- 						<input id="outletId" name="outletId" class="easyui-combobox" style="width:150px;"></input>-->
						<sys:noptreeselect id="outletId" name="outletId" options="width:160,panelWidth:200" idField="outletId" textField="outletName" url="comm/listOutletTree.do" pidField="outletType"
							isParentLevel="false" />
					</li>
					<li style="width: 50px; padding-left: 8px;">票种名称</li>
					<li>
						<input id="ticketTypeId" name="ticketTypeId" class="easyui-combobox" style="width: 180px;"></input>
					</li>
				</ul>
				<ul class="ss-ul">
					<li style="width: 50px; padding-left: 10px;">机构名称</li>
					<li>
						<input id="orgId" name="orgId"></input>
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