package com.zhming.support.common;

import java.io.IOException;
import java.util.Date;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.zhming.support.util.DateUtil;

/**
 * 
* Title:   日期json输出格式化，日期格式：yyyy-MM-dd<br/>
* Description: 
* @ClassName: DateSerializer
* @author ly
* @date 2017年6月15日 上午9:07:50
*
 */
public class DateSerializer extends JsonSerializer<Date> {
	@Override
	public void serialize(Date date, JsonGenerator gen, SerializerProvider arg2)
			throws IOException, JsonProcessingException {
		if (date == null) {
			gen.writeString("");
		} else {
			gen.writeString(DateUtil.formatDateToString(date, "yyyy-MM-dd"));
		}
	}
}