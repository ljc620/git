<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="myel" uri="/elFunction"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sys" tagdir="/WEB-INF/tags/sys" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>终端管理</title>
<%@ include file="/common.jsp"%>
<script type="text/javascript" src="${path}/js/common/commcheck.js"></script>
<script type="text/javascript" src="clientMng.js"></script>
<link rel="stylesheet" href="${path}/resources/ztree_src/css/zTreeStyle/zTreeStyle.css" type="text/css"></link>
<script type="text/javascript" src="${path}/resources/ztree/js/jquery.ztree.core.min.js"></script>
<script type="text/javascript" src="${path}/resources/ztree/js/jquery.ztree.excheck.min.js"></script>
</head>
<body>
	<div id="mytable-buttons">
		<div class="ss-bar">
			<ul class="ss-ul">
				<li style="width: 50px;padding-left:15px;">终端类型</li>
				<li>
					<input id="qclientType" name="clientType" class="easyui-combobox" style="width:120px;"></input>
				</li>
				<li id="li1" style="width: 50px;padding-left:15px;display: none;">网点名称</li>
				<li id="li2" style="display: none;">
				<sys:noptreeselect id="qoutletId" name="qoutletId" options="width:200,panelWidth:200"  idField ="outletId"  textField="outletName" 
						url="comm/listOutletTree.do" pidField="outletType" isParentLevel="false" />
				</li>
				<li id="li3" style="width: 50px;padding-left:15px;display: none;">区域名称</li>
				<li id="li4" style="display: none;">
					<input id="qregionId" name="qregionId"  class="easyui-combobox" style="width:200px;"/>
				</li>
				<li style="width: 50px;padding-left:15px;">终端名称</li>
				<li>
					<input id="clientName" name="clientName" class="easyui-textbox" style="width:180px;"></input>
				</li>
				<li  style="width: 25px;padding-left:15px;">状态</li>
				<li>
					<input id="qstat" name="stat" class="easyui-combobox" style="width:120px;"></input>
				</li>
				<li>
					<a href="javascript:void(0)" onclick="javascript:queryClient();" class="gray2-btn">&nbsp;查询&nbsp;</a>
				</li>
			</ul>
		</div>
		<div class="ss-bar2">
			<ul class="ss-ul">
 				<li>
					<a href="javascript:void(0)" class="red-btn icon-add" onclick="javascript:addClient();">
						<span class="btn-text">新增终端</span>
					</a>
				</li>
				<li>
					<a href="javascript:void(0)" class="red-btn icon-add" onclick="javascript:updateStatus('Y');">
						<span class="btn-text">启用</span>
					</a>
				</li>
				<li>
					<a href="javascript:void(0)" class="red-btn icon-add" onclick="javascript:updateStatus('N');">
						<span class="btn-text">禁用</span>
					</a>
				</li>
				<li>
					<a href="javascript:void(0)" class="red-btn icon-add" onclick="javascript:updateToken();">
						<span class="btn-text">重置授权码</span>
					</a>
				</li>
 			</ul>
		</div>
	</div>
	<div class="it-datagrid" style="height:100%">
		<table id="mytable"></table>
	</div>
	<div id="clientDialog" class="easyui-dialog" style="width: 450px; height: 370px; padding: 5px 10px" modal="true" closed="true" buttons="#dlg-buttons">
	</div>
	<div id="dlg-buttons" style="text-align: center; display: none;padding-bottom:15px;padding-right:60px;">
		<a href="javascript:void(0)" id="save-button" class="blue-btn" onclick="javascript:saveClient('/clientMng/addClient.do')" style="width: 60px; margin-left: 10px;">保存</a>
		<a href="javascript:void(0)" id="update-button" class="blue-btn"  onclick="javascript:saveClient('/clientMng/updateClient.do')" style="width: 60px; margin-left: 10px;">修改</a>
		<a href="javascript:void(0)" class="gray-btn" onclick="javascript:$('#clientDialog').dialog('close');" style="width: 60px; margin-left: 10px;">取消</a>
	</div>
</body>
</html>