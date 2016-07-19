package com.ava.servlet.listener;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.apache.log4j.Logger;

import com.ava.resource.SessionManager;
import com.ava.util.DateTime;

/**会话监听器类*/
public class MyHttpSessionListener extends HttpServlet implements HttpSessionListener {

    private static Logger logger = Logger.getLogger(MyHttpSessionListener.class);  

    public void sessionCreated(HttpSessionEvent se) {
		// 把session的引用放到一个全局静态变量里
//		SessionManager.getSessionCache().put(se.getSession().getId(), se.getSession());
//		logger.info(DateTime.getNormalDateTime() + " 新创建 sessionId is " + se.getSession().getId() + "共" + SessionManager.getSessionCache().size());
	}

	public void sessionDestroyed(HttpSessionEvent se) {
		if( (null!=SessionManager.getSessionCache() && SessionManager.getSessionCache().size()>=1) && null!=se.getSession()){
			logger.info(DateTime.getNormalDateTime() + " 销毁 sessionId is " + se.getSession().getId() + "共" + SessionManager.getSessionCache().size());
			SessionManager.getSessionCache().remove(se.getSession().getId());
		}
	}
}
