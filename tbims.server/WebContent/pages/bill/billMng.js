/**
 * 精确搜索
 */
function queryRole() {
	var q_stat = $("#q_stat").textbox("getValue");
	var q_billDateStart = $("#q_billDateStart").textbox("getValue");
	var q_billDateEnd = $("#q_billDateEnd").textbox("getValue");

	var queryParams = {
		"stat" : q_stat,
		"billDateStart" : q_billDateStart,
		"billDateEnd" : q_billDateEnd
	};
	$dg.datagrid('options').queryParams = queryParams;
	$dg.datagrid('load');
	clsCheck("#mytable");// 清空选择的行
	return true;
}

/**
 * 表格内操作按钮
 */
function getstr(value, row, index) {
	var s1, s2;
	s1 = '<a href="javascript:void(0)" onclick="billDetail(' + index + ')">对账明细</a> ';
	s2 = '<a href="javascript:void(0)" onclick="redobill(' + index + ')">重新对账</a> ';
	return s1 + s2;
}

/**
 * 对账明细列表
 * 
 * @param index
 */
function billDetail(index) {
	var rowData = $dg.datagrid("getRows")[index];
	var billId = rowData.billId;
	top.addTab('对账明细', $ctx + '/pages/bill/billDetail.jsp?billId=' + billId);
}

/**
 * 重新对账
 * 
 * @param index
 */
function redobill(index) {
	var rowData = $dg.datagrid("getRows")[index];
	var billId = rowData.billId;
	$.messager.progress({
		text : '正在处理,请稍候...'
	});
	$.ajax({
		url : $ctx + "/bill/redoBill.do",
		type : "post",
		data : {
			"billId" : billId,
		},
		success : function(e) {
			$.messager.progress("close");
			$dg.datagrid("reload");
			$.messager.show({
				showType : 'slide',
				title : "提示信息",
				msg : "处理完成",
				timeout : 2000
			});

		},
		error : function(e) {
			getAjaxError(e);
		}
	});
}

/**
 * 初始化列表
 */
function createMytable() {
	$dg.datagrid({
		nowrap : true,// 是否只显示一行，即文本过多是否省略部分。
		striped : true,
		url : $ctx + '/bill/listBillInfo.do', // controller地址
		onLoadSuccess : function(e) {
		},
		onLoadError : function(e) {
			getAjaxError(e);
		},
		idField : 'billId',
		fitColumns : false,
		fit : true,
		rownumbers : true,
		pagination : true, // 包含分页
		pageSize : getPageSize(),
		pageList : getPageList(),
		toolbar : "#mytable-buttons",
		columns : [ [// 显示的列
		{
			field : 'billDate',
			title : '账单日期',
			width : 100,
			align : 'center'
		}, {
			field : 'payFeeTotal',
			title : '支付总金额',
			width : 100,
			align : 'center'
		}, {
			field : 'payNumTotal',
			title : '支付总笔数',
			width : 100,
			align : 'center'
		}, {
			field : 'refundFeeTotal',
			title : '退款总金额',
			width : 100,
			align : 'center'
		}, {
			field : 'refundNumTotal',
			title : '退款总笔数',
			width : 100,
			align : 'center'
		}, {
			field : 'payNumFail',
			title : '支付对账失败总笔数',
			width : 120,
			align : 'center'
		}, {
			field : 'refundNumFail',
			title : '退款对账失败总笔数',
			width : 120,
			align : 'center'
		}, {
			field : 'statInfo',
			title : '对账结果',
			align : 'center'
		}, {
			field : 'opt',
			title : '操作',
			width : 120,
			align : 'center',
			formatter : getstr
		} ] ]
	});

}

$(function() {
	$dg = $('#mytable');

	$('#q_stat').combobox({
		valueField : 'id',
		textField : 'text',
		editable : false,
		panelHeight : 'auto',
		width : 120,
		data : [ {
			'id' : '',
			'text' : '--全部--'
		}, {
			'id' : '0',
			'text' : '对账成功'
		}, {
			'id' : '1',
			'text' : '对账失败'
		} ]
	});

	// 创建树数据列表
	createMytable();
});