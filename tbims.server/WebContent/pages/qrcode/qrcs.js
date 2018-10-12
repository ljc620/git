$(function() {
	$myTable = $('#mytable');
	initcombo();
	var par={examStat:'1'};
	createExamTable(par);
});
function createExamTable(params) {
	$myTable.datagrid({
		nowrap : true,// 是否只显示一行，即文本过多是否省略部分。
		striped : true,
		url : $ctx + '/deliveryExam/listDeliveryApplyForOut.do', // controller地址
		onLoadSuccess : function(e) {
		},
		onLoadError : function(e) {
			getAjaxError(e);
		},
		idField : 'applyId',
		fitColumns : true,
		rownumbers : true,
		singleSelect : true,
		striped : true,
		pagination : true,
		queryParams:params,
		fit:true,
		columns : [ [// 显示的列
		{
			field : 'applyId',
			title : '申请编号',
			editor : "text",
			align : 'center'
		}, {
			field : 'outletName',
			title : '网点名称',
			editor : "text",
			align : 'left'
		}, {
			field : 'outletType',
			title : '网点类型',
			editor : "text",
			align : 'center'
		}, {
			field : 'orgName',
			title : '机构名称',
			editor : "text",
			align : 'center'
		}, {
			field : 'applyUserName',
			title : '申请人',
			editor : "text",
			align : 'center'
		}, {
			field : 'applyTime',
			title : '申请时间',
			editor : "text",
			align : 'center'
		}, {
			field : 'applyDeliveryTime',
			title : '申请配送日期',
			editor : "text",
			align : 'center'
		}, {
			field : 'deliveryUserName',
			title : '出库人姓名',
			align : 'center',
			halign: 'center'
		} , {
			field : 'deliveryTime',
			title : '出库时间',
			align : 'center',
			halign: 'center'
		} , {
			field : 'examStat',
			title : '状态',
			align : 'center',
			halign: 'center',
			formatter : getExamStat
		} , {
			field : 'caozuo',
			title : '查看详情',
			align : 'center',
			formatter : getstr
		}, {
			field : 'caozuo1',
			title : '配送确认',
			align : 'center',
			formatter : getstr1
		}] ],
		toolbar : '#mytable-buttons'
	});

}



function initcombo(){
	$.ajax({
		url : $ctx + "/comm/listItemsByKey.do",
		data : "key=DELIVERY_STAT",
		type : "post",
		success : function(e) {
			$("#examStat").combobox({
				width : 120,
				required : true,
				editable : false,
				panelHeight:'auto',
				valueField : 'id',
				textField : 'text',
				data : e
			});
			//$('#examStat').combobox('select', '0');
			$('#examStat').combobox('setValue','1');
		},
		error : function(e) {
			getAjaxError(e);
		}
	});
}
//导出Excel
function expExcel(){
	var url=$ctx + '/rptsale/expExcelCenterStore.do';
	 $('#queryForm').attr("action", url).submit();
}
