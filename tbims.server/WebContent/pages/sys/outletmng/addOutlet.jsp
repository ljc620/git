<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sys" tagdir="/WEB-INF/tags/sys" %>
<%@ taglib prefix="myel" uri="/elFunction"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"></c:set>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>新增网点</title>
<%@ include file="/common.jsp"%>
<script type="text/javascript" src="outletMng.js"></script> 
</head>
<body>
	<div> 
		<form id="fm" method="post" class="bf-from">
			<table class="form-table" style="width: 90%;">
			<tr>
					<td class="label">网点编号</td>
					<td >
						<input name="outletId" id="outletId" class="easyui-numberbox"  max="500" min="100"
						precision="0"  style="width:160px;" required="true"></input>
						<span class="starStyle">*</span>
					</td>
				
					<td class="label">网点名称</td>
					<td >
						<input name="outletName" id="outletName" class="easyui-textbox" style="width:160px;" required="true"></input>
						<span class="starStyle">*</span>
					</td>
				</tr>
				<tr>
					<td class="label">地点</td>
					<td>
						<input name="location" id="location" class="easyui-textbox" style="width:160px;"></input>
					</td>
					<td class="label" style="width: 120px;">负责人</td>
					<td>
						<input name="leader" id="leader" class="easyui-textbox" style="width:160px;"></input>
					</td>
				</tr>
				<tr>
					<td class="label" style="width: 120px;">联系电话</td>
					<td>
						<input name="tel" id="tel" class="easyui-textbox" data-options="validType:['mobile']" style="width:160px;"></input>
					</td>
					<td class="label" style="width: 120px;">网点类型</td>
					<td colspan="3">
						<input name="outletType" id="outletType" class="easyui-combobox" style="width:160px;" data-options="  editable:false,panelHeight:'auto',
                                url: '<%=path%>/comm/listItemsByKeyOutletMng.do?key=OUTLET_TYPE',   
                                method:'get',  
                                valueField:'id',  
                                textField:'text',  
                                multiple:false,  
                                editable:false,
                                required:true  
                        "></input>
                        <span class="starStyle">*</span>
					</td>
				</tr>
				<%-- <tr>
					<td class="label" style="width: 120px;">所属机构</td>
					<td colspan="3">
					<sys:cascadeselect valueField="orgId"  textField="orgName" url="outletMng/orgList.do" name="orgId" id="orgId"
						options="width:150,editable:false" paramName="orgType" parentId="outletType"  isParentLevel="false"   />
					</td>
				</tr> --%>
			</table>
		</form>
	</div>
</body>
			