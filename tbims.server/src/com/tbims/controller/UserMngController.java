package com.tbims.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.tbims.bean.SysUserQBean;
import com.tbims.entity.SysUser;
import com.tbims.service.IUserMngService;
import com.zhming.support.BaseController;
import com.zhming.support.annontion.ControlAspect;
import com.zhming.support.annontion.ControllerException;
import com.zhming.support.bean.PageBean;
import com.zhming.support.common.OperType;
import com.zhming.support.exception.BaseException;

/**
 * Title: 用户管理 <br/>
 * Description:
 * @ClassName: UserMngController
 * @author ydc
 * @date 2017年6月29日 下午3:29:30
 * 
 */
@RestController
@RequestMapping("/usermng/")
public class UserMngController extends BaseController {

	@Autowired
	private IUserMngService userMngService;

	/**
	 * 
	 * Title: 查询用户列表<br/>
	 * Description:
	 * @param sysUserQBean
	 * @return
	 */
	@RequestMapping(value = "listUser")
	@ControlAspect(funtionCd = "i2_sys_user_list", havPrivs = true)
	@ControllerException
	public PageBean<Map<String, Object>> listUser(SysUserQBean sysUserQBean) throws BaseException {
		PageBean<Map<String, Object>> userList = userMngService.listUser(getLoginUserBean(), sysUserQBean);
		return userList;
	}

	/**
	 * 
	 * Title: 设置角色<br/>
	 * Description:
	 * @param sysUserQBean
	 * @return
	 */
	@RequestMapping(value = "setRoleToUser")
	@ControlAspect(funtionCd = "i2_sys_user_list", operType = OperType.UPDATE, havPrivs = true)
	@ControllerException
	public void setRoleToUser(String userCds, String roleCds) throws BaseException {
		userMngService.setRoleToUser(getLoginUserBean(), userCds, roleCds);
	}

	/**
	 * 
	 * Title: 更新用户状态<br/>
	 * Description:
	 * @param sysUserQBean
	 * @return
	 */
	@RequestMapping(value = "updateUserStat")
	@ControlAspect(funtionCd = "i2_sys_user_list", operType = OperType.UPDATE, havPrivs = true)
	@ControllerException
	public void updateUserStat(String userIds, String userStat) throws BaseException {
		userMngService.updateUserStat(getLoginUserBean(), userIds, userStat);
	}

	/**
	 * 
	 * Title: 查询用户<br/>
	 * Description:
	 * @param sysUserQBean
	 * @return
	 */
	@RequestMapping(value = "findByUserCd")
	@ControlAspect(funtionCd = "查询用户")
	@ControllerException(type = "page")
	public ModelAndView findByUserCd(String userId) throws BaseException {
		ModelAndView mv = new ModelAndView("/pages/sys/usermng/updateUser");
		SysUser sysUser = userMngService.findByUserCd(getLoginUserBean(), userId);
		mv.addObject("sysUser", sysUser);
		return mv;
	}

	/**
	 * 
	 * Title: 密码重置<br/>
	 * Description:
	 * @return
	 */
	@RequestMapping(value = "passReSet")
	@ControlAspect(funtionCd = "i2_sys_user_list", operType = OperType.UPDATE, havPrivs = true)
	@ControllerException
	public void passReSet(String[] userIds) throws BaseException {
		userMngService.passReSet(getLoginUserBean(), userIds);
	}

	/**
	 * 
	 * Title: 添加用户<br/>
	 * Description:
	 * @return
	 */
	@RequestMapping(value = "addUser")
	@ControlAspect(funtionCd = "i2_sys_user_list", operType = OperType.ADD, havPrivs = true)
	@ControllerException
	public void addUser(SysUser sysUser) throws BaseException {
		userMngService.addUser(getLoginUserBean(), sysUser);
	}

	/**
	 * Title: 修改用户<br/>
	 * Description:
	 * @return
	 */
	@RequestMapping(value = "updateUser")
	@ControlAspect(funtionCd = "i2_sys_user_list", operType = OperType.UPDATE, havPrivs = true)
	@ControllerException
	public void updateUser(SysUser sysUser) throws BaseException {
		userMngService.updateUser(getLoginUserBean(), sysUser);
	}

	
	/**
	 * Title: 删除用户<br/>
	 * Description:
	 * @return
	 */
	@RequestMapping(value = "deleteUser")
	@ControlAspect(funtionCd = "i2_sys_user_list", operType = OperType.DELETE, havPrivs = true)
	@ControllerException
	public void deleteUser(String userIds) throws BaseException {
		userMngService.deleteUser(getLoginUserBean(), userIds);
	}

	/**
	 * Title: 删除用户<br/>
	 * Description:
	 * @return
	 */
	@RequestMapping(value = "updatePassword")
	@ControlAspect(funtionCd = "修改密码", operType = OperType.UPDATE, havPrivs = false)
	@ControllerException
	public void updatePassword(String oldpass, String repassword) throws BaseException {
		userMngService.updatePassword(getLoginUserBean(), oldpass, repassword);
	}
	/**
	 * 
	 * Title: 查询用户<br/>
	 * Description:
	 * @param sysUserQBean
	 * @return
	 */
	@RequestMapping(value = "setRoleBef")
	@ControlAspect(funtionCd = "查询用户")
	@ControllerException(type = "page")
	public ModelAndView setRoleBef(String userId) throws BaseException {
		ModelAndView mv = new ModelAndView("/pages/sys/usermng/setRole");
		SysUser sysUser = userMngService.findByUserCd(getLoginUserBean(), userId);
		mv.addObject("sysUser", sysUser);
		return mv;
	}
}
