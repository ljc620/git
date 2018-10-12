<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="myel" uri="/elFunction"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>保证金</title>
<%@ include file="/common.jsp"%>
<script type="text/javascript" src="${path}/pages/sale/agentmng/depositMng.js"></script>
<script type="text/javascript" src="${path}/js/common/commcheck.js"></script>
</head>
<body>
	<div id="mytable-buttons">
		<div class="ss-bar">
			<ul class="ss-ul">
			    <li>组织机构代码:</li>
			    <li>
					${slOrg.orgId}
					<input type="hidden" id="orgId" name="orgId" value="${slOrg.orgId}">
				</li>
				
				<li>机构名称：</li>
				<li>
					${slOrg.orgName}
				</li>
				
				<li>保证金总金额：</li>
				<li>
					${depositSum}
				</li>
			</ul>
		</div>
		<div class="ss-bar2">
			<ul class="ss-ul">
			<c:if test="${myel:hasPriv('i2_sale_org_add',userSession.functionSet)}">
				<li>
					<a href="javascript:void(0)" class="red-btn icon-add" onclick="javascript:addDeposit('${slOrg.orgId}');">
						<span class="btn-text">新增保证金</span>
					</a>
				</li>
			</c:if>
			</ul>
		</div>
	</div>
	<div class="it-datagrid" style="height: 100%">
		<table id="mytable"></table>
	</div>
	<div id="addDialog" class="easyui-dialog" style="width: 600px; height: 350px; padding: 5px 10px" modal="true" closed="true" buttons="#dlg-buttons">
	</div>
	<div id="dlg-buttons" style="text-align: center; display: none;">
		<a href="javascript:void(0)" id="save-buttons" class="blue-btn" onclick="javascript:saveDeposit()" style="width: 90px; margin-right: 10px;margin-left:90px;">保存</a>
		<a href="javascript:void(0)" class="gray-btn" onclick="javascript:$('#addDialog').dialog('close');" style="width: 90px; margin-left: 10px;">取消</a>
	</div>
</body>
</html>