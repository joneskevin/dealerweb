package com.ava.resource;

import java.io.Serializable;
import java.util.Date;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ava.dao.impl.UserRoleRelationDao;
import com.ava.domain.entity.Company;
import com.ava.domain.entity.Operator;
import com.ava.domain.entity.User;
import com.ava.resource.cache.CacheOrgManager;
import com.ava.resource.cache.CacheUserManager;
import com.ava.resource.security.UserToken;
import com.ava.util.DateTime;
import com.ava.util.MyStringUtils;
import com.ava.util.TypeConverter;
import com.ava.util.UrlUtil;
import com.ava.util.ValidationCodeUtil;

/**会话管理类*/
public class SessionManager implements Serializable {

	public static java.util.HashMap sessionCache = new java.util.HashMap();
	
	private static UserRoleRelationDao userRoleRelationDao=(UserRoleRelationDao) GlobalConfig.getBean("userRoleRelationDao");

	public static java.util.HashMap getSessionCache() {
		return sessionCache;
	}

	/** 把用户信息写入session */
	static public void setCurrentUserInfo(HttpServletRequest request,
			HttpServletResponse response, User user, String rememberMe) {
		if (rememberMe != null && "111".equalsIgnoreCase(rememberMe)) {
			// 记住在线状态，生成新的伪密码，做下次的cookie验证用
			String pseudoPassword = ValidationCodeUtil.nextValidationCode();
			user.setPseudoPassword(pseudoPassword);
			// 写入cookie中
			/**
			CookieProcess.setCookieValue(GlobalConstant.COOKIE_USER_LOGIN_NAME,
					user.getLoginName(), response);
			CookieProcess.setCookieValue(
					GlobalConstant.COOKIE_USER_REMEMBER_ME, rememberMe,
					response);
			CookieProcess.setCookieValue(
					GlobalConstant.COOKIE_USER_PSEUDO_PASSWORD, pseudoPassword,
					response);
			**/
		}
		// 把用户信息写入session
		request.getSession().setAttribute(GlobalConstant.SESSION_USER_ID,
				user.getId());
	}
	
	/** 把登录密码写入session */
	static public void setCurrentPseudoPassword(HttpServletRequest request,
			String pseudoPassword) {
		request.getSession().setAttribute(GlobalConstant.SESSION_USER_PSEUDO_PASSWORD,
				pseudoPassword);
	}
	
	static public String getCurrentPseudoPassword(HttpServletRequest request) {
		if (request.getSession().getAttribute(GlobalConstant.SESSION_USER_PSEUDO_PASSWORD) == null) {
			return null;
		}
		return (String) request.getSession().getAttribute(
				GlobalConstant.SESSION_USER_PSEUDO_PASSWORD);
	}

	static private Integer getUserIdFromToken(HttpServletRequest request){
		Object userTokenObj = request.getAttribute(GlobalConstant.API_USER_LOGIN_TOKEN);
		UserToken userToken = null;
		if(userTokenObj != null){
			userToken = (UserToken)userTokenObj;
		}
		return userToken == null ? null : userToken.getUserAccountId();
	}
	
	/** Session中存储的用户ID */
	static public Integer getCurrentUserId(HttpServletRequest request) {
		if (request.getSession().getAttribute(GlobalConstant.SESSION_USER_ID) == null) {
			//如果会话中没有用户ID，则根据Token获取
			return getUserIdFromToken(request);
		}
		return (Integer) request.getSession().getAttribute(
				GlobalConstant.SESSION_USER_ID);
	}

	/** 判断用户是否登录 */
	static public boolean isLogined(HttpServletRequest request) {
		Integer userId = getCurrentUserId(request);
		if (userId != null && userId.intValue() > 0) {
			return true;
		}
		return false;
	}

	/** 更新用户最后一次操作时间，要排除掉浏览器自动请求的Uri */
	static public void updateLastOperationTime(HttpServletRequest request) {
		Integer userId = getCurrentUserId(request);
		if (userId == null || userId.intValue() <= 0) {
			return;
		}

		List irrespectiveList = new LinkedList<Object>();
		irrespectiveList.add("/checkNew.");

		String currentForwardUri = UrlUtil.getFullURI(request);
		for (int i = 0; i < irrespectiveList.size(); i++) {
			if (currentForwardUri.indexOf(irrespectiveList.get(i).toString()) > -1) {
				continue;
			} else {
				request.getSession().setAttribute(
						GlobalConstant.SESSION_USER_LAST_OPERATION_TIME,
						new Date());
				//System.out.println(DateTime.getNormalDateTime() + ":" + currentForwardUri);
				break;
			}
		}
	}

	static public User getCurrentUser(HttpServletRequest request) {
		return CacheUserManager.getUser(getCurrentUserId(request));
	}

	static public String getCurrentUserLoginName(HttpServletRequest request) {
		if (getCurrentUser(request) == null) {
			return null;
		}
		return getCurrentUser(request).getLoginName();
	}

	static public String getCurrentUserNickName(HttpServletRequest request) {
		if (getCurrentUser(request) == null) {
			return null;
		}
		return getCurrentUser(request).getNickName();
	}

	static public Integer getCurrentPositionId(HttpServletRequest request) {
		if (getCurrentUser(request) == null) {
			return null;
		}
		return getCurrentUser(request).getPositionId();
	}

	static public Integer getCurrentCompanyId(HttpServletRequest request) {
		if (getCurrentUser(request) == null) {
			return null;
		}
		Integer companyId = getCurrentUser(request).getCompanyId();
		return companyId;
	}

	static public String getCurrentCompanyObName(HttpServletRequest request) {
		if (CacheOrgManager.get(getCurrentCompanyId(request)) == null) {
			return null;
		}
		Company org = (Company) CacheOrgManager
				.get(getCurrentCompanyId(request));
		return org.getObName();
	}

	static public String getCurrentCompanyCnName(HttpServletRequest request) {
		if (CacheOrgManager.get(getCurrentCompanyId(request)) == null) {
			return null;
		}
		Company org = (Company) CacheOrgManager
				.get(getCurrentCompanyId(request));
		return org.getCnName();
	}

	static public String getCurrentCompanyName(HttpServletRequest request) {
		if (CacheOrgManager.get(getCurrentCompanyId(request)) == null) {
			return null;
		}
		Company org = (Company) CacheOrgManager
				.get(getCurrentCompanyId(request));
		return org.getName();
	}

	static public void setCurrentProjectId(HttpServletRequest request,
			Integer projectId) {
		request.getSession().setAttribute(GlobalConstant.SESSION_PROJECT_ID,
				projectId);
	}

	static public Integer getCurrentProjectId(HttpServletRequest request) {
		if (request.getSession()
				.getAttribute(GlobalConstant.SESSION_PROJECT_ID) == null) {
			return null;
		}
		return (Integer) request.getSession().getAttribute(
				GlobalConstant.SESSION_PROJECT_ID);
	}

	static public void setCurrentTaskId(HttpServletRequest request,
			Integer projectId) {
		request.getSession().setAttribute(GlobalConstant.SESSION_TASK_ID,
				projectId);
	}

	static public Integer getCurrentTaskId(HttpServletRequest request) {
		if (request.getSession().getAttribute(GlobalConstant.SESSION_TASK_ID) == null) {
			return null;
		}
		return (Integer) request.getSession().getAttribute(
				GlobalConstant.SESSION_TASK_ID);
	}

	static public void setDefaultProjectId(HttpServletRequest request,
			Integer projectId) {
		request.getSession().setAttribute(
				GlobalConstant.SESSION_DEFAULT_PROJECT_ID, projectId);
	}

	static public Integer getDefaultProjectId(HttpServletRequest request) {
		if (request.getSession().getAttribute(
				GlobalConstant.SESSION_DEFAULT_PROJECT_ID) == null) {
			return null;
		}
		return (Integer) request.getSession().getAttribute(
				GlobalConstant.SESSION_DEFAULT_PROJECT_ID);
	}

	static public void setDefaultTaskId(HttpServletRequest request,
			Integer taskId) {
		request.getSession().setAttribute(
				GlobalConstant.SESSION_DEFAULT_TASK_ID, taskId);
	}

	static public Integer getDefaultTaskId(HttpServletRequest request) {
		if (request.getSession().getAttribute(
				GlobalConstant.SESSION_DEFAULT_TASK_ID) == null) {
			return null;
		}
		return (Integer) request.getSession().getAttribute(
				GlobalConstant.SESSION_DEFAULT_TASK_ID);
	}

	static public void setCurrentForumId(HttpServletRequest request,
			Integer forumId) {
		request.getSession().setAttribute(GlobalConstant.SESSION_FORUM_ID,
				forumId);
	}

	static public void setCurrentGateUrl(HttpServletRequest request,
			String gateUrl) {
		request.getSession().setAttribute(GlobalConstant.SESSION_GATE_URL,
				gateUrl);
	}

	static public String getCurrentGateUrl(HttpServletRequest request) {
		if (request.getSession().getAttribute(GlobalConstant.SESSION_GATE_URL) == null) {
			return null;
		}
		return (String) request.getSession().getAttribute(
				GlobalConstant.SESSION_GATE_URL);
	}

	static public Integer getCurrentForumId(HttpServletRequest request) {
		if (request.getSession().getAttribute(GlobalConstant.SESSION_FORUM_ID) == null) {
			return null;
		}
		return (Integer) request.getSession().getAttribute(
				GlobalConstant.SESSION_FORUM_ID);
	}

	static public Operator getCurrentOperator(HttpServletRequest request) {
		if (request.getSession().getAttribute(GlobalConstant.SESSION_OPERATOR) != null) {
			Operator operator = (Operator) request.getSession().getAttribute(
					GlobalConstant.SESSION_OPERATOR);
			return operator;
		}
		return null;
	}

	static public Integer getCurrentOperatorId(HttpServletRequest request) {
		Operator operator = getCurrentOperator(request);
		if (operator != null) {
			return operator.getId();
		}
		return null;
	}

	static public String getCurrentOperatorLoginName(HttpServletRequest request) {
		Operator operator = getCurrentOperator(request);
		if (operator != null) {
			return operator.getLoginName();
		}
		return null;
	}

	static public String getCurrentOperatorName(HttpServletRequest request) {
		Operator operator = getCurrentOperator(request);
		if (operator != null) {
			return operator.getName();
		}
		return null;
	}

	public static Integer getMaxRoleId(Integer userId){
		User user=CacheUserManager.getUser(userId);
		if(null==user)
			return null;
		if(null!=user.getMaxUserRight() && user.getMaxUserRight()>=GlobalConstant.HEADQUARTERS_ROLE_ID)
			return user.getMaxUserRight();
		Integer maxRoleId = userRoleRelationDao.getMaxRoleId(user.getId());
		user.setMaxUserRight(maxRoleId);
		return maxRoleId;
	}
	
	private static String setCurrentUrl(HttpServletRequest request) {
		String currentUrl = "";
		StringBuffer url = request.getRequestURL();

		Enumeration parameterNames = request.getParameterNames();
		for (int i = 0; parameterNames.hasMoreElements(); i++) {
			String name = (String) parameterNames.nextElement();
			String[] values = request.getParameterValues(name);
			if (name != null && name.length() > 0 && values != null
					&& values.length > 0) {
				String paraValue = "";
				if (values.length == 1) {
					paraValue = values[0];
				} else {
					paraValue = TypeConverter.join(values, ",");
				}
				if (i == 0) {
					url.append("?");
				} else {
					url.append("&");
				}
				url.append(name + "=" + paraValue);
			}
		}

		if (url != null) {
			currentUrl = url.toString();
		}
		request.setAttribute(GlobalConstant.REQUEST_CURRENT_URL, currentUrl);

		return currentUrl;
	}

	public static String getCurrentUrl(HttpServletRequest request) {
		String currentUrl = TypeConverter.toString(request
				.getAttribute(GlobalConstant.REQUEST_CURRENT_URL));
		if (MyStringUtils.isBlank(currentUrl)) {
			currentUrl = setCurrentUrl(request);
		}
		return currentUrl;
	}

	/**
	 * 判断用户是否在线
	 * 
	 * @param userId
	 * @return
	 */
	public static boolean isOnline(Integer userId) {
		HttpSession sessionOfUser = null;
		Set entries = sessionCache.entrySet();
		Iterator itor = entries.iterator();
		while (itor.hasNext()) {
			Entry entry = (Entry) itor.next();
			HttpSession session = (HttpSession) entry.getValue();
			Integer aUserId = (Integer) session
					.getAttribute(GlobalConstant.SESSION_USER_ID);
			if (aUserId != null && aUserId.equals(userId)) {
				sessionOfUser = session;
			}
		}

		if (sessionOfUser == null
				|| sessionOfUser.getAttribute(GlobalConstant.SESSION_USER_ID) == null) {
			return false;
		} else {
			Date lastOperationTime = (Date) sessionOfUser.getAttribute(GlobalConstant.SESSION_USER_LAST_OPERATION_TIME);
			long secDiff = DateTime.getSecondDifference(lastOperationTime, new Date());
			if(secDiff > sessionOfUser.getMaxInactiveInterval()){
				return false;
			}
			return true;
		}
	}

}
