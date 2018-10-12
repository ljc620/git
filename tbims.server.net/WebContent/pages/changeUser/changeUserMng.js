$(function() {
	$myTable = $('#mytable');
	
	createOrgTable();
});
function createOrgTable() {
	$myTable.datagrid({
		nowrap : true,// 是否只显示一行，即文本过多是否省略部分。
		striped : true,
		url : $ctx + '/changeUser/listChangeUser.do', // controller地址
		onLoadSuccess : function(e) {
		},
		onLoadError : function(e) {
			getAjaxError(e);
		},
		idField : 'changeUserId',
		fitColumns : false,
		rownumbers : true,
		singleSelect : true,
		striped : true,
		pagination : true,
		fit:true,
		columns : [ [// 显示的列
		{
			field : 'changeUserName',
			title : '姓名',
			width : 120,
			editor : "text",
			align : 'left'
				
		}, {
			field : 'cardType',
			title : '证件类型',
			align : 'center',
			halign: 'center',
			formatter : getCardType,
			width:100
		} ,{
			field : 'cardId',
			title : '证件号码',
			width : 200,
			editor : "text",
			align : 'center'
		},{
			field : 'tel',
			title : '联系电话',
			width : 120,
			editor : "text",
			align : 'center'
		}, {
			field : 'mail',
			title : '电子邮箱',
			width : 200,
			editor : "text",
			align : 'center'
		},{
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

function getCardType(value, row, index) {
	if("01"==value){
		return "身份证";
	}
	else if("02"==value){
		return "户口本";
	}
	else{
		return "";
	}
}


function getstr(value, row, index) {
	var selRow = $myTable.datagrid('getRows')[index];
	var s1, s2;
	s1 = '<a href="javascript:void(0)" title="修改" class="opr-btn icon-edit" onclick="updateChangeUser(' + index + ')"></a> ';
	s2 = '<a href="javascript:void(0)" title="删除" class="opr-btn icon-del" onclick="delChangeUser(' + index + ')"></a> ';
	return s1 + s2;
}






/**
 * 添加换票人
 */

function addChangeUser() {
	$("#save-button").show();
    $("#update-button").hide();
    var default_left;
	var default_top; 
var url = $ctx + '/pages/changeUser/addChangeUser.jsp';
var iframe='<iframe id="sysRegioframe" src='+url+' frameborder="0" width="790px" height="550px"></iframe>'
	$("#regionDialog").dialog({
		title:"新增换票人",
		resizable:true,
		inline:true,
		cache : false,
		content:iframe,
		modal : true,
		href :url, 
		buttons : "#dlg-buttons",
		onclose:function(){$("#sysRegioframe").remove();},
		onOpen:function(){ 
			//dialog原始left
			default_left=$('#regionDialog').panel('options').left; 
			default_top=$('#regionDialog').panel('options').top;
			},
		onMove:function(left,top){ //鼠标拖动时事件
			var dd_width= $('#regionDialog').panel('options').width;//dialog的宽度
			var dd_height= $('#regionDialog').panel('options').height;//dialog的高度
			if(left<1||top<1){
			$('#regionDialog').dialog('move',{
			left:default_left,
			top:default_top
			}); 
			}
			}
	}).dialog("open");
    //$('#fm').form("clear");
}



function delChangeUser(index){
	var rowData = $myTable.datagrid("getRows")[index];
	var changeUserId = rowData.changeUserId;
	$.messager.confirm('提示', '您确定要删除[' + rowData.changeUserName + ']记录吗?', function(r) {
		if (r) {
			$.ajax({
				url : $ctx + "/changeUser/delChangeUser.do",
				type : "post",
				data : {changeUserId:changeUserId},
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
function saveChangeUser(url) {
	if (!$('#fm').form('validate')) {
		return false;
	}
	$.messager.confirm('提示', '确定保存信息吗？', function(r) {
		if (r) {
			$.messager.progress({
				text : '正在处理,请稍候...'
			});

			$('#fm').form('submit', {
				url : $ctx +url ,
				success : function(e) {
					$.messager.progress("close");
					if (!getFormError(e)) {
						return false;
					}
					$('#regionDialog').dialog('close');
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

function updateChangeUser(index) {
	$("#save-button").hide();
	$("#update-button").show();
	var default_left;
	var default_top; 
	var rowData = $myTable.datagrid("getRows")[index];
	var changeUserId = rowData.changeUserId;
	var url = $ctx + '/changeUser/beforeUpdteChangeUser.do?changeUserId='+changeUserId;
		$("#regionDialog").dialog({
			title:"修改换票人",
			resizable:true,
			inline:true,
			cache : false,
			modal : true,
			href :url, 
			buttons : "#dlg-buttons",
			onLoadError:function(e){
				getAjaxError(e);
			},
			onOpen:function(){ 
				//dialog原始left
				default_left=$('#regionDialog').panel('options').left; 
				default_top=$('#regionDialog').panel('options').top;
				},
			onMove:function(left,top){ //鼠标拖动时事件
				var dd_width= $('#regionDialog').panel('options').width;//dialog的宽度
				var dd_height= $('#regionDialog').panel('options').height;//dialog的高度
				if(left<1||top<1){
				$('#regionDialog').dialog('move',{
				left:default_left,
				top:default_top
				}); 
				}
				}
		}).dialog("open");
}

function myQuery() {
	$myTable.datagrid('options').queryParams = queryParams;
	$myTable.datagrid('load');
	clsCheck('#mytable');
	return true;
}