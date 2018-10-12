<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="myel" uri="/elFunction"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"></c:set>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>角色管理</title>
<%@ include file="/common.jsp"%>
<script type="text/javascript" src="roleMng.js"></script>
<link rel="stylesheet" href="${path}/resources/ztree_src/css/zTreeStyle/zTreeStyle.css" type="text/css"></link>
<script type="text/javascript" src="${path}/resources/ztree/js/jquery.ztree.core.min.js"></script>
<script type="text/javascript" src="${path}/resources/ztree/js/jquery.ztree.excheck.min.js"></script>
<script type="text/javascript" src="${path}/resources/ztree/js/jquery.ztree.exhide.min.js"></script>
</head>
<body>
	<div id="mytable-buttons">
		<div class="ss-bar">
			<ul class="ss-ul">
				<li style="width: 50px;padding-left:15px;">角色代码</li>
				<li>
					<input id="q_role_cd" class="easyui-textbox" width="60px"></input>
				</li>
				<li style="width: 50px;padding-left:15px;">角色名称</li>
				<li>
					<input id="q_role_nm" class="easyui-textbox" width="60px"></input>
				</li>
				<li>
					<a href="javascript:void(0)" onclick="javascript:queryRole();" class="gray2-btn">&nbsp;查询&nbsp;</a>
				</li>
			</ul>
		</div>
		<div class="ss-bar2">
			<ul class="ss-ul">
				<li>
					<a href="javascript:void(0)" class="red-btn icon-add" onclick="javascript:addRole(0);">
						<span class="btn-text">新增内部角色</span>
					</a>
				</li>
				<li>
					<a href="javascript:void(0)" class="red-btn icon-add" onclick="javascript:addRole(1);">
						<span class="btn-text">新增外部角色</span>
					</a>
				</li>
			</ul>
		</div>
	</div>
	<div class="it-datagrid" style="height: 100%">
		<table id="mytable"></table>
	</div>
	<div class="easyui-dialog" id="roleDialog" style="width: 530px; height: 350px; padding: 5px 10px;" closed="true"></div>
	<div id="dlg-buttons" style="text-align: center; padding-bottom: 15px; display: none;">
		<a href="javascript:void(0)" class="red-btn" id="save-button" onclick="javascript:save('/rolemng/addRole.do')" style="width: 90px; margin-right: 10px;">保存</a>
		<a href="javascript:void(0)" class="blue-btn" id="update-button" onclick="javascript:save('/rolemng/updateRole.do')" style="width: 90px; margin-right: 10px;">修改</a>
		<a href="javascript:void(0)" class="gray-btn" onclick="javascript:$('#roleDialog').dialog('close')" style="width: 90px; margin-right: 10px;">取消</a>
	</div>
</body>
</html>