$(function() {
	$myTable = $('#chesttable');
	var batchId = $('#batchId').val();
	var par = {
		batchId : batchId
	};

	createChestTable(par);
});

var submitFlag = false;
function uploadxml() {
	if (submitFlag) {
		alert("正在处理，请不要重复提交,再次上传请刷新！");
		return;
	}
	$.messager.confirm('提示', '上传后将清空未提交入库的批次,确定上传文件吗?<br\>单击确定后请等待...', function(r) {
		if (r) {
			$.messager.progress({
				text : '正在处理,请稍候...'
			});
			var url = $ctx + "/ticket/loadDataFromXML.do";
			$('#fm').attr("action", url).submit();
			submitFlag = true;
		}
	});
}

function createChestTable(params) {
	$myTable.datagrid({
		nowrap : true,// 是否只显示一行，即文本过多是否省略部分。
		url : $ctx + '/ticket/listChest.do', // controller地址
		onLoadSuccess : function(e) {
			parent.$.messager.progress("close");
			if (e.rows.length > 0) {
				$('#batchId').textbox("setValue", e.rows[0].batchId);
			}
		},
		onLoadError : function(e) {
			getAjaxError(e);
			parent.$.messager.progress("close");
		},
		idField : 'chestId',
		fitColumns : false,
		rownumbers : true,
		singleSelect : true,
		striped : true,
		fit : true,
		toolbar : '#mytable-buttons',
		columns : [ [// 显示的列
		{
			field : 'chestId',
			title : '箱号',
			width : 120,
			align : 'center'
		}, {
			field : 'ticketTypeId',
			title : '门票种类',
			hidden : true,
			align : 'center'
		}, {
			field : 'ticketTypeName',
			title : '门票种类',
			width : 120,
			align : 'center'
		}, {
			field : 'boxNum',
			title : '盒数量',
			width : 120,
			align : 'center'
		}, {
			field : 'ticketNum',
			title : '票数量',
			width : 120,
			align : 'center'
		}, {
			field : 'stat',
			title : '状态',
			width : 120,
			align : 'center',
			formatter : getExamStat
		} ] ]
	});
}

function getExamStat(value, row, index) {
	var s1 = '';
	var selRow = $myTable.datagrid('getRows')[index];
	var examStat = selRow.stat;
	if ('Y' == examStat) {
		s1 = "已核实";
	} else if ('N' == examStat) {
		s1 = "未核实";
	} else if ('001' == examStat) {
		s1 = "已入库";
	} else if ('002' == examStat) {
		s1 = "配送中";
	} else if ('003' == examStat) {
		s1 = "已接收";
	}
	return s1;
}

function mySave() {
	if (!$('#fm1').form('validate')) {
		return false;
	}
	var batchId = $('#batchId').textbox('getValue');
	$.ajax({
		url : $ctx + "/ticket/getChestTmpNum.do",
		data : {
			batchId : batchId
		},
		type : "post",
		success : function(e) {
			var msg = "";
			if (e > 0) {
				msg = '您有' + e + '箱未核实，是否继续提交？';
			} else {

				msg = '所有箱都已核实，是否继续提交？';
			}
			parent.$.messager.confirm('提示', msg, function(r) {
				if (r) {
					parent.$.messager.progress({
						width:400,
						text : '正在处理,请勿关闭当前页面再次提交,请稍候...'
					});
					formUrl = "/ticket/saveInfo.do";
					$('#fm1').form('submit', {
						url : $ctx + formUrl,
						success : function(e) {
							parent.$.messager.progress("close");
							if (!getFormError1(e)) {
								return false;
							}
							$('#batchId').textbox("setValue", "");
							$('#chestIds').textbox("setValue", "");
							var queryParams = {
								'batchId' : batchId
							}
							$myTable.datagrid('options').queryParams = queryParams;
							$myTable.datagrid('load');
							parent.$.messager.show({
								showType : 'slide',
								title : "提示信息",
								msg : '保存成功',
								timeout : 2000
							});
						}
					}, 'json');
				}
			});
		},
		error : function(e) {
			getAjaxError1(e);
		}
	});
}

/**
 * 核对数据
 */
function checkChest() {
	var chestIds = $('#chestIds').textbox('getValue');
	var batchId = $('#batchId').textbox('getValue');
	if ("" == chestIds) {
		parent.$.messager.alert('系统提示', '请扫描或录入箱号');
		return false;
	}
	if ("" == batchId) {
		parent.$.messager.alert('系统提示', '请输入批次号');
		return false;
	}
	parent.$.ajax({
		url : $ctx + "/ticket/checkChest.do",
		data : {
			chestIds : chestIds,
			batchId : batchId
		},
		type : "post",
		success : function(e) {
			var queryParams = {
				'batchId' : batchId
			}

			$myTable.datagrid('options').queryParams = queryParams;
			$myTable.datagrid('load');
			parent.$.messager.show({
				showType : 'slide',
				title : "提示信息",
				msg : '核对成功',
				timeout : 2000
			});
		},
		error : function(e) {
			getAjaxError1(e);
		}
	});
}

function getAjaxError1(result) {
	try {
		var resultJSON = parent.$.parseJSON(result.responseText);
		alert(resultJSON.errorDescribe);
	} catch (e) {
		alert(result.responseText);
		return false;
	}
	return true;
}

function getFormError1(result) {
	try {
		// 后台处理成功，返回值 void
		if (result == '') {
			return true;
		}

		// 后台处理失败，返回值为json 错误字符串
		var resultJSON = parent.$.parseJSON(result);
		if (resultJSON.errorCode != '0') {
			parent.$.messager.alert("错误", resultJSON.errorDescribe);
			return false;
		}

	} catch (e) {
		$.messager.alert("错误", e.description);
		return false;
	}

	return true;
}

function deleteChestTmp() {
	var batchId = $('#batchId').val();
	if (batchId.length == 0) {
		parent.$.messager.alert("提示","没有未提交的入库批次!");
		return;
	}

	parent.$.messager.confirm('提示', '清空后该批次[' + batchId + "]需要重新上传并提交,确认清空吗?", function(r) {
		if (r) {
			parent.$.ajax({
				url : $ctx + "/ticket/deleteChestTmp.do",
				data : {},
				type : "post",
				success : function(e) {
					$('#batchId').textbox("setValue", "");
					var queryParams = {
						'batchId' : batchId
					}
					$myTable.datagrid('options').queryParams = queryParams;
					$myTable.datagrid('load');
					parent.$.messager.show({
						showType : 'slide',
						title : "提示信息",
						msg : '保存成功',
						timeout : 2000
					});
				},
				error : function(e) {
					getAjaxError1(e);
				}
			});
		}
	});
}