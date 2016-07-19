package com.ava.back.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ava.back.service.IHelpQuestionService;
import com.ava.base.dao.hibernate.TransMsg;
import com.ava.base.dao.util.ParameterUtil;
import com.ava.dao.IHelpQuestionDao;
import com.ava.domain.entity.HelpQuestion;
import com.ava.resource.GlobalConstant;
import com.ava.util.MyBeanUtils;

@Service
public class HelpQuestionService implements IHelpQuestionService{

	@Autowired
	private IHelpQuestionDao helpQuestionDao;
	
	public List searchQuestion(TransMsg transMsg, HelpQuestion question){
		if (question != null) {
			ParameterUtil.appendLikeQueryCondition(transMsg.getParameters(), "title", question.getTitle());
			
			if (question.getSortLevelId() != null && question.getSortLevelId().intValue() >= 0) {
				transMsg.put("sortLevelId", question.getSortLevelId());
			}
		}
		
		List questions = helpQuestionDao.findByPage(transMsg, " ORDER BY id DESC ");
		return questions;
	}
	
	public HelpQuestion getQuestion(Integer questionId){
		HelpQuestion question = helpQuestionDao.get(questionId);
		return question;
	}
	
	public int addQuestion(HelpQuestion question){
		question.setType(GlobalConstant.HELP_BASE_QUESTION_TYPE);
		helpQuestionDao.edit(question);
		
		return 1;
	}
	
	public int editQuestion(HelpQuestion question){
		Integer helpQuestionId = question.getId();
		if (helpQuestionId == null) {
			return 0;
		}
		HelpQuestion dbhelpQuestion = helpQuestionDao.getHelpQuestionById(helpQuestionId);
		MyBeanUtils.copyPropertiesContainsDate(dbhelpQuestion, question);
		helpQuestionDao.edit(dbhelpQuestion);
		return 1;
	}

	public int deleteQuestion(Integer questionId){
		if (questionId  != null && questionId.intValue() > 0) {
			HelpQuestion helpQuestion = helpQuestionDao.getHelpQuestionById(questionId);
			helpQuestionDao.delete(helpQuestion);
			return 1;
		}
		return 0;
	}
}
