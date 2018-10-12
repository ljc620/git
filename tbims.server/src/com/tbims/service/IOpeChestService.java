package com.tbims.service;

import com.tbims.bean.OpeChestBean;
import com.tbims.entity.StrChest;
import com.zhming.support.bean.PageBean;
import com.zhming.support.bean.UserBean;
/**
 * 
* Title:  中心入库明细查询
* Description: 
* @ClassName: IOpeChestService
* @author ly
* @date 2017年8月8日 上午9:47:21
*
 */
public interface IOpeChestService {
	/**
	 * 
	* Title: 中心入库明细查询
	* Description: 
	* @param userBean
	* @param strChest
	* @return
	 */
	public PageBean<OpeChestBean> listStorageTicket(UserBean userBean,StrChest strChest);
}
