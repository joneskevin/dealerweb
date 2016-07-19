package com.ava.baseHelp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.ava.base.controller.Base4MvcController;
import com.ava.base.dao.hibernate.TransMsg;
import com.ava.baseHelp.service.IBaseHelpCenterService;
import com.ava.domain.entity.HelpQuestion;


/**帮助问题控制器类*/
@Controller
@RequestMapping("/base/question")
public class BaseHelpQuestionController extends Base4MvcController{

	@Autowired
	private IBaseHelpCenterService service;

	/**
	 * 显示所有的问题标识
	 */
    @RequestMapping(value="/helpCenter.vti", method=RequestMethod.GET)
	public String displayHelpCenter(@ModelAttribute("question") HelpQuestion question) {
    	getRequest().setAttribute("question", question);
		return "/dealer/help/helpBaseCenter";
	}

    /** 
     * 显示问题列表
     */
    @RequestMapping(value="/search.vti", method=RequestMethod.GET)
	public String searchQuestion(TransMsg transMsg, @ModelAttribute("question") HelpQuestion question) {
    	Integer sortId = getIntegerParameter("sortId");
		List questionList = service.searchQuestion(transMsg, getRequest(), question, sortId);
		getRequest().setAttribute("question", question);
		getRequest().setAttribute("questionList", questionList);
		getRequest().setAttribute("transMsg", transMsg);
		
		return "/dealer/help/baseQuestionList";
	}
    
    /**
     *  显示问题详细页
     */
    @RequestMapping(value="/view.vti", method=RequestMethod.GET)
	public String viewQuestion(@RequestParam("questionId") Integer questionId) {
    	HelpQuestion question = service.getQuestion(questionId);
		getRequest().setAttribute("question", question);
		
		return "/dealer/help/baseQuestionDetail";
	}
    
    /**
     *  显示问题详细页
     */
    @RequestMapping(value="/linkOur.vti", method=RequestMethod.GET)
	public String linkOur() {
    			
		return "/dealer/help/linkOur";
	}
}
