function updChangeUser(){
	if (!$('#fm').form('validate')) {
		return false;
	}
	$.messager.confirm('提示', '确定保存换票人吗？', function(r) {
		if (r) {
			$.messager.progress({
				text : '正在处理,请稍候...'
			});

			$('#fm').form('submit', {
				url : $ctx + "/teamOrder/updChangeUser.do",
				success : function(e) {
					$.messager.progress("close");
					if (!getFormError(e)) {
						return false;
					}
					$.messager.show({
						showType : 'slide',
						title : "提示信息",
						msg : '保存成功',
						timeout : 2000
					});
					parent.$('#editDialog').dialog('close');
				}
			}, 'json');
		}
	});
}