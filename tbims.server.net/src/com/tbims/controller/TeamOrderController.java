package com.tbims.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.tbims.bean.ChangeUserBean;
import com.tbims.bean.TeamOrderBean;
import com.tbims.entity.SlChangeUser;
import com.tbims.entity.SlTeamOrder;
import com.tbims.entity.SlTeamOrderDetail;
import com.tbims.entity.SysTicketType;
import com.tbims.service.ITeamOrderService;
import com.zhming.support.BaseController;
import com.zhming.support.annontion.ControlAspect;
import com.zhming.support.annontion.ControllerException;
import com.zhming.support.bean.PageBean;
import com.zhming.support.bean.UserBean;
import com.zhming.support.common.OperType;
import com.zhming.support.exception.ServiceException;

/**
 * 
 * Title: 团队订单管理<br/>
 * Description:
 * 
 * @ClassName: TeamsunOrderController
 * @author ly
 * @date 2017年6月26日 下午12:47:52
 *
 */
@RestController
@RequestMapping("/teamOrder/")
public class TeamOrderController extends BaseController {
	@Autowired
	private ITeamOrderService teamOrderService;

	/**
	 * 
	 * Title: 查询团队票订单预订<br/>
	 * Description:
	 * 
	 * @param strDeliveryApply
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "listApply")
	@ControlAspect(funtionCd = "i2_sale_team_apply", operType = OperType.QUERY, havPrivs=true)
	@ControllerException
	public PageBean<TeamOrderBean> listApply(TeamOrderBean teamOrderBean) throws Exception {
		UserBean userBean = getLoginUserBean();
		return teamOrderService.listApply(userBean, teamOrderBean);
	}

	/**
	 * 
	 * Title: 查询配送申请<br/>
	 * Description:
	 * 
	 * @param strDeliveryApply
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "listApplyHis")
	@ControlAspect(funtionCd = "查询配送申请", operType = OperType.QUERY)
	@ControllerException
	public PageBean<TeamOrderBean> listApplyHis(TeamOrderBean teamOrderBean) throws Exception {
		UserBean userBean = getLoginUserBean();
		return teamOrderService.listApplyHis(userBean, teamOrderBean);
	}

	/**
	 * 
	 * Title: 添加订单-获取票种下拉框列表<br/>
	 * Description:
	 * 
	 * @return
	 */
	@RequestMapping(value = "listTicketType")
	@ControlAspect(funtionCd = "查询票种", operType = OperType.QUERY)
	@ControllerException
	public List<SysTicketType> listTicketType() {
		return teamOrderService.listTicketType(getLoginUserBean());
	}

	/**
	 * 
	 * Title: 添加订单<br/>
	 * Description:
	 * 
	 * @param slTeamOrder
	 * @param detailStr
	 * @throws Exception
	 */
	@RequestMapping(value = "addOrder")
	@ControlAspect(funtionCd = "i2_sale_team_apply", operType = OperType.ADD, havPrivs=true)
	@ControllerException
	public void addOrder(SlTeamOrder slTeamOrder, String detailStr) throws Exception {
		UserBean userBean = getLoginUserBean();
		List<SlTeamOrderDetail> detailList = JSON.parseArray(detailStr, SlTeamOrderDetail.class);
		teamOrderService.addOrder(userBean, slTeamOrder, detailList);
	}

	/**
	 * 
	 * Title: 查询换票人<br/>
	 * Description:
	 * 
	 * @return
	 */
	@RequestMapping(value = "listChangeUser")
	@ControlAspect(funtionCd = "查询换票人", operType = OperType.QUERY)
	@ControllerException
	public List<SlChangeUser> listChangeUser() {
		return teamOrderService.listChangeUser(getLoginUserBean());
	}

	/**
	 * 
	 * Title: 订单明细<br/>
	 * Description:
	 * 
	 * @param applyId
	 * @param flag
	 * @return
	 */
	@RequestMapping(value = "showDetail")
	@ControlAspect(funtionCd = "查询明细", operType = OperType.QUERY)
	@ControllerException
	public ModelAndView showDetail(String applyId, String flag) {
		ModelAndView mv = null;
		if ("d".equals(flag)) {
			mv = new ModelAndView("pages/sale/orderDetail");
		} else if ("p".equals(flag)) {
			mv = new ModelAndView("pages/sale/orderPrint");
		}
		SlTeamOrder slTeamOrder = teamOrderService.getSlTeamOrder(applyId);
		String changeUserId = slTeamOrder.getChangeUserId();
		ChangeUserBean changeUserBean = teamOrderService.getChangeUser(changeUserId);
		mv.addObject("slTeamOrder", slTeamOrder);
		mv.addObject("changeUserBean", changeUserBean);
		return mv;
	}

	/**
	 * 
	 * Title: 删除订单<br/>
	 * Description:
	 * 
	 * @param applyId
	 */
	@RequestMapping(value = "delOrder")
	@ControlAspect(funtionCd = "i2_sale_team_apply", operType = OperType.DELETE, havPrivs=true)
	@ControllerException
	public void delOrder(String applyId) {
		teamOrderService.delOrder(applyId);
	}

	/**
	 * 
	 * Title: 提交订单<br/>
	 * Description:
	 * 
	 * @param applyId
	 * @throws Exception
	 */
	@RequestMapping(value = "commitOrder")
	@ControlAspect(funtionCd = "i2_sale_team_apply", operType = OperType.UPDATE, havPrivs=true)
	@ControllerException
	public void commitOrder(String applyId) throws Exception {
		teamOrderService.commitOrder(applyId);
	}

	/**
	 * 
	 * Title: 修改换票人之前获取换票人信息<br/>
	 * Description:
	 * 
	 * @param applyId
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping(value = "befUpdChangeUser")
	@ControlAspect(funtionCd = "查询明细", operType = OperType.QUERY)
	@ControllerException
	public ModelAndView befUpdChangeUser(String applyId) throws ServiceException {
		SlTeamOrder slTeamOrder = teamOrderService.getSlTeamOrder(applyId);
		ModelAndView mv = new ModelAndView("pages/sale/updChangeUser");
		mv.addObject("slTeamOrder", slTeamOrder);
		return mv;
	}

	/**
	 * 
	 * Title: 更新换票人信息<br/>
	 * Description:
	 * 
	 * @param teamOrderBean
	 * @throws ServiceException
	 */
	@RequestMapping(value = "updChangeUser")
	@ControlAspect(funtionCd = "更新换票人信息", operType = OperType.UPDATE)
	@ControllerException
	public void updChangeUser(TeamOrderBean teamOrderBean) throws ServiceException {
		teamOrderService.updChangeUser(teamOrderBean);
	}

	/**
	 * 
	 * Title: 订单取消<br/>
	 * Description:
	 * 
	 * @param strDeliveryApply
	 * @param applyDetailStr
	 * @throws Exception
	 */
	@RequestMapping(value = "cancleOrder")
	@ControlAspect(funtionCd = "i2_sale_team_apply", operType = OperType.UPDATE, havPrivs=true)
	@ControllerException
	public void cancleOrder(SlTeamOrder slTeamOrder) throws Exception {
		teamOrderService.cancleOrder(getLoginUserBean(), slTeamOrder);
	}
}
