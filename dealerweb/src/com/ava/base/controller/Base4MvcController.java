package com.ava.base.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.ava.base.domain.ResponseData;
import com.ava.domain.entity.User;
import com.ava.domain.vo.Tree;
import com.ava.resource.GlobalConstant;
import com.ava.resource.SessionManager;
import com.ava.resource.cache.CacheUserManager;
import com.ava.resource.cache.CacheUserMenuManager;
import com.ava.resource.security.UserToken;
import com.ava.util.MyBeanUtils;
import com.ava.util.TypeConverter;

/**整个系统的控制器类的基类*/
public class Base4MvcController {

	@Autowired
	private HttpServletRequest request;
	
	private ThreadLocal<HttpServletResponse> responseLocal = new ThreadLocal<HttpServletResponse>();
		
	/**
	 * 加载快捷菜单
	 * @author liuq 
	 * @version 0.1 
	 * @param rd
	 */
	public void siteMenuType(ResponseData rd) {
		rd = siteMenuType();
		request.setAttribute("menuId", rd.get("menuId"));
		request.setAttribute("menuType", rd.get("menuType"));
		request.setAttribute("userMenuRelationVOList", rd.get("userMenuRelationVOList"));
	}
	
	public void siteMenuType(Model model, ResponseData rd) {
		rd = siteMenuType();
		model.addAttribute("menuId", rd.get("menuId"));
		model.addAttribute("menuType", rd.get("menuType"));
		model.addAttribute("userMenuRelationVOList", rd.get("userMenuRelationVOList"));
	}
	
	public Base4MvcController(){
	}

	/**Spring会根据注解注入request和response*/
	@ModelAttribute
	public void setRequestAndResponse(HttpServletResponse response){
		responseLocal.set(response);
//		this.response = response;
	}
	
	protected HttpServletRequest getRequest(){
		return request;
	}

	protected HttpServletResponse getResponse(){
		return responseLocal.get();
	}
    
	/**输出指定类型的字符串到response对象，并要指定编码*/
    private void writeStr(String formatType, String responseStr, String charset) {
		PrintWriter pw = null;
		try{
			if(TypeConverter.isEmpty(formatType)){
				throw new RuntimeException("formatType is null!");
			}
			if(getResponse() == null){
				throw new RuntimeException("response is null when write string to response !");
			}
			getResponse().setContentType("text/" + formatType + ";charset=" + charset);
			pw = getResponse().getWriter();
			pw.write(responseStr);
		}catch(IOException ie){
			throw new RuntimeException("Occur an error when write xml to response !");
		}finally{
			try{
			if (pw != null)
				pw.close();
			}catch(NullPointerException ie){
				System.out.println( " response writer is null" );
			}
		}
	}

	/**输出指定类型的字符串到response对象*/
    private void writeStr(String formatType, String responseStr) {
    	writeStr(formatType, responseStr, "UTF-8");
    }

    /** listObjName：为responseData.data中含有的列表类型对象的别名 */
    private void writeRd(String formatType, ResponseData responseData, String listObjName) {
    	String rdStr = "";
		if(TypeConverter.isEmpty(formatType)){
			throw new RuntimeException("formatType is null!");
		}
		if(formatType.equalsIgnoreCase("json")){
			//默认json格式
			String jsonStr = MyBeanUtils.toJson(responseData);
			// 去掉json串包裹的头尾中括号
			if (jsonStr != null && jsonStr.length() > 2) {
				jsonStr = jsonStr.substring(1);
				jsonStr = jsonStr.substring(0, jsonStr.length() - 1);
			}
			rdStr = jsonStr;
		}else{
			//xml格式
		}
		writeStr(formatType, rdStr);
    }
    
    /** 将map转换为json数据 */
    @SuppressWarnings("rawtypes")
	private void writeMap(String formatType, Map responseData, String listObjName) {
    	String rdStr = "";
		if(TypeConverter.isEmpty(formatType)){
			throw new RuntimeException("formatType is null!");
		}
		if(formatType.equalsIgnoreCase("json")){
			//默认json格式
			rdStr = MyBeanUtils.toJson(responseData);
		}else{
			//xml格式
		}
		writeStr(formatType, rdStr);
    }
    

    /** listObjName：为responseData.data中含有的列表类型对象的别名 */
    protected void writeRd(ResponseData responseData, String listObjName) {
    	String formatType = getStringParameter("formatType");
		if(TypeConverter.isEmpty(formatType)){
			formatType = "json";
		}
    	writeRd(formatType, responseData, listObjName);
    }

    public void writeRd(ResponseData responseData) {
    	String formatType = getStringParameter("formatType");
		if(TypeConverter.isEmpty(formatType)){
			formatType = "json";
		}
    	writeRd(formatType, responseData, null);
    }
    
    public void writeRd(List<Tree> treeList) {
    	String formatType = getStringParameter("formatType");
		if(TypeConverter.isEmpty(formatType)){
			formatType = "json";
		}
		writeList(formatType, treeList);
    }
    
    /** 将lsit转换为json数据 */
    @SuppressWarnings("rawtypes")
	private void writeList(String formatType, List list) {
    	String rdStr = "";
		if(TypeConverter.isEmpty(formatType)){
			throw new RuntimeException("formatType is null!");
		}
		if(formatType.equalsIgnoreCase("json")){
			//默认json格式
			rdStr = MyBeanUtils.toJson(list);
		}else{
			//xml格式
		}
		writeStr(formatType, rdStr);
    }
    
    @SuppressWarnings("rawtypes")
	public void writeMap(Map responseData) {
    	String formatType = getStringParameter("formatType");
		if(TypeConverter.isEmpty(formatType)){
			formatType = "json";
		}
		writeMap(formatType, responseData, null);
    }
    
	/** 单条消息传递,key缺省*/
	protected void message(String messageText){
		if(TypeConverter.isEmpty(messageText)){
			return;
		}
    	message(GlobalConstant.MSG_RESULT_LEVEL_SUCCESS, GlobalConstant.MSG_RESULT_CONTENT, messageText);
    }
	
	/** 单条消息传递，key指定*/
    protected void message(String messageKey, String messageText){
		if(TypeConverter.isEmpty(messageText)){
			return;
		}
    	message(GlobalConstant.MSG_RESULT_LEVEL_SUCCESS, messageKey, messageText);
    }

	/** 单条消息传递，key和提示级别指定*/
    @SuppressWarnings({ "unchecked", "rawtypes" })
	protected void message(String messageLevel, String messageKey, String messageText){
		if(TypeConverter.isEmpty(messageText)){
			return;
		}
		String intLevel = GlobalConstant.MSG_RESULT_LEVEL_SUCCESS;
		if(StringUtils.isNotEmpty(messageLevel)){
    		intLevel = messageLevel;
    	}
    	request.setAttribute(GlobalConstant.MSG_RESULT_LEVEL, intLevel);
		
    	Map<String, String> actionMessageMap = null;
    	actionMessageMap = (Map)(request).getAttribute(GlobalConstant.MSG_RESULT_MAP_ID);
        if (actionMessageMap == null) {
        	actionMessageMap = new HashMap<String, String>();
        }
        actionMessageMap.put(messageKey, messageText);
        request.setAttribute(GlobalConstant.MSG_RESULT_MAP_ID, actionMessageMap);
    }
    
    /** 单条消息传递，key和提示级别指定*/
    @SuppressWarnings({ "unchecked", "rawtypes" })
	protected void message(Model model, String messageLevel, String messageKey, String messageText){
		if(TypeConverter.isEmpty(messageText)){
			return;
		}
		String intLevel = GlobalConstant.MSG_RESULT_LEVEL_SUCCESS;
		if(StringUtils.isNotEmpty(messageLevel)){
    		intLevel = messageLevel;
    	}
		model.addAttribute(GlobalConstant.MSG_RESULT_LEVEL, intLevel);
		
    	Map<String, String> actionMessageMap = null;
    	actionMessageMap = (Map)(request).getAttribute(GlobalConstant.MSG_RESULT_MAP_ID);
        if (actionMessageMap == null) {
        	actionMessageMap = new HashMap<String, String>();
        }
        actionMessageMap.put(messageKey, messageText);
        model.addAttribute(GlobalConstant.MSG_RESULT_MAP_ID, actionMessageMap);
    }

	/** 从request中获取整形参数*/
	protected Integer getIntegerParameter(String parameterKey){
		String value = null;
		value = (String) request.getParameter(parameterKey);
		Integer result = null;
		try {
			result = new Integer(value);
		} catch (Exception e) {
			result = new Integer(0);
		}		
		return result;
	}

	/** 从request中获取字符串参数*/
	protected String getStringParameter(String parameterKey){
		String value = null;
		try {
			value = (String) request.getParameter(parameterKey);
		} catch (Exception e) {
			
		}		
		if (value == null) {
			value = "";
		}	
		return value;
	}

	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}

	public void setResponse(HttpServletResponse response) {
		responseLocal.set(response);
		//this.response = response;
	}
	
	public Integer getUserIdFromToken(){
		Object userTokenObj = request.getAttribute(GlobalConstant.API_USER_LOGIN_TOKEN);
		UserToken userToken = null;
		if(userTokenObj != null){
			userToken = (UserToken)userTokenObj;
		}
		return userToken == null ? null : userToken.getUserAccountId();
	}
	
	/** Session中存储的用户ID */
	public Integer getCurrentUserId() {
		if (request.getSession().getAttribute(GlobalConstant.SESSION_USER_ID) == null) {
			//如果会话中没有用户ID，则根据Token获取
			return getUserIdFromToken();
		}
		return (Integer) request.getSession().getAttribute(
				GlobalConstant.SESSION_USER_ID);
	}
	
	public Integer getCurrentCompanyId() {
		if (getCurrentUser() == null) {
			return null;
		}
		return getCurrentUser().getCompanyId();
	}
	
	public User getCurrentUser() {
		return CacheUserManager.getUser(getCurrentUserId());
	}
	
	public ResponseData siteMenuType() {
		ResponseData rd = new ResponseData();
		Integer userId = SessionManager.getCurrentUserId(request);
		String menuType = String.valueOf(GlobalConstant.FALSE);
		String menuId = String.valueOf(GlobalConstant.FALSE);
		
		if (request.getParameter("menuId") != null && request.getParameter("menuId").length() > 0) {
			menuId = request.getParameter("menuId");
		} 
		
		if (request.getParameter("menuType") != null && request.getParameter("menuType").length() > 0) {
			menuType = request.getParameter("menuType");
			if (menuType.equals("1.jsp")) {
				menuType = menuType.substring(0,menuType.indexOf(".jsp"));
			}
			rd.put("userMenuRelationVOList", CacheUserMenuManager.getUserMenuRelationVOList(userId));
		}
		
		rd.put("menuId", menuId);
		rd.put("menuType", menuType);
		return rd;
	}
}
