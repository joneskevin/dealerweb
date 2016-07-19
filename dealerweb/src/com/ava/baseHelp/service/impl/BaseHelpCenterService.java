package com.ava.baseHelp.service.impl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ava.base.dao.hibernate.TransMsg;
import com.ava.base.dao.util.ParameterUtil;
import com.ava.baseHelp.service.IBaseHelpCenterService;
import com.ava.dao.IHelpQuestionDao;
import com.ava.domain.entity.HelpQuestion;
import com.ava.resource.GlobalConstant;

@Service
public class BaseHelpCenterService implements IBaseHelpCenterService{
	
	@Autowired
	private IHelpQuestionDao helpQuestionDao;
	
	@Override
	public List searchQuestion(TransMsg transMsg, HttpServletRequest request, HelpQuestion question, Integer sortId) {
		transMsg.setPageSize(13);
		if (sortId != null && sortId.intValue() > 0) {
			transMsg.put("sortLevelId", sortId);
		}
		transMsg.put("type", GlobalConstant.HELP_BASE_QUESTION_TYPE);
		if (question.getTitle()!=null && question.getTitle().length()>0){
			ParameterUtil.appendLikeQueryCondition(transMsg.getParameters(), "title", question.getTitle());
		}
		
		List questions = helpQuestionDao.findByPage(transMsg, " ORDER BY id DESC");
		return questions;
	}

	@Override
	public HelpQuestion getQuestion(Integer questionId) {
		HelpQuestion question = helpQuestionDao.get(questionId);
		return question;
	}
}
