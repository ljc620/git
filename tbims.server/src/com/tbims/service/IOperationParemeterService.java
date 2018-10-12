package com.tbims.service;

import java.util.Map;

import com.tbims.entity.SysParemeter;
import com.zhming.support.bean.PageBean;
import com.zhming.support.bean.UserBean;
import com.zhming.support.exception.ServiceException;


/**
* Title:  运营参数管理 <br/>
* Description: 
* @ClassName: IOperationParemeterService
* @author ydc
* @date 2017年7月8日 下午3:29:45
* 
*/
public interface IOperationParemeterService {
	/**
	 * 
	* Title: 查询运营参数
	* Description: 
	* @param userBean
	* @param sysParemeter
	* @return
	 */
	public PageBean<Map<String, Object>> listOperationParemeter(UserBean userBean,SysParemeter sysParemeter);
	
	/**
	 * 
	* Title: 添加运营参数
	* Description: 
	* @param userBean
	* @param sysParemeter
	* @throws ServiceException
	 */
	public void  addOperationParemeter(UserBean userBean,SysParemeter sysParemeter) throws ServiceException;
	
	/**
	 * 
	* Title:更新运营参数
	* Description: 
	* @param userBean
	* @param sysParemeter
	 */
	public void updateOperationParemeter(UserBean userBean,SysParemeter sysParemeter);
	
	/**
	 * 
	* Title: 删除运营参数
	* Description: 
	* @param userBean
	* @param paremeterId
	 */
	public void delChangeUser(UserBean userBean,String paremeterId);
	
	
	/**
	 * 
	* Title: 根据参数ID查询
	* Description: 
	* @param paremeterId
	* @return
	 */
	public SysParemeter getById(String paremeterId);
}
