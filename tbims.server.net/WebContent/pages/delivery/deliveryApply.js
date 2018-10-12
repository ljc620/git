$(function() {
	$myTable = $('#applytable');
	createApplyTable();
});
function createApplyTable() {
	$myTable.datagrid({
		singleSelect: true,
		fitColumns : false,
		striped:true,
		idField:'applyId',
		height:200,
		columns : [ [// 显示的列           
		{
			field : 'ticketTypeId',
			title : '票种编号',
			align : 'center',
			width : 100
		}, {
			field : 'ticketTypeName',
			title : '票种名称',
			align : 'center',
			width : 150
		}, {
			field : 'applyNum',
			title : '数量',
			align : 'center',
			width : 120
		}, {
			field : 'caozuo',
			title : '操作',
			align : 'center',
			halign: 'center',
			width :120,
			formatter : getstr
		}] ] 
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
function addApply(){
	if (!$('#fm').form('validate')) {
		return false;
	}
	var applyNum=$('#applyNum').numberbox('getValue');
	var ticketTypeId=$('#ticketTypeId').combobox('getValue');
	var row=$myTable.datagrid('getRows');
	for(var i=0;i<row.length;i++){
		if(ticketTypeId==row[i].ticketTypeId){
			$.messager.show({
				showType : 'slide',
				title : "提示信息",
				msg : '该票种编号已添加！',
				timeout : 2000
			});
			return false;
		}
	}
	$.ajax({
		url : $ctx + "/delivery/checkApplyNum.do",
		type : "post",
		data : {
			"applyNum" : applyNum
		},
		success : function(e) {
			$myTable.datagrid('appendRow',{
				ticketTypeId:$('#ticketTypeId').combobox('getValue'),
				ticketTypeName:$('#ticketTypeId').combobox('getText'),
				applyNum:$('#applyNum').numberbox('getValue')
			});
		},
		error : function(e) {
			getAjaxError(e);
		}
	});
}
function saveApply(){
	var rows = $myTable.datagrid('getRows');
	if(rows==null||rows.length==0){
		$.messager.show({
			showType : 'slide',
			title : "提示信息",
			msg : '请添加申请票种及数量！',
			timeout : 2000
		});
		return false;
	}
	var myJSONText=JSON.stringify(rows);
	$('#detailStr').val(myJSONText);
	$.messager.confirm('提示', '确定提交申请吗？', function(r) {
		if (r) {
			parent.$.messager.progress({
				text : '正在处理,请稍候...'
			});

			$('#fm').form('submit', {
				url : $ctx + "/delivery/saveApply.do",
				success : function(e) {
					parent.$.messager.progress("close");
					if (!getFormError(e)) {
						return false;
					}
					parent.$.messager.show({
						showType : 'slide',
						title : "提示信息",
						msg : '申请成功',
						timeout : 2000
					});
					parent.$myTable.datagrid("reload");
					parent.$('#applyDialog').dialog('close');
				}
			}, 'json');
		}
	});
}