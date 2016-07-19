package com.ava.dao;

import java.util.HashMap;
import java.util.List;

import com.ava.base.dao.hibernate.TransMsg;
import com.ava.domain.entity.SurveyResult;


public interface ISurveyResultDao {
	
	public Integer save(SurveyResult surveyResult);
	
	public void delete(Integer id);
	
	public void update(SurveyResult surveyResult);
	
	public SurveyResult get(Integer id);
	
	public List<SurveyResult> find(HashMap<Object, Object> parameters, String additionalCondition);
	
	public List<SurveyResult> findByPage(TransMsg msg, String additionalCondition);
}
