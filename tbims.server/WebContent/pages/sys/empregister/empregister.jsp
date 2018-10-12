<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@taglib prefix="myel" uri="/elFunction"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>员工信息查询</title>
<%@ include file="/common.jsp"%>
<script type="text/javascript" src="${path}/js/common/commcheck.js"></script>
<script type="text/javascript" src="empregister.js"></script>
</head>
<body>
	<div id="mytable-buttons">
		<div class="ss-bar">
			<ul class="ss-ul">
				<li>员工编号:</li>
				<li>
					<input id="empId" name="empId" class="easyui-numberbox"  data-options="validType:'length[1,10]'" style="width:160px;"></input>
				</li>
				<li>员工名称:</li>
				<li>
					<input id="empName" name="empName" class="easyui-textbox"  data-options="validType:'length[1,100]'" style="width:160px;"></input>
				</li>
				<li>部门:</li>
				<li>
					<input id="department" name="department" class="easyui-textbox"  data-options="validType:'length[1,100]'" style="width:160px;"></input>
				</li>
				<li>状态:</li>
				<li>
					<select name="stat" id="stat" class="easyui-combobox"
							data-options="editable:false,panelHeight:'auto'"
							style="width: 120px;" required="true">
							<option value="">—全部—</option>
							<option value="Y">启用</option>
							<option value="N">禁用</option>
							
						</select>
				</li>
				<li>
					<a href="javascript:void(0)" onclick="javascript:myQuery();" class="gray2-btn">&nbsp;查询&nbsp;</a>
				</li>
			</ul>
			<ul class="ss-ul">
				<li>
					<a href="javascript:void(0)" class="red-btn icon-add" onclick="javascript:updateStat('Y');">
						<span class="btn-text">启用</span>
					</a>
				</li>
				<li>
					<a href="javascript:void(0)" class="red-btn icon-add" onclick="javascript:updateStat('N');">
						<span class="btn-text">禁用</span>
					</a>
				</li>
			</ul>
		</div>
	</div>
	<div class="it-datagrid" style="height:100%">
		<table id="mytable"></table>
	</div>
	<div id="showNoticeDlg" class="easyui-dialog"style="width:500px; height:300px; " closed="true"></div>
</body>
</html>