package com.zhming.support.filter;

import java.io.IOException;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServlet;

import org.apache.log4j.MDC;

/**
 * Title: 用于设置 HTTP请求字符编码的过滤器，通过过滤器参数encoding指明使用何种字符编码,用于处理Form请求参数的中文问题<br/>
 * Description:
 * @ClassName: CharacterEncodingFilter
 * @author ydc
 * @date 2016-1-7 下午5:01:13
 * 
 */
@WebFilter("/characterEncodingFilter")
public class CharacterEncodingFilter extends HttpServlet implements Filter {

	/**   
	 *   
	 */
	private static final long serialVersionUID = 1L;

	protected FilterConfig filterConfig = null;

	protected String encoding = "";

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public CharacterEncodingFilter() {
		super();
	}

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		filterConfig = null;
		encoding = null;
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		if (encoding != null) {
			request.setCharacterEncoding(encoding);
			chain.doFilter(request, response);
		} else {
			chain.doFilter(request, response);
		}

		// 清空log4j的mdc信息
		@SuppressWarnings("rawtypes")
		Map map = MDC.getContext();
		if (map != null) {
			map.clear();
		}
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		this.filterConfig = fConfig;
		this.encoding = filterConfig.getInitParameter("encoding");
	}

}
