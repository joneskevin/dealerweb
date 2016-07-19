package com.ava.servlet.interceptor;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.ava.dao.IOperatorLogDao;
import com.ava.domain.entity.Operator;
import com.ava.domain.entity.OperatorLog;
import com.ava.domain.vo.AbstractLog.OperactionObjProperty;
import com.ava.resource.GlobalConfig;
import com.ava.resource.SessionManager;
import com.ava.util.MyStringUtils;

/**back子系统的操作日志类*/
public class Log4BackInteceptor extends HandlerInterceptorAdapter{

	/** 需要记录操作日志的模式列表 */
	private static List<String[]> needOperationLogPatternListAtPre = null;
	private static List<String[]> needOperationLogPatternListAtAfter = null;
	/** 不需要记录操作日志的模式列表 */
	private static List<OperactionObjProperty> noOperationLogPatternListAtPre = null;
	private static List<OperactionObjProperty> noOperationLogPatternListAtAfter = null;

	/** 需要记录错误日志的模式列表 */
	private static List<String[]> needErrorLogPatternList = null;
	/** 不需要记录错误日志的模式列表 */
	private static List<OperactionObjProperty> noErrorLogPatternList = null;
	
	static {
		String[] urlAndAction = null;
		needOperationLogPatternListAtPre = new LinkedList<String[]>();
		
        urlAndAction = new String[2];
        urlAndAction[0] = "/back/operator/logout.vti";
        urlAndAction[1] = "操作员退出";
        needOperationLogPatternListAtPre.add(urlAndAction);
	}
	
	static {
		String[] urlAndAction = null;
		needOperationLogPatternListAtAfter = new LinkedList<String[]>();
		
        urlAndAction = new String[2];
        urlAndAction[0] = "/back/operator/login.vti";
        urlAndAction[1] = "操作员登录";
        needOperationLogPatternListAtAfter.add(urlAndAction);
	}

	/**在进入方法之前执行
	 * @param handler 是下一个拦截器*/
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler){
		String currentForwardUri = request.getRequestURI();
		String actionId = "";
		String actionName = "";
		//防止接口调用session
//		if(currentForwardUri.indexOf("/api") > -1){
//			return true;
//		}
		Operator theOperator = SessionManager.getCurrentOperator(request);
		if(theOperator != null){
			boolean needLog = false;
			for (int i = 0; i < needOperationLogPatternListAtPre.size(); i++) {
				String[] actionProperties = needOperationLogPatternListAtPre.get(i);
	    		if (currentForwardUri.indexOf(actionProperties[0]) > -1){
	    			actionName = actionProperties[1];
					needLog = true;
					break;
	    		}
			}
			
			if (needLog) {
				String currentUrl = SessionManager.getCurrentUrl(request);
				if(currentUrl.length() > 7){
					currentUrl = currentUrl.substring(7);
					int beginIndex = currentUrl.indexOf("/");
					int endIndex = currentUrl.indexOf(".vti");
					actionId = currentUrl.substring(beginIndex, endIndex);
				}
				persistOperatorLog(theOperator, currentUrl, actionId, actionName);
			}
		}else{
			//可以根据ex是否为null判断是否发生了异常，进行系统错误日志记录。
		}
		return true;
	}

	/**在生成视图之后执行
	 * @param handler 是下一个拦截器*/
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex){
		String currentForwardUri = request.getRequestURI();
		String actionId = "";
		String actionName = "";
		Operator theOperator = SessionManager.getCurrentOperator(request);
		if(ex == null && theOperator != null){
			boolean needLog = false;
			for (int i = 0; i < needOperationLogPatternListAtAfter.size(); i++) {
				String[] actionProperties = needOperationLogPatternListAtAfter.get(i);
	    		if (currentForwardUri.indexOf(actionProperties[0]) > -1){
	    			actionName = actionProperties[1];
					needLog = true;
					break;
	    		}
			}
			
			if (needLog) {
				String currentUrl = SessionManager.getCurrentUrl(request);
				if(currentUrl.length() > 7){
					currentUrl = currentUrl.substring(7);
					int beginIndex = currentUrl.indexOf("/");
					int endIndex = currentUrl.indexOf(".vti");
					actionId = currentUrl.substring(beginIndex, endIndex);
				}
				persistOperatorLog(theOperator, currentUrl, actionId, actionName);
			}
		}else{
			//可以根据ex是否为null判断是否发生了异常，进行系统错误日志记录。
		}
	}
	
	private int persistOperatorLog(Operator operator, String currentUrl, String actionId, String actionName) {
		if(MyStringUtils.isBlank(currentUrl)){
			return 0;
		}
		IOperatorLogDao operatorLogDao= (IOperatorLogDao) GlobalConfig.getBean("operatorLogDao");
				
		OperatorLog log = new OperatorLog();
		log.setActionId(actionId);
		log.setActionName(actionName);
		log.setActionTime(new Date());
		log.setUrlAndParameters(currentUrl);
		
		if(operator != null){
			log.setOperatorId(operator.getId());
			operatorLogDao.save(log);
		}
		return 1;
	}
}
