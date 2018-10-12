<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="myel" uri="/elFunction"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>二维码生成</title>
<%@ include file="/common.jsp"%>
<script type="text/javascript" src="qrcs.js"></script>

</head>
<body>
	<div id="mytable-buttons">
		<div class="ss-bar">
			<ul class="ss-ul">
				<li style="width: 25px;padding-left:15px;">票种</li>
				<li>
					<input id="ticketType" name="examStat" class="easyui-combobox" style="width:120px;"></input>
				</li>
				<li style="width: 50px;padding-left:15px;">起始票号</li>
				<li>
					<input id="beginTicketNum" name="applyId" class="easyui-textbox" style="width:160px;"></input>
				</li>
				<li style="width: 50px;padding-left:15px;">截止票号</li>
				<li>
					<input id="endTicketNum" name="applyId" class="easyui-textbox" style="width:160px;"></input>
				</li>
				<li>
					<a href="javascript:void(0)" onclick="javascript:makeCode();" class="gray2-btn">&nbsp;生成二维码&nbsp;</a>
				</li>
				<!-- <li>
					<a href="javascript:void(0)" onclick="javascript:exportData();" class="gray2-btn">&nbsp;导出TXT&nbsp;</a>
				</li> -->
				<li>
					<a class="indigo-btn" id="btnExcel"  id="btnExcel" onclick="expExcel()">导出TXT123</a>
				</li>
			</ul>
		</div>
	</div>
	
	<div class="it-datagrid" style="height:100%">
		<table id="mytable"></table>
	</div>
	<!-- 
	<div id="deliverConDialog" class="easyui-dialog" style="width: 800px; height: 500px; padding: 5px" modal="true" closed="true" >
	</div>
	<div id="detailDialog" class="easyui-dialog" style="width: 800px; height: 500px; padding: 5px" modal="true" closed="true" >
	</div>
	 -->
</body>
</html>