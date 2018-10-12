
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/ul/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>公告信息</title>
<%@ include file="/common.jsp"%>
</head>
<body>
	<div class="fast-menu-icon" style="width: 770px; height: auto;">
		<table style="width: 760px; padding-left: 5px;">
			<tr>
				<td align="center"><span class="gray2" style="font-size: 15px;">${slNotice.title}</span></td>
			</tr>
			<tr style="height: 35px;">
				<td  align="center"><span class="gray" style="font-size: 13px;">发布时间：<fmt:formatDate
							value="${slNotice.opeTime}" type="both" pattern="yyyy-MM-dd" /></span></td>
			</tr>
			<tr>
				<td><span class="gray1" style="padding-left: 40px; font-size: 13px;">${slNotice.content}</span></td>
			</tr>
			<tr style="height: 35px;">
				<td><span class="gray" style="padding-left: 10px; font-size: 14px;">发布机构：票务中心</span></td>
			</tr>
		</table>
	</div>
</body>
</html>