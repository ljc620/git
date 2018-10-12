<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="myel" uri="/elFunction"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>配送审核管理</title>
<%@ include file="/common.jsp"%>
<script type="text/javascript"
	src="${path}/resources/lodop/lodoopcomm.js"></script>
	<script type="text/javascript"
	src="${path}/resources/lodop/LodopFuncs.js"></script>
<script type="text/javascript" src="teamexamMng.js"></script>
<%@ include file="/pages/print.jsp"%>
</head>
<body>
	<div id="mytable-buttons">
	<form id="fm" method="post" class="bf-from">
		<div class="ss-bar">
			<ul class="ss-ul">
<!-- 				<li style="width: 50px;padding-left:15px;">订单编号</li> -->
<!-- 				<li> -->
<!-- 					<input id="qapplyId" name="applyId" class="easyui-textbox" style="width:160px;"></input> -->
<!-- 				</li> -->
				<li style="width: 50px;padding-left:15px;">订单状态</li>
				<li>
					<select  id="qstat" name="stat"  data-options="editable:false,panelHeight:'auto'" class="easyui-combobox" style="width:120px;">
					<option value="'02'" selected>未审核</option>
					<option value="'03'">已审核</option>
					</select>
				</li>
				<li>预订日期</li>
				<li>
					<input id="qapplyBTime" name="qapplyBTime" class="easyui-datebox" style="width:120px;" data-options="editable:false"  value="<%=com.zhming.support.util.DateUtil.getYestoday() %>"></input>-
					<input id="qapplyETime" name="qapplyETime" class="easyui-datebox" style="width:120px;" data-options="editable:false"  value="<%=com.zhming.support.util.DateUtil.getNowDate("yyyy-MM-dd") %>"></input>
				</li>
			</ul>
			<ul class="ss-ul">
				<li style="width: 50px;padding-left:15px;">入园日期</li>
				<li>
					<input id="inDt" name="inDt" class="easyui-datebox" data-options="editable:false"  style="width:120px;" value="<%=com.zhming.support.util.DateUtil.getNowDate("yyyy-MM-dd") %>"></input>
				</li>
				<li style="width: 50px;">机构名称</li>
				<li>
					<input id="orgId" name="orgId" ></input>
				</li>
				<li >
					<a href="javascript:void(0)" onclick="javascript:myQuery();" class="gray2-btn">&nbsp;查询&nbsp;</a>
				</li>
			</ul>
		</div>
		<div class="ss-bar2">
			<ul class="ss-ul">
				<li>
					<a href="javascript:void(0)" class="red-btn icon-add" onclick="javascript:addOrder();">
						<span class="btn-text">新增预订单</span>
					</a>
					<span id="mark" style="padding-left:200px;font-weight:700;"> &nbsp;</span>&nbsp;&nbsp;<span id="totalNum" style="padding-left:3px;"></span>
				</li>
			</ul>
		</div>
		</form>
	</div>
	<div class="it-datagrid" style="height:100%">
		<table id="mytable"></table>
	</div>
	<div id="addDialog" class="easyui-dialog" style="width: 800px; height: 500px; padding: 5px" modal="true" closed="true" >
	</div>
	<div id="examDialog" class="easyui-dialog" style="width: 700px; height: 600px; padding: 5px" modal="true" closed="true" >
	</div>
	<div id="detailDialog" class="easyui-dialog" style="width: 800px; height: 500px; padding: 5px" modal="true" closed="true" >
	</div>
	
</body>
</html>