$(function() {
	$myTable = $('#mytable');
	
	createOrgTable();
});
function createOrgTable() {
	$myTable.datagrid({
		nowrap : false,// 是否只显示一行，即文本过多是否省略部分。
		striped : true,
		url : $ctx + '/noticeMng/listNotice.do', // controller地址
		onLoadSuccess : function(e) {
		},
		onLoadError : function(e) {
			getAjaxError(e);
		},
		idField : 'noticeId',
		fitColumns : false,
		rownumbers : true,
		singleSelect : true,
		striped : true,
		pagination : true,
		fit:true,
		columns : [ [// 显示的列
	     {
			field : 'title',
			title : '标题',
			width : 120,
			editor : "text",
			align : 'center'
				
		}, {
			field : 'content',
			title : '内容',
			width : 120,
			editor : "text",
			align : 'center',
			formatter : getcontent
		}, {
			field : 'opeUserName',
			title : '操作人',
			width : 150,
			editor : "text",
			align : 'center'
		}, {
			field : 'opeTime',
			title : '发布时间',
			width : 120,
			editor : "text",
			align : 'center'
		}, {
			field : 'lev',
			title : '优先级',
			width : 120,
			align : 'center',
			halign: 'center',
			formatter : getLev,
			width:100
		}, {
			field : 'caozuo',
			title : '操作',
			align : 'center',
			halign: 'center',
			formatter : getstr,
			width:100
		}] ],
		toolbar : '#mytable-buttons'
	});

}

function getstr(value, row, index) {
	var selRow = $myTable.datagrid('getRows')[index];
	var s2
	s2 = '<a href="javascript:void(0)" title="删除" class="opr-btn icon-del" onclick="delNotice(' + index + ')"></a> ';
	return s2;
}

function getLev(value, row, index) {
	if("1"==value){
		return "紧急";
	}
	else if("2"==value){
		return "重要";
	}
	else if("3"==value){
		return "一般";
	}
	else{
		return "";
	}
}


/**
 * 添加信息公告
 */
function addNotice() {
	$("#save-button").show();
    $("#update-button").hide();
var url = $ctx + '/pages/sys/noticeMng/addNotice.jsp';
var iframe='<iframe id="blackIframe" src='+url+' frameborder="0" width="790px" height="550px"></iframe>'
	$("#periodDialog").dialog({
		title:"新增信息公告",
		resizable:true,
		inline:true,
		cache : false,
		content:iframe,
		modal : true,
		href :url, 
		buttons : "#dlg-buttons",
		onclose:function(){$("#blackIframe").remove();},
	}).dialog("open");
   // $('#fm').form("clear");
}


function query() {
	var title = $('#title').textbox('getValue');
    var lev = $('#lev').combobox('getValue');
	var queryParams = {
		'title' : title,
		'lev':lev
	}
	$myTable.datagrid('options').queryParams = queryParams;
	$myTable.datagrid('load');
	clsCheck('#mytable');
	return true;
}


function getcontent(value, row, index){
	var selRow = $myTable.datagrid('getRows')[index];
	var noticeId=selRow.noticeId;
	var s1;
	//var newValue=value;
	//if(value.length>10){
	//	newValue=value.substr(0,10);}
	
	s1 = '<span class="datagrida"><a href="javascript:void(0)" title="详情"  onclick="showNotice(' + index + ')">详情</a> </span>';
	return s1;
}
function showNotice(index){
	
	var selRow = $myTable.datagrid('getRows')[index];
	var noticeId=selRow.noticeId;
	$("#showNoticeDlg").dialog({
		title: "公告信息",
		href: $ctx + "/noticeMng/showNotice.do?noticeId="+noticeId,
		closed: false,
		cache: false,
		top:125,
		resizable:true
	});}
function delNotice(index){
	var rowData = $myTable.datagrid("getRows")[index];
	var noticeId = rowData.noticeId;
	$.messager.confirm('提示', '您确定要删除[' + rowData.title + ']记录吗?', function(r) {
		if (r) {
			$.ajax({
				url : $ctx + "/noticeMng/delNotice.do",
				type : "post",
				data : {noticeId:noticeId},
				success : function(e) {
					$.messager.show({
						showType : 'slide',
						title : "提示信息",
						msg : '删除成功',
						timeout : 2000
					});
					$myTable.datagrid("reload");
					$.messager.progress("close");
				},
				error : function(e) {
					getAjaxError(e);
				}
			});
		}
	});

}

function saveNotice(url) {
	if (!$('#fm').form('validate')) {
		return false;
	}
	$.messager.confirm('提示', '确定保存信息吗？', function(r) {
		if (r) {
			$.messager.progress({
				text : '正在处理,请稍候...'
			});

			$('#fm').form('submit', {
				url : $ctx + url,
				success : function(e) {
					$.messager.progress("close");
					if (!getFormError(e)) {
						return false;
					}

					$('#periodDialog').dialog('close');
					$myTable.datagrid('reload'); // 重载行
					$myTable.datagrid('clearSelections');
					$.messager.show({
						showType : 'slide',
						title : "提示信息",
						msg : '保存成功',
						timeout : 2000
					});
				}
			}, 'json');
		}
	});
}


