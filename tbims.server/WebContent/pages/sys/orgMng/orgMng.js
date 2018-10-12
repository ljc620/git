$(function() {
	$myTable = $('#mytable');
	createOrgTable();
});
function createOrgTable() {
	$myTable.datagrid({
		nowrap : true,// 是否只显示一行，即文本过多是否省略部分。
		striped : true,
		url : $ctx + '/orgMng/listOrg.do', // controller地址
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
			field : 'orgId',
			title : '组织机构代码',
			editor : "text",
			align : 'center'
		}, {
			field : 'orgName',
			title : '机构名称',
			editor : "text",
			align : 'left'
		} ] ],
		columns : [ [// 显示的列
		 {
			field : 'orgType',
			title : '代理商类型',
			width : 100,
			editor : "text",
			align : 'center',
			formatter : function(value, row, index) {
				if (value == '0') {
					value = '签约社';
				} else if (value == '1') {
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
			align : 'left'
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
			width : 130
		} , {
			field : 'orgStat',
			title : '状态',
			align : 'center',
			halign: 'center',
			formatter : function(value, row, index) {
				if (value == 'Y') {
					value = '<span style="color:green;">启用</span>';
				} else if (value == 'N') {
					value = '<span style="color:red;">禁用</span>';
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
		, {
			field : 'advanceAmt',
			title : '预付款余额',
			align : 'right',
			halign: 'center',
			width : 100//,
//			formatter:getAdvanceAmt
		}, {
			field : 'advanceFrozeAmt',
			title : '预付款冻结金额',
			halign: 'center',
			align : 'right',
			width : 100
		}, {
			field : 'callbackUrl',
			title : '核销回调地址',
			halign: 'center',
			align : 'left',
			width : 150
		}, {
			field : 'callbackData',
			title : '核销回调数据格式',
			halign: 'center',
			align : 'left',
			width : 150
		}, {
			field : 'token',
			title : '授权码',
			align : 'center',
			halign: 'center',
			formatter:function(value, row, index) {
				if(value == null)
					return '';
				value = '<a href="javascript:void(0)" title="查看" onclick="DspToken(\'' + value + '\')">查看</a> ';
				value += '<a href="javascript:void(0)" title="重置授权码" onclick="updateToken(\''+row.orgId + '\')">重置</a> ';
				return value;
			},
			width : 80
		}, {
			field : 'caozuo',
			title : '操作',
			align : 'center',
			width : 120,
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
	s1 = '<span class="datagrida"><a href="javascript:void(0)" title="预付款详情"  onclick="getAdvanceWlInfo(' + index + ')">'+value+'</a> </span>';
	return s1;
}
function getDepositInfo(index){
	var rowData = $myTable.datagrid("getRows")[index];
	var orgId = rowData.orgId;
	var url=$ctx + "/orgMng/beforeListDeposit.do?orgId="+orgId+"&type=wl";
	top.addTab("网络代理商保证金",url);
}
function getAdvanceWlInfo(index){
	var rowData = $myTable.datagrid("getRows")[index];
	var orgId = rowData.orgId;
	var url=$ctx + "/orgMng/beforeListAdvance.do?orgId="+orgId+"&flag=w";//网络代理商-预付款
	top.addTab("网络代理商预付款",url);
}
function getstr(value, row, index) {
	var s1='';
	if(row.orgType == '1'){
		s1 += '<a href="javascript:void(0)" title="修改回调信息" class="opr-btn icon-edit" onclick="updateOrg(' + index + ')"></a> ';
	}
	s1 += '<a href="javascript:void(0)" title="可售票种设置" style="margin:0px;" class="opr-btn icon-set" onclick="saleTicketUpdate(' + index + ')"></a> ';
	//s1 = '<a href="javascript:void(0)" title="删除" class="opr-btn icon-del" onclick="delOrg(' + index + ')"></a> ';
	if(row.orgStat == 'N')
		s1 += '<a href="javascript:void(0)" style="color:green;" title="启用" onclick="updateStat(\'Y\', \'' + row.orgId + '\')">启用</a> ';
	else
		s1 += '<a href="javascript:void(0)" style="color:red;" title="禁用" onclick="updateStat(\'N\', \'' + row.orgId + '\')">禁用</a> ';
	return s1 ;
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

var editOrgId='';
/**
 * 修改代理商回调设置
 * @param index
 */
function updateOrg(index) {
//	$("#save-button").hide();
	$("#update-button").show();
	var rowData = $myTable.datagrid("getRows")[index];
	$("#orgId").text(rowData.orgId);
	editOrgId = rowData.orgId;
	$("#orgName").text(rowData.orgName);
//	$("#callbackUrl").val(rowData.callbackUrl);
//	$("#callbackData").val(rowData.callbackData);
	$("#callbackUrl").textbox("setValue",rowData.callbackUrl);
	$("#callbackData").textbox("setValue",rowData.callbackData);
	
	$("#update-button").unbind('click');
	$("#update-button").on('click', saveOrg);
	$("#close-button").unbind('click');
	$("#close-button").on('click', function(){
		$('#orgDialog').dialog('close');
	});
	
	$("#orgDialog").dialog({
		title:"修改机构回调设置",
		resizable:true,
		inline:true,
		cache : false,
		modal : true,
		buttons : "#dlg-buttons",
		onLoadError:function(e){
			getAjaxError(e);
		}
	}).dialog("open");
}
/**
 * 修改代理商回调设置
 * @param index
 */
function saleTicketUpdate(index) {
//	$("#save-button").hide();
	$("#update-button").show();
	var rowData = $myTable.datagrid("getRows")[index];
	editOrgId = rowData.orgId;
//	$("#orgId").text(rowData.orgId);
//	$("#orgName").text(rowData.orgName);
//	$("#callbackUrl").val(rowData.callbackUrl);
//	$("#callbackData").val(rowData.callbackData);
//	$("#callbackUrl").textbox("setValue",rowData.callbackUrl);
//	$("#callbackData").textbox("setValue",rowData.callbackData);
	
	$("#update-button").unbind('click');
	$("#update-button").on('click', saveOrgTicketTyps);
	$("#close-button").unbind('click');
	$("#close-button").on('click', function(){
		$('#saleTicketDialog').dialog('close');
	});
	
	$("#saleTicketDialog").dialog({
		title:"机构可售票种设置--"+rowData.orgName,
		resizable:true,
		inline:true,
		cache : false,
		modal : true,
		buttons : "#dlg-buttons",
		onLoadError:function(e){
			getAjaxError(e);
		}
	}).dialog("open");
	createTicketTable();
}

/**
 * 初始化机构可售票种列表
 */
function createTicketTable() {
	$ticketTable = $('#saleTicketTable');
	$ticketTable.datagrid({
		nowrap : true,// 是否只显示一行，即文本过多是否省略部分。
		striped : true,
		url : $ctx + '/orgMng/listOrgSaleTicketTypes.do', // controller地址
		onLoadSuccess : function(data) {
			if(data){
				$.each(data.rows,function(index,item){
					if(item.checked){
						$ticketTable.datagrid('checkRow',index);
					}else {
						$ticketTable.datagrid('uncheckRow',index);
					}
					});
		}},
		onLoadError : function(e) {
			getAjaxError(e);
		},
		idField : 'ticketTypeId',
		fitColumns : true,
		fit : true,
		checkbox : true,
		rownumbers : true,
		queryParams:{orgId: editOrgId},
		singleSelect : false,
		frozenColumns : [ [ {
			field : 'ck',
			checkbox : true
		} ] ],

		columns : [ [// 显示的列
		{
			field : 'ticketTypeId',
			title : '票种代码',
			width : 120,
			editor : "text",
			align : 'center'
		}, {
			field : 'ticketTypeName',
			title : '票种名称',
			width : 200,
			editor : "text",
			align : 'center'
		} ] ]
	});

}
/**
 * 保存代理商
 * @returns {Boolean}
 */
function saveOrg() {
	$.messager.confirm('提示', '确定保存信息吗？', function(r) {
		if (r) {
			$.messager.progress({
				text : '正在处理,请稍候...'
			});
			$.ajax({
				url : $ctx + "/orgMng/updateCallBackSetting.do",
				type : "post",
				data : {
					"orgId" : $("#orgId").text(),
					"callbackUrl" : $("#callbackUrl").textbox("getValue"),
					"callbackData": $("#callbackData").textbox("getValue")
				},
				success : function(e) {
					$.messager.progress("close");
					$('#orgDialog').dialog('close');
					$myTable.datagrid('reload'); // 重载行
					$myTable.datagrid('clearSelections');
					$.messager.show({
						showType : 'slide',
						title : "提示信息",
						msg : "机构回调设置更新成功",
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

function saveOrgTicketTyps() {
	// 获取选定的票种
	var selRoleRow = $('#saleTicketTable').datagrid("getChecked"); // 返回选中多行
	var msg = '确定保存信息吗？';
	if (selRoleRow.length == 0) {
		msg = '您未选定任何票种，确定要不授予此机构任何可售票种吗？';
	}
	
	$.messager.confirm('提示', msg, function(r) {
		if (r) {
			$.messager.progress({
				text : '正在处理,请稍候...'
			});
			
			var saleTickets = [];
			for (var i = 0; i < selRoleRow.length; i++) {
				// 获取自定义table 的中的checkbox值
				saleTickets.push(selRoleRow[i].ticketTypeId); // 然后把单个id循环放到ids的数组中
			}
			
			$.ajax({
				url : $ctx + "/orgMng/updateOrgSaleTicketTypes.do",
				type : "post",
				data : {
					"orgId" : editOrgId,
					"saleTickets": saleTickets.toString()
				},
				success : function(e) {
					$.messager.progress("close");
					$('#saleTicketDialog').dialog('close');
					$.messager.show({
						showType : 'slide',
						title : "提示信息",
						msg : "机构可售票种更新成功",
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
 *修改状态 
 * 
 */
function updateStat(orgStat, orgId) {
	var message='';
	if(orgStat == 'Y')
		message = '您确定要启用机构[' + orgId + ']吗？';
	else
		message = '您确定要禁用机构[' + orgId + ']吗？';
	$.messager.confirm('提示', message, function(r) {
		if (r) {
			$.ajax({
				url : $ctx + "/orgMng/updateStat.do",
				type : "post",
				data : {
					"orgId" : "'"+orgId+"'",
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
function updateToken(orgId) {

	$.messager.confirm('提示', '您确定要重置机构' + '[' + orgId + ']的授权码吗?', function(r) {
		if (r) {
			$.ajax({
				url : $ctx + "/orgMng/updateToken.do",
				type : "post",
				data : {
					"orgIds" : "'"+orgId+"'", 
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

function DspToken(token){
	$.messager.alert("授权码", token);//, 'info'
}

