package com.tbims.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.tbims.bean.CustFlowBean;
import com.tbims.bean.CustFlowDateTimeBean;
import com.tbims.bean.CustFlowDayBean;
import com.tbims.bean.CustFlowTimeBean;
import com.tbims.bean.RefundTicketBean;
import com.tbims.bean.RptTeamOrderDetailBean;
import com.tbims.bean.TicketTeamOrderBean;
import com.tbims.bean.WlAndStTicketChangeBean;
import com.tbims.bean.WlOrgChangeBean;
import com.tbims.service.IRptTeamService;
import com.zhming.support.BaseController;
import com.zhming.support.annontion.ControlAspect;
import com.zhming.support.annontion.ControllerException;
import com.zhming.support.bean.PageBean;
import com.zhming.support.bean.UserBean;
import com.zhming.support.common.MSG;
import com.zhming.support.common.OperType;
import com.zhming.support.exception.BaseException;
import com.zhming.support.util.DateUtil;
import com.zhming.support.util.StringUtil;

/**
 * 
 * Title: 网络代理商换票统计、实体商代理商销售统计、团队换票统计、客流日统计、 客流分时段统计
 * 客流日统计（按区域、票种统计）、客流（按区域、时间段统计）、 网点退票统计
 * 
 * @ClassName: RptTeamController
 * @author syq
 * @date 2017年7月11日 上午9:31:49
 *
 */
@RestController
@RequestMapping("/rptTeam/")
public class RptTeamController extends BaseController {
	@Autowired
	private IRptTeamService rptTeamService;

	/**
	 * 
	 * Title: 网络代理商换票统计报表<br/>
	 * Description:
	 * 
	 * @param wlBean
	 * @return
	 * @throws BaseException
	 */
	@RequestMapping(value = "listRptTeamWl")
	@ControlAspect(funtionCd = "网络代理商换票统计报表", operType = OperType.QUERY)
	@ControllerException
	public PageBean<WlOrgChangeBean> listRptTeamWl(WlOrgChangeBean wlBean) throws BaseException {
		UserBean userBean = getLoginUserBean();
		userBean.setPageSize(0);
		PageBean<WlOrgChangeBean> wlList = rptTeamService.listRptTeamWl(userBean, wlBean);
		return wlList;
	}

	/**
	 * 
	 * Title:网络代理商换票统计报表_导出EXCEL<br/>
	 * Description:
	 * 
	 * @param wlBean
	 * @throws BaseException
	 */
	@RequestMapping(value = "expExcelWl")
	@ControlAspect(funtionCd = "网络代理商换票统计报表EXCEL导出", operType = OperType.QUERY)
	@ControllerException(type = "page")
	public void expExcelWl(WlOrgChangeBean wlBean) throws BaseException {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			UserBean userBean = getLoginUserBean();
			userBean.setPageSize(0);
			PageBean<WlOrgChangeBean> wlList = rptTeamService.listRptTeamWl(userBean, wlBean);
			map.put("list", wlList.getRows());
			Date dat = new Date();
			Date dat2 = new Date();
			if (wlBean.getOpeTime() != null && wlBean.getOpeTime2() != null) {
				dat = wlBean.getOpeTime();
				dat2 = wlBean.getOpeTime2();
			}
			exportDetailExcel(map, "wlAgentStatistic.xls", "网络代理商换票统计" + DateUtil.formatDateToString(dat, "yyyyMMdd") + "-" + DateUtil.formatDateToString(dat2, "yyyyMMdd") + ".xls");
		} catch (Exception e) {
			throw new BaseException(MSG.EXCEL_ERROR, e);
		}
	}

	/**
	 * 
	 * Title: 网络代理商售票统计报表<br/>
	 * Description:
	 * 
	 * @param wlBean
	 * @return
	 * @throws BaseException
	 */
	@RequestMapping(value = "listRptTeamWlSale")
	@ControlAspect(funtionCd = "网络代理商售票统计报表", operType = OperType.QUERY)
	@ControllerException
	public PageBean<WlOrgChangeBean> listRptTeamWlSale(WlOrgChangeBean wlBean) throws BaseException {
		UserBean userBean = getLoginUserBean();
		userBean.setPageSize(0);
		PageBean<WlOrgChangeBean> wlList = rptTeamService.listRptTeamWlSale(userBean, wlBean);
		return wlList;
	}

	/**
	 * 
	 * Title:网络代理商换票统计报表_导出EXCEL<br/>
	 * Description:
	 * 
	 * @param wlBean
	 * @throws BaseException
	 */
	@RequestMapping(value = "expExcelWlSale")
	@ControlAspect(funtionCd = "网络代理商售票统计报表EXCEL导出", operType = OperType.QUERY)
	@ControllerException(type = "page")
	public void expExcelWlSale(WlOrgChangeBean wlBean) throws BaseException {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			UserBean userBean = getLoginUserBean();
			userBean.setPageSize(0);
			PageBean<WlOrgChangeBean> wlList = rptTeamService.listRptTeamWlSale(userBean, wlBean);
			map.put("list", wlList.getRows());
			Date dat = new Date();
			Date dat2 = new Date();
			if (wlBean.getOpeTime() != null && wlBean.getOpeTime2() != null) {
				dat = wlBean.getOpeTime();
				dat2 = wlBean.getOpeTime2();
			}
			exportDetailExcel(map, "wlAgentSaleStatistic.xls", "网络代理商售票统计" + DateUtil.formatDateToString(dat, "yyyyMMdd") + "-" + DateUtil.formatDateToString(dat2, "yyyyMMdd") + ".xls");
		} catch (Exception e) {
			throw new BaseException(MSG.EXCEL_ERROR, e);
		}
	}

	/**
	 * 
	 * Title: 实体代理商销售统计报表<br/>
	 * Description:
	 * 
	 * @param sTBean
	 * @return
	 * @throws BaseException
	 */
	@RequestMapping(value = "listRptTeamSt")
	@ControlAspect(funtionCd = "实体代理商销售统计报表", operType = OperType.QUERY)
	@ControllerException
	public PageBean<WlAndStTicketChangeBean> listRptTeamSt(WlAndStTicketChangeBean sTBean) throws BaseException {
		UserBean userBean = getLoginUserBean();
		userBean.setPageSize(0);
		PageBean<WlAndStTicketChangeBean> sTList = rptTeamService.listRptTeamSt(userBean, sTBean);
		return sTList;
	}

	/**
	 * 
	 * Title: 实体代理商销售统计表_导出EXCEL<br/>
	 * Description:
	 * 
	 * @param sTBean
	 * @throws BaseException
	 */
	@RequestMapping(value = "expExcelSt")
	@ControlAspect(funtionCd = "实体代理商销售统计表EXCEL导出", operType = OperType.QUERY)
	@ControllerException(type = "page")
	public void expExcelSt(WlAndStTicketChangeBean sTBean) throws BaseException {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			UserBean userBean = getLoginUserBean();
			userBean.setPageSize(0);
			PageBean<WlAndStTicketChangeBean> sTList = rptTeamService.listRptTeamSt(userBean, sTBean);
			map.put("list", sTList.getRows());
			Date dat = new Date();
			exportDetailExcel(map, "stAgentSaleStatistic.xls", "实体代理商销售统计" + DateUtil.formatDateToString(dat, "yyyyMMdd") + ".xls");
		} catch (Exception e) {
			throw new BaseException(MSG.EXCEL_ERROR, e);
		}
	}

	/**
	 * 
	 * Title: 团队换票统计报表-多票种<br/>
	 * Description:
	 * 
	 * @param ticketTypeId 票种
	 * @param changeTime 换票时间
	 * @return
	 * @throws BaseException
	 */
	@RequestMapping(value = "listRptTeamTd")
	@ControlAspect(funtionCd = "团队换票统计报表", operType = OperType.QUERY)
	@ControllerException(type = "page")
	public ModelAndView listRptTeamTd(String ticketTypeId, Date changeTime) throws BaseException {
		ModelAndView mv = new ModelAndView("pages/rpt/rptteam/rptTeamTd/rptTeamTdQuery");
		try {
			if (changeTime == null) {
				changeTime = DateUtil.formatStringToDate(DateUtil.getNowDate(), "yyyy-MM-dd");
			}
			List<RptTeamOrderDetailBean> teamList = rptTeamService.listRptTeamTd(getLoginUserBean(), ticketTypeId, changeTime);
			mv.addObject("teamList", teamList);
			mv.addObject("changeTime", changeTime);
			mv.addObject("ticketTypeId", ticketTypeId);
		} catch (Exception e) {
			throw new BaseException(MSG.DB_ERROR, e);
		}
		return mv;
	}

	/**
	 * 
	 * Title: 团队换票统计-多票种_导出EXCEL<br/>
	 * Description:
	 * 
	 * @param ticketTypeId
	 * @param changeTime
	 * @throws BaseException
	 */
	@RequestMapping(value = "expExcelTd")
	@ControlAspect(funtionCd = "团队换票统计表EXCEL导出", operType = OperType.QUERY)
	@ControllerException(type = "page")
	public void expExcelTd(String ticketTypeId, Date changeTime) throws BaseException {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			List<TicketTeamOrderBean> teamList = rptTeamService.listRptTeamTdExp(getLoginUserBean(), ticketTypeId, changeTime);
			map.put("teamList", teamList);
			Date dat = new Date();
			if (changeTime != null) {
				dat = changeTime;
			}
			List<Integer[]> mergeList = rptTeamService.getExcelMerge(teamList, ticketTypeId, dat);
			exportDetailExcelMerge(map, "tdChangeTicket2.xls", "团队换票统计_多票种" + DateUtil.formatDateToString(dat, "yyyyMMdd") + ".xls", mergeList);
		} catch (Exception e) {
			throw new BaseException(MSG.EXCEL_ERROR, e);
		}
	}

	/**
	 * 
	 * Title:客流日统计 <br/>
	 * Description:
	 * 
	 * @param startDate
	 * @param endDate
	 * @param venueId
	 * @param regionId
	 * @param ticketTypeId
	 * @return
	 * @throws BaseException
	 */
	@RequestMapping(value = "listCustFlowDay")
	@ControlAspect(funtionCd = "客流日统计", operType = OperType.QUERY)
	@ControllerException(type = "page")
	public ModelAndView listCustFlowDay(Date startDate, Date endDate, String venueId, String regionId, String ticketTypeId, String venueName, String regionName, String ticketTypeName) throws BaseException {
		ModelAndView mv = new ModelAndView("pages/rpt/rptteam/customer/custDayStat");
		try {
			if (startDate == null && endDate == null) {
				startDate = DateUtil.formatStringToDate(DateUtil.getYestoday(), "yyyy-MM-dd");
				endDate = startDate;
			}
			List<CustFlowDayBean> list = rptTeamService.listCustFlowDay(getLoginUserBean(), startDate, endDate, venueId, regionId, ticketTypeId);
			mv.addObject("venueName", venueName);
			mv.addObject("regionName", regionName);
			mv.addObject("ticketTypeName", ticketTypeName);
			mv.addObject("list", list);
			mv.addObject("startDate", startDate);
			mv.addObject("endDate", endDate);
			mv.addObject("venueId", venueId);
			mv.addObject("regionId", regionId);
			mv.addObject("ticketTypeId", ticketTypeId);
		} catch (Exception e) {
			throw new BaseException(MSG.DB_ERROR, e);
		}
		return mv;
	}

	/**
	 * 
	 * Title:客流日统计_导出excel <br/>
	 * Description:
	 * 
	 * @param startDate
	 * @param endDate
	 * @param venueId
	 * @param regionId
	 * @param ticketTypeId
	 * @throws BaseException
	 */
	@RequestMapping(value = "expExcelCustFlowDay")
	@ControlAspect(funtionCd = "客流日统计表EXCEL导出", operType = OperType.QUERY)
	@ControllerException(type = "page")
	public void expExcelCustFlowDay(Date startDate, Date endDate, String venueId, String regionId, String ticketTypeId) throws BaseException {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			List<CustFlowDayBean> list = rptTeamService.listCustFlowDay(getLoginUserBean(), startDate, endDate, venueId, regionId, ticketTypeId);
			map.put("list", list);
			exportDetailExcel(map, "custDayStatistic.xls", "客流日统计" + DateUtil.formatDateToString(startDate, "yyyyMMdd") + "-" + DateUtil.formatDateToString(endDate, "yyyyMMdd") + ".xls");
		} catch (Exception e) {
			throw new BaseException(MSG.EXCEL_ERROR, e);
		}
	}

	/**
	 * 
	 * Title:客流量分时统计 <br/>
	 * Description:
	 * 
	 * @param startTime
	 * @param endTime
	 * @param venueId
	 * @param regionId
	 * @param ticketTypeId
	 * @return
	 * @throws BaseException
	 */
	@RequestMapping(value = "listCustFlowInterTime")
	@ControlAspect(funtionCd = "客流分时段统计", operType = OperType.QUERY)
	@ControllerException(type = "page")
	public ModelAndView listCustFlowInterTime(Date date, String startTime, String endTime, String intervalTime, String venueId, String regionId, String ticketTypeId, String venueName, String regionName, String ticketTypeName) throws BaseException {
		ModelAndView mv = new ModelAndView("pages/rpt/rptteam/customer/custTimeStat");
		// 默认查询当天客流
		try {
			if (date == null) {
				date = DateUtil.formatStringToDate(DateUtil.getNowDate(), "yyyy-MM-dd");
			}
			// 时间间隔默认60分钟
			if (StringUtil.isNull(intervalTime)) {
				intervalTime = "60";
			}
			// 默认查询时间段 最大值和最小值
			if (StringUtil.isNull(startTime) && StringUtil.isNull(endTime)) {
				List<Map<String, Object>> itemsStart = rptTeamService.listStartTime(intervalTime);
				List<Map<String, Object>> itemsEnd = rptTeamService.listEndTime(intervalTime);

				startTime = StringUtil.convertString(itemsStart.get(0).get("id"));
				endTime = StringUtil.convertString(itemsEnd.get(itemsEnd.size() - 1).get("id"));
			}
			List<CustFlowDateTimeBean> list = rptTeamService.listCustFlowInterTime(getLoginUserBean(), date, startTime, endTime, intervalTime, venueId, regionId, ticketTypeId);
			mv.addObject("list", list);
			mv.addObject("statdate", date);
			mv.addObject("startTime", startTime);
			mv.addObject("endTime", endTime);
			if (StringUtil.isNotNull(intervalTime)) {
				mv.addObject("intervalTime", intervalTime);
			} else {
				mv.addObject("intervalTime", "15");
			}

			mv.addObject("venueName", venueName);
			mv.addObject("regionName", regionName);
			mv.addObject("ticketTypeName", ticketTypeName);
			mv.addObject("venueId", venueId);
			mv.addObject("regionId", regionId);
			mv.addObject("ticketTypeId", ticketTypeId);
		} catch (Exception e) {
			throw new BaseException(MSG.DB_ERROR, e);
		}
		return mv;
	}

	/**
	 * 
	 * Title: 客流量分时统计表_导出EXCEL<br/>
	 * Description:
	 * 
	 * @param date
	 * @param startTime
	 * @param endTime
	 * @param intervalTime
	 * @param venueId
	 * @param regionId
	 * @param ticketTypeId
	 * @throws BaseException
	 */
	@RequestMapping(value = "expExcelInterTime")
	@ControlAspect(funtionCd = "客流分时段统计表EXCEL导出", operType = OperType.QUERY)
	@ControllerException(type = "page")
	public void expExcelCustFlowInterTime(Date date, String startTime, String endTime, String intervalTime, String venueId, String regionId, String ticketTypeId) throws BaseException {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			List<CustFlowDateTimeBean> list = rptTeamService.listCustFlowInterTime(getLoginUserBean(), date, startTime, endTime, intervalTime, venueId, regionId, ticketTypeId);
			map.put("list", list);
			map.put("statdate", DateUtil.formatDateToString(date, "yyyy-MM-dd"));
			exportDetailExcel(map, "custDateTimeStatistic.xls", "客流时间段统计" + DateUtil.formatDateToString(date, "yyyyMMdd") + ".xls");
		} catch (Exception e) {
			throw new BaseException(MSG.EXCEL_ERROR, e);
		}
	}

	/**
	 * 
	 * Title: 查询指定间隔对应的开始时间<br/>
	 * Description:
	 * 
	 * @param splitTime
	 * @return
	 * @throws BaseException
	 */
	@RequestMapping(value = "listStartTime")
	@ControlAspect(funtionCd = "查询指定间隔对应的开始时间", operType = OperType.QUERY)
	@ControllerException
	public List<Map<String, Object>> listStartTime(String splitTime) throws BaseException {
		List<Map<String, Object>> items = rptTeamService.listStartTime(splitTime);
		return items;
	}

	/**
	 * 
	 * Title: 查询指定间隔对应的结束时间<br/>
	 * Description:
	 * 
	 * @param splitTime
	 * @return
	 * @throws BaseException
	 */
	@RequestMapping(value = "listEndTime")
	@ControlAspect(funtionCd = "查询指定间隔对应的结束时间", operType = OperType.QUERY)
	@ControllerException
	public List<Map<String, Object>> listEndTime(String splitTime) throws BaseException {
		List<Map<String, Object>> items = rptTeamService.listEndTime(splitTime);
		return items;
	}

	/**
	 * 团队统计报表-单票种 Title: <br/>
	 * Description:
	 * 
	 * @param tDBean
	 * @return
	 * @throws BaseException
	 */
	@RequestMapping(value = "listRptTeamTdX")
	@ControlAspect(funtionCd = "团队统计报表（单票种）", operType = OperType.QUERY)
	@ControllerException
	public PageBean<TicketTeamOrderBean> listRptTeamTdX(TicketTeamOrderBean tDBean) throws BaseException {
		UserBean userBean = getLoginUserBean();
		userBean.setPageSize(0);
		PageBean<TicketTeamOrderBean> tDList = rptTeamService.listRptTeamTdX(userBean, tDBean);
		return tDList;
	}

	/**
	 * 
	 * Title: 团队换票统计表-单票种_导出EXCEL<br/>
	 * Description:
	 * 
	 * @param tDBean
	 * @throws BaseException
	 */
	@RequestMapping(value = "expExcelRptTeamTdX")
	@ControlAspect(funtionCd = "团队换票统计表（单票种）EXCEL导出", operType = OperType.QUERY)
	@ControllerException(type = "page")
	public void expExcelRptTeamTdX(TicketTeamOrderBean tDBean) throws BaseException {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			UserBean userBean = getLoginUserBean();
			userBean.setPageSize(0);
			PageBean<TicketTeamOrderBean> tDList = rptTeamService.listRptTeamTdX(userBean, tDBean);
			map.put("list", tDList.getRows());
			Date dat = new Date();
			if (tDBean.getChangeTime() != null) {
				dat = tDBean.getChangeTime();
			}
			exportDetailExcel(map, "tdChangeTicketTwo.xls", "团队换票统计_单票种" + DateUtil.formatDateToString(dat, "yyyyMMdd") + ".xls");
		} catch (Exception e) {
			throw new BaseException(MSG.EXCEL_ERROR, e);
		}
	}

	/**
	 * 
	 * Title: 客流量日统计_按区域、票种统计表<br/>
	 * Description:
	 * 
	 * @param startDate
	 * @param endDate
	 * @param regionId
	 * @return
	 * @throws BaseException
	 */
	@RequestMapping(value = "listCustFlowDayN")
	@ControlAspect(funtionCd = "客流日统计_按区域、票种", operType = OperType.QUERY)
	@ControllerException(type = "page")
	public ModelAndView listCustFlowDayN(Date startDate, Date endDate, String regionId, String regionName) throws BaseException {
		ModelAndView mv = new ModelAndView("pages/rpt/rptteam/customer/custDayStatN");
		try {
			if (startDate == null && endDate == null) {
				startDate = DateUtil.formatStringToDate(DateUtil.getNowDate(), "yyyy-MM-dd");
				endDate = startDate;
			}
			List<CustFlowBean> custList = rptTeamService.listCustFlowDayN(getLoginUserBean(), startDate, endDate, regionId);
			mv.addObject("custList", custList);
			if (!custList.isEmpty()) {
				mv.addObject("ticTypeColName", custList.get(0).getTicTypeColName());
			}
			mv.addObject("startDate", startDate);
			mv.addObject("endDate", endDate);
			mv.addObject("regionName", regionName);
			mv.addObject("regionId", regionId);
		} catch (Exception e) {
			throw new BaseException(MSG.DB_ERROR, e);
		}
		return mv;
	}

	/**
	 * 
	 * Title: 客流量统计表_按区域、票种EXCEL导出<br/>
	 * Description:
	 * 
	 * @param startDate
	 * @param endDate
	 * @param regionId
	 * @param ticketTypeId
	 * @throws BaseException
	 */
	@RequestMapping(value = "expExcelCustFlowDayN")
	@ControlAspect(funtionCd = "客流量统计表_按区域、票种EXCEL导出", operType = OperType.QUERY)
	@ControllerException(type = "page")
	public void expExcelCustFlowDayN(Date startDate, Date endDate, String regionId) throws BaseException {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			List<CustFlowBean> custList = rptTeamService.listCustFlowDayN(getLoginUserBean(), startDate, endDate, regionId);
			map.put("custList", custList);
			if (!custList.isEmpty()) {
				map.put("ticTypeColName", custList.get(0).getTicTypeColName());
			}
			map.put("startDate", DateUtil.formatDateToString(startDate, "yyyy-MM-dd"));
			map.put("endDate", DateUtil.formatDateToString(endDate, "yyyy-MM-dd"));
			exportDetailExcel(map, "custStatisticO.xls", "客流量统计（区域票种）" + DateUtil.formatDateToString(startDate, "yyyyMMdd") + "-" + DateUtil.formatDateToString(endDate, "yyyyMMdd") + ".xls");
		} catch (Exception e) {
			throw new BaseException(MSG.EXCEL_ERROR, e);
		}
	}

	/**
	 * 
	 * Title: 客流量统计_按区域、时间段<br/>
	 * Description:
	 * 
	 * @param date 默认sysdate
	 * @param startTime 默认
	 * @param endTime 默认
	 * @param intervalTime 默认60
	 * @return
	 * @throws BaseException
	 */
	@RequestMapping(value = "listCustFlowInterTimeN")
	@ControlAspect(funtionCd = "客流量统计_按区域、时间段", operType = OperType.QUERY)
	@ControllerException(type = "page")
	public ModelAndView listCustFlowInterTimeN(Date date, String startTime, String endTime, String intervalTime) throws BaseException {
		ModelAndView mv = new ModelAndView("pages/rpt/rptteam/customer/custTimeStatN");
		try {
			if (date == null) {
				date = DateUtil.formatStringToDate(DateUtil.getNowDate(), "yyyy-MM-dd");
			}
			// 时间间隔默认60分钟
			if (StringUtil.isNull(intervalTime)) {
				intervalTime = "60";
			}
			// 默认查询时间段 最大值和最小值
			if (StringUtil.isNull(startTime) && StringUtil.isNull(endTime)) {
				List<Map<String, Object>> itemsStart = rptTeamService.listStartTime(intervalTime);
				List<Map<String, Object>> itemsEnd = rptTeamService.listEndTime(intervalTime);

				startTime = StringUtil.convertString(itemsStart.get(0).get("id"));
				endTime = StringUtil.convertString(itemsEnd.get(itemsEnd.size() - 1).get("id"));
			}
			List<CustFlowTimeBean> list = rptTeamService.listCustFlowInterTimeN(getLoginUserBean(), date, startTime, endTime, intervalTime);
			if (!list.isEmpty()) {
				mv.addObject("splitName", list.get(0).getSplitName());
			}
			mv.addObject("list", list);
			mv.addObject("date", date);
			mv.addObject("startTime", startTime);
			mv.addObject("endTime", endTime);
			mv.addObject("intervalTime", intervalTime);
		} catch (Exception e) {
			throw new BaseException(MSG.DB_ERROR, e);
		}
		return mv;
	}

	/**
	 * 
	 * Title: 客流量统计表_按区域、票种EXCEL导出<br/>
	 * Description:
	 * 
	 * @param date
	 * @param startTime
	 * @param endTime
	 * @param intervalTime
	 * @throws BaseException
	 */
	@RequestMapping(value = "expExcelInterTimeN")
	@ControlAspect(funtionCd = "客流量统计表_按区域、时间段EXCEL导出", operType = OperType.QUERY)
	@ControllerException(type = "page")
	public void expExcelInterTimeN(Date date, String startTime, String endTime, String intervalTime) throws BaseException {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			List<CustFlowTimeBean> list = rptTeamService.listCustFlowInterTimeN(getLoginUserBean(), date, startTime, endTime, intervalTime);
			map.put("custList", list);
			if (!list.isEmpty()) {
				map.put("splitName", list.get(0).getSplitName());
			}
			map.put("date", DateUtil.formatDateToString(date, "yyyy-MM-dd"));
			exportDetailExcel(map, "custStatisticT.xls", "客流量统计（区域时间段）" + DateUtil.formatDateToString(date, "yyyyMMdd") + ".xls");
		} catch (Exception e) {
			throw new BaseException(MSG.EXCEL_ERROR, e);
		}
	}

	/**
	 * 
	 * Title: 网点退票统计-查询<br/>
	 * Description:
	 * 
	 * @param refundTime
	 * @return
	 * @throws BaseException
	 */
	@RequestMapping(value = "outletRefundTicket")
	@ControlAspect(funtionCd = "i2_rpt_refund_ticket", operType = OperType.QUERY,havPrivs=true)
	@ControllerException
	public PageBean<RefundTicketBean> outletRefundTicket(Date refundTimeBegin, Date refundTimeEnd) throws BaseException {
		PageBean<RefundTicketBean> refundPage = rptTeamService.outletRefundTicket(getLoginUserBean(), refundTimeBegin, refundTimeEnd);
		return refundPage;
	}

	/**
	 * 
	 * Title: 退票统计-导出excel<br/>
	 * Description:
	 * 
	 * @param refundTime
	 * @throws BaseException
	 */
	@RequestMapping(value = "expOutletRefundTicket")
	@ControlAspect(funtionCd = "网点退票报表EXCEL导出", operType = OperType.QUERY)
	@ControllerException(type = "page")
	public void expOutletRefundTicket(Date refundTimeBegin, Date refundTimeEnd) throws BaseException {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			UserBean userBean = getLoginUserBean();
			userBean.setPageSize(0);
			PageBean<RefundTicketBean> refundPage = rptTeamService.outletRefundTicket(userBean, refundTimeBegin, refundTimeEnd);
			map.put("list", refundPage.getRows());
			Date dat = new Date();
			map.put("refundTime", DateUtil.formatDateToString(dat, "yyyy-MM-dd"));
			exportDetailExcel(map, "outletRefTicket.xls", "退票统计" + DateUtil.formatDateToString(dat, "yyyyMMdd") + ".xls");
		} catch (Exception e) {
			throw new BaseException(MSG.EXCEL_ERROR, e);
		}
	}

}
