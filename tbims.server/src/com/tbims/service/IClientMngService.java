package com.tbims.service;

import java.util.Map;

import com.tbims.entity.SysClient;
import com.zhming.support.bean.PageBean;
import com.zhming.support.bean.UserBean;
import com.zhming.support.exception.BaseException;
import com.zhming.support.exception.DBException;

/**
 * 
* Title:客户端管理使用接口 <br/>
* Description: 
* @ClassName: IClientMngService
* @author syq
* @date 2017年7月3日 下午2:09:38
*
 */
public interface IClientMngService {

	/**
	 * 
	* Title: 查询所有的客户端列表<br/>
	* Description: 
	* @param loginUserBean
	* @param sysClient
	* @return
	 */
	public PageBean<Map<String, Object>> listClient(UserBean loginUserBean, SysClient sysClient) throws DBException;

	/**
	 * 
	* Title: 根据客户端ID获取客户端信息<br/>
	* Description: 
	* @param clientId
	* @return
	* @throws DBException
	 */
	public SysClient getClientById(Long clientId) throws DBException;

	/**
	 * 
	* Title: 修改客户端信息<br/>
	* Description: 
	* @param loginUserBean
	* @param sysClient
	* @throws DBException
	 */
	public void updateClient(UserBean loginUserBean, SysClient sysClient) throws BaseException;

	/**
	 * 
	* Title: 删除客户端<br/>
	* Description: 
	* @param clientId
	* @throws DBException
	 */
	public void delClient(Long clientId) throws DBException;

	/**
	 * 
	* Title: 新增客户端<br/>
	* Description: 
	* @param loginUserBean
	* @param sysClient
	* @throws DBException
	 */
	public void addClient(UserBean loginUserBean, SysClient sysClient) throws BaseException;

	/**
	 * 
	* Title: 修改状态<br/>
	* Description: 
	* @param loginUserBean
	* @param clientIds
	* @param stat
	* @throws DBException
	 */
	public void updateStat(UserBean loginUserBean, String clientIds, String stat) throws DBException;

	/**
	 * 
	* Title:重置授权码 <br/>
	* Description: 
	* @param loginUserBean
	* @param clientIds
	* @throws DBException
	 */
	public void updateToken(UserBean loginUserBean, String clientIds) throws DBException;

}
