package com.tbims.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.tbims.entity.SlAdvance;
import com.tbims.entity.SlLimit;
import com.tbims.entity.SlOrg;
import com.tbims.entity.SysTicketType;
import com.tbims.service.IOrgMngService;
import com.zhming.support.BaseController;
import com.zhming.support.annontion.ControlAspect;
import com.zhming.support.annontion.ControllerException;
import com.zhming.support.bean.PageBean;
import com.zhming.support.common.OperType;
import com.zhming.support.exception.BaseException;
import com.zhming.support.exception.ServiceException;

/**
 * 
 * Title: 机构管理 <br/>
 * Description:
 * 
 * @ClassName: OrgMngController
 * @author ly
 * @date 2017年6月12日 上午11:32:55
 *
 */

@RestController
@RequestMapping("/orgMng/")
public class OrgMngController extends BaseController {
	@Autowired
	private IOrgMngService orgMngService;

	/**
	 * 
	 * Title: 查询签约社列表<br/>
	 * Description:
	 * 
	 * @param user
	 * @return
	 */
	@RequestMapping(value = "listOrg")
	@ControlAspect(funtionCd = "查询签约社列表", operType = OperType.QUERY)
	@ControllerException
	public PageBean<Map<String, Object>> listOrg(SlOrg slOrg, String orgBtype) throws BaseException {
		PageBean<Map<String, Object>> orgList = orgMngService.listOrg(getLoginUserBean(), slOrg, orgBtype);
		return orgList;
	}

	/**
	 * 
	 * Title: 修改前查询<br/>
	 * Description:
	 * 
	 * @param orgId
	 * @return
	 */
	@RequestMapping(value = "beforeUpdteOrg")
	@ControlAspect(funtionCd = "修改前查询", operType = OperType.QUERY)
	@ControllerException
	public ModelAndView beforeUpdteOrg(String orgId, String orgBtype) {
		ModelAndView mv = null;
		if ("org".equals(orgBtype)) {
			mv = new ModelAndView("pages/sale/orgmng/updateOrg");
		} else if ("agent".equals(orgBtype)) {
			mv = new ModelAndView("pages/sale/agentmng/updateAgent");
		}
		SlOrg slOrg = orgMngService.getOrgById(orgId);
		mv.addObject("slOrg", slOrg);
		return mv;
	}

	/**
	 * 
	 * Title: 查询额度余额列表前查询详情<br/>
	 * Description:
	 * 
	 * @param user
	 * @return
	 */
	@RequestMapping(value = "beforeListLimitAmt")
	@ControlAspect(funtionCd = "查询额度余额列表前查询详情", operType = OperType.QUERY)
	@ControllerException
	public ModelAndView beforeListLimitAmt(String orgId, String flag) throws BaseException {
		ModelAndView mv = null;
		if ("o".equals(flag)) {
			mv = new ModelAndView("pages/sale/orgmng/limitAmtMng");
		} else {
			mv = new ModelAndView("pages/sale/orgminusmng/limitAmtMng");
		}
		SlOrg slOrg = orgMngService.getOrgById(orgId);
		mv.addObject("slOrg", slOrg);
		return mv;
	}

	/**
	 * 
	 * Title: 查询额度余额列表<br/>
	 * Description:
	 * 
	 * @param user
	 * @return
	 */
	@RequestMapping(value = "listLimitAmt")
	@ControlAspect(funtionCd = "查询额度余额列表", operType = OperType.QUERY)
	@ControllerException
	public PageBean<Map<String, Object>> listLimitAmt(String orgId) throws BaseException {
		PageBean<Map<String, Object>> limitList = orgMngService.listAmtLimit(getLoginUserBean(), orgId);
		return limitList;
	}

	/**
	 * 
	 * Title: 添加额度<br/>
	 * Description:
	 * 
	 * @param slOrg
	 * @throws ServiceException
	 */
	@RequestMapping(value = "addLimit")
	@ControlAspect(funtionCd = "添加额度", operType = OperType.UPDATE)
	@ControllerException
	public void addLimit(SlLimit slLimit, String flag) throws ServiceException {
		if ("m".equals(flag)) {
			slLimit.setLimit(-slLimit.getLimit());
		}
		orgMngService.addLimit(getLoginUserBean(), slLimit);
	}

	/**
	 * 
	 * Title: 查询额度列表<br/>
	 * Description:
	 * 
	 * @param user
	 * @return
	 */
	@RequestMapping(value = "listLimit")
	@ControlAspect(funtionCd = "查询额度列表", operType = OperType.QUERY)
	@ControllerException
	public PageBean<SlLimit> listLimit(String orgId, String ticketTypeId) throws BaseException {
		PageBean<SlLimit> limitList = orgMngService.listLimit(getLoginUserBean(), orgId, ticketTypeId);
		return limitList;
	}

	/**
	 * 
	 * Title: 查询票种列表<br/>
	 * Description:
	 * 
	 * @return
	 */
	@RequestMapping(value = "ticketTypeList")
	@ControlAspect(funtionCd = "查询票种列表下拉框", operType = OperType.QUERY)
	@ControllerException
	public List<SysTicketType> ticketTypeList(String flag) {
		List<SysTicketType> ticketTypeList = orgMngService.ticketTypeList(flag);
		return ticketTypeList;
	}

	/**
	 * 
	 * Title: 查询预付款列表前查询详情<br/>
	 * Description:
	 * 
	 * @param user
	 * @return
	 */
	@RequestMapping(value = "beforeListAdvance")
	@ControlAspect(funtionCd = "查询预付款列表前查询详情", operType = OperType.QUERY)
	@ControllerException
	public ModelAndView beforeListAdvance(String orgId, String flag) throws BaseException {
		ModelAndView mv = null;
		if ("o".equals(flag)) {
			mv = new ModelAndView("pages/sale/orgmng/advanceMng");
		} else {
			mv = new ModelAndView("pages/sale/orgminusmng/advanceMng");
		}
		SlOrg slOrg = orgMngService.getOrgById(orgId);
		mv.addObject("slOrg", slOrg);
		return mv;
	}

	/**
	 * 
	 * Title: 查询预付款列表<br/>
	 * Description:
	 * 
	 * @param user
	 * @return
	 */
	@RequestMapping(value = "listAdvance")
	@ControlAspect(funtionCd = "查询预付款列表", operType = OperType.QUERY)
	@ControllerException
	public PageBean<SlAdvance> listAdvance(String orgId) throws BaseException {
		PageBean<SlAdvance> advanceList = orgMngService.listAdvance(getLoginUserBean(), orgId);
		return advanceList;
	}

	/**
	 * 
	 * Title: 增加预付款前获取机构信息<br/>
	 * Description:
	 * 
	 * @param orgId
	 * @return
	 */
	@RequestMapping(value = "beforeAddAdvance")
	@ControlAspect(funtionCd = "增加预付款前获取机构信息", operType = OperType.QUERY)
	@ControllerException
	public ModelAndView beforeAddAdvance(String orgId, String flag) {
		ModelAndView mv = null;
		if ("o".equals(flag)) {
			mv = new ModelAndView("pages/sale/orgmng/addAdvance");
		} else {
			mv = new ModelAndView("pages/sale/orgminusmng/addAdvance");
		}
		SlOrg slOrg = orgMngService.getOrg(orgId);
		mv.addObject("slOrg", slOrg);
		return mv;
	}

}