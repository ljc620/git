package com.tbims.service;

import java.util.Map;

import java.util.List;
import com.zhming.support.bean.UserBean;
import com.zhming.support.exception.DBException;

/**
 * 
* Title:  闸机、服务器及服务健康检查
* Description: 
* @ClassName: IHealthCheckService
* @author zhuxy
* @date 2017年11月17日 上午9:47:21
*
 */
public interface IHealthCheckService {

	/**
	* Title: 查询所有闸机的运行状态
	* Description: 
	* @param loginUserBean
	* @return
	*/
	public List<Map<String, Object>> listGateState(UserBean loginUserBean) throws DBException;
	
	/**
	* Title: 查询所有自助售票机的运行状态
	* Description: 
	* @param loginUserBean
	* @return
	*/
	public List<Map<String, Object>> listSlfServiceState(UserBean loginUserBean) throws DBException;
	
	/**
	* Title: 查询所有网络代理回调错误计数
	* Description: 
	* @param loginUserBean
	* @return
	*/
	public List<Map<String, Object>> listWLAgentCallbackError(UserBean loginUserBean) throws DBException;
	
	/**
	* Title: 查询所有需要检查状态的服务器及服务信息列表
	* Description: 
	* @param loginUserBean
	* @return
	*/
	public List<Map<String, Object>> listCheckServer(UserBean loginUserBean) throws DBException;
	
	/**
	* Title: 检查所有服务器及服务的状态
	* Description: 
	* @param loginUserBean
	* @return
	*/
	public List<Map<String, Object>> listServerAndServiceState(UserBean loginUserBean, List<Map<String, Object>> checkList) throws DBException;
	
	/**
	* Title: 重置所有网络代理回调错误计数
	* Description: 
	* @param loginUserBean
	* @return
	*/
	public void resetWLAgentCallbackError(UserBean loginUserBean) throws DBException;
	
	/**
	* Title: 查询所有网络代理待回调门票数
	* Description: 
	* @param loginUserBean
	* @return
	*/
	public List<Map<String, Object>> listWLAgentWaitCallbackTicket(UserBean loginUserBean) throws DBException;
	
	/**
	* Title: 根据机构ID查询待回调门票明细
	* Description: 
	* @param loginUserBean
	* @return
	*/
	public List<Map<String, Object>> listWaitCallbackTicketDetail(UserBean loginUserBean, String orgId) throws DBException;
	
	/**
	* Title: 跳过指定门票的机构回调
	* Description: 
	* @param loginUserBean
	*/
	public void skipTicketCallback(UserBean loginUserBean, String orderDetailId) throws DBException;
}
