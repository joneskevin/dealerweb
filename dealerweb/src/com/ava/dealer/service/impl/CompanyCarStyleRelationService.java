package com.ava.dealer.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ava.dao.ICompanyCarStyleRelationDao;
import com.ava.dao.IVehicleDao;
import com.ava.dealer.service.ICompanyCarStyleRelationService;
import com.ava.domain.entity.CarStyle;
import com.ava.domain.entity.CompanyCarStyleRelation;
import com.ava.domain.entity.Vehicle;
import com.ava.resource.GlobalConstant;
import com.ava.resource.cache.CacheCarManager;

@Service
public class CompanyCarStyleRelationService implements ICompanyCarStyleRelationService {

	@Autowired
	IVehicleDao vehicleDao;
	
	@Autowired
	ICompanyCarStyleRelationDao companyCarStyleRelationDao;
	
	@Override
	public List<CarStyle> findByCompanyId(Integer companyId, Integer seriesId) {
		List<CarStyle> carStyleList = new ArrayList<CarStyle>();
		if (companyId == null) {
			return carStyleList;
		}
		if (companyId == GlobalConstant.DEFAULT_GROUP_ORG_ID) {
			List<CarStyle> cacheCarStyleList = CacheCarManager.getAllCarStyleByBrandId(GlobalConstant.CAR_BRAND_VM);
			if (cacheCarStyleList != null && cacheCarStyleList.size() > 0) {
				for (CarStyle cachCarStyle : cacheCarStyleList) {
					CarStyle carStyle = new CarStyle();
					carStyle.setId(cachCarStyle.getId());
					carStyle.setName(cachCarStyle.getName() + " "+ cachCarStyle.getYearType());
					carStyleList.add(carStyle);
				}
			}
		} else {
			Map parameters = new HashMap();
			parameters.put("companyId", companyId);
			List<CompanyCarStyleRelation> companyCarStyleRelationList = companyCarStyleRelationDao.find(parameters, "order by carStyleId asc");
			if (companyCarStyleRelationList != null && companyCarStyleRelationList.size() > 0) {
				for (CompanyCarStyleRelation companyCarStyleRelation : companyCarStyleRelationList) {
					if (companyCarStyleRelation != null && companyCarStyleRelation.getCarStyleId() != null &&
							companyCarStyleRelation.getConfigType() != null ) {
						Integer carStyleId = companyCarStyleRelation.getCarStyleId();
						Integer configType = companyCarStyleRelation.getConfigType();
						if (configType == GlobalConstant.CONFIGURE_TYPE_NO) {
							continue;
						}
						
						CarStyle carStyle = new CarStyle();
						carStyle.setId(carStyleId);
						carStyle.setName(CacheCarManager.getCarStyleNameById(carStyleId));
						CarStyle currentCarStyle = CacheCarManager.getCarStyleById(carStyleId);
						if (seriesId != null) {
							if (seriesId == currentCarStyle.getSeriesId().intValue()) {
								carStyleList.add(carStyle);
							}
						} else {
							carStyleList.add(carStyle);
						}
					}
					
				}
			}
		}
		return carStyleList;
	}

	@Override
	public List<CarStyle> findReplaceCarStyle(Integer companyId, Integer vehicleId) {
		List<CarStyle> carStyleList = new ArrayList<CarStyle>();
		if (companyId == null || vehicleId == null) {
			return carStyleList;
		}
		
		Vehicle vehicle = vehicleDao.get(vehicleId);
		if (vehicle == null) {
			return carStyleList;
		}
		
		int carSeriesId = 0;//车系Id
		if (vehicle != null && vehicle.getCarStyleId() != null) {
			CarStyle vehicleCarStyle = CacheCarManager.getCarStyleById(vehicle.getCarStyleId());
			if (vehicleCarStyle != null) {
				carSeriesId = vehicleCarStyle.getSeriesId();
			}
		}
		
		Map parameters = new HashMap();
		parameters.put("companyId", companyId);
		List<CompanyCarStyleRelation> companyCarStyleRelationList = companyCarStyleRelationDao.find(parameters, "order by carStyleId asc");
		if (companyCarStyleRelationList != null && companyCarStyleRelationList.size() > 0) {
			for (CompanyCarStyleRelation companyCarStyleRelation : companyCarStyleRelationList) {
				if (companyCarStyleRelation != null && companyCarStyleRelation.getCarStyleId() != null &&
						companyCarStyleRelation.getConfigType() != null ) {
					Integer carStyleId = companyCarStyleRelation.getCarStyleId();
					Integer configType = companyCarStyleRelation.getConfigType();
					if (configType == GlobalConstant.CONFIGURE_TYPE_NO) {
						continue;
					}
					
					//根据车辆车型Id的车系Id查匹配配置表中的车型
					CarStyle carStyle = CacheCarManager.getCarStyleById(carStyleId);
					if (carStyle.getSeriesId() == carSeriesId) {
						CarStyle currentCarStyle = new CarStyle();
						currentCarStyle.setId(carStyleId);
						currentCarStyle.setName(CacheCarManager.getCarStyleNameById(carStyleId));
						carStyleList.add(currentCarStyle);
					}
					
				}
				
			}
		}
		return carStyleList;
	}
	
}
