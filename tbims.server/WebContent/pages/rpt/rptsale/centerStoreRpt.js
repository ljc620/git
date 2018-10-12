$(function() {
	$myTable = $('#mytable');
	createTicketTypeTable();
	 initCombobox();
});
function createTicketTypeTable() {
	$myTable.datagrid({
		nowrap : true,// 是否只显示一行，即文本过多是否省略部分。
		striped : true,
		url : $ctx + '/rptsale/centerStoreList.do', // controller地址
		onLoadSuccess : function(e) {
		},
		onLoadError : function(e) {
			getAjaxError(e);
		},
		idField : 'outletName',
		fitColumns : true,
		fit : true,
		//checkbox : true,
		rownumbers : true,
		singleSelect : true,
		striped : true,
		pagination : true,		
		columns : [ [// 显示的列
		{
			field : 'ticketTypeName',
			title : '票种名称',
			align : 'center',
			halign: 'center',
			width:100
		}, {
			field : 'strLastChestNum',
			title : '上日库存箱数',
			align : 'center',
			width:100
		}, {
			field : 'strLastTicketNum',
			title : '上日库存张数',
			editor : "text",
			align : 'center',
			width:100
			
		}, {
			field : 'inChestNum',
			title : '入库箱数',
			editor : "text",
			align : 'center',
			width:100
		}, {
			field : 'inTicketNum',
			title : '入库张数',
			editor : "text",
			align : 'center',
			width:100
		}, {
			field : 'outChestNum',
			title : '出库箱数',
			editor : "text",
			align : 'center',
			width:100
		} , {
			field : 'outTicketNum',
			title : '出库张数',
			align : 'center',
			halign: 'center',
			width:100
		}, {
			field : 'strChestNum',
			title : '库存箱数',
			align : 'center',
			halign: 'center',
			width:100
		}, {
			field : 'strTicketNum',
			title : '库存张数',
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
	
}

function myQuery() {
    var ticketTypeId=$('#ticketTypeId').combobox('getValue');
	var queryParams = {
		'ticketTypeId' : ticketTypeId
	}
	$('#mytable').datagrid('options').queryParams = queryParams;
	$('#mytable').datagrid('load');
	return true;
}
function expExcel(){
	var url=$ctx + '/rptsale/expExcelCenterStore.do';
	 $('#queryForm').attr("action", url).submit();
}