package com.zhming.basic.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.tbims.service.IUserMngService;
import com.zhming.basic.service.ILoginService;
import com.zhming.support.BaseController;
import com.zhming.support.annontion.ControlAspect;
import com.zhming.support.annontion.ControllerException;
import com.zhming.support.bean.UserBean;
import com.zhming.support.common.Constant;
import com.zhming.support.common.MSG;
import com.zhming.support.exception.BaseException;
import com.zhming.support.exception.ServiceException;
import com.zhming.support.init.SessionListener;
import com.zhming.support.util.FrameProp;
import com.zhming.support.util.StringUtil;

/**
 * 系统登录控制类
* Title:   <br/>
* Description: 
* @ClassName: LoginController
* @author ljt
* @date 2017年1月3日 下午3:00:52
*
 */
@RestController
@RequestMapping(value = "/sys/")
public class LoginController extends BaseController {

	@Autowired
	private ILoginService loginService;
	@Autowired
	private IUserMngService userMngService;

	@RequestMapping(value = "login")
	@ControlAspect(funtionCd = "用户登录")
	@ControllerException(type = "page", viewName = "login")
	public ModelAndView login(HttpServletRequest request, HttpServletResponse response, String userId, String userPassword) throws BaseException {
		ModelAndView mv = null;
		HttpSession session = request.getSession();
		// 登录初始用户信息用于输出日志
		UserBean userBean = new UserBean();
		userBean.setUserId(userId);
		if (StringUtil.isNull(userId)) {
			throw new ServiceException(MSG.BF_ERROR_USERCODE_BANK);
		}
		if (StringUtil.isNull(userPassword) && !FrameProp.isOnlineTestEnv()) {
			throw new ServiceException(MSG.BF_ERROR_PASS_BANK);
		}

		userBean = loginService.login(userBean, userId, userPassword);
		
		// 判断用是否登录
		if (SessionListener.getSessionList().containsKey(userId)) {
			// 如果用户已经登录注销已登录的会话
			SessionListener.getSessionList().get(userId).invalidate();
		}
		session.setAttribute(Constant.USERKEY, userBean);
		
		
		// 检查用户密码是否为初始值
		if (userPassword.equals(FrameProp.getInitPassword())) {
			mv = new ModelAndView("updatepassword"); 
			mv.addObject("user", userBean);
			mv.addObject("message", "密码为初始值，必须设置新密码。");
			return mv;
		} else if(userPassword.length()<6){
			mv = new ModelAndView("updatepassword"); 
			mv.addObject("user", userBean);
			mv.addObject("message", "您的密码长度过短，请设置新密码。");
			return mv;
		}
		// 如果盐为空，设置盐
		if(StringUtil.isNull(userBean.getSysUser().getSalt())){
			userMngService.updatePassword(getLoginUserBean(), userPassword, userPassword);
		}
		
		mv = new ModelAndView("index"); 
		mv.addObject("user", userBean);
		return mv;
	}

	@RequestMapping(value = "logout")
	@ControlAspect(funtionCd="用户注销")
	@ControllerException(type = "page")
	public ModelAndView logout(HttpServletRequest request) throws BaseException {
		request.getSession().invalidate();
		ModelAndView mv = new ModelAndView("redirect:/login.jsp");
		return mv;
	}
}
