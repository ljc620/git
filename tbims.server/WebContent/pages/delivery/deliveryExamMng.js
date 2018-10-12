$(function() {
	$myTable = $('#mytable');
	initcombo();
	var par={examStat:'0'};
	createExamTable(par);
});
function createExamTable(params) {
	$myTable.datagrid({
		nowrap : true,// 是否只显示一行，即文本过多是否省略部分。
		striped : true,
		url : $ctx + '/deliveryExam/listDeliveryApply.do', // controller地址
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
			align : 'left'
		}, {
			field : 'outletType',
			title : '网点类型',
			align : 'center'
		}, {
			field : 'orgName',
			title : '机构名称',
			align : 'center'
		}, {
			field : 'applyUserName',
			title : '申请人',
			align : 'center'
		}, {
			field : 'applyTime',
			title : '申请时间',
			align : 'center'
		}, {
			field : 'applyDeliveryTime',
			title : '申请配送日期',
			editor : "text",
			align : 'center'
		}, {
			field : 'examUserName',
			title : '审核人',
			align : 'center',
			halign: 'center'
		} , {
			field : 'examTime',
			title : '审核时间',
			align : 'center',
			halign: 'center'
		} , {
			field : 'examStat',
			title : '审核状态',
			align : 'right',
			halign: 'center',
			formatter : getExamStat
		} , {
			field : 'caozuo',
			title : '配送审核',
			align : 'center',
			formatter : getstr
		}] ],
		toolbar : '#mytable-buttons'
	});

}
function myQuery() {
	var examStat = $('#examStat').combobox('getValue');
	var applyId = $('#applyId').textbox('getValue');

	var queryParams = {
		'examStat':examStat,
		'applyId' : applyId
		
	}
	$myTable.datagrid('options').queryParams = queryParams;
	$myTable.datagrid('load');
	clsCheck('#mytable');
	return true;
}

function initcombo(){
	$.ajax({
		url : $ctx + "/comm/listItemsByKey.do",
		data : "key=DELIVERY_EXAM_STAT",
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
			$('#examStat').combobox('setValue','0');
		},
		error : function(e) {
			getAjaxError(e);
		}
	});
}
function getstr(value, row, index){
	var selRow = $myTable.datagrid('getRows')[index];
	var examStat=selRow.examStat;
	var s1;
	if('0'==examStat){
	s1 = '<span class="datagrida"><a href="javascript:void(0)" title="配送审核"  onclick="deliveryExam(' + index + ')">配送审核</a><span> ';
	}
	else{
		s1 = '<span class="datagrida"><a href="javascript:void(0)" title="审核详情"  onclick="showDetail(' + index + ')">审核详情</a> <span>';	
	}
	return s1;
}
function getExamStat(value, row, index){
	var s1='';
	var selRow = $myTable.datagrid('getRows')[index];
	var examStat=selRow.examStat;
	if('0'==examStat){
		s1="待审核";
	}
	else if('1'==examStat){
		s1="已审核";
	}
	else if('2'==examStat){
		s1="已配送";
	}
	else if('3'==examStat){
		s1="已接收";
	}
	else if('4'==examStat){
		s1="已拒收";
	}
	else if('5'==examStat){
		s1="已拒绝";
	}
	return s1;
}
/**
 * 
 * @param index
 */
function deliveryExam(index){
	var rowData = $myTable.datagrid("getRows")[index];
	var applyId = rowData.applyId;
	var url = $ctx + '/deliveryExam/beforeDExam.do?applyId='+applyId+'&flag=0';
	var iframe='<iframe id="examIframe" src='+url+' frameborder="0" width="100%" height="100%"></iframe>'
		$("#examDialog").dialog({
			title:"配送审核",
			resizable:true,
			inline:true,
			content:iframe,
			cache: false,
			modal: true,
			buttons : "#dlg-buttons",
			onLoadError:function(e){
				getAjaxError(e);
			},
			onclose:function(){$("#examIframe").remove();}
		}).dialog("open");
}
function showDetail(index){
	var rowData = $myTable.datagrid("getRows")[index];
	var applyId = rowData.applyId;
	var url = $ctx + '/deliveryExam/examDetail.do?applyId='+applyId;
	var iframe='<iframe id="detailIframe" src='+url+' frameborder="0" width="100%" height="100%"></iframe>'
		$("#detailDialog").dialog({
			title:"审核详情",
			resizable:true,
			inline:true,
			content:iframe,
			cache: false,
			modal: true,
			onLoadError:function(e){
				getAjaxError(e);
			},
			onclose:function(){$("#detailIframe").remove();}
		}).dialog("open");
	
}