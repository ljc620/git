$(function() {
	$myTable = $('#mytable');
	$orgId = $('#orgId').val();
	$ticketTypeId=$('#ticketTypeId').val();
	createLimitTable();
});
function createLimitTable() {
	$myTable.datagrid({
		nowrap : true,// 是否只显示一行，即文本过多是否省略部分。
		striped : true,
		url : $ctx + '/orgMng/listLimit.do?orgId='+$orgId+"&ticketTypeId="+$ticketTypeId, // controller地址
		onLoadSuccess : function(e) {
		},
		onLoadError : function(e) {
			getAjaxError(e);
		},
		idField : 'ticketTypeId',
		fitColumns : false,
		fit : true,
		checkbox : true,
		rownumbers : true,
		rownumbers : true,
		singleSelect : true,
		striped : true,
		pagination : true,
		frozenColumns : [ [ {
			field : 'ck',
			checkbox : false
		} ] ],
		
		columns : [ [// 显示的列
		{
			field : 'limit',
			title : '额度',
			width : 120,
			editor : "text",
			align : 'center'
		}, {
			field : 'opeTime',
			title : '操作时间',
			align : 'right',
			halign: 'center',
			width : 150
		} , {
			field : 'remark',
			title : '备注',
			align : 'center',
			halign: 'center',
			width : 200
		}] ]
	});

}