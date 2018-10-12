<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="myel" uri="/elFunction"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"></c:set>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>修改区域</title>
<%@ include file="/common.jsp"%>
<script type="text/javascript" src="regionMng.js"></script> 
</head>
<body>
	<div> 
		<form id="fm" method="post" class="bf-from">
			<table class="form-table" style="width: 90%;">
			<tr>
					<td class="label">区域编号</td>
					<td >
						<input name="regionId" id="regionId" value="${sysRegion.regionId}" class="easyui-textbox" style="width:150px;" readonly required="true"></input>
						<span class="starStyle">*</span>
					</td>
			</tr>
			<tr>
					<td class="label">区域名称</td>
					<td >
						<input name="regionName" id="regionName" value="${sysRegion.regionName}" class="easyui-textbox"style="width:150px;" required="true"></input>
						<span class="starStyle">*</span>
					</td>
			</tr>
			<tr>
				<td class="label">馆场</td>
					<td >
						<input class="easyui-combobox"  id="venueId" name="venueId" value="${sysRegion.venueId}" style="width:150px;" data-options="  
                                url: '<%=path%>/ticketTypeMng/venueList.do',   
                                method:'get',  
                                valueField:'venueId',  
                                textField:'venueName',  
                                multiple:false,  
                                editable:false,  
                                panelHeight:'auto',required:true  
                        " />
					</td>
			</tr>
				<tr>
					<td class="label">地点</td>
					<td >
						<input name="location" id="location" value="${sysRegion.location}" class="easyui-textbox" style="width:150px;"></input>
					</td>
				</tr>
				<tr>
					<td class="label">联系电话</td>
					<td >
						<input name="tel" id="tel" value="${sysRegion.tel}" class="easyui-textbox" data-options="validType:['mobile']" style="width:150px;"></input>
					</td>
				</tr>
				<tr>
					<td class="label">负责人</td>
					<td>
						<input name="leader" id="leader" value="${sysRegion.leader}" class="easyui-textbox"style="width:150px;"></input>
					</td>
				</tr>
			</table>
		</form>
		</div>
		</body>
		</html>