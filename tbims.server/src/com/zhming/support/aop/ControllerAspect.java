package com.zhming.support.aop;

import java.lang.reflect.Method;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.MDC;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.tbims.entity.SysLog;
import com.zhming.support.annontion.ControlAspect;
import com.zhming.support.bean.UserBean;
import com.zhming.support.common.Constant;
import com.zhming.support.common.MSG;
import com.zhming.support.common.OperType;
import com.zhming.support.db.DBUtil;
import com.zhming.support.exception.ServiceException;
import com.zhming.support.util.DateUtil;
import com.zhming.support.util.FrameProp;
import com.zhming.support.util.LogUtil;
import com.zhming.support.util.MenuUtil;
import com.zhming.support.util.StringUtil;
import com.zhming.support.util.UUIDGenerator;

/**
 * Title: 拦截控制层的方法，在之前之后进行相关操作 <br/>
 * Description:
 * @ClassName: ControllerAspect
 * @author ydc
 * @date 2016年12月14日 下午12:42:26
 * 
 */
@Aspect
@Component
public class ControllerAspect {

	@Autowired
	protected DBUtil dbUtil;

	// Controller层切点
	@Pointcut("@annotation(com.zhming.support.annontion.ControlAspect)")
	public void controllerAspect() {
	}

	/**
	 * Title: 拦截所有的Controller层方法记录用户的操作 <br/>
	 * Description: 用户权限控制、导航信息生成等操作
	 * @param pjp 切点
	 * @throws Throwable
	 */
	@Around("controllerAspect()")
	public Object around(ProceedingJoinPoint pjp) throws Throwable {
		/**
		 * 1. 获取会话信息,2.生成导航信息,3.进行权限控制,4.保存日志
		 */
		// 1.获取会话信息
		Object result = null;
		String function_cd = "";
		int operType = OperType.QUERY;
		boolean hasPrivs = false;

		// 获取原方法的信息
		String targetName = pjp.getTarget().getClass().getName();
		String methodName = pjp.getSignature().getName();

		// 获取注解信息
		ControlAspect ca = null;
		ca = getAnnonTionByControlAspect(pjp);
		if (ca != null) {
			function_cd = ca.funtionCd();
			operType = ca.operType();
			hasPrivs = ca.havPrivs();
		}

		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		String logCd = UUIDGenerator.getPK();
		request.setAttribute("logCd", logCd);

		// 2.获取导航信息，生成导航信息串
		String navigation = MenuUtil.getFunctionName(function_cd);
		HttpSession session = request.getSession();
		UserBean userBean = (UserBean) session.getAttribute(Constant.USERKEY);

		navigation = LogUtil.prefix(logCd, navigation, userBean);

		MDC.put("navigation", StringUtil.convertString(navigation));
		MDC.put("start", targetName + "-" + methodName);

		// 3.进行权限控制,无权限直接抛出异常
		if (userBean != null && hasPrivs) {
			if (!userBean.getFunctionSet().contains(function_cd)) {
				throw new ServiceException(MSG.BF_ERROR_AUTH_NO);
			}
		}

		// 4.调用原Controller的方法
		result = pjp.proceed();

		// 5.保存成功日志
		if (StringUtil.isContain(StringUtil.convertString(operType), FrameProp.getSys_log_type().split(","))) {
			SysLog sysLog = new SysLog();
			if (userBean == null) {
				sysLog.setUserId("system");
				sysLog.setUserName("程序后台");
			} else {
				sysLog.setUserId(userBean.getUserId());
				sysLog.setUserName(userBean.getSysUser().getUserName());
			}

			sysLog.setClientId(0L);
			sysLog.setLogId(logCd);
			sysLog.setLogType(Long.valueOf(operType));
			sysLog.setMenuId(function_cd);
			sysLog.setMenuName(MenuUtil.getFunctionName(function_cd));
			sysLog.setLogTime(DateUtil.getNowDate());
			sysLog.setLogStat(StringUtil.convertString(MSG.OK));
			sysLog.setVldDesc("操作成功");
			dbUtil.saveEntity("保存日志", sysLog);
		}

		// 清空log4j的mdc信息
		@SuppressWarnings("rawtypes")
		Map map = MDC.getContext();
		if (map != null) {
			map.clear();
		}

		return result;
	}

	/**
	 * 获取注解中对方法的描述信息 用于Controller层注解
	 * 
	 * @param pjp 切点
	 * @return 方法描述
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public static ControlAspect getAnnonTionByControlAspect(JoinPoint pjp) throws Exception {
		String targetName = pjp.getTarget().getClass().getName();
		String methodName = pjp.getSignature().getName();
		Object[] arguments = pjp.getArgs();
		Class targetClass = Class.forName(targetName);
		Method[] methods = targetClass.getMethods();
		for (Method method : methods) {
			if (method.getName().equals(methodName)) {
				Class[] clazzs = method.getParameterTypes();
				if (clazzs.length == arguments.length) {
					ControlAspect ca = method.getAnnotation(ControlAspect.class);
					return ca;
				}
			}
		}
		return null;
	}

}
