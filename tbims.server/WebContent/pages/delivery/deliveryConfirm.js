$(function() {
	$myTable = $('#tickettable');
	$deliverytable = $('#deliverytable');
	var applyId = $('#applyId').val();
	var par={applyId:applyId};
	createTicketTable(par);
	createDeliveryTable();
	$(":radio").click(function(){
		var thisval = $(this).val();
		if('1'==thisval){
			$('#chestId').textbox("readonly",false);	
			$('#boxId').textbox("readonly",true);	
			$('#boxId').textbox('setValue',null);	
			$('#beginNo').numberbox('setValue',null);
			$('#endNo').numberbox("setValue",null);	
			$('#beginNo').numberbox("readonly",true);	
			$('#endNo').numberbox("readonly",true);	
			$('#boxId').textbox('clear');	
			$('#beginNo').numberbox('clear');
			$('#endNo').numberbox('clear');	
		}else if('2'==thisval){
			$('#boxId').textbox("readonly",false);
			$('#chestId').textbox("readonly",true);
			$('#chestId').textbox('setValue',null);	
			$('#beginNo').numberbox('setValue',null);
			$('#endNo').numberbox("setValue",null);	
			$('#beginNo').numberbox("readonly",true);	
			$('#endNo').numberbox("readonly",true);
			$('#chestId').textbox('clear');	
			$('#beginNo').numberbox('clear');
			$('#endNo').numberbox('clear');	
		}
		else{
			$('#chestId').textbox("setValue",null);
			$('#boxId').textbox("setValue",null);
			$('#chestId').textbox("readonly",true);	
			$('#boxId').textbox("readonly",true);	
			$('#beginNo').numberbox("readonly",false);	
			$('#endNo').numberbox("readonly",false);
			$('#boxId').textbox('clear');	
			$('#chestId').textbox('clear');
			$('#beginNo').numberbox('clear');
			$('#endNo').numberbox('clear');	
		}
	});
/*	 $("#chestId").next().children().eq(0).keydown(function(event){
	        if(event.keyCode==13){
	            var chestId = $("#chestId").numberbox("getValue");
	            $.post($ctx + '/deliveryExam/getTicketNo.do',
	                    {chestId:chestId},
	                    function(data){
	                        if(data == ""){
	                            alert("库中无此箱号");
	                        }else{
	                        	for (var i = 0; i < data.length; i++) {
	                        		if(data[0].beginNum==null||data[0].endNum==null){
	    	                            alert("库中无此箱号");
	                        		}
	                        		$('#beginNo').numberbox('setValue',data[0].beginNum);
		                			$('#endNo').numberbox("setValue",data[0].endNum);	
	                        	}
	                        }
	                    }
	            ).error(function(e){
	            	getAjaxError(e);
	            });
	        }
	    });
	  $("#boxId").next().children().eq(0).keydown(function(event){
	        if(event.keyCode==13){
	            var boxId = $("#boxId").numberbox("getValue");
	            $.post($ctx + '/deliveryExam/getTicketNo.do',
	                    {boxId:boxId},
	                    function(data){
	                        if(data == ""){
	                            alert("库中无此箱号");
	                        }else{
	                        	for (var i = 0; i < data.length; i++) {
	                        		if(data[0].beginNum==null||data[0].endNum==null){
	    	                            alert("库中无此盒号");
	                        		}
	                        		$('#beginNo').numberbox('setValue',data[0].beginNum);
		                			$('#endNo').numberbox("setValue",data[0].endNum);	
	                        	}
	                        }
	                    }
	            ).error(function(e){
	            	getAjaxError(e);
	            });
	        }
	    });*/
});

function createTicketTable(params) {
	$myTable.datagrid({
		nowrap : true,// 是否只显示一行，即文本过多是否省略部分。
		striped : true,
		url : $ctx + '/deliveryExam/applyDetail.do', // controller地址
		onLoadSuccess : function(e) {
		},
		onLoadError : function(e) {
			getAjaxError(e);
		},
		idField : 'applyExamId',
		fitColumns : false,
		fit:true,
		rownumbers : true,
		queryParams:params,
		singleSelect : true,
		striped : true,
		fit:true,
		columns : [ [// 显示的列       
		{
			field : 'applyExamId',
			title : '门票种类',
			hidden : true,
			align : 'center',
		},{
			field : 'ticketTypeId',
			title : '门票种类',
			hidden : true,
			align : 'center'
		},{
			field : 'ticketTypeName',
			title : '门票种类',
			width : 120,
			align : 'center'
		}, {
			field : 'examNum',
			title : '配送数量(张)',
			width : 120,
			align : 'center'
		}] ]
	});

}
function createDeliveryTable() {
	$deliverytable.datagrid({
		singleSelect: true,
		fitColumns : true,
		rownumbers : true,
		striped:true,
		singleSelect : true,
		fit:true,
		idField:'chestId',
		columns : [ [// 显示的列           
		{
			field : 'chestId',
			title : '箱号',
			align : 'center',
			width : 100
		},{
			field : 'ticketTypeId',
			title : '门票种类',
			hidden : true,
			align : 'center',
			width : 100
		},{
			field : 'ticketTypeName',
			title : '门票种类',
			align : 'center',
			width : 100
		}, {
			field : 'ticketNum',
			title : '张数',
			editor : "text",
			align : 'center',
			width : 100
		}, {
			field : 'beginNo',
			title : '起号',
			editor : "text",
			align : 'center',
			width : 100
		}, {
			field : 'endNo',
			title : '止号',
			editor : "text",
			align : 'center',
			width : 100
		}, {
			field : 'caozuo',
			title : '操作',
			align : 'center',
			halign: 'center',
			width : 100,
			formatter : getstr
		}] ],
		toolbar : '#mytable-buttons'
	});

}
function getstr(value, row, index) {
	var s1;
	s1= '<a href="javascript:void(0)" title="删除" class="opr-btn icon-del" onclick="delThis(this)"></a> ';
	return s1;
}
function getRowIndex(target) {
	var tr = $(target).closest('tr.datagrid-row');
	return parseInt(tr.attr('datagrid-row-index'));
}
function delThis(obj){
	var index =getRowIndex(obj);
	$deliverytable.datagrid('deleteRow', index);

}

function addChest(){
	var checkVal=$('input:radio:checked').val();
	var chestId="";
	var beginNo="";
	var endNo="";
	var boxId="";
	if("1"==checkVal){
	   chestId=$('#chestId').textbox('getValue');
	   beginNo = $('#beginNo').numberbox('getValue');	
	   endNo = $('#endNo').numberbox('getValue');	
	   if(""==chestId){
		   $.messager.alert('系统提示','请扫描或填写箱号');
		   return false;
	   }
	}else if("2"==checkVal){
		boxId=$('#boxId').textbox('getValue');
		beginNo = $('#beginNo').numberbox('getValue');	
		endNo = $('#endNo').numberbox('getValue');	
		   if(""==boxId){
			   $.messager.alert('系统提示','请扫描或填写盒号');
			   return false;
		   }
	}
	else{
		beginNo = $('#beginNo').numberbox('getValue');	
		endNo = $('#endNo').numberbox('getValue');	
		if(""==beginNo||""==endNo){
			$.messager.alert('系统提示','请扫描或填写起止号');
			return false;
		}
	}
	
	$.messager.progress({
		text : '正在处理,请勿重复提交数据,请稍候...'
	});
	
	  $.ajax({
			url : $ctx + '/deliveryExam/listChest.do',
			type : "post",
			data :{ chestId:chestId,
					beginNo:beginNo,
					endNo:endNo,
					boxId:boxId
			},
			success:function(mydata) {
				if(mydata==null||mydata.length==0){
					$.messager.progress("close");
					$.messager.alert('系统提示','未查询到符合条件的数据');
					return false; 	
				}
				if (mydata) {
					var rows = $deliverytable.datagrid('getRows');
					for (var i = 0; i < mydata.length; i++) {
					for(var j=0;j<rows.length;j++)	{
						 if(mydata[i].beginNo>=rows[j].beginNo&&mydata[i].endNo<=rows[j].endNo){
							 $.messager.progress("close");
							 $.messager.alert('系统提示','请不要重复添加');
							 return false; 
						 }
					 }
					 //$deliverytable.datagrid('appendRow',mydata[i]);
					}
					
					var oldDatas=$deliverytable.datagrid('getRows');
					var newDatas=$.merge(oldDatas,mydata);
					$deliverytable.datagrid('loadData',newDatas);
				}
				$('#boxId').textbox('clear');
				$('#chestId').textbox('clear');
				$('#beginNo').numberbox('clear');
				$('#endNo').numberbox('clear');
				$.messager.progress("close");
			},
			error : function(e) {
				getAjaxError(e);
			}
		});	
	}
function myconfirm(){
	var rows = $deliverytable.datagrid('getRows');
	var myJSONText=JSON.stringify(rows);
	//$('#detailStr').val(myJSONText);
	var applyId=$('#applyId').val();
	if(rows==null||rows.length==0){
		alert("请添加票后再配送");
		return false;
	}
	$.messager.confirm('提示', '提交后不可修改,确定配送吗？', function(r) {
		if (r) {
			$.messager.progress({
				text : '正在处理,请稍候...'
			});
			$.ajax({
				url : $ctx + "/deliveryExam/confirmDelivery.do",
				type : "post",
				data : {'detailStr':myJSONText,
					'applyId':applyId	
				},
				success : function(e) {
					$.messager.progress("close");
					$.messager.show({
						showType : 'slide',
						title : "提示信息",
						msg : '操作成功',
						timeout : 2000
					});
					refreshCurrTab("门票出库配送");
					closeCurrTab('配送确认');
				},
				error : function(e) {
					getAjaxError(e);
				}
			});
		}
	
		/*
		if (r) {
			$.messager.progress({
				text : '正在处理,请稍候...'
			});

			$('#fm').form('submit', {
				url : $ctx + "/deliveryExam/confirmDelivery.do",
				success : function(e) {	
					$.messager.progress("close");
					if (!getFormError(e)) {
						return false;
					};
					$.messager.show({
						showType : 'slide',
						title : "提示信息",
						msg : '保存成功',
						timeout : 2000
					});
					
					refreshCurrTab("门票出库配送");
					closeCurrTab('配送确认');
					
				}
			}, 'json');
		}
	*/});
	
}



