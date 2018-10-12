<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="myel" uri="/elFunction"%>
<html>
<head>
<%
	response.setHeader("Cache-Control", "no-cache");
	response.setHeader("Pragma", "no-cache");
	response.setDateHeader("Expires", -1);
	String path = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ request.getContextPath();
	pageContext.setAttribute("path", path);
	String beginTimeVal = com.zhming.support.util.DateUtil
			.getNearMinute(com.zhming.support.util.DateUtil.beforeOrAfterHour("HH:mm", 3), 15, "b");
	String endTimeVal = com.zhming.support.util.DateUtil
			.getNearMinute(com.zhming.support.util.DateUtil.getNowDate("HH:mm"), 15, "e");
%>
<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
<meta http-equiv=X-UA-Compatible content="IE=edge,chrome=1">
<meta name="viewport" content="width=device-width,initial-scale=1.0">
<title>票务综合管理系统</title>
<link rel="stylesheet" href="${path}/css/easyui.css" />
<link rel="stylesheet" href="${path}/css/mainstyle.css" />
<link rel="shortcut icon" href="${path}/css/icons/favicon.ico" >
<script src="${path}/resources/jquery-easyui/jquery.min.js"></script>
<script src="${path}/resources/jquery-easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${path}/resources/jquery-easyui/locale/easyui-lang-zh_CN.js"></script>

<link rel="stylesheet" href="${path}/resources/ztree/css/zTreeStyle/zTreeStyle.css" type="text/css">
<script type="text/javascript" src="${path}/resources/ztree/js/jquery.ztree.core.js"></script>
<script src="${path}/resources/echarts/echarts.min.js"></script>
<script src="${path}/resources/echarts/theme/default.js"></script>
 <!-- 
<script src="${path}/resources/echarts/world.js"></script>
<script src="${path}/resources/echarts/china.js"></script>
-->
<script type="text/javascript" src="${path}/rptSaleDayChart.js"></script>
<script type="text/javascript" src="${path}/rptCheckDayChart.js"></script>
<script type="text/javascript" src="${path}/rptCheckMonthChart.js"></script>
<script type="text/javascript" src="${path}/rtpSaleMonthChart.js"></script>
<script type="text/javascript" src="${path}/js/main.js"></script>


<script type="text/javascript">
	
	
	var setting = {
		view : {
			showLine : false,
			showIcon : false,
			selectedMulti : false,
			dblClickExpand : false,
			addDiyDom : addDiyDom
		},
		data : {
			simpleData : {
				enable : true
			}
		},
		callback : {
			beforeClick : beforeClick,
			onClick : onClick
		}
	};

	function onClick(e, treeId, treeNode) {
		addTab(treeNode.name, '${path}' + treeNode.menuUrl);
	}

	var zNodes = [];

	function addDiyDom(treeId, treeNode) {
		var spaceWidth = 5;
		var switchObj = $("#" + treeNode.tId + "_switch"), icoObj = $("#" + treeNode.tId + "_ico");
		switchObj.remove();
		icoObj.before(switchObj);

		if (treeNode.level > 1) {
			var spaceStr = "<span style='display: inline-block;width:" + (spaceWidth * treeNode.level) + "px'></span>";
			switchObj.before(spaceStr);
		}
	}

	function beforeClick(treeId, treeNode, clickFlag) {
		var openflag = treeNode.open;
		var zTree = $.fn.zTree.getZTreeObj(treeId);//展开三级菜单
		if (treeNode.level == 0 && treeNode.isParent) {
			zTree.expandAll(false);
			zTree.expandNode(treeNode, !openflag, false, false, false);
			return false;
		} else if (treeNode.level != 0 && treeNode.isParent) {
			zTree.expandNode(treeNode, !openflag, false, false, false);
			return false;
		} else {
			return true;
		}
	}
	function initMenuTree(obj) {
		var treeObj = $("#" + obj);
		var functionTree;
		if ('i1_delivery' == obj) {
			functionTree = eval('${userSession.deliveryList}');
		} else if ('i1_sale' == obj) {
			functionTree = eval('${userSession.saleList}');
		} else if ('i1_sys' == obj) {
			functionTree = eval('${userSession.sysList}');
		} else if ('i1_rpt_mng' == obj) {
			functionTree = eval('${userSession.rptList}');
		} else if ('i1_analy' == obj) {
			functionTree = eval('${userSession.analyList}');
		}

		$.fn.zTree.init(treeObj, setting, functionTree);
		treeObj.hover(function() {
			if (!treeObj.hasClass("showIcon")) {
				treeObj.addClass("showIcon");
			}
		}, function() {
			treeObj.removeClass("showIcon");
		});

	}

	function updatePass() {
		addTab("修改密码", '${path}' + "/updatepassword.jsp");
	}

	function a(title, index) {
		$('#mainMenu').accordion('getSelected').css({
			"overflow-x" : "hidden",
			"overflow-y" : "auto"
		});
	}
	function b(title, index) {
		alert($('#mainMenu').accordion('getPanel', index).attr("style"));
		$('#mainMenu').accordion('getPanel', index).css({
			"overflow-x" : "hidden",
			"overflow-y" : "hidden"
		});
		alert($('#mainMenu').accordion('getPanel', index).attr("style"));
	}
	function panelCloseBefore() {
		$(this).css({
			"overflow-x" : "hidden",
			"overflow-y" : "hidden"
		});
	}
	function panelOpenBefore() {
		$(this).css({
			"overflow-x" : "hidden",
			"overflow-y" : "hidden"
		});
	}
	function panelOpenAfter() {
		$(this).css({
			"overflow-x" : "hidden",
			"overflow-y" : "auto"
		});
	}

	$(function() {
		initMenuTree('i1_delivery');
		initMenuTree('i1_sale');
		initMenuTree('i1_sys');
		initMenuTree('i1_rpt_mng');
		initMenuTree('i1_analy');
		
		// 加载首页报表
		loadRPT();
		//setInterval(loadRPT, 1 * 60 * 1000);	// 自动刷新会造成显示的图片太小
	});
	
     function loadRPT(){
 		hrptSaleDayChart();
		hrptCheckDayChart();
		hrptCheckMonthChart();
		hrtpSaleMonthChart();
     }
</script>
</head>
<body class="easyui-layout">
	<div data-options="region:'north'" class="n-north">
		<a class="logo"></a>
		<span class="wel">欢迎您！${userSession.sysUser.userName}</span>
		<div class="north-r">
			<ul class="nav-menu">
				<li class="quit">
					<a href="${path}/sys/logout.do" target="_parent">退出登录</a>
				</li>
				<li class="mes">用户手册</li>
				<li class="set" onclick="updatePass();">修改密码</li>
				<!--<li class="mes">留言</li> -->
				<!-- 				<li class="notice"> -->
				<!-- 					消息<span style="color: #ff6c6c;">(1)</span> -->
				<!-- 				</li> -->
				<!--<li class="ss">自定义查询</li> -->
				<!--<li class="sy">首页</li> -->
			</ul>
		</div>
	</div>
	<div id="region_west" data-options="region:'west',title:'功能导航',headerCls:'layout-panel-west-headCSS',fit:false" class="n-west">
		<div id="mainMenu" class="easyui-accordion" data-options="fit:true">
			<c:forEach var="panal" items="${userSession.panals}" varStatus="status">
				<div title="${panal.name}" data-options="iconCls:'${panal.iconSkin}',onBeforeCollapse:panelCloseBefore,onBeforeExpand:panelOpenBefore,onExpand:panelOpenAfter" style="padding: 10px;">
					<div class="zTreeDemoBackground left">
						<ul id="${panal.id}" class="ztree"></ul>
					</div>
				</div>
			</c:forEach>
			<c:if test="${userSession.panals.size()==0}">
				<div style="width: 100%;" align="center">无此操作权限</div>
			</c:if>
		</div>
	</div>
	<div data-options="region:'center'" class="n-center">
		<div id="tabs" class="easyui-tabs n-tabs" fit="true">
			<div title="首页" style="height:100%;">
				<div style="height:50%;padding:6px;box-sizing:border-box;" class="column">
					<div class="easyui-layout" data-options="fit:true" style="height: 100%;">
											
						<!-- begin售票日统计  min-width: 500px; min-height: 300px;-->
						<div data-options="region:'west',border:false" style="width: 50%;">
							<div id="rptSaleDayChart" style="width: 100%; height: 100%;"></div>
						</div>
						<!-- end售票日统计 -->
						
						<!-- begin检票日统计 -->
						<div data-options="region:'east',border:false" style="width:50%;margin-left:6px;">
							<div id="rptCheckDayChart" style="width: 100%; height: 100%;"></div>
						</div>
						<!-- end检票日统计  -->

					</div>
				</div>
				<div style="height:50%;padding:0px 6px 6px 6px;box-sizing:border-box;" class="column">
					<div class="easyui-layout" data-options="fit:true" style="height: 100%;">
						
						<!-- begin售票月统计 min-width: 500px; min-height: 300px;-->
						<div data-options="region:'west',border:false" style="width: 50%;">
							<div id="rtpSaleMonthChart" style="width: 100%; height: 100%;"></div>
						</div>
						<!-- end售票月统计 -->

						<!-- begin检票月统计 -->
						<div data-options="region:'east',border:false" style="width: 50%;margin-left:6px;">
							<div id="rptCheckMonthChart" style="width: 100%; height: 100%;"></div>
						</div>
						<!-- end 检票月统计 -->
					</div>
				</div>
			</div>

		</div>
	</div>
	<div data-options="region:'south'" class="n-south">中国印钞造币&nbsp;▪&nbsp;石家庄印钞有限公司&nbsp;&nbsp;&nbsp;版权所有&nbsp;©&nbsp;2017</div>
</body>
</html>