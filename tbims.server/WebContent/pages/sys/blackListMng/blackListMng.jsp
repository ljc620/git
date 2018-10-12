<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="myel" uri="/elFunction"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>黑名单管理</title>
<%@ include file="/common.jsp"%>


<script type="text/javascript" src="blackListMng.js"></script>
</head>
<body>
	<div id="mytable-buttons">
		<div class="ss-bar">
		<ul class="ss-ul">
				
				<li style="width: 50px;padding-left:15px;">票号:</li>
				<li>
					<input id="ticketId" name="ticketId" class="easyui-numberbox"  data-options="validType:'length[1,12]'" style="width:160px;"></input>
				</li>
				<li>
					<a href="javascript:void(0)" onclick="javascript:myQuery();" class="gray2-btn enter">&nbsp;查询&nbsp;</a>
				</li>
			</ul>
			<ul class="ss-ul">
				<li>
					<a href="javascript:void(0)" class="red-btn icon-add" onclick="javascript:addBlackList();">
						<span class="btn-text">新增黑名单</span>
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
			</ul>
		</div>
	</div>
	<div class="it-datagrid" style="height:100%">
		<table id="mytable"></table>
	</div>
	<div id="blackDialog" class="easyui-dialog" style="width: 600px; height: 250px; padding: 5px 10px" modal="true" closed="true">
	</div>
	<div id="dlg-buttons" style="text-align: center; display: none;">
		<a href="javascript:void(0)" id="save-button" class="blue-btn" onclick="javascript:saveBlack('/blackListMng/addBlackList.do')"  style="width: 90px; margin-left: 10px;">保存</a>
		<a href="javascript:void(0)" class="gray-btn" onclick="javascript:$('#blackDialog').dialog('close');" style="width: 90px; margin-left: 10px;">取消</a>
	</div>
</body>
</html>