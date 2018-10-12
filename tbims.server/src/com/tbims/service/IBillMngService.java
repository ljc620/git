package com.tbims.service;

import com.tbims.bean.RepairSaleOrderBean;
import com.tbims.entity.SlBill;
import com.tbims.entity.SlBillDetail;
import com.zhming.support.bean.PageBean;
import com.zhming.support.bean.UserBean;
import com.zhming.support.exception.ServiceException;

/**
 * Title: 对账管理 <br/>
 * Description:
 * 
 * @ClassName: IBillMngService
 * @author ydc
 * @date 2017年8月28日 上午9:10:54
 * 
 */
public interface IBillMngService {

	/**
	 * Title:查询对账列表 <br/>
	 * Description:
	 * 
	 * @param billDate
	 * @return
	 */
	PageBean<SlBill> listBillInfo(UserBean userBean, String billDateStart, String billDateEnd, String stat);

	/**
	 * Title: 查询对账单明细 <br/>
	 * Description:
	 * 
	 * @param userBean
	 * @param billId
	 * @return
	 */
	PageBean<SlBillDetail> listBillDetail(UserBean userBean, String billId, String tranStatus, String billResult);

	/**
	 * Title:重新对账 <br/>
	 * Description:
	 * 
	 * @param userBean
	 * @param billId
	 * @throws ServiceException
	 */
	void redoBill(UserBean userBean, String billId) throws ServiceException;

	/**
	 * Title:发起退款 <br/>
	 * Description: 可以重复发送，退款单号不变不会重复退款
	 * 
	 * @param userBean
	 * @param billDetailId
	 * @throws ServiceException
	 */
	void refundPay(UserBean userBean, String billDetailId) throws ServiceException;

	/**
	 * 人工补录订单
	 * 
	 * @param userBean
	 * @param repairSaleOrderBean
	 * @throws ServiceException
	 * @throws Exception 
	 */
	void repairSaleOrder(UserBean userBean, RepairSaleOrderBean repairSaleOrderBean) throws ServiceException, Exception;

	/**
	 * 校验票号是否存在
	 * 
	 * @param userBean
	 * @param ticketIds
	 * @throws ServiceException 
	 */
	void validateTicketIds(UserBean userBean, long ticketCount, String ticketIds) throws ServiceException;
	
	/**
	 * 自营增加订单，按身份证
	 * @param userBean
	 * @param repairSaleOrderBean
	 * @throws Exception 
	 */
	void repairSaleOrderByIdenttyId(UserBean userBean, RepairSaleOrderBean repairSaleOrderBean) throws Exception;
}
