package com.tbims.service;

import java.util.Map;

import com.tbims.bean.SysUserQBean;
import com.tbims.entity.SysUser;
import com.zhming.support.bean.PageBean;
import com.zhming.support.bean.UserBean;
import com.zhming.support.exception.BaseException;

/**
 * 
 * Title: 用户管理 
 * Description:
 * @ClassName: IUserService
 * @author syq
 * @date 2016年12月12日 下午4:45:57
 *
 */
public interface IUserMngService {

	/**
	 * 
	 * Title: 查询用户列表<br/>
	 * Description:
	 * @param userBean
	 * @param sysUserQBean
	 * @return
	 */
	public PageBean<Map<String, Object>> listUser(UserBean userBean, SysUserQBean sysUserQBean);

	/**
	 * 
	 * Title: 设置用户角色<br/>
	 * Description:
	 * @param userBean
	 * @param userCds 用户代码
	 * @param roleCd 角色代码
	 * @throws BaseException
	 */
	public void setRoleToUser(UserBean userBean, String userCds, String roleCds);

	/**
	 * 
	 * Title:修改用户的状态 <br/>
	 * Description:
	 * @param userId
	 * @param userStt
	 * @throws BaseException
	 */
	public void updateUserStat(UserBean userBean, String userIds, String userStat) throws BaseException;

	/**
	 * 
	 * Title:查询用户实体 <br/>
	 * Description:
	 * @param userId
	 * @throws BaseException
	 */
	public SysUser findByUserCd(UserBean userBean, String userId) throws BaseException;

	/**
	 * Title:密码重置 <br/>
	 * Description:
	 * @param userBean
	 * @param userIds
	 * @throws BaseException
	 */
	public void passReSet(UserBean userBean, String[] userIds) throws BaseException;

	/**
	 * Title:添加用户 <br/>
	 * Description:
	 * @param userBean
	 * @param sysUser
	 * @throws BaseException
	 */
	public void addUser(UserBean userBean, SysUser sysUser) throws BaseException;

	/**
	 * Title:修改用户 <br/>
	 * Description:
	 * @param userBean
	 * @param sysUser
	 * @throws BaseException
	 */
	public void updateUser(UserBean userBean, SysUser sysUser) throws BaseException;
	
	/**
	 * Title:删除用户 <br/>
	 * Description:
	 * @param userBean
	 * @param sysUser
	 * @throws BaseException
	 */
	public void deleteUser(UserBean userBean, String userIds) throws BaseException;
	
	/** 
	* Title:修改密码 <br/>
	* Description: 
	* @param userBean
	* @param oldpass
	* @param newpass
	* @throws BaseException
	*/ 
	public void updatePassword(UserBean userBean, String oldpass, String newpass) throws BaseException;


}
