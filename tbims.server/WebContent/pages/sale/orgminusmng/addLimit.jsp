<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="myel" uri="/elFunction"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"></c:set>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>扣减额度</title>
<%@ include file="/common.jsp"%>
<script type="text/javascript" src="${path}/pages/sale/orgminusmng/limitAmtMng.js"></script> 
</head>
<body>
	<div> 
		<form id="fm" method="post" class="bf-from">
	
			<table class="form-table" style="width: 90%;">
			<tr>
					<td class="label" style="width:150px;">组织机构代码</td>
					<td>
					<input type="hidden" name="orgId" id="orgId" value="${slOrg.orgId}">
					${slOrg.orgId}
					</td>
				</tr>
				 <tr>
					<td class="label" style="width:150px;">机构名称</td>
					<td>
					${slOrg.orgName}
					</td>
				</tr> 
			    <tr>
					<td class="label" style="width:150px;">票种</td>
					<td colspan="3">
						<input name="ticketTypeId" id="ticketTypeId" class="easyui-combobox" style="width:300px;" data-options="  
                                url: '<%=path%>/orgMng/ticketTypeList.do?flag=t',   
                                method:'get',  
                                valueField:'ticketTypeId',  
                                textField:'ticketTypeName',  
                                multiple:false,  
                                editable:false,  
                                panelHeight:'auto',required:true" ></input>
                                <span class="starStyle">*</span>
					</td>
				</tr>
				<tr>
					<td class="label" style="width:150px;">数量</td>
					<td>
						<input name="limit" id="limit" class="easyui-numberbox" data-options="validType:['num']"
						precision="0" style="width:300px;" required="true"></input>
						<span class="starStyle">*</span>
					</td>
				</tr>
				<tr>
					<td class="label" style="width:150px;">备注</td>
					<td>
						<input name="remark" id="remark" class="easyui-textbox"  style="width:300px;" required="true"></input>
						<span class="starStyle">*</span>
					</td>
				</tr>
			</table>
	</form>
</div>

</body>
</html>