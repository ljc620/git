package com.tbims.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.tbims.bean.CustFlowDayBean;
import com.tbims.bean.RptCheckBean;
import com.tbims.bean.RptDeliveryBean;
import com.tbims.bean.RptOutletSale1Bean;
import com.tbims.bean.RptStrinfoBean;
import com.tbims.bean.TicketCheckByIdenttyBean;
import com.tbims.bean.TicketNoCheckBean;
import com.tbims.bean.TicketSaleByIdenttyBean;
import com.zhming.support.bean.PageBean;
import com.zhming.support.bean.UserBean;
import com.zhming.support.exception.BaseException;
import com.zhming.support.exception.ServiceException;

/**
 * 
 * Title: <br/>
 * Description:
 * 
 * @ClassName: IRptSaleService
 * @author ly
 * @date 2017年7月11日 上午9:56:34
 *
 */
public interface IRptSaleService {
	/**
	 * 
	 * Title: 网点当日库存查询分页<br/>
	 * Description:
	 * 
	 * @param userBean
	 * @param ticketTypeId
	 * @return
	 */
	public PageBean<RptStrinfoBean> getOutletStorePage(UserBean userBean, String ticketTypeId, Long outletId);

	/**
	 * 
	 * Title: 票务中心当日库存查询分页<br/>
	 * Description:
	 * 
	 * @param userBean
	 * @param ticketTypeId
	 * @return
	 */
	public PageBean<RptStrinfoBean> getCenterStorePage(UserBean userBean, String ticketTypeId);

//	/**
//	 * 
//	 * Title: 网点销售统计表EXCEL导出<br/>
//	 * Description:
//	 * 
//	 * @param rptDt
//	 * @param flag
//	 * @return
//	 */
//	public List<RptOutletSale1Bean> getRptOutletSaleBeanList(Date rptDt, String flag);

	/**
	 * 
	 * Title: 网点销售统计表分页<br/>
	 * Description:
	 * 
	 * @param userBean
	 * @param rptDt
	 * @return
	 */
	public List<RptOutletSale1Bean> getRptOutletSaleBeanListPage(Date rptDt, String flag,boolean isExcel);

	/**
	 * 
	 * Title: 出库配送查询分页<br/>
	 * Description:
	 * 
	 * @param beginDt
	 * @param endDt
	 * @param outletId
	 * @param ticketTypeId
	 * @return
	 */
	public PageBean<RptDeliveryBean> getRptDeliveryBeanListPage(UserBean userBean, Date beginDt, Date endDt, Long outletId, String ticketTypeId);

	/**
	 * 
	 * Title: 首页日销售<br/>
	 * Description:
	 * 
	 * @param sbeginTime
	 * @param sendTime
	 * @return
	 * @throws ServiceException
	 */

	public Map<String, Object> saleDay(String sbeginTime, String sendTime) throws ServiceException;

	/**
	 * 
	 * Title: 首页日检票<br/>
	 * Description:
	 * 
	 * @param cbeginTime
	 * @param cendTime
	 * @return
	 * @throws ServiceException
	 */
	public Map<String, Object> checkDay(String cbeginTime, String cendTime) throws ServiceException;

	/**
	 * 
	 * Title: 首页月销售<br/>
	 * Description:
	 * 
	 * @return
	 */
	public Map<String, Object> saleMonth();

	/**
	 * 
	 * Title: 首页月检票<br/>
	 * Description:
	 * 
	 * @return
	 */
	public Map<String, Object> checkMonth();

	/**
	 * 
	 * Title: 票据信息<br/>
	 * Description:
	 * 
	 * @param ticketId
	 * @return
	 * @throws ServiceException
	 */
	public Map<String, Object> ticketBaseInfo(Long ticketId) throws ServiceException;

	/**
	 * 票据配送信息
	 * 
	 * @param ticketId
	 * @return
	 * @throws ServiceException
	 */
	public Map<String, Object> ticketDelInfo(Long ticketId) throws ServiceException;

	/**
	 * 
	 * Title: 销售信息<br/>
	 * Description:
	 * 
	 * @param ticketId
	 * @return
	 * @throws ServiceException
	 */
	public Map<String, Object> ticketSaleInfo(Long ticketId) throws ServiceException;

	/**
	 * 
	 * Title: 检票信息<br/>
	 * Description:
	 * 
	 * @param ticketId
	 * @return
	 */
	public List<RptCheckBean> checkInfo(Long ticketId) throws ServiceException;

	/**
	 * 
	 * Title: 销售明细<br/>
	 * Description:
	 * 
	 * @param rptDt
	 * @return
	 */
	public PageBean<Map<String, Object>> saleDetail(UserBean userBean, Date rptDt, String beginTime, String endTime, String ticketTypeId, Long outletId, String ejectUserId);

	/**
	 * 
	 * Title: 检票明细<br/>
	 * Description:
	 * 
	 * @param rptDt
	 * @return
	 */
	public PageBean<Map<String, Object>> checkDetail(UserBean userBean, Date rptDt, String beginTime, String endTime, String ticketTypeId, Long venueId, Long regionId, Long clientId);

	/**
	 * 
	 * Title: 查询员工入园信息列表<br/>
	 * Description:
	 * 
	 * @param loginUserBean
	 * @param startDate
	 * @param endDate
	 * @param venueId
	 * @param regionId
	 * @param ticketTypeId
	 * @return
	 * @throws BaseException
	 */
	public List<CustFlowDayBean> listEmpInDay(UserBean loginUserBean, Date startDate, Date endDate, String venueId, String regionId, String empId) throws BaseException;

	/**
	 * 查询售检信息
	 * 
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public List<Map<String, Object>> listSaleChange(Date startDate, Date endDate);

	/**
	 * Title:身份证检票记录列表 <br/>
	 * Description:
	 * 
	 * @param identty_id
	 * @return
	 */
	public List<TicketCheckByIdenttyBean> listTicketCheckByIdentty(String identtyId);

	/**
	 * Title:身份证售票记录列表 <br/>
	 * Description:
	 * 
	 * @param identty_id
	 * @return
	 */
	public List<TicketSaleByIdenttyBean> listTicketSaleByIdentty(String identtyId);

	/**
	 * 自营销售统计报表
	 * 
	 * @param startDate
	 * @param endDate
	 * @param ticketTypeId
	 * @return
	 */
	public List<RptOutletSale1Bean> getRptSelfSaleBeanListPage(Date startDate, Date endDate, String ticketTypeId);

	/**
	 * 
	 * Title: 门票未入园统计<br/>
	 * Description:
	 * 
	 * @param loginUserBean
	 * @return
	 * @throws BaseException
	 */
	public List<TicketNoCheckBean> ticketNoCheck(UserBean loginUserBean) throws BaseException;
	
	/**
	 * 
	* Title: 网络代理商销售明细<br/>
	* Description: 
	* @param loginUserBean
	* @param rptDt
	* @param ticketTypeId
	* @return
	* @throws BaseException
	 */
	public PageBean<Map<String, Object>> wlSaleDetail(UserBean loginUserBean, Date rptDt, String ticketTypeId, String orgId, String orderId) throws BaseException;

	/**
	 * 
	* Title: 网络代理商换票明细<br/>
	* Description: 
	* @param loginUserBean
	* @param rptDt
	* @param beginTime
	* @param endTime
	* @param ticketTypeId
	* @return
	* @throws BaseException
	 */
	public PageBean<Map<String, Object>> wlSaleDetailByHB(UserBean loginUserBean, Date rptDt, String beginTime, String endTime, String ticketTypeId,String orgId,String outletId) throws BaseException;
	
	/** 
	* Title: 入园游客渠道来源统计 <br/>
	* Description: 
	* @param loginUserBean
	* @param beginDate
	* @param endDate
	* @return
	*/ 
	public List<Map<String, Object>> regionCheckTicketByChannel(UserBean loginUserBean,Date beginDate, Date endDate);
	
	/**
	 * 
	* Title: 网络代理商退票明细<br/>
	* Description: 
	* @param loginUserBean
	* @param startDate
	* @param endDate
	* @param orgId
	* @return
	* @throws BaseException
	 */
	public PageBean<Map<String, Object>> wlRefundDetail(UserBean loginUserBean, Date startDate, Date endDate, String orgId) throws BaseException;

	/**
	 * 
	* Title: 网络代理商门票核销明细<br/>
	* Description: 
	* @param loginUserBean
	* @param startDate
	* @param endDate
	* @param orgId
	* @return
	* @throws BaseException
	 */
	public PageBean<Map<String, Object>> wlCheckdDetail(UserBean loginUserBean, Date startDate, Date endDate, String orgId) throws BaseException;
}
