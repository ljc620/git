<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="myel" uri="/elFunction"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>机构管理</title>
<%@ include file="/common.jsp"%>
<script type="text/javascript" src="orgMng.js"></script>
<script type="text/javascript" src="${path}/js/common/commcheck.js"></script>
</head>
<body>
	<div id="mytable-buttons">
		<div class="ss-bar">
			<ul class="ss-ul">
				<li style="width: 50px;padding-left:15px;">机构名称</li>
				<li>
					<input id="qorgName" name="orgName" class="easyui-textbox" style="width:160px;"></input>
				</li>
				<li>
					<a href="javascript:void(0)" onclick="javascript:myQuery();" class="gray2-btn">&nbsp;查询&nbsp;</a>
				</li>
			</ul>
		</div>
	</div>
	<div class="it-datagrid" style="height:100%">
		<table id="mytable"></table>
	</div>
	<div id="orgDialog" class="easyui-dialog" style="width: 550px; height: 370px; padding: 5px 10px" modal="true" closed="true" buttons="#dlg-buttons">
		<table class="form-table" style="width: 90%;">
			<tr>
				<td class="label" style="width: 150px;height:40px;">组织机构代码：</td>
				<td ><span id="orgId"></span></td>
			</tr>
			<tr>
				<td class="label" style="width: 150px;height:40px;">机构名称：</td>
				<td ><span id="orgName"></span></td>
			</tr>
			<tr>
				<td class="label" style="width: 150px;height:40px;">核销回调地址：</td>
				<td ><input name="callbackUrl" id="callbackUrl"
					class="easyui-textbox" style="width: 300px;"></input>
				</td>
			</tr>
			<tr>
				<td class="label" style="width: 150px;height:40px;">核销回调数据格式：</td>
				<td ><input name="callbackData" id="callbackData"
					class="easyui-textbox" style="width: 300px;"></input>
				</td>
			</tr>
		</table>
		<div style="margin-top:10px;">说明：<br/>
		<ul><li style="list-style-type:disc;padding-left:20px;">
		1、机构ID、订单ID、身份证号、检票时间、签名分别用#orgid、#orderid#、#identity#、#checktime#、#sign#替换；</li>
		<li style="list-style-type:disc;padding-left:20px;">2、默认格式为：{"orgId":"#orgid#","orderId":"#orderid#","identityCode":"#identity#",<br/>
		"checkTime":"#checktime#","sign":"#sign#"}。</li></ul>
		</div>
	</div>
	<div id="saleTicketDialog" class="easyui-dialog" style="width: 550px; height: 400px;" modal="true" closed="true" buttons="#dlg-buttons">
		<div class="it-datagrid" style="height: 309px;width:548px;">
			<table id="saleTicketTable"></table>
		</div>
	</div>
	<div id="dlg-buttons" style="text-align: center; display: none;height:30px;">
		<a href="javascript:void(0)" id="update-button" class="blue-btn" style="width: 90px; margin-right: 10px;">修改</a>
		<a href="javascript:void(0)" id="close-button" class="gray-btn" style="width: 90px; margin-left: 10px;">取消</a>
	</div>
</body>
</html>