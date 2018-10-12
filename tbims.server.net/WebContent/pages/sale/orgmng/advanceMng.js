$(function() {
	$myTable = $('#mytable');
	$orgId = $('#orgId').val();
	createAdvanceTable();
});
function createAdvanceTable() {
	$myTable.datagrid({
		nowrap : true,// 是否只显示一行，即文本过多是否省略部分。
		striped : true,
		url : $ctx + '/orgMng/listAdvance.do?orgId='+$orgId, // controller地址
		onLoadSuccess : function(e) {
		},
		onLoadError : function(e) {
			getAjaxError(e);
		},
		idField : 'advanceId',
		fitColumns : false,
		fit : true,
		checkbox : true,
		rownumbers : true,
		rownumbers : true,
		singleSelect : true,
		striped : true,
		pagination : true,
		frozenColumns : [ [ {
			field : 'ck',
			checkbox : false
		} ] ],
		
		columns : [ [// 显示的列
	    {
			field : 'advanceId',
			title : '预付款编号',
			hidden:true,
			align : 'right',
			halign: 'center',
			width : 150
		} , {
			field : 'advanceAmt',
			title : '预付款金额',
			align : 'right',
			halign: 'center',
			width : 150
		}, {
			field : 'remark',
			title : '备注',
			align : 'right',
			halign: 'center',
			width : 150
		} , {
			field : 'opeTime',
			title : '增加时间',
			align : 'right',
			halign: 'center',
			width : 150
		} ] ],
		toolbar : '#mytable-buttons'
	});

}
function addAdvance(obj) {
	$("#save-button").show();
var url = $ctx + '/orgMng/beforeAddAdvance.do?orgId='+obj+'&flag=o';
	$("#addDialog").dialog({
		title:"新增预付款",
		resizable:true,
		inline:true,
		cache : false,
		modal : true,
		buttons : "#dlg-buttons",
		href:url
	}).dialog("open");
    $('#fm').form("clear");
}

/**
 * 保存额度
 * @returns {Boolean}
 */
function saveAdvance() {
	if (!$('#fm').form('validate')) {
		return false;
	}
	$.messager.confirm('提示', '确定保存信息吗?', function(r) {
		if (r) {
			$.messager.progress({
				text : '正在处理,请稍候...'
			});

			$.ajax({
				url : $ctx + "/orgMng/addAdvance.do?flag=o",
				type : "post",
				data : $("#fm").serialize(),
				success : function(e) {
					$.messager.show({
						showType : 'slide',
						title : "提示信息",
						msg : '保存成功',
						timeout : 2000
					});
					$myTable.datagrid("reload");
					$.messager.progress("close");
					refreshCurrTab("签约社管理");
					$('#addDialog').dialog('close');
				},
				error : function(e) {
					getAjaxError(e);
				}
			});
		}
	});

}
