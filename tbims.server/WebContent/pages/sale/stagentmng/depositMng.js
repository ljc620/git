$(function() {
	$myTable = $('#mytable');
	$orgId = $('#orgId').val();
	createDepositTable();
});
function createDepositTable() {
	$myTable.datagrid({
		nowrap : true,// 是否只显示一行，即文本过多是否省略部分。
		striped : true,
		url : $ctx + '/orgMng/listDeposit.do?orgId='+$orgId, // controller地址
		onLoadSuccess : function(e) {
		},
		onLoadError : function(e) {
			getAjaxError(e);
		},
		idField : 'depositId',
		fitColumns : false,
		fit : true,
		checkbox : true,
		rownumbers : true,
		singleSelect : true,
		striped : true,
		pagination : true,
/*		frozenColumns : [ [ {
			field : 'ck',
			checkbox : false
		} ] ],*/
		
		columns : [ [// 显示的列
		{
			field : 'depositId',
			title : '保证金',
			hidden:true,
			width : 120,
			editor : "text",
			align : 'center'
		},{
			field : 'depositAmt',
			title : '增加金额',
			width : 120,
			align : 'right',
			halign: 'center'
		},{
			field : 'opeUserName',
			title : '操作人',
			width : 120,
			align : 'center'
		}, {
			field : 'opeTime',
			title : '增加时间',
			align : 'center',
			halign: 'center',
			width : 150
		}, {
			field : 'remark',
			title : '备注',
			align : 'center',
			halign: 'center',
			width : 150
		} ] ]
	});

}

function addDeposit(obj){
	$("#save-button").show();
	var url = $ctx + '/orgMng/beforeAddDeposit.do?orgId='+obj;
		$("#addDialog").dialog({
			title:"新增保证金",
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
 * 保存保证金
 * @returns {Boolean}
 */
function saveDeposit() {
	if (!$('#fm').form('validate')) {
		return false;
	}
	$.messager.confirm('提示', '确定保存信息吗?', function(r) {
		if (r) {
			$.messager.progress({
				text : '正在处理,请稍候...'
			});

			$.ajax({
				url : $ctx + "/orgMng/addDeposit.do",
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
					refreshCurrTab("实体代理商管理");
					closeCurrTab("实体代理商保证金");
					$('#addDialog').dialog('close');
				},
				error : function(e) {
					getAjaxError(e);
				}
			});
		}
	});

}



