$(function() {
	$myTable = $('#mytable');
	createClientTable();
	initCombobox();
});
function createClientTable() {
	$myTable.datagrid({
		nowrap : true,// 是否只显示一行，即文本过多是否省略部分。
		striped : true,
		url : $ctx + '/clientMng/listClient.do', // controller地址
		onLoadSuccess : function(e) {
		},
		onLoadError : function(e) {
			getAjaxError(e);
		},
		idField : 'clientId',
		checkbox:true,
		fitColumns : false,
		rownumbers : true,
		striped : true,
		pagination : true,
		fit:true,
		frozenColumns : [ [ {
			field : 'ck',
			checkbox : true
		} ] ],
		columns : [ [// 显示的列
		{
			field : 'clientId',
			title : '终端编号',
			width : 110,
			align : 'center'
		}, {
			field : 'clientName',
			title : '终端名称',
			align : 'center'
		}, {
			field : 'itemName',
			title : '终端类型',
			width : 110,
			align : 'center',
			formatter:function(value,row,index){
				 return row.clientType+'-'+row.itemName;
			}
		}, {
			field : 'regionName',
			title : '区域名称',
			align : 'center',
			formatter:function(value,row,index){
				if(row.clientType=='4'&&row.regionId==0){
					return '票务中心';
				}else{
					return value;
				}
			}
		}, {
			field : 'outletName',
			title : '网点名称',
			align : 'center'
		},{
			field : 'stat',
			title : '状态',
			align : 'center',
			halign: 'center',
			width : 60,
			formatter:function(value,row,index){
				switch(value){
				case 'Y':
					return "启用";
					break;
				case 'N':
					return "禁用";
					break;
				default:
					return value;
				}
			}
		}, {
			field : 'token',
			title : '授权码',
			align : 'center' 
		},{
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
	var s1//, s2;
	s1 = '<a href="javascript:void(0)" title="修改" class="opr-btn icon-edit" onclick="updateClient(' + index + ')"></a> ';
	//s2 = '<a href="javascript:void(0)" title="删除" class="opr-btn icon-del" onclick="delClient(' + index + ')"></a> ';
	return s1 //+ s2;
}
function queryClient() {
	var clientName = $('#clientName').textbox('getValue');
	var clientType = $('#qclientType').combobox('getValue');
	var stat = $('#qstat').combobox('getValue');
	var outletId = $('#qoutletId').combobox('getValue');
	var regionId = $('#qregionId').combobox('getValue');

	var queryParams = {
		'clientName' : clientName,
		'clientType' : clientType,
		'stat' : stat,
		'outletId' :outletId,
		'regionId':regionId
	}
	$myTable.datagrid('options').queryParams = queryParams;
	$myTable.datagrid('load');
	clsCheck('#mytable');
	return true;
}
/**
 * 修改终端
 * @param index
 */
function updateClient(index) {
	$("#save-button").hide();
	$("#clear-button").hide();
	$("#update-button").show();
	var rowData = $myTable.datagrid("getRows")[index];
	var clientId = rowData.clientId;
	var url = $ctx + '/clientMng/beforeUpdateClient.do?clientId='+clientId;
		$("#clientDialog").dialog({
			title:"修改终端",
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
 * 添加终端
 */

function addClient() {
	$("#save-button").show();
	$("#clear-button").show();
    $("#update-button").hide();
    var url = $ctx + '/pages/sys/clientMng/addClient.jsp';
	$("#clientDialog").dialog({
		title:"新增终端",
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
 * 保存终端
 * @returns {Boolean}
 */
function saveClient(url) {
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

					$('#clientDialog').dialog('close');
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
function delClient(index){
	var rowData = $myTable.datagrid("getRows")[index];
	var clientId = rowData.clientId;
	$.messager.confirm('提示', '您确定要删除[' + rowData.clientName + ']记录吗?', function(r) {
		if (r) {
			$.ajax({
				url : $ctx + "/clientMng/delClient.do",
				type : "post",
				data : {clientId:clientId},
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
function updateStatus(status) {
	var selRow = $myTable.datagrid("getSelections"); // 返回选中多行
	var sttText = '';
	if (status == 'Y') {
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
			var id = selRow[i].clientId; // OTRECORDID这个是你要在列表中取的单个id
			ids.push("'" + id + "'"); // 然后把单个id循环放到ids的数组中
		}
		var keyIds = ids.join(",");

		$.messager.confirm('提示', '您确定要' + sttText + '[' + selRow.length
				+ ']个终端吗?', function(r) {
			if (r) {
				$.ajax({
					url : $ctx + "/clientMng/updateStat.do",
					type : "post",
					data : {
						"clientIds" : keyIds,
						"stat" : status
					},
					success : function(e) {
						$myTable.datagrid("reload");
						$myTable.datagrid('clearSelections');
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
function updateToken() {
	var selRow = $myTable.datagrid("getSelections"); // 返回选中多行
	if (selRow.length == 0) {
		alert("请至少选择一行数据!");
		return false;
	} else {
		var ids = [];
		for (var i = 0; i < selRow.length; i++) {
			if (selRow[i].clientType=='1'||selRow[i].clientType=='4') {
				alert("请确定选中的行都能重置授权码!");
				return false;
			}
			// 获取自定义table 的中的checkbox值
			var id = selRow[i].clientId; // OTRECORDID这个是你要在列表中取的单个id
			ids.push("'" + id + "'"); // 然后把单个id循环放到ids的数组中
		}
		var keyIds = ids.join(",");

		$.messager.confirm('提示', '您确定要重置' + '[' + selRow.length
				+ ']终端的授权码吗?', function(r) {
			if (r) {
				$.ajax({
					url : $ctx + "/clientMng/updateToken.do",
					type : "post",
					data : {
						"clientIds" : keyIds 
					},
					success : function(e) {
						$myTable.datagrid("reload");
						$myTable.datagrid('clearSelections');
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
function initCombobox(){
	//终端类型初始化
	$('#qclientType').combobox({
		url: $ctx+'/comm/listItemsByKey.do?key=CLIENT_TYPE',   
        method:'get',  
        valueField:'id',  
        textField:'text',  
        multiple:false,  
        editable:false,  
        panelHeight:'auto',
        loadFilter : function(data) {
			var list = [ {
				id : '',
				text : '--全部--'
			} ];
			return $.merge(list, data);
		},
		onChange:function(newValue, oldValue){
			$('#qoutletId').combobox('clear');
			$('#qregionId').combobox('clear');
			if(newValue==''){
				$('#li1').hide();
				$('#li2').hide();
				$('#li3').hide();
				$('#li4').hide();
			}
			if(newValue=='1'||newValue=='5'){
				$('#li1').show();
				$('#li2').show();
				$('#li3').hide();
				$('#li4').hide();
			}
			if(newValue=='2'||newValue=='3'||newValue=='4'){
				$('#li1').hide();
				$('#li2').hide();
				$('#li3').show();
				$('#li4').show();
				if(newValue=='2'||newValue=='3'){
					//区域
					$('#qregionId').combobox({
						url: $ctx+'/comm/listRegion.do',   
				        method:'get',  
				        valueField:'regionId',  
				        textField:'regionName',  
				        panelHeight:'auto' ,
				        editable:false,
				        loadFilter : function(data) {
							var list = [ {
								regionId : '',
								regionName : '--全部--'
							} ];
							return $.merge(list, data);
						}
					});
				}
				if(newValue=='4'){
					//区域
					$('#qregionId').combobox({
						url: $ctx+'/comm/listRegion.do',   
				        method:'get',  
				        valueField:'regionId',  
				        textField:'regionName',  
				        panelHeight:'auto' ,
				        editable:false,
				        loadFilter : function(data) {
							var list = [ {
								regionId : '',
								regionName : '--全部--'
							},{
								regionId : '0',
								regionName : '票务中心'
							} ];
							return $.merge(list, data);
						}
					});
				}
			} 
		}
	});
	//终端状态初始化
	$('#qstat').combobox({
		valueField : 'id',
		textField : 'text',
		editable : false,
		panelHeight : 'auto',
		data : [ {
			id : '',
			text : '--全部--'
		}, {
			id : 'Y',
			text : '启用'
		}, {
			id : 'N',
			text : '停用'
		} ]
	});
}