<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="myel" uri="/elFunction"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>签约社管理</title>
<%@ include file="/common.jsp"%>
<script type="text/javascript" src="orgMng.js"></script>

</head>
<body>
	<div class="it-datagrid" style="height:100%">
		<table id="mytable"></table>
	</div>
	<div id="orgDialog" class="easyui-dialog" style="width: 500px; height: 350px; padding: 5px 10px" modal="true" closed="true" buttons="#dlg-buttons">
	</div>
</body>
</html>