$(function() {
	$dg = $('#mytable');
	$roleTable = $('#roleTable');
	createUsertable();
	var queryParams={};
	createRoletable(queryParams);
	initCombo();
});

/**
 * 初始化角色列表
 */
function createRoletable(queryParams) {
	$roleTable.datagrid({
		nowrap : true,// 是否只显示一行，即文本过多是否省略部分。
		striped : true,
		url : $ctx + '/comm/listRole.do', // controller地址
		onLoadSuccess : function(e) {
		},
		onLoadError : function(e) {
			getAjaxError(e);
		},
		idField : 'roleId',
		fitColumns : false,
		fit : true,
		checkbox : true,
		rownumbers : true,
		queryParams:queryParams,
		singleSelect : false,
		frozenColumns : [ [ {
			field : 'ck',
			checkbox : true
		} ] ],

		columns : [ [// 显示的列
		{
			field : 'roleId',
			title : '角色代码',
			width : 120,
			editor : "text",
			align : 'center'
		}, {
			field : 'roleName',
			title : '角色名称',
			width : 120,
			editor : "text",
			align : 'center'
		}, {
			field : 'roleType',
			title : '角色类型',
			editor : "text",
			align : 'center',
			width : 150,
			formatter : function(value, row, index) {
				if (value == '0') {
					value = '内部角色';
				} else if (value == '1') {
					value = '外部角色';
				}
				return value;
			}
		} ] ]
	});

}

/**
 * 初始化查询表单
 */
function initCombo() {
	$('#q_role_id').combobox({
		valueField : 'roleId',
		textField : 'roleName',
		editable : false,
		width : 150,
		url : $ctx + '/comm/listRole.do',
		loadFilter : function(data) {
			var roleLists = [ {
				roleId : '',
				roleName : '--全部--',
				roleType : ''
			} ];

			return $.merge(roleLists, data);
		},
		onLoadError : function(e) {
			getAjaxError(e);
		}
	});

	$('#q_user_stat').combobox({
		valueField : 'id',
		textField : 'text',
		editable : false,
		panelHeight : 'auto',
		width : 150,
		data : [ {
			'id' : '',
			'text' : '--全部--'
		}, {
			'id' : 'Y',
			'text' : '启用'
		}, {
			'id' : 'N',
			'text' : '禁用'
		} ]
	});
	//岗位
	$('#qpositionCode').combobox({
		url : $ctx + '/comm/listItemsByKey.do?key=POSITION_CODE',
		method : 'get',
		valueField : 'id',
		textField : 'text',
		multiple : false,
		editable : false,
		panelHeight:'auto',
		loadFilter : function(data) {
			var roleLists = [ {
				id : '',
				text : '--全部--' 
			} ];
			return $.merge(roleLists, data);
		},
		onChange:function(newValue, oldValue){
			if(newValue=='01'){
				$('#qoutletId').combobox("clear");	
			} 
		}
	});
}

/**
 * 修改删除操作
 * 
 * @param value
 * @param row
 * @param index
 * @returns {String}
 */
function getstr(value, row, index) {
	var s1, s2;
	s1 = '<span class="datagrida"><a href="javascript:void(0)" title="修改用户" class="opr-btn icon-edit" onclick="openUserUpdateWin('
			+ index + ')"></a> ';
	s2 = '<a href="javascript:void(0)" title="设置角色"  class="opr-btn icon-set"  onclick="openRoleWin1('
		+ index + ')"></a>';
	return s1+s2;
}

/**
 * 初始化用户列表
 */
function createUsertable() {
	$dg.datagrid({
		nowrap : true,// 是否只显示一行，即文本过多是否省略部分。
		striped : true,
		url : $ctx + '/usermng/listUser.do', // controller地址
		onLoadSuccess : function(e) {
		},
		onLoadError : function(e) {
			getAjaxError(e);
		},
		idField : 'userId',
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
			field : 'userId',
			title : '用户账号',
			width : 120,
			align : 'center'
		}, {
			field : 'userName',
			title : '用户姓名',
			width : 150,
			align : 'center'
		}, {
			field : 'positionCode',
			title : '岗位',
			width : 120,
			align : 'center',
			formatter : function(value, row, index) {
				if (value == '01') {
					value = '票务中心';
				} else if (value == '02') {
					value = '网点管理岗';
				} else if (value == '03') {
					value = '网点操作岗';
				}
				return value;
			}
		}, {
			field : 'outletName',
			title : '所属网点',
			align : 'center'
		}, {
			field : 'userStat',
			title : '状态',
			width : 80,
			align : 'center',
			formatter : function(value, row, index) {
				if (value == 'Y') {
					value = '启用';
				} else if (value == 'N') {
					value = '停用';
				}
				return value;
			}
		}, {
			field : 'department',
			title : '部门',
			width : 150,
			align : 'center'
		}, {
			field : 'roleNames',
			title : '所属角色',
			align : 'center'
		}, {
			field : 'tel',
			title : '联系电话',
			width : 100,
			align : 'center'
		}, {
			field : 'caozuo',
			title : '操作',
			align : 'center',
			halign : 'center',
			formatter : getstr,
			width : 90
		} ] ],
		toolbar : '#mytable-buttons'
	});
}

/**
 * 查询用户
 * 
 * @returns {Boolean}
 */
function queryUser() {
	var userId = $('#q_user_id').textbox('getValue');
	var userName = $('#q_user_name').textbox('getValue');
	var roleId = $('#q_role_id').combobox('getValue');
	var userStat = $('#q_user_stat').combobox('getValue');
	var outletId = $('#qoutletId').combobox('getValue');
	var positionCode = $('#qpositionCode').combobox('getValue');

	var queryParams = {
		'userId' : userId,
		'userName' : userName,
		'roleId' : roleId,
		'userStat' : userStat,
		'outletId' :outletId,
		'positionCode':positionCode
	}

	$dg.datagrid('options').queryParams = queryParams;
	$dg.datagrid('load');
	clsCheck('#mytable');
	return true;
}

/**
 * 打开角色窗口
 * 
 * @returns {Boolean}
 */
function openRoleWin() {
	clsCheck('#roleTable');

	var ids = [];
	var selRow = $dg.datagrid("getSelections"); // 返回选中多行
	if (selRow.length == 0) {
		alert("请至少选择一行数据!");
		return false;
	}
	$("#addRoleDlg").dialog({
		title : "设置角色",
		modal : true,
		buttons : "#dlg-buttons"
	}).dialog("open");

}
/**
 * 打开角色窗口
 * 
 * @returns {Boolean}
 */
function openRoleWin1(index) {
	clsCheck('#roleTable');
	var rowData = $dg.datagrid("getRows")[index];
	var url=$ctx + "/usermng/setRoleBef.do?userId="+rowData.userId;
	var iframe='<iframe id="roleIframe" src='+url+' frameborder="0" width="790px" height="550px"></iframe>'
	$("#addRoleDlg1").dialog({
		title:"设置角色",
		resizable:true,
		inline:true,
		content:iframe,
		top:5,
		closed: false,
		cache: false,
		modal: true,
		onclose:function(){$("#roleIframe").remove();}
	}).dialog("open");
}
/**
 * 设置角色
 * 
 * @returns {Boolean}
 */
function setRoleToUser() {
	// 获取角色
	var selRoleRow = $roleTable.datagrid("getSelections"); // 返回选中多行
	if (selRoleRow.length == 0) {
		$.messager.alert('提示', '请选择一种角色', 'info');
		return false;
	}

	var roleCds = [];

	for (var i = 0; i < selRoleRow.length; i++) {
		// 获取自定义table 的中的checkbox值
		var roleId = selRoleRow[i].roleId; // OTRECORDID这个是你要在列表中取的单个id
		roleCds.push(roleId); // 然后把单个id循环放到ids的数组中
	}

	// 获取用户
	var ids = [];
	var selRow = $dg.datagrid("getSelections"); // 返回选中多行

	for (var i = 0; i < selRow.length; i++) {
		// 获取自定义table 的中的checkbox值
		var userId = selRow[i].userId; // OTRECORDID这个是你要在列表中取的单个id
		ids.push(userId); // 然后把单个id循环放到ids的数组中
	}
	var keyIds = ids.join(",");
	$.messager.confirm('提示', '您确定要设置【' + ids.length + '】位用户的角色吗?', function(r) {
		if (r) {
			$.ajax({
				url : $ctx + "/usermng/setRoleToUser.do",
				type : "post",
				data : {
					"userCds" : keyIds,
					"roleCds" : roleCds.join(",")
				},
				success : function(e) {
					$("#addRoleDlg").dialog("close");
					$dg.datagrid("reload");
					$dg.datagrid('clearSelections');
					$.messager.progress("close");
					$.messager.show({
						showType : 'slide',
						title : "提示信息",
						msg : "设置成功",
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

/**
 * 修改用户状态
 * 
 * @returns {Boolean}
 */
function updateUserStatus(user_status) {
	var selRow = $dg.datagrid("getSelections"); // 返回选中多行
	var sttText = '';
	if (user_status == 'Y') {
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
			var id = selRow[i].userId; // OTRECORDID这个是你要在列表中取的单个id
			ids.push("'" + id + "'"); // 然后把单个id循环放到ids的数组中
		}
		var keyIds = ids.join(",");

		$.messager.confirm('提示', '您确定要' + sttText + '[' + selRow.length
				+ ']位用户吗?', function(r) {
			if (r) {
				$.ajax({
					url : $ctx + "/usermng/updateUserStat.do",
					type : "post",
					data : {
						"userIds" : keyIds,
						"userStat" : user_status
					},
					success : function(e) {
						$dg.datagrid("reload");
						$dg.datagrid('clearSelections');
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

/**
 * 密码重置
 * 
 * @returns {Boolean}
 */
function passReSet() {
	var selRow = $dg.datagrid("getSelections"); // 返回选中多行
	if (selRow.length == 0) {
		alert("请至少选择一行数据!");
		return false;
	} else {
		var ids = [];
		for (var i = 0; i < selRow.length; i++) {
			// 获取自定义table 的中的checkbox值
			var id = selRow[i].userId; // OTRECORDID这个是你要在列表中取的单个id
			ids.push(id);//ids.push("'" + id + "'"); // 然后把单个id循环放到ids的数组中
		}
		//var keyIds = ids.join(",");

		$.messager.confirm('提示', '您确定要密码重置[' + selRow.length + ']位用户吗?',
				function(r) {
					if (r) {
						$.ajax({
							url : $ctx + "/usermng/passReSet.do",
							type : "post",
							data : {
								"userIds" : ids.toString()//keyIds
							},
							success : function(e) {
								$dg.datagrid("reload");
								$dg.datagrid('clearSelections');
								$.messager.show({
									showType : 'slide',
									title : "提示信息",
									msg : "重置成功",
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
 * 打开用户添加窗口
 */
function openUserAddWin() {
	$("#save-user-button").show();
	$("#update-user-button").hide();

	var url = $ctx + '/pages/sys/usermng/addUser.jsp';

	$("#userDialog").dialog({
		title : "新增用户",
		resizable : true,
		inline : true,
		cache : true,
		modal : true,
		href : url,
		buttons : "#dlg-user-buttons",
		onLoadError : function(e) {
			getAjaxError(e);
		}
	}).dialog("open");

}

/**
 * 打开用户修改窗口
 */
function openUserUpdateWin(index) {
	$("#save-user-button").hide();
	$("#update-user-button").show();

	var rowData = $dg.datagrid("getRows")[index];
	var url = $ctx + '/usermng/findByUserCd.do?userId=' + rowData.userId;

	$("#userDialog").dialog({
		title : "修改用户",
		resizable : true,
		inline : true,
		cache : true,
		modal : true,
		href : url,
		buttons : "#dlg-user-buttons",
		onLoadError : function(e) {
			getAjaxError(e);
		}
	}).dialog("open");

}

/**
 * 保存用户
 * 
 * @param url
 * @returns {Boolean}
 */
function saveUser(url) {
	if (!$('#fm').form('validate')) {
		return false;
	}
	$.messager.confirm('提示', '确定提交吗？', function(r) {
		if (r) {
			$.messager.progress({
				text : '正在处理,请稍候...'
			});
			$('#fm').form('submit', {
				url : $ctx + url,
				onSubmit : function() {
				},
				success : function(e) {
					$.messager.progress("close");
					if (!getFormError(e)) {
						return false;
					}
					$('#userDialog').dialog('close');
					$dg.datagrid('reload'); // 重载行
					$dg.datagrid('clearSelections');
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

/**
 * 密码重置
 * 
 * @returns {Boolean}
 */
function deleteUser() {
	var selRow = $dg.datagrid("getSelections"); // 返回选中多行
	if (selRow.length == 0) {
		alert("请至少选择一行数据!");
		return false;
	} else {
		var ids = [];
		for (var i = 0; i < selRow.length; i++) {
			// 获取自定义table 的中的checkbox值
			var id = selRow[i].userId; // OTRECORDID这个是你要在列表中取的单个id
			ids.push("'" + id + "'"); // 然后把单个id循环放到ids的数组中
		}
		var keyIds = ids.join(",");

		$.messager.confirm('提示', '您确定要删除[' + selRow.length + ']位用户吗?',
				function(r) {
					if (r) {
						$.ajax({
							url : $ctx + "/usermng/deleteUser.do",
							type : "post",
							data : {
								"userIds" : keyIds
							},
							success : function(e) {
								$dg.datagrid("reload");
								$dg.datagrid('clearSelections');
								$.messager.show({
									showType : 'slide',
									title : "提示信息",
									msg : "重置成功",
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
