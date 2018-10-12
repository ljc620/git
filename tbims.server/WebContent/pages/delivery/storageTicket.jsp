<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="myel" uri="/elFunction"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>库存明细查询</title>
<%@ include file="/common.jsp"%>
<script type="text/javascript" src="storageTicket.js"></script>
</head>
<body>
	<div id="mytable-buttons">
		<div class="ss-bar">
			<ul class="ss-ul">
				
				<li style="width: 50px;padding-left:15px;">箱号:</li>
				<li>
					<input id="chestId" name="chestId" class="easyui-textbox"  data-options="validType:'length[1,12]'" style="width:160px;"></input>
				</li>
				<li  style="width: 50px;padding-left:15px;">票种名称</li>
					<li>
						<input id="ticketTypeId" name="ticketTypeId" class="easyui-combobox" style="width:120px;"></input>
					</li>
				<li>
					<a href="javascript:void(0)" onclick="javascript:myQuery();" class="gray2-btn">&nbsp;查询&nbsp;</a>
				</li>
			</ul>
		</div>
	</div>
	<div class="it-datagrid" style="height:100%">
		<table id="mytable"></table>
	</div>
	
</body>
</html>