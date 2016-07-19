package com.ava.domain.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name="t_operator")
public class Operator implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(unique = true, nullable = true, length = 32)
	private java.lang.Integer id;

	private String name;

	@Column(name = "LOGIN_NAME")
	private String loginName;

	@Column(name = "LOGIN_PASSWORD")
	private String loginPassword;

	@Column(name = "PERMISSION_CODE")
	private String permissionCode;

	@Column(name = "LAST_LOGIN_DATETIME")
	private java.util.Date lastLoginTime;

	//**************************************rights-start	**************************************
	@Transient
	private boolean isAdminstrator;
	//**************************************系统管理-start	**************************************
	@Transient
	private boolean hasRightOfManageSystem;
	@Transient
	private boolean hasRightOfManageOperator;
	@Transient
	private boolean hasRightOfMaintOperator;
	@Transient
	private boolean hasRightOfAddOperator;
	@Transient
	private boolean hasRightOfDeleteOperator;
	@Transient
	private boolean hasRightOfEditPassword;
	@Transient
	private boolean hasRightOfGrantRight;
	@Transient
	private boolean hasRightOfManageDictionary;
	@Transient
	private boolean hasRightOfSetDictionary;
	@Transient
	private boolean hasRightOfProductSortAndBrand;
	@Transient
	private boolean hasRightOfMaintRegion;
	@Transient
	private boolean hasRightOfStatisticalReport;
	@Transient
	private boolean hasRightOfSalesStatistics;
	@Transient
	//**************************************系统管理-end	**************************************
	//**************************************内容管理-start	**************************************
	private boolean hasRightOfManageContent;
	@Transient
	private boolean hasRightOfDynamicInfo;
	@Transient
	private boolean hasRightOfInfoEx;
	@Transient
	private boolean hasRightOfDeleteInfo;
	@Transient
	//**************************************内容管理-end	**************************************
	//**************************************客户关系-start	**************************************
	private boolean hasRightOfManageCrm;
	@Transient
	private boolean hasRightOfManageCustomer;
	@Transient
	private boolean hasRightOfMaintCustomer;
	@Transient
	private boolean hasRightOfDeleteCustomer;
	@Transient
	private boolean hasRightOfManageFriend;
	@Transient
	private boolean hasRightOfInviteFriend;
	@Transient
	private boolean hasRightOfDeleteFriend;
	@Transient
	//**************************************客户关系-end	**************************************
	//**************************************仓储物流-start	**************************************
	private boolean hasRightOfManageStorageLogistics;
	@Transient
	private boolean hasRightOfManageStorage;
	@Transient
	private boolean hasRightOfInstore;
	@Transient
	private boolean hasRightOfOutstore;
	@Transient
	private boolean hasRightOfManageLogistics;
	@Transient
	private boolean hasRightOfMaintLogisticsCompany;
	@Transient
	private boolean hasRightOfSubbillLogisticsProcess;
	@Transient
	//**************************************仓储物流-end	**************************************
	//**************************************商品管理-start	**************************************
	private boolean hasRightOfManageCommodity;
	@Transient
	private boolean hasRightOfManageProduct;
	@Transient
	private boolean hasRightOfMaintProduct;
	@Transient
	private boolean hasRightOfProductRemark;
	@Transient
	private boolean hasRightOfManagePurchursing;
	@Transient
	private boolean hasRightOfMaintPurchursing;
	@Transient
	private boolean hasRightOfManageSuite;
	@Transient
	private boolean hasRightOfMaintSuite;
	@Transient
	private boolean hasRightOfManageScore;
	@Transient
	private boolean hasRightOfMaintScore;
	@Transient
	//**************************************商品管理-end	**************************************
	//**************************************客户服务-start	**************************************
	private boolean hasRightOfCustomerService;
	@Transient
	private boolean hasRightOfManagePresale;
	@Transient
	private boolean hasRightOfCustomerConsultation;
	@Transient
	private boolean hasRightOfManagePointOfSale;
	@Transient
	private boolean hasRightOfSubbillProcess;
//	private boolean hasRightOfPostsale;
	//**************************************客户服务-end	**************************************
	//**************************************rights-end	**************************************
	public Operator() {
	}
	public Operator(String loginName, String loginPassword) {
		setLoginName(loginName);
		setLoginPassword(loginPassword);
	}
	/**	权限转换处理，根据rightsCode字段得到对应的权限	*/
	public void processPermissionCode(){
		String rights = this.getPermissionCode();
		if (rights == null){
			rights = "";
		}
		if(getLoginName().equals("admin")){
			isAdminstrator = true;//超级管理员
			//**************************************系统管理-start**************************************
			hasRightOfManageSystem= true;
			hasRightOfManageOperator= true;
			hasRightOfMaintOperator= true;
			hasRightOfAddOperator= true;
			hasRightOfDeleteOperator= true;
			hasRightOfEditPassword= true;
			hasRightOfGrantRight= true;
			hasRightOfManageDictionary= true;
			hasRightOfSetDictionary= true;
			hasRightOfProductSortAndBrand= true;
			hasRightOfMaintRegion= true;
			hasRightOfStatisticalReport= true;
			hasRightOfSalesStatistics= true;
			//**************************************系统管理-end	**************************************
			//**************************************内容管理-start	**************************************
			hasRightOfManageContent= true;
			hasRightOfDynamicInfo= true;
			hasRightOfInfoEx= true;
			hasRightOfDeleteInfo= true;
			//**************************************内容管理-end	**************************************
			//**************************************客户关系-start	**************************************
			hasRightOfManageCrm= true;
			hasRightOfManageCustomer= true;
			hasRightOfMaintCustomer= true;
			hasRightOfDeleteCustomer= true;
			hasRightOfManageFriend= true;
			hasRightOfInviteFriend= true;
			hasRightOfDeleteFriend= true;
			//**************************************客户关系-end	**************************************
			//**************************************仓储物流-start	**************************************
			hasRightOfManageStorageLogistics= true;
			hasRightOfManageStorage= true;
			hasRightOfInstore= true;
			hasRightOfOutstore= true;
			hasRightOfManageLogistics= true;
			hasRightOfMaintLogisticsCompany= true;
			hasRightOfSubbillLogisticsProcess= true;
			//**************************************仓储物流-end	**************************************
			//**************************************商品管理-start	**************************************
			hasRightOfManageCommodity = true;
			hasRightOfManageProduct= true;
			hasRightOfMaintProduct= true;
			hasRightOfProductRemark= true;
			hasRightOfManagePurchursing = true;
			hasRightOfMaintPurchursing = true;
			hasRightOfManageSuite = true;
			hasRightOfMaintSuite = true;
			hasRightOfManageScore = true;
			hasRightOfMaintScore = true;
			//**************************************商品管理-end	**************************************
			//**************************************客户服务-start	**************************************
			hasRightOfCustomerService = true;
			hasRightOfManagePresale= true;
			hasRightOfCustomerConsultation= true;
			hasRightOfManagePointOfSale= true;
			hasRightOfSubbillProcess= true;
			//**************************************客户服务-end	**************************************
		}else{
			if(rights.length()>0 ){
				//**************************************系统管理-start	**************************************
				if(rights.indexOf("Menu1")>-1){
					hasRightOfManageSystem = true;
				}
				if(rights.indexOf("Menu1List1")>-1){//--------------//操作员管理
					hasRightOfManageOperator = true;
				}
				if(rights.indexOf("1101")>-1){
					hasRightOfMaintOperator = true;//操作员列表
				}
				if(rights.indexOf("1102")>-1){
					hasRightOfAddOperator = true;//增加操作员
				}
				if(rights.indexOf("1103")>-1){
					hasRightOfDeleteOperator= true;//删除操作员
				}
				if(rights.indexOf("1104")>-1){
					hasRightOfEditPassword = true;//修改密码
				}
				if(rights.indexOf("1105")>-1){
					hasRightOfGrantRight = true;//分配权限
				}
				if(rights.indexOf("Menu1List2")>-1){//--------------//数据字典
					hasRightOfManageDictionary = true;
				}
				if(rights.indexOf("1201")>-1){//设置字典
					hasRightOfSetDictionary = true;
				}
				if(rights.indexOf("1202")>-1){
					hasRightOfProductSortAndBrand = true;//种类品牌关系
				}
				if(rights.indexOf("1203")>-1){
					hasRightOfMaintRegion= true;//地区维护
				}
				if(rights.indexOf("Menu1List3")>-1){//统计报表
					hasRightOfStatisticalReport = true;
				}
				if(rights.indexOf("1301")>-1){
					hasRightOfSalesStatistics = true;//销售类报表
				}
				//**************************************系统管理-end	**************************************
				//**************************************内容管理-start	**************************************
				if(rights.indexOf("Menu2")>-1){
					hasRightOfManageContent = true;
				}
				if(rights.indexOf("Menu2List1")>-1){//--------------//动态信息
					hasRightOfDynamicInfo = true;
				}
				if(rights.indexOf("2101")>-1){//带附件资讯
					hasRightOfInfoEx = true;
				}
				if(rights.indexOf("2102")>-1){
					hasRightOfDeleteInfo = true;//删除资讯
				}
				//**************************************内容管理-end	**************************************
				//**************************************客户关系-start	**************************************
				if(rights.indexOf("Menu3")>-1){
					hasRightOfManageCrm = true;
				}
				if(rights.indexOf("Menu3List1")>-1){//--------------//客户管理
					hasRightOfManageCustomer = true;
				}
				if(rights.indexOf("3101")>-1){//客户列表
					hasRightOfMaintCustomer = true;
				}
				if(rights.indexOf("3102")>-1){
					hasRightOfDeleteCustomer = true;//删除客户
				}
				if(rights.indexOf("Menu3List1")>-1){//--------------//好友管理
					hasRightOfManageFriend = true;
				}
				if(rights.indexOf("3101")>-1){//邀请好友
					hasRightOfInviteFriend = true;
				}
				if(rights.indexOf("3102")>-1){
					hasRightOfDeleteFriend = true;//删除好友
				}
				//**************************************客户关系-end	**************************************
				//**************************************仓储物流-start	**************************************
				if(rights.indexOf("Menu4")>-1){
					hasRightOfManageStorageLogistics = true;
				}
				if(rights.indexOf("Menu4List1")>-1){//--------------//库存管理
					hasRightOfManageStorage = true;
				}
				if(rights.indexOf("4101")>-1){//入库
					hasRightOfInstore = true;
				}
				if(rights.indexOf("4102")>-1){//出库
					hasRightOfOutstore= true;
				}
				if(rights.indexOf("Menu4List2")>-1){//--------------//配送管理
					hasRightOfManageLogistics= true;
				}
				if(rights.indexOf("4201")>-1){//配送公司维护
					hasRightOfMaintLogisticsCompany= true;
				}
				if(rights.indexOf("4202")>-1){//订单配送处理"
					hasRightOfSubbillLogisticsProcess= true;
				}
				//**************************************仓储物流-end	**************************************
				//**************************************商品管理-start	**************************************
				if(rights.indexOf("Menu5")>-1){
					hasRightOfManageCommodity = true;
				}
				if(rights.indexOf("Menu5List1")>-1){//--------------//产品管理
					hasRightOfManageProduct = true;
				}
				if(rights.indexOf("5101")>-1){//产品维护
					hasRightOfMaintProduct = true;
				}
				if(rights.indexOf("5102")>-1){//产品评论
					hasRightOfProductRemark= true;
				}
				if(rights.indexOf("Menu5List2")>-1){//--------------//采购管理
					hasRightOfManagePurchursing = true;
				}
				if(rights.indexOf("5201")>-1){//采购
					hasRightOfMaintPurchursing = true;
				}
				if(rights.indexOf("Menu5List3")>-1){//--------------//套餐管理
					hasRightOfManageSuite = true;
				}
				if(rights.indexOf("5301")>-1){//套餐维护
					hasRightOfMaintSuite = true;
				}
				if(rights.indexOf("Menu5List4")>-1){//--------------//积分管理
					hasRightOfManageScore = true;
				}
				if(rights.indexOf("5401")>-1){//积分维护
					hasRightOfMaintScore = true;
				}
				//**************************************商品管理-end	**************************************
				//**************************************客户服务-start	**************************************
				if(rights.indexOf("Menu6")>-1){
					hasRightOfCustomerService = true;
				}
				if(rights.indexOf("Menu6List1")>-1){//--------------//售前管理
					hasRightOfManagePresale = true;
				}
				if(rights.indexOf("6101")>-1){//客户咨询
					hasRightOfCustomerConsultation = true;
				}
				if(rights.indexOf("Menu6List2")>-1){//--------------//售中管理
					hasRightOfManagePointOfSale = true;
				}
				if(rights.indexOf("6201")>-1){//订购单处理
					hasRightOfSubbillProcess = true;
				}
				//**************************************客户服务-end	**************************************
			}
		}
	}

	public boolean isAdminstrator() {
		return isAdminstrator;
	}

	public void setAdminstrator(boolean isAdminstrator) {
		this.isAdminstrator = isAdminstrator;
	}

	public boolean hasRightOfManageOperator() {
		return hasRightOfManageOperator;
	}

	public void setRightOfManageOperator(boolean hasRightOfManageOperator) {
		this.hasRightOfManageOperator = hasRightOfManageOperator;
	}

	public boolean hasRightOfMaintOperator() {
		return hasRightOfMaintOperator;
	}

	public void setRightOfMaintOperator(boolean hasRightOfMaintOperator) {
		this.hasRightOfMaintOperator = hasRightOfMaintOperator;
	}

	public boolean hasRightOfAddOperator() {
		return hasRightOfAddOperator;
	}

	public void setRightOfAddOperator(boolean hasRightOfAddOperator) {
		this.hasRightOfAddOperator = hasRightOfAddOperator;
	}

	public boolean hasRightOfDeleteOperator() {
		return hasRightOfDeleteOperator;
	}

	public void setRightOfDeleteOperator(boolean hasRightOfDeleteOperator) {
		this.hasRightOfDeleteOperator = hasRightOfDeleteOperator;
	}

	public boolean hasRightOfEditPassword() {
		return hasRightOfEditPassword;
	}

	public void setRightOfEditPassword(boolean hasRightOfEditPassword) {
		this.hasRightOfEditPassword = hasRightOfEditPassword;
	}

	public boolean hasRightOfGrantRight() {
		return hasRightOfGrantRight;
	}

	public void setRightOfGrantRight(boolean hasRightOfGrantRight) {
		this.hasRightOfGrantRight = hasRightOfGrantRight;
	}

	public boolean hasRightOfManageDictionary() {
		return hasRightOfManageDictionary;
	}

	public void setRightOfManageDictionary(boolean hasRightOfManageDictionary) {
		this.hasRightOfManageDictionary = hasRightOfManageDictionary;
	}

	public boolean hasRightOfSetDictionary() {
		return hasRightOfSetDictionary;
	}

	public void setRightOfSetDictionary(boolean hasRightOfSetDictionary) {
		this.hasRightOfSetDictionary = hasRightOfSetDictionary;
	}

	public boolean hasRightOfProductSortAndBrand() {
		return hasRightOfProductSortAndBrand;
	}

	public void setRightOfProductSortAndBrand(
			boolean hasRightOfProductSortAndBrand) {
		this.hasRightOfProductSortAndBrand = hasRightOfProductSortAndBrand;
	}

	public boolean hasRightOfMaintRegion() {
		return hasRightOfMaintRegion;
	}

	public void setRightOfMaintRegion(
			boolean hasRightOfMaintRegion) {
		this.hasRightOfMaintRegion = hasRightOfMaintRegion;
	}

	public boolean hasRightOfStatisticalReport() {
		return hasRightOfStatisticalReport;
	}

	public void setRightOfStatisticalReport(boolean hasRightOfStatisticalReport) {
		this.hasRightOfStatisticalReport = hasRightOfStatisticalReport;
	}

	public boolean hasRightOfSalesStatistics() {
		return hasRightOfSalesStatistics;
	}

	public void setRightOfSalesStatistics(boolean hasRightOfSalesStatistics) {
		this.hasRightOfSalesStatistics = hasRightOfSalesStatistics;
	}

	public boolean hasRightOfDynamicInfo() {
		return hasRightOfDynamicInfo;
	}

	public void setRightOfDynamicInfo(boolean hasRightOfDynamicInfo) {
		this.hasRightOfDynamicInfo = hasRightOfDynamicInfo;
	}

	public boolean hasRightOfInfoEx() {
		return hasRightOfInfoEx;
	}

	public void setRightOfInfoEx(boolean hasRightOfInfoEx) {
		this.hasRightOfInfoEx = hasRightOfInfoEx;
	}

	public boolean hasRightOfDeleteInfo() {
		return hasRightOfDeleteInfo;
	}

	public void setRightOfDeleteInfo(boolean hasRightOfDeleteInfo) {
		this.hasRightOfDeleteInfo = hasRightOfDeleteInfo;
	}

	public boolean hasRightOfManageCustomer() {
		return hasRightOfManageCustomer;
	}

	public void setRightOfManageCustomer(boolean hasRightOfManageCustomer) {
		this.hasRightOfManageCustomer = hasRightOfManageCustomer;
	}

	public boolean hasRightOfMaintCustomer() {
		return hasRightOfMaintCustomer;
	}

	public void setRightOfMaintCustomer(boolean hasRightOfMaintCustomer) {
		this.hasRightOfMaintCustomer = hasRightOfMaintCustomer;
	}

	public boolean hasRightOfDeleteCustomer() {
		return hasRightOfDeleteCustomer;
	}

	public void setRightOfDeleteCustomer(boolean hasRightOfDeleteCustomer) {
		this.hasRightOfDeleteCustomer = hasRightOfDeleteCustomer;
	}

	public boolean hasRightOfManageFriend() {
		return hasRightOfManageFriend;
	}

	public void setRightOfManageFriend(boolean hasRightOfManageFriend) {
		this.hasRightOfManageFriend = hasRightOfManageFriend;
	}

	public boolean hasRightOfInviteFriend() {
		return hasRightOfInviteFriend;
	}

	public void setRightOfInviteFriend(boolean hasRightOfInviteFriend) {
		this.hasRightOfInviteFriend = hasRightOfInviteFriend;
	}

	public boolean hasRightOfDeleteFriend() {
		return hasRightOfDeleteFriend;
	}

	public void setRightOfDeleteFriend(boolean hasRightOfDeleteFriend) {
		this.hasRightOfDeleteFriend = hasRightOfDeleteFriend;
	}

	public boolean hasRightOfManageStorage() {
		return hasRightOfManageStorage;
	}

	public void setRightOfManageStorage(boolean hasRightOfManageStorage) {
		this.hasRightOfManageStorage = hasRightOfManageStorage;
	}

	public boolean hasRightOfInstore() {
		return hasRightOfInstore;
	}

	public void setRightOfInstore(boolean hasRightOfInstore) {
		this.hasRightOfInstore = hasRightOfInstore;
	}

	public boolean hasRightOfOutstore() {
		return hasRightOfOutstore;
	}

	public void setRightOfOutstore(boolean hasRightOfOutstore) {
		this.hasRightOfOutstore = hasRightOfOutstore;
	}

	public boolean hasRightOfManageLogistics() {
		return hasRightOfManageLogistics;
	}

	public void setRightOfManageLogistics(boolean hasRightOfManageLogistics) {
		this.hasRightOfManageLogistics = hasRightOfManageLogistics;
	}

	public boolean hasRightOfMaintLogisticsCompany() {
		return hasRightOfMaintLogisticsCompany;
	}

	public void setRightOfMaintLogisticsCompany(boolean hasRightOfMaintLogisticsCompany) {
		this.hasRightOfMaintLogisticsCompany = hasRightOfMaintLogisticsCompany;
	}

	public boolean hasRightOfSubbillLogisticsProcess() {
		return hasRightOfSubbillLogisticsProcess;
	}

	public void setRightOfSubbillLogisticsProcess(boolean hasRightOfSubbillLogisticsProcess) {
		this.hasRightOfSubbillLogisticsProcess = hasRightOfSubbillLogisticsProcess;
	}

	public boolean hasRightOfManageProduct() {
		return hasRightOfManageProduct;
	}

	public void setRightOfManageProduct(boolean hasRightOfManageProduct) {
		this.hasRightOfManageProduct = hasRightOfManageProduct;
	}

	public boolean hasRightOfMaintProduct() {
		return hasRightOfMaintProduct;
	}

	public void setRightOfMaintProduct(boolean hasRightOfMaintProduct) {
		this.hasRightOfMaintProduct = hasRightOfMaintProduct;
	}

	public boolean hasRightOfProductRemark() {
		return hasRightOfProductRemark;
	}

	public void setRightOfProductRemark(boolean hasRightOfProductRemark) {
		this.hasRightOfProductRemark = hasRightOfProductRemark;
	}

	public boolean hasRightOfManagePurchursing() {
		return hasRightOfManagePurchursing;
	}

	public void setRightOfManagePurchursing(boolean hasRightOfManagePurchursing) {
		this.hasRightOfManagePurchursing = hasRightOfManagePurchursing;
	}

	public boolean hasRightOfMaintPurchursing() {
		return hasRightOfMaintPurchursing;
	}

	public void setRightOfMaintPurchursing(boolean hasRightOfMaintPurchursing) {
		this.hasRightOfMaintPurchursing = hasRightOfMaintPurchursing;
	}

	public boolean hasRightOfManageSuite() {
		return hasRightOfManageSuite;
	}

	public void setRightOfManageSuite(boolean hasRightOfManageSuite) {
		this.hasRightOfManageSuite = hasRightOfManageSuite;
	}

	public boolean hasRightOfMaintSuite() {
		return hasRightOfMaintSuite;
	}

	public void setRightOfMaintSuite(boolean hasRightOfMaintSuite) {
		this.hasRightOfMaintSuite = hasRightOfMaintSuite;
	}

	public boolean hasRightOfManageScore() {
		return hasRightOfManageScore;
	}

	public void setRightOfManageScore(boolean hasRightOfManageScore) {
		this.hasRightOfManageScore = hasRightOfManageScore;
	}

	public boolean hasRightOfMaintScore() {
		return hasRightOfMaintScore;
	}

	public void setRightOfMaintScore(boolean hasRightOfMaintScore) {
		this.hasRightOfMaintScore = hasRightOfMaintScore;
	}

	public boolean hasRightOfManagePresale() {
		return hasRightOfManagePresale;
	}

	public void setRightOfManagePresale(boolean hasRightOfManagePresale) {
		this.hasRightOfManagePresale = hasRightOfManagePresale;
	}

	public boolean hasRightOfCustomerConsultation() {
		return hasRightOfCustomerConsultation;
	}

	public void setRightOfCustomerConsultation(
			boolean hasRightOfCustomerConsultation) {
		this.hasRightOfCustomerConsultation = hasRightOfCustomerConsultation;
	}

	public boolean hasRightOfManagePointOfSale() {
		return hasRightOfManagePointOfSale;
	}

	public void setRightOfManagePointOfSale(boolean hasRightOfManagePointOfSale) {
		this.hasRightOfManagePointOfSale = hasRightOfManagePointOfSale;
	}

	public boolean hasRightOfSubbillProcess() {
		return hasRightOfSubbillProcess;
	}

	public void setRightOfSubbillProcess(boolean hasRightOfSubbillProcess) {
		this.hasRightOfSubbillProcess = hasRightOfSubbillProcess;
	}

	public boolean hasRightOfManageSystem() {
		return hasRightOfManageSystem;
	}

	public void setRightOfManageSystem(boolean hasRightOfManageSystem) {
		this.hasRightOfManageSystem = hasRightOfManageSystem;
	}

	public boolean hasRightOfManageContent() {
		return hasRightOfManageContent;
	}

	public void setRightOfManageContent(boolean hasRightOfManageContent) {
		this.hasRightOfManageContent = hasRightOfManageContent;
	}

	public boolean hasRightOfManageCrm() {
		return hasRightOfManageCrm;
	}

	public void setRightOfManageCrm(boolean hasRightOfManageCrm) {
		this.hasRightOfManageCrm = hasRightOfManageCrm;
	}

	public boolean hasRightOfManageStorageLogistics() {
		return hasRightOfManageStorageLogistics;
	}

	public void setRightOfManageStorageLogistics(
			boolean hasRightOfManageStorageLogistics) {
		this.hasRightOfManageStorageLogistics = hasRightOfManageStorageLogistics;
	}

	public boolean hasRightOfManageCommodity() {
		return hasRightOfManageCommodity;
	}

	public void setRightOfManageCommodity(boolean hasRightOfManageCommodity) {
		this.hasRightOfManageCommodity = hasRightOfManageCommodity;
	}

	public boolean hasRightOfCustomerService() {
		return hasRightOfCustomerService;
	}

	public void setRightOfCustomerService(boolean hasRightOfCustomerService) {
		this.hasRightOfCustomerService = hasRightOfCustomerService;
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getLoginPassword() {
		return loginPassword;
	}

	public void setLoginPassword(String loginPassword) {
		this.loginPassword = loginPassword;
	}

	public String getPermissionCode() {
		return permissionCode;
	}

	public void setPermissionCode(String permissionCode) {
		this.permissionCode = permissionCode;
	}

	public java.util.Date getLastLoginTime() {
		return lastLoginTime;
	}

	public void setLastLoginTime(java.util.Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

}