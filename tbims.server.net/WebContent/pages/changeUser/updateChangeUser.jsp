<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="myel" uri="/elFunction"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"></c:set>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>修改换票人</title>
<%@ include file="/common.jsp"%>
<script type="text/javascript" src="changeUserMng.js"></script> 
</head>
<body>
	<div> 
		<form id="fm" method="post" class="bf-from">
			<input type="hidden" name="changeUserId" id="changeUserId" value="${slChangeUser.changeUserId}"/>
			<input type="hidden" name="orgId" id="orgId" value="${slChangeUser.orgId}"/>
			<table class="form-table" style="width: 90%;">
			<tr>
					<td class="label"style="width:150px;">换票人名称</td>
					<td >
						<input name="changeUserName" id="changeUserName" value="${slChangeUser.changeUserName}" class="easyui-textbox" style="width:220px;" required="true"></input>
						<span class="starStyle">*</span>
					</td>
			</tr>
			<tr>
					<td class="label"style="width:150px;">换票人证件类型</td>
					<td >
						<input name="cardType" id="cardType" value="${slChangeUser.cardType}"class="easyui-combobox" style="width:220px;" required="true"
							data-options="  
                                url: '<%=path%>/comm/listItemsByKey.do?key=ID_CARD_TYPE',   
                                method:'get',  
                                valueField:'id',  
                                textField:'text',  
                                multiple:false,  
                                editable:false,  
                                panelHeight:'auto',
                                required:true  
                        " ></input>	
                        <span class="starStyle">*</span>
					</td>
			</tr>
			<tr>
				<td class="label"style="width:150px;">换票人证件号码</td>
					<td >
						<input name="cardId" id="cardId" value="${slChangeUser.cardId}" class="easyui-textbox" style="width:220px;" validType="length[1,20]"  required="true"></input>
						<span class="starStyle">*</span>
					</td>
			</tr>
				<tr>
					<td class="label"style="width:150px;">联系电话</td>
					<td >
						<input name="tel" id="tel" value="${slChangeUser.tel}" class="easyui-textbox" style="width:220px;" data-options="validType:['mobile']" required="true"></input>
						<span class="starStyle">*</span>
					</td>
				</tr>
				<tr>
					<td class="label"style="width:150px;">电子邮箱</td>
					<td >
						<input name="mail" id="mail" value="${slChangeUser.mail}" class="easyui-textbox" style="width:220px;" data-options="validType:['mail']"></input>
					</td>
				</tr>
			</table>
		</form>
		</div>
		</body>
		</html>