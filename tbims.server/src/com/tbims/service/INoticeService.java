package com.tbims.service;

import com.tbims.bean.SlNoticeBean;
import com.tbims.entity.SlNotice;
import com.zhming.support.bean.PageBean;
import com.zhming.support.bean.UserBean;
import com.zhming.support.exception.ServiceException;

/**
* Title: 信息公告管理  <br/>
* Description: 
* @ClassName: INoticeService
* @author ydc
* @date 2017年7月8日 下午3:30:20
* 
*/
public interface INoticeService {
	/**
	 * 
	* Title:查询信息公告
	* Description: 
	* @param userBean
	* @param slNotice
	* @return
	 */
	public PageBean<SlNoticeBean> listNotice(UserBean userBean,SlNotice slNotice);
	
	/**
	 * 
	* Title: 添加信息公告
	* Description: 
	* @param userBean
	* @param slNotice
	* @throws ServiceException
	 */
	public void  addNotice(UserBean userBean,SlNotice slNotice) throws ServiceException;
	
	/**
	 * 
	* Title: 删除信息公告
	* Description: 
	* @param userBean
	* @param noticeId
	 */
	public void delNotice(UserBean userBean,String noticeId);
	/**
	 * 
	* Title: 查询优先级列表
	* Description: 
	* @param noticeId
	* @return
	* @throws ServiceException
	 */
	public String getLev(String noticeId)throws ServiceException;
	
	/**
	 * 
	* Title: 查询内容
	* Description: 
	* @param noticeId
	* @return
	 */
	public SlNotice showNotice(String noticeId);
	
}
