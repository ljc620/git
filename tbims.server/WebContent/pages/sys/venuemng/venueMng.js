$(function() {
	$myTable = $('#mytable');
	createVenueTable();
});
function createVenueTable() {
	$myTable.datagrid({
		nowrap : true,// 是否只显示一行，即文本过多是否省略部分。
		striped : true,
		url : $ctx + '/venueMng/listVenue.do', // controller地址
		onLoadSuccess : function(e) {
		},
		onLoadError : function(e) {
			getAjaxError(e);
		},
		idField : 'venueId',
		fitColumns : false,
		rownumbers : true,
		singleSelect : false,
		checkbox:true,
		striped : true,
		pagination : true,
		fit:true,
		frozenColumns : [ [ {
			field : 'ck',
			checkbox : true
		} ] ],
		columns : [ [// 显示的列
		{
			field : 'venueId',
			title : '场馆编号',
			width : 120,
			align : 'center'
		}, {
			field : 'venueName',
			title : '场馆名称',
			width : 120,
			align : 'center'
		}, {
			field : 'stat',
			title : '状态',
			width : 120,
			align : 'center',
			formatter:function(value,row,index){
				if(value=='Y'){
					return '启用';
				}
				else if(value=='N'){
					return '禁用';
				}else{
					return value;
				}
			}
		},{
			field : 'caozuo',
			title : '操作',
			align : 'center',
			width : 100,
			formatter : getstr
		}] ],
		toolbar : '#mytable-buttons'
	});

}
function getstr(value, row, index) {
	var selRow = $myTable.datagrid('getRows')[index];
	var s1//, s2;
	s1 = '<a href="javascript:void(0)" title="修改" class="opr-btn icon-edit" onclick="updateVenue(' + index + ')"></a> ';
	//s2 = '<a href="javascript:void(0)" title="删除" class="opr-btn icon-del" onclick="delVenue(' + index + ')"></a> ';
	return s1 //+ s2;
}
function myQuery() {
	var venueName = $('#qvenueName').textbox('getValue');
	var queryParams = {
		'venueName' : venueName
	}
	$myTable.datagrid('options').queryParams = queryParams;
	$myTable.datagrid('load');
	clsCheck('#mytable');
	return true;
}
/**
 * 修改网点
 * @param index
 */
function updateVenue(index) {
	$("#save-button").hide();
	$("#update-button").show();
	var rowData = $myTable.datagrid("getRows")[index];
	var venueId = rowData.venueId;
	var url = $ctx + '/venueMng/beforeUpdtVenue.do?venueId='+venueId;
		$("#venueDialog").dialog({
			title:"修改场馆",
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

/**
 * 添加网点
 */

function addVenue() {
	$("#save-button").show();
    $("#update-button").hide();
    var url = $ctx + '/pages/sys/venuemng/addVenue.jsp';
	$("#venueDialog").dialog({
		title:"新增场馆",
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
 * 保存网点
 * @returns {Boolean}
 */
function saveVenue(url) {
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

					$('#venueDialog').dialog('close');
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
function delVenue(index){
	var rowData = $myTable.datagrid("getRows")[index];
	var venueId = rowData.venueId;
	$.messager.confirm('提示', '您确定要删除[' + rowData.venueName + ']记录吗?', function(r) {
		if (r) {
			$.ajax({
				url : $ctx + "/venueMng/delVenue.do",
				type : "post",
				data : {venueId:venueId},
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
function updateStatus(status) {
	var selRow = $myTable.datagrid("getSelections"); // 返回选中多行
	var sttText = '';
	if (status == 'Y') {
		sttText = '启用';
	} else {
		sttText = '停用';
	}
	if (selRow.length == 0) {
		alert("请至少选择一行数据!");
		return false;
	} else {
		var ids = [];
		for (var i = 0; i < selRow.length; i++) {
			// 获取自定义table 的中的checkbox值
			var id = selRow[i].venueId; // OTRECORDID这个是你要在列表中取的单个id
			ids.push("'" + id + "'"); // 然后把单个id循环放到ids的数组中
		}
		var keyIds = ids.join(",");

		$.messager.confirm('提示', '您确定要' + sttText + '[' + selRow.length
				+ ']个场馆吗?', function(r) {
			if (r) {
				$.ajax({
					url : $ctx + "/venueMng/updateStat.do",
					type : "post",
					data : {
						"venueIds" : keyIds,
						"stat" : status
					},
					success : function(e) {
						$myTable.datagrid("reload");
						$myTable.datagrid('clearSelections');
						$.messager.show({
							showType : 'slide',
							title : "提示信息",
							msg : sttText + "成功",
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
}