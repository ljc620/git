<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="myel" uri="/elFunction"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>配送审核</title>
<%@ include file="/common.jsp"%>
<script type="text/javascript"
	src="${path}/resources/lodop/lodoopcomm.js"></script>
<script type="text/javascript"
	src="${path}/resources/lodop/LodopFuncs.js"></script>
<script type="text/javascript">
	function printInfo() {
		myPreview("35", "35", $("#printContent").html());
	}
</script>
<%@ include file="/pages/print.jsp"%>
</head>
<body style="text-align:center">
	<object id="LODOP_OB"
		classid="clsid:2105C259-1E0C-4534-8141-A753534CB4CA" width="0"
		height="0">
		<embed id="LODOP_EM" type="application/x-print-lodop" width="0"
			height="0"></embed>
	</object>
	<div class="ss-bar" style="padding: 2px;">
		<a href="javascript:void(0)" class="blue-btn" onclick="javascript:printInfo()" style="width: 40px; padding-left: 5px">打印</a>
	</div>
	<div id="printContent">
    <!-- 签约社 -->
	<div style="width: 649px; height: 350px;margin:0 auto;">
			<table class="report-table" style="width: 640px;">
				<tr>
					<td colspan="6" style="width: 600px;font-size:14px;font-weight:bold" align="center">团队换票单</td>
				</tr>
				<tr>
					<td colspan="5" style="width: 540px;font-weight:bold"  align="center">基本信息</td>
					<td rowspan="4" align="center" style="border-bottom: 2px dashed black;">旅行社盖章处</td>
				</tr>
				<tr>
					<td style="width: 120px;" align="center">机构名称</td>
					<td style="width: 460px;" colspan="4">${slTeamOrder.orgName}</td>
				</tr>
				<tr>
					<td align="center">预订单号</td>
					<td>${slTeamOrder.applyId}</td>
					<td style="width: 120px;"  align="center">操作员</td>
					<td colspan="2" align="center">${userSession.sysUser.userName}</td>
				</tr>
				<tr>
					<td align="center" style="width: 120px;">订单说明</td>
					<td style="width: 460px;" colspan="4">${slTeamOrder.remark}</td>
				</tr>
				<tr>
					<td colspan="5" style="width: 540px;font-weight:bold"  align="center">入园信息</td>
					<td rowspan="${fn:length(slTeamOrder.slTeamOrderDetails)+3}" style="border-top: 0;" align="center">签约社留存</td>
				</tr>
				<tr>
					<td align="center" style="width: 120px;">入园日期</td>
					<td>
						<fmt:formatDate value="${slTeamOrder.inDt}" type="both"
							pattern="yyyy-MM-dd" />
					</td>
					<td align="center" style="width: 120px;">带团人数</td>
					<td colspan="2"></td>
				</tr>
				<tr>
					
					<td  align="center" colspan="2" >票种</td>
					<td  align="center">申请数量</td>
					<td  align="center">审核数量</td>
					<td  align="center">换票数量</td>
				</tr>
				<c:forEach var="slTeamOrderDetail"
					items="${slTeamOrder.slTeamOrderDetails}" varStatus="status">
					<tr>
						<td align="center" colspan="2" >${slTeamOrderDetail.ticketTypeName}</td>
						<td align="center">${slTeamOrderDetail.applyNum}</td>
						<td align="center">${slTeamOrderDetail.examNum}</td>
						<td></td>
					</tr>
				</c:forEach>
				<tr>
					<td colspan="6" style="font-weight:bold" align="center">换票信息</td>
				</tr>
				<tr>
					<td align="center" style="width: 120px;">换票人姓名</td>
					<td >${changeUserBean.changeUserName}</td>
					<td align="center" style="width: 120px;">手机号</td>
					<td>${changeUserBean.tel}</td>
					<td align="center" style="width: 120px;">证件号码</td>
					<td>${changeUserBean.cardId}</td>
				</tr>

			</table>
			<table style="font-size:11px;margin-top:5px;width:100%">
				<tr>
					<td>旅行社经办人签名（盖章）：</td>
					<td style="width: 280px;">操作人员签名（盖章）：</td>
					<td style="width: 120px;">日期：</td>
				</tr>
				<tr>
					<td colspan="3" style="height:20px; border-bottom: 1px dashed black;">
					<td>
				</tr>
			</table>
		</div>
		
	<!-- end 签约社 -->
	<!-- 换票网点 -->
	<div style="width: 649px; height: 350px;margin:30px auto;">
			<table class="report-table" style="width: 640px;">
				<tr>
					<td colspan="6" style="width: 600px;font-size:14px;font-weight:bold" align="center">团队换票单</td>
				</tr>
				<tr>
					<td colspan="5" style="width: 540px;font-weight:bold"  align="center">基本信息</td>
					<td rowspan="4" align="center" style="border-bottom: 2px dashed black;">旅行社盖章处</td>
				</tr>
				<tr>
					<td style="width: 120px;" align="center">机构名称</td>
					<td style="width: 460px;" colspan="4">${slTeamOrder.orgName}</td>
				</tr>
				<tr>
					<td align="center">预订单号</td>
					<td>${slTeamOrder.applyId}</td>
					<td style="width: 120px;"  align="center">操作员</td>
					<td colspan="2" align="center">${userSession.sysUser.userName}</td>
				</tr>
				<tr>
					<td align="center" style="width: 120px;">订单说明</td>
					<td style="width: 460px;" colspan="4">${slTeamOrder.remark}</td>
				</tr>
				<tr>
					<td colspan="5" style="width: 540px;font-weight:bold"  align="center">入园信息</td>
					<td rowspan="${fn:length(slTeamOrder.slTeamOrderDetails)+3}" style="border-top: 0;" align="center">换票网点留存</td>
				</tr>
				<tr>
					<td align="center" style="width: 120px;">入园日期</td>
					<td>
						<fmt:formatDate value="${slTeamOrder.inDt}" type="both"
							pattern="yyyy-MM-dd" />
					</td>
					<td align="center" style="width: 120px;">带团人数</td>
					<td colspan="2"></td>
				</tr>
				<tr>
					
					<td  align="center" colspan="2" >票种</td>
					<td  align="center">申请数量</td>
					<td  align="center">审核数量</td>
					<td  align="center">换票数量</td>
				</tr>
				<c:forEach var="slTeamOrderDetail"
					items="${slTeamOrder.slTeamOrderDetails}" varStatus="status">
					<tr>
						<td align="center" colspan="2" >${slTeamOrderDetail.ticketTypeName}</td>
						<td align="center">${slTeamOrderDetail.applyNum}</td>
						<td align="center">${slTeamOrderDetail.examNum}</td>
						<td></td>
					</tr>
				</c:forEach>
				<tr>
					<td colspan="6" style="font-weight:bold" align="center">换票信息</td>
				</tr>
				<tr>
					<td align="center" style="width: 120px;">换票人姓名</td>
					<td >${changeUserBean.changeUserName}</td>
					<td align="center" style="width: 120px;">手机号</td>
					<td>${changeUserBean.tel}</td>
					<td align="center" style="width: 120px;">证件号码</td>
					<td>${changeUserBean.cardId}</td>
				</tr>

			</table>
			<table style="font-size:11px;padding:2px;margin-top:5px; width:100%">
				<tr>
					<td>旅行社经办人签名（盖章）：</td>
					<td style="width: 280px;">操作人员签名（盖章）：</td>
					<td style="width: 120px;">日期：</td>
				</tr>
				<tr>
					<td colspan="3" style="height:20px; border-bottom: 1px dashed black;">
					<td>
				</tr>
			</table>
		</div>
		
	<!-- end 换票网点 -->
	<!-- 票务中心 -->
	<div style="width: 649px; height: 326px;padding:2px;margin:0 auto;">
			<table class="report-table" style="width: 640px;">
				<tr>
					<td colspan="6" style="width: 600px;font-size:14px;font-weight:bold" align="center">团队换票单</td>
				</tr>
				<tr>
					<td colspan="5" style="width: 540px;font-weight:bold"  align="center">基本信息</td>
					<td rowspan="4" align="center" style="border-bottom: 2px dashed black;">旅行社盖章处</td>
				</tr>
				<tr>
					<td style="width: 120px;" align="center">机构名称</td>
					<td style="width: 460px;" colspan="4">${slTeamOrder.orgName}</td>
				</tr>
				<tr>
					<td align="center">预订单号</td>
					<td>${slTeamOrder.applyId}</td>
					<td style="width: 120px;"  align="center">操作员</td>
					<td colspan="2" align="center">${userSession.sysUser.userName}</td>
				</tr>
				<tr>
					<td align="center" style="width: 120px;">订单说明</td>
					<td style="width: 460px;" colspan="4">${slTeamOrder.remark}</td>
				</tr>
				<tr>
					<td colspan="5" style="width: 540px;font-weight:bold"  align="center">入园信息</td>
					<td rowspan="${fn:length(slTeamOrder.slTeamOrderDetails)+3}" style="border-top: 0;" align="center">票务中心留存</td>
				</tr>
				<tr>
					<td align="center" style="width: 120px;">入园日期</td>
					<td>
						<fmt:formatDate value="${slTeamOrder.inDt}" type="both"
							pattern="yyyy-MM-dd" />
					</td>
					<td align="center" style="width: 120px;">带团人数</td>
					<td colspan="2"></td>
				</tr>
				<tr>
					
					<td  align="center" colspan="2" >票种</td>
					<td  align="center">申请数量</td>
					<td  align="center">审核数量</td>
					<td  align="center">换票数量</td>
				</tr>
				<c:forEach var="slTeamOrderDetail"
					items="${slTeamOrder.slTeamOrderDetails}" varStatus="status">
					<tr>
						<td align="center" colspan="2" >${slTeamOrderDetail.ticketTypeName}</td>
						<td align="center">${slTeamOrderDetail.applyNum}</td>
						<td align="center">${slTeamOrderDetail.examNum}</td>
						<td></td>
					</tr>
				</c:forEach>
				<tr>
					<td colspan="6" style="font-weight:bold" align="center">换票信息</td>
				</tr>
				<tr>
					<td align="center" style="width: 120px;">换票人姓名</td>
					<td >${changeUserBean.changeUserName}</td>
					<td align="center" style="width: 120px;">手机号</td>
					<td>${changeUserBean.tel}</td>
					<td align="center" style="width: 120px;">证件号码</td>
					<td>${changeUserBean.cardId}</td>
				</tr>

			</table>
			<table style="font-size:11px;padding:2px;margin-top:5px; width:100%">
				<tr>
					<td>旅行社经办人签名（盖章）：</td>
					<td style="width: 280px;">操作人员签名（盖章）：</td>
					<td style="width: 120px;">日期：</td>
				</tr>
			</table>
		</div>
	<!-- end 票务中心 -->
	</div>
</body>
</html>