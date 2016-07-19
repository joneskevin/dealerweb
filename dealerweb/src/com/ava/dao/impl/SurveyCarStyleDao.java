package com.ava.dao.impl;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ava.base.dao.IHibernateDao;
import com.ava.dao.ISurveyCarStyleDao;
import com.ava.domain.entity.SurveyCarStyle;
import com.ava.resource.GlobalConstant;

@Repository
public class SurveyCarStyleDao implements ISurveyCarStyleDao {
	
	@Autowired
	private IHibernateDao hibernateDao;

	public void delete(Integer id) {
		hibernateDao.delete(SurveyCarStyle.class, id);
	}

	public void update(SurveyCarStyle surveyCarStyle) {
		hibernateDao.update(surveyCarStyle);
	}
	
	public SurveyCarStyle get(Integer id) {
		if (id == null) {
			return null;
		}
		SurveyCarStyle org = (SurveyCarStyle) hibernateDao.get(SurveyCarStyle.class, id);
		return org;
	}
	

	public List<SurveyCarStyle> find(HashMap<Object, Object> parameters, String additionalCondition) {
		List<SurveyCarStyle> surveyCarStyleList = hibernateDao.find("SurveyCarStyle", parameters, additionalCondition);
		return surveyCarStyleList;
	}

}
