<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="myel" uri="/elFunction"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"></c:set>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>添加票种</title>
<%@ include file="/common.jsp"%>
<script type="text/javascript" src="addTicketType.js"></script>
</head>
<body style="overflow-y: auto">
	<div>
		<p class="item-title"
			style="height: 24px; line-height: 24px; margin-top: 5px">基本信息</p>
		<form id="fm" method="post" class="bf-from">
			<table class="form-table" style="width: 90%;">
				<tr>
					<td class="label">票种编号</td>
					<td>
						<input name="ticketTypeId" id="ticketTypeId"
							class="easyui-numberbox" validType="length[2,3]" required="true"></input>
						<span class="starStyle">*</span>
					</td>
					<td class="label">票种名称</td>
					<td>
						<input name="ticketTypeName" id="ticketTypeName"
							class="easyui-textbox" validType="length[2,150]" required="true"></input>
						<span class="starStyle">*</span>
					</td>
					<td class="label">次数</td>
					<td>
						<input name="validateTimes" id="validateTimes"
							class="easyui-numberbox" validType="length[1,2]" required="true"></input>
						<span class="starStyle">*</span>
					</td>

				</tr>
				<tr>
					<td class="label">销售日当日有效</td>
					<td>
						<select name="dayValidateFlag" id="dayValidateFlag"
							class="easyui-combobox"
							data-options="editable:false,panelHeight:'auto'"
							style="width: 120px;" required="true">
							<option value="Y">是</option>
							<option value="N">否</option>
						</select>
					</td>
					<td class="label">票价</td>
					<td>
						<input name="price" id="price" class="easyui-numberbox" min="0"
							max="9999" precision="0" required="true">
						<span class="starStyle">*</span>
					</td>
					<td class="label">是否团体</td>
					<td>
						<select name="teamFlag" id="teamFlag" class="easyui-combobox"
							data-options="editable:false,panelHeight:'auto'"
							style="width: 120px;" required="true">
							<option value="Y">是</option>
							<option value="N">否</option>
						</select>
						<span class="starStyle">*</span>
					</td>
				</tr>
				<tr>
					<td class="label">是否优惠</td>
					<td>
						<select name="lessFlag" id="lessFlag" class="easyui-combobox"
							data-options="editable:false,panelHeight:'auto'"
							style="width: 120px;" required="true">
							<option value="Y">是</option>
							<option value="N">否</option>
						</select>
						<span class="starStyle">*</span>
					</td>
					<td class="label">日夜场</td>
					<td>
						<select name="dayNightFlag" id="dayNightFlag"
							class="easyui-combobox"
							data-options="editable:false,panelHeight:'auto'"
							style="width: 120px;" required="true">
							<option value="D" selected>日场</option>
							<option value="N">夜场</option>
						</select>
						<span class="starStyle">*</span>
					</td>
					<td class="label">场馆</td>
					<td>
						<input class="easyui-combobox" id="venueIds" name="venueIds"
							style="width: 120px;"
							data-options="  
                                url: '<%=path%>/ticketTypeMng/venueList.do',   
                                method:'get',  
                                valueField:'venueId',  
                                textField:'venueName',  
                                multiple:true,  
                                editable:false,  
                                panelHeight:'auto',required:true  
                        " />
						<span class="starStyle">*</span>
					</td>

				</tr>

			</table>
			<input type="hidden" name="ruleListStr" id="ruleListStr" />
			<p class="item-title"
				style="height: 24px; line-height: 24px; margin-top: 5px">规则信息</p>
			<table id="mytable-buttons" class="form-table" style="width: 90%;">

				<tr>
					<td class="label" colspan="2">有效开始日期</td>
					<td colspan="2">
						<input name="beginDt" id="beginDt" class="easyui-datebox"  data-options="editable:false" ></input>
					</td>
					<td class="label" colspan="2">有效结束日期</td>
					<td colspan="2">
						<input name="endDt" id="endDt" class="easyui-datebox"  data-options="editable:false" ></input>
					</td>
					<td class="label" colspan="2">有效开始时间</td>
					<td colspan="2">
						<input name="beginTime" id="beginTime" class="easyui-timespinner"
							data-options="showSeconds:true"></input>
					</td>
					<td class="label" colspan="2">有效结束时间</td>
					<td class="label" colspan="2">
						<input name="endTime" id="endTime" class="easyui-timespinner"
							data-options="showSeconds:true"></input>
					</td>
					<td>
					</td>
				</tr>
				<tr>
				   
					<td class="label">星期日</td>
					<td><input type="checkbox" name="w0" id="w0" ></td>
					<td class="label">星期一</td>
					<td><input type="checkbox" name="w1" id="w1" ></td>
					<td class="label">星期二</td>
					<td><input type="checkbox" name="w2" id="w2"></td>
					<td class="label">星期三</td>
					<td><input type="checkbox" name="w3" id="w3"></td>
					<td class="label">星期四</td>
					<td><input type="checkbox" name="w4" id="w4"></td>
					<td class="label">星期五</td>
					<td><input type="checkbox" name="w5" id="w5"></td>
					<td class="label">星期六</td>
					<td><input type="checkbox" name="w6" id="w6" ></td>
				    <td class="label">全选</td>
				    <td><input type="checkbox" name="checkall" id="checkall"></td>
					<td>
						<a href="javascript:void(0)" id="save-button" class="blue-btn"
							onclick="javascript:addRule()"
							style="width: 50px; margin-right: 10px; margin-left: 90px;">添加</a>
					</td>
				</tr>
			</table>
		</form>
	</div>
	<div class="it-datagrid" style="height: 200px">
		<table id="ruletable" class="easyui-datagrid">
		</table>
	</div>
	<div id="dlg-buttons" class="window-btn-bar">
		<a href="javascript:void(0)" id="save-button" class="blue-btn"
			onclick="javascript:saveTicketType()"
			style="width: 90px; margin-right: 10px; margin-left: 90px;">保存</a> <a
			href="javascript:void(0)" class="gray-btn"
			onclick="javascript:closeCurrTab('新增票种');"
			style="width: 90px; margin-left: 10px;">取消</a>
	</div>
</body>
</html>