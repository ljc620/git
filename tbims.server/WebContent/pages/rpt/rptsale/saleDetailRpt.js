$(function() {
	$myTable = $('#mytable');
	$('#rptDt').datebox('setValue',formatterDate(new Date()));
	var rptDt=$('#rptDt').datebox('getValue');
	var beginTime=$('#beginTime').timespinner('getValue');
	var endTime=$('#endTime').timespinner('getValue');
	var queryParams={"rptDt":rptDt,
			         "beginTime":beginTime,
			         "endTime":endTime};
	createTicketTypeTable(queryParams);
});

function createTicketTypeTable(myqueryParams) {
	$myTable.datagrid({
		nowrap : true,// 是否只显示一行，即文本过多是否省略部分。
		striped : true,
		url : $ctx + '/rptsale/saleDetail.do', // controller地址
		onLoadSuccess : function(e) {
		},
		onLoadError : function(e) {
			getAjaxError(e);
		},
		queryParams:myqueryParams,
		fitColumns : true,
		fit : true,
		rownumbers : true,
		singleSelect : true,
		striped : true,
		pagination : true,		
		columns : [ [// 显示的列
         {
 			field : 'ejectTicketTime',
 			title : '售票时间',
 			align : 'center',
 			halign: 'center',
 			width:120
 		}, {
			field : 'outletName',
			title : '售票网点',
			halign: 'center',
			align : 'center',
			width:100
		},{
 			field : 'ticketTypeName',
 			title : '票种名称',
 			align : 'center',
 			halign: 'center',
 			width:100
 		} ,{
  			field : 'ticketId',
  			title : '票号',
  			align : 'center',
  			halign: 'center',
			width:100
  		}, {
			field : 'orderTypeDesc',
			title : '销售类型',
			halign: 'center',
			align : 'center',
			width:100
		}, {
			field : 'payTypeDesc',
			title : '支付方式',
			halign: 'center',
			align : 'center',
			width:100
			
		}, {
			field : 'salePrice',
			title : '售价',
			halign: 'center',
			align : 'center',
			width:100
		}, {
			field : 'userName',
			title : '售票人',
			halign: 'center',
			align : 'center',
			width:100
		}] ],
		toolbar : '#mytable-buttons'
	});

}
function query(){
	var rptDt=$('#rptDt').datebox('getValue');
	var beginTime=$('#beginTime').timespinner('getValue');
	var endTime=$('#endTime').timespinner('getValue');
	if(beginTime>endTime){
		$.messager.alert('系统提示','开始时间不能大于结束时间');
		return false;
	}
	var ticketTypeId=$('#ticketTypeId').combobox('getValue');
	var outletId=$('#outletId').combobox('getValue');
	var ejectUserId=$('#ejectUserId').combobox('getValue');
    var queryParams = {
		'ticketTypeId' : ticketTypeId,
		'beginTime' : beginTime,
		'endTime' : endTime,
		'outletId' : outletId,
		'ejectUserId' : ejectUserId,
		'rptDt' : rptDt
	}
	$('#mytable').datagrid('options').queryParams = queryParams;
	$('#mytable').datagrid('load');
	return true;
}
function expExcel(){
	if (!$('#queryForm').form('validate')) {
		return false;
	}
	var beginTime=$('#beginTime').timespinner('getValue');
	var endTime=$('#endTime').timespinner('getValue');
	if(beginTime>endTime){
		$.messager.alert('系统提示','开始时间不能大于结束时间');
		return false;
	}
	var url= $ctx + '/rptsale/expExlSaleDetail.do';
	 $('#queryForm').attr("action", url).submit();
}

