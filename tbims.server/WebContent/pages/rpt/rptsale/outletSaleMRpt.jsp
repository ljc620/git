<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="myel" uri="/elFunction"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"></c:set>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>网点销售月统计</title>
<%@ include file="/common1.jsp"%>
<script type="text/javascript" src="${path}/resources/jquery-easyui/plugins/jquery.table.rowspan.js"></script>
<script type="text/javascript">
$(function() {
$('#rptDt').datebox({
         onShowPanel: function () {//显示日趋选择对象后再触发弹出月份层的事件，初始化时没有生成月份层
            span.trigger('click'); //触发click事件弹出月份层
            if (!tds) setTimeout(function () {//延时触发获取月份对象，因为上面的事件触发和对象生成有时间间隔
                tds = p.find('div.calendar-menu-month-inner td');
                 tds.click(function (e) {
                     e.stopPropagation(); //禁止冒泡执行easyui给月份绑定的事件
                    var year = /\d{4}/.exec(span.html())[0]//得到年份
                    , month = parseInt($(this).attr('abbr'), 10); //月份，这里不需要+1
                    $('#rptDt').datebox('hidePanel')//隐藏日期对象
                    .datebox('setValue', year + '-' + month); //设置日期的值
                });
             }, 0);
             yearIpt.unbind();//解绑年份输入框中任何事件
        },
         parser: function (s) {
             if (!s) return new Date();
             var arr = s.split('-');
             return new Date(parseInt(arr[0], 10), parseInt(arr[1], 10) - 1, 1);
         },
         formatter: function (d) { return d.getFullYear() + '-' + (d.getMonth() + 1);/*getMonth返回的是0开始的，忘记了。。已修正*/ }
     });
     var p = $('#rptDt').datebox('panel'), //日期选择对象
        tds = false, //日期选择对象中月份
        yearIpt = p.find('input.calendar-menu-year'),//年份输入框
        span = p.find('span.calendar-text'); //显示月份层的触发控件
        $("#rptDt").datebox('setValue','<fmt:formatDate value="${rptDt}" type="both" pattern="yyyy-MM-dd" />');
     initLoad();

});
function initLoad() {
	$("#tbListTM").rowspan(0); //第一列合并
	$("#tbListTM").rowspan(1);//第二列合并
   // $("#tbListTM").rowspan(2);//第二列合并  
}
//查询
function query(){
	if (!$('#queryForm').form('validate')) {
		return false;
	}
	$.messager.progress({
		text : '正在处理,请稍候...'
	});
	var url="<%=path%>/rptsale/outletSaleMRpt.do";
	 $('#queryForm').attr("action", url).submit();
}
function expExcel(){
	if (!$('#queryForm').form('validate')) {
		return false;
	}
	var url='<%=path%>/rptsale/expExceloutletMSale.do';
	 $('#queryForm').attr("action", url).submit();
}
</script> 
</head>
<body style="background-color:#fff;">
				
				<div class="ss-bar">
				<form id="queryForm">
						<ul class="ss-ul">
							<li>统计月份</li>
							<li>
								<input id="rptDt" name="rptDt" class="easyui-datebox"  data-options="editable:false"  style="width:120px;" required="true"></input>
							</li>
							<li><a class="gray2-btn"  onclick="query();" >查询</a>
							</li>
							<li>
							<a class="indigo-btn" id="btnExcel"  id="btnExcel" onclick="expExcel()">导出EXCEL</a></li>
						</ul>
					</form>
				</div>
				<div  class="it-datagrid">
				<table class="report-table" id="tbListTM" style="width:100%">
					<thead class="datagrid1-header">
						<tr class="theader">
						   
							<th>网点名称</th>
							<th>交易类型（包括换票）</th>
							<th>支付方式</th>
							<th>票种名称</th>
							<th>销售数量</th>
							<th>销售总金额（不包括换票）</th>
						</tr>
					</thead>
					<tbody class="datagrid1-body">
					   <c:set var="sumNum" value="0"/>
					    <c:set var="sumAmt" value="0"/>
						<c:forEach var="bean" items="${outletSaleList}" varStatus="status">
							    <tr class="tbody">		
							    
								<td >${bean.outletName}</td> 
								<td align="center">${bean.orderTypeDesc}</td>
								<td align="center">${bean.payTypeDesc}</td>
								<td align="center">${bean.ticketTypeName}</td>
								<td align="right">${bean.saleSum}</td>
								<td align="right">${bean.saleAmt}</td>
							</tr>
							 <c:set var="sumNum" value="${sumNum+bean.saleSum}"/>
							 <c:set var="sumAmt" value="${sumAmt+bean.saleAmt}"/>
						</c:forEach>
						<c:if test="${sumNum !=0}">
						 <tr class="tbody">		
							    <td colspan="4" style="font-weight:900;color:#000;text-align:center">合计</td>
								<td align="right"><fmt:formatNumber type="number"  value="${sumNum/2}" maxFractionDigits="0"></fmt:formatNumber></td>
								<td align="right"><fmt:formatNumber type="number"  value="${sumAmt/2}" maxFractionDigits="0"></fmt:formatNumber></td>
							</tr>
						</c:if>
					</tbody>
				</table>
				</div>

</body>
</html>