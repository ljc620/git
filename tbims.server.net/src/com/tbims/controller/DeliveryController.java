package com.tbims.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONArray;
import com.tbims.bean.ApplyDetailBean;
import com.tbims.bean.DeliveryApplyBean;
import com.tbims.bean.DeliveryApplyDetailBean;
import com.tbims.entity.StrDeliveryApply;
import com.tbims.service.IDeliveryService;
import com.zhming.support.BaseController;
import com.zhming.support.annontion.ControlAspect;
import com.zhming.support.annontion.ControllerException;
import com.zhming.support.bean.PageBean;
import com.zhming.support.common.OperType;
import com.zhming.support.exception.BaseException;
import com.zhming.support.util.DateUtil;

/**
 * 
* Title:  门票配送申请、确认接收（ 实体代理商操作） <br/>
* Description: 
* @ClassName: DeliveryController
* @author syq
* @date 2017年9月11日 下午2:34:44
*
 */
@RestController
@RequestMapping("/delivery/")
public class DeliveryController extends BaseController {
	@Autowired
	private IDeliveryService deliveryService;

	/**
	 * 
	* Title: 查询配送申请列表<br/>
	* Description: 
	* @param strDeliveryApply
	* @return
	 */
	@RequestMapping(value = "listDeliveryApply")
	@ControlAspect(funtionCd = "i2_ticket_delivery", operType = OperType.QUERY, havPrivs=true)
	@ControllerException
	public PageBean<DeliveryApplyBean> listDeliveryApply(StrDeliveryApply strDeliveryApply) throws BaseException{
		return deliveryService.listDeliveryApply(getLoginUserBean(), strDeliveryApply);
	}
	/**
	 * 
	* Title: 配送详情查询-配送申请审核详情<br/>
	* Description: 
	* @param applyId
	* @return
	 */
	@RequestMapping(value = "applyExamDetail")
	@ControlAspect(funtionCd = "配送申请审核详情", operType = OperType.QUERY)
	@ControllerException
	public List<DeliveryApplyDetailBean> applyExamDetail(String applyId) throws BaseException{
		List<DeliveryApplyDetailBean> deliveryApplyBean = deliveryService.applyExamDetail(applyId);
		return deliveryApplyBean;
	}
	/**
	 * 
	* Title: 配送详情查询-配送情况<br/>
	* Description: 
	* @param applyId
	* @return
	* @throws BaseException
	 */
	@RequestMapping(value = "deliveryDetail")
	@ControlAspect(funtionCd = "配送详情查询", operType = OperType.QUERY)
	@ControllerException
	public List<Map<String, Object>> deliveryDetail(String applyId) throws BaseException{
		List<Map<String, Object>> detailList = deliveryService.deliveryDetail(applyId);
		return detailList;
	}
	/**
	 * 
	* Title: 门票配送-确认接收<br/>
	* Description: 
	* @param applyId
	* @param examStat
	* @return
	* @throws BaseException
	 */
	@RequestMapping(value = "confrimReceive")
	@ControlAspect(funtionCd = "i2_ticket_delivery", operType = OperType.UPDATE, havPrivs=true)
	@ControllerException
	public void confrimReceive(String applyId,String examStat) throws BaseException{
		deliveryService.confrimReceive(getLoginUserBean(),applyId,examStat);
	}
	/**
	 * 
	* Title: 门票申请<br/>
	* Description: 
	* @param applyId
	* @param detailStr
	* @throws BaseException
	 */
	@RequestMapping(value = "saveApply")
	@ControlAspect(funtionCd = "i2_ticket_delivery", operType = OperType.ADD, havPrivs=true)
	@ControllerException
	public void saveApply(String applyDeliveryTime,String detailStr) throws Exception{
		List<ApplyDetailBean> deliveryApply=JSONArray.parseArray(detailStr, ApplyDetailBean.class);
		deliveryService.saveApply(getLoginUserBean(),applyDeliveryTime,deliveryApply);
	}
	/**
	 * 
	* Title: 配送申请历史查询<br/>
	* Description: 
	* @param strDeliveryApply
	* @return
	* @throws BaseException
	 */
	@RequestMapping(value = "listDeliveryApplyH")
	@ControlAspect(funtionCd = "配送申请历史查询", operType = OperType.QUERY)
	@ControllerException
	public PageBean<DeliveryApplyBean> listDeliveryApplyH(String applyId,String examStat,String beginApplyTime,String endApplyTime) throws Exception{
		return deliveryService.listDeliveryApplyH(getLoginUserBean(), applyId, examStat, beginApplyTime, endApplyTime);
	}
	/**
	 * 
	* Title: 设置默认配送时间<br/>
	* Description: 
	* @return
	* @throws Exception
	 */
	@RequestMapping(value = "deliveryApply")
	@ControlAspect(funtionCd = "设置默认配送时间", operType = OperType.QUERY)
	@ControllerException
	public ModelAndView deliveryApply() throws Exception{
		ModelAndView mv=new ModelAndView("pages/delivery/deliveryApply");
		String date= DateUtil.addDay(DateUtil.getNowDate(), 1);
		String deliveryTime=date+" 08:00:00";
		mv.addObject("deliveryTime", deliveryTime);
		return mv;
	}
	/**
	 * 
	* Title: 校验申请票种数量是否满足参数倍数要求<br/>
	* Description: 
	* @return
	* @throws Exception
	 */
	@RequestMapping(value = "checkApplyNum")
	@ControlAspect(funtionCd = "校验申请票种数量是", operType = OperType.QUERY)
	@ControllerException
	public void checkApplyNum(Long applyNum) throws Exception{
		deliveryService.checkApplyNum(getLoginUserBean(),applyNum);
	}
}
