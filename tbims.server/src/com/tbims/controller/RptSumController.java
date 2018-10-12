package com.tbims.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.servlet.ModelAndView;

import com.tbims.bean.PayTypeBean;
import com.tbims.bean.RptStrinfoDBean;
import com.tbims.bean.RptStrinfoOutletDBean;
import com.tbims.bean.SyntheticalBean;
import com.tbims.entity.SysUser;
import com.tbims.service.IRptSumService;
import com.zhming.support.BaseController;
import com.zhming.support.annontion.ControlAspect;
import com.zhming.support.annontion.ControllerException;
import com.zhming.support.bean.PageBean;
import com.zhming.support.bean.UserBean;
import com.zhming.support.common.MSG;
import com.zhming.support.common.OperType;
import com.zhming.support.exception.BaseException;
import com.zhming.support.util.DateUtil;
/**
 * 
 * Title: 中心历史库存导, 网点历史库存 Description:
 * @ClassName: RptSumController
 * @author ly
 * @date 2017年7月23日 下午5:34:43
 *
 */
@RestController
@RequestMapping("/rptsum/")
public class RptSumController extends BaseController {
	@Autowired
	private IRptSumService rptSumService;
	/**
	 * 
	 * Title: 查询中心历史库存导
	 * @param rptDate
	 * @param ticketTypeId
	 * @return
	 * @throws BaseException
	 */
	@RequestMapping(value = "listRptStrinfoD")
	@ControlAspect(funtionCd = "i2_rpt_store_query", operType = OperType.QUERY, havPrivs = true)
	@ControllerException
	public PageBean<RptStrinfoDBean> listRptStrinfoD(Date startDate, Date endDate, String ticketTypeId) throws BaseException {
		PageBean<RptStrinfoDBean> listRptStrinfoD = rptSumService.listRptStrinfoD(getLoginUserBean(), startDate, endDate, ticketTypeId);
		return listRptStrinfoD;
	}
	/**
	 * 
	 * Title: 查询 网点历史库存
	 * @param rptDate
	 * @param outletId
	 * @param ticketTypeId
	 * @return
	 * @throws BaseException
	 */
	@RequestMapping(value = "listRptSumOutlet")
	@ControlAspect(funtionCd = "i2_rpt_outlet_query", operType = OperType.QUERY, havPrivs = true)
	@ControllerException
	public PageBean<RptStrinfoOutletDBean> listRptSumOutlet(Date startDate, Date endDate, Long outletId, String ticketTypeId) throws BaseException {
		PageBean<RptStrinfoOutletDBean> listRptSumOutlet = rptSumService.listRptSumOutlet(getLoginUserBean(), startDate, endDate, outletId, ticketTypeId);
		return listRptSumOutlet;
	}

	/**
	 * 中心历史库存导出EXCEL
	 * 
	 * @param orderListId
	 * @param itemId
	 * @throws BaseException
	 */
	@RequestMapping(value = "expExcelRptStrinfoD")
	@ControlAspect(funtionCd = "中心历史库存导出EXCEL", operType = OperType.QUERY)
	@ControllerException(type = "page")
	public void expExcelCenterStore(Date startDate, Date endDate, String ticketTypeId) throws BaseException {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			UserBean userBean = getLoginUserBean();
			userBean.setPageSize(0);
			PageBean<RptStrinfoDBean> rptStrinfoDList = rptSumService.listRptStrinfoD(userBean, startDate, endDate, ticketTypeId);
			List<RptStrinfoDBean> listRptStrinfoD = rptStrinfoDList.getRows();
			map.put("listRptStrinfoD", listRptStrinfoD);
			Date dat = new Date();
			Date dat2 = new Date();
			if(startDate!=null&&endDate!=null){
				dat = startDate;
				dat2 = endDate;
			}
			exportDetailExcel(map, "rptStrinfoD.xls", "中心历史库存" + DateUtil.formatDateToString(dat, "yyyyMMdd")+ "-"+DateUtil.formatDateToString(dat2, "yyyyMMdd") + ".xls");
		} catch (Exception e) {
			throw new BaseException(MSG.EXCEL_ERROR, e);
		}
	}
	/**
	 * 网点历史库存导出EXCEL
	 * 
	 * @param orderListId
	 * @param itemId
	 * @throws BaseException
	 */
	@RequestMapping(value = "expExcelRptSumOutlet")
	@ControlAspect(funtionCd = "网点历史库存导出EXCEL", operType = OperType.QUERY)
	@ControllerException(type = "page")
	public void expExcelCenterStore(Date startDate, Date endDate, Long outletId, String ticketTypeId) throws BaseException {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			UserBean userBean = getLoginUserBean();
			userBean.setPageSize(0);
			PageBean<RptStrinfoOutletDBean> rptSumOutletlist = rptSumService.listRptSumOutlet(userBean, startDate, endDate, outletId, ticketTypeId);
			List<RptStrinfoOutletDBean> listRptSumOutlet = rptSumOutletlist.getRows();
			map.put("listRptSumOutlet", listRptSumOutlet);
			Date dat = new Date();
			Date dat2 = new Date();
			if(startDate!=null&&endDate!=null){
				dat = startDate;
				dat2 = endDate;
			}
			exportDetailExcel(map, "rptSumOutlet.xls", "网点历史库存" + DateUtil.formatDateToString(dat, "yyyyMMdd")+ "-"+DateUtil.formatDateToString(dat2, "yyyyMMdd") + ".xls");
		} catch (Exception e) {
			throw new BaseException(MSG.EXCEL_ERROR, e);
		}
	}

	/**
	 * 
	 * Title: 支付方式统计表查询 Description:
	 * @param startDate
	 * @param endDate
	 * @param outletId
	 * @param userId
	 * @return
	 * @throws BaseException
	 */
	@RequestMapping(value = "payTypeSum")
	@ControlAspect(funtionCd = "支付方式统计表", operType = OperType.QUERY)
	@ControllerException(type = "page")
	public ModelAndView payTypeSum(Date startDate, Date endDate, String outletId, String outletName, String userId) throws BaseException {
		ModelAndView mv = new ModelAndView("pages/rpt/rptsum/payTypeSum");
		try {
			if (startDate == null) {
				startDate = DateUtil.formatStringToDate(DateUtil.getNowDate(), "yyyy-MM-dd");
			}
			if (endDate == null) {
				endDate = DateUtil.formatStringToDate(DateUtil.getNowDate(), "yyyy-MM-dd");
			}
			List<PayTypeBean> payTypeSumList = rptSumService.listPayType(startDate, endDate, outletId, userId);
			mv.addObject("payTypeSumList", payTypeSumList);
			mv.addObject("startDate", startDate);
			mv.addObject("endDate", endDate);
			mv.addObject("outletId", outletId);
			mv.addObject("userId", userId);
			mv.addObject("outletName", outletName);
		} catch (Exception e) {
			throw new BaseException(MSG.DB_ERROR, e);
		}
		return mv;
	}
	/**
	 * 
	 * Title: 支付方式统计EXCEL导出 Description:
	 * @param startDate
	 * @param endDate
	 * @param outletId
	 * @param userId
	 * @throws BaseException
	 */
	@RequestMapping(value = "expExcelpayTypeSum")
	@ControlAspect(funtionCd = "支付方式统计表EXCEL导出", operType = OperType.QUERY)
	@ControllerException(type = "page")
	public void expExcelpayTypeSum(Date startDate, Date endDate, String outletId, String userId) throws BaseException {
		Map<String, Object> map = new HashMap<String, Object>();
		try {

			List<PayTypeBean> paytypesumList = rptSumService.listPayType(startDate, endDate, outletId, userId);
			map.put("paytypesumList", paytypesumList);
			map.put("listSize", paytypesumList.size());
			Date dat = new Date();
			Date dat2 = new Date();
			if(startDate!=null&&endDate!=null){
				dat = startDate;
				dat2 = endDate;
			}
			exportDetailExcel(map, "payTypeSum.xls", "支付方式统计表" + DateUtil.formatDateToString(dat, "yyyyMMdd")+ "-"+DateUtil.formatDateToString(dat2, "yyyyMMdd") + ".xls");
		} catch (Exception e) {
			throw new BaseException(MSG.EXCEL_ERROR, e);
		}
	}

	/**
	 * 
	 * Title: 综合汇总表 Description:
	 * @param startDate
	 * @param endDate
	 * @param ticketTypeId
	 * @return
	 * @throws BaseException
	 */
	@RequestMapping(value = "syntheticalSum")
	@ControlAspect(funtionCd = "综合汇总表", operType = OperType.QUERY)
	@ControllerException(type = "page")
	public ModelAndView syntheticalSum(Date startDate, Date endDate, String ticketTypeId) throws BaseException {
		ModelAndView mv = new ModelAndView("pages/rpt/rptsum/syntheticalSum");
		try {
			if (startDate == null) {
				startDate = DateUtil.formatStringToDate(DateUtil.addDay(DateUtil.getNowDate(),-1),"yyyy-MM-dd");
			}
			if (endDate == null) {
				endDate = DateUtil.formatStringToDate(DateUtil.addDay(DateUtil.getNowDate(),-1),"yyyy-MM-dd");
			}
			List<SyntheticalBean> syntheticalList = rptSumService.listSynthetical(startDate, endDate, ticketTypeId);
			mv.addObject("syntheticalList", syntheticalList);
			mv.addObject("startDate", startDate);
			mv.addObject("endDate", endDate);
			mv.addObject("ticketTypeId", ticketTypeId);
		} catch (Exception e) {
			throw new BaseException(MSG.DB_ERROR, e);
		}
		return mv;
	}
	/**
	 * 
	 * Title: 综合汇总表-导出excel<br/>
	 * Description:
	 * @param startDate
	 * @param endDate
	 * @param ticketTypeId
	 * @throws BaseException
	 */
	@RequestMapping(value = "expExcelSyntheticalSum")
	@ControlAspect(funtionCd = "综合汇总表EXCEL导出", operType = OperType.QUERY)
	@ControllerException(type = "page")
	public void expExcelSyntheticalSum(Date startDate, Date endDate, String ticketTypeId) throws BaseException {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			List<SyntheticalBean> syntheticalList = rptSumService.listSynthetical(startDate, endDate, ticketTypeId);
			map.put("syntheticalList", syntheticalList);
			map.put("listSize", syntheticalList.size());
			Date dat = DateUtil.formatStringToDate(DateUtil.addDay(DateUtil.getNowDate(),-1),"yyyy-MM-dd");
			Date dat2 = DateUtil.formatStringToDate(DateUtil.addDay(DateUtil.getNowDate(),-1),"yyyy-MM-dd");
			if(startDate!=null&&endDate!=null){
				dat = startDate;
				dat2 = endDate;
			}
			exportDetailExcel(map, "syntheticalSum.xls", "综合汇总表" + DateUtil.formatDateToString(dat, "yyyyMMdd")+ "-"+DateUtil.formatDateToString(dat2, "yyyyMMdd") + ".xls");
		} catch (Exception e) {
			throw new BaseException(MSG.EXCEL_ERROR, e);
		}
	}

	/**
	 * 
	 * Title: 用户列表下拉框 Description:
	 * @param userId
	 * @return
	 */
	@RequestMapping(value = "userList")
	@ControlAspect(funtionCd = "查询用户列表下拉框", operType = OperType.QUERY)
	@ControllerException
	public List<SysUser> userList(Long outletId) {
		ModelAndView mv = new ModelAndView("pages/rpt/rptsum/userList");
		List<SysUser> userList = rptSumService.userList(outletId);
		mv.addObject("userList", userList);
		return userList;
	}
}
