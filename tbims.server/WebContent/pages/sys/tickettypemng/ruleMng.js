$(function() {
	$myTable = $('#ruletable');
	var ticketTypeId = $('#ticketTypeId').val();
	createRuleTable(ticketTypeId);
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
function createRuleTable(obj) {
	$myTable.datagrid({
		singleSelect: true,
		fit:true,
		fitColumns : true,
		striped:true,
		pagination : true,
		idField:'beginDt',
		url : $ctx + '/ticketTypeMng/ruleList.do?ticketTypeId='+obj, // controller地址
		onLoadSuccess : function(e) {
		},
		onLoadError : function(e) {
			getAjaxError(e);
		},
		columns : [ [// 显示的列    
       {
	   field : 'ruleId',
	   title : '规则编号',
	   hidden:true, 
	   align : 'left'
	   },
	   {
			field : 'beginDt',
			title : '有效开始日期',
			align : 'center'
		}, {
			field : 'endDt',
			title : '有效结束日期',
			align : 'center',
		},
		{
			field : 'beginTime',
			title : '有效开始时间',
			align : 'left'
		}, {
			field : 'endTime',
			title : '有效结束时间',
			align : 'center'
		}, {
			field : 'w0',
			title : '星期日',
			align : 'center',
			width:50,
			formatter : getYN
		}, {
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
		}, {
			field : 'caozuo',
			title : '操作',
			align : 'center',
			halign: 'center',
			formatter : getstr
		}] ],
		toolbar : '#mytable-buttons'
	});
}
	function getstr(value, row, index) {
		var selRow = $myTable.datagrid('getRows')[index];
		var s1, s2;
		//s1 = '<a href="javascript:void(0)" title="修改" class="opr-btn icon-edit" onclick="updateRule(' + index + ')"></a> ';
		s2 = '<a href="javascript:void(0)" title="删除" class="opr-btn icon-del" onclick="delRule(' + index + ')"></a> ';
		return  s2;
	}
	function getYN(value, row, index){
		if("1"==value){
			return "是";
		}
		else{
			return "否";
		}
	} 
	function delRule(index){
		var rowData = $myTable.datagrid("getRows")[index];
		var ruleId = rowData.ruleId;
		$.messager.confirm('提示', '您确定要删除此规则吗?', function(r) {
			if (r) {
				$.ajax({
					url : $ctx + '/ticketTypeMng/delTicketTypeRule.do',
					type : "post",
					data : {ruleId:ruleId},
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
	
	function addTypeRule(){
		if (!$('#fm').form('validate')) {
			return false;
		}
		var ticketTypeId=$('#ticketTypeId').val();
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
			$.messager.alert('系统提示','日期和时间全部是必输项');
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
		$.messager.confirm('提示', '您确定要添加规则吗?', function(r) {
			if (r) {
				$.ajax({
					url : $ctx + '/ticketTypeMng/addTicketTypeRule.do',
					type : "post",
					data : {
						ticketTypeId:ticketTypeId,
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
					},
					success : function(e) {
						$.messager.show({
							showType : 'slide',
							title : "提示信息",
							msg : '添加成功',
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
	
