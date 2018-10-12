<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="myel" uri="/elFunction"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"></c:set>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>客流时间段统计</title>
<%@ include file="/common1.jsp"%>
<script type="text/javascript" src="${path}/resources/jquery-easyui/plugins/jquery.table.rowspan.js"></script>
<script type="text/javascript" src="<%=path%>/js/common/initcombo.js"></script>
<link rel="stylesheet" href="${path}/resources/ztree_src/css/zTreeStyle/zTreeStyle.css" type="text/css"></link>
<script type="text/javascript" src="${path}/resources/ztree/js/jquery.ztree.core.min.js"></script>
<script type="text/javascript" src="${path}/resources/ztree/js/jquery.ztree.excheck.min.js"></script>
<script type="text/javascript">
$(function(){
	$('#date').datebox('setValue','<fmt:formatDate value="${statdate}" pattern="yyyy-MM-dd" />');
	initCombobox(); 
	$('#intervalTime').combobox('setValue','${intervalTime}');
	$('#startTime').combobox('setValue','${startTime}');
	$('#endTime').combobox('setValue','${endTime}');
	initSeletedCheckBox('<%=path%>',0,'${venueId}','${venueName}');
	$("#venueTree").appendTo($('#venueId').combo('panel'));
	initSeletedCheckBox('<%=path%>',1,'${regionId}','${regionName}');
	$("#regionTree").appendTo($('#regionId').combo('panel'));
	initSeletedCheckBox('<%=path%>',2,'${ticketTypeId}','${ticketTypeName}');
	$("#ticketTypeTree").appendTo($('#ticketTypeId').combo('panel'));
	$("#tbListTM").rowspan(0); //第一列合并
	$("#tbListTM").rowspan(1);//第二列合并
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
		  onLoadSuccess : function(e) {
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
	
	$('#venueName').val($('#venueId').combobox('getText'));
	$('#regionName').val($('#regionId').combobox('getText'));
	$('#ticketTypeName').val($('#ticketTypeId').combobox('getText'));
	var url="<%=path%>/rptTeam/listCustFlowInterTime.do";
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
	var url="<%=path%>/rptTeam/expExcelInterTime.do";
		$('#queryForm').attr("action", url).submit();
	}
</script>
</head>
<body style="background-color: #fff">
	<div class="ss-bar">
		<form id="queryForm" method="post">
			<input type="hidden" name="venueName" id="venueName">
			<input type="hidden" name="regionName" id="regionName">
			<input type="hidden" name="ticketTypeName" id="ticketTypeName">
			<div>
				<ul class="ss-ul">
					<li style="width: 50px; padding-left: 15px;">统计日期</li>
					<li>
						<input id="date" name="date" class="easyui-datebox" style="width: 180px;" required="true" editable="false"></input>
					</li>
					<li style="width: 50px; padding-left: 15px;">时间间隔</li>
					<li>
						<input id="intervalTime" name="intervalTime" class="easyui-combobox" style="width: 180px;" required="true"></input>
					</li>
					<li style="width: 45px; padding-left: 15px;display: none;">时间段</li>
					<li style="display: none;">
						<input class="easyui-combobox" id="startTime" name="startTime" style="width: 130px;" required="true" />
						--
						<input class="easyui-combobox" id="endTime" name="endTime" style="width: 130px;" required="true" />
					</li>
				</ul>
				<ul class="ss-ul">
					<li style="width: 50px; padding-left: 15px;">场馆名称</li>
					<%-- <li><input id="venueId" name="venueId" class="easyui-combobox"
						data-options="url :'<%=path %>/ticketTypeMng/venueList.do',
								method : 'get',
								valueField : 'venueId',
								textField : 'venueName',
								multiple : true,
								editable : false,
								onLoadSuccess : function(e) {
								},
								onLoadError : function(e) {
									getAjaxError(e);
							} "
						value="${venueId }" style="width: 140px;"></input></li> --%>
					<li>
						<span class="col" id="venuecss">
							<input id="venueId" name="venueId" class="easyui-combobox" style="width: 180px;" data-options="panelHeight : 'auto',panelWidth : 'auto'" editable="false" />
						</span>
					</li>
					<li style="width: 50px; padding-left: 15px;">区域名称</li>
					<%-- <li><input id="regionId" name="regionId"
						class="easyui-combobox"
						data-options="url : '<%=path %>/comm/listRegion.do',
								method : 'get',
								valueField : 'regionId',
								textField : 'regionName',
								multiple : true,
								editable : false,
								onLoadSuccess : function(e) {
								},
								onLoadError : function(e) {
									getAjaxError(e);
								} "
						value="${regionId}" style="width: 140px;"></input></li> --%>
					<li>
						<span class="col" id="regioncss">
							<input id="regionId" name="regionId" class="easyui-combobox" style="width: 180px;" data-options="panelHeight : 'auto',panelWidth : 'auto'" editable="false" />
						</span>
					</li>
					<li style="width: 50px; padding-left: 15px;">票种名称</li>
					<%-- <li><input id="ticketTypeId" name="ticketTypeId"
						class="easyui-combobox"
						data-options="url: '<%=path%>/orgMng/ticketTypeList.do?flag=a',   
						       method:'get',  
						       valueField:'ticketTypeId',  
						       textField:'ticketTypeName',  
						       multiple:true,  
						       editable:false,  
						       onLoadSuccess : function(e) {
						  },
						  onLoadError : function(e) {
								getAjaxError(e);
						  } "
						value="${ticketTypeId}" style="width: 140px;"></input></li> --%>
					<li>
						<span class="col" id="ticketTypecss">
							<input id="ticketTypeId" name="ticketTypeId" class="easyui-combobox" style="width: 191px;" data-options="panelHeight : 'auto',panelWidth : 'auto'" editable="false" />
						</span>
					</li>
					<li>
						<a onclick="query();" class="gray2-btn" style="width: 35px;">查询</a>
					</li>
					<li>
						<a class="indigo-btn" id="btnExcel" style="margin-left: 15px;" onclick="expExcel()">导出EXCEL</a>
					</li>
				</ul>
			</div>
		</form>
	</div>
	<div class="zcfg-panel-body">
		<table class="report-table" style="width: 100%;" id="tbListTM">
			<thead class="datagrid1-header">
				<tr class="theader">
					<th style="width: 150px;">时间段</th>
					<th style="width: 150px;">场馆名称</th>
					<th style="width: 150px;">区域名称</th>
					<th style="width: 150px;">票种名称</th>
					<th style="width: 150px;">入园人数</th>
				</tr>
			</thead>
			<tbody class="datagrid1-body">
				<c:forEach var="bean" items="${list}" varStatus="status">
					<tr class="tbody">
						<c:if test="${bean.venueName!='总计'&&(bean.regionName=='合计'||bean.regionName=='小计')}">
							<td align="center">${bean.rptDate}</td>
							<td align="center">${bean.venueName}</td>
							<td align="center" style="font-weight: bold; color: #000">${bean.regionName}</td>
							<td align="center">${bean.ticketTypeName}</td>
							<td align="right" style="font-weight: bold; color: #000">
								<fmt:formatNumber type="number" value="${bean.checkTicketNum}" maxFractionDigits="0"></fmt:formatNumber>
							</td>

						</c:if>
						<c:if test="${bean.venueName=='总计'}">
							<td colspan="4" align="right" style="font-weight: bold; color: #000">${bean.venueName}</td>
							<td align="right" style="font-weight: bold; color: #000;" align="right">
								<fmt:formatNumber type="number" value="${bean.checkTicketNum}" maxFractionDigits="0"></fmt:formatNumber>
							</td>
						</c:if>
						<c:if test="${bean.venueName!='总计'&&bean.regionName!='小计'&&bean.regionName!='合计'}">
							<td align="center">${bean.rptDate}</td>
							<td align="center">${bean.venueName}</td>
							<td align="center">${bean.regionName}</td>
							<td align="center">${bean.ticketTypeName}</td>
							<td align="right">
								<fmt:formatNumber type="number" value="${bean.checkTicketNum}" maxFractionDigits="0"></fmt:formatNumber>
							</td>
						</c:if>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
	<div id="venueCon" class="orgContent" style="display: none; position: absolute;">
		<ul id="venueTree" class="ztree" style="margin-top: 0; width: 180px; height: 200px;"></ul>
	</div>
	<div id="regionCon" class="orgContent" style="display: none; position: absolute;">
		<ul id="regionTree" class="ztree" style="margin-top: 0; width: 180px; height: 200px;"></ul>
	</div>
	<div id="ticketTypeCon" class="orgContent" style="display: none; position: absolute;">
		<ul id="ticketTypeTree" class="ztree" style="margin-top: 0; width: 180px; height: 200px;"></ul>
	</div>
</body>
</html>