package com.ava.dealer.service;

import java.util.List;

import com.ava.base.dao.hibernate.TransMsg;
import com.ava.base.domain.ResponseData;
import com.ava.domain.entity.CarSeries;
import com.ava.domain.entity.CarStyle;
import com.ava.domain.entity.DesignateOrder;
import com.ava.domain.entity.VehicleInstallationPlan;
import com.ava.domain.vo.DesignateOrderExcelVO;
import com.ava.domain.vo.VehicleInstallationPlanVO;

public interface IVehicleInstallationPlanService {

	/**
	 * 获得待拆除的车型列表
	 * @author liuq 
	 * @version 0.1 
	 * @return
	 */
	public List<CarStyle> getDemolitionCarStyleList();
	
	public List<CarSeries> getCarSeriesList();
	
	public ResponseData listDesignateOrder(TransMsg transMsg, DesignateOrder designateOrder, boolean isExport);
	
	public ResponseData listVehicleInstallationPlan(TransMsg transMsg, VehicleInstallationPlanVO vehicleInstallationPlanVO, boolean isExport);
	
	public ResponseData addInstallationPlan(VehicleInstallationPlan vehicleInstallationPlan);
	
	public ResponseData listCarSeries(TransMsg transMsg, CarSeries carSeries, boolean isExport);
	
	public ResponseData addCarSeries(CarSeries carSeries);

	public ResponseData displayEditCarSeries(Integer carSeriesId);
	
	public ResponseData editCarSeries(CarSeries carSeries);
	
	public ResponseData deleteCarSeries(Integer carSeriesId);
	
	/**
	 * 【启用】换装/拆除计划
	 * @author liuq 
	 * @version 0.1 
	 * @param installationPlanId
	 * @return
	 */
	public ResponseData enableInstallationPlan(Integer installationPlanId);
	
	/**
	 * 【停用】换装/拆除计划
	 * @author liuq 
	 * @version 0.1 
	 * @param installationPlanId
	 * @return
	 */
	public ResponseData disableInstallationPlan(Integer installationPlanId);
	
	/**
	 * 【删除】换装/拆除计划
	 * @author liuq 
	 * @version 0.1 
	 * @param installationPlanId
	 * @return
	 */
	public ResponseData deleteInstallationPlan(Integer installationPlanId);
	
	public ResponseData listCarStyle(TransMsg transMsg, CarStyle carStyle, boolean isExport);
	
	public ResponseData addCarStyle(CarStyle carStyle);

	public ResponseData displayEditCarStyle(Integer carStyleId);
	
	public ResponseData editCarStyle(CarStyle carStyle);
	
	public ResponseData deleteCarStyle(Integer carStyleId);
	
	public ResponseData deleteDesignateOrder(Integer designateOrderId);
	
	/**
	 * 上传指派订单
	 * @author liuq 
	 * @version 0.1 
	 * @param designateOrderExcelList
	 * @return
	 */
	public ResponseData upLoadDesignateOrder(List<DesignateOrderExcelVO> designateOrderExcelList);
}
