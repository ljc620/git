package com.tbims.service;

import java.util.Date;
import java.util.List;

import com.tbims.bean.PayTypeBean;
import com.tbims.bean.RptStrinfoDBean;
import com.tbims.bean.RptStrinfoOutletDBean;
import com.tbims.bean.SyntheticalBean;
import com.tbims.entity.SysUser;
import com.zhming.support.bean.PageBean;
import com.zhming.support.bean.UserBean;
import com.zhming.support.exception.BaseException;

/**
 * 
* Title:   <br/>
* Description: 
* @ClassName: IRptSumService
* @author ly
* @date 2017年7月23日 下午5:27:15
*
 */
public interface IRptSumService {
	/**
	 * 
	* Title: 票务中心库存查询
	* Description: 
	* @param userBean
	* @param rptDate
	* @param ticketTypeId
	* @return
	 */
	public PageBean<RptStrinfoDBean> listRptStrinfoD(UserBean userBean,Date startDate,Date endDate,String ticketTypeId);
	
	/**
	 * 
	* Title: 网点库存查询
	* Description: 
	* @param userBean
	* @param rptDate
	* @param outletId
	* @param ticketTypeId
	* @return
	 */
	public PageBean<RptStrinfoOutletDBean> listRptSumOutlet(UserBean userBean, Date startDate,Date endDate,Long outletId,String ticketTypeId);
	/**
	 * 
	* Title: 支付方式统计表
	* Description: 
	* @param loginUserBean
	* @param startDate
	* @param endDate
	* @param outletId
	* @param saleUserId
	* @return
	* @throws BaseException
	 */
	public List<PayTypeBean> listPayType( Date startDate, Date endDate, String outletId, String userId) throws BaseException;
	/**
	 * 
	* Title: 综合汇总查询
	* Description: 
	* @param startDate
	* @param endDate
	* @param ticketTypeId
	* @return
	* @throws BaseException
	 */
	
	public List<SyntheticalBean> listSynthetical( Date startDate, Date endDate, String ticketTypeId) throws BaseException;
	/**
	 * 
	* Title: 用户账号
	* Description: 
	* @param userId
	* @return
	 */
	public List<SysUser> userList(Long outletId);
	
}
