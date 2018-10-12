package com.tbims.service;

import java.util.List;
import java.util.Map;

import com.tbims.bean.OrgSaleTicketType;
import com.tbims.bean.SlAdvanceBean;
import com.tbims.bean.SlDepositBean;
import com.tbims.bean.SlLimitBean;
import com.tbims.entity.SlAdvance;
import com.tbims.entity.SlDeposit;
import com.tbims.entity.SlLimit;
import com.tbims.entity.SlOrg;
import com.tbims.entity.SysTicketType;
import com.zhming.support.bean.PageBean;
import com.zhming.support.bean.UserBean;
import com.zhming.support.exception.BaseException;
import com.zhming.support.exception.ServiceException;

/**
* Title: 签约社、代理商管理  <br/>
* Description: 
* @ClassName: IOrgMngService
* @author ydc
* @date 2017年7月22日 上午11:06:54
* 
*/
public interface IOrgMngService {
	/**
	 * 
	* Title: 查询签约社、代理商列表<br/>
	* Description: 
	* @param userBean
	* @param slOrg
	* @return
	 */
	public PageBean<Map<String, Object>> listOrg(UserBean userBean,SlOrg slOrg,String orgBtype);
	/**
	 * 
	* Title: 添加签约社、代理商信息<br/>
	* Description: 
	* @param userBean
	* @param slOrg
	* @throws ServiceException
	 */
	public void addOrg(UserBean userBean, SlOrg slOrg) throws ServiceException;

	/**
	 * 
	* Title: 根据机构编号查询机构<br/>
	* Description: 
	* @param orgId
	* @return
	 */
	public SlOrg getOrgById(String orgId);
	

	/**
	 * 
	* Title: 更新签约社、代理商<br/>
	* Description: 
	* @param userBean
	* @param slOrg
	 */
	public void updateOrg(UserBean userBean,SlOrg slOrg);
	

	/**
	 * 
	* Title: 更新签约社、代理商状态<br/>
	* Description: 
	* @param userBean
	* @param orgId
	* @param stat
	 */
	public void updateOrgStat(UserBean userBean,String orgId,String stat);
	
	/**
	 * 
	* Title: 删除签约社、代理商<br/>
	* Description: 
	* @param userBean
	* @param orgId
	 */
	public void delOrg(UserBean userBean,String orgId);
	
	
	/**
	 * 
	* Title: 查询额度余额列表<br/>
	* Description: 
	* @param userBean
	* @param orgId
	* @return
	 */
	public PageBean<Map<String, Object>> listAmtLimit(UserBean userBean,String orgId);
    
	/**
	 * 
	* Title: 查询额度列表<br/>
	* Description: 
	* @param orgId
	* @return
	 */
	public PageBean<SlLimitBean> listLimit(UserBean userBean,String orgId,String ticketTypeId);
	
	/** 
	* Title: 查询票种下拉列表<br/>
	* Description: 
	* @param flag a:所有票种 t:团队票 nt:非团队票
	* @return
	*/ 
	public List<SysTicketType> ticketTypeList(String flag);
	
	/**
	 * 
	* Title: 添加额度<br/>
	* Description: 
	* @param userBean
	* @param slLimit
	 * @throws ServiceException 
	 */
	public void addLimit(UserBean userBean,SlLimit slLimit) throws ServiceException;
	
	/**
	 * 
	* Title: 根据组织机构代码获取机构信息<br/>
	* Description: 
	* @param orgId
	* @return
	 */
	public SlOrg getOrg(String orgId);
	
	/**
	 *
	* Title:  添加预付款<br/>
	* Description: 
	* @param slAdvance
	 * @throws ServiceException 
	 */
	public void addAdvance(UserBean userBean,SlAdvance slAdvance) throws ServiceException;
	
	/**
	 * 
	* Title: 查询预付款列表<br/>
	* Description: 
	* @param userBean
	* @param orgId
	* @return
	 */
	public PageBean<SlAdvanceBean> listAdvance(UserBean userBean,String orgId);
	
	/**
	 * 
	* Title: 查询保证金<br/>
	* Description: 
	* @param slOrg
	* @return
	 */
	public PageBean<SlDepositBean> listDeposit(UserBean userBean,String orgId);
	
	/**
	 * 
	* Title: <br/>
	* Description: 
	* @param userBean
	* @param slDeposit
	 */
	public void addDeposit(UserBean userBean,SlDeposit slDeposit);
	
	/**
	 * 
	* Title: 获取保证金总和<br/>
	* Description: 
	* @param orgId
	* @return
	 */
	public Long getDepositSum(String orgId);
	
	/**
	 * 
	* Title: 修改状态
	* Description: 
	* @param userBean
	* @param orgId
	* @param orgStat
	* @throws BaseException
	 */
	public void updateStat(UserBean userBean, String orgId, String orgStat) throws BaseException;
	/**
	 * 
	* Title:重置授权码 <br/>
	* Description: 
	* @param orgIds
	* @throws BaseException
	 */
	public void updateToken(String orgIds) throws BaseException;
	/**
	 * 
	* Title:更新机构回调设置 <br/>
	* Description: 
	* @param orgId
	* @param callbackUrl
	* @param callbackData
	* @throws BaseException
	 */
	public void updateCallBackSetting(String orgId, String callbackUrl, String callbackData) throws BaseException;
	
	/**
	 * 
	* Title:查询机构可售票种列表 <br/>
	* Description: 
	* @param orgId
	* @throws BaseException
	 */
	public List<OrgSaleTicketType> listOrgSaleTicketTypes(String orgId) throws BaseException;
	/**
	 * 
	* Title:更新机构可售票种 <br/>
	* Description: 
	* @param orgId
	* @param callbackUrl
	* @param callbackData
	* @throws BaseException
	 */
	public void updateOrgSaleTicketTypes(String orgId, String[] saleTickets) throws BaseException;
}
