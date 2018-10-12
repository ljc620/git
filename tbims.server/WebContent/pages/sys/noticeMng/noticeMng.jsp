<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="myel" uri="/elFunction"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>信息公告管理</title>
<%@ include file="/common.jsp"%>
<script type="text/javascript" src="noticeMng.js"></script>
</head>
<body>
	<div id="mytable-buttons">
		<div class="ss-bar">
		<ul class="ss-ul">
				<li>
							<span>标题</span>
							<span><input id="title" name="title" class="easyui-textbox" type="text" style="width:200px;"></input></span>
						</li>
						<li>
							<span>级别</span>
							<span>
								<input name="lev" id="lev" class="easyui-combobox" style="width:160px;" required="true"
									data-options="  
	                                url: '<%=path%>/comm/listItemsByKey.do?key=PRIORITY',   
	                                method:'get',  
	                                valueField:'id',  
	                                textField:'text',  
	                                multiple:false,  
	                                editable:false,  
	                                panelHeight:'auto',
	                                required:true,
	                                loadFilter : function(data) {
										var list = [ {
											id : '',
											text : '--全部--'
										} ];
										return $.merge(list, data);
									}"></input>
							</span>
						</li>
						<li>
						<a href="javascript:void(0)" onclick="javascript:query();" class="gray2-btn">&nbsp;查询&nbsp;</a>
						</li>
				
			</ul>
			<ul class="ss-ul">

				<li>
					<a href="javascript:void(0)" class="red-btn icon-add" onclick="javascript:addNotice();">
						<span class="btn-text">添加信息公告</span>
					</a>
				</li>

			</ul>
		</div>
	</div>
	<div class="it-datagrid" style="height:100%">
		<table id="mytable"></table>
	</div>
	<div id="showNoticeDlg" class="easyui-dialog"style="width:800px; height:350px; " closed="true"></div>
	<div id="periodDialog" class="easyui-dialog" style="width: 400px; height: 250px; padding: 5px 10px" modal="true" closed="true">
	</div>
	<div id="dlg-buttons" style="text-align: center; display: none;">
		<a href="javascript:void(0)" id="save-button" class="blue-btn" onclick="javascript:saveNotice('/noticeMng/addNotice.do')" style="width: 90px; margin-right: 10px;margin-left:90px;">保存</a>
		<a href="javascript:void(0)" class="gray-btn" onclick="javascript:$('#periodDialog').dialog('close');" style="width: 90px; margin-left: 10px;">取消</a>
	</div>
	
</body>
</html>