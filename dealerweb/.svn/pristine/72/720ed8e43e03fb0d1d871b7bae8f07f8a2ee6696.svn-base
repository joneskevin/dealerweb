package com.ava.dealer.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ava.dao.ITestDriveRateDao;
import com.ava.dao.IVehicleConfigDao;
import com.ava.dealer.service.IReportManagerService;
import com.ava.domain.entity.CarStyle;
import com.ava.domain.entity.Org;
import com.ava.domain.vo.VehicleConfigCount;
import com.ava.domain.vo.VehicleConfigData;
import com.ava.resource.GlobalConstant;
import com.ava.resource.SelectOption;
import com.ava.resource.cache.CacheCarManager;
import com.ava.resource.cache.CacheOrgManager;
import com.ava.util.DateTime;

@Service("dealer.reportManagerService")
public class ReportManagerService implements IReportManagerService{
    @Resource(name = "dealer.vehicleConfigDao")
    IVehicleConfigDao vehicleConfigDao;
    
    @Autowired
    ITestDriveRateDao testDriveRateDao;

    /**
     * 
    * Description: 车辆配置统计(按车型、区域)
    * @author henggh 
    * @version 0.1 
    * @param companyId
    * @return
     */
    public Map<String,List<SelectOption>> getVehicleConfigCountInit(Integer companyId){
    	Map<String,List<SelectOption>> map=new HashMap<String,List<SelectOption>>();
    	List<SelectOption> list=getRegionOptionList(true,companyId);
    	map.put("selectRegionOptionList", list);
    	return map;
    }
    
    /**
     * 车辆配置统计（分车型）
     */
	@Override
	public List getVehicleConfigCountByType(String regionId) {
		
		if(null==regionId || "".equals(regionId.trim())){
			return null;
		}
		//String reportDate=DateTime.toNormalDate(DateTime.minusDays(new Date(), 1));
		String reportDate=DateTime.toNormalDate(new Date());
		if(GlobalConstant.DEFAULT_GROUP_ORG_ID==Integer.parseInt(regionId.trim()) 
				|| GlobalConstant.OPERATIONS_CENTER_ID.equals(regionId.trim()) 
				|| GlobalConstant.NETWORK_DEVELOPMENT_ID.equals(regionId.trim())){
			//reportDate=getMaxDate(null,1);//查询全国

			return getResultDataByCarType(null,1,reportDate);
		}else{
			//reportDate=getMaxDate(regionId,2);//查询大区
			return getResultDataByCarType(regionId,2,reportDate);
		}
	}

    /**
     * 车辆配置统计（分区）
     */
	@Override
	public List getVehicleConfigCountByRegion(String regionId) {
		if(null==regionId || "".equals(regionId.trim())){
			return null;
		}
		//String reportDate=DateTime.toNormalDate(DateTime.minusDays(new Date(), 1));
		String reportDate=DateTime.toNormalDate(new Date());
		if(GlobalConstant.DEFAULT_GROUP_ORG_ID==Integer.parseInt(regionId.trim()) 
				|| GlobalConstant.OPERATIONS_CENTER_ID.equals(regionId.trim()) 
				|| GlobalConstant.NETWORK_DEVELOPMENT_ID.equals(regionId.trim())){
			//reportDate=getMaxDate(null,1);
			return getResultDataByRegion(regionId,1,reportDate);
		}else{
			//reportDate=getMaxDate(regionId,2);
			return getResultDataByRegion(regionId,2,reportDate);
		}
	}
	
   
	
	
	
	/**
	 * 
	* Description: 根据区域选择车辆配置情况
	* @author henggh 
	* @version 0.1 
	* @param regionId
	* @param dataType
	* @param reportDate
	* @return
	 */
	public List getResultDataByRegion(String regionId,Integer dataType,String reportDate) {
		//DateTime.toNormalDate(DateTime.minusDays(new Date(), -1))
		List<VehicleConfigCount> vehicleConfigCounts=new ArrayList<VehicleConfigCount>();
		StringBuffer exeSql=new StringBuffer();
		
		if(1==dataType){ //全国
			exeSql.append("select max(trb.sale_center_name) regionName,");
			//要求配置 = 要求配置-已拆除
			exeSql.append("sum(trb.denand_config_count) - sum(trb.demolition_count) as requireCount, sum(trb.optional_config_count) as optionCount, ");
			//实际安装 = 已安装-已拆除
			exeSql.append("sum(trb.reality_config_count) - sum(trb.demolition_count) as installCount, ");
			//待安装 = 要求配置+可选配置-已安装-已拆除
			exeSql.append("sum(trb.denand_config_count) + sum(trb.optional_config_count) - sum(trb.reality_config_count) - sum(trb.demolition_count) as uninstallCount ");
			exeSql.append("from t_report_base trb where trb.extraction_date between date('").
			append(DateTime.toNormalDate(DateTime.addYears(DateTime.toDate(reportDate), -1))).
			append("') and date('").append(reportDate).append("') ");
			exeSql.append("group by trb.sale_center_id order by sale_center_id");
		}else{ //大区
			exeSql.append("select max(trb.province_name) regionName,");
			//要求配置 = 要求配置-已拆除
			exeSql.append("sum(trb.denand_config_count) - sum(trb.demolition_count) as requireCount, sum(trb.optional_config_count) as optionCount, ");
			//实际安装 = 已安装-已拆除
			exeSql.append("sum(trb.reality_config_count) - sum(trb.demolition_count) as installCount, ");
			//待安装 = 要求配置+可选配置-已安装-已拆除
			exeSql.append("sum(trb.denand_config_count) + sum(trb.optional_config_count) - sum(trb.reality_config_count) - sum(trb.demolition_count) as uninstallCount ");
			exeSql.append("from t_report_base trb where trb.extraction_date between date('").
			append(DateTime.toNormalDate(DateTime.addYears(DateTime.toDate(reportDate), -1))).
			append("') and date('").append(reportDate).append("') and trb.sale_center_id=").append(Integer.parseInt(regionId)).append(" ");
			exeSql.append("group by trb.province_id order by province_id");
		}
		
		List list=vehicleConfigDao.executeSQLQueryList(exeSql.toString());
		VehicleConfigCount vehicleConfigAllCount=null;
		if(null!=list && !list.isEmpty()){
			vehicleConfigAllCount=new VehicleConfigCount();
			for(int i=0;i<list.size();i++){
				VehicleConfigCount vehicleConfigCount=new VehicleConfigCount();
				Object[] obj=(Object[])list.get(i);
				if(null!=obj && obj.length>=5){
					vehicleConfigCount.setRegionName(null==obj[0] || null==obj[0].toString() ? "未知" : obj[0].toString());
					vehicleConfigCount.setRequireCount(getResultData(obj[1]));
					vehicleConfigCount.setOptionCount(getResultData(obj[2]));
					vehicleConfigCount.setInstallCount(getResultData(obj[3]));
					vehicleConfigCount.setUninstallCount(getResultData(obj[4]));
					vehicleConfigCounts.add(vehicleConfigCount);
					
					vehicleConfigAllCount.setRequireCount(null == vehicleConfigAllCount.getRequireCount() ? vehicleConfigCount.getRequireCount() : vehicleConfigAllCount.getRequireCount()+vehicleConfigCount.getRequireCount());
					vehicleConfigAllCount.setOptionCount(null == vehicleConfigAllCount.getOptionCount() ? vehicleConfigCount.getOptionCount() : vehicleConfigAllCount.getOptionCount()+vehicleConfigCount.getOptionCount());
					vehicleConfigAllCount.setInstallCount(null == vehicleConfigAllCount.getInstallCount() ? vehicleConfigCount.getInstallCount() : vehicleConfigAllCount.getInstallCount()+vehicleConfigCount.getInstallCount());
					vehicleConfigAllCount.setUninstallCount(null == vehicleConfigAllCount.getUninstallCount() ? vehicleConfigCount.getUninstallCount() : vehicleConfigAllCount.getUninstallCount()+vehicleConfigCount.getUninstallCount());
				}
			}
			if(1==dataType){
				vehicleConfigAllCount.setRegionName("全国");
			}else{
				String regionName=CacheOrgManager.getOrgName(Integer.parseInt(regionId));
				vehicleConfigAllCount.setRegionName(null==regionName || "".equals(regionName.trim()) ? "未知" : regionName.trim());
			}
			vehicleConfigCounts.add(0, vehicleConfigAllCount);
		}
		
		List dataList = getFinalResultData(vehicleConfigCounts,1);
		return dataList;
	}
	
	/**
	 * 
	* Description: 根据区域、车型选择车辆配置情况
	* @author henggh 
	* @version 0.1 
	* @param regionId
	* @param dataType
	* @param reportDate
	* @return
	 */
	public List getResultDataByCarType(String regionId,Integer dataType,String reportDate) {

		List<VehicleConfigCount> vehicleConfigCounts = new ArrayList<VehicleConfigCount>();
		StringBuffer exeSql = new StringBuffer();
		
		exeSql.append("select max(trb.car_style_name) carStyleName,");
		//要求配置 = 要求配置-已拆除
		exeSql.append("sum(trb.denand_config_count) - sum(trb.demolition_count) as requireCount, sum(trb.optional_config_count) as optionCount, ");
		//实际安装 = 已安装-已拆除
		exeSql.append("sum(trb.reality_config_count) - sum(trb.demolition_count) as installCount, ");
		//待安装 = 要求配置+可选配置-已安装-已拆除
		exeSql.append("sum(trb.denand_config_count) + sum(trb.optional_config_count) - sum(trb.reality_config_count) - sum(trb.demolition_count) as uninstallCount ");
		exeSql.append("from t_report_base trb ");
		exeSql.append("where trb.extraction_date between date('")
			  .append(DateTime.toNormalDate(DateTime.addYears(DateTime.toDate(reportDate), -1)))
			  .append("') and date('").append(reportDate).append("') ");
		if (2 == dataType) { // 分销中心查询
			exeSql.append("and trb.sale_center_id=")
					.append(Integer.parseInt(regionId)).append(" ");
		}
		exeSql.append("group by trb.car_style_id");
		List list = vehicleConfigDao.executeSQLQueryList(exeSql.toString());
		if (null != list && !list.isEmpty()) {
			for (int i = 0; i < list.size(); i++) {
				VehicleConfigCount vehicleConfigCount = new VehicleConfigCount();
				Object[] obj = (Object[]) list.get(i);
				if (null != obj && obj.length >= 5) {
					vehicleConfigCount.setCarTypeName(null == obj[0]|| null == obj[0].toString() ? "未知" : obj[0].toString());
					vehicleConfigCount.setRequireCount(getResultData(obj[1]));
					vehicleConfigCount.setOptionCount(getResultData(obj[2]));
					vehicleConfigCount.setInstallCount(getResultData(obj[3]));
					vehicleConfigCount.setUninstallCount(getResultData(obj[4]));
					vehicleConfigCounts.add(vehicleConfigCount);
				}
			}
		}
		List dataList = getFinalResultData(vehicleConfigCounts,2);
		return dataList;
	}
	
		
	/**
	 * 转换最终所需统计数据
	 * @param vehicleConfigCountList
	 * @return
	 */
	public List getFinalResultData(List<VehicleConfigCount> vehicleConfigCountList,Integer queryType){
		
		List<VehicleConfigData> vehicleConfigDataList=new ArrayList<VehicleConfigData>();
		
		List<List<?>> dataList=new ArrayList<List<?>>();
		
		List<String> vehicleTpyeNameList=new ArrayList<String>();

		List<Integer> vehicleRequireCount=new ArrayList<Integer>();
		List<Integer> vehicleOptionCount=new ArrayList<Integer>();
		List<Integer> vehicleInstallCount=new ArrayList<Integer>();
		List<Integer> vehicleUninstallCount=new ArrayList<Integer>();
		
		if(null!=vehicleConfigCountList && !vehicleConfigCountList.isEmpty()){
			for(VehicleConfigCount vehicleConfigCount:vehicleConfigCountList){
				if(1==queryType){//要区域名称
					vehicleTpyeNameList.add(vehicleConfigCount.getRegionName());
				}else{//要车型
					vehicleTpyeNameList.add(vehicleConfigCount.getCarTypeName());
				}
				vehicleRequireCount.add(vehicleConfigCount.getRequireCount());
				vehicleOptionCount.add(vehicleConfigCount.getOptionCount());
				vehicleInstallCount.add(vehicleConfigCount.getInstallCount());
				vehicleUninstallCount.add(vehicleConfigCount.getUninstallCount());
			}
			vehicleConfigDataList.add(getVehicleConfigData("要求配置","bar","配置",30,vehicleRequireCount));
			vehicleConfigDataList.add(getVehicleConfigData("可选配置","bar","配置",30,vehicleOptionCount));
			vehicleConfigDataList.add(getVehicleConfigData("实际安装","bar","安装",30,vehicleInstallCount));
			vehicleConfigDataList.add(getVehicleConfigData("待安装" ,"bar","安装",30,vehicleUninstallCount));
			
			dataList.add(vehicleTpyeNameList);
			dataList.add(vehicleConfigDataList);
		}
		return dataList;
	}
	
    /**
     * 设置区域下来框
     */
    private List<SelectOption> getRegionOptionList(boolean needParent, Integer companyId){
    	List<SelectOption>  selectOptionList=null;
    	
    	if(null!=companyId){
    		if(GlobalConstant.DEFAULT_GROUP_ORG_ID==companyId.intValue()
    				|| GlobalConstant.OPERATIONS_CENTER_ID.equals(String.valueOf(companyId)) 
        			||GlobalConstant.NETWORK_DEVELOPMENT_ID.equals(String.valueOf(companyId))){
            	selectOptionList= getRegionSelectOption(Integer.parseInt(GlobalConstant.NETWORK_DEVELOPMENT_ID),needParent);
        	}else {
        		Org org=CacheOrgManager.get(companyId);
        		if(null==selectOptionList){
        			selectOptionList=new ArrayList<SelectOption>();
        			SelectOption selectOption=new SelectOption();
        			selectOption.setOptionValue(org.getId().toString());
        			selectOption.setOptionText(org.getName());
            		selectOptionList.add(selectOption);
        		}
        	}
    	}
    	if(null==selectOptionList){
    		selectOptionList=new ArrayList<SelectOption>();
    	}
    	return selectOptionList;
    }
	
	private Integer getResultData(Object obj){
		return Integer.parseInt(null==obj || null==obj.toString() || "".equals(obj.toString()) ? "0" : obj.toString());
	}
	/**
	 * 返回全国及大区或分销中心
	 * @param orgId
	 * @return
	 */
	public List<SelectOption> getRegionSelectOption(Integer orgId,boolean needParent){
		
		List<SelectOption> selectOptionList=new ArrayList<SelectOption>();
		
		if(null==orgId ){
			return selectOptionList;
		}
		
		List<Org> orgList=new ArrayList<Org>();
		
		if(needParent){
			orgList=CacheOrgManager.getSelfAndNextChildOrg(orgId);
		}else{
			orgList=CacheOrgManager.getNextChildOrg(orgId);
		}
		if(null!=orgList && !orgList.isEmpty()){
			for(Org org:orgList){
				if(org.getId().intValue()== Integer.parseInt(GlobalConstant.NETWORK_DEVELOPMENT_ID) || org.getId().intValue()== Integer.parseInt(GlobalConstant.OPERATIONS_CENTER_ID)){
					selectOptionList.add(getSelectOption(org,"全国"));
				}else{
					selectOptionList.add(getSelectOption(org,null));
				}
			}
		}

		return selectOptionList;
	}	
	private SelectOption getSelectOption(Org selfOrg,String newName){
		SelectOption selectOption=new SelectOption();
		selectOption.setOptionValue(selfOrg.getId().toString());
		String optionName=selfOrg.getName();
		if(!(null==newName || "".equals(newName))){
			optionName=newName;
		}else if(null==optionName || "".equals(optionName)){
			optionName="未知";
		}
		selectOption.setOptionText(optionName);
		return selectOption;
	}
	
	public List<SelectOption> getCarStyleSelectOption(Integer brandId ){
		
		List<CarStyle> carStyleList=CacheCarManager.getAllCarStyleByBrandId(brandId);
		
		List<SelectOption> selectOptionList=new ArrayList<SelectOption>();
		
		if(null!=carStyleList && !carStyleList.isEmpty()){
			SelectOption selectOption=null;
			for(CarStyle carStyle:carStyleList){
				selectOption=new SelectOption();
				selectOption.setOptionValue(carStyle.getId().toString());
				selectOption.setOptionText(carStyle.getName());
				selectOptionList.add(selectOption);
			}
		}
		return selectOptionList;
	}
	
	public VehicleConfigData getVehicleConfigData(String name,String type,String stack,Integer barWidth,List<Integer> data){
		VehicleConfigData vehicleConfigData=new VehicleConfigData();
		vehicleConfigData.setName(name);
		vehicleConfigData.setType(type);
		vehicleConfigData.setStack(stack);
		//vehicleConfigData.setBarWidth(barWidth);
		vehicleConfigData.setData(data);
		return vehicleConfigData;
	}

}
