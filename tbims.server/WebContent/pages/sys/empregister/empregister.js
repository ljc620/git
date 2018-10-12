$(function() {
	$myTable = $('#mytable');
	createOrgTable();
});
function createOrgTable() {
	$myTable.datagrid({
		nowrap : true,// 是否只显示一行，即文本过多是否省略部分。
		striped : true,
		url : $ctx + '/empregister/empregister.do', // controller地址
		onLoadSuccess : function(e) {
		},
		onLoadError : function(e) {
			getAjaxError(e);
		},
		idField : 'empId',
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
 			field : 'empId',
 			title : '员工编号',
 			width : 80,
 			editor : "text",
 			align : 'center'
     		},{
			field : 'empName',
			title : '员工名称',
			width : 80,
			editor : "text",
			align : 'center'
			}, {
			field : 'department',
			title : '部门',
			width : 80,
			editor : "text",
			align : 'center'
			} ,{
			field : 'chipId',
			title : '芯片ID',
			width : 80,
			editor : "text",
			align : 'center'
	 		}, {
			field : 'stat',
			title : '员工状态',
			width : 80,
			editor : 'center',
			align : 'center',
			formatter : function(value, row, index) {
				if (value == 'Y') {
					value = '启用';
				} else if (value == 'N') {
					value = '禁用';
				}
				return value;
				}
			}, {
			field : 'chipStat',
			title : '卡片状态',
			width : 80,
			editor : 'center',
			align : 'center',
			formatter : function(value, row, index) {
				if (value == 'Y') {
					value = '可用';
				} else if (value == 'N') {
					value = '禁用';
				}
				return value;
				}
	 		},{
			field : 'cardType',
			title : '证件类型',
			align : 'center',
			halign: 'center',
			formatter : getCardType,
			width : 80
	 		}, {
			field : 'cardId',
			title : '证件号码',
			width : 200,
			editor : "text",
			align : 'center'
			},{
			field : 'photo',
			title : '相片',
			align : 'center',
			halign: 'center',
			formatter : getphoto,
			width : 80
	 		}, {
			field : 'mail',
 			title : '邮箱地址',
 			width : 200,
 			editor : "text",
 			align : 'center'
	 		},{
			field : 'gender',
			title : '性别',
			width : 80,
			editor : "center",
			align : 'center',
				formatter : function(value, row, index) {
					if (value == '1') {
						value = '男';
					} else if (value == '2') {
						value = '女';
					}
					return value;
					}	
	 		}, {
			field : 'tel',
			title : '联系电话',
			width : 150,
			editor : "text",
			align : 'center'
	 		},{
			field : 'opeUserName',
			title : '操作人',
			width : 200,
			editor : "text",
			align : 'center'
					
			}, {
			field : 'opeTime',
			title : '操作时间',
			width : 80,
			editor : "text",
			align : 'center'
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


/**
 *修改状态 
 * 
 */
function updateStat(stat) {
	var selRow = $myTable.datagrid("getSelections"); // 返回选中多行
	var sttText = '';
	if (stat == 'Y') {
		sttText = '启用';
	} else {
		sttText = '禁用';
	}
	if (selRow.length == 0) {
		alert("请至少选择一行数据!");
		return false;
	} else {
		var ids = [];
		for (var i = 0; i < selRow.length; i++) {
			// 获取自定义table 的中的checkbox值
			var id = selRow[i].empId; // OTRECORDID这个是你要在列表中取的单个id
			ids.push(id ); // 然后把单个id循环放到ids的数组中
		}
		var keyIds = ids.join(",");

		$.messager.confirm('提示', '您确定修改[' + selRow.length + ']条状态吗?', function(r) {
			if (r) {
				$.ajax({
					url : $ctx + "/empregister/updateStat.do",
					type : "post",
					data : {
						"empId" : keyIds,
						"stat" : stat
					},
					success : function(e) {
						$myTable.datagrid('reload');
						clsCheck("#mytable");// 清空选择的行
						$.messager.show({
							showType : 'slide',
							title : "提示信息",
							msg : "更新成功",
							timeout : 2000
						});
					},
					error : function(e) {
						getAjaxError(e);
					}
				});
			}
		});
	}

}

function getphoto(value, row, index){
	var selRow = $myTable.datagrid('getRows')[index];
	var empId=selRow.empId;
	var s1;
	s1 = '<span class="datagrida"><a href="javascript:void(0)" title="详情"  onclick="getlmg(' + index + ')">详情</a> </span>';
	return s1;
}
function getlmg(index){
	var selRow = $myTable.datagrid('getRows')[index];
	var empId=selRow.empId;
	$("#showNoticeDlg").dialog({
		title: "图片信息",
		href: $ctx + "/empregister/befShowImg.do?empId="+empId,
		closed: false,
		cache: false,
		top:125,
		resizable:true
	});}


function myQuery() {
	var empId = $('#empId').textbox('getValue');
    var empName = $('#empName').textbox('getValue');
    var department = $('#department').textbox('getValue');
    var stat = $('#stat').combobox('getValue');
	var queryParams = {
		'empId' : empId,
		'empName' : empName,
		'department' : department,
		'stat':stat
	}
	$myTable.datagrid('options').queryParams = queryParams;
	$myTable.datagrid('load');
	clsCheck('#mytable');
	return true;
}

