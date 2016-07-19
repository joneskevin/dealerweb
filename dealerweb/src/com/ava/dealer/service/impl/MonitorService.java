package com.ava.dealer.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.ava.base.dao.util.ParameterUtil;
import com.ava.base.domain.ResponseData;
import com.ava.dao.IMonitorLocationDao;
import com.ava.dao.IOrgDao;
import com.ava.dao.IUserRoleRelationDao;
import com.ava.dao.IVehicleDao;
import com.ava.dealer.service.IMonitorService;
import com.ava.domain.entity.Company;
import com.ava.domain.entity.Location;
import com.ava.domain.entity.MonitorLocation;
import com.ava.domain.entity.Org;
import com.ava.domain.entity.Vehicle;
import com.ava.domain.vo.Tree;
import com.ava.domain.vo.VehicleMonitorVO;
import com.ava.resource.GlobalConstant;
import com.ava.resource.cache.CacheCompanyManager;
import com.ava.resource.cache.CacheOrgManager;
import com.ava.resource.cache.CacheOrgVehicleManager;
import com.ava.resource.cache.CacheTestDriveManager;
import com.ava.util.MyBeanUtils;

@Service
public class MonitorService implements IMonitorService {
	
	@Autowired
	private IVehicleDao vehicleDao;
	
	@Autowired
	private IMonitorLocationDao monitorLocationDao;
	
	@Autowired
	private IOrgDao orgDao;
	
	@Autowired
	private IUserRoleRelationDao userRoleRelationDao;
	
	public ResponseData displayHome(Integer userId, Integer companyId) {
		ResponseData rd = new ResponseData(0);
		String newXML = CacheOrgVehicleManager.getChildrenOrgTree4Xml(null, companyId);
		
		rd.put("dhtmlXtreeXML", newXML);
		return rd;
	}

	public ResponseData findVehicle(Integer companyId, Integer orgId, String plateNumber, String vehicleIds) {
		ResponseData rd = new ResponseData(0);
		String additionalCondition = "";
		HashMap<Object, Object> parameters = new HashMap<Object, Object>();
		if (vehicleIds != null && vehicleIds.length() > 0) {//车辆id集合有优先考虑
			additionalCondition += " and id in(" + vehicleIds + ")";
		} else {//在没有车辆id集合参数的情况下，考虑其他搜索条件
			Org org = CacheOrgManager.get(orgId);
			if (org != null) {
				additionalCondition += " and companyId in("+ CacheOrgManager.getChildrenOrgIdsStr(orgId, GlobalConstant.TRUE) + ")";
			} else {
				additionalCondition += " and companyId in("+ CacheOrgManager.getChildrenOrgIdsStr(companyId, GlobalConstant.TRUE) + ")";
			}
			ParameterUtil.appendLikeQueryCondition(parameters, "plateNumber", plateNumber);
		}

		additionalCondition = additionalCondition + " order by id desc";
		List vehicleList = vehicleDao.find(parameters, additionalCondition);
		
		List<VehicleMonitorVO> vehicleVOList = new ArrayList<VehicleMonitorVO>();
        if(vehicleList != null && vehicleList.size() > 0){
        	for(int i = 0; i<vehicleList.size(); i++ ){
        		Vehicle vehicle = (Vehicle) vehicleList.get(i);
        		VehicleMonitorVO vehicleVO = new VehicleMonitorVO(vehicle);
        		MonitorLocation monitorLocation = monitorLocationDao.getLastByVehicleId(vehicle.getId());
        		vehicleVO.setCurrentLocation(monitorLocation);
        		vehicleVOList.add(vehicleVO);
        	}
        }
		
		rd.setFirstItem(vehicleVOList);
        rd.setCode(1);
		return rd;
	}
	
	public ResponseData getVehicle(Integer vehicleId){
		ResponseData rd = new ResponseData(GlobalConstant.TRUE);
		if (vehicleId != null) {
			Vehicle vehicle = vehicleDao.get(vehicleId);
			VehicleMonitorVO vehicleVO = new VehicleMonitorVO(vehicle);
		
			if (vehicle != null) {
				MonitorLocation monitorLocation = monitorLocationDao.getLastByVehicleId(vehicle.getId());
        		vehicleVO.setCurrentLocation(monitorLocation);
				
				rd.setFirstItem(vehicleVO);

			}
			
		}
		
		return rd;
	}
	
	@Override
	public ResponseData findTree(Tree tree, Integer companyId, Integer userId) {
		ResponseData rd = new ResponseData(GlobalConstant.TRUE);
		
		Integer saleCenterId = 0;//分销中心ID
		Integer dealerId = 0;//经销商ID
		String icon = "../../js/ztree/css/zTreeStyle/img/diy/blue.gif";
		
		if (userId != null && userRoleRelationDao.getMaxRoleId(userId) == GlobalConstant.DISTRIBUTION_CENTER_ADMIN_ROLE_ID) {
			saleCenterId = companyId;
		}
		if (userId != null && userRoleRelationDao.getMaxRoleId(userId) == GlobalConstant.DEFAULT_DEALER_ROLE_ID) {
			Org org = CacheOrgManager.get(companyId);
			saleCenterId = org.getParentId();
			dealerId = companyId;
		}
		
		List<Tree> treeList = new ArrayList<Tree>();
		Map<String, Object> attributes = tree.getAttributes();
		Integer id = Integer.valueOf(tree.getId());
		String leafType = (String) attributes.get("leafType");
		
		HashMap<Object, Object> parameters = new HashMap<Object, Object>();
		if ("saleCenter".equals(leafType)){
			parameters.clear();
			parameters.put("parentId", id);
			parameters.put("levelId", GlobalConstant.ORG_LEVEL_ID_SALE_CENTER);
			if (saleCenterId > 0) {
				parameters.put("id", saleCenterId);
			}
			List<Org> orgList = orgDao.find(parameters, "");
			if (orgList != null && orgList.size() > 0) {
				for(Org org : orgList){
					Integer secondSaleCenterId = org.getId();
					Tree t = new Tree(secondSaleCenterId, org.getName(), icon, true);
					//查询分校中心下是否有经销商
					parameters.clear();
					parameters.put("parentId", secondSaleCenterId);
					List<Org> dealerList = orgDao.find(parameters, "");
					if(dealerList != null && dealerList.size() > 0){
						t.setIsParent(true);
						t.addAttribute("leafType", "dealer");
					}
					treeList.add(t);
				}
			}
		} else if ("dealer".equals(leafType)) {
			parameters.clear();
			parameters.put("parentId", id);
			parameters.put("levelId", GlobalConstant.ORG_LEVEL_ID_DEALER);
			if (dealerId > 0) {
				parameters.put("id", dealerId);
			}
			List<Org> orgList = orgDao.find(parameters, "");
			if (orgList != null && orgList.size() > 0) {
				for(Org org : orgList){
					Integer secondDealerId = org.getId();
					Tree t = new Tree(secondDealerId, org.getName(), icon, false);
					//查询经销商下是否有车辆
					parameters.clear();
					parameters.put("companyId", secondDealerId);
					
					/* 查询该经销商下是否有车辆*/
					parameters.put("configureStatus", GlobalConstant.CONFIGURE_STATUS_INSTALLED);
					parameters.put("obdFlag", GlobalConstant.TRUE);
					List<Vehicle> vehicleList = vehicleDao.find(parameters, "");
					if(vehicleList != null && vehicleList.size() > 0){
						t.setIsParent(true);
						t.addAttribute("leafType", "vehicle");
					}
					
					treeList.add(t);
				} 
			}
		} else if ("vehicle".equals(leafType)) {
			Company company = CacheCompanyManager.getCompany(id);
			//如果车辆位置没查询出来，将车辆位置设置成经销商位置
			MonitorLocation monitorLocation = null;
			if (company != null) {
				if (company.getBaiduLat() != null && company.getBaiduLat() != 0 
						&& company.getBaiduLng() != null && company.getBaiduLng() != 0) {
					monitorLocation = new MonitorLocation();
					monitorLocation.setLatitude(company.getBaiduLat().toString());
					monitorLocation.setLongitude(company.getBaiduLng().toString());
					monitorLocation.setSpeed(0);
				}
			}
			parameters.clear();
			parameters.put("companyId", id);
			parameters.put("configureStatus", GlobalConstant.CONFIGURE_STATUS_INSTALLED);
			parameters.put("obdFlag", GlobalConstant.TRUE);
			List<Vehicle> vehicleList = vehicleDao.find(parameters, "");
			if(vehicleList != null && vehicleList.size() > 0){
				for(Vehicle vehicle : vehicleList){
					Tree t = new Tree(vehicle.getId(), vehicle.getPlateNumber(), icon, false);
					if (monitorLocation != null) {
						VehicleMonitorVO vehicleMonitor = new VehicleMonitorVO(vehicle);
						t.setVehicleMonitor(vehicleMonitor);
						vehicleMonitor.setCurrentLocation(monitorLocation);
					}
					treeList.add(t);
				}
			}
		}
		rd.put("treeList", treeList);
		return rd;
	}

	@Override
	public ResponseData getVehicleByDealerId(Integer companyId) {
		ResponseData rd = new ResponseData(GlobalConstant.TRUE);
		List<VehicleMonitorVO> vehicleMonitorList = new ArrayList<VehicleMonitorVO>();
		if (companyId != null) {
			HashMap<Object, Object> parameters = new HashMap<Object, Object>();
			parameters.put("companyId", companyId);
			parameters.put("configureStatus", GlobalConstant.CONFIGURE_STATUS_INSTALLED);
			parameters.put("obdFlag", GlobalConstant.TRUE);
			List<Vehicle> vehicleList = vehicleDao.find(parameters, "");
			if (vehicleList != null && vehicleList.size() > 0) {
				for (Vehicle vehicle : vehicleList) {
					 VehicleMonitorVO vehicleVO = new VehicleMonitorVO(vehicle);
					 if (vehicle != null) {
						MonitorLocation monitorLocation = monitorLocationDao.getLastByVehicleId(vehicle.getId());
				        vehicleVO.setCurrentLocation(monitorLocation);
						vehicleMonitorList.add(vehicleVO);
					}
				}
			}
		
		}
		rd.put("vehicleMonitorList", vehicleMonitorList);
		return rd;	
		
	}
	
	/**
	 * 更新车辆实时位置表
	 */
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public void saveOrUpdateMonitorLocation(Location location){
		//1.检查输入参数是否合法
		if(null == location || location.getVehicleId() == null){
			return;
		}
		//2.查询监听车辆当前位置信息
		MonitorLocation monitorLocation =  monitorLocationDao.getLastByVehicleId(location.getVehicleId());
		
		//3.根据传入的位置对象，更新或新增车辆位置信息
		if(null == monitorLocation){//没有记录，则新增
			monitorLocation = new MonitorLocation();
			MyBeanUtils.copyPropertiesContainsDate(monitorLocation, location);
			monitorLocation.setId(null);
			monitorLocationDao.save(monitorLocation);
		}else{//存在已有数据信息
			Integer id = monitorLocation.getId();
			MyBeanUtils.copyPropertiesContainsDate(monitorLocation, location);
			monitorLocation.setId(id);
			monitorLocationDao.update(monitorLocation);
		}
		//4.更新缓存信息
		CacheTestDriveManager.putMonitorLocation(location.getVehicleId(), monitorLocation);		
	}
}
