package com.tbims.service;

import java.util.List;
import java.util.Map;

import com.tbims.entity.SysRegion;
import com.tbims.entity.SysVenue;
import com.zhming.support.bean.PageBean;
import com.zhming.support.bean.UserBean;
import com.zhming.support.exception.BaseException;
import com.zhming.support.exception.ServiceException;

/**
* Title: 区域管理  <br/>
* Description: 
* @ClassName: IRegionService
* @author ydc
* @date 2017年7月8日 下午3:23:45
* 
*/
public interface IRegionService {
	/**
	 * 查询区域
	 * @param userBean
	 * @param sysRegion
	 * @return
	 */
	public PageBean<Map<String, Object>> listRegion(UserBean userBean,SysRegion region);
	/**
	 * 添加区域
	 * @param userBean
	 * @param sysRegion
	 * @throws ServiceException
	 */
	public void  addRegion(UserBean userBean,SysRegion region) throws ServiceException;
	
	/**
	 * 更新区域
	 * @param userBean
	 * @param sysRegion
	 */
	public void updateRegion(UserBean userBean,SysRegion region);
	/**
	 * 场馆列表
	 * @return
	 */
	public List<SysVenue> venueList();
	/**
	 * 删除
	 * @param userBean
	 * @param regionId
	 */
	public void delRegion(UserBean userBean,Long regionId);
	
	/**
	 * 
	* Title: 根据区域编号查询
	* Description: 
	* @param regionId
	* @return
	 */
	public SysRegion getById(Long regionId);
	/**
	 * 
	* Title: 根据区域Id查询场馆 
	* Description: 
	* @param regionId
	* @return
	* @throws ServiceException
	 */
	public String getVenueStr(Long regionId)throws ServiceException;
	/**
	 * 
	* Title: 修改状态
	* Description: 
	* @param userBean
	* @param regionId
	* @param stat
	* @throws BaseException
	 */
	public void updateStat(UserBean userBean, String regionId, String stat) throws BaseException;
	
}
