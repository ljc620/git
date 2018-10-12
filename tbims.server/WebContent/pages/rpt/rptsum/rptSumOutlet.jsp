<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="myel" uri="/elFunction"%>
<%@ taglib prefix="sys" tagdir="/WEB-INF/tags/sys" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"></c:set>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>网点历史库存</title>
<%@ include file="/common.jsp"%>
<script type="text/javascript" src="rptSumOutlet.js"></script>
<link rel="stylesheet" href="${path}/resources/ztree_src/css/zTreeStyle/zTreeStyle.css" type="text/css"></link>
<script type="text/javascript" src="${path}/resources/ztree/js/jquery.ztree.core.min.js"></script>
<script type="text/javascript" src="${path}/resources/ztree/js/jquery.ztree.excheck.min.js"></script>
<script type="text/javascript">

//查询
function myQuery() {
    var outletId=$('#outletId').combobox('getValue');
    var startDate=$('#startDate').datebox('getValue');
    var endDate=$('#endDate').datebox('getValue');
    var ticketTypeId=$('#ticketTypeId').combobox('getValue');
    if(startDate>endDate){
		$.messager.alert('系统提示','开始日期不能大于结束日期');
	   return false;
	}
    var queryParams = {
		'ticketTypeId':ticketTypeId,
    	'outletId' : outletId,
    	'startDate':startDate,
		'endDate':endDate
	}
	$('#mytable').datagrid('options').queryParams = queryParams;
	$('#mytable').datagrid('load');
	return true;
}
function expExcel(){
	var url='<%=path%>/rptsum/expExcelRptSumOutlet.do';
	 $('#queryForm').attr("action", url).submit();
}
</script> 
</head>
<body>
	<div id="mytable-buttons">	
	<div class="ss-bar">	
	<form id="queryForm" >
			<ul class="ss-ul">
					<li style="width: 50px;padding-left:15px;">网点名称</li>
					<li>
<!-- 						<input id="outletId" name="outletId" class="easyui-combobox" style="width:200px;"></input> -->	
					<sys:noptreeselect id="outletId" name="outletId" options="width:200,panelWidth:200"  idField ="outletId"  textField="outletName" 
							url="comm/listOutletTree.do" pidField="outletType" isParentLevel="false" />
					</li>
					<li  style="width: 50px;padding-left:15px;">票种名称</li>
					<li>
						<input id="ticketTypeId" name="ticketTypeId" class="easyui-combobox" style="width:120px;"></input>
					</li>
					<li style="width: 50px;padding-left:15px;">起止日期</li>
					<li style="margin-left: 10px;"><input id="startDate"
						name="startDate" class="easyui-datebox"  data-options="editable:false"  style="width: 120px;" value="<%=com.zhming.support.util.DateUtil.getYestoday()%>" />-<input
						id="endDate" name="endDate" class="easyui-datebox"  data-options="editable:false" 
						style="width: 120px;" value="<%=com.zhming.support.util.DateUtil.getYestoday()%>" /></li>
					<li><a class="gray2-btn" href="javascript:void(0)"
					onclick="javascript:myQuery();">查询</a> 
				</li>
				<li>
				<a class="indigo-btn"
					id="btnExcel"  id="btnExcel"
					onclick="expExcel()">导出EXCEL</a>
					</li>
			</ul>
		</form>
	</div>
</div>
	<div class="it-datagrid" style="height: 100%">
		<table id="mytable">
		</table>
	</div>
</body>
</html>