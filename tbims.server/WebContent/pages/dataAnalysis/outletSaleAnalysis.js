/**
 * 
 */

var optionC, ecTheme = 'default',
	myChart, startDate, endDate, groupType
	selectIdx = -1;

function initcombo() {
	var data = [ {id : 'byClients', name : '按窗口'}, {id : 'byUsers', name : '按员工'} ];
	$('#groupType').combobox({
		valueField : 'id',
		textField : 'name',
		panelHeight : 'auto',
		editable : false,
		multiple : false,
		width : 120,
		data: data
	});
	$('#groupType').combobox("setValue", "byClients");
}

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
	
	initcombo();
	outletSaleChart();
});


function outletSaleChart(){
	startDate = $('#startDate').datebox('getValue');
	endDate = $('#endDate').datebox('getValue');
	groupType = $('#groupType').datebox('getValue');
	myChart = echarts.init(document.getElementById('analysisChart'), ecTheme)
//	myChart.showLoading();
	$.ajax({
		url : $ctx + '/dataAnalysis/listOutletSaleData.do',
		type : "post",
		dataType:"json",
		data :{ startDate : startDate,
			endDate : endDate,
			groupType : groupType
		},
		success:function(data) {
			var clientMax = 0;
			for(var i=0;i<data.clientData.length;i++){
				if(data.clientData[i].value>clientMax)
					clientMax = data.clientData[i].value
			}
			optionC = {
					//color: ["#b6a2de", "#f5994e", "#d87a80", "#5ab1ef" ],
					title : {
						text : '网点门票销售数量分析',
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
				                  name:'销售网点',
				                  type:'pie',
				                  selectedMode: 'single',
				                  startAngle: '45',
				                  selectedOffset: '35',
				                  //radius : [0, 70],
				                  radius: [0, '65%'],
				                  center: ['25%', '55%'],
				                  
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
				                  data : data.outletData,
				                  label: {
				                      normal: {
				                          formatter: '{b}: {d}%',
				                      }
				                  }
				              },
				              {
				                  name:'销售窗口',
				                  type:'funnel',
				                  //selectedMode: 'single',
				                  startAngle: '120',
				                  //radius : [100, 140],
				                  //radius: ['50%', '65%'],
				                  
				                  // for funnel
				                  x: '50%',
				                  width: '40%',
				                  height: '80%',
				                  y: '80',
				                  funnelAlign: 'left',
				                  max: clientMax,
				                  data : data.clientData
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
			
	myChart.on("pieselectchanged", selectOutlet);		// echarts 3
	myChart.on("legendselectchanged", selectLegend);
}

function selectLegend(param) {
	//alert("name: "+ param.name);
	var outlets = [], serieData = optionC.series[0].data, hasSel = false;
	for(var j=0;j<serieData.length;j++){
		if(param.selected[serieData[j].name]){
			outlets.push(serieData[j].outletId);
			if(serieData[j].selected)
				hasSel = true;
		}
	}

	//alert(hasSel);
	if(!hasSel) {
		for(var j=0;j<serieData.length;j++)
			serieData[j].selected = false;
		selectIdx = -1;
		searchDetailData(outlets);
	}
}

function selectOutlet(param) {	// echarts 3
	//alert(param.name);
	//$.each(param.selected, function(k,v){  alert( 'k='+k+':v=' + v)  });
    var outlet = 'all';
    var idx = -1;
    for(var j=0;j<optionC.series.length;j++) {
    	var serie = optionC.series[j];
	    for (var i = 0, l = serie.data.length; i < l; i++) {
	        if (serie.data[i].name == param.name && param.selected[param.name]) {
	        	outlet = serie.data[i].outletId;
	        	selectIdx = i;
	        }
	        else
	        	serie.data[i].selected = false;
	    }
    }

    var outlets = [];
	if(outlet == 'all') {
		var serieData = optionC.series[0].data;
		for(var j=0;j<serieData.length;j++){
			if(param.selected.hasOwnProperty(serieData[j].name))
				outlets.push(serieData[j].outletId);
		}
		selectIdx = -1;
	}
	else {
		outlets[0] = outlet;
	}
	
	searchDetailData(outlets);
}

function searchDetailData(outlets) {
	if(outlets.toString() == '') {
		optionC.series[1].data = [];
		myChart.setOption(optionC);
		return;
	}
	$.ajax({
		url : $ctx + '/dataAnalysis/listDetailSaleData.do',
		type : "post",
		dataType:"json",
		data : { startDate : startDate,
			endDate : endDate,
			outlets : outlets.toString(),
			groupType : groupType
		},
		success:function(data) {
			var clientMax =0;
			for(var i=0;i<data.length;i++){
				if(data[i].value > clientMax)
					clientMax = data[i].value
			}

			optionC.series[1].data = data;
			optionC.series[1].max = clientMax;
			if(selectIdx >= 0)
				optionC.series[0].data[selectIdx].selected = true;
			
			myChart.setOption(optionC);
		},
		error : function(e) {
			getAjaxError(e);
		}
	});
}
