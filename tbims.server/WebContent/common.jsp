<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
<meta content=always name=referrer>
<link rel="stylesheet" href="${path}/css/easyui.css" />
<link rel="stylesheet" href="${path}/css/itstyle.css" type="text/css"></link>
<script src="${path}/resources/jquery-easyui/jquery.min.js"></script>
<script src="${path}/resources/jquery-easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${path}/resources/jquery-easyui/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript">
	var $functionCds = [];
	var $sessionid_user_cd='${userSession.userId}';

	//判断是否此权限根据权限代码
	function hasPriv(functionCd) {
		return $.inArray(functionCd, $functionCds) == -1 ? false : true;
	}

	$(function() {
		var funcTreeStr = eval('${userSession.functionListStr}');
		if ($.trim(funcTreeStr)) {
			$.each(funcTreeStr, function(i, node) {
				$functionCds.push(node.menuId);
			});
		}
	});
</script>

<script type="text/javascript" src="${path}/js/main.js"></script>