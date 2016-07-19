package com.ava.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ava.base.dao.IHibernateDao;
import com.ava.base.dao.hibernate.TransMsg;
import com.ava.dao.IOperatorDao;
import com.ava.domain.entity.Operator;

@Repository
public class OperatorDao implements IOperatorDao{
	@Autowired
	private IHibernateDao hibernateDao;

	public Operator getOperator(Integer id) {
		Operator operator = (Operator) hibernateDao.get(Operator.class, id);
        return operator;
	}

	public void editOperator(Operator operator) {
		hibernateDao.edit(operator);
	}

	
	public void delete(Operator operator) {
		hibernateDao.delete(operator);
	}

	public Operator getByLoginNameAndPassword(String name,String pass) {
		if (name==null || pass==null || "".equals(name) || "".equals(pass)){
			return null;
		}
		Map<Object,Object> parameters = new HashMap<Object,Object>();
		parameters.put("loginName", name);
		parameters.put("loginPassword", pass);
		List list = hibernateDao.find("Operator",parameters);
		if (list == null || list.isEmpty()){
			return null;
		}
		return ( Operator ) list.get(0);
	}

	public Operator getByLoginName(String name) {
		if (name==null || name.length()==0 ){
			return null;
		}
		Map<Object,Object> parameters = new HashMap<Object,Object>();
		parameters.put("loginName", name);
		List list = hibernateDao.find("Operator",parameters);
		if (list.isEmpty()){
			return null;
		}
		return ( Operator ) list.get(0);
	}
	
	public List getAll(){
		return hibernateDao.getAll(Operator.class);
	}
	
	public List find(Map parameters,String additionalCondition) {
		List<Object> objList = (List<Object>) hibernateDao.find("Operator", parameters,additionalCondition);
		return objList;
	}
	
	public List findByPage(TransMsg transMsg,String additionalCondition){

		List<Object> objList = (List<Object>) hibernateDao.findByPage("Operator", transMsg,additionalCondition);
		return objList;
	}
}
