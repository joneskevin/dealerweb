package com.ava.dao.impl;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ava.base.dao.IHibernateDao;
import com.ava.base.dao.hibernate.TransMsg;
import com.ava.dao.ISurveyResultDao;
import com.ava.domain.entity.SurveyResult;
import com.ava.resource.GlobalConstant;

@Repository
public class SurveyResultDao implements ISurveyResultDao {
	
	@Autowired
	private IHibernateDao hibernateDao;

	public Integer save(SurveyResult surveyResult) {
		Integer objId = null;
		if (surveyResult != null) {
			try {
				objId = (Integer) hibernateDao.save(surveyResult);
			} catch (Exception e) {
			}
		}

		return objId;
	}

	public void delete(Integer id) {
		hibernateDao.delete(SurveyResult.class, id);
	}

	public void update(SurveyResult surveyResult) {
		hibernateDao.update(surveyResult);
	}
	
	public SurveyResult get(Integer id) {
		if (id == null) {
			return null;
		}
		SurveyResult surveyResul = (SurveyResult) hibernateDao.get(SurveyResult.class, id);
		return surveyResul;
	}
	

	public List<SurveyResult> find(HashMap<Object, Object> parameters, String additionalCondition) {
		List<SurveyResult> surveyResultList = hibernateDao.find("SurveyResult",
				parameters, additionalCondition);
		return surveyResultList;
	}

	public List<SurveyResult> findByPage(TransMsg msg, String additionalCondition) {
		List<SurveyResult> surveyResultList =  hibernateDao.findByPage("SurveyResult",
				msg, additionalCondition);
		return surveyResultList;
	}

}
