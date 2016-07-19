package com.ava.servlet.interceptor;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;


/**控制器拦截类*/
public class SecurityControlInteceptor extends HandlerInterceptorAdapter{
/*	1,Action之前执行:
		 public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler);
		 
	2,生成视图之前执行
		 public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView);
		 
	3,最后执行，可用于释放资源
		 public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
		 
		 
	4,分别实现预处理、后处理（调用了Service并返回ModelAndView，但未进行页面渲染）、返回处理（已经渲染了页面） 
		在preHandle中，可以进行编码、安全控制等处理； 
		在postHandle中，有机会修改ModelAndView，比如格式转化； 
		在afterCompletion中，可以根据ex是否为null判断是否发生了异常，进行日志记录。 
		参数中的Object handler是下一个拦截器。
	5,配置（近似） 总拦截器， 拦截匹配的URL：
	<mvc:interceptors >  
	  <mvc:interceptor>  
	        <mvc:mapping path="/user/*" /> <!-- /user/*  -->  
	        <bean class="com.mvc.MyInteceptor"></bean>  
	    </mvc:interceptor>  
	</mvc:interceptors>  

*/
}
