package com.ava.domain.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.ava.domain.vo.CarStyleVO;
import com.ava.domain.vo.CompanyCarStyleRelationVO;
import com.ava.resource.DbCacheResource;
import com.ava.resource.GlobalConstant;
import com.ava.resource.SelectOptionResource;
import com.ava.util.TextUtil;

@Entity
@Table(name="t_company")
@PrimaryKeyJoinColumn(name="ORG_ID")
public class Company extends Org implements Serializable{	
	
	/** 中文名（组织名称） */
	@Column(name = "CN_NAME")
	private java.lang.String cnName;
	
	/** 英文名 */
	@Column(name = "EN_NAME")
	private java.lang.String enName;
	
	/** 组织账号 */
	@Column(name = "OB_NAME")
	private java.lang.String obName;
	
	/**	组织当前人数	*/
	@Column(name = "USER_NUM")
	private java.lang.Integer userNum;
	/** 组织空间硬盘已使用量，单位：字节 */
	@Column(name = "ORG_USAGE")
	private java.lang.Long orgUsage;
	
	/** 地区-国家编号 */
	@Column(name = "REGION_COUNTRY_ID")
	private java.lang.Integer regionCountryId;	
	/** 地区-省编号 */
	@Column(name = "REGION_PROVINCE_ID")
	private java.lang.Integer regionProvinceId;	
	/** 地区-市编号 */
	@Column(name = "REGION_CITY_ID")
	private java.lang.Integer regionCityId;
	/** 行业一级编号 */
	@Column(name = "INDUSTRY_LEVEL1_ID")
	private java.lang.Integer industryLevel1Id;
	/** 行业二级编号 */
	@Column(name = "INDUSTRY_LEVEL2_ID")
	private java.lang.Integer industryLevel2Id;
	
	/** 业务范围 */
	private java.lang.String description;
	/**	关键字	*/
	@Column(name = "KEY_WORD_SET")
	private java.lang.String keyWordSet;
	
	/**	组织logo，切割后的	*/
	private java.lang.String logo;
	/**	组织logo原图，未经任何处理的	*/
	@Column(name = "LOGO_ORIG")
	private java.lang.String logoOrig;
	@Column(name = "LOGO_CREATION_TIME")
	private java.util.Date logoCreationTime;

	@Column(name = "CONTACT_NAME")
	private java.lang.String contactName;
	/** 邮编 */
	private java.lang.String zip;	
	/** 联系电话 */
	@Column(name = "CONTACT_TEL")
	private java.lang.String contactTel;	
	/** 传真 */
	private java.lang.String fax;	
	/**	联系地址 */
	@Column(name = "CONTACT_ADDRESS")
	private java.lang.String contactAddress;	
	/** 官方网址 */
	@Column(name = "OFFICIAL_WEBSITE")
	private java.lang.String officialWebsite;	
	/**	是否启用官方网址标志： 1：启用    0：不启用	*/
	@Column(name = "OFFICIAL_WEBSITE_FLAG")
	private java.lang.Integer officialWebsiteFlag;
	/** 电子邮件 */
	private java.lang.String email;

	/**	组织上次任一成员登录日期	*/
	@Column(name = "LAST_LOGIN_TIME")
	private java.util.Date lastLoginTime;
	
	/** 组织短信发送设置 */
	@Column(name = "SMS_CONFIG")
	private java.lang.String smsConfig;
	
	/** 国家 */
	@Column(name = "COUNTRY")
	private java.lang.String country;
	
	/** 网络代码 */
	@Column(name = "DEALER_CODE")
	private java.lang.String dealerCode;
	
	/** 是否重点城市(经销商状态) */
	@Column(name = "IS_KEY_CITY")
	private java.lang.Integer isKeyCity;
	
	/** 网络形态：对于汽车经销商公司才可使用，也叫“网络形态”（4S店、城市展厅、直管直营店、非直管直营店） */
	@Column(name = "DEALER_TYPE")
	private java.lang.Integer dealerType;
	
	/** 上班时间  */
	@Column(name = "START_WORK_TIME")
	private java.lang.Integer startWorkTime;
	
	/** 下班时间  */
	@Column(name = "END_WORK_TIME")
	private java.lang.Integer endWorkTime;
	
	/** 经销商位置经度 */
	@Column(name = "BAIDU_LNG")
	private java.lang.Double baiduLng;
	
	/** 经销商位置纬度 */
	@Column(name = "BAIDU_LAT")
	private java.lang.Double baiduLat;

	public Company() {
		//注意：Org构造时候如果调用init方法，则在搜索时，容易产生搜索用的OrgForSearch也被初始化，从而产生错误的搜索结果
		//init();
	}
	
	/**一级网点ID*/
	@Transient
	private Integer parentCompanyId;
	
	/** 一级网点名称 */
	@Transient
	private String parentCompanyName;
	
	/** 一级网点代码 */
	@Transient
	private java.lang.String parentDealerCode;
	
	/**	组织类型	*/
	@Transient
	private String orgType_Nick;

	@Transient
	private String logo_Nick;

	@Transient
	private String logoOrig_Nick;

	@Transient
	private String orgUsage_Nick;

	@Transient
	private String regionProvinceId_Nick;

	@Transient
	private String regionCityId_Nick;

	@Transient
	private String region_Nick;

	@Transient
	private String industryLevel1Id_Nick;

	@Transient
	private String industryLevel2Id_Nick;

	@Transient
	private String industry_Nick;
	
	/**	网络形态中文名*/
	@Transient
	private String isKeyCity_Nick;
	
	/**	经销商状态中文名*/
	@Transient
	private String dealerType_Nick;
	
	/** 上班时间中文名  */
	@Transient
	private String startWorkTime_Nick;
	
	/** 下班时间中文名  */
	@Transient
	private String endWorkTime_Nick;
	
	/**	车型对象*/
	@Transient
	private CarStyleVO carStyleVO;
	
	/**	车型对象*/
	@Transient
	private CompanyCarStyleRelationVO companyCarStyleRelationVO;
	
	/**配置年份*/
	@Transient
	private Integer years;
	
	/** 是否限购城市 */
	@Transient
	private Integer isRestrictionCity;

	/** 组织短信发送设置-站内消息模块-内部联系人发送提醒 */
	@Transient
	private boolean smsShortMessageSend = false;
	/** 组织短信发送设置-办公审批模块-请假申请提醒 */
	@Transient
	private boolean smsApprovalLeave = false;
	/** 组织短信发送设置-办公审批模块-物品领用申请提醒 */
	@Transient
	private boolean smsApprovaRecipient = false;
	/** 组织短信发送设置-办公审批模块-报销申请提醒 */
	@Transient
	private boolean smsApprovalReimburse = false;
	/** 组织短信发送设置-办公审批模块-其他申请提醒 */
	@Transient
	private boolean smsApprovalCommon = false;
	/** 组织短信发送设置-工作辅助-待办事宜提醒 */
	@Transient
	private boolean smsPendingTask = false;
	/** 组织短信发送设置-工作辅助-日常任务提醒 */
	@Transient
	private boolean smsProjTask = false;
	/**	是否已经处理过短信发送设置转换	*/
	@Transient
	private boolean hadProcessSmsConfig = false;

	public void init(){
		super.init();
		this.setOrgType(GlobalConstant.ABSTRACT_ORG_TYPE_COMPANY);
		if(getDeletionFlag()==null)
			setDeletionFlag(GlobalConstant.FALSE);	//默认删除标志为0
		if(getOfficialWebsiteFlag()==null)
			setOfficialWebsiteFlag(0);	//默认官网链接为不启用
		if(getUserNum()==null)
			setUserNum(0);	//默认组织用户已使用数为0
		if(getOrgUsage()==null)
			setOrgUsage(0L);	//初始已使用硬盘空间为0
		if(getRegionCountryId()==null)
			setRegionCountryId(1);	//默认国家为中国
		if(getRegionProvinceId()==null)
			setRegionProvinceId(367);	//默认省份为上海
		if(getRegionCityId()==null)
			setRegionCityId(395);	//默认城市为浦东
		if(getIndustryLevel1Id()==null)
			setIndustryLevel1Id(473);	//默认行业一级类别为未选择
		if(getIndustryLevel2Id()==null)
			setIndustryLevel2Id(474);	//默认行业二级类别为未选择
		if(getKeyWordSet()==null)
			setKeyWordSet("");	//默认搜索关键字为空
		if(getLastLoginTime()==null)
			setLastLoginTime(new Date());	//最后登录时间
		if(getObName()==null)
			setObName("");	//英文简称
	}
	
	/**
     * 这个方法调用本域对象及其通过hibernate自动级联的域对象的及其...（以此类推到必要层次，不同的域对象层次不同）的nick方法
     */
	public void nick(){
		super.nick();
		if ( getOfficialWebsite() != null ){
			//官网网址自动加“http://”的处理
			String officialWebsite = this.getOfficialWebsite();
			if (officialWebsite!=null && officialWebsite.length()>0){
				officialWebsite = TextUtil.convertInvisibleTag(officialWebsite);
				if (officialWebsite.indexOf("://")<0){
					officialWebsite = "http://" + officialWebsite;
					this.setOfficialWebsite(officialWebsite);
				}
			}
		}
		if (getOfficialWebsiteFlag() == null){
			setOfficialWebsiteFlag(0);
		}
		
		//经销商状态
		isKeyCity_Nick =  SelectOptionResource.getOptionTextByValue(SelectOptionResource.isKeyCitys, getIsKeyCity());
		//网络形态
		dealerType_Nick = SelectOptionResource.getOptionTextByValue(SelectOptionResource.dealerTypes, getDealerType());
		//上班时间
		startWorkTime_Nick = SelectOptionResource.getOptionTextByValue(SelectOptionResource.workTimeArray, getStartWorkTime());
		//下班时间
		endWorkTime_Nick = SelectOptionResource.getOptionTextByValue(SelectOptionResource.workTimeArray, getEndWorkTime());
		
		//地区
		regionProvinceId_Nick = DbCacheResource.getDataDictionaryNameById(this.getRegionProvinceId());
		regionCityId_Nick = DbCacheResource.getDataDictionaryNameById(this.getRegionCityId());
		region_Nick = regionProvinceId_Nick + "/" + regionCityId_Nick;
		
		//行业
		if (getIndustryLevel1Id() == null || getIndustryLevel1Id().intValue() <= 0){
			setIndustryLevel1Id(473);
		}
		if (getIndustryLevel2Id() == null || getIndustryLevel2Id().intValue() <= 0){
			setIndustryLevel2Id(474);
		}
		
		industryLevel1Id_Nick = DbCacheResource.getDataDictionaryNameById(this.getIndustryLevel1Id());
		industryLevel2Id_Nick = DbCacheResource.getDataDictionaryNameById(this.getIndustryLevel2Id());
		industry_Nick = industryLevel1Id_Nick + "/" + industryLevel2Id_Nick;
		
		//组织全称
		if (getCnName()==null || "".equals(getCnName())){
			setCnName(getName());
		}
		//已使用硬盘空间
		if (getOrgUsage()==null){
			this.setOrgUsage_Nick("0K");
		}else{
			if ( getOrgUsage().longValue()<(1*1000*1000L) ){
				//小于1M，则用K表示
				this.setOrgUsage_Nick(getOrgUsage().longValue()/(1000L) + "K");
			}else if ( getOrgUsage().longValue()<(10*1000*1000*1000L) ){
				//小于10G，则用M表示
				this.setOrgUsage_Nick(getOrgUsage().longValue()/(1000*1000L) + "M");
			}else{
				//大于10G，用G表示
				this.setOrgUsage_Nick(getOrgUsage().longValue()/(1000*1000*1000L) + "G");
			}
		}
		
		if (getLogo()==null || "".equals(getLogo())){
			logo_Nick = "/gate/defaultLogo.gif";			
		}else{
			logo_Nick = getLogo();
		}
		if (getLogoOrig()==null || "".equals(getLogoOrig())){
			logoOrig_Nick = "/gate/defaultLogo.gif";			
		}else{
			logoOrig_Nick = getLogoOrig();
		}
	}

	/**	短信发送设置转换处理，根据smsConfig字段得到对应的短信发送设置	*/
	public void processSmsConfig(){
		hadProcessSmsConfig = true;
		String smsConfig = getSmsConfig();
		if (smsConfig == null || smsConfig.trim().length() < 1){
			return;
		}
		if (smsConfig.indexOf("1101") > -1){
			smsShortMessageSend = true;
		}
		if (smsConfig.indexOf("2101") > -1){
			smsApprovalLeave = true;
		}
		if (smsConfig.indexOf("2102") > -1){
			smsApprovalReimburse = true;
		}
		if (smsConfig.indexOf("2103") > -1){
			smsApprovaRecipient = true;
		}
		if (smsConfig.indexOf("2109") > -1){
			smsApprovalCommon = true;
		}
		if (smsConfig.indexOf("3101") > -1){
			smsPendingTask = true;
		}
		if (smsConfig.indexOf("3102") > -1){
			smsProjTask = true;
		}
	}
	
	
	public String getOrgType_Nick() {
		return orgType_Nick;
	}

	public void setOrgType_Nick(String orgType_Nick) {
		this.orgType_Nick = orgType_Nick;
	}
	public String getLogo_Nick() {
		return logo_Nick;
	}
	public void setLogo_Nick(String logo_Nick) {
		this.logo_Nick = logo_Nick;
	}
	public String getIndustry_Nick() {
		return industry_Nick;
	}
	public void setIndustry_Nick(String industry_Nick) {
		this.industry_Nick = industry_Nick;
	}
	public String getRegion_Nick() {
		return region_Nick;
	}
	public void setRegion_Nick(String region_Nick) {
		this.region_Nick = region_Nick;
	}
	public String getIndustryLevel1Id_Nick() {
		return industryLevel1Id_Nick;
	}
	public void setIndustryLevel1Id_Nick(String industryLevel1Id_Nick) {
		this.industryLevel1Id_Nick = industryLevel1Id_Nick;
	}
	public String getIndustryLevel2Id_Nick() {
		return industryLevel2Id_Nick;
	}
	public void setIndustryLevel2Id_Nick(String industryLevel2Id_Nick) {
		this.industryLevel2Id_Nick = industryLevel2Id_Nick;
	}
	public String getRegionProvinceId_Nick() {
		return regionProvinceId_Nick;
	}
	public void setRegionProvinceId_Nick(String regionProvinceId_Nick) {
		this.regionProvinceId_Nick = regionProvinceId_Nick;
	}
	public String getRegionCityId_Nick() {
		return regionCityId_Nick;
	}
	public void setRegionCityId_Nick(String regionCityId_Nick) {
		this.regionCityId_Nick = regionCityId_Nick;
	}
	public String getOrgUsage_Nick() {
		return orgUsage_Nick;
	}
	public void setOrgUsage_Nick(String orgUsage_Nick) {
		this.orgUsage_Nick = orgUsage_Nick;
	}
	public String getLogoOrig_Nick() {
		return logoOrig_Nick;
	}

	public void setLogoOrig_Nick(String logoOrig_Nick) {
		this.logoOrig_Nick = logoOrig_Nick;
	}

	public boolean isHadProcessSmsConfig() {
		return hadProcessSmsConfig;
	}


	public void setHadProcessSmsConfig(boolean hadProcessSmsConfig) {
		this.hadProcessSmsConfig = hadProcessSmsConfig;
	}


	/** 组织短信发送设置-办公审批模块-其他申请提醒 */
	public boolean isSmsApprovalCommon() {
		if (!hadProcessSmsConfig)
			this.processSmsConfig();
		return smsApprovalCommon;
	}

	public void setSmsApprovalCommon(boolean smsApprovalCommon) {
		this.smsApprovalCommon = smsApprovalCommon;
	}

	/** 组织短信发送设置-办公审批模块-请假申请提醒 */
	public boolean isSmsApprovalLeave() {
		if (!hadProcessSmsConfig)
			this.processSmsConfig();
		return smsApprovalLeave;
	}

	public void setSmsApprovalLeave(boolean smsApprovalLeave) {
		this.smsApprovalLeave = smsApprovalLeave;
	}

	/** 组织短信发送设置-办公审批模块-报销申请提醒 */
	public boolean isSmsApprovalReimburse() {
		if (!hadProcessSmsConfig)
			this.processSmsConfig();
		return smsApprovalReimburse;
	}

	public void setSmsApprovalReimburse(boolean smsApprovalReimburse) {
		this.smsApprovalReimburse = smsApprovalReimburse;
	}

	/** 组织短信发送设置-办公审批模块-物品领用申请提醒 */
	public boolean isSmsApprovaRecipient() {
		if (!hadProcessSmsConfig)
			this.processSmsConfig();
		return smsApprovaRecipient;
	}

	public void setSmsApprovaRecipient(boolean smsApprovaRecipient) {
		this.smsApprovaRecipient = smsApprovaRecipient;
	}

	/** 组织短信发送设置-站内消息模块-内部联系人发送提醒 */
	public boolean isSmsShortMessageSend() {
		if (!hadProcessSmsConfig)
			this.processSmsConfig();
		return smsShortMessageSend;
	}

	public void setSmsShortMessageSend(boolean smsShortMessageSend) {
		this.smsShortMessageSend = smsShortMessageSend;
	}

	/** 组织短信发送设置-工作辅助-待办事宜提醒 */
	public boolean isSmsPendingTask() {
		if (!hadProcessSmsConfig)
			this.processSmsConfig();
		return smsPendingTask;
	}

	public void setSmsPendingTask(boolean smsPendingTask) {
		this.smsPendingTask = smsPendingTask;
	}
	/** 组织短信发送设置-工作辅助-日常任务提醒 */
	public boolean isSmsProjTask() {
		if (!hadProcessSmsConfig)
			this.processSmsConfig();
		return smsProjTask;
	}

	public void setSmsProjTask(boolean smsProjTask) {
		this.smsProjTask = smsProjTask;
	}


	public java.lang.Integer getIsKeyCity() {
		return isKeyCity;
	}


	public void setIsKeyCity(java.lang.Integer isKeyCity) {
		this.isKeyCity = isKeyCity;
	}


	public String getIsKeyCity_Nick() {
		return isKeyCity_Nick;
	}


	public void setIsKeyCity_Nick(String isKeyCity_Nick) {
		this.isKeyCity_Nick = isKeyCity_Nick;
	}


	public String getDealerType_Nick() {
		return dealerType_Nick;
	}


	public void setDealerType_Nick(String dealerType_Nick) {
		this.dealerType_Nick = dealerType_Nick;
	}

	

	public java.lang.Integer getStartWorkTime() {
		return startWorkTime;
	}


	public void setStartWorkTime(java.lang.Integer startWorkTime) {
		this.startWorkTime = startWorkTime;
	}


	public java.lang.Integer getEndWorkTime() {
		return endWorkTime;
	}


	public void setEndWorkTime(java.lang.Integer endWorkTime) {
		this.endWorkTime = endWorkTime;
	}


	public CarStyleVO getCarStyleVO() {
		return carStyleVO;
	}


	public void setCarStyleVO(CarStyleVO carStyleVO) {
		this.carStyleVO = carStyleVO;
	}


	public CompanyCarStyleRelationVO getCompanyCarStyleRelationVO() {
		return companyCarStyleRelationVO;
	}


	public void setCompanyCarStyleRelationVO(
			CompanyCarStyleRelationVO companyCarStyleRelationVO) {
		this.companyCarStyleRelationVO = companyCarStyleRelationVO;
	}


	public Integer getYears() {
		return years;
	}


	public void setYears(Integer years) {
		this.years = years;
	}


	public String getStartWorkTime_Nick() {
		return startWorkTime_Nick;
	}


	public void setStartWorkTime_Nick(String startWorkTime_Nick) {
		this.startWorkTime_Nick = startWorkTime_Nick;
	}


	public String getEndWorkTime_Nick() {
		return endWorkTime_Nick;
	}


	public void setEndWorkTime_Nick(String endWorkTime_Nick) {
		this.endWorkTime_Nick = endWorkTime_Nick;
	}


	public java.lang.Double getBaiduLng() {
		return baiduLng;
	}

	public void setBaiduLng(java.lang.Double baiduLng) {
		this.baiduLng = baiduLng;
	}

	public java.lang.Double getBaiduLat() {
		return baiduLat;
	}

	public void setBaiduLat(java.lang.Double baiduLat) {
		this.baiduLat = baiduLat;
	}


	public Integer getParentCompanyId() {
		return parentCompanyId;
	}


	public void setParentCompanyId(Integer parentCompanyId) {
		this.parentCompanyId = parentCompanyId;
	}


	public String getParentCompanyName() {
		return parentCompanyName;
	}


	public void setParentCompanyName(String parentCompanyName) {
		this.parentCompanyName = parentCompanyName;
	}


	public java.lang.String getParentDealerCode() {
		return parentDealerCode;
	}


	public void setParentDealerCode(java.lang.String parentDealerCode) {
		this.parentDealerCode = parentDealerCode;
	}

	public java.lang.Long getOrgUsage() {
		return orgUsage;
	}

	public void setOrgUsage(java.lang.Long orgUsage) {
		this.orgUsage = orgUsage;
	}

	public java.lang.String getEmail() {
		return email;
	}

	public void setEmail(java.lang.String email) {
		this.email = email;
	}
	
	public java.lang.String getObName() {
		return obName;
	}

	public void setObName(java.lang.String obName) {
		this.obName = obName;
	}

	public java.lang.String getCnName() {
		return cnName;
	}

	public void setCnName(java.lang.String cnName) {
		this.cnName = cnName;
	}

	public java.lang.String getEnName() {
		return enName;
	}

	public void setEnName(java.lang.String enName) {
		this.enName = enName;
	}

	public java.lang.Integer getRegionCountryId() {
		return regionCountryId;
	}

	public void setRegionCountryId(java.lang.Integer regionCountryId) {
		this.regionCountryId = regionCountryId;
	}

	public java.lang.Integer getRegionProvinceId() {
		return regionProvinceId;
	}

	public void setRegionProvinceId(java.lang.Integer regionProvinceId) {
		this.regionProvinceId = regionProvinceId;
	}

	public java.lang.Integer getRegionCityId() {
		return regionCityId;
	}

	public void setRegionCityId(java.lang.Integer regionCityId) {
		this.regionCityId = regionCityId;
	}

	public java.lang.Integer getIndustryLevel1Id() {
		return industryLevel1Id;
	}

	public void setIndustryLevel1Id(java.lang.Integer industryLevel1Id) {
		this.industryLevel1Id = industryLevel1Id;
	}

	public java.lang.Integer getIndustryLevel2Id() {
		return industryLevel2Id;
	}

	public void setIndustryLevel2Id(java.lang.Integer industryLevel2Id) {
		this.industryLevel2Id = industryLevel2Id;
	}

	public java.lang.String getDescription() {
		return description;
	}

	public void setDescription(java.lang.String description) {
		this.description = description;
	}

	public java.lang.String getKeyWordSet() {
		return keyWordSet;
	}

	public void setKeyWordSet(java.lang.String keyWordSet) {
		this.keyWordSet = keyWordSet;
	}

	public java.lang.String getContactName() {
		return contactName;
	}

	public void setContactName(java.lang.String contactName) {
		this.contactName = contactName;
	}

	public java.lang.String getContactTel() {
		return contactTel;
	}

	public void setContactTel(java.lang.String contactTel) {
		this.contactTel = contactTel;
	}

	public java.lang.String getContactAddress() {
		return contactAddress;
	}

	public void setContactAddress(java.lang.String contactAddress) {
		this.contactAddress = contactAddress;
	}

	public java.lang.String getOfficialWebsite() {
		return officialWebsite;
	}

	public void setOfficialWebsite(java.lang.String officialWebsite) {
		this.officialWebsite = officialWebsite;
	}

	public java.lang.Integer getOfficialWebsiteFlag() {
		return officialWebsiteFlag;
	}

	public void setOfficialWebsiteFlag(java.lang.Integer officialWebsiteFlag) {
		this.officialWebsiteFlag = officialWebsiteFlag;
	}

	public java.lang.String getZip() {
		return zip;
	}

	public void setZip(java.lang.String zip) {
		this.zip = zip;
	}

	public java.lang.String getFax() {
		return fax;
	}

	public void setFax(java.lang.String fax) {
		this.fax = fax;
	}

	public java.lang.String getLogo() {
		return logo;
	}

	public void setLogo(java.lang.String logo) {
		this.logo = logo;
	}

	public java.util.Date getLogoCreationTime() {
		return logoCreationTime;
	}

	public void setLogoCreationTime(java.util.Date logoCreationTime) {
		this.logoCreationTime = logoCreationTime;
	}
	public java.lang.Integer getUserNum() {
		return userNum;
	}

	public void setUserNum(java.lang.Integer userNum) {
		this.userNum = userNum;
	}

	public java.util.Date getLastLoginTime() {
		return lastLoginTime;
	}

	public void setLastLoginTime(java.util.Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	public java.lang.String getLogoOrig() {
		return logoOrig;
	}

	public void setLogoOrig(java.lang.String logoOrig) {
		this.logoOrig = logoOrig;
	}

	public java.lang.String getSmsConfig() {
		return smsConfig;
	}

	public void setSmsConfig(java.lang.String smsConfig) {
		this.smsConfig = smsConfig;
	}
	
	public java.lang.String getCountry() {
		return country;
	}

	public void setCountry(java.lang.String country) {
		this.country = country;
	}

	public java.lang.String getDealerCode() {
		return dealerCode;
	}

	public void setDealerCode(java.lang.String dealerCode) {
		this.dealerCode = dealerCode;
	}


	public java.lang.Integer getDealerType() {
		return dealerType;
	}


	public void setDealerType(java.lang.Integer dealerType) {
		this.dealerType = dealerType;
	}

	public Integer getIsRestrictionCity() {
		return isRestrictionCity;
	}

	public void setIsRestrictionCity(Integer isRestrictionCity) {
		this.isRestrictionCity = isRestrictionCity;
	}

	
}