<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="myel" uri="/elFunction"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>团队票订单审核</title>
<%@ include file="/common.jsp"%>
<script type="text/javascript" src="${path}/pages/sale/teamexam/teamexam.js"></script>

</head>
<body>
	<div id="mytable-buttons">
	<form id="fm" method="post" class="bf-from">
		<input type="hidden" name="applyDetailStr" id="applyDetailStr">
		<input type="hidden" name="stat" id="stat">
		<input type="hidden" name="applyId" id="applyId" value="${slTeamOrder.applyId}">
		<table class="form-table" style="width: 90%;">
		    <tr><td colspan="4">签约社名称：${slTeamOrder.orgName}
				</td></tr>
			<tr>
				<td colspan="2">预订单号：${slTeamOrder.applyId}
				</td>
				<td>预订日期：<fmt:formatDate  value="${slTeamOrder.applyTime}" type="both" pattern="yyyy-MM-dd" />
				</td>
				
				<td>入园日期：<fmt:formatDate  value="${slTeamOrder.inDt}" type="both" pattern="yyyy-MM-dd" />
				</td>
			</tr>
			<tr>
				<td>换票人姓名：${changeUserBean.changeUserName}
				</td>
				<td>换票人手机号：${changeUserBean.tel}
				</td>
				<td>证件类型：${changeUserBean.cardTypeName}
				</td>
				<td>证件号码：${changeUserBean.cardId}
				</td>
			</tr>
		</table>
		</form>
	</div>
	<div class="it-datagrid" style="height:300px;padding-top:3px">
		<table id="tickettable"></table>
	</div>
	<div style="font-size:12px;color:red;padding:5px">点击批准数量可编辑</div>
	<div id="dlg-buttons" class="window-btn-bar" >
		<a href="javascript:void(0)" id="save-button" class="blue-btn" onclick="javascript:myOrderExam('03')" style="width: 90px; margin-right: 10px;margin-left:90px;">同意</a>
		<a href="javascript:void(0)" class="gray-btn" onclick="javascript:myOrderExam('05')" style="width: 90px; margin-left: 10px;">拒绝</a>
	</div>
</body>
</html>