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
		s1="已退回";
	}
	return s1;
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
function getstr(value, row, index){
	var selRow = $myTable.datagrid('getRows')[index];
	var examStat = selRow.examStat;
	var s1;
	if(examStat>'1'){
	s1 = '<span class="datagrida"><a href="javascript:void(0)" title="查看详情"  onclick="showDetail(' + index + ')">查看详情</a> <span>';
	}
	else{
		s1 ="--";	
	}
	return s1;
}
function getstr1(value, row, index){
	var selRow = $myTable.datagrid('getRows')[index];
	var examStat = selRow.examStat;
	var s1;
	if(examStat=='1'){
	s1 = '<span class="datagrida"><a href="javascript:void(0)" title="配送确认"  onclick="deliveryConfirm(' + index + ')">配送确认</a>  <span>';
	}
	else{
		s1 ="--";	
	}
	return s1;
}
/**
 * 
 * @param index
 */
function showDetail(index){
	var rowData = $myTable.datagrid("getRows")[index];
	var applyId = rowData.applyId;
	var url = $ctx + '/deliveryExam/beforeDelivery.do?applyId='+applyId;
	var iframe='<iframe id="detailIframe" src='+url+' frameborder="0" width="100%" height="100%"></iframe>'
		$("#detailDialog").dialog({
			title:"查看详情",
			resizable:true,
			inline:true,
			content:iframe,
			cache: false,
			modal: true,
			buttons : "#dlg-buttons",
			onLoadError:function(e){
				getAjaxError(e);
			},
			onclose:function(){$("#detailIframe").remove();}
		}).dialog("open");
}
/**
 * 
 * @param index
 */
function deliveryConfirm(index){
	var rowData = $myTable.datagrid("getRows")[index];
	var applyId = rowData.applyId;
	var url = $ctx + '/deliveryExam/beforeDExam.do?applyId='+applyId+'&flag=1';
	top.addTab("配送确认",url);
	/*var iframe='<iframe id="deliverConIframe" src='+url+' frameborder="0" width="100%" height="100%"></iframe>'
		$("#deliverConDialog").dialog({
			title:"配送确认",
			resizable:true,
			inline:true,
			content:iframe,
			cache: false,
			modal: true,
			buttons : "#dlg-buttons",
			onLoadError:function(e){
				getAjaxError(e);
			},
			onclose:function(){$("#deliverConIframe").remove();}
		}).dialog("open");*/
}
