package com.tbims.service;

import java.util.Date;
import java.util.Map;

import com.zhming.support.bean.PageBean;
import com.zhming.support.bean.UserBean;
import com.zhming.support.exception.ServiceException;

/**
 * Title: 销售订单信息查询 <br/>
 * Description:
 * 
 * @ClassName: ISlOrderQueryService
 * @author ydc
 * @date 2017年9月19日 上午11:12:05
 * 
 */
public interface ISlOrderQueryService {
	/**
	 * Title: 查询未取票的订单销售信息 <br/>
	 * Description:
	 * 
	 * @param loginUserBean
	 * @return
	 */
	PageBean<Map<String, Object>> querySlOrderByNOEjectList(UserBean loginUserBean, Date saleTimeBegin, Date saleTimeEnd);

	/**
	 * Title:未取票的订单发起退款 <br/>
	 * Description:
	 * 
	 * @param loginUserBean
	 * @param orderId
	 * @throws ServiceException
	 */
	void refundTicketByNOEject(UserBean loginUserBean, String orderId, int refundFee) throws ServiceException;

	/**
	 * Title:查询未取票的订单退款退款 <br/>
	 * Description:
	 * 
	 * @param loginUserBean
	 * @param orderId
	 * @throws ServiceException
	 */
	Map<String, Object> queryRefundByNOEject(UserBean loginUserBean, String orderId, String ticketTypeId) throws ServiceException;
}
