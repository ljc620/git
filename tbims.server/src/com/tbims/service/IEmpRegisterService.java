package com.tbims.service;



import com.tbims.bean.SysEmpRegisterBean;
import com.tbims.entity.SysEmpRegister;
import com.zhming.support.bean.PageBean;
import com.zhming.support.bean.UserBean;
import com.zhming.support.exception.BaseException;
/**
 * 
* Title:   员工信息查询
* Description: 
* @ClassName: IEmpRegisterService
* @author ly
* @date 2017年8月8日 上午9:09:11
*
 */
public interface IEmpRegisterService {
	/**
	 * 
	* Title: 查询员工信息
	* Description: 
	* @param userBean
	* @param sysEmpRegister
	* @return
	 */
	public PageBean<SysEmpRegisterBean> listEmpRegister(UserBean userBean,SysEmpRegister sysEmpRegister);
	
	/**
	 * 
	* Title: 修改状态
	* Description: 
	* @param userBean
	* @param empId
	* @param stat
	* @throws BaseException
	 */
	public void updateStat(UserBean userBean, String empId, String stat) throws BaseException;
	
	/**
	 * 
	* Title: 获取员工信息<br/>
	* Description: 
	* @param empId
	* @return
	 */
	public SysEmpRegister photo ( Long empId) ;
	
}
