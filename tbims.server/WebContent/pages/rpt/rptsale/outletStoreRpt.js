$(function() {
	$myTable = $('#mytable');
	createTicketTypeTable();
	 initCombobox();
});
function createTicketTypeTable() {
	$myTable.datagrid({
		nowrap : true,// 是否只显示一行，即文本过多是否省略部分。
		striped : true,
		url : $ctx + '/rptsale/outletStoreList.do', // controller地址
		onLoadSuccess : function(e) {
		},
		onLoadError : function(e) {
			getAjaxError(e);
		},
		idField : 'outletName',
		fitColumns : false,
		fit : true,
		//checkbox : true,
		rownumbers : true,
		singleSelect : true,
		striped : true,
		pagination : true,		
		columns : [ [// 显示的列
		 {
		   field : 'outletName',
		   title : '网点名称',
		   align : 'center',
		   width:220,
		   halign: 'center'
		 },
		{
			field : 'ticketTypeName',
			title : '票种名称',
			align : 'center',
			width:220,
			halign: 'center'
		}, {
			field : 'strLastTicketNum',
			title : '上日库存张数',
			align : 'center',
			width:150,
			halign: 'center'
		}, {
			field : 'inTicketNum',
			title : '入库张数',
			editor : "text",
			width:150,
			align : 'center',
			halign: 'center'
			
		}, {
			field : 'saleTicketNum',
			title : '售票张数（包括换票）',
			editor : "text",
			align : 'center',
			width:150,
			halign: 'center'
		}
//		, {
//			field : 'uselessTicketNum',
//			title : '坏票废票张数',
//			editor : "text",
//			align : 'center',
//			width:150,
//			halign: 'center'
//		}
		, {
			field : 'supplyTicketNum',
			title : '补票废票张数',
			editor : "text",
			align : 'center',
			width:150,
			halign: 'center'
		}, {
			field : 'strTicketNum',
			title : '库存张数',
			editor : "text",
			align : 'center',
			width:150,
			halign: 'center'
		} ] ],
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
    var ticketTypeId=$('#ticketTypeId').combobox('getValue');
    var outletId=$('#outletId').combobox('getValue');
	var queryParams = {
		'ticketTypeId' : ticketTypeId,
		'outletId':outletId
	}
	$('#mytable').datagrid('options').queryParams = queryParams;
	$('#mytable').datagrid('load');
	return true;
}
function expExcel(){
	var url=$ctx +'/rptsale/expExlOutletStore.do';
	 $('#queryForm').attr("action", url).submit();
}