package com.ava.dealer.service;

import java.util.List;

import com.ava.base.dao.hibernate.TransMsg;
import com.ava.base.domain.ResponseData;
import com.ava.domain.entity.DriveLine;
import com.ava.domain.vo.DriveLineVO;
import com.ava.domain.vo.DriveLineWithFenceVO;
import com.ava.domain.vo.DriveLineWithPolygonVO;

public interface IDriveLineService {
	/**
	 * 新增线路
	 * @author liuq 
	 * @version 0.1 
	 * @param driveLine
	 * @return
	 */
	public ResponseData addDriveLine(DriveLine driveLine);
	
	/**更新半圈检测点*/
	public void updateSemcycle(DriveLineVO driveLine);

	/** 删除线路 ，其中companyId是当前登录者所属的组织ID*/
	public ResponseData deleteDriveLine(Integer driveLineId);

	/**
	 * 编辑线路
	 * @author liuq 
	 * @version 0.1 
	 * @param driveLine
	 * @return
	 */
	public ResponseData editDriveLine(DriveLine driveLine);

	/**
	 * 设置围栏
	 * @author liuq 
	 * @version 0.1 
	 * @param driveLine
	 * @return
	 */
	public ResponseData setFence(DriveLineVO driveLine);
	
	/**
	 * 设置参考线路
	 * @author liuq 
	 * @version 0.1 
	 * @param driveLine
	 * @return
	 */
	public ResponseData setReferenceLine(DriveLineVO driveLine);
	
	public DriveLineVO getDriveLine(Integer driveLineId);
	
	/**
	 * 获得参考线路
	 * @param driveLineId
	 * @return
	 */
	public DriveLineVO getReferenceLine(Integer driveLineId);
	
	public ResponseData displayHome(Integer companyId, Integer userId);

	/** 查询线路列表 ，其中companyId是当前登录者所属的组织ID
	 * @param userId 
	 * @param isExport */
	public ResponseData listDriveLine(TransMsg transMsg, DriveLineVO driveLineVO, Integer companyId, Integer userId, Boolean isExport);
	
	/***根据车辆ID获取线路列表*/
	public List<DriveLineWithFenceVO> getDriveLineListByVehicleId(Integer vehicleId);
	
	/**获取判断违规越界所需围栏数据格式 */
	public DriveLineWithPolygonVO getDriveLineWithPolygonListByDealerId(
			Integer dealerId);
	public DriveLineWithPolygonVO getDriveLineWithPolygonListByDealerIdNew(
			Integer dealerId);
	
	/**
	 * 初始化线路里程[批处理]
	 * @author liuq 
	 * @version 0.1
	 */
	public void initDriveLineMileage();
}
