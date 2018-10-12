<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="myel" uri="/elFunction"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>用户管理</title>
<%@ include file="/common.jsp"%>
<script type="text/javascript" src="noEjectList.js"></script>
</head>
<body>
	<div id="mytable-buttons">
		<div class="ss-bar">
			<ul class="ss-ul">
				<li style="width: 50px; padding-left: 15px;">销售日期</li>
				<li>
					<input id="saleTimeBegin" name="saleTimeBegin" class="easyui-datebox"  data-options="editable:false"  style="width: 110px;" value="true" />
					-
					<input id="saleTimeEnd" name="saleTimeEnd" class="easyui-datebox"  data-options="editable:false"  style="width: 110px;" value="true" />
				</li>
				<li>
					<a href="javascript:void(0)" onclick="javascript:query();" class="gray2-btn" style="width: 35px;">查询</a>
				</li>
			</ul>
		</div>
		<div class="ss-bar">
			<ul class="ss-ul">
				<li style="color: red;">* 发起退款：只能对未取票的票据金额进行一次性退款</li>
			</ul>
		</div>
	</div>
	<div class="it-datagrid" style="height: 100%">
		<table id="mytable"></table>
	</div>
</body>
</html>