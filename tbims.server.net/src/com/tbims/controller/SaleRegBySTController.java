package com.tbims.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.tbims.entity.SlSaleReg;
import com.tbims.entity.SysTicketType;
import com.tbims.service.ISaleRegBySTService;
import com.zhming.support.BaseController;
import com.zhming.support.annontion.ControlAspect;
import com.zhming.support.annontion.ControllerException;
import com.zhming.support.bean.PageBean;
import com.zhming.support.common.OperType;
import com.zhming.support.exception.BaseException;
import com.zhming.support.exception.DBException;
import com.zhming.support.exception.ServiceException;

/**
 * Title: 实体代理商销售记录登记 <br/>
 * Description:
 * 
 * @ClassName: SaleRegBySTController
 * @author ydc
 * @date 2017年9月11日 下午4:25:22
 * 
 */
@RestController
@RequestMapping("/saleReg/")
public class SaleRegBySTController extends BaseController {

	@Autowired
	ISaleRegBySTService saleRegBySTService;

	/**
	 * Title:查询销售记录 <br/>
	 * Description:
	 * 
	 * @param startDt
	 * @param endDt
	 * @param ticketTypeId
	 * @return
	 */
	@RequestMapping(value = "listSaleReg")
	@ControlAspect(funtionCd = "i2_sale_reg_mng", havPrivs=true)
	@ControllerException
	public PageBean<Map<String, Object>> listSaleReg(Date startDt, Date endDt, String ticketTypeId) {
		return saleRegBySTService.listSaleReg(getLoginUserBean(), startDt, endDt, ticketTypeId);
	}

	/**
	 * Title:实体代理商销售记录登记 <br/>
	 * Description:
	 * 
	 * @param slSaleReg
	 * @throws ServiceException
	 * @throws DBException 
	 */
	@RequestMapping(value = "saveSaleReg")
	@ControlAspect(funtionCd = "i2_sale_reg_mng", operType = OperType.ADD, havPrivs=true)
	@ControllerException
	public void saveSaleReg(SlSaleReg slSaleReg) throws ServiceException, DBException {
		saleRegBySTService.saveSaleReg(getLoginUserBean(), slSaleReg);
	}

	/**
	 * Title:更新销售记录 <br/>
	 * Description:
	 * 
	 * @param slSaleReg
	 * @throws ServiceException
	 * @throws DBException 
	 */
	@RequestMapping(value = "updateSaleReg")
	@ControlAspect(funtionCd = "i2_sale_reg_mng", operType = OperType.UPDATE, havPrivs=true)
	@ControllerException
	public void updateSaleReg(SlSaleReg slSaleReg) throws ServiceException, DBException {
		saleRegBySTService.updateSaleReg(getLoginUserBean(), slSaleReg);
	}

	/**
	 * 
	 * Title: 查询销售记录<br/>
	 * Description:
	 * 
	 * @param sysUserQBean
	 * @return
	 */
	@RequestMapping(value = "getSlSaleRegById")
	@ControlAspect(funtionCd = "查询销售记录")
	@ControllerException(type = "page")
	public ModelAndView getSlSaleRegById(String saleRegId) throws BaseException {
		ModelAndView mv = new ModelAndView("/pages/saleReg/updateSaleReg");
		SlSaleReg slSaleReg = saleRegBySTService.getSlSaleRegById(saleRegId);
		mv.addObject("slSaleReg", slSaleReg);
		return mv;
	}

	/**
	 * Title:查询票种 <br/>
	 * Description:
	 */
	@RequestMapping(value = "listTicketTypeByST")
	@ControlAspect(funtionCd = "查询票种")
	@ControllerException
	public List<SysTicketType> listTicketTypeByST() {
		return saleRegBySTService.listTicketTypeByST(getLoginUserBean());
	}
}
