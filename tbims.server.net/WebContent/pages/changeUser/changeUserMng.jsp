<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@taglib prefix="myel" uri="/elFunction"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>换票人管理</title>
<%@ include file="/common.jsp"%>
<script type="text/javascript" src="${path}/js/common/commcheck.js"></script>
<script type="text/javascript" src="changeUserMng.js"></script>
</head>
<body>
	<div id="mytable-buttons">
		<div class="ss-bar">
		<table class="form-table" style="width: 500px;">
			<tr>
				<td>所属机构编码：${userSession.orgId}
				</td>
				<td>所属机构名称：${userSession.orgName}
				</td>
			</tr>
		</table>
			<ul class="ss-ul">
				<li>
					<a href="javascript:void(0)" class="red-btn icon-add" onclick="javascript:addChangeUser();">
						<span class="btn-text">添加换票人</span>
					</a>
				</li>
			</ul>
		</div>
	</div>
	<div class="it-datagrid" style="height:100%">
		<table id="mytable"></table>
	</div>
	<div id="regionDialog" class="easyui-dialog" style="width: 600px; height: 350px; padding: 5px 10px" modal="true" closed="true">
	</div>
	<div id="dlg-buttons" style="text-align: center; display: none;">
		<a href="javascript:void(0)" id="save-button" class="blue-btn" onclick="javascript:saveChangeUser('/changeUser/addChangeUser.do')" style="width: 90px; margin-right: 10px;margin-left:90px;">保存</a>
		<a href="javascript:void(0)" id="update-button" class="blue-btn"  onclick="javascript:saveChangeUser('/changeUser/updateChangeUser.do')" style="width: 90px; margin-right: 10px;">修改</a>
		<a href="javascript:void(0)" class="gray-btn" onclick="javascript:$('#regionDialog').dialog('close');" style="width: 90px; margin-left: 10px;">取消</a>
	</div>
</body>
</html>