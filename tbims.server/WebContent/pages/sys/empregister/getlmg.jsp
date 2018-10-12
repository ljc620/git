
<%@ page language="java"  contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/ul/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>图片信息</title>
<%@ include file="/common.jsp"%>
</head>
<body>
	<div class="fast-menu-icon" style="width: 95%; height: auto;">
		<table style="width: 90%;text-align:center">
		        <tr>
		        <td>
		                       姓名：${empRegister.empName}
		        </td>
		        <tr>
				<tr>
				<td align="center">
				<img  src="<%=path%>/empregister/getlmg.do?empId=${empRegister.empId}">
				</td>
			</tr>
			
		</table>
	</div>
</body>
</html>