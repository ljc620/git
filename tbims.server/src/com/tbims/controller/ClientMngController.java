package com.tbims.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.tbims.entity.SysClient;
import com.tbims.service.IClientMngService;
import com.zhming.support.BaseController;
import com.zhming.support.annontion.ControlAspect;
import com.zhming.support.annontion.ControllerException;
import com.zhming.support.bean.PageBean;
import com.zhming.support.common.OperType;
import com.zhming.support.exception.BaseException;
import com.zhming.support.exception.DBException;
import com.zhming.support.exception.ServiceException;

/**
 * 
 * Title: 终端管理 <br/>
 * Description:
 * @ClassName: ClientMngController
 * @author syq
 * @date 2017年7月3日 下午2:06:07
 *
 */

@RestController
@RequestMapping("/clientMng")
public class ClientMngController extends BaseController {
	@Autowired
	private IClientMngService clientMngService;

	/**
	 * 
	 * Title: 查询终端列表<br/>
	 * Description:
	 * @param sysClient
	 * @return
	 * @throws BaseException
	 */
	@RequestMapping(value = "listClient")
	@ControlAspect(funtionCd = "i2_sys_client_mng", operType = OperType.QUERY, havPrivs=true)
	@ControllerException
	public PageBean<Map<String, Object>> listClient(SysClient sysClient) throws BaseException {
		PageBean<Map<String, Object>> clientList = clientMngService.listClient(getLoginUserBean(), sysClient);
		return clientList;
	}

	/**
	 * 
	 * Title: 新增终端<br/>
	 * Description:
	 * @param sysClient
	 * @throws ServiceException
	 * @throws DBException
	 */
	@RequestMapping(value = "addClient")
	@ControlAspect(funtionCd = "i2_sys_client_mng", operType = OperType.ADD, havPrivs=true)
	@ControllerException
	public void addClient(SysClient sysClient) throws BaseException {
		clientMngService.addClient(getLoginUserBean(), sysClient);
	}

	/**
	 * 
	 * Title: 获得要修改的终端信息<br/>
	 * Description:
	 * @param clientId
	 * @return
	 * @throws DBException
	 */
	@RequestMapping(value = "beforeUpdateClient")
	@ControlAspect(funtionCd = "根据终端号查询终端", operType = OperType.QUERY)
	@ControllerException
	public ModelAndView beforeUpdateClient(Long clientId) throws BaseException {
		ModelAndView mv = new ModelAndView("pages/sys/clientMng/updateClient");
		SysClient sysClient = clientMngService.getClientById(clientId);
		mv.addObject("sysClient", sysClient);
		return mv;
	}
	/**
	 * 
	 * Title: 修改终端信息<br/>
	 * Description:
	 * @param sysClient
	 * @throws DBException
	 */
	@RequestMapping(value = "updateClient")
	@ControlAspect(funtionCd = "i2_sys_client_mng", operType = OperType.UPDATE, havPrivs=true)
	@ControllerException
	public void updateClient(SysClient sysClient) throws BaseException {
		clientMngService.updateClient(getLoginUserBean(), sysClient);
	}
	/**
	 * 
	 * Title: 删除终端<br/>
	 * Description:
	 * @param clientId
	 * @throws DBException
	 */
	@RequestMapping(value = "delClient")
	@ControlAspect(funtionCd = "i2_sys_client_mng", operType = OperType.DELETE, havPrivs=true)
	@ControllerException
	public void delClient(Long clientId) throws BaseException {
		clientMngService.delClient(clientId);
	}
	/**
	 * 
	 * Title: 修改状态<br/>
	 * Description:
	 * @param clientIds
	 * @param stat Y-启用 N-停用
	 * @throws BaseException
	 */
	@RequestMapping(value = "updateStat")
	@ControlAspect(funtionCd = "i2_sys_client_mng", operType = OperType.UPDATE, havPrivs=true)
	@ControllerException
	public void updateStat(String clientIds, String stat) throws BaseException {
		clientMngService.updateStat(getLoginUserBean(), clientIds, stat);
	}
	/**
	 * 
	 * Title:重置授权码 <br/>
	 * Description:
	 * @param clientIds
	 * @throws BaseException
	 */
	@RequestMapping(value = "updateToken")
	@ControlAspect(funtionCd = "i2_sys_client_mng", operType = OperType.UPDATE, havPrivs=true)
	@ControllerException
	public void updateToken(String clientIds) throws BaseException {
		clientMngService.updateToken(getLoginUserBean(), clientIds);
	}
}
