<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="myel" uri="/elFunction"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>阶梯票价管理</title>
<%@ include file="/common.jsp"%>
<script type="text/javascript" src="${path}/pages/sys/tickettypemng/ticTypePriceMng.js"></script>
<style type="text/css">
.new-btn{
	display: block;
	height: 28px;
	width:auto;
	background-repeat: no-repeat;
	-moz-border-radius: 2px 2px 2px 2px;
	-webkit-border-radius: 2px 2px 2px 2px;
	border-radius: 2px 2px 2px 2px;
	cursor: pointer;
	color: #fff;
	font-size: 12px;
	text-align: center;
	padding: 0 15px;
	line-height:28px;
	background-color: #3099da;
}
.new-btn:hover {
	background-color: #188fd9;
}
</style>
</head>
<body>
	<div id="mytable-buttons">
	<form id="fm" method="post" class="bf-from">
		<div class="ss-bar">
			<ul class="ss-ul">
				<li>票种编号：${ticPrice.ticketTypeId}
				 <input type="hidden" id="ticketTypeId" name="ticketTypeId"
						value="${ticPrice.ticketTypeId}" />
				</li>
				<li>票种名称：${ticPrice.ticketTypeName}
				</li>

			</ul>
		</div>
		<p class="item-title" style="height: 20px; line-height: 20px; margin-top: 2px">阶梯票价信息</p>
		<div class="ss-bar2" >
			<ul class="ss-ul">
				<li>开始张数（含）</li>
				<li>
					<input name="startNo" id="startNo" style="width:120px;" class="easyui-numberbox" data-options="prompt:'请输入数字',required:'true'" value="${endNo}"></input>
				</li>
				<li>结束张数（含）</li>
				<li>
					<input name="endNo" id="endNo" style="width:120px;"  class="easyui-numberbox" data-options="prompt:'请输入数字',required:'true'" ></input>
				</li>
				<li>票价</li>
				<li>
					<input name="price" id="price"  style="width:120px;" class="easyui-numberbox" data-options="prompt:'请输入数字',min:'0'	,max:'9999',precision:'0',required:'true'" ></input>
				</li>
				<li>
					<a href="javascript:void(0)" id="save-button" class="new-btn" onclick="javascript:addticTypePrice()" style="width: 50px;margin-left:20px;">添加</a>
				</li>
				<li>
					<a href="javascript:void(0)" id="save-button" class="new-btn" onclick="javascript:checkContinue()" style="width: 50px;margin-left:30px;">连续校验</a>
				</li>
			</ul>
		</div>
		</form>
	</div>
	<div class="it-datagrid" style="height:60%">
		<table id="pricetable"></table>
	</div>
	<p class="item-title" style="height: 20px; line-height: 20px; margin-top: 10px">阶梯票价历史信息</p>
	<div class="it-datagrid" style="height:35%">
		<table id="priceHtable"></table>
	</div>
</body>
</html>