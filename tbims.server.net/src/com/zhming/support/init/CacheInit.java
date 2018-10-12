package com.zhming.support.init;

import java.util.Map;

import org.apache.log4j.MDC;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.zhming.support.cache.OrgAuthCache;

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

	/**
	 * Title:缓存终端信息 <br/>
	 * Description:定时每5分钟刷新一次缓存
	 */
	@Scheduled(cron = "0 */5 * * * ?")
	public void initAuthBean() {
		try {
			OrgAuthCache.initOrgMaps();
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
