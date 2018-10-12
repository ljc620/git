$(function() {
	$myTable = $('#mytable');
	
	createOrgTable();
});
function createOrgTable() {
	$myTable.datagrid({
		nowrap : true,// 是否只显示一行，即文本过多是否省略部分。
		striped : true,
		url : $ctx + '/periodMng/listPeriod.do', // controller地址
		onLoadSuccess : function(e) {
		},
		onLoadError : function(e) {
			getAjaxError(e);
		},
		idField : 'salePeriodId',
		fitColumns : false,
		rownumbers : true,
		singleSelect : true,
		striped : true,
		pagination : true,
		fit:true,
		columns : [ [// 显示的列
	      {
			field : 'salePeriodName',
			title : '预售期名称',
			width : 120,
			editor : "text",
			align : 'left'
		}, {
			field : 'beginDt',
			title : '预售期开始日期',
			width : 120,
			editor : "text",
			align : 'center'
		}, {
			field : 'endDt',
			title : '预售期结束日期',
			width : 120,
			editor : "text",
			align : 'center'
		},{
			field : 'ticketTypeId',
			title : '票种名称',
			hidden:true,
			width:100
		},{
			field : 'ticketTypeName',
			title : '票种名称',
			align : 'center',
			halign: 'center',
			width:100
		},{
			field : 'discountPrice',
			title : '折后票价（元）',
			width : 120,
			editor : "text",
			align : 'center'
		}, {
			field : 'caozuo',
			title : '操作',
			align : 'center',
			halign: 'center',
			formatter : getstr,
			width:100
		}] ],
		toolbar : '#mytable-buttons'
	});

}



function getstr(value, row, index) {
	var selRow = $myTable.datagrid('getRows')[index];
	var s1, s2;
	s1 = '<a href="javascript:void(0)" title="修改" class="opr-btn icon-edit" onclick="updatePeriod(' + index + ')"></a> ';
	s2 = '<a href="javascript:void(0)" title="删除" class="opr-btn icon-del" onclick="delPeriod(' + index + ')"></a> ';
	return s1 + s2;
}



/**
 * 添加预售期
 */

function addPeriod() {
	$("#save-button").show();
    $("#update-button").hide();
var url = $ctx + '/pages/sys/periodMng/addPeriod.jsp';
var iframe='<iframe id="blackIframe" src='+url+' frameborder="0" width="790px" height="550px"></iframe>'
	$("#periodDialog").dialog({
		title:"新增预售期",
		resizable:true,
		inline:true,
		cache : false,
		content:iframe,
		modal : true,
		href :url, 
		buttons : "#dlg-buttons",
		onclose:function(){$("#blackIframe").remove();},
	}).dialog("open");
   // $('#fm').form("clear");
}



function delPeriod(index){
	var rowData = $myTable.datagrid("getRows")[index];
	var salePeriodId = rowData.salePeriodId;
	$.messager.confirm('提示', '您确定要删除[' + rowData.salePeriodName + ']记录吗?', function(r) {
		if (r) {
			$.ajax({
				url : $ctx + "/periodMng/delPeriod.do",
				type : "post",
				data : {salePeriodId:salePeriodId},
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

function updatePeriod(index) {
	$("#save-button").hide();
	$("#update-button").show();
	var rowData = $myTable.datagrid("getRows")[index];
	var salePeriodId = rowData.salePeriodId;
	var url = $ctx + '/periodMng/beforeUpdtePeriod.do?salePeriodId='+salePeriodId;
		$("#periodDialog").dialog({
			title:"修改预售期",
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


function savePeriod(url) {
	if (!$('#fm').form('validate')) {
		return false;
	}
	$.messager.confirm('提示', '确定保存信息吗？', function(r) {
		if (r) {
			$.messager.progress({
				text : '正在处理,请稍候...'
			});

			$('#fm').form('submit', {
				url : $ctx + url,
				success : function(e) {
					$.messager.progress("close");
					if (!getFormError(e)) {
						return false;
					}

					$('#periodDialog').dialog('close');
					$myTable.datagrid('reload'); // 重载行
					$myTable.datagrid('clearSelections');
					$.messager.show({
						showType : 'slide',
						title : "提示信息",
						msg : '保存成功',
						timeout : 2000
					});
				}
			}, 'json');
		}
	});
}

