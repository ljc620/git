package com.zhming.support.annontion;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
* Title:  自定义注解 Controller 抛出异常时使用 <br/>
* Description: 
* @ClassName: ControllerException
* @author ydc
* @date 2016年12月13日 下午9:47:13
* 
*/
@Target({ElementType.PARAMETER, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ControllerException {

	/** 
	* Title: 抛出异常时的返回类型 <br/>
	* Description: json:返回json数据; page:返回ModelAndView类型通过viewName指定页面(默认msg/error.jsp)
	* @return
	*/ 
	String type() default "json";
	
	/** 
	* Title: 指定返回的页面（默认msg/error.jsp） <br/>
	* Description: 
	* @return
	*/ 
	String viewName() default "msg/error";

}
