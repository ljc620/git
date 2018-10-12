<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="myel" uri="/elFunction"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>代理商量销售登记</title>
<%@ include file="/common.jsp"%>
<script type="text/javascript" src="saleRegMng.js"></script>
</head>
<body>
	<div id="mytable-buttons">
		<div class="ss-bar">
			<ul class="ss-ul">
				<li style="width: 50px; padding-left: 15px;">票种</li>
				<li>
					<input id="qticketTypeId" name="qticketTypeId" style="width: 160px;"></input>
				</li>
				<li>起止日期</li>
				<li>
					<input id="startDt" name="startDt" class="easyui-datebox" style="width: 120px;" value="<%=com.zhming.support.util.DateUtil.getNowDate("yyyy-MM-dd")%>"></input>
					-
					<input id="endDt" name="endDt" class="easyui-datebox" style="width: 120px;" value="<%=com.zhming.support.util.DateUtil.getNowDate("yyyy-MM-dd")%>"></input>
				</li>
				<li>
					<a href="javascript:void(0)" onclick="javascript:myQuery();" class="ss-btn">&nbsp;</a>
				</li>
			</ul>
		</div>
		<div class="ss-bar2">
			<ul class="ss-ul">
				<li>
					<a href="javascript:void(0)" class="red-btn icon-add" onclick="javascript:openAddSaleRegWin();">
						<span class="btn-text">销售登记</span>
					</a>
				</li>
			</ul>
		</div>
	</div>
	<div class="it-datagrid" style="height: 100%">
		<table id="mytable"></table>
	</div>
	<div id="saleRegDialog" class="easyui-dialog" style="width: 450px; height: 250px; padding: 5px" modal="true" closed="true"></div>
	<div id="saleRegDialog-buttons" style="display: none;">
		<a href="javascript:void(0)" id="save-button" class="blue-btn" onclick="javascript:saveUser('/saleReg/saveSaleReg.do')" style="width: 90px; margin-right: 10px; ">保存</a>
		<a href="javascript:void(0)" id="update-button" class="blue-btn" onclick="javascript:saveUser('/saleReg/updateSaleReg.do')" style="width: 90px; margin-right: 10px; ">修改</a>
		<a href="javascript:void(0)" class="gray-btn" onclick="javascript:$('#saleRegDialog').dialog('close');" style="width: 90px; ">取消</a>
	</div>
</body>
</html>