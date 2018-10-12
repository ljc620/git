package com.tbims.service;




import com.tbims.bean.StorageTicketBean;
import com.tbims.entity.StrTicketInfo;
import com.zhming.support.bean.PageBean;
import com.zhming.support.bean.UserBean;
import com.zhming.support.exception.ServiceException;
/**
 * 
* Title:   中心仓储库存明细查询
* Description: 
* @ClassName: IStorageTicketService
* @author ly
* @date 2017年7月23日 下午5:28:33
*
 */
public interface IStorageTicketService {
	/**
	 * 
	* Title: 中心仓储库存明细查询分页
	* Description: 
	* @param userBean
	* @param strChest
	* @return
	 */
	public PageBean<StorageTicketBean> listStorageTicket(UserBean userBean,StrTicketInfo strTicketInfo);
	/**
	 * 
	* Title: 查询状态列
	* Description: 
	* @param chestId
	* @return
	* @throws ServiceException
	 */
	public String getStat(String chestId) throws ServiceException;
}
