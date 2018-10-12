package com.tbims.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.tbims.entity.SysRegion;
import com.tbims.service.IRegionService;
import com.zhming.support.BaseController;
import com.zhming.support.annontion.ControlAspect;
import com.zhming.support.annontion.ControllerException;
import com.zhming.support.bean.PageBean;
import com.zhming.support.common.OperType;
import com.zhming.support.exception.BaseException;
import com.zhming.support.exception.ServiceException;

/**
 * 检票区域管理
 * @author a
 *
 */
@RestController
@RequestMapping("/regionMng/")
public class RegionController extends BaseController {
	@Autowired
	private IRegionService iRegionService;

	/**
	 * 查询检票区域
	 * @param sysRegion
	 * @return
	 */
	@RequestMapping(value = "listRegion")
	@ControlAspect(funtionCd = "i2_sys_region_list", operType = OperType.QUERY, havPrivs = true)
	@ControllerException
	public PageBean<Map<String, Object>> listRegion(SysRegion region) {
		PageBean<Map<String, Object>> listRegion = iRegionService.listRegion(getLoginUserBean(), region);
		return listRegion;
	}

	/**
	 * 添加检票区域
	 * @param sysRegion
	 * @throws ServiceException
	 */
	@RequestMapping(value = "addRegion")
	@ControlAspect(funtionCd = "i2_sys_region_list", operType = OperType.ADD, havPrivs = true)
	@ControllerException
	public void addRegion(SysRegion region) throws ServiceException {
		iRegionService.addRegion(getLoginUserBean(), region);
	}

	/**
	 * 修改检票区域
	 * @param sysRegion
	 */
	@RequestMapping(value = "updateRegion")
	@ControlAspect(funtionCd = "i2_sys_region_list", operType = OperType.UPDATE, havPrivs = true)
	@ControllerException
	public void updateRegion(SysRegion region) {

		iRegionService.updateRegion(getLoginUserBean(), region);
	}

	/**
	 * 删除检票区域
	 * @param regionId
	 */
	@RequestMapping(value = "delRegion")
	@ControlAspect(funtionCd = "i2_sys_region_list", operType = OperType.DELETE, havPrivs = true)
	@ControllerException
	public void delRegion(Long regionId) {
		iRegionService.delRegion(getLoginUserBean(), regionId);
	}
	/**
	 * 
	 * Title: 修改前查询 Description:
	 * @param regionId
	 * @return
	 */
	@RequestMapping(value = "beforeUpdteRegion")
	@ControlAspect(funtionCd = "查询检票区域", operType = OperType.QUERY)
	@ControllerException
	public ModelAndView beforeUpdteRegion(Long regionId) {
		ModelAndView mv = new ModelAndView("pages/sys/regionMng/updateRegion");
		SysRegion sysRegion = iRegionService.getById(regionId);
		mv.addObject("sysRegion", sysRegion);
		return mv;
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
	public ModelAndView venueStr(Long regionId) throws ServiceException {
		ModelAndView mv = new ModelAndView("pages/sys/tickettypemng/venue");
		String ret = iRegionService.getVenueStr(regionId);
		mv.addObject("venueStr", ret);
		return mv;
	}
	/**
	 * 
	 * Title: 更新状态<br/>
	 * Description: 状态(Y正常N停用)
	 * @param regionId
	 * @param stat
	 * @throws BaseException
	 */
	@RequestMapping(value = "updateStat")
	@ControlAspect(funtionCd = "i2_sys_region_list", operType = OperType.UPDATE, havPrivs = true)
	@ControllerException
	public void updateStat(String regionId, String stat) throws BaseException {
		iRegionService.updateStat(getLoginUserBean(), regionId, stat);
	}
}
