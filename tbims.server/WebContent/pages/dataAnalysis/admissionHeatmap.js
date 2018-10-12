/**
 * 
 */

var optionC, ecTheme = '',//default
	myChart, startDate, endDate;

$(function(){
	var myDate = new Date();

	if(myDate.getMonth()>=6)
		$('#startDate').datebox('setValue',myDate.getFullYear() + '-' + (myDate.getMonth()-5) + '-' + myDate.getDate());
	else
		$('#startDate').datebox('setValue',(myDate.getFullYear()-1) + '-' + (myDate.getMonth()+7) + '-' + myDate.getDate());
	
	$('#endDate').datebox('setValue',myDate.getFullYear() + '-' + (myDate.getMonth()+1) + '-' + myDate.getDate());
	
	admissionChart();
});


function admissionChart(){
	startDate = $('#startDate').datebox('getValue');
	endDate = $('#endDate').datebox('getValue');
	myChart = echarts.init(document.getElementById('analysisChart'), ecTheme)
	myChart.showLoading();
	$.ajax({
		url : $ctx + '/dataAnalysis/listAdmissionData.do',
		type : "post",
		dataType:"json",
		data :{ startDate : startDate,
			endDate : endDate
		},
		success:function(data) {
			var max = 0;
			for(var i=0;i<data.length;i++) {
				if(max<data[i][1])
					max = data[i][1];
			}
			if(max>30000)
				max = 30000;
			optionC = {
					//color: ["#b6a2de", "#f5994e", "#d87a80", "#5ab1ef" ],
					title : {
						text : '游客入园热力分析',
						subtext: $('#startDate').datebox('getValue') + ' 至 ' + $('#endDate').datebox('getValue'),
						textStyle : {
							fontSize : 24,
							fontWeight : 'bolder',
							color : '#4071b3'
						},
						x:'center'
					},
					tooltip: {
//						trigger: 'item',
				        position: 'top',
				        //formatter: '{a} <br/> {c}'
				        formatter: function (params, ticket, callback) {  
				            var seriesName = params.seriesName;
				            var myDate = new Date(parseInt(params.data[0]));
				            var sdate = myDate.getFullYear() + '-' + (myDate.getMonth()+1) + '-' + myDate.getDate();
				            return seriesName + '<br/>'+sdate+'： ' + params.data[1]; //+ '<br />' + valueFliter  ;
				        }
				    },
				    visualMap: {
				        min: 0,
				        max: max,
				        calculable: true,
				        itemHeight: 500,
				        orient: 'horizontal',
				        left: 'center',
				        top: '60'
				    },
				    calendar: {
	                   range: [startDate, endDate],
	                   cellSize: ['auto', 40],
	                   top: 140,
	                   right: 30,
	                   left: 90,
	                   bottom: 40,
	                   dayLabel: {
	                       firstDay: 1, // 从周一开始
	                       nameMap: 'cn'
	                   },
	                   monthLabel: {
	                       nameMap: 'cn'
	                   },
	                   yearLabel: {
	                	   margin : 50
	                   }

	               },
				    series : [{
				    	name: '入园人数',
				        type: 'heatmap',
				        coordinateSystem: 'calendar',
				        data: data
				    }]
		         };
			myChart.hideLoading();
			myChart.setOption(optionC); 
		},
		error : function(e) {
			myChart.hideLoading();
			getAjaxError(e);
		}
	});	
}



