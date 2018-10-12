package com.tbims.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.tbims.bean.SysEmpRegisterBean;
import com.tbims.entity.SysEmpRegister;
import com.tbims.service.IEmpRegisterService;
import com.zhming.support.BaseController;
import com.zhming.support.annontion.ControlAspect;
import com.zhming.support.annontion.ControllerException;
import com.zhming.support.bean.PageBean;
import com.zhming.support.common.MSG;
import com.zhming.support.common.OperType;
import com.zhming.support.exception.BaseException;

/**
 * 
 * Title: 员工信息查询<br/>
 * Description:
 * @ClassName: EmpRegisterController
 * @author shc
 * @date 2017年9月1日 上午9:58:03
 *
 */
@RestController
@RequestMapping("/empregister/")
public class EmpRegisterController extends BaseController {
	@Autowired
	IEmpRegisterService empRegisterService;

	/**
	 * 
	 * Title:员工信息查询 <br/>
	 * Description:
	 * @param empRegister
	 * @return
	 */
	@RequestMapping(value = "empregister")
	@ControlAspect(funtionCd = "i2_sys_emp_register", operType = OperType.QUERY, havPrivs = true)
	@ControllerException
	public PageBean<SysEmpRegisterBean> listEmpRegister(SysEmpRegister empRegister) {
		PageBean<SysEmpRegisterBean> listEmpRegister = empRegisterService.listEmpRegister(getLoginUserBean(), empRegister);
		return listEmpRegister;
	}

	/**
	 * 
	 * Title: 更新状态 
	 * Description:员工状态(Y启用N禁用)
	 * @param blackListId
	 * @param stat
	 * @throws BaseException
	 */
	@RequestMapping(value = "updateStat")
	@ControlAspect(funtionCd = "更新状态", operType = OperType.UPDATE)
	@ControllerException
	public void updateStat(String empId, String stat) throws BaseException {
		empRegisterService.updateStat(getLoginUserBean(), empId, stat);
	}
	/**
	 * 
	 * Title: 根据员工号获取员工信息<br/>
	 * Description:
	 * @param empId
	 * @return
	 * @throws BaseException
	 */
	@RequestMapping(value = "befShowImg")
	@ControlAspect(funtionCd = "获取员工信息", operType = OperType.QUERY)
	@ControllerException
	public ModelAndView befShowImg(Long empId) throws BaseException {
		ModelAndView mv = new ModelAndView("pages/sys/empregister/getlmg");
		SysEmpRegister empRegister = empRegisterService.photo(empId);
		mv.addObject("empRegister", empRegister);
		return mv;
	}

	/**
	 * 
	 * Title: 解析获取员工图片信息<br/>
	 * Description:
	 * @param request
	 * @param empId
	 * @param response
	 * @throws BaseException
	 */
	@RequestMapping(value = "getlmg")
	@ControlAspect(funtionCd = "解析图片", operType = OperType.QUERY)
	@ControllerException
	public void getlmg(HttpServletRequest request, Long empId, HttpServletResponse response) throws BaseException {
		SysEmpRegister empRegister = empRegisterService.photo(empId);
		response.setContentType("image/jpeg");
		OutputStream outputStream = null;
		try {
			outputStream = response.getOutputStream();

			ByteArrayInputStream in = new ByteArrayInputStream(empRegister.getPhoto());
			int len;
			byte[] buf = new byte[1024];
			while ((len = in.read(buf)) != -1) {
				outputStream.write(buf, 0, len);
			}
			outputStream.flush();
		} catch (IOException e) {
			throw new BaseException(MSG.BF_ERROR, "读取图片失败");
		} finally {
			try {
				outputStream.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

}
