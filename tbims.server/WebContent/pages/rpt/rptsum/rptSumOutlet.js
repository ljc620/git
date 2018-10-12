$(function() {
	$myTable = $('#mytable');
	//$('#rptDate').datebox('setValue',formatterDate(new Date()));
	var startDate=$('#startDate').datebox('getValue');
	var endDate=$('#endDate').datebox('getValue');
	queryParams={
			'startDate' : startDate	,
			'endDate':endDate
	};
	createTicketTypeTable(queryParams);
	initCombobox();
});
function createTicketTypeTable(queryParams) {
	$myTable.datagrid({
		nowrap : true,// 是否只显示一行，即文本过多是否省略部分。
		striped : true,
		url : $ctx + '/rptsum/listRptSumOutlet.do', // controller地址
		onLoadSuccess : function(e) {
		},
		onLoadError : function(e) {
			getAjaxError(e);
		},
		fitColumns : true,
		fit : true,
		rownumbers : true,
		singleSelect : true,
		queryParams : queryParams, 
		striped : true,
		pagination : true,		
		columns : [ [// 显示的列
         {
   			field : 'rptDate',
   			title : '统计日期',
   			align : 'center',
 			width:100
   		},{
 			field : 'outletId',
 			title : '网点名称',
 			hidden:true,
 			width:100
 		},{
 			field : 'outletName',
 			title : '网点名称',
 			align : 'center',
 			halign: 'center',
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
 		},{
			field : 'strLastTicketNum',
			title : '上日库存张数',
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
			field : 'saleTicketNum',
			title : '售票张数（包括换票）',
			editor : "text",
			align : 'center',
			width:100
		} 
//		, {
//			field : 'uselessTicketNum',
//			title : '坏票废票张数',
//			align : 'center',
//			halign: 'center',
//			width:100
//		}
		, {
			field : 'supplyTicketNum',
			title : '补票废票张数',
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