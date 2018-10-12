<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="myel" uri="/elFunction"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"></c:set>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>添加销售记录</title>
<%@ include file="/common.jsp"%>
</head>
<body>
	<div>
		<form id="fm" method="post" class="bf-from">
			<table class="form-table" style="width: 100%; height: 150px;">
				<tr>
					<td class="label">销售日期：</td>
					<td>
						${myel:getNow('yyyy-MM-dd')}
					</td>
				</tr>
				<tr>
					<td class="label">票种：</td>
					<td>
						<input name="ticketTypeId" id="ticketTypeId" />
					</td>
				</tr>
				<tr>
					<td class="label">销售数量：</td>
					<td>
						<input name="saleNum" id="saleNum" class="easyui-textbox" required="true" />
					</td>
				</tr>
			</table>
		</form>
	</div>
	<script type="text/javascript">
		$(function() {
			$('#ticketTypeId').combobox({
				url : $ctx + '/saleReg/listTicketTypeByST.do', // controller地址
				method : 'get',
				valueField : 'ticketTypeId',
				textField : 'ticketTypeName',
				multiple : false,
				editable : false,
				required : true
			});
		});
	</script>
</body>
</html>