$(function() {
	$myTable = $('#mytable');
	createApplyTable();
	initcombo();
});

function createApplyTable() {
	$myTable.datagrid({
		nowrap : true,// 是否只显示一行，即文本过多是否省略部分。
		striped : true,
		url : $ctx + '/delivery/listDeliveryApply.do', // controller地址
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
		columns : [ [// 显示的列
		{
			field : 'applyId',
			title : '申请编号',
			align : 'center',
			halign : 'center',
			width : 150
		}, {
			field : 'applyUserName',
			title : '申请人',
			align : 'center',
			halign : 'center',
			width : 100
		}, {
			field : 'applyTime',
			title : '申请时间',
			align : 'center',
			halign : 'center',
			width : 120
		}, {
			field : 'applyDeliveryTime',
			title : '申请配送日期',
			align : 'center',
			halign : 'center',
			width : 120
		}, {
			field : 'examStat',
			title : '审核状态',
			align : 'center',
			halign : 'center',
			formatter : getExamStat
		}, {
			field : 'caozuo',
			title : '操作',
			align : 'center',
			width : 150,
			formatter : getstr
		} ] ],
		toolbar : '#mytable-buttons'
	});
}
function myQuery() {
	var examStat = $('#examStat').combobox('getValue');
	var applyId = $('#applyId').textbox('getValue');

	var queryParams = {
		'examStat' : examStat,
		'applyId' : applyId

	}
	$myTable.datagrid('options').queryParams = queryParams;
	$myTable.datagrid('load');
	clsCheck('#mytable');
	return true;
}
function initcombo() {
	/*
	 * $.ajax({ url : $ctx + "/comm/listItemsByKey.do", data :
	 * "key=DELIVERY_EXAM_STAT", type : "post", success : function(e) {
	 * $("#examStat").combobox({ width : 120, required : true, editable : false,
	 * panelHeight : 'auto', valueField : 'id', textField : 'text', data : e });
	 * $('#examStat').combobox('setValue', '0'); }, error : function(e) {
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
			id : '',
			text : '--全部--'
		},{
			id : '0',
			text : '待审核'
		}, {
			id : '1',
			text : '已审核'
		}, {
			id : '2',
			text : '已配送'
		} ]
	});
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
/**
 * 配送申请
 * 
 * @param index
 */
function deliveryApply() {
	var url = $ctx + '/delivery/deliveryApply.do';
	var iframe = '<iframe id="applyIframe" src=' + url + ' frameborder="0" width="100%" height="100%"></iframe>'
	$("#applyDialog").dialog({
		title : "配送申请",
		resizable : true,
		inline : true,
		content : iframe,
		cache : false,
		modal : true,
		buttons : "#dlg-buttons",
		onLoadError : function(e) {
			getAjaxError(e);
		},
		onclose : function() {
			$("#applyIframe").remove();
		}
	}).dialog("open");
}
function showDetail(index) {
	var rowData = $myTable.datagrid("getRows")[index];
	var applyId = rowData.applyId;
	var examStat = rowData.examStat;
	var url = $ctx + '/pages/delivery/deliveryDetail.jsp?applyId=' + applyId + "&examStat=" + examStat;
	var iframe = '<iframe id="detailIframe" src=' + url + ' frameborder="0" width="100%" height="100%" scrolling="no"></iframe>'
	$("#detailDialog").dialog({
		title : "配送申请详情",
		resizable : true,
		inline : true,
		content : iframe,
		cache : false,
		modal : true,
		buttons : "#dlg-buttons",
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
		height:200,
		url : $ctx + '/delivery/applyExamDetail.do?applyId=' + applyId, // controller地址
		onLoadSuccess : function(e) {
		},
		onLoadError : function(e) {
			getAjaxError(e);
		},
		idField : 'applyExamId',
		fitColumns : true,
		rownumbers : true,
		fit : true,
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
		striped : true,
		fit : true,
		height:200,
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
function confirmReceive() {
	var applyId = $('#applyId').val();
	var examStat = $('#hexamStat').val();
	if (examStat != '2') {
		$.messager.show({
			showType : 'slide',
			title : "提示信息",
			msg : "此申请单当前不是【已配送】状态",
			timeout : 2000
		});
		return false;
	}
	
	parent.$.messager.progress({
		text : '正在处理,请勿重复提交数据,请稍候...'
	});
	
	$.ajax({
		url : $ctx + "/delivery/confrimReceive.do",
		type : "post",
		data : {
			"applyId" : applyId,
			"examStat" : '3'// 已接收状态
		},
		success : function(e) {
			parent.$.messager.progress("close");
			parent.$.messager.show({
				showType : 'slide',
				title : "提示信息",
				msg : "接收成功",
				timeout : 2000
			});
			parent.$myTable.datagrid("reload");
			parent.$("#detailDialog").dialog("close");
		},
		error : function(e) {
			getAjaxErrorByParent(e);
		}
	});
}