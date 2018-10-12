/**
 * 
 */

var optionC, ecTheme = 'default',
	myChart, startDate, endDate,
	selectIdx = -1;

$(function(){
	var myDate = new Date(),
	year = myDate.getFullYear(),
	month = myDate.getMonth(),
	days = myDate.getDate();

	if(month>0)
		$('#startDate').datebox('setValue',year+'-'+(month<10?'0'+month:month)+'-01');
	else
		$('#startDate').datebox('setValue',year+'-01-01');
	$('#endDate').datebox('setValue',year+'-'+((month+1)<10?'0'+(month+1):(month+1))+'-'+(days<10?'0'+days:days));
	
	admissionChart();
});


function admissionChart(){
	startDate = $('#startDate').datebox('getValue');
	endDate = $('#endDate').datebox('getValue');
	myChart = echarts.init(document.getElementById('analysisChart'), ecTheme)
//	myChart.showLoading();
	$.ajax({
		url : $ctx + '/dataAnalysis/listRegionAdmissionData.do',
		type : "post",
		dataType:"json",
		data :{ startDate : startDate,
			endDate : endDate
		},
		success:function(data) {
			var gataMax =0, regionMax = 0;
			for(var i=0;i<data.regionData.length;i++){
				if(data.regionData[i].value>regionMax)
					regionMax = data.regionData[i].value
			}
			for(var i=0;i<data.gataData.length;i++){
				if(data.gataData[i].value>gataMax)
					gataMax = data.gataData[i].value
			}
			optionC = {
					//color: ["#b6a2de", "#f5994e", "#d87a80", "#5ab1ef" ],
					title : {
						text : '游客入园通道分析(人次)',
						subtext: $('#startDate').datebox('getValue') + ' 至 ' + $('#endDate').datebox('getValue'),
						textStyle : {
							fontSize : 24,
							fontWeight : 'bolder',
							color : '#4071b3'
						},
						x:'center'
					},
					toolbox : {
						show : true,
						feature : {
		                    //mark : {show: true},
		                    magicType : {show: true, type: ['pie', 'funnel']},//
		                    dataView : {show: true, readOnly: true},
		                    //dataZoom: {show: true},
		                    restore : {show: true},
				            saveAsImage : {show: true}
						},
						right:'25',
						top:'15'
					},
				    legend: {
				        orient : 'vertical',
				        x : 'left',
				        top: '30',
				        left: '15',
				        data: data.legend
				    },
				    tooltip : {
				        trigger: 'item',
				        formatter: "{a} <br/>{b} : {c} ({d}%)"
				    },
				    calculable : false,
				    series : [
				              {
				                  name:'检票区域',
				                  type:'pie',
				                  selectedMode: 'single',
				                  startAngle: '45',
				                  selectedOffset: '35',
				                  //radius : [0, 70],
				                  radius: [0, '65%'],
				                  center: ['25%', '55%'],
				                  
				                  // for funnel
				                  x: '20%',
				                  width: '40%',
				                  funnelAlign: 'right',
				                  max: regionMax,
				                  
				                  itemStyle : {
				                      normal : {
				                          label : {
				                              position : 'inner'
				                          },
				                          labelLine : {
				                              show : false
				                          }
				                      }
				                  },
				                  data : data.regionData,
				                  label: {
				                      normal: {
				                          formatter: '{b}: {d}%',
				                      }
				                  }
				              },
				              {
				                  name:'检票闸机',
				                  type:'funnel',
				                  //selectedMode: 'single',
				                  startAngle: '120',
				                  //radius : [100, 140],
				                  //radius: ['50%', '65%'],
				                  
				                  // for funnel
				                  sort: 'none',
				                  x: '50%',
				                  width: '40%',
				                  height: '80%',
				                  y: '80',
				                  funnelAlign: 'left',
				                  max: gataMax,
				                  data : data.gataData//,
//				                  label: {
//				                      normal: {
//				                          formatter: ' {b|{b}：} {per|{d}%}  ',//' {b|{b}：}{c}  {per|{d}%}  '
//				                          backgroundColor: '#eee',
//				                          borderColor: '#aaa',
//				                          borderWidth: 0,
//				                          borderRadius: 4,
//				                          shadowBlur:3,
//				                          shadowOffsetX: 2,
//				                          shadowOffsetY: 2,
////				                          shadowColor: '#999',
//				                          // padding: [0, 7],
//				                          rich: {
//				                              b: {
//				                                  fontSize: 13,
//				                                  lineHeight: 28
//				                              },
//				                              per: {
//				                                  color: '#eee',
//				                                  backgroundColor: '#334455',
//				                                  padding: [2, 4],
//				                                  borderRadius: 2
//				                              }
//				                          }
//				                      }
//				                  }
				              }
				          ]
		         };
//			myChart.hideLoading();
			myChart.setOption(optionC); 
		},
		error : function(e) {
//			myChart.hideLoading();
			getAjaxError(e);
		}
	});	
			
	myChart.on("pieselectchanged", selectRegion);		// echarts 3
	myChart.on("legendselectchanged", selectLegend);
}

function selectLegend(param) {
	//alert("name: "+ param.name);
	var regions = [], serieData = optionC.series[0].data, hasSel = false;
	for(var j=0;j<serieData.length;j++){
		if(param.selected[serieData[j].name]){
			regions.push(serieData[j].regionId);
			if(serieData[j].selected)
				hasSel = true;
		}
	}

	//alert(hasSel);
	if(!hasSel) {
		for(var j=0;j<serieData.length;j++)
			serieData[j].selected = false;
		selectIdx = -1;
		searchGataData(regions);
	}
}

function selectRegion(param) {	// echarts 3
	//alert(param.name);
	//$.each(param.selected, function(k,v){  alert( 'k='+k+':v=' + v)  });
    var region = 'all';
    var idx = -1;
    for(var j=0;j<optionC.series.length;j++) {
    	var serie = optionC.series[j];
	    for (var i = 0, l = serie.data.length; i < l; i++) {
	        if (serie.data[i].name == param.name && param.selected[param.name]) {
	        	region = serie.data[i].regionId;
	        	selectIdx = i;
	        }
	        else
	        	serie.data[i].selected = false;
	    }
    }

    var regions = [];
	if(region == 'all') {
		var serieData = optionC.series[0].data;
		for(var j=0;j<serieData.length;j++){
			if(param.selected.hasOwnProperty(serieData[j].name))
				regions.push(serieData[j].regionId);
		}
		selectIdx = -1;
	}
	else {
		regions[0] = region;
	}
	
	searchGataData(regions);
}

function searchGataData(regions) {
	if(regions.toString() == '') {
		optionC.series[1].data = [];
		myChart.setOption(optionC);
		return;
	}
	$.ajax({
		url : $ctx + '/dataAnalysis/listGataAdmissionData.do',
		type : "post",
		dataType:"json",
		data : { startDate : startDate,
			endDate : endDate,
			regions : regions.toString()
		},
		success:function(data) {
			var gataMax =0;
			for(var i=0;i<data.length;i++){
				if(data[i].value > gataMax)
					gataMax = data[i].value
			}

			optionC.series[1].data = data;
			optionC.series[1].max = gataMax;
			if(selectIdx >= 0)
				optionC.series[0].data[selectIdx].selected = true;
			
			myChart.setOption(optionC);
		},
		error : function(e) {
			getAjaxError(e);
		}
	});
}

