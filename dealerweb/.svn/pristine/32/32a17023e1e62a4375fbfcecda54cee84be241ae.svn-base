package com.ava.dealer.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ava.base.dao.IHibernateDao;
import com.ava.dao.ITestDriveDao;
import com.ava.dao.IVehicleDao;
import com.ava.dealer.service.INoTestDriveService;
import com.ava.domain.entity.Company;
import com.ava.domain.entity.TestDrive;
import com.ava.domain.entity.Vehicle;
import com.ava.domain.vo.DealerVO;
import com.ava.domain.vo.TestDriveTimeVO;
import com.ava.domain.vo.TestDriveVO;
import com.ava.domain.vo.VehicleVO;
import com.ava.resource.GlobalConstant;
import com.ava.resource.cache.CacheCompanyManager;
import com.ava.resource.cache.CacheVehicleManager;
import com.ava.util.DateTime;

@Service
public class NoTestDriveService implements INoTestDriveService {
	
	@Autowired
	private IHibernateDao hibernateDao;
	
	@Autowired
	private ITestDriveDao testDriveDao;
	
	@Autowired
	private IVehicleDao vehicleDao;
	
	public Integer saveTestDrive(TestDrive testDrive){
		return testDriveDao.saveTestDrive(testDrive);
	}
	
	public void updateTestDrive(TestDrive testDrive){
		testDriveDao.updateTestDrive(testDrive);
	}
	
	public void delTestDrive(TestDrive testDrive){
		testDriveDao.delTestDrive(testDrive);
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
			
			//判断已安装BOX的车辆入库日期是否大于该日期
			Boolean falg = this.vehicleInstallationTime(vehicleId, currentDateStr);
			if (falg) {
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
			
	   }
	   return dateList;
	}
	
	/**
	 * 判断已安装BOX的车辆入库日期是否大于该日期
	 * TODO 只考虑的新装的、换装的未考虑
	 * @param vehicleId
	 * @param currentDateStr
	 * @return
	 */
	private Boolean vehicleInstallationTime(Integer vehicleId, String currentDateStr){
		Boolean flag = false;
		if (currentDateStr.equals("2014-12-13")) {
			flag = false;
		}
		if (vehicleId != null && currentDateStr != null) {
			String endTime = currentDateStr + " 23:59:59";
			StringBuffer exeSql = new StringBuffer("");
			exeSql.append(" SELECT count(tv.ID) FROM t_vehicle tv, t_box_operation tbo ");
			exeSql.append(" WHERE tv.COMPANY_ID = tv.COMPANY_ID ");
			exeSql.append(" AND tv.ID = tbo.VEHICLE_ID ");
			exeSql.append(" AND tv.CONFIGURE_STATUS = " + GlobalConstant.CONFIGURE_STATUS_INSTALLED);
			exeSql.append(" AND tv.ID = " + vehicleId);
			exeSql.append(" AND tbo.TYPE = " + GlobalConstant.PROPOSAL_TYPE_INSTALLATION);
			exeSql.append(" AND tbo.OPERATION_TIME < ' " + endTime + " ' ");
			Integer count = hibernateDao.getSqlCount(exeSql.toString());
			if (count != null && count > 0) {
				flag = true;
			}
		}
		
		return flag;
	}

	@Override
	public List<TestDriveVO> listNoTestDrive(Integer season) {
		//车辆表条件
		String addVehicleCondition = "";
		HashMap<Object, Object> parameters = new HashMap<Object, Object>();
		//已安装
		parameters.put("configureStatus", GlobalConstant.CONFIGURE_STATUS_INSTALLED);
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
							driveTimeVO.setStartDate(driveTimeVO.getStartDate() + " 00:00:00");
							driveTimeVO.setEndDate(driveTimeVO.getEndDate() + " 23:59:59");
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
