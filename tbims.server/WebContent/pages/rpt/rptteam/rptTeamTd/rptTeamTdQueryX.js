$(function() {
	$myTable = $('#mytable');
	createTable();
	initCombobox();
});
function createTable() {
	$myTable.datagrid({
		nowrap : true,// 是否只显示一行，即文本过多是否省略部分。
		striped : true,
		url : $ctx + '/rptTeam/listRptTeamTdX.do', // controller地址
		onLoadSuccess : function(e) {
			$(this).datagrid('appendRow',{
				ticketTypeName:'<span style="font-weight:900;color:#000;">合计</span>',
				changeNum:compute('#mytable','changeNum'),
				minusAdvanceAmt:compute('#mytable','minusAdvanceAmt')
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
			changeTimeStart : formatterDate(new Date()),
			changeTimeEnd : formatterDate(new Date())
		},
		columns : [ [// 显示的列
		{
			field : 'changeTime',
			title : '销售日期',
			width : 150,
			align : 'center'
		}, {
			field : 'orgName',
			title : '签约社名称',
			align : 'center'
		}, {
			field : 'ticketTypeName',
			title : '票种名称',
			width : 150,
			align : 'center'
		}, {
			field : 'changeNum',
			title : '换票数量',
			width : 100,
			align : 'center'
		}
//		, {
//			field : 'minusLimit',
//			title : '扣减额度',
//			width : 100,
//			align : 'center'
//		}
		, {
			field : 'minusAdvanceAmt',
			title : '扣减预付款',
			width : 100,
			align : 'center'
		} ] ],
		toolbar : '#mytable-buttons'
	});

}
function query() {
	var changeTimeStart = $('#changeTimeStart').datebox('getValue');
	var changeTimeEnd = $('#changeTimeEnd').combobox('getValue');
	var ticketTypeId = $('#ticketTypeId').combobox('getValue');
	var orgId = $('#orgId').combobox('getValue');
	
	var queryParams = {
		'changeTimeStart' : changeTimeStart,
		'changeTimeEnd' : changeTimeEnd,
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
		url : $ctx + '/orgMng/ticketTypeList.do?flag=t',
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
		url : $ctx + "/comm/listOrgAll.do?orgType='0'", // controller地址
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
	var url = $ctx + '/rptTeam/expExcelRptTeamTdX.do';
	$('#form').attr("action", url).submit();
}