function hrptCheckMonthChart(){
	var ecTheme = 'default';
	var myChartCM = echarts.init(document.getElementById('rptCheckMonthChart'), ecTheme); 
	myChartCM.showLoading();
	$.ajax({
		url : $ctx + '/rptsale/checkMonthFirst.do',
		type : "post",
		dataType:"json",
		success:function(data) {	
			var option = {
					color: ["#b6a2de", "#f5994e", "#d87a80", "#5ab1ef" ],
				    title : {
				         text: '近30日检票情况',
				         textStyle:{
				        	 fontSize: 16,
				             fontWeight: 'bolder',
				             color: '#4071b3'
				         }
				     },
				     tooltip : {
				         trigger: 'axis'
				     },
		             toolbox: {
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
		             dataZoom: [{type: 'slider'},{type: 'inside'}],//, right:'86',left:'80'
		             legend: {
		                 data:['数量']
		             },
		             xAxis : [
		                 {
		                     type : 'category',
		                     boundaryGap: false,
		                     data :data.xValues/*,
		                    	 axisLabel:{
		                    	 interval:0,
		                         rotate:30 
		                     }*/
		                 }
		             ],
		             yAxis : [
		                 {
		                     type : 'value',
		                     minInterval:100
		                 } 
		             ],
		             series : [
		                 {
		                     name:"检票数量",
		                     type:"line",
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
		                 }
		             ]
		         };
			myChartCM.hideLoading();
			myChartCM.setOption(option); 
		},
		error : function(e) {
			myChartCM.hideLoading();
			getAjaxError(e);
		}
	});	
	myChartCM.on("restore",function(parmas){
		hrptCheckMonthChart();
	}); 
}