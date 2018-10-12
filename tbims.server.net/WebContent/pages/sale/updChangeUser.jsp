<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="myel" uri="/elFunction"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"></c:set>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>新增预订单</title>
<%@ include file="/common.jsp"%>
<script type="text/javascript" src="${path}/pages/sale/updChangeUser.js"></script>
</head>
<body>
	<div> 
		<form id="fm" method="post" class="bf-from">
		<input type="hidden" name="detailStr" id="detailStr"> 
		<input type="hidden" name="stat" id="stat"> 
			<table class="form-table" style="width: 90%;">
			  <tr>
					<td class="label">订单编号:</td>
					<td>
						${slTeamOrder.applyId}
						<input type="hidden" name="applyId" id="applyId" value="${slTeamOrder.applyId}"/>
					</td>
				</tr>
			    <tr>
					<td class="label">订单说明:</td>
					<td>
						${slTeamOrder.remark}
					</td>
				</tr>
				<tr>
					<td class="label">入园日期:</td>
					<td>
						<fmt:formatDate  value="${slTeamOrder.inDt}" type="both" pattern="yyyy-MM-dd" />
					</td>
				</tr>
				<tr>
					<td class="label" style="width: 120px;">换票人姓名:</td>
					<td>
						<input name="changeUserId" id="changeUserId" style="width:150px;"  class="easyui-combobox"  data-options="  
                                url: '<%=path%>/teamOrder/listChangeUser.do',   
                                method:'get',  
                                valueField:'changeUserId',  
                                textField:'changeUserName',  
                                multiple:false,  
                                editable:false,  
                                panelHeight:'auto',required:true,value:'${slTeamOrder.changeUserId}'
                        " ></input>
					</td>
				</tr>
			</table>
		</form>
</div>
<div id="dlg-buttons" class="window-btn-bar">
		<a href="javascript:void(0)" id="save-button" class="blue-btn" onclick="javascript:updChangeUser()" style="width: 90px; margin-right: 10px;margin-left:90px;">保存</a>
		<a href="javascript:void(0)" class="gray-btn" onclick="javascript:parent.$('#editDialog').dialog('close');" style="width: 90px; margin-left: 90px;">取消</a>
	</div>
</body>
</html>