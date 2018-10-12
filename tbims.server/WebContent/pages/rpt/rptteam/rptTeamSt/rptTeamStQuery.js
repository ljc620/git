$(function() {
	$myTable = $('#mytable');
	createTable();
	initCombobox();
});
function createTable() {
	$myTable.datagrid({
		nowrap : true,// 是否只显示一行，即文本过多是否省略部分。
		striped : true,
		url : $ctx + '/rptTeam/listRptTeamSt.do', // controller地址
		onLoadSuccess : function(e) {
			$(this).datagrid('appendRow',{
				ticketTypeName:'<span style="font-weight:900;color:#000;">合计</span>',
				ticketNum:compute('#mytable','ticketNum'),
				totalPrice:compute('#mytable','totalPrice')
			});
		},
		onLoadError : function(e) {
			getAjaxError(e);
		},
		fitColumns : false,
		rownumbers : true,
		striped : true,
		pagination : false,
		fit:true,
		queryParams:{
			opeTime:formatterDate(new Date())
		},
		columns : [ [// 显示的列
         {
 			field : 'opeTime',
 			title : '销售日期',
 			width : 150,
 			align : 'center' 
 		},
     	{
			field : 'orgName',
			title : '代理商名称',
			align : 'center'
		}, {
			field : 'ticketTypeName',
			title : '票种名称',
			width : 150,
			align : 'center'
		}, {
			field : 'ticketNum',
			title : '销售数量',
			width : 100,
			align : 'center'
		}, {
			field : 'totalPrice',
			title : '销售金额',
			width : 100,
			align : 'center'
		}] ],
		toolbar : '#mytable-buttons'
	});

}
function query() {
	var saleStartTime = $('#saleStartTime').datebox('getValue');
	var saleEndTime = $('#saleEndTime').datebox('getValue');
	var ticketTypeId = $('#ticketTypeId').combobox('getValue');
	var orgId = $('#orgId').combobox('getValue');
	
	var queryParams = {
		'saleStartTime' : saleStartTime,
		'saleEndTime'   : saleEndTime,
		'orgId'   : orgId,
		'ticketTypeId'  : ticketTypeId 
	}
	$myTable.datagrid('options').queryParams = queryParams;
	$myTable.datagrid('load');
	clsCheck('#mytable');
	return true;
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
	
	$('#orgId').combobox({
		url : $ctx + "/comm/listOrgAll.do?orgType='2'", // controller地址
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
function expExcel(){
	var url=$ctx+'/rptTeam/expExcelSt.do';
	 $('#form').attr("action", url).submit();
}