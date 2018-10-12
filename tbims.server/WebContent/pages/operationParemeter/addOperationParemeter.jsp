<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="myel" uri="/elFunction"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"></c:set>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>新增运营参数</title>
<%@ include file="/common.jsp"%>
<script type="text/javascript" src="${path}/js/common/commcheck.js"></script>
<script type="text/javascript" src="operationParemeter.js"></script>
</head>
<body>
	<div> 
		<form id="fm" method="post" class="bf-from">
			<table class="form-table" style="width: 90%;">
			    <tr>
					<td class="label" style="width:150px;">运营参数Id:</td>
					<td colspan="3">
						<input name="paremeterId" id="paremeterId" class="easyui-textbox" style="width:220px;" data-options="validType:['chineseCheck']" validType="length[1,60]"  required="true"></input>	
						<span class="starStyle">*</span>
					</td>
				</tr>
			    <tr>
					<td class="label" style="width:150px;">运营参数名称:</td>
					<td colspan="3">
						<input name="paremeterName" id="paremeterName" class="easyui-textbox" style="width:220px;" validType="length[1,100]" required="true"></input>	
						<span class="starStyle">*</span>
					</td>
				</tr>
				<tr>
					<td class="label" style="width:150px;">参数值:</td>
					<td colspan="3">
						<input name="paremeterVal" id="paremeterVal" class="easyui-textbox" style="width:220px;" data-options="validType:['chineseCheck']" validType="length[1,100]"  required="true"></input>
						<span class="starStyle">*</span>
					</td>
				</tr>
			</table>
		</form>
	</div>
</body>
</html>