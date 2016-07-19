package com.ava.dao;

import java.util.List;
import java.util.Map;

import com.ava.base.dao.hibernate.TransMsg;
import com.ava.domain.entity.Company;
import com.ava.domain.entity.User;

public interface IUserDao {
	
	public void delete(Integer id);
	
	public void deleteByCompanyId(Integer companyId);

	public User createAdmin(Company company, String adminEmail, String adminPassword, String fpTemplate);
	
	public int save(User user);
	
	public void edit(User user);
	
	public User get(Integer userId);
	
	public User getByLoginName(String loginName);
	
	public User getByEmail(String email);
	
	public User getByMobile(String mobile);
	
	public List<User> getByPositionId(Integer positionId);
	
	public List<Integer> findIdsByCompanyId(Integer companyId);
	
	public String[] getNicksByPositionId(Integer positionId);
	
	public List findByPage(TransMsg msg, String additionalCondition);
	
	public List find(Map parameters, String additionalCondition);
	
	public List findAll(Map parameters, String additionalCondition);
	
	public List executeQueryList(String hql);
	
	public String executeQueryString(String hql);
	
	public int executeUpdate(String hql);
	
	public int executeQueryInt(String hql);
	
}
