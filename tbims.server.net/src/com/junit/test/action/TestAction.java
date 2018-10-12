package com.junit.test.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zhming.support.bean.EchartsBean;

@RestController
@RequestMapping("/test/")
public class TestAction {

	@RequestMapping(value = "listTestData")
	public EchartsBean list() {
		List<String> xValues = new ArrayList<String>();
		xValues.add("衬衫");
		xValues.add("羊毛衫");
		xValues.add("雪纺衫");
		xValues.add("裤子");
		xValues.add("高跟鞋");
		xValues.add("袜子");
		xValues.add("沙滩裤");
		xValues.add("滑板鞋");
		List<String> yValues = new ArrayList<String>();
		yValues.add("5");
		yValues.add("20");
		yValues.add("36");
		yValues.add("10");
		yValues.add("10");
		yValues.add("20");
		yValues.add("5");
		yValues.add("59");
		EchartsBean data = new EchartsBean(xValues,yValues);
		return data;
	}
	@RequestMapping(value="/listPieDemo")
	public  Map<String, Object> listPieDemo(){
		Map<String,Object> series=new HashMap<String, Object>();
		int idx=1;
		//初始化饼图数据
		series.put("Chrome",idx * 128 + 80);
		series.put("Firefox",idx * 64  + 160);
		series.put("Safari",idx * 32  + 320);
		series.put("IE9+",idx * 16  + 640);
		series.put("IE8-",idx++ * 8  + 1280);
		return series;
	}
	
	@RequestMapping(value="/listPieLegend")
	public  List<String> listPieLegend(){
		 List<String> data = new ArrayList<String>();  
		//初始化饼图数据
		 data.add("Chrome");
		 data.add("Firefox");
		 data.add("Safari");
		 data.add("IE9+");
		 data.add("IE8-");
		return data;
	}
}
