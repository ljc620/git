package com.tbims.service;


import com.tbims.bean.SysBlackListBean;
import com.tbims.entity.SysBlackList;
import com.zhming.support.bean.PageBean;
import com.zhming.support.bean.UserBean;
import com.zhming.support.exception.BaseException;
import com.zhming.support.exception.ServiceException;



/**
* Title: 黑名单管理  <br/>
* Description: 
* @ClassName: IBlackListMngService
* @author ydc
* @date 2017年7月22日 上午11:09:24
* 
*/
public interface IBlackListMngService {
	/**
	 * 查询黑名单
	 * @param userBean
	 * @return
	 */
	public PageBean<SysBlackList> listBlackList(UserBean userBean,SysBlackList sysBlackList);
	/**
	 * 添加黑名单
	 */
	public void  addBlackList(UserBean userBean,SysBlackListBean sysBlackListBean) throws ServiceException;
	/**
	 * 
	* Title: 修改状态
	* Description: 
	* @param userBean
	* @param blackListId
	* @param stat
	* @throws BaseException
	 */
	public void updateStat(UserBean userBean, String blackListId, String stat) throws BaseException;
}
