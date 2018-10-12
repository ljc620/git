<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="myel" uri="/elFunction"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"></c:set>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>新增换票人</title>
<%@ include file="/common.jsp"%>
<script type="text/javascript" src="${path}/js/common/commcheck.js"></script>
<script type="text/javascript" src="${path}/pages/changeUser/changeUserMng.js"></script>
</head>
<body>
	<div> 
		<form id="fm" method="post" class="bf-from">
			<table class="form-table" style="width: 90%;">
			    <tr>
					<td class="label" style="width:150px;">姓名</td>
					<td colspan="3">
						<input name="changeUserName" id="changeUserName" validType="length[2,90]" class="easyui-textbox" style="width:220px;" required="true"></input>
						<span class="starStyle">*</span>	
					</td>
				</tr>
				 <tr>
					<td class="label" style="width:150px;">证件类型</td>
					<td colspan="3">
						<input name="cardType" id="cardType" class="easyui-combobox" style="width:220px;"  required="true"
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
					<td class="label" style="width:150px;">证件号码</td>
					<td colspan="3">
						<input name="cardId" id="cardId" class="easyui-textbox" validType="length[1,20]" style="width:220px;"  required="true"></input>
						<span class="starStyle">*</span>
					</td>
				</tr>
				
				<tr>
					<td class="label" style="width:150px;">联系电话</td>
					<td colspan="3">
						<input name="tel" id="tel" class="easyui-textbox" data-options="validType:['mobile']"  style="width:220px;" required="true"></input>
						<span class="starStyle">*</span>
					</td>
				</tr>
				<tr>
					<td class="label" >电子邮箱</td>
					<td>
						<input class="easyui-textbox" id="mail" name="mail" data-options="validType:['mail']"  style="width:220px;"  ></input>
					</td>
				</tr>
			
			</table>
			
		</form>
</div>
</body>
</html>