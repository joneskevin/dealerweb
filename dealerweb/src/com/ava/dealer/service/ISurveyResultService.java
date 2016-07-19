package com.ava.dealer.service;

import java.util.HashMap;
import java.util.List;

import com.ava.base.dao.hibernate.TransMsg;
import com.ava.base.domain.ResponseData;
import com.ava.domain.entity.Company;
import com.ava.domain.entity.SurveyResult;
import com.ava.domain.vo.SurveyResultStaticVO;
import com.ava.domain.vo.SurveyResultVO;

public interface ISurveyResultService {
	
	/**
	 * 问卷调查（页面动态车型数据）
	 * @author liuq 
	 * @version 0.1 
	 * @param surveyResultAdd
	 * @param company
	 * @return
	 */
	public ResponseData displayAddSurveyResult(SurveyResultVO surveyResultAdd, Company company);
	
	/**
	 * 问卷调查（页面静态车型数据）
	 * @author liuq 
	 * @version 0.1 
	 * @param surveyResultStaticVO
	 * @param company
	 * @return
	 */
	public ResponseData displayAddSurveyResultStatic(SurveyResultStaticVO surveyResultStaticVO, Company company);

	public ResponseData add(SurveyResultVO surveyResult, Integer companyId);
	
	public ResponseData querySurveyLevel(Integer companyId);
	
	public ResponseData addSurveyLevel(Integer companyId);
	
	public ResponseData addSurveyResultStatic(SurveyResultStaticVO surveyResultStaticVO, Integer companyId);

	public ResponseData delete(Integer surveyResultId, Integer companyId);

	public ResponseData edit(SurveyResult surveyResult, Integer companyId, Integer flag);

	public ResponseData listSurveyResult(TransMsg transMsg, SurveyResultVO surveyResultVO, Integer companyId, Integer userId, boolean isExport);
	
	public List<SurveyResult> find(HashMap<Object, Object> parameters, String additionalCondition);
	
	public SurveyResult getSurveyResult(Integer surveyResultId);
	
	/**
	 * 删除调研结果
	 * @author liuq 
	 * @version 0.1 
	 * @param companyId
	 * @param userId 
	 * @return
	 */
	public ResponseData deleteSurveyResult(Integer companyId, Integer userId);
	
}
