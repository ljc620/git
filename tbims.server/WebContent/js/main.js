/**
 * --------js常用工具----begin----------
 */

$.extend($.fn.validatebox.defaults.rules, {
	equalTo : {
		validator : function(value, param) {
			return $("#" + param[0]).val() == value;
		},
		message : '字段不匹配'
	}
});

/**
 * 获取当前浏览器的版本号
 * 
 * @returns {String}
 */
function getBroswerVersion() {
	// alert(navigator.appVersion);
	// alert(navigator.userAgent);
	var ieset = navigator.userAgent;
	// $.messager.alert("错误信息", ieset);
	if (ieset.indexOf("MSIE 6.0") > -1) {
		return "IE6";
	}
	if (ieset.indexOf("MSIE 7.0") > -1) {
		return "IE7";
	}
	if (ieset.indexOf("MSIE 8.0") > -1) {
		return "IE8";
	}
	if (ieset.indexOf("MSIE 9.0") > -1) {
		return "IE9";
	}
	if (ieset.indexOf("Chrome") > -1) {
		return "google";
	}

	return "OTHER";
}

/**
 * 设置本地cookies
 * 
 * @param name
 * @param value
 */
function setCookie(name, value) {// 两个参数，一个是cookie的名子，一个是值
	var Days = 300; // 此 cookie 将被保存 30 天
	var exp = new Date(); // new Date("December 31, 9998");
	exp.setTime(exp.getTime() + Days * 24 * 60 * 60 * 1000);
	document.cookie = name + "=" + escape(value) + ";expires=" + exp.toGMTString();
}

/**
 * 获取本地cookies
 * 
 * @param name
 * @returns
 */
function getCookie(name) {// 取cookies函数
	var arr = document.cookie.match(new RegExp("(^| )" + name + "=([^;]*)(;|$)"));
	if (arr != null)
		return unescape(arr[2]);
	return null;
}

/**
 * 获取项目路径
 * 
 * @returns
 */
function getContextPath() {
	var pathName = document.location.pathname;
	var index = pathName.substr(1).indexOf("/");
	var result = pathName.substr(0, index + 1);
	return result;
}

/**
 * 日期格式YYYY-MM-DD
 * 
 * @param date
 * @returns {String}
 */
formatterDate = function(date) {
	var day = date.getDate() > 9 ? date.getDate() : "0" + date.getDate();
	var month = (date.getMonth() + 1) > 9 ? (date.getMonth() + 1) : "0" + (date.getMonth() + 1);
	return date.getFullYear() + '-' + month + '-' + day;
};

/**
 * 日期格式YYYYMMDD
 * 
 * @param date
 * @returns {String}
 */
formatterDate2 = function(date) {
	var day = date.getDate() > 9 ? date.getDate() : "0" + date.getDate();
	var month = (date.getMonth() + 1) > 9 ? (date.getMonth() + 1) : "0" + (date.getMonth() + 1);
	return date.getFullYear() + "" + month + "" + day;
};

parserDate2 = function(s) {
	if (!s)
		return new Date();
	var y = parseInt(s.substr(0, 4), 10);
	var m = parseInt(s.substr(4, 2), 10);
	var d = parseInt(s.substr(6, 2), 10);
	if (!isNaN(y) && !isNaN(m) && !isNaN(d)) {
		return new Date(y, m - 1, d);
	} else {
		return new Date();
	}
};

/**
 * 为按钮注册回车单击事件
 * 
 * @param buttonid
 */
function registerEnter(buttonid) {
	// 搜索回车实现单击效果 //兼容 IE 火狐 谷歌
	document.onkeydown = function(e) {
		if (!e)
			e = window.event;
		if ((e.keyCode || e.which) == 13) {
			$(buttonid).click();
		}
	};
}
/**
 * 为所有带 enter 的类增加回车单击事件
 */
function enterAuto() {
	$(".enter").each(function(i, n) {
		$(this).addClass("easyui-tooltip");
		$(this).attr("title", "回车自动提交");
		registerEnter(this);
	});
}

/**
 * --------项目公用方法-----begin--------
 */

/**
 * 增加标签，存在则刷新
 * 
 * @param title
 * @param url
 */
function addTab(title, url) {
	if ($('#tabs').tabs('exists', title)) {
		$('#tabs').tabs('select', title);// 选中并刷新
		var currTab = $('#tabs').tabs('getSelected');
		if (url != undefined && currTab.panel('options').title != '首页') {
			$('#tabs').tabs('update', {
				tab : currTab,
				options : {
					content : createFrame(url, title)
				}
			});
		}
	} else {
		var content = createFrame(url, title);
		$('#tabs').tabs('add', {
			title : title,
			content : content,
			closable : true
		});
	}
	reLoadTabIframesForIE6();
}

/**
 * 关闭当前标签
 * 
 * @param tbnm
 */
function closeCurrTab(tbnm) {
	window.parent.$('#tabs').tabs('close', tbnm);
}

/**
 * 为标签创建窗体
 * 
 * @param url
 * @param title
 * @returns {String}
 */
function createFrame(url, title) {
	var s = '<iframe scrolling="auto" id="' + $.trim(title) + '" frameborder="0"  allowtransparency="true"  src="' + url + '" style="width:100%;height:100%;"></iframe>';
	return s;
}

function dialogFrame(id, url) {
	var s = '<iframe scrolling="no" allowtransparency="true"  frameborder="0" id="' + id + '" name="' + id + '" src="' + url + '" style="width:100%;height:100%;"></iframe>';
	return s;
}

// 刷新标签
function refreshCurrTab(title) {
	window.parent.$('#tabs').tabs('select', title);// 选中并刷新
	var currTab = window.parent.$('#tabs').tabs('getSelected'); // 获得当前tab
	var url = $(currTab.panel('options').content).attr('src');
	window.parent.$('#tabs').tabs('update', {
		tab : currTab,
		options : {
			content : createFrame(url, title)
		}
	});
}
/**
 * 刷新所有空白框架，此程序主要解决IE6动态加载iframe时不显示内容的问题
 */
function reLoadTabIframesForIE6() {
	var ieset = getBroswerVersion();
	if (ieset == "IE6") {
		var currTab1 = $('#tabs').tabs('getSelected');
		setTimeout(function() {
			refreshTab(currTab1);
		}, 0);
		function refreshTab(refresh_tab) {
			if (refresh_tab && refresh_tab.find('iframe').length > 0) {
				var _refresh_ifram = refresh_tab.find('iframe')[0];
				var refresh_url = _refresh_ifram.src;
				_refresh_ifram.src = refresh_url;
			}
		}
	}
}
/**
 * 与后台交互返回json对象错误处理 适用于 [jquery $.ajax]、[easyui datagrid ] 等
 * 
 * @param result
 * @returns
 */
function getAjaxError(result) {
	try {
		$.messager.progress("close");
		var resultJSON = $.parseJSON(result.responseText);
		$.messager.alert("错误", resultJSON.errorDescribe);
	} catch (e) {
		$.messager.alert("错误", result.responseText);
		return false;
	}
	return true;
}

/**
 * 与后台交互返回json字符串错误处理 试用于 [easyui form表单 提交] 等
 * 
 * @param result
 * @returns
 */
function getFormError(result) {
	try {
		// $.messager.progress("close");
		// 后台处理成功，返回值 void
		if (result == '') {
			return true;
		}

		// 后台处理失败，返回值为json 错误字符串
		var resultJSON = $.parseJSON(result);
		if (resultJSON.errorCode != '0') {
			$.messager.alert("错误", resultJSON.errorDescribe);
			return false;
		}

	} catch (e) {
		$.messager.alert("错误", e.description);
		return false;
	}

	return true;
}

/**
 * 
 * 清空easyui table的选择中
 * 
 * @param gridid
 */

function clsCheck(gridid) {
	$(gridid).datagrid('clearChecked');
}

/**
 * 隐藏页面上的元素
 * 
 * @param id
 */
function hideToolbar(id) {
	$("#" + id).toggle("slow");
}

/**
 * 展开ztree所有节点。
 * 
 * @param treeid
 */
function expandAll(treeid) {
	var $tree = $.fn.zTree.getZTreeObj(treeid);
	$tree.expandAll(true);
}
/**
 * 折叠ztree所有节点,并打开根节点
 * 
 * @param treeid
 */
function collapseAll(treeid) {
	var $tree = $.fn.zTree.getZTreeObj(treeid);
	$tree.expandAll(false);
}

/**
 * 得到树的根节点
 * 
 * @param treeObj
 */
function getRoot(treeObj) {
	// 返回一个根节点
	var node = treeObj.getNodesByFilter(function(node) {
		return node.level == 0
	}, true);
	return node;
}
/**
 * 得到树的根节点集合
 * 
 * @param treeObj
 */
function getRoots(treeObj) {
	// 返回根节点集合
	var nodes = treeObj.getNodesByFilter(function(node) {
		return node.level == 0
	});
	return nodes;
}

/**
 * 判断panel、window、dialog 是否打开
 * 
 * @param id
 * @returns 打开的时候返回false,隐藏的时候返回true
 */
function isClose(id) {
	return $(id).parent().is(":hidden");
}

/**
 * 为只读文本框或不允许改变文本框增加样式
 */
function initInputReadOnly() {
	$("input[readOnly='readonly']").each(function(i, n) {
		$(n).parent(".textbox:eq(0)").addClass("textbox-readonly");
	});
	$(".bf-nochange").each(function(i, n) {
		$(n).next(".textbox:eq(0)").addClass("bf-nochange");
	});
}

/**
 * 设置文本框不允改变的样式
 */
function inputNoChange(id, flag) {
	if (flag) {
		$(id).next(".textbox:eq(0)").addClass("bf-nochange");
	} else {
		$(id).next(".textbox:eq(0)").removeClass("bf-nochange");
	}
}

/**
 * --------项目公用配值-----begin--------
 */

/**
 * 获取默认每页页数
 * 
 * @returns {Number}
 */
function getPageSize() {
	return 10;
}
/**
 * 获取默认页数列表
 * 
 * @returns {Array}
 */
function getPageList() {
	return [ 5, 10, 15, 20, 25, 30, 50, 100, 300 ];
}

/**
 * 性别映射
 * 
 * @param value
 * @returns {String}
 */
function getSex(value) {
	if (value == '1') {
		return '男';
	} else if (value == '2') {
		return '女';
	} else {
		return '未知';
	}
}

/**
 * --------项目公用配值-----end--------
 */

/**
 * --------公用变量--------
 */
var $ctx = ""; // 项目根路径，即项目名

/**
 * DateBox控件增加一个清空按钮
 */
$.fn.datebox.defaults.cleanText = '清空';
var buttons = $.extend([], $.fn.datebox.defaults.buttons);
   buttons.splice(1, 0, {
       text: function (target) {
           return $(target).datebox("options").cleanText
       },
       handler: function (target) {
           $(target).datebox("setValue", "");
           $(target).datebox("hidePanel");
       }
   });
   $.extend($.fn.datebox.defaults, {
       buttons: buttons
});
   
/**
 * 指定列datagrid求和
 * @param gridTable
 * @param colName
 * @returns {Number}
 */
function compute(gridTable,colName) {
     var rows = $(gridTable).datagrid('getRows');
     var total = 0;
     for (var i = 0; i < rows.length; i++) {
       total += parseFloat(rows[i][colName]);
     }
     return total;
}
   
/**
 * --------页面公共初始化--------
 */
$(function() {
	$ctx = getContextPath();

	// 为所有带 enter 的类增加回车单击事件
	enterAuto();
	
});
