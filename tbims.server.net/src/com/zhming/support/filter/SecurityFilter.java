package com.zhming.support.filter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.zhming.support.bean.UserBean;
import com.zhming.support.common.Constant;

/**
 * Title: 安全过滤器 <br/>
 * Description:
 * @ClassName: SecurityFilter
 * @author ydc
 * @date 2016-1-7 下午5:00:57
 * 
 */
public class SecurityFilter implements Filter {
	private static Log log = LogFactory.getLog(SecurityFilter.class);

	private String ignore = "";
	/**
	 * 不验证的url请求信息,web.xml中配值
	 */
	private static Map<String, String> ignoreUrlMap = new HashMap<String, String>();

	public void destroy() {

	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		log.trace("执行SecurityFilter before");

		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;

		String uri = httpRequest.getRequestURI();
		String contextPath = httpRequest.getContextPath();
		uri = uri.substring(contextPath.length());

		// 验证会话信息
		if (ignoreUrlMap.get(uri) == null) {
			// 需要判断是否登录的请求
			if (httpRequest.getSession().getAttribute(Constant.USERKEY) == null) {
				if ("/".equals(uri)) {
					httpResponse.getWriter().println("<script>window.top.location.href='" + httpRequest.getContextPath() + "/login.jsp" + "'</script>");
				} else {
					httpResponse.getWriter().println("<script>window.top.location.href='" + httpRequest.getContextPath() + "/timeOut.jsp" + "'</script>");
				}
				return;
			}

			// 判断当前会话是否已注销
			String sessionid_user_cd = request.getParameter("sessionid_user_cd");
			UserBean user = (UserBean) (httpRequest.getSession().getAttribute(Constant.USERKEY));

			if (sessionid_user_cd != null && !sessionid_user_cd.equals(user.getUserId())) {
				httpResponse.getWriter().println("<script>window.top.location.href='" + httpRequest.getContextPath() + "/index.jsp" + "'</script>");
				return;
			}

		} else {
			// 验证是否已登录,如果已登录则跳到首页
			if (httpRequest.getSession().getAttribute(Constant.USERKEY) != null && ("/sys/login.do".equals(uri) || "/login.jsp".equals(uri))) {
				httpResponse.getWriter().println("<script>window.top.location.href='" + httpRequest.getContextPath() + "/index.jsp" + "'</script>");
				return;
			}
		}

		chain.doFilter(request, response);
		log.trace("执行SecurityFilter after");
	}
	public void init(FilterConfig filterConfig) throws ServletException {
		this.ignore = filterConfig.getInitParameter("ignoreUrl");
		if (this.ignore.indexOf(",") > -1) {
			String[] ignores = ignore.split(",");
			for (int i = 0; i < ignores.length; i++) {
				ignoreUrlMap.put(ignores[i], ignores[i]);
			}
		} else {
			ignoreUrlMap.put(ignore, ignore);
		}
	}
}
