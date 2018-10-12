/**
 * 刷新权限树
 */
function refreshOrgTree() {
	var setting = {
		data : {
			simpleData : {
				enable : true
			}
		},
		view : {
			showLine : true
		},
		callback : {
			onClick : query
		}
	};

	$.ajax({
		url : $ctx + '/menumng/treeMenu.do',
		type : "post",
		success : function(data) {
			$.fn.zTree.init($("#menuTree"), setting, data);
			$menuTree = $.fn.zTree.getZTreeObj("menuTree");
		},
		error : function(e) {
			getAjaxError(e);
		}
	});
}

/**
 * 新增或编辑表单保存数据
 * 
 * @param url
 * @returns {Boolean}
 */
function saveMenu(url) {
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
				onSubmit : function() {
					return true;
				},
				success : function(e) {
					// 根据所属菜单选中权限菜单树
					$.messager.progress("close");
					if (!getFormError(e)) {
						return false;
					}
					$('#menuDialog').dialog('close');
					$dg.datagrid('reload'); // 重载行
					$dg.datagrid('clearSelections');
					$.messager.show({
						showType : 'slide',
						title : "提示信息",
						msg : '保存成功',
						timeout : 2000
					});
					refreshOrgTree();
				},
				error : function() {
					alert("加载失败");
				}
			});
		}
	});
}

function addMenu() {
	$("#addMenu").show();
	$("#updateMenu").hide();

	var url = $ctx + '/menumng/addMenuBefore.do';

	$("#menuDialog").dialog({
		title : "添加菜单",
		cache : false,
		modal : true,
		method : "post",
		href : url,
		buttons:"#dlg-menu-buttons",
		queryParams : {
			menuPid : $parent_cd
		},
		onLoadError : function(e) {
			getAjaxError(e);
		}
	});
	
	$("#menuDialog").dialog("open");
}

function deleteMenu() {
	var ids = [];
	var selRow = $dg.datagrid("getSelections"); // 返回选中多行
	if (selRow.length == 0) {
		alert("请至少选择一行数据!");
		return false;
	}
	for (var i = 0; i < selRow.length; i++) {
		// 获取自定义table 的中的checkbox值
		var menuId = selRow[i].menuId; // OTRECORDID这个是你要在列表中取的单个id
		ids.push("'" + menuId + "'"); // 然后把单个id循环放到ids的数组中
	}

	var keyIds = ids.join(",");
	$.messager.confirm('提示', '您确定要删除[' + ids.length + ']条记录吗?', function(r) {
		if (r) {
			$.ajax({
				url : $ctx + "/menumng/deleteByMenuIds.do",
				type : "post",
				data : "menuIds=" + keyIds,
				success : function(e) {
					$dg.datagrid('reload');
					$dg.datagrid('clearSelections');
					refreshOrgTree();
					$.messager.show({
						showType : 'slide',
						title : "提示信息",
						msg : "删除成功",
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

function updateMenu(index) {
	$("#addMenu").hide();
	$("#updateMenu").show();
	var rowData = $dg.datagrid("getRows")[index];
	var url = $ctx + '/menumng/findMenuById.do';

	$("#menuDialog").dialog({
		title : "修改菜单",
		resizable : true,
		inline : true,
		cache : true,
		modal : true,
		method : "post",
		href : url,
		buttons:"#dlg-menu-buttons",
		queryParams : {
			menuId : rowData.menuId
		},
		onLoadError : function(e) {
			getAjaxError(e);
		}
	}).dialog("open");

}

/**
 * 更改权限状态
 * 
 */
function updateMenuStatus(menuStat) {
	var selRow = $dg.datagrid("getSelections"); // 返回选中多行
	if (selRow.length == 0) {
		alert("请至少选择一行数据!");
		return false;
	} else {
		var ids = [];
		for (var i = 0; i < selRow.length; i++) {
			// 获取自定义table 的中的checkbox值
			var id = selRow[i].menuId; // OTRECORDID这个是你要在列表中取的单个id
			ids.push("'" + id + "'"); // 然后把单个id循环放到ids的数组中
		}
		var keyIds = ids.join(",");

		$.messager.confirm('提示', '您确定修改[' + selRow.length + ']条状态吗?', function(r) {
			if (r) {
				$.ajax({
					url : $ctx + "/menumng/updateMenuStat.do",
					type : "post",
					data : {
						"menuIds" : keyIds,
						"menuStat" : menuStat
					},
					success : function(e) {
						$dg.datagrid('reload');
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

/**
 * 查询菜单
 * 
 * @param event
 * @param treeId
 * @param treeNode
 * @returns {Boolean}
 */
function query(event, treeId, treeNode) {
	var menuId = treeNode.id;
	var function_name = treeNode.name;
	var queryParams = {
		menuId : menuId
	};
	$parent_cd = menuId;
	$dg.datagrid('options').queryParams = queryParams;
	$dg.datagrid('load');
	$dg.datagrid('clearSelections');
	return true;
}

/**
 * 表格内操作按钮
 */
function getstr(value, row, index) {
	var s1;
	s1 = '<a href="javascript:void(0)" title="修改" class="opr-btn icon-edit" onclick="updateMenu('
			+ index + ')"></a> ';
	return s1;
}

/**
 * 菜单列表
 */
function createMyTable() {
	$dg.datagrid({
		nowrap : true,// 是否只显示一行，即文本过多是否省略部分。
		striped : true,
		url : $ctx + '/menumng/listMenu.do', // action地址
		onLoadSuccess : function(e) {
		},
		onLoadError : function(e) {
			getAjaxError(e);
		},
		idField : 'menuId',
		fitColumns : false,
		fit : true,
		checkbox : true,
		rownumbers : true,
		singleSelect : false,
		pagination : false, // 包含分页
		frozenColumns : [ [ {
			field : 'ck',
			checkbox : true
		} ] ],
		columns : [ [// 显示的列
		{
			field : 'menuId',
			title : '菜单编码'
		}, {
			field : 'menuPid',
			title : '父菜单编码',
			width : 140
		}, {
			field : 'menuName',
			title : '菜单名称'
		}, {
			field : 'orderNum',
			title : '顺序号',
			width : 60
		}, {
			field : 'menuType',
			title : '菜单类型',
			width : 70,
			formatter : function(value, row, index) {
				switch (value) {
				case '0':
					return '菜单';
				case '1':
					return '功能';
				case '2':
					return '面板';
				}
			}
		}, {
			field : 'menuPanal',
			title : '面板'
		}, {
			field : 'menuStat',
			title : '状态',
			width : 35,
			formatter : function(value, row, index) {
				switch (value) {
				case 'Y':
					return '启用';
				case 'N':
					return '禁用';
				}
			}
		}, {
			field : 'menuIcon',
			title : '图片图标',
			width : 120,
		}, {
			field : 'menuUrl',
			title : 'URL'
		}, {
			field : 'aaaa',
			title : '操作',
			width : 60,
			align : 'center',
			formatter : getstr
		} ] ],
		toolbar : '#mytable-buttons'
	});
}

$(function() {
	$dg = $('#mytable');
	$menuTree = null;
	$parent_cd = "-1";

	// 加载权限树
	refreshOrgTree();

	// 创建数据列表
	createMyTable();
});