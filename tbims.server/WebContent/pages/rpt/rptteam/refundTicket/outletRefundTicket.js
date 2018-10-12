$(function() {
	$myTable = $('#mytable');
	createTable();
});
function createTable() {
	$myTable.datagrid({
		nowrap : true,// 是否只显示一行，即文本过多是否省略部分。
		striped : true,
		url : $ctx + '/rptTeam/outletRefundTicket.do', // controller地址
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
		queryParams : {
			refundTimeBegin : formatterDate(new Date()),
			refundTimeEnd : formatterDate(new Date())
		},
		columns : [ [// 显示的列
		{
			field : 'refundTime',
			title : '退款时间',
			width : 150,
			align : 'center'
		}, {
			field : 'saleTime',
			title : '销售时间',
			width : 150,
			align : 'center'
		}, {
			field : 'orderType',
			title : '交易类型',
			width : 150,
			align : 'center'
		}, {
			field : 'orderStat',
			title : '订单状态',
			width : 150,
			align : 'center'
		}, {
			field : 'payType',
			title : '支付方式',
			width : 150,
			align : 'center'
		}, {
			field : 'ticketCount',
			title : '销售张数',
			width : 100,
			align : 'center'
		}, {
			field : 'realSum',
			title : '销售金额',
			width : 100,
			align : 'center'
		}, {
			field : 'tradeNum',
			title : '退款张数',
			width : 100,
			align : 'center'
		}, {
			field : 'refundAmtSum',
			title : '退款金额',
			width : 100,
			align : 'center'
		} ] ],
		toolbar : '#mytable-buttons'
	});

}
function query() {
	var refundTimeBegin = $('#refundTimeBegin').datebox('getValue');
	var refundTimeEnd = $('#refundTimeEnd').datebox('getValue');

	var queryParams = {
		'refundTimeBegin' : refundTimeBegin,
		'refundTimeEnd' : refundTimeEnd
	}
	$myTable.datagrid('options').queryParams = queryParams;
	$myTable.datagrid('load');
	clsCheck('#mytable');
	return true;
}
function expExcel() {
	var url = $ctx + '/rptTeam/expOutletRefundTicket.do';
	$('#form').attr("action", url).submit();
}