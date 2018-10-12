$(function() {
	$myTable = $('#mytable');
	createTable();
	initcombo();
});

function initcombo() {
	$(function() {
		$('#qticketTypeId').combobox({
			url : $ctx + '/saleReg/listTicketTypeByST.do', // controller地址
			method : 'get',
			valueField : 'ticketTypeId',
			textField : 'ticketTypeName',
			multiple : false,
			editable : false,
			loadFilter : function(data) {
				var ticketLists = [ {
					ticketTypeId : '',
					ticketTypeName : '--全部--'
				} ];
				return $.merge(ticketLists, data);
			}
		});
	});
}

function createTable() {
	$myTable.datagrid({
		nowrap : true,// 是否只显示一行，即文本过多是否省略部分。
		striped : true,
		url : $ctx + '/saleReg/listSaleReg.do', // controller地址
		onLoadSuccess : function(e) {
		},
		onLoadError : function(e) {
			getAjaxError(e);
		},
		idField : 'saleRegId',
		fitColumns : false,
		fit : true,
		rownumbers : true,
		singleSelect : false,
		pagination : true, // 包含分页
		pageSize : getPageSize(),
		pageList : getPageList(),
		columns : [ [// 显示的列
		{
			field : 'saleDate',
			title : '销售日期',
			align : 'center',
			width : 120,
		}, {
			field : 'ticketTypeName',
			title : '票种名称',
			align : 'center',
			width : 250,
		}, {
			field : 'saleNum',
			title : '销售数量',
			align : 'center',
			width : 120,
		}, {
			field : 'saleAmt',
			title : '销售金额',
			align : 'center',
			width : 120,
		}, {
			field : 'cx',
			title : '操作',
			align : 'center',
			halign : 'center',
			formatter : getstr,
			width : 120
		} ] ],
		toolbar : '#mytable-buttons'
	});
}

function getstr(value, row, index) {
	var s1;
	var date1 = row.saleDate;
	var date2 = formatterDate(new Date());
	if (date1 == date2) {
		s1 = '<span class="datagrida"><a href="javascript:void(0)" title="修改" onclick="openUpdateSaleRegWin(' + index + ')">修改</a></span> ';
	} else {
		s1 = '';
	}
	return s1;
}

/**
 * 增加销售记录
 */
function openAddSaleRegWin() {
	$("#save-button").show();
	$("#update-button").hide();

	$('#saleRegDialog').dialog({
		title : "新增销售信息",
		cache : true,
		modal : true,
		href : $ctx + "/pages/saleReg/addSaleReg.jsp",
		buttons : "#saleRegDialog-buttons",
		onLoad : function() {
		}
	}).dialog("open");
	;

}

/**
 * 修改销售记录
 */
function openUpdateSaleRegWin(index) {
	$("#save-button").hide();
	$("#update-button").show();

	var rowData = $myTable.datagrid("getRows")[index];

	$('#saleRegDialog').dialog({
		title : "修改销售信息",
		cache : true,
		modal : true,
		href : $ctx + '/saleReg/getSlSaleRegById.do', // controller地址
		queryParams : {
			saleRegId : rowData.saleRegId
		},
		buttons : "#saleRegDialog-buttons",
		onLoadError : function(e) {
			alert(e);
			getAjaxError(e);
		}
	}).dialog("open");
	;

}

/**
 * 保存销售记录
 * 
 * @param url
 * @returns {Boolean}
 */
function saveUser(url) {
	if (!$('#fm').form('validate')) {
		return false;
	}
	$.messager.confirm('提示', '提交后只允许修改当天的销售记录,您确定提交吗？', function(r) {
		if (r) {
			$.messager.progress({
				text : '正在处理,请稍候...'
			});
			$('#fm').form('submit', {
				url : $ctx + url,
				onSubmit : function() {
				},
				success : function(e) {
					if (!getFormError(e)) {
						return false;
					}
					$.messager.progress("close");
					$('#saleRegDialog').dialog('close');
					$myTable.datagrid('reload'); // 重载行
					$myTable.datagrid('clearSelections');
					$.messager.show({
						showType : 'slide',
						title : "提示信息",
						msg : '保存成功',
						timeout : 2000
					});
				}
			}, 'json');
		}
	});
}

function myQuery() {
	var qticketTypeId = $('#qticketTypeId').textbox('getValue');
	var startDt = $('#startDt').datebox('getValue');
	var endDt = $('#endDt').datebox('getValue');
	if (startDt > endDt) {
		$.messager.alert('系统提示', '开始日期不能大于结束日期');
		return false;
	}
	var queryParams = {
		'ticketTypeId' : qticketTypeId,
		'startDt' : startDt,
		'endDt' : endDt
	}
	$myTable.datagrid('options').queryParams = queryParams;
	$myTable.datagrid('load');
	clsCheck('#mytable');
	return true;
}
