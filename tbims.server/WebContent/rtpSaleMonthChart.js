function hrtpSaleMonthChart(){
	var ecTheme = 'default';
	var myChartSM = echarts.init(document.getElementById('rtpSaleMonthChart'), ecTheme); 
	myChartSM.showLoading();
	$.ajax({
		url : $ctx + '/rptsale/saleMonthFirst.do',
		type : "post",
		dataType:"json",
		success:function(data) {	
			var option = {
				  title : {
				        text: '近30日售票情况',
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
		                     name:"售票数量",
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
			myChartSM.hideLoading();
			myChartSM.setOption(option); 
		},
		error : function(e) {
			myChartSM.hideLoading();
			getAjaxError(e);
		}
	});	
	myChartSM.on("restore",function(parmas){
		hrtpSaleMonthChart();
	}); 
}