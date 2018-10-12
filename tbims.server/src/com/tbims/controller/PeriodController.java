package com.tbims.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.tbims.bean.PeriodBean;
import com.tbims.entity.SlPeriod;
import com.tbims.service.IPeriodService;
import com.zhming.support.BaseController;
import com.zhming.support.annontion.ControlAspect;
import com.zhming.support.annontion.ControllerException;
import com.zhming.support.bean.PageBean;
import com.zhming.support.common.OperType;
import com.zhming.support.exception.ServiceException;
import com.zhming.support.util.UUIDGenerator;

/**
 * 
 * Title: 预售期管理 Description:
 * @ClassName: PeriodController
 * @author ly
 * @date 2017年7月3日 上午11:22:17
 *
 */

@RestController
@RequestMapping("/periodMng/")
public class PeriodController extends BaseController {
	@Autowired
	private IPeriodService iPeriodService;

	/**
	 * 
	 * Title: 查询预售期 Description:
	 * @param slPeriod
	 * @return
	 */
	@RequestMapping(value = "listPeriod")
	@ControlAspect(funtionCd = "i2_sale_period", operType = OperType.QUERY, havPrivs = true)
	@ControllerException
	public PageBean<PeriodBean> listPeriod(SlPeriod slPeriod) {
		return iPeriodService.listPeriod(getLoginUserBean(), slPeriod);
	}
	/***
	 * 
	 * Title: 添加预售期 Description:
	 * @param slPeriod
	 * @throws ServiceException
	 */
	@RequestMapping(value = "addPeriod")
	@ControlAspect(funtionCd = "i2_sale_period", operType = OperType.ADD, havPrivs = true)
	@ControllerException
	public void addPeriod(SlPeriod slPeriod) throws ServiceException {
		slPeriod.setSalePeriodId(UUIDGenerator.getPK());
		slPeriod.setVersionNo(new Date());
		iPeriodService.addPeriod(getLoginUserBean(), slPeriod);
	}

	/**
	 * 
	 * Title: 更新预售期 Description:
	 * @param slPeriod
	 * @throws ServiceException
	 */
	@RequestMapping(value = "updatePeriod")
	@ControlAspect(funtionCd = "i2_sale_period", operType = OperType.UPDATE, havPrivs = true)
	@ControllerException
	public void updatePeriod(SlPeriod slPeriod) throws ServiceException {
		iPeriodService.updatePeriod(getLoginUserBean(), slPeriod);
	}

	/**
	 * 
	 * Title: 删除预售期 Description:
	 * @param salePeriodId
	 */
	@RequestMapping(value = "delPeriod")
	@ControlAspect(funtionCd = "i2_sale_period", operType = OperType.UPDATE, havPrivs = true)
	@ControllerException
	public void delPeriod(String salePeriodId) {
		iPeriodService.delPeriod(getLoginUserBean(), salePeriodId);
	}
	/**
	 * 
	 * Title: 修改前查询时 Description:
	 * @param salePeriodId
	 * @return
	 */
	@RequestMapping(value = "beforeUpdtePeriod")
	@ControlAspect(funtionCd = "i2_sale_period", operType = OperType.QUERY)
	@ControllerException
	public ModelAndView beforeUpdtePeriod(String salePeriodId) {
		ModelAndView mv = new ModelAndView("pages/sys/periodMng/updatePeriod");
		SlPeriod slPeriod = iPeriodService.getPeriodById(salePeriodId);
		mv.addObject("slPeriod", slPeriod);
		return mv;
	}

	/**
	 * 
	 * Title: 查询场馆信息<br/>
	 * Description:
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping(value = "ticketName")
	@ControlAspect(funtionCd = "查询场馆列表", operType = OperType.QUERY)
	@ControllerException
	public ModelAndView ticketName(String salePeriodId) throws ServiceException {
		ModelAndView mv = new ModelAndView("pages/sys/tickettypemng/ticketName");
		String ret = iPeriodService.getTicketTypeName(salePeriodId);
		mv.addObject("ticketName", ret);
		return mv;
	}

}
