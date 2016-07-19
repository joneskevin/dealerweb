package com.ava.baseSystem.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.ava.base.domain.ResponseData;
import com.ava.baseSystem.service.IUserRegisterLoginService;
import com.ava.dao.ICompanyDao;
import com.ava.dao.IUserDao;
import com.ava.domain.entity.Company;
import com.ava.domain.entity.User;
import com.ava.resource.GlobalConfig;
import com.ava.resource.GlobalConstant;
import com.ava.resource.SessionManager;
import com.ava.util.CookieProcess;
import com.ava.util.DateTime;
import com.ava.util.MailUtil;
import com.ava.util.MyBeanUtils;
import com.ava.util.TypeConverter;
import com.ava.util.ValidationCodeUtil;
import com.ava.util.encrypt.EncryptUtil;

@Service
public class UserRegisterLoginService implements IUserRegisterLoginService {
    
	@Autowired
	private IUserDao userDao;
	
	@Autowired
	private ICompanyDao companyDao;
	
	public boolean obNameIsExistence(String obName) {
		if (obName == null){
			return false;
		}
		HashMap parameters = new HashMap();
		parameters.put("obName", obName);
		List result = companyDao.findNatively(parameters);
		if (result != null && result.size() > 0) {
			return true;
		}
		return false;
	}
	
	public boolean emailIsExistence(String firstEmail){
		HashMap parameters = new HashMap();
		parameters.put("email", firstEmail);
		List result = userDao.find(parameters, "");
		if (result != null && result.size() > 0) {
			return true;
		}
		return false;
		
	}
	
	public ResponseData fetchPassword(String email) {
		ResponseData rd = new ResponseData(0);
		User currentUser = null;
		HashMap parameters = new HashMap();
		parameters.put("email", email);
		List users = userDao.find(parameters, "");
		if (users != null && users.size() > 0) {
			currentUser = (User) users.get(0);
		}
		if(currentUser == null){
			rd.setCode(-1);
			return rd;
		}		
		
		//随机一个临时重置密码，等待用户激活
		currentUser.setTempPassword( ValidationCodeUtil.nextValidationCode(6) );
		//重置临时密码有效时间
		currentUser.setTempPasswordExpiration(DateTime.getTimestamp());
		userDao.edit(currentUser);
		
		rd.put("user", currentUser);
		
		rd.setCode(1);
		return rd;
	}
	
	public ResponseData reActivation(String email) {
		ResponseData rd = new ResponseData(0);
		User currentUser = null;
		HashMap parameters = new HashMap();
		parameters.put("email", email);
		List users = userDao.find(parameters, "");
		if (users != null && users.size() > 0) {
			currentUser = (User) users.get(0);
		}
		if(currentUser == null){
			rd.setCode(-1);
			return rd;
		}		
		
		//随机一个临时重置密码，等待用户激活
		currentUser.setTempPassword( ValidationCodeUtil.nextValidationCode(6) );
		//重置临时密码有效时间
		currentUser.setTempPasswordExpiration(DateTime.getTimestamp());
		userDao.edit(currentUser);
		
		rd.put("user", currentUser);
		
		rd.setCode(1);
		return rd;
	}
	
	public ResponseData activeTempPassword(String loginName, String tempPassword) {
		ResponseData rd = new ResponseData(0);
		User theUser = null;
		HashMap parameters = new HashMap();
		if ( tempPassword==null || "".equals(tempPassword) ){
			rd.setCode(-1);
			rd.setMessage("临时密码为空");
			return rd;
		}
		
		parameters.put("loginName", loginName);
		parameters.put("tempPassword", tempPassword);
		List users = userDao.find(parameters, "");
		if (users != null && users.size() > 0) {
			theUser = (User) users.get(0);
			//如果用户超过三小时未激活账户，则激活失败
			int second=GlobalConstant.TEMP_PASSWORD_EXPIRATION*3600;
			int secondResult=(int) DateTime.getSecondDifference(theUser.getTempPasswordExpiration(),new Date());
			System.out.println(secondResult);
		    if(secondResult>second){
		    	rd.setCode(-2);
		    	rd.setMessage("激活失败");
				return rd;
		    }
		}
		
		if(theUser == null){
			rd.setCode(-3);
			rd.setMessage("用户对象为空");
			return rd;
		}
		//激活过密码的用户登录是否修改密码打开
		theUser.setIsModifyPassword(GlobalConstant.FALSE);
		//用临时重置密码替换用户旧密码
		theUser.setEncryptedPassword(EncryptUtil.encryptPassword(tempPassword));
		//同时清空临时密码防止再次激活email
		theUser.setTempPassword(null);
		userDao.edit(theUser);
		
		rd.setCode(1);
		return rd;
	}
	
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public ResponseData login(String loginName, String password, String isRememberMe, 
			HttpServletRequest request, HttpServletResponse response){
		ResponseData rd = new ResponseData(0);
		User dbUser = existTheUser(loginName, password);
		if (dbUser != null && dbUser.getLoginName() != null) {
			//判断用户是否禁止登录
			if (dbUser.getIsDisable() != null && dbUser.getIsDisable() == GlobalConstant.TRUE) {
				rd.setCode(-1);
				rd.setMessage("您已被禁止登录系统，请联系客服");
				return rd;
			}
			
			//把登录密码写入session
			if (password == null || password.length() == 0 ) {
				password = SessionManager.getCurrentPseudoPassword(request);
			} else {
				SessionManager.setCurrentPseudoPassword(request, password);
			}
			
			Company org = companyDao.get(dbUser.getCompanyId());
			//把需要的用户信息写入session
			SessionManager.setCurrentUserInfo(request, response, dbUser, isRememberMe);
			//记录用户最后登录时间
			dbUser.setLastLoginTime(DateTime.getTimestamp());  	
			userDao.edit(dbUser);
			//记录组织最后登录时间
			if (org != null){					
				org.setLastLoginTime(new Date());
				companyDao.edit(org);
			}
			
			rd.setFirstItem(dbUser);
			rd.setCode(1);
			return rd;
		} else {
			rd.setCode(-1);
			rd.setMessage("用户名或者密码不正确");
			return rd;
		}
	}	
	
	/**	是否存在该用户，存在返回用户对象，不存在返回null	*/
	private User existTheUser(String loginName, String password) {
		if (!TypeConverter.sizeLagerZero(loginName) || !TypeConverter.sizeLagerZero(password)) {
			return null;
		}
		User theUser = userDao.getByLoginName(loginName);
		if (theUser!=null){
			if(password.equals(EncryptUtil.decryptPassword(theUser.getEncryptedPassword()))){
				return theUser;
			}
		}
		return null;
	}

	public int logout(HttpServletRequest request, HttpServletResponse response){
		//clean session
		request.getSession().removeAttribute(GlobalConstant.SESSION_USER_ID);
		// clean cookie
		CookieProcess.removeCookie(GlobalConstant.COOKIE_USER_REMEMBER_ME,response);
		return 1;
	}	
	
	public ResponseData editFirstLoginPassword(User formUser, String pagePassword) {
		ResponseData rd = new ResponseData(0);
		
		Integer userId = formUser.getId();
		User dbUser = userDao.get(userId);
		if (formUser == null || dbUser == null) {
			rd.setCode(0);
			rd.setMessage("用户对象为空！");
			return rd;
		}
		
		if(dbUser.getIsModifyPassword() == GlobalConstant.FALSE &&  (pagePassword.equals(EncryptUtil.decryptPassword(dbUser.getEncryptedPassword())))){
			rd.setCode(-1);
			rd.setMessage("新密码不能和原密码相同！");
			return rd;
		}
		
		dbUser.setIsFirstLogin(GlobalConstant.FALSE);// 将首次登录设为 FALSE
		
		dbUser.setIsModifyPassword(GlobalConstant.TRUE);
		dbUser.setEncryptedPassword(EncryptUtil.encryptPassword(pagePassword));//加密
		userDao.edit(dbUser);

		dbUser.setPseudoPassword(pagePassword);
		rd.put("user", dbUser);
		
		rd.setCode(1);
		rd.setMessage("新密码设置成功！");
		return rd;
	}
	
	
	public ResponseData displayeditFirstLoginPassword(User frmUser) {
		ResponseData rd = new ResponseData(0);
		String loginName = frmUser.getLoginName().trim();

		Map parameters = new HashMap();
		parameters.put("loginName", loginName);
		List userList = userDao.find(parameters, "");

		User dbUser = new User();
		if (userList != null && userList.size() > 0) {
			dbUser = (User) userList.get(0);
		}

		MyBeanUtils.copyPropertiesContainsDate(dbUser, frmUser);
		dbUser.setPseudoPassword(frmUser.getPseudoPassword());
		
		rd.put("user", dbUser);

		rd.setCode(1);
		return rd;

	}


	private boolean sendTempPasswordEmail(User user, String email) {
		try {
			MailUtil mail = new MailUtil();
			if (user != null && email != null) {
				StringBuffer contentBuf = new StringBuffer(200);
				contentBuf.append(user.getNickName());
				contentBuf.append("，您好:<br>");
				contentBuf.append("您通过Newews网站重置了您的密码，您的新密码为："+ user.getTempPassword()+ "。<br>");
				contentBuf.append("请点击下面链接激活您的新密码<br><a href='"+GlobalConfig.getDomain()+"/front/registerLogin/userRegisterLoginAction.do?" +
						"action=activeTempPassword&loginName="+user.getLoginName()+"&temppassword="+user.getTempPassword()+"'>" +
						GlobalConfig.getDomain()+"/front/registerLogin/userRegisterLoginAction.do?" +
						"action=activeTempPassword&loginName="+user.getLoginName()+"&tempPassword="+user.getTempPassword()+"</a><br>");
				contentBuf.append("提示：请激活后尽快修改您的新密码！如仍有问题请联系客服：021-68766981。<br>");
				// 暗送给管理员一封邮件
				//mail.setBccuser(GlobalConfig.getMail_fromuser());
				mail.send(email, "您在Newews的新密码", contentBuf.toString());
			}
			return true;
		} catch (Exception e) {
			return false;
		}
	}


}
