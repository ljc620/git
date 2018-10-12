package com.zhming.support.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Collection工具类
* Title:   <br/>
* Description: 
* @ClassName: CollectionUtil
* @author ljt
* @date 2016年4月11日 上午10:07:17
*
 */
public class CollectionUtil {
	@SuppressWarnings("rawtypes")
	public static Map<String, String> getParameterMapByPage(Map<String, String[]> map) {
		Map<String, String> returnMap = new HashMap<String, String>();
		Iterator entries = map.entrySet().iterator();
		Map.Entry entry;
		String name = "";
		String value = "";
		while (entries.hasNext()) {
			entry = (Map.Entry) entries.next();
			name = (String) entry.getKey();
			Object valueObj = entry.getValue();
			if (name.endsWith("_pageno")) {
				continue;
			}
			if (name.equals("initflag")) {
				continue;
			}
			if (null == valueObj) {
				value = "";
			} else if (valueObj instanceof String[]) {
				String[] values = (String[]) valueObj;
				for (int i = 0; i < values.length; i++) {
					value = values[i] + ",";
				}
				value = value.substring(0, value.length() - 1);
			} else {
				value = valueObj.toString();
			}
			returnMap.put(name, value);
		}
		return returnMap;
	}

	@SuppressWarnings({"rawtypes", "unchecked"})
	public static Map<String, String> getParameterMap(Map<String, String[]> map) {
		Map returnMap = new HashMap();
		Iterator entries = map.entrySet().iterator();
		Map.Entry entry;
		String name = "";
		String value = "";
		while (entries.hasNext()) {
			entry = (Map.Entry) entries.next();
			name = (String) entry.getKey();
			Object valueObj = entry.getValue();
			if (null == valueObj) {
				value = "";
			} else if (valueObj instanceof String[]) {
				String[] values = (String[]) valueObj;
				for (int i = 0; i < values.length; i++) {
					value = values[i] + ",";
				}
				value = value.substring(0, value.length() - 1);
			} else {
				value = valueObj.toString();
			}
			returnMap.put(name, value);
		}
		return returnMap;
	}
}
