
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/ul/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>公告信息</title>
<%@ include file="/common.jsp"%>
<script type="text/javascript">
$(function() {
	var outletId='${param.outletId}';
	var startDate='${param.startDate}';
	var endDate='${param.endDate}';
	var stat='${param.stat}';
	$myTable = $('#detailtable');
	createTable(outletId,startDate,endDate,stat);
});

function createTable(outletId,startDate,endDate,stat) {
	$myTable.datagrid({
		nowrap : true,// 是否只显示一行，即文本过多是否省略部分。
		url : $ctx + '/ticketConfirm/showTicketInfo.do', // controller地址
		onLoadSuccess : function(e) {
		},
		onLoadError : function(e) {
			getAjaxError(e);
		},
		fitColumns : false,
		rownumbers : true,
		striped : true,
		pagination : true,
		fit:true,
		queryParams:{
			outletId:outletId,
			startDate:startDate,
			endDate:endDate,
			stat:stat
		},
		columns : [ [// 显示的列       
         {
 			field : 'ticketId',
 			title : '票号',
 			width : 120,
 			align : 'center'
 		},
 		{
			field : 'outletName',
			title : '网点名称',
			width : 150,
			align : 'center'
		},
		{
			field : 'ticketTypeName',
			title : '门票种类',
			width : 150,
			align : 'center',
		},
		{
			field : 'uselessTime',
			title : '作废时间',
			width : 150,
			align : 'center',
		},
		{
			field : 'userlessUserName',
			title : '作废人',
			width : 150,
			align : 'center' 
		}, {
			field : 'uselessReason',
			title : '作废原因',
			width : 200,
			align : 'center' 
		}] ],
		toolbar : '#mytable-buttons'
	});
}
</script>
</head>
<body>
	<div class="it-datagrid" style="height:100%">
		<table id="detailtable"></table>
	</div>
</body>
</html>