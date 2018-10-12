<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="myel" uri="/elFunction"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"></c:set>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>对账管理</title>
<%@ include file="/common.jsp"%>
<script type="text/javascript" src="billMng.js"></script>
<script type="text/javascript">
	$(function() {
		$dg = $('#mytable');
		// 创建树数据列表
		createMytable();

		$('#tranStatus').combobox({
			valueField : 'id',
			textField : 'text',
			editable : false,
			panelHeight : 'auto',
			width : 120,
			data : [ {
				'id' : '',
				'text' : '--全部--'
			}, {
				'id' : '支付成功',
				'text' : '支付交易'
			}, {
				'id' : '转入退款',
				'text' : '退款交易'
			} ]
		});

		$('#billResult').combobox({
			valueField : 'id',
			textField : 'text',
			editable : false,
			panelHeight : 'auto',
			width : 120,
			data : [ {
				'id' : '',
				'text' : '--全部--'
			}, {
				'id' : '0',
				'text' : '对账成功'
			}, {
				'id' : '1',
				'text' : '对账失败'
			} ]
		});
	});

	/**
	 * 过滤对账明细
	 */
	function queryBill() {
		var tranStatus = $('#tranStatus').textbox('getValue');
		var billResult = $('#billResult').textbox('getValue');

		var queryParams = {
			'tranStatus' : tranStatus,
			'billResult' : billResult,
			"billId" : "${param.billId}"
		}

		$dg.datagrid('options').queryParams = queryParams;
		$dg.datagrid('load');
		return true;
	}

	/**
	 * 表格内操作按钮
	 */
	function getstr(value, row, index) {
		var s1;
		var billResult = row.billResult;
		var tranStatus = row.tranStatus;
		
		//对账失败、支付成功、未退款的记录才允许 发起退款
		if (billResult == '1' && tranStatus == '支付成功') {
			if($.trim(row.outRefundNo).length==0 || $.trim(row.outRefundNo)=='null'){
				s1 = '<a href="javascript:void(0)" onclick="refundPay(' + index + ')">发起退款</a> ';
			}
		}
		return s1;
	}

	//发起退款
	function refundPay(index) {
		var rowData = $dg.datagrid("getRows")[index];
		var billDetailId = rowData.billDetailId;
		$.ajax({
			url : $ctx + "/bill/refundPay.do",
			type : "post",
			data : {
				"billDetailId" : billDetailId,
			},
			success : function(e) {
				$dg.datagrid("reload");
				$.messager.show({
					showType : 'slide',
					title : "提示信息",
					msg : "处理完成",
					timeout : 2000
				});
			},
			error : function(e) {
				getAjaxError(e);
			}
		});
	}

	/**
	 * 初始化列表
	 */
	function createMytable() {
		$dg.datagrid({
			nowrap : true,// 是否只显示一行，即文本过多是否省略部分。
			striped : true,
			queryParams : {
				"billId" : "${param.billId}"
			},
			url : $ctx + '/bill/listBillDetail.do', // controller地址
			onLoadSuccess : function(e) {
			},
			onLoadError : function(e) {
				getAjaxError(e);
			},
			idField : 'billDetailId',
			fitColumns : false,
			fit : true,
			rownumbers : true,
			pagination : true, // 包含分页
			pageSize : getPageSize(),
			pageList : getPageList(),
			toolbar : "#mytable-buttons",
			columns : [ [// 显示的列
			{
				field : 'tranTime',
				title : '交易时间',
				width : 130,
				align : 'center'
			}, {
				field : 'billResult',
				title : '对账结果',
				width : 100,
				align : 'center',
				formatter : function(value, row, index) {
					if (value == '0') {
						value = '对账成功';
					} else if (value == '1') {
						value = '<span style="color: red;">对账失败</span>';
					}
					return value;
				}
			}, {
				field : 'mchId',
				title : '商户号',
				width : 90,
				align : 'center'
			}, {
				field : 'transactionId',
				title : '平台订单号',
				align : 'center'
			}, {
				field : 'outTradeNo',
				title : '商户订单号',
				align : 'center'
			}, {
				field : 'tranStatus',
				title : '交易状态',
				width : 80,
				align : 'center'
			}, {
				field : 'orderFee',
				title : '总金额',
				width : 100,
				align : 'center'
			}, {
				field : 'refundId',
				title : '平台退款单号',
				align : 'center'
			}, {
				field : 'outRefundNo',
				title : '商户退款单号',
				align : 'center'
			}, {
				field : 'refundFee',
				title : '退款金额',
				width : 100,
				align : 'center'
			}, {
				field : 'remark',
				title : '备注',
				align : 'center'
			}, {
				field : 'opt',
				title : '操作',
				width : 80,
				align : 'center',
				formatter : getstr
			} ] ]
		});

	}
</script>
</head>
<body>
	<div id="mytable-buttons">
		<div class="ss-bar">
			<ul class="ss-ul">
				<li style="width: 50px; padding-left: 15px;">对账状态</li>
				<li>
					<input id="tranStatus" width="60px"></input>
				</li>
				<li style="width: 50px; padding-left: 15px;">对账结果</li>
				<li>
					<input id="billResult" width="60px"></input>
				</li>
				<li>
					<a href="javascript:void(0)" onclick="javascript:queryBill();" class="ss-btn">&nbsp;</a>
				</li>
			</ul>
		</div>
	</div>
	<div class="it-datagrid" style="height: 100%">
		<table id="mytable"></table>
	</div>
</body>
</html>