$(function() {
	$myTable = $('#ruletable');
	createRuleTable();
	$("#w0").prop("checked",true);
	$("#w1").prop("checked",true);
	$("#w2").prop("checked",true);
	$("#w3").prop("checked",true);
	$("#w4").prop("checked",true);
	$("#w5").prop("checked",true);
	$("#w6").prop("checked",true);
	$("#checkall").prop("checked",true);
	$("#checkall").change(function(){
			$("#w0").prop("checked",this.checked);
			$("#w1").prop("checked",this.checked);
			$("#w2").prop("checked",this.checked);
			$("#w3").prop("checked",this.checked);
			$("#w4").prop("checked",this.checked);
			$("#w5").prop("checked",this.checked);
			$("#w6").prop("checked",this.checked);
	});
});
function createRuleTable() {
	$myTable.datagrid({
		singleSelect: true,
		fitColumns : false,
		striped:true,
		fit:true,
		idField:'beginDt',
		columns : [ [// 显示的列 
         {
 			field : 'beginDt',
 			title : '有效开始日期',
 			align : 'center',
 			width:120
 		}, {
 			field : 'endDt',
 			title : '有效结束日期',
 			align : 'center',
 			width:120
 		},	             
		{
			field : 'beginTime',
			title : '有效开始时间',
			align : 'center',
			width:100
		}, {
			field : 'endTime',
			title : '有效结束时间',
			align : 'center',
			width:100
		}
		, {
			field : 'w0',
			title : '星期日',
			align : 'center',
			width:50,
			formatter : getYN
		}
		, {
			field : 'w1',
			title : '星期一',
			align : 'center',
			width:50,
			formatter : getYN
		}
		, {
			field : 'w2',
			title : '星期二',
			align : 'center',
			width:50,
			formatter : getYN
		}
		, {
			field : 'w3',
			title : '星期三',
			align : 'center',
			width:50,
			formatter : getYN
		}
		, {
			field : 'w4',
			title : '星期四',
			align : 'center',
			width:50,
			formatter : getYN
		}
		, {
			field : 'w5',
			title : '星期五',
			align : 'center',
			width:50,
			formatter : getYN
		}, {
			field : 'w6',
			title : '星期六',
			align : 'center',
			width:50,
			formatter : getYN
		},  {
			field : 'caozuo',
			title : '操作',
			align : 'center',
			halign: 'center',
			width:50,
			formatter : getstr
		}] ]/*,
		toolbar : '#mytable-buttons'*/
	});

}

function getstr(value, row, index) {
	var s1;
	s1= '<a href="javascript:void(0)" title="删除" class="opr-btn icon-del" onclick="delRule(this)"></a> ';
	return s1;
}
function getRowIndex(target) {
	var tr = $(target).closest('tr.datagrid-row');
	return parseInt(tr.attr('datagrid-row-index'));
}
function delRule(obj){
	var index =getRowIndex(obj);
	$myTable.datagrid('deleteRow', index);

}

function getYN(value, row, index){
	if("1"==value){
		return "是";
	}
	else{
		return "否";
	}
}


/**
 *  新增票种
 */

function addTicketType() {
var url = $ctx + '/pages/sys/tickettypemng/addTicketType.jsp';
top.addTab("新增票种",url);
}

/**
 * 新增规则
 */
function addRule(){
	var beginTime=$('#beginTime').timespinner('getValue');
	var endTime=$('#endTime').timespinner('getValue');
	var beginDt=$('#beginDt').datebox('getValue');
	var endDt=$("#endDt").datebox('getValue'); 
	var w0="";
	var w1="";
	var w2="";
	var w3="";
	var w4="";
	var w5="";
	var w6="";
	if($("#w0").prop("checked")==true){
		w0="1";
	}
	else{
		w0="0";
	}	
	if($("#w1").prop("checked")==true){
		w1="1";
	}
	else{
		w1="0";
	}
	if($("#w2").prop("checked")==true){
		w2="1";
	}
	else{
		w2="0";
	}
	if($("#w3").prop("checked")==true){
		w3="1";
	}
	else{
		w3="0";
	}
	if($("#w4").prop("checked")==true){
		w4="1";
	}
	else{
		w4="0";
	}
	if($("#w5").prop("checked")==true){
		w5="1";
	}
	else{
		w5="0";
	}
	if($("#w6").prop("checked")==true){
		w6="1";
	}
	else{
		w6="0";
	}
	if(beginTime==""||endTime==""||beginDt==""||endDt==""){
		$.messager.alert('系统提示','日期和时间全部为必输项');
		return false;
	}
	if(beginTime>=endTime){
		$.messager.alert('系统提示','开始时间不能大于结束时间');
		return false;
	}
	if(beginDt>endDt){
		$.messager.alert('系统提示','开始日期不能大于结束日期');
	   return false;
	}
	if(w0=="0"&&w1=="0"&&w2=="0"&&w3=="0"&&w4=="0"&&w5=="0"&&w6=="0"){
		$.messager.alert('系统提示','周日到周六至少选择一个');
		return false;
	}
	var rows = $myTable.datagrid('getRows');
	 for(var j=0;j<rows.length;j++)	{
		 if(beginDt<rows[j].endDt){
			 $.messager.alert('系统提示','日期请顺序添加，且不要有交集');
				return false; 
		 }
	 }
	$myTable.datagrid('appendRow',{
		ruleId:$('#beginDt').datebox('getValue'),
		beginDt:beginDt,
		endDt:endDt,
		beginTime:beginTime,
		endTime:endTime,
		w0:w0,
		w1:w1,
		w2:w2,
		w3:w3,
		w4:w4,
		w5:w5,
		w6:w6
	});
}

/**
 * 保存票种
 * @returns {Boolean}
 */
function saveTicketType(){
	if (!$('#fm').form('validate')) {
		return false;
	}
	var rows = $myTable.datagrid('getRows');
	var myJSONText=JSON.stringify(rows);
	$('#ruleListStr').val(myJSONText);
	$.messager.confirm('提示', '确定保存信息吗？', function(r) {
		if (r) {
			$.messager.progress({
				text : '正在处理,请稍候...'
			});

			$('#fm').form('submit', {
				url : $ctx + "/ticketTypeMng/addTicketType.do",
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
					refreshCurrTab("票种管理");
					closeCurrTab('新增票种');
				}
			}, 'json');
		}
	});
}


