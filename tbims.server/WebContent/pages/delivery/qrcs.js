$(function() {
	$myTable = $('#mytable');
	createClientTable();
	initCombobox();
});
function createClientTable() {
	$myTable.datagrid({
		nowrap : true,// 是否只显示一行，即文本过多是否省略部分。
		striped : true,     
		//url : $ctx + '/qrcode/listQrCode.do', // controller地址
		onLoadSuccess : function(e) {
		},
		onLoadError : function(e) {
			getAjaxError(e);
		},
		idField : 'ticketTypeId',
		checkbox:true,
		fitColumns : false,
		rownumbers : true,
		striped : true,
		pagination : true,
		fit:true,
		frozenColumns : [ [ {
			field : 'ck',
			checkbox : true
		} ] ],
		columns : [ [// 显示的列
		{
			field : 'ticketTypeId',
			title : '票种编号',
			width : 100,
			align : 'center'
		}, {
			field : 'ticketTypeName',
			title : '票种名称',
			width : 130,
			align : 'center'
		}, {
			field : 'beginNo',
			title : '起始票号',
			width : 200,
			align : 'center',
		}, {
			field : 'endNo',
			title : '截止票号',
			width : 200,
			align : 'center',
		}, {
			field : 'opeUserId',
			title : '操作人',
			width : 130,
			align : 'center'
		}, {
			field : 'opeTime',
			title : '操作时间',
			width : 130,
			align : 'center' ,
			
		},{
			field : 'caozuo',
			title : '操作',
			width : 130,
			align : 'center',
			formatter : getstr
		}] ],
		toolbar : '#mytable-buttons'
	});

}

function initCombobox() {
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
		},
	}); 
}

function getstr(value, row, index) {
	//var selRow = $myTable.datagrid('getRows')[index];
	var s1//, s2;
	s1 = '<a href="javascript:void(0)" title="导出TXT" class="opr-btn icon-filesave" onclick="updateClient(' + index + ')"></a> ';
	//s2 = '<a href="javascript:void(0)" title="删除" class="opr-btn icon-del" onclick="delClient(' + index + ')"></a> ';
	return s1 //+ s2;
}
/**
 * 查询二维码生成记录
 */
function queryCode() {
	var beginDt = $('#makeQrTimeBegin').datebox('getValue');
    var endDt = $('#makeQrTimeEnd').datebox('getValue');
    var ticketTypeId=$('#ticketTypeId').combobox('getValue');
    if(beginDt>endDt){
		$.messager.alert('系统提示','开始日期不能大于结束日期');
	   return false;  
	}
	var queryParams = {
		'beginDt' : beginDt,
		'endDt':endDt,
		'ticketTypeId':ticketTypeId
	}
	$('#mytable').datagrid('options').queryParams = queryParams;
	$('#mytable').datagrid('load');
	return true;
}

/**
 * 添加二维码
 */

function makeCode() {
	$("#save-button").show();
    $("#update-button").hide();
    var url = $ctx + '/pages/delivery/qrAdd.jsp';
	$("#qrDialog").dialog({
		title:"添加二维码",
		resizable:true,
		inline:true,
		cache : false,
		modal : true,
		buttons : "#qr-buttons",
		href:url
	}).dialog("open");
    $('#fm').form("clear");
}

function saveQr(url) {
	if (!$('#fm').form('validate')) {
		return false;
	}
	var beginNo = $('#beginNo').numberbox('getValue');
    var endNo = $('#endNo').numberbox('getValue');
    var TicketTypeIdBegin=beginNo.substring(0,2);
    var TicketTypeIdEnd=endNo.substring(0,2);
    var ticketTypeIds = $("input[name='ticketTypeIds']").val();
    if(beginNo.length!=10) {
    	$.messager.alert('系统提示','起始票号长度必须是10位');
 	   return false;
    }
    if(endNo.length!=10) {
    	$.messager.alert('系统提示','截止票号长度必须是10位');
 	   return false;
    }
    if(beginNo>=endNo){
		$.messager.alert('系统提示','起始票号不能大于或等于截止票号！');
	   return false;
	}
    if(TicketTypeIdBegin!=ticketTypeIds){
    	$.messager.alert('系统提示','票号与票种不一致,请核对!');
 	   return false;
    }
    if(TicketTypeIdEnd!=ticketTypeIds){
    	$.messager.alert('系统提示','票号与票种不一致,请核对!');
 	   return false;
    }
    
	$.messager.confirm('提示', '确认后无法修改，要确认吗？', function(r) {
		if (r) {
			$.messager.progress({
				text : '正在处理,请稍候...'
			});
				
			$('#fm').form('submit', {
				//url : $ctx + url,
				success : function(e) {
					if(e==1){
						$.messager.progress("close");
						if (!getFormError(e)) {
							return false;
						}
						//$('#clientDialog').dialog('close');
						$myTable.datagrid('reload'); // 重载行
						$myTable.datagrid('clearSelections');
						$.messager.show({
							showType : 'slide',
							title : "提示信息",
							msg : '保存成功',
							timeout : 2000
						});
					}else if(e==0){
						$.messager.alert('系统提示','票号与票种不一致11,请核对!');
					 	   return false;
					}else{
						$.messager.alert('系统提示','票号有重复,请核对!');
					 	   return false;
					}
				}
			}, 'json');
		}
	});
}


