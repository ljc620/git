$(function() {
	$myTable = $('#mytable');
	$orgId = $('#orgId').val();
	createLimitAmtTable();
});
function createLimitAmtTable() {
	$myTable.datagrid({
		nowrap : true,// 是否只显示一行，即文本过多是否省略部分。
		striped : true,
		url : $ctx + '/orgMng/listLimitAmt.do?orgId='+$orgId, // controller地址
		onLoadSuccess : function(e) {
		},
		onLoadError : function(e) {
			getAjaxError(e);
		},
		idField : 'ticketTypeId',
		fitColumns : false,
		fit : true,
		checkbox : true,
		rownumbers : true,
		rownumbers : true,
		singleSelect : true,
		striped : true,
		pagination : true,
		frozenColumns : [ [ {
			field : 'ck',
			checkbox : false
		} ] ],
		
		columns : [ [// 显示的列
	    {
			field : 'ticketTypeId',
			title : '票种编号',
			width : 120,
			hidden:true,
			editor : "text",
			align : 'center'
		} ,{
			field : 'ticketTypeName',
			title : '票种名称',
			width : 120,
			editor : "text",
			align : 'center'
		} ,{
			field : 'limitAmt',
			title : '剩余额度',
			align : 'right',
			halign: 'center',
			width : 150
		} , {
			field : 'frozeLimit',
			title : '冻结额度',
			align : 'right',
			halign: 'center',
			width : 150
		} , {
			field : 'caozuo',
			title : '操作',
			align : 'center',
			width : 150,
			formatter : getStr
		}] ],
		toolbar : '#mytable-buttons'
	});

}
/**
 * 获取额度详情
 * @param value
 * @param row
 * @param index
 * @returns {String}
 */
function getStr(value, row, index) {
	var s1;
	s1 = '<span class="datagrida"><a href="javascript:void(0)" title="额度明细"  onclick="getLimitDetail(' + index + ')">额度明细</a> </span>';
	return s1;
}

function getLimitDetail(index){
	var rowData = $myTable.datagrid("getRows")[index];
	var ticketTypeId = rowData.ticketTypeId;
	var url = $ctx + '/orgMng/beforeListLimit.do?orgId='+$orgId+"&ticketTypeId="+ticketTypeId;
	top.addTab("额度明细",url);
}
/**
 * 添加额度
 */

function addLimit(obj) {
	$("#save-button").show();
var url = $ctx + '/orgMng/getOrg.do?orgId='+obj+'&flag=m';
	$("#limitAddDialog").dialog({
		title:"扣减额度",
		resizable:true,
		inline:true,
		cache : false,
		modal : true,
		buttons : "#dlg-buttons",
		href:url
	}).dialog("open");
    $('#fm').form("clear");
}
/**
 * 保存额度
 * @returns {Boolean}
 */
function saveLimit() {
	if (!$('#fm').form('validate')) {
		return false;
	}
	$.messager.confirm('提示', '确定保存信息吗?', function(r) {
		if (r) {
			$.messager.progress({
				text : '正在处理,请稍候...'
			});

			$.ajax({
				url : $ctx + "/orgMng/addLimit.do?flag=m",
				type : "post",
				data : $("#fm").serialize(),
				success : function(e) {
					$.messager.show({
						showType : 'slide',
						title : "提示信息",
						msg : '保存成功',
						timeout : 2000
					});
					$myTable.datagrid("reload");
					$.messager.progress("close");
					$('#limitAddDialog').dialog('close');
				},
				error : function(e) {
					getAjaxError(e);
					$.messager.progress("close");
				}
			});
		}
	});

}


