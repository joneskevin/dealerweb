package com.ava.servlet.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.http.HttpServlet;

import com.ava.resource.MyHourTimerTask;

/** Servlet上下文监听类 */
public class MyServletContextListener extends HttpServlet implements
		ServletContextListener {

	private java.util.Timer myHourTimer = null;

	public void contextInitialized(ServletContextEvent event) {
		myHourTimer = new java.util.Timer(true);
		System.out.println("小时定时器已启动");
		myHourTimer.schedule(new MyHourTimerTask(), 0, 60 * 60 * 1000);
	}

	public void contextDestroyed(ServletContextEvent event) {
		myHourTimer.cancel();
		System.out.println("小时定时器已销毁==================================================");
	}
}
