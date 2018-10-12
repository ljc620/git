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
<script type="text/javascript" src="orderapply.js"></script>

</head>
<body>
	<div id="mytable-buttons">
	<form id="fm" method="post" class="bf-from">
		<div class="ss-bar">
			<ul class="ss-ul">
				<li>订单编号</li>
				<li>
					<input id="qapplyId" name="qapplyId" class="easyui-textbox" style="width:120px;"></input>
				</li>
				<li>订单状态</li>
				<li>
					<select id="qstat" name="qstat" class="easyui-combobox" style="width:120px;">
					<option value="" selected>--全部--</option>
					<option value="01">已保存</option>
					<option value="02">未审核</option>
					<option value="03">已审核</option>
					</select>
				</li>
				
				<li>预订起止日期</li>
				<li>
					<input id="qapplyTime" name="qapplyBTime" class="easyui-datebox" data-options="editable:false"  style="width:120px;"></input>-
					<input id="qapplyTime" name="qapplyETime" class="easyui-datebox" data-options="editable:false"  style="width:120px;"></input>
				</li>
				<li>
					<a href="javascript:void(0)" onclick="javascript:myQuery();" class="ss-btn">&nbsp;</a>
				</li>
			</ul>
		</div>
		<div class="ss-bar2">
			<ul class="ss-ul">
				<li>
					<a href="javascript:void(0)" class="red-btn icon-add" onclick="javascript:addOrder();">
						<span class="btn-text">新增预订单</span>
					</a>
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
	<div id="detailDialog" class="easyui-dialog" style="width: 800px; height: 500px; padding: 5px" modal="true" closed="true" >
	</div>
	
	<div id="editDialog" class="easyui-dialog" style="width: 700px; height: 500px; padding: 5px" modal="true" closed="true" >
	</div>
</body>
</html>