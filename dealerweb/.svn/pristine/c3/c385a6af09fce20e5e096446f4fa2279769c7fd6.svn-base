package com.ava.dealer.service.impl;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.ava.dao.ISurveyCarStyleDao;
import com.ava.dealer.service.ISurveryCarStyleService;
import com.ava.domain.entity.SurveyCarStyle;

@Service
public class SurveyCarStyleService implements ISurveryCarStyleService {
	
	@Autowired
	private ISurveyCarStyleDao surveyCarStyleDao;
	
	public List<SurveyCarStyle> find(HashMap<Object, Object> parameters, String additionalCondition){
		return surveyCarStyleDao.find(parameters, additionalCondition);
	}
	public SurveyCarStyle get(Integer surveyCarStyleId) {
		SurveyCarStyle surveyCarStyle = surveyCarStyleDao.get(surveyCarStyleId);
		return surveyCarStyle;
	}

}
