<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="myel" uri="/elFunction"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>菜单管理</title>
<%@ include file="/common.jsp"%>
<script type="text/javascript" src="menuMng.js"></script>
<link rel="stylesheet" href="${path}/resources/ztree_src/css/zTreeStyle/zTreeStyle.css" type="text/css"></link>
<script type="text/javascript" src="${path}/resources/ztree/js/jquery.ztree.core.min.js"></script>
<script type="text/javascript" src="${path}/resources/ztree/js/jquery.ztree.excheck.min.js"></script>
</head>
<body class="easyui-layout">
	<div data-options="region:'west'" style="width: 250px; height: 100%; overflow: auto;" title="请选择菜单">
		<ul id="menuTree" class="ztree"></ul>
	</div>
	<div data-options="region:'center'">
		<div id="mytable-buttons">
			<div class="ss-bar2">
				<ul class="ss-ul">
					<li>
						<a class="red-btn icon-add" onclick="javascript:addMenu()">
							<span class="btn-text">添加菜单</span>
						</a>
					</li>
					<li>
						<a class="red-btn icon-add" onclick="javascript:deleteMenu(null)">
							<span class="btn-text">删除菜单</span>
						</a>
					</li>
					<li>
						<a class="red-btn icon-add" onclick="javascript:clsCheck('#mytable');">
							<span class="btn-text">清空选中</span>
						</a>
					</li>
					<li>
						<a class="red-btn icon-add" onclick="javascript:updateMenuStatus('Y');">
							<span class="btn-text">菜单启用</span>
						</a>
					</li>
					<li>
						<a class="red-btn icon-add" onclick="javascript:updateMenuStatus('N');">
							<span class="btn-text">菜单禁用</span>
						</a>
					</li>
				</ul>
			</div>
		</div>
		<div class="it-datagrid" style="height: 100%; width: 100%;">
			<table id="mytable"></table>
		</div>
	</div>
	<!-- 	菜单框 -->
	<div class="easyui-dialog" id="menuDialog" style="width: 600px; height: 350px; padding: 5px 10px;" closed="true"></div>
	<div id="dlg-menu-buttons" style="text-align: center; padding-bottom: 15px; display: none;">
		<a href="javascript:void(0)" class="blue-btn" id="addMenu" onclick="javascript:saveMenu('/menumng/addMenu.do')" style="width: 90px; margin-right: 10px;">保存</a>
		<a href="javascript:void(0)" class="blue-btn" id="updateMenu" onclick="javascript:saveMenu('/menumng/updateMenu.do')" style="width: 90px; margin-right: 10px;">修改</a>
		<a href="javascript:void(0)" class="gray-btn" onclick="javascript:$('#menuDialog').dialog('close')" style="width: 90px; margin-right: 10px;">取消</a>
	</div>
</body>
</html>