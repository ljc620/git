<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sys" tagdir="/WEB-INF/tags/sys"%>
<%@ taglib prefix="myel" uri="/elFunction"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"></c:set>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>配送申请</title>
<%@ include file="/common.jsp"%>
<script type="text/javascript" src="${path}/pages/delivery/deliveryApply.js"></script>
</head>
<body>
	<div>
		<form id="fm" method="post" class="bf-from">
			<input type="hidden" name="detailStr" id="detailStr">
			<table class="form-table" style="width: 580px;">
				<tr>
					<td class="label" style="width: 120px;">票种</td>
					<td colspan="3">
						<input name="ticketTypeId" id="ticketTypeId" class="easyui-combobox" data-options="  
                                url: '<%=path%>/saleReg/listTicketTypeByST.do',   
                                method:'get',  
                                valueField:'ticketTypeId',  
                                textField:'ticketTypeName',  
                                multiple:false,  
                                editable:false,  
                                required:true,
                                width:'160px'  
                        "></input>
						<span class="starStyle">*</span>
					</td>
				</tr>
				<tr>
					<td class="label" style="width: 120px;">门票张数</td>
					<td colspan="3">
						<input name="applyNum" id="applyNum" class="easyui-numberbox" data-options="min:1,precision:'0',required:'true'" style="width: 100px;"></input>
						<span class="starStyle">*</span>
					</td>
				</tr>
				<tr>
					<td class="label" style="width: 150px;">申请配送时间</td>
					<td colspan="3">
						<input name="applyDeliveryTime" id="applyDeliveryTime" class="easyui-datetimebox" style="width: 180px;" required="true" value='${deliveryTime}'></input>
						<span class="starStyle">*</span>
					</td>
					<td>
						<a href="javascript:void(0)" id="save-button" class="blue-btn" onclick="javascript:addApply()" style="width: 60px; margin-right: 10px; margin-left: 60px;">添加</a>
					</td>
				</tr>
			</table>
			<div class="it-datagrid" style="height: 200px">
				<table id="applytable" class="easyui-datagrid">
				</table>
			</div>
		</form>
	</div>
	<div id="dlg-buttons" class="window-btn-bar" style="text-align: center; padding-top: 10px;">
		<a href="javascript:void(0)" id="save-button" class="blue-btn" onclick="javascript:saveApply()" style="width: 90px; margin-right: 50px;">提交</a>
		<a href="javascript:void(0)" class="gray-btn" onclick="javascript:parent.$('#applyDialog').dialog('close');" style="width: 90px; margin-right: 50px;">取消</a>
	</div>
</body>
</html>