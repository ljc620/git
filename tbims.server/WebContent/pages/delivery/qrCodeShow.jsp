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
				<li style="width: 50px;padding-left:15px;">票种名称</li>
				<li>
					<input id="ticketTypeId" name="ticketTypeId" class="easyui-combobox" style="width:150px;"></input>
				</li>
				<!-- <li style="width: 50px; padding-left: 15px;">生成日期</li> -->
				<li>
					开始日期：
					<input id="makeQrTimeBegin" name="makeQrTimeBegin" class="easyui-datebox"  data-options="editable:false"  style="width: 110px;" value="true" />
					&nbsp;&nbsp;结束日期：
					<input id="makeQrTimeEnd" name="makeQrTimeEnd" class="easyui-datebox"  data-options="editable:false"  style="width: 110px;" value="true" />
				</li>
				<li>
					<a href="javascript:void(0)" onclick="javascript:queryCode();" class="gray2-btn">&nbsp;查询&nbsp;</a>
				</li>
 				<!-- <li>
					<a class="indigo-btn" id="btnExcel"  id="btnExcel" onclick="expExcel()">导出Excel</a>
				</li> -->
				
			</ul>
			<ul class="ss-ul">
				<li>
					<a href="javascript:void(0)" onclick="javascript:makeCode();" class="indigo-btn">&nbsp;生成二维码&nbsp;</a>
				</li>
			</ul>
		</div>
	</div>
	
	<div class="it-datagrid" style="height:100%">
		<table id="mytable"></table>
	</div>
	<div id="qrDialog" class="easyui-dialog" style="width: 600px; height: 350px; padding: 5px 10px" modal="true" closed="true" buttons="#qr-buttons">
	</div>
	<div id="qr-buttons" style="text-align: center; display: none;">
		<a href="javascript:void(0)" id="save-button" class="blue-btn" onclick="javascript:saveQr('/orgMng/addOrg.do')" style="width: 90px; margin-right: 10px;margin-left:90px;">保存</a>
		<!-- <a href="javascript:void(0)" id="update-button" class="blue-btn"  onclick="javascript:saveOrg('/orgMng/updateOrg.do')" style="width: 90px; margin-right: 10px;">修改</a> -->
		<a href="javascript:void(0)" class="gray-btn" onclick="javascript:$('#qrDialog').dialog('close');" style="width: 90px; margin-left: 10px;">取消</a>
	</div>
</body>
</html>