$(function() {
	$myTable = $('#mytable');
	init();

	var rptDt = $('#rptDt').datebox('getValue');
	var queryParams = {
		"rptDt" : rptDt
	};
	createWlSaleTable(queryParams);
});

function init() {
	$('#rptDt').datebox('setValue', formatterDate(new Date()));

	$('#orgId').combobox({
		url : $ctx + "/comm/listOrg.do?orgType='1'", // controller地址
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
}

function createWlSaleTable(params) {
	$myTable.datagrid({
		nowrap : true,// 是否只显示一行，即文本过多是否省略部分。
		striped : true,
		url : $ctx + '/rptsale/wlSaleDetailByHB.do', // controller地址
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
			title : '换票时间',
			align : 'center',
			halign : 'center',
			width : 150
		}, {
			field : 'orgName',
			title : '代理商名称',
			halign : 'center',
			align : 'center',
			width : 180
		}, {
			field : 'outletName',
			title : '网点名称',
			align : 'center',
			halign : 'center',
			width : 160
		}, {
			field : 'ticketTypeName',
			title : '票种',
			align : 'center',
			halign : 'center',
			width : 160
		}, {
			field : 'userName',
			title : '换票人',
			align : 'center',
			halign : 'center',
			width : 120
		}, {
			field : 'thirdOrderNum',
			title : '第三方单号',
			halign : 'center',
			align : 'center',
			width : 160
		}, {
			field : 'saleCount',
			title : '换票数量',
			halign : 'center',
			align : 'center',
			width : 100

		} ] ],
		toolbar : '#mytable-buttons'
	});
}
function query() {
	if (!$('#queryForm').form('validate')) {
		return false;
	}

	var rptDt = $('#rptDt').datebox('getValue');
	var orgId=$('#orgId').combobox('getValue');
	var outletId=$('#outletId').combobox('getValue');

	var queryParams = {
		'rptDt' : rptDt,
		'orgId' : orgId,
		'outletId' : outletId
	}
	$('#mytable').datagrid('options').queryParams = queryParams;
	$('#mytable').datagrid('load');
	return true;
}
function expExcel() {
	if (!$('#queryForm').form('validate')) {
		return false;
	}
	var url = $ctx + '/rptsale/expExlWlSaleDetailByHB.do';
	$('#queryForm').attr("action", url).submit();
}