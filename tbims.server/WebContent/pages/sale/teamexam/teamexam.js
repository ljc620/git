$(function() {
	$myTable = $('#tickettable');
	var applyId = $('#applyId').val();
	var par={applyId:applyId};
	createTicketTable(par);
});

function createTicketTable(params) {
	$myTable.datagrid({
		nowrap : true,// 是否只显示一行，即文本过多是否省略部分。
		striped : true,
		url : $ctx + "/teamExam/applyDetail.do",
		onLoadSuccess : function(e) {
		},
		onLoadError : function(e) {
			getAjaxError(e);
		},
		idField : 'detailId',
		fitColumns : false,
		rownumbers : true,
		queryParams:params,
		onClickCell: onClickCell,
		singleSelect : true,
		striped : true,
		fit:true,
		columns : [ [// 显示的列       
		{
			field : 'detailId',
			title : '门票种类',
			hidden : true,
			align : 'center',
		},{
			field : 'ticketTypeId',
			title : '门票种类',
			hidden : true,
			align : 'center'
		},{
			field : 'ticketTypeName',
			title : '门票种类',
			width : 120,
			align : 'center'
		}, {
			field : 'applyNum',
			title : '申请数量',
			width : 120,
			align : 'left'
		}, {
			field : 'examNum',
			title : '审核数量',
			width : 120,
			editor:{type:'numberbox',
				options:{editable:true,
					min:1,
					precision:0 
				}
			}/*,
			formatter : function(value, row, index){
				return row.applyNum;
			}*/,
			align : 'center'
		}] ]
	});

}

function onClickCell(index, field){
	 $myTable.datagrid("beginEdit",index);
} 
/**
 * 审核
 * @returns {Boolean}
 */
function myOrderExam(obj){
	if (!$('#fm').form('validate')) {
		return false;
	}
	var examdesc="";
	if("03"==obj){
		examdesc="同意";
	}
	else{
		examdesc="拒绝";
	}
	var rows = $myTable.datagrid('getRows');
	for(var i = 0 ; i < rows.length;i++){
		$myTable.datagrid('endEdit', i);
	}
	var myJSONText=JSON.stringify(rows);
	$('#applyDetailStr').val(myJSONText);
	$('#stat').val(obj);
	$.messager.confirm('提示', '确定要【'+examdesc+'】此订单申请吗？', function(r) {
		if (r) {
			$.messager.progress({
				text : '正在处理,请稍候...'
			});

			$('#fm').form('submit', {
				url : $ctx + "/teamExam/examOrder.do",
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
					parent.$('#mytable').queryParams={examStat:'0'};
					parent.$('#mytable').datagrid("reload");
					parent.$('#examDialog').dialog('close');
				}
			}, 'json');
		}
	});
}
