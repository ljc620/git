$(function() {
	$myTable = $('#mytable');
	initCombobox();
	createOrgTable();
});
function createOrgTable() {
	$myTable.datagrid({
		nowrap : true,// 是否只显示一行，即文本过多是否省略部分。
		striped : true,
		url : $ctx + '/regionMng/listRegion.do', // controller地址
		onLoadSuccess : function(e) {
		},
		onLoadError : function(e) {
			getAjaxError(e);
		},
		idField : 'regionId',
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
			field : 'regionId',
			title : '区域编号',
			width : 120,
			editor : "text",
			align : 'left'
				
		}, {
			field : 'regionName',
			title : '区域名称',
			width : 120,
			editor : "text",
			align : 'left'
		} ,{
			field : 'venueId',
			title : '场馆',
			align : 'center',
			halign: 'center',
			hidden:true,
			width:100
		} ,{
			field : 'venueName',
			title : '场馆',
			align : 'center',
			halign: 'center',
			width:100
		},{
			field : 'location',
			title : '地点',
			width : 120,
			editor : "text",
			align : 'center'
		}, {
			field : 'tel',
			title : '联系电话',
			width : 120,
			editor : "text",
			align : 'center'
		}, {
			field : 'leader',
			title : '负责人',
			width : 120,
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
	var s1//, s2;
	s1 = '<a href="javascript:void(0)" title="修改" class="opr-btn icon-edit" onclick="updateRegion(' + index + ')"></a> ';
	//s2 = '<a href="javascript:void(0)" title="删除" class="opr-btn icon-del" onclick="delRegion(' + index + ')"></a> ';
	return s1// + s2;
}




/**
 * 添加检票区域
 */

function addRegion() {
	$("#save-button").show();
    $("#update-button").hide();
var url = $ctx + '/pages/sys/regionMng/addRegion.jsp';
var iframe='<iframe id="sysRegioframe" src='+url+' frameborder="0" width="790px" height="550px"></iframe>'
	$("#regionDialog").dialog({
		title:"新增检票区域",
		resizable:true,
		inline:true,
		cache : false,
		content:iframe,
		modal : true,
		href :url, 
		buttons : "#dlg-buttons",
		onclose:function(){$("#regionDialog").remove();},
	}).dialog("open");
   // $('#fm').form("clear");
}


/**
 * 删除
 * @param index
 */
function delRegion(index){
	var rowData = $myTable.datagrid("getRows")[index];
	var regionId = rowData.regionId;
	$.messager.confirm('提示', '您确定要删除[' + rowData.regionName + ']记录吗?', function(r) {
		if (r) {
			$.ajax({
				url : $ctx + "/regionMng/delRegion.do",
				type : "post",
				data : {regionId:regionId},
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
 * 
 * @param index
 */
function updateRegion(index) {
	$("#save-button").hide();
	$("#update-button").show();
	var rowData = $myTable.datagrid("getRows")[index];
	var regionId = rowData.regionId;
	var url = $ctx + '/regionMng/beforeUpdteRegion.do?regionId='+regionId;
		$("#regionDialog").dialog({
			title:"修改区域",
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

function saveRegion(url) {
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

					$('#regionDialog').dialog('close');
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
			var id = selRow[i].regionId; // OTRECORDID这个是你要在列表中取的单个id
			ids.push("'" + id + "'"); // 然后把单个id循环放到ids的数组中
		}
		var keyIds = ids.join(",");

		$.messager.confirm('提示', '您确定修改[' + selRow.length + ']条状态吗?', function(r) {
			if (r) {
				$.ajax({
					url : $ctx + "/regionMng/updateStat.do",
					type : "post",
					data : {
						"regionId" : keyIds,
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


function initCombobox(){
	//区域初始化
	$('#venueId').combobox({
		url : $ctx + '/ticketTypeMng/venueList.do',
		method : 'get',
		valueField : 'venueId',
		textField : 'venueName',
		multiple : false,
		editable : false,
		panelHeight : 'auto',
		onLoadSuccess : function(e) {
		},
		onLoadError : function(e) {
			getAjaxError(e);
		},
		loadFilter : function(data) {
			var venueLists = [ {
				venueId : '',
				venueName : '--全部--'
			} ];
			return $.merge(venueLists, data);
		}
	});
	
}


function myQuery() {
	var venueId = $('#venueId').combobox('getValue');
	var regionName = $('#regionName').textbox('getValue');
	var queryParams = {
		'regionName' : regionName,
		'venueId' : venueId
	}
	$myTable.datagrid('options').queryParams = queryParams;
	$myTable.datagrid('load');
	clsCheck('#mytable');
	return true;
}
