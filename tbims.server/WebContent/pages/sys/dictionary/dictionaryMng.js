function save(url) {
	if (!$('#fm').form('validate')) {
		return false;
	}
	$.messager.confirm('提示', '确定提交字典数据吗？', function(r) {
		if (r) {
			$.messager.progress({
				text : '正在处理,请稍候...'
			});
			$('#fm').form('submit', {
				url : $ctx + url,
				success : function(e) {
					$.messager.progress("close");
					if(!getFormError(e)){
						return false;
					}
					
					$('#dicMngDialog').dialog('close');
					$dg.datagrid('reload'); // 重载行
					$dg.datagrid('clearSelections');
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
/**
 * 增加字典
 */
function addDictionary() {
	$("#save-button").show();
	$("#update-button").hide();
	var url = $ctx + '/pages/sys/dictionary/dictionaryAdd.jsp';
		$("#dicMngDialog").dialog({
			title:"新增字典",
			resizable:true,
			inline:true,
			cache : false,
			modal : true,
			buttons : "#dlg-buttons",
			href:url
		}).dialog("open");
	$('#fm').form("clear");
}

/**
 * 批量删除字典
 * 
 * @returns {Boolean}
 */
function deleteDictionary(index) {
	var ids = [];
	if (index == null) {
		var selRow = $dg.datagrid("getSelections"); // 返回选中多行
		if (selRow.length == 0) {
			alert("请至少选择一行数据!");
			return false;
		}
		for (var i = 0; i < selRow.length; i++) {
			// 获取自定义table 的中的checkbox值
			var dictionary_id = selRow[i].dictionaryId; // OTRECORDID这个是你要在列表中取的单个id
			ids.push("'" + dictionary_id + "'"); // 然后把单个id循环放到ids的数组中
		}
	} else {
		var rowData = $dg.datagrid("getRows")[index];
		var dictionary_id = rowData.dictionaryId;
		ids.push("'" + dictionary_id + "'"); // 然后把单个id循环放到ids的数组中
	}
	var keyIds = ids.join(",");
	$.messager.confirm('提示', '您确定要删除[' + ids.length + ']条记录吗?', function(r) {
		if (r) {
			$.ajax({
				url : $ctx + "/dictionary/deleteDictionary.do",
				type : "post",
				data : "dictionaryId=" + keyIds,
				success : function(e) {
					$.messager.show({
						showType : 'slide',
						title : "提示信息",
						msg : '删除成功',
						timeout : 2000
					});
					$dg.datagrid("reload");
					$.messager.progress("close");
				},
				error : function(e) {
					getAjaxError(e);
				}
			});
		}
	});
}

/**
 * 更新字典
 * 
 * @param id
 * @returns {Boolean}
 */
function updateDictionary(index) {
	$("#save-button").hide();
	$("#update-button").show();
	var rowData = $dg.datagrid("getRows")[index];
	var dictionaryId = rowData.dictionaryId;
	var url = $ctx + '/dictionary/initBeforeUpdate.do?dictionaryId='+dictionaryId;
		$("#dicMngDialog").dialog({
			title:"修改字典",
			resizable:true,
			inline:true,
			cache : false,
			modal : true,
			href :url, 
			buttons : "#dlg-buttons",
			onLoadError:function(e){
				getAjaxError(e);
			}
		}).dialog("open");
}

/**
 * 精确搜索
 */
function queryDictionary() {
	var q_key = $("#q_key").textbox("getValue");
	var q_key_nm = $("#q_key_nm").textbox("getValue");
	var q_item_cd = $("#q_item_cd").numberbox("getValue");
	var q_item_name = $("#q_item_name").textbox("getValue");
	var q_res_stt = $("#q_stat").textbox("getValue");

	var queryParams = {
		"keyCd" : q_key,
		"keyName" : q_key_nm,
		"itemCd" : q_item_cd,
		"itemName" : q_item_name,
		"stat" : q_res_stt
	};
	$dg.datagrid('options').queryParams = queryParams;
	$dg.datagrid('load');
	clsCheck("#mytable");// 清空选择的行
	return true;
}

/**
 * 表格内操作按钮
 */
function getstr(value, row, index) {
	var s1//, s2;
	s1 = '<a href="javascript:void(0)" title="修改" class="opr-btn icon-edit" onclick="updateDictionary(' + index + ')"></a> ';
	//s2 = '<a href="javascript:void(0)" title="删除" class="opr-btn icon-del" onclick="deleteDictionary(' + index + ')"></a> ';
	return s1 //+ s2;
}

/**
 * 初始化列表
 */
function createMytable() {
	$dg.datagrid({
		nowrap : true,// 是否只显示一行，即文本过多是否省略部分。
		striped : true,
		url : $ctx + '/dictionary/listDictionary.do', // controller地址
		onLoadSuccess : function(e) {
			
		},
		onLoadError : function(e) {
			getAjaxError(e);
		},
		idField : 'dictionaryId',
		fitColumns : false,
		fit : true,
		checkbox : true,
		rownumbers : true,
		singleSelect : false,
		pagination : true, // 包含分页
		pageSize : getPageSize(),
		pageList : getPageList(),
		frozenColumns : [ [ {
			field : 'ck',
			checkbox : true
		} ] ],
		columns : [ [// 显示的列
		{
			field : 'keyCd',
			title : '字段名',
			width : 200,
			align:'left'
		}, {
			field : 'keyName',
			title : '字段中文名',
			width : 150,
			align:'center'
		}, {
			field : 'itemCd',
			title : '数据项代码',
			width : 120,
			align:'center'
		}, {
			field : 'itemName',
			title : '数据项名称' ,
			width : 150,
			align:'center'
		}, {
			field : 'itemVal',
			title : '数据项值',
			width : 150,
			align:'center'
		}, {
			field : 'stat',
			title : '状态',
			width : 80,
			align:'center',
			formatter:function(value,row,index){
				if(value=='Y'){
					value='启用'
				}else if(value=='N'){
					value='停用'
				}else{
					value=""
				}
				return value;
			},
			editor : "text"
		}, {
			field : 'orderNum',
			title : '序号',
			width : 50,
			align:'center'
		},{
			field : 'aaaa',
			title : '操作',
			width : 120,
			align : 'center',
			formatter : getstr
		} ] ],
		toolbar : '#mytable-buttons'
	});

}

$(function() {
	$dg = $('#mytable');

	// 创建数据列表
	createMytable();
	$("#q_stat").combobox({
		valueField:'id',
		textField:'text',
		editable:false,
		panelHeight:'auto',
		data:[
		      {
		    	  id:'',
		    	  text:'--全部--'
		      },{
		    	  id:'Y',
		    	  text:'启用 '
		      },{
		    	  id:'N',
		    	  text:'停用'
		      }
		]
	});
});
function init(){
	$("#stat").combobox({
		valueField:'id',
		textField:'text',
		editable:false,
		panelHeight:'auto',
		width:130,
		required:true,
		data:[
		    	{
		    	  id:'Y',
		    	  text:'启用 '
		      },{
		    	  id:'N',
		    	  text:'停用'
		      }
		]
	});
}