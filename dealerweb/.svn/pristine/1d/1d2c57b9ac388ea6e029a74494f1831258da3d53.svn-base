package com.ava.dealer.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ava.base.controller.Base4MvcController;
import com.ava.base.domain.ResponseData;
import com.ava.baseSystem.service.IOrgService;
import com.ava.baseSystem.service.IUserMenuService;
import com.ava.dealer.service.IReportManagerService;
import com.ava.domain.entity.Org;
import com.ava.domain.vo.TestDriveRateVO;
import com.ava.resource.GlobalConstant;
import com.ava.resource.SelectOption;
import com.ava.resource.SessionManager;
import com.ava.resource.cache.CacheOrgManager;

@Controller
@RequestMapping("/dealer/reportManager")
public class ReportManagerController extends Base4MvcController {
	
	@Resource(name = "dealer.reportManagerService")
	private IReportManagerService reportManagerService;
	
	@Resource()
	private IOrgService orgService;

	@Autowired
	IUserMenuService userMenuService;
	
	/**
	 * 车辆配置统计(按车型)
	 * @return
	 */
    @RequestMapping(value="/byVehicleTypeInit", method = RequestMethod.GET) 
	public String getVehicleConfigCountByTypeInit(){
    	
    	Integer companyId = SessionManager.getCurrentCompanyId(getRequest());
    	Map<String,List<SelectOption>> map=reportManagerService.getVehicleConfigCountInit(companyId);
    	setInitParam(map);
    	setPageOpenType();
		return "/dealer/chartManager/vehicleConfigByType";
	}
        
    /**
     * 车辆配置统计(按分区)
     * @return
     */
    @RequestMapping(value="/byVehicleRegionInit", method = RequestMethod.GET) 
	public String getVehicleConfigCountByRegionInit(){
    	Integer companyId = SessionManager.getCurrentCompanyId(getRequest());
    	Map<String,List<SelectOption>> map=reportManagerService.getVehicleConfigCountInit(companyId);
    	setInitParam(map);
    	setPageOpenType();
		return "/dealer/chartManager/vehicleConfigByRegion";
	}
    	
    /**
     * 车辆配置统计（分车型）
     * @param regionId
     */
    @RequestMapping(value="/byVehicleType", method = RequestMethod.POST) 
	public void getVehicleConfigCountByType(String regionId){
    	
		ResponseData rd = new ResponseData(0);
		
		List vehicleConfigCount=reportManagerService.getVehicleConfigCountByType(regionId);
		
		Map mapData=new HashMap();
		if(null!=vehicleConfigCount && !vehicleConfigCount.isEmpty()){
			if(null!=vehicleConfigCount.get(0))
				mapData.put("vehicleTpyeNameData", vehicleConfigCount.get(0));
			if(null!=vehicleConfigCount.get(1)){
				mapData.put("vehicleConfigData", vehicleConfigCount.get(1));
			}
		}		
		rd.setData(mapData);
		writeRd(rd);
	}
	
    /**
     * 车辆配置统计（分区）
     * @param regionId
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value="/byVehicleRegion", method = RequestMethod.POST) 
	public void getVehicleConfigCountByRegion(String regionId){
		ResponseData rd = new ResponseData(0);
		
		List vehicleConfigCount=reportManagerService.getVehicleConfigCountByRegion(regionId);
		
		Map mapData=new HashMap();
		if(null!=vehicleConfigCount && !vehicleConfigCount.isEmpty()){
			if(null!=vehicleConfigCount.get(0))
				mapData.put("vehicleTpyeNameData", vehicleConfigCount.get(0));
			if(null!=vehicleConfigCount.get(1)){
				mapData.put("vehicleConfigData", vehicleConfigCount.get(1));
			}
		}		
		rd.setData(mapData);
		writeRd(rd);
	}
    
    
    /**
     * 设置从主页面和左边菜单打开方式
     */
    private void setPageOpenType(){
		ResponseData rd = userMenuService.siteMenuType(getRequest());
		getRequest().setAttribute("menuId", rd.get("menuId"));
		getRequest().setAttribute("menuType", rd.get("menuType"));
		getRequest().setAttribute("userMenuRelationVOList", rd.get("userMenuRelationVOList"));
    }
   
    private void setInitParam(Map<String,List<SelectOption>> map){
    	if(null!=map && map.size()>=1){
    		for(Map.Entry<String, List<SelectOption>> entry: map.entrySet()) {
    			 getRequest().setAttribute(entry.getKey(), entry.getValue());
    		}
    	}
    }
}
