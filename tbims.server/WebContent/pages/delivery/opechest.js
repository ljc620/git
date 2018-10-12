$(function() {
		$myTable = $('#mytable');
		createOrgTable();
		initCombobox();
});
function createOrgTable() {
	
	$myTable.datagrid({
		nowrap : true,// 是否只显示一行，即文本过多是否省略部分。 
		striped : true,
		url : $ctx + '/opechest/listOpeChest.do', // controller地址 opeTime
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
			width:80
		},{
			field : 'ticketTypeId',
			title : '票种名称',
			hidden:true,
			width:80
		},{
			field : 'ticketTypeName',
			title : '票种名称',
			align : 'center',
			halign: 'center',
			width:100
		}, {
			field : 'beginNo',
			title : '箱号票起号',
			editor : "text",
			align : 'center',
			width:100
		}, {
			field : 'endNo',
			title : '箱号票止号',
			editor : "text",
			align : 'center',
			width:100
		}, {
			field : 'batchId',
			title : '批次号',
			editor : "text",
			align : 'center',
			width:200
		}, {
			field : 'stat',
			title : '状态',
			editor : "text",
			align : 'center',
			formatter : getStat,
			width:80
		}, {
			field : 'boxNum',
			title : '盒数',
			editor : "text",
			align : 'center',
			width:80
		}, {
			field : 'ticketNum',
			title : '门票数量',
			editor : "text",
			align : 'center',
			width:80
		},{
			field : 'opeUserId',
			title : '入库人',
			hidden:true,
			width:80
		}, {
			field : 'userName',
			title : '入库人',
			editor : "text",
			align : 'center',
			width:100
		}, {
			field : 'opeTime',
			title : '入库时间',
			editor : "text",
			align : 'center',
			width:100
		}] ],
		toolbar : '#mytable-buttons'  
	});

}
function getStat(value, row, index) {
	if("001"==value){
		return "已入库";
	}
	else{
		return "";
	}
}
function myQuery() {

	var chestId = $('#chestId').textbox('getValue');
	var batchId = $('#batchId').textbox('getValue');
	var opeTime = $('#opeTime').datebox('getValue');
	var ticketTypeId = $('#ticketTypeId').combobox('getValue');
	var queryParams = {
		'chestId':chestId,
		'batchId':batchId,
		'opeTime':opeTime,
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