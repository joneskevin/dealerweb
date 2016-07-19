package com.ava.api.vo;

import com.ava.domain.entity.User;
import com.ava.util.DateTime;
import com.ava.util.MyBeanUtils;


public class UserApiVo {

	private java.lang.Integer id;

	/** 用户的登录名，如：admin@org */
	private java.lang.String loginName;
	
	/** 姓名*/
	private java.lang.String nickName;
	
	/** 该用户注册时，是登记指纹 0：没有登记过指纹；1：登记过指纹 */
	private java.lang.Integer hasFingerprint;
	
	/** 是否首次登录 0：老用户；1：首次 */
	private java.lang.Integer isFirstLogin;
	
	/** 首次登录是否修改了密码 0：否；1：是 */
	private java.lang.Integer isModifyPassword;
	
	/** 是否在职 */
	private java.lang.Integer isInOffice;
	
	/** 是否在岗*/
	private java.lang.Integer isInPosition;
	
	private java.lang.String email;

	private java.lang.Integer departmentId;

	private java.lang.Integer positionId;

	private java.lang.Integer companyId;

	private java.lang.String obName;
	
	/** 创建时间 */
	private java.lang.String creationDate;

	/** 修改时间 */
	private java.lang.String modificationDate;
 
	/** 删除标志 */
	private java.lang.Integer deletionFlag;
	
	/** 删除时间 */
	private java.lang.String deletionTime;

	/** 头像 */
	private java.lang.String avatar;

	private java.lang.Integer sex;

	private java.lang.String birthday;

	private java.lang.Integer maritalStatus;

	private java.lang.String identification;

	private java.lang.String address;

	private java.lang.String zip;

	private java.lang.String mobile;

	private java.lang.String phone;

	private java.lang.String qq;

	private java.lang.Integer degree;

	private java.lang.String lastLoginTime;
	
	private java.lang.String macOfPc;

	/** ---------------------------------Transient start------------------------------*/
	
	/** 公司名称 */
	private java.lang.String companyName;
	
	/** 部门名称 */
	private java.lang.String departmentName;
	
	/** 岗位名称 */
	private java.lang.String positionName;
	
	
	/** 是否在职名称 */
	private java.lang.String inOfficeNick;
	
	/** 是否在岗名称 */
	private java.lang.String inPositionNick;
	
	/** 年龄，根据生日推算 */
	private int ageNum;

	private String sex_Nick;

	/** 用户姓名和职位信息，没有职位则只显示姓名。格式：张三（总经理）；李四 */
	private String nickNameAndPosition;

	/** 用户登录名前缀，并自动转为小写，比如：admin，fisher等 */
	private String prefixLoginName;
	
	/** 在线状态 */
	private Integer onlineStatus;
	private String onlineStatus_Nick;

	/** api传来的加密的密码 */
	private String apiEncryptedPassword;

	/** 访问令牌 */
	private java.lang.String accessToken;
	/** ---------------------------------Transient end------------------------------*/
	public UserApiVo(String loginName, String apiEncryptedPassword){
		this.setLoginName(loginName);
		this.setApiEncryptedPassword(apiEncryptedPassword);
	}
	
	public UserApiVo(User user){
		if(user == null){
			return;
		}
		MyBeanUtils.copyPropertiesContainsDate(this, user);
		
		this.setBirthday(DateTime.toNormalDate(user.getBirthday()));
		this.setLastLoginTime(DateTime.toNormalDateTime(user.getLastLoginTime()));
	}
	
	public java.lang.Integer getId() {
		return id;
	}
	public void setId(java.lang.Integer id) {
		this.id = id;
	}
	public java.lang.String getLoginName() {
		return loginName;
	}
	public void setLoginName(java.lang.String loginName) {
		this.loginName = loginName;
	}
	public java.lang.String getNickName() {
		return nickName;
	}
	public void setNickName(java.lang.String nickName) {
		this.nickName = nickName;
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
	public java.lang.String getEmail() {
		return email;
	}
	public void setEmail(java.lang.String email) {
		this.email = email;
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
	public java.lang.Integer getCompanyId() {
		return companyId;
	}
	public void setCompanyId(java.lang.Integer companyId) {
		this.companyId = companyId;
	}
	public java.lang.String getObName() {
		return obName;
	}
	public void setObName(java.lang.String obName) {
		this.obName = obName;
	}
	public java.lang.String getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(java.lang.String creationDate) {
		this.creationDate = creationDate;
	}
	public java.lang.String getModificationDate() {
		return modificationDate;
	}
	public void setModificationDate(java.lang.String modificationDate) {
		this.modificationDate = modificationDate;
	}
	public java.lang.Integer getDeletionFlag() {
		return deletionFlag;
	}
	public void setDeletionFlag(java.lang.Integer deletionFlag) {
		this.deletionFlag = deletionFlag;
	}
	public java.lang.String getDeletionTime() {
		return deletionTime;
	}
	public void setDeletionTime(java.lang.String deletionTime) {
		this.deletionTime = deletionTime;
	}
	public java.lang.String getAvatar() {
		return avatar;
	}
	public void setAvatar(java.lang.String avatar) {
		this.avatar = avatar;
	}
	public java.lang.Integer getSex() {
		return sex;
	}
	public void setSex(java.lang.Integer sex) {
		this.sex = sex;
	}
	public java.lang.String getBirthday() {
		return birthday;
	}
	public void setBirthday(java.lang.String birthday) {
		this.birthday = birthday;
	}
	public java.lang.Integer getMaritalStatus() {
		return maritalStatus;
	}
	public void setMaritalStatus(java.lang.Integer maritalStatus) {
		this.maritalStatus = maritalStatus;
	}
	public java.lang.String getIdentification() {
		return identification;
	}
	public void setIdentification(java.lang.String identification) {
		this.identification = identification;
	}
	public java.lang.String getAddress() {
		return address;
	}
	public void setAddress(java.lang.String address) {
		this.address = address;
	}
	public java.lang.String getZip() {
		return zip;
	}
	public void setZip(java.lang.String zip) {
		this.zip = zip;
	}
	public java.lang.String getMobile() {
		return mobile;
	}
	public void setMobile(java.lang.String mobile) {
		this.mobile = mobile;
	}
	public java.lang.String getPhone() {
		return phone;
	}
	public void setPhone(java.lang.String phone) {
		this.phone = phone;
	}
	public java.lang.String getQq() {
		return qq;
	}
	public void setQq(java.lang.String qq) {
		this.qq = qq;
	}
	public java.lang.Integer getDegree() {
		return degree;
	}
	public void setDegree(java.lang.Integer degree) {
		this.degree = degree;
	}
	public java.lang.String getLastLoginTime() {
		return lastLoginTime;
	}
	public void setLastLoginTime(java.lang.String lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}
	public java.lang.String getMacOfPc() {
		return macOfPc;
	}
	public void setMacOfPc(java.lang.String macOfPc) {
		this.macOfPc = macOfPc;
	}
	public java.lang.String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(java.lang.String companyName) {
		this.companyName = companyName;
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
	public int getAgeNum() {
		return ageNum;
	}
	public void setAgeNum(int ageNum) {
		this.ageNum = ageNum;
	}
	public String getSex_Nick() {
		return sex_Nick;
	}
	public void setSex_Nick(String sexNick) {
		sex_Nick = sexNick;
	}
	public String getNickNameAndPosition() {
		return nickNameAndPosition;
	}
	public void setNickNameAndPosition(String nickNameAndPosition) {
		this.nickNameAndPosition = nickNameAndPosition;
	}
	public String getPrefixLoginName() {
		return prefixLoginName;
	}
	public void setPrefixLoginName(String prefixLoginName) {
		this.prefixLoginName = prefixLoginName;
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

	public String getApiEncryptedPassword() {
		return apiEncryptedPassword;
	}

	public void setApiEncryptedPassword(String apiEncryptedPassword) {
		this.apiEncryptedPassword = apiEncryptedPassword;
	}

	public java.lang.String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(java.lang.String accessToken) {
		this.accessToken = accessToken;
	}
}