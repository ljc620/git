$(function() {
	$myTable = $('#mytable');
	createOrgTable();
});
function createOrgTable() {
	$myTable.datagrid({
		nowrap : true,// 是否只显示一行，即文本过多是否省略部分。
		striped : true,
		url : $ctx + '/outletMng/listOutlet.do', // controller地址
		onLoadSuccess : function(e) {
		},
		onLoadError : function(e) {
			getAjaxError(e);
		},
		idField : 'outletId',
		fitColumns : false,
		fit : true,
		checkbox : true,
		rownumbers : true,
		singleSelect : false,
		pagination : true, // 包含分页
		pageSize : getPageSize(),
		pageList : getPageList(),
		frozenColumns : [ [ {
			field : 'ck',
			checkbox : true
		} ] ],
		columns : [ [// 显示的列
		{
			field : 'outletId',
			title : '编号',
			width : 120,
			align : 'center'
		}, {
			field : 'outletName',
			title : '名称',
			width : 120,
			align : 'left'
		}, {
			field : 'location',
			title : '地点',
			width : 120,
			align : 'center'
		}, {
			field : 'leader',
			title : '负责人',
			width : 100,
			align : 'center'
		}, {
			field : 'tel',
			title : '联系电话',
			align : 'center',
			width : 100
		}, {
			field : 'outletTypeName',
			title : '网点类型',
			align : 'center',
			width : 150
		}, {
			field : 'stat',
			title : '状态',
			align : 'center',
			halign: 'center',
			formatter : function(value, row, index) {
				if (value == 'Y') {
					value = '启用';
				} else if (value == 'N') {
					value = '禁用';
				}
				return value;
			},
			width:100
		}, {
			field : 'orgName',
			title : '所属机构',
			align : 'center',
			halign: 'center',
			width : 150
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
function getstr(value, row, index) {
	var selRow = $myTable.datagrid('getRows')[index];
	var s1;
	s1 = '<a href="javascript:void(0)" title="修改" class="opr-btn icon-edit" onclick="updateOutlet(' + index + ')"></a> ';
	//s2 = '<a href="javascript:void(0)" title="删除" class="opr-btn icon-del" onclick="delOutlet(' + index + ')"></a> ';
	return s1 ;
}
function myQuery() {
	var outletName = $('#qoutletName').textbox('getValue');

	var queryParams = {
		'outletName' : outletName
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
function updateOutlet(index) {
	$("#save-button").hide();
	$("#update-button").show();
	var rowData = $myTable.datagrid("getRows")[index];
	var outletId = rowData.outletId;
	var url = $ctx + '/outletMng/beforeUpdteOutlet.do?outletId='+outletId;
		$("#outletDialog").dialog({
			title:"修改网点",
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

function addOutlet() {
	$("#save-button").show();
    $("#update-button").hide();
var url = $ctx + '/pages/sys/outletmng/addOutlet.jsp';
	$("#outletDialog").dialog({
		title:"新增网点",
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
function saveOutlet(url) {
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

					$('#outletDialog').dialog('close');
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
function delOutlet(index){
	var rowData = $myTable.datagrid("getRows")[index];
	var outletId = rowData.outletId;
	$.messager.confirm('提示', '您确定要删除[' + rowData.outletName + ']记录吗?', function(r) {
		if (r) {
			$.ajax({
				url : $ctx + "/outletMng/delOutlet.do",
				type : "post",
				data : {outletId:outletId},
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

/**
 *修改状态 
 * 
 */
function updateStat(stat) {
	var selRow = $myTable.datagrid("getSelections"); // 返回选中多行
	var sttText = '';
	if (stat == 'Y') {
		sttText = '正常';
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
			var id = selRow[i].outletId; // OTRECORDID这个是你要在列表中取的单个id
			ids.push("'" + id + "'"); // 然后把单个id循环放到ids的数组中
		}
		var keyIds = ids.join(",");

		$.messager.confirm('提示', '您确定修改[' + selRow.length + ']条状态吗?', function(r) {
			if (r) {
				$.ajax({
					url : $ctx + "/outletMng/updateStat.do",
					type : "post",
					data : {
						"outletId" : keyIds,
						"stat" : stat
					},
					success : function(e) {
						$myTable.datagrid('reload');
						clsCheck("#mytable");// 清空选择的行
						$.messager.show({
							showType : 'slide',
							title : "提示信息",
							msg : "更新成功",
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
