package com.zhming.support.exception;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.MDC;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONArray;
import com.tbims.entity.SysLog;
import com.zhming.support.annontion.ControlAspect;
import com.zhming.support.annontion.ControllerException;
import com.zhming.support.bean.ErrorBean;
import com.zhming.support.bean.UserBean;
import com.zhming.support.common.Constant;
import com.zhming.support.common.MSG;
import com.zhming.support.common.OperType;
import com.zhming.support.db.DBUtil;
import com.zhming.support.db.impl.DBUtilImpl;
import com.zhming.support.init.SpringContextUtil;
import com.zhming.support.util.DateUtil;
import com.zhming.support.util.MenuUtil;
import com.zhming.support.util.StringUtil;

/**
 * Title: 拦截Controller层所有方法抛出的异常并进行日志等处理 <br/>
 * Description:
 * @ClassName: MyExceptionHandler
 * @author ydc
 * @date 2016年12月13日 下午9:50:47
 * 
 */
public class MyExceptionHandler implements HandlerExceptionResolver {
	private final Log logger = LogFactory.getLog(getClass());

	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler,
			Exception ex) {
		DBUtil dbUtil = SpringContextUtil.getBean(DBUtilImpl.class);

		ModelAndView mv = new ModelAndView();
		ErrorBean errorBean = new ErrorBean();
		Throwable throwableException = null;
		HandlerMethod sourceObj = (HandlerMethod) handler;
		int operType = OperType.QUERY;
		String menuId = "";
		boolean saveLogFlag = true;

		//输出异常
		if (logger.isErrorEnabled()) {
			logger.error("",ex);
		}
		
		ControlAspect ca = sourceObj.getMethodAnnotation(ControlAspect.class);
		if (ca != null) {
			operType = ca.operType();
			menuId = ca.funtionCd();
		}

		String logId = StringUtil.convertString(request.getAttribute("logCd"));
		HttpSession session = request.getSession();
		UserBean userBean = (UserBean) session.getAttribute(Constant.USERKEY);

		/**
		 * 1. 获取错误信息放入ErrorBean 2. 保存操作日志 3. 根据注解type的类型（json、页面），判断返回方式
		 */

		// 1. 获取错误信息放入ErrorBean
		if (ex instanceof UndeclaredThrowableException) {
			UndeclaredThrowableException ute = (UndeclaredThrowableException) ex;
			throwableException = ute.getUndeclaredThrowable();
		} else {
			throwableException = ex;
		}

		if (throwableException instanceof BaseException) {
			// 处理自定义-业务逻辑异常，不保存日志
			BaseException baseException = (BaseException) throwableException;
			errorBean.setErrorCode(baseException.getErrcode());
			errorBean.setErrorDescribe(baseException.getErrinfo());
			saveLogFlag = false;
		} else if (throwableException instanceof org.hibernate.exception.GenericJDBCException) {
			errorBean.setErrorCode(MSG.DB_ERROR_CONNECT);
			errorBean.setErrorDescribe(throwableException.getMessage());
		} else if (throwableException instanceof org.hibernate.exception.SQLGrammarException) {
			errorBean.setErrorCode(MSG.DB_ERROR_EXECUTE);
			errorBean.setErrorDescribe(throwableException.getMessage());
		} else if (throwableException instanceof org.hibernate.JDBCException || ex instanceof java.sql.SQLException) {
			errorBean.setErrorCode(MSG.DB_ERROR);
			errorBean.setErrorDescribe(throwableException.getMessage());
		} else if (throwableException instanceof Exception) {
			errorBean.setErrorCode(MSG.ERROR);
			errorBean.setErrorDescribe(throwableException.getMessage());
		}

		// 2. 保存日志
		if (saveLogFlag) {
			SysLog sysLog = new SysLog();
			if (userBean == null) {
				sysLog.setUserId("system");
				sysLog.setUserName("程序后台");
			} else {
				sysLog.setUserId(userBean.getUserId());
				sysLog.setUserName(userBean.getSysUser().getUserName());
			}

			sysLog.setClientId(0L);
			sysLog.setLogId(logId);
			sysLog.setLogType(Long.valueOf(operType));
			sysLog.setMenuId(menuId);
			sysLog.setMenuName(MenuUtil.getFunctionName(menuId));
			sysLog.setLogTime(DateUtil.getNowDate());
			sysLog.setLogStat(StringUtil.convertString(errorBean.getErrorCode()));
			sysLog.setVldDesc(errorBean.getErrorMsg());
			dbUtil.saveEntity("保存日志", sysLog);
		}

		// 3. 根据注解type的类型（json、页面），判断返回方式
		String type = "json";
		String viewName = "";

		ControllerException exceptionReturn = sourceObj.getMethodAnnotation(ControllerException.class);

		if (exceptionReturn != null) {
			type = StringUtil.convertString(exceptionReturn.type()).trim();
			viewName = StringUtil.convertString(exceptionReturn.viewName()).trim();
		}

		// 设置响应信息为失败，返回文档类型为json
		if ("json".equals(type)) {
			/* 使用response返回 */
			response.setStatus(HttpStatus.FAILED_DEPENDENCY.value()); // 设置状态码
			response.setContentType(MediaType.TEXT_HTML_VALUE); // 设置ContentType
			response.setCharacterEncoding("UTF-8"); // 避免乱码
			response.setHeader("Cache-Control", "no-cache, must-revalidate");
			PrintWriter printWriter = null;
			try {
				printWriter = response.getWriter();
				if (logger.isDebugEnabled()) {
					logger.debug(StringUtil.convertString(JSONArray.toJSON(errorBean)));
				}
				printWriter.write(StringUtil.convertString(JSONArray.toJSON(errorBean)));
				printWriter.flush();
			} catch (IOException e) {
				logger.error(errorBean.getErrorMsg(), e);
			} finally {
				if (printWriter != null) {
					printWriter.close();
				}
			}
		}

		// 返回指定的错误页面
		if ("page".equals(type)) {
			if (viewName.equals("")) {
				viewName = "msg/error";
			}
			mv.setViewName(viewName);
			mv.addObject("errorCode", errorBean.getErrorCode());
			mv.addObject("errorMsg", errorBean.getErrorMsg());
			mv.addObject("errorDescribe", errorBean.getErrorDescribe());
		}

		// 清空log4j的mdc信息
		@SuppressWarnings("rawtypes")
		Map map = MDC.getContext();
		if (map != null) {
			map.clear();
		}

		return mv;
	}
}
