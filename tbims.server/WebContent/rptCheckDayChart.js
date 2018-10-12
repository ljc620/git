
function hrptCheckDayChart(){
	var ecTheme = 'default';
	var date = new Date();
	var cendTime = (date.getHours()<10?'0'+date.getHours():date.getHours() ) +":"+(date.getMinutes()<10?'0'+date.getMinutes():date.getMinutes());
	if(date.getHours<9){
		cendTime="09:00";
	}
	if(date.getHours()>=18)
		cendTime = "17:59:59";
	myChartCD = echarts.init(document.getElementById('rptCheckDayChart'), ecTheme);
	myChartCD.showLoading();
	$.ajax({
		url : $ctx + '/rptsale/checkDayFirst.do',
		type : "post",
		dataType:"json",
		data :{ cbeginTime:"08:00",
			cendTime:cendTime
			},
		success:function(data) {	
			var optionC = {
					color: ["#b6a2de", "#f5994e", "#d87a80", "#5ab1ef" ],
					title : {
						text : '当日检票情况',
						textStyle : {
							fontSize : 16,
							fontWeight : 'bolder',
							color : '#4071b3'
						}
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
					tooltip : {
						trigger : 'axis'
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
						name : "检票数量",
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
			myChartCD.hideLoading();
			myChartCD.setOption(optionC); 
		},
		error : function(e) {
			myChartCD.hideLoading();
			getAjaxError(e);
		}
	});	
	myChartCD.on("restore",function(parmas){
		hrptCheckDayChart();
	}); 
}
