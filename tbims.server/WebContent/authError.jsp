<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/common.jsp"%>
<title>会话过期</title>
<script type="text/javascript">
	function relogin() {
		window.location = $ctx + '/login.jsp';
	}
</script>
</head>
<body style="background: #fff; text-align: center; margin: 0 auto;">
	<div class="tips" style="margin: 0 auto;margin-top:80px; width: 400px; height: 200px;" align="center">
		<table>
			<tr>
				<td>
					<p class="con icon-err">无此操作的权限！</p>
				</td>
			</tr>
			<tr>
				<td>
					<a class="blue-btn enter" onclick="relogin()" style="width: 150px;margin-left: 55px;">确定</a>
				</td>
			</tr>
		</table>
	</div>
</body>
</html>