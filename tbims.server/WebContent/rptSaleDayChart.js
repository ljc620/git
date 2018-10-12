
function hrptSaleDayChart(){
	var ecTheme = 'default';
	var date = new Date();
	var sendTime = (date.getHours()<10?'0'+date.getHours():date.getHours() ) +":"+(date.getMinutes()<10?'0'+date.getMinutes():date.getMinutes());
	if(date.getHours<9){
		sendTime="09:00";
	}
	if(date.getHours()>=18)
		sendTime = "17:59";
	myChartSD = echarts.init(document.getElementById('rptSaleDayChart'), ecTheme);
	myChartSD.showLoading();
	$.ajax({
		url : $ctx + '/rptsale/saleDayFirst.do',
		type : "post",
		dataType:"json",
		data :{ sbeginTime:"08:00",
			  sendTime:sendTime
			  },
		success:function(data) {	
			var optionS = {
					title : {
						text : '当日售票情况',
						textStyle : {
							fontSize : 16,
							fontWeight : 'bolder',
							color : '#4071b3'
						}
					},
					tooltip : {
						trigger : 'axis'
					},
					toolbox : {
						show : true,
						feature : {				
		                    mark : {show: true},
		                    magicType : {show: true, type: ['line', 'bar']},//, 'stack', 'tiled'
		                    dataView : {show: false, readOnly: true},
		                    //dataZoom: {show: true},
		                    restore : {show: true, title: '刷新'},
				            saveAsImage : {show: true}
						},
						right:'25'
					},
					legend : {
						data : [ '数量' ]
					},
					dataZoom: [{type: 'slider'},{type: 'inside'}],
		            xAxis : [{
	                     type : 'category',
	                     boundaryGap: false,
	                     data :data.xValues/*,
	                    	 axisLabel:{
	                    	 interval:0,
	                         rotate:30 
	                     }*/
		            }],
		 			yAxis : [ {
		 				type : 'value',
		 				minInterval : 1
		 			}],
					series : [{
						name : "售票数量",
						type : "line",
						data:data.yValues,
						areaStyle:{normal:{opacity:0.3}},
		                markPoint : {
		                    data : [
		                        {type : 'max', name: '最大值'},
		                        {type : 'min', name: '最小值'}
		                    ]
		                },
		                markLine : {
		                    data : [
		                        {type : 'average', name: '平均值'}
		                    ]
		                }
					}]
		         };
			myChartSD.hideLoading();
			myChartSD.setOption(optionS); 
		},
		error : function(e) {
			myChartSD.hideLoading();
			getAjaxError(e);
		}
	});	
	myChartSD.on("restore",function(parmas){
		hrptSaleDayChart();
	});
}

