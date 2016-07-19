package com.ava.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ava.base.dao.IHibernateDao;
import com.ava.base.dao.hibernate.TransMsg;
import com.ava.dao.IHelpQuestionDao;
import com.ava.domain.entity.HelpQuestion;

@Repository
public class HelpQuestionDao implements IHelpQuestionDao{
	@Autowired
	private IHibernateDao hibernateDao;
	
	public void edit(HelpQuestion question){
		hibernateDao.edit(question);
	}
	
	public void delete(HelpQuestion question){
		hibernateDao.delete(question);
	}
	
	public List findByPage(TransMsg msg, String additionalCondition){
		List<Object> objList = (List<Object>) hibernateDao.findByPage("HelpQuestion", msg, additionalCondition);
		return objList;
		
	}
	
	public HelpQuestion getHelpQuestionById(Integer helpQuestionId){
		if (helpQuestionId == null) {
			return null;
		}
		HelpQuestion helpQuestion = (HelpQuestion) hibernateDao.get(HelpQuestion.class, helpQuestionId);
		return helpQuestion;
	}
	
	public HelpQuestion get(Integer id){
		HelpQuestion helpQuestion = (HelpQuestion) hibernateDao.get(HelpQuestion.class, id);
		return helpQuestion;
	}
	
}
