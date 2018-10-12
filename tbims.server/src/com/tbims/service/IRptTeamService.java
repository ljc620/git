package com.tbims.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.tbims.bean.CustFlowBean;
import com.tbims.bean.CustFlowDateTimeBean;
import com.tbims.bean.CustFlowDayBean;
import com.tbims.bean.CustFlowTimeBean;
import com.tbims.bean.RefundTicketBean;
import com.tbims.bean.RptTeamOrderDetailBean;
import com.tbims.bean.TicketTeamOrderBean;
import com.tbims.bean.WlAndStTicketChangeBean;
import com.tbims.bean.WlOrgChangeBean;
import com.zhming.support.bean.PageBean;
import com.zhming.support.bean.UserBean;
import com.zhming.support.exception.BaseException;

public interface IRptTeamService {

	/**
	 * 
	 * Title: 网络代理商换票统计报表<br/>
	 * Description:
	 * @param loginUserBean
	 * @param wlBean
	 * @return
	 * @throws BaseException
	 */
	public PageBean<WlOrgChangeBean> listRptTeamWl(UserBean loginUserBean, WlOrgChangeBean wlBean) throws BaseException;

	/**
	 * 
	 * Title: 网络代理商售票统计报表<br/>
	 * Description:
	 * @param loginUserBean
	 * @param wlBean
	 * @return
	 * @throws BaseException
	 */
	public PageBean<WlOrgChangeBean> listRptTeamWlSale(UserBean loginUserBean, WlOrgChangeBean wlBean) throws BaseException;
	
	/**
	 * 
	 * Title: 实体代理商销售统计报表<br/>
	 * Description:
	 * @param loginUserBean
	 * @param sTBean
	 * @return
	 * @throws BaseException
	 */
	public PageBean<WlAndStTicketChangeBean> listRptTeamSt(UserBean loginUserBean, WlAndStTicketChangeBean sTBean) throws BaseException;

	/**
	 * 
	 * Title: 团队换票统计报表<br/>
	 * Description:
	 * @param loginUserBean
	 * @param changeOutletId
	 * @param ticketTypeId
	 * @param changeTime
	 * @throws BaseException
	 * @return
	 */
	public List<RptTeamOrderDetailBean> listRptTeamTd(UserBean loginUserBean, String ticketTypeId, Date changeTime) throws BaseException;

	/**
	 * 
	 * Title: 客流日统计<br/>
	 * Description:
	 * @param loginUserBean
	 * @param startDate
	 * @param endDate
	 * @param venueId
	 * @param regionId
	 * @param ticketTypeId
	 * @return
	 * @throws BaseException
	 */
	public List<CustFlowDayBean> listCustFlowDay(UserBean loginUserBean, Date startDate, Date endDate, String venueId, String regionId, String ticketTypeId) throws BaseException;

	/**
	 * 
	 * Title: 客流分时段统计<br/>
	 * Description:
	 * @param loginUserBean
	 * @param date
	 * @param startTime
	 * @param endTime
	 * @param intervalTime
	 * @param venueId
	 * @param regionId
	 * @param ticketTypeId
	 * @throws BaseException
	 * @return
	 */
	public List<CustFlowDateTimeBean> listCustFlowInterTime(UserBean loginUserBean, Date date, String startTime, String endTime, String intervalTime, String venueId, String regionId, String ticketTypeId) throws BaseException;

	/**
	 * 
	 * Title: 查询指定间隔对应的开始时间<br/>
	 * Description:
	 * @param splitTime
	 * @return
	 * @throws BaseException
	 */
	public List<Map<String, Object>> listStartTime(String splitTime) throws BaseException;

	/**
	 * 
	 * Title: 查询指定间隔对应的结束时间<br/>
	 * Description:
	 * @param splitTime
	 * @return
	 * @throws BaseException
	 */
	public List<Map<String, Object>> listEndTime(String splitTime) throws BaseException;

	/**
	 * 
	 * Title: 团队换票统计（单票种）<br/>
	 * Description:
	 * @param userBean
	 * @param tDBean
	 * @return
	 * @throws BaseException
	 */
	public PageBean<TicketTeamOrderBean> listRptTeamTdX(UserBean userBean, TicketTeamOrderBean tDBean) throws BaseException;

	/**
	 * 
	* Title: 客流量统计（按区域、票种）<br/>
	* Description: 
	* @param loginUserBean
	* @param startDate
	* @param endDate
	* @param regionId
	* @return
	* @throws BaseException
	 */
	public List<CustFlowBean> listCustFlowDayN(UserBean loginUserBean, Date startDate, Date endDate, String regionId) throws BaseException;

	/**
	 * 
	* Title:客流量统计（按区域、时间段） <br/>
	* Description: 
	* @param loginUserBean
	* @param date
	* @param startTime
	* @param endTime
	* @param intervalTime
	* @return
	* @throws BaseException
	 */
	public List<CustFlowTimeBean> listCustFlowInterTimeN(UserBean loginUserBean, Date date, String startTime, String endTime, String intervalTime) throws BaseException;

	/**
	 * 
	* Title: 查询场馆名称<br/>
	* Description: 
	* @param venueId
	* @return
	* @throws BaseException
	 */
	public String queryVenueName(String venueId) throws BaseException;

	/**
	 * 
	* Title: 查询区域名称<br/>
	* Description: 
	* @param regionId
	* @return
	* @throws BaseException
	 */
	public String queryRegionName(String regionId) throws BaseException;

	/**
	 * 
	* Title: 查询票种名称<br/>
	* Description: 
	* @param ticketTypeId
	* @return
	* @throws BaseException
	 */
	public String queryTicketTypeName(String ticketTypeId) throws BaseException;
	
	/**
	 * 
	* Title: 导出excel<br/>
	* Description: 
	* @param loginUserBean
	* @param ticketTypeId
	* @param changeTime
	* @return
	 */
	public List<TicketTeamOrderBean> listRptTeamTdExp(UserBean loginUserBean, String ticketTypeId, Date changeTime) throws BaseException;

	/**
	 * 
	* Title: excel合并单元格<br/>
	* Description: 取得要合并行的行、列
	* @param teamList
	* @param ticketTypeId
	* @param changeTime
	* @return
	* @throws BaseException
	 */
	public List<Integer[]> getExcelMerge(List<TicketTeamOrderBean> teamList, String ticketTypeId, Date changeTime) throws BaseException;

	/**
	 * 
	* Title: 网点退票统计报表<br/>
	* Description: 
	* @param loginUserBean
	* @param refundTime
	* @return
	 */
	public PageBean<RefundTicketBean> outletRefundTicket(UserBean loginUserBean,Date refundTimeBegin,Date refundTimeEnd) throws BaseException;

}
