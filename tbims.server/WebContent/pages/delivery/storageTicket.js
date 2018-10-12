$(function() {
		$myTable = $('#mytable');
		createOrgTable();
		initCombobox();
});
function createOrgTable() {
	
	$myTable.datagrid({
		nowrap : true,// 是否只显示一行，即文本过多是否省略部分。 
		striped : true,
		url : $ctx + '/storageTicket/listStorageTicket.do', // controller地址 opeTime
		onLoadSuccess : function(e) {
		},
		onLoadError : function(e) {
			getAjaxError(e);
		},
		fitColumns : true,
		rownumbers : true,
		singleSelect : true,
		striped : true,
		pagination : true,
	
		fit:true,
		columns : [ [// 显示的列
		 {
			field : 'chestId',
			title : '箱号',
			editor : "text",
			align : 'center',
			width:100
		},{
			field : 'ticketTypeId',
			title : '票种名称',
			hidden:true,
			width:100
		},{
			field : 'ticketTypeName',
			title : '票种名称',
			align : 'center',
			halign: 'center',
			width:100
		}, {
			field : 'beginNo',
			title : '箱票号起号',
			editor : "text",
			align : 'center',
			width:100
		}, {
			field : 'endNo',
			title : '箱票号止号',
			editor : "text",
			align : 'center',
			width:100
		}
		, {
			field : 'ticketNum',
			title : '箱门票数量',
			editor : "text",
			align : 'center',
			width:100
		}] ],
		toolbar : '#mytable-buttons' 
	});

}
function myQuery() {

	var chestId = $('#chestId').textbox('getValue');
	var ticketTypeId = $('#ticketTypeId').combobox('getValue');
	var queryParams = {
		'chestId':chestId,
		'ticketTypeId':ticketTypeId
	}
	$myTable.datagrid('options').queryParams = queryParams;
	$myTable.datagrid('load');
	clsCheck('#mytable');
	return true;
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
