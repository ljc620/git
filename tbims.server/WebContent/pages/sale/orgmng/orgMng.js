$(function() {
	$myTable = $('#mytable');
	createOrgTable();
});
function createOrgTable() {
	$myTable.datagrid({
		nowrap : true,// 是否只显示一行，即文本过多是否省略部分。
		striped : true,
		url : $ctx + '/orgMng/listOrg.do?orgBtype=org', // controller地址
		onLoadSuccess : function(e) {
		},
		onLoadError : function(e) {
			getAjaxError(e);
		},
		idField : 'orgId',
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
			field : 'orgId',
			title : '组织机构代码',
			editor : "text",
			align : 'center'
		}, {
			field : 'orgName',
			title : '机构名称',
			editor : "text",
			align : 'left'
		}, {
			field : 'location',
			title : '机构所在地',
			width : 120,
			editor : "text",
			align : 'center'
		}, {
			field : 'legal',
			title : '法人代表',
			width : 100,
			editor : "text",
			align : 'center'
		}, {
			field : 'contact',
			title : '联系人',
			editor : "text",
			align : 'center',
			width : 100
		}, {
			field : 'tel',
			title : '联系电话',
			editor : "text",
			align : 'center',
			width : 150
		} , {
			field : 'limit',
			title : '额度',
			align : 'center',
			halign: 'center',
			width : 80,
			formatter : getLimit
		} , {
			field : 'advanceAmt',
			title : '预付款余额',
			align : 'right',
			halign: 'center',
			width : 100,
			formatter : getAdvance
		}, {
			field : 'advanceFrozeAmt',
			title : '预付款冻结金额',
			halign: 'center',
			align : 'right',
			width : 100
		}, {
			field : 'orgStat',
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
			field : 'caozuo',
			title : '操作',
			align : 'center',
			width : 100,
			formatter : getstr
		}] ],
		toolbar : '#mytable-buttons'
	});

}
function getLimit(value, row, index) {
	var s1;
	s1 = '<span class="datagrida"><a href="javascript:void(0)" title="额度详情"  onclick="getLimitInfo(' + index + ')">额度详情</a></span> ';
	return s1;
}
function getLimitInfo(index){
	var rowData = $myTable.datagrid("getRows")[index];
	var orgId = rowData.orgId;
	var url=$ctx + "/orgMng/beforeListLimitAmt.do?orgId="+orgId+"&flag=o";
	top.addTab("额度详情",url);
}
function getAdvance(value, row, index) {
	var selRow = $myTable.datagrid('getRows')[index];
	var s1;
	s1 = '<span class="datagrida"><a href="javascript:void(0)" title="预付款详情"   onclick="getAdvanceInfo(' + index + ')">'+value+'</a></span> ';
	return s1;
}
function getAdvanceInfo(index){
	var rowData = $myTable.datagrid("getRows")[index];
	var orgId = rowData.orgId;
	var url=$ctx + "/orgMng/beforeListAdvance.do?orgId="+orgId+"&flag=o";
	top.addTab("预付款详情",url);
}
function getstr(value, row, index) {
	var selRow = $myTable.datagrid('getRows')[index];
	var s1//, s2;
	s1 = '<a href="javascript:void(0)" title="修改" class="opr-btn icon-edit" onclick="updateOrg(' + index + ')"></a> ';
	//s2 = '<a href="javascript:void(0)" title="删除" class="opr-btn icon-del" onclick="delOrg(' + index + ')"></a> ';
	return s1 //+ s2;
}


function myQuery() {
	var orgName = $('#qorgName').textbox('getValue');

	var queryParams = {
		'orgName' : orgName
	}
	$myTable.datagrid('options').queryParams = queryParams;
	$myTable.datagrid('load');
	clsCheck('#mytable');
	return true;
}


/**
 * 添加签约社
 */

function addOrg() {
	$("#save-button").show();
    $("#update-button").hide();
    var url = $ctx + '/pages/sale/orgmng/addOrg.jsp';
	$("#orgDialog").dialog({
		title:"新增签约社",
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
 * 修改签约社
 * @param index
 */
function updateOrg(index) {
	$("#save-button").hide();
	$("#update-button").show();
	var rowData = $myTable.datagrid("getRows")[index];
	var orgId = rowData.orgId;
	var url = $ctx + '/orgMng/beforeUpdteOrg.do?orgId='+orgId+'&orgBtype=org';
		$("#orgDialog").dialog({
			title:"修改签约社",
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
 * 保存签约社
 * @returns {Boolean}
 */
function saveOrg(url) {
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
					$('#orgDialog').dialog('close');
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
function delOrg(index){
	var rowData = $myTable.datagrid("getRows")[index];
	var orgId = rowData.orgId;
	$.messager.confirm('提示', '您确定要删除[' + rowData.orgName + ']记录吗?', function(r) {
		if (r) {
			$.ajax({
				url : $ctx + "/orgMng/delOrg.do",
				type : "post",
				data : {orgId:orgId},
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
function updateStat(orgStat) {
	var selRow = $myTable.datagrid("getSelections"); // 返回选中多行
	var sttText = '';
	if (orgStat == 'Y') {
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
			var id = selRow[i].orgId; // OTRECORDID这个是你要在列表中取的单个id
			ids.push("'" + id + "'"); // 然后把单个id循环放到ids的数组中
		}
		var keyIds = ids.join(",");

		$.messager.confirm('提示', '您确定修改[' + selRow.length + ']条状态吗?', function(r) {
			if (r) {
				$.ajax({
					url : $ctx + "/orgMng/updateStat.do",
					type : "post",
					data : {
						"orgId" : keyIds,
						"orgStat" : orgStat
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