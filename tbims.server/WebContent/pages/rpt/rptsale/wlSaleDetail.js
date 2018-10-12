$(function() {
	$myTable = $('#mytable');
	$('#rptDt').datebox('setValue', formatterDate(new Date()));
	var rptDt = $('#rptDt').datebox('getValue');
	//var beginTime = $('#beginTime').timespinner('getValue');
	//var endTime = $('#endTime').timespinner('getValue');
	
	//初始化机构下拉选项
	$('#orgId').combobox({
		url : $ctx + "/comm/listOrgAll.do?orgType='1'", // controller地址
		method : 'post',
		width : 160,
		valueField : 'orgId',
		textField : 'orgName',
		multiple : false,
		editable : false,
		onLoadSuccess : function(e) {
		},
		onLoadError : function(e) {
			getAjaxError(e);
		},
		loadFilter : function(data) {
			var all = [ {
				orgId : '',
				orgName : '--全部--'
			} ];
			return $.merge(all, data);
		}
	});
	
	var queryParams = {
		"rptDt" : rptDt//,
		//"beginTime" : beginTime,
		//"endTime" : endTime
	};
	createWlSaleTable(queryParams);
});
function createWlSaleTable(params) {
	$myTable.datagrid({
		nowrap : true,// 是否只显示一行，即文本过多是否省略部分。
		striped : true,
		url : $ctx + '/rptsale/wlSaleDetail.do', // controller地址
		onLoadSuccess : function(e) {
		},
		onLoadError : function(e) {
			getAjaxError(e);
		},
		fitColumns : false,
		rownumbers : true,
		striped : true,
		pagination : true,
		fit : true,
		queryParams : params,
		columns : [ [// 显示的列
		{
			field : 'saleTime',
			title : '售票时间',
			align : 'center',
			halign : 'center',
			width : 150
		}, {
			field : 'orgName',
			title : '代理商名称',
			halign : 'center',
			align : 'center',
			width : 120
		}, {
			field : 'ticketTypeName',
			title : '票种名称',
			align : 'center',
			halign : 'center',
			width : 120
		}, {
			field : 'orderId',
			title : '订单号',
			align : 'center',
			halign : 'center',
			width : 220
		}, {
			field : 'identtyId',
			title : '身份证号',
			align : 'center',
			halign : 'center',
			width : 160
		}, {
			field : 'orderTypeDesc',
			title : '销售类型',
			halign : 'center',
			align : 'center',
			width : 120
		}, {
			field : 'payTypeDesc',
			title : '支付方式',
			halign : 'center',
			align : 'center',
			width : 100

		}, {
			field : 'salePrice',
			title : '售价',
			halign : 'center',
			align : 'center',
			width : 100
		}, {
			field : 'checkFlag',
			title : '是否检票',
			halign : 'center',
			align : 'center',
			width : 60
		}, {
			field : 'checkTime',
			title : '检票时间',
			halign : 'center',
			align : 'center',
			width : 150
		}, {
			field : 'uselessFlag',
			title : '是否退票',
			halign : 'center',
			align : 'center',
			width : 60
		}, {
			field : 'uselessTime',
			title : '退票时间',
			halign : 'center',
			align : 'center',
			width : 150
		}, {
			field : 'ejectTicketStat',
			title : '是否取票',
			halign : 'center',
			align : 'center',
			width : 60
		} ] ],
		toolbar : '#mytable-buttons'
	});
}
function query() {
	if (!$('#queryForm').form('validate')) {
		return false;
	}
	
	var rptDt = $('#rptDt').datebox('getValue');
	//var beginTime = $('#beginTime').timespinner('getValue');
	//var endTime = $('#endTime').timespinner('getValue');
	//if (beginTime > endTime) {
	//	$.messager.alert('系统提示', '开始时间不能大于结束时间');
	//	return false;
	//}
	var ticketTypeId = $('#ticketTypeId').combobox('getValue');
	var queryParams = {
		'ticketTypeId' : ticketTypeId,
		//'beginTime' : beginTime,
		//'endTime' : endTime,
		'orgId' : $('#orgId').combobox('getValue'),
		'orderId' : $('#orderId').textbox('getValue'),
		'rptDt' : rptDt
	}
	$('#mytable').datagrid('options').queryParams = queryParams;
	$('#mytable').datagrid('load');
	return true;
}
function expExcel() {
	if (!$('#queryForm').form('validate')) {
		return false;
	}
	//var beginTime = $('#beginTime').timespinner('getValue');
	//var endTime = $('#endTime').timespinner('getValue');
	//if (beginTime > endTime) {
	//	$.messager.alert('系统提示', '开始时间不能大于结束时间');
	//	return false;
	//}
	var url = $ctx + '/rptsale/expExlWlSaleDetail.do';
	$('#queryForm').attr("action", url).submit();
}