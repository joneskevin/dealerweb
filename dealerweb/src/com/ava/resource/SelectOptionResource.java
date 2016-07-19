package com.ava.resource;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.ava.domain.entity.Company;
import com.ava.domain.entity.DataDictionary;
import com.ava.domain.entity.Department;
import com.ava.domain.entity.User;

public class SelectOptionResource {
	/**
	 * 适用于在区间数组中查找指定的区间
	 * 
	 * @param strArray
	 *            例如：{"1,500元及以下,0-500", "2,501-1000元,501-1000"}
	 * @param value
	 *            例如：2
	 * @return 例如：{"501","1000"}
	 */
	static public String[] getOptionTextPairByValue(String[] strList,
			Integer value) {
		String[] temp;
		if (strList == null || strList.length == 0) {
			return null;
		}
		if (value == null || value.toString().equals("")) {
			return null;
		}
		for (int i = 0; i < strList.length; i++) {
			if (!(strList[i] == null || "".equals(strList[i]))) {
				temp = strList[i].split(",");
				if (!(temp[0] == null || temp[1] == null)) {
					if (temp[0].equalsIgnoreCase(value.toString())) {
						String[] eArray = temp[2].split("-");
						if (eArray != null && eArray.length > 1) {
							return eArray;
						}
					}
				}
			}
		}
		return null;
	}

	static public String getOptionTextByValue(String[] strList, Integer value) {
		List optionList = generateSelectOptions(strList);
		return getOptionTextByValue(optionList, value);
	}

	static public String getOptionTextByValue(List optionList, Integer value) {
		if (optionList == null || optionList.size() == 0) {
			return "";
		}
		if (value == null || value.toString().equals(""))
			return "";
		for (int i = 0; i < optionList.size(); i++) {
			SelectOption option = (SelectOption)optionList.get(i);
			if (option != null) {
				if (option.getOptionValue() != null && option.getOptionValue().equalsIgnoreCase(value.toString())) {
					return option.getOptionText();
				}
			}
		}
		return "未定义";
	}
	
	static public SelectOption getOptionByValue(List optionList, Integer value) {
		if (optionList == null || optionList.size() == 0) {
			return null;
		}
		if (value == null || value.toString().equals(""))
			return null;
		for (int i = 0; i < optionList.size(); i++) {
			Iterator itor = optionList.iterator();
			while (itor.hasNext()) {
				SelectOption option = (SelectOption) itor.next();
				if (option.getOptionValue().equalsIgnoreCase(value.toString())) {
					return option;
				}
			}
		}
		return null;
	}

	static protected String getOptionTextByValue(String[] strList, Byte value) {
		Integer myValue = null;
		if (value == null) {
			return null;
		} else {
			myValue = new Integer(value.intValue());
		}
		return getOptionTextByValue(strList, myValue);
	}

	static protected Integer getOptionValueByText(String[] strList, String text) {
		String[] temp = null;
		if (strList == null || strList.length == 0) {
			return null;
		}
		if (text == null || text.toString().equals(""))
			return null;
		for (int i = 0; i < strList.length; i++) {
			if (!(strList[i] == null || "".equals(strList[i]))) {
				temp = strList[i].split(",");
				if (!(temp[0] == null || temp[1] == null)) {
					if (temp[1].equalsIgnoreCase(text.toString())) {
						try {
							return new Integer(temp[0]);
						} catch (Exception e) {
							return null;
						}
					}
				}
			}
		}
		return null;
	}

	static public List<SelectOption> getOptionsByExtValue4appro(String parentId) {
		if (parentId == null) {
			return generateSelectOptions(null);
		}
		//请假
		if(GlobalConstant.APPRO_PROPOSAL_BASE_TYPE_LEAVE.toString().equalsIgnoreCase(parentId)){
			return leaveTypeOptions();
		}
		//外出
		if(GlobalConstant.APPRO_PROPOSAL_BASE_TYPE_OUT.toString().equalsIgnoreCase(parentId)){
			return outTypeOptions();
		}
		return generateSelectOptions(null);
	}

	static private List generateSelectOptions(String[] strList) {
		return generateSelectOptions(strList, "---请选择---", "-1");
	}
	
	static private List generateSelectOptions(String[] strList,
			String defaultView, String defaultValue) {
		List<Object> colReturn = new ArrayList<Object>();
		SelectOption listOption = null;
		listOption = new SelectOption();
		if (defaultView != null && !"".equals(defaultView)) {
			listOption.setOptionText(defaultView);
			listOption.setOptionValue(defaultValue);
			colReturn.add(listOption);
		}
		
		if(strList == null){
			return colReturn;
		}
		
		String[] temp;
		for (int i = 0; i < strList.length; i++) {
			if (!(strList[i] == null || "".equals(strList[i]))) {
				temp = strList[i].split(",");
				if (!(temp[0] == null || temp[1] == null)) {
					listOption = new SelectOption();
					listOption.setOptionText(temp[1]);
					listOption.setOptionValue(temp[0]);
					colReturn.add(listOption);
				}
				if (temp.length > 2 && temp[2] != null) {
					listOption.setExtValue(temp[2]);
				}
			}
		}
		return colReturn;
	}

	/**	根据数据字典类型对象集合产生SelectOption集合	*/
	static protected List generateSelectOptionsFromDataDictionaryList(List dataList) {
		List<Object> colReturn = new ArrayList<Object>();
		SelectOption listOption = null;
		listOption = new SelectOption();
		String defaultValue = "-1";
		listOption.setOptionText("请选择");
		listOption.setOptionValue(defaultValue);
		colReturn.add(listOption);

		Iterator itor = dataList.iterator();
		while(itor.hasNext()){
			DataDictionary data = (DataDictionary)itor.next();
			listOption = new SelectOption();
			listOption.setOptionText(data.getName());
			listOption.setOptionValue(data.getId().toString());
			colReturn.add(listOption);
		}
		return colReturn;
	}
	
	/**	根据Org对象集合产生SelectOption集合	*/
	static public List generateSelectOptionsFromOrgList(List dataList,
			String defaultView, String defaultValue) {
		List<Object> colReturn = new ArrayList<Object>();
		SelectOption listOption = null;
		
		listOption = new SelectOption();
		defaultValue = "-1";
		listOption.setOptionText("请选择");
		listOption.setOptionValue(defaultValue);
		colReturn.add(listOption);
		Iterator itor = dataList.iterator();
		while(itor.hasNext()){
			Company org = (Company)itor.next();
			listOption = new SelectOption();
			listOption.setOptionText(org.getName()+"（"+org.getObName()+"）");
			listOption.setOptionValue(org.getId().toString());
			colReturn.add(listOption);
		}
		return colReturn;
	}
	
	/**	根据数据用户类型对象集合产生SelectOption集合	*/
	static public List generateSelectOptionsFromUserList(List dataList){
		return generateSelectOptionsFromUserList(dataList, "请选择", "-1");
	}

	/**	根据数据用户类型对象集合产生SelectOption集合	*/
	static public List generateSelectOptionsFromUserList(List dataList,
			String defaultView, String defaultValue) {
		List<Object> colReturn = new ArrayList<Object>();
		SelectOption listOption = null;
		
		if (defaultView != null && !"".equals(defaultView)) {
			listOption = new SelectOption();
			listOption.setOptionText(defaultView);
			listOption.setOptionValue(defaultValue);
			colReturn.add(listOption);
		}

		Iterator itor = dataList.iterator();
		while(itor.hasNext()){
			User user = (User)itor.next();
			listOption = new SelectOption();
			listOption.setOptionText(user.getLoginName()+"（"+user.getNickName()+"）");
			listOption.setOptionValue(user.getId().toString());
			colReturn.add(listOption);
		}
		return colReturn;
	}
	
	/**	根据数据用户类型(员工列表)对象集合产生SelectOption集合	*/
	static public List generateSelectOptionsFromEmployeeList(List dataList,
			String defaultView, String defaultValue) {
		List<Object> colReturn = new ArrayList<Object>();
		SelectOption listOption = null;
		
		if (defaultView != null && !"".equals(defaultView)) {
			listOption = new SelectOption();
			listOption.setOptionText(defaultView);
			listOption.setOptionValue(defaultValue);
			colReturn.add(listOption);
		}

		Iterator itor = dataList.iterator();
		while(itor.hasNext()){
			User user = (User)itor.next();
			listOption = new SelectOption();
			listOption.setOptionText(user.getLoginName()+"（"+user.getNickName()+"）");
			listOption.setOptionValue(user.getId().toString());
			colReturn.add(listOption);
		}
		return colReturn;
	}
	
	/**	上级部门SelectOption集合	*/
	static public List getSuperDepartmentList(List dataList,
			String defaultView, String defaultValue) {
		
		List<Object> colReturn = new ArrayList<Object>();
		SelectOption listOption = null;
		
		if (defaultView != null && !"".equals(defaultView)) {
			listOption = new SelectOption();
			listOption.setOptionText(defaultView);
			listOption.setOptionValue(defaultValue);
			colReturn.add(listOption);
		}

		Iterator itor = dataList.iterator();
		while(itor.hasNext()){
			Department orgDepartment = (Department)itor.next();
			listOption = new SelectOption();
			listOption.setOptionText(orgDepartment.getName().toString());
			listOption.setOptionValue(orgDepartment.getId().toString());
			colReturn.add(listOption);
		}
		return colReturn;
	}
	
	static public String[] yesOrNo = { "1,是", "0,否" };
	
	static public String[] inOffices = { "1,在职", "0,离职" };
	
	static public List inOfficeOptions() {
		return generateSelectOptions(inOffices, null, null);
	}
	
	static public String[] dealerStatus = { "0,正常", "2,高危", "1,退网"};
	
	static public List<Object> dealerStatusOptions() {
		return generateSelectOptions(dealerStatus, null, null);
	}
	
	static public String[] inPositions = { "1,在岗", "0,离岗" };
	
	static public List inPositionOptions() {
		return generateSelectOptions(inPositions, null, null);
	}
	
	
	static public List yesOrNoOptions() {
		return generateSelectOptions(yesOrNo);
	}

	static public String[] sexs = { "1,男", "0,女" };

	static public List sexOptions() {
		return generateSelectOptions(sexs);
	}

	static public String[] degrees = { "1,小学", "2,初中", "3,高中", "4,大学", "5,硕士",
			"6,博士" };

	static public List degreeOptions() {
		return generateSelectOptions(degrees);
	}

	/**帮助类别（base，post，gate，admin，search）	*/
	static public List helpCenterTypeOptions() {
		return generateSelectOptions(helpCenterTypes);
	}
	static public String[] helpCenterTypes = {
		GlobalConstant.HELPCENTER_TYPE_BASE + ",新空间",
		GlobalConstant.HELPCENTER_TYPE_SEARCH + ",搜索",
		GlobalConstant.HELPCENTER_TYPE_ADMIN + ",后台管理",
		GlobalConstant.HELPCENTER_TYPE_REGISTER + ",注册" };
	
	/**申请基本类型：请假、报销、领用、其它	*/
	static public List approBaseTypeOptions() {
		return generateSelectOptions(approProposalBaseTypes);
	}
	static public String[] approProposalBaseTypes = {
		GlobalConstant.APPRO_PROPOSAL_BASE_TYPE_LEAVE + ",请假",
		GlobalConstant.APPRO_PROPOSAL_BASE_TYPE_OUT + ",外出"
	};
	
	/**请假类型：年休、病假、事假、其它	*/
	static public List leaveTypeOptions() {
		return generateSelectOptions(leaveTypes);
	}
	static private String[] leaveTypes = {
		GlobalConstant.APPRO_LEAVE_TYPE_YEARREST + ",年休" + "," + GlobalConstant.APPRO_PROPOSAL_BASE_TYPE_LEAVE,
		GlobalConstant.APPRO_LEAVE_TYPE_ILLNESS + ",病假" + "," + GlobalConstant.APPRO_PROPOSAL_BASE_TYPE_LEAVE,
		GlobalConstant.APPRO_LEAVE_TYPE_AFFAIR + ",事假" + "," + GlobalConstant.APPRO_PROPOSAL_BASE_TYPE_LEAVE,
		GlobalConstant.APPRO_LEAVE_TYPE_OTHER + ",其它" + "," + GlobalConstant.APPRO_PROPOSAL_BASE_TYPE_LEAVE };
	
	/**外出类型：公出、出差、其它	*/
	static public List outTypeOptions() {
		return generateSelectOptions(outTypes);
	}
	static private String[] outTypes = {
		GlobalConstant.APPRO_OUT_TYPE_AWAY + ",公出" + "," + GlobalConstant.APPRO_PROPOSAL_BASE_TYPE_OUT,
		GlobalConstant.APPRO_OUT_TYPE_EVECTION + ",出差" + "," + GlobalConstant.APPRO_PROPOSAL_BASE_TYPE_OUT,
		GlobalConstant.APPRO_OUT_TYPE_OTHER + ",其它" + "," + GlobalConstant.APPRO_PROPOSAL_BASE_TYPE_OUT };
	
	
	/**申请单类型：待审核、通过、拒绝	*/
	static public List proposalTypeOptions() {
		return generateSelectOptions(proposalTypeArray);
	}
	
	static public String[] proposalTypeArray = {
		GlobalConstant.PROPOSAL_TYPE_INSTALLATION + ",新装",
		GlobalConstant.PROPOSAL_TYPE_DEMOLITION + ",拆除",
		GlobalConstant.PROPOSAL_TYPE_INSTALLATION_PENDING + ",新装审批",
		GlobalConstant.PROPOSAL_TYPE_DEMOLITION_PENDING + ",拆除审批"
	};
	

	/**申请类型(报表使用)：新装申请 ,拆除申请	*/
	static public List proposalReportTypeOptions() {
		return generateSelectOptions(proposalReportTypeArray);
				
	}
	
	static public String[]	 proposalReportTypeArray = {
		GlobalConstant.PROPOSAL_TYPE_INSTALLATION + ",新装申请",
		GlobalConstant.PROPOSAL_TYPE_DEMOLITION + ",拆除申请",
	};
	
	
	/**申请单状态：待审核、通过、拒绝	*/
	static public List approvalStatusOptions() {
		return generateSelectOptions(approvalStatus);
	}
	static public String[] approvalStatus = {
		GlobalConstant.PROPOSAL_STATUS_ZERO + ",无申请",
		GlobalConstant.PROPOSAL_STATUS_PROCESSING + ",待审核",
		GlobalConstant.PROPOSAL_STATUS_PASSED + ",待安装",
		GlobalConstant.PROPOSAL_STATUS_UNPASSED + ",拒绝" };
	
	/**新装申请状态：新装待审核、待安装、拒绝【查询条件】	*/
	static public List installationStatusOptions() {
		return generateSelectOptions(installationStatus, null, null);
	}
	static public String[] installationStatus = {
		GlobalConstant.PROPOSAL_STATUS_PASSED + ",待安装",
		GlobalConstant.PROPOSAL_STATUS_INSTALLED + ",已安装"};
	
	/**新装申请状态：新装待审核、待安装、拒绝【查询列表】	*/
	static public List installationListOptions() {
		return generateSelectOptions(installationList, null, null);
	}
	static public String[] installationList = {
		GlobalConstant.PROPOSAL_STATUS_ZERO + ",无申请",
		GlobalConstant.PROPOSAL_STATUS_PROCESSING + ",新装待审核",
		GlobalConstant.PROPOSAL_STATUS_PASSED + ",待安装",
		GlobalConstant.PROPOSAL_STATUS_UNPASSED + ",拒绝",
		GlobalConstant.PROPOSAL_STATUS_IMPORTANT_NO + ",重点不安装",
		GlobalConstant.PROPOSAL_STATUS_NO_IMPORTANT_NO + ",非重点不安装",
		GlobalConstant.PROPOSAL_STATUS_INSTALLED + ",已安装"};
	
	/**拆除申请状态：拆除待审核、待拆除、拒绝【查询条件】	*/
	static public List demolitionStatusOptions() {
		return generateSelectOptions(demolitionStatus);
	}
	static public String[] demolitionStatus = {
		GlobalConstant.PROPOSAL_STATUS_PROCESSING + ",拆除待审核",
		GlobalConstant.PROPOSAL_STATUS_PASSED + ",待拆除",
		GlobalConstant.PROPOSAL_STATUS_UNPASSED + ",拒绝" };
	
	/**拆除申请状态：拆除待审核、待拆除、拒绝【查询列表】	*/
	static public List demolitionListOptions() {
		return generateSelectOptions(demolitionList);
	}
	static public String[] demolitionList = {
		GlobalConstant.PROPOSAL_STATUS_ZERO + ",无申请",
		GlobalConstant.PROPOSAL_STATUS_PROCESSING + ",拆除待审核",
		GlobalConstant.PROPOSAL_STATUS_PASSED + ",待拆除",
		GlobalConstant.PROPOSAL_STATUS_UNPASSED + ",拒绝" };
	
	/**审批状态：通过、不通过	*/
	static public List approvalItemStatusOptions() {
		return generateSelectOptions(proposalItemStatus);
	}
	static public String[] proposalItemStatus = {
		GlobalConstant.APPROVAL_STATUS_PASSED + ",通过",
		GlobalConstant.APPROVAL_STATUS_UNPASSED + ",不通过"};
	
	/**抽象组织类型*/
	static public List orgTypeOptions() {
		return generateSelectOptions(orgTypes);
	}
	static public String[] orgTypes = {
		GlobalConstant.ABSTRACT_ORG_TYPE_COMPANY + ",公司",
		GlobalConstant.ABSTRACT_ORG_TYPE_DEPARTMENT + ",部门 " };
	
	/**职能类型*/
	static public List functionTypeOptions() {
		return generateSelectOptions(functionTypes);
	}
	static public String[] functionTypes = {
		GlobalConstant.ABSTRACT_ORG_FUNCTION_TYPE_GROUP + ",集团",
		GlobalConstant.ABSTRACT_ORG_FUNCTION_TYPE_BIZ + ",业务部门",
		GlobalConstant.ABSTRACT_ORG_FUNCTION_TYPE_BACK + ",后勤部门",
		GlobalConstant.ABSTRACT_ORG_FUNCTION_TYPE_FACTORTY + ",工厂" };

	/**行驶路线类型：加油、试驾	*/
	static public List driveLineTypeOptions() {
		return generateSelectOptions(driveLineTypeArray);
	}
	
	static public String[] driveLineTypeArray = {
		GlobalConstant.DRIVE_LINE_TYPE_OIL + ",加油",
		GlobalConstant.DRIVE_LINE_TYPE_TEST_DRIVE + ",试驾"
	};
	/**试驾状态 有效 无效	*/
	static public List testDriveStatusOptions() {
		return generateSelectOptions(driveLineTypeArray);
	}
	
	static public String[] testDriveStatusArray = {
		GlobalConstant.TEST_DRIVE_STATUS_VALID + ",合规",
		GlobalConstant.TEST_DRIVE_STATUS_INVALID + ",无效",
		GlobalConstant.TEST_DRIVE_STATUS_UNLAW + ",违规"
	};
	
	/**行驶路线类型：单围栏、双围栏	*/
	static public List driveLineFenceTypeOptions() {
		return generateSelectOptions(driveLineFenceTypeArray, "", "");
	}
	
	static public String[] driveLineFenceTypeArray = {
		GlobalConstant.DRIVE_LINE_FENCE_TYPE_SINGLE + ",单围栏",
		GlobalConstant.DRIVE_LINE_FENCE_TYPE_DOUBLE + ",双围栏"
	};
	
	/**禁止类型：禁止驶入、禁止驶出	*/
	static public List banTypeOptions() {
		return generateSelectOptions(banTypes);
	}
	static public String[] banTypes = {
		GlobalConstant.BAN_TYPE_OUT + ",禁止驶出",
		GlobalConstant.BAN_TYPE_IN + ",禁止驶入"};
	
	/** 网站数据字典名称（操作员角色,客户级别,动态信息等）	*/
	static public List dictinaryRootNodeOptionList() {
		return SelectOptionResource.generateSelectOptions(dictinaryRootNodeArray);
	}
	static public String[] dictinaryRootNodeArray = {
			GlobalConstant.DICTIONARY_OPERATOR_ROLE + ",操作员角色",
			GlobalConstant.DICTIONARY_CUSTOMER_RANK + ",客户级别",
			GlobalConstant.DICTIONARY_PRODUCT_SORT + ",产品种类",
			GlobalConstant.DICTIONARY_INDUSTRY_SORT + ",行业分类",
			GlobalConstant.DICTIONARY_BASE_HELP_CENTER + ",Base帮助中心类别",
			GlobalConstant.DICTIONARY_AREA + ",行政地区",
			GlobalConstant.DICTIONARY_NEWS_SORT + ",动态信息"};

	
	/** 网站数据字典层数限制（1,3,1...）	*/
	static public List dictinaryLeveCountOptionList() {
		return SelectOptionResource.generateSelectOptions(dictinaryLeveCountArray);
	}
	static public String[] dictinaryLeveCountArray = {
			GlobalConstant.DICTIONARY_OPERATOR_ROLE + ",1",
			GlobalConstant.DICTIONARY_CUSTOMER_RANK + ",1",
			GlobalConstant.DICTIONARY_PRODUCT_SORT + ",3",
			GlobalConstant.DICTIONARY_AREA + ",3",
			GlobalConstant.DICTIONARY_INDUSTRY_SORT + ",2",
			GlobalConstant.DICTIONARY_BASE_HELP_CENTER + ",3",
			GlobalConstant.DICTIONARY_NEWS_SORT + ",2"};
	
	/**经销商模块----------start-----------------*/
	/**网络形态*/
	static public String[] dealerTypes = {
		GlobalConstant.DEALER_TYPE_4S_SHOP + ",4S店",
		GlobalConstant.DEALER_TYPE_CITY_SHOWROOM + ",城市展厅",
		GlobalConstant.DEALER_TYPE_STRAIGHT_DIRECT_SHOP + ",直管直营店",
		GlobalConstant.DEALER_TYPE_NON_STRAIGHT_DIRECT_SHOP + ",直营店"};
	
	static public List dealerTypeOptions() {
		return generateSelectOptions(dealerTypes, null, null);
	}
		
	/**经销商状态（是否重点城市）*/
	static public String[] isKeyCitys = {
		GlobalConstant.KEY_CITY + ",重点城市",
		GlobalConstant.NON_KEY_CITY + ",非重点城市"};
	
	static public List isKeyCityOptions() {
		return generateSelectOptions(isKeyCitys, null, null);
	}
	
	/**经销商上下班时间段*/
	static public String[] workTimeArray = {
		GlobalConstant.HOUR_1 + ",1点",
		GlobalConstant.HOUR_2 + ",2点",
		GlobalConstant.HOUR_3 + ",3点",
		GlobalConstant.HOUR_4 + ",4点",
		GlobalConstant.HOUR_5 + ",5点",
		GlobalConstant.HOUR_6 + ",6点",
		GlobalConstant.HOUR_7 + ",7点",
		GlobalConstant.HOUR_8 + ",8点",
		GlobalConstant.HOUR_9 + ",9点",
		GlobalConstant.HOUR_10 + ",10点",
		GlobalConstant.HOUR_11 + ",11点",
		GlobalConstant.HOUR_12 + ",12点",
		GlobalConstant.HOUR_13 + ",13点",
		GlobalConstant.HOUR_14 + ",14点",
		GlobalConstant.HOUR_15 + ",15点",
		GlobalConstant.HOUR_16 + ",16点",
		GlobalConstant.HOUR_17 + ",17点",
		GlobalConstant.HOUR_18 + ",18点",
		GlobalConstant.HOUR_19 + ",19点",
		GlobalConstant.HOUR_20 + ",20点",
		GlobalConstant.HOUR_21 + ",21点",
		GlobalConstant.HOUR_22 + ",22点",
		GlobalConstant.HOUR_23 + ",23点",
		GlobalConstant.HOUR_24 + ",24点"};
		
	static public List workTimeOptions() {
		return generateSelectOptions(workTimeArray, null, null);
	}
		
	/**经销商模块----------end-----------------*/
	
	/**设备模块----------start-----------------*/
	/**设备状态*/
	static public String[] boxStatusArray = {
		GlobalConstant.BOX_STATUS_ACTIVATION + ",已激活",
		GlobalConstant.BOX_STATUS_INACTIVE + ",未激活"};
	
	static public List boxStatusOptions() {
		return generateSelectOptions(boxStatusArray, null, null);
	}
	/**设备模块----------end-----------------*/
	
	/**车辆模块----------start-----------------*/
	static public String[] carBrandArray = {
		GlobalConstant.CAR_BRAND_SKODA + ",1",
		GlobalConstant.CAR_BRAND_VM + ",2"};
	
	static public List carBrandOptions() {
		return generateSelectOptions(carBrandArray);
	}

	static public String[] configureTypeArray = {
		GlobalConstant.CONFIGURE_TYPE_NO + ",不配",
		GlobalConstant.CONFIGURE_TYPE_MAY + ",选配",
		GlobalConstant.CONFIGURE_TYPE_MUST + ",必配"};
	
	static public List configureTypeOptions() {
		return generateSelectOptions(configureTypeArray, null, null);
	}

	/** 查询列表 */
	static public String[] configureStatusArray = {
		GlobalConstant.CONFIGURE_STATUS_WATTING + ",待安装",
		GlobalConstant.CONFIGURE_STATUS_INSTALLED + ",已安装",
		GlobalConstant.CONFIGURE_STATUS_UNINSTALLED + ",锁定"};
	
	/**查询条件：待安装、已安装	*/
	static public String[] configureStatusConditinonArray = {
		GlobalConstant.CONFIGURE_STATUS_WATTING + ",待安装",
		GlobalConstant.CONFIGURE_STATUS_INSTALLED + ",已安装"};
	
	static public List configureStatusOptions() {
		return generateSelectOptions(configureStatusConditinonArray, null, null);
	}
	/**车辆模块----------end-----------------*/
	
	/**违规模块----------start-----------------*/
	/**申请单状态：草稿、已驳回、已取消、审批中、已通过等	*/
	static public List violationTypeOptions() {
		return generateSelectOptions(violationTypes, null, null);
	}
	static public String[] violationTypes = {
		GlobalConstant.LINE_VIOLATION_TYPE + ",路线违规",
		GlobalConstant.TIME_VIOLATION_TYPE + ",时间违规"
		 };
	
	static public List constantMonthOptions() {
		return generateSelectOptions(constantMonthTypes, null, null);
	}
	static public String[] constantMonthTypes = {
		GlobalConstant.MONTH_1 + ",1月",
		GlobalConstant.MONTH_2 + ",2月",
		GlobalConstant.MONTH_3 + ",3月",
		GlobalConstant.MONTH_4 + ",4月",
		GlobalConstant.MONTH_5 + ",5月",
		GlobalConstant.MONTH_6 + ",6月",
		GlobalConstant.MONTH_7 + ",7月",
		GlobalConstant.MONTH_8 + ",8月",
		GlobalConstant.MONTH_9 + ",9月",
		GlobalConstant.MONTH_10 + ",10月",
		GlobalConstant.MONTH_11 + ",11月",
		GlobalConstant.MONTH_12 + ",12月"
		
		};
	
	static public List constantSeasonOptions() {
		return generateSelectOptions(constantSeasonTypes, null, null);
	}
	static public String[] constantSeasonTypes = {
		GlobalConstant.SEASON_1 + ",Q1",
		GlobalConstant.SEASON_2 + ",Q2",
		GlobalConstant.SEASON_3 + ",Q3",
		GlobalConstant.SEASON_4 + ",Q4"
		
		};

	static public List violationYearOptions() {
		return generateSelectOptions(violationYearTypes, null, null);
	}
	static public String[] violationYearTypes = {
		GlobalConstant.BIGGER_VIOLATION_YEAR + ",今年",
		GlobalConstant.LESS_VIOLATION_YEAR + ",去年"		
		};
	
	
	
	/**违规模块----------end-----------------*/
	
	/**报备审批
	 * 
	 */
	static public String[] filingProposalTypeArray = {
		GlobalConstant.FILING_PROPOSAL_OUT_TYPE + ",市场活动",
		GlobalConstant.FILING_PROPOSAL_REPAIR_TYPE + ",事故维修"
	};
	static public List filingProposalOptions() {
		return generateSelectOptions(filingProposalTypeArray);
	}
	static public String[] filingProposalStatusArray = {
		GlobalConstant.FILING_PROPOSAL_STATUS_INIT + ",待审核 ",
		GlobalConstant.FILING_PROPOSAL_STATUS_YES + ",同意",
		GlobalConstant.FILING_PROPOSAL_STATUS_NO + ",拒绝",
		GlobalConstant.FILING_PROPOSAL_STATUS_CANCEL + ",取消"
	};
}