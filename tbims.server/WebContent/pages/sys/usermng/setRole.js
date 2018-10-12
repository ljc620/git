$(function() {
	$roleTable = $('#roleTable1');
	var queryParams={userId:$("#roleUserId").val()};
	createRoletable(queryParams);
});
/**
 * 初始化角色列表
 */
function createRoletable(queryParams) {
	$roleTable.datagrid({
		nowrap : true,// 是否只显示一行，即文本过多是否省略部分。
		striped : true,
		url : $ctx + '/comm/listRole.do', // controller地址
		onLoadSuccess : function(data) {
			if(data){
				$.each(data.rows,function(index,item){
					if(item.checked){
						$roleTable.datagrid('checkRow',index);
					}
					});
		}},
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
function setRoleToUser() {
	// 获取角色
	var selRoleRow = $roleTable.datagrid("getChecked"); // 返回选中多行
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

		var userId =$("#roleUserId").val(); // OTRECORDID这个是你要在列表中取的单个id
		ids.push(userId); // 然后把单个id循环放到ids的数组中
	var keyIds = ids.join(",");
	parent.$.messager.confirm('提示', '您确定要设置【' + userId + '】用户的角色吗?', function(r) {
		if (r) {
			$.ajax({
				url : $ctx + "/usermng/setRoleToUser.do",
				type : "post",
				data : {
					"userCds" : keyIds,
					"roleCds" : roleCds.join(",")
				},
				success : function(e) {
					parent.$("#addRoleDlg1").dialog("close");
					parent.$dg.datagrid("reload");
					parent.$dg.datagrid('clearSelections');
					parent.$.messager.progress("close");
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