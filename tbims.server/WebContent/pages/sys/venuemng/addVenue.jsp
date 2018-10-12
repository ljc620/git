<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sys" tagdir="/WEB-INF/tags/sys" %>
<%@ taglib prefix="myel" uri="/elFunction"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"></c:set>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>新增网点</title>
<%@ include file="/common.jsp"%>
</head>
<body>
	<div> 
		<form id="fm" method="post" class="bf-from">
			<table class="form-table" style="width: 90%;">
			<tr>
				<td class="label">场馆编号</td>
				<td >
					<input name="venueId" id="venueId" class="easyui-numberbox" validType="length[1,2]"
					precision="0"  style="width:150px;" required="true"></input>
					<span class="starStyle">*</span>
				</td>
			</tr>
			<tr>
				<td class="label">场馆名称</td>
				<td >
					<input name="venueName" id="venueName" class="easyui-textbox" style="width:150px;" required="true"></input>
					<span class="starStyle">*</span>
				</td>
			</tr>
			<tr>
				<td class="label">状态</td>
				<td>
					<select name="stat" id="stat" class="easyui-combobox" required="true" style="width:150px;"	editable="false" panelHeight='auto' >
						<option value="Y">启用</option>
						<option value="N">停用</option>
					</select>
				</td>
			</tr>
			</table>
		</form>
	</div>
</body>
			