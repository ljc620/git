package com.zhming.basic.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.tbims.entity.SysMenu;
import com.zhming.basic.service.IMenuService;
import com.zhming.support.BaseController;
import com.zhming.support.annontion.ControlAspect;
import com.zhming.support.annontion.ControllerException;
import com.zhming.support.bean.ZTreeJson;
import com.zhming.support.common.OperType;
import com.zhming.support.exception.BaseException;
import com.zhming.support.exception.ServiceException;

/**
 * Title: 菜单管理 <br/>
 * Description:
 * @ClassName: MenuMngController
 * @author ydc
 * @date 2017年7月1日 下午4:06:39
 * 
 */
@RestController
@RequestMapping("/menumng/")
public class MenuMngController extends BaseController {

	@Autowired
	private IMenuService menuService;

	/**
	 * 
	 * Title:查询菜单列表 <br/>
	 * Description:
	 * @param Menu
	 * @param page
	 * @param rows
	 * @return
	 * @throws BaseException
	 */
	@RequestMapping(value = "listMenu")
	@ControlAspect(funtionCd = "i2_sys_menu_list", operType = OperType.QUERY, havPrivs = true)
	@ControllerException
	public List<SysMenu> listMenu(String menuId) throws BaseException {
		return menuService.listMenu(menuId);
	}

	/**
	 * 
	 * Title: 添加菜单前处理<br/>
	 * Description:
	 * @param MenuId
	 * @return
	 */
	@RequestMapping(value = "addMenuBefore")
	@ControlAspect(funtionCd = "添加菜单前处理", operType = OperType.QUERY)
	@ControllerException
	public ModelAndView addMenuBefore(String menuPid) throws BaseException {
		SysMenu sysMenu = new SysMenu();
		sysMenu.setMenuPid(menuPid);
		ModelAndView mv = new ModelAndView("pages/sys/menumng/addMenu");
		mv.addObject("sysMenu", sysMenu);
		return mv;
	}

	/**
	 * 
	 * Title:添加菜单 <br/>
	 * Description:
	 * @param role
	 * @param funcKeys
	 * @return
	 * @throws BaseException
	 */
	@RequestMapping(value = "findMenuById")
	@ControlAspect(funtionCd = "查询菜单", operType = OperType.QUERY)
	@ControllerException
	public ModelAndView findMenuById(String menuId) throws ServiceException {
		SysMenu sysMenu = menuService.findMenuById(getLoginUserBean(), menuId);
		ModelAndView mv = new ModelAndView("pages/sys/menumng/updateMenu");
		mv.addObject("sysMenu", sysMenu);
		return mv;
	}

	/**
	 * 
	 * Title:添加菜单 <br/>
	 * Description:
	 * @param role
	 * @param funcKeys
	 * @return
	 * @throws BaseException
	 */
	@RequestMapping(value = "addMenu")
	@ControlAspect(funtionCd = "i2_sys_menu_list", operType = OperType.ADD, havPrivs = true)
	@ControllerException
	public void addMenu(SysMenu sysMenu) throws BaseException {
		menuService.addMenu(getLoginUserBean(), sysMenu);
	}

	/**
	 * 
	 * Title:更新菜单 <br/>
	 * Description:
	 * @param role
	 * @param funcKeys
	 * @return
	 * @throws BaseException
	 */
	@RequestMapping(value = "updateMenu")
	@ControlAspect(funtionCd = "i2_sys_menu_list", operType = OperType.UPDATE, havPrivs = true)
	@ControllerException
	public void updateMenu(SysMenu sysMenu) throws BaseException {
		menuService.updateMenu(getLoginUserBean(), sysMenu);
	}

	/**
	 * 
	 * Title: 删除菜单<br/>
	 * Description:
	 * @param menuId
	 * @return
	 * @throws BaseException
	 */
	@RequestMapping(value = "deleteByMenuIds")
	@ControlAspect(funtionCd = "i2_sys_menu_list", operType = OperType.DELETE, havPrivs = true)
	@ControllerException
	public void deleteByMenuIds(String menuIds) throws BaseException {
		menuService.deleteMenu(getLoginUserBean(), menuIds);
	}

	/**
	 * 
	 * Title: 获取菜单树<br/>
	 * Description:
	 * @param MenuId
	 * @return
	 */
	@RequestMapping(value = "treeMenu")
	@ControlAspect(funtionCd = "获取菜单树", operType = OperType.QUERY)
	@ControllerException
	public List<ZTreeJson> treeMenu() throws BaseException {
		return menuService.listMenuTree();
	}

	/**
	 * 
	 * Title: 更新菜单状态<br/>
	 * Description:
	 * @param MenuId
	 * @return
	 */
	@RequestMapping(value = "updateMenuStat")
	@ControlAspect(funtionCd = "更新菜单状态", operType = OperType.QUERY)
	@ControllerException
	public void updateMenuStat(String menuIds, String menuStat) throws BaseException {
		menuService.updateMenuStat(getLoginUserBean(), menuIds, menuStat);;
	}

}