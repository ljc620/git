$(function() {
	$myTable = $('#pricetable');
	$myTableH = $('#priceHtable');
	var ticketTypeId = $('#ticketTypeId').val();
	createPriceTable(ticketTypeId);//阶梯票价表
	createPriceHTable(ticketTypeId);//阶梯票价历史表
});
function createPriceTable(ticketTypeId) {
	$myTable.datagrid({
		singleSelect : true,
		fit : true,
		fitColumns : true,
		striped : true,
		pagination : true,
		url : $ctx + '/ticketTypeMng/ticPriceList.do?ticketTypeId=' + ticketTypeId, // controller地址
		onLoadSuccess : function(e) {
		},
		onLoadError : function(e) {
			getAjaxError(e);
		},
		columns : [ [// 显示的列
		{
			field : 'priceId',
			title : '票价编号',
			hidden : true,
			align : 'left'
		}, {
			field : 'startNo',
			title : '开始张数（含）',
			width : 60,
			align : 'center'
		}, {
			field : 'endNo',
			title : '结束张数（含）',
			width : 60,
			align : 'center',
		}, {
			field : 'price',
			title : '票价',
			width : 60,
			align : 'center'
		}, {
			field : 'createUserName',
			title : '创建人',
			align : 'center',
			width : 60
		}, {
			field : 'createTime',
			title : '创建时间',
			align : 'center',
			width : 80
		}, {
			field : 'caozuo',
			title : '操作',
			align : 'center',
			halign : 'center',
			formatter : getstr,
			width : 20
		} ] ],
		toolbar : '#mytable-buttons'
	});
}
function createPriceHTable(ticketTypeId) {
	$myTableH.datagrid({
		singleSelect : true,
		fit : true,
		fitColumns : true,
		striped : true,
		pagination : true,
		url : $ctx + '/ticketTypeMng/ticPriceHList.do?ticketTypeId=' + ticketTypeId, // controller地址
		onLoadSuccess : function(e) {
		},
		onLoadError : function(e) {
			getAjaxError(e);
		},
		columns : [ [// 显示的列
		{
			field : 'priceId',
			title : '票价编号',
			hidden : true,
			align : 'left'
		}, {
			field : 'startNo',
			title : '开始张数（含）',
			width : 60,
			align : 'center'
		}, {
			field : 'endNo',
			title : '结束张数（含）',
			width : 60,
			align : 'center',
		}, {
			field : 'price',
			title : '票价',
			width : 60,
			align : 'center'
		}, {
			field : 'createUserName',
			title : '创建人',
			align : 'center',
			width : 60
		}, {
			field : 'createTime',
			title : '创建时间',
			align : 'center',
			width : 80
		}, {
			field : 'updateUserName',
			title : '最后更新人',
			align : 'center',
			width : 60
		}, {
			field : 'endTime',
			title : '结束时间',
			align : 'center',
			width : 80
		}] ] 
	});
}
function getstr(value, row, index) {
	var selRow = $myTable.datagrid('getRows')[index];
	var s1;
	s1 = '<a href="javascript:void(0)" title="删除" class="opr-btn icon-del" onclick="delPrice('+ index + ')"></a> ';
	return s1;
}

function delPrice(index) {
	var rowData = $myTable.datagrid("getRows")[index];
	var priceId = rowData.priceId;
	$.messager.confirm('提示', '您确定要删除此票价吗?', function(r) {
		if (r) {
			$.ajax({
				url : $ctx + '/ticketTypeMng/delTicketTypePrice.do',
				type : "post",
				data : {
					priceId : priceId
				},
				success : function(e) {
					$.messager.show({
						showType : 'slide',
						title : "提示信息",
						msg : '删除成功',
						timeout : 2000
					});
					//$myTable.datagrid("reload");
					//$myTableH.datagrid("reload");
					top.refreshCurrTab("阶梯票价");
					$.messager.progress("close");
				},
				error : function(e) {
					getAjaxError(e);
				}
			});
		}
	});
}
function addticTypePrice() {
	if (!$('#fm').form('validate')) {
		return false;
	}
	var ticketTypeId = $('#ticketTypeId').val();
	var startNo = $('#startNo').numberbox('getValue');
	var endNo = $('#endNo').numberbox('getValue');
	var price = $('#price').numberbox('getValue');
	if ((startNo-endNo)>=0) {
		$.messager.alert('系统提示', '开始张数不能大于等于结束张数');
		return false;
	}
	$.messager.confirm('提示', '您确定要添加该票价吗?', function(r) {
		if (r) {
			$.ajax({
				url : $ctx + '/ticketTypeMng/addTicketTypePrice.do',
				type : "post",
				data : {
					ticketTypeId : ticketTypeId,
					startNo : startNo,
					endNo : endNo,
					price : price 
				},
				success : function(e) {
					$.messager.show({
						showType : 'slide',
						title : "提示信息",
						msg : '添加成功',
						timeout : 2000
					});
					//$myTable.datagrid("reload");
					//$myTableH.datagrid("reload");
					top.refreshCurrTab("阶梯票价");
					$.messager.progress("close");
				},
				error : function(e) {
					getAjaxError(e);
				}
			});
		}
	});
}
/**
 * 校验连续
 */
function checkContinue(){
	var ticketTypeId = $('#ticketTypeId').val();
	var rowData = $myTable.datagrid("getRows");
	if(rowData==null||rowData.length==0){
		alert("无可校验的数据");
		return false;
	}
	$.ajax({
		url : $ctx + '/ticketTypeMng/checkContinue.do',
		type : "post",
		data : {
			ticketTypeId : ticketTypeId 
		},
		success : function(data) {
			if(data){
				alert("校验结果：张数连续");
			}else{
				alert("校验结果：张数不连续");
			}
		},
		error : function(e) {
			getAjaxError(e);
		}
	});
}