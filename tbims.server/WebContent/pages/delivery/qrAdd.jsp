<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="myel" uri="/elFunction"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"></c:set>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>新增二维码</title>
<%@ include file="/common.jsp"%>
<!-- <script type="text/javascript" src="qrAdd.js"></script> -->
</head>
<body>
	<div>
		<form id="fm" method="post" class="bf-from">
			<table class="form-table" style="width: 85%;">
				<tr>
					<td class="label" style="width: 120px;">票种</td>
					<td><input name="ticketTypeIds" id="ticketTypeIds" class="easyui-combobox" required="true" style="width:140px;" ></input><span class="starStyle">*</span></td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
				</tr>
				<tr>
					<td class="label">票种类型</td>
					<td>
						<select name="stat" id="stat" class="easyui-combobox" required="true" style="width:110px;"	editable="false" panelHeight='auto' >
							<option value="Y">实体票</option>
							<option value="N">电子票</option>
						</select>
						<span class="starStyle">*</span>
					</td>
				</tr>
				<tr>
					<td class="label" style="width: 150px;">起始票号</td>
					<td colspan="3"><input name="beginNo" id="beginNo"
						class="easyui-numberbox" style="width: 300px;"
						 valueField="number" 
						required="true"></input> <span class="starStyle">*</span></td>
				</tr>
				<tr>
					<td class="label" style="width: 150px;">截止票号</td>
					<td colspan="3"><input name="endNo" id="endNo"
						class="easyui-numberbox"
						style="width: 300px;" required="true"></input> <span
						class="starStyle">*</span></td>
				</tr>
			</table>
		</form>
	</div>
	<script type="text/javascript">
		$(function() {
			//票种初始化
			$('#ticketTypeIds').combobox({
				url : $ctx + '/orgMng/ticketTypeList.do?flag=a',
				method : 'get',
				valueField : 'ticketTypeId',
				textField : 'ticketTypeName',
				multiple : false,
				editable : false,
				onLoadSuccess : function(e) {
				},
				onLoadError : function(e) {
					getAjaxError(e);
				},
			});
		});	
	</script>
</body>
</html>
