package com.tbims.service;

import com.tbims.entity.SysVenue;
import com.zhming.support.bean.PageBean;
import com.zhming.support.bean.UserBean;
import com.zhming.support.exception.BaseException;
import com.zhming.support.exception.DBException;
import com.zhming.support.exception.ServiceException;

/**
 * 
* Title:场馆管理 <br/>
* Description: 
* @ClassName: IVenueMngService
* @author syq
* @date 2017年7月20日 下午2:47:42
*
 */
public interface IVenueMngService {

	/**
	 * 
	* Title: 添加场馆<br/>
	* Description: 
	* @param userBean
	* @param sysVenue
	 */
	public void addVenue(UserBean userBean,SysVenue sysVenue) throws ServiceException ;
	
	/**
	 * 查询场馆列表
	* Title: <br/>
	* Description: 
	* @param sysVenue
	* @return
	 */
	public PageBean<SysVenue> listVenue(UserBean userBean,SysVenue sysVenue) throws DBException;
	
	/**
	 * 
	* Title: 删除场馆<br/>
	* Description: 
	* @param venueId
	 */
	public void delVenue(Long venueId) throws DBException;
	
	/**
	 * 
	* Title: 根据编号获取场馆<br/>
	* Description: 
	* @param venueId
	* @return
	 */
	public SysVenue getVenueById(Long venueId) throws DBException;
	/**
	 * 
	* Title: 更新场馆<br/>
	* Description: 
	* @param userBean
	* @param sysVenue
	 */
	public void  updateVenue(UserBean userBean,SysVenue sysVenue) throws DBException;

	/**
	 * 
	* Title: 修改状态<br/>
	* Description: 
	* @param loginUserBean
	* @param venueIds
	* @param stat
	* @throws BaseException
	 */
	public void updateStat(UserBean loginUserBean, String venueIds, String stat) throws BaseException;
}
