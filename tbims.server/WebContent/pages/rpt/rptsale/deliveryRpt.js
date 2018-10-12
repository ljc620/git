$(function() {
	$myTable = $('#mytable');
	$('#beginDt').datebox('setValue',formatterDate(new Date()));
	$('#endDt').datebox('setValue',formatterDate(new Date()));
	var beginDt=$('#beginDt').datebox('getValue');
	var endDt=$('#endDt').datebox('getValue');
	queryParams={
			'beginDt' : beginDt,
			'endDt' : endDt
	};
	createTicketTypeTable(queryParams);
	initCombobox();
});
function createTicketTypeTable(queryParams) {
	$myTable.datagrid({
		nowrap : true,// 是否只显示一行，即文本过多是否省略部分。
		striped : true,
		url : $ctx + '/rptsale/deliveryRpt.do', // controller地址
		onLoadSuccess : function(e) {
		},
		onLoadError : function(e) {
			getAjaxError(e);
		},
		idField : 'outletName',
		fitColumns : true,
		fit : true,
		//checkbox : true,
		queryParams:queryParams,
		rownumbers : true,
		singleSelect : true,
		striped : true,
		pagination : true,		
		columns : [ [// 显示的列
		{
			field : 'outletName',
			title : '网点名称',
			editor : "text",
			align : 'center',
			width:100
		}, {
			field : 'deliveryDt',
			title : '出库时间',
			align : 'center',
			width:100
		}, {
			field : 'signTime',
			title : '签收时间',
			align : 'center',
			width:100
		}, {
			field : 'ticketTypeName',
			title : '票种名称',
			editor : "text",
			align : 'center',
			width:100
			
		}, {
			field : 'ticketNum',
			title : '张数',
			editor : "text",
			align : 'center',
			width:100
		}, {
			field : 'deliveryUserName',
			title : '配送人',
			editor : "text",
			align : 'center',
			width:100
		} , {
			field : 'signUserName',
			title : '接收人',
			align : 'center',
			halign: 'center',
			width:100
		}] ],
		toolbar : '#mytable-buttons'
	});

}
function initCombobox(){
	//票种初始化
	$('#ticketTypeId').combobox({
		url : $ctx + '/orgMng/ticketTypeList.do?flag=a',
		method : 'get',
		valueField : 'ticketTypeId',
		textField : 'ticketTypeName',
		multiple : false,
		editable : false,
		onLoadSuccess : function(e) {
		},
		onLoadError : function(e) {
			getAjaxError(e);
		},
		loadFilter : function(data) {
			var ticketLists = [ {
				ticketTypeId : '',
				ticketTypeName : '--全部--'
			} ];
			return $.merge(ticketLists, data);
		}
	});
	/*// 网点初始化
	$('#outletId').combobox({
		url : $ctx + '/comm/listOutletByType.do',
		method : 'get',
		valueField : 'outletId',
		textField : 'outletName',
		multiple : false,
		editable : false,
		onLoadSuccess : function(e) {
		},
		onLoadError : function(e) {
			getAjaxError(e);
		},
		loadFilter : function(data) {
			var outletLists = [ {
				outletId : '',
				outletName : '--全部--' 
			} ];
			return $.merge(outletLists, data);
		},
		formatter : function(rows) {
			return rows.outletName;
		}
	});*/
}

function myQuery() {
	var beginDt = $('#beginDt').datebox('getValue');
    var endDt = $('#endDt').datebox('getValue');
    var outletId=$('#outletId').combobox('getValue');
    var ticketTypeId=$('#ticketTypeId').combobox('getValue');
    if(beginDt>endDt){
		$.messager.alert('系统提示','开始日期不能大于结束日期');
	   return false;
	}
	var queryParams = {
		'beginDt' : beginDt,
		'endDt':endDt,
		'outletId':outletId,
		'ticketTypeId':ticketTypeId
	}
	$('#mytable').datagrid('options').queryParams = queryParams;
	$('#mytable').datagrid('load');
	return true;
}
function expExcel(){
	var url=$ctx + '/rptsale/expExcelDelivery.do';
	 $('#queryForm').attr("action", url).submit();
}

