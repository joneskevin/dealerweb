package com.ava.dealer.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.ava.base.dao.IHibernateDao;
import com.ava.base.dao.hibernate.QueryParameter;
import com.ava.base.dao.hibernate.TransMsg;
import com.ava.base.dao.util.ParameterUtil;
import com.ava.base.domain.ResponseData;
import com.ava.dao.ICompanyDao;
import com.ava.dao.ILocationDao;
import com.ava.dao.INoTestDriveDao;
import com.ava.dao.ITestDriveDao;
import com.ava.dao.IVehicleDao;
import com.ava.dealer.service.ITestDriveService;
import com.ava.domain.entity.Company;
import com.ava.domain.entity.Location;
import com.ava.domain.entity.NoTestDrive;
import com.ava.domain.entity.TestDrive;
import com.ava.domain.entity.Vehicle;
import com.ava.domain.vo.DealerVO;
import com.ava.domain.vo.NoTestDriveFindVO;
import com.ava.domain.vo.NoTestDriveVO;
import com.ava.domain.vo.TestDrive4StatVO;
import com.ava.domain.vo.TestDriveDetailFindVO;
import com.ava.domain.vo.TestDriveFindVO;
import com.ava.domain.vo.TestDriveStatisticFindVO;
import com.ava.domain.vo.TestDriveTimeVO;
import com.ava.domain.vo.TestDriveVO;
import com.ava.domain.vo.VehicleVO;
import com.ava.resource.GlobalConstant;
import com.ava.resource.cache.CacheCompanyManager;
import com.ava.resource.cache.CacheOrgManager;
import com.ava.resource.cache.CacheVehicleManager;
import com.ava.util.DateTime;
import com.ava.util.TypeConverter;

@Service
public class TestDriveService extends BaseService implements ITestDriveService {
	
	@Autowired
	private ITestDriveDao testDriveDao;
	
	@Autowired
	private INoTestDriveDao noTestDriveDao;
	
	@Autowired
	private IVehicleDao vehicleDao;
	
	@Autowired
	private ICompanyDao companyDao;
	
	@Autowired
	private IHibernateDao hibernateDao;
	
	@Autowired
	private ILocationDao locationDao;
	
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public Integer saveTestDrive(TestDrive testDrive){
		return testDriveDao.saveTestDrive(testDrive);
	}
	
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public void updateTestDrive(TestDrive testDrive){
		testDriveDao.updateTestDrive(testDrive);
	}
	
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public void delTestDrive(TestDrive testDrive){
		testDriveDao.delTestDrive(testDrive);
	}
	
	public List<TestDrive> getTestDriveList(Map parameters, String additionalCondition){
		return testDriveDao.find(parameters, additionalCondition);
	}
	
	public List<Location> getLocationList(HashMap parameters, String additionalCondition){
		List<Location> locations = locationDao.find(parameters, additionalCondition);
		return locations;
	}
	
	public Location getLastLocation(HashMap parameters,String additionalCondition){
		additionalCondition +=" order by creationTime desc limit 0";
		List<Location> locations = getLocationList(parameters, additionalCondition);
		if(null==locations || locations.isEmpty())
			return null;
		return locations.get(0);
	}
	
	/**
	 * 重写获得经销商查询字段SQL
	 * @author liuq 
	 * @version 0.1 
	 * @return
	 */
	@Override
	public StringBuffer getDealerSql() {
		StringBuffer exeSql = new StringBuffer("");
		exeSql.append(" tdn.saleCenterId, tdn.saleCenterName, tdn.provinceId, tdn.provinceName, tdn.cityId, tdn.cityName, ");
		exeSql.append(" tdn.parentCompanyId, tdn.parentCompanyName, tdn.parentDealerCode, tdn.companyId, tdn.companyName, tdn.dealerCode, ");
		exeSql.append(" tdn.isKeyCity, tdn.isKeyCityNick, tdn.dealerType, tdn.dealerTypeNick, tdn.netWorkTime, ");
		return exeSql;
	}
	
	/**
	 * 重写获得车辆查询字段SQL
	 * @author liuq 
	 * @version 0.1 
	 * @return
	 */
	@Override
	public StringBuffer getVehicleSql() {
		StringBuffer exeSql = new StringBuffer("");
		exeSql.append(" tdn.vehicleId, tdn.plateNumber, tdn.vin, tdn.configureStatus, tdn.carStyleId,tdn.carStyleName, ");
		exeSql.append(" tdn.licensingTime, tdn.licensingExecutorName, tdn.obdNo, tdn.obdFlag, tdn.obdFlagNick, ");
		exeSql.append(" tdn.exitTime, tdn.configureStatusNick, ");
		
		return exeSql;
	}
	
	private StringBuffer getTestDriveSql() {
		StringBuffer exeSql = new StringBuffer("");
		exeSql.append(" tdn.LINE_ID as lineId, tdn.LOOP_COUNT as loopCount, tdn.MILEAGE as mileage, ");
		exeSql.append(" tdn.TEST_DRIVE_DATE as testDriveDate, tdn.START_TIME as startTime, tdn.END_TIME as endTime, tdn.STATUS as status, "); 
		exeSql.append(" (CASE tdn.STATUS ");
		exeSql.append(" WHEN ").append(GlobalConstant.TEST_DRIVE_STATUS_VALID).append(" THEN '合规' ");
		exeSql.append(" WHEN ").append(GlobalConstant.TEST_DRIVE_STATUS_INVALID).append(" THEN '无效' ");
		exeSql.append(" WHEN ").append(GlobalConstant.TEST_DRIVE_STATUS_UNLAW).append(" THEN '违规' ");
		exeSql.append(" WHEN ").append(GlobalConstant.TEST_DRIVE_COMPANY_INNER).append(" THEN '4S店内试跑' ");
		exeSql.append(" WHEN ").append(GlobalConstant.TEST_DRIVE_STATUS_FUEL).append(" THEN '加油' ");
		exeSql.append(" END) as statusNick, ");
		exeSql.append(" dl.NAME as lineNick "); 
		return exeSql;
	}
	
	
	public ResponseData listTestDrive(TransMsg transMsg, TestDriveVO testDrive, String startTime, String endTime, Integer companyId, Integer userId, boolean isExport) {
		ResponseData rd = new ResponseData(1);
		StringBuffer exeSql = new StringBuffer(" SELECT ");
		StringBuffer exeCountSql = new StringBuffer(" SELECT COUNT(1) ");
		StringBuffer exeChildSql = new StringBuffer("");
		VehicleVO vehicleVO = testDrive.getVehicle();
		boolean vehicleFlag = isHaveVehicleParameter(vehicleVO);
		//查询经销商信息
		exeSql.append(this.getDealerSql());
		//查询车辆信息
		if (vehicleFlag) {
			exeSql.append(this.getVehicleSql());
		} else {
			exeSql.append(super.getVehicleSql());
		}
		
		//试驾信息
		exeSql.append(getTestDriveSql());
		
		exeCountSql.append(" FROM t_test_drive td"); 
		//关联线路表
		exeChildSql.append(" LEFT JOIN t_drive_line dl on dl.ID = tdn.LINE_ID ");
		exeCountSql.append(" INNER JOIN view_dealer_for_count vdi on vdi.companyId = td.COMPANY_ID ");
		
		if (!vehicleFlag) {
			exeChildSql.append(" INNER JOIN t_vehicle v ON v.ID = tdn.VEHICLE_ID ");
			//关联车型
			exeChildSql.append(getVehicleJoinCarStyleSql());
		}
	
		exeCountSql.append(" INNER JOIN t_vehicle v ON v.ID = td.VEHICLE_ID ");
		exeCountSql.append(getAccessSql(companyId, userId));
		exeCountSql.append(getDealerConditionSql(testDrive.getDealer(), userId));
		exeCountSql.append(getVehicleConditionSql(vehicleVO));
		
		//---------- testDriveSql ---------- start ----------
		StringBuffer testDriveSql = new StringBuffer(" FROM (SELECT  "); 
		testDriveSql.append(super.getDealerSql());
		if (vehicleFlag) {
			testDriveSql.append(super.getVehicleSql());
		}
		testDriveSql.append(" td.LINE_ID, td.COMPANY_ID, td.VEHICLE_ID, ");
		testDriveSql.append(" td.LOOP_COUNT, td.MILEAGE, td.TEST_DRIVE_DATE, td.START_TIME, td.END_TIME,  td.STATUS FROM t_test_drive td USE INDEX (t_test_drive1_index)");
		testDriveSql.append(" INNER JOIN view_dealer_info vdi on vdi.companyId = td.COMPANY_ID ");
		if (vehicleFlag) {
			testDriveSql.append(" INNER JOIN t_vehicle v ON v.ID = td.VEHICLE_ID ");
			//关联车型
			testDriveSql.append(getVehicleJoinCarStyleSql());
		}
		
		//试驾状态为试驾结束的
		String currentStatusSql = " WHERE td.CURRENT_STATUS = 1 ";
		testDriveSql.append(currentStatusSql);
		exeCountSql.append(currentStatusSql);
		if (testDrive.getStatus() != null) {
			String statusSql = " AND td.STATUS = " + testDrive.getStatus();
			testDriveSql.append(statusSql);
			exeCountSql.append(statusSql);
		}
		
		if (StringUtils.isNotBlank(startTime)) {
			String startTimeSql = " AND td.START_TIME " + QueryParameter.OPERATOR_GE + " ' " + startTime + " ' ";
			testDriveSql.append(startTimeSql);
			exeCountSql.append(startTimeSql);
			
		}
		
		if (StringUtils.isNotBlank(endTime)) {
			String endTimeSql = " AND td.START_TIME " + QueryParameter.OPERATOR_LE + " ' " + endTime + " ' ";
			testDriveSql.append(endTimeSql);
			exeCountSql.append(endTimeSql);
		}
		
		//添加权限过滤条件
		testDriveSql.append(getAccessSql(companyId, userId));
		//添加经销商查询条件
		testDriveSql.append(getDealerConditionSql(testDrive.getDealer(), userId));
		testDriveSql.append(getVehicleConditionSql(vehicleVO));
	
		testDriveSql.append(" ORDER BY td.COMPANY_ID, td.TEST_DRIVE_DATE desc ");
				
		//设置分页
		testDriveSql.append(getPaginationSql(transMsg, exeCountSql, isExport));
		testDriveSql.append(") tdn ");
		exeSql.append(testDriveSql); 
		//---------- testDriveSql ---------- end ----------
		
		exeSql.append(exeChildSql);
		
		List<TestDriveFindVO> testDriveList = hibernateDao.executeSQLQueryVoList(exeSql.toString(), TestDriveFindVO.class);
		rd.setFirstItem(testDriveList);
		return rd;
	}
	
	
	public ResponseData listNoTestDriveOld(TransMsg transMsg, NoTestDriveVO noTestDrive,
			Integer companyId, boolean isListOrExport) {
		ResponseData rd = new ResponseData(0);
		//查询试驾列表
		ArrayList<NoTestDriveVO> noTestDriveVOList = new ArrayList<NoTestDriveVO>();
		
		if(isListOrExport == true)
		{
			transMsg.setPageSize(GlobalConstant.DEFAULT_TABLE_ROWS);
		}else{
			transMsg.setPageSize(Integer.MAX_VALUE);
		}
		//无试驾表条件
		String addNoTestDriveCondition = "";
		//车辆表条件
		String addVehicleCondition = "";
		//经销商表条件
		String addCompnayCondition = "";
		
		if (companyId != null) {
			addVehicleCondition += " and companyId in(" +  CacheOrgManager.getChildrenOrgIdsStr(companyId, GlobalConstant.TRUE) + ")";
		}
		
		HashMap<Object, Object> parameters = new HashMap<Object, Object>();
		if (noTestDrive.getVehicle().getPlateNumber() != null && noTestDrive.getVehicle().getPlateNumber().length() > 0) {
			ParameterUtil.appendLikeQueryCondition(parameters, "plateNumber", noTestDrive.getVehicle().getPlateNumber());
		}
		
		if (noTestDrive.getVehicle().getVin() != null && noTestDrive.getVehicle().getVin().length() > 0) {
			ParameterUtil.appendLikeQueryCondition(parameters, "vin", noTestDrive.getVehicle().getVin());
		}
		
		//查询车辆列表
		List<Vehicle> vehicleList = vehicleDao.find(parameters, addVehicleCondition);
		if (vehicleList !=null && vehicleList.size() > 0) {
			String vehicles = "";
			StringBuffer vehicleBuff = new StringBuffer();
			for (Vehicle vehicle : vehicleList) {
				vehicleBuff.append(vehicle.getId()).append(",");
			}
			if (vehicleBuff.length() > 0) {
				vehicles = vehicleBuff.substring(0, vehicleBuff.length() - 1);
				addNoTestDriveCondition += " and vehicleId in(" + vehicles + ")";
			}
		}else{
			//查不到车辆列表 就返回 
			return rd;
		}
		
		//查询经销商列表 
		parameters.clear();
		if (noTestDrive.getDealer().getDealerCode() != null && noTestDrive.getDealer().getDealerCode().length() > 0) {
			ParameterUtil.appendLikeQueryCondition(parameters, "dealerCode", noTestDrive.getDealer().getDealerCode());
		}
		if (companyId != null) {						
			addCompnayCondition += " and id in(" +  CacheOrgManager.getChildrenOrgIdsStr(companyId, GlobalConstant.TRUE) + ")";
		}
		
		List<Company> companyList = companyDao.find(parameters, addCompnayCondition);
		if (companyList != null && companyList.size() > 0) {
			String companys = "";
			StringBuffer companyBuff = new StringBuffer();
			for (Company company : companyList) {
				companyBuff.append(company.getId()).append(",");
			}
			
			if (companyBuff.length() > 0) {
				companys = companyBuff.substring(0, companyBuff.length() - 1);
				addNoTestDriveCondition += " and companyId in(" + companys + ")";
			}
		}else{
			//查不到列表 就返回 
			return rd;
		}
		
		if (noTestDrive.getSeason() != null && noTestDrive.getSeason().intValue() > 0) {
			transMsg.put("season", noTestDrive.getSeason());
		} else {
			transMsg.put("season", GlobalConstant.SEASON_1);
		}
		
		if (noTestDrive.getDurationDayCount() != null && noTestDrive.getDurationDayCount().intValue() > 0) {
			//transMsg.put("durationDayCount", noTestDrive.getDurationDayCount());
			addNoTestDriveCondition += " and durationDayCount >= " + noTestDrive.getDurationDayCount();
		}
		
		addNoTestDriveCondition += " order by vehicleId,durationDayCount asc ";
		List<NoTestDrive> noTestDriveList = noTestDriveDao.findByPage(transMsg, addNoTestDriveCondition);
		if (noTestDriveList != null) {
			for (NoTestDrive dbNoTestDrive : noTestDriveList) {
				NoTestDriveVO noTestDriveVO = new NoTestDriveVO(dbNoTestDrive);
				Company dbCompany = CacheCompanyManager.getCompany(noTestDriveVO.getCompanyId());
				if (dbCompany != null) {
					DealerVO  dealerVo = new DealerVO (dbCompany);
					noTestDriveVO.setDealer(dealerVo);
				}
				
				Vehicle dbVehicle = vehicleDao.get(noTestDriveVO.getVehicleId());
				if (dbVehicle != null) {
					VehicleVO vehicleVO = new VehicleVO(dbVehicle);
					noTestDriveVO.setVehicle(vehicleVO);
				}
				noTestDriveVOList.add(noTestDriveVO);
			}
		}
		
		rd.setFirstItem(noTestDriveVOList);
		rd.setCode(1);
		return rd;
	}
	
	public ResponseData listNoTestDrive(TransMsg transMsg, NoTestDriveVO noTestDrive,
			Integer companyId, boolean isExport) {
		ResponseData rd = new ResponseData(0);
		if(isExport) {
			transMsg.setPageSize(Integer.MAX_VALUE);
		} else {
			transMsg.setPageSize(GlobalConstant.DEFAULT_TABLE_ROWS);
		}
	
		StringBuffer exeSql = new StringBuffer("");
		//经销商信息
		exeSql.append(" SELECT o.PARENT_ID as parentId,");
		exeSql.append(" c.REGION_PROVINCE_ID as regionProvinceId , c.REGION_CITY_ID as regionCityId, ");
		exeSql.append(" c.CN_NAME as cnName, c.DEALER_CODE as dealerCode, c.IS_KEY_CITY as isKeyCity, c.DEALER_TYPE as dealerType, ");
		//车辆信息
		exeSql.append(" v.PLATE_NUMBER as plateNumber, v.CAR_STYLE_ID as carStyleId, v.VIN as vin, ");
		//连续无试驾信息
		exeSql.append(" td.ID as id, td.DURATION_DAY_COUNT as durationDayCount, td.START_TIME as startTime, td.END_TIME as endTime "); 
		exeSql.append(" FROM t_no_test_drive td, t_org o, t_company c, t_vehicle v "); 
		exeSql.append(" WHERE td.COMPANY_ID = o.ID ");
		exeSql.append(" AND td.COMPANY_ID = c.ORG_ID ");
		exeSql.append(" AND td.VEHICLE_ID = v.ID ");
		if (companyId != null) {
			exeSql.append(" AND td.COMPANY_ID IN (" + CacheOrgManager.getChildrenOrgIdsStr(companyId, GlobalConstant.TRUE) + ") ");
		}
		
		VehicleVO vehicleVO = noTestDrive.getVehicle();
		if (vehicleVO.getVin() != null && vehicleVO.getVin().trim().length() > 0) {
			exeSql.append(" AND v.VIN LIKE '%" + vehicleVO.getVin() + "%' ");
		}

		if (vehicleVO.getPlateNumber() != null && vehicleVO.getPlateNumber().trim().length() > 0) {
			exeSql.append(" AND v.PLATE_NUMBER LIKE '%" + vehicleVO.getPlateNumber() + "%' ");
		}
		
		DealerVO dealerVO = noTestDrive.getDealer();
		if (dealerVO.getDealerCode() != null && dealerVO.getDealerCode().trim().length() > 0) {
			exeSql.append(" AND c.DEALER_CODE LIKE '%" + dealerVO.getDealerCode() + "%' ");
		}
		
		if (noTestDrive.getSeason() != null && noTestDrive.getSeason().intValue() > 0) {
			exeSql.append(" AND td.SEASON = " + noTestDrive.getSeason());
		}
		
		if (noTestDrive.getDurationDayCount() != null && noTestDrive.getDurationDayCount().intValue() > 0) {
			exeSql.append(" AND td.DURATION_DAY_COUNT >= " + noTestDrive.getDurationDayCount());
		}
		
		exeSql.append(" ORDER BY o.PARENT_ID, c.DEALER_CODE, td.VEHICLE_ID, td.DURATION_DAY_COUNT ");
		
		List<NoTestDriveFindVO> noTestDriveFindVOList = hibernateDao.executeSQLQueryVoList(exeSql.toString(), NoTestDriveFindVO.class);
		//总条数
		Integer total = 0;
		if (noTestDriveFindVOList != null && noTestDriveFindVOList.size() > 0) {
			total = noTestDriveFindVOList.size();
		}
		transMsg.setTotalRecords(TypeConverter.toLong(total));
		
		//分页
		if (transMsg.getStartIndex() == null) {
			transMsg.setStartIndex(0);
		}
		exeSql.append(" limit "+ transMsg.getStartIndex()
			          + "," + (transMsg.getPageSize()) + " ");
		
		ArrayList<NoTestDriveVO> noTestDriveVOList = new ArrayList<NoTestDriveVO>();
		noTestDriveFindVOList = hibernateDao.executeSQLQueryVoList(exeSql.toString(), NoTestDriveFindVO.class);
		if (noTestDriveFindVOList != null) {
			for (NoTestDriveFindVO noTestDriveFindVO : noTestDriveFindVOList) {
				 NoTestDriveVO noTestDriveVO = new NoTestDriveVO(noTestDriveFindVO);
				
				 DealerVO dealer = new DealerVO(noTestDriveFindVO);
				 noTestDriveVO.setDealer(dealer);
			
				 VehicleVO vehicle = new VehicleVO(noTestDriveFindVO);
				 noTestDriveVO.setVehicle(vehicle);
				
				 noTestDriveVOList.add(noTestDriveVO);
			}
		}
		
		rd.setFirstItem(noTestDriveVOList);
		rd.setCode(1);
		return rd;
	}
	
	public ResponseData listTestDriveStatistic(TransMsg transMsg, TestDrive4StatVO testDrive, Integer companyId, Integer userId, boolean isDetail, boolean isExport) {
		ResponseData rd = new ResponseData(1);
		StringBuffer exeSql = new StringBuffer(" SELECT ");
		StringBuffer exeCountSql = new StringBuffer(" SELECT COUNT(1) FROM (SELECT 1 ");
		StringBuffer exeChildSql = new StringBuffer("");
		StringBuffer testDriveSql = new StringBuffer("");
		VehicleVO vehicleVO = testDrive.getVehicle();
		boolean vehicleFlag = isHaveVehicleParameter(vehicleVO);
		//查询经销商信息
		exeSql.append(this.getDealerSql());
		//查询车辆信息
		if (vehicleFlag) {
			exeSql.append(this.getVehicleSql());
		} else {
			exeSql.append(super.getVehicleSql());
		}
		
		if (isDetail) {
			exeSql.append(" tdn.MILEAGE as mileage, tdn.TEST_DRIVE_DATE as testDriveDate, tdn.START_TIME as startTime, tdn.END_TIME as endTime, tdn.INTERVAL_TIME as intervalTime, '' as useTimeStr, "); 
		} else {
			exeSql.append(" tdn.loopTotal, tdn.mileageTotal, ");
		}
		exeSql.append(" tdn.LINE_ID as lineId, ");
		exeSql.append(" dl.NAME as lineNick "); 
		
		exeCountSql.append(" FROM t_test_drive td");
		//关联线路表
		exeChildSql.append(" LEFT JOIN t_drive_line dl on dl.ID = tdn.LINE_ID ");
		exeCountSql.append(" INNER JOIN view_dealer_for_count vdi on vdi.companyId = td.COMPANY_ID ");
		
		if (!vehicleFlag) {
			exeChildSql.append(" INNER JOIN t_vehicle v ON v.ID = tdn.VEHICLE_ID ");
			//关联车型
			exeChildSql.append(getVehicleJoinCarStyleSql());
		}
		exeCountSql.append(" INNER JOIN t_vehicle v ON v.ID = td.VEHICLE_ID ");
		
		exeCountSql.append(getAccessSql(companyId, userId));
		exeCountSql.append(getVehicleConditionSql(vehicleVO));
		exeCountSql.append(getDealerConditionSql(testDrive.getDealer(), userId));
		
		//---------- testDriveSql ---------- start ----------
		testDriveSql.append(" FROM (SELECT td.LINE_ID, td.VEHICLE_ID, "); 
		testDriveSql.append(super.getDealerSql());
		if (vehicleFlag) {
			testDriveSql.append(super.getVehicleSql());
		}
		if(isDetail){
			testDriveSql.append(" td.LOOP_COUNT, td.MILEAGE, td.TEST_DRIVE_DATE, td.START_TIME, td.END_TIME, td.INTERVAL_TIME ");
		} else {
			testDriveSql.append(" SUM(td.LOOP_COUNT) AS loopTotal, SUM(td.MILEAGE) AS mileageTotal ");
		}
		testDriveSql.append(" FROM t_test_drive td ");
		testDriveSql.append(" INNER JOIN view_dealer_info vdi on vdi.companyId = td.COMPANY_ID ");
		if (vehicleFlag) {
			testDriveSql.append(" INNER JOIN t_vehicle v ON v.ID = td.VEHICLE_ID ");
			//关联车型
			testDriveSql.append(getVehicleJoinCarStyleSql());
		}
		
		//试驾状态为试驾结束并且状态为有效
		String statusSql = " WHERE td.CURRENT_STATUS = 1 AND td.STATUS = 1";
		testDriveSql.append(statusSql);
		exeCountSql.append(statusSql);
		
		String[] conditions = this.getTestDriveStatisticConditions(testDrive);
		exeCountSql.append(conditions[0]);
		testDriveSql.append(conditions[1]);
		
		//添加权限过滤条件
		testDriveSql.append(getAccessSql(companyId, userId));
		//添加车辆查询条件
		testDriveSql.append(getVehicleConditionSql(vehicleVO));
		//添加经销商查询条件
		testDriveSql.append(getDealerConditionSql(testDrive.getDealer(), userId));
		
		if(isDetail){
			exeCountSql.append(" ) t");
		} else {
			String groupBySql = " GROUP BY td.VEHICLE_ID, td.LINE_ID ";
			testDriveSql.append(groupBySql);
			exeCountSql.append(groupBySql).append(" ) t");
			//根据分销中心、网络代码排序
			testDriveSql.append(" ORDER BY td.COMPANY_ID,td.VEHICLE_ID ");
		}
		
		//设置分页
		testDriveSql.append(getPaginationSql(transMsg, exeCountSql, isExport));
		testDriveSql.append(") tdn ");
		exeSql.append(testDriveSql); 
		//---------- testDriveSql ---------- end ----------
		
		exeSql.append(exeChildSql);
		if(isDetail){
			List<TestDriveDetailFindVO> testDriveList = hibernateDao.executeSQLQueryVoList(exeSql.toString(), TestDriveDetailFindVO.class);
			formatUseTimeStr(testDriveList);
			rd.setFirstItem(testDriveList);
		} else {
			List<TestDriveStatisticFindVO> testDriveList = hibernateDao.executeSQLQueryVoList(exeSql.toString(), TestDriveStatisticFindVO.class);
			rd.setFirstItem(testDriveList);
		}
		
		return rd;
	}
	
	
	/**
	 * 设置试驾统计条件
	 * @author liuq 
	 * @version 0.1 
	 * @param testDrive
	 */
	private String[] getTestDriveStatisticConditions(TestDrive4StatVO testDrive) {
		String[] conditions = new String[2];
		StringBuffer exeCountSql = new StringBuffer("");
		StringBuffer testDriveSql = new StringBuffer("");
		String startTime = null;
		String endTime = null;
		if (testDrive.getStartTime() != null || testDrive.getEndTime() != null) {
			startTime = DateTime.toNormalDateTime(testDrive.getStartTime());
			endTime = DateTime.toNormalDateTime(testDrive.getEndTime());
			
		} else if (testDrive.getWeek() != null&&testDrive.getWeek().length()>0) {
			// 得到该周的起始日期
			String[] days = DateTime.getRangeOfWeek(TypeConverter.toInteger(testDrive.getWeek()));
			startTime = DateTime.toNormalDateTime(DateTime.toDate(days[0] + "000000"));
			endTime = DateTime.toNormalDateTime(DateTime.toDate(days[1] + "235959"));
			
		} else if (testDrive.getMonth() != null&&testDrive.getMonth().length()>0) {
			// 得到该月的起始日期
			String yearMonth = testDrive.getYear().toString() + testDrive.getMonth() + "01";
			String[] days = DateTime.getRangeOfMonth(DateTime.toDate(yearMonth));
			startTime = DateTime.toNormalDateTime(DateTime.toDate(days[0]));
			endTime = DateTime.toNormalDateTime(DateTime.toDate(days[1]));
			
		} else if (testDrive.getSeason() != null) {
			if (testDrive.getSeason() == 1) {
				startTime = testDrive.getYear() + "-01-01 00:00:00";
				endTime = testDrive.getYear() + "-03-31 23:59:59";
				
			} else if (testDrive.getSeason() == 2) {
				startTime = testDrive.getYear() + "-04-01 00:00:00";
				endTime = testDrive.getYear() + "-06-30 23:59:59";
				
			} else if (testDrive.getSeason() == 3) {
				startTime = testDrive.getYear() + "-07-01 00:00:00";
				endTime = testDrive.getYear() + "-09-30 23:59:59";
				
			} else if (testDrive.getSeason() == 4) {
				startTime = testDrive.getYear() + "-10-01 00:00:00";
				endTime = testDrive.getYear() + "-12-31 23:59:59";
			}
		} 	
		
		if (startTime != null) {
			StringBuffer startTimeSql = new StringBuffer(" AND td.START_TIME ").append(QueryParameter.OPERATOR_GE).append(" ' ").append(startTime).append(" ' ");
			testDriveSql.append(startTimeSql);
			exeCountSql.append(startTimeSql);
		}
		
		if (endTime != null) {
			StringBuffer endTimeSql = new StringBuffer(" AND td.END_TIME ").append(QueryParameter.OPERATOR_LE).append(" ' " ).append(endTime).append( " ' ");
			testDriveSql.append(endTimeSql);
			exeCountSql.append(endTimeSql);
		}
		
		if (testDrive.getLineId() != null && testDrive.getLineId().intValue() > 0) {
			String lineIdSql = " AND td.LINE_ID =" + testDrive.getLineId();
			testDriveSql.append(lineIdSql);
			exeCountSql.append(lineIdSql);
		}
		
		if (testDrive.getVehicleId() != null && testDrive.getVehicleId().intValue() > 0) {
			String vehicleIdSql = " AND td.VEHICLE_ID =" + testDrive.getVehicleId();
			testDriveSql.append(vehicleIdSql);
			exeCountSql.append(vehicleIdSql);
		}
		conditions[0] = exeCountSql.toString();
		conditions[1] = testDriveSql.toString();
		return conditions;
	}
	
	/**
	 * 将用时格式化成X小时X分
	 * @param testDriveList
	 */
	private void formatUseTimeStr(List<TestDriveDetailFindVO> testDriveList){
		if (testDriveList != null && testDriveList.size() > 0) {
			for (TestDriveDetailFindVO testDriveDetailFindVO : testDriveList) {
				if (testDriveDetailFindVO.getIntervalTime() != null) {
					String fomatDate = DateTime.formatSecond(testDriveDetailFindVO.getIntervalTime(), 1);
					testDriveDetailFindVO.setUseTimeStr(fomatDate);
				}
			}
		}
	}
	
	/**
	 * 返回连续无试驾的MAP集合  1一个季度日期集合
	 * @param seasonDateList
	 * @param noTestDriveCount 连续无试驾天数
	 * @return
	 */
	private Map getNoTestDriveMap(List seasonDateList, Integer noTestDriveCount) {
		int k = 0; //key名字序号
		Map map = new HashMap();
		boolean lastHasTestDrive = false;//上次是否有试驾
		TestDriveTimeVO  testDriveTimeVO2 = new TestDriveTimeVO();
		if (seasonDateList != null && seasonDateList.size() > 0) {
			for (int i = 0; i < seasonDateList.size(); i++) {
				TestDriveTimeVO testDriveTimeVO = (TestDriveTimeVO) seasonDateList.get(i);
				
				//如果上次有试驾本次无试驾
				if (lastHasTestDrive == true && testDriveTimeVO.getIsTestDrive() == GlobalConstant.FALSE ){
						k++;
				}
				
				String key = "row" + k; 
				if(testDriveTimeVO.getIsTestDrive() == GlobalConstant.TRUE){
					//如果上次等于无试驾、本次等于有试驾则根据判断逻辑决定是否删除
					if (lastHasTestDrive == false){
						TestDriveTimeVO	testDriveTimeVO1 = (TestDriveTimeVO) map.get(key);
						if (testDriveTimeVO1 != null && testDriveTimeVO1.getNoTestDriveCount() < noTestDriveCount) {
							map.remove(key);
						}
					}
					//当前循环到的日期是否有试驾，不处理
					lastHasTestDrive = true;
					continue;
				} else {
					if (testDriveTimeVO.getIsTestDrive() == GlobalConstant.FALSE) {
						   testDriveTimeVO2 = (TestDriveTimeVO) map.get(key);
						if (testDriveTimeVO2 == null) {
							testDriveTimeVO2 = new TestDriveTimeVO();
							testDriveTimeVO2.setVehicleId(testDriveTimeVO.getVehicleId());
							testDriveTimeVO2.setYear(testDriveTimeVO.getYear());
							testDriveTimeVO2.setSeason(testDriveTimeVO.getSeason());
							testDriveTimeVO2.setStartDate(testDriveTimeVO.getQuarterDate());
							testDriveTimeVO2.setNoTestDriveCount(1);
						}else {
							testDriveTimeVO2.setVehicleId(testDriveTimeVO.getVehicleId());
							testDriveTimeVO2.setEndDate(testDriveTimeVO.getQuarterDate());
							testDriveTimeVO2.setNoTestDriveCount(testDriveTimeVO2.getNoTestDriveCount()+1);
						}
						
						map.put(key, testDriveTimeVO2);
						lastHasTestDrive = false;
						
					}
				}
			}
		}
		return map;
	}
	
	/**
	 * 通过季度返回日期的集合
	 * @param season
	 * @param vehicleId
	 * @return
	 */
	public List<TestDriveTimeVO> getSeasonDateList(Integer season, Integer vehicleId) {
		
		String[] monthArray = new String[3];
		
		if (season == GlobalConstant.SEASON_1) {
			monthArray[0] = "01";
			monthArray[1] = "02";
			monthArray[2] = "03";
			
		} else if (season == GlobalConstant.SEASON_2){
			monthArray[0] = "04";
			monthArray[1] = "05";
			monthArray[2] = "06";
			
		} else if (season == GlobalConstant.SEASON_3) {
			monthArray[0] = "07";
			monthArray[1] = "08";
			monthArray[2] = "09";
		
		} else if (season == GlobalConstant.SEASON_4){
			monthArray[0] = "10";
			monthArray[1] = "11";
			monthArray[2] = "12";
		}
		//日期列表
		List dateList = new ArrayList();
		dateList.addAll(getMonthArrayDateList(season, monthArray, vehicleId));
		return dateList;
	}
	
	/**
	 * 通过季度月份Array返回日期的集合
	 * @param season
	 * @param monthArray
	 * @param vehicleId
	 * @return
	 */
	public List<TestDriveTimeVO> getMonthArrayDateList(Integer season, String[] monthArray, Integer vehicleId) {
		
		List dateList = new ArrayList();//日期列表
		for (int i = 0; i < monthArray.length; i++ ) {
			dateList.addAll(getMonthDateList(season, monthArray[i], vehicleId));
		}
		return dateList;
	}
	
	/**
	 * 通过季度月份Array返回日期的集合
	 * @param season
	 * @param month
	 * @param vehicleId
	 * @return
	 */
	public List<TestDriveTimeVO> getMonthDateList(Integer season, String month, Integer vehicleId) {
		List dateList = new ArrayList();//日期列表
		String currentYear = DateTime.getYear();//当前年
		String currentDateStr = currentYear + "-" + month + "-01";//当前月
		Date date = DateTime.toDate(currentDateStr);
		
		String lastDay = DateTime.getMonthLastDay(date, DateTime.PATTERN_DATE);//一个月的最后一天
		Integer monthLength = Integer.valueOf(lastDay.substring(8));//一个月总天数
		
		for (int i = 1; i < monthLength + 1; i++ ) {
			if (i < 10) {
				currentDateStr = currentYear + "-" + month + "-0" + i;
			} else {
				currentDateStr = currentYear + "-" + month + "-" + i;
			}
			
			//不能大于今天
			date = DateTime.toDate(currentDateStr);
			if (date.getTime() > new Date().getTime()) {
	            return dateList;
	        } 
			
			TestDriveTimeVO testDriveTimeVO = new TestDriveTimeVO();
			
			HashMap parameters = new HashMap();
			parameters.put("vehicleId", vehicleId);
			parameters.put("testDriveDate", DateTime.toDate(currentDateStr));
			List testDriveList = testDriveDao.find(parameters, null);
			if (testDriveList != null && testDriveList.size() > 0) {
				testDriveTimeVO.setIsTestDrive(GlobalConstant.TRUE);
			} else {
				testDriveTimeVO.setIsTestDrive(GlobalConstant.FALSE);
			}
			
			testDriveTimeVO.setYear(Integer.valueOf(currentYear));
			testDriveTimeVO.setSeason(season);
			testDriveTimeVO.setQuarterDate(currentDateStr);
			testDriveTimeVO.setVehicleId(vehicleId);
			dateList.add(testDriveTimeVO);
	   }
	   return dateList;
	}

	@Override
	public List<TestDriveVO> listNoTestDrive(Integer companyId, Integer season) {
		//车辆表条件
		String addVehicleCondition = "";
		//经销商表条件
		String addCompnayCondition = "";
		
		HashMap<Object, Object> parameters = new HashMap<Object, Object>();
		
		parameters.clear();
		if (companyId != null) {
			addCompnayCondition += " and id in(" +  CacheOrgManager.getChildrenOrgIdsStr(companyId, GlobalConstant.TRUE) + ")";
		}
		
		List<Company> companyList = companyDao.find(parameters, addCompnayCondition);
		if (companyList != null && companyList.size() > 0) {
			String companys = "";
			StringBuffer companyBuff = new StringBuffer();
			for (Company company : companyList) {
				 companyBuff.append(company.getId()).append(",");
			}
			
			if (companyBuff.length() > 0) {
				companys = companyBuff.substring(0, companyBuff.length() - 1);
				addVehicleCondition += " and companyId in(" + companys + ")";
			}
		}
		
		parameters.clear();
		//查询车辆列表
		List<Vehicle> vehicleList = vehicleDao.find(parameters, addVehicleCondition);
		//存储一辆车对应一个季度是否试驾的集合
		List testDriveTimeList  =  new ArrayList();
		if (vehicleList !=null && vehicleList.size() > 0) {
			for (Vehicle vehicle : vehicleList) {
			  //季度返回日期的集合
			   List seasonDateList = getSeasonDateList(season, vehicle.getId());
			  //连续无试驾天数
			 Integer noTestDriveCount = 1;
			 Map map = this.getNoTestDriveMap(seasonDateList, noTestDriveCount);
			 testDriveTimeList.add(map);
			 
			}
		}
		
		List<TestDriveVO> testDriveVOList = this.getTestDriveVOList(testDriveTimeList);
		return testDriveVOList;
	}
	
	/**
	 * 通过
	 * @param testDriveTimeList
	 * @return
	 */
	public List<TestDriveVO> getTestDriveVOList(List testDriveTimeList) {
		ArrayList<TestDriveVO> testDriveVOList = new ArrayList<TestDriveVO>();
		if (testDriveTimeList != null && testDriveTimeList.size() > 0) {
			for (int i = 0; i < testDriveTimeList.size(); i++) {
				Map map = (Map) testDriveTimeList.get(i);
				if (map != null) {
					for (int y = 0; y < map.size() + 1; y++) {
						TestDriveVO testDriveVO = new TestDriveVO();
						
						TestDriveTimeVO driveTimeVO = (TestDriveTimeVO) map.get("row" + y);
						if (driveTimeVO != null) {
							String startDate = driveTimeVO.getStartDate();
							//如果只有一天无试驾，则结束日期为当天
							if (driveTimeVO.getEndDate() == null) {
								driveTimeVO.setEndDate(startDate);
							}
							driveTimeVO.setStartDate(driveTimeVO.getStartDate()+" 09:00:00");
							driveTimeVO.setEndDate(driveTimeVO.getEndDate()+" 18:00:00");
							testDriveVO.setTestDriveTime(driveTimeVO);
							
							Vehicle dbVehicle = CacheVehicleManager.getVehicleById(driveTimeVO.getVehicleId());
							 if (dbVehicle != null) {
								 VehicleVO vehicleVO = new VehicleVO(dbVehicle);
								 testDriveVO.setVehicle(vehicleVO);
							 }
							 
							 Company dbCompany = CacheCompanyManager.getCompany(dbVehicle.getCompanyId());
							 if (dbCompany != null) {
								 DealerVO  dealerVo = new DealerVO (dbCompany);
								 testDriveVO.setDealer(dealerVo);
							 }
							 
							 testDriveVOList.add(testDriveVO);
						}
					}
				}
			}
		}
		return testDriveVOList;
	}
}
