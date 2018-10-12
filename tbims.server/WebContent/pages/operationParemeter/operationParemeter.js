$(function() {
	$myTable = $('#mytable');
	createOrgTable();
});
function createOrgTable() {
	$myTable.datagrid({
		nowrap : true,// 是否只显示一行，即文本过多是否省略部分。
		striped : true,
		url : $ctx + '/operationParemeter/listOperationParemeter.do', // controller地址
		onLoadSuccess : function(e) {
		},
		onLoadError : function(e) {
			getAjaxError(e);
		},
		idField : 'paremeterId',
		fitColumns : false,
		rownumbers : true,
		singleSelect : true,
		striped : true,
		pagination : true,
		fit:true,
		columns : [ [// 显示的列
             {
     			field : 'paremeterId',
     			title : '运营参数Id',
     			width : 200,
     			editor : "text",
     			align : 'left'
     				
     		},{
			field : 'paremeterName',
			title : '运营参数名称',
			width : 200,
			editor : "text",
			align : 'left'
				
		}, {
			field : 'paremeterVal',
			title : '参数值',
			width : 150,
			editor : "text",
			align : 'left'
		},{
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
	s1 = '<a href="javascript:void(0)" title="修改" class="opr-btn icon-edit" onclick="updateOperationParemeter(' + index + ')"></a> ';
	s2 = '<a href="javascript:void(0)" title="删除" class="opr-btn icon-del" onclick="delOperationParemeter(' + index + ')"></a> ';
	return s1 + s2;
}





/**
 * 添加销售参数
 */

function addOperationParemeter() {
	$("#save-button").show();
    $("#update-button").hide();
    var url = $ctx + '/pages/operationParemeter/addOperationParemeter.jsp';
var iframe='<iframe id="blackIframe" src='+url+' frameborder="0" width="790px" height="550px"></iframe>'
	$("#periodDialog").dialog({
		title:"新增运营参数",
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


function delOperationParemeter(index){
	var rowData = $myTable.datagrid("getRows")[index];
	var paremeterId = rowData.paremeterId;
	$.messager.confirm('提示', '您确定要删除[' + rowData.paremeterName + ']记录吗?', function(r) {
		if (r) {
			$.ajax({
				url : $ctx + "/operationParemeter/delOperationParemeter.do",
				type : "post",
				data : {paremeterId:paremeterId},
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
function saveOperationParemeter(url) {
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

function updateOperationParemeter(index) {
	$("#save-button").hide();
	$("#update-button").show();
	var rowData = $myTable.datagrid("getRows")[index];
	var paremeterId = rowData.paremeterId;
	var url = $ctx + '/operationParemeter/beforeUpdteParemeter.do?paremeterId='+paremeterId;
		$("#periodDialog").dialog({
			title:"修改运营参数",
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



