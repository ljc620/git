package com.zhming.support.init;

import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.MDC;

import com.zhming.support.util.MenuUtil;

/**
 * Title: bframe框架初始化 <br/>
 * Description:
 * @ClassName: ProjectInitServlet
 * @author ydc
 * @date 2016年11月22日 下午3:38:28
 * 
 */
public class ProjectInitServlet extends HttpServlet {
	private final Log logger = LogFactory.getLog(getClass());

	/**
	*/
	private static final long serialVersionUID = 1L;

	public void init(ServletConfig servletConfig) throws ServletException {
		MDC.put("navigation", "初始化");
		MDC.put("start", this.getClass().getName());
		try {
			logger.info("---缓存信息--begin--");
			MenuUtil.setNavigationMaps();
			MenuUtil.setFunctionMaps();
			logger.info("---缓存信息--end--");
		} catch (Exception e) {
			logger.error("初始化失败", e);
		} finally{
			@SuppressWarnings("rawtypes")
			Map map = MDC.getContext();
			if (map != null) {
				map.clear();
			}
		}
	}
}
