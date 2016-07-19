package com.ava.dealer.service;

import java.util.List;

import com.ava.domain.entity.CarStyle;

public interface ICompanyCarStyleRelationService {
	
	/**
	 * 根据经销商ID获得经销商可安装的车型列表
	 * @param companyId
	 * @param seriesId TODO
	 * @return
	 */
   
	public List<CarStyle> findByCompanyId(Integer companyId, Integer seriesId);
	/**
	 * 根据经销商ID和车辆ID获得经销商可换装的车型列表
	 * @param companyId
	 * @param vehicleId
	 * @return
	 */
   public List<CarStyle> findReplaceCarStyle(Integer companyId, Integer vehicleId);
	
}
