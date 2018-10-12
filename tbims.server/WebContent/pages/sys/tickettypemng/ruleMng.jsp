<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="myel" uri="/elFunction"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>票种规则管理</title>
<%@ include file="/common.jsp"%>
<script type="text/javascript" src="${path}/pages/sys/tickettypemng/ruleMng.js"></script>

</head>
<body>

	<div id="mytable-buttons">
	<form id="fm" method="post" class="bf-from">
		<div class="ss-bar">
			<ul class="ss-ul">
				<li>票种编号：${sysTicketType.ticketTypeId}
				 <input type="hidden" id="ticketTypeId" name="ticketTypeId"
						value="${sysTicketType.ticketTypeId}" />
				</li>
				<li>票种名称：${sysTicketType.ticketTypeName}
				</li>

			</ul>
		</div>
		<c:if
				test="${myel:hasPriv('i2_sys_ticket_type_rule_add',userSession.functionSet)}">
		<div class="ss-bar2" >
			
			<ul class="ss-ul">
			<li>有效开始日期</li>
					<li>
						<input name="beginDt" id="beginDt" style="width:100px" class="easyui-datebox"  data-options="editable:false" ></input>
					</li>
					<li>有效结束日期</li>
					<li>
						<input name="endDt" id="endDt" style="width:100px"  class="easyui-datebox"  data-options="editable:false" ></input>
					</li>
					<li>有效开始时间</li>
					<li>
						<input name="beginTime" id="beginTime"  style="width:80px" class="easyui-timespinner"  data-options="min:'00:00:00',showSeconds:true"></input>
					</li>
					<li>有效结束时间</li>
					<li>
						<input name="endTime" id="endTime" style="width:80px" class="easyui-timespinner"  data-options="min:'00:00:00',showSeconds:true"></input>
					</li>
				</ul>
			<table class="form-table" style="padding-top:5px">
			<tr>   
					<td >星期日</td><td><input type="checkbox" name="w0" id="w0" ></td>
					<td style="padding-left:5px">星期一</td><td><input type="checkbox" name="w1" id="w1" ></td>
					<td style="padding-left:5px">星期二</td><td><input type="checkbox" name="w2" id="w2"></td>
					<td style="padding-left:5px">星期三</td><td><input type="checkbox" name="w3" id="w3"></td>
					<td style="padding-left:5px">星期四</td><td><input type="checkbox" name="w4" id="w4"></td>
					<td style="padding-left:5px">星期五</td><td><input type="checkbox" name="w5" id="w5"></td>
					<td style="padding-left:5px">星期六</td><td><input type="checkbox" name="w6" id="w6" ></td>  
			<td style="padding-left:20px">全选</td><td><input type="checkbox" name="checkall" id="checkall"></td>
			<td><a href="javascript:void(0)" id="save-button" class="blue-btn" onclick="javascript:addTypeRule()" style="width: 50px; margin-right: 10px;margin-left:60px;">添加</a></td>
			</tr>
          </table>
		</div>
		</c:if>
		</form>
	</div>
	<div class="it-datagrid" style="height:100%">
		<table id="ruletable"></table>
	</div>
</body>
</html>