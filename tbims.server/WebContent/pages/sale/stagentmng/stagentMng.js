$(function() {
	$myTable = $('#mytable');
	createOrgTable();
});
function createOrgTable() {
	$myTable.datagrid({
		nowrap : true,// 是否只显示一行，即文本过多是否省略部分。
		striped : true,
		url : $ctx + '/orgMng/listOrg.do?orgBtype=stagent', // controller地址
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
			field : 'orgType',
			title : '代理商类型',
			width : 100,
			editor : "text",
			align : 'center',
			formatter : function(value, row, index) {
				if (value == '1') {
					value = '网络代理商';
				} else if (value == '2') {
					value = '实体代理商';
				} 
				return value;
			}
		}, {
			field : 'location',
			title : '机构所在地',
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
		}
//		, {
//			field : 'depositAmt',
//			title : '保证金',
//			align : 'center',
//			halign: 'center',
//			width : 80,
//			formatter : getDepositAmt
//		} 
//		, {
//			field : 'creditAmt',
//			title : '信用额度',
//			align : 'right',
//			halign: 'center',
//			width : 100
//		}
		,  {
			field : 'advanceAmt',
			title : '预付款余额',
			align : 'right',
			halign: 'center',
			width : 100,
			formatter:getAdvanceAmt
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
function getDepositAmt(value, row, index) {
	var s1;
	s1 = '<span class="datagrida"><a href="javascript:void(0)" title="保证金详情"  onclick="getDepositInfo(' + index + ')">保证金详情</a> </span>';
	return s1;
}
function getAdvanceAmt(value, row, index) {
	var s1;
	s1 = '<span class="datagrida"><a href="javascript:void(0)" title="预付款详情"  onclick="getAdvanceStInfo(' + index + ')">'+value+'</a> </span>';
	return s1;
}
function getDepositInfo(index){
	var rowData = $myTable.datagrid("getRows")[index];
	var orgId = rowData.orgId;
	var url=$ctx + "/orgMng/beforeListDeposit.do?orgId="+orgId+"&type=st";
	top.addTab("实体代理商保证金",url);
}
function getAdvanceStInfo(index){
	var rowData = $myTable.datagrid("getRows")[index];
	var orgId = rowData.orgId;
	var url=$ctx + "/orgMng/beforeListAdvance.do?orgId="+orgId+"&flag=s";//实体代理商-预付款
	top.addTab("实体代理商预付款",url);
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
 * 添加代理商
 */

function addOrg() {
	$("#save-button").show();
    $("#update-button").hide();
var url = $ctx + '/pages/sale/stagentmng/addAgent.jsp';
	$("#orgDialog").dialog({
		title:"新增实体代理商",
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
 * 修改代理商
 * @param index
 */
function updateOrg(index) {
	$("#save-button").hide();
	$("#update-button").show();
	var rowData = $myTable.datagrid("getRows")[index];
	var orgId = rowData.orgId;
	var url = $ctx + '/orgMng/beforeUpdteOrg.do?orgId='+orgId+'&orgBtype=stagent';
		$("#orgDialog").dialog({
			title:"修改实体代理商",
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
 * 保存代理商
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
/**
 * 删除代理商
 * @param index
 */
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