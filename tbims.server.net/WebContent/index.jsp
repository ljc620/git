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
%>
<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
<meta http-equiv=X-UA-Compatible content="IE=edge,chrome=1">
<title>票务综合管理系统</title>
<link rel="stylesheet" href="${path}/css/easyui.css" />
<link rel="stylesheet" href="${path}/css/mainstyle.css" />
<link rel="shortcut icon" href="${path}/css/icons/favicon.ico" >

<script src="${path}/resources/jquery-easyui/jquery.min.js"></script>
<script src="${path}/resources/jquery-easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${path}/resources/jquery-easyui/locale/easyui-lang-zh_CN.js"></script>

<link rel="stylesheet" href="${path}/resources/ztree/css/zTreeStyle/zTreeStyle.css" type="text/css">
<script type="text/javascript" src="${path}/resources/ztree/js/jquery.ztree.core.js"></script>

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
		var zTree = $.fn.zTree.getZTreeObj(treeId);
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
		if ('i1_sale_out' == obj) {
			functionTree = eval('${userSession.saleList}');
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
	function showNotice() {
		addTab("公告信息", '${path}' + "/pages/sys/noticeMng/noticeMng.jsp");
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
		//initMenuTree('i1_delivery');
		initMenuTree('i1_sale_out');
		/* initMenuTree('i1_sys');
		initMenuTree('i1_rpt_mng'); */
		//initTab();
		//initTable();
		if ('${userSession.sysOutlet.outletType}' == '05') {
			addTab("销售记录登记", '${path}' + "/pages/saleReg/saleRegMng.jsp");
		} else {
			addTab("团队票订单预订", '${path}' + "/pages/sale/orderapply.jsp");
		}
	});
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
				<li class="notice" onclick="showNotice();">公告</li>
				<!--<li class="ss">自定义查询</li> -->
				<!--<li class="sy">首页</li> -->
			</ul>
		</div>
	</div>
	<div id="region_west" data-options="region:'west',title:'功能导航',headerCls:'layout-panel-west-headCSS'" class="n-west">
		<div class="easyui-accordion" style="width: 100%;">
			<c:forEach var="panal" items="${userSession.panals}" varStatus="status">
				<%-- <div title="${panal.name}" data-options="iconCls:'${panal.iconSkin}',onBeforeCollapse:panelCloseBefore,onBeforeExpand:panelOpenBefore,onExpand:panelOpenAfter" style="padding: 10px;"> --%>
				<div class="zTreeDemoBackground left">
					<ul id="${panal.id}" class="ztree"></ul>
				</div>
				<!-- </div> -->
			</c:forEach>
			<c:if test="${userSession.panals.size()==0}">
				<div style="width: 100%;" align="center">无此操作权限</div>
			</c:if>
		</div>
	</div>
	<div data-options="region:'center'" class="n-center">
		<div id="tabs" class="easyui-tabs n-tabs" fit="true"></div>
	</div>
	<div data-options="region:'south'" class="n-south">中国印钞造币&nbsp;▪&nbsp;石家庄印钞有限公司&nbsp;&nbsp;&nbsp;版权所有&nbsp;©&nbsp;2017</div>
</body>
</html>