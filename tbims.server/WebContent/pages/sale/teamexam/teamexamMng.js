$(function() {
	$myTable = $('#mytable');
	initcombo();
	var inDt=$('#inDt').datebox('getValue');
	var applyBTime=$('#qapplyBTime').datebox('getValue');
    var applyETime=$('#qapplyETime').datebox('getValue');
	var queryParams = {
		'applyBTime':applyBTime,
		'applyETime':applyETime,
		'inDt':inDt,
		stat:"'02'"
	}
	createTable(queryParams);
	getTotalNum(queryParams);
});
function initcombo(){
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
function getTotalNum(params){
	$.ajax({
		url : $ctx + "/teamExam/getTotalNum.do",
		data : params,
		type : "post",
		success : function(e) {
			if(params.stat=="'02'"){
				$("#mark").html("未审核人数:");
			}
			if(params.stat=="'03'"){
				$("#mark").html("已审核人数:");
			}
			$("#totalNum").html("");
			$("#totalNum").html(e);
		},
		error : function(e) {
			getAjaxError(e);
		}
	});
	
}
function createTable(params) {
	$myTable.datagrid({
		nowrap : true,// 是否只显示一行，即文本过多是否省略部分。
		striped : true,
		url : $ctx + '/teamExam/listApply.do', // controller地址
		onLoadSuccess : function(e) {
		},
		onLoadError : function(e) {
			getAjaxError(e);
		},
		idField : 'applyId',
		fitColumns : false,
		rownumbers : true,
		singleSelect : true,
		queryParams:params,
		pagination : true,
		fit:true,
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
			field : 'applyUserName',
			title : '预订人',
			width : 100,
			align : 'center'
		}, {
			field : 'stat',
			title : '订单状态',
			width : 70,
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
			field : 'detail',
			title : '详情',
			width : 60,
			align : 'center',
		    formatter : getDetail
		}, {
			field : 'applyFrozenAdvanceAmt',
			title : '申请冻结预付款',
			width : 100,
			align : 'center'
		}, {
			field : 'examFrozenAdvanceAmt',
			title : '审核冻结金额',
			width : 100,
			align : 'center'
		}, {
			field : 'totalExamNum',
			title : '人数',
			width : 60,
			align : 'center'
		}, {
			field : 'printInfo',
			title : '打印',
			align : 'center',
			halign: 'center',
			hidden:true,
			width : 50,
			formatter : printInfo
		}, {
			field : 'caozuo',
			title : '操作',
			align : 'center',
			width : 100,
			formatter : getstr
		}] ],
		toolbar : '#mytable-buttons'
	});

}

function getstr(value, row, index){
	var selRow = $myTable.datagrid('getRows')[index];
	var stat=selRow.stat;
	var s1='',s2='';
	if('02'==stat){
	s1 = '<span class="datagrida"><a href="javascript:void(0)" title="订单审核"  onclick="orderExam(' + index + ')">订单审核</a></span> ';
	}
	else{
		s2 = '<span class="datagrida"><a href="javascript:void(0)" title="订单取消"  onclick="cancleExam(' + index + ')">订单取消</a></span> ';;	
	}
	return s1+s2;
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
	var url = $ctx + '/teamExam/showDetail.do?applyId='+applyId+'&flag=d';
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
	s1 = '<a href="javascript:void(0)" title="打印" onclick="showPrint(' + index + ')">打印</a> ';
	}
	else{
		s1 = '--';	
	}
	return s1;
}
function showPrint(index){
	var rowData = $myTable.datagrid("getRows")[index];
	var applyId = rowData.applyId;
	var url = $ctx + '/teamExam/showDetail.do?applyId='+applyId+'&flag=p';
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
   var stat = $('#qstat').combobox('getValue');
   var applyBTime=$('#qapplyBTime').datebox('getValue');
   var applyETime=$('#qapplyETime').datebox('getValue');
   var orgId = $('#orgId').combobox('getValue');
   var inDt=$('#inDt').datebox('getValue');
   
	var queryParams = {
		'stat':stat,
		'applyBTime':applyBTime,
		'applyETime':applyETime,
		'orgId' : orgId,
		'inDt':inDt,
	}
	$myTable.datagrid('options').queryParams = queryParams;
	$myTable.datagrid('load');
	clsCheck('#mytable');
	getTotalNum(queryParams);
	return true;
}
function cancleExam(index){
	var rowData = $myTable.datagrid("getRows")[index];
	var applyId = rowData.applyId;
	$.messager.confirm('提示', '您确定要取消订单号为：[' + rowData.applyId + ']的订单吗?', function(r) {
		if (r) {
			$.ajax({
				url : $ctx + "/teamExam/cancleOrder.do",
				type : "post",
				data : {applyId:applyId},
				success : function(e) {
					$.messager.show({
						showType : 'slide',
						title : "提示信息",
						msg : '取消成功',
						timeout : 2000
					});
					$myTable.datagrid("reload");
					$.messager.progress("close");
				},
				error : function(e) {
					getAjaxError(e);
				}
			});
		}
	});
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
/**
 * 添加订单申请
 */

function addOrder() {
	var default_left;
	var default_top;
var url = $ctx + '/pages/sale/addOrder.jsp';
var iframe='<iframe id="examIframe" src='+url+' frameborder="0" width="100%" height="100%"></iframe>'
	$("#addDialog").dialog({
		title:"新增预订单",
		resizable:true,
		inline:true,
		content:iframe,
		cache : false,
		modal : true,
		//buttons : "#dlg-buttons",
		onLoadError:function(e){
			getAjaxError(e);
		},
		onOpen:function(){ 
			//dialog原始left
			default_left=$('#addDialog').panel('options').left; 
			default_top=$('#addDialog').panel('options').top;
			},
		onMove:function(left,top){ //鼠标拖动时事件
			var dd_width= $('#addDialog').panel('options').width;//dialog的宽度
			var dd_height= $('#addDialog').panel('options').height;//dialog的高度
			if(left<1||top<1){
			$('#addDialog').dialog('move',{
			left:default_left,
			top:default_top
			}); 
			}
			},
		onclose:function(){$("#examIframe").remove();}
	}).dialog("open");
    $('#fm').form("clear");
}





