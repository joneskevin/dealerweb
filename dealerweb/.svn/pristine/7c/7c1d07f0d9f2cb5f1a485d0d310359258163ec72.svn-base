package com.ava.dealer.service.impl;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ava.base.dao.hibernate.TransMsg;
import com.ava.base.dao.util.ParameterUtil;
import com.ava.base.domain.ResponseData;
import com.ava.dao.ITestDriveRateDao;
import com.ava.dealer.service.ITestDriveRateService;
import com.ava.domain.entity.TestDriveRate;
import com.ava.domain.entity.Org;
import com.ava.resource.GlobalConstant;
import com.ava.resource.cache.CacheOrgManager;
import com.ava.util.MyBeanUtils;

@Service
public class TestDriveRateService implements ITestDriveRateService{
	
	@Autowired
	private ITestDriveRateDao testDriveRateDao;
	
	public ResponseData addTestDriveRate(TestDriveRate testDriveRate, Integer companyId) {
		ResponseData rd = new ResponseData(0);
        
//		if (uniqueIdIsExistence(testDriveRate.getUniqueId())) {
//			rd.setCode(-1);
//			rd.setMessage("设备号已存在");
//			return rd;
//		}
//		
//		TestDriveRate dbBtestDriveRate = testDriveRateDao.getBySimId(testDriveRate.getSimId());
//		if (dbBtestDriveRate != null) {
//			rd.setCode(-2);
//			rd.setMessage("SIM卡已存在");
//			return rd;
//		}
//		
//		if (simMobileIsExistence(testDriveRate.getSimMobile())) {
//			rd.setCode(-3);
//			rd.setMessage("通讯号已存在");
//			return rd;
//		}
		
		/*Integer vehicleId = GlobalConstant.FALSE;
		if (testDriveRate.getVehicleId() != null) {
			vehicleId = testDriveRate.getVehicleId();
		}
		testDriveRate.setVehicleId(vehicleId);
		testDriveRate.setStatus(GlobalConstant.BOX_STATUS_ACTIVATION);
		testDriveRate.setDeletionFlag(GlobalConstant.FALSE);*/
		Integer newId = (Integer) testDriveRateDao.save(testDriveRate);
		
		if (null != newId && newId > 0) {
			rd.setCode(1);
			rd.setMessage("设备新增成功");
			rd.setFirstItem(testDriveRate);
		} else {
			rd.setCode(-4);
			rd.setMessage("设备新增失败");
		}

		return rd;
	}

	public ResponseData deleteTestDriveRate(Integer testDriveRateId, Integer companyId) {
		ResponseData rd = new ResponseData(0);

		TestDriveRate dbTestDriveRate = testDriveRateDao.get(testDriveRateId);
		if (dbTestDriveRate == null) {
			rd.setCode(-1);
			rd.setMessage("设备对象为空！");
			return rd;
		}

		/*dbTestDriveRate.setDeletionFlag(GlobalConstant.TRUE);// 删除状态
		testDriveRateDao.update(dbTestDriveRate);*/

		rd.setFirstItem(dbTestDriveRate);
		rd.setCode(1);
		rd.setMessage("设备删除成功！");
		return rd;
	}


	public ResponseData listTestDriveRate(TransMsg transMsg, TestDriveRate testDriveRate,
			Integer currentCompanyId) {
		ResponseData rd = new ResponseData(0);
		transMsg.setPageSize(12);

		String additionalCondition = "";

		Integer orgId = testDriveRate.getCompanyId();
		Org org = CacheOrgManager.get(orgId);
		if (org != null) {
			additionalCondition += " and companyId in("
					+ CacheOrgManager.getChildrenOrgIdsStr(orgId, GlobalConstant.TRUE) + ")";
		} else {
			additionalCondition += " and companyId in("
					+ CacheOrgManager.getChildrenOrgIdsStr(currentCompanyId, GlobalConstant.TRUE)
					+ ")";
		}

//		ParameterUtil.appendLikeQueryCondition(transMsg.getParameters(), "simId", testDriveRate.getSimId());
//		ParameterUtil.appendLikeQueryCondition(transMsg.getParameters(), "simMobile", testDriveRate.getSimMobile());


		additionalCondition = additionalCondition + " order by id desc";
		List<TestDriveRate> testDriveRateList = testDriveRateDao.findByPage(transMsg, additionalCondition);

		rd.setFirstItem(testDriveRateList);
		rd.setCode(1);
		return rd;
	}

	public List<TestDriveRate> find(HashMap<Object, Object> parameters, String additionalCondition){
		return testDriveRateDao.find(parameters, additionalCondition);
	}
	public TestDriveRate getTestDriveRate(Integer testDriveRateId) {
		TestDriveRate testDriveRate = testDriveRateDao.get(testDriveRateId);
		return testDriveRate;
	}

	public TestDriveRate getTestDriveRate(String simId) {
//		TestDriveRate testDriveRate = testDriveRateDao.getBySimId(simId);
//		return testDriveRate;
		return null;
		
	}
	
	public boolean vehicleIdIsExistence(Integer vehicleId) {
		if (vehicleId == null || vehicleId.intValue() == 1) {
			return false;
		}
		HashMap<Object, Object> parameters = new HashMap<Object, Object>();
		parameters.put("vehicleId", vehicleId);
		List objs = testDriveRateDao.find(parameters, null);
		if (objs != null && objs.size() > 0) {
			return true;
		}
		return false;
	}



	@Override
	public ResponseData displayAddTestDriveRate(TestDriveRate testDriveRateAdd) {
		ResponseData rd = new ResponseData(0);
		if (testDriveRateAdd == null) {
			testDriveRateAdd = new TestDriveRate();
		}
		
		rd.put("testDriveRateAdd", testDriveRateAdd);
		return rd;

	}




}
