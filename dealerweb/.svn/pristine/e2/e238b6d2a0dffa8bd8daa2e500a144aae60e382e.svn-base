package com.ava.baseSystem.controller;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.ava.api.service.IUserApiService;
import com.ava.base.controller.Base4MvcController;
import com.ava.base.dao.hibernate.TransMsg;
import com.ava.base.domain.ResponseData;
import com.ava.baseSystem.service.IUserService;
import com.ava.domain.entity.Org;
import com.ava.domain.entity.User;
import com.ava.domain.vo.UserLogFindVO;
import com.ava.resource.GlobalConstant;
import com.ava.resource.SelectOptionResource;
import com.ava.resource.SessionManager;
import com.ava.resource.cache.CacheOrgManager;
import com.ava.util.DateTime;
import com.ava.util.TypeConverter;
import com.ava.util.ValidatorUtil;
import com.ava.util.excel.ExportExcelUtil;

@Controller
@RequestMapping(value="/base/user")
public class UserController extends Base4MvcController {
	
	@Autowired
	private IUserService userService;
	
	@Autowired
	private IUserApiService userApiService;
	
	
	/**
	 * 显示用户列表
	 * 
	 * @param transMsg
	 * @param user
	 * @return
	 */
	@RequestMapping(value="/search.vti", method=RequestMethod.GET)
	public String listUser(TransMsg transMsg, @ModelAttribute("user") User user) {
		Integer companyId = SessionManager.getCurrentCompanyId(getRequest());
		ResponseData rd = userService.listUser(transMsg, user, companyId, getRequest(), false);
		
		getRequest().setAttribute("userList", rd.getFirstItem());
		getRequest().setAttribute("user", user);
		getRequest().setAttribute("transMsg", transMsg);
		
		siteMenuType(rd);
		
		initSelectList(companyId);
		
		return "/dealer/user/listUser";
	}
	
	/**
	 * 用户登录日志
	 * @author liuq 
	 * @version 0.1 
	 * @param transMsg
	 * @param dealerCode
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	@RequestMapping(value="/listUserLog.vti", method=RequestMethod.GET)
	public String listUserLog(TransMsg transMsg, String dealerCode, String startTime, String endTime) {
		try {
			if (StringUtils.isNotBlank(startTime)) {
				getRequest().setAttribute("startTime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(startTime));
			} else {
				startTime = DateTime.toNormalDate(DateTime.minusDays(new Date(), 7)) + " 00:00:00 ";
				getRequest().setAttribute("startTime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(startTime));
				
			}
			
			if (StringUtils.isNotBlank(endTime)) {
				getRequest().setAttribute("endTime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(endTime));
			} else {
				endTime = DateTime.getNormalDate()+ " 23:59:59 ";
				getRequest().setAttribute("endTime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(endTime));
			}
			
			ResponseData rd = userService.listUserLog(transMsg, dealerCode, startTime, endTime, false);
			
			getRequest().setAttribute("dealerCode", dealerCode);
			getRequest().setAttribute("userLogList", rd.getFirstItem());
			getRequest().setAttribute("transMsg", transMsg);
			
			siteMenuType(rd);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "/dealer/user/listUserLog";
	}
	
	/**
	 * 用户数据导出Excel
	 * @param transMsg
	 * @param user
	 * @return
	 */
	@RequestMapping(value="/exportUserExcel.vti", method=RequestMethod.GET)
	public void exportUserExcel(TransMsg transMsg, @ModelAttribute("user") User user) {
		Integer companyId = SessionManager.getCurrentCompanyId(getRequest());
		
		ResponseData rd = userService.listUser(transMsg, user, companyId, getRequest(), true);
		
		List<User> userList = (List<User>) rd.getFirstItem();
		writeExcelUser(userList);
	}
	
	/**
	 * 导出数据到excel
	 * @param violations
	 */
	public  void writeExcelUser(List<User> userList) {
		String[] titles = {"所属单位","名称","帐号","登录密码","手机","邮箱","角色","禁用"};
		String fileName ="";
	
		fileName="用户信息"+DateTime.toShortDateTime(new Date());
		
		HSSFWorkbook excel = new HSSFWorkbook();
		HSSFSheet sheet = excel.createSheet("用户列表");
		HSSFRow firstRow = sheet.createRow(0);
		HSSFCell cells[] = new HSSFCell[titles.length];
	
		for (int i = 0; i < titles.length; i++) {
			cells[0] = firstRow.createCell(i);
			cells[0].setCellValue(titles[i]);
		}
		
		if (userList != null && userList.size() > 0) {
			for (int i = 0; i < userList.size(); i++) {
				HSSFRow row = sheet.createRow(i + 1);
				User user = (User) userList.get(i);
				Integer y = 0;
				HSSFCell cell = row.createCell(0);
	    		
				cell.setCellValue(user.getCompanyName());
				cell = row.createCell(++ y);
				cell.setCellValue(user.getNickName());
				cell = row.createCell(++ y);
				cell.setCellValue(user.getLoginName());
				cell = row.createCell(++ y);
				cell.setCellValue(user.getPseudoPassword());
				cell = row.createCell(++ y);
				cell.setCellValue(user.getMobile());
				cell = row.createCell(++ y);
				cell.setCellValue(user.getEmail());
				cell = row.createCell(++ y);
				cell.setCellValue(user.getRoles());
				cell = row.createCell(++ y);
				cell.setCellValue(user.getIsDisableNick());
				cell = row.createCell(++ y);
			}	
		}
		
		//传送excel到客户
		ExportExcelUtil.sendFileToUser(fileName, excel, getResponse(), getRequest().getHeader("User-Agent"));

	}
	
	/**
	 * 用户日志导出Excel
	 * @author liuq 
	 * @version 0.1 
	 * @param transMsg
	 * @param dealerCode
	 * @param startTime
	 * @param endTime
	 */
	@RequestMapping(value="/exportUserLogExcel.vti", method=RequestMethod.GET)
	public void exportUserLogExcel(TransMsg transMsg, String dealerCode, String startTime, String endTime) {
		ResponseData rd = userService.listUserLog(transMsg, dealerCode, startTime, endTime, true);
		
		List<UserLogFindVO> userLogFindVOList = (List<UserLogFindVO>) rd.getFirstItem();
		writeExcelUserLog(userLogFindVOList);
	}
	
	/**
	 * 导出登录日志数据到excel
	 * @author liuq 
	 * @version 0.1 
	 * @param userLogFindVOList
	 */
	public  void writeExcelUserLog(List<UserLogFindVO> userLogFindVOList) {
		String[] titles = {"网络代码","经销商名称","登录时间"};
		String fileName ="";
	
		fileName="用户登录日志"+DateTime.toShortDateTime(new Date());
		
		HSSFWorkbook excel = new HSSFWorkbook();
		HSSFSheet sheet = excel.createSheet("登录日志列表");
		HSSFRow firstRow = sheet.createRow(0);
		HSSFCell cells[] = new HSSFCell[titles.length];
	
		for (int i = 0; i < titles.length; i++) {
			cells[0] = firstRow.createCell(i);
			cells[0].setCellValue(titles[i]);
		}
		
		if (userLogFindVOList != null && userLogFindVOList.size() > 0) {
			for (int i = 0; i < userLogFindVOList.size(); i++) {
				HSSFRow row = sheet.createRow(i + 1);
				UserLogFindVO userLogFindVO = (UserLogFindVO) userLogFindVOList.get(i);
				Integer y = 0;
				HSSFCell cell = row.createCell(0);
	    		
				cell.setCellValue(userLogFindVO.getDealerCode());
				cell = row.createCell(++ y);
				cell.setCellValue(userLogFindVO.getCompanyName());
				cell = row.createCell(++ y);
				cell.setCellValue(DateTime.toNormalDateTime(userLogFindVO.getActionTime()));
				cell = row.createCell(++ y);
			}	
		}
		
		//传送excel到客户
		ExportExcelUtil.sendFileToUser(fileName, excel, getResponse(), getRequest().getHeader("User-Agent"));

	}
	
	 public void initSelectList(Integer companyId) {
		  Integer userId = SessionManager.getCurrentUserId(getRequest());
		  List<Org> orgList = CacheOrgManager.getOrgTree4Select(userId, companyId, GlobalConstant.OPERATIONS_ADMIN_ROLE_ID);
		  getRequest().setAttribute("orgList", orgList);
		  
		  List inOfficeList = SelectOptionResource.inOfficeOptions();
		  getRequest().setAttribute("inOfficeList", inOfficeList);
		  
		  List inPositionList = SelectOptionResource.inPositionOptions();
		  getRequest().setAttribute("inPositionList", inPositionList);
		  
		  List isKeyCityList = SelectOptionResource.isKeyCityOptions();
		  getRequest().setAttribute("isKeyCityList", isKeyCityList);
	}

	/**
	 * 显示新增用户的页面
	 * 
	 * @param user
	 * @return
	 */
	@RequestMapping(value="/displayAdd.vti", method=RequestMethod.GET)
	public String displayAddUser(@ModelAttribute("userAdd") User user) {
		ResponseData rd = userService.displayAddUser(user);
		getRequest().setAttribute("userAdd", rd.get("userAdd"));
		getRequest().setAttribute("startIndex", getIntegerParameter("startIndex"));
		
		Integer companyId = SessionManager.getCurrentCompanyId(getRequest());
		initSelectList(companyId);
		
		return "/dealer/user/addUser";
	}

	/**
	 * 添加用户
	 * 
	 * @param user
	 * @return
	 */
	@RequestMapping(value="/add.vti", method=RequestMethod.POST)
	public String addUser(@ModelAttribute("userAdd") User user) {
		if (!checkaddUser(user)) {
			return displayAddUser(user);
		}
		
		Integer companyId = SessionManager.getCurrentCompanyId(getRequest());
		ResponseData rd =userService.addUser(user, companyId, "");
		if (rd.getCode() == 1) {
			User currentUser = (User) rd.getFirstItem();
			//发送登录帐号、密码到经销商的邮箱
//			int sendPasswordResult = userApiService.sendPassowrd(currentUser.getId(), currentUser.getPseudoPassword(), currentUser.getEmail());
//			if(sendPasswordResult == 1) {
//				message(GlobalConstant.MSG_RESULT_LEVEL_SUCCESS,
//						GlobalConstant.MSG_RESULT_CONTENT, rd.getMessage());
//			} else {
//				message(GlobalConstant.MSG_RESULT_LEVEL_ERROR, GlobalConstant.MSG_RESULT_CONTENT, "用户帐号信息邮件发送失败！"); 
//			}
			
			//发送登录帐号、密码到总部
//			int sendPasswordToHeadquartersResult = userApiService.sendPasswordToHeadquarters(currentUser.getId(), currentUser.getPseudoPassword());
//			if(sendPasswordToHeadquartersResult == 1) {
//				message(GlobalConstant.MSG_RESULT_LEVEL_SUCCESS,
//						GlobalConstant.MSG_RESULT_CONTENT, rd.getMessage());
//			} else {
//				message(GlobalConstant.MSG_RESULT_LEVEL_ERROR, GlobalConstant.MSG_RESULT_CONTENT, "总部帐号信息邮件发送失败！"); 
//			}
			
//			TransMsg transMsg = new TransMsg();
//			transMsg.setStartIndex(getIntegerParameter("startIndex"));
//			return listUser(transMsg, new User());
			return "/pub/jsPages/closeDialogAndReloadParent";
			
		} else {
			message(GlobalConstant.MSG_RESULT_LEVEL_ERROR,
					GlobalConstant.MSG_RESULT_CONTENT, rd.getMessage());
		}
		
		return displayAddUser(user);
	}
	
	/**
	 * 查看用户详细信息
	 * 
	 * @param userId
	 * @return 
	 */
	@RequestMapping(value="/view.vti", method=RequestMethod.GET)
	public String viewUser(@RequestParam("userId") Integer userId) {
		ResponseData rd = userService.viewUser(userId);
		getRequest().setAttribute("user", rd.get("user"));

		return "/dealer/user/viewUser";
	}
	
	/**
	 * 管理员编辑用户页面
	 * 
	 * @param user
	 * @param userId
	 * @return
	 */
	@RequestMapping(value="/displayEdit.vti", method=RequestMethod.GET)
	public String displayEditUser(@ModelAttribute("frmUser") User frmUser) {
		Integer companyId = SessionManager.getCurrentCompanyId(getRequest());
		Integer userId = frmUser.getId();
		if (frmUser.getId() == null) {
			userId = getIntegerParameter("userId");
		}
		
		ResponseData rd = userService.displayEditUser(userId, companyId);
		User user = (User) rd.get("user");
		getRequest().setAttribute("user", user);
		getRequest().setAttribute("startIndex", getIntegerParameter("startIndex"));
		
		initSelectList(companyId);
		return "/dealer/user/editUser";
	}

	/**
	 * 管理员编辑用户
	 * 
	 * @param user
	 * @return
	 */
	@RequestMapping(value="/edit.vti", method=RequestMethod.POST)
	public String editUser(@ModelAttribute("userEdit") User user) {
		if (!check2editUser(user)) {
			return displayEditUser(user);
		}
		
		ResponseData rd  = userService.editUser(user, getRequest());
		if (rd.getCode() == 1) {
			message(GlobalConstant.MSG_RESULT_LEVEL_SUCCESS,
					GlobalConstant.MSG_RESULT_CONTENT, rd.getMessage());
		return "/pub/jsPages/closeDialogAndReloadParent";
		} else {
			message(GlobalConstant.MSG_RESULT_LEVEL_ERROR,
					GlobalConstant.MSG_RESULT_CONTENT, rd.getMessage());
		}
		
		return displayEditUser(user);
	}
	
	/**
	 * 用户管理-重置密码，发送一封EMIAL给用户
	 * 
	 * @param user
	 * @param userId
	 * @return
	 */
	@RequestMapping(value="/reSendTempPassword.vti", method=RequestMethod.GET)
	public String reSendTempPassword(@RequestParam("userId") Integer userId) {
		ResponseData rd  = userService.reSendTempPassword(userId);
		if (rd.getCode() == 1) {
			User currentUser = (User) rd.getFirstItem();
			//发送临时密码到用户的邮箱
			int sendTempPasswordResult = userApiService.reSendTempPassowrd(currentUser.getId(), currentUser.getTempPassword(), null, currentUser.getEmail());
			if(sendTempPasswordResult == 1) {
				message(GlobalConstant.MSG_RESULT_LEVEL_SUCCESS,
						GlobalConstant.MSG_RESULT_CONTENT, "用户密码重置成功！");
			} else {
				message(GlobalConstant.MSG_RESULT_LEVEL_ERROR, GlobalConstant.MSG_RESULT_CONTENT, "用户帐号信息邮件发送失败！"); 
			}
			
		} else {
			message(GlobalConstant.MSG_RESULT_LEVEL_ERROR,
					GlobalConstant.MSG_RESULT_CONTENT, "用户密码重置失败！请重试！");
		}
		
		TransMsg transMsg = new TransMsg();
		transMsg.setStartIndex(getIntegerParameter("startIndex"));
		return listUser(transMsg, new User());

	}

	/**
	 * 删除用户
	 * 
	 * @param userId
	 * @return
	 */
	@RequestMapping(value="/delete.vti", method=RequestMethod.GET)
	public void deleteUser(@RequestParam("userId") Integer userId) {
		ResponseData rd = userService.deleteUser(userId, getRequest());
		if (rd.getCode() == 1) {
			message(GlobalConstant.MSG_RESULT_LEVEL_SUCCESS,
					GlobalConstant.MSG_RESULT_CONTENT, rd.getMessage());

		} else {
			message(GlobalConstant.MSG_RESULT_LEVEL_ERROR,
					GlobalConstant.MSG_RESULT_CONTENT, rd.getMessage());
		}
		writeRd(rd);
	}
	
	/**
	 * 显示个人中心用户设置
	 * 
	 * @param userId
	 * @return
	 */
	@RequestMapping(value="/displayEditPersonalInfo.vti", method=RequestMethod.GET)
	public String displayEditPersonalInfo(@RequestParam("userId") Integer userId) {
		Integer companyId = SessionManager.getCurrentCompanyId(getRequest());
		ResponseData rd = userService.displayEditPersonalInfo(userId, companyId);
		
		User userEdit = (User) rd.getFirstItem();
		getRequest().setAttribute("userEdit", userEdit);
		getRequest().setAttribute("userBirthday", userEdit.getBirthday());
		
		initSelectList(companyId);
		
		return "/base/baseSystem/personal/editPersonalInfo";
	}

	/**
	 * 保存个人中心用户设置
	 * 
	 * @param user
	 * @return
	 */
	@RequestMapping(value="/editPersonalInfo.vti", method=RequestMethod.POST)
	public String editPersonalInfo(@ModelAttribute("frmUser") User user) {
		if (!check1editUser(user)) {
			return displayEditPersonalInfo(user.getId());
		}
	   
		Date birthday = DateTime.toDate(getStringParameter("userBirthday"));
		
		ResponseData rd = userService.editPersonalInfo(user, getRequest(), birthday);
		if (rd.getCode() == 1) {
			message(GlobalConstant.MSG_RESULT_LEVEL_SUCCESS,
					GlobalConstant.MSG_RESULT_CONTENT, rd.getMessage());
		} else {
			message(GlobalConstant.MSG_RESULT_LEVEL_ERROR,
					GlobalConstant.MSG_RESULT_CONTENT,rd.getMessage());
		}
		return displayEditPersonalInfo(user.getId());
	}

	/**
	 * 个人中心-头像上传
	 * 
	 * @param user
	 * @param file
	 * @return
	 */
	@RequestMapping(value="/uploadPersonalAvatar.vti", method=RequestMethod.POST)
	public String uploadPersonalAvatar(@ModelAttribute("frmUser") User user, @RequestParam("userFile") MultipartFile file) {
			
			InputStream is = null;
			try {
				is = file.getInputStream();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			ResponseData rd = userService.uploadPersonalAvatar(user, user.getId(), file.getOriginalFilename(), is);
			if (rd.getCode() == 1) {
				getRequest().setAttribute("user", rd.get("user"));
				getRequest().setAttribute("newUserAvatar", rd.get("newUserAvatar"));
				return "/base/baseSystem/personal/cutPersonalAvatar";
				
			} else {
				message(GlobalConstant.MSG_RESULT_LEVEL_WARNING,
						GlobalConstant.MSG_RESULT_CONTENT,  rd.getMessage());
			}
			return displayEditPersonalInfo(user.getId());

	}
	
	/**
	 * 裁剪头像里-头像上传
	 * 
	 * @param user
	 * @param file
	 * @return
	 */
	@RequestMapping(value="/uploadCutPersonalAvatar.vti", method=RequestMethod.POST)
	public String uploadCutPersonalAvatar(@ModelAttribute("frmUser") User user, @RequestParam("userFile") MultipartFile file) {
		InputStream is = null;
		try {
			is = file.getInputStream();
		} catch (IOException e) {
			e.printStackTrace();
		}
		ResponseData rd = userService.uploadPersonalAvatar(user, user.getId(), file.getOriginalFilename(), is);
			if (rd.getCode() == 1) {
				getRequest().setAttribute("user", rd.get("user"));
				getRequest().setAttribute("newUserAvatar", rd.get("newUserAvatar"));
				
			} else {
				message(GlobalConstant.MSG_RESULT_LEVEL_WARNING,
						GlobalConstant.MSG_RESULT_CONTENT,  rd.getMessage());
				getRequest().setAttribute("user", user);
				getRequest().setAttribute("newUserAvatar", getStringParameter("newUserAvatar"));
			}
			return "/base/baseSystem/personal/cutPersonalAvatar";
	}

	/**
	 * 头像上传-裁剪头像
	 * 
	 * @param user
	 * @param userId
	 * @return
	 */
	@RequestMapping(value="/cutPersonalAvatar.vti", method=RequestMethod.POST)
	public String cutPersonalAvatar(@ModelAttribute("frmUser") User user) {
			ResponseData rd = userService.cutUserAvatar(user, user.getId(), getRequest());
			if (rd.getCode() == 1) {
				message(GlobalConstant.MSG_RESULT_LEVEL_SUCCESS,
						GlobalConstant.MSG_RESULT_CONTENT, "头像更新成功！");
				// 头像裁剪成功，同时给OBC_Server发送消息通知同步
				// String urlhttp = GlobalConfig.getObcServer() + "/sync";
				// String parameter = "password=" +
				// GlobalConfig.getPasswordOcApi() +
				// "&requestType=vcard-edit&userName=" +
				// TextUtil.escapeNode(SessionManager.getCurrentUserLoginName(request))
				// ;
				// GetHttp.doGet(urlhttp, parameter);
			}
			return displayEditPersonalInfo(user.getId());

	}

	/**
	 * 个人中心-显示修改密码页面
	 * 
	 * @param user
	 * @param userId
	 * @return
	 */
	@RequestMapping(value="/displayEditPersonalPassword.vti", method=RequestMethod.GET)
	public String displayEditPersonalPassword(@RequestParam("userId") Integer userId) {
		Integer companyId = SessionManager.getCurrentCompanyId(getRequest());
		ResponseData rd = userService.displayEditPersonalInfo(userId, companyId);
		getRequest().setAttribute("user", rd.getFirstItem());
		getRequest().setAttribute("userEdit", rd.getFirstItem());
		
		getRequest().setAttribute("pageOldPassword", getStringParameter("pageOldPassword"));
		getRequest().setAttribute("pagePassword", getStringParameter("pagePassword"));
		getRequest().setAttribute("pageConfirmPassword", getStringParameter("pageConfirmPassword"));
		
		return "/dealer/personal/editPersonalPassword";
	}

	/**
	 * 个人中心-修改密码
	 * 
	 * @param user
	 * @return
	 */
	@RequestMapping(value="/editPersonalPassword.vti", method=RequestMethod.POST)
	public String editPersonalPassword(@ModelAttribute("userEdit") User user) {
		
		String pageOldPassword = getStringParameter("pageOldPassword");
		String pagePassword = getStringParameter("pagePassword");
		String pageConfirmPassword = getStringParameter("pageConfirmPassword");
		
		if (!checkeditPersonalPassword(pageOldPassword, pagePassword, pageConfirmPassword)) {
			return displayEditPersonalPassword(user.getId());
		}

		ResponseData rd = userService.editPersonalPassword(user, pageOldPassword, pagePassword);
		if (rd.getCode() == 1) {
			message(GlobalConstant.MSG_RESULT_LEVEL_SUCCESS,
					GlobalConstant.MSG_RESULT_CONTENT, "新密码设置成功！");
			
		} else if (rd.getCode() == -1) {
			message(GlobalConstant.MSG_RESULT_LEVEL_ERROR,
					GlobalConstant.MSG_RESULT_CONTENT, "旧密码输入不正确！");
		} else {
			message(GlobalConstant.MSG_RESULT_LEVEL_ERROR,
					GlobalConstant.MSG_RESULT_CONTENT, "新密码设置失败！请重试！");
		}
		
		return displayEditPersonalPassword(user.getId());

	}

	/**
	 * 用户管理——显示修改密码页面
	 * 此功能没启用
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	 /**public ActionForward displayEditUserPassword(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		
		IUserService service = (IUserService) getBean("userService");
		//service.displayEditUser(form1, request, response);

		return (mapping.findForward("editUserPassword"));
	} */

	/**
	 * 用户管理——修改密码
	 * 此功能没启用
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	   /**	public ActionForward editUserPassword(ActionMapping mapping,
				ActionForm form, HttpServletRequest request,
				HttpServletResponse response) {
			UserForm form1 = (UserForm) form;
	
			if (!check4editUserPassword(form1, request)) {
				return displayEditUserPassword(mapping, form1, request, response);
			}
	
			int result = service.editUserPassword(form1, request, response);
			if (result == 1) {
				message(request, "新密码重置成功！");
			} else {
	
				message(request, "新密码重置失败！请重试！");
			}
			
			return listUser(mapping, form1, request, response);

	}	*/

	/**
	 * 添加用户表单验证
	 * 
	 * @param user
	 * @return
	 */
	protected boolean checkaddUser(User user) {
		String name = user.getNickName();
		if (!TypeConverter.sizeLagerZero(name)) {
			message(GlobalConstant.MSG_RESULT_LEVEL_WARNING,
					GlobalConstant.MSG_RESULT_CONTENT, "名称必须填写！");
			return false;
		}
		
		String loginName = user.getLoginName();
		if (!TypeConverter.sizeLagerZero(loginName)) {
			message(GlobalConstant.MSG_RESULT_LEVEL_WARNING,
					GlobalConstant.MSG_RESULT_CONTENT, "帐号必须填写！");
			return false;
		/**} else if (!ValidatorUtil.isAlphaNumeric(loginName)) {
			message(GlobalConstant.MSG_RESULT_LEVEL_WARNING,
					GlobalConstant.MSG_RESULT_CONTENT, "帐号只能包含字母和数字！");
			return false; */
		} else if ("www".equals(loginName)
				|| "help".equals(loginName)
				|| "api".equals(loginName)
				|| "sso".equals(loginName)
				|| "admin".equals(loginName)
				|| "system".equals(loginName)
				|| "back".equals(loginName)
				|| "base".equals(loginName)
				|| "search".equals(loginName)
				|| "group".equals(loginName)
				|| "vti".equals(loginName)) {
			message(GlobalConstant.MSG_RESULT_LEVEL_WARNING,
					GlobalConstant.MSG_RESULT_CONTENT, "帐号为保留关键字，不可用！");
			return false;
		}
		
		String pseudoPassword = user.getPseudoPassword();
		if (pseudoPassword == null || "".equals(pseudoPassword)) {
			message(GlobalConstant.MSG_RESULT_LEVEL_WARNING,
					GlobalConstant.MSG_RESULT_CONTENT, "密码必须填写！");
			return false;
		}

		if (!ValidatorUtil.isAlphaNumeric(pseudoPassword)) {
			message(GlobalConstant.MSG_RESULT_LEVEL_WARNING,
					GlobalConstant.MSG_RESULT_CONTENT, "输入的密码格式不正确！");
			return false;
		}
		if (!ValidatorUtil.isValidSize4Password(pseudoPassword)) {
			message(GlobalConstant.MSG_RESULT_LEVEL_WARNING,
					GlobalConstant.MSG_RESULT_CONTENT, "密码长度应在6-16位之间！");
			return false;
		}

		String email = user.getEmail();
		if (!TypeConverter.sizeLagerZero(email)) {
			message(GlobalConstant.MSG_RESULT_LEVEL_WARNING,
					GlobalConstant.MSG_RESULT_CONTENT, "电子邮箱不能为空！");
			return false;
		} 
		
		if (!ValidatorUtil.isEmail(email)) {
			message(GlobalConstant.MSG_RESULT_LEVEL_WARNING,
					GlobalConstant.MSG_RESULT_CONTENT, "电子邮箱格式不正确！");
			return false;
		} else if (userService.emailIsExistence(email)) {
			message(GlobalConstant.MSG_RESULT_LEVEL_WARNING,
					GlobalConstant.MSG_RESULT_CONTENT, "email已存在！");
			return false;
		}
		
		String mobile = user.getMobile();
		if (TypeConverter.sizeLagerZero(mobile)) {
			if (!ValidatorUtil.isMobile(mobile)) {
				message(GlobalConstant.MSG_RESULT_LEVEL_WARNING,
						GlobalConstant.MSG_RESULT_CONTENT, "手机号格式不正确！");
				return false;
			}
		} 
		
		List<Integer> selectRoles = user.getSelectRoles();
		if (selectRoles == null || selectRoles.size() == 0) {
			message(GlobalConstant.MSG_RESULT_LEVEL_WARNING,
					GlobalConstant.MSG_RESULT_CONTENT, "请选择用户角色！");
			return false;
		}

		return true;

	}

	/**
	 * 个人中心用户设置表单验证
	 * 
	 * @param user
	 * @return
	 */
	protected boolean check1editUser(User user) {
		String name = user.getNickName();
		if (!TypeConverter.sizeLagerZero(name)) {
			message(GlobalConstant.MSG_RESULT_LEVEL_WARNING, GlobalConstant.MSG_RESULT_CONTENT, "名称必须填写！");
			return false;
		}
		
		String email = user.getEmail();
		if (TypeConverter.sizeLagerZero(email)) {
			if (!ValidatorUtil.isEmail(email)) {
				message(GlobalConstant.MSG_RESULT_LEVEL_WARNING,
						GlobalConstant.MSG_RESULT_CONTENT, "电子邮箱格式不正确！");
				return false;
			}
		}
		return true;
	}

	/**
	 * 管理员编辑用户表单验证
	 * 
	 * @param user
	 * @return
	 */
	protected boolean check2editUser(User user) {
		String name = user.getNickName();
		if (!TypeConverter.sizeLagerZero(name)) {
			message(GlobalConstant.MSG_RESULT_LEVEL_WARNING,
					GlobalConstant.MSG_RESULT_CONTENT, "名称必须填写！");
			return false;
		}
		
		String email = user.getEmail();
		if (!TypeConverter.sizeLagerZero(email)) {
			message(GlobalConstant.MSG_RESULT_LEVEL_WARNING,
					GlobalConstant.MSG_RESULT_CONTENT, "电子邮箱不能为空！");
			return false;
		} 
		
		if (TypeConverter.sizeLagerZero(email)) {
			if (!ValidatorUtil.isEmail(email)) {
				message(GlobalConstant.MSG_RESULT_LEVEL_WARNING,
						GlobalConstant.MSG_RESULT_CONTENT, "电子邮箱格式不正确！");
				return false;
			}
		}
		String mobile = user.getMobile();
		if (TypeConverter.sizeLagerZero(mobile)) {
			if (!ValidatorUtil.isMobile(mobile)) {
				message(GlobalConstant.MSG_RESULT_LEVEL_WARNING,
						GlobalConstant.MSG_RESULT_CONTENT, "手机号格式不正确！");
				return false;
			}
		} 
		
		List<Integer> selectRoles = user.getSelectRoles();
		if (selectRoles == null || selectRoles.size() == 0) {
			message(GlobalConstant.MSG_RESULT_LEVEL_WARNING,
					GlobalConstant.MSG_RESULT_CONTENT, "请选择用户角色！");
			return false;
		}

		return true;
	}

	/**
	 * 用户管理——修改密码——表单验证
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	protected boolean check4editUserPassword(User user) {
		String pagePassword = user.getPagePassword();
		String pageConfirmPassword = user.getPageConfirmPassword();

		if (pagePassword == null || "".equals(pagePassword)) {
			message(GlobalConstant.MSG_RESULT_LEVEL_WARNING,
					GlobalConstant.MSG_RESULT_CONTENT, "新密码必须填写");
			return false;
		}
		if (!ValidatorUtil.isValidSize4Password(pagePassword)) {
			message(GlobalConstant.MSG_RESULT_LEVEL_WARNING,
					GlobalConstant.MSG_RESULT_CONTENT, "密码长度应在6-15位之间！");
			return false;
		}
		if (pageConfirmPassword == null || "".equals(pageConfirmPassword)) {
			message(GlobalConstant.MSG_RESULT_LEVEL_WARNING,
					GlobalConstant.MSG_RESULT_CONTENT, "确认新密码必须填写");
			return false;
		}
		if (!pagePassword.equals(pageConfirmPassword)) {
			message(GlobalConstant.MSG_RESULT_LEVEL_WARNING,
					GlobalConstant.MSG_RESULT_CONTENT, "新密码和确认新密码输入不一致！");
			return false;
		}
		return true;
	}

	/**
	 * 个人中心——修改密码——表单验证
	 * 
	 * @param user
	 * @return
	 */
	protected boolean checkeditPersonalPassword(String pageOldPassword, String pagePassword, String pageConfirmPassword) {
		if (pageOldPassword == null || "".equals(pageOldPassword)) {
			message(GlobalConstant.MSG_RESULT_LEVEL_WARNING,
					GlobalConstant.MSG_RESULT_CONTENT, "现在的密码必须填写！");
			return false;
		}
		if (pagePassword == null || "".equals(pagePassword)) {
			message(GlobalConstant.MSG_RESULT_LEVEL_WARNING,
					GlobalConstant.MSG_RESULT_CONTENT, "新密码必须填写！");
			return false;
		}
		if (!ValidatorUtil.isAlphaNumeric(pagePassword)) {
			message(GlobalConstant.MSG_RESULT_LEVEL_WARNING,
					GlobalConstant.MSG_RESULT_CONTENT, "输入的密码格式不正确！");
			return false;
		}
		if (!ValidatorUtil.isValidSize4Password(pagePassword)) {
			message(GlobalConstant.MSG_RESULT_LEVEL_WARNING,
					GlobalConstant.MSG_RESULT_CONTENT, "密码长度应在6-16位之间！");
			return false;
		}
		if (pageConfirmPassword == null || "".equals(pageConfirmPassword)) {
			message(GlobalConstant.MSG_RESULT_LEVEL_WARNING,
					GlobalConstant.MSG_RESULT_CONTENT, "重复新密码必须填写！");
			return false;
		}
		if (!pagePassword.equals(pageConfirmPassword)) {
			message(GlobalConstant.MSG_RESULT_LEVEL_WARNING,
					GlobalConstant.MSG_RESULT_CONTENT, "新密码和重复新密码输入不一致！");
			return false;
		}
		if (pageConfirmPassword.equals(pageOldPassword)) {
			message(GlobalConstant.MSG_RESULT_LEVEL_WARNING,
					GlobalConstant.MSG_RESULT_CONTENT, "新密码不能和现在的密码相同！");
			return false;
		}
		return true;
	}

}
