$(function() {
	$myTable = $('#mytable');
	var orgId = $('#orgId').val();
	createTicketTypeTable(orgId);
});
function createTicketTypeTable(orgId) {
	$myTable.datagrid({
		nowrap : true,// 是否只显示一行，即文本过多是否省略部分。
		striped : true,
		url : $ctx + '/ticketTypeMng/listTicketType.do?orgId='+orgId, // controller地址
		onLoadSuccess : function(e) {
		},
		onLoadError : function(e) {
			getAjaxError(e);
		},
		idField : 'ticketTypeId',
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
			field : 'ticketTypeId',
			title : '票种编号',
			editor : "text",
			align : 'center',
			halign: 'center',
			width:80
		}, {
			field : 'ticketTypeName',
			title : '票种名称',
			editor : "text",
			align : 'left',
			halign: 'center',
			width:100
		}, {
			field : 'teamFlag',
			title : '是否团体',
			editor : "text",
			align : 'center',
			halign: 'center',
			formatter : getYN,
			width:80
			
		}, {
			field : 'validateTimes',
			title : '次数',
			editor : "text",
			align : 'center',
			width:40
		}, {
			field : 'lessFlag',
			title : '是否优惠',
			editor : "text",
			align : 'center',
			formatter : getYN,
			width:80
		}, {
			field : 'dayNightFlag',
			title : '日夜场',
			editor : "text",
			align : 'center',
			formatter : getDN,
			width:100
		} , {
			field : 'dayValidateFlag',
			title : '是否销售日当日有效',
			align : 'center',
			halign: 'center',
			formatter : getYN,
			width:180
		} , {
			field : 'ticketTypeStat',
			title : '票种状态',
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
			field : 'price',
			title : '票价',
			halign: 'center',
			align : 'right',
			width:60
		}, {
			field : 'venueId',
			title : '场馆',
			align : 'center',
			halign: 'center',
			formatter : getVenue,
			width:100
				
		}, {
			field : 'ruleId',
			title : '规则',
			halign: 'center',
			align : 'center',
			formatter : getRule,
			width:100
		}, {
			field : 'priceId',
			title : '阶梯票价',
			halign: 'center',
			align : 'center',
			formatter : getPrice,
			width:100
		}, {
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
/**
 * 规则详情
 * @param value
 * @param row
 * @param index
 * @returns {String}
 */
function getRule(value, row, index) {
	var s1;
	s1 = '<span class="datagrida"><a href="javascript:void(0)" title="规则详情"  onclick="getRuleInfo(' + index + ')">规则详情</a></span> ';	
	return s1;
}
/**
 * 阶梯票价详情
 * @param value
 * @param row
 * @param index
 * @returns {String}
 */
function getPrice(value, row, index) {
	var s1;
	s1 = '<span class="datagrida"><a href="javascript:void(0)" title="阶梯票价详情"  onclick="getPriceInfo(' + index + ')">票价详情</a></span> ';	
	return s1;
}
/**
 * 获取阶梯票价详情
 * @param index
 */
function getPriceInfo(index){
	var rowData = $myTable.datagrid("getRows")[index];
	var ticketTypeId = rowData.ticketTypeId;
	var url=$ctx + "/ticketTypeMng/beforePriceList.do?ticketTypeId="+ticketTypeId;
	top.addTab("阶梯票价",url);
	/*var iframe='<iframe id="priceIframe" src='+url+' frameborder="0" width="790px" height="550px"></iframe>'
	$("#ticTypePriceDialog").dialog({
		title:"阶梯票价详情",
		resizable:true,
		inline:true,
		content:iframe,
		top:5,
		closed: false,
		cache: false,
		modal: true,
		onclose:function(){$("#priceIframe").remove();}
	}).dialog("open");*/
}
function getYN(value, row, index) {
	if("Y"==value){
		return "是";
	}
	else if("N"==value){
		return "否";
	}
	else{
		return "";
	}
}

function getDN(value, row, index) {
	if("D"==value){
		return "日场";
	}
	else if("N"==value){
		return "夜场";
	}
	else{
		return "";
	}
}
/**
 * 获取规则信息
 * @param index
 */
function getRuleInfo(index){
	var rowData = $myTable.datagrid("getRows")[index];
	var ticketTypeId = rowData.ticketTypeId;
	var url=$ctx + "/ticketTypeMng/beforeRuleList.do?ticketTypeId="+ticketTypeId;
	var iframe='<iframe id="ruleIframe" src='+url+' frameborder="0" width="790px" height="550px"></iframe>'
	$("#ruleDialog").dialog({
		title:"规则详情",
		resizable:true,
		inline:true,
		content:iframe,
		top:5,
		closed: false,
		cache: false,
		modal: true,
		onclose:function(){$("#ruleIframe").remove();}
	}).dialog("open");
}
/**
 * 场馆详情
 * @param value
 * @param row
 * @param index
 * @returns {String}
 */
function getVenue(value, row, index) {
	var s1;
	s1 = '<span class="datagrida"><a href="javascript:void(0)" title="详情"  onclick="showVenue(' + index + ')">查看</a> </span>';
	return s1;
}

function showVenue(index){
	var rowData = $myTable.datagrid("getRows")[index];
	var ticketTypeId = rowData.ticketTypeId;
	var url =$ctx + "/ticketTypeMng/venueStr.do?ticketTypeId="+ticketTypeId;
			$("#updateTicketTypeDialog").dialog({
				title:"场馆信息",
				resizable:true,
				inline:true,
				cache : false,
				modal : false,
				href :url, 
				onLoadError:function(e){
					getAjaxError(e);
				}
			}).dialog("open");	
}

/**
 * 修改删除操作
 * @param value
 * @param row
 * @param index
 * @returns {String}
 */
function getstr(value, row, index) {
	var selRow = $myTable.datagrid('getRows')[index];
	
	var s1//, s2;
	s1 = '<a href="javascript:void(0)" title="修改" class="opr-btn icon-edit" onclick="updTicketType(' + index + ')"></a> ';
	//s2 = '<a href="javascript:void(0)" title="删除" class="opr-btn icon-del" onclick="delticketType(' + index + ')"></a> ';
	return s1 //+ s2;
}

/**
 * 修改票种信息
 * @param index
 */
function updTicketType(index) {
	var rowData = $myTable.datagrid("getRows")[index];
	var ticketTypeId = rowData.ticketTypeId;
	var url = $ctx + '/ticketTypeMng/beforeUpdTicketType.do?ticketTypeId='+ticketTypeId;
		$("#updateTicketTypeDialog").dialog({
			title:"修改基本信息",
			resizable:true,
			inline:true,
			cache : false,
			modal : true,
			href :url, 
			//buttons : "#dlg-buttons",
			onLoadError:function(e){
				getAjaxError(e);
			}
		}).dialog("open");
}

function delticketType(index){
	var rowData = $myTable.datagrid("getRows")[index];
	var ticketTypeId = rowData.ticketTypeId;
	$.messager.confirm('提示', '您确定要删除[' + rowData.ticketTypeName + ']记录吗?', function(r) {
		if (r) {
			$.ajax({
				url : $ctx + "/ticketTypeMng/delTicketType.do",
				type : "post",
				data : {ticketTypeId:ticketTypeId},
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
function saveUpdTicketType(){
	$.messager.confirm('提示', '您确定要修改此记录吗?', function(r) {
		if (r) {
			$.messager.progress({
				text : '正在处理,请稍候...'
			});
			$.ajax({
				url : $ctx + "/ticketTypeMng/saveUpdTicketType.do",
				type : "post",
				data : $("#fm").serialize(),
				success : function(e) {
					$.messager.progress("close");
					$.messager.show({
						showType : 'slide',
						title : "提示信息",
						msg : '修改成功',
						timeout : 2000
					});
					$myTable.datagrid("reload");
					$('#updateTicketTypeDialog').dialog('close');
				},
				error : function(e) {
					getAjaxError(e);
				}
			});
		}
	});

}
/**
 *  新增票种
 */

function addTicketType() {
var url = $ctx + '/pages/sys/tickettypemng/addTicketType.jsp';
top.addTab("新增票种",url);
}

/**
 *修改状态 
 * 
 */
function updateTicketStat(ticketTypeStat) {
	var selRow = $myTable.datagrid("getSelections"); // 返回选中多行
	var sttText = '';
	if (ticketTypeStat == 'Y') {
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
			var id = selRow[i].ticketTypeId; // OTRECORDID这个是你要在列表中取的单个id
			ids.push("'" + id + "'"); // 然后把单个id循环放到ids的数组中
		}
		var keyIds = ids.join(",");

		$.messager.confirm('提示', '您确定修改[' + selRow.length + ']条状态吗?', function(r) {
			if (r) {
				$.ajax({
					url : $ctx + "/ticketTypeMng/updateTicketStat.do",
					type : "post",
					data : {
						"ticketTypeId" : keyIds,
						"ticketTypeStat" : ticketTypeStat
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