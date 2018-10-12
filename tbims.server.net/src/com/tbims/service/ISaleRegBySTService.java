package com.tbims.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.tbims.entity.SlSaleReg;
import com.tbims.entity.SysTicketType;
import com.zhming.support.bean.PageBean;
import com.zhming.support.bean.UserBean;
import com.zhming.support.exception.DBException;
import com.zhming.support.exception.ServiceException;

/**
 * Title: 实体代理商销售登记 <br/>
 * Description:
 * 
 * @ClassName: ISaleRegByST
 * @author ydc
 * @date 2017年9月11日 下午3:01:59
 * 
 */
public interface ISaleRegBySTService {

	public PageBean<Map<String, Object>> listSaleReg(UserBean userBean, Date startDt, Date endDt, String ticketTypeId);

	/**
	 * Title:实体代理商销售登记 <br/>
	 * Description:
	 * 
	 * @param slSaleReg
	 * @throws ServiceException
	 * @throws DBException 
	 */
	public void saveSaleReg(UserBean userBean, SlSaleReg slSaleReg) throws ServiceException, DBException;

	/**
	 * Title:更新代理商销售登记 <br/>
	 * Description:
	 * 
	 * @param slSaleReg
	 * @throws ServiceException
	 * @throws DBException 
	 */
	public void updateSaleReg(UserBean userBean, SlSaleReg slSaleReg) throws ServiceException, DBException;

	/**
	 * Title:查询销售记录 <br/>
	 * Description:
	 * 
	 * @param saleRegId
	 * @return
	 */
	public SlSaleReg getSlSaleRegById(String saleRegId);

	/**
	 * 
	 * Title: 查询票种<br/>
	 * Description:
	 * 
	 * @return
	 */
	public List<SysTicketType> listTicketTypeByST(UserBean userBean);
}
