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
		loadFilter : function(data){
			var newData=[];
			$.each(data, function(i, row){
				if(row.outletType=='02'){
					newData.push(row);
				}
			});
			return newData;
		},
		onSelect : function(data) {
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
	
	$("#errorSpec").html("格式错误个数[0]");
	
	$("#ticketIds").textbox({
		multiline:true,
		width: 250,
		height: 400,
		onChange:function(newValue, oldValue){
			var errorIds=[];
			var ids= newValue.split("\n");
			var newIds=[];
			var num=0;
			$.each(ids, function(i, id){
				if($.trim(id).length==0){
					return true;//相当于return true: contiune,return false 相关于break
				}
				if(!checkIdCard(id)){
					errorIds.push(id);
					return true;
				}
				num++;
			});

			$("#errorSpec").html("格式错误个数["+errorIds.length+"]");
			$("#errorIds").textbox("setValue",errorIds.join("\n"));

			return true;
		}
	});
}


function saveRepairSale() {
	if (!$('#fm').form('validate')) {
		return false;
	}

	var errorIds=[];
	var ticketIds=$("#ticketIds").textbox("getValue");
	var ids= ticketIds.split("\n");
	var num=0;
	$.each(ids, function(i, id){
		if($.trim(id).length==0){
			return true;//相当于return true: contiune,return false 相关于break
		}
		if(!checkIdCard(id)){
			errorIds.push(id);
			return true;
		}
		num++;
	});
	
	if(errorIds.length!=0){
		alert("格式错误个数["+errorIds.length+"]"+"\n错误列表["+errorIds.join(",")+"]");
		return false;
	}
	
	var ticketCount=$("#ticketCount").textbox("getValue");
	
	if(ticketCount!=num){
		alert("门票数量与身份证号数量不一致");
		return false;
	}
	
	$.messager.confirm('提示', '确定提交吗？', function(r) {
		if (r) {
			$.messager.progress({
				text : '正在处理,请稍候...'
			});
			$('#fm').form('submit', {
				url : $ctx + "/bill/repairSaleOrderByIdenttyId.do",
				method : "post",
				onSubmit : function() {
				},
				success : function(e) {
					$.messager.progress("close");
					if (!getFormError(e)) {
						return false;
					}
					$('#fm').form("reset");
					
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
