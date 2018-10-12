/**
 * 重置网络代理回调错误计数
 */
function resetWLAgentError(){
	$.messager.confirm('提示', '您确定要重置所有网络代理回调错误计数吗?', function(r) {
		if (r) {
			$.ajax({
				url : $ctx + "/health/resetWLAgentCallbackError.do",
				type : "post",
				beforeSend:function(){
					$.messager.progress({
						text : '正在重置网络代理回调错误计数,请稍候...'
					});
				},
				success : function(e) {
					$.messager.progress("close");
					$.messager.show({
						showType : 'slide',
						title : "提示信息",
						msg : '重置网络代理回调错误计数成功。',
						timeout : 2000
					});
					listWLAgentCallbackError();
				},
				error : function(e) {
					getAjaxError(e);
				},
				complete: function (XMLHttpRequest, textStatus) {
		            $.messager.progress("close");
				}
			});
		}
	});
}
/**
 * 查询所有网络代理回调错误计数
 * */
function listWLAgentCallbackError(){
	$.ajax({
		url : $ctx + "/health/listWLAgentCallbackError.do",
		type : "post",
		dataType: "json",
//		beforeSend:function(){
//			$.messager.progress({
//				text : '正在加载网络代理回调错误计数,请稍候...'
//			});
//		},
		success : function(data) {
			var txt="<ul>";
			for(var i=0;i<data.length;i++){
				if(data[i]['callbackErrorCount']==0)
					txt += "<li>" + data[i]['orgName'] + "：" + data[i]['callbackErrorCount'] + "</li>";
				else
					txt += "<li style='color:red;'>" + data[i]['orgName'] + "：" + data[i]['callbackErrorCount'] + "</li>";
			}
			txt +="</ul>"
			$('#waErrCntContainer').html(txt);
		},
		error : function(e) {
			getAjaxError(e);
		},
		complete: function (XMLHttpRequest, textStatus) {
            cnt--;
            if(cnt==0)
            	$.messager.progress("close");
		}
	});
}
/**
 * 查询所有网络代理待回调门票数
 * */
function listWLAgentWaitCallbackTicket(){
	$.ajax({
		url : $ctx + "/health/listWLAgentWaitCallbackTicket.do",
		type : "post",
		dataType: "json",
//		beforeSend:function(){
//			$.messager.progress({
//				text : '正在加载网络代理待回调门票数,请稍候...'
//			});
//		},
		success : function(data) {
			var txt="<ul>";
			for(var i=0;i<data.length;i++){
				if(data[i]['waitCallbackTicketCount']==0)
					txt += "<li>" + data[i]['orgName'] + "：" + data[i]['waitCallbackTicketCount'] + "</li>";
				else
					txt += "<li style='color:red;'>" + data[i]['orgName'] + 
					"：<a href='javascript:void(0);' onclick='showWaitCallbackDetail(\"" + data[i]['orgId'] + "\",\"" +data[i]['orgName']+ "\")'>" + data[i]['waitCallbackTicketCount'] + "</a></li>";
			}
			txt +="</ul>"
			$('#waWaitCallbackCntContainer').html(txt);
		},
		error : function(e) {
			getAjaxError(e);
		},
		complete: function (XMLHttpRequest, textStatus) {
            cnt--;
            if(cnt==0)
            	$.messager.progress("close");
		}
	});
}
/**
 * 显示网络代理机构待回调门票明细
 * @param orgId
 */
function showWaitCallbackDetail(orgId, orgName) {
	$ticketTable = $('#waitCallbackTicketTable');
	$ticketTable.datagrid({
		nowrap : true,// 是否只显示一行，即文本过多是否省略部分。
		striped : true,
		url : $ctx + '/health/listWaitCallbackTicketDetail.do', // controller地址
		onLoadSuccess : function(data) {
		},
		onLoadError : function(e) {
			getAjaxError(e);
		},
		idField : 'orderDetailId',
		fitColumns : true,
		fit : true,
		checkbox : false,
		rownumbers : true,
		queryParams:{orgId: orgId},
		singleSelect : false,
		columns : [ [// 显示的列
        {
 			field : 'orderId',
 			title : '订单号',
 			width : 150,
 			editor : "text",
 			align : 'center'
 		}, {
			field : 'ticketTypeName',
			title : '票种名称',
			width : 180,
			editor : "text",
			align : 'center'
		}, {
			field : 'identtyId',
			title : '身份证号',
			width : 160,
			editor : "text",
			align : 'center'
		}, {
			field : 'checkTime',
			title : '检票时间',
			width : 150,
			editor : "text",
			align : 'center'
		}, {
			field : 'orgCallbackStat',
			title : '核销状态',
			width : 100,
			formatter:function(value, row, index) {
				if(value == 'N')
					return '未核销';
				else if(value == 'W')
					return '待机构核销';
			},
			align : 'center'
		}, {
			field : 'caozuo',
			title : '操作',
			align : 'center',
			halign: 'center',
			formatter:function(value, row, index) {
				if(row.orgCallbackStat==='W'){
					value = '<a href="javascript:void(0)" title="内部已核销，停止此门票机构核销回调" onclick="skipTiket(\'' + row.orderDetailId + '\')">停止回调</a> ';
					return value;
				} else
					return '<span title="内部未核销，不可停止此门票机构核销回调">（无）</span>';
			},
			width : 100
		} ] ]
	});
	
	$("#waitCallbackTicketDialog").dialog({
		title:"机构待回调门票明细【" + orgName + "】",
		resizable:true,
		inline:true,
		cache : false,
		modal : true,
		buttons : "#dlg-buttons",
		onLoadError:function(e){
			getAjaxError(e);
		}
	}).dialog("open");
}
/**
 * 跳过指定门票的回调操作
 * @param orderDetailId
 */
function skipTiket(orderDetailId) {
	$.messager.confirm('提示', '您确定要跳过此门票的核销回调码？', function(r) {
		if (r) {
			$.ajax({
				url : $ctx + "/health/skipTicketCallback.do",
				type : "post",
				data : {
					"orderDetailId" : orderDetailId
				},
				beforeSend:function(){
					$.messager.progress({
						text : '正在处理,请稍候...'
					});
				},
				success : function(data, textStatus) {
					$ticketTable.datagrid('reload'); // 重新加载数据
					$.messager.show({
						showType : 'slide',
						title : "提示信息",
						msg : "更新成功",
						timeout : 2000
					});
					cnt = 1;
					listWLAgentWaitCallbackTicket();
				},
				error : function(e) {
					getAjaxError(e);
				},
				complete: function (XMLHttpRequest, textStatus) {
		        	$.messager.progress("close");
				}
			});
		}
	});
}
/**
 * 查询所有闸机的运行状态
 * */
function listGateState(){
	$.ajax({
		url : $ctx + "/health/listGateState.do",
		type : "post",
		dataType: "json",
//		beforeSend:function(){
//			$.messager.progress({
//				text : '正在加载闸机运行状态,请稍候...'
//			});
//		},
		success : function(data) {
			var txt="";
			for(var i=0;i<data.length;i++){
				if(data[i]['reportStat']==0)
					txt += "<div class='divItem failure' title='"+data[i]['clientName']+"'>" + data[i]['clientName'] + "</div>";
				else
					txt += "<div class='divItem success' title='"+data[i]['clientName']+"'>" + data[i]['clientName'] + "</div>";
			}
			$('#gateStateContainer').html(txt);
		},
		error : function(e) {
			getAjaxError(e);
		},
		complete: function (XMLHttpRequest, textStatus) {
            cnt--;
            if(cnt==0)
            	$.messager.progress("close");
		}
	});
}

/**
 * 检查所有服务器及服务的状态
 * */
function listServerAndServiceState(){
	$.ajax({
		url : $ctx + "/health/listServerAndServiceState.do",
		type : "post",
		dataType: "json",
//		beforeSend:function(){
//			$.messager.progress({
//				text : '正在检查服务器及服务运行状态,请稍候...'
//			});
//		},
		success : function(data) {
			var txt="";
			for(var i=0;i<data.length;i++){
				if(data[i]['CheckState']==true)
					txt += "<div class='divItem success' title='"+data[i]['name']+"'>" + data[i]['name'] + "</div>";//data[i]['Message'] 
				else
					txt += "<div class='divItem failure' title='"+data[i]['name']+"'>" + data[i]['name'] + "</div>";
			}
			$('#srvStateContainer').html(txt);
		},
		error : function(e) {
			getAjaxError(e);
		},
		complete: function (XMLHttpRequest, textStatus) {
            cnt--;
            if(cnt==0)
            	$.messager.progress("close");
		}
	});
}

/**
 * 查询所有自助售票机的运行状态
 * */
function listSlfServiceState(){
	$.ajax({
		url : $ctx + "/health/listSlfServiceState.do",
		type : "post",
		dataType: "json",
//		beforeSend:function(){
//			$.messager.progress({
//				text : '正在加载自助售票机运行状态,请稍候...'
//			});
//		},
		success : function(data) {
			var txt="";
			for(var i=0;i<data.length;i++){
				if(data[i]['reportStat']==0)
					txt += "<div class='divItem failure' title='"+data[i]['clientName']+"'>" + data[i]['clientName'] + "</div>";
				else
					txt += "<div class='divItem success' title='"+data[i]['clientName']+"'>" + data[i]['clientName'] + "</div>";
			}
			$('#slfSrvStateContainer').html(txt);
		},
		error : function(e) {
			getAjaxError(e);
		},
		complete: function (XMLHttpRequest, textStatus) {
            cnt--;
            if(cnt==0)
            	$.messager.progress("close");
		}
	});
}
/**
 * 刷新状态
 * @param refblock
 */
function refreshState(refblock) {
	$.messager.progress({
		text : '正在刷新，请稍候...'
	});
	cnt = 1;
	if(refblock == 'CallbackError')
		listWLAgentCallbackError();
	else if(refblock == 'CallbackTicket')
		listWLAgentWaitCallbackTicket();
	else if(refblock == 'Server')
		listServerAndServiceState();
	else if(refblock == 'Gate')
		listGateState();
	else if(refblock == 'SlfSrvMachine')
		listSlfServiceState();
}
var cnt;
$(function() {
	$("#close-button").on('click', function(){
		$('#waitCallbackTicketDialog').dialog('close');
	});
	
	$.messager.progress({
		text : '正在检查设备及服务运行状态，请稍候...'
	});
	cnt = 5;
	listWLAgentCallbackError();
	listWLAgentWaitCallbackTicket();
	listServerAndServiceState();
	listGateState();
	listSlfServiceState();
});