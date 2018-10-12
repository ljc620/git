package com.zhming.basic.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.tbims.entity.SysRole;
import com.zhming.basic.service.IMenuService;
import com.zhming.basic.service.IRoleMngService;
import com.zhming.support.BaseController;
import com.zhming.support.annontion.ControlAspect;
import com.zhming.support.annontion.ControllerException;
import com.zhming.support.bean.PageBean;
import com.zhming.support.bean.ZTreeJson;
import com.zhming.support.common.OperType;
import com.zhming.support.exception.BaseException;
import com.zhming.support.exception.ServiceException;

/**
* Title: 角色管理  <br/>
* Description: 
* @ClassName: RoleMngController
* @author ydc
* @date 2017年7月1日 下午4:06:39
* 
*/
@RestController
@RequestMapping("/rolemng/")
public class RoleMngController extends BaseController {

	@Autowired
	private IRoleMngService roleService;

	@Autowired
	private IMenuService menuService;

	/**
	 * 
	 * Title:查询角色列表 <br/>
	 * Description:
	 * @param role
	 * @param page
	 * @param rows
	 * @return
	 * @throws BaseException
	 */
	@RequestMapping(value = "listRole")
	@ControlAspect(funtionCd = "i2_sys_role_list", operType = OperType.QUERY, havPrivs = true)
	@ControllerException
	public PageBean<Map<String, Object>> listRole(SysRole role) throws BaseException {
		PageBean<Map<String, Object>> roleList = roleService.listRole(getLoginUserBean(), role);
		return roleList;
	}

	/**
	 * 
	 * Title:添加角色，及角色对应的权限 <br/>
	 * Description:
	 * @param role
	 * @param funcKeys
	 * @return
	 * @throws BaseException
	 */
	@RequestMapping(value = "addRole")
	@ControlAspect(funtionCd = "i2_sys_role_list", operType = OperType.ADD, havPrivs = true)
	@ControllerException
	public void addRole(SysRole role, String funcKeys) throws ServiceException {
		roleService.addRole(getLoginUserBean(), role, funcKeys);
	}

	/**
	 * 
	 * Title:修改角色，及角色对应的权限 <br/>
	 * Description:
	 * @param role
	 * @param funcKeys
	 * @return
	 * @throws BaseException
	 */
	@RequestMapping(value = "updateRole")
	@ControlAspect(funtionCd = "i2_sys_role_list", operType = OperType.UPDATE, havPrivs = true)
	@ControllerException
	public void updateRole(SysRole role, String funcKeys) throws BaseException {
		roleService.updateRole(getLoginUserBean(), role, funcKeys);
	}

	/**
	 * 
	 * Title: 初始化角色，及角色对应的权限<br/>
	 * Description:
	 * @param roleId
	 * @return
	 * @throws BaseException
	 */
	@RequestMapping(value = "findByRoleId")
	@ControlAspect(funtionCd = "初始化角色数据", operType = OperType.QUERY)
	@ControllerException
	public ModelAndView findByRoleId(String roleId) throws BaseException {
		ModelAndView mv = new ModelAndView("pages/sys/rolemng/updateRole");
		SysRole role = roleService.findByRoleId(getLoginUserBean(), roleId);
		mv.addObject("role", role);
		return mv;
	}

	/**
	 * 
	 * Title: 删除角色，及角色对应的权限<br/>
	 * Description:
	 * @param roleId
	 * @return
	 * @throws BaseException
	 */
	@RequestMapping(value = "deleteByRoleCds")
	@ControlAspect(funtionCd = "i2_sys_role_list", operType = OperType.DELETE, havPrivs = true)
	@ControllerException
	public void deleteByRoleCds(String roleId) throws BaseException {
		roleService.deleteByRoleIds(getLoginUserBean(), roleId);
	}

	/**
	 * 
	 * Title: 获取角色列表，若角色已有权限，则选中<br/>
	 * Description:
	 * @param roleId
	 * @return
	 */
	@RequestMapping(value = "treeFuncRole")
	@ControlAspect(funtionCd = "获取角色列表", operType = OperType.QUERY)
	@ControllerException
	public List<ZTreeJson> treeFuncUser(String roleId,String roleType) throws BaseException {
		return menuService.listMenuTree(roleId,roleType);
	}
}