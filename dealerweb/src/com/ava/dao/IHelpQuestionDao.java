package com.ava.dao;

import java.util.List;

import com.ava.base.dao.hibernate.TransMsg;
import com.ava.domain.entity.HelpQuestion;

public interface IHelpQuestionDao {
	
	public void edit(HelpQuestion question);
	
	public void delete(HelpQuestion question);
	
	public List findByPage(TransMsg msg, String additionalCondition);
	
	public HelpQuestion getHelpQuestionById(Integer helpQuestionId);
	
	public HelpQuestion get(Integer helpQuestionId);

}
