<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="myel" uri="/elFunction"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"></c:set>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>修改预售期</title>
<%@ include file="/common.jsp"%>
<script type="text/javascript" src="periodMng.js"></script> 
</head>
<body>
	<div> 
		<form id="fm" method="post" class="bf-from">
			<input type="hidden" name="salePeriodId" id="salePeriodId" value="${slPeriod.salePeriodId}"/>
			<table class="form-table" style="width: 90%;">
			
				<tr>
					<td class="label">预售期名称</td>
					<td >
						<input name="salePeriodName" id="salePeriodName" value="${slPeriod.salePeriodName}" class="easyui-textbox" readonly style="width:150px;" ></input>
					</td>
				</tr>
				<tr>
				<td class="label">票种名称</td>
					<td >
						
                        <input name="ticketTypeId" id="ticketTypeId" value="${slPeriod.ticketTypeId}" class="easyui-combobox" style="width:150px;" data-options="  
                                url: '<%=path%>/orgMng/ticketTypeList.do?flag=a',   
                                method:'get',  
                                valueField:'ticketTypeId',  
                                textField:'ticketTypeName',  
                                multiple:false,  
                                editable:false,  
                                panelHeight:'auto',required:true" ></input>
                                <span class="starStyle">*</span>
					</td>
				<tr>
					<td class="label">预售期开始日期</td>
					<td >
						<input name="beginDt" id="beginDt" value="${slPeriod.beginDt}" class="easyui-datebox"  data-options="editable:false"  readonly style="width:150px;" ></input>
					</td>
				</tr>
				<tr>
					<td class="label">预售期结束日期</td>
					<td style="width:200px;">
						<input name="endDt" id="endDt" value="${slPeriod.endDt}" class="easyui-datebox"  data-options="editable:false"  readonly style="width:150px;" ></input>
					</td>
				</tr>
				<tr>
					<td class="label">折后票价（元）</td>
					<td>
						<input name="discountPrice" id="discountPrice" value="${slPeriod.discountPrice}" class="easyui-numberbox" validType="length[1,5]" style="width:150px;" required="true"></input>
					</td>
				</tr>
			</table>
		</form>
		
		</body>
		</html>