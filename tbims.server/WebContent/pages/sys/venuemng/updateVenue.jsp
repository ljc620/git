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
		<input type="hidden" name="venueId" id="venueId" value="${sysVenue.venueId}"/>
			<table class="form-table" style="width: 90%;">
				<tr>
					<td class="label">场馆编号</td>
					<td>${sysVenue.venueId}</td>
				</tr>
				<tr>
					<td class="label">场馆名称</td>
					<td>
						<input name="venueName" id="venueName" value="${sysVenue.venueName}" class="easyui-textbox" style="width: 200px;" required="true"></input>
						<span class="starStyle">*</span>
					</td>
				</tr>
				<tr>
					<td class="label">状态</td>
					<td><input name="stat" id="stat" class="easyui-combobox" value="${sysVenue.stat }" style="width: 110px;" /></td>
				</tr>
			</table>
		</form>
	</div>
	<script type="text/javascript">
	$(function() {
		//终端状态初始化
		$('#stat').combobox({
			valueField : 'id',
			textField : 'text',
			editable : false,
			panelHeight :'auto',
			required:true,
			data : [ {
				id : 'Y',
				text : '启用'
			}, {
				id : 'N',
				text : '停用'
			} ]
		});
	}); 
	</script>
</body>
			