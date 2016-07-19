package com.ava.dao.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ava.base.dao.IHibernateDao;
import com.ava.base.dao.IJdbcDao4mysql;
import com.ava.base.dao.hibernate.TransMsg;
import com.ava.dao.ITestDriveDao;
import com.ava.domain.entity.Company;
import com.ava.domain.entity.TestDrive;
import com.ava.domain.entity.Vehicle;
import com.ava.domain.vo.DealerVO;
import com.ava.domain.vo.TestDrive4StatVO;
import com.ava.domain.vo.TestDriveVO;
import com.ava.domain.vo.VehicleVO;
import com.ava.resource.cache.CacheCompanyManager;
import com.ava.resource.cache.CacheVehicleManager;
import com.ava.util.MyBeanUtils;

@Repository
public class TestDriveDao implements ITestDriveDao {
	
	@Autowired
	private IHibernateDao hibernateDao;
	
	@Autowired
	private IJdbcDao4mysql jdbcDao4mysql;
	
	public Integer saveTestDrive(TestDrive testDrive){
		
		return (Integer) hibernateDao.save(testDrive);
	}
	
	public void updateTestDrive(TestDrive testDrive){
		if(null!=testDrive){
			if(null!=testDrive.getId() && testDrive.getId().intValue()>=1){
				TestDrive updateTestDrive=get(testDrive.getId());
				if(null!=updateTestDrive){
					hibernateDao.merge(testDrive);
				}
			}
		}
	}
	
	public void delTestDrive(TestDrive testDrive){
		if(null!=testDrive){
			hibernateDao.delete(testDrive);
		}
	}
	
	public TestDrive get(Integer id){
		return hibernateDao.get(TestDrive.class, id);
	}
	
	@Override
	public List<TestDrive4StatVO> findByPage(String nativeSql, TransMsg transMsg) {
		List<TestDrive4StatVO> testDrive4StatVOList = null;
		List<Map<String, Object>> mapObjs = jdbcDao4mysql.queryForPage(nativeSql, transMsg);
		if(mapObjs != null && mapObjs.size() > 0){
			testDrive4StatVOList = new ArrayList();
			TestDrive4StatVO aTestDrive4StatVO = null;
			
			for(Map properties : mapObjs){
				try {
					TestDrive testDrive = new TestDrive();
					MyBeanUtils.populate(testDrive, properties);
					testDrive.nick();
					
					aTestDrive4StatVO = new TestDrive4StatVO(new TestDriveVO(testDrive));
					MyBeanUtils.populate(aTestDrive4StatVO, properties);
					aTestDrive4StatVO.nick();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				}

				Company dealer = CacheCompanyManager.getCompany(aTestDrive4StatVO.getCompanyId());
				aTestDrive4StatVO.setDealer(new DealerVO(dealer));
				
				Vehicle vehicle = CacheVehicleManager.getVehicleById(aTestDrive4StatVO.getVehicleId());
				aTestDrive4StatVO.setVehicle(new VehicleVO(vehicle));
				
				testDrive4StatVOList.add(aTestDrive4StatVO);
			}
		}
		return testDrive4StatVOList;
	}
	
	/**
	 * 获取试驾中的线路  并缓存起来
	 */
	public TestDrive getTestDriveByVehicle(Integer vehicleId){
		Map map=new HashMap();
		map.put("vehicleId", vehicleId);
		map.put("currentStatus", 0);//试驾中
		StringBuffer additionalCondition=new StringBuffer("");
		additionalCondition.append(" order by saveDate desc");
		List<TestDrive> testDriveList =find(map,additionalCondition.toString());
		if(null!=testDriveList && !testDriveList.isEmpty()){
			return testDriveList.get(0);
		}
		return null;
	}
	
	/**
	 * 获取试驾中的线路  并缓存起来
	 */
	public TestDrive getTestDriveByVehicle(Integer vehicleId,String startDate){
		StringBuffer additionalCondition=new StringBuffer("");
		additionalCondition.append(" order by saveDate desc");
		List<TestDrive> testDriveList = hibernateDao.executeQueryList("from TestDrive where vehicleId='"+vehicleId+"' and currentStatus=0 " +
				"and startTime<='"+startDate+"' order by saveDate desc");
		if(null!=testDriveList && !testDriveList.isEmpty()){
			return testDriveList.get(0);
		}

		return null;
	}
	
	
	@Override
	public List<TestDrive> findByPage(TransMsg transMsg, String additionalCondition) {
		List<TestDrive> testDriveList = hibernateDao.findByPage("TestDrive", transMsg, additionalCondition);
		return testDriveList;
	}
	@Override
	public List<TestDrive> find(Map parameters, String additionalCondition) {
		List<TestDrive> testDriveList =  hibernateDao.find("TestDrive", parameters, additionalCondition);
		return testDriveList;
	}

	public List executeSQLQuery(String nativeSql) {
		return hibernateDao.executeSQLQueryList(nativeSql);
	}
	
	/**
	 * 修改指定车辆的试驾记录为无效记录（报备审批通过后的操作）修改状态为  5报备审批后删除的状态
	 *
	 * @author wangchao
	 * @version 
	 * @param filingProposalId
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public int bulkUpdateTestDrive4DeclareManage(Integer filingProposalId,Date startTime,Date endTime){
		String hql = "update TestDrive testDrive set testDrive.status = 5 where testDrive.vehicleId  in ( select fpd.vehicleId from FilingProposalDetail fpd where fpd.proposalId = ?) and testDrive.startTime >= ? and testDrive.endTime <= ?";
		return hibernateDao.bulkUpdate(hql, filingProposalId, startTime, endTime);
	}
}
