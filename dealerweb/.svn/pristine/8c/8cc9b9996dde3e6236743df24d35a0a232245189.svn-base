package com.ava.dealer.service;

import com.ava.base.domain.ResponseData;

public interface ITrackService {
	public ResponseData displayPlayTrack(String plateNumber);
	
	/**
	 * 查看轨迹
	 * @param companyId
	 * @param userId
	 * @param plateNumber
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public ResponseData displayHome(Integer companyId, Integer userId, String plateNumber, String startTime, String endTime);

	/** 查询位置列表 ，其中companyId是当前登录者所属的组织ID*/
	public ResponseData findVehicle(String plateNumber);

	/**
	 * 轨迹播放
	 * @author liuq 
	 * @version 0.1 
	 * @param companyId
	 * @param plateNumber
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public ResponseData findLocation(Integer companyId, String plateNumber, Long startTime, Long endTime);
	
	/**
	 * 通过车牌查询经销商的围栏信息
	 * @param vehicleId
	 * @return
	 */
	public ResponseData findDriveLine(String plateNumber);
	
}
