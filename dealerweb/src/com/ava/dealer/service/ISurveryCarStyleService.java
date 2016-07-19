package com.ava.dealer.service;

import java.util.HashMap;
import java.util.List;

import com.ava.domain.entity.SurveyCarStyle;

public interface ISurveryCarStyleService {
	
	public List<SurveyCarStyle> find(HashMap<Object, Object> parameters, String additionalCondition);
	
	public SurveyCarStyle get(Integer surveyCarStyleId);
	
}
