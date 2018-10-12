package com.tbims.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.tbims.bean.CustFlowDayBean;
import com.tbims.bean.RptCheckBean;
import com.tbims.bean.RptDeliveryBean;
import com.tbims.bean.RptOutletSale1Bean;
import com.tbims.bean.RptStrinfoBean;
import com.tbims.bean.TicketCheckByIdenttyBean;
import com.tbims.bean.TicketNoCheckBean;
import com.tbims.bean.TicketSaleByIdenttyBean;
import com.tbims.service.IRptSaleService;
import com.zhming.support.BaseController;
import com.zhming.support.annontion.ControlAspect;
import com.zhming.support.annontion.ControllerException;
import com.zhming.support.bean.PageBean;
import com.zhming.support.bean.UserBean;
import com.zhming.support.common.MSG;
import com.zhming.support.common.OperType;
import com.zhming.support.exception.BaseException;
import com.zhming.support.exception.ServiceException;
import com.zhming.support.util.CommonUtil;
import com.zhming.support.util.DateUtil;
import com.zhming.support.util.StringUtil;

/**
 * 
 * Title: 出库配送查询、票务中心当日库存查询、网点当日库存查询、网点销售统计表 <br/>
 * Description:
 * 
 * @ClassName: RptSaleController
 * @author ly
 * @date 2017年7月11日 上午9:19:02
 *
 */
@RestController
@RequestMapping("/rptsale/")
public class RptSaleController extends BaseController {
	@Autowired
	private IRptSaleService rptSaleService;

	/**
	 * 
	 * Title: 网点销售统计表<br/>
	 * Description:
	 * 
	 * @param rptDt
	 * @return
	 * @throws BaseException
	 */
	@RequestMapping(value = "outletSaleRpt")
	@ControlAspect(funtionCd = "网点销售统计表", operType = OperType.QUERY)
	@ControllerException(type = "page")
	public ModelAndView outletSaleRpt(Date rptDt) throws BaseException {
		ModelAndView mv = new ModelAndView("pages/rpt/rptsale/outletSaleRpt");
		try {
			if (rptDt == null) {
				rptDt = DateUtil.formatStringToDate(DateUtil.getNowDate(), "yyyy-MM-dd");
			}
			List<RptOutletSale1Bean> outletSaleList = rptSaleService.getRptOutletSaleBeanListPage(rptDt, "dd", false);
			mv.addObject("outletSaleList", outletSaleList);
			mv.addObject("rptDt", rptDt);
		} catch (Exception e) {
			throw new BaseException(MSG.DB_ERROR, e);
		}
		return mv;
	}

	/**
	 * 
	 * Title: 网点销售统计表EXCEL导出<br/>
	 * Description:
	 * 
	 * @param rptDt
	 * @throws BaseException
	 */
	@RequestMapping(value = "expExceloutletSale")
	@ControlAspect(funtionCd = "网点销售统计表EXCEL导出", operType = OperType.QUERY)
	@ControllerException(type = "page")
	public void expExceloutletSale(Date rptDt) throws BaseException {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			List<RptOutletSale1Bean> outletSaleList = rptSaleService.getRptOutletSaleBeanListPage(rptDt, "dd", true);
			map.put("outletSaleList", outletSaleList);
			map.put("listSize", outletSaleList.size());
			map.put("rptDt", DateUtil.formatDateToString(rptDt, "yyyy-MM-dd"));
			exportDetailExcel(map, "outletSale.xls", "网点销售日统计表" + DateUtil.formatDateToString(rptDt, "yyyyMMdd") + ".xls");
		} catch (Exception e) {
			throw new BaseException(MSG.EXCEL_ERROR, e);
		}
	}

	/**
	 * 
	 * Title: 网点销售统计月表<br/>
	 * Description:
	 * 
	 * @param rptDt
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "outletSaleMRpt")
	@ControlAspect(funtionCd = "网点销售统计月表", operType = OperType.QUERY)
	@ControllerException(type = "page")
	public ModelAndView outletSaleMRpt(String rptDt) throws BaseException {

		ModelAndView mv = new ModelAndView("pages/rpt/rptsale/outletSaleMRpt");
		Date mydt;
		String mydate = "";
		try {
			if (StringUtil.isNotNull(rptDt)) {
				mydate = rptDt + "-01";
			} else {
				mydate = DateUtil.getNowDate().substring(0, 7) + "-01";
			}
			mydt = DateUtil.formatStringToDate(mydate, "yyyy-MM-dd");
			List<RptOutletSale1Bean> outletSaleList = rptSaleService.getRptOutletSaleBeanListPage(mydt, "MM", false);
			mv.addObject("outletSaleList", outletSaleList);
			mv.addObject("rptDt", mydt);
		} catch (Exception e) {
			throw new BaseException(MSG.DB_ERROR, e);
		}
		return mv;
	}

	/**
	 * 
	 * Title:网点销售统计月表EXCEL导出 <br/>
	 * Description:
	 * 
	 * @param rptDt
	 * @throws BaseException
	 */
	@RequestMapping(value = "expExceloutletMSale")
	@ControlAspect(funtionCd = "网点销售统计月表EXCEL导出", operType = OperType.QUERY)
	@ControllerException(type = "page")
	public void expExceloutletMSale(String rptDt) throws BaseException {
		try {
			String mydate = rptDt + "-01";
			Date mydt = DateUtil.formatStringToDate(mydate, "yy-MM-dd");
			Map<String, Object> map = new HashMap<String, Object>();
			List<RptOutletSale1Bean> outletSaleList = rptSaleService.getRptOutletSaleBeanListPage(mydt, "MM", true);
			map.put("outletSaleList", outletSaleList);
			map.put("rptDt", rptDt);
			map.put("listSize", outletSaleList.size());
			exportDetailExcel(map, "outletSaleM.xls", "网点销售月统计表" + rptDt + ".xls");
		} catch (Exception e) {
			throw new BaseException(MSG.EXCEL_ERROR, e);
		}
	}

	/**
	 * 
	 * Title: 出库配送查询<br/>
	 * Description:
	 * 
	 * @param beginDt
	 * @param endDt
	 * @param outletId
	 * @param ticketTypeId
	 * @return
	 */
	@RequestMapping(value = "deliveryRpt")
	@ControlAspect(funtionCd = "i2_rpt_delivery_query", operType = OperType.QUERY, havPrivs=true)
	@ControllerException
	public PageBean<RptDeliveryBean> deliveryRpt(Date beginDt, Date endDt, Long outletId, String ticketTypeId) {
		PageBean<RptDeliveryBean> rptDeliveryBeanList = rptSaleService.getRptDeliveryBeanListPage(getLoginUserBean(), beginDt, endDt, outletId, ticketTypeId);
		return rptDeliveryBeanList;
	}

	/**
	 * 
	 * Title: 出库配送查询EXCEL导出<br/>
	 * Description:
	 * 
	 * @param beginDt
	 * @param endDt
	 * @param outletId
	 * @param ticketTypeId
	 * @throws BaseException
	 */
	@RequestMapping(value = "expExcelDelivery")
	@ControlAspect(funtionCd = "出库配送查询EXCEL导出", operType = OperType.QUERY)
	@ControllerException(type = "page")
	public void expExcelDelivery(Date beginDt, Date endDt, Long outletId, String ticketTypeId) throws BaseException {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			UserBean userBean = getLoginUserBean();// 不分页
			userBean.setPageSize(0);
			PageBean<RptDeliveryBean> rptDeliveryBeanList = rptSaleService.getRptDeliveryBeanListPage(userBean, beginDt, endDt, outletId, ticketTypeId);
			Date dat = new Date();
			Date dat2 = new Date();
			if (beginDt != null && endDt != null) {
				dat = beginDt;
				dat2 = endDt;
			}
			map.put("deliveryList", rptDeliveryBeanList.getRows());
			exportDetailExcel(map, "deliveryRpt.xls", "出库配送查询" + DateUtil.formatDateToString(dat, "yyyyMMdd") + "-" + DateUtil.formatDateToString(dat2, "yyyyMMdd") + ".xls");
		} catch (Exception e) {
			throw new BaseException(MSG.EXCEL_ERROR, e);
		}
	}

	/**
	 * 
	 * Title: 票务中心当日库存查询<br/>
	 * Description:
	 * 
	 * @param ticketTypeId
	 * @return
	 */
	@RequestMapping(value = "centerStoreList")
	@ControlAspect(funtionCd = "票务中心当日库存查询", operType = OperType.QUERY)
	@ControllerException
	public PageBean<RptStrinfoBean> centerStoreList(String ticketTypeId) {
		PageBean<RptStrinfoBean> centerStoreList = rptSaleService.getCenterStorePage(getLoginUserBean(), ticketTypeId);
		return centerStoreList;
	}

	/**
	 * 
	 * Title: 票务中心当日库存查询EXCEL导出<br/>
	 * Description:
	 * 
	 * @param ticketTypeId
	 * @throws BaseException
	 */
	@RequestMapping(value = "expExcelCenterStore")
	@ControlAspect(funtionCd = "票务中心当日库存查询EXCEL导出", operType = OperType.QUERY)
	@ControllerException(type = "page")
	public void expExcelCenterStore(String ticketTypeId) throws BaseException {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			UserBean userBean = getLoginUserBean();// 不分页
			userBean.setPageSize(0);
			PageBean<RptStrinfoBean> centerStorePage = rptSaleService.getCenterStorePage(userBean, ticketTypeId);
			map.put("centerStoreList", centerStorePage.getRows());
			exportDetailExcel(map, "centerStore.xls", "中心库实时库存" + DateUtil.getNowDate("yyyyMMdd") + ".xls");
		} catch (Exception e) {
			throw new BaseException(MSG.EXCEL_ERROR, e);
		}
	}

	/**
	 * 
	 * Title: 网点当日库存查询<br/>
	 * Description:
	 * 
	 * @param ticketTypeId
	 * @param outletId
	 * @return
	 */
	@RequestMapping(value = "outletStoreList")
	@ControlAspect(funtionCd = "网点当日库存查询", operType = OperType.QUERY)
	@ControllerException
	public PageBean<RptStrinfoBean> outletStoreList(String ticketTypeId, Long outletId) {
		PageBean<RptStrinfoBean> rptDeliveryBeanList = rptSaleService.getOutletStorePage(getLoginUserBean(), ticketTypeId, outletId);
		return rptDeliveryBeanList;
	}

	/**
	 * 
	 * Title: 网点当日库存查询EXCEL导出<br/>
	 * Description:
	 * 
	 * @param ticketTypeId
	 * @param outletId
	 * @throws BaseException
	 */
	@RequestMapping(value = "expExlOutletStore")
	@ControlAspect(funtionCd = "网点当日库存查询EXCEL导出", operType = OperType.QUERY)
	@ControllerException(type = "page")
	public void expExlOutletStore(String ticketTypeId, Long outletId) throws BaseException {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			UserBean userBean = getLoginUserBean();// 不分页
			userBean.setPageSize(0);
			PageBean<RptStrinfoBean> rptDeliveryBeanList = rptSaleService.getOutletStorePage(userBean, ticketTypeId, outletId);
			List<RptStrinfoBean> outletStoreList = rptDeliveryBeanList.getRows();
			map.put("outletStoreList", outletStoreList);
			exportDetailExcel(map, "outletStore.xls", "网点实时库存" + DateUtil.getNowDate("yyyyMMdd") + ".xls");
		} catch (Exception e) {
			throw new BaseException(MSG.EXCEL_ERROR, e);
		}
	}

	/**
	 * 
	 * Title: 首页日销售表<br/>
	 * Description:
	 * 
	 * @param sbeginTime
	 * @param sendTime
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping(value = "saleDayFirst")
	@ControlAspect(funtionCd = "首页日销售表", operType = OperType.QUERY)
	@ControllerException
	public Map<String, Object> saleDayFirst(String sbeginTime, String sendTime) throws ServiceException {
		Map<String, Object> retmap = rptSaleService.saleDay(sbeginTime, sendTime);
		return retmap;
	}

	/**
	 * 
	 * Title: 首页日检票表<br/>
	 * Description:
	 * 
	 * @param cbeginTime
	 * @param cendTime
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping(value = "checkDayFirst")
	@ControlAspect(funtionCd = "首页日检票表", operType = OperType.QUERY)
	@ControllerException
	public Map<String, Object> checkDayFirst(String cbeginTime, String cendTime) throws ServiceException {
		Map<String, Object> retmap = rptSaleService.checkDay(cbeginTime, cendTime);
		return retmap;
	}

	/**
	 * 
	 * Title: 首页月销售表<br/>
	 * Description:
	 * 
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping(value = "saleMonthFirst")
	@ControlAspect(funtionCd = "首页月销售表", operType = OperType.QUERY)
	@ControllerException
	public Map<String, Object> saleMonthFirst() throws ServiceException {
		Map<String, Object> retmap = rptSaleService.saleMonth();
		return retmap;
	}

	/**
	 * 
	 * Title: 首页月检票表<br/>
	 * Description:
	 * 
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping(value = "checkMonthFirst")
	@ControlAspect(funtionCd = "首页月检票表", operType = OperType.QUERY)
	@ControllerException
	public Map<String, Object> checkMonthFirst() throws ServiceException {
		Map<String, Object> retmap = rptSaleService.checkMonth();
		return retmap;
	}

	/**
	 * 
	 * Title: 门票售检信息查询<br/>
	 * Description:
	 * 
	 * @param ticketId
	 * @return
	 * @throws BaseException
	 */
	@RequestMapping(value = "saleCheckRpt")
	@ControlAspect(funtionCd = "门票售检信息查询", operType = OperType.QUERY)
	@ControllerException(type = "page")
	public ModelAndView saleCheckRpt(Long ticketId) throws BaseException {
		ModelAndView mv = new ModelAndView("pages/rpt/rptsale/ticketsaleCheckInfo");
		try {
			Map<String, Object> ticketBaseInfoBean = rptSaleService.ticketBaseInfo(ticketId);
			Map<String, Object> ticketSaleInfoBean = rptSaleService.ticketSaleInfo(ticketId);
			Map<String, Object> delBean = rptSaleService.ticketDelInfo(ticketId);
			List<RptCheckBean> checkList = rptSaleService.checkInfo(ticketId);
			mv.addObject("checkList", checkList);
			mv.addObject("ticketBaseInfoBean", ticketBaseInfoBean);
			mv.addObject("ticketSaleInfoBean", ticketSaleInfoBean);
			mv.addObject("delBean", delBean);
			mv.addObject("ticketId", ticketId);
		} catch (Exception e) {
			throw new BaseException(MSG.DB_ERROR, e);
		}
		return mv;
	}

	/**
	 * 
	 * Title: 门票售检信息查询EXCEL导出<br/>
	 * Description:
	 * 
	 * @param ticketTypeId
	 * @param outletId
	 * @throws BaseException
	 */
	@RequestMapping(value = "expExlSaleCheck")
	@ControlAspect(funtionCd = "门票售检信息查询EXCEL导出", operType = OperType.QUERY)
	@ControllerException(type = "page")
	public void expExlSaleCheck(Long ticketId) throws BaseException {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			Map<String, Object> ticketBaseInfoBean = rptSaleService.ticketBaseInfo(ticketId);
			Map<String, Object> ticketSaleInfoBean = rptSaleService.ticketSaleInfo(ticketId);
			Map<String, Object> delBean = rptSaleService.ticketDelInfo(ticketId);
			List<RptCheckBean> checkList = rptSaleService.checkInfo(ticketId);
			map.put("ticketBaseInfoBean", ticketBaseInfoBean);
			map.put("ticketSaleInfoBean", ticketSaleInfoBean);
			map.put("delBean", delBean);
			map.put("checkList", checkList);
			exportDetailExcel(map, "saleCheck.xls", "门票售检信息查询" + DateUtil.getNowDate("yyyyMMdd") + ".xls");
		} catch (Exception e) {
			throw new BaseException(MSG.EXCEL_ERROR, e);
		}
	}

	/**
	 * Title:身份证售检信息查询-验票 <br/>
	 * Description:
	 * 
	 * @param identty_id
	 * @return
	 */
	@RequestMapping(value = "listTicketInfoByIdentty")
	@ControlAspect(funtionCd = "i2_rpt_salecheck_identty", operType = OperType.QUERY, havPrivs = true)
	@ControllerException(type = "page")
	public ModelAndView listTicketInfoByIdentty(String identtyId) {
		ModelAndView mv = new ModelAndView("pages/rpt/rptsale/ticketSaleByIdentty");
		List<TicketCheckByIdenttyBean> checkIdenttyList = rptSaleService.listTicketCheckByIdentty(identtyId);
		List<TicketSaleByIdenttyBean> saleIdenttyList = rptSaleService.listTicketSaleByIdentty(identtyId);
		mv.addObject("checkIdenttyList", checkIdenttyList);
		mv.addObject("saleIdenttyList", saleIdenttyList);
		mv.addObject("identtyId", identtyId);
		return mv;
	}

	/**
	 * Title:身份证售检信息查询-验票-导出excel <br/>
	 * Description:
	 * 
	 * @param identty_id
	 * @return
	 * @throws BaseException
	 */
	@RequestMapping(value = "expExlListTicketInfoByIdentty")
	@ControlAspect(funtionCd = "i2_rpt_salecheck_identty", operType = OperType.QUERY, havPrivs = true)
	@ControllerException(type = "page")
	public void expExlListTicketInfoByIdentty(String identtyId) throws BaseException {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			List<TicketCheckByIdenttyBean> checkIdenttyList = rptSaleService.listTicketCheckByIdentty(identtyId);
			List<TicketSaleByIdenttyBean> saleIdenttyList = rptSaleService.listTicketSaleByIdentty(identtyId);
			map.put("checkIdenttyList", checkIdenttyList);
			map.put("saleIdenttyList", saleIdenttyList);
			exportDetailExcel(map, "saleCheckIdentty.xls", String.format("%s_%s.xls", "身份证验票查询", DateUtil.getNowDate("yyyyMMdd")));
		} catch (Exception e) {
			throw new BaseException(MSG.EXCEL_ERROR, e);
		}
	}

	/**
	 * 
	 * Title: 门票售票明细信息查询<br/>
	 * Description:
	 * 
	 * @param ticketId
	 * @return
	 * @throws BaseException
	 */
	@RequestMapping(value = "saleDetail")
	@ControlAspect(funtionCd = "门票售票明细信息查询", operType = OperType.QUERY)
	@ControllerException
	public PageBean<Map<String, Object>> saleDetail(Date rptDt, String beginTime, String endTime, String ticketTypeId, Long outletId, String ejectUserId) throws BaseException {
		try {
			PageBean<Map<String, Object>> saleDetailList = rptSaleService.saleDetail(getLoginUserBean(), rptDt, beginTime, endTime, ticketTypeId, outletId, ejectUserId);
			return saleDetailList;
		} catch (Exception e) {
			throw new BaseException(MSG.DB_ERROR, e);
		}

	}

	/**
	 * 
	 * Title: 门票售票明细信息查询EXCEL导出<br/>
	 * Description:
	 * 
	 * @param ticketTypeId
	 * @param outletId
	 * @throws BaseException
	 */
	@RequestMapping(value = "expExlSaleDetail")
	@ControlAspect(funtionCd = "门票售票明细信息查询EXCEL导出", operType = OperType.QUERY)
	@ControllerException(type = "page")
	public void expExlSaleDetail(Date rptDt, String beginTime, String endTime, String ticketTypeId, Long outletId, String ejectUserId) throws BaseException {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			UserBean userBean = getLoginUserBean();// 不分页
			userBean.setPageSize(0);
			List<Map<String, Object>> saleDetailList = rptSaleService.saleDetail(userBean, rptDt, beginTime, endTime, ticketTypeId, outletId, ejectUserId).getRows();
			map.put("saleDetailList", saleDetailList);
			Date date = new Date();
			if (rptDt != null) {
				date = rptDt;
			}
			exportDetailExcel(map, "saleDetail.xls", "销售明细查询" + DateUtil.formatDateToString(date, "yyyyMMdd") + ".xls");
		} catch (Exception e) {
			throw new BaseException(MSG.EXCEL_ERROR, e);
		}
	}

	/**
	 * 
	 * Title: 门票检票明细信息查询<br/>
	 * Description:
	 * 
	 * @param ticketId
	 * @return
	 * @throws BaseException
	 */
	@RequestMapping(value = "checkDetail")
	@ControlAspect(funtionCd = "门票检票明细信息查询", operType = OperType.QUERY)
	@ControllerException
	public PageBean<Map<String, Object>> checkDetail(Date rptDt, String beginTime, String endTime, String ticketTypeId, Long venueId, Long regionId, Long clientId) throws BaseException {
		try {
			PageBean<Map<String, Object>> checkDetailList = rptSaleService.checkDetail(getLoginUserBean(), rptDt, beginTime, endTime, ticketTypeId, venueId, regionId, clientId);
			return checkDetailList;
		} catch (Exception e) {
			throw new BaseException(MSG.DB_ERROR, e);
		}
	}

	/**
	 * 
	 * Title: 门票检票明细信息查询EXCEL导出<br/>
	 * Description:
	 * 
	 * @param ticketTypeId
	 * @param outletId
	 * @throws BaseException
	 */
	@RequestMapping(value = "expExlCheckDetail")
	@ControlAspect(funtionCd = "门票检票明细信息查询EXCEL导出", operType = OperType.QUERY)
	@ControllerException(type = "page")
	public void expExlCheckDetail(Date rptDt, String beginTime, String endTime, String ticketTypeId, Long venueId, Long regionId, Long clientId) throws BaseException {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			UserBean userBean = getLoginUserBean();// 不分页
			userBean.setPageSize(0);
			PageBean<Map<String, Object>> checkDetailPageBean = rptSaleService.checkDetail(userBean, rptDt, beginTime, endTime, ticketTypeId, venueId, regionId, clientId);
			List<Map<String, Object>> checkDetailList = checkDetailPageBean.getRows();
			map.put("checkDetailList", checkDetailList);
			Date date = new Date();
			if (rptDt != null) {
				date = rptDt;
			}
			exportDetailExcel(map, "checkDetail.xls", "检票明细查询" + DateUtil.formatDateToString(date, "yyyyMMdd") + ".xls");
		} catch (Exception e) {
			throw new BaseException(MSG.EXCEL_ERROR, e);
		}
	}

	/**
	 * 
	 * Title:员工入园日统计<br/>
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
	@RequestMapping(value = "listEmpInDay")
	@ControlAspect(funtionCd = "员工入园日统计", operType = OperType.QUERY)
	@ControllerException(type = "page")
	public ModelAndView listEmpInDay(Date startDate, Date endDate, String venueId, String regionId, String empId, String venueName, String regionName, String empName) throws BaseException {
		ModelAndView mv = new ModelAndView("pages/rpt/rptsale/empDayStat");
		try {
			if (startDate == null && endDate == null) {
				startDate = DateUtil.formatStringToDate(DateUtil.getNowDate(), "yyyy-MM-dd");
				endDate = startDate;
			}
			List<CustFlowDayBean> list = rptSaleService.listEmpInDay(getLoginUserBean(), startDate, endDate, venueId, regionId, empId);
			mv.addObject("venueName", venueName);
			mv.addObject("regionName", regionName);
			mv.addObject("empName", empName);
			mv.addObject("list", list);
			mv.addObject("startDate", startDate);
			mv.addObject("endDate", endDate);
			mv.addObject("venueId", venueId);
			mv.addObject("regionId", regionId);
			mv.addObject("empId", empId);
		} catch (Exception e) {
			throw new BaseException(MSG.DB_ERROR, e);
		}
		return mv;
	}

	/**
	 * 
	 * Title:员工入园日统计_导出excel <br/>
	 * Description:
	 * 
	 * @param startDate
	 * @param endDate
	 * @param venueId
	 * @param regionId
	 * @param ticketTypeId
	 * @throws BaseException
	 */
	@RequestMapping(value = "expExcelEmpFlowDay")
	@ControlAspect(funtionCd = "员工入园日统计EXCEL导出", operType = OperType.QUERY)
	@ControllerException(type = "page")
	public void expExcelCustFlowDay(Date startDate, Date endDate, String venueId, String regionId, String empId) throws BaseException {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			List<CustFlowDayBean> list = rptSaleService.listEmpInDay(getLoginUserBean(), startDate, endDate, venueId, regionId, empId);
			map.put("list", list);
			exportDetailExcel(map, "empDayStatistic.xls", "员工入园日统计" + DateUtil.formatDateToString(startDate, "yyyyMMdd") + "-" + DateUtil.formatDateToString(endDate, "yyyyMMdd") + ".xls");
		} catch (Exception e) {
			throw new BaseException(MSG.EXCEL_ERROR, e);
		}
	}

	/**
	 * 
	 * Title: 售换票分时统计报表<br/>
	 * Description:
	 * 
	 * @param startDate
	 * @param endDate
	 * @return
	 * @throws BaseException
	 */
	@RequestMapping(value = "listSaleChange")
	@ControlAspect(funtionCd = "售换票分时统计报表", operType = OperType.QUERY)
	@ControllerException(type = "page")
	public ModelAndView listSaleChange(Date startDate, Date endDate) throws BaseException {
		ModelAndView mv = new ModelAndView("pages/rpt/rptsale/saleChange");
		try {
			if (startDate == null && endDate == null) {
				startDate = DateUtil.formatStringToDate(DateUtil.getNowDate(), "yyyy-MM-dd");
				endDate = startDate;
			}
			mv.addObject("startDate", startDate);
			mv.addObject("endDate", endDate);
			List<Map<String, Object>> saleChangeList = rptSaleService.listSaleChange(startDate, endDate);
			mv.addObject("saleChangeList", saleChangeList);
		} catch (Exception e) {
			throw new BaseException(MSG.DB_ERROR, e);
		}
		return mv;
	}

	/**
	 * 
	 * Title:售换票分时统计报表_导出excel <br/>
	 * Description:
	 * 
	 * @param startDate
	 * @param endDate
	 * @param venueId
	 * @param regionId
	 * @param ticketTypeId
	 * @throws BaseException
	 */
	@RequestMapping(value = "expExcelSaleChange")
	@ControlAspect(funtionCd = "售换票分时统计报表EXCEL导出", operType = OperType.QUERY)
	@ControllerException(type = "page")
	public void expExcelSaleChange(Date startDate, Date endDate) throws BaseException {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			List<Map<String, Object>> saleChangeList = rptSaleService.listSaleChange(startDate, endDate);
			map.put("saleChangeList", saleChangeList);
			exportDetailExcel(map, "saleChange.xls", "售换票分时统计报表" + DateUtil.formatDateToString(startDate, "yyyyMMdd") + "-" + DateUtil.formatDateToString(endDate, "yyyyMMdd") + ".xls");
		} catch (Exception e) {
			throw new BaseException(MSG.EXCEL_ERROR, e);
		}
	}

	/**
	 * 
	 * Title: 自营销售统计报表<br/>
	 * Description:
	 * 
	 * @param ticketId
	 * @return
	 * @throws BaseException
	 */
	@RequestMapping(value = "listSelf")
	@ControlAspect(funtionCd = "自营销售统计报表", operType = OperType.QUERY)
	@ControllerException(type = "page")
	public ModelAndView listSelf(Date startDate, Date endDate, String ticketTypeId) throws BaseException {
		ModelAndView mv = new ModelAndView("pages/rpt/rptsale/selfSale");
		try {
			if (startDate == null && endDate == null) {
				startDate = DateUtil.formatStringToDate(DateUtil.getNowDate(), "yyyy-MM-dd");
				endDate = startDate;
			}
			mv.addObject("startDate", startDate);
			mv.addObject("endDate", endDate);
			List<RptOutletSale1Bean> saleChangeList = rptSaleService.getRptSelfSaleBeanListPage(startDate, endDate, ticketTypeId);
			mv.addObject("saleChangeList", saleChangeList);
		} catch (Exception e) {
			throw new BaseException(MSG.DB_ERROR, e);
		}
		return mv;
	}

	/**
	 * 
	 * Title:自营销售统计_导出excel <br/>
	 * Description:
	 * 
	 * @param startDate
	 * @param endDate
	 * @param venueId
	 * @param regionId
	 * @param ticketTypeId
	 * @throws BaseException
	 */
	@RequestMapping(value = "expExcelSelfSale")
	@ControlAspect(funtionCd = "自营销售统计报表EXCEL导出", operType = OperType.QUERY)
	@ControllerException(type = "page")
	public void expExcelSelfSale(Date startDate, Date endDate, String ticketTypeId) throws BaseException {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			List<RptOutletSale1Bean> saleChangeList = rptSaleService.getRptSelfSaleBeanListPage(startDate, endDate, ticketTypeId);
			;
			map.put("saleChangeList", saleChangeList);
			exportDetailExcel(map, "selfSale.xls", "自营销售统计报表" + DateUtil.formatDateToString(startDate, "yyyyMMdd") + "-" + DateUtil.formatDateToString(endDate, "yyyyMMdd") + ".xls");
		} catch (Exception e) {
			throw new BaseException(MSG.EXCEL_ERROR, e);
		}
	}

	/**
	 * 
	 * Title: 门票未入园统计<br/>
	 * Description:
	 * 
	 * @return
	 * @throws BaseException
	 */
	@RequestMapping(value = "ticketNoCheck")
	@ControlAspect(funtionCd = "门票未入园统计", operType = OperType.QUERY)
	@ControllerException(type = "page")
	public ModelAndView ticketNoCheck() throws BaseException {
		ModelAndView mv = new ModelAndView("pages/rpt/rptsale/ticketNoCheck");
		try {
			List<TicketNoCheckBean> list = rptSaleService.ticketNoCheck(getLoginUserBean());
			mv.addObject("list", list);
		} catch (Exception e) {
			throw new BaseException(MSG.DB_ERROR, e);
		}
		return mv;
	}

	/**
	 * 
	 * Title: 门票未入园统计EXCEL导出<br/>
	 * Description:
	 * 
	 * @throws BaseException
	 */
	@RequestMapping(value = "expTicketNoCheck")
	@ControlAspect(funtionCd = "门票未入园统计EXCEL导出", operType = OperType.QUERY)
	@ControllerException(type = "page")
	public void expTicketNoCheck() throws BaseException {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			List<TicketNoCheckBean> list = rptSaleService.ticketNoCheck(getLoginUserBean());
			String statDate = DateUtil.formatDateToString(new Date(), "yyyy-MM-dd");
			map.put("list", list);
			map.put("statDate", statDate);
			exportDetailExcel(map, "ticketNoCheck.xls", "门票未入园统计" + DateUtil.formatDateToString(new Date(), "yyyyMMdd") + ".xls");
		} catch (Exception e) {
			throw new BaseException(MSG.EXCEL_ERROR, e);
		}
	}

	/**
	 * Title: 网络代理商销售明细查询<br/>
	 * Description:
	 * 
	 * @param rptDt
	 * @param ticketTypeId
	 * @return
	 * @throws BaseException
	 */
	@RequestMapping(value = "wlSaleDetail")
	@ControlAspect(funtionCd = "网络代理商销售明细", operType = OperType.QUERY)
	@ControllerException
	public PageBean<Map<String, Object>> wlSaleDetail(Date rptDt, String ticketTypeId, String orgId, String orderId) throws BaseException {
		try {
			PageBean<Map<String, Object>> saleDetailList = rptSaleService.wlSaleDetail(getLoginUserBean(), rptDt, ticketTypeId, orgId, orderId);
			return saleDetailList;
		} catch (Exception e) {
			throw new BaseException(MSG.DB_ERROR, e);
		}
	}

	/**
	 * 
	 * Title: 网络代理商门票售票明细信息查询EXCEL导出<br/>
	 * Description:
	 * 
	 * @param rptDt
	 * @param ticketTypeId
	 * @throws BaseException
	 */
	@RequestMapping(value = "expExlWlSaleDetail")
	@ControlAspect(funtionCd = "网络代理商门票售票明细信息查询EXCEL导出", operType = OperType.QUERY)
	@ControllerException(type = "page")
	public void expExlWlSaleDetail(Date rptDt, String ticketTypeId, String orgId, String orderId) throws BaseException {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			UserBean userBean = getLoginUserBean();// 不分页
			userBean.setPageSize(0);
			List<Map<String, Object>> saleDetailList = rptSaleService.wlSaleDetail(userBean, rptDt, ticketTypeId, orgId, orderId).getRows();
			map.put("saleDetailList", saleDetailList);
			Date date = new Date();
			if (rptDt != null) {
				date = rptDt;
			}
			exportDetailExcel(map, "wlSaleDetail.xls", "网络代理商门票售票明细" + DateUtil.formatDateToString(date, "yyyyMMdd") + ".xls");
		} catch (Exception e) {
			throw new BaseException(MSG.EXCEL_ERROR, e);
		}
	}

	/**
	 * Title: 网络代理商换票明细查询<br/>
	 * Description:
	 * 
	 * @param rptDt
	 * @param beginTime
	 * @param endTime
	 * @param ticketTypeId
	 * @return
	 * @throws BaseException
	 */
	@RequestMapping(value = "wlSaleDetailByHB")
	@ControlAspect(funtionCd = "网络代理商换票明细查询", operType = OperType.QUERY)
	@ControllerException
	public PageBean<Map<String, Object>> wlSaleDetailByHB(Date rptDt, String beginTime, String endTime, String ticketTypeId, String orgId, String outletId) throws BaseException {
		PageBean<Map<String, Object>> saleDetailList = rptSaleService.wlSaleDetailByHB(getLoginUserBean(), rptDt, beginTime, endTime, ticketTypeId, orgId, outletId);
		return saleDetailList;
	}

	@RequestMapping(value = "expExlWlSaleDetailByHB")
	@ControlAspect(funtionCd = "网络代理商换票明细查询EXCEL导出", operType = OperType.QUERY)
	@ControllerException(type = "page")
	public void expExlWlSaleDetailByHB(Date rptDt, String beginTime, String endTime, String ticketTypeId, String orgId, String outletId) throws BaseException {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			UserBean userBean = getLoginUserBean();// 不分页
			userBean.setPageSize(0);
			List<Map<String, Object>> saleDetailList = rptSaleService.wlSaleDetailByHB(userBean, rptDt, beginTime, endTime, ticketTypeId, orgId, outletId).getRows();
			map.put("saleDetailList", saleDetailList);
			Date date = new Date();
			if (rptDt != null) {
				date = rptDt;
			}
			exportDetailExcel(map, "wlSaleDetailHB.xls", "网络代理商换票明细" + DateUtil.formatDateToString(date, "yyyyMMdd") + ".xls");
		} catch (Exception e) {
			throw new BaseException(MSG.EXCEL_ERROR, e);
		}
	}

	/**
	 * Title: 入园游客渠道来源表 <br/>
	 * Description:
	 * 
	 * @param rptDt
	 * @param beginTime
	 * @param endTime
	 * @param ticketTypeId
	 * @return
	 * @throws BaseException
	 */
	@RequestMapping(value = "regionCheckTicketByChannel")
	@ControlAspect(funtionCd = "i2_rpt_region_checkticket", operType = OperType.QUERY, havPrivs = true)
	@ControllerException(type = "page")
	public ModelAndView regionCheckTicketByChannel(Date beginDate, Date endDate) throws BaseException {
		ModelAndView mv = new ModelAndView("pages/rpt/rptsale/regionCheckTicketByChannel");
		try {
			if (beginDate == null || endDate == null) {
				beginDate = new Date();
				endDate = new Date();
			}
			List<Map<String, Object>> regionCheckTicketList = rptSaleService.regionCheckTicketByChannel(getLoginUserBean(), beginDate, endDate);
			mv.addObject("regionCheckTicketList", regionCheckTicketList);
			mv.addObject("beginDate", beginDate);
			mv.addObject("endDate", endDate);
		} catch (Exception e) {
			throw new BaseException(MSG.DB_ERROR, e);
		}
		return mv;
	}

	@RequestMapping(value = "expExlRegionCheckTicketByChannel")
	@ControlAspect(funtionCd = "i2_rpt_region_checkticket", operType = OperType.QUERY, havPrivs = true)
	@ControllerException(type = "page")
	public void expExlRegionCheckTicketByChannel(Date beginDate, Date endDate) throws BaseException {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			List<Map<String, Object>> regionCheckTicketList = rptSaleService.regionCheckTicketByChannel(getLoginUserBean(), beginDate, endDate);
			map.put("regionCheckTicketList", regionCheckTicketList);

			int totalNum = 0;
			for (Map<String, Object> obj : regionCheckTicketList) {
				if ("合计".equals(obj.get("orderTypeDsec"))) {
					totalNum = totalNum + CommonUtil.covertInt(obj.get("ejectTicketNum"));
				}
			}

			map.put("totalNum", totalNum);

			String date;
			if (beginDate != null && endDate != null) {
				date = DateUtil.formatDateToString(beginDate, "yyyyMMdd") + "-" + DateUtil.formatDateToString(endDate, "yyyyMMdd");
			} else {
				date = DateUtil.getNowDate("yyyyMMdd") + "-" + DateUtil.getNowDate("yyyyMMdd");
			}
			exportDetailExcel(map, "regionCheckTicketList.xls", "入园游客渠道来源统计" + date + ".xls");
		} catch (Exception e) {
			throw new BaseException(MSG.EXCEL_ERROR, e);
		}
	}
	
	
	
	/**
	 * Title: 网络代理商退票明细查询<br/>
	 * Description:
	 * 
	 * @param rptDt
	 * @param ticketTypeId
	 * @return
	 * @throws BaseException
	 */
	@RequestMapping(value = "wlRefundDetail")
	@ControlAspect(funtionCd = "网络代理商退票明细", operType = OperType.QUERY)
	@ControllerException
	public PageBean<Map<String, Object>> wlRefundDetail(Date startDate, Date endDate, String orgId) throws BaseException {
		try {
			PageBean<Map<String, Object>> refundDetailList = rptSaleService.wlRefundDetail(getLoginUserBean(), startDate, endDate, orgId);
			return refundDetailList;
		} catch (Exception e) {
			throw new BaseException(MSG.DB_ERROR, e);
		}
	}

	/**
	 * 
	 * Title: 网络代理商退票明细查询EXCEL导出<br/>
	 * Description:
	 * 
	 * @param rptDt
	 * @param ticketTypeId
	 * @throws BaseException
	 */
	@RequestMapping(value = "expExlWlRefundDetail")
	@ControlAspect(funtionCd = "网络代理商退票明细查询EXCEL导出", operType = OperType.QUERY)
	@ControllerException(type = "page")
	public void expExlWlRefundDetail(Date startDate, Date endDate, String orgId) throws BaseException {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			UserBean userBean = getLoginUserBean();// 不分页
			userBean.setPageSize(0);
			List<Map<String, Object>> refundDetailList = rptSaleService.wlRefundDetail(userBean, startDate, endDate, orgId).getRows();
			map.put("refundDetailList", refundDetailList);

			exportDetailExcel(map, "wlRefundDetail.xls", "网络代理商退票明细" + DateUtil.formatDateToString(startDate, "yyyyMMdd") + "-" + DateUtil.formatDateToString(endDate, "yyyyMMdd") + ".xls");
		} catch (Exception e) {
			throw new BaseException(MSG.EXCEL_ERROR, e);
		}
	}
	
	/**
	 * Title: 网络代理商门票核销明细查询<br/>
	 * Description:
	 * 
	 * @param rptDt
	 * @param ticketTypeId
	 * @return
	 * @throws BaseException
	 */
	@RequestMapping(value = "wlCheckdDetail")
	@ControlAspect(funtionCd = "网络代理商门票核销明细", operType = OperType.QUERY)
	@ControllerException
	public PageBean<Map<String, Object>> wlCheckdDetail(Date startDate, Date endDate, String orgId) throws BaseException {
		try {
			PageBean<Map<String, Object>> wlCheckDetailList = rptSaleService.wlCheckdDetail(getLoginUserBean(), startDate, endDate, orgId);
			return wlCheckDetailList;
		} catch (Exception e) {
			throw new BaseException(MSG.DB_ERROR, e);
		}
	}
	/**
	 * 
	 * Title: 网络代理商门票核销明细查询EXCEL导出<br/>
	 * Description:
	 * 
	 * @param rptDt
	 * @param ticketTypeId
	 * @throws BaseException
	 */
	@RequestMapping(value = "expExlWlCheckdDetail")
	@ControlAspect(funtionCd = "网络代理商门票核销明细查询EXCEL导出", operType = OperType.QUERY)
	@ControllerException(type = "page")
	public void expExlWlCheckdDetail(Date startDate, Date endDate, String orgId) throws BaseException {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			UserBean userBean = getLoginUserBean();// 不分页
			userBean.setPageSize(0);
			List<Map<String, Object>> wlCheckedDetailList = rptSaleService.wlCheckdDetail(userBean, startDate, endDate, orgId).getRows();
			map.put("wlCheckedDetailList", wlCheckedDetailList);

			exportDetailExcel(map, "wlCheckedDetail.xls", "网络代理商门票核销明细" + DateUtil.formatDateToString(startDate, "yyyyMMdd") + "-" + DateUtil.formatDateToString(endDate, "yyyyMMdd") + ".xls");
		} catch (Exception e) {
			throw new BaseException(MSG.EXCEL_ERROR, e);
		}
	}
}
