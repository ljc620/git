package com.tbims.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.tbims.entity.SlOrg;
import com.tbims.entity.SysOutlet;
import com.tbims.service.IOutletMngService;
import com.zhming.support.BaseController;
import com.zhming.support.annontion.ControlAspect;
import com.zhming.support.annontion.ControllerException;
import com.zhming.support.bean.PageBean;
import com.zhming.support.common.OperType;
import com.zhming.support.exception.BaseException;
import com.zhming.support.exception.ServiceException;

/**
 * 
 * Title: 网点信息管理<br/>
 * Description:
 * @ClassName: OutletMngController
 * @author ly
 * @date 2017年6月23日 下午5:50:25
 *
 */
@RestController
@RequestMapping("/outletMng/")
public class OutletMngController extends BaseController {
	@Autowired
	private IOutletMngService outletMngService;

	/**
	 * 
	 * Title: 查询网点列表<br/>
	 * Description:
	 * 
	 * @param user
	 * @return
	 */
	@RequestMapping(value = "listOutlet")
	@ControlAspect(funtionCd = "i2_sys_outlet_list", operType = OperType.QUERY, havPrivs = true)
	@ControllerException
	public PageBean<Map<String, Object>> listOutlet(SysOutlet sysOutlet) throws BaseException {
		PageBean<Map<String, Object>> orgList = outletMngService.listOutlet(getLoginUserBean(), sysOutlet);
		return orgList;
	}

	/**
	 * Title:添加网点<br/>
	 * Description:
	 * @param taskFrqc
	 * @throws ServiceException
	 */
	@RequestMapping(value = "addOutlet")
	@ControlAspect(funtionCd = "i2_sys_outlet_list", operType = OperType.ADD, havPrivs = true)
	@ControllerException
	public void addOutlet(SysOutlet sysOutlet) throws ServiceException {
		outletMngService.addOutlet(getLoginUserBean(), sysOutlet);
	}
	/**
	 * 
	 * Title: 修改前查询<br/>
	 * Description:
	 * @param orgId
	 * @return
	 */
	@RequestMapping(value = "beforeUpdteOutlet")
	@ControlAspect(funtionCd = "i2_sale_org_update_before", operType = OperType.QUERY)
	@ControllerException
	public ModelAndView beforeUpdteOutlet(Long outletId) {
		ModelAndView mv = new ModelAndView("pages/sys/outletmng/updateOutlet");
		SysOutlet sysOutlet = outletMngService.getOutletById(outletId);
		mv.addObject("sysOutlet", sysOutlet);
		return mv;
	}
	/**
	 * 
	 * Title: 修改网点<br/>
	 * Description:
	 * @param sysOutlet
	 */
	@RequestMapping(value = "updateOutlet")
	@ControlAspect(funtionCd = "i2_sale_org_upd", operType = OperType.UPDATE)
	@ControllerException
	public void updateOutlet(SysOutlet sysOutlet) {
		outletMngService.updateOutlet(getLoginUserBean(), sysOutlet);
	}
	/**
	 * 
	 * Title: 获取机构<br/>
	 * Description:
	 * @return
	 */
	@RequestMapping(value = "orgList")
	@ControlAspect(funtionCd = "获取机构", operType = OperType.QUERY)
	@ControllerException
	public List<SlOrg> orgList(String orgType) {
		String realOrgType = "";
		if ("05".equals(orgType)) {
			realOrgType = "'1','2'";
		}
		else if ("06".equals(orgType)) {
			realOrgType = "'0'";
		}
		List<SlOrg> ret = outletMngService.orgList(realOrgType);
		return ret;
	}

	/**
	 * 
	 * Title:删除网点<br/>
	 * Description:
	 * @param outletId
	 */
	@RequestMapping(value = "delOutlet")
	@ControlAspect(funtionCd = "i2_sale_org_upd", operType = OperType.UPDATE)
	@ControllerException
	public void delOutlet(Long outletId) {
		outletMngService.delOutlet(outletId);
	}
	/**
	 * 
	 * Title: 更新状态 
	 * Description:状态(Y正常N停用)
	 * @param outletId
	 * @param stat
	 * @throws BaseException
	 */
	@RequestMapping(value = "updateStat")
	@ControlAspect(funtionCd = "更新状态", operType = OperType.UPDATE)
	@ControllerException
	public void updateStat(String outletId, String stat) throws BaseException {
		outletMngService.updateStat(getLoginUserBean(), outletId, stat);
	}
}
