<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="myel" uri="/elFunction"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"></c:set>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>新增角色</title>
<%@ include file="/common.jsp"%>
</head>
<body>
	<div>
		<form id="fm" method="post" class="bf-from">
			<table class="form-table" style="width: 100%;">
				<tr>
					<td class="label">父菜单编码</td>
					<td>
						<input name="menuPid" id="menuPid" class="easyui-textbox bf-nochange" required="true" readonly="true" value="${sysMenu.menuPid}" />
						<span class="starStyle">*</span>
					</td>
					<td class="label">菜单名称</td>
					<td>
						<input name="menuName" class="easyui-textbox" required="true" value="${sysMenu.menuName}" />
						<span class="starStyle">*</span>
					</td>
				</tr>
				<tr>
					<td class="label">菜单编码</td>
					<td>
						<input name="menuId" id="menuId" class="easyui-textbox bf-nochange"  readonly="true"  required="true" value="${sysMenu.menuId}" />
						<span class="starStyle">*</span>
					</td>
					<td class="label">顺序号</td>
					<td>
						<input name="orderNum" class="easyui-numberbox" data-options="min:0,max:999" required="true" value="${sysMenu.orderNum}" />
						<span class="starStyle">*</span>
					</td>
				</tr>
				<tr>
					<td class="label">菜单状态</td>
					<td>
						<input name="menuStat" id="menuStat" required="true" value="${sysMenu.menuStat}" />
						<span class="starStyle">*</span>
					</td>
					<td class="label">面板编号</td>
					<td>
						<input name="menuPanal" class="easyui-textbox" value="${sysMenu.menuPanal}" />
					</td>
				</tr>
				<tr>
					<td class="label">类型</td>
					<td>
						<input name="menuType" id="menuType" required="true" value="${sysMenu.menuType}" />
						<span class="starStyle">*</span>
					</td>
					<td class="label">图标css类</td>
					<td>
						<input name="menuIcon" class="easyui-textbox" value="${sysMenu.menuIcon}" />
					</td>
				</tr>
				<tr>
					<td class="label">菜单（内/外）</td>
					<td>
						<input name="menuClass" id="menuClass" required="true" value="${sysMenu.menuClass}"/>
						<span class="starStyle">*</span>
					</td>
				</tr>
				<tr>
					<td class="label">URL</td>
					<td colspan="3">
						<input name="menuUrl" class="easyui-textbox" data-options="width:430" value="${sysMenu.menuUrl}" />
					</td>
				</tr>
			</table>
		</form>
	</div>
	<script type="text/javascript">
		$(function() {
			$("#menuType").combobox({
				valueField : 'id',
				textField : 'text',
				editable : false,
				panelHeight : 'auto',
				width : 120,
				data : [ {
					'id' : '0',
					'text' : '菜单'
				}, {
					'id' : '1',
					'text' : '功能'
				}, {
					'id' : '2',
					'text' : '面板'
				} ]
			});

			$("#menuStat").combobox({
				valueField : 'id',
				textField : 'text',
				editable : false,
				panelHeight : 'auto',
				width : 120,
				data : [ {
					'id' : 'Y',
					'text' : '启用'
				}, {
					'id' : 'N',
					'text' : '停用'
				} ]
			});
			 //菜单内外部类型(0内部，1外部)
			$("#menuClass").combobox({
				valueField : 'id',
				textField : 'text',
				editable : false,
				panelHeight : 'auto',
				width : 120,
				data : [ {
					'id' : '0',
					'text' : '内部'
				}, {
					'id' : '1',
					'text' : '外部'
				} ]
			});
		});
	</script>
</body>
</html>