$(function() {
	$myTable = $('#mytable');
	initcombo();

	var applyBTime = $('#qapplyBTime').datebox('getValue');
	var applyETime = $('#qapplyETime').datebox('getValue');
	var inDt = $('#inDt').datebox('getValue');
	var queryParams = {
		'applyBTime' : applyBTime,
		'applyETime' : applyETime,
		'inDt' : inDt,
		stat : "'02','03','04','05','06','07'"
	}
	getTotalNum(queryParams);
	createTable(queryParams);

});

function initcombo() {
	$('#qstat').combobox({
		valueField : 'id',
		textField : 'text',
		panelHeight : 'auto',
		editable : false,
		multiple : true,
		width : 120,
		onSelect : function(option) {
			if (option.id != "") {
				$(this).combobox("unselect", "");
			} else {
				$(this).combobox("reset");
				$(this).combobox("select", option.id);
			}
		},
		data : [ {
			'id' : '',
			'text' : '--全部--'
		}, {
			'id' : "'02'",
			'text' : '未审核'
		}, {
			'id' : "'03'",
			'text' : '已审核'
		}, {
			'id' : "'04'",
			'text' : '已换票'
		}, {
			'id' : "'05'",
			'text' : '已拒绝'
		}, {
			'id' : "'06'",
			'text' : '已流单'
		}, {
			'id' : "'07'",
			'text' : '已取消'
		} ]
	});

	$('#qstat').combobox("setValue", "");

	$('#orgId').combobox({
		url : $ctx + "/comm/listOrg.do?orgType='0'", // controller地址
		method : 'post',
		width : 250,
		valueField : 'orgId',
		textField : 'orgName',
		multiple : false,
		editable : false,
		onLoadSuccess : function(e) {
		},
		onLoadError : function(e) {
			getAjaxError(e);
		},
		loadFilter : function(data) {
			var all = [ {
				orgId : '',
				orgName : '--全部--'
			} ];
			return $.merge(all, data);
		}
	});
}
function createTable(queryParams) {
	$myTable.datagrid({
		nowrap : true,// 是否只显示一行，即文本过多是否省略部分。
		striped : true,
		url : $ctx + '/teamExam/listApplyHis.do', // controller地址
		onLoadSuccess : function(e) {
		},
		onLoadError : function(e) {
			getAjaxError(e);
		},
		queryParams : queryParams,
		idField : 'applyId',
		fitColumns : true,
		rownumbers : true,
		singleSelect : true,
		pagination : true,
		fit : true,
		columns : [ [// 显示的列
		{
			field : 'applyId',
			title : '订单号',
			width : 100,
			align : 'center'
		}, {
			field : 'applyTime',
			title : '预订时间',
			width : 140,
			align : 'center'
		}, {
			field : 'inDt',
			title : '入园日期',
			width : 100,
			align : 'center'
		}, {
			field : 'stat',
			title : '订单状态',
			width : 80,
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
			width : 100,
			align : 'center'
		}, {
			field : 'examFrozenAdvanceAmt',
			title : '审核冻结预付款',
			width : 100,
			align : 'center'
		}, {
			field : 'minusAdvanceAmt',
			title : '换票扣减预付款',
			width : 100,
			align : 'center'
		}, {
			field : 'flowAdvanceAmt',
			title : '流单扣罚预付款',
			width : 100,
			align : 'center'
		}, {
			field : 'totalExamNum',
			title : '人数',
			width : 60,
			align : 'center'
		}, {
			field : 'detail',
			title : '详情',
			width : 60,
			align : 'center',
			formatter : getDetail
		}, {
			field : 'printInfo',
			title : '打印',
			width : 100,
			align : 'center',
			halign : 'center',
			hidden : true,
			formatter : printInfo
		} ] ],
		toolbar : '#mytable-buttons'
	});

}

function getDetail(value, row, index) {
	var s1;
	s1 = '<span class="datagrida"><a href="javascript:void(0)" title="详情" onclick="showDetail('
			+ index + ')">详情</a></span> ';
	return s1;
}

function getStatDesc(value, row, index) {
	var desc = "";
	var rowData = $myTable.datagrid("getRows")[index];
	var stat = rowData.stat;
	if ("01" == stat) {
		desc = "临时保存";
	} else if ("02" == stat) {
		desc = "待审核";
	} else if ("03" == stat) {
		desc = "已审核";
	} else if ("04" == stat) {
		desc = "已换票";
	} else if ("05" == stat) {
		desc = "已拒绝";
	} else if ("06" == stat) {
		desc = "已流单";
	} else if ("07" == stat) {
		desc = "已取消";
	}
	return desc;
}
function showDetail(index) {
	var rowData = $myTable.datagrid("getRows")[index];
	var applyId = rowData.applyId;
	var url = $ctx + '/teamExam/showDetail.do?applyId=' + applyId + '&flag=d';
	$("#detailDialog").dialog({
		title : " 详情",
		resizable : true,
		inline : true,
		cache : false,
		modal : true,
		href : url,
		buttons : "#dlg-buttons",
		onLoadError : function(e) {
			getAjaxError(e);
		}
	}).dialog("open");
}
function printInfo(value, row, index) {
	var s1;
	var selRow = $myTable.datagrid('getRows')[index];
	var stat = selRow.stat;
	if ('03' == stat) {
		s1 = '<span class="datagrida"><a href="javascript:void(0)" title="打印" onclick="showPrint('
				+ index + ')">打印</a></span> ';
	} else {
		s1 = '--';
	}
	return s1;
}
function showPrint(index) {
	var rowData = $myTable.datagrid("getRows")[index];
	var applyId = rowData.applyId;
	var url = $ctx + '/teamExam/showDetail.do?applyId=' + applyId + '&flag=p';
	$("#detailDialog").dialog({
		title : "打印",
		resizable : true,
		inline : true,
		cache : false,
		modal : true,
		href : url,
		buttons : "#dlg-buttons",
		onLoadError : function(e) {
			getAjaxError(e);
		}
	}).dialog("open");
}
function myQuery() {
	var stat = $('#qstat').combobox('getValues');
	var applyBTime = $('#qapplyBTime').datebox('getValue');
	var applyETime = $('#qapplyETime').datebox('getValue');
	var orgId = $('#orgId').combobox('getValue');

	var inDt = $('#inDt').datebox('getValue');

	if (stat == "") {
		stat = "'02','03','04','05','06','07'";
	}

	var queryParams = {
		'stat' : stat.toString(),
		'applyBTime' : applyBTime,
		'applyETime' : applyETime,
		'orgId' : orgId,
		'inDt' : inDt
	};

	$myTable.datagrid('options').queryParams = queryParams;
	$myTable.datagrid('load');
	getTotalNum(queryParams);
	clsCheck('#mytable');
	return true;
}
function getTotalNum(params) {
	$.ajax({
		url : $ctx + "/teamExam/getTotalNumHis.do",
		data : params,
		type : "post",
		success : function(e) {
			$("#totalNum").html(e);
		},
		error : function(e) {
			getAjaxError(e);
		}
	});

}
function orderExam(index) {
	var rowData = $myTable.datagrid("getRows")[index];
	var applyId = rowData.applyId;
	var url = $ctx + '/teamExam/beforeDExam.do?applyId=' + applyId;
	var iframe = '<iframe id="examIframe" src=' + url
			+ ' frameborder="0" width="100%" height="100%"></iframe>'
	$("#examDialog").dialog({
		title : "订单审核",
		resizable : true,
		inline : true,
		content : iframe,
		cache : false,
		modal : true,
		// buttons : "#dlg-buttons",
		onLoadError : function(e) {
			getAjaxError(e);
		},
		onclose : function() {
			$("#examIframe").remove();
		}
	}).dialog("open");
}

function printInfo1() {
	myPreview("35", "35", $("#printContent").html());
}
