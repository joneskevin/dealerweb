package com.ava.dealer.service.impl;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ava.base.dao.util.ParameterUtil;
import com.ava.base.domain.ResponseData;
import com.ava.dao.ILocationDao;
import com.ava.dao.IVehicleDao;
import com.ava.dealer.service.ITrackService;
import com.ava.domain.entity.Company;
import com.ava.domain.entity.DriveLine;
import com.ava.domain.vo.DealerVO;
import com.ava.domain.vo.VehicleVO;
import com.ava.resource.GlobalConfig;
import com.ava.resource.GlobalConstant;
import com.ava.resource.cache.CacheCompanyManager;
import com.ava.resource.cache.CacheDriveLineManager;
import com.ava.resource.cache.CacheVehicleManager;

@Service
public class TrackService implements ITrackService {
	@Autowired
	private IVehicleDao vehicleDao;
	@Autowired
	private ILocationDao locationDao;
	@Resource(name="locationHistoryDao")
	private ILocationDao locationHistoryDao;

	private int TRACK_LOCATION_MAX = 1000;// 获取一次位置集合的最大数

	public ResponseData displayPlayTrack(String plateNumber) {
		ResponseData rd = new ResponseData(0);

		if (plateNumber != null && plateNumber.trim().length() > 0) {
			Integer vehicleId = null;
			List<Integer> vehicleIds = vehicleDao.findIdsByPlateNumber(plateNumber, "" + " order by id asc");
			if (vehicleIds == null || vehicleIds.size() == 0) {
				rd.setMessage("该车辆信息不存在；或登录会话失效(请重新登录后再试)");
				return rd;
			}else{
				vehicleId = vehicleIds.get(0);
				VehicleVO vehicle = new VehicleVO(vehicleDao.get(vehicleId));
				Company dealer = CacheCompanyManager.getCompany(vehicle.getCompanyId());
				vehicle.setDealer(new DealerVO(dealer));
				rd.put("vehicle", vehicle);
				
				List<DriveLine> driveLineList = CacheDriveLineManager.getDriveLinesByDealerId(vehicle.getCompanyId());
				rd.put("driveLines", driveLineList);
			}
		}

		return rd;
	}

	public ResponseData findVehicle(String plateNumber) {
		ResponseData rd = new ResponseData(0);

		String additionalCondition = "";
		
		HashMap<Object, Object> parameters = new HashMap<Object, Object>();
		if (StringUtils.isNotBlank(plateNumber)) {
			ParameterUtil.appendLikeQueryCondition(parameters, "plateNumber",plateNumber);
		}

		List vehicles = vehicleDao.find(parameters, additionalCondition + " order by id asc");

		rd.setFirstItem(vehicles);
		rd.setCode(1);
		return rd;
	}

	public ResponseData findLocation(Integer companyId, String plateNumber, Long startTime, Long endTime) {
		ResponseData rd = new ResponseData(GlobalConstant.TRUE);

		Integer vehicleId = null;
		List<Integer> vehicleIds = vehicleDao.findIdsByPlateNumber(plateNumber, " order by configureStatus asc");
		if (vehicleIds == null || vehicleIds.size() == 0) {
			rd.setMessage("该车辆信息不存在；或登录会话失效(请重新登录后再试)");
			return rd;
		} else {
			vehicleId = vehicleIds.get(0);
		}
		HashMap<Object, Object> parameters = new HashMap<Object, Object>();
		HashMap<Object, Object> historParameters = new HashMap<Object, Object>();

		parameters.put("vehicleId", vehicleId);
		historParameters.put("vehicleId", vehicleId);
		HashMap<String,Long> result = new HashMap<>();
		

		String addiCondition = " and longitude !=0 and latitude !=0 ";	
		addiCondition += " order by creationTime asc";
		List locationList = null;
		//拆分查询处理; 
		int isPart = partDate(startTime.toString(),endTime.toString(),result);
		if(isPart>=0){
			//需要从历史库开始查询
			if(isPart>0){
				//需要拆分查询...
				ParameterUtil.appendConfineQueryCondition(parameters, "creationTime", result.get("startDate"), endTime);
				ParameterUtil.appendConfineQueryCondition(historParameters, "creationTime", startTime, result.get("endDate"));
				//先从历史库查询出结果
				locationList = locationHistoryDao.find(historParameters, addiCondition, new Integer(TRACK_LOCATION_MAX));
				//从现有库中查询
				List tempLocationList = locationDao.find(parameters, addiCondition, new Integer(TRACK_LOCATION_MAX));
				//合并查询结果
				if(locationList!=null){
					if(tempLocationList!=null){
						locationList.addAll(tempLocationList);
					}
				}else{
					locationList = tempLocationList;
				}
			}else{
				//只需要从历史库查询
				ParameterUtil.appendConfineQueryCondition(historParameters, "creationTime", startTime, endTime);
				locationList = locationHistoryDao.find(historParameters, addiCondition);
			}
		}else{
			//不需要拆分查询
			ParameterUtil.appendConfineQueryCondition(parameters, "creationTime", startTime, endTime);
			locationList = locationDao.find(parameters, addiCondition);
		}
		
		if (locationList == null || locationList.size() == 0) {
			rd.setMessage("没有查询到位置记录");
		} else if (locationList.size() > TRACK_LOCATION_MAX) {
			locationList.remove(locationList.size() - 1);

			rd.setMessage("查询的位置记录数量超过" + TRACK_LOCATION_MAX + "了，目前只返回了" + TRACK_LOCATION_MAX + "条。");
			rd.setCode(2);
		} else {
			rd.setCode(1);
		}

		rd.setFirstItem(locationList);
		
		VehicleVO vehicle = new VehicleVO(CacheVehicleManager.getVehicleById(vehicleId));
		
		Company dealer = CacheCompanyManager.getCompany(vehicle.getCompanyId());
		vehicle.setDealer(new DealerVO(dealer));
		rd.put("vehicle", vehicle);
		
		List<DriveLine> driveLineList = CacheDriveLineManager.getDriveLinesByDealerId(vehicle.getCompanyId());
		rd.put("driveLines", driveLineList);
		return rd;
	}
	
	/**
	 * 计算查询时间
	 * @author tangqingsong
	 * @version 
	 * @param start
	 * @param end
	 * @param result
	 * @return
	 * 
	 */
	private int partDate(String start,String end,HashMap<String,Long> result){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		String locationKeepMonth = GlobalConfig.getString("location_keep_month");
		int keepMonth = Integer.valueOf(locationKeepMonth)-1;//现有库中保留月数
		try {
			Date curDate = new Date();//当前系统时间
			Calendar curCal = Calendar.getInstance();
			curCal.setTime(curDate);
			Date startDate = sdf.parse(start);
			Date endDate = sdf.parse(end);
			Calendar startCal = Calendar.getInstance();
			startCal.setTime(startDate);
			Calendar endCal = Calendar.getInstance();
			endCal.setTime(endDate);
			//1、判断当前查询段是否有跨历史库
			int yearDiffer = curCal.get(Calendar.YEAR) - startCal.get(Calendar.YEAR);//求年差
			int monthDiffer = (yearDiffer*12)-startCal.get(Calendar.MONTH)+curCal.get(Calendar.MONTH);
			if(monthDiffer>keepMonth){
				//查询时间大于保留月份，需要跨库查询
				//根据结束时间判断查询段是否全部在历史库
				int endYearDiffer = curCal.get(Calendar.YEAR) - endCal.get(Calendar.YEAR);//求年差
				int endMonthDiffer = (endYearDiffer*12)-endCal.get(Calendar.MONTH)+curCal.get(Calendar.MONTH);
				if(endMonthDiffer>keepMonth){
					//只需要从历史库查询
					return 0;
				}else{
					//需要跨库查询
				   //求在线库的起始时间
				   curCal.add(Calendar.MONTH,-(keepMonth));
				   curCal.set(Calendar.DAY_OF_MONTH, 1);
				   curCal.set(Calendar.HOUR_OF_DAY, 0);
				   curCal.set(Calendar.MINUTE, 0);
				   curCal.set(Calendar.SECOND, 0);
				   result.put("startDate", Long.parseLong(sdf.format(curCal.getTime())));
//				   System.out.println("在线库的起始时间:"+sdf.format(curCal.getTime()).toString());	
					//计算历史库查询时间段
				   curCal.add(Calendar.MONTH, -1);
				   int maxDay = curCal.getActualMaximum(Calendar.DAY_OF_MONTH);
				   curCal.set(Calendar.DAY_OF_MONTH, maxDay);
				   curCal.set(Calendar.HOUR_OF_DAY, 23);
				   curCal.set(Calendar.MINUTE, 59);
				   curCal.set(Calendar.SECOND, 59);
				   result.put("endDate", Long.parseLong(sdf.format(curCal.getTime())));
//				   System.out.println("历史结束时间:"+sdf.format(curCal.getTime()).toString());
				   return 1;
				}
			}else{
				//不需要从历史库查询
				return -1;
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}
	
	public ResponseData findDriveLine(String plateNumber) {
		ResponseData rd = new ResponseData(GlobalConstant.TRUE);
		
		if (StringUtils.isBlank(plateNumber)) {
			return rd;
		}
		
		Integer vehicleId = null;
		List<Integer> vehicleIds = vehicleDao.findIdsByPlateNumber(plateNumber, " order by configureStatus asc");
		if (vehicleIds == null || vehicleIds.size() == 0) {
			rd.setMessage("该车辆信息不存在；或登录会话失效(请重新登录后再试)");
			return rd;
		} else {
			vehicleId = vehicleIds.get(0);
		}

		VehicleVO vehicle = new VehicleVO(CacheVehicleManager.getVehicleById(vehicleId));
		
		Company dealer = CacheCompanyManager.getCompany(vehicle.getCompanyId());
		vehicle.setDealer(new DealerVO(dealer));
		rd.put("vehicle", vehicle);
		
		List<DriveLine> driveLineList = CacheDriveLineManager.getDriveLinesByDealerId(vehicle.getCompanyId());
		rd.put("driveLines", driveLineList);
		return rd;
	}

	@Override
	public ResponseData displayHome(Integer companyId, Integer userId,
			String plateNumber, String startTime, String endTime) {
		// TODO Auto-generated method stub
		return null;
	}
}
