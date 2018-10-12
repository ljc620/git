$(function() {
	$myTable = $('#detailtable');
	createTTypeTable();
});
function createTTypeTable() {
	$myTable.datagrid({
		singleSelect: true,
		fitColumns : false,
		striped:true,
		idField:'ticketTypeId',
		columns : [ [// 显示的列           
		{
			field : 'ticketTypeId',
			title : '有效开始时间',
			hidden: true,
			align : 'center',
			width:200
		}, {
			field : 'ticketTypeName',
			title : '票种',
			editor : "text",
			align : 'center',
			width:200
		}, {
			field : 'applyNum',
			title : '人数',
			editor : "text",
			align : 'center',
			width:200
		}, {
			field : 'caozuo',
			title : '操作',
			align : 'center',
			halign: 'center',
			width:100,
			formatter : getstr
		}] ]/*,
		toolbar : '#mytable-buttons'*/
	});

}

function getstr(value, row, index) {
	var s1;
	s1= '<a href="javascript:void(0)" title="删除" class="opr-btn icon-del" onclick="delMyRow(this)"></a> ';
	return s1;
}
function getRowIndex(target) {
	var tr = $(target).closest('tr.datagrid-row');
	return parseInt(tr.attr('datagrid-row-index'));
}
function delMyRow(obj){
	var index =getRowIndex(obj);
	$myTable.datagrid('deleteRow', index);

}
function addMyRow(){
	var ticketTypeId=$('#ticketTypeId').combobox('getValue');
	var applyNum=$('#applyNum').numberbox('getValue');
	if(ticketTypeId==null||ticketTypeId==""){
		$.messager.show({
			showType : 'slide',
			title : "提示信息",
			msg : '票种不能为空！',
			timeout : 2000
		});
		return false;
	}
	if(applyNum==null||applyNum<1){
		$.messager.show({
			showType : 'slide',
			title : "提示信息",
			msg : '申请数量为空或过少 ！',
			timeout : 2000
		});
		return false;
	}
	var row = $myTable.datagrid("getRows");
	for(var i=0;i<row.length;i++){
		if(ticketTypeId==row[i].ticketTypeId){
			$.messager.show({
				showType : 'slide',
				title : "提示信息",
				msg : '此票种不能重复添加！',
				timeout : 2000
			});
			return false;
		}
	}
	if(row.length>=1){
		$.messager.show({
			showType : 'slide',
			title : "提示信息",
			msg : '只能添加一个票种！',
			timeout : 2000
		});	
		return false;
	}
	$myTable.datagrid('appendRow',{
		ticketTypeId:ticketTypeId,
		ticketTypeName:$('#ticketTypeId').combobox('getText'),
		applyNum:applyNum
	});
}
function saveOrder(obj){
	$('#stat').val(obj);
	if (!$('#fm').form('validate')) {
		return false;
	}
	var rows = $myTable.datagrid('getRows');
	if(rows==null||rows.length==0){
		$.messager.show({
			showType : 'slide',
			title : "提示信息",
			msg : '请添加票种及数量！',
			timeout : 2000
		});
		return false;
	}
	var myJSONText=JSON.stringify(rows);
	$('#detailStr').val(myJSONText);
	$.messager.confirm('提示', '确定保存信息吗？', function(r) {
		if (r) {
			$.messager.progress({
				text : '正在处理,请稍候...'
			});

			$('#fm').form('submit', {
				url : $ctx + "/teamOrder/addOrder.do",
				success : function(e) {
					$.messager.progress("close");
					if (!getFormError(e)) {
						return false;
					}
					$.messager.show({
						showType : 'slide',
						title : "提示信息",
						msg : '保存成功',
						timeout : 2000
					});
					parent.$('#mytable').datagrid("reload");
					parent.$('#addDialog').dialog('close');
				}
			}, 'json');
		}
	});
}