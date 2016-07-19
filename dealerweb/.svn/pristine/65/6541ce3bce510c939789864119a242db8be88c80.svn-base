
package com.ava.dao;

import java.util.List;
import java.util.Map;

import com.ava.base.dao.hibernate.TransMsg;
import com.ava.domain.entity.Operator;

public interface IOperatorDao  {

	public Operator getOperator(Integer id);
	
	public void editOperator(Operator opr);
	
	public void delete(Operator opr);
	
	public Operator getByLoginNameAndPassword(String name,String pass);
	
	public Operator getByLoginName(String name);

	public List getAll();
	
	public List find(Map parameters,String additionalCondition);
	
	public List findByPage(TransMsg transMsg,String additionalCondition);
	
}
