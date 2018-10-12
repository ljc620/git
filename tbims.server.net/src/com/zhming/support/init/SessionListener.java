package com.zhming.support.init;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.zhming.support.bean.UserBean;
import com.zhming.support.common.Constant;

/**
 * Title: 会话监听管理 <br/>
 * Description:
 * @ClassName: SessionListener
 * @author ydc
 * @date 2016-1-7 下午5:14:21
 * 
 */
public class SessionListener implements HttpSessionListener, HttpSessionAttributeListener {
	private final Log logger = LogFactory.getLog(getClass());

	private static Map<String, HttpSession> sessionList = new HashMap<String, HttpSession>();

	public void sessionCreated(HttpSessionEvent hs) {
		UserBean userBean = (UserBean) hs.getSession().getAttribute(Constant.USERKEY);
		if (userBean != null) {
			if (!sessionList.containsKey(userBean.getUserId())) {
				sessionList.put(userBean.getUserId(), hs.getSession());
			}
		}
	}

	public void sessionDestroyed(HttpSessionEvent hs) {
		UserBean userBean = (UserBean) hs.getSession().getAttribute(Constant.USERKEY);
		if (userBean != null) {
			logger.debug("[" + userBean.getUserId() + "]-会话结束");
		}
	}

	public void attributeAdded(HttpSessionBindingEvent se) {
		if (Constant.USERKEY.equals(se.getName())) {
			UserBean userBean = (UserBean) se.getValue();
			sessionList.put(userBean.getUserId(), se.getSession());
		}
	}

	public void attributeRemoved(HttpSessionBindingEvent se) {
		if (Constant.USERKEY.equals(se.getName())) {
			UserBean userBean = (UserBean) se.getValue();
			sessionList.remove(userBean.getUserId());
		}
	}

	public void attributeReplaced(HttpSessionBindingEvent se) {
	}

	public static Map<String, HttpSession> getSessionList() {
		return sessionList;
	}
}
