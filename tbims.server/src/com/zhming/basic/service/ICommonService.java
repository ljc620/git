package com.zhming.basic.service;

import java.util.List;
import java.util.Map;

import com.tbims.bean.RoleBean;
import com.tbims.entity.SlOrg;
import com.tbims.entity.SysClient;
import com.tbims.entity.SysOutlet;
import com.tbims.entity.SysRegion;
import com.zhming.support.bean.UserBean;
import com.zhming.support.bean.ZTreeJson;
import com.zhming.support.exception.BaseException;
import com.zhming.support.exception.DBException;

public interface ICommonService {
	/**
	 * Title: 查询指定字段的所有项目的列表 <br/>
	 * Description:
	 * 
	 * @param navigation
	 * @param key
	 * @return
	 * @throws BFException
	 */
	public List<Map<String, Object>> listItemsByKey(String key) throws DBException;

	/**
	 * 
	 * Title: 网点管理专用<br/>
	 * Description:
	 * 
	 * @param key
	 * @return
	 * @throws DBException
	 */
	public List<Map<String, Object>> listItemsByKeyOutletMng(String key) throws DBException;

	/**
	 * 
	 * Title: 查询系统角色供其他角色名称参数调用<br/>
	 * Description:
	 * 
	 * @param navigation
	 * @return
	 * @throws DBException
	 */
	public List<RoleBean> listRole(String userId) throws DBException;

	/**
	 * 
	 * Title: 查询网点列表<br/>
	 * Description:
	 * 
	 * @param navigation
	 * @return
	 * @throws DBException
	 */
	public List<SysOutlet> listOutlet() throws DBException;

	/**
	 * 
	 * Title: 查询网点列表<br/>
	 * Description:
	 * 
	 * @param navigation
	 * @return
	 * @throws DBException
	 */
	public List<SysOutlet> listSaleOutlet() throws DBException;

	/**
	 * 
	 * Title: 查询区域列表<br/>
	 * Description:
	 * 
	 * @return
	 * @throws DBException
	 */
	public List<SysRegion> listRegion() throws DBException;

	/**
	 * 
	 * Title: 根据网点类型查询网点<br/>
	 * Description:
	 * 
	 * @param outletType
	 * @return
	 * @throws DBException
	 */
	public List<SysOutlet> listOutletByType(String outletType) throws DBException;

	/**
	 * 
	 * Title: 根据客户端类型获取客户端列表<br/>
	 * Description:
	 * 
	 * @param clientType
	 * @return
	 * @throws DBException
	 */
	public List<SysClient> listClientByType(String clientType) throws DBException;

	/**
	 * 客流统计使用多选下拉框：场馆下拉多选框 Title: <br/>
	 * Description:
	 * 
	 * @return
	 * @throws DBException
	 */
	public List<ZTreeJson> initVenueJson(String venueId) throws DBException;

	/**
	 * 
	 * Title: 客流统计使用多选下拉框：区域下拉多选框<br/>
	 * Description:
	 * 
	 * @return
	 * @throws BaseException
	 */
	public List<ZTreeJson> initRegionJson(String regionId) throws DBException;

	/**
	 * 
	 * Title: 客流统计使用多选下拉框：票种下拉多选框 <br/>
	 * Description:
	 * 
	 * @return
	 * @throws BaseException
	 */
	public List<ZTreeJson> initTicketTypeJson(String ticketTypeId) throws DBException;

	/**
	 * 
	 * Title: 员工列表<br/>
	 * Description:
	 * 
	 * @return
	 * @throws BaseException
	 */
	public List<ZTreeJson> empList(String empId) throws BaseException;

	/**
	 * 网点树
	 * 
	 * @return
	 * @throws DBException
	 */
	public List<Map<String, Object>> listOutletTree() throws DBException;

	/**
	 * 网点树根据用户岗位
	 * @param userBean
	 * @return
	 * @throws DBException
	 */
	public List<Map<String, Object>> listSaleOutletByUser(UserBean userBean) throws DBException;

	/**
	 * 查询 机构 不含APP
	 * @param userBean
	 * @return
	 * @throws DBException
	 */
	public List<SlOrg> listOrg(String orgType) throws DBException;
	
	
	/**
	 * 查询机构所有
	 * @param orgType
	 * @return
	 * @throws DBException
	 */
	public List<SlOrg> listOrgAll(String orgType) throws DBException;
	
}
