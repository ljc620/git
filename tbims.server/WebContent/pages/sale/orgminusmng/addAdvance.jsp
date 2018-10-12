<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="myel" uri="/elFunction"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"></c:set>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>新增预付款</title>
<%@ include file="/common.jsp"%>
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
					<td class="label" style="width:150px;">扣减金额</td>
					<td>
						<input name="advanceAmt" id="advanceAmt" class="easyui-numberbox"  data-options="validType:['num']"  max="999999999999"
						precision="0" style="width:300px;" required="true"></input>
						<span class="starStyle">*</span>
					</td>
				</tr>
				<tr>
					<td class="label" style="width:150px;">备注</td>
					<td>
						<input name="remark" id="remark" class="easyui-textbox" style="width:300px;"></input>
					</td>
				</tr>
			</table>
	</form>
</div>

</body>
</html>