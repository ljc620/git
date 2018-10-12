<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="myel" uri="/elFunction"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>网点管理</title>
<%@ include file="/common.jsp"%>
<script type="text/javascript" src="venueMng.js"></script>
<script type="text/javascript" src="${path}/js/common/commcheck.js"></script>

</head>
<body>
	<div id="mytable-buttons">
		<div class="ss-bar">
			<ul class="ss-ul">
				<li style="width: 50px;padding-left:15px;">场馆名称</li>
				<li>
					<input id="qvenueName" name="venueName" class="easyui-textbox" style="width:180px;"></input>
				</li>
				<li>
					<a href="javascript:void(0)" onclick="javascript:myQuery();" class="ss-btn enter">&nbsp;</a>
				</li>
			</ul>
		</div>
		<div class="ss-bar2">
			<ul class="ss-ul">
				<li>
					<a href="javascript:void(0)" class="red-btn icon-add" onclick="javascript:addVenue();">
						<span class="btn-text">新增场馆</span>
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
			</ul>
		</div>
	</div>
	<div class="it-datagrid" style="height:100%">
		<table id="mytable"></table>
	</div>
	<div id="venueDialog" class="easyui-dialog" style="width: 400px; height: 300px; padding: 5px 10px" modal="true" closed="true" buttons="#dlg-buttons">
	</div>
	<div id="dlg-buttons" style="text-align: center; display: none;">
		<a href="javascript:void(0)" id="save-button" class="blue-btn" onclick="javascript:saveVenue('/venueMng/addVenue.do')" style="width: 90px; margin-left: 10px;">保存</a>
		<a href="javascript:void(0)" id="update-button" class="blue-btn"  onclick="javascript:saveVenue('/venueMng/updateVenue.do')" style="width: 90px; margin-left: 10px;">修改</a>
		<a href="javascript:void(0)" class="gray-btn" onclick="javascript:$('#venueDialog').dialog('close');" style="width: 90px; margin-left: 10px;">取消</a>
	</div>
</body>
</html>