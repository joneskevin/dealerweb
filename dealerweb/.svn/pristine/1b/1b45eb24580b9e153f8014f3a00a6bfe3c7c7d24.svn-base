package com.ava.dao;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import com.ava.base.dao.hibernate.TransMsg;
import com.ava.domain.entity.TestNoDriveWeek;
import com.ava.domain.entity.Violation;

public interface IViolationDao {
	
	public Integer saveTestNoDriveWeek(TestNoDriveWeek testNoDriveWeek);
	
	public Integer saveViolation(Violation violation);
	
	public void updateViolation(Violation violation);
	/*
	 * 查询违规明细
	 */
	public List find(HashMap parameters, String additionalCondition);
	
	public Violation getByVechicle(Integer vechicleId);
	
	public List findByPage(TransMsg msg, String additionalCondition);
	
	public List queryByWeek(String excutesSql) ;
	
	public List<TestNoDriveWeek> listTestNoDriveWeek(TransMsg msg, String additionalCondition);
	
	public <T> List<T>  exeSqlQueryList(String exeSql,Class classz);

	/**
	 * 修改指定车辆的违规记录为无效记录（报备审批通过后的操作）
	 *
	 * @author wangchao
	 * @version 
	 * @param filingProposalId
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public int bulkUpdateViolation4DeclareManage(Integer filingProposalId,
			Date startTime, Date endTime);
}
