package com.zhming.basic.service;

import java.util.List;
import java.util.Map;

import com.tbims.entity.SysMenu;
import com.zhming.support.bean.UserBean;
import com.zhming.support.bean.ZTreeJson;
import com.zhming.support.exception.BaseException;

public interface IMenuService {
	/**
	 * Title:查询菜单 <br/>
	 * Description:
	 * @return
	 * @throws BaseException
	 */
	public Map<String, SysMenu> listMenuForMap() throws BaseException;
	/**
	 * Title: 菜单树<br/>
	 * Description:
	 * @param userBean
	 * @param func
	 * @return
	 * @throws BFException
	 */
	public List<ZTreeJson> listMenuTree(String roleId,String roleType);
	/**
	 * Title:获取导航信息 <br/>
	 * Description:
	 * @return
	 */
	public Map<String, String> getNavigationMaps();

	/**
	 * Title: 获取指定菜单列表<br/>
	 * Description:
	 * @return
	 */
	public List<SysMenu> listMenu(String menuId);

	/**
	 * Title: 列表所有的菜单 <br/>
	 * Description:
	 * @return
	 */
	public List<ZTreeJson> listMenuTree();

	/**
	 * Title:添加菜单菜单 <br/>
	 * Description:
	 * @param sysMenu
	 * @throws BaseException 
	 */
	public void addMenu(UserBean userBean, SysMenu sysMenu) throws BaseException;

	/**
	 * Title: 查询菜单根据菜单编号<br/>
	 * Description:
	 * @param userBean
	 * @param menuId
	 */
	public SysMenu findMenuById(UserBean userBean, String menuId);

	/**
	 * Title:更新菜单菜单 <br/>
	 * Description:
	 * @param sysMenu
	 */
	public void updateMenu(UserBean userBean, SysMenu sysMenu);

	/**
	 * Title:删除菜单 <br/>
	 * Description:
	 * @param menuId
	 */
	public void deleteMenu(UserBean userBean, String menuIds);

	/**
	 * Title:更新菜单状态 <br/>
	 * Description:
	 * @param userBean
	 * @param menuIds
	 */
	public void updateMenuStat(UserBean userBean, String menuIds, String menuStat);

}
