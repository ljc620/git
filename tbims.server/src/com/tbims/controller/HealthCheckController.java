package com.tbims.controller;

import java.util.Map;
//import java.util.ArrayList;
//import java.util.HashMap;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tbims.service.IHealthCheckService;
import com.zhming.support.BaseController;
import com.zhming.support.annontion.ControlAspect;
import com.zhming.support.annontion.ControllerException;
import com.zhming.support.common.OperType;
import com.zhming.support.exception.BaseException;

/**
 * 
 * Title: 闸机、服务器及服务健康检查
 * Description:
 * @ClassName: HealthCheckController
 * @author zhuxy
 * @date 2017年11月17日 上午9:58:03
 *
 */
@RestController
@RequestMapping("/health/")
public class HealthCheckController extends BaseController{
	@Autowired
	private IHealthCheckService healthCheckService;
	
	/**
	 * Title: 查询所有闸机的运行状态<br/>
	 * Description:
	 * @param sysClient
	 * @return
	 * @throws BaseException
	 */
	@RequestMapping(value = "listGateState")
	@ControlAspect(funtionCd = "i2_sys_health_check", operType = OperType.QUERY, havPrivs=true)
	@ControllerException
	public List<Map<String, Object>> listGateState() throws BaseException {
		List<Map<String, Object>> clientStateList = healthCheckService.listGateState(getLoginUserBean());
		return clientStateList;
	}
	
	/**
	 * Title: 查询所有自助售票机的运行状态<br/>
	 * Description:
	 * @param sysClient
	 * @return
	 * @throws BaseException
	 */
	@RequestMapping(value = "listSlfServiceState")
	@ControlAspect(funtionCd = "i2_sys_health_check", operType = OperType.QUERY, havPrivs=true)
	@ControllerException
	public List<Map<String, Object>> listSlfServiceState() throws BaseException {
		List<Map<String, Object>> clientStateList = healthCheckService.listSlfServiceState(getLoginUserBean());
		return clientStateList;
	}
	
	/**
	 * Title: 查询所有网络代理回调错误计数<br/>
	 * Description:
	 * @param sysClient
	 * @return
	 * @throws BaseException
	 */
	@RequestMapping(value = "listWLAgentCallbackError")
	@ControlAspect(funtionCd = "i2_sys_health_check", operType = OperType.QUERY, havPrivs=true)
	@ControllerException
	public List<Map<String, Object>> listWLAgentCallbackError() throws BaseException {
		List<Map<String, Object>> agentErrorList = healthCheckService.listWLAgentCallbackError(getLoginUserBean());
		return agentErrorList;
	}
	
	/**
	 * Title: 检查所有服务器及服务的状态<br/>
	 * Description:
	 * @param sysClient
	 * @return
	 * @throws BaseException
	 */
	@RequestMapping(value = "listServerAndServiceState")
	@ControlAspect(funtionCd = "i2_sys_health_check", operType = OperType.QUERY, havPrivs=true)
	@ControllerException
	public List<Map<String, Object>> listServerAndServiceState() throws BaseException {	
		List<Map<String, Object>> serverList = healthCheckService.listCheckServer(getLoginUserBean());
		serverList = healthCheckService.listServerAndServiceState(getLoginUserBean(), serverList);
		return serverList;
	}
	
	/**
	 * Title: 重置所有网络代理回调错误计数<br/>
	 * Description:
	 * @param sysClient
	 * @return
	 * @throws BaseException
	 */
	@RequestMapping(value = "resetWLAgentCallbackError")
	@ControlAspect(funtionCd = "i2_sys_health_check", operType = OperType.QUERY, havPrivs=true)
	@ControllerException
	public void resetWLAgentCallbackError() throws BaseException {
		healthCheckService.resetWLAgentCallbackError(getLoginUserBean());
	}
	
	/**
	 * Title: 查询所有网络代理待回调门票数<br/>
	 * Description:
	 * @param sysClient
	 * @return
	 * @throws BaseException
	 */
	@RequestMapping(value = "listWLAgentWaitCallbackTicket")
	@ControlAspect(funtionCd = "i2_sys_health_check", operType = OperType.QUERY, havPrivs=true)
	@ControllerException
	public List<Map<String, Object>> listWLAgentWaitCallbackTicket() throws BaseException {
		List<Map<String, Object>> agentErrorList = healthCheckService.listWLAgentWaitCallbackTicket(getLoginUserBean());
		return agentErrorList;
	}
	
	/**
	 * Title: 根据机构ID查询待回调门票明细<br/>
	 * Description:
	 * @param sysClient
	 * @return
	 * @throws BaseException
	 */
	@RequestMapping(value = "listWaitCallbackTicketDetail")
	@ControlAspect(funtionCd = "i2_sys_health_check", operType = OperType.QUERY, havPrivs=true)
	@ControllerException
	public List<Map<String, Object>> listWaitCallbackTicketDetail(String orgId) throws BaseException {
		List<Map<String, Object>> agentErrorList = healthCheckService.listWaitCallbackTicketDetail(getLoginUserBean(), orgId);
		return agentErrorList;
	}
	
	/**
	 * Title: 跳过指定门票的机构回调<br/>
	 * Description:
	 * @param sysClient
	 * @return
	 * @throws BaseException
	 */
	@RequestMapping(value = "skipTicketCallback")
	@ControlAspect(funtionCd = "i2_sys_health_check", operType = OperType.QUERY, havPrivs=true)
	@ControllerException
	public void skipTicketCallback(String orderDetailId) throws BaseException {
		healthCheckService.skipTicketCallback(getLoginUserBean(), orderDetailId);
	}
}
