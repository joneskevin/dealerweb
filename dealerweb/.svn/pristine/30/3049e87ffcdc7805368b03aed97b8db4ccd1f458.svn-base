package com.ava.dao;

import java.util.List;
import java.util.Map;

import com.ava.base.dao.hibernate.TransMsg;
import com.ava.base.domain.ResponseData;
import com.ava.domain.entity.Org;

public interface IOrgDao {
	
	public void edit(Org org);
	
	public List getAllWithoutNickInvoke();
	
	public Org get(Integer orgId);

	public Org getByName(String Name);
	
	public List findByPage(TransMsg msg, String additionalCondition);
	
	public List find(Map parameters, String additionalCondition);
	
	/**
	 * 验证集团管理员对分公司的管理权限
	 * @param departmentId 部门编号
	 * @return ResponseData
	 */
	public ResponseData organizationCompetence(int departmentId);
	
}
