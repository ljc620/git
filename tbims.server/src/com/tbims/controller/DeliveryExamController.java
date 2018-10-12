package com.tbims.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.tbims.bean.ChestBean;
import com.tbims.bean.DeliveryApplyBean;
import com.tbims.bean.DeliveryApplyDetailBean;
import com.tbims.entity.StrDeliveryApply;
import com.tbims.service.IDeliveryExamService;
import com.zhming.support.BaseController;
import com.zhming.support.annontion.ControlAspect;
import com.zhming.support.annontion.ControllerException;
import com.zhming.support.bean.PageBean;
import com.zhming.support.common.OperType;
import com.zhming.support.exception.BaseException;

/**
 * 
 * Title:配送管理<br/>
 * Description:
 * @ClassName: DeliveryExamController
 * @author ly
 * @date 2017年6月23日 下午5:50:25
 *
 */
@RestController
@RequestMapping("/deliveryExam/")
public class DeliveryExamController extends BaseController {
	@Autowired
	private IDeliveryExamService deliveryExamService;

	/**
	 * 
	 * Title: 查询配送申请（待审核）<br/>
	 * Description:
	 * @param strDeliveryApply
	 * @return
	 */
	@RequestMapping(value = "listDeliveryApply")
	@ControlAspect(funtionCd = "i2_delivery_exam", operType = OperType.QUERY, havPrivs=true)
	@ControllerException
	public PageBean<DeliveryApplyBean> listDeliveryApply(StrDeliveryApply strDeliveryApply) {
		return deliveryExamService.listDeliveryApply(getLoginUserBean(), strDeliveryApply);
	}
	/**
	 * 
	 * Title: 查询配送申请（已审核）<br/>
	 * Description:
	 * @param strDeliveryApply
	 * @return
	 */
	@RequestMapping(value = "listDeliveryApplyForOut")
	@ControlAspect(funtionCd = "i2_delivery_outstore", operType = OperType.QUERY, havPrivs=true)
	@ControllerException
	public PageBean<DeliveryApplyBean> listDeliveryApplyForOut(StrDeliveryApply strDeliveryApply) {
		return deliveryExamService.listDeliveryApply(getLoginUserBean(), strDeliveryApply);
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
		ModelAndView mv = null;
		if ("0".equals(flag)) {
			mv = new ModelAndView("pages/delivery/deliveryExam");
		}
		else {
			mv = new ModelAndView("pages/delivery/deliveryConfirm");
		}
		DeliveryApplyBean deliveryApplyBean = deliveryExamService.getDeliveryApply(applyId);
		mv.addObject("deliveryApplyBean", deliveryApplyBean);
		return mv;
	}

	/**
	 * 
	 * Title: 配送详情<br/>
	 * Description:
	 * @param applyId
	 * @return
	 */
	@RequestMapping(value = "beforeDelivery")
	@ControlAspect(funtionCd = "配送详情查询", operType = OperType.QUERY)
	@ControllerException
	public ModelAndView beforeDelivery(String applyId) {
		ModelAndView mv = new ModelAndView("pages/delivery/deliveryDetail");
		DeliveryApplyBean deliveryApplyBean = deliveryExamService.getDeliveryApply(applyId);
		mv.addObject("deliveryApplyBean", deliveryApplyBean);
		List<Map<String, Object>> detailList = deliveryExamService.listDeliveryDetail(applyId);
		mv.addObject("detailList", detailList);
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
	public List<DeliveryApplyDetailBean> applyDetail(String applyId) {
		return deliveryExamService.applyDetail(applyId);
	}

	/**
	 * 
	 * Title: 配送审核<br/>
	 * Description:
	 * @param strDeliveryApply
	 * @param applyDetailStr
	 * @throws Exception
	 */
	@RequestMapping(value = "examDelivery")
	@ControlAspect(funtionCd = "i2_delivery_exam", operType = OperType.UPDATE, havPrivs=true)
	@ControllerException
	public void examDelivery(StrDeliveryApply strDeliveryApply, String applyDetailStr) throws Exception {
		List<DeliveryApplyDetailBean> applyDetailList = JSON.parseArray(applyDetailStr,
				DeliveryApplyDetailBean.class);
		deliveryExamService.examDelivery(getLoginUserBean(), strDeliveryApply, applyDetailList);
	}

	/**
	 * 
	 * Title: 查询箱列表<br/>
	 * Description:
	 * @param chestId
	 * @param beginNo
	 * @param endNo
	 * @return
	 */
	@RequestMapping(value = "listChest")
	@ControlAspect(funtionCd = "查询箱列表", operType = OperType.QUERY)
	@ControllerException
	public List<Map<String, Object>> listChest(String chestId, String boxId, Long beginNo, Long endNo) throws BaseException {
		return deliveryExamService.listChest(chestId, boxId, beginNo, endNo);
	}

	/**
	 * 
	 * Title:  门票配送确认<br/>
	 * Description:
	 * @param applyId
	 * @param detailStr
	 * @throws Exception
	 */
	@RequestMapping(value = "confirmDelivery")
	@ControlAspect(funtionCd = "i2_delivery_outstore", operType = OperType.UPDATE, havPrivs=true)
	@ControllerException
	public void confirmDelivery(String applyId, String detailStr) throws Exception {
		List<ChestBean> chestList = JSON.parseArray(detailStr,
				ChestBean.class);
		deliveryExamService.confirmDelivery(getLoginUserBean(), applyId, chestList);
	}
	
	/**
	 * 
	* Title: 获取箱号或盒号内票的起止号<br/>
	* Description: 
	* @param chestId
	* @param boxId
	* @return
	* @throws Exception
	 */
	@RequestMapping(value = "getTicketNo")
	@ControlAspect(funtionCd = "获取箱号或盒号内票的起止号", operType = OperType.QUERY)
	@ControllerException
	public List<Map<String, Object>> getTicketNo(String chestId, String boxId) throws Exception {
		return deliveryExamService.getTicketNo(chestId, boxId);
	}

	/**
	 * 
	 * Title: 审核详情<br/>
	 * Description:
	 * @param applyId
	 * @return
	 */
	@RequestMapping(value = "examDetail")
	@ControlAspect(funtionCd = "配送详情查询", operType = OperType.QUERY)
	@ControllerException
	public ModelAndView examDetail(String applyId) {
		ModelAndView mv = new ModelAndView("pages/delivery/examDetail");
		DeliveryApplyBean deliveryApplyBean = deliveryExamService.getDeliveryApply(applyId);
		mv.addObject("deliveryApplyBean", deliveryApplyBean);
		List<DeliveryApplyDetailBean> examList = deliveryExamService.applyDetail(applyId);
		mv.addObject("examList", examList);
		return mv;
	}
}
