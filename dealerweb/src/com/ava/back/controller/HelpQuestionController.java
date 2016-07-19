package com.ava.back.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.ava.back.service.IHelpQuestionService;
import com.ava.base.dao.hibernate.TransMsg;
import com.ava.base.controller.Base4MvcController;
import com.ava.domain.entity.HelpQuestion;
import com.ava.resource.DbCacheResource;
import com.ava.resource.GlobalConstant;
import com.ava.util.TypeConverter;

@Controller
@RequestMapping("/back/question")
public class HelpQuestionController extends Base4MvcController{

	@Autowired
	private IHelpQuestionService service;

    @RequestMapping(value="/search.vti", method=RequestMethod.GET)
	public String searchQuestion(TransMsg transMsg, HelpQuestion question) {
		List questionList = service.searchQuestion(transMsg, question);
		getRequest().setAttribute("question", question);
		getRequest().setAttribute("questionList", questionList);
		
		List questionSortsLevel3IdList = DbCacheResource.getDataDictionaryOptionsForSingleSelect(GlobalConstant.HELP_BASE_QUESTION_LEVEL3);
		getRequest().setAttribute("questionSortsLevel3IdList", questionSortsLevel3IdList);
		return "/back/backContent/help/listQuestion";
	}

    @RequestMapping(value="/displayAdd.vti", method=RequestMethod.GET)
	public String displayAddQuestion() {
    	HelpQuestion question = new HelpQuestion();
		getRequest().setAttribute("question", question);
		
		List questionSortsLevel3IdList = DbCacheResource.getDataDictionaryOptionsForSingleSelect(GlobalConstant.HELP_BASE_QUESTION_LEVEL3);
		getRequest().setAttribute("questionSortsLevel3IdList", questionSortsLevel3IdList);
		
		return "/back/backContent/help/addQuestion";
	}

    @RequestMapping(value="/add.vti", method=RequestMethod.POST)
	public String addQuestion(@ModelAttribute("question") HelpQuestion question) {
		if (!checkFormForQuestion(question)) {
			return displayAddQuestion() ;	
		} else{
			int result = service.addQuestion(question);
			if(result == 1){
				message("新增BASE帮助成功！");
			}else{
				message("新增BASE帮助失败！");
			}
			return searchQuestion(new TransMsg(), null);
		}			
	
	}	

    @RequestMapping(value="/displayEdit.vti", method=RequestMethod.GET)
	public String displayEditQuestion(@RequestParam("questionId") Integer questionId) {
    	HelpQuestion question = service.getQuestion(questionId);
		getRequest().setAttribute("question", question);
		
		List questionSortsLevel3IdList = DbCacheResource.getDataDictionaryOptionsForSingleSelect(GlobalConstant.HELP_BASE_QUESTION_LEVEL3);
		getRequest().setAttribute("questionSortsLevel3IdList", questionSortsLevel3IdList);
		
		return "/back/backContent/help/editQuestion";
	}

    @RequestMapping(value="/edit.vti", method=RequestMethod.POST)
	public String editQuestion(@ModelAttribute("question") HelpQuestion question) {
		int result = service.editQuestion(question);
		if(result == 1){
		}else{
			message("修改BASE帮助失败！");
		}
		return searchQuestion(new TransMsg(), null);
	}

    @RequestMapping(value="/delete.vti", method=RequestMethod.GET)
	public String deleteQuestion(@RequestParam("questionId") Integer questionId) {
		service.deleteQuestion(questionId);
		return searchQuestion(new TransMsg(), null);
	}
	
	protected boolean checkFormForQuestion(HelpQuestion question) {
		String title = question.getTitle();
		if (!TypeConverter.sizeLagerZero(title)) {
			message("标题必须填写");
			return false;
		}
		
		String content = question.getContent();
		if (!TypeConverter.sizeLagerZero(content)) {
			message("内容必须填写");
			return false;
		}
		
		Integer sortLevelId = question.getSortLevelId();
		if (!TypeConverter.sizeLagerZero(sortLevelId)) {
			message("问题标识必须填写");
			return false;
		}

		return true;
	}

}
