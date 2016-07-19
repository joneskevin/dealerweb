package com.ava.dealer.service;

import java.util.List;
import java.util.Map;

import com.ava.domain.vo.TestDriveRateVO;
import com.ava.domain.vo.VehicleConfigCount;
import com.ava.resource.SelectOption;

public interface IReportManagerService {
	
	public Map<String,List<SelectOption>> getVehicleConfigCountInit(Integer companyId);
	public List getVehicleConfigCountByType(String regionId);
	public List getVehicleConfigCountByRegion(String regionId);
}
