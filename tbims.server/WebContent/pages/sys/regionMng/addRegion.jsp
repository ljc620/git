<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="myel" uri="/elFunction"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"></c:set>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>新增检票区域</title>
<%@ include file="/common.jsp"%>
<script type="text/javascript" src="${path}/pages/sys/regionMng/regionMng.js"></script> 

</head>
<body>
	<div> 
		<form id="fm" method="post" class="bf-from">
			<table class="form-table" style="width: 90%;">
			    <tr>
					<td class="label" style="width:150px;">区域编号</td>
					<td colspan="3">
						<input name="regionId" id="regionId" class="easyui-numberbox" validType="length[2,2]" 
						precision="0" style="width:150px;" required="true"></input>	
						<span class="starStyle">*</span>
					</td>
				</tr>
				 <tr>
					<td class="label" style="width:150px;">区域名称</td>
					<td colspan="3">
						<input name="regionName" id="regionName" class="easyui-textbox" style="width:150px;" required="true"></input>
						<span class="starStyle">*</span>	
					</td>
				</tr>
				<tr>
					<td class="label" style="width:150px;">负责人</td>
					<td colspan="3">
						<input name="leader" id="leader" class="easyui-textbox" style="width:150px;"required="true" ></input>
						<span class="starStyle">*</span>
					</td>
				</tr>
				<tr>
					<td class="label" >场馆</td>
					<td>
						<input class="easyui-combobox"  id="venueId" name="venueId" style="width:150px;"required="true" data-options="  
                                url: '<%=path%>/ticketTypeMng/venueList.do',   
                                method:'get',  
                                valueField:'venueId',  
                                textField:'venueName',  
                                multiple:false,  
                                editable:false,  
                                panelHeight:'auto',required:true  
                        " />
                        <span class="starStyle">*</span>
					</td>
				</tr>
				<tr>
					<td  class="label" style="width: 120px;">联系电话</td>
					<td colspan="3">
						<input name="tel" id="tel" class="easyui-textbox" style="width:150px;" data-options="validType:['mobile']" required="true"></input>
						<span class="starStyle">*</span>					
					</td>
				</tr>
				<tr>
					<td class="label" style="width:150px;">地点</td>
					<td colspan="3">
						<input name="location" id="location" class="easyui-textbox"style="width:150px;" ></input>
					</td>
				</tr>
			</table>
		</form>
</div>

</body>
</html>