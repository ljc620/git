package com.zhming.support.util;

import javax.persistence.Entity;

/**
 * Title: 注解处理类 <br/>
 * Description:
 * @ClassName: AnnotationUtil
 * @author ydc
 * @date 2016-1-7 下午5:15:05
 * 
 */
public class AnnotationUtil {

	public static boolean isEntity(Class<?> cla) {
		return cla.isAnnotationPresent(Entity.class);
	}

}
