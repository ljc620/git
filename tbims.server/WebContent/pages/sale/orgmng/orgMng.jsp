<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="myel" uri="/elFunction"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>签约社管理</title>
<%@ include file="/common.jsp"%>
<script type="text/javascript" src="orgMng.js"></script>
<script type="text/javascript" src="${path}/js/common/commcheck.js"></script>
</head>
<body>
	<div id="mytable-buttons">
		<div class="ss-bar">
			<ul class="ss-ul">
				<li style="width: 50px;padding-left:15px;">机构名称</li>
				<li>
					<input id="qorgName" name="orgName" class="easyui-textbox" style="width:160px;"></input>
				</li>
				<li>
					<a href="javascript:void(0)" onclick="javascript:myQuery();return false;" class="gray2-btn">&nbsp;查询&nbsp;</a>
				</li>
			</ul>
		</div>
		<div class="ss-bar2">
			<ul class="ss-ul">
			<c:if test="${myel:hasPriv('i2_sale_org_add',userSession.functionSet)}">
				<li>
					<a href="javascript:void(0)" class="red-btn icon-add" onclick="javascript:addOrg();">
						<span class="btn-text">新增签约社</span>
					</a>
				</li>
				<li>
					<a href="javascript:void(0)" class="red-btn icon-add" onclick="javascript:updateStat('Y');">
						<span class="btn-text">启用</span>
					</a>
				</li>
				<li>
					<a href="javascript:void(0)" class="red-btn icon-add" onclick="javascript:updateStat('N');">
						<span class="btn-text">禁用</span>
					</a>
				</li>
			</c:if>
			</ul>
		</div>
	</div>
	<div class="it-datagrid" style="height:100%">
		<table id="mytable"></table>
	</div>
	<div id="orgDialog" class="easyui-dialog" style="width: 500px; height: 350px; padding: 5px 10px" modal="true" closed="true" buttons="#dlg-buttons">
	</div>
	<div id="dlg-buttons" style="text-align: center; display: none;">
		<a href="javascript:void(0)" id="save-button" class="blue-btn" onclick="javascript:saveOrg('/orgMng/addOrg.do')" style="width: 90px; margin-right: 10px;margin-left:90px;">保存</a>
		<a href="javascript:void(0)" id="update-button" class="blue-btn"  onclick="javascript:saveOrg('/orgMng/updateOrg.do')" style="width: 90px; margin-right: 10px;">修改</a>
		<a href="javascript:void(0)" class="gray-btn" onclick="javascript:$('#orgDialog').dialog('close');" style="width: 90px; margin-left: 10px;">取消</a>
	</div>
</body>
</html>