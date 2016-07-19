package com.ava.dealer.service;

import java.util.HashMap;
import java.util.List;

import com.ava.base.dao.hibernate.TransMsg;
import com.ava.base.domain.ResponseData;
import com.ava.domain.entity.TestDriveRate;

public interface ITestDriveRateService {

	
	public ResponseData displayAddTestDriveRate(TestDriveRate testDriveRateAdd);

	/** 新增试驾率 ，其中companyId是当前登录者所属的组织ID*/
	public ResponseData addTestDriveRate(TestDriveRate testDriveRate, Integer companyId);

	/** 删除试驾率 ，其中companyId是当前登录者所属的组织ID*/
	public ResponseData deleteTestDriveRate(Integer testDriveRateId, Integer companyId);

	/** 查询试驾率列表 ，其中companyId是当前登录者所属的组织ID*/
	public ResponseData listTestDriveRate(TransMsg transMsg, TestDriveRate testDriveRate, Integer companyId);
	
	public List<TestDriveRate> find(HashMap<Object, Object> parameters, String additionalCondition);
	
}