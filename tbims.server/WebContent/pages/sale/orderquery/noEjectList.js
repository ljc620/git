$(function() {
	$dg = $('#mytable');
	createOrdertable();
});

/**
 * 修改删除操作
 * 
 * @param value
 * @param row
 * @param index
 * @returns {String}
 */
function getstr(value, row, index) {
	var s1;
	var payStat = row.payStat;
	if (payStat == '2') {
		s1 = '<span class="datagrida"><a href="javascript:void(0)" title="发起退款" onclick="refundTicketByNOEject(' + index + ')">发起退款</a> ';
	}
	return s1;
}

/**
 * 发起退款
 * 
 * @param index
 */
function refundTicketByNOEject(index) {
	var rowData = $dg.datagrid("getRows")[index];

	$.ajax({
		url : $ctx + '/slOrderQuery/queryRefundByNOEject.do', // 查询退款信息
		type : "post",
		data : {
			"orderId" : rowData.orderId,
			"ticketTypeId" : rowData.ticketTypeId
		},
		success : function(e) {
			var messge = '您确定要发起退款吗？<br/> <br/><span style="color:red;">退款张数:[' + e.refundNum + '],退款金额:[' + e.refundFee + ']<span>';
			$.messager.confirm('提示', messge, function(r) {
				if (r) {
					$.ajax({
						url : $ctx + '/slOrderQuery/refundTicketByNOEject.do', // 发起退款
						type : "post",
						data : {
							"orderId" : rowData.orderId,
							"refundFee" : e.refundFee
						},
						success : function(e) {
							$dg.datagrid("reload");
							$dg.datagrid('clearSelections');
							$.messager.show({
								showType : 'slide',
								title : "提示信息",
								msg : "退款成功",
								timeout : 2000
							});

						},
						error : function(e) {
							getAjaxError(e);
						}
					});
				}
			});
		},
		error : function(e) {
			getAjaxError(e);
		}
	});

}

/**
 * 初始化用户列表
 */
function createOrdertable() {
	$dg.datagrid({
		nowrap : true,// 是否只显示一行，即文本过多是否省略部分。
		striped : true,
		url : $ctx + '/slOrderQuery/querySlOrderByNOEjectList.do', // controller地址
		onLoadSuccess : function(e) {
		},
		onLoadError : function(e) {
			getAjaxError(e);
		},
		idField : 'orderId',
		fitColumns : false,
		fit : true,
		checkbox : true,
		rownumbers : true,
		singleSelect : false,
		pagination : true, // 包含分页
		pageSize : getPageSize(),
		pageList : getPageList(),
		frozenColumns : [ [ {
			field : 'ck',
			checkbox : true
		} ] ],
		columns : [ [// 显示的列
		{
			field : 'bankId',
			title : '商户单号',
			width : 220,
			align : 'center'
		}, {
			field : 'saleTime',
			title : '销售时间',
			width : 150,
			align : 'center'
		}, {
			field : 'ticketTypeName',
			title : '票种',
			width : 120,
			align : 'center'
		}, {
			field : 'payType',
			title : '支付类型',
			width : 120,
			align : 'center',
			formatter : function(value, row, index) {
				if (value == '01') {
					value = '现金';
				} else if (value == '02') {
					value = 'POS付款';
				} else if (value == '03') {
					value = '微信';
				} else if (value == '04') {
					value = '支付宝';
				} else if (value == '05') {
					value = '代理';
				}
				return value;
			}
		}, {
			field : 'payStat',
			title : '支付状态',
			align : 'center',
			width : 120,
			formatter : function(value, row, index) {
				if (value == '1') {
					value = '待支付';
				} else if (value == '2') {
					value = '已支付';
				} else if (value == '3') {
					value = '支付失败';
				}
				return value;
			}
		}, {
			field : 'realSum',
			title : '实收金额',
			width : 80,
			align : 'center'
		}, {
			field : 'ticketCount',
			title : '售票张数',
			width : 80,
			align : 'center'
		}, {
			field : 'ejectTicketCount',
			title : '出票张数',
			width : 80,
			align : 'center'
		}, {
			field : 'remark',
			title : '备注',
			align : 'center'
		}, {
			field : 'caozuo',
			title : '操作',
			align : 'center',
			formatter : getstr,
			width : 90
		} ] ],
		toolbar : '#mytable-buttons'
	});
}

/**
 * 查询用户
 * 
 * @returns {Boolean}
 */
function query() {
	var saleTimeBegin = $('#saleTimeBegin').textbox('getValue');
	var saleTimeEnd = $('#saleTimeEnd').textbox('getValue');

	var queryParams = {
		'saleTimeBegin' : saleTimeBegin,
		'saleTimeEnd' : saleTimeEnd
	}

	$dg.datagrid('options').queryParams = queryParams;
	$dg.datagrid('load');
	clsCheck('#mytable');
	return true;
}
