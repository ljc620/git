<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@taglib prefix="myel" uri="/elFunction"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>实体代理商销售统计</title>
<%@ include file="/common.jsp"%>
<script type="text/javascript" src="rptTeamStQuery.js"></script>
</head>
<body>
	
	<div id="mytable-buttons">
		<div class="ss-bar">
			<form id="form">
				<ul class="ss-ul">
					<li style="width: 50px;padding-left:15px;">销售日期</li>
					<li>
					<input id="saleStartTime" name="saleStartTime" class="easyui-datebox"  data-options="editable:false"  style="width:120px;" value="<%=com.zhming.support.util.DateUtil.getNowDate("yyyy-MM-dd") %>"></input>-
					<input id="saleEndTime" name="saleEndTime" class="easyui-datebox"  data-options="editable:false"  style="width:120px;" value="<%=com.zhming.support.util.DateUtil.getNowDate("yyyy-MM-dd") %>"></input>
					</li>
					<li style="width: 50px;padding-left:15px;">票种名称</li>
					<li>
						<input id="ticketTypeId" name="ticketTypeId" class="easyui-combobox" style="width:120px;"></input>
					</li>
					<li style="width: 50px;">机构名称</li>
					<li>
						<input id="orgId" name="orgId"></input>
					</li>
					<li>
						<a href="javascript:void(0)" onclick="javascript:query();" class="gray2-btn" style="width:35px;">查询</a>
					</li>
					<li><span class="col"><a class="indigo-btn" style="margin-left:10px;" id="btnExcel" onclick="expExcel()">导出EXCEL</a></span></li>
				</ul>
			</form>
		</div>
	</div>
	<div class="it-datagrid" style="height:100%">
		<table id="mytable"></table>
	</div>
</body>
</html>