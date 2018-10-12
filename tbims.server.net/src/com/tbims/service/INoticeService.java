package com.tbims.service;

import com.tbims.bean.SlNoticeBean;
import com.tbims.entity.SlNotice;
import com.zhming.support.bean.PageBean;
import com.zhming.support.bean.UserBean;

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
	* Title: 显示公告<br/>
	* Description: 
	* @param noticeId
	* @return
	 */
	public SlNotice showNotice(String noticeId);
	
	
}
