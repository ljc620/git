$(function() {
	$myTable = $('#mytable');
	//var orgId = $('#orgId').val();
	createOrgTable();
	

});
function createOrgTable() {
	$myTable.datagrid({
		nowrap : true,// 是否只显示一行，即文本过多是否省略部分。
		striped : true,
		url : $ctx + '/blackListMng/listBlackList.do', // controller地址
		onLoadSuccess : function(e) {
		},
		onLoadError : function(e) {
			getAjaxError(e);
		},
		idField : 'blackListId',
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
			field : 'cardType',
			title : '类型',
			width : 120,
			formatter : getCardType,
			editor : "text",
			align : 'left'
				
		}, {
			field : 'ticketId',
			title : '票号/员工卡',
			width : 120,
			editor : "text",
			align : 'left'
		}, {
			field : 'lossDt',
			title : '挂失日期',
			width : 120,
			editor : "text",
			align : 'center'
		}, {
			field : 'lossReason',
			title : '挂失原因',
			width : 400,
			editor : "text",
			align : 'center'
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
		}] ],
		toolbar : '#mytable-buttons'
	});

}

function getCardType(value, row, index) {
	if("1"==value){
		return "员工卡";
	}
	else if("2"==value){
		return "门票";
	}
	else{
		return "";
	}
}
function myQuery() {
	var ticketId = $('#ticketId').textbox('getValue');

	var queryParams = {
		'ticketId' : ticketId
	}
	$myTable.datagrid('options').queryParams = queryParams;
	$myTable.datagrid('load');
	clsCheck('#mytable');
	return true;
}


/**
 * 添加黑名单
 */

function addBlackList() {
	$("#save-button").show();
    $("#update-button").hide();
var url = $ctx + '/pages/sys/blackListMng/addBlackList.jsp';
var iframe='<iframe id="blackIframe" src='+url+' frameborder="0" width="790px" height="550px"></iframe>'
	$("#blackDialog").dialog({
		title:"新增黑名单",
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

/**
 * 保存
 * @param url
 * @returns {Boolean}
 */
function saveBlack(url) {
	
	
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

					$('#blackDialog').dialog('close');
					$('#mytable').datagrid('reload'); // 重载行
					$('#mytable').datagrid('clearSelections');
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
			var id = selRow[i].blackListId; // OTRECORDID这个是你要在列表中取的单个id
			if(selRow[i].cardType=='1'){
				alert("员工卡-【"+selRow[i].ticketId+"】不能启用或禁用！");
				return false;
			}
			ids.push("'" + id + "'"); // 然后把单个id循环放到ids的数组中
		}
		var keyIds = ids.join(",");

		$.messager.confirm('提示', '您确定修改[' + selRow.length + ']条状态吗?', function(r) {
			if (r) {
				$.ajax({
					url : $ctx + "/blackListMng/updateStat.do",
					type : "post",
					data : {
						"blackListId" : keyIds,
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





