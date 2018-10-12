<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="myel" uri="/elFunction"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"></c:set>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>新增信息公告</title>
<%@ include file="/common.jsp"%>
<script type="text/javascript" src="noticeMng.js"></script>
 
</head>
<body>
	<div> 
		<form id="fm" method="post" class="bf-from">
			<table class="form-table" style="width: 90%;">
			   <tr>
					<td class="label" style="width:100px;">标题</td>
					<td colspan="3">
						<input name="title" id="title" class="easyui-textbox" style="width:200px;" required="true"></input>
						<span class="starStyle">*</span>	
					</td>
				</tr>
				<tr>
					<td class="label" style="width:100px;">优先级</td>
					<td colspan="3">
						<input name="lev" id="lev" class="easyui-combobox" style="width:200px;" required="true"
						data-options="  
                                url: '<%=path%>/comm/listItemsByKey.do?key=PRIORITY',   
                                method:'get',  
                                valueField:'id',  
                                textField:'text',  
                                multiple:false,  
                                editable:false,  
                                panelHeight:'auto',
                                required:true  
                        " 
						></input>
						<span class="starStyle">*</span>
					</td>
				</tr>
				 <tr>
					<td class="label" style="width:100px;">内容</td>
					<td colspan="3">
						<textarea rows="3" cols="23" name="content" id="content"  onkeydown="checklength(this)"></textarea>
					</td>
				</tr>
			</table>
		</form>
</div>

</body>
</html>