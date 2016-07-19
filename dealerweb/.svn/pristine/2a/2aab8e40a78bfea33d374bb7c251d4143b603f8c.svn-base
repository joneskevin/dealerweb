package com.ava.baseSystem.controller;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.ava.api.service.IUserApiService;
import com.ava.base.controller.Base4MvcController;
import com.ava.base.domain.ResponseData;
import com.ava.baseSystem.service.IUserRegisterLoginService;
//import com.ava.baseSystem.service.IUserService;
import com.ava.domain.entity.User;
import com.ava.facade.IUserRegisterLoginFacade;
import com.ava.resource.GlobalConstant;
import com.ava.resource.SessionManager;
import com.ava.servlet.AuthenCodeImage;
import com.ava.util.TypeConverter;
import com.ava.util.ValidatorUtil;

@Controller
@RequestMapping(value="/base/registerLogin")
public class UserRegisterLoginController extends Base4MvcController {
	
//	@Autowired
//	private IUserService userService;
	
	@Autowired
	private IUserApiService userApiService;
	
	@Autowired
	private IUserRegisterLoginService userRegisterLoginService;
	
	@Autowired
	private IUserRegisterLoginFacade userRegisterLoginFacade;

	@RequestMapping(value="/loginName/validateUnique.vti", method=RequestMethod.GET)
	public void validateUniqueLoginName(){
		ResponseData rd = new ResponseData(0);
		String obName = getStringParameter("obName");
		if (TypeConverter.sizeLagerZero(obName.trim())) {
			if (userRegisterLoginService.obNameIsExistence(obName.trim())) {
				rd.setCode(-1);
				rd.setMessage("组织账号'"+obName.trim()+"'已经存在");
			}else{
				rd.setCode(1);
			}
		}else{
			rd.setCode(-2);
			rd.setMessage("组织账号不能为空");			
		}
		
		writeRd(rd);
	}

	
	/**显示注册经销商页面*/
//	@RequestMapping(value = "/displayRegister.vti", method = RequestMethod.GET)
//	public String displayRegister(@ModelAttribute("user") User user) {
//		Integer currentCompanyId = SessionManager.getCurrentCompanyId(getRequest());
//		ResponseData rdUser = userService.displayAddUser(user);
//		getRequest().setAttribute("userAdd", rdUser.getFirstItem());
//		getRequest().setAttribute("companyAdd", new Company());
//		initSelectList(currentCompanyId);
//		return "base/baseSystem/registerLogin/registerDealer";
//	}
//	
//	 public void initSelectList(Integer cmpanyId) {
//		  List dealerTypeList = SelectOptionResource.dealerTypeOptions();
//		  getRequest().setAttribute("dealerTypeList", dealerTypeList);
//	}
	
	/** 注册页面-占时未启用
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	/** public String displayRegister(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		UserRegisterLoginForm form1 = (UserRegisterLoginForm) form;
		service.displayRegister(form1, request, response);
		return "base/baseSystem/registerLogin/register";
	} */

	/** 注册页面-占时未启用
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	/**public String register(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		UserRegisterLoginForm form1 = (UserRegisterLoginForm) form;
		if (!checkFormForRegister(form1, request)) {
			return displayRegister(mapping, form, request, response);
		}

		IUserRegisterLoginService service = (IUserRegisterLoginService) getBean("userRegisterLoginService");
		int operationResult = service.register(form1, request, response);
		if (operationResult == 1) {
			message("注册成功！您的登录帐号为："+form1.getUser().getLoginName());
			//添加admin用户为在线客服
			//String urlhttp = GlobalConfig.getObcServer() + "/sync";
			//String parameter = "password=" + GlobalConfig.getPasswordOcApi() + "&requestType=agent-create&obName=" + form1.getUser().getObName() + "&agent=admin\\40" +form1.getUser().getObName();
			//GetHttp.doGet(urlhttp, parameter);
			return displayLogin();
		} else {
			message("注册失败");
			return displayRegister(mapping, form, request, response);
		}
	}*/

	
	
	/**
	 *  登录页面
	 * @return
	 */
	@RequestMapping(value="/displayLogin.vti", method=RequestMethod.GET)
	public String displayLogin(){
		getRequest().setAttribute("user", new User());
		return "/dealer/registerLogin/login";
	}

	/**
	 * 登录
	 * @param frmUser
	 * @return
	 */
	@RequestMapping(value="/login.vti", method=RequestMethod.POST)
	public String login(@ModelAttribute("user") User frmUser) {
		
		String loginName = frmUser.getLoginName().trim();
		String password = frmUser.getPseudoPassword();
		
//		 Object valueObj=getRequest().getSession(true).getAttribute(AuthenCodeImage.SESSION_AUTHEN_CODE);
//		 if (! getStringParameter("validationCode").equalsIgnoreCase(TypeConverter.toString(valueObj))) {
//			message(GlobalConstant.MSG_RESULT_LEVEL_WARNING, GlobalConstant.MSG_RESULT_CONTENT, "验证码输入错误");
//			return displayLogin();
//		}
		
		String isRememberMe = getStringParameter("isRememberMe");
		//ResponseData rd = userRegisterLoginService.login(loginName, password, isRememberMe, getRequest(), getResponse());
		ResponseData rd = userRegisterLoginFacade.login(loginName, password, isRememberMe, getRequest(), getResponse());
		
		if (rd.getCode() == 1) {
			User dbUser = (User) rd.getFirstItem();
			frmUser.setLoginName(dbUser.getLoginName());
			
			//从session读取的登录后跳转类型
			Integer jumpType = TypeConverter.toInteger(getRequest().getSession(true).getAttribute(GlobalConstant.SESSION_LOGIN_FORWARD_TYPE));
			//从session读取的跳回页面路径信息
			String forwardUrl = TypeConverter.toString(getRequest().getSession(true).getAttribute(GlobalConstant.SESSION_LOGIN_FORWARD_URL));
			
			//读取完后清空，防止下次登录时候重复使用
			getRequest().getSession(true).setAttribute(GlobalConstant.SESSION_LOGIN_FORWARD_TYPE, null);
			getRequest().getSession(true).setAttribute(GlobalConstant.SESSION_LOGIN_FORWARD_URL, null);
			
			if (TypeConverter.isEqualIntValue(jumpType, GlobalConstant.SESSION_LOGIN_FORWARD_TYPE_COMMON)) {
				//jumpType决定登陆后需要跳回原有页面
    			if ( StringUtils.isBlank(forwardUrl)) {
    				//如果从session读取的跳回页面路径信息为空，则直接跳到普通登录后页面
    			} else {
    				//跳到从session读取的跳回页面路径信息
    				return forwardUrl;
    				
    			}
			}
			
			//jumpType决定的其他类型的跳转情况
			if (TypeConverter.isEqualIntValue(jumpType, GlobalConstant.SESSION_LOGIN_FORWARD_TYPE_CLOSE_RELOAD)) {
				return "pub/jsPages/closeAndReload";
			} 

			//剩余其他情况则是未定义的jumpType类型，同时也不用跳回原有页面，则统一跳回普通登录后页面
			return "/dealer/index";
			
		} else if (rd.getCode() == -1 || rd.getCode() == -2 || rd.getCode() == -3) {
			message(GlobalConstant.MSG_RESULT_LEVEL_ERROR, GlobalConstant.MSG_RESULT_CONTENT, rd.getMessage());
			return displayLogin();
			
		} else if (rd.getCode() == -9) { 
			message(GlobalConstant.MSG_RESULT_LEVEL_WARNING, GlobalConstant.MSG_RESULT_CONTENT, rd.getMessage());
			return displayLogin();
			
			
		}
		return displayLogin();
	}
	
	/**
	 * 注销
	 * @return
	 */
	@RequestMapping(value="/logout.vti", method=RequestMethod.GET)
	public String logout() {
		userRegisterLoginService.logout(getRequest(), getResponse());
		return displayLogin();
	}
	
	/**
	 * 获得登录名
	 */
	@RequestMapping(value="/getLoginName.vti", method=RequestMethod.GET)
	public void getLoginName() {		
		ResponseData rd = new ResponseData(0);
        
		String userLoginName = SessionManager.getCurrentUserLoginName(getRequest());
		if (userLoginName != null && userLoginName.length() > 0) {
			rd.setFirstItem(userLoginName);
		    rd.setCode(1);
		} else {
			//跳转到登录页面
			rd.setCode(-1);
		}
		writeRd(rd);
	}
	

	/**	显示取回密码页面	*/
	@RequestMapping(value="/displayFetchPassword.vti", method=RequestMethod.GET)
	public String displayFetchPassword() {
		getRequest().setAttribute("email", getStringParameter("email"));
		getRequest().setAttribute("validationCode", getStringParameter("validationCode"));
		return "base/baseSystem/registerLogin/fetchPassword";
	}
	
	/**	取回密码操作	*/
	@RequestMapping(value="/fetchPassword.vti", method=RequestMethod.POST)
	public String fetchPassword(@RequestParam("email") String email, @RequestParam("validationCode") String validationCode) {
		if (!checkFormForFetchPassword(email, validationCode)) {
			return displayFetchPassword();
		}
		
		ResponseData rd = userRegisterLoginService.fetchPassword(email);
		if (rd.getCode() == 1) {
			User user = (User) rd.get("user");
			//发送临时密码到用户的邮箱
			int sendTempPasswordResult = userApiService.reSendTempPassowrd(user.getId(), user.getTempPassword(), null, user.getEmail());
			if(sendTempPasswordResult == 1){
				message(GlobalConstant.MSG_RESULT_LEVEL_SUCCESS, GlobalConstant.MSG_RESULT_CONTENT, "取回密码成功，新密码已经发送到您的邮箱里！"); 
			}else{
				message(GlobalConstant.MSG_RESULT_LEVEL_ERROR, GlobalConstant.MSG_RESULT_CONTENT, "邮件发送失败！"); 
			}
		}else if(rd.getCode() == -1){
			message(GlobalConstant.MSG_RESULT_LEVEL_ERROR, GlobalConstant.MSG_RESULT_CONTENT, "您输入的email系统不存在，请重试！");
		}else{
			message(GlobalConstant.MSG_RESULT_LEVEL_ERROR, GlobalConstant.MSG_RESULT_CONTENT, "取回密码失败，请重试！");
		}
		
		return displayFetchPassword();
	}
	
	/**	显示重新激活页面	*/
	@RequestMapping(value="/displayReActivation.vti", method=RequestMethod.GET)
	public String displayReActivation( ) {
		getRequest().setAttribute("email", getStringParameter("email"));
		getRequest().setAttribute("validationCode", getStringParameter("validationCode"));
		return "base/baseSystem/registerLogin/reActivation";
	}
	
	/**	重新激活操作	*/
	@RequestMapping(value="/reActivation.vti", method=RequestMethod.POST)
	public String reActivation(@RequestParam("email") String email, @RequestParam("validationCode") String validationCode) {
		if (!checkFormForFetchPassword(email, validationCode)) {
			return displayReActivation();
		}
		
		ResponseData rd = userRegisterLoginService.reActivation(email);
		if (rd.getCode() == 1) {
			User user = (User) rd.get("user");
			//发送临时密码到用户的邮箱
			int sendTempPasswordResult = userApiService.reSendTempPassowrd(user.getId(), user.getTempPassword(), null, user.getEmail());
			if(sendTempPasswordResult == 1){
				message(GlobalConstant.MSG_RESULT_LEVEL_SUCCESS, GlobalConstant.MSG_RESULT_CONTENT, "重新激活成功，请查收您的邮件！"); 
			}else{
				message(GlobalConstant.MSG_RESULT_LEVEL_ERROR, GlobalConstant.MSG_RESULT_CONTENT, "邮件发送失败！"); 
			}
		}else if(rd.getCode() == -1){
			message(GlobalConstant.MSG_RESULT_LEVEL_ERROR, GlobalConstant.MSG_RESULT_CONTENT, "您输入的email系统不存在，请重试！");
		}else{
			message(GlobalConstant.MSG_RESULT_LEVEL_ERROR, GlobalConstant.MSG_RESULT_CONTENT, "重新激活失败，请重试！");
		}
		
		return displayReActivation();
	}

	/**	取回密码操作－输入表单检查	*/
	protected boolean checkFormForFetchPassword(String email, String validationCode) {
		if (!TypeConverter.sizeLagerZero(email)) {
			message(GlobalConstant.MSG_RESULT_LEVEL_WARNING, GlobalConstant.MSG_RESULT_CONTENT, "email必须填写");
			return false;
		} else if (!ValidatorUtil.isEmail(email)) {
			message(GlobalConstant.MSG_RESULT_LEVEL_WARNING, GlobalConstant.MSG_RESULT_CONTENT, "email格式不正确！");
			return false;
		}
		
		Object valueObj=getRequest().getSession(true).getAttribute(AuthenCodeImage.SESSION_AUTHEN_CODE);
		if(!TypeConverter.sizeLagerZero(validationCode)){
			message(GlobalConstant.MSG_RESULT_LEVEL_WARNING, GlobalConstant.MSG_RESULT_CONTENT, "验证码必须填写");
			return false;
		}else if (!validationCode.equalsIgnoreCase(TypeConverter.toString(valueObj))) {
			message(GlobalConstant.MSG_RESULT_LEVEL_WARNING, GlobalConstant.MSG_RESULT_CONTENT, "验证码输入错误");
			return false;
		}
		return true;
	}
	
	/**
	 * 首页登录修改密码页面
	 * @param frmUser
	 * @return String
	 */
	@RequestMapping(value="/displayEditFirstLoginPassword.vti", method=RequestMethod.GET)
	public String displayEditFirstLoginPassword(@ModelAttribute("frmUser") User frmUser) {
		ResponseData rd = userRegisterLoginService.displayeditFirstLoginPassword(frmUser);
		getRequest().setAttribute("userEdit", rd.get("user"));
		getRequest().setAttribute("pagePassword", getStringParameter("pagePassword"));
		getRequest().setAttribute("pageConfirmPassword", getStringParameter("pageConfirmPassword"));
		getRequest().setAttribute("validationCode", getStringParameter("validationCode"));
		
		return "base/baseSystem/registerLogin/editFirstLoginPassword";
	}
	
	/**
	 * 首次登录设置密码
	 * @param frmUser
	 * @return
	 */
	@RequestMapping(value="/editFirstLoginPassword.vti", method=RequestMethod.POST)
	public String editFirstLoginPassword(@ModelAttribute("frmUser") User frmUser){
		String pagePassword = getStringParameter("pagePassword");
		String pageConfirmPassword = getStringParameter("pageConfirmPassword");
		if (!checkeditPassword(pagePassword, pageConfirmPassword)) {
			return displayEditFirstLoginPassword(frmUser);
		}
		
		ResponseData rd = userRegisterLoginService.editFirstLoginPassword(frmUser, pagePassword);
		if (rd.getCode() == 1) {
			message(GlobalConstant.MSG_RESULT_LEVEL_SUCCESS, GlobalConstant.MSG_RESULT_CONTENT, rd.getMessage());
			return login((User)rd.get("user"));
			
		} else if (rd.getCode() == -1) {
			message(GlobalConstant.MSG_RESULT_LEVEL_ERROR, GlobalConstant.MSG_RESULT_CONTENT, rd.getMessage());
			return displayEditFirstLoginPassword(frmUser);
			
		} else {
			message(GlobalConstant.MSG_RESULT_LEVEL_ERROR, GlobalConstant.MSG_RESULT_CONTENT, rd.getMessage());
		}
		
		return displayEditFirstLoginPassword(frmUser);

	}
	
	
	/**	用户激活新密码操作	*/
	@RequestMapping(value="/activeTempPassword.vti", method=RequestMethod.GET)
	public String activeTempPassword(@RequestParam("loginName") String loginName, @RequestParam("tempPassword") String tempPassword) {
		ResponseData rd = userRegisterLoginService.activeTempPassword(loginName, tempPassword);
		if (rd.getCode() == 1) {
			return "base/baseSystem/registerLogin/activation_s";
			
		}else {
			return "base/baseSystem/registerLogin/activation_f";
		}

	}
	
	/**首次登录系统密码修改验证*/
	protected boolean checkeditPassword(String pagePassword, String pageConfirmPassword) {
		if (pagePassword == null || "".equals(pagePassword)) {
			message(GlobalConstant.MSG_RESULT_LEVEL_WARNING, GlobalConstant.MSG_RESULT_CONTENT, "新密码必须填写！");
			return false;
		}
		if(!ValidatorUtil.isValidSize4Password(pagePassword)){
			message(GlobalConstant.MSG_RESULT_LEVEL_WARNING, GlobalConstant.MSG_RESULT_CONTENT, "密码长度应在6-15位之间！");
			return false;
		}
		if (pageConfirmPassword == null || "".equals(pageConfirmPassword)) {
			message(GlobalConstant.MSG_RESULT_LEVEL_WARNING, GlobalConstant.MSG_RESULT_CONTENT, "确认新密码必须填写！");
			return false;
		}
		if (!pagePassword.equals(pageConfirmPassword)) {
			message(GlobalConstant.MSG_RESULT_LEVEL_WARNING, GlobalConstant.MSG_RESULT_CONTENT, "新密码和确认新密码输入不一致！");
			return false;
		}
		return true;
	}
	
	
}
