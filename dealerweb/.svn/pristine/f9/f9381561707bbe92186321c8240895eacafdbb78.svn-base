package com.ava.facade.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ava.base.dao.IHibernateDao;
import com.ava.dao.INoTestDriveDao;
import com.ava.dealer.service.INoTestDriveService;
import com.ava.domain.entity.NoTestDrive;
import com.ava.domain.vo.TestDriveVO;
import com.ava.facade.INoTestDriveFacade;
import com.ava.resource.GlobalConstant;
import com.ava.util.DateTime;
import com.ava.util.MyBeanUtils;

@Service
public class NoTestDriveFacade implements INoTestDriveFacade {
	
	@Autowired
	private INoTestDriveDao noTestDriveDao;
	
	@Autowired
	private INoTestDriveService noTestDriveService;
	
	@Autowired
	private IHibernateDao hibernateDao;
    
	
	/**
	 * 抽取数据【入口】
	 * @param extractionFlag 0、抽取一年、1、抽取当年某个季度
	 * @param season 季度
	 */
	public void extractionNoTestDrive(Integer extractionFlag, Integer season) {
		try {
			if (extractionFlag != null) {
				if (extractionFlag == GlobalConstant.FALSE) {
					//先删除一年的数据 TODO,
					hibernateDao.executeUpdate("delete NoTestDrive where year = " + DateTime.getYear());
					Integer[] yearArray = {GlobalConstant.SEASON_1,GlobalConstant.SEASON_2,GlobalConstant.SEASON_3,GlobalConstant.SEASON_4};
					for (int i = 0; i < yearArray.length; i++ ) { 
						this.addNoTestDrive(yearArray[i]);
					}
					
				} else if (season != null && extractionFlag == GlobalConstant.TRUE){
					//先删除一个季度的数据 TODO,
					hibernateDao.executeUpdate("delete NoTestDrive where year = " + DateTime.getYear() + " and season = " + season);
					this.addNoTestDrive(season);
				}
			}
			
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	/**
	 * 添加连续试驾记录
	 * @param season
	 */
	private void addNoTestDrive(Integer season) {
		List<TestDriveVO> testDriveVOList = noTestDriveService.listNoTestDrive(season);
		if (testDriveVOList != null && testDriveVOList.size() > 0) {
			for (TestDriveVO testDriveVO : testDriveVOList) {
				NoTestDrive noTestDrive = new NoTestDrive();
				
				noTestDrive.setCompanyId(testDriveVO.getVehicle().getCompanyId());
				noTestDrive.setVehicleId(testDriveVO.getTestDriveTime().getVehicleId());
				noTestDrive.setYear(testDriveVO.getTestDriveTime().getYear());
				noTestDrive.setSeason(testDriveVO.getTestDriveTime().getSeason());
				noTestDrive.setDurationDayCount(testDriveVO.getTestDriveTime().getNoTestDriveCount());
				Date startDate = DateTime.toDate(testDriveVO.getTestDriveTime().getStartDate());
				noTestDrive.setStartTime(startDate);
				Date endDate = DateTime.toDate(testDriveVO.getTestDriveTime().getEndDate());
				noTestDrive.setEndTime(endDate);
				
				MyBeanUtils.copyPropertiesContainsDate(noTestDrive, testDriveVO);
				noTestDriveDao.save(noTestDrive);
			}
		}
	}
	
}
