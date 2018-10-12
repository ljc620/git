<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="myel" uri="/elFunction"%>
<%@ taglib prefix="sys" tagdir="/WEB-INF/tags/sys" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<c:set var="ctx" value="${pageContext.request.contextPath}"></c:set>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>网络代理商门票核销明细</title>
<%@ include file="/common.jsp"%>
<script type="text/javascript" src="<%=path%>/js/common/commcheck.js"></script>
<script type="text/javascript" src="wlCheckdDetail.js"></script>
<!--  -->
<link rel="stylesheet" href="${path}/resources/ztree_src/css/zTreeStyle/zTreeStyle.css" type="text/css"></link>
<script type="text/javascript" src="${path}/resources/ztree/js/jquery.ztree.core.min.js"></script>
<script type="text/javascript" src="${path}/resources/ztree/js/jquery.ztree.excheck.min.js"></script>

</head>
<body>
	<div id="mytable-buttons">
		<div class="ss-bar">
			<form id="queryForm">
				<ul class="ss-ul">
					<li style="width: 50px; padding-left: 15px;">统计日期</li>
					<li>
					<input id="startDate" name="startDate" class="easyui-datebox"  editable="false" required="true" style="width:120px;"></input>-
					<input id="endDate" name="endDate" class="easyui-datebox"  validType="checkDt['#startDate']" editable="false" required="true" style="width:120px;"></input>
					</li>
					<li style="width: 50px;">机构名称</li>
					<li>
						<input id="orgId" name="orgId"></input>
					</li>
					<li><a onclick="query();" class="gray2-btn"
						style="width: 35px;">查询</a></li>
					<li><a class="indigo-btn" id="btnExcel"
						style="margin-left: 15px;" onclick="expExcel()">导出EXCEL</a></li>
				</ul>
			</form>
		</div>
	</div>
	<div class="it-datagrid" style="height: 100%">
		<table id="mytable">
		</table>
	</div>
</body>
</html>