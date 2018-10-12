<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="myel" uri="/elFunction"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>额度余额管理</title>
<%@ include file="/common.jsp"%>
<link rel="stylesheet" href="${path}/resources/ztree/css/zTreeStyle/zTreeStyle.css" type="text/css"></link>
<script type="text/javascript" src="${path}/resources/ztree/js/jquery.ztree.core.min.js"></script>
<script type="text/javascript" src="${path}/resources/ztree/js/jquery.ztree.excheck.min.js"></script>
<script type="text/javascript" src="${path}/resources/ztree/js/jquery.ztree.exhide.min.js"></script>
<script type="text/javascript" src="${path}/pages/sale/orgmng/limitAmtMng.js"></script>
<script type="text/javascript" src="${path}/js/common/commcheck.js"></script>
</head>
<body>
	<div id="mytable-buttons">
		<div class="ss-bar">
			<ul class="ss-ul">
			    <li style="width:80px;">组织机构代码</li>
			    <li style="width:150px;">
					${slOrg.orgId}
					<input type="hidden" id="orgId" name="orgId" value="${slOrg.orgId}">
				</li>
				
				<li style="width:55px;">机构名称</li>
				<li style="width:300px;">
					${slOrg.orgName}
				</li>
			</ul>
		</div>
		<div class="ss-bar2">
			<ul class="ss-ul">
			<c:if test="${myel:hasPriv('i2_sale_org_add',userSession.functionSet)}">
				<li>
					<a href="javascript:void(0)" class="red-btn icon-add" onclick="javascript:addLimit('${slOrg.orgId}');">
						<span class="btn-text">新增额度</span>
					</a>
				</li>
			</c:if>
			</ul>
		</div>
	</div>
	<div class="it-datagrid" style="height: 100%">
		<table id="mytable"></table>
	</div>
	<div id="limitAddDialog" class="easyui-dialog" style="width: 600px; height: 350px; padding: 5px 10px" modal="true" closed="true" buttons="#dlg-buttons">
	</div>
	<div id="limitListDialog" class="easyui-dialog" style="width: 600px; height: 350px; padding: 5px 10px" modal="true" closed="true" >
	</div>
	<div id="dlg-buttons" style="text-align: center; display: none;">
		<a href="javascript:void(0)" id="save-button" class="blue-btn" onclick="javascript:saveLimit()" style="width: 90px; margin-right: 10px;margin-left:90px;">保存</a>
		<a href="javascript:void(0)" class="gray-btn" onclick="javascript:$('#limitAddDialog').dialog('close');" style="width: 90px; margin-left: 10px;">取消</a>
	</div>
</body>
</html>