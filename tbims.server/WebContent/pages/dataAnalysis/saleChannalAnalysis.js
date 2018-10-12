/**
 * 
 */

var optionC, ecTheme = 'default',
	myChart, startDate, endDate,
	selectIdx = -1;

function initcombo() {
	var url = $ctx + '/dataAnalysis/listSaleType.do';
	var params = { startDate : startDate, endDate : endDate	};
	$.post(url, params, function(data){
		$('#saleTypeId').combobox({
			valueField : 'id',
			textField : 'name',
			panelHeight : 'auto',
			editable : false,
			multiple : true,
			width : 180,
			onSelect : function(option) {
				if (option.id != "") {
					$(this).combobox("unselect", "");
				} else {
					$(this).combobox("reset");
					$(this).combobox("select", option.id);
				}
			},
			onLoadSuccess : function(e) {
			},
			onLoadError : function(e) {
				getAjaxError(e);
			},
			data: data,
			loadFilter : function(data) {
				var all = [ {
					id : '',
					name : '--全部--'
				} ];
				return $.merge(all, data);
			}
		});

		$('#saleTypeId').combobox("setValue", "");
	}, "json");
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

	//initcombo();
	
	saleAnalysisChart();
});


function saleAnalysisChart(){
	startDate = $('#startDate').datebox('getValue');
	endDate = $('#endDate').datebox('getValue');
	myChart = echarts.init(document.getElementById('analySaleChart'), ecTheme)
//	myChart.showLoading();
	$.ajax({
		url : $ctx + '/dataAnalysis/listSaleAndPayData.do',
		type : "post",
		dataType:"json",
		data :{ startDate : startDate,
			endDate : endDate,
			saleTypes : ''//$('#saleTypeId').combobox('getValues').toString()
		},
		success:function(data) {
			var payMax =0, saleMax = 0;
			for(var i=0;i<data.saleData.length;i++){
				if(data.saleData[i].value>saleMax)
					saleMax = data.saleData[i].value
			}
			for(var i=0;i<data.payData.length;i++){
				if(data.payData[i].value>saleMax)
					payMax = data.payData[i].value
			}
			optionC = {
					//color: ["#b6a2de", "#f5994e", "#d87a80", "#5ab1ef" ],
					title : {
						text : '销售渠道及支付方式分析(金额)',
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
				                  name:'支付方式',
				                  type:'pie',
				                  //selectedMode: 'single',
				                  startAngle: '45',
				                  //radius : [0, 70],
				                  radius: [0, '40%'],
				                  
				                  // for funnel
				                  x: '20%',
				                  width: '40%',
				                  funnelAlign: 'right',
				                  max: payMax,
				                  
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
				                  data : data.payData,
				                  label: {
				                      normal: {
				                          formatter: '{b}: {d}%',
				                      }
				                  }
				              },
				              {
				                  name:'销售渠道',
				                  type:'pie',
				                  selectedMode: 'single',
				                  startAngle: '120',
				                  selectedOffset: '25',
				                  //radius : [100, 140],
				                  radius: ['50%', '65%'],
				                  
				                  // for funnel
				                  x: '60%',
				                  width: '35%',
				                  funnelAlign: 'left',
				                  max: saleMax,
				                  data : data.saleData,
				                  label: {
				                      normal: {
				                          formatter: ' {b|{b}：} {per|{d}%}  ',//' {b|{b}：}{c}  {per|{d}%}  '
				                          backgroundColor: '#eee',
				                          borderColor: '#aaa',
				                          borderWidth: 0,
				                          borderRadius: 4,
				                          shadowBlur:3,
				                          shadowOffsetX: 2,
				                          shadowOffsetY: 2,
//				                          shadowColor: '#999',
				                          // padding: [0, 7],
				                          rich: {
				                              b: {
				                                  fontSize: 13,
				                                  lineHeight: 28
				                              },
				                              per: {
				                                  color: '#eee',
				                                  backgroundColor: '#334455',
				                                  padding: [2, 4],
				                                  borderRadius: 2
				                              }
				                          }
				                      }
				                  }
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
			
	myChart.on("pieselectchanged", selectSaleType);		// echarts 3
	myChart.on("legendselectchanged", selectLegend);
}

function selectLegend(param) {
	//alert("name: "+ param.name);
	var saleTypes = [], serieData = optionC.series[1].data, hasSel = false;
	for(var j=0;j<serieData.length;j++){
		if(param.selected[serieData[j].name]){
			saleTypes.push(serieData[j].orderType);
			if(serieData[j].selected)
				hasSel = true;
		}
	}
	
	//alert(hasSel);
	if(!hasSel) {
		for(var j=0;j<serieData.length;j++)
			serieData[j].selected = false;
		selectIdx = -1;
		searchPayData(saleTypes);
	}
}

function selectSaleType(param) {
	//alert(param.name);
	//$.each(param.selected, function(k,v){  alert( 'k='+k+':v=' + v)  });
    var saleType = 'all';
    var idx = -1;
    for(var j=0;j<optionC.series.length;j++) {
    	var serie = optionC.series[j];
	    for (var i = 0, l = serie.data.length; i < l; i++) {
	        if (serie.data[i].name == param.name && param.selected[param.name]) {
	            saleType = serie.data[i].orderType;
	            selectIdx = i;
	        }
	        else
	        	serie.data[i].selected = false;
	    }
    }
    
    var saleTypes = [];
	if(saleType == 'all') {
		var serieData = optionC.series[1].data;
		for(var j=0;j<serieData.length;j++){
			if(param.selected.hasOwnProperty(serieData[j].name))
				saleTypes.push(serieData[j].orderType);
		}
		selectIdx = -1;
	}
	else {
		saleTypes[0] = saleType;
	}
	
	searchPayData(saleTypes);
}

function searchPayData(saleTypes) {
	if(saleTypes.toString() == '') {
		optionC.series[0].data = [];
		myChart.setOption(optionC);
		return;
	}
	$.ajax({
		url : $ctx + '/dataAnalysis/listPayDataBySaleType.do',
		type : "post",
		dataType:"json",
		data : { startDate : startDate,
			endDate : endDate,
			saleTypes : saleTypes.toString()
		},
		success:function(data) {
			optionC.series[0].data = data;
			if(selectIdx >= 0)
				optionC.series[1].data[selectIdx].selected = true;
			
			myChart.setOption(optionC);
		},
		error : function(e) {
			getAjaxError(e);
		}
	});
}

