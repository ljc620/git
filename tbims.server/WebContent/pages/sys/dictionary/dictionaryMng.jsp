<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="myel" uri="/elFunction"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"></c:set>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>数据字典</title>
<%@ include file="/common.jsp"%>
<script type="text/javascript" src="${path}/js/common/commcheck.js"></script>
<script type="text/javascript" src="dictionaryMng.js"></script>
<style type="text/css">
span.text{
	float:left;
	display:block;
	height:30px;
}
</style>
</head>
<body>
	<div id="mytable-buttons">
		<div class="ss-bar">
			<ul class="ss-ul">
				<li style="width: 35px;padding-left:15px;"><span class="text" style="width:40px">字段名</span></li>
				<li>
					<span class="text"><input id="q_key" name="q_key" class="easyui-textbox" style="width: 150px;"></input></span>
				</li>
				<li style="width: 60px;padding-left:15px;"><span style="width:70px;" class="text">字段中文名</span></li>
				<li>
					<span class="text"><input id="q_key_nm" name="q_key_nm" class="easyui-textbox" style="width: 150px;"></input></span>
				</li>
				<li style="width: 60px;padding-left:15px;"><span style="width:70px" class="text">数据项代码</span></li>
				<li>
					<span class="text"><input id="q_item_cd" name="q_item_cd" class="easyui-textbox"  validType="length[1,3]"  style="width: 150px;"></input></span>
				</li>
			</ul>
			<br/>
			<ul class="ss-ul">
				<li style="width: 35px;padding-left:15px;"><span style="width:40px;" class="text">状态</span></li>
				<li style="width: 150px;">
					<span class="text"><input id="q_stat" name="q_stat" style="width: 150px;"></input></span>
				</li>	
				<li style="width: 60px;padding-left:15px;"><span style="width:70px" class="text">数据项名称</span></li>
				<li>
					<span class="text"><input id="q_item_name" name="q_item_name" class="easyui-textbox" style="width: 150px;"></input></span>
				</li>			
				<li><span style="width:70px" class="text">
					<a href="javascript:void(0)" onclick="javascript:queryDictionary();" class="gray2-btn">&nbsp;查询&nbsp;</a>
					</span>
				</li>
			</ul>
		</div>
		<div class="ss-bar2">
			<ul class="ss-ul">
		
				<li>
					<a href="javascript:void(0)" class="red-btn icon-add" onclick="javascript:addDictionary();">
						<span class="btn-text">新增字典</span>
					</a>
				</li>
				<!-- <li>
					<a href="javascript:void(0)" class="red-btn icon-add" onclick="javascript:deleteDictionary(null);">
						<span class="btn-text">批量删除</span>
					</a>
				</li> -->
				<li>
					<a href="javascript:void(0)" class="red-btn icon-add" onclick="javascript:clsCheck('#mytable');">
						<span class="btn-text">清空选中</span>
					</a>
				</li>
			</ul>
		</div>
	</div>
	<div class="it-datagrid" style="height: 100%">
		<table id="mytable"></table>
	</div>
		<div class="easyui-dialog" id="dicMngDialog" style="width:600px;height:400px;padding:5px 10px;" closed="true">
	</div>
	<div id="dlg-buttons" style="text-align: center;display: none;">
		<a href="javascript:void(0)" class="red-btn" id="save-button" onclick="javascript:save('/dictionary/addDictionary.do')" style="width: 90px; margin-right: 10px;">保存</a>
		<a href="javascript:void(0)" class="blue-btn" id="update-button" onclick="javascript:save('/dictionary/updateDictionary.do')" style="width: 90px; margin-right: 10px;">修改</a>
		<a href="javascript:void(0)" class="gray-btn" onclick="javascript:$('#dicMngDialog').dialog('close')" style="width: 90px; margin-right: 10px;">取消</a>
	</div>
</body>
</html>