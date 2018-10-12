<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="myel" uri="/elFunction"%>
<%@ taglib prefix="sys" tagdir="/WEB-INF/tags/sys"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"></c:set>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>人工补录订单</title>
<%@ include file="/common.jsp"%>
<script type="text/javascript" src="${path}/js/common/commcheck.js"></script>
<script type="text/javascript" src="repairSaleOrderByID.js"></script>
</head>
<body class="easyui-layout">
	<form id="fm" method="post" class="bf-from">
		<div data-options="region:'west',collapsible:false" style="width: 560px; height: 100%; overflow: auto;" title="订单信息">
			<table class="form-table" style="width: 90%;">
				<tr>
					<td class="label">销售日期</td>
					<td>
						<input name="saleDate" id="saleDate" class="easyui-datebox" data-options="editable:false" value="<%=com.zhming.support.util.DateUtil.getNowDate("yyyy-MM-dd")%>" style="width: 250px;"
							required="true" readonly="true" />
					</td>
				</tr>
				<tr>
					<td class="label">订单类型</td>
					<td>
						<select name="orderType" id="orderType" class="easyui-combobox" data-options="editable:false,panelHeight:'auto'" style="width: 250px;" required="true">
							<option value="ZY">自营</option>
						</select>
					</td>
				</tr>
				<tr>
					<td class="label">票种名称</td>
					<td>
						<input id="ticketTypeId" name="ticketTypeId" style="width: 250px;" required="true"></input>
					</td>
				</tr>
				<tr>
					<td class="label">售票网点</td>
					<td>
						<input id="outletId" name="outletId" style="width: 250px;" required="true"></input>
					</td>
				</tr>
				<tr>
					<td class="label">售票窗口</td>
					<td>
						<input id="clientId" name="clientId" class="easyui-combobox" style="width: 250px;" required="true"></input>
					</td>
				</tr>
				<tr>
					<td class="label">支付类型</td>
					<td>
						<select name="payType" id="payType" class="easyui-combobox" data-options="editable:false,valueField: 'label',textField: 'value'" style="width: 250px;" required="true">
						</select>
					</td>
				</tr>
				<tr>
					<td class="label">门票数量</td>
					<td>
						<input id="ticketCount" name="ticketCount" class="easyui-numberbox" style="width: 250px;" required="true"></input>
					</td>
				</tr>
				<tr>
					<td class="label">单价</td>
					<td>
						<input id="price" name="price" class="easyui-numberbox" style="width: 250px;" required="true"></input>
					</td>
				</tr>
				<tr>
					<td class="label">门票开始日期  </td>
					<td>
						<input id="validStartDate" name="validStartDate" class="easyui-datebox" data-options="editable:false" value="<%=com.zhming.support.util.DateUtil.getNowDate("yyyy-MM-dd")%>" ></input>
					</td>
				</tr>
				<tr>
					<td class="label">门票结束日期 </td>
					<td>
						<input id="validEndDate" name="validEndDate" class="easyui-datebox" data-options="editable:false" value="<%=com.zhming.support.util.DateUtil.getDate("yyyy-MM-dd",30)%>" ></input>
					</td>
				</tr>
			</table>

			<div id="dlg-buttons" class="window-btn-bar">
				<a href="javascript:void(0)" id="save-button" class="blue-btn" onclick="javascript:saveRepairSale()" style="width: 90px; margin-right: 10px; margin-left: 90px;">保存</a>
			</div>
		</div>
		<div data-options="region:'center'" title="票号">
			<table>
				<tr>
					<td>
						<span style="color: red;">1. 请在第一个文本框中输入订单中的身份证号</span><br /> <span style="color: red;">2. 身份证号输入完毕后按回车键输入下一个身份证号</span><br /> <span style="color: red;">
					</td>
					<td>&nbsp;&nbsp;</td>
					<td align="center">
						<span id="errorSpec" style="color: red;font-size: larger;"></span>
					</td>
				</tr>
				<tr>
					<td>
						<input id="ticketIds" name="ticketIds" class="easyui-textbox" data-options="multiline:true" style="width: 250px; height: 400px;"></input>
					</td>
					<td>&nbsp;&nbsp;</td>
					<td valign="top">
						<input id="errorIds" name="errorIds" class="easyui-textbox" data-options="multiline:true"  readonly="false" style="width: 250px; height: 400px;"></input>
					</td>
				</tr>
			</table>
		</div>
	</form>
</body>
</html>