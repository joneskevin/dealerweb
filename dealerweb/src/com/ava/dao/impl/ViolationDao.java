package com.ava.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ava.base.dao.IHibernateDao;
import com.ava.base.dao.hibernate.TransMsg;
import com.ava.dao.IViolationDao;
import com.ava.domain.entity.TestNoDriveWeek;
import com.ava.domain.entity.Violation;

@Repository
public class ViolationDao implements IViolationDao {
	@Autowired
	private IHibernateDao hibernateDao;
	
	public Integer saveTestNoDriveWeek(TestNoDriveWeek testNoDriveWeek){
		return (Integer) hibernateDao.save(testNoDriveWeek);
	}
	
	public Integer saveViolation(Violation violation){
		return (Integer) hibernateDao.save(violation);
	}
	
	public void updateViolation(Violation violation){
		hibernateDao.update(violation);
	}
	
	@Override
	public List find(HashMap parameters, String additionalCondition) {
		List<Object> objList = (List<Object>) hibernateDao.find("Violation", parameters, additionalCondition);
		return objList;
	}
	@Override
	public Violation getByVechicle(Integer vechicleId) {
		if (vechicleId == null ) {
			return null;
		}
		HashMap<Object, Object> parameters = new HashMap<Object, Object>();
		parameters.put("vehicleId", vechicleId);
		List<Violation> violations = find(parameters, "");
		if (violations != null && violations.size() > 0) {
			return (Violation) violations.get(0);
		}
		return null;
	}
	@Override
	public List findByPage(TransMsg msg, String additionalCondition) {
		List<Object> objList = (List<Object>) hibernateDao.findByPage("Violation", msg, additionalCondition);
		return objList;
	}
	public List queryByWeek(String excuteSql) {
		return hibernateDao.executeSQLQueryList(excuteSql);
//		return	 hibernateDao.executeSQLQueryList(excuteSql, ViolationVO.class);
	}
	
	@Override
	public List<TestNoDriveWeek> listTestNoDriveWeek(TransMsg msg, String additionalCondition) {
		List<TestNoDriveWeek> testNoDriveWeekList = hibernateDao.findByPage("TestNoDriveWeek", msg, additionalCondition);
		return testNoDriveWeekList;
	}
	
	@Override
	public <T> List<T>  exeSqlQueryList(String exeSql,Class classz){
		List<T> list=hibernateDao.executeSQLQueryVoList(exeSql,classz);
		return list;
	}

	@Override
	public int bulkUpdateViolation4DeclareManage(Integer filingProposalId,
			Date startTime, Date endTime) {
		String hql = "update Violation violation set violation.deletionFlag = 1 where violation.vehicleId in ( select fpd.vehicleId from FilingProposalDetail fpd where fpd.proposalId = ?) and violation.start_Time >= ? and violation.end_Time <= ?";
		return hibernateDao.bulkUpdate(hql, filingProposalId, startTime, endTime);
	}
}
