package com.ava.baseHelp.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.ava.base.dao.hibernate.TransMsg;
import com.ava.domain.entity.HelpQuestion;


public interface IBaseHelpCenterService {	
	
	/**
	 * 查询问题列表
	 * @param request
	 * @param question
	 * @return
	 */
	public List searchQuestion(TransMsg transMsg, HttpServletRequest request, HelpQuestion question, Integer sortId);
	
	/**
	 * 查询一条问题记录
	 * @param questionId
	 * @return
	 */
	public HelpQuestion getQuestion(Integer questionId);
}
