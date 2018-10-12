package com.tbims.controller;

import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tbims.service.ISlOrderQueryService;
import com.zhming.support.BaseController;
import com.zhming.support.annontion.ControlAspect;
import com.zhming.support.annontion.ControllerException;
import com.zhming.support.bean.PageBean;
import com.zhming.support.common.OperType;
import com.zhming.support.exception.BaseException;

/**
 * Title:销售订单信息 <br/>
 * Description:
 * 
 * @ClassName: SlOrderQueryController
 * @author ydc
 * @date 2017年9月19日 下午2:57:29
 * 
 */
@RestController
@RequestMapping("/slOrderQuery")
public class SlOrderQueryController extends BaseController {
	@Autowired
	private ISlOrderQueryService slOrderQueryService;

	/**
	 * 
	 * Title:查询未取票的销售订单信息 <br/>
	 * Description:
	 * 
	 * @throws BaseException
	 */
	@RequestMapping(value = "querySlOrderByNOEjectList")
	@ControlAspect(funtionCd = "i2_order_query_eject", operType = OperType.QUERY, havPrivs=true)
	@ControllerException
	public PageBean<Map<String, Object>> querySlOrderByNOEjectList(Date saleTimeBegin, Date saleTimeEnd) throws BaseException {
		return slOrderQueryService.querySlOrderByNOEjectList(getLoginUserBean(), saleTimeBegin, saleTimeEnd);
	}

	@RequestMapping(value = "refundTicketByNOEject")
	@ControlAspect(funtionCd = "i2_order_query_eject", operType = OperType.ADD, havPrivs=true)
	@ControllerException
	public void refundTicketByNOEject(String orderId, int refundFee) throws BaseException {
		slOrderQueryService.refundTicketByNOEject(getLoginUserBean(), orderId, refundFee);
	}

	@RequestMapping(value = "queryRefundByNOEject")
	@ControlAspect(funtionCd = "退款信息查询", operType = OperType.ADD)
	@ControllerException
	public Map<String, Object> queryRefundByNOEject(String orderId, String ticketTypeId) throws BaseException {
		return slOrderQueryService.queryRefundByNOEject(getLoginUserBean(), orderId, ticketTypeId);
	}
}
