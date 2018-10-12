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
		striped : true,
		pagination : true,
		fit:true,
		columns : [ [// 显示的列	             
	     {
			field : 'title',
			title : '标题',
			halign: 'center',
			width : 500,
			align : 'left'
				
		},{
			field : 'noticeId',
			title : '标题',
			hidden:true,
			halign: 'center',
			align : 'left'
				
		}, {
			field : 'content',
			title : '内容',
			halign: 'center',
			width : 120,
			align : 'center',
			formatter : getstr
			/*styler: function(value,row,index){
						return 'color:#de7063;border: none;';
		}*/
		}, {
			field : 'opeUserName',
			title : '录入人',
			width : 200,
			editor : "text",
			halign: 'center',
			align : 'center'
		}, {
			field : 'lev',
			title : '优先级',
			width : 120,
			halign: 'center',
			align : 'center',
			formatter : getLev
		}, {
			field : 'opeTime',
			title : '发布时间',
			width : 150,
			editor : "text",
			halign: 'center',
			align : 'center'
		}] ],
		toolbar : '#mytable-buttons'
	});

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

function addTitle(value, row, index) {
	var str = '';
	if (value) {
		str = '<span title="' + value + '">' + value + '</span>';
	}
	return str;
}
function getstr(value, row, index){
	var selRow = $myTable.datagrid('getRows')[index];
	var noticeId=selRow.noticeId;
	var s1;
	/*var newValue=value;
	if(value.length>10){
		newValue=value.substr(0,10);
	}*/
	s1 = '<span class="datagrida"><a href="javascript:void(0)" title="'+value+'"  onclick="showNotice(' + index + ')">详情</a></span"> ';
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
	});
}	