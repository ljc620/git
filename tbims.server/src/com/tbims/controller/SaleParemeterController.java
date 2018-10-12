package com.tbims.controller;

import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.tbims.entity.SysParemeter;
import com.tbims.service.ISaleParemeterService;
import com.zhming.support.BaseController;
import com.zhming.support.annontion.ControlAspect;
import com.zhming.support.annontion.ControllerException;
import com.zhming.support.bean.PageBean;
import com.zhming.support.common.OperType;
import com.zhming.support.exception.ServiceException;

/**
 * 
 * Title: 销售参数管理 Description:
 * @ClassName: SaleParemeterController
 * @author ly
 * @date 2017年7月3日 上午11:22:42
 *
 */
@RestController
@RequestMapping("/saleParemeter/")
public class SaleParemeterController extends BaseController {
	@Autowired
	private ISaleParemeterService iSaleParemeterService;

	/**
	 * 
	 * Title: 查询销售参数 Description:
	 * @param sysParemeter
	 * @return
	 */
	@RequestMapping(value = "listSaleParemeter")
	@ControlAspect(funtionCd = "i2_sale_paremeter", operType = OperType.QUERY, havPrivs = true)
	@ControllerException
	public PageBean<Map<String, Object>> listSaleParemeter(SysParemeter sysParemeter) {
		PageBean<Map<String, Object>> listSaleParemeter = iSaleParemeterService.listSaleParemeter(getLoginUserBean(), sysParemeter);
		return listSaleParemeter;
	}

	/**
	 * 
	 * Title: 添加销售参数 Description:
	 * @param sysParemeter
	 * @throws ServiceException
	 */
	@RequestMapping(value = "addSaleParemeter")
	@ControlAspect(funtionCd = "i2_sale_paremeter", operType = OperType.ADD, havPrivs = true)
	@ControllerException
	public void addSaleParemeter(SysParemeter sysParemeter) throws ServiceException {
		sysParemeter.setOpeTime(new Date());
		iSaleParemeterService.addSaleParemeter(getLoginUserBean(), sysParemeter);
	}

	/**
	 * 
	 * Title: 更新销售参数 Description:
	 * @param sysParemeter
	 */
	@RequestMapping(value = "updateSaleParemeter")
	@ControlAspect(funtionCd = "i2_sale_paremeter", operType = OperType.UPDATE, havPrivs = true)
	@ControllerException
	public void updateSaleParemeter(SysParemeter sysParemeter) {
		iSaleParemeterService.updateSaleParemeter(getLoginUserBean(), sysParemeter);
	}

	/**
	 * 
	 * Title: 删除销售参数 Description:
	 * @param paremeterId
	 */
	@RequestMapping(value = "delSaleParemeter")
	@ControlAspect(funtionCd = "i2_sale_paremeter", operType = OperType.UPDATE, havPrivs = true)
	@ControllerException
	public void delSaleParemeter(String paremeterId) {
		iSaleParemeterService.delSaleParemeter(getLoginUserBean(), paremeterId);
	}
	/**
	 * 
	 * 
	 * Title: 修改前查询 Description:
	 * @param paremeterId
	 * @return
	 */
	@RequestMapping(value = "beforeUpdteParemeter")
	@ControlAspect(funtionCd = "i2_sys_paremeter", operType = OperType.QUERY)
	@ControllerException
	public ModelAndView beforeUpdteParemeter(String paremeterId) {
		ModelAndView mv = new ModelAndView("pages/saleParemeter/updateSaleParemeter");
		SysParemeter sysParemeter = iSaleParemeterService.getById(paremeterId);
		mv.addObject("sysParemeter", sysParemeter);
		return mv;
	}
}