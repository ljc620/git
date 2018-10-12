$(function() {
	$myTable = $('#mytable');
	var myDate = new Date(),
	year = myDate.getFullYear(),
	month = myDate.getMonth(),
	days = myDate.getDate();
	
	$('#startDate').datebox('setValue', year+'-'+(month+1)+'-'+days);
	$('#endDate').datebox('setValue', year+'-'+(month+1)+'-'+days);
	
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
		"startDate" : $('#startDate').datebox('getValue'),
		"endDate" : $('#endDate').datebox('getValue'),
		"orgId" : ''
	};
	createWlCheckdTable(queryParams);
});
function createWlCheckdTable(params) {
	$myTable.datagrid({
		nowrap : true,// 是否只显示一行，即文本过多是否省略部分。
		striped : true,
		url : $ctx + '/rptsale/wlCheckdDetail.do', // controller地址
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
			field : 'saleTime',
			title : '销售时间',
			align : 'center',
			halign : 'center',
			width : 150
		}, {
			field : 'checkTime',
			title : '检票时间',
			halign : 'center',
			align : 'center',
			width : 150
		}, {
			field : 'salePrice',
			title : '核销金额',
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
	
	var queryParams = {
		"startDate" : $('#startDate').datebox('getValue'),
		"endDate" : $('#endDate').datebox('getValue'),
		"orgId" : $('#orgId').textbox('getValue')
	}
	$('#mytable').datagrid('options').queryParams = queryParams;
	$('#mytable').datagrid('load');
	return true;
}
function expExcel() {
	if (!$('#queryForm').form('validate')) {
		return false;
	}
	var url = $ctx + '/rptsale/expExlWlCheckdDetail.do';
	$('#queryForm').attr("action", url).submit();
}