$(function() {
	$myTable = $('#mytable');
	var param = {
		examStat : '3'
	};
	createApplyTable(param);
	initcombo();
});

function createApplyTable(param) {
	$myTable.datagrid({
		nowrap : true,// 是否只显示一行，即文本过多是否省略部分。
		striped : true,
		url : $ctx + '/delivery/listDeliveryApplyH.do', // controller地址
		onLoadSuccess : function(e) {
		},
		onLoadError : function(e) {
			getAjaxError(e);
		},
		idField : 'applyId',
		fitColumns : true,
		rownumbers : true,
		pagination : true,
		fit : true,
		queryParams : param,
		columns : [ [// 显示的列
		{
			field : 'applyId',
			title : '申请编号',
			align : 'center',
			width : 120,
			halign : 'center'
		}, {
			field : 'applyUserName',
			title : '申请人',
			align : 'center',
			width : 120,
			halign : 'center'
		}, {
			field : 'applyTime',
			title : '申请时间',
			align : 'center',
			width : 120,
			halign : 'center'
		}, {
			field : 'applyDeliveryTime',
			title : '申请配送时间',
			align : 'center',
			width : 120,
			halign : 'center'
		}, {
			field : 'examStat',
			title : '审核状态',
			align : 'center',
			width : 100,
			halign : 'center',
			formatter : getExamStat
		}, {
			field : 'caozuo',
			title : '操作',
			align : 'center',
			width : 120,
			formatter : getstr
		} ] ],
		toolbar : '#mytable-buttons'
	});
}
function query() {
	var examStat = $('#examStat').combobox('getValue');
	var applyId = $('#applyId').textbox('getValue');
	var beginApplyTime = $('#beginApplyTime').datetimebox('getValue');
	var endApplyTime = $('#endApplyTime').datetimebox('getValue');
	var queryParams = {
		'examStat' : examStat,
		'applyId' : applyId,
		'beginApplyTime' : beginApplyTime,
		'endApplyTime' : endApplyTime
	}
	$myTable.datagrid('options').queryParams = queryParams;
	$myTable.datagrid('load');
	clsCheck('#mytable');
	return true;
}
function initcombo() {
	/*
	 * $.ajax({ url : $ctx + "/comm/listItemsByKey.do", data :
	 * "key=DELIVERY_STAT", type : "post", success : function(e) {
	 * $("#examStat").combobox({ width : 160, required : true, editable : false,
	 * panelHeight:'auto', valueField : 'id', textField : 'text', data:e });
	 * $('#examStat').combobox('setValue','3'); }, error : function(e) {
	 * getAjaxError(e); } });
	 */
	$("#examStat").combobox({
		width : 160,
		required : true,
		editable : false,
		panelHeight : 'auto',
		valueField : 'id',
		textField : 'text',
		data : [ {
			id : '3',
			text : '已接收'
		}, {
			id : '5',
			text : '已拒绝'
		} ]
	});
	$('#examStat').combobox('setValue', '3');
}
function getstr(value, row, index) {
	var selRow = $myTable.datagrid('getRows')[index];
	var s1;
	s1 = '<span class="datagrida"><a href="javascript:void(0)" title="详情"  onclick="showDetail(' + index + ')">查看详情</a> <span>';
	return s1;
}
function getExamStat(value, row, index) {
	var s1 = '';
	var selRow = $myTable.datagrid('getRows')[index];
	var examStat = selRow.examStat;
	if ('0' == examStat) {
		s1 = "待审核";
	} else if ('1' == examStat) {
		s1 = "已审核";
	} else if ('2' == examStat) {
		s1 = "已配送";
	} else if ('3' == examStat) {
		s1 = "已接收";
	} else if ('4' == examStat) {
		s1 = "已拒收";
	} else if ('5' == examStat) {
		s1 = "已拒绝";
	}
	return s1;
}

function showDetail(index) {
	var rowData = $myTable.datagrid("getRows")[index];
	var applyId = rowData.applyId;
	var examStat = rowData.examStat;
	var url = $ctx + '/pages/delivery/deliveryDetailH.jsp?applyId=' + applyId + "&examStat=" + examStat;
	var iframe = '<iframe id="detailIframe" src=' + url + ' frameborder="0" width="100%" height="100%" scrolling="no"></iframe>'
	$("#detailDialog").dialog({
		title : "配送申请详情",
		resizable : true,
		inline : true,
		content : iframe,
		cache : false,
		modal : true,
		onLoadError : function(e) {
			getAjaxError(e);
		},
		onclose : function() {
			$("#detailIframe").remove();
		}
	}).dialog("open");
}
function createApplyExamTable(applyId) {
	$('#applyExamtable').datagrid({
		nowrap : true,// 是否只显示一行，即文本过多是否省略部分。
		striped : true,
		fitColumns : true,
		rownumbers : true,
		fit : true,
		height : 200,
		url : $ctx + '/delivery/applyExamDetail.do?applyId=' + applyId, // controller地址
		onLoadSuccess : function(e) {
		},
		onLoadError : function(e) {
			getAjaxError(e);
		},
		idField : 'applyExamId',
		columns : [ [// 显示的列
		{
			field : 'ticketTypeId',
			title : '票种编号',
			width : 100,
			align : 'center'
		}, {
			field : 'ticketTypeName',
			title : '门票种类',
			width : 120,
			align : 'center'
		}, {
			field : 'applyNum',
			title : '申请数量（张）',
			width : 120,
			align : 'center'
		}, {
			field : 'examNum',
			title : '审核数量（张）',
			width : 120,
			align : 'center'
		} ] ]
	});

}
function createDeliveryTable(applyId) {
	$('#deliverytable').datagrid({
		url : $ctx + '/delivery/deliveryDetail.do?applyId=' + applyId, // controller地址
		fitColumns : true,
		rownumbers : true,
		height : 200,
		striped : true,
		fit : true,
		idField : 'chestId',
		columns : [ [// 显示的列
		{
			field : 'ticketTypeId',
			title : '票种编号',
			align : 'center',
			width : 100
		}, {
			field : 'ticketTypeName',
			title : '门票种类',
			align : 'center',
			width : 120
		}, {
			field : 'chestId',
			title : '箱号',
			align : 'center',
			width : 100
		}, {
			field : 'beginNo',
			title : '起号',
			editor : "text",
			align : 'center',
			width : 100
		}, {
			field : 'endNo',
			title : '止号',
			editor : "text",
			align : 'center',
			width : 100
		} ] ]
	});
}