<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>门票入库管理</title>
<%@ include file="/common1.jsp"%>
<script type="text/javascript" src="${path}/pages/delivery/imp.js"></script>

<script type="text/javascript">
	$(function() {

	});
</script>
</head>
<body class="easyui-layout">
	<div data-options="region:'east'" style="width: 300px;">
		<div id="checkDiv" style="padding-top: 4px;height: 100px;">
			<table class="form-table" style="height: 100%;">
				<tr style="height: 50px;">
					<td>
						<div style="font-size: 12px; color: red;">
							扫描箱号：<br />逗号或回车分割符<br />(例：箱号1,箱号2,......)
						</div>
					</td>
				</tr>
				<tr >
					<td >
						<input id="chestIds" name="chestIds" class="easyui-textbox" data-options="multiline:true" style="width: 290px; height: 500px;"></input>
					</td>
				</tr>
			</table>
		</div>
	</div>
	<div data-options="region:'center'" style="padding: 5px; background: #eee; width: 300px;">
		<div id="mytable-buttons">
			<div class="ss-bar">
				<form id="fm" method="post" class="bf-from" method="post" enctype="multipart/form-data">
					<ul class="ss-ul">
						<li style="width: 120px;">上传附件：</li>
						<li style="width: 250px;">
							<input type="file" id="xmlFilePath" accept="text/xml,application/xml" name="xmlFilePath" style="height: 28px;">
						</li>
						<li>
							<a href="javascript:void(0)" onclick="javascript:uploadxml();" class="blue-btn" style="width: 50px; float: none;">文件上传</a>
						</li>
					</ul>
				</form>
			</div>
			<div class="ss-bar">
				<form id="fm1" method="post" class="bf-from" method="post">
					<ul class="ss-ul">
						<li style="width: 120px;">未提交入库的批次:</li>
						<li style="width: 250px;">
							<input class="easyui-textbox" id="batchId" name="batchId" style="width: 210px;" value="${batchId}" readonly="readonly">
						</li>
					</ul>
				</form>
			</div>
			<div class="ss-bar2">
				<ul class="ss-ul">
					<li>
						<a href="javascript:void(0)" class="blue-btn" onclick="javascript:deleteChestTmp()" style="width: 40px; margin-left: 10px;">清空</a>
						<a onclick="checkChest();" class="blue-btn" style="width: 40px; margin-left: 15px">核对</a>
						<a href="javascript:void(0)" class="blue-btn" onclick="javascript:mySave()" style="width: 40px; margin-left: 10px;">提交</a>
					</li>
				</ul>
			</div>

		</div>

		<div class="it-datagrid" style="height: 100%;">
			<table id="chesttable"></table>
		</div>
	</div>
</body>
</html>