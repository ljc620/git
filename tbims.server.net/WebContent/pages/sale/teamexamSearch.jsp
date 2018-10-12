<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="myel" uri="/elFunction"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>团队订单管理</title>
<%@ include file="/common.jsp"%>
<script type="text/javascript"
	src="${path}/resources/lodop/lodoopcomm.js"></script>
	<script type="text/javascript"
	src="${path}/resources/lodop/LodopFuncs.js"></script>
<script type="text/javascript" src="teamexamSearch.js"></script>
<%@ include file="/pages/print.jsp"%>
</head>
<body>
	<div id="mytable-buttons">
	<form id="fm" method="post" class="bf-from">
		<div class="ss-bar">
			<ul class="ss-ul">
				<li style="width: 50px;padding-left:15px;">订单编号</li>
				<li>
					<input id="qapplyId" name="applyId" class="easyui-textbox" style="width:160px;"></input>
				</li>
				<li style="width: 50px;padding-left:15px;">订单状态</li>
				<li>
					<select  id="qstat" name="stat" editable="false"  class="easyui-combobox" style="width:120px;">
					<option value="" selected>--全部--</option>
					<option value="04">已换票</option>
					<option value="05">已拒绝</option>
					<option value="06">已流单</option>
					<option value="07">已取消</option>
					</select>
				</li>
				
				<li>预订起止日期</li>
				<li>
					<input id="qapplyBTime" name="qapplyBTime" class="easyui-datebox" style="width:120px;" value="<%=com.zhming.support.util.DateUtil.getNowDate("yyyy-MM-dd") %>"></input>-
					<input id="qapplyETime" name="qapplyETime" class="easyui-datebox" style="width:120px;" value="<%=com.zhming.support.util.DateUtil.getNowDate("yyyy-MM-dd") %>"></input>
				</li>
				<li>
					<a href="javascript:void(0)" onclick="javascript:myQuery();" class="ss-btn">&nbsp;</a>
				</li>
			</ul>
		</div>
		</form>
	</div>
	<div class="it-datagrid" style="height:100%">
		<table id="mytable"></table>
	</div>
	<div id="examDialog" class="easyui-dialog" style="width: 700px; height: 600px; padding: 5px" modal="true" closed="true" >
	</div>
	<div id="detailDialog" class="easyui-dialog" style="width: 800px; height: 500px; padding: 5px" modal="true" closed="true" >
	</div>
	
</body>
</html>