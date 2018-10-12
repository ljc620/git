package com.tbims.service;

import java.util.List;
import java.util.Map;

import com.tbims.bean.SysTicketTypePriceBean;
import com.tbims.bean.TicketTypeRuleBean;
import com.tbims.entity.SysTicketType;
import com.tbims.entity.SysTicketTypePrice;
import com.tbims.entity.SysTicketTypeVenue;
import com.tbims.entity.SysVenue;
import com.zhming.support.bean.PageBean;
import com.zhming.support.bean.UserBean;
import com.zhming.support.exception.BaseException;
import com.zhming.support.exception.ServiceException;

/**
* Title: 票种管理  <br/>
* Description: 
* @ClassName: ITicketTypeService
* @author ydc
* @date 2017年7月22日 上午11:22:53
* 
*/
public interface ITicketTypeService {

	/**
	 * 
	* Title: 查询票种列表<br/>
	* Description: 
	* @param userBean
	* @param orgId
	* @return
	 */
	public PageBean<Map<String, Object>> listTicketType(UserBean userBean);
	
	/**
	 * 
	* Title: 添加票种<br/>
	* Description: 
	* @param sysTicketType
	* @param ruleList
	* @param venueList
	 * @throws ServiceException 
	 */
	public void  addTicketType(UserBean userBean,SysTicketType sysTicketType,List<TicketTypeRuleBean> ruleList,String venueIds) throws ServiceException;
	
	/**
	 * 
	* Title: 保存修改票种<br/>
	* Description: 
	* @param sysTicketType
	* @param venueIds
	* @throws ServiceException
	 */
	public void  saveUpdTicketType(UserBean userBean,SysTicketType sysTicketType,String[] venueIds) throws ServiceException;
	
	/**
	 * 
	* Title: 场馆列表<br/>
	* Description: 
	* @return
	 */
	public List<SysVenue> venueList();
	
	/**
	 * 
	* Title: 根据票种编号获取规则列表<br/>
	* Description: 
	* @param userBean
	* @param ticketTypeId
	* @return
	 */
	public PageBean<TicketTypeRuleBean> ruleList(UserBean userBean,String ticketTypeId);
	
	/**
	 * 
	* Title: 根据票种编号获取票种对象<br/>
	* Description: 
	* @param ticketTypeId
	* @return
	 */
	public SysTicketType getTicketType(String ticketTypeId);
	
	/**
	 * 
	* Title: 添加票种规则<br/>
	* Description: 
	* @param ticketTypeId
	* @param ticketTypeRuleBean
	 * @throws ServiceException 
	 */
	public void addTicketTypeRule(UserBean userBean,TicketTypeRuleBean ticketTypeRuleBean) throws ServiceException;
	
	/**
	 * 
	* Title: 删除票种<br/>
	* Description: 
	* @param ticketTypeId
	* @throws ServiceException
	 */
	public void  delTicketType(UserBean userBean,String ticketTypeId) throws ServiceException;
	
	/**
	 * 
	* Title: 根据票种编号查询场馆列表<br/>
	* Description: 
	* @param ticketTypeId
	* @return
	 */
	public List<SysTicketTypeVenue> typeVenueList(String ticketTypeId);
	/**
	 * 
	* Title: 添加票种规则<br/>
	* Description: 
	* @param ticketTypeId
	* @param ticketTypeRuleBean
	 * @throws ServiceException 
	 */
	public void delTicketTypeRule(UserBean userBean,String ruleId) throws ServiceException;
	
	/**
	 * 
	* Title: 获取场馆列表<br/>
	* Description: 
	* @param ticketTypeId
	* @return
	* @throws ServiceException
	 */
	public String getVenueStr(String ticketTypeId)throws ServiceException;
	/**
	 * 
	* Title: 修改票种的状态
	* Description: 
	* @param userBean
	* @param ticketTypeId
	* @param ticketTypeStat
	* @throws BaseException
	 */
	public void updateTicketStat(UserBean userBean, String ticketTypeId, String ticketTypeStat) throws BaseException;

	/**
	 * 
	* Title: 添加阶梯票价段<br/>
	* Description: 
	* @param loginUserBean
	* @param ticTypePrice
	* @throws BaseException
	 */
	public void addTicketTypePrice(UserBean loginUserBean, SysTicketTypePrice ticTypePrice) throws BaseException;

	/**
	 * 
	* Title:  删除票价信息<br/>
	* Description: 
	* @param loginUserBean
	* @param priceId
	* @throws BaseException
	 */
	public void delTicketTypePrice(UserBean loginUserBean, String priceId) throws BaseException;

	/**
	 * 
	* Title: 查询阶梯票价列表<br/>
	* Description: 
	* @param loginUserBean
	* @param ticketTypeId
	* @return
	* @throws BaseException
	 */
	public PageBean<SysTicketTypePriceBean> ticPriceList(UserBean loginUserBean, String ticketTypeId) throws BaseException;

	/**
	 * 
	* Title: 获取该票种库中结束张数最小的数据<br/>
	* Description: 
	* @param ticketTypeId
	* @return
	* @throws BaseException
	 */
	public Long getEndNo(String ticketTypeId) throws BaseException;
	
	/**
	 * 
	* Title: 查询阶梯票价历史表<br/>
	* Description: 
	* @param loginUserBean
	* @param ticketTypeId
	* @return
	* @throws BaseException
	 */
	public PageBean<SysTicketTypePriceBean> ticPriceHList(UserBean loginUserBean, String ticketTypeId) throws BaseException;

	/**
	 * 
	* Title: 阶梯票价张数连续性校验 <br/>
	* Description: 
	* @param loginUserBean
	* @param ticketTypeId
	* @return
	* @throws BaseException
	 */
	public boolean checkContinue(UserBean loginUserBean, String ticketTypeId) throws BaseException;
	
	
}
