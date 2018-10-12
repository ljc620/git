package com.tbims.service;

import java.util.Map;

import com.tbims.entity.SysParemeter;
import com.zhming.support.bean.PageBean;
import com.zhming.support.bean.UserBean;
import com.zhming.support.exception.ServiceException;

/**
* Title:  销售参数管理 <br/>
* Description: 
* @ClassName: ISaleParemeterService
* @author ydc
* @date 2017年7月8日 下午3:30:59
* 
*/
public interface ISaleParemeterService {
	/**
	 * 
	* Title: 查询销售参数
	* Description: 
	* @param userBean
	* @param sysParemeter
	* @return
	 */
	public PageBean<Map<String, Object>> listSaleParemeter(UserBean userBean,SysParemeter sysParemeter);
	
	/**
	 * 
	* Title: 添加销售参数
	* Description: 
	* @param userBean
	* @param sysParemeter
	* @throws ServiceException
	 */
	public void  addSaleParemeter(UserBean userBean,SysParemeter sysParemeter) throws ServiceException;
	
	/**
	 * 
	* Title: 更新销售参数
	* Description: 
	* @param userBean
	* @param sysParemeter
	 */
	public void updateSaleParemeter(UserBean userBean,SysParemeter sysParemeter);
	/**
	 * 
	* Title: 删除销售参数
	* Description: 
	* @param userBean
	* @param paremeterId
	 */
	public void delSaleParemeter(UserBean userBean,String paremeterId);

	/**
	 * 
	* Title: 根据参数ID查询
	* Description: 
	* @param paremeterId
	* @return
	 */
	public SysParemeter getById(String paremeterId);
}
