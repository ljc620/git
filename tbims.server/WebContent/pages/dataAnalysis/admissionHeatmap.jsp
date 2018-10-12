<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="myel" uri="/elFunction"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"></c:set>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv=X-UA-Compatible content="IE=edge,chrome=1">
<title>游客热力分布图</title>
<%@ include file="/common1.jsp"%>
<script type="text/javascript" src="<%=path%>/js/common/commcheck.js"></script>
<script src="${path}/resources/echarts/echarts.min-3.8.5.js"></script>
<script src="${path}/resources/echarts/theme/default.js"></script>
<script type="text/javascript" src="${path}/pages/dataAnalysis/admissionHeatmap.js"></script>
</head>
<body class="easyui-layout" data-options="fit:true">
	<div data-options="region:'north',border:false" style="height:50px;background-color: #eee;">
		<div class="ss-bar" style="margin-top:13px;">
			<div>
				<ul class="ss-ul">
					<li style="width: 50px; padding-left: 15px;">统计区间</li>
					<li><input id="startDate" name="startDate"
						class="easyui-datebox" style="width: 180px;" required="true"
						editable="false"></input>--<input id="endDate" name="endDate"
						class="easyui-datebox" style="width: 180px;"
						validType="checkDt['#startDate']" editable="false" required="true"></input></li>
					<li><a href="javascript:void(0);" onclick="admissionChart();" class="gray2-btn"
						style="width: 50px;">查询分析</a></li>
				</ul>
			</div>
		</div>
	</div>
	<div data-options="region:'center',border:false" style="background-color: #eee;padding:5px 15px 15px 15px;">
		<div style="height:100%;cursor: default; -ms-user-select: none; -webkit-tap-highlight-color: transparent; background-color: #fff;box-shadow: inset 0 0 0 1px rgba(0,0,0,.1);-webkit-box-shadow: inset 0 0 0 1px rgba(0,0,0,.1);">
			<div id="analysisChart" style="width: 100%; height: 100%;;"></div>
		</div>
	</div>
</body>
</html>