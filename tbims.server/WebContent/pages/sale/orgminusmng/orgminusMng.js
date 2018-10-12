$(function() {
	$myTable = $('#mytable');
	createOrgTable();
});
function createOrgTable() {
	$myTable.datagrid({
		nowrap : true,// 是否只显示一行，即文本过多是否省略部分。
		striped : true,
		url : $ctx + '/orgMng/listOrg.do?orgBtype=org', // controller地址
		onLoadSuccess : function(e) {
		},
		onLoadError : function(e) {
			getAjaxError(e);
		},
		idField : 'orgId',
		fitColumns : false,
		rownumbers : true,
		singleSelect : true,
		striped : true,
		pagination : true,
		fit:true,
		columns : [ [// 显示的列
		{
			field : 'orgId',
			title : '组织机构代码',
			width : 120,
			editor : "text",
			align : 'center'
		}, {
			field : 'orgName',
			title : '机构名称',
			width : 120,
			editor : "text",
			align : 'left'
		}, {
			field : 'location',
			title : '机构所在地',
			width : 120,
			editor : "text",
			align : 'center'
		}, {
			field : 'legal',
			title : '法人代表',
			width : 100,
			editor : "text",
			align : 'center'
		}, {
			field : 'contact',
			title : '联系人',
			editor : "text",
			align : 'center',
			width : 100
		}, {
			field : 'tel',
			title : '联系电话',
			editor : "text",
			align : 'center',
			width : 150
		} , {
			field : 'limit',
			title : '额度',
			align : 'center',
			halign: 'center',
			width : 80,
			formatter : getLimit
		} , {
			field : 'advanceAmt',
			title : '预付款余额',
			align : 'right',
			halign: 'center',
			width : 100,
			formatter : getAdvance
		}] ],
		toolbar : '#mytable-buttons'
	});

}
function getLimit(value, row, index) {
	var s1;
	s1 = '<span class="datagrida"><a href="javascript:void(0)" title="额度详情"  onclick="getLimitInfo(' + index + ')">额度详情</a></span> ';
	return s1;
}
function getLimitInfo(index){
	var rowData = $myTable.datagrid("getRows")[index];
	var orgId = rowData.orgId;
	var url=$ctx + "/orgMng/beforeListLimitAmt.do?orgId="+orgId+"&flag=m";
	top.addTab("额度详情",url);
}
function getAdvance(value, row, index) {
	var selRow = $myTable.datagrid('getRows')[index];
	var s1;
	s1 = '<span class="datagrida"><a href="javascript:void(0)" title="预付款详情"   onclick="getAdvanceInfo(' + index + ')">'+value+'</a></span> ';
	return s1;
}
function getAdvanceInfo(index){
	var rowData = $myTable.datagrid("getRows")[index];
	var orgId = rowData.orgId;
	var url=$ctx + "/orgMng/beforeListAdvance.do?orgId="+orgId+"&flag=m";
	top.addTab("预付款详情",url);
}



function myQuery() {
	var orgName = $('#qorgName').textbox('getValue');

	var queryParams = {
		'orgName' : orgName
	}
	$myTable.datagrid('options').queryParams = queryParams;
	$myTable.datagrid('load');
	clsCheck('#mytable');
	return true;
}




