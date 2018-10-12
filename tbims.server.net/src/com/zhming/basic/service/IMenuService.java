package com.zhming.basic.service;

import java.util.Map;

import com.tbims.entity.SysMenu;

public interface IMenuService {
	/**
	 * Title: 登录系统，加载所有权限<br/>
	 * Description:
	 * @param userBean
	 * @param func
	 * @return
	 * @throws BFException
	 */
	public Map<String, SysMenu> listMenu();
	/**
	 * Title:获取导航信息 <br/>
	 * Description:
	 * @param function_cd
	 * @return
	 * @throws BFException
	 */
	public Map<String, String> getNavigationMaps();
}
