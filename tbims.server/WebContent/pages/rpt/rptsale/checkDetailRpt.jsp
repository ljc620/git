<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="myel" uri="/elFunction"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<c:set var="ctx" value="${pageContext.request.contextPath}"></c:set>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>检票明细查询</title>
<%@ include file="/common.jsp"%>
<script type="text/javascript" src="checkDetailRpt.js"></script>
</head>
<body>
	<div id="mytable-buttons">
		<div class="ss-bar">
			<form id="queryForm">
				<ul class="ss-ul">
					<li style="width: 50px; padding-left: 15px;">统计日期</li>
					<li>
						<input id="rptDt" name="rptDt" class="easyui-datebox" value="<%=com.zhming.support.util.DateUtil.getNowDate("yyyy-MM-dd")%>" style="width: 120px;" required="true" editable="false"></input>
					</li>
					<li style="width: 36px; padding-left: 10px;">时间段</li>
					<li>
						<input class="easyui-timespinner" style="width: 80px" data-options="showSeconds:false,value:'08:00'" id="beginTime" name="beginTime" style="width: 130px;" required="true" />
						-
						<input class="easyui-timespinner" style="width: 80px" data-options="showSeconds:false,value:'20:00'" id="endTime" name="endTime" required="true" />
					</li>
					<li style="width: 25px; padding-left: 10px;">票种</li>
					<li>
						<input id="ticketTypeId" name="ticketTypeId" class="easyui-combobox"
							data-options="url: '<%=path%>/orgMng/ticketTypeList.do?flag=a',   
						       method:'get',  
						       valueField:'ticketTypeId',  
						       textField:'ticketTypeName',  
						       multiple:false,  
						       editable:false, 
						       onLoadSuccess : function(e) {
						  },
						  onLoadError : function(e) {
								getAjaxError(e);
						  }  ,loadFilter : function(data) {
									var ticketTypeLists = [ {
										ticketTypeId: '',
										ticketTypeName : '--全部--'
									} ];
									return $.merge(ticketTypeLists, data);
								} "
							value="${ticketTypeId}" style="width: 140px;"></input>
					</li>
				</ul>
				<ul class="ss-ul">
					<li style="width: 50px; padding-left: 15px;">场馆</li>
					<li>
						<input id="venueId" name="venueId" class="easyui-combobox"
							data-options="url :'<%=path %>/ticketTypeMng/venueList.do',
								method : 'get',
								valueField : 'venueId',
								textField : 'venueName',
								multiple : false,
								editable : false,
								onLoadSuccess : function(e) {
								},
								onLoadError : function(e) {
									getAjaxError(e);
								} ,loadFilter : function(data) {
									var venueLists = [ {
										venueId: '',
										venueName : '--全部--'
									} ];
									return $.merge(venueLists, data);
								} "
							value="${venueId }" style="width: 120px;"></input>
					</li>
					<li style="width: 36px; padding-left: 10px;">区域</li>
					<li>
						<input id="regionId" name="regionId" class="easyui-combobox"
							data-options="url : '<%=path %>/comm/listRegion.do',
								method : 'get',
								valueField : 'regionId',
								textField : 'regionName',
								multiple : false,
								editable : false,
								onLoadSuccess : function(e) {
								},
								onLoadError : function(e) {
									getAjaxError(e);
								} ,loadFilter : function(data) {
									var regionLists = [ {
										regionId: '',
										regionName : '--全部--'
									} ];
									return $.merge(regionLists, data);
								} "
							value="${regionId}" style="width: 173px;"></input>
					</li>
					<li style="width: 25px; padding-left: 10px;">闸机</li>
					<li>
						<input id="clientId" name="clientId" class="easyui-combobox"
							data-options="url : '<%=path %>/comm/listClientByType.do?clientType=2',
								method : 'get',
								valueField : 'clientId',
								textField : 'clientName',
								multiple : false,
								editable : false,
								onLoadSuccess : function(e) {
								},
								onLoadError : function(e) {
									getAjaxError(e);
								}  ,loadFilter : function(data) {
									var clientLists = [ {
										clientId: '',
										clientName : '--全部--'
									} ];
									return $.merge(clientLists, data);
								} "
							value="${clientId}" style="width: 140px;"></input>
					</li>
					<li>
						<a onclick="query();" class="gray2-btn" style="width: 35px;">查询</a>
					</li>
					<li>
						<a class="indigo-btn" id="btnExcel" style="margin-left: 15px;" onclick="expExcel()">导出EXCEL</a>
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