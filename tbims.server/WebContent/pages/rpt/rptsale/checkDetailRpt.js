$(function() {
	$myTable = $('#mytable');
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
		url : $ctx + '/rptsale/checkDetail.do', // controller地址
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
 			field : 'opeTime',
 			title : '检票时间',
 			align : 'center',
 			halign: 'center',
 			width:120
 		}, {
			field : 'venueName',
			title : '场馆名称',
			halign: 'center',
			align : 'center',
			width:100
		}, {
			field : 'regionName',
			title : '区域名称',
			editor : "text",
			halign: 'center',
			align : 'center',
			width:100
			
		}, {
			field : 'clientName',
			title : '闸机',
			editor : "text",
			halign: 'center',
			align : 'center',
			width:100
		}, {
 			field : 'ticketClass',
 			title : '门票类型',
 			halign: 'center',
 			width:70
 		},{
 			field : 'ticketTypeName',
 			title : '票种名称',
 			align : 'center',
 			halign: 'center',
 			width:100
 		}, {
  			field : 'ticketId',
  			title : '票号',
  			halign: 'center',
  			align : 'center',
			width:100
  		}, {
  			field : 'ticketUid',
  			title : '票据唯一号',
  			halign: 'center',
  			align : 'center',
			width:200
  		}, {
			field : 'passFlag',
			title : '是否通过',
			editor : "text",
			align : 'center',
			halign: 'center',
			width:70
		}, {
			field : 'nopassReason',
			title : '未通过原因',
			editor : "text",
			halign: 'center',
			align : 'center',
			width:100
		} , {
			field : 'remainTimes',
			title : '剩余次数',
			align : 'center',
			halign: 'center',
			formatter : function(value, row, index) {
				if (value == '-1') {
					value ="";
				}
				return value;
			},
			width:50
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
	var regionId=$('#regionId').combobox('getValue');
	var clientId=$('#clientId').combobox('getValue');
	var venueId=$('#venueId').combobox('getValue');
    var queryParams = {
		'ticketTypeId' : ticketTypeId,
		'regionId' : regionId,
		'beginTime' : beginTime,
		'endTime' : endTime,
		'clientId' : clientId,
		'venueId' : venueId,
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
	var url= $ctx + '/rptsale/expExlCheckDetail.do';
	 $('#queryForm').attr("action", url).submit();
}

