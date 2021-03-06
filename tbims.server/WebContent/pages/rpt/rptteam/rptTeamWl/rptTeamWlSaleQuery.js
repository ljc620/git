$(function() {
	$myTable = $('#mytable');
	createTable();
	initCombobox();
});

function createTable() {
	$myTable
			.datagrid({
				nowrap : true,// 是否只显示一行，即文本过多是否省略部分。
				striped : true,
				url : $ctx + '/rptTeam/listRptTeamWlSale.do', // controller地址
				onLoadSuccess : function(e) {
					$(this)
							.datagrid(
									'appendRow',
									{
										itemName : '<span style="font-weight:900;color:#000;">合计</span>',
										saleNum : compute('#mytable', 'saleNum'),
										saleAmt : compute('#mytable', 'saleAmt'),
										ejectNum : compute('#mytable',
												'ejectNum'),
										tradeNum : compute('#mytable',
												'tradeNum'),
										tradeAmt : compute('#mytable',
												'tradeAmt'),
										checkNum : compute('#mytable',
												'checkNum'),
										checkAmt : compute('#mytable',
												'checkAmt')
									});
				},
				onLoadError : function(e) {
					getAjaxError(e);
				},
				fitColumns : false,
				rownumbers : true,
				striped : true,
				pagination : false,
				fit : true,
				queryParams : {
					opeTime : formatterDate(new Date()),
					opeTime2 : formatterDate(new Date())
				},
				columns : [ [// 显示的列
				{
					field : 'opeTime',
					title : '销售日期',
					width : 150,
					align : 'center'
				}, {
					field : 'orgName',
					title : '代理商名称',
					align : 'center'
				}, {
					field : 'ticketTypeName',
					title : '票种名称',
					width : 180,
					align : 'center'
				}, {
					field : 'itemName',
					title : '支付方式',
					width : 80,
					align : 'center'
				}, {
					//门票类型(1-FRID、2-身份证、3-二维码)
					field : 'ticketClass',
					title : '门票类型',
					width : 80,
					align : 'center'/*,
					formatter : function(value, row, index) {
						if (value == '1') {
							value = '实体票';
						} else if (value == '2') {
							value = '身份证';
						}else if (value == '3') {
							value = '二维码';
						}
						return value;
					}*/
				}, {
					field : 'saleNum',
					title : '销售数量',
					width : 100,
					align : 'center'
				}, {
					field : 'saleAmt',
					title : '销售金额',
					width : 100,
					align : 'center'
				}, {
					field : 'ejectNum',
					title : '出票数量',
					width : 100,
					align : 'center'
				}, {
					field : 'tradeNum',
					title : '退票数量',
					width : 100,
					align : 'center'
				}, {
					field : 'tradeAmt',
					title : '退票金额',
					width : 100,
					align : 'center'
				}, {
					field : 'checkNum',
					title : '检票数量',
					width : 100,
					align : 'center'
				}, {
					field : 'checkAmt',
					title : '检票金额',
					width : 100,
					align : 'center'
				} ] ],
				toolbar : '#mytable-buttons'
			});

}
function query() {
	var opeTime = $('#opeTime').datebox('getValue');
	var opeTime2 = $('#opeTime2').datebox('getValue');
	var ticketTypeId = $('#ticketTypeId').combobox('getValue');
	var orgId = $('#orgId').combobox('getValue');

	var queryParams = {
		'opeTime' : opeTime,
		'opeTime2' : opeTime2,
		'orgId' : orgId,
		'ticketTypeId' : ticketTypeId
	}
	$myTable.datagrid('options').queryParams = queryParams;
	$myTable.datagrid('load');
	clsCheck('#mytable');
	return true;
}

function initCombobox() {
	// 票种初始化
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

	$('#orgId').combobox({
		url : $ctx + "/comm/listOrgAll.do?orgType='1'", // controller地址
		method : 'post',
		width : 250,
		valueField : 'orgId',
		textField : 'orgName',
		multiple : false,
		editable : false,
		onLoadSuccess : function(e) {
		},
		onLoadError : function(e) {
			getAjaxError(e);
		},
		loadFilter : function(data) {
			var all = [ {
				orgId : '',
				orgName : '--全部--'
			} ];
			return $.merge(all, data);
		}
	});
}
function expExcel() {
	var url = $ctx + '/rptTeam/expExcelWlSale.do';
	$('#form').attr("action", url).submit();
}