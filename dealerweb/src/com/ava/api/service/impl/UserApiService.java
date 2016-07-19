package com.ava.api.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ava.api.service.IUserApiService;
import com.ava.api.vo.UserApiVo;
import com.ava.base.domain.ResponseData;
import com.ava.dao.IUserDao;
import com.ava.domain.entity.Company;
import com.ava.domain.entity.User;
import com.ava.resource.GlobalConfig;
import com.ava.resource.GlobalConstant;
import com.ava.resource.SessionManager;
import com.ava.resource.SmsManager;
import com.ava.resource.cache.CacheCompanyManager;
import com.ava.resource.cache.CacheUserManager;
import com.ava.resource.security.AuthenticationManager;
import com.ava.resource.security.UserToken;
import com.ava.resource.security.param.EmailAuthenticationParam;
import com.ava.resource.security.param.LoginNameAuthenticationParam;
import com.ava.resource.security.param.MobileAuthenticationParam;
import com.ava.resource.security.provider.EmailAuthenticationProvider;
import com.ava.resource.security.provider.LoginNameAuthenticationProvider;
import com.ava.resource.security.provider.MobileAuthenticationProvider;
import com.ava.util.MailUtil;
import com.ava.util.MyStringUtils;
import com.ava.util.TypeConverter;
import com.ava.util.ValidatorUtil;
import com.ava.util.encrypt.EncryptUtil;

@Service
public class UserApiService implements IUserApiService {
	@Autowired
	private IUserDao userDao;

	public ResponseData login(HttpServletRequest request, HttpServletResponse response, UserApiVo user){
		ResponseData rd = new ResponseData(0);
		
		if(user.getLoginName() == null || user.getLoginName().length() == 0){
			// 未填写userName
			rd.setCode(-1);
			rd.setMessage("用户名没有填写！");
			return rd;
		}
		if(user.getApiEncryptedPassword() == null || user.getApiEncryptedPassword().length() == 0){
			// 未填写password
			rd.setCode(-2);
			rd.setMessage("密码没有填写！");
			return rd;
		}
		
		String password = EncryptUtil.decryptPassword(user.getApiEncryptedPassword());
		ResponseData responseData = userAuthentication(user.getLoginName(), password);
		UserToken userToken = null;
		if(responseData.getCode() == 1){
			userToken = (UserToken) responseData.getFirstItem();
		} else {
			rd.setMessage(responseData.getMessage());
			rd.setCode(-3);
			return responseData;
		}
		
		User dbUser = userDao.get(userToken.getUserAccountId());
		if (dbUser == null) {
			//该用户不存在！
			rd.setMessage("该用户不存在！");
			rd.setCode(-4);
		}

		//设置用户最后登录时间
		dbUser.setLastLoginTime(new Date());
		userDao.edit(dbUser);
		
		// 把需要的用户信息写入session
		SessionManager.setCurrentUserInfo(request, response, dbUser, "false");
		
		UserApiVo userVo = new UserApiVo(dbUser);
		userVo.setAccessToken(userToken.getToken());
		rd.setFirstItem(userVo);

		rd.setCode(1);
		return rd;	
	}
	
	/**
	 * 注意：password应该是明文密码
	 * @return 1:验证通过；其他值：验证未通过，以及原因*/
	private ResponseData userAuthentication(String accountName, String password) {
		ResponseData rd = new ResponseData(0);
		
		UserToken userToken = null;
		if(MyStringUtils.isBlank(accountName)){
			rd.setCode(-11);
			rd.setMessage("账号名为空");
			return rd;
		}
		
		accountName = accountName.trim();
		if (ValidatorUtil.isEmail(accountName)) {
			//如果是email
			userToken = AuthenticationManager.authenticate(new EmailAuthenticationProvider(), new EmailAuthenticationParam(accountName, password));
		} else if (ValidatorUtil.isMobile(accountName)){
			//如果是手机号
			userToken = AuthenticationManager.authenticate(new MobileAuthenticationProvider(), new MobileAuthenticationParam(accountName, password));
		} else {
			//否则当作用户名处理
			userToken = AuthenticationManager.authenticate(new LoginNameAuthenticationProvider(), new LoginNameAuthenticationParam(accountName, password));
		}

		if(userToken != null){
			rd.setCode(1);
			rd.setFirstItem(userToken);
		} else {
			rd.setCode(-12);
			rd.setMessage("登陆验证失败");
		}
		return rd;
	}
	
	public int sendPassowrd(Integer userId, String password, String toEmail) {
		User toUser = userDao.get(userId);
		if(toUser == null || toUser.getId() == null || toUser.getId().intValue() <= 0){
			return -1;
		}
		Integer companyId = toUser.getCompanyId();
		String loginName = toUser.getLoginName();
		String nickName = toUser.getNickName();
		
		if (toEmail == null || !TypeConverter.sizeLagerZero(toEmail)) {
		}else{
			Company company = CacheCompanyManager.getCompany(companyId);
			if(company != null){
				nickName = nickName + "(" + company.getName() + ")";
			}
			if(ValidatorUtil.isEmail(toEmail)){
				MailUtil mail = new MailUtil();
				StringBuffer contentBuf = new StringBuffer(200);
				contentBuf.append("<font size=2 color='#003366'>" + nickName);
				contentBuf.append("，您好:<br>");
				contentBuf.append("系统已为您创建一个帐号，用户名：" + loginName+"，密码：" + password + "<br><br>");
				contentBuf.append("系统登录地址" 
						+ "<br><a href='" + GlobalConfig.getDomain() + "'>" 
						+ GlobalConfig.getDomain() + "</a><br><br>");
				contentBuf.append("<font size=2 color='#663300'>本邮件为系统自动发出，请勿直接回复 ！</font><br>");
				mail.send(toEmail, "试乘试驾系统帐号", contentBuf.toString());
				
			}
		}
		
		return 1;
	}
	
	public int sendPasswordToHeadquarters(Integer userId, String password) {
		User toUser = userDao.get(userId);
		if(toUser == null || toUser.getId() == null || toUser.getId().intValue() <= 0){
			return -1;
		}
		Integer companyId = toUser.getCompanyId();
		String loginName = toUser.getLoginName();
		String nickName = toUser.getNickName();
		
			Company company = CacheCompanyManager.getCompany(companyId);
			if(company != null){
				nickName = nickName + "(" + company.getName() + ")";
			}
			
				MailUtil mail = new MailUtil();
				StringBuffer contentBuf = new StringBuffer(200);
				contentBuf.append("<font size=2 color='#003366'>" + nickName);
				contentBuf.append("，您好:<br>");
				contentBuf.append("系统添加了一个系统帐号，用户名：" + loginName+"，密码：" + password + "<br><br>");
				contentBuf.append("系统登录地址" 
						+ "<br><a href='" + GlobalConfig.getDomain() + "'>" 
						+ GlobalConfig.getDomain() + "</a><br><br>");
				contentBuf.append("<font size=2 color='#663300'>本邮件为系统自动发出，请勿直接回复 ！</font><br>");
				mail.send(GlobalConfig.getHeadquartersEmail(), "试乘试驾系统帐号", contentBuf.toString());
				
		
		return 1;
	}
	
	
	public int sendTempPassowrd(Integer userId, String tempPassword, String toMobile, String toEmail){
		User toUser = userDao.get(userId);
		if(toUser == null || toUser.getId() == null || toUser.getId().intValue() <= 0){
			return -1;
		}
		Integer companyId = toUser.getCompanyId();
		String loginName = toUser.getLoginName();
		String nickName = toUser.getNickName();
		
		if (toMobile == null || !TypeConverter.sizeLagerZero(toMobile)) {
		}else{
			if(ValidatorUtil.isMobile(toMobile)){
				CacheUserManager.putTempPassword(userId, tempPassword);
				if(!SmsManager.send(companyId, userId, toMobile, tempPassword, "")){
					return -4;//短信发送失败
				}
			}
		}
		if (toEmail == null || !TypeConverter.sizeLagerZero(toEmail)) {
		}else{
			Company company = CacheCompanyManager.getCompany(companyId);
			if(company != null){
				nickName = nickName + "(" + company.getName() + ")";
			}
			if(ValidatorUtil.isEmail(toEmail)){
				CacheUserManager.putTempPassword(userId, tempPassword);			
				MailUtil mail = new MailUtil();
					StringBuffer contentBuf = new StringBuffer(200);
					contentBuf.append("<font size=2 color='#003366'>" + nickName);
					contentBuf.append("，您好:<br>");
					contentBuf.append("系统已为您创建一个帐号，用户名：" + loginName+"，临时密码：" + tempPassword + "<br>");
					contentBuf.append("请点击以下链接激活您的帐号（链接" 
							+ GlobalConstant.TEMP_PASSWORD_EXPIRATION 
							+ "小时内有效）<br><br><a href='" + GlobalConfig.getDomain() 
							+ "/base/registerLogin/activeTempPassword.vti?loginName=" + loginName + "&tempPassword=" + tempPassword + "'>" 
							+ GlobalConfig.getDomain()+"/base/registerLogin/activeTempPassword.vti?" 
							+ "loginName=" + loginName + "&tempPassword=" 
							+ tempPassword + "</a><br><br>");
					contentBuf.append("提示：请激活您的帐号，并且及时修改您的密码，如果链接无法打开，请复制链接到浏览器中打开，如有问题请联系系统管理员！</font><br><br>");
					contentBuf.append("<font size=2 color='#663300'>本邮件为系统自动发出，请勿直接回复 ！</font><br>");
					mail.send(toEmail, "系统帐号", contentBuf.toString());

			}
		}
		
		return 1;
	}
	
	public int reSendTempPassowrd(Integer userId, String tempPassword, String toMobile, String toEmail){
		User toUser = userDao.get(userId);
		if(toUser == null || toUser.getId() == null || toUser.getId().intValue() <= 0){
			return -1;
		}
		Integer companyId = toUser.getCompanyId();
		String loginName = toUser.getLoginName();
		String nickName = toUser.getNickName();
		
		if (toMobile == null || !TypeConverter.sizeLagerZero(toMobile)) {
		}else{
			if(ValidatorUtil.isMobile(toMobile)){
				CacheUserManager.putTempPassword(userId, tempPassword);
				if(!SmsManager.send(companyId, userId, toMobile, tempPassword, "")){
					return -4;//短信发送失败
				}
			}
		}
		if (toEmail == null || !TypeConverter.sizeLagerZero(toEmail)) {
		}else{
			Company company = CacheCompanyManager.getCompany(companyId);
			if(company != null){
				nickName = nickName + "(" + company.getName() + ")";
			}
			
			if(ValidatorUtil.isEmail(toEmail)){
				CacheUserManager.putTempPassword(userId, tempPassword);			
				MailUtil mail = new MailUtil();
				StringBuffer contentBuf = new StringBuffer(200);
				contentBuf.append("<font size=2 color='#003366'>" + nickName);
				contentBuf.append("，您好:<br>");
				contentBuf.append("系统已为您重置密码，用户名：" + loginName+"，临时密码：" + tempPassword + "<br>");
				contentBuf.append("请点击以下链接激活您的帐号（链接" 
						+ GlobalConstant.TEMP_PASSWORD_EXPIRATION 
						+ "小时内有效）<br><br><a href='" + GlobalConfig.getDomain() 
						+ "/base/registerLogin/activeTempPassword.vti?loginName=" + loginName + "&tempPassword=" + tempPassword + "'>" 
						+ GlobalConfig.getDomain()+"/base/registerLogin/activeTempPassword.vti?" 
						+ "loginName=" + loginName + "&tempPassword=" 
						+ tempPassword + "</a><br><br>");
				contentBuf.append("提示：请激活您的新密码，并且及时修改您的密码，如果链接无法打开，请复制链接到浏览器中打开，如有问题请联系系统管理员！</font><br><br>");
				contentBuf.append("<font size=2 color='#663300'>本邮件为系统自动发出，请勿直接回复 ！</font><br>");
				mail.send(toEmail, "OA系统帐号", contentBuf.toString());
				
			}
		}
		
		return 1;
	}

	public UserApiVo getUser(Integer userId) {
		User aUser = userDao.get(userId);
		UserApiVo userApiVo = new UserApiVo(aUser);
		return userApiVo;
	}

	public List<UserApiVo> findUser(Integer orgId){
		HashMap parameters = new HashMap();
		parameters.put("companyId", orgId);
		
		List<UserApiVo> userApiVoList = new ArrayList();
		List userList = userDao.find(parameters, "");
		if(userList != null && userList.size() > 0){
			for (Iterator iterator = userList.iterator(); iterator.hasNext();) {
				User user = (User) iterator.next();
				userApiVoList.add(new UserApiVo(user));
			}
		}
		return userApiVoList;
	}
}
