package com.zhming.basic.service;

import java.util.Map;
import java.util.Set;

import com.tbims.entity.SysRole;
import com.zhming.support.bean.PageBean;
import com.zhming.support.bean.UserBean;
import com.zhming.support.exception.DBException;
import com.zhming.support.exception.ServiceException;

public interface IRoleMngService {

	/**
	 * 
	 * Title: 根据角色代码查询角色<br/>
	 * Description:
	 * @param userBean
	 * @param roleId 角色代码
	 * @return
	 * @throws DBException
	 */
	public SysRole findByRoleId(UserBean userBean, String roleId) throws DBException;

	/**
	 * Title:新增角色 <br/>
	 * Description:新增角色的同时为角色添加相应的权限
	 * @param userBean
	 * @param role 角色实体
	 * @param funcKeys 权限字符串
	 * @throws BFException
	 */
	public void addRole(UserBean userBean, SysRole role, String funcKeys) throws ServiceException;
	/**
	 * Title: 更新角色<br/>
	 * Description: 可修改角色对应的权限
	 * @param userBean
	 * @param role
	 * @param funcKeys 权限字符串
	 * @throws BFException
	 */
	public void updateRole(UserBean userBean, SysRole role, String funcKeys) throws DBException;

	/**
	 * Title:删除角色 <br/>
	 * Description:
	 * @param userBean
	 * @param roleId
	 * @throws BFException
	 */
	public void deleteByRoleIds(UserBean userBean, String roleId) throws DBException;

	/**
	 * 
	 * Title: 查询显示角色列表<br/>
	 * Description:
	 * @param userBean
	 * @param role
	 * @param page
	 * @param pageSize
	 * @return
	 */
	public PageBean<Map<String, Object>> listRole(UserBean userBean, SysRole role);

	/**
	 * Title:查询角色对应权限 <br/>
	 * Description: 修改角色时，角色对应的权限选中
	 * @param userBean
	 * @param roleId
	 * @return
	 * @throws BFException
	 */
	public Set<String> findRoleFunc(UserBean userBean, String roleId);
}
