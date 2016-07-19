package com.ava.domain.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.swing.text.Position;

import com.ava.base.domain.UserRight;
import com.ava.resource.GlobalConstant;
import com.ava.resource.SelectOptionResource;
import com.ava.resource.cache.CacheOrgManager;
import com.ava.util.DateTime;

@Entity
@Table(name = "t_user")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(unique = true, nullable = true, length = 32)
	private java.lang.Integer id;

	/** 用户的登录名，如：admin@org */
	@Column(name = "LOGIN_NAME")
	private java.lang.String loginName;
	
	/** 姓名*/
	@Column(name = "NICK_NAME")
	private java.lang.String nickName;
	
	/** 该用户注册时，是登记指纹 0：没有登记过指纹；1：登记过指纹 */
	@Column(name = "HAS_FINGERPRINT")
	private java.lang.Integer hasFingerprint;
	
	/** 是否首次登录 0：老用户；1：首次 */
	@Column(name = "IS_FIRST_LOGIN")
	private java.lang.Integer isFirstLogin;
	
	/** 首次登录是否修改了密码 0：否；1：是 */
	@Column(name = "IS_MODIFY_PASSWORD")
	private java.lang.Integer isModifyPassword;
	
	/** 用户的加密密码 */
	@Column(name = "ENCRYPTED_PASSWORD")
	private java.lang.String encryptedPassword;
	
	/** 用户取回密码时系统自动产生的临时重置密码，在用户激活后用此密码替换老密码 */
	/** 伪密码，一般用于cookie记住登录名时保存在客户机上，cookie判断时直接匹配用户名和此伪密码来验证用户是否通过 */
	@Column(name = "TEMP_PASSWORD")
	private java.lang.String tempPassword;

	/** 临时密码有效期 */
	@Column(name = "TEMP_PASSWORD_EXPIRATION")
	private java.util.Date tempPasswordExpiration;

	/** 用户的登录密码 */
	@Column(name = "PSEUDO_PASSWORD")
	private java.lang.String pseudoPassword;
	
	/** 是否在职 */
	@Column(name = "IS_IN_OFFICE")
	private java.lang.Integer isInOffice;
	
	/** 是否在岗*/
	@Column(name = "IS_IN_POSITION")
	private java.lang.Integer isInPosition;
	
	@Column(name = "EMAIL")
	private java.lang.String email;

	@Column(name = "DEPARTMENT_ID")
	private java.lang.Integer departmentId;

	@Column(name = "POSITION_ID")
	private java.lang.Integer positionId;

	@Column(name = "COMPANY_ID")
	private java.lang.Integer companyId;

	@Column(name = "OB_NAME")
	private java.lang.String obName;
	
	/** 用户权限，一般是以逗号（，）分隔的权限字符串，例如：right01,right03,right08 */
	@Column(name = "RIGHTS_CODE")
	private java.lang.String rightsCode;
	
	/** 创建时间 */
	@Column(name = "CREATION_DATE")
	private java.lang.String creationDate;

	/** 修改时间 */
	@Column(name = "MODIFICATION_DATE")
	private java.lang.String modificationDate;
 
	/** 删除标志 */
	@Column(name = "DELETION_FLAG")
	private java.lang.Integer deletionFlag;
	
	/** 删除时间 */
	@Column(name = "DELETION_TIME")
	private java.lang.String deletionTime;

	/** 头像 */
	@Column(name = "AVATAR")
	private java.lang.String avatar;

	@Column(name = "SEX")
	private java.lang.Integer sex;

	@Column(name = "BIRTHDAY")
	private java.util.Date birthday;

	@Column(name = "MARITAL_STATUS")
	private java.lang.Integer maritalStatus;

	@Column(name = "IDENTIFICATION")
	private java.lang.String identification;

	@Column(name = "ADDRESS")
	private java.lang.String address;

	@Column(name = "ZIP")
	private java.lang.String zip;

	@Column(name = "MOBILE")
	private java.lang.String mobile;

	@Column(name = "PHONE")
	private java.lang.String phone;

	@Column(name = "QQ")
	private java.lang.String qq;

	@Column(name = "DEGREE")
	private java.lang.Integer degree;

	@Column(name = "LAST_LOGIN_TIME")
	private java.util.Date lastLoginTime;
	
	@Column(name = "MAC_OF_PC")
	private java.lang.String macOfPc;
	
	@Column(name = "IS_DISABLE")
	private java.lang.Integer isDisable;//是否禁用

	/** ---------------------------------Transient start------------------------------*/
	
	/** 组织ID */
	@Transient
	private java.lang.Integer orgId;
	
	/** 公司名称 */
	@Transient
	private java.lang.String companyName;
	
	/** 是否重点城市 经销商状态 */
	@Transient
	private java.lang.String isKeyCity;
	
	/** 部门名称 */
	@Transient
	private java.lang.String departmentName;
	
	/** 岗位名称 */
	@Transient
	private java.lang.String positionName;
	
	
	/** 是否在职名称 */
	@Transient
	private java.lang.String inOfficeNick;
	
	/** 是否在岗名称 */
	@Transient
	private java.lang.String isDisableNick;
	
	/** 是否禁用 */
	@Transient
	private java.lang.String inPositionNick;
	

	/** 岗位集合 */
	@Transient
	private List<Position> positions;
	
	/** 所有角色*/
	@Transient
	private Map<Integer,String> allRoles = new HashMap<Integer,String>();; 
	
	/** 选中的角色*/
	@Transient
	private List<Integer> selectRoles = new ArrayList<Integer>();
	
	/** 角色中文名*/
	@Transient
	private String roles;

	/** 年龄，根据生日推算 */
	@Transient
	private int ageNum;

	@Transient
	private String sex_Nick;

	/** 用户姓名和职位信息，没有职位则只显示姓名。格式：张三（总经理）；李四 */
	@Transient
	private String nickNameAndPosition;

	/** 用户登录名前缀，并自动转为小写，比如：admin，fisher等 */
	@Transient
	private String prefixLoginName;

	/** 页面上用户输入的旧密码，明文保存 */
	@Transient
	private String pageOldPassword;

	/** 页面上用户输入的密码，明文保存 */
	@Transient
	private String pagePassword;

	/** 页面上用户输入的确认密码，明文保存 */
	@Transient
	private String pageConfirmPassword;
	
	/** 在线状态 */
	@Transient
	private Integer onlineStatus;
	@Transient
	private String onlineStatus_Nick;
	
	/** 页面访问权限 */
	@Transient
	private UserRight userRight;
	
	/** 用户所拥有级别最高的角色 */
	@Transient
	private Integer maxUserRight;
	
	/** 是否是管理员角色 */
	@Transient
	private Boolean isAdminRole = false;
	
	/** ---------------------------------Transient end------------------------------*/

	public User() {
		// 注意：User对象构造时候如果调用init方法，则在搜索时，容易产生搜索用的UserForSearch也被初始化，从而产生错误的搜索结果
		// init();
	}

	public void init() {
		if (this.getHasFingerprint() == null)
			this.setHasFingerprint(GlobalConstant.FALSE);
		
		if (this.getDeletionFlag() == null)
			this.setDeletionFlag(GlobalConstant.FALSE);
		
		if (this.getIsDisable() == null)
			this.setIsDisable(GlobalConstant.FALSE);
		
		if (this.getCreationDate() == null)
			this.setCreationDate(Long.toString(System.currentTimeMillis()));
		
		if (this.getModificationDate() == null)
			this.setModificationDate(Long.toString(System.currentTimeMillis()));
		
		if (this.getTempPasswordExpiration() == null)
			this.setTempPasswordExpiration(DateTime.getTimestamp());
		
		if (this.getRightsCode() == null)
			this.setRightsCode("Menu1L2,Menu2L25,2500,Menu1L3,Menu2L32,3200,Menu2L33,3300,Menu2L34,3400,Menu1L4,Menu2L41,4107");
	}

	public void nick() {
		userRight = new UserRight();
		
		if (this.getAvatar() == null || "".equals(this.getAvatar())) {
			this.setAvatar("/images/noAvatar.gif");
		}
		// 处理帐号前缀
		if (this.getLoginName() == null || this.getLoginName().indexOf("@") < 0) {
			// 登录名为null或者不包含@符号
			prefixLoginName = "";
		} else {
			prefixLoginName = this.getLoginName().substring(0,
					this.getLoginName().indexOf("@")).toLowerCase();
		}
		nickNameAndPosition = this.getNickName();

		sex_Nick = SelectOptionResource.getOptionTextByValue(SelectOptionResource.sexs, getSex());
		inOfficeNick = SelectOptionResource.getOptionTextByValue(SelectOptionResource.inOffices, getIsInOffice());
		inPositionNick = SelectOptionResource.getOptionTextByValue(SelectOptionResource.inPositions, getIsInPosition());
		isDisableNick = SelectOptionResource.getOptionTextByValue(SelectOptionResource.yesOrNo, getIsDisable());
		
		//公司名称
		companyName=CacheOrgManager.getOrgName(companyId);
		
		//部门名称
		departmentName=CacheOrgManager.getOrgName(departmentId);
		
		ageNum = DateTime.getYearInterval(getBirthday(), new Date());
	}
	

	public UserRight getUserRight() {
		return userRight;
	}

	public java.lang.Integer getId() {
		return id;
	}

	public void setId(java.lang.Integer id) {
		this.id = id;
	}


	public java.lang.String getAvatar() {
		return avatar;
	}

	public void setAvatar(java.lang.String avatar) {
		this.avatar = avatar;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public java.lang.String getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(java.lang.String creationDate) {
		this.creationDate = creationDate;
	}


	public java.lang.Integer getDegree() {
		return degree;
	}

	public void setDegree(java.lang.Integer degree) {
		this.degree = degree;
	}

	public java.lang.String getEmail() {
		return email;
	}

	public void setEmail(java.lang.String email) {
		this.email = email;
	}

	public java.lang.String getEncryptedPassword() {
		return encryptedPassword;
	}

	public void setEncryptedPassword(java.lang.String encryptedPassword) {
		this.encryptedPassword = encryptedPassword;
	}

	public java.lang.Integer getDeletionFlag() {
		return deletionFlag;
	}

	public void setDeletionFlag(java.lang.Integer deletionFlag) {
		this.deletionFlag = deletionFlag;
	}


	public java.lang.String getIdentification() {
		return identification;
	}

	public void setIdentification(java.lang.String identification) {
		this.identification = identification;
	}


	public java.util.Date getLastLoginTime() {
		return lastLoginTime;
	}

	public void setLastLoginTime(java.util.Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}


	public java.lang.Integer getMaritalStatus() {
		return maritalStatus;
	}

	public void setMaritalStatus(java.lang.Integer maritalStatus) {
		this.maritalStatus = maritalStatus;
	}

	public java.lang.String getMobile() {
		return mobile;
	}

	public void setMobile(java.lang.String mobile) {
		this.mobile = mobile;
	}

	public java.lang.String getModificationDate() {
		return modificationDate;
	}

	public void setModificationDate(java.lang.String modificationDate) {
		this.modificationDate = modificationDate;
	}


	public java.lang.String getNickName() {
		return nickName;
	}

	public void setNickName(java.lang.String nickName) {
		this.nickName = nickName;
	}

	public java.lang.String getObName() {
		return obName;
	}

	public void setObName(java.lang.String obName) {
		this.obName = obName;
	}

	public java.lang.Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(java.lang.Integer companyId) {
		this.companyId = companyId;
	}

	public java.lang.String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(java.lang.String companyName) {
		this.companyName = companyName;
	}


	public java.lang.String getPseudoPassword() {
		return pseudoPassword;
	}

	public void setPseudoPassword(java.lang.String pseudoPassword) {
		this.pseudoPassword = pseudoPassword;
	}

	public java.lang.String getQq() {
		return qq;
	}

	public void setQq(java.lang.String qq) {
		this.qq = qq;
	}


	public java.lang.Integer getSex() {
		return sex;
	}

	public void setSex(java.lang.Integer sex) {
		this.sex = sex;
	}


	public java.lang.String getZip() {
		return zip;
	}

	public void setZip(java.lang.String zip) {
		this.zip = zip;
	}

	public String toString() {
		return super.toString();
	}

	public java.lang.String getTempPassword() {
		return tempPassword;
	}

	public void setTempPassword(java.lang.String tempPassword) {
		this.tempPassword = tempPassword;
	}

	public java.util.Date getTempPasswordExpiration() {
		return tempPasswordExpiration;
	}

	public void setTempPasswordExpiration(java.util.Date tempPasswordExpiration) {
		this.tempPasswordExpiration = tempPasswordExpiration;
	}

	public java.lang.String getLoginName() {
		return loginName;
	}

	public void setLoginName(java.lang.String loginName) {
		this.loginName = loginName;
	}

	public java.lang.Integer getHasFingerprint() {
		return hasFingerprint;
	}

	public void setHasFingerprint(java.lang.Integer hasFingerprint) {
		this.hasFingerprint = hasFingerprint;
	}

	public java.lang.Integer getIsFirstLogin() {
		return isFirstLogin;
	}

	public void setIsFirstLogin(java.lang.Integer isFirstLogin) {
		this.isFirstLogin = isFirstLogin;
	}

	public java.lang.Integer getIsModifyPassword() {
		return isModifyPassword;
	}

	public void setIsModifyPassword(java.lang.Integer isModifyPassword) {
		this.isModifyPassword = isModifyPassword;
	}

	public java.lang.String getRightsCode() {
		return rightsCode;
	}

	public void setRightsCode(java.lang.String rightsCode) {
		this.rightsCode = rightsCode;
	}

	public java.lang.Integer getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(java.lang.Integer departmentId) {
		this.departmentId = departmentId;
	}

	public java.lang.Integer getPositionId() {
		return positionId;
	}

	public void setPositionId(java.lang.Integer positionId) {
		this.positionId = positionId;
	}

	
	public java.lang.String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(java.lang.String departmentName) {
		this.departmentName = departmentName;
	}

	public java.lang.String getPositionName() {
		return positionName;
	}

	public void setPositionName(java.lang.String positionName) {
		this.positionName = positionName;
	}

	public java.lang.String getAddress() {
		return address;
	}

	public void setAddress(java.lang.String address) {
		this.address = address;
	}

	public List<Position> getPositions() {
		return positions;
	}

	public void setPositions(List<Position> positions) {
		this.positions = positions;
	}

	public String getPageOldPassword() {
		return pageOldPassword;
	}

	public void setPageOldPassword(String pageOldPassword) {
		this.pageOldPassword = pageOldPassword;
	}

	public String getPagePassword() {
		return pagePassword;
	}

	public void setPagePassword(String pagePassword) {
		this.pagePassword = pagePassword;
	}

	public String getPageConfirmPassword() {
		return pageConfirmPassword;
	}

	public void setPageConfirmPassword(String pageConfirmPassword) {
		this.pageConfirmPassword = pageConfirmPassword;
	}

	public String getSex_Nick() {
		return sex_Nick;
	}

	public void setSex_Nick(String sex_Nick) {
		this.sex_Nick = sex_Nick;
	}

	public int getAgeNum() {
		return ageNum;
	}

	public void setAgeNum(int ageNum) {
		this.ageNum = ageNum;
	}

	/** 获得用户登录名前缀，并自动转为小写，比如：admin，fisher等 */
	public String getPrefixLoginName() {
		if (prefixLoginName == null) {
			if (this.getLoginName() == null
					|| this.getLoginName().indexOf("@") < 0) {
				// 登录名为null或者不包含@符号
				prefixLoginName = "";
			} else {
				prefixLoginName = this.getLoginName().substring(0,
						this.getLoginName().indexOf("@")).toLowerCase();
			}

		}
		return prefixLoginName;
	}

	public void setPrefixLoginName(String prefixLoginName) {
		this.prefixLoginName = prefixLoginName;
	}

	public String getNickNameAndPosition() {
		return nickNameAndPosition;
	}

	public void setNickNameAndPosition(String nickNameAndPosition) {
		this.nickNameAndPosition = nickNameAndPosition;
	}

	public Integer getOnlineStatus() {
		return onlineStatus;
	}

	public void setOnlineStatus(Integer onlineStatus) {
		this.onlineStatus = onlineStatus;
	}

	public String getOnlineStatus_Nick() {
		return onlineStatus_Nick;
	}

	public void setOnlineStatus_Nick(String onlineStatusNick) {
		onlineStatus_Nick = onlineStatusNick;
	}

	public java.lang.String getPhone() {
		return phone;
	}

	public void setPhone(java.lang.String phone) {
		this.phone = phone;
	}


	public java.lang.Integer getIsInOffice() {
		return isInOffice;
	}

	public void setIsInOffice(java.lang.Integer isInOffice) {
		this.isInOffice = isInOffice;
	}

	public java.lang.Integer getIsInPosition() {
		return isInPosition;
	}

	public void setIsInPosition(java.lang.Integer isInPosition) {
		this.isInPosition = isInPosition;
	}

	public java.lang.String getDeletionTime() {
		return deletionTime;
	}

	public void setDeletionTime(java.lang.String deletionTime) {
		this.deletionTime = deletionTime;
	}

	public java.lang.String getMacOfPc() {
		return macOfPc;
	}

	public void setMacOfPc(java.lang.String macOfPc) {
		this.macOfPc = macOfPc;
	}

	public java.lang.String getInOfficeNick() {
		return inOfficeNick;
	}

	public void setInOfficeNick(java.lang.String inOfficeNick) {
		this.inOfficeNick = inOfficeNick;
	}

	public java.lang.String getInPositionNick() {
		return inPositionNick;
	}

	public void setInPositionNick(java.lang.String inPositionNick) {
		this.inPositionNick = inPositionNick;
	}

	public java.lang.String getIsKeyCity() {
		return isKeyCity;
	}

	public void setIsKeyCity(java.lang.String isKeyCity) {
		this.isKeyCity = isKeyCity;
	}


	public String getRoles() {
		return roles;
	}

	public void setRoles(String roles) {
		this.roles = roles;
	}

	public java.lang.Integer getIsDisable() {
		return isDisable;
	}

	public void setIsDisable(java.lang.Integer isDisable) {
		this.isDisable = isDisable;
	}

	public java.lang.String getIsDisableNick() {
		return isDisableNick;
	}

	public void setIsDisableNick(java.lang.String isDisableNick) {
		this.isDisableNick = isDisableNick;
	}


	public Map<Integer, String> getAllRoles() {
		return allRoles;
	}

	public void setAllRoles(Map<Integer, String> allRoles) {
		this.allRoles = allRoles;
	}

	public List<Integer> getSelectRoles() {
		return selectRoles;
	}

	public void setSelectRoles(List<Integer> selectRoles) {
		this.selectRoles = selectRoles;
	}


	public void setUserRight(UserRight userRight) {
		this.userRight = userRight;
	}

	public java.lang.Integer getOrgId() {
		return orgId;
	}

	public void setOrgId(java.lang.Integer orgId) {
		this.orgId = orgId;
	}

	public Integer getMaxUserRight() {
		return maxUserRight;
	}

	public void setMaxUserRight(Integer maxUserRight) {
		this.maxUserRight = maxUserRight;
	}

	public Boolean getIsAdminRole() {
		return isAdminRole;
	}

	public void setIsAdminRole(Boolean isAdminRole) {
		this.isAdminRole = isAdminRole;
	}
	
	
	
}