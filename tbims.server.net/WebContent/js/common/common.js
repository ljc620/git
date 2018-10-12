//显示消息
function showFadeMessage(title, msg) {
	$.messager.show({
		title:title,
		msg:msg,
		timeout : 4000,
		showType:'fade',
			 style:{
				 top:100
			 }
		});
}

//显示消息
function showFadeMessageParent(title, msg) {
	parent.$.messager.show({
		title:title,
		msg:msg,
		timeout : 4000,
		showType:'fade',
			 style:{
				 top:100
			 }
		});
}
function beginLoading() {
	$('#exec-loading').dialog({modal:true,title:'',closable:false}).dialog('open');
}
function closeLoading() {
	$('#exec-loading').dialog('close');
}
