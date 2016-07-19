package com.ava.dealer.service;

import com.ava.base.domain.ResponseData;
import com.ava.domain.entity.Location;
import com.ava.domain.vo.Tree;

public interface IMonitorService {
	/** 查询车辆列表 */
	public ResponseData displayHome(Integer userId,Integer companyId);

	/** 查询车辆列表 ，其中companyId是当前登录者所属的组织ID*/
	public ResponseData findVehicle(Integer companyId, Integer orgId, String plateNumber, String vehicleIds);

	public ResponseData getVehicle(Integer vehicleId);
	

	/**
	 * 根据经销商ID获得车辆位置信息
	 * @param companyId
	 * @return
	 */
	public ResponseData getVehicleByDealerId(Integer companyId);
	
	/**
	 * 查询下级组织、包括经销商下挂的车
	 * @param tree
	 * @param companyId TODO
	 * @param userId TODO
	 * @return
	 */
	public ResponseData findTree(Tree tree, Integer companyId, Integer userId);
	
	/**
	 * 更新车辆实时位置表
	 * @param location
	 */
	public void saveOrUpdateMonitorLocation(Location location);
}
