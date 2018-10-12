<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="myel" uri="/elFunction"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>配送审核</title>
<%@ include file="/common.jsp"%>
<script type="text/javascript" src="${path}/pages/delivery/deliveryConfirm.js"></script>
<style type="text/css">
.form-table td {
	height: 23px;
}
</style>
</head>
<body style="background-color: #fff;">
	<div id="mytable-buttons">
		<!--  style="margin-left: 10px;" -->
		<form id="fm" method="post" class="bf-from">
			<input type="hidden" id="applyId" name="applyId" value="${deliveryApplyBean.applyId}">
			<table class="form-table" style="width: 90%;">
				<tr>
					<td>
						<table>
							<tr>
								<td width="250px;">申请编号：${deliveryApplyBean.applyId}</td>
								<td width="200px;">
									申请时间：
									<fmt:formatDate value="${deliveryApplyBean.applyTime}" pattern="yyyy-MM-dd HH:mm:ss" />
								</td>
							</tr>
						</table>
					</td>

				</tr>
				<tr>
					<td>
						<table>
							<tr>
								<td width="250px;">申请人：${deliveryApplyBean.applyUserName}</td>
								<td width="200px;">网点类型：${deliveryApplyBean.outletType}</td>
								<td>网点名称：${deliveryApplyBean.outletName}</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
			<!-- 	<input type="hidden" name="detailStr" id="detailStr"> -->
		</form>
		<div style="height: 132px" class="it-datagrid">
			<table id="tickettable"></table>
		</div>
		<table id="mytable-addable" class="form-table" style="width: 90%;">
			<tr>
				<td>
					<table>
						<tr>
							<td style="padding-left: 10px;padding-right: 10px;">
								<input type="radio" name="radio" value="1" checked></input>
							</td>
							<td width="75px">箱号</td>
							<td colspan="6">
								<input id="chestId" name="chestId" class="easyui-textbox" multiline="true" style="width: 450px; height: 50px;" placeholder="请输入箱号，以逗号或回车分割符(例：箱号1,箱号2,......)"></input>
								<span style="color: red; padding-left:5px;">逗号或回车分割符(例：箱号1,箱号2,......)</span>
							</td>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td>
					<table>
						<tr>
							<td style="padding-left: 10px;padding-right: 10px;">
								<input type="radio" name="radio" value="2"></input>
							</td>
							<td width="75px">盒号</td>
							<td colspan="6" style="text-align:left;">
								<input id="boxId" name="boxId" class="easyui-textbox" multiline="true" readonly="true" style="width: 450px; height: 50px;" placeholder="请输入箱号，以逗号或回车分割符(例：箱号1,箱号2,......)"></input>
								<span style="color: red;">&nbsp;逗号或回车分割符(例：盒号1,盒号2,......)</span>
							</td>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td>
					<table>
						<tr>
							<td style="padding-left: 10px;padding-right: 10px;">
								<input type="radio" name="radio" value="3"></input>
							</td>
							<td width="75px">票号起号</td>
							<td style="text-align:left;">
								<input id="beginNo" name="beginNo" class="easyui-numberbox" min="0" max="9999999999999999" readonly="true" precision="0" style="width: 120px;"></input>
							</td>
							<td style="padding-left: 50px;padding-right: 10px;">票号止号</td>
							<td style="padding-left: 7px;"style="text-align:left;">
								<input id="endNo" name="endNo" class="easyui-numberbox" min="0" max="9999999999999999" readonly="true" precision="0" style="width: 120px;"></input>
							</td>
							<td style="padding-left: 100px;">
								<a href="javascript:void(0)" id="save-button" class="blue-btn" onclick="javascript:addChest()" style="width: 60px; margin-right: 10px; margin-left: 10px;">添加</a>
							</td>
							<td style="padding-left: 7px;">
								<a href="javascript:void(0)" id="save-button" class="blue-btn" onclick="javascript:myconfirm()" style="width: 60px; margin-right: 10px; margin-left: 10px;">确定配送</a>
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
	</div>
	<div class="it-datagrid" style="height: 100%;">
		<table id="deliverytable"></table>
	</div>
</body>
</html>