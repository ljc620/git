package com.tbims.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.tbims.bean.ChangeUserBean;
import com.tbims.bean.TeamOrderBean;
import com.tbims.bean.TeamOrderDetailBean;
import com.tbims.entity.SlTeamOrder;
import com.tbims.service.ITeamExamService;
import com.zhming.support.BaseController;
import com.zhming.support.annontion.ControlAspect;
import com.zhming.support.annontion.ControllerException;
import com.zhming.support.bean.PageBean;
import com.zhming.support.bean.UserBean;
import com.zhming.support.common.OperType;

@RestController
@RequestMapping("/teamExam/")
public class TeamExamController extends BaseController {
	@Autowired
	private ITeamExamService teamExamService;
	/**
	 * 
	 * Title: 查询配送申请<br/>
	 * Description:
	 * @param strDeliveryApply
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "listApply")
	@ControlAspect(funtionCd = "i2_sale_team_exam", operType = OperType.QUERY, havPrivs=true)
	@ControllerException
	public PageBean<TeamOrderBean> listApply(TeamOrderBean teamOrderBean) throws Exception {
		UserBean userBean = getLoginUserBean();
		return teamExamService.listApply(userBean, teamOrderBean, "C");
	}

	/**
	 * 
	 * Title: 获取总数量<br/>
	 * Description:
	 * @param teamOrderBean
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "getTotalNum")
	@ControlAspect(funtionCd = "查询总数量", operType = OperType.QUERY)
	@ControllerException
	public Long getTotalNum(TeamOrderBean teamOrderBean) throws Exception {
		TeamOrderBean teamOrderBeanNew = teamExamService.getTotalNum(teamOrderBean, "C");
		if (teamOrderBeanNew != null && teamOrderBeanNew.getTotalExamNum() != null) {
			return teamOrderBeanNew.getTotalExamNum();
		}
		else {
			return 0l;
		}
	}
	/**
	 * 
	 * Title: 获取总数量<br/>
	 * Description:
	 * @param teamOrderBean
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "getTotalNumHis")
	@ControlAspect(funtionCd = "查询总数量", operType = OperType.QUERY)
	@ControllerException
	public Long getTotalNumHis(TeamOrderBean teamOrderBean) throws Exception {
		TeamOrderBean teamOrderBeanNew = teamExamService.getTotalNum(teamOrderBean, "H");
		if (teamOrderBeanNew != null && teamOrderBeanNew.getTotalExamNum() != null) {
			return teamOrderBeanNew.getTotalExamNum();
		}
		else {
			return 0l;
		}
	}
	/**
	 * 
	 * Title: 查询配送申请<br/>
	 * Description:
	 * @param strDeliveryApply
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "listApplyHis")
	@ControlAspect(funtionCd = "查询配送申请", operType = OperType.QUERY)
	@ControllerException
	public PageBean<TeamOrderBean> listApplyHis(TeamOrderBean teamOrderBean) throws Exception {
		UserBean userBean = getLoginUserBean();
		return teamExamService.listApply(userBean, teamOrderBean, "H");
	}
	/**
	 * 
	 * Title: 查询明细<br/>
	 * Description:
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
			mv = new ModelAndView("pages/sale/teamexam/orderDetail");
		}
		else if ("p".equals(flag)) {
			mv = new ModelAndView("pages/sale/teamexam/orderPrint");
		}
		SlTeamOrder slTeamOrder = teamExamService.getSlTeamOrder(applyId);
		mv.addObject("slTeamOrder", slTeamOrder);
		String changeUserId = slTeamOrder.getChangeUserId();
		ChangeUserBean changeUserBean = teamExamService.getChangeUser(changeUserId);
		mv.addObject("slTeamOrder", slTeamOrder);
		mv.addObject("changeUserBean", changeUserBean);
		return mv;
	}
	/**
	 * 
	 * Title: 审核前查询<br/>
	 * Description:
	 * @param applyId
	 * @param flag(0:审核1：配送确认)
	 * @return
	 */
	@RequestMapping(value = "beforeDExam")
	@ControlAspect(funtionCd = "审核前查询", operType = OperType.QUERY)
	@ControllerException
	public ModelAndView beforeDExam(String applyId, String flag) {
		ModelAndView mv = new ModelAndView("pages/sale/teamexam/teamexam");
		SlTeamOrder slTeamOrder = teamExamService.getSlTeamOrder(applyId);
		mv.addObject("slTeamOrder", slTeamOrder);
		String changeUserId = slTeamOrder.getChangeUserId();
		ChangeUserBean changeUserBean = teamExamService.getChangeUser(changeUserId);
		mv.addObject("slTeamOrder", slTeamOrder);
		mv.addObject("changeUserBean", changeUserBean);
		return mv;
	}

	/**
	 * 
	 * Title: 审核明细查询<br/>
	 * Description:
	 * @param applyId
	 * @return
	 */
	@RequestMapping(value = "applyDetail")
	@ControlAspect(funtionCd = "审核明细查询", operType = OperType.QUERY)
	@ControllerException
	public List<TeamOrderDetailBean> applyDetail(String applyId) {
		return teamExamService.applyDetail(applyId);
	}

	/**
	 * 
	 * Title: 订单审核<br/>
	 * Description:
	 * @param strDeliveryApply
	 * @param applyDetailStr
	 * @throws Exception
	 */
	@RequestMapping(value = "examOrder")
	@ControlAspect(funtionCd = "i2_sale_team_exam", operType = OperType.UPDATE,havPrivs=true)
	@ControllerException
	public void examOrder(SlTeamOrder slTeamOrder, String applyDetailStr) throws Exception {
		List<TeamOrderDetailBean> applyDetailList = JSON.parseArray(applyDetailStr,
				TeamOrderDetailBean.class);
		teamExamService.examOrder(getLoginUserBean(), slTeamOrder, applyDetailList);
	}

	/**
	 * 
	 * Title: 订单取消<br/>
	 * Description:
	 * @param strDeliveryApply
	 * @param applyDetailStr
	 * @throws Exception
	 */
	@RequestMapping(value = "cancleOrder")
	@ControlAspect(funtionCd = "i2_sale_team_exam", operType = OperType.UPDATE,havPrivs=true)
	@ControllerException
	public void cancleOrder(SlTeamOrder slTeamOrder) throws Exception {
		teamExamService.cancleOrder(getLoginUserBean(), slTeamOrder);
	}

}
