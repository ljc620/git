package com.tbims.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tbims.bean.RepairSaleOrderBean;
import com.tbims.entity.SlBill;
import com.tbims.entity.SlBillDetail;
import com.tbims.entity.SysClient;
import com.tbims.service.IBillMngService;
import com.tbims.service.IClientMngService;
import com.zhming.support.BaseController;
import com.zhming.support.annontion.ControlAspect;
import com.zhming.support.annontion.ControllerException;
import com.zhming.support.bean.PageBean;
import com.zhming.support.common.OperType;
import com.zhming.support.exception.BaseException;
import com.zhming.support.exception.ServiceException;

/**
 * Title: 对账管理 <br/>
 * Description:
 * 
 * @ClassName: SlBillMngController
 * @author ydc
 * @date 2017年8月28日 上午9:27:54
 * 
 */
@RestController
@RequestMapping("/bill/")
public class SlBillMngController extends BaseController {
	@Autowired
	private IBillMngService billMngService;
	@Autowired
	private IClientMngService clientMngService;
	
	/**
	 * Title:查询对账列表 <br/>
	 * Description:
	 * 
	 * @param billDateStart
	 * @param billDateEnd
	 * @param stat
	 * @return
	 */
	@RequestMapping(value = "listBillInfo")
	@ControlAspect(funtionCd = "i2_bill_list", operType = OperType.QUERY, havPrivs = true)
	@ControllerException
	public PageBean<SlBill> listBillInfo(String billDateStart, String billDateEnd, String stat) {
		return billMngService.listBillInfo(getLoginUserBean(), billDateStart, billDateEnd, stat);
	}

	/**
	 * Title:查询对账单明细 <br/>
	 * Description:
	 * 
	 * @param billId
	 * @param tranStatus
	 * @param billResult
	 * @return
	 */
	@RequestMapping(value = "listBillDetail")
	@ControlAspect(funtionCd = "i2_bill_list", operType = OperType.QUERY, havPrivs = true)
	@ControllerException
	public PageBean<SlBillDetail> listBillDetail(String billId, String tranStatus, String billResult) {
		return billMngService.listBillDetail(getLoginUserBean(), billId, tranStatus, billResult);
	}

	/**
	 * Title: 重新对账 <br/>
	 * Description:
	 * 
	 * @param billId
	 * @throws ServiceException
	 */
	@RequestMapping(value = "redoBill")
	@ControlAspect(funtionCd = "i2_bill_list", operType = OperType.QUERY, havPrivs = true)
	@ControllerException
	public void redoBill(String billId) throws ServiceException {
		billMngService.redoBill(getLoginUserBean(), billId);
	}

	/**
	 * Title:发起退款 <br/>
	 * Description:
	 * 
	 * @param billDetailId
	 * @throws ServiceException
	 */
	@RequestMapping(value = "refundPay")
	@ControlAspect(funtionCd = "i2_bill_list", operType = OperType.QUERY, havPrivs = true)
	@ControllerException
	public void refundPay(String billDetailId) throws ServiceException {
		billMngService.refundPay(getLoginUserBean(), billDetailId);
	}

	/**
	 * Title:人工补录订单 <br/>
	 * Description:
	 * 
	 * @param billDetailId
	 * @throws Exception 
	 */
	@RequestMapping(value = "repairSaleOrder")
	@ControlAspect(funtionCd = "i2_repair_sale_order", operType = OperType.QUERY, havPrivs = true)
	@ControllerException
	public void repairSaleOrder(RepairSaleOrderBean repairSaleOrderBean) throws Exception {
		billMngService.repairSaleOrder(getLoginUserBean(), repairSaleOrderBean);
	}
	
	/**
	 * Title:人工补录订单-身份证 <br/>
	 * Description:
	 * 
	 * @param billDetailId
	 * @throws Exception 
	 */
	@RequestMapping(value = "repairSaleOrderByIdenttyId")
	@ControlAspect(funtionCd = "i2_repair_sale_order_identtyid", operType = OperType.QUERY, havPrivs = true)
	@ControllerException
	public void repairSaleOrderByIdenttyId(RepairSaleOrderBean repairSaleOrderBean) throws Exception {
		billMngService.repairSaleOrderByIdenttyId(getLoginUserBean(), repairSaleOrderBean);
	}

	/**
	 * Title:验证票号 <br/>
	 * Description:
	 * 
	 * @param ticketIds
	 * @throws ServiceException
	 */
	@RequestMapping(value = "validateTicketIds")
	@ControlAspect(funtionCd = "验证票号", operType = OperType.QUERY)
	@ControllerException
	public void validateTicketIds(long ticketCount, String ticketIds) throws ServiceException {
		billMngService.validateTicketIds(getLoginUserBean(), ticketCount, ticketIds);
	}
	
	/**
	 * 
	 * Title: 查询终端列表不分页<br/>
	 * Description:
	 * @param sysClient
	 * @return
	 * @throws BaseException
	 */
	@RequestMapping(value = "listClient")
	@ControlAspect(funtionCd = "查询终端列表不分页", operType = OperType.QUERY)
	@ControllerException
	public List<Map<String, Object>> listClient(SysClient sysClient) throws BaseException {
		getLoginUserBean().setPageSize(0);//不分页
		PageBean<Map<String, Object>> clientList = clientMngService.listClient(getLoginUserBean(), sysClient);
		
		return clientList.getRows();
	}
}
