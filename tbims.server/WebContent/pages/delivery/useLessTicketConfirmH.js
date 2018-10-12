$(function() {
	$myTable = $('#mytable');
	/*
	// 网点初始化
	$('#outletId').combobox({
		url : $ctx + '/comm/listOutlet.do',
		method : 'get',
		valueField : 'outletId',
		textField : 'outletName',
		multiple : false,
		editable : false,
		onLoadSuccess : function(e) {
		},
		onLoadError : function(e) {
			getAjaxError(e);
		},
		loadFilter : function(data) {
			var outletLists = [ {
				outletId : '',
				outletName : '--全部--',
				outletType : ''
			} ];

			return $.merge(outletLists, data);
		},
		formatter : function(rows) {
			var typeName = "";

			switch (rows.outletType) {
			case '01':
				typeName = "现场网点";
				break;
			case '02':
				typeName = "自营网点";
				break;
			case '03':
				typeName = "换票网点";
				break;
			case '04':
				typeName = "票务处理";
				break;
			case '05':
				typeName = "实体代理";
				break;
			case '06':
				typeName = " 签约社";
				break;
			}

			return typeName + "--" + rows.outletName;
		}
	});
	//是否确认
	$('#stat').combobox({
		valueField : 'id',
		textField : 'text',
		editable:false,
		panelHeight:'auto',
		data:[{
			id:'2',
			text:'已确认',
			selected:true
		}]
	});*/
	
	var queryParams = {
			'stat':'2',
			'startDate' : formatterDate(new Date()),
			'endDate':formatterDate(new Date())	
		}
	createTable(queryParams);
	getTotalNum(queryParams);
});

function createTable(queryParams) {
	$myTable.datagrid({
		nowrap : true,// 是否只显示一行，即文本过多是否省略部分。
		url : $ctx + '/ticketConfirm/listTicketConfirmHis.do', // controller地址
		onLoadSuccess : function(data) {
			/*var rows=data.rows;
			var totalNum=0;
			if(rows!=null&&rows.length!=0){
				for(var j=0;j<rows.length;j++){
					totalNum=totalNum+rows[j].ticketNum;
				}
				$myTable.datagrid('appendRow',{
					uselessTime: '合计',
					ticketNum:totalNum,
					opt:''
				});
				var merges=[{
					index:data.total-1,
					colspan:3
				}]; 
				for(var i=0;i<merges.length;i++){
					$myTable.datagrid('mergeCells',{
						index:merges[i].index,
						field:'uselessTime',
						colspan:merges[i].colspan
					});
				}
			}
			var checkbox=$('div.datagrid-cell-check input[type=checkbox]');
			if(rows.length){
				for(var i=0;i<rows.length;i++){
					if(i==rows.length-1){
						$(checkbox.get(i)).disabled==true;
						$(checkbox.get(i)).remove();
					}
				}
			}*/
		},
		/*onCheckAll:function(rowIndex,data){
			$myTable.datagrid('uncheckRow',rowIndex.length-1);
			var checkbox=$('div.datagrid-header-check input[type=checkbox]');
			checkbox.prop('checked','checked');
		},*/
		onLoadError : function(e) {
			getAjaxError(e);
		},
		queryParams:queryParams,
		singleSelect : false,
		fitColumns : false,
		rownumbers : true,
		showFooter : true,
		striped : true,
		pagination : true,
		fit : true,
		queryParams :queryParams,
		/*frozenColumns : [ [ {
			field : 'ck',
			checkbox : true
		} ] ],*/
		columns : [ [// 显示的列
		{
			field : 'uselessTime',
			title : '作废日期',
			width : 150,
			align : 'center'
		}, {
			field : 'outletName',
			title : '网点名称',
			width : 150,
			align : 'center'
		}, {
			field : 'ticketTypeName',
			title : '门票种类',
			width : 150,
			align : 'center',
		}, {
			field : 'ticketNum',
			title : '作废数量',
			width : 80,
			align : 'center',
			formatter : getTicketInfo
		}, {
			field : 'opt',
			title : '是否确认',
			width : 120,
			align : 'center',
			formatter : getStr
		} ] ],
		toolbar : '#mytable-buttons'
	});
}

function getStr(value, row, index) {
	var selectRows = $myTable.datagrid('getRows')[index];
	if(selectRows.outletId!=null&&selectRows.ticketTypeId!=null){
		if (selectRows.confirmTime == null || selectRows.confirmTime == '') {
			return "未确认";
		} else {
			return "已确认";
		}
	}
}
function getTotalNum(params){
	$.ajax({
		url : $ctx + "/ticketConfirm/getToNum.do",
		data : params,
		type : "post",
		success : function(e) {
			$("#totalNum").html("");
			$("#totalNum").html(e);
		},
		error : function(e) {
			getAjaxError(e);
		}
	});
	
}
function getTicketInfo(value, row, index) {
	var s1 = '<a href="javascript:void(0)" title="详情"  onclick="showDetail('+ index + ')"><u>'+value + '</u></a> ';
	return s1;
}
function showDetail(index) {
	var selRow = $myTable.datagrid('getRows')[index];
	var uselessTime = selRow.uselessTime;
	var outletId = selRow.outletId;
	var ticketTypeId = selRow.ticketTypeId;
	/*var stat1;
	if(selRow.confirmTime == null || selRow.confirmTime == ''){
		stat1='1';//未确认
	}else{
		stat1='2';//已确认
	}*/
	var startDate = $('#startDate').datebox('getValue');
	var endDate = $('#endDate').datebox('getValue');
	var outletId1 = $('#outletId').combobox('getValue');
	var stat = '2';
	top.addTab('废票详情', $ctx + '/pages/delivery/ticketInfo.jsp?uselessTime='+uselessTime + '&outletId=' + outletId + '&ticketTypeId='+ ticketTypeId+'&stat='+stat);
}
function myQuery() {
	if (!$('#form').form('validate')) {
		return false;
	} 
	var startDate = $('#startDate').datebox('getValue');
	var endDate = $('#endDate').datebox('getValue');
	var outletId = $('#outletId').combobox('getValue');
	var stat='2';
	var queryParams = {
		'startDate' : startDate,
		'endDate':endDate,
		'outletId' : outletId,
		'stat':stat
	}
	$myTable.datagrid('options').queryParams = queryParams;
	$myTable.datagrid('load');
	clsCheck('#mytable');
	getTotalNum(queryParams);
	return true;
}
/**
 * 交回确认
 * 
 * @returns {Boolean}
 */
function confirm(index) {
	var outletId = $('#outletId').combobox('getValue');

	var selRow = $myTable.datagrid("getSelections"); // 返回选中多行
	if (selRow.length == 0) {
		alert("请选择要确认的票!");
		return false;
	} 
	alert(selRow.length);
	for (var i = 0; i < selRow.length; i++) {
		/*if (selRow[i].confirmTime != null) {
			alert("请确定选中的行是否已确认!");
			return false;
		}*/
		if (selRow[i].uselessTime == '合计') {
			alert("合计行不能进行确认!");
			return false;
		}
	}
	var details = new Array();
	for (var i = 0; i < selRow.length; i++) {
		var detail = new Object();
		detail.uselessTime = selRow[i].uselessTime;
		detail.outletId = selRow[i].outletId;
		detail.ticketTypeId = selRow[i].ticketTypeId;
		details.push(detail);
	}
	var myJSONText = JSON.stringify(details);
	$.messager.confirm('提示', '您确定要确认吗?', function(r) {
		if (r) {
			$.ajax({
				url : $ctx + "/ticketConfirm/confirm.do",
				type : "post",
				data : {
					myJSONText : myJSONText
				},
				success : function(e) {
					$myTable.datagrid("reload");
					$myTable.datagrid('clearSelections');
					$.messager.show({
						showType : 'slide',
						title : "提示信息",
						msg : "确认成功",
						timeout : 2000
					});

				},
				error : function(e) {
					getAjaxError(e);
				}
			});
		}
	});
}
