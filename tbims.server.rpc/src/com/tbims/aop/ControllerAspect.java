package com.tbims.aop;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.MDC;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONArray;
import com.tbims.annontion.ControlAspect;
import com.tbims.bean.AuthBean;
import com.tbims.cache.ClientAuthCache;
import com.tbims.cache.ConfigUtil;
import com.tbims.cache.MsgUtil;
import com.tbims.common.OperType;
import com.tbims.db.entity.SysLog;
import com.tbims.db.util.DBUtil;
import com.tbims.init.ServerMain;
import com.tbims.init.SpringContextUtil;
import com.tbims.rpc.entity.AUTHORIZATION;
import com.tbims.rpc.entity.RPCException;
import com.tbims.util.CommonUtil;
import com.tbims.util.DateUtil;
import com.tbims.util.LogUtil;
import com.tbims.util.StringUtil;
import com.tbims.util.UUIDGenerator;

/**
 * Title: 拦截控制层的方法，在之前之后进行相关操作 <br/>
 * Description:
 * 
 * @ClassName: ControllerAspect
 * @author ydc
 * @date 2016年12月14日 下午12:42:26
 * 
 */
@Aspect
@Component
public class ControllerAspect {
	private static final Log logger = LogFactory.getLog(ServerMain.class);
	private static final String DBLOG_LEVEL = StringUtil.convertString(ConfigUtil.configs.get("sys.dblog.level"));

	// Controller层切点
	@Pointcut("@annotation(com.tbims.annontion.ControlAspect)")
	public void controllerAspect() {
	}

	/**
	 * Title: 拦截所有的Controller层方法记录用户的操作 <br/>
	 * Description: 用户权限控制、导航信息生成等操作
	 * 
	 * @param pjp 切点
	 * @throws Throwable
	 */
	@Around("controllerAspect()")
	public Object around(ProceedingJoinPoint pjp) throws Throwable {
		/**
		 * 1. 获取会话信息,2.生成导航信息,3.进行权限控制,4.保存日志
		 */
		// 1.获取会话信息
		Object[] arguments = null;
		Object result = null;
		String functionName = "";
		int operType = OperType.QUERY;
		SysLog sysLog = null;

		// 获取原方法的信息
		String targetName = pjp.getTarget().getClass().getName();
		String methodName = pjp.getSignature().getName();
		try {
			MDC.put("start", targetName + "-" + methodName);

			// 获取注解信息
			ControlAspect ca = null;
			ca = getAnnonTionByControlAspect(pjp);
			if (ca != null) {
				functionName = ca.funtionName();
				operType = ca.operType();
			}

			String logId = UUIDGenerator.getPK();
			MDC.put("navigation", logId);
			MDC.put("logid", logId);
			MDC.put("opertype", operType);

			arguments = pjp.getArgs();
			logger.debug(String.format("RPC接口-输入参数:[%s]", JSONArray.toJSONString(arguments)));

			// 2.获取导航信息，生成导航信息串

			Map<String, String> info = getInfoByControlAspect(pjp);
			String navigation = LogUtil.prefix(info, logId, functionName);

			MDC.put("navigation", navigation);

			// 日志信息
			sysLog = new SysLog();
			sysLog.setClientId(CommonUtil.covertLong(info.get("CLIENTID")));

			sysLog.setUserId(info.get("USERID"));
			sysLog.setUserName(info.get("USERNAME"));

			sysLog.setLogId(logId);
			sysLog.setLogType(Long.valueOf(operType));
			sysLog.setMenuId(functionName);
			sysLog.setMenuName(functionName);
			sysLog.setLogTime(DateUtil.getNowDate());

			// 4.调用原Controller的方法,保存操作日志
			result = pjp.proceed();
			saveLog(operType, sysLog, 0);
			logger.debug(String.format("RPC接口-输出参数:[%s]", JSONArray.toJSONString(result)));
		} catch (RPCException e) {
			logger.error(String.format("业务处理错误,错误码[%d]错误描述[%s]", e.getErrorCode(), e.getErrorMess()));
			saveLog(operType, sysLog, e.getErrorCode());
			throw e;
		} catch (Exception e) {
			logger.error("错误", e);
			saveLog(operType, sysLog, 3);
			throw new RPCException(3, MsgUtil.getMsg(3));
		} finally {
			// 清空log4j的mdc信息
			@SuppressWarnings("rawtypes")
			Map map = MDC.getContext();
			if (map != null) {
				map.clear();
			}
		}

		return result;
	}

	/**
	 * Title:保存日志 <br/>
	 * Description:
	 * 
	 * @param operType
	 * @param sysLog
	 * @param errorCode
	 */
	public static void saveLog(int operType, SysLog sysLog, int errorCode) {
		saveLog(operType, sysLog, errorCode, null);
	}

	/**
	 * Title:保存日志 <br/>
	 * Description:
	 * 
	 * @param operType
	 * @param sysLog
	 * @param errorCode
	 * @param errorInfo
	 */
	public static void saveLog(int operType, SysLog sysLog, int errorCode, String errorInfo) {
		try {
			DBUtil dbUtil = SpringContextUtil.getBean("DBUtilImpl");
			if (sysLog == null) {
				return;
			}
			// 保存日志,错误日志都保存
			if (StringUtil.isContain(StringUtil.convertString(operType), DBLOG_LEVEL.split(",")) || errorCode != 0) {
				sysLog.setLogStat(StringUtil.convertString(errorCode));

				if (StringUtil.isNull(errorInfo)) {
					sysLog.setVldDesc(MsgUtil.getMsg(errorCode));
				} else {
					sysLog.setVldDesc(errorInfo);
				}

				dbUtil.saveEntity("", sysLog);
			}
		} catch (Exception e) {
			logger.error("保存日志错误", e);
		}
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

	/**
	 * 获取注解中对方法的描述信息 用于Controller层注解
	 * 
	 * @param pjp 切点
	 * @return AUTHORIZATION
	 * @throws RPCException
	 */
	public static Map<String, String> getInfoByControlAspect(JoinPoint pjp) throws RPCException {
		String methodName = pjp.getSignature().getName();
		Object[] arguments = pjp.getArgs();
		if (arguments.length == 0) {
			return null;
		}
		Map<String, String> retMap = new HashMap<String, String>();

		Set<String> ignoreFuntionName = new HashSet<String>();
		ignoreFuntionName.add("login");
		if (ignoreFuntionName.contains(methodName)) {// 客户端登录
			retMap.put("CLIENTID", StringUtil.convertString(arguments[0]));
			retMap.put("USERID", StringUtil.convertString(arguments[0]));
			retMap.put("USERNAME", "");
			return retMap;
		} else {
			// 非登录操作，分2种情况如下：
			// 1. 当为 闸机或缓存服务器 时无 登录用户信息
			// 2. 当为 网点 时有 登录用户信息
			if (arguments[0] == null) {// 约定rpc对外的方法第一个参数为 AUTHORIZATION
				throw new RPCException(4, MsgUtil.getMsg(4));
			}
			AUTHORIZATION auth = (AUTHORIZATION) arguments[0];
			AuthBean authBean = ClientAuthCache.getAuthBean(auth.getClientId());

			ClientAuthCache.checkToken(auth, authBean);

			retMap.put("CLIENTID", StringUtil.convertString(auth.getClientId()));
			// 终端类型(3-缓存服务器1-网点普通终端,2-闸机,4-控制终端,5-自助售票机)
			if ("1".equals(authBean.getSysClient().getClientType()) || "4".equals(authBean.getSysClient().getClientType())) {
				// 当为 网点 时有 登录用户信息
				if (authBean.getSysUser() == null) {
					throw new RPCException(10000, MsgUtil.getMsg(10000));
				} else {
					retMap.put("USERID", StringUtil.convertString(authBean.getSysUser().getUserId()));
					retMap.put("USERNAME", StringUtil.convertString(authBean.getSysUser().getUserName()));
				}
			} else {
				// 当为 闸机或缓存服务器 时无 登录用户信息
				if ("N".equals(authBean.getSysClient().getStat())) {
					throw new RPCException(30001, "终端已停用");
				}
				retMap.put("USERID", StringUtil.convertString(authBean.getSysClient().getClientId()));
				retMap.put("USERNAME", StringUtil.convertString(authBean.getSysClient().getClientName()));
			}

			return retMap;
		}
	}
}
