package com.ava.baseSystem.service.impl;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ava.base.dao.IHibernateDao;
import com.ava.base.dao.hibernate.TransMsg;
import com.ava.base.dao.util.ParameterUtil;
import com.ava.base.domain.ResponseData;
import com.ava.base.domain.UploadReturnData;
import com.ava.baseSystem.service.IUserService;
import com.ava.dao.ICompanyDao;
import com.ava.dao.IRoleDao;
import com.ava.dao.IUserDao;
import com.ava.dao.IUserRoleRelationDao;
import com.ava.dealer.service.impl.BaseService;
import com.ava.domain.entity.Company;
import com.ava.domain.entity.Org;
import com.ava.domain.entity.Role;
import com.ava.domain.entity.User;
import com.ava.domain.entity.UserRoleRelation;
import com.ava.domain.vo.UserLogFindVO;
import com.ava.resource.GlobalConfig;
import com.ava.resource.GlobalConstant;
import com.ava.resource.SessionManager;
import com.ava.resource.cache.CacheOrgManager;
import com.ava.resource.cache.CacheUserManager;
import com.ava.util.DateTime;
import com.ava.util.FileUtil;
import com.ava.util.ImageProcess;
import com.ava.util.MyBeanUtils;
import com.ava.util.ReadTemplate;
import com.ava.util.TextUtil;
import com.ava.util.TypeConverter;
import com.ava.util.UploadUtil;
import com.ava.util.ValidationCodeUtil;
import com.ava.util.encrypt.EncryptUtil;

@Service
public class UserService extends BaseService implements IUserService {

	@Autowired
	private IHibernateDao hibernateDao;
	
	@Autowired
	private IUserDao userDao;

	@Autowired
	private ICompanyDao companyDao;
	
	@Autowired
	private IRoleDao  roleDao;
	
	@Autowired
	private IUserRoleRelationDao userRoleRelationDao;

	public ResponseData listUser(TransMsg transMsg, User currentUser, Integer currentCompanyId, HttpServletRequest request, boolean isExport) {
		ResponseData rd = new ResponseData(0);
		if(isExport == true) {
			transMsg.setPageSize(Integer.MAX_VALUE);
		} else {
			transMsg.setPageSize(GlobalConstant.DEFAULT_TABLE_ROWS);
		}

		String additionalCondition = "";
		
		Integer orgId = currentUser.getOrgId();
		if (currentUser.getOrgId() == null) {
			orgId = currentCompanyId;
		}
		Org org = CacheOrgManager.get(orgId);
		if (org != null) {
			//如果组织选择的经销商
			if (org.getOrgType() == GlobalConstant.ABSTRACT_ORG_TYPE_COMPANY) {
				additionalCondition += " and companyId in("
						+ CacheOrgManager.getChildrenOrgIdsStr(orgId, GlobalConstant.TRUE) + ")";
			}
			//如果组织选择的部门
			if (org.getOrgType() == GlobalConstant.ABSTRACT_ORG_TYPE_DEPARTMENT) {
				additionalCondition += " and departmentId in("
						+ CacheOrgManager.getChildrenOrgIdsStr(orgId, GlobalConstant.TRUE) + ")";
			}
		}
		
		if (currentUser.getLoginName() != null
				&& currentUser.getLoginName().length() > 0) {
			ParameterUtil.appendLikeQueryCondition(transMsg.getParameters(), "loginName",
					currentUser.getLoginName());
		}
		
		if (currentUser.getNickName() != null
				&& currentUser.getNickName().length() > 0) {
			ParameterUtil.appendLikeQueryCondition(transMsg.getParameters(), "nickName",
					currentUser.getNickName());
		}
		
		if (currentUser.getPositionId() != null
				&& currentUser.getPositionId().intValue() >= 0) {
			transMsg.put("positionId", currentUser.getPositionId());
		}
		
		additionalCondition = additionalCondition + " order by loginName asc";
		List userList = userDao.findByPage(transMsg, additionalCondition);
        
		if (userList != null && userList.size() > 0) {
			for (int i = 0; i < userList.size(); i++) {
				User user = (User) userList.get(i);
				
				//"admin@group"不能被删除，并且自己不能被删除
				String loginName = user.getLoginName();
				String currentLoginName = SessionManager.getCurrentUserLoginName(request);
				if (loginName.equals("admin@group") || loginName.equals(currentLoginName)) {
					user.getUserRight().setEditorAndDeleteCompetence(GlobalConstant.FALSE);
				} else {
					user.getUserRight().setEditorAndDeleteCompetence(GlobalConstant.TRUE);
				}
				
				
				//显示角色名称
				HashMap parameters = new HashMap();
				parameters.put("userId", user.getId());
				
				ArrayList<UserRoleRelation> userRoleRelationList = (ArrayList<UserRoleRelation>) userRoleRelationDao.find(parameters, null);
				if (userRoleRelationList != null && userRoleRelationList.size() > 0) {
					for(UserRoleRelation roleRelation : userRoleRelationList){
						HashMap parametersTemp = new HashMap();
						parametersTemp.put("id", roleRelation.getRoleId());
						Role role =(Role)(roleDao.find(parametersTemp, "").get(0));
						if (role != null) {
							roleRelation.setRole(role);
						}
					}
					
					String roleString ="";
					for(UserRoleRelation roleRelation  : userRoleRelationList)
					{
						roleString += roleRelation.getRole().getName()+"  ";
					}
					user.setRoles(roleString);
				}
			}
		}
        
		rd.setFirstItem(userList);
        rd.setCode(1);
		return rd;
	}

	public ResponseData displayAddUser(User userAdd) {
		ResponseData rd = new ResponseData(0);
		if (userAdd == null) {
			userAdd = new User();
			userAdd.init();
		}
		
		//将所有的角色放入roleList中
		ArrayList<Role> roleList = (ArrayList<Role>) roleDao.find(null, null);
		if (roleList != null && roleList.size() >0) {
			for (Role role : roleList) {
				userAdd.getAllRoles().put(role.getId(), role.getName());
			}	
		}

	    rd.put("userAdd", userAdd);
		return rd;
	}

	public ResponseData addUser(User userAdd, Integer companyId, String obName) {
		ResponseData rd = new ResponseData(0);
		
		Integer orgId = userAdd.getOrgId();
		Org org = CacheOrgManager.get(orgId);
		if (org != null) {
				userAdd.setCompanyId(orgId);
				userAdd.setDepartmentId(orgId);
		}

		if (userDao.getByLoginName(userAdd.getLoginName()) != null) {
			// 用户登录名已存在
			rd.setCode(-1);
			rd.setMessage("登录帐号已经存在！");
			return rd;
		}
		
		userAdd.init();
		userAdd.setIsFirstLogin(GlobalConstant.FALSE);// 新用户
		userAdd.setIsModifyPassword(GlobalConstant.FALSE);// 首次登录是否修改了密码
		String pseudoPassword = userAdd.getPseudoPassword();// 登录密码
		userAdd.setEncryptedPassword(EncryptUtil.encryptPassword(pseudoPassword));
		userAdd.setObName("shvw");
		userAdd.setSex(1);

		Integer newUserId = (Integer) userDao.save(userAdd);
		userAdd.setId(newUserId);

		if (newUserId != null) {
			// 新增用户成功，给组织用户数+1
			Company company = companyDao.get(orgId);
			if (company != null) {
				if (company.getUserNum() == null) {
					company.setUserNum(1);
				} else {
					company.setUserNum(company.getUserNum().intValue() + 1);
				}
				companyDao.edit(company);
			}
			
			List<Integer> selectRoles = userAdd.getSelectRoles();
			if (selectRoles != null) {
				for (Integer roleId : selectRoles) {
					UserRoleRelation userRoleRelation = new UserRoleRelation();
					userRoleRelation.setUserId(newUserId);
					userRoleRelation.setRoleId(roleId);
					userRoleRelationDao.save(userRoleRelation);
				}
			}

		}

		rd.setCode(1);
		rd.setMessage("用户新增成功！");
		rd.setFirstItem(userAdd);
		return rd;
	}

	public ResponseData displayEditUser(Integer userId, Integer companyId) {
		ResponseData rd = new ResponseData(0);

        User userEdit = userDao.get(userId);
		Date birthday = userEdit.getBirthday();
		userEdit.setOrgId(userEdit.getCompanyId());
		
		//将所有的角色放入roleList中
		ArrayList<Role> roleList = (ArrayList<Role>) roleDao.find(null, null);
		if (roleList != null && roleList.size() >0) {
			for (Role role : roleList) {
				userEdit.getAllRoles().put(role.getId(), role.getName());
			}	
		}
		//将拥有的角色放入selectRoles中
		HashMap parameters = new HashMap();
		parameters.put("userId", userId);
		ArrayList<UserRoleRelation> userRoleRelationList = (ArrayList<UserRoleRelation>) userRoleRelationDao.find(parameters, null);
		if (userRoleRelationList != null && userRoleRelationList.size() > 0) {
			for(UserRoleRelation roleRelation : userRoleRelationList){
				HashMap parametersTemp = new HashMap();
				Role role =roleDao.get(roleRelation.getRoleId());
				userEdit.getSelectRoles().add(role.getId()); 
				
			}
		}

		userEdit.setBirthday(birthday);
		rd.put("user", userEdit);
		return rd;
	}

	public ResponseData editUser(User userEdit, HttpServletRequest request) {
		ResponseData rd = new ResponseData(0);

		Integer orgId = userEdit.getOrgId();
		Org org = CacheOrgManager.get(orgId);
		if (org != null) {
			userEdit.setCompanyId(orgId);
			userEdit.setDepartmentId(orgId);
		}
		
		Integer userId = userEdit.getId();
		if ((userId == null) || (userId != null && userId.intValue() <= 0)) {
			userId = userEdit.getId();
		}

		User dbUser = userDao.get(userId);

		Integer companyId = userEdit.getCompanyId();
		if (companyId == null || companyId.intValue() <= 0) {
			companyId = SessionManager.getCurrentCompanyId(request);
		}

		if (userEdit.getEmail() != null
				&& !userEdit.getEmail().equalsIgnoreCase(dbUser.getEmail())) {
			// 如果用户要修改email地址，则判断新的email是否已经存在
			if (this.emailIsExistence(userEdit.getEmail())) {
				rd.setCode(-1);
				rd.setMessage("用户信息保存失败：新的E-Mail已存在！");
				return rd;
			}
		}
		if (userEdit.getIsDisable() == null) {
			userEdit.setIsDisable(GlobalConstant.FALSE);
		}
		MyBeanUtils.copyPropertiesContainsDate(dbUser, userEdit);
		dbUser.setBirthday(userEdit.getBirthday());
		userDao.edit(dbUser);
		
		configRoleRelation(userEdit);
		
		// 重置用户缓存中的对象
		CacheUserManager.removeUser(dbUser.getId());

		rd.setFirstItem(dbUser);
		rd.setCode(1);
		rd.setMessage("用户信息修改成功！");
		return rd;
	}
	
	/**
	 * 设置权限
	 * @param userEdit
	 */
	public void configRoleRelation(User userEdit) {
		HashMap parameters = new HashMap();
		parameters.put("userId", userEdit.getId());
		List userRoleRelationList = userRoleRelationDao.find(parameters, "");
		if (userRoleRelationList != null && userRoleRelationList.size() > 0) {
			for (int i = 0; i < userRoleRelationList.size(); i++) {
				UserRoleRelation userRoleRelation = (UserRoleRelation) userRoleRelationList.get(i);
				userRoleRelationDao.delete(userRoleRelation.getId());
			}
		}
		
		List<Integer> selectRoles = userEdit.getSelectRoles();
		if (selectRoles != null && selectRoles.size() > 0) {
			for (Integer roleId : selectRoles) {
				UserRoleRelation userRoleRelation = new UserRoleRelation();
				userRoleRelation.setUserId(userEdit.getId());
				userRoleRelation.setRoleId(roleId);
				userRoleRelationDao.save(userRoleRelation);
			}
		}
	}
	
	public ResponseData editPersonalInfo(User userEdit, HttpServletRequest request, Date birthday) {
		ResponseData rd = new ResponseData(0);
		
		Integer userId = userEdit.getId();
		if ((userId == null) || (userId != null && userId.intValue() <= 0)) {
			userId = userEdit.getId();
		}
		
		User dbUser = userDao.get(userId);
		
		Integer companyId = userEdit.getCompanyId();
		if (companyId == null || companyId.intValue() <= 0) {
			companyId = SessionManager.getCurrentCompanyId(request);
		}
		
		if (userEdit.getEmail() != null
				&& !userEdit.getEmail().equalsIgnoreCase(dbUser.getEmail())) {
			// 如果用户要修改email地址，则判断新的email是否已经存在
			if (this.emailIsExistence(userEdit.getEmail())) {
				rd.setCode(-1);
				rd.setMessage("用户信息保存失败：新的E-Mail已存在！");
				return rd;
			}
		}
		
		MyBeanUtils.copyPropertiesContainsDate(dbUser, userEdit);
		dbUser.setBirthday(birthday);
		userDao.edit(dbUser);
		// 重置用户缓存中的对象
		CacheUserManager.removeUser(dbUser.getId());
		
		rd.setFirstItem(dbUser);
		rd.setCode(1);
		rd.setMessage("用户信息修改成功！");
		return rd;
	}

	public ResponseData deleteUser(Integer userId, HttpServletRequest request) {
		ResponseData rd = new ResponseData(0);

		User dbUser = userDao.get(userId);
		if (dbUser == null) {
			rd.setCode(-2);
			rd.setMessage("用户对象为空！");
			return rd;
		}
		
		//"admin@group" 不允许删除"
		if ("admin@group".equals(dbUser.getLoginName())) {
			rd.setCode(-1);
			rd.setMessage("该用户为系统管理员，不能删除！");
			return rd;
		}
		
		dbUser.setDeletionFlag(1);// 删除状态
		// 删除该用户
		userDao.edit(dbUser);
		configRoleRelation(dbUser);

		// 从用户缓存表移除该用户对象
		CacheUserManager.removeUser(userId);
        
		rd.setFirstItem(dbUser);
		rd.setCode(1);
		rd.setMessage("用户信息删除成功！");
		return rd;
	}

	public ResponseData uploadPersonalAvatar(User user, Integer userId, String originalFilename, InputStream is) {
		ResponseData rd = new ResponseData(0);

		if ((userId == null) || (userId != null && userId.intValue() <= 0)) {
			userId = user.getId();
		}

		// 头像上传限制10M
		rd = UploadUtil.upload(originalFilename, is,
				GlobalConstant.UploadPathName_Avatar + GlobalConstant.FILE_SEPARATOR + "/temp",
				GlobalConstant.UPLOAD_FILE_MAX_SIZE, true, false, false, false);

		if (rd.getCode() == 1) {
			// 文件上传成功
			UploadReturnData urd = ((UploadReturnData) rd.getFirstItem());
			User dbUser = userDao.get(userId);
			String newUserAvatar = urd.getFullPath();

			rd.put("user", dbUser);
			rd.put("newUserAvatar", newUserAvatar);
		}

		return rd;
	}

	public ResponseData cutUserAvatar(User user, Integer userId, HttpServletRequest request) {
		ResponseData rd = new ResponseData(0);
		if ((userId == null) || (userId != null && userId.intValue() <= 0)) {
			userId = user.getId();
		}

		User dbUser = userDao.get(userId);
		rd.setFirstItem(dbUser);

		int pLeft = TypeConverter.toInteger(request.getParameter("pLeft")); // 距离左边
		int pTop = TypeConverter.toInteger(request.getParameter("pTop")); // 距离顶部
		int CutWidth = TypeConverter.toInteger(request
				.getParameter("dropWidth")); // 截取框的宽
		int CutHeight = TypeConverter.toInteger(request
				.getParameter("dropHeight"));// 截取框的高
		int PicWidth = TypeConverter.toInteger(request.getParameter("pWidth")); // 图片宽度
		int PicHeight = TypeConverter
				.toInteger(request.getParameter("pHeight")); // 图片高度

		// 上传的头像临时目录的位置（绝对地址）
		String srcImageFile = GlobalConfig.getDefaultAppPath()
				+ ParameterUtil.getStringParameter(request, "newUserAvatar");
		// 将要裁剪出的头像文件位置目录（虚拟地址）
		String virtualPath = GlobalConstant.UploadPathName_Avatar
				+ GlobalConstant.FILE_SEPARATOR
				+ SessionManager.getCurrentCompanyId(request)
				+ GlobalConstant.UploadPathName_Date
				+ GlobalConstant.FILE_SEPARATOR
				+ FileUtil.getFileName(srcImageFile);
		// 将要裁剪出的头像文件位置目录（绝对地址）
		String newImageFile = GlobalConfig.getDefaultAppPath() + virtualPath;

		ImageProcess.cut(srcImageFile, newImageFile, CutWidth, CutHeight,
				pLeft, pTop, PicWidth, PicHeight);
		// 头像裁剪完成，开始删除老的图片
		String oldFile = dbUser.getAvatar();
		if (oldFile == null || "".equals(oldFile)
				|| "/images/noAvatar.gif".equals(oldFile)) {
		} else {
			FileUtil.delFile(GlobalConfig.getDefaultAppPath() + oldFile);
		}
		dbUser.setAvatar(virtualPath);
		// 持久化到数据库中
		userDao.edit(dbUser);
		// 重置用户缓存中的对象
		CacheUserManager.removeUser(dbUser.getId());

		// 开始处理VCard表的头像数据
		/*
		 * SAXReader xmlReader = new SAXReader(); String photoValue = null; try
		 * { photoValue = FileUtil.getBytes(newImageFile); } catch (IOException
		 * e) { e.printStackTrace(); } // 持久化ofVCard对象 OfVCard ofVCard = null;
		 * ofVCard = ofVCardDao.getOfVCardByUserName(dbUser.getUsername()); //
		 * 如果ofVCard表中没有图像数据，把图片转换后的字符串 填入VCard字段。， if (ofVCard == null) {
		 * Element newElement = null; DocumentFactory docFactory =
		 * DocumentFactory.getInstance(); // 组成XML格式的字符串<vCard //
		 * xmlns="vcard-temp"><PHOTO><TYPE>image/jpeg</TYPE><BINVAL> //
		 * xxxxxxx图片byte型区域xxxxxx</BINVAL></PHOTO></vCard> // newElement =
		 * docFactory.createDocument().addElement("vCard"); //
		 * newElement.addAttribute("xmlns", "vcard-temp"); newElement =
		 * docFactory.createDocument().addElement("vCard", "vcard-temp");
		 * Element photo = newElement.addElement("PHOTO"); //
		 * photo.addElement("TYPE", "image/jpeg"); // photo.addElement("BINVAL",
		 * photoValue); photo.addElement("TYPE").setText("image/jpeg");
		 * photo.addElement("BINVAL").setText(photoValue);
		 * 
		 * ofVCard = new OfVCard(); ofVCard.setUsername(dbUser.getUsername());
		 * ofVCard.setVcard(newElement.asXML()); ofVCardDao.edit(ofVCard); }
		 * else { // 如果ofVCard表中有图像数据.则把老的图像字符串数据替换成新的 String oldVCardXML =
		 * ofVCard.getVcard(); try { // vCard存在命名空间造成解析错误的处理 Map map = new
		 * HashMap(); map.put("nsVCard", "vcard-temp");
		 * xmlReader.getDocumentFactory().setXPathNamespaceURIs(map);
		 * 
		 * Element oldElement = xmlReader.read( new
		 * StringReader(oldVCardXML)).getRootElement(); //
		 * 查询vCard根目录下PHOTO的子元素BINVAL List nodes =
		 * oldElement.selectNodes("//nsVCard:PHOTO"); if (nodes != null) {
		 * Iterator itor = nodes.iterator(); // 替换掉查询迭代出的元素值（图片转换后的字符窜）， while
		 * (itor.hasNext()) { Element theElement = (Element) itor.next();
		 * Iterator iterator = theElement .elementIterator("BINVAL"); if
		 * (iterator.hasNext()) { Element aElement = (Element) iterator.next();
		 * aElement.setText(photoValue); ofVCard.setVcard(oldElement.asXML());
		 * ofVCardDao.edit(ofVCard); } } } } catch (DocumentException e) {
		 * e.printStackTrace(); }
		 * 
		 * }
		 */
		// VCard表的头像数据处理结束
		rd.setCode(1);
		return rd;
	}

	public ResponseData editPersonalPassword(User user, String pageOldPassword, String pagePassword) {
		ResponseData rd = new ResponseData(0);

		User dbUser = userDao.get(user.getId());

		if (user == null || dbUser == null) {
			rd.setCode(0);
			rd.setMessage("用户对象为空");
			return rd;
		}
		
		if (!pageOldPassword.equals(EncryptUtil.decryptPassword(dbUser
				.getEncryptedPassword()))) {
			rd.setCode(-1);
			rd.setMessage("旧密码输入不正确");
			return rd;
		}
		dbUser.setPseudoPassword(pagePassword);
		dbUser.setEncryptedPassword(EncryptUtil.encryptPassword(pagePassword));

		userDao.edit(dbUser);
		// 重置用户缓存中的对象
		CacheUserManager.removeUser(dbUser.getId());

		rd.setFirstItem(dbUser);
        
		rd.setCode(1);
		rd.setMessage("密码修改成功！");
		return rd;
	}

	public ResponseData editUserPassword(User user) {
		ResponseData rd = new ResponseData(0);

		Integer userId = user.getId();
		User dbUser = userDao.get(userId);
		if (dbUser == null) {
			rd.setCode(0);
			rd.setMessage("用户对象为空");
			return rd;
		}
		String pagePassword = user.getPagePassword();
		dbUser.setEncryptedPassword(EncryptUtil.encryptPassword(pagePassword));

		userDao.edit(dbUser);
		// 重置用户缓存中的对象
		CacheUserManager.removeUser(dbUser.getId());

		rd.setFirstItem(dbUser);
		rd.setCode(1);
		rd.setMessage("密码修改成功！");

		return rd;
	}

	public ResponseData displayEditUserRightsCode(Integer userId,Integer companyId) {
		ResponseData rd = new ResponseData(0);
		
		User dbUser = userDao.get(userId);
		if ("admin".equalsIgnoreCase(dbUser.getPrefixLoginName())) {
			// 如果是admin用户，则默认所有权限都有，无法改变
			rd.setCode(-1);
			rd.setMessage("admin用户无法编辑权限！");
			return rd;
		}
		String oldXML = TextUtil.disableBreakLineTag(ReadTemplate
				.getUserRightsCodeContent(""));
		
		rd.put("user", dbUser);
		rd.put("dhtmlXtreeXML", oldXML);

		return rd;
	}

	/** 编辑用户权限 */
	public ResponseData editUserRightsCode(User user, HttpServletRequest request) {
		ResponseData rd = new ResponseData(0);
		User dbUser = userDao.get(user.getId());

		dbUser.setRightsCode(user.getRightsCode());
		userDao.edit(dbUser);
		// 由于需要跳回用户编辑页面，所以需要把userid赋值到request中，以供displayEditUser()方法调用
		request.setAttribute("userId", user.getId());
		
		String rightsCodeOfPosition = "";
		dbUser.getUserRight().processRightCode(dbUser.getPrefixLoginName(), dbUser.getRightsCode(), rightsCodeOfPosition);

		// 更新用户缓存
		CacheUserManager.removeUser(dbUser.getId());
		rd.setFirstItem(dbUser);
		rd.setCode(1);
		return rd;
	}

	public boolean emailIsExistence(String email) {
		if (email == null || email.length() <= 0) {
			return false;
		}
		HashMap parameters = new HashMap();
		parameters.put("email", email);
		List result = (List) hibernateDao.find("User", parameters, "");
		if (result != null && result.size() > 0) {
			return true;
		}
		return false;
	}
    
	public ResponseData reSendTempPassword(Integer userId) {
		ResponseData rd = new ResponseData(0);

		User dbUser = userDao.get(userId);
		// 重置临时密码
		String tempPassword = ValidationCodeUtil.nextValidationCode(6);// 临时密码
		dbUser.setTempPassword(tempPassword);
		// 重置临时密码有效时间
		dbUser.setTempPasswordExpiration(DateTime.getTimestamp());
		userDao.edit(dbUser);
		
		rd.setFirstItem(dbUser);
		rd.setCode(1);
		return rd;
	}

	public ResponseData displayEditPersonalInfo(Integer userId, Integer orgId) {
		ResponseData rd = new ResponseData(0);

		User userEdit = userDao.get(userId);
		Date birthday = userEdit.getBirthday();
		//MyBeanUtils.copyPropertiesExceptDate(dbUser, user);

		userEdit.setBirthday(birthday);
        rd.setFirstItem(userEdit);
        rd.setCode(1);
		return rd;
	}
		
	public ResponseData viewUser(Integer userId) {
		ResponseData rd = new ResponseData(0);
		User user = userDao.get(userId);
		
		HashMap parameters = new HashMap();
		parameters.put("userId", userId);
		
		ArrayList<UserRoleRelation> roleList = (ArrayList<UserRoleRelation>) userRoleRelationDao.find(parameters, null);
		if (roleList != null && roleList.size()>0) {
			for(UserRoleRelation roleRelation : roleList){
				HashMap parametersTemp = new HashMap();
				parametersTemp.put("id", roleRelation.getRoleId());
				Role role =(Role)(roleDao.find(parametersTemp, "").get(0));
				roleRelation.setRole(role);
			}
			
			String roleString ="";
			for(UserRoleRelation roleRelation  : roleList)
			{
				roleString += roleRelation.getRole().getName()+"  ";
			}
			user.setRoles(roleString);
		}
		
		//设置用户在线状态
		if(SessionManager.isOnline(user.getId())) {
			user.setOnlineStatus(GlobalConstant.TRUE);
			user.setOnlineStatus_Nick("在线");
		} else {
			user.setOnlineStatus(GlobalConstant.FALSE);
			user.setOnlineStatus_Nick("离线");
		}
		
		rd.put("user", user);
		return rd;
	}

	public ResponseData addAdmin(Company company, Integer companyId) {
			ResponseData rd = new ResponseData(0);
	
			String loginName = company.getDealerCode();//登录名为网络形态
					
			if (userDao.getByLoginName(loginName) != null) {
				rd.setCode(-1);
				rd.setMessage("登录帐号已经存在！");
				return rd;
			}
			
			User userAdd = new User();
			userAdd.init();
			userAdd.setCompanyId(company.getId());
			userAdd.setDepartmentId(company.getId());
			userAdd.setLoginName(loginName);
			userAdd.setNickName(company.getDealerCode());
			userAdd.setMobile(company.getContactTel());
			userAdd.setEmail(company.getEmail());
			userAdd.setIsFirstLogin(GlobalConstant.FALSE);// 新用户
			userAdd.setIsModifyPassword(GlobalConstant.FALSE);// 首次登录是否修改了密码
			//String pseudoPassword = ValidationCodeUtil.nextValidationCode(6);// 登录密码
			String pseudoPassword = "000000";// 登录密码
			userAdd.setPseudoPassword(pseudoPassword);
			userAdd.setEncryptedPassword(EncryptUtil.encryptPassword(pseudoPassword));
			userAdd.setObName(company.getObName());
			userAdd.setSex(1);
			
			Integer newUserId = (Integer) userDao.save(userAdd);
			userAdd.setId(newUserId);
	
			if (newUserId != null) {
				// 新增用户成功，给组织用户数+1
				if (company.getUserNum() == null) {
					company.setUserNum(1);
				} else {
					company.setUserNum(company.getUserNum().intValue() + 1);
				}
				companyDao.edit(company);
			}
	
			rd.setCode(1);
			rd.setMessage("管理员新增成功！");
			rd.setFirstItem(userAdd);
			return rd;
		}

	@Override
	public ResponseData listUserLog(TransMsg transMsg, String dealerCode, String startTime, String endTime, boolean isExport) {
		ResponseData rd = new ResponseData(1);
		StringBuffer exeSql = new StringBuffer(" SELECT ");
		StringBuffer exeCountSql = new StringBuffer(" SELECT COUNT(1) ");
		exeSql.append(" c.dealer_code as dealerCode,c.cn_name as companyName, ul.action_time as actionTime ");
		String userLogSql = " from t_user_log ul, t_company c where ul.company_id=c.org_id and ul.action_name='用户登录' ";
		exeSql.append(userLogSql);
		exeCountSql.append(userLogSql);
		
		
		if (StringUtils.isNotBlank(dealerCode)) {
			exeSql.append(" and c.dealer_code like \"%").append(dealerCode).append("%\" ");
			exeCountSql.append(" and c.dealer_code like \"%").append(dealerCode).append("%\" ");
		}
		
		if(StringUtils.isNotBlank(startTime)){
			exeSql.append(" and ul.action_time >= date_format('").append(startTime).append("','%Y-%m-%d %T')");
			exeCountSql.append(" and ul.action_time >= date_format('").append(startTime).append("','%Y-%m-%d %T')");
		}
		
		if(StringUtils.isNotBlank(endTime)){
			exeSql.append(" and ul.action_time <= date_format('").append(endTime).append("','%Y-%m-%d %T')");
			exeCountSql.append(" and ul.action_time <= date_format('").append(endTime).append("','%Y-%m-%d %T')");
		}
		exeSql.append(" order by c.dealer_code, ul.action_time ");
		
		//设置分页
		exeSql.append(getPaginationSql(transMsg, exeCountSql, isExport));
		List<UserLogFindVO> userLogFindVOList = hibernateDao.executeSQLQueryVoList(exeSql.toString(), UserLogFindVO.class);
		rd.setFirstItem(userLogFindVOList);
		return rd; 
	}
}
