package com.tbims.init;

import java.util.List;
import java.util.Map;

import org.apache.log4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.tbims.bean.AuthBean;
import com.tbims.cache.ClientAuthCache;
import com.tbims.common.OperType;
import com.tbims.db.entity.SysClient;
import com.tbims.db.entity.SysLoginCache;
import com.tbims.db.entity.SysOutlet;
import com.tbims.db.entity.SysUser;
import com.tbims.db.util.DBUtil;
import com.tbims.rpc.entity.AUTHORIZATION;
import com.tbims.util.UUIDGenerator;

/**
 * Title:定时缓存信息 <br/>
 * Description:
 * 
 * @ClassName: CacheInit
 * @author ydc
 * @date 2017年8月3日 下午6:02:28
 * 
 */
@Component
public class CacheInit {
	@Autowired
	DBUtil dbUtil;

	/**
	 * Title:缓存终端信息 <br/>
	 * Description:定时每5分钟刷新一次缓存
	 */
	@Scheduled(cron = "0 */5 * * * ?")
	public void initAuthBean() {
		try {
			// 获取导航信息，生成导航信息串
			String logId = UUIDGenerator.getPK();
			String navigation = logId + "-" + "缓存终端信息";
			MDC.put("navigation", navigation);
			MDC.put("logid", logId);
			MDC.put("opertype", OperType.SCHEDULER);

			// 终端类型(3-缓存服务器1-网点普通终端,2-闸机,4-控制终端,5-自助售票机)
			List<SysClient> sysClients = dbUtil.queryListToBean("查询终端信息", "SELECT * FROM SYS_CLIENT WHERE CLIENT_TYPE in ('3','2','5')", SysClient.class);
			for (SysClient sysClient : sysClients) {
				AuthBean authBean = new AuthBean();
				AUTHORIZATION auth = new AUTHORIZATION();
				auth.setClientId(sysClient.getClientId());
				auth.setToken(sysClient.getToken());
				authBean.setAuth(auth);
				authBean.setSysClient(sysClient);

				if (sysClient.getOutletId() != null) {
					SysOutlet sysOutlet = dbUtil.findById("查询网点信息", SysOutlet.class, sysClient.getOutletId());
					authBean.setSysOutlet(sysOutlet);
				}

				ClientAuthCache.setAuthorizationMap(authBean);
			}
		} catch (Exception e) {
			throw e;
		} finally {
			// 清空log4j的mdc信息
			@SuppressWarnings("rawtypes")
			Map map = MDC.getContext();
			if (map != null) {
				map.clear();
			}
		}
	}

	public void initLoginCache() {
		try {
			// 获取导航信息，生成导航信息串
			String logId = UUIDGenerator.getPK();
			String navigation = logId + "-" + "缓存终端登录信息";
			MDC.put("navigation", navigation);
			MDC.put("logid", logId);
			MDC.put("opertype", OperType.SCHEDULER);

			List<SysLoginCache> sysLoginCacheList = dbUtil.queryListToBean("", "SELECT * FROM SYS_LOGIN_CACHE", SysLoginCache.class);
			for (SysLoginCache sysLoginCache : sysLoginCacheList) {
				// 缓存网点授权码
				AUTHORIZATION auth = new AUTHORIZATION(sysLoginCache.getClientId(), sysLoginCache.getTokey());
				SysUser sysUser = dbUtil.findById("查询用户", SysUser.class, sysLoginCache.getUserId());
				SysOutlet sysOutlet = dbUtil.findById("查询网点信息", SysOutlet.class, sysLoginCache.getOutletId());
				SysClient sysClient = dbUtil.findById("查询终端", SysClient.class, sysLoginCache.getClientId());

				AuthBean authBean = new AuthBean();
				authBean.setAuth(auth);
				authBean.setSysUser(sysUser);
				authBean.setSysClient(sysClient);
				authBean.setSysOutlet(sysOutlet);
				ClientAuthCache.setAuthorizationMap(authBean);
			}
		} catch (Exception e) {
			throw e;
		} finally {
			// 清空log4j的mdc信息
			@SuppressWarnings("rawtypes")
			Map map = MDC.getContext();
			if (map != null) {
				map.clear();
			}
		}
	}
}
