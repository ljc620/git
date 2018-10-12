<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="myel" uri="/elFunction"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"></c:set>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<%@ include file="/common.jsp"%>
<title>错误页面</title>
</head>
<body style="padding: 15px;">
<style type="text/css" >
.stable {
	border-collapse: collapse;
	border: none;
	width: 200px;
}

.stable .stable-td {
	border: solid #000 1px;
}

.stable .stable-right-td {
	border-right: solid #000 1px;
}

.stable .stable-bottom-td {
	border-bottom: solid #000 1px;
}

.stable .center {
	text-align: center;
}

.stable .title {
	background-color: #c8c8c8;
}

.stable table {
	border-collapse: collapse;
	border-spacing: 0;
	padd: expression(this.cellPadding =
		                                                                                                                                                                                                                                                                                                                                 
		0);
}

.stable table tr {
	height: 10px;
}

.stable table td {
	display: table-cell;
	vertical-align: middle;
}
</style>
	<span style="padding-left: 295px; font-size: 22px; font-weight: bold;">系统处理错误</span>
	<table cellpadding="0" cellspacing="0" class="stable" style="width: 100%; margin-top: 14px;">
		<tr>
			<td class="stable-td">
				<table style="width: 100%;">
					<tr>
						<td width="100" class="stable-right-td center">错误码</td>
						<td>&nbsp;&nbsp;${errorCode}</td>
					</tr>
				</table>
			</td>
		</tr>
		<tr>
			<td class="stable-td">
				<table style="width: 100%;">
					<tr>
						<td width="100" class="stable-right-td center">错误信息</td>
						<td>&nbsp;&nbsp;${errorMsg}</td>
					</tr>
				</table>
			</td>
		</tr>
		<tr>
			<td class="stable-td">
				<table style="width: 100%;">
					<tr>
						<td height="50" width="100" class="stable-right-td center">错误描述</td>
						<td>&nbsp;&nbsp;${errorDescribe}</td>
					</tr>
				</table>
			</td>
		</tr>
	</table>
	<table style="width: 100%;">
		<tr>
			<td style="height: 6px;"></td>
		</tr>
		<tr>
			<td align="center">
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="" onclick="javascript:history.back(-1);" style="width: 100px">返回</a>
			</td>
		</tr>
	</table>
</body>
</html>