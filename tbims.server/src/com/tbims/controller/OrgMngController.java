package com.tbims.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.tbims.bean.OrgSaleTicketType;
import com.tbims.bean.SlAdvanceBean;
import com.tbims.bean.SlDepositBean;
import com.tbims.bean.SlLimitBean;
import com.tbims.entity.SlAdvance;
import com.tbims.entity.SlDeposit;
import com.tbims.entity.SlLimit;
import com.tbims.entity.SlOrg;
import com.tbims.entity.SysTicketType;
import com.tbims.service.IOrgMngService;
import com.tbims.service.ITicketTypeService;
import com.zhming.support.BaseController;
import com.zhming.support.annontion.ControlAspect;
import com.zhming.support.annontion.ControllerException;
import com.zhming.support.bean.PageBean;
import com.zhming.support.common.MSG;
import com.zhming.support.common.OperType;
import com.zhming.support.exception.BaseException;
import com.zhming.support.exception.ServiceException;
/**
 * 
 * Title: 机构管理 <br/>
 * Description:
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
	@Autowired
	private ITicketTypeService ticketTypeService;

	/**
	 * 
	 * Title: 查询签约社列表<br/>
	 * Description:
	 * 
	 * @param user
	 * @return
	 */
	@RequestMapping(value = "listOrg")
	@ControlAspect(funtionCd = "i2_sale_org_list", operType = OperType.QUERY, havPrivs = true)
	@ControllerException
	public PageBean<Map<String, Object>> listOrg(SlOrg slOrg, String orgBtype) throws BaseException {
		PageBean<Map<String, Object>> orgList = orgMngService.listOrg(getLoginUserBean(), slOrg, orgBtype);
		return orgList;
	}

	/**
	 * Title:添加签约社<br/>
	 * Description:
	 * @param taskFrqc
	 * @throws ServiceException
	 */
	@RequestMapping(value = "addOrg")
	@ControlAspect(funtionCd = "i2_sale_org_add", operType = OperType.ADD, havPrivs = true)
	@ControllerException
	public void addOrg(SlOrg slOrg) throws ServiceException {
		SlOrg slOrgOld = orgMngService.getOrg(slOrg.getOrgId());
		if (slOrgOld != null) {
			throw new ServiceException(MSG.ERROR, "组织机构代码已存在");
		}
		orgMngService.addOrg(getLoginUserBean(), slOrg);
	}

	/**
	 * 
	 * Title: 修改前查询<br/>
	 * Description:
	 * @param orgId
	 * @return
	 */
	@RequestMapping(value = "beforeUpdteOrg")
	@ControlAspect(funtionCd = "i2_sale_org_update_before", operType = OperType.QUERY)
	@ControllerException
	public ModelAndView beforeUpdteOrg(String orgId, String orgBtype) {
		ModelAndView mv = null;
		if ("org".equals(orgBtype)) {
			mv = new ModelAndView("pages/sale/orgmng/updateOrg");
		}
		else if ("wlagent".equals(orgBtype)) {
			mv = new ModelAndView("pages/sale/agentmng/updateAgent");
		}else if ("stagent".equals(orgBtype)) {
			mv = new ModelAndView("pages/sale/stagentmng/updateAgent");
		}
		SlOrg slOrg = orgMngService.getOrgById(orgId);
		mv.addObject("slOrg", slOrg);
		return mv;
	}

	/**
	 * 
	 * Title: 修改签约社<br/>
	 * Description:
	 * @param slOrg
	 */
	@RequestMapping(value = "updateOrg")
	@ControlAspect(funtionCd = "i2_sale_org_upd", operType = OperType.UPDATE, havPrivs = true)
	@ControllerException
	public void updateOrg(SlOrg slOrg) {
		orgMngService.updateOrg(getLoginUserBean(), slOrg);
	}

	/**
	 * 
	 * Title: 删除签约社<br/>
	 * Description:
	 * @param orgId
	 */
	@RequestMapping(value = "delOrg")
	@ControlAspect(funtionCd = "i2_sale_org_del", operType = OperType.UPDATE, havPrivs = true)
	@ControllerException
	public void delOrg(String orgId) {
		orgMngService.delOrg(getLoginUserBean(), orgId);
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
		}
		else {
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
	@ControlAspect(funtionCd = "i2_sale_org_limit_amt_list", operType = OperType.QUERY, havPrivs = true)
	@ControllerException
	public PageBean<Map<String, Object>> listLimitAmt(String orgId) throws BaseException {
		PageBean<Map<String, Object>> limitList = orgMngService.listAmtLimit(getLoginUserBean(), orgId);
		return limitList;
	}

	/**
	 * 
	 * Title: 添加额度<br/>
	 * Description:
	 * @param slOrg
	 * @throws ServiceException
	 */
	@RequestMapping(value = "addLimit")
	@ControlAspect(funtionCd = "i2_sale_org_upd", operType = OperType.UPDATE, havPrivs = true)
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
	@RequestMapping(value = "beforeListLimit")
	@ControlAspect(funtionCd = "查询额度明细前详情", operType = OperType.QUERY)
	@ControllerException
	public ModelAndView beforeListLimit(String orgId, String ticketTypeId) throws BaseException {
		ModelAndView mv = new ModelAndView("pages/sale/orgmng/limitMng");
		SlOrg slOrg = orgMngService.getOrgById(orgId);
		SysTicketType sysTicketType = ticketTypeService.getTicketType(ticketTypeId);
		mv.addObject("slOrg", slOrg);
		mv.addObject("sysTicketType", sysTicketType);
		return mv;
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
	@ControlAspect(funtionCd = "i2_sale_org_limit_list", operType = OperType.QUERY, havPrivs = true)
	@ControllerException
	public PageBean<SlLimitBean> listLimit(String orgId, String ticketTypeId) throws BaseException {
		PageBean<SlLimitBean> limitList = orgMngService.listLimit(getLoginUserBean(), orgId, ticketTypeId);
		return limitList;
	}

	/**
	 * 
	 * Title: 查询票种列表<br/>
	 * Description:
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
	 * Title: 增加额度前获取机构信息<br/>
	 * Description:
	 * @param orgId
	 * @return
	 */
	@RequestMapping(value = "getOrg")
	@ControlAspect(funtionCd = "增加额度前获取机构信息", operType = OperType.QUERY)
	@ControllerException
	public ModelAndView getOrg(String orgId, String flag) {
		ModelAndView mv = null;
		if ("o".equals(flag)) {
			mv = new ModelAndView("pages/sale/orgmng/addLimit");
		}
		else {
			mv = new ModelAndView("pages/sale/orgminusmng/addLimit");
		}
		SlOrg slOrg = orgMngService.getOrg(orgId);
		mv.addObject("slOrg", slOrg);
		return mv;
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
		}else if("w".equals(flag)){
			mv = new ModelAndView("pages/sale/agentmng/advanceMng");//网络代理商-预付款
		}else if("s".equals(flag)){
			mv = new ModelAndView("pages/sale/stagentmng/advanceMng");//实体代理商-预付款
		}
		else {
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
	public PageBean<SlAdvanceBean> listAdvance(String orgId) throws BaseException {
		PageBean<SlAdvanceBean> advanceList = orgMngService.listAdvance(getLoginUserBean(), orgId);
		return advanceList;
	}
	/**
	 * 
	 * Title: 增加预付款前获取机构信息<br/>
	 * Description:
	 * @param orgId
	 * @return
	 */
	@RequestMapping(value = "beforeAddAdvance")
	@ControlAspect(funtionCd = "增加预付款前获取机构信息", operType = OperType.QUERY)
	@ControllerException
	public ModelAndView beforeAddAdvance(String orgId, String flag) {
		ModelAndView mv = null;
		if ("o".equals(flag)) {
			mv = new ModelAndView("pages/sale/orgmng/addAdvance");//签约社--预付款
		}else if("w".equals(flag)){
			mv = new ModelAndView("pages/sale/agentmng/addAdvance");//网络代理商--预付款
		}else if("s".equals(flag)){
			mv = new ModelAndView("pages/sale/stagentmng/addAdvance");//实体代理商--预付款
		}
		else {
			mv = new ModelAndView("pages/sale/orgminusmng/addAdvance");
		}
		SlOrg slOrg = orgMngService.getOrg(orgId);
		mv.addObject("slOrg", slOrg);
		return mv;
	}
	/**
	 * 
	 * Title: 添加预付款<br/>
	 * Description:
	 * @param slOrg
	 * @throws ServiceException
	 */
	@RequestMapping(value = "addAdvance")
	@ControlAspect(funtionCd = "添加预付款", operType = OperType.UPDATE)
	@ControllerException
	public void addAdvance(SlAdvance slAdvance, String flag) throws ServiceException {
		if ("m".equals(flag)) {
			slAdvance.setAdvanceAmt(-slAdvance.getAdvanceAmt());//签约社扣罚
		}
		orgMngService.addAdvance(getLoginUserBean(), slAdvance);
	}

	/**
	 * 
	 * Title: 查询额度列表<br/>
	 * Description:
	 * @param user
	 * @return
	 */
	@RequestMapping(value = "beforeListDeposit")
	@ControlAspect(funtionCd = "查询额度明细前详情", operType = OperType.QUERY)
	@ControllerException
	public ModelAndView beforeListDeposit(String orgId,String type) throws BaseException {
		ModelAndView mv =null;
		if ("wl".equals(type)) {
			mv = new ModelAndView("pages/sale/agentmng/depositMng");
		}else if("st".equals(type)){
			mv = new ModelAndView("pages/sale/stagentmng/depositMng");
		}
		SlOrg slOrg = orgMngService.getOrgById(orgId);
		mv.addObject("slOrg", slOrg);
		Long depositSum = orgMngService.getDepositSum(orgId);
		mv.addObject("depositSum", depositSum);
		return mv;
	}

	/**
	 * 
	 * Title: 查询保证金<br/>
	 * Description:
	 * 
	 * @param user
	 * @return
	 */
	@RequestMapping(value = "listDeposit")
	@ControlAspect(funtionCd = "查询保证金", operType = OperType.QUERY)
	@ControllerException
	public PageBean<SlDepositBean> listDeposit(String orgId) throws BaseException {
		PageBean<SlDepositBean> retList = orgMngService.listDeposit(getLoginUserBean(), orgId);
		return retList;
	}
	/**
	 * 
	 * Title: 增加保证金前获取机构信息<br/>
	 * Description:
	 * @param orgId
	 * @return
	 */
	@RequestMapping(value = "beforeAddDeposit")
	@ControlAspect(funtionCd = "增加预付款前获取机构信息", operType = OperType.QUERY)
	@ControllerException
	public ModelAndView beforeAddDeposit(String orgId) {
		ModelAndView mv = new ModelAndView("pages/sale/agentmng/addDeposit");
		SlOrg slOrg = orgMngService.getOrg(orgId);
		mv.addObject("slOrg", slOrg);
		return mv;
	}
	/**
	 * 
	 * Title: 添加保证金<br/>
	 * Description:
	 * @param slOrg
	 */
	@RequestMapping(value = "addDeposit")
	@ControlAspect(funtionCd = "添加保证金", operType = OperType.UPDATE)
	@ControllerException
	public void addDeposit(SlDeposit slDeposit) {
		orgMngService.addDeposit(getLoginUserBean(), slDeposit);
	}
	/**
	 * 
	 * Title: 更新状态 Description:
	 * @param orgId
	 * @param orgStat
	 * @throws BaseException
	 */
	@RequestMapping(value = "updateStat")
	@ControlAspect(funtionCd = "更新状态", operType = OperType.UPDATE)
	@ControllerException
	public void updateStat(String orgId, String orgStat) throws BaseException {
		orgMngService.updateStat(getLoginUserBean(), orgId, orgStat);
	}
	/**
	 * 
	* Title: 重置授权码<br/>
	* Description: 
	* @param orgIds
	* @throws BaseException
	 */
	@RequestMapping(value = "updateToken")
	@ControlAspect(funtionCd = "重置终端授权码", operType = OperType.UPDATE)
	@ControllerException
	public void updateToken(String orgIds) throws BaseException {
		orgMngService.updateToken(orgIds);
	}
	
	/**
	* Title:更新机构回调设置 <br/>
	* Description: 
	* @param loginUserBean
	* @param orgId
	* @param callbackUrl
	* @param callbackData
	* @throws BaseException
	 */
	@RequestMapping(value = "updateCallBackSetting")
	@ControlAspect(funtionCd = "i2_sys_org_mng", operType = OperType.UPDATE, havPrivs = true)
	@ControllerException
	public void updateToken(String orgId, String callbackUrl, String callbackData) throws BaseException {
		orgMngService.updateCallBackSetting(orgId, callbackUrl, callbackData);
	}
	
	/**
	* Title:查询机构可售票种列表 <br/>
	* Description: 
	* @param loginUserBean
	* @param orgId
	* @throws BaseException
	 */
	@RequestMapping(value = "listOrgSaleTicketTypes")
	@ControlAspect(funtionCd = "i2_sys_org_mng", operType = OperType.UPDATE, havPrivs = true)
	@ControllerException
	public List<OrgSaleTicketType> listOrgSaleTicketTypes(String orgId) throws BaseException {
		List<OrgSaleTicketType> ticketTypes = orgMngService.listOrgSaleTicketTypes(orgId);
		return ticketTypes;
	}
	
	/**
	* Title:更新机构可售票种 <br/>
	* Description: 
	* @param loginUserBean
	* @param orgId
	* @param saleTickets
	* @throws BaseException
	 */
	@RequestMapping(value = "updateOrgSaleTicketTypes")
	@ControlAspect(funtionCd = "i2_sys_org_mng", operType = OperType.UPDATE, havPrivs = true)
	@ControllerException
	public void updateOrgSaleTicketTypes(String orgId, String[] saleTickets) throws BaseException {
		orgMngService.updateOrgSaleTicketTypes(orgId, saleTickets);
	}
}