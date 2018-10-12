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
		<div class="dia-tabs">
			<div id="tt" class="easyui-tabs" style="width: 480px; height: 265px;">
				<div title="基本信息" class="bf-position">
					<form id="fm" method="post" class="bf-from">
						<input type="hidden" name="funcKeys" id="funcKeys" />
						<table class="form-table" style="width: 100%; height: 150px;">
							<tr>
								<td class="label">角色类型：</td>
								<td>
									<input name="roleType" id="roleType" class="easyui-combobox" required="true" data-options="width:300,validType:'length[1,60]'" value="${role.roleType}" />
									<span class="starStyle">*</span>
								</td>
							</tr>
							<tr>
								<td class="label">角色代码：</td>
								<td>
									<input name="roleId" id="roleId" class="easyui-textbox bf-nochange" required="true" readonly="true" data-options="width:300,validType:'length[1,60]'" value="${role.roleId}" />
									<span class="starStyle">*</span>
								</td>
							</tr>
							<tr>
								<td class="label">角色名称：</td>
								<td>
									<input name="roleName" id="roleName" class="easyui-textbox" required="true" data-options="width:300,validType:'length[1,100]'" value="${role.roleName}" />
									<span class="starStyle">*</span>
								</td>
							</tr>
							<tr>
								<td class="label">角色说明：</td>
								<td>
									<input name="remark" id="remark" class="easyui-textbox" data-options="width:300,validType:'length[1,200]'" value="${role.remark}" />
								</td>
							</tr>
							<tr>
								<td colspan='2' style="height: 15px; text-align: right;">
									<span class="starStyle">*</span> <span style="color: red;">项为必输项</span>
								</td>
							</tr>
						</table>
					</form>
				</div>
				<div title="选择功能资源">
					<ul id="funcRoleTree" class="ztree"></ul>
				</div>
			</div>
		</div>
		<script type="text/javascript">
			$(function() {
				$('#roleType').combobox({
					valueField : 'id',
					textField : 'text',
					editable : false,
					panelHeight : 'auto',
					width : 120,
					readonly:true,
					data : [ {
						'id' : '0',
						'text' : '内部角色'
					}, {
						'id' : '1',
						'text' : '外部角色'
					} ]
				});
				
				$("#roleType").combobox("select", '0');
			});
		</script>
</body>
</html>