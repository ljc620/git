<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="myel" uri="/elFunction"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sys" tagdir="/WEB-INF/tags/sys" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>用户管理</title>
<%@ include file="/common.jsp"%>
<script type="text/javascript" src="userMng.js"></script>
<script type="text/javascript" src="${path}/js/common/commcheck.js"></script>
<link rel="stylesheet" href="${path}/resources/ztree_src/css/zTreeStyle/zTreeStyle.css" type="text/css"></link>
<script type="text/javascript" src="${path}/resources/ztree/js/jquery.ztree.core.min.js"></script>
<script type="text/javascript" src="${path}/resources/ztree/js/jquery.ztree.excheck.min.js"></script>
</head>
<body>
	<div id="mytable-buttons">
		<div class="ss-bar">
			<ul class="ss-ul">
				<li style="width: 50px;padding-left:15px;">岗位</li>
				<li>
					<input id="qpositionCode" name="positionCode" class="easyui-combobox" style="width: 200px;"></input>
				</li>
			 	<li style="width: 50px;padding-left:15px;">网点名称</li>
				<li>
				<sys:noptreeselect id="qoutletId" name="qoutletId" options="width:200,panelWidth:200"  idField ="outletId"  textField="outletName" 
						url="comm/listOutletTree.do" pidField="outletType" isParentLevel="false" />
				</li>
				<li style="width: 25px;padding-left:15px;">角色</li>
				<li>
					<input id="q_role_id" name="q_role_id"></input>
				</li>
			</ul>
			<ul class="ss-ul">
				<li style="width: 50px;padding-left:15px;">用户账号</li>
				<li>
					<input id="q_user_id" name="q_user_id" class="easyui-textbox" style="width: 200px;"></input>
				</li>
				<li style="width: 50px;padding-left:15px;">用户姓名</li>
				<li>
					<input id="q_user_name" name="q_user_name" class="easyui-textbox" style="width: 200px;"></input>
				</li>
				<li style="width: 25px;padding-left:15px;">状态</li>
				<li>
					<input id="q_user_stat" name="q_user_stat" ></input>
				</li>
				<li style="padding-left:20px;">
					<a href="javascript:void(0)" onclick="javascript:queryUser();" class="gray2-btn">&nbsp;查询&nbsp;</a>
				</li>
			</ul>
		</div>
		<div class="ss-bar2">
			<ul class="ss-ul">
				<li>
					<a href="javascript:void(0)" class="red-btn icon-add" onclick="javascript:clsCheck('#mytable');">
						<span class="btn-text">清空选中 </span>
					</a>
				</li>
				<li>
					<a href="javascript:void(0)" class="red-btn icon-add" onclick="javascript:openUserAddWin();">
						<span class="btn-text">添加用户</span>
					</a>
				</li>
			<!--  	<li>
					<a href="javascript:void(0)" class="red-btn icon-add" onclick="javascript:deleteUser();">
						<span class="btn-text">删除用户</span>
					</a>
				</li>-->
				<li>
					<a href="javascript:void(0)" class="red-btn icon-add" onclick="javascript:openRoleWin();">
						<span class="btn-text">设置角色</span>
					</a>
				</li>
				<li>
					<a href="javascript:void(0)" class="red-btn icon-add" onclick="javascript:passReSet();">
						<span class="btn-text">密码重置</span>
					</a>
				</li>
				<li>
					<a href="javascript:void(0)" class="red-btn icon-add" onclick="javascript:updateUserStatus('Y');">
						<span class="btn-text">启用</span>
					</a>
				</li>
				<li>
					<a href="javascript:void(0)" class="red-btn icon-add" onclick="javascript:updateUserStatus('N');">
						<span class="btn-text">禁用</span>
					</a>
				</li>
			</ul>
		</div>
	</div>
	<div class="it-datagrid" style="height: 100%">
		<table id="mytable"></table>
	</div>
	<!-- 	角色处理 -->
	<div class="easyui-dialog" id="addRoleDlg" style="width: 550px; height: 500px; padding: 5px 10px;" closed="true">
		<div class="it-datagrid" style="height: 100%">
			<table id="roleTable"></table>
		</div>
	</div>
	<div class="easyui-dialog" id="addRoleDlg1" style="width: 550px; height: 500px; padding: 5px 10px;" closed="true">
		<div class="it-datagrid" style="height: 100%">
			<table id="roleTable"></table>
		</div>
	</div>
	<div id="dlg-buttons" style="text-align: center; display: none;">
		<a href="javascript:void(0)" class="red-btn" id="save-button" onclick="javascript:setRoleToUser()" style="width: 60px; margin-right: 10px;">提交</a>
		<a href="javascript:void(0)" class="gray-btn" onclick="javascript:$('#addRoleDlg').dialog('close');" style="width: 60px; margin-left: 10px;">关闭</a>
	</div>
	<!-- 	用户处理 -->
	<div id="userDialog" class="easyui-dialog" style="width: 800px; height: 400px; padding: 5px 10px" closed="true"></div>
	<div id="dlg-user-buttons" style="text-align: center; display: none; padding-bottom: 15px;">
		<a href="javascript:void(0)" id="save-user-button" class="blue-btn" onclick="javascript:saveUser('/usermng/addUser.do')" style="width: 90px; margin-right: 10px; margin-left: 250px;">保存</a>
		<a href="javascript:void(0)" id="update-user-button" class="blue-btn" onclick="javascript:saveUser('/usermng/updateUser.do')" style="width: 90px; margin-right: 10px; margin-left: 250px;">修改</a>
		<a href="javascript:void(0)" class="gray-btn" onclick="javascript:$('#userDialog').dialog('close');" style="width: 90px; margin-left: 10px;">取消</a>
	</div>
</body>
</html>