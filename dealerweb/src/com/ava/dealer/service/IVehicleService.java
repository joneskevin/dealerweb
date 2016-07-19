package com.ava.dealer.service;

import com.ava.base.dao.hibernate.TransMsg;
import com.ava.base.domain.ResponseData;
import com.ava.domain.entity.Vehicle;
import com.ava.domain.vo.VehicleVO;

public interface IVehicleService {

	/**
	 * 车辆更新-车辆新增
	 * @author liuq 
	 * @version 0.1 
	 * @param vehicle
	 * @param companyId
	 * @return
	 */
	public ResponseData addVehicle(Vehicle vehicle, Integer companyId);
	
	/**
	 * 车辆更新-编辑车辆
	 * @author liuq 
	 * @version 0.1 
	 * @param vehicle
	 * @return
	 */
	public ResponseData editVehicle(Vehicle vehicle);
	
	/**
	 * 车辆维护-车辆新增
	 * @author liuq 
	 * @version 0.1 
	 * @param vehicle
	 * @return
	 */
	public ResponseData displayAddMaintainVehicle(VehicleVO vehicle);
	
	/**
	 * 车辆维护-车辆新增
	 * @author liuq 
	 * @version 0.1 
	 * @param vehicle
	 * @return
	 */
	public ResponseData addMaintainVehicle(VehicleVO vehicle);

	/**
	 * 车辆维护-显示编辑车辆页面
	 * @param proposalId
	 * @return
	 */
	public ResponseData displayEditMaintainVehicle(Integer vehicleId);
	
	/**
	 * 车辆维护-编辑车辆
	 * @author liuq 
	 * @version 0.1 
	 * @param vehicle
	 * @return
	 */
	public ResponseData editMaintainVehicle(VehicleVO vehicle);
	
	/**
	 * 历史车辆-恢复车辆
	 * @author liuq 
	 * @version 0.1 
	 * @param vehicle
	 * @return
	 */
	public ResponseData regainVehcile(VehicleVO vehicle);

	/**
	 * 查询车辆列表 ，其中companyId是当前登录者所属的组织ID - 不启用
	 * @author liuq 
	 * @version 0.1 
	 * @param transMsg
	 * @param vehicle
	 * @param companyId
	 * @param currentUserId
	 * @param dealerCode
	 * @param isExport
	 * @return
	 */
	public ResponseData listVehicleOld(TransMsg transMsg, Vehicle vehicle, Integer companyId, Integer currentUserId, String dealerCode, boolean isExport);
	
	/**
	 * 查询车辆列表
	 * @author liuq 
	 * @version 0.1 
	 * @param transMsg
	 * @param vehicleVO
	 * @param companyId
	 * @param currentUserId
	 * @param isExport
	 * @return
	 */
	public ResponseData listVehicle(TransMsg transMsg, VehicleVO vehicleVO, Integer companyId, Integer currentUserId, boolean isExport);
	
	/**
	 * 查询历史车辆列表
	 * @author liuq 
	 * @version 0.1 
	 * @param transMsg
	 * @param vehicleVO
	 * @param currentCompanyId
	 * @param currentUserId
	 * @param isExport
	 * @return
	 */
	public ResponseData listHistoryVehicle(TransMsg transMsg, VehicleVO vehicleVO, Integer currentCompanyId, Integer currentUserId, boolean isExport);
	
  /**
	* Description: 车辆维护列表
	* @author henggh 
	* @version 0.1 
	* @param transMsg
	* @param vehicleVO
	* @param currentCompanyId
	* @param currentUserId
	* @param isExport
	* @return
	 */
	public ResponseData listMaintainVehicle(TransMsg transMsg, VehicleVO vehicleVO, Integer currentCompanyId, Integer currentUserId, boolean isExport);
	
	/**
	 * Description: 车辆OBD监控列表
	 * @author henggh 
	 * @version 0.1 
	 * @param transMsg
	 * @param vehicleVO
	 * @param currentCompanyId
	 * @param currentUserId
	 * @param isExport
	 * @return
	 */
	public ResponseData listMonitorVehicleObd(TransMsg transMsg, VehicleVO vehicleVO, Integer currentCompanyId, Integer currentUserId, boolean isExport);
	
	/**
	 * 查询车辆返利
	 * @author liuq 
	 * @version 0.1 
	 * @param transMsg
	 * @param vehicleVO
	 * @param companyId
	 * @param currentUserId
	 * @param isExport
	 * @return
	 */
	public ResponseData listVehicleRebate(TransMsg transMsg, VehicleVO vehicleVO, Integer companyId, Integer currentUserId, boolean isExport);
	
	public Vehicle getVehicle(Integer vehicleId);
	
	public Vehicle getByVin(String vin);
	
	public VehicleVO getVehcileByVin(String vin);
	
	public VehicleVO getByPalteNumber(String plateNumber);

	public ResponseData listVehicleForOutput(TransMsg transMsg,
			Vehicle vehicle, Integer companyId, Integer dealerCode);
	
	public ResponseData getVehicleList(Integer currentCompanyId);
	
	/**
	 * 车辆插入、拔除OBD处理接口
	 * @param type 1:绑定 0:解绑
	 * @param vin 
	 * @param uniqueId 设备号
	 * @param simId SIM卡ID
	 */
	public void handleVehicleBox(Integer type, String vin ,String uniqueId, String simId);
	
	public boolean isExistence(Integer vehicleId, String field, String value, String additionalCondition);
	
}
