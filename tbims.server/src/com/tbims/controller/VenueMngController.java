package com.tbims.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.tbims.entity.SysVenue;
import com.tbims.service.IVenueMngService;
import com.zhming.support.BaseController;
import com.zhming.support.annontion.ControlAspect;
import com.zhming.support.annontion.ControllerException;
import com.zhming.support.bean.PageBean;
import com.zhming.support.common.OperType;
import com.zhming.support.exception.BaseException;
import com.zhming.support.exception.DBException;
import com.zhming.support.exception.ServiceException;

/**
 * 
* Title: 场馆管理 <br/>
* Description: 
* @ClassName: VenueMngController
* @author syq
* @date 2017年7月20日 下午2:48:21
*
 */
@RestController
@RequestMapping("/venueMng/")
public class VenueMngController  extends BaseController {
	@Autowired
	private IVenueMngService VenueMngService;
	
	/**
	 * 
	 * Title: 查询场馆列表<br/>
	 * Description:
	 * 
	 * @param user
	 * @return
	 */
	@RequestMapping(value = "listVenue")
	@ControlAspect(funtionCd = "i2_sys_venue_mng", operType = OperType.QUERY, havPrivs=true)
	@ControllerException
	public PageBean<SysVenue> listVenue(SysVenue sysVenue) throws BaseException {
		PageBean<SysVenue> venueList =VenueMngService.listVenue(getLoginUserBean(),sysVenue);
		return venueList;
	}

	/**
	 * Title:添加场馆<br/>
	 * Description:
	 * @param taskFrqc
	 * @throws ServiceException
	 */
	@RequestMapping(value = "addVenue")
	@ControlAspect(funtionCd = "新增场馆", operType = OperType.ADD)
	@ControllerException
	public void addVenue(SysVenue sysVenue) throws ServiceException {
		VenueMngService.addVenue(getLoginUserBean(), sysVenue);
	}
	/**
	 * 
	* Title: 修改前查询<br/>
	* Description: 
	* @param orgId
	* @return
	 */
	@RequestMapping(value = "beforeUpdtVenue")
	@ControlAspect(funtionCd = "获取场馆详情", operType = OperType.QUERY)
	@ControllerException
	public ModelAndView beforeUpdteVenue(Long venueId) throws DBException{
		ModelAndView mv = new ModelAndView("pages/sys/venuemng/updateVenue");
		SysVenue sysVenue = VenueMngService.getVenueById(venueId);
		mv.addObject("sysVenue", sysVenue);
		return mv;
	}
	/**
	 * 
	* Title: 修改场馆<br/>
	* Description: 
	* @param sysVenue
	 */
	@RequestMapping(value = "updateVenue")
	@ControlAspect(funtionCd = "修改场馆", operType = OperType.UPDATE)
	@ControllerException
	public void  updateVenue(SysVenue sysVenue) throws DBException{
		VenueMngService.updateVenue(getLoginUserBean(), sysVenue);
	}
	
	/**
	 * 
	* Title:删除场馆<br/>
	* Description: 
	* @param venueId
	 */
	@RequestMapping(value = "delVenue")
	@ControlAspect(funtionCd = "删除场馆", operType = OperType.DELETE)
	@ControllerException
	public void  delVenue(Long venueId) throws DBException{
		VenueMngService.delVenue(venueId);
	}
	/**
	 * 
	* Title: 修改状态<br/>
	* Description: 
	* @param clientIds
	* @param stat Y-启用 N-停用
	* @throws BaseException
	 */
	@RequestMapping(value = "updateStat")
	@ControlAspect(funtionCd = "修改场馆状态", operType = OperType.UPDATE)
	@ControllerException
	public void updateStat(String venueIds, String stat) throws BaseException {
		VenueMngService.updateStat(getLoginUserBean(), venueIds, stat);
	}
}
