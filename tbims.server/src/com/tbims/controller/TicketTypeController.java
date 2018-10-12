package com.tbims.controller;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.tbims.bean.SysTicketTypePriceBean;
import com.tbims.bean.TicketTypeRuleBean;
import com.tbims.entity.SysTicketType;
import com.tbims.entity.SysTicketTypePrice;
import com.tbims.entity.SysTicketTypeVenue;
import com.tbims.entity.SysVenue;
import com.tbims.service.ITicketTypeService;
import com.zhming.support.BaseController;
import com.zhming.support.annontion.ControlAspect;
import com.zhming.support.annontion.ControllerException;
import com.zhming.support.bean.PageBean;
import com.zhming.support.common.OperType;
import com.zhming.support.exception.BaseException;
import com.zhming.support.exception.ServiceException;
/**
 * 
 * Title: 票种管理<br/>
 * Description:
 * @ClassName: TicketTypeController
 * @author ly
 * @date 2017年6月13日 上午11:23:49
 *
 */
@RestController
@RequestMapping("/ticketTypeMng/")
public class TicketTypeController extends BaseController {
	private static final Logger logger = LoggerFactory.getLogger(TicketTypeController.class);
	@Autowired
	private ITicketTypeService ticketTypeService;
	/**
	 * 
	 * Title: 查询票种列表<br/>
	 * Description:
	 * 
	 * @param user
	 * @return
	 */
	@RequestMapping(value = "listTicketType")
	@ControlAspect(funtionCd = "i2_sys_ticket_type_list", operType = OperType.QUERY, havPrivs=true)
	@ControllerException
	public PageBean<Map<String, Object>> listTicketType() throws BaseException {
		PageBean<Map<String, Object>> ticketTypeList = ticketTypeService.listTicketType(getLoginUserBean());
		return ticketTypeList;
	}

	/**
	 * 
	 * Title: 查询场馆列表<br/>
	 * Description:
	 * @return
	 */
	@RequestMapping(value = "venueList")
	@ControlAspect(funtionCd = "查询场馆列表下拉框", operType = OperType.QUERY)
	@ControllerException
	public List<SysVenue> venueList() {
		List<SysVenue> venueList = ticketTypeService.venueList();
		return venueList;
	}
	/**
	 * 
	 * Title: 查询规则列表前获取详情<br/>
	 * Description:
	 * @param ticketTypeId
	 * @return
	 */
	@RequestMapping(value = "beforeRuleList")
	@ControlAspect(funtionCd = "i2_sys_ticket_type_list", operType = OperType.QUERY, havPrivs=true)
	@ControllerException
	public ModelAndView beforeRuleList(String ticketTypeId) {
		ModelAndView mv = new ModelAndView("pages/sys/tickettypemng/ruleMng");
		SysTicketType sysTicketType = ticketTypeService.getTicketType(ticketTypeId);
		mv.addObject("sysTicketType", sysTicketType);
		return mv;
	}

	/**
	 * 
	 * Title: 查询规则列表<br/>
	 * Description:
	 * @return
	 */
	@RequestMapping(value = "ruleList")
	@ControlAspect(funtionCd = "查询规则列表", operType = OperType.QUERY)
	@ControllerException
	public PageBean<TicketTypeRuleBean> ruleList(String ticketTypeId) {
		PageBean<TicketTypeRuleBean> ruleList = ticketTypeService.ruleList(getLoginUserBean(), ticketTypeId);
		return ruleList;
	}

	/**
	 * 
	 * Title: 查询场馆信息<br/>
	 * Description:
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping(value = "venueStr")
	@ControlAspect(funtionCd = "查询场馆列表", operType = OperType.QUERY)
	@ControllerException
	public ModelAndView venueStr(String ticketTypeId) throws ServiceException {
		ModelAndView mv = new ModelAndView("pages/sys/tickettypemng/venue");
		String ret = ticketTypeService.getVenueStr(ticketTypeId);
		mv.addObject("venueStr", ret);
		return mv;
	}

	/**
	 * 
	 * Title: 添加票种<br/>
	 * Description:
	 * @param sysTicketType
	 * @param ruleListStr
	 * @param venueIds
	 * @throws BaseException
	 */
	@RequestMapping(value = "addTicketType")
	@ControlAspect(funtionCd = "i2_sys_ticket_type_add", operType = OperType.ADD, havPrivs=true)
	@ControllerException
	public void addTicketType(SysTicketType sysTicketType, String ruleListStr, String venueIds) throws BaseException {
		List<TicketTypeRuleBean> ruleList = JSON.parseArray(ruleListStr,
				TicketTypeRuleBean.class);
		ticketTypeService.addTicketType(getLoginUserBean(), sysTicketType, ruleList, venueIds);
	}

	/**
	 * 
	 * Title:删除票种 <br/>
	 * Description:
	 * @param sysTicketType
	 * @param ruleListStr
	 * @param venueIds
	 * @throws BaseException
	 */
	@RequestMapping(value = "delTicketType")
	@ControlAspect(funtionCd = "i2_sys_ticket_type_del", operType = OperType.DELETE, havPrivs=true)
	@ControllerException
	public void delTicketType(String ticketTypeId) throws BaseException {
		ticketTypeService.delTicketType(getLoginUserBean(), ticketTypeId);
	}

	/**
	 * 
	 * Title: 更新前查询明细<br/>
	 * Description:
	 * @param ticketTypeId
	 * @return
	 * @throws BaseException
	 */
	@RequestMapping(value = "beforeUpdTicketType")
	@ControlAspect(funtionCd = "更新前查询明细", operType = OperType.DELETE)
	@ControllerException
	public ModelAndView beforeUpdTicketType(String ticketTypeId) throws BaseException {
		ModelAndView mv = new ModelAndView("pages/sys/tickettypemng/updateTicketType");
		SysTicketType sysTicketType = ticketTypeService.getTicketType(ticketTypeId);
		List<SysTicketTypeVenue> typeVenueList = ticketTypeService.typeVenueList(ticketTypeId);
		String venueIds = "";
		for (SysTicketTypeVenue sysTicketTypeVenue : typeVenueList) {
			venueIds += sysTicketTypeVenue.getId().getVenueId() + ",";
		}
		if (venueIds.indexOf(",") != -1) {
			venueIds = venueIds.substring(0, venueIds.length() - 1);
		}
		mv.addObject("sysTicketType", sysTicketType);
		mv.addObject("venueIds", venueIds);
		return mv;
	}

	/**
	 * 保存更新数据 Title: <br/>
	 * Description:
	 * @param sysTicketType
	 * @param venueIds
	 * @throws BaseException
	 */
	@RequestMapping(value = "saveUpdTicketType")
	@ControlAspect(funtionCd = "i2_sys_ticket_type_upd", operType = OperType.UPDATE, havPrivs=true)
	@ControllerException
	public void saveUpdTicketType(SysTicketType sysTicketType, String[] venueIds) throws BaseException {
		logger.debug(sysTicketType.toString(), venueIds.toString());
		ticketTypeService.saveUpdTicketType(getLoginUserBean(), sysTicketType, venueIds);
	}

	/**
	 * 
	 * Title: 添加规则<br/>
	 * Description:
	 * @param ticketTypeRuleBean
	 * @throws BaseException
	 */
	@RequestMapping(value = "addTicketTypeRule")
	@ControlAspect(funtionCd = "i2_sys_ticket_type_list", operType = OperType.ADD, havPrivs=true)
	@ControllerException
	public void addTicketTypeRule(TicketTypeRuleBean ticketTypeRuleBean) throws BaseException {
		ticketTypeService.addTicketTypeRule(getLoginUserBean(), ticketTypeRuleBean);
	}

	/**
	 * 
	 * Title: 删除规则<br/>
	 * Description:
	 * @param ticketTypeRuleBean
	 * @throws BaseException
	 */
	@RequestMapping(value = "delTicketTypeRule")
	@ControlAspect(funtionCd = "i2_sys_ticket_type_list", operType = OperType.ADD, havPrivs=true)
	@ControllerException
	public void delTicketTypeRule(String ruleId) throws BaseException {
		ticketTypeService.delTicketTypeRule(getLoginUserBean(), ruleId);
	}

	/**
	 * 
	 * Title: 修改状态 Description:
	 * @param ticketTypeId
	 * @param ticketTypeStat 票种状态(Y正常N停用)
	 * @throws BaseException
	 */
	@RequestMapping(value = "updateTicketStat")
	@ControlAspect(funtionCd = "i2_sys_ticket_type_list", operType = OperType.UPDATE, havPrivs=true)
	@ControllerException
	public void updateTicketStat(String ticketTypeId, String ticketTypeStat) throws BaseException {
		ticketTypeService.updateTicketStat(getLoginUserBean(), ticketTypeId, ticketTypeStat);
	}
	/**
	 * 
	* Title: 阶梯票价详情<br/>
	* Description: 
	* @param ticketTypeId
	* @return
	 */
	@RequestMapping(value = "beforePriceList")
	@ControlAspect(funtionCd = "i2_sys_ticket_type_list", operType = OperType.QUERY, havPrivs=true)
	@ControllerException
	public ModelAndView beforePriceList(String ticketTypeId) throws BaseException{
		ModelAndView mv = new ModelAndView("pages/sys/tickettypemng/ticTypePriceMng");
		SysTicketType ticPrice = ticketTypeService.getTicketType(ticketTypeId);
		Long endNo=ticketTypeService.getEndNo(ticketTypeId);
		mv.addObject("ticPrice", ticPrice);
		mv.addObject("endNo", endNo);
		return mv;
	}
	/**
	 * 
	* Title: 查询阶梯票价列表<br/>
	* Description: 
	* @param ticketTypeId
	* @return
	 * @throws BaseException 
	 */
	@RequestMapping(value = "ticPriceList")
	@ControlAspect(funtionCd = "查询阶梯票价列表", operType = OperType.QUERY)
	@ControllerException
	public PageBean<SysTicketTypePriceBean> ticPriceList(String ticketTypeId) throws BaseException {
		PageBean<SysTicketTypePriceBean> ticPriceList = ticketTypeService.ticPriceList(getLoginUserBean(), ticketTypeId);
		return ticPriceList;
	}
	/**
	 * 
	* Title: 添加阶梯票价段<br/>
	* Description: 
	* @param ticketTypeRuleBean
	* @throws BaseException
	 */
	@RequestMapping(value = "addTicketTypePrice")
	@ControlAspect(funtionCd = "i2_sys_ticket_type_list", operType = OperType.ADD, havPrivs=true)
	@ControllerException
	public void addTicketTypePrice(SysTicketTypePrice ticTypePrice) throws BaseException {
		ticketTypeService.addTicketTypePrice(getLoginUserBean(), ticTypePrice);
	}
	/**
	 * 
	* Title: 删除阶梯票价信息<br/>
	* Description: 
	* @param ruleId
	* @throws BaseException
	 */
	@RequestMapping(value = "delTicketTypePrice")
	@ControlAspect(funtionCd = "i2_sys_ticket_type_list", operType = OperType.ADD, havPrivs=true)
	@ControllerException
	public void delTicketTypePrice(String priceId) throws BaseException {
		ticketTypeService.delTicketTypePrice(getLoginUserBean(), priceId);
	}
	/**
	 * 
	* Title: 查询阶梯票价历史表<br/>
	* Description: 
	* @param ticketTypeId
	* @return
	* @throws BaseException
	 */
	@RequestMapping(value = "ticPriceHList")
	@ControlAspect(funtionCd = "查询阶梯票价历史表", operType = OperType.QUERY)
	@ControllerException
	public PageBean<SysTicketTypePriceBean> ticPriceHList(String ticketTypeId) throws BaseException {
		PageBean<SysTicketTypePriceBean> ticPriceList = ticketTypeService.ticPriceHList(getLoginUserBean(), ticketTypeId);
		return ticPriceList;
	}
	/**
	 * 
	* Title:阶梯票价张数连续性校验 <br/>
	* Description: 
	* @param ticketTypeId
	* @throws BaseException
	 */
	@RequestMapping(value = "checkContinue")
	@ControlAspect(funtionCd = "校验连续", operType = OperType.QUERY)
	@ControllerException
	public boolean checkContinue(String ticketTypeId) throws BaseException {
		boolean result=ticketTypeService.checkContinue(getLoginUserBean(), ticketTypeId);
		return result;
	}
}
