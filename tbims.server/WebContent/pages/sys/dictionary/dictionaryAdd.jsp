<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="myel" uri="/elFunction"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"></c:set>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>字典管理</title>
<%@ include file="/common.jsp"%>
<script type="text/javascript" src="dictionaryMng.js"></script> 
</head>
<body>
	<div>
		<form id="fm" method="post">
			<table class="form-table" style="width: 100%; height: 100%" >
				<tr>
					<td class="label" style="width: 80px;">字段名：</td>
					<td>
						<input name="keyCd" id="keyCd" class="easyui-textbox" required="true" data-options="width:130"/>
						<span class="starStyle">*</span>
					</td>
					<td class="label" style="width: 80px;">字段中文名：</td>
					<td>
						<input name="keyName" id="keyName" class="easyui-textbox" required="true" data-options="width:130"/>
						<span class="starStyle">*</span>
					</td>
				</tr> 
				<tr>
					<td class="label">数据项代码：</td>
					<td>
						<input name="itemCd" id="itemCd" class="easyui-textbox" required="true" data-options="width:130"/>
						<span class="starStyle">*</span>
					</td>
					<td class="label">数据项名称：</td>
					<td>
						<input name="itemName" id="itemName" class="easyui-textbox" required="true" data-options="width:130"/>
						<span class="starStyle">*</span>
					</td>
				</tr> 
				<tr>
					<td class="label">状态：</td>
					<td>
						<input name="stat" id="stat" />
						<span class="starStyle">*</span>
					</td>
					<td class="label">序号：</td>
					<td>
						<input name="orderNum" id="orderNum" class="easyui-numberbox"   validType="length[1,3]"  data-options="width:130,required:true"/>
						<span class="starStyle">*</span>
					</td>
				</tr> 
				<tr>
					<td class="label">数据项值：</td>
					<td colspan="3">
						<input name="itemVal" id="itemVal" class="easyui-textbox" data-options="width:379,required:true"/>
						<span class="starStyle">*</span>
					</td>
				</tr>
				<tr>
					<td colspan='4' style="height:15px;text-align:right;">
						<span class="starStyle">*</span>
						<span style="color:red;">项为必输项</span>
					</td>
				</tr>
			</table>
		</form>
	</div>
	<script type="text/javascript">
		$(function(){
			init();
		});
	</script>
</body>
</html>