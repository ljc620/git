<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="myel" uri="/elFunction"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"></c:set>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>新增预售期</title>
<%@ include file="/common.jsp"%>
<script type="text/javascript" src="${path}/pages/sys/periodMng/periodMng.js"></script>


</head>
<body>
	<div> 
		<form id="fm" method="post" class="bf-from">
			<table class="form-table" style="width: 90%;">
			   <tr>
					<td class="label" style="width:150px;">预售期名称</td>
					<td colspan="3">
						<input name="salePeriodName" id="salePeriodName" class="easyui-textbox" style="width:150px;" required="true"></input>
						<span class="starStyle">*</span>	
					</td>
				</tr><tr>
					<td class="label" style="width:150px;">预售期开始日期</td>
					<td colspan="3">
						<input name="beginDt" id="beginDt" class="easyui-datebox"  data-options="editable:false"  style="width:150px;" required="true"></input>
						<span class="starStyle">*</span>
					</td>
				</tr>
				
				<tr>
					<td class="label" style="width:150px;">预售期结束日期</td>
					<td colspan="3">
						<input name="endDt" id="endDt" class="easyui-datebox"  data-options="editable:false"  style="width:150px;"required="true" ></input>
						<span class="starStyle">*</span>
					</td>
				</tr>
				  <tr>
					<td class="label" style="width:150px;">票种</td>
					<td colspan="3">
						<input name="ticketTypeId" id="ticketTypeId" class="easyui-combobox" style="width:150px;" data-options="  
							editable:false,panelHeight:'100px',
                                url: '<%=path%>/orgMng/ticketTypeList.do?flag=nt',   
                                method:'get',  
                                valueField:'ticketTypeId',  
                                textField:'ticketTypeName',  
                                multiple:false,  
                                editable:false,  
                                required:true" ></input>
                                <span class="starStyle">*</span>
					</td>
				</tr>
				<tr>
					<td  class="label" style="width: 120px;">折后票价（元）</td>
					<td colspan="3">
						<input name="discountPrice" id="discountPrice" class="easyui-numberbox" validType="length[1,5]"style="width:150px;"required="true"></input>
					</td>
				</tr>
			</table>
		</form>
</div>

</body>
</html>