<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="myel" uri="/elFunction"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"></c:set>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>客流时间段统计(按区域)</title>
<%@ include file="/common1.jsp"%>
<script type="text/javascript" src="${path}/resources/jquery-easyui/plugins/jquery.table.rowspan.js"></script>
<script type="text/javascript">
$(function(){
	$('#date').datebox('setValue','<fmt:formatDate value="${date}" pattern="yyyy-MM-dd" />');
	initCombobox(); 
	$('#intervalTime').combobox('setValue','${intervalTime}');
	$('#startTime').combobox('setValue','${startTime}');
	$('#endTime').combobox('setValue','${endTime}');
	$("#tbListTM").rowspan(0); //第一列合并 
});
function initCombobox() {
	//时间间隔
	$('#intervalTime').combobox({
        valueField:'id',  
        textField:'text',  
        editable:false,  
        panelHeight:'auto',
        data:[
            {
        		id:'15',
        		text:'15分钟'
        	},{
        		id:'30',
        		text:'30分钟'
        	},{
        		id:'45',
        		text:'45分钟'
        	},{
        		id:'60',
        		text:'60分钟'
        	}
       ],
       onChange:function(newValue,oldValue){
    	   var urlS='<%=path%>/rptTeam/listStartTime.do?splitTime='+newValue;
    	   var urlN='<%=path%>/rptTeam/listEndTime.do?splitTime='+newValue;
    	   $('#startTime').combobox('reload',urlS);
    	   $('#endTime').combobox('reload',urlN);
       }
	});
	var time=$('#intervalTime').combobox('getValue');
	
	$('#startTime').combobox({
		  url:'<%=path%>/rptTeam/listStartTime.do?splitTime='+time,
		  method : 'get',
		  valueField: 'id',
		  textField:'text',
		  editable:true,
		  onLoadSuccess : function(e) {
			  if(e.length>0){
				  $(this).combobox("setValue",e[0].id);
			  }
		  },
		  onLoadError : function(e) {
				getAjaxError(e);
		  } 
	 });
	$('#endTime').combobox({
		  url:'<%=path%>/rptTeam/listEndTime.do?splitTime='+time,
		  method : 'get',
		  valueField: 'id',
		  textField:'text',
		  editable:true,
		  onLoadSuccess : function(e) {alert(ddd);
			  if(e.length>0){
				  $(this).combobox("setValue",e[e.length-1].id);
			  }
		  },
		  onLoadError : function(e) {
				getAjaxError(e);
		  } 
	 });
}
//查询
function query(){
	if (!$('#queryForm').form('validate')) {
		return false;
	} 
	var startTime=$('#startTime').combobox('getValue');
	var endTime=$('#endTime').combobox('getValue');
	if(startTime>endTime){
		alert("请确认开始时间段小于结束时间段");
		return false;
	}

	$.messager.progress({
		text : '正在处理,请稍候...'
	});

	var url="<%=path%>/rptTeam/listCustFlowInterTimeN.do";
	 $('#queryForm').attr("action", url).submit();
}
function expExcel(){
	if (!$('#queryForm').form('validate')) {
		return false;
	} 
	var startTime=$('#startTime').combobox('getValue');
	var endTime=$('#endTime').combobox('getValue');
	if(startTime>endTime){
		alert("请确认开始时间段小于结束时间段");
		return false;
	}
	var url='<%=path%>/rptTeam/expExcelInterTimeN.do';
		$('#queryForm').attr("action", url).submit();
	}
</script>
</head>
<body style="background-color: #fff">
	<div class="ss-bar">
		<form id="queryForm">
			<div>
				<ul class="ss-ul">
					<li style="width: 50px; padding-left: 15px;">统计日期</li>
					<li>
						<input id="date" name="date" class="easyui-datebox" style="width: 180px;" required="true" editable="false"></input>
					</li>
					<li style="width: 50px; padding-left: 15px;">时间间隔</li>
					<li>
						<input id="intervalTime" name="intervalTime" class="easyui-combobox" style="width: 140px;" required="true"></input>
					</li>
					<li style="width: 45px; padding-left: 15px; display: none;">时间段</li>
					<li style="display: none;">
						<input class="easyui-combobox" id="startTime" name="startTime" style="width: 130px;" required="true" />
						-
						<input class="easyui-combobox" id="endTime" name="endTime" style="width: 130px;" required="true" />
					</li>
					<li>
						<a onclick="query();" class="gray2-btn" style="width: 35px;">查询</a>
					</li>
					<li>
						<a class="indigo-btn" id="btnExcel" style="margin-left: 15px;" onclick="expExcel()">导出EXCEL</a>
					</li>
				</ul>
				<%-- <ul class="ss-ul">
					<li style="width: 50px; padding-left: 15px;">区域</li>
					<li><input id="regionId" name="regionId"
						class="easyui-combobox"
						data-options="url :'<%=path%>/comm/listRegion.do',
								method : 'get',
								valueField : 'regionId',
								textField : 'regionName',
								multiple : true,
								editable : false,
								panelHeight : 'auto',
								onLoadSuccess : function(e) {
								},
								onLoadError : function(e) {
									getAjaxError(e);
								} "
						value="${regionId}" style="width: 140px;"></input></li>
					<li style="width: 30px; padding-left: 15px;">票种</li>
					<li><input id="ticketTypeId" name="ticketTypeId"
						class="easyui-combobox"
						data-options="url:'<%=path%>/orgMng/ticketTypeList.do?flag=a',   
						         method:'get',  
						         valueField:'ticketTypeId',  
						         textField:'ticketTypeName',  
						         multiple:true,  
						         editable:false,  
						         panelHeight:'auto',
								onLoadSuccess : function(e) {
								},
								onLoadError : function(e) {
									getAjaxError(e);
								} "
						value="${ticketTypeId}" style="width: 140px;"></input></li>
				</ul> --%>
			</div>
		</form>
	</div>
	<div class="zcfg-panel-body" style="padding:1px;">
		<table style="width: 100%;" class="report-table" id="tbListTM">
			<thead class="datagrid1-header">
				<tr class="theader">
					<th align="center" nowrap="nowrap">区域名称</th>
					<th align="center" nowrap="nowrap">票种名称</th>
					<c:forEach items="${splitName}" var="list">
						<th align="center" nowrap="nowrap">${list}</th>
					</c:forEach>
					<th align="center" nowrap="nowrap" style="font-weight: bold; color: #000">合计</th>
				</tr>
			</thead>
			<tbody class="datagrid1-body">
				<c:forEach var="bean" items="${list }">
					<tr>
						<c:if test="${bean.regionName=='总计'}">
							<td class="data" align="right" colspan="2" nowrap="nowrap" style="font-weight: bold;">${bean.regionName}</td>
							<c:forEach items="${bean.ticTypeNum}" var="num">
								<td class="data" align="right" nowrap="nowrap" style="font-weight: bold;">${num}</td>
							</c:forEach>
						</c:if>
						<c:if test="${bean.regionName!='总计'}">
							<td class="data" align="center" nowrap="nowrap">${bean.regionName}</td>
							<c:if test="${bean.ticketTypeName=='小计'}">
								<td class="data" align="right" nowrap="nowrap" style="font-weight: bold;">${bean.ticketTypeName}</td>
								<c:forEach items="${bean.ticTypeNum}" var="num">
									<td class="data" align="right" nowrap="nowrap" style="font-weight: bold;">${num}</td>
								</c:forEach>
							</c:if>
							<c:if test="${bean.ticketTypeName!='小计'}">
								<td class="data" align="center" nowrap="nowrap">${bean.ticketTypeName}</td>
								<c:forEach items="${bean.ticTypeNum}" var="num">
									<td class="data" align="right" nowrap="nowrap">${num}</td>
								</c:forEach>
							</c:if>
						</c:if>
						<td class="data" align="right" nowrap="nowrap" style="font-weight: bold;">${bean.totalNum}</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
</body>
</html>