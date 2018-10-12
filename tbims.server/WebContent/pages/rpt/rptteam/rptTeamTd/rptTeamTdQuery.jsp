<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="myel" uri="/elFunction"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>团队销售换票统计报表</title>
<%@ include file="/common1.jsp"%>
<style type="text/css">
.report-table1 {
	border-collapse: collapse;
	background: #fff;
}

.report-table1 th {
	background:#f5f5f5;
	height:38px;
	border-right:none;
	vertical-align:middle;
	text-align:center;
	color:#017bbc;
	font-weight: bold;
	padding:0 5px;
}

.report-table1 td {
	padding: 8px;
	color: #696c70;
	border-color: #ddd;
    border-style: solid;
}

.report-table1 td.data {
	color: #323232
}

.report-table1 td.title {
	font-size: 18px;
	font-weight: bold;
	line-height: 180%;
	text-align: center
}
</style>
<script type="text/javascript">
	$(function() {
		$('#changeTime').datebox('setValue','<fmt:formatDate value="${changeTime}" pattern="yyyy-MM-dd" />');
		initCombobox();
		$('#ticketTypeId').combobox('setValue', '${ticketTypeId}');
	});
	function query() {
		if (!$('#form').form('validate')) {
			return false;
		}
		var submitFlag = false;
		if (submitFlag) {
			alert("系统已经在处理您的请求，请不要重复提交");
			return;
		}
		var url = $ctx + '/rptTeam/listRptTeamTd.do';
		$('#form').attr("action", url).submit();
		submitFlag = true;
	}
	function expExcel() {
		if (!$('#form').form('validate')) {
			return false;
		}
		var url = $ctx + '/rptTeam/expExcelTd.do';
		$('#form').attr("action", url).submit();
	}
	function initCombobox() {
		//票种初始化
		$('#ticketTypeId').combobox({
			url : $ctx + '/orgMng/ticketTypeList.do?flag=t',
			method : 'get',
			valueField : 'ticketTypeId',
			textField : 'ticketTypeName',
			multiple : false,
			editable : false,
			onLoadSuccess : function(e) {
			},
			onLoadError : function(e) {
				getAjaxError(e);
			},
			loadFilter : function(data) {
				var ticketLists = [ {
					ticketTypeId : '',
					ticketTypeName : '--全部--'
				} ];
				return $.merge(ticketLists, data);
			}
		});
	}
</script>
</head>
<body style="background-color:#fff">
	<form id="form">
		<ul class="ss-ul">
			<li>销售日期</li>
			<li><input id="changeTime" name="changeTime"
				class="easyui-datebox"  data-options="editable:false"  style="width: 180px;" required="true"/></li>
			<li>票种名称</li>
			<li><input id="ticketTypeId" name="ticketTypeId"
				class="easyui-combobox" style="width: 160px;" /></li>
			<li><a href="javascript:void(0)" onclick="javascript:query();"
				class="gray2-btn" style="width: 35px;">查询</a></li>
			<li><span class="col"><a class="indigo-btn"
					style="margin-left: 10px;" id="btnExcel" onclick="expExcel()">导出EXCEL</a></span></li>
		</ul>
	</form> 
	<br/>
	<div class="zcfg-panel-body">
		<table style="width: 100%; border: 1 ;cellspacing:0;cellpadding:0 ;" class="report-table">
			<thead class="datagrid1-header">
			<tr class="theader">
				<th style="width: 100px;">销售日期</th>
				<th style="width: 100px;">签约社名称</th>
				<th style="width: 100px;">扣减预付款</th>
				<th style="width: 200px;">票种名称</th>
				<th style="width: 200px;">换票数量</th>
				<th style="width: 200px;">扣减额度</th>
			</tr>
			</thead>
			<tbody class="datagrid1-body">
			<c:forEach items="${teamList}" var="bean" varStatus="index">
				<tr>
					<td class="data" align="center"><fmt:formatDate
							value="${bean.teamBean.changeTime}" pattern="yyyy-MM-dd" /></td>
					<td class="data" align="center">${bean.teamBean.orgName}</td>
					<td class="data" align="center">${bean.teamBean.minusAdvanceAmt}</td>
					<td colspan="3" style="padding:0;margin:0;width: 600px;">
							<table style="width: 100%; cellspacing:0;cellpadding:0 ;border-style:hidden;" class="report-table1">
								<c:forEach items="${bean.orderInfo}" var="info">
									<tr>
										<td style="width: 200px;" align="center">${info.ticketTypeName}</td>
										<td style="width: 200px;" align="center">${info.changeNum}</td>
										<td style="width: 200px;" align="center">${info.minusLimit}</td>
									</tr>
								</c:forEach>
						</table>
					</td>
				</tr>
			</c:forEach>
			</tbody>
		</table>
 	</div> 
</body>
</html>