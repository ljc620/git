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
				width : 180,
				editable : false,
				valueField : 'id',
				textField : 'text',
				data : e
			});
			//$('#examStat').combobox('select', '0');
			//$('#qstat').combobox('setValue','03');
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
		url : $ctx + '/teamOrder/listApply.do', // controller地址
		onLoadSuccess : function(e) {
		},
		onLoadError : function(e) {
			getAjaxError(e);
		},
		idField : 'applyId',
		fitColumns : false,
		rownumbers : true,
		singleSelect : true,
		queryParams:queryParams,
		striped : true,
		pagination : true,
		fit:true,
		columns : [ [// 显示的列
		{
			field : 'applyId',
			title : '订单编号',
			width : 120,
			align : 'center'
		}, {
			field : 'applyTime',
			title : '预订日期',
			width : 120,
			align : 'left'
		}, {
			field : 'stat',
			title : '订单状态',
			width : 120,
			align : 'center',
			formatter : getStatDesc
		}, {
			field : 'remark',
			title : '订单说明',
			width : 120,
			align : 'center'
		}, {
			field : 'applyFrozenAdvanceAmt',
			title : '申请冻结预付款',
			width : 120,
			align : 'center'
		}, {
			field : 'detail',
			title : '详情',
			width : 100,
			align : 'center',
		    formatter : getDetail
		}, {
			field : 'printInfo',
			title : '打印',
			align : 'center',
			halign: 'center',
			width : 100,
			formatter : printInfo
		}, {
			field : 'caozuo',
			title : '操作',
			align : 'center',
			width : 150,
			formatter : getstr
		}] ],
		toolbar : '#mytable-buttons'
	});

}

function getstr(value, row, index) {
	var rowData = $myTable.datagrid("getRows")[index];
	var s1='', s2='',s3='';
	if(rowData.stat=='01'||rowData.stat=='02'||rowData.stat=='03'){
	s1='<a href="javascript:void(0)" title="修改换票人" class="opr-btn icon-edit" onclick="updateChangeUser(' + index + ')"></a> ';
	}
	if(rowData.stat=='01'){
	s2 = '<a href="javascript:void(0)" title="删除" class="opr-btn icon-del" onclick="delOrder(' + index + ')"></a> ';
	s3 = '<a href="javascript:void(0)" title="提交" class="opr-btn icon-submit"   onclick="commitOrder(' + index + ')"></a> ';
	}
	
	return s1+ s2 + s3;
}
function getDetail(value, row, index){
	var s1;
	s1 = '<a href="javascript:void(0)" title="详情" onclick="showDetail(' + index + ')">详情</a> ';
	return s1;
}
function updateChangeUser(index){
	var default_left;
	var default_top;
	var rowData = $myTable.datagrid("getRows")[index];
	var applyId = rowData.applyId;
	var url = $ctx + '/teamOrder/befUpdChangeUser.do?applyId='+applyId;
	var iframe='<iframe id="changIframe" src='+url+' frameborder="0" width="100%" height="100%"></iframe>'
		$("#editDialog").dialog({
			title:"修改换票人",
			resizable:true,
			inline:true,
			content:iframe,
			cache : false,
			modal : true,
			onLoadError:function(e){
				getAjaxError(e);
			},
			onOpen:function(){ 
				//dialog原始left
				default_left=$('#editDialog').panel('options').left; 
				default_top=$('#editDialog').panel('options').top;
				},
			onMove:function(left,top){ //鼠标拖动时事件
				var dd_width= $('#editDialog').panel('options').width;//dialog的宽度
				var dd_height= $('#editDialog').panel('options').height;//dialog的高度
				if(left<1||top<1){
				$('#editDialog').dialog('move',{
				left:default_left,
				top:default_top
				}); 
				}
				},
			onclose:function(){$("#changIframe").remove();}
		}).dialog("open");
	    $('#fm').form("clear");
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
	var rowData = $myTable.datagrid("getRows")[index];
	var s1='--';
	if(rowData.stat=='03'){
	s1 = '<a href="javascript:void(0)" title="打印" onclick="showPrint(' + index + ')">打印</a> ';
	}
	return s1;
}
function showPrint(index){
	var rowData = $myTable.datagrid("getRows")[index];
	var applyId = rowData.applyId;
	var url = $ctx + '/teamOrder/showDetail.do?applyId='+applyId+'&flag=p';
	top.addTab("打印",url);
	/*$("#detailDialog").dialog({
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
	}).dialog("open");*/
}
function myQuery() {
	var applyId = $('#qapplyId').textbox('getValue');
    var stat = $('#qstat').combobox('getValue');
    var applyTime=$('#qapplyTime').datebox('getValue');
	var queryParams = {
		'applyId' : applyId,
		'stat':stat,
		'applyTime':applyTime
	}
	$myTable.datagrid('options').queryParams = queryParams;
	$myTable.datagrid('load');
	clsCheck('#mytable');
	return true;
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




function delOrder(index){
	var rowData = $myTable.datagrid("getRows")[index];
	var applyId = rowData.applyId;
	$.messager.confirm('提示', '您确定要删除[' + rowData.applyId + ']记录吗?', function(r) {
		if (r) {
			$.ajax({
				url : $ctx + "/teamOrder/delOrder.do",
				type : "post",
				data : {applyId:applyId},
				success : function(e) {
					$.messager.show({
						showType : 'slide',
						title : "提示信息",
						msg : '删除成功',
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

function commitOrder(index){
	var rowData = $myTable.datagrid("getRows")[index];
	var applyId = rowData.applyId;
	$.messager.confirm('提示', '提交后不可修改及删除，您确定要提交[' + rowData.applyId + ']记录吗?', function(r) {
		if (r) {
			$.ajax({
				url : $ctx + "/teamOrder/commitOrder.do",
				type : "post",
				data : {applyId:applyId},
				success : function(e) {
					$.messager.show({
						showType : 'slide',
						title : "提示信息",
						msg : '提交成功',
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
