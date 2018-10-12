<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="myel" uri="/elFunction"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"></c:set>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>新增预订单</title>
<%@ include file="/common.jsp"%>
<script type="text/javascript" src="addOrder.js"></script> 
</head>
<body>
	<div> 
		<form id="fm" method="post" class="bf-from">
		<input type="hidden" name="detailStr" id="detailStr"> 
		<input type="hidden" name="stat" id="stat"> 
			<table class="form-table" style="width: 780px;">
			    <tr>
					<td class="label" style="width:150px;">订单说明</td>
					<td colspan="3">
						<input name="remark" id="remark" validType="length[2,60]"  class="easyui-textbox" style="width:300px;" required="true"></input>
						<span class="starStyle">*</span>
					</td>
				</tr>
				<tr>
					<td class="label" style="width:150px;">入园日期</td>
					<td colspan="3">
						<input name="inDt" id="inDt" class="easyui-datebox" style="width:150px;"  editable="false" required="true"></input>
						<span class="starStyle">*</span>
					</td>
				</tr>
				<tr>
					<td class="label" style="width: 120px;">换票人姓名</td>
					<td>
						<input name="changeUserId" id="changeUserId" style="width:150px;"  class="easyui-combobox"  data-options="  
                                url: '<%=path%>/teamOrder/listChangeUser.do',   
                                method:'get',  
                                valueField:'changeUserId',  
                                textField:'changeUserName',  
                                multiple:false,  
                                editable:false,  
                                panelHeight:'auto',required:true  
                        " ></input>
                        <span class="starStyle">*</span>
					</td>
				</tr>
			</table>
			<table id ="mytable-buttons" class="form-table" style="width: 780px;">
			<tr>
					<td class="label" style="width: 150px;" >票种</td>
					<td style="width:170px;">
						<input name="ticketTypeId" id="ticketTypeId" class="easyui-combobox" style="width:150px;"  data-options="  
                                url: '<%=path%>/teamOrder/listTicketType.do',   
                                method:'get',  
                                valueField:'ticketTypeId',  
                                textField:'ticketTypeName',  
                                multiple:false,  
                                editable:false,  
                                panelHeight:'auto',required:true  
                        " ></input>
                        <span class="starStyle">*</span>
					</td>
					<td class="label" style="width:80px;">数量</td>
					<td style="width:150px;">
						<input name="applyNum" id="applyNum" class="easyui-numberbox" style="width:120px;"  precision="0" required="true"></input>
						<span class="starStyle">*</span>
					</td>
					<td><a href="javascript:void(0)" id="save-button" class="blue-btn" onclick="javascript:addMyRow()" style="width: 50px; margin-right: 10px;margin-left:90px;">添加</a></td>
			</tr>
			</table>
			<div class="it-datagrid" style="height:200px">
		<table id="detailtable" class="easyui-datagrid">
		</table>
	</div>
		</form>
</div>
<div id="dlg-buttons" class="window-btn-bar">
		<a href="javascript:void(0)" id="save-button" class="blue-btn" onclick="javascript:saveOrder('01')" style="width: 90px; margin-right: 10px;margin-left:90px;">临时保存</a>
		<a href="javascript:void(0)" id="save-button" class="blue-btn" onclick="javascript:saveOrder('02')" style="width: 90px; margin-right: 10px;margin-left:90px;">提交</a>
		<a href="javascript:void(0)" class="gray-btn" onclick="javascript:parent.$('#addDialog').dialog('close');" style="width: 90px; margin-left: 90px;">取消</a>
	</div>
</body>
</html>