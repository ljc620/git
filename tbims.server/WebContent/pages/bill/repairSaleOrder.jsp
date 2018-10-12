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
<script type="text/javascript" src="repairSaleOrder.js"></script>
</head>
<body class="easyui-layout">
	<form id="fm" method="post" class="bf-from">
		<div data-options="region:'west',collapsible:false" style="width: 560px; height: 100%; overflow: auto;" title="订单信息">
			<table class="form-table" style="width: 90%;">
				<tr>
					<td class="label">销售日期</td>
					<td>
						<input name="saleDate" id="saleDate" class="easyui-datebox" data-options="editable:false" value="<%=com.zhming.support.util.DateUtil.getNowDate("yyyy-MM-dd")%>" style="width: 250px;"
							required="true" />
					</td>
				</tr>
				<tr>
					<td colspan="2" style="color: red;">
					&nbsp;&nbsp;1.销售日期为当天，补单保存成功以后，不需要再做其它处理，即补单成功 <br/>
					&nbsp;&nbsp;2.销售日期不是当天，补单保存成功以后还需要做以下操作处理，即补单成功：<br/>
					&nbsp;&nbsp;&nbsp;--修改销售日期与当天的前一天之间的历史库存，都要依次扣减库存 <br/>
					&nbsp;&nbsp;&nbsp;--执行begin p_outlet_sale_d('2017-10-07') end; 重新执行这个存储过程<br/>
					</td>
				</tr>
				<tr>
					<td class="label">订单类型</td>
					<td>
						<select name="orderType" id="orderType" class="easyui-combobox" data-options="editable:false,panelHeight:'auto'" style="width: 250px;" required="true">
							<option value="XC">现场售票</option>
							<option value="WL">网络人工换票</option>
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
					<td class="label">售票人</td>
					<td>
						<input id="saleUserId" name="saleUserId" class="easyui-combobox" style="width: 250px;" required="true"></input>
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
				<tr id="orgIdTR">
					<td class="label">机构号</td>
					<td>
						<input id="orgId" name="orgId" class="easyui-textbox" style="width: 250px;" required="true"></input>
					</td>
				</tr>
				<tr id="thirdOrderNumTR">
					<td class="label">第三方单号</td>
					<td>
						<input id="thirdOrderNum" name="thirdOrderNum" class="easyui-textbox" style="width: 250px;" required="true"></input>
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
						<a href="javascript:void(0)" class="blue-btn" onclick="javascript:validateTicketIds()" style="width: 200px;">校验门票数量、票号是否正确</a>
					</td>
				</tr>
				<tr>
					<td>
						<span style="color: red;">1. 请在文本框中输入订单中的票号</span><br /> <span style="color: red;">2. 票号输入完毕后按回车键输入下一个票号</span><br /> <span style="color: red;">3. 检验票号时票号有误可以继续保存</span><br />
					</td>
				</tr>
				<tr>
					<td>
						<input id="ticketIds" name="ticketIds" class="easyui-textbox" data-options="multiline:true" style="width: 250px; height: 400px;"></input>
					</td>
				</tr>
			</table>
		</div>
	</form>
</body>
</html>