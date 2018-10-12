package com.zhming.support.util;

import java.util.HashMap;
import java.util.Map;

/**
 * Title: 项目导航信息工具类 <br/>
 * Description:
 * @ClassName: FunctionUtil
 * @author ljt
 * @date 2016年4月11日 上午10:10:24
 *
 */
public class FunctionUtil {
	/**
	 * 缓存导航信息
	 */
	public static Map<String, String> navigationMaps;
	/**
	 * 缓存权限信息
	 */
	//public static Map<String, TbSysRes> functionMaps;

	/**
	 * Title: 根据权限代码获取导航信息<br/>
	 * Description:
	 * @param function_cd
	 * @return
	 */
	public static String getNavigation(String function_cd) {
		String Navigation = navigationMaps.get(function_cd);
		if (StringUtil.isNull(Navigation)) {
			return function_cd;
		}
		return Navigation;
	}

	/**
	 * Title: 缴存导航信息<br/>
	 * Description:
	 * @throws DBExceptionBframe
	 */
	public static void setNavigationMaps() {
		navigationMaps = new HashMap<String, String>();
		//navigationMaps.put("0001", "用户管理>添加用户");
		//navigationMaps.put("0002", "用户管理>删除用户");

		/*IFunctionService funcService = SpringContextUtil.getBean(FunctionService.class);

		navigationMaps = funcService.getNavigationMaps();
		*/
		
	}

	/**
	 * Title: 根据权限代码获取权限对象<br/>
	 * Description:
	 * @param function_cd
	 * @return
	 *//*
	public static TbSysRes getFunction(String function_cd) {
		return functionMaps.get(function_cd);
	}*/

	/**
	 * Title: 根据权限代码获取权限名称<br/>
	 * Description:
	 * @param function_cd
	 * @return
	 */
	/*public static String getFunctionName(String function_cd) {
		TbSysRes sysRes = functionMaps.get(function_cd);
		if (sysRes != null) {
			return sysRes.getResNm();
		}
		return function_cd;
	}*/

	/**
	 * Title: 缓存权限信息<br/>
	 * Description:
	 * @throws BFException
	 */
	public static void setFunctionMaps() {
	/*	functionMaps = new HashMap<String, TbSysRes>();
		//functionMaps.put("0001", new TbSysRes());
		//functionMaps.put("0002", new TbSysRes());
		IFunctionService funcService = SpringContextUtil.getBean(FunctionService.class);

		functionMaps = funcService.listFunc();*/
	}

}
