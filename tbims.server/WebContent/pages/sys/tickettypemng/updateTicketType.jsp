<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="myel" uri="/elFunction"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"></c:set>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>修改票种</title>
<%@ include file="/common.jsp"%>
<script type="text/javascript" src="ticketTypeMng.js"></script> 
</head>
<body>
	<div > 
	<p class="item-title" style="height:24px;line-height:24px;margin-top:10px">基本信息</p>
		<form id="fm" method="post" class="bf-from">
			<table class="form-table" style="width: 90%;">
			    <tr>
			        <td class="label">票种编号</td>
					<td>
						<span style="padding-left:5px">${sysTicketType.ticketTypeId}</span>
						<input type="hidden" name="ticketTypeId" id="ticketTypeId" value="${sysTicketType.ticketTypeId}" validType="length[2,3]"  />
					</td>
					<td class="label">票种名称</td>
					<td>
						<input name="ticketTypeName" id="ticketTypeName" value="${sysTicketType.ticketTypeName}" validType="length[2,150]"  class="easyui-textbox" required="true"></input>
						<span class="starStyle">*</span>
					</td>
					<td class="label">次数</td>
					<td>
						<input name="validateTimes" id="validateTimes" value="${sysTicketType.validateTimes}" validType="length[1,2]" class="easyui-numberbox"  required="true"></input>
						<span class="starStyle">*</span>
					</td>
					
				</tr>
				<tr>
					<td class="label">销售日当日有效</td>
					<td >
					    <select name="dayValidateFlag" id="dayValidateFlag" value="${sysTicketType.dayValidateFlag}" class="easyui-combobox" style="width:120px;"  required="true">
					    <option value="Y"  <c:if  test="${'Y'  eq  sysTicketType.dayValidateFlag}"> selected="selected"</c:if> >是</option>
						<option value="N" <c:if  test="${'N'  eq  sysTicketType.dayValidateFlag}"> selected="selected"</c:if>>否</option>
						</select>
						<span class="starStyle">*</span>
					</td>
					<td class="label">票价</td>
					<td >
						<input name="price" id="price" value="${sysTicketType.price}" class="easyui-numberbox" min="0" max="9999" precision="0" required="true">
						<span class="starStyle">*</span>
					</td>
					<td class="label">是否团体</td>
					<td >
					   <select name="teamFlag" id="teamFlag"  class="easyui-combobox" style="width:120px;"  required="true">
					   <option value="Y"  <c:if  test="${'Y'  eq  sysTicketType.teamFlag}"> selected="selected"</c:if> >是</option>
						<option value="N" <c:if  test="${'N'  eq  sysTicketType.teamFlag}"> selected="selected"</c:if>>否</option>
						</select>
						<span class="starStyle">*</span>
					</td>
				</tr>
				<tr>
					<td class="label">是否优惠</td>
					<td>
						<select name="lessFlag" id="lessFlag"  class="easyui-combobox" style="width:120px;" required="true">
						<option value="Y"  <c:if  test="${'Y'  eq  sysTicketType.lessFlag}"> selected="selected"</c:if> >是</option>
						<option value="N" <c:if  test="${'N'  eq  sysTicketType.lessFlag}"> selected="selected"</c:if>>否</option>
						</select>
						<span class="starStyle">*</span>
					</td>
					<td class="label">日夜场</td>
					<td>
						<select name="dayNightFlag" id="dayNightFlag"  class="easyui-combobox" style="width:120px;" required="true">
						<option value="D"  <c:if  test="${'D'  eq  sysTicketType.dayNightFlag}"> selected="selected"</c:if> >日</option>
						<option value="N" <c:if  test="${'N'  eq  sysTicketType.dayNightFlag}"> selected="selected"</c:if>>夜</option>
						</select>
						<span class="starStyle">*</span>
					</td>
					<td class="label" >场馆</td>
					<td>
						<input class="easyui-combobox"  id="venueIds" name="venueIds"  style="width:120px;" data-options="  
                                url: '<%=path%>/ticketTypeMng/venueList.do',   
                                method:'get',  
                                valueField:'venueId',  
                                textField:'venueName',  
                                multiple:true,  
                                editable:false,  
                                panelHeight:'auto',required:true ,value: '${venueIds}'
                        " />
                        <span class="starStyle">*</span>
					</td>
					
				</tr>
				
			</table>
		</form>
</div>
	<div id="dlg-buttons" class="window-btn-bar">
		<a href="javascript:void(0)" id="save-button" class="blue-btn" onclick="javascript:saveUpdTicketType()" style="width: 90px; margin-right: 10px;margin-left:90px;">修改</a>
		<a href="javascript:void(0)" class="gray-btn" onclick="javascript:$('#updateTicketTypeDialog').dialog('close');" style="width: 90px; margin-left: 10px;">取消</a>
	</div>
</body>
</html>