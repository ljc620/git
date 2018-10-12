package com.zhming.support.util;

import java.util.HashMap;
import java.util.Map;

import com.tbims.entity.SysMenu;
import com.zhming.basic.service.IMenuService;
import com.zhming.basic.service.impl.MenuService;
import com.zhming.support.init.SpringContextUtil;

/**
 * Title: 项目导航信息工具类 <br/>
 * Description:
 * @ClassName: FunctionUtil
 * @author ljt
 * @date 2016年4月11日 上午10:10:24
 *
 */
public class MenuUtil {
	/**
	 * 缓存导航信息
	 */
	public static Map<String, String> navigationMaps;
	/**
	 * 缓存权限信息
	 */
	public static Map<String, SysMenu> menuMaps;

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
		IMenuService funcService = SpringContextUtil.getBean(MenuService.class);
		navigationMaps = funcService.getNavigationMaps();
	}

	/**
	 * Title: 根据权限代码获取权限对象<br/>
	 * Description:
	 * @param function_cd
	 * @return
	 */
	public static SysMenu getFunction(String menuId) {
		return menuMaps.get(menuId);
	}

	/**
	 * Title: 根据权限代码获取权限名称<br/>
	 * Description:
	 * @param function_cd
	 * @return
	 */
	public static String getFunctionName(String menuId) {
		SysMenu sysRes = menuMaps.get(menuId);
		if (sysRes != null) {
			return sysRes.getMenuName();
		}
		return menuId;
	}

	/**
	 * Title: 缓存权限信息<br/>
	 * Description:
	 * @throws BFException
	 */
	public static void setFunctionMaps() {
		menuMaps = new HashMap<String, SysMenu>();
		IMenuService menuService = SpringContextUtil.getBean(MenuService.class);
		menuMaps = menuService.listMenu();
	}

}
