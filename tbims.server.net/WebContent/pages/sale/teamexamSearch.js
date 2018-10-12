$(function() {
	$myTable = $('#mytable');
	var applyBTime=$('#qapplyBTime').datebox('getValue');
    var applyETime=$('#qapplyETime').datebox('getValue');
	var queryParams = {
		'applyBTime':applyBTime,
		'applyETime':applyETime
	}
	createTable(queryParams);
});
function initcombo(){
	$.ajax({
		url : $ctx + "/comm/listItemsByKey.do",
		data : "key=TEAM_EXAM_STAT",
		type : "post",
		success : function(e) {
			$("#qstat").combobox({
				width : 120,
				required : true,
				editable : false,
				panelHeight:'auto',
				valueField : 'id',
				textField : 'text',
				data : e
			});
			//$('#examStat').combobox('select', '0');
			$('#qstat').combobox('setValue','02');
		},
		error : function(e) {
			getAjaxError(e);
		}
	});
}
function createTable(queryParams) {
	$myTable.datagrid({
		nowrap : true,// 是否只显示一行，即文本过多是否省略部分。
		striped : true,
		url : $ctx + '/teamOrder/listApplyHis.do', // controller地址
		onLoadSuccess : function(e) {
		},
		onLoadError : function(e) {
			getAjaxError(e);
		},
		idField : 'applyId',
		fitColumns : true,
		rownumbers : true,
		singleSelect : true,
		queryParams : queryParams,
		striped : true,
		pagination : true,
		fit:true,
		columns : [ [// 显示的列
		{
			field : 'applyId',
			title : '订单号',
			align : 'center'
		}, {
			field : 'applyTime',
			title : '预订日期',
			align : 'center'
		}, {
			field : 'stat',
			title : '订单状态',
			align : 'center',
			formatter : getStatDesc
		}, {
			field : 'orgName',
			title : '签约社名称',
			align : 'center'
		}, {
			field : 'remark',
			title : '订单说明',
			align : 'center'
		}, {
			field : 'applyFrozenAdvanceAmt',
			title : '申请冻结预付款',
			align : 'center'
		}, {
			field : 'examFrozenAdvanceAmt',
			title : '审核冻结预付款',
			align : 'center'
		}, {
			field : 'minusAdvanceAmt',
			title : '换票扣减预付款',
			align : 'center'
		}, {
			field : 'flowAdvanceAmt',
			title : '流单扣罚预付款',
			align : 'center'
		}, {
			field : 'detail',
			title : '详情',
			align : 'center',
		    formatter : getDetail
		}, {
			field : 'printInfo',
			title : '打印',
			align : 'center',
			halign: 'center',
			hidden:true,
			formatter : printInfo
		}] ],
		toolbar : '#mytable-buttons'
	});

}



function getDetail(value, row, index){
	var s1;
	s1 = '<span class="datagrida"><a href="javascript:void(0)" title="详情" onclick="showDetail(' + index + ')">详情</a></span> ';
	return s1;
}

function getStatDesc(value, row, index){
	var desc="";
	var rowData = $myTable.datagrid("getRows")[index];
	var stat = rowData.stat;
	if("01"==stat){
		desc="临时保存";
	}
	else if("02"==stat){
		desc="待审核";
	}
	else if("03"==stat){
		desc="已审核";
	}
	else if("04"==stat){
		desc="已换票";
	}
	else if("05"==stat){
		desc="已拒绝";
	}
	else if("06"==stat){
		desc="已流单";
	}
	else if("07"==stat){
		desc="已取消";
	}
	return desc;
} 
function showDetail(index){
	var rowData = $myTable.datagrid("getRows")[index];
	var applyId = rowData.applyId;
	var url = $ctx + '/teamOrder/showDetail.do?applyId='+applyId+'&flag=d';
	$("#detailDialog").dialog({
		title:" 详情",
		resizable:true,
		inline:true,
		cache : false,
		modal : true,
		href :url, 
		buttons : "#dlg-buttons",
		onLoadError:function(e){
			getAjaxError(e);
		}
	}).dialog("open");
}
function printInfo(value, row, index){
	var s1;
	var selRow = $myTable.datagrid('getRows')[index];
	var stat=selRow.stat;
	if('03'==stat){
	s1 = '<span class="datagrida"><a href="javascript:void(0)" title="打印" onclick="showPrint(' + index + ')">打印</a></span> ';
	}
	else{
		s1 = '--';	
	}
	return s1;
}
function showPrint(index){
	var rowData = $myTable.datagrid("getRows")[index];
	var applyId = rowData.applyId;
	var url = $ctx + '/teamOrder/showDetail.do?applyId='+applyId+'&flag=p';
	$("#detailDialog").dialog({
		title:"打印",
		resizable:true,
		inline:true,
		cache : false,
		modal : true,
		href :url, 
		buttons : "#dlg-buttons",
		onLoadError:function(e){
			getAjaxError(e);
		}
	}).dialog("open");
}
function myQuery() {
	var applyId = $('#qapplyId').textbox('getValue');
    var stat = $('#qstat').combobox('getValue');
    var applyBTime=$('#qapplyBTime').datebox('getValue');
    var applyETime=$('#qapplyETime').datebox('getValue');
    if(applyBTime>applyETime){
		$.messager.alert('系统提示','开始日期不能大于结束日期');
	   return false;
	}
	var queryParams = {
		'applyId' : applyId,
		'stat':stat,
		'applyBTime':applyBTime,
		'applyETime':applyETime
	}
	$myTable.datagrid('options').queryParams = queryParams;
	$myTable.datagrid('load');
	clsCheck('#mytable');
	return true;
}

function orderExam(index){
	var rowData = $myTable.datagrid("getRows")[index];
	var applyId = rowData.applyId;
	var url = $ctx + '/teamExam/beforeDExam.do?applyId='+applyId;
	var iframe='<iframe id="examIframe" src='+url+' frameborder="0" width="100%" height="100%"></iframe>'
		$("#examDialog").dialog({
			title:"订单审核",
			resizable:true,
			inline:true,
			content:iframe,
			cache: false,
			modal: true,
			//buttons : "#dlg-buttons",
			onLoadError:function(e){
				getAjaxError(e);
			},
			onclose:function(){$("#examIframe").remove();}
		}).dialog("open");
}

function printInfo1(){
	myPreview("35", "35", $("#printContent").html());	
}
