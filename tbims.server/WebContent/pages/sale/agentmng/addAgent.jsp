<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="myel" uri="/elFunction"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"></c:set>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>新增代理商</title>
<%@ include file="/common.jsp"%>
<script type="text/javascript" src="orgMng.js"></script>
</head>
<body>
	<div>
		<form id="fm" method="post" class="bf-from">
			<table class="form-table" style="width: 90%;">
				<tr>
					<td class="label" style="width: 150px;">组织机构代码</td>
					<td colspan="3"><input name="orgId" id="orgId"
						class="easyui-textbox" style="width: 300px;"
						data-options="validType:['chineseCheck']" validType="length[2,20]"
						required="true"></input> <span class="starStyle">*</span></td>
				</tr>
				<tr>
					<td class="label" style="width: 150px;">机构名称</td>
					<td colspan="3"><input name="orgName" id="orgName"
						class="easyui-textbox" validType="length[2,250]"
						style="width: 300px;" required="true"></input> <span
						class="starStyle">*</span></td>
				</tr>
				<tr>
					<td class="label" style="width: 120px;">法人代表</td>
					<td colspan="3"><input name="legal" id="legal"
						class="easyui-textbox" validType="length[2,40]"></input></td>
				</tr>
				<tr>
					<td class="label" style="width: 120px;">机构所在地</td>
					<td colspan="3"><input name="location" id="location"
						class="easyui-textbox" validType="length[2,250]"
						style="width: 300px;"></input></td>
				</tr>
				<tr>
					<td class="label" style="width: 120px;">联系人</td>
					<td><input name="contact" id="contact" class="easyui-textbox"
						validType="length[2,40]"></input></td>
					<td class="label" style="width: 120px;">联系电话</td>
					<td><input name="tel" id="tel" class="easyui-textbox"
						style="width: 200px;" data-options="validType:['mobile']"></input>
					</td>
				</tr>
				<tr>
					<td class="label" style="width: 120px;">代理商类型</td>
					<td><select name="orgType" id="orgType"
						class="easyui-combobox" style="width: 120px;"
						data-options="editable:false,panelHeight:'auto'" readonly="true"
						required="true">
							<option value="1">网络代理商</option>
							<!-- 	<option value="2">实体代理商</option> -->
					</select> <span class="starStyle">*</span></td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<!-- 					<td class="label" style="width: 120px;">信用额度(元)</td> -->
					<!-- 					<td> -->
					<!-- 						<input name="creditAmt" id="creditAmt" class="easyui-numberbox" data-options="validType:['num']" max="999999999999" -->
					<!-- 						precision="0"  required="true" style="width:200px;"></input> -->
					<!-- 					</td> -->
				</tr>
			</table>
		</form>
	</div>
</body>
</html>