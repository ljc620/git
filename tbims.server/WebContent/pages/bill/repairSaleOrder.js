$(function() {
	initCombobox();
});

function initCombobox() {
	// 票种初始化
	$('#ticketTypeId').combobox({
		url : $ctx + '/orgMng/ticketTypeList.do?flag=a',
		method : 'get',
		valueField : 'ticketTypeId',
		textField : 'ticketTypeName',
		multiple : false,
		editable : false,
		onLoadSuccess : function(e) {
		},
		onLoadError : function(e) {
			getAjaxError(e);
		}
	});

	$("#saleUserId").combobox({
		valueField : 'userId',
		textField : 'userName',
		method : 'post',
		multiple : false,
		editable : false,
		onLoadSuccess : function(e) {
		},
		onLoadError : function(e) {
			getAjaxError(e);
		}
	});

	$('#orgId').combobox({
		url : $ctx + "/comm/listOrg.do?orgType='1'", // controller地址
		method : 'post',
		valueField : 'orgId',
		textField : 'orgName',
		multiple : false,
		editable : false,
		onLoadSuccess : function(e) {
		},
		onLoadError : function(e) {
			getAjaxError(e);
		}
	});

	$('#outletId').combobox({
		url : $ctx + '/comm/listSaleOutletByUser.do',
		method : 'post',
		valueField : 'outletId',
		textField : 'outletName',
		multiple : false,
		editable : false,
		onLoadSuccess : function(e) {
		},
		onLoadError : function(e) {
			getAjaxError(e);
		},
		onSelect : function(data) {
			$("#saleUserId").combobox({
				url : $ctx + '/rptsum/userList.do?outletId=' + data.outletId,
				valueField : 'userId',
				textField : 'userName',
				method : 'post',
				multiple : false,
				editable : false,
				onLoadSuccess : function(e) {
				},
				onLoadError : function(e) {
					getAjaxError(e);
				}
			});
			
			$("#clientId").combobox({
				url : $ctx + '/bill/listClient.do?outletId=' + data.outletId,
				valueField : 'clientId',
				textField : 'clientName',
				method : 'post',
				multiple : false,
				editable : false,
				onLoadSuccess : function(e) {
				},
				onLoadError : function(e) {
					getAjaxError(e);
				}
			});
		}
	});

	$("#orderType").combobox({
		onSelect : function(data) {
			if (data.value == "XC") {
				$("#orgIdTR").hide();
				$("#thirdOrderNumTR").hide();

				$("#orgId").textbox("disableValidation");
				$("#thirdOrderNum").textbox("disableValidation");

				payTypeXC();

			} else {
				$("#orgIdTR").show();
				$("#thirdOrderNumTR").show();

				$("#orgId").textbox("enableValidation");
				$("#thirdOrderNum").textbox("enableValidation");

				payTypeDL();
			}
		}
	});
	
	initForm();
}

function initForm(){
	// 默认不验证
	$("#orgIdTR").hide();
	$("#thirdOrderNumTR").hide();
	$("#orgId").textbox("disableValidation");
	$("#thirdOrderNum").textbox("disableValidation");
	
	$("#clientId").combobox("clear");
	$("#saleUserId").combobox("clear");
	
	$("#clientId").combobox("loadData",[]);
	$("#saleUserId").combobox("loadData",[]);
	payTypeXC();
}

function payTypeXC() {
	$("#payType").combobox("clear");
	$("#payType").combobox("loadData", [ {
		label : '01',
		value : '现金'
	}, {
		label : '02',
		value : 'POS付款'
	}, {
		label : '03',
		value : '微信'
	}, {
		label : '04',
		value : '支付宝'
	} ]);
}

function payTypeDL() {
	$("#payType").combobox("clear");
	$("#payType").combobox("loadData", [ {
		label : '05',
		value : '代理'
	} ]);

	$("#payType").combobox("setValue", "05");
}

/**
 * 校验门票数量和票号是否正确
 */
function validateTicketIds() {
	var ticketCount = $("#ticketCount").numberbox("getValue");
	var ticketIds = $("#ticketIds").textbox("getValue");

	if (!$('#fm').form('validate')) {
		$.messager.alert("提示", "请填写订单信息");
		return false;
	}

	$.ajax({
		url : $ctx + "/bill/validateTicketIds.do",
		type : "post",
		data : {
			"ticketCount" : ticketCount,
			"ticketIds" : ticketIds
		},
		success : function(e) {
			$.messager.alert("提示", "检验正确");
		},
		error : function(e) {
			getAjaxError(e);
		}
	});
}

function saveRepairSale() {
	if (!$('#fm').form('validate')) {
		return false;
	}
	$.messager.confirm('提示', '确定提交吗？', function(r) {
		if (r) {
			$.messager.progress({
				text : '正在处理,请稍候...'
			});
			$('#fm').form('submit', {
				url : $ctx + "/bill/repairSaleOrder.do",
				method : "post",
				onSubmit : function() {
				},
				success : function(e) {
					$.messager.progress("close");
					if (!getFormError(e)) {
						return false;
					}
					$('#fm').form("reset");
					
					initForm();
					
					$.messager.show({
						showType : 'slide',
						title : "提示信息",
						msg : '保存成功',
						timeout : 5000
					});
				}
			}, 'json');
		}
	});
}
