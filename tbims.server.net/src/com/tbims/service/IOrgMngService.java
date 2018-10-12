package com.tbims.service;

import java.util.List;
import java.util.Map;

import com.tbims.entity.SlAdvance;
import com.tbims.entity.SlLimit;
import com.tbims.entity.SlOrg;
import com.tbims.entity.SysTicketType;
import com.zhming.support.bean.PageBean;
import com.zhming.support.bean.UserBean;
import com.zhming.support.exception.DBException;
import com.zhming.support.exception.ServiceException;

/**
* Title: 机构管理  <br/>
* Description: 
* @ClassName: IOrgMngService
* @author ydc
* @date 2017年7月26日 上午10:37:13
* 
*/
public interface IOrgMngService {
	/**
	 * 
	 * Title: 查询签约社列表<br/>
	 * Description:
	 * 
	 * @param userBean
	 * @param slOrg
	 * @return
	 */
	public PageBean<Map<String, Object>> listOrg(UserBean userBean, SlOrg slOrg, String orgBtype);

	/**
	 * 
	 * Title: 查询机构列表<br/>
	 * Description:
	 * 
	 * @param userBean
	 * @param slOrg
	 * @return
	 * @throws DBException
	 */
	public Map<String,SlOrg> listOrg(String orgBtype) throws DBException;

	
	/**
	 * 
	 * Title: 根据机构编号查询机构<br/>
	 * Description:
	 * 
	 * @param orgId
	 * @return
	 */
	public SlOrg getOrgById(String orgId);

	/**
	 * 
	 * Title: 查询额度余额列表<br/>
	 * Description:
	 * 
	 * @param userBean
	 * @param orgId
	 * @return
	 */
	public PageBean<Map<String, Object>> listAmtLimit(UserBean userBean, String orgId);

	/**
	 * 
	 * Title: 查询额度列表<br/>
	 * Description:
	 * 
	 * @param orgId
	 * @return
	 */
	public PageBean<SlLimit> listLimit(UserBean userBean, String orgId, String ticketTypeId);

	/**
	 * 
	 * Title: 查询票种下拉列表<br/>
	 * Description:
	 * 
	 * @return
	 */
	public List<SysTicketType> ticketTypeList(String flag);

	/**
	 * 
	 * Title: 添加额度<br/>
	 * Description:
	 * 
	 * @param userBean
	 * @param slLimit
	 * @throws ServiceException
	 */
	public void addLimit(UserBean userBean, SlLimit slLimit) throws ServiceException;

	/**
	 * 
	 * Title: 根据组织机构代码获取机构信息<br/>
	 * Description:
	 * 
	 * @param orgId
	 * @return
	 */
	public SlOrg getOrg(String orgId);

	/**
	 * 
	 * Title: 查询预付款列表<br/>
	 * Description:
	 * 
	 * @param userBean
	 * @param orgId
	 * @return
	 */
	public PageBean<SlAdvance> listAdvance(UserBean userBean, String orgId);

}
