package com.tbims.annontion;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.tbims.common.OperType;

/**
 * Title: 自定义注解 拦截Controller时使用 <br/>
 * Description:
 * @ClassName: ControlAspect
 * @author ydc
 * @date 2016年12月13日 下午9:46:21
 * 
 */
@Target({ElementType.PARAMETER, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ControlAspect {

	/**
	 * Title: 权限名称<br/>
	 * Description: 
	 * @return
	 */
	String funtionName() default "";
	
	/**
	 * Title: 方法操作类型（1-增、2-删、3-改、4-查）<br/>
	 * Description:
	 * @return
	 */
	int operType() default OperType.QUERY;
	
}
