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
import com.tbims.db.entity.SysOutlet;
import com.tbims.db.util.DBUtil;
import com.tbims.rpc.entity.AUTHORIZATION;

/**
* Title:定时缓存信息 <br/>
* Description: 
* @ClassName: CacheInit
* @author ydc
* @date 2017年8月3日 下午6:02:04
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

}
