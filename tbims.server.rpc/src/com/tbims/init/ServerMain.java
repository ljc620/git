package com.tbims.init;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ServerMain {
	private static final Log logger = LogFactory.getLog(ServerMain.class);
	private static ApplicationContext factory;

	public static void start() {
		try {
			logger.debug("加载spring 配值...start");
			factory = new ClassPathXmlApplicationContext(new String[]{"spring-common.xml", "spring-hibernate.xml"});
			logger.debug("加载spring 配值...end");
			
			//缓存信息
			logger.info("缓存终端信息 begin...");
			CacheInit cacheInit = factory.getBean(CacheInit.class);
			cacheInit.initAuthBean();
			cacheInit.initLoginCache();
			logger.info("缓存终端信息 end...");
			
			logger.info("回调接口 begin...");
			IdentiyCheckOpeInit identiyCheckOpeInit=factory.getBean(IdentiyCheckOpeInit.class);
			identiyCheckOpeInit.identiyCheckByOrgCacheRun();
			logger.info("回调接口 end...");
			
			//开启RPC服务
			RPCServerInit init = factory.getBean(RPCServerInit.class);
			init.startServer();
		} catch (Exception e) {
			logger.error("启动失败", e);
		}
	}
	
	public static void main(String[] args) {
		ServerMain.start();
	}
}
