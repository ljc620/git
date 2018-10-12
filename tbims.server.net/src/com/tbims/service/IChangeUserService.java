package com.tbims.service;

import java.util.Map;

import com.tbims.entity.SlChangeUser;
import com.zhming.support.bean.PageBean;
import com.zhming.support.bean.UserBean;
import com.zhming.support.exception.ServiceException;

public interface IChangeUserService {
	/**
	 * 
	* Title: 查询换票人
	* Description: 
	* @param userBean
	* @param slChangeUser
	* @return
	 */
	public PageBean<Map<String, Object>> listChangeUser(UserBean userBean,SlChangeUser slChangeUser);
	
	/**
	 * 
	* Title:添加换票人
	* Description: 
	* @param userBean
	* @param slChangeUser
	* @throws ServiceException
	 */
	public void  addChangeUser(UserBean userBean,SlChangeUser slChangeUser) throws ServiceException;
	
	/**
	 * 
	* Title: 更改换票人
	* Description: 
	* @param userBean
	* @param slChangeUser
	 */
	public void updateChangeUser(UserBean userBean,SlChangeUser slChangeUser);
	
	/**
	 * 
	* Title: 删除换票人
	* Description: 
	* @param userBean
	* @param changeUserId
	 */
	public void delChangeUser(UserBean userBean,String changeUserId);
	
	/**
	 * 
	* Title: 换票人证件类型
	* Description: 
	* @param changeUserId
	* @return
	* @throws ServiceException
	 */
	public String getCareType(String changeUserId)throws ServiceException;
	
	/**
	 * 
	* Title: 根据换票人Id查询
	* Description: 
	* @param changeUserId
	* @return
	 */
	public SlChangeUser getById(String changeUserId);
}
