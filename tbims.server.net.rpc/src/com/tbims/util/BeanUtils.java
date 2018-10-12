package com.tbims.util;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.beans.BeansException;
import org.springframework.beans.FatalBeanException;
import org.springframework.util.Assert;

/**
 * Title: RPC 专用Bean处理类 <br/>
 * Description:
 * @ClassName: BeanUtils
 * @author ydc
 * @date 2017年6月28日 下午1:39:13
 * 
 */
public class BeanUtils extends org.springframework.beans.BeanUtils {

	public static void copyRPCProperties(Object source, Object target, String[] ignoreProperties) throws BeansException {
		copyRPCProperties(source, target, null, ignoreProperties);
	}

	public static void copyRPCProperties(Object source, Object target, Class<?> editable) throws BeansException {
		copyRPCProperties(source, target, editable, null);
	}

	/**
	 * Title:RPC 专用Bean处理 <br/>
	 * Description:
	 * @param source
	 * @param target
	 * @throws BeansException
	 */
	public static void copyRPCProperties(Object source, Object target) throws BeansException {
		copyRPCProperties(source, target, null, null);
	}

	private static void copyRPCProperties(Object source, Object target, Class<?> editable, String[] ignoreProperties) throws BeansException {

		Assert.notNull(source, "Source must not be null");
		Assert.notNull(target, "Target must not be null");

		Class<?> actualEditable = target.getClass();
		if (editable != null) {
			if (!editable.isInstance(target)) {
				throw new IllegalArgumentException("Target class [" + target.getClass().getName() + "] not assignable to Editable class [" + editable.getName() + "]");
			}
			actualEditable = editable;
		}
		PropertyDescriptor[] targetPds = getPropertyDescriptors(actualEditable);
		List<String> ignoreList = (ignoreProperties != null) ? Arrays.asList(ignoreProperties) : null;

		for (PropertyDescriptor targetPd : targetPds) {
			if (targetPd.getWriteMethod() != null && (ignoreProperties == null || (!ignoreList.contains(targetPd.getName())))) {
				PropertyDescriptor sourcePd = getPropertyDescriptor(source.getClass(), targetPd.getName());
				if (sourcePd != null && sourcePd.getReadMethod() != null) {
					try {
						Method readMethod = sourcePd.getReadMethod();
						if (!Modifier.isPublic(readMethod.getDeclaringClass().getModifiers())) {
							readMethod.setAccessible(true);
						}
						Method targetMethod = targetPd.getReadMethod();
						Object value = readMethod.invoke(source);

						// 转换特殊处理 begin and ydc for 20170628
						// 这里判断以下value是否为空，当然这里也能进行一些特殊要求的处理 例如绑定时格式转换等等，
						// 如果是String类型，则不需要验证是否为空
						String sourceMethodReturnType = readMethod.getReturnType().getName();
						String targetMethodReturnType = targetMethod.getReturnType().getName();

						if (sourceMethodReturnType.equals("java.util.Date") && (targetMethodReturnType.equals("java.lang.Long") || targetMethodReturnType.equals("long"))) {
							// DB--RPC
							if (value == null) {
								value = new Date().getTime();
							} else {
								value = ((java.util.Date) value).getTime();
							}
						} else if (targetMethodReturnType.equals("java.util.Date") && (sourceMethodReturnType.equals("java.lang.Long") || sourceMethodReturnType.equals("long"))) {
							// RPC--DB
							if (value != null && CommonUtil.covertLong(value) != 0) {
								value = new Date(CommonUtil.covertLong(value));
							} else {
								continue;
							}
						} else if ((targetMethodReturnType.equals("java.lang.Double") || targetMethodReturnType.equals("double")) && sourceMethodReturnType.equals("java.math.BigDecimal")) {
							// DB--RPC
							if (value == null) {
								value = 0;
							} else {
								value = ((BigDecimal) value).doubleValue();
							}
						} else if (sourceMethodReturnType.equals("java.lang.Double") || sourceMethodReturnType.equals("java.lang.Long") || sourceMethodReturnType.equals("java.lang.Integer")) {
							// DB--RPC
							if (value == null) {
								value = 0;
							}
						}
						// end

						Method writeMethod = targetPd.getWriteMethod();
						if (!Modifier.isPublic(writeMethod.getDeclaringClass().getModifiers())) {
							writeMethod.setAccessible(true);
						}
						writeMethod.invoke(target, value);
					} catch (Throwable ex) {
						throw new FatalBeanException("Could not copy properties from source to target", ex);
					}
				}
			}
		}
	}

}
