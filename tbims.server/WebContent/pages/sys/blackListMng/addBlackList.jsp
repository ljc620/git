<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="myel" uri="/elFunction"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"></c:set>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>新增黑名单</title>
<%@ include file="/common.jsp"%>
<script type="text/javascript" src="${path}/pages/sys/blackListMng/blackListMng.js"></script>
</head>
<body>
	<div> 
		<form id="fm" method="post" class="bf-from">
			<table  id="mytable-buttons" class="form-table" style="width: 90%;">
			    <tr>
					<td class="label"colspan="2">票起号:</td>
					<td colspan="3">
						<input id="beginNo" name="beginNo" class="easyui-numberbox" validType="length[1,15]"
						></input>
					</td>
					</tr>
					<tr>
					<td class="label"colspan="2">票止号:</td>
					<td colspan="3">
							<input id="endNo" name="endNo" class="easyui-numberbox" validType="length[1,15]"
							></input>
					</td>
			
				</tr>

				<tr>
					<td  class="label" colspan="2">挂失原因:</td>
					<td colspan="3">
						<input name="lossReason" style="width: 300px" id="lossReason" validType="length[1,200]"class="easyui-textbox" ></input>
					</td>
				</tr>
			</table>
		</form>
</div>
</body>
</html>