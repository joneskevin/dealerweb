package com.ava.servlet.filter;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ava.base.controller.Base4MvcController;
import com.ava.base.dao.util.ParameterUtil;
import com.ava.dao.IUserDao;
import com.ava.dealer.service.IUserTokenService;
import com.ava.domain.entity.User;
import com.ava.domain.vo.UserTokenVO;
import com.ava.resource.GlobalConfig;
import com.ava.resource.GlobalConstant;
import com.ava.resource.SessionManager;
import com.ava.resource.SpringContext;
import com.ava.util.CookieProcess;
import com.ava.util.DateTime;
import com.ava.util.UrlUtil;


/**登录过滤器类，根据web.xml配置，根据控制列表拦截请求，进行是否登录判断等
 * 注意：过滤器映射中的url模式是这样的：<url-pattern>/*</url-pattern>，去掉斜杠后面的星号就不会进入该过滤器了。*/
public class LoginFilter extends HttpServlet implements Filter {

	private static final long serialVersionUID = -3573598311190168925L;

	private FilterConfig filterConfig;
	
	private IUserTokenService userTokenService;

    static final private String CONTENT_TYPE = "text/html; charset=utf-8";
    
    // Handle the passed-in FilterConfig
    public void init(FilterConfig filterConfig) {
        this.filterConfig = filterConfig;
    }

    public void destroy() {
        filterConfig = null;
    }

    public void doFilter(ServletRequest srequest, ServletResponse sresponse,
            FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) srequest;
        HttpServletResponse response = (HttpServletResponse) sresponse;
        response.setContentType(CONTENT_TYPE);
        String uri = request.getRequestURI();
       	if (uri.indexOf("/back") > -1) {
        		filterBackground(request, response, filterChain);
        } else if(uri.indexOf("/api") > -1){
	        	filterApiLayer(request, response, filterChain);
        } else {
        	filterForeground(request, response, filterChain);
        }
    }
    
    /**	管理员后台受保护的目录或页面	*/
    private static List<Object> privateListBackground = null;
    /**	管理员后台允许访问的特殊页面，主要用于访问存在于受限目录中的特殊网页接口	*/
    private static List<Object> permissibleListBackground = null;
    static {
        privateListBackground = new LinkedList<Object>();
        privateListBackground.add("/back");

        permissibleListBackground = new LinkedList<Object>();
        permissibleListBackground.add("/back/operator/login.vti");
    }

	/**	用户前台受保护的目录或页面	*/
    private static List<Object> privateListForeground = null;
    /**	用户前台允许访问的特殊页面，主要用于访问存在于受限目录中的特殊网页接口	*/
    private static List<Object> permissibleListForeground = null;
    
    static {
    	privateListForeground = new LinkedList<Object>();
	    privateListForeground.add("/base/baseHelp/"+","+GlobalConstant.SESSION_LOGIN_FORWARD_TYPE_COMMON);
	    privateListForeground.add("/base/baseSystem/"+","+GlobalConstant.SESSION_LOGIN_FORWARD_TYPE_COMMON);
	    privateListForeground.add("/base/baseVehicle/"+","+GlobalConstant.SESSION_LOGIN_FORWARD_TYPE_COMMON);

		permissibleListForeground = new LinkedList<Object>();
    	permissibleListForeground.add("/base/registerLogin/displayLogin.vti");
    	permissibleListForeground.add("/base/registerLogin/login.vti");
    	permissibleListForeground.add("/dealer/proposal/displayConfirmInstallationResult.vti");
    	permissibleListForeground.add("/dealer/proposal/confirmInstallationResult.vti");
    	permissibleListForeground.add("/dealer/reportBase/extraction.vti");
    }  
    /**	api允许访问的特殊页面	*/
    private static List<Object> permissibleListApi = null; 
    static {
    	
    	permissibleListApi = new LinkedList<Object>();
    	permissibleListApi.add("/api/user/login");
    	permissibleListApi.add(GlobalConstant.BASE_SYSTEM_NAME+"/api/web2gateway/handleMessage.vti");
    	permissibleListApi.add(GlobalConstant.BASE_SYSTEM_NAME+"/api/web2gateway/refreshToken.vti");
    	permissibleListApi.add(GlobalConstant.BASE_SYSTEM_NAME+"/api/testDriveApp/");
    	permissibleListApi.add(GlobalConstant.BASE_SYSTEM_NAME+"/api/fileList/download.vti");
    }

    /**	api层访问控制方法 
     * @throws ServletException */
    private void filterApiLayer(HttpServletRequest request,HttpServletResponse response,FilterChain filterChain) throws IOException, ServletException {        
    	String uri = request.getRequestURI();
    	boolean permissibleFlag = false;//默认为false
    	boolean permissibleUri=false;

		// 检查是否是允许访问的接口
		for (int i = 0; i < permissibleListApi.size(); i++) {
			if (uri.indexOf(permissibleListApi.get(i).toString()) > -1) {
				permissibleUri=true;
				break;
			}
		}
		if((uri.indexOf("/web2gateway/refreshToken") > -1 || uri.indexOf("/web2gateway/handleMessage") > -1) && permissibleUri){
			permissibleFlag = true;
		}else if(uri.indexOf("fileList/download") > -1 && permissibleUri){
			permissibleFlag = true;
		}else if(uri.indexOf("/testDriveApp") > -1 && permissibleUri){
			String tokenString = ParameterUtil.getStringParameter(request, "accessToken");
			String userName = ParameterUtil.getStringParameter(request, "userName");
	    	if(null==userTokenService){
	    		userTokenService=SpringContext.getBean("userTokenService");
	    	}
			UserTokenVO userTokenVO=userTokenService.getToken(userName, tokenString);
			if(userTokenService.checkUserTokenValid(userTokenVO)){
				permissibleFlag = true;
				request.setAttribute(userName, tokenString);
			}
		}else{
			permissibleFlag = false;
		}
        if (permissibleFlag) {
//            try {
                filterChain.doFilter(request, response);
//            } catch (ServletException sx) {
//                filterConfig.getServletContext().log(sx.getMessage());
//            } catch (IOException iox) {
//                filterConfig.getServletContext().log(iox.getMessage());
//            }
        } else {
        	Map<String,String> responseData=new HashMap<String,String>();
        	responseData.put("resultCode", "10013");
        	responseData.put("desc", "非法地址");
        	responseData.put("data", null);
        	Base4MvcController baseAcion = new Base4MvcController();
        	baseAcion.setResponse(response);
        	baseAcion.writeMap(responseData);
        }     
    }    
    
    /**	管理员后台页面访问控制方法	
     * @throws ServletException */
    private void filterBackground(HttpServletRequest request,HttpServletResponse response,FilterChain filterChain) throws IOException, ServletException {
    	String uri = request.getRequestURI();
    	boolean permissibleFlag = false;//默认为false
        HttpSession httpSession=request.getSession(true);
        Object user = httpSession.getAttribute(GlobalConstant.SESSION_OPERATOR);
        if ( user != null ) {
        	permissibleFlag = true;
        } else {
        	if(null==SessionManager.getSessionCache().get(httpSession.getId())){
        		SessionManager.getSessionCache().put(httpSession.getId(), httpSession);
        	}
        	for (int i=0; i<privateListBackground.size(); i++ ){
        		if (uri.indexOf(privateListBackground.get(i).toString())>-1 ){
                	permissibleFlag = false;
                	break;
        		}
        	}           	
        	for (int i=0; i<permissibleListBackground.size(); i++ ){
        		if (uri.indexOf(permissibleListBackground.get(i).toString())>-1 ){
                	permissibleFlag = true;
                	break;
        		}
        	}
        }
        if (permissibleFlag) {
//            try {
                filterChain.doFilter(request, response);
//            } catch (ServletException sx) {
//                filterConfig.getServletContext().log(sx.getMessage());
//            } catch (IOException iox) {
//                filterConfig.getServletContext().log(iox.getMessage());
//            }
        } else {
            response.sendRedirect(request.getContextPath() + "/back/login.jsp");
        }    
    }
    
    /**	用户前台页面访问控制方法
     * @throws ServletException */
    private void filterForeground(HttpServletRequest request,HttpServletResponse response,FilterChain filterChain) throws IOException, ServletException {

    	boolean permissibleFlag = false;//默认为false
    	
    	HttpSession httpSession=request.getSession(true);
    	if(null==SessionManager.getSessionCache().get(httpSession.getId())){
    		SessionManager.getSessionCache().put(httpSession.getId(), httpSession);
    	}
        if (SessionManager.isLogined(request) ){
        	permissibleFlag = true;
        }
    	
    	/**	当前访问页面	*/
		String currentForwardUri = UrlUtil.getFullURI(request);

        if ( !SessionManager.isLogined(request) ){
        	//如果用户还未登录，尝试访问cookie来验证是否记住在线状态
    		//cookieLoginProcess(request, response);
        }
        //做完cookie验证后，再次判断用户是否登录，如果仍未登录则开始进行访问控制检查
        if ( !SessionManager.isLogined(request) ){    		
    		//首先检查是否受保护的目录或页面
        	for (int i=0; i<privateListForeground.size(); i++ ){
        		String row = (String) privateListForeground.get(i);
        		String[] pathAndType = row.split(",");
        		String uriPath = pathAndType[0];
        		String forwardType = pathAndType[1];
        		if (currentForwardUri.indexOf(uriPath)>-1 ){
        			//把当前访问页面放到request中，用来登录后返回
            		request.getSession().setAttribute(GlobalConstant.SESSION_LOGIN_FORWARD_TYPE,forwardType);
        			request.getSession().setAttribute(GlobalConstant.SESSION_LOGIN_FORWARD_URL,currentForwardUri);
                	permissibleFlag = false;
                	break;
        		}
        	} 	
        	//如果没有访问权限，则检查是否允许访问的特殊页面
        	if( !permissibleFlag ){
            	for (int i=0; i<permissibleListForeground.size(); i++ ){
            		if (currentForwardUri.indexOf(permissibleListForeground.get(i).toString())>-1 ){
                    	permissibleFlag = true;
                    	break;
            		}
            	}
        	}
        }
        
        if (permissibleFlag) {
			//更新用户最后一次操作时间
			SessionManager.updateLastOperationTime(request);

//	        try {
	            filterChain.doFilter(request, response);
//	        } catch (ServletException sx) {
//	            filterConfig.getServletContext().log(sx.getMessage());
//	        } catch (IOException iox) {
//	            filterConfig.getServletContext().log(iox.getMessage());
//	        }
        } else {
        	//clean session
    		request.getSession().removeAttribute(GlobalConstant.SESSION_USER_ID);
    		SessionManager.getSessionCache().remove(request.getSession().getId());
    		// clean cookie
    		CookieProcess.removeCookie(GlobalConstant.COOKIE_USER_REMEMBER_ME,response);
        	//跳到登录页面
			response.sendRedirect(request.getContextPath() + "/base/registerLogin/displayLogin.vti?url_from_=LoginFilter");
        }
    }
    
    /**	从cookie读取数据，判断用户是否记住在线状态，进行登录行为	*/
    private void cookieLoginProcess(HttpServletRequest request, HttpServletResponse response){
    	String cookieRememberMe = CookieProcess.getCookieValue(GlobalConstant.COOKIE_USER_REMEMBER_ME, request);
		String cookieLoginName = CookieProcess.getCookieValue(GlobalConstant.COOKIE_USER_LOGIN_NAME, request);
		String cookiePseudoPassword = CookieProcess.getCookieValue(GlobalConstant.COOKIE_USER_PSEUDO_PASSWORD, request);
    	if("111".equalsIgnoreCase(cookieRememberMe) && cookiePseudoPassword!=null){      
        	IUserDao userDao = (IUserDao) GlobalConfig.getBean("userDao");
    		User theUser = userDao.getByLoginName(cookieLoginName);
    		if(theUser != null){
    			String pseudoPassword = theUser.getPseudoPassword();
    			if (cookiePseudoPassword.equals(pseudoPassword)){
    				//cookie保存的伪密码和数据库一致，则允许cookie登录，把用户信息写入session中  				
    				SessionManager.setCurrentUserInfo(request, response, theUser, cookieRememberMe);
    				//记录用户最后登录时间
    				theUser.setLastLoginTime(DateTime.getTimestamp());  	
    				userDao.edit(theUser);		        			
    			}else{
    				//别处机器已登录过该用户名，此机器需要重新登录
    			}
    		}
    	}
    }
    
    /**	临时从cookie读取数据，判断用户是否记住在线状态，进行登录行为	*/
    private void tempCookieLoginProcess(HttpServletRequest request, HttpServletResponse response){
    	String cookieTempLogin = CookieProcess.getCookieValue("tempLogin", request);
    	if("welcome".equalsIgnoreCase(cookieTempLogin)){
    		//cookie记录的临时密码正确
    		request.getSession().setAttribute("tempLogin", "welcome");
    	}
    }
    
}
