
/**
 * 保存角色
 * @param url
 * @returns {Boolean}
 */
function save(url) {
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
						var $funcRoleTree = $.fn.zTree.getZTreeObj("funcRoleTree");
						var selecfuncNodes = $funcRoleTree.getCheckedNodes(true);
						var funcs = [];
						for ( var i = 0; i < selecfuncNodes.length; i++) {
							funcs.push(selecfuncNodes[i].id);
						}
						$("#funcKeys").val(funcs.join(","));					
					},
					success : function(e) {
						$.messager.progress("close");
						if(!getFormError(e)){
							return false;
						}
						$('#roleDialog').dialog('close');
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
 * 增加角色
 */
function addRole(obj) {
	// 加载权限树
	$("#save-button").show();
	$("#update-button").hide();
	$('#roleDialog').dialog({
		title : "新增角色",
		cache : false,
		modal : true,
		href :$ctx+"/pages/sys/rolemng/addRole.jsp",
		buttons : "#dlg-buttons",
		onLoad:function(){
			var roleTypeDesc="";
			if (obj == '0') {
				roleTypeDesc = '内部角色';
			} else if (obj == '1') {
				roleTypeDesc = '外部角色';
			}
			$('#roleTypeDesc').html(roleTypeDesc);
			$('#roleType').val(obj);
			refreshTreeFuncRole(null,obj);
		}
	});

	$('#roleDialog').dialog("open");
}

/**
 * 更新角色
 * 
 * @param id
 * @returns {Boolean}
 */
function updateRole(index) {
	var rowData = $dg.datagrid("getRows")[index];
	var roleId = rowData.roleId;
    var roleType = rowData.roleType;
	$("#save-button").hide();
	$("#update-button").show();
	
	$('#roleDialog').dialog({
		title : "修改角色",
		method:"post",
		href : $ctx+"/rolemng/findByRoleId.do",
		queryParams:{
			'roleId':roleId,
			'roleType':roleType
		},
		cache : false,
		modal : true,
		buttons : "#dlg-buttons",
		onLoad:function(){
			refreshTreeFuncRole(roleId,roleType);
		},
		onLoadError:function(e){
			getAjaxError(e);
		}
	});

	$('#roleDialog').dialog("open");

}

/**
 * 权限树
 */
function refreshTreeFuncRole(roleId,roleType) {
	var settingTreeFuncUse = {
		data : {
			simpleData : {
				enable : true
			}
		},
		view : {
			showLine : true
		},
		check : {
			enable : true,
			chkboxType : {
				"Y" : "ps",
				"N" : "s"
			}
		}
	};

	$.ajax({
		url : $ctx + '/rolemng/treeFuncRole.do',
		data : {'roleId':roleId,'roleType':roleType},
		type : "post",
		success : function(data) {
			$.fn.zTree.init($("#funcRoleTree"), settingTreeFuncUse, data);
		},
		error : function(e) {
			getAjaxError(e);
		} 
	});
}

/**
 * 删除角色
 * 
 * @returns {Boolean}
 */
function deleteRole(index) {
	var rowData = $dg.datagrid("getRows")[index];
	var roleId = rowData.roleId;
	$.messager.confirm('提示', '您确定要删除[' + roleId + ']吗?', function(r) {
		if (r) {
			$.ajax({
				url : $ctx + "/rolemng/deleteByRoleCds.do",
				type : "post",
				data : "roleId=" + roleId,
				success : function(e) {
					$.messager.show({
						showType : 'slide',
						title : "提示信息",
						msg : '删除成功',
						timeout : 2000
					});
					$dg.datagrid("reload");
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
 * 精确搜索
 */
function queryRole() {
	var q_role_cd = $("#q_role_cd").textbox("getValue");
	var q_role_nm = $("#q_role_nm").textbox("getValue"); 

	var queryParams = {
		"roleId" : q_role_cd,
		"roleName" : q_role_nm
	};
	$dg.datagrid('options').queryParams = queryParams;
	$dg.datagrid('load');
	clsCheck("#mytable");// 清空选择的行
	return true;
}

/**
 * 表格内操作按钮
 */
function getstr(value, row, index) {
	var s1, s2;
	s1 = '<a href="javascript:void(0)" title="修改" class="opr-btn icon-edit" onclick="updateRole(' + index + ')"></a> ';
	s2 = '<a href="javascript:void(0)" title="删除" class="opr-btn icon-del" onclick="deleteRole(' + index + ')"></a> ';
	return s1 + s2;
}

/**
 * 初始化列表
 */
function createMytable() {
	$dg.datagrid({
		nowrap : true,// 是否只显示一行，即文本过多是否省略部分。
		striped : true,
		url : $ctx + '/rolemng/listRole.do', // controller地址
		onLoadSuccess : function(e) {
		},
		onLoadError : function(e) {
			getAjaxError(e);
		},
		idField : 'roleId',
		fitColumns : false,
		fit : true,
		rownumbers : true,
		pagination : true, // 包含分页
		pageSize : getPageSize(),
		pageList : getPageList(), 
		columns : [ [// 显示的列
		{
			field : 'roleId',
			title : '角色代码',
			width : 200,
			align:'center'
		},{
			field : 'roleType',
			title : '角色类型',
			width : 80,
			align:'center',
			formatter : function(value, row, index) {
				if (value == '0') {
					return '内部角色';
				} else if (value == '1') {
					return '外部角色';
				}
			}
		},  {
			field : 'roleName',
			title : '角色名称',
			width : 250,
			align:'center'
		}, {
			field : 'remark',
			title : '角色说明',
			align:'center'
		}, {
			field : 'opt',
			title : '操作',
			width : 120,
			align : 'center',
			formatter:getstr
		}] ],
		toolbar : '#mytable-buttons'
	});

}

$(function() {
	$dg = $('#mytable');

	// 创建树数据列表
	createMytable();
}); 