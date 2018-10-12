package com.tbims.service;

import java.util.List;
import java.util.Map;

import com.tbims.entity.SlOrg;
import com.tbims.entity.SysOutlet;
import com.zhming.support.bean.PageBean;
import com.zhming.support.bean.UserBean;
import com.zhming.support.exception.BaseException;
import com.zhming.support.exception.ServiceException;

/**
* Title:  网点管理 <br/>
* Description: 
* @ClassName: IOutletMngService
* @author ydc
* @date 2017年7月8日 下午3:28:28
* 
*/
public interface IOutletMngService {

	/**
	 * 
	* Title: 添加网点<br/>
	* Description: 
	* @param userBean
	* @param sysOutlet
	 */
	public void addOutlet(UserBean userBean,SysOutlet sysOutlet) throws ServiceException ;
	
	/**
	 * 查询网点列表
	* Title: <br/>
	* Description: 
	* @param sysOutlet
	* @return
	 */
	public PageBean<Map<String, Object>> listOutlet(UserBean userBean,SysOutlet sysOutlet);
	
	/**
	 * 
	* Title: 删除网点<br/>
	* Description: 
	* @param outletId
	 */
	public void delOutlet(Long outletId);
	/**
	 * 
	* Title: 查询机构列表<br/>
	* Description: 
	* @return
	 */
	public List<SlOrg> orgList(String orgType);
	
	/**
	 * 
	* Title: 根据编号获取网点<br/>
	* Description: 
	* @return
	 */
	public SysOutlet getOutletById(Long outletId);
	/**
	 * 
	* Title: 更新网点<br/>
	* Description: 
	* @param userBean
	* @param sysOutlet
	 */
	public void  updateOutlet(UserBean userBean,SysOutlet sysOutlet);
	
	/**
	 * 
	* Title: 修改网点状态<br/>
	* Description: 
	* @param userBean
	* @param outletId
	* @param stat
	* @throws BaseException
	 */
	public void updateStat(UserBean userBean, String outletId, String stat) throws BaseException;
}
