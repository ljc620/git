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
								    <span id="roleTypeDesc"></span>
									<input name="roleType" id="roleType" type="hidden"/>
								</td>
							</tr>
							<tr>
								<td class="label">角色代码：</td>
								<td>
									<input name="roleId" id="roleId" class="easyui-textbox" required="true" data-options="width:300,validType:'length[1,60]'" />
									<span class="starStyle">*</span>
								</td>
							</tr>
							<tr>
								<td class="label">角色名称：</td>
								<td>
									<input name="roleName" id="roleName" class="easyui-textbox" required="true" data-options="width:300,validType:'length[1,100]'" />
									<span class="starStyle">*</span>
								</td>
							</tr>
							<tr>
								<td class="label">角色说明：</td>
								<td>
									<input name="remark" id="remark" class="easyui-textbox" data-options="width:300,validType:'length[1,200]'" />
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
</body>
</html>