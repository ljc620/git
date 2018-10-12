package com.zhming.support.util;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeansException;
import org.springframework.beans.FatalBeanException;
import org.springframework.util.Assert;

import com.alibaba.fastjson.JSONArray;
import com.tbims.pay.bean.CancelOrderRequest;
import com.tbims.pay.bean.OrderPayRequest;

/**
 * Title: RPC 专用Bean处理类 <br/>
 * Description:
 * 
 * @ClassName: BeanUtils
 * @author ydc
 * @date 2017年6月28日 下午1:39:13
 * 
 */
public class BeanUtils extends org.springframework.beans.BeanUtils {

	// javabean TO javabean 处理

	public static void copyRPCProperties(Object source, Object target, String[] ignoreProperties) throws BeansException {
		copyRPCProperties(source, target, null, ignoreProperties);
	}

	public static void copyRPCProperties(Object source, Object target, Class<?> editable) throws BeansException {
		copyRPCProperties(source, target, editable, null);
	}

	/**
	 * Title:RPC 专用Bean处理 <br/>
	 * Description:
	 * 
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

	// javabean TO map 处理

	/**
	 * Title:bean 转换为 map <br/>
	 * Description:
	 * 
	 * @param source
	 * @param ignoreProperties
	 * @return
	 * @throws BeansException
	 */
	public static Map<String, String> copyBeanToMap(Object source, String[] ignoreProperties) throws BeansException {
		Assert.notNull(source, "Source must not be null");

		Map<String, String> map = new HashMap<String, String>();
		PropertyDescriptor[] sourcePds = getPropertyDescriptors(source.getClass());
		List<String> ignoreList = (ignoreProperties != null) ? Arrays.asList(ignoreProperties) : null;

		for (PropertyDescriptor sourcePd : sourcePds) {
			if (sourcePd.getWriteMethod() != null && (ignoreProperties == null || (!ignoreList.contains(sourcePd.getName())))) {
				if (sourcePd != null && sourcePd.getReadMethod() != null) {
					try {
						Method readMethod = sourcePd.getReadMethod();
						if (!Modifier.isPublic(readMethod.getDeclaringClass().getModifiers())) {
							readMethod.setAccessible(true);
						}
						String attrName = sourcePd.getName();
						Object value = readMethod.invoke(source);
						String value1 = StringUtil.convertString(value);
						if (StringUtil.isNull(value1)) {
							continue;
						}
						map.put(attrName, value1);
					} catch (Throwable ex) {
						throw new FatalBeanException("转换错误", ex);
					}
				}
			}
		}

		return map;
	}

	/**
	 * Title:Map 转换为 bean <br/>
	 * Description: 目标bean,支持 String,int 类型
	 * 
	 * @param source
	 * @param entityClass
	 * @param ignoreProperties
	 * @return
	 * @throws BeansException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	public static <T> T copyMapToBean(Map<String, String> source, Class<T> entityClass, String[] ignoreProperties) throws BeansException, InstantiationException, IllegalAccessException {
		Assert.notNull(source, "Source must not be null");

		PropertyDescriptor[] targetPds = getPropertyDescriptors(entityClass);
		List<String> ignoreList = (ignoreProperties != null) ? Arrays.asList(ignoreProperties) : null;

		T targetObj = (T) entityClass.newInstance();

		for (PropertyDescriptor targetPd : targetPds) {
			if (targetPd.getWriteMethod() != null && (ignoreProperties == null || (!ignoreList.contains(targetPd.getName())))) {
				if (targetPd != null && targetPd.getReadMethod() != null) {
					String attrName = targetPd.getName();
					Object value = source.get(attrName);
					if (!source.containsKey(attrName)) {
						continue;
					}
					try {
						String targetMethodReturnType = targetPd.getReadMethod().getReturnType().getName();

						if (targetMethodReturnType.equals("int") || (targetMethodReturnType.equals("java.lang.Integer"))) {
							value = Integer.valueOf(StringUtil.convertString(value));
						}

						Method writeMethod = targetPd.getWriteMethod();
						if (!Modifier.isPublic(writeMethod.getDeclaringClass().getModifiers())) {
							writeMethod.setAccessible(true);
						}
						writeMethod.invoke(targetObj, value);

					} catch (Throwable ex) {
						throw new FatalBeanException("转换错误", ex);
					}
				}
			}
		}

		return targetObj;
	}

	public static void main(String[] args) throws BeansException, InstantiationException, IllegalAccessException {
		CancelOrderRequest cancelOrderRequest = new CancelOrderRequest();
		cancelOrderRequest.setMch_id("1000");
		cancelOrderRequest.setOut_trade_no("00001111");
		Map<String, String> map = BeanUtils.copyBeanToMap(cancelOrderRequest, null);
		System.out.println(map);

		Map<String, String> map1 = new HashMap<String, String>();
		map1.put("mch_id", "1000");
		map1.put("out_trade_no", "00001111");
		map1.put("body", "aaddd");
		map1.put("total_fee", "111");
		map1.put("auth_code", "111");

		OrderPayRequest obj = BeanUtils.copyMapToBean(map1, OrderPayRequest.class, null);
		System.out.println(JSONArray.toJSONString(obj));
	}

}
