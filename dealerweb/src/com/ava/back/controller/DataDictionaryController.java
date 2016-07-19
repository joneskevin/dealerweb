package com.ava.back.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.ava.back.service.IDataDictionaryService;
import com.ava.base.domain.ResponseData;
import com.ava.base.controller.Base4MvcController;
import com.ava.domain.entity.DataDictionary;
import com.ava.resource.GlobalConstant;
import com.ava.resource.SessionManager;

@Controller
@RequestMapping("/back/dataDictionary")
public class DataDictionaryController extends Base4MvcController {
	@Autowired
	private IDataDictionaryService service;
	
    @RequestMapping(value="/list.vti", method=RequestMethod.GET)  
	public String list(@RequestParam("sortId") Integer sortId) {
		List dictonaries = service.list(sortId);
		getRequest().setAttribute("dictonaries", dictonaries);
		getRequest().setAttribute("sortId", sortId);
		return "/back/backSystem/dataDictionary/dataDictionarySet";
	}
    
	/**
	 * 显示组织树
	 * 
	 * @return String
	 */
	@RequestMapping(value="/displayTree.vti", method=RequestMethod.GET)
	public String displayTree(@RequestParam("sortId") Integer sortId) {
		ResponseData rd = service.displayTree(sortId);
		getRequest().setAttribute("dhtmlXtreeXML", rd.getFirstItem().toString());
		getRequest().setAttribute("sortId", sortId);
		
		return "/back/backSystem/dataDictionary/dataDictionarySet";
	}

    @RequestMapping(value="/add.vti", method=RequestMethod.POST) 
	public String add(@ModelAttribute("dataDictionary") DataDictionary dataDictionary){
    	Integer currentOperatorId = SessionManager.getCurrentOperatorId(getRequest());
    	ResponseData rd = service.add(currentOperatorId, dataDictionary);
    	
    	if (rd.getCode() == 1) {
			message(GlobalConstant.MSG_RESULT_LEVEL_SUCCESS,
					GlobalConstant.MSG_RESULT_CONTENT, rd.getMessage());
			return displayTree(dataDictionary.getSortId());

		} else {
			message(GlobalConstant.MSG_RESULT_LEVEL_ERROR,
					GlobalConstant.MSG_RESULT_CONTENT, rd.getMessage());
		}

		return displayAdd(dataDictionary);
    	
	}


    @RequestMapping(value="/displayAdd.vti", method=RequestMethod.GET) 
    public String displayAdd(@ModelAttribute("dataDictionary") DataDictionary dataDictionary){
    	Integer currentNodeId = getIntegerParameter("currentNodeId");
    	Integer levelId = getIntegerParameter("levelId");
    	Integer sortId = getIntegerParameter("sortId");
    	
    	if (levelId != null && levelId != GlobalConstant.FALSE) {
    		dataDictionary.setLevelId(levelId);
    	}
    	
    	if (sortId != null) {
    		dataDictionary.setSortId(sortId);
    	}
    	
    	ResponseData rd = service.displayAdd(dataDictionary, currentNodeId);
    	getRequest().setAttribute("dataDictionary", rd.get("dataDictionary"));
    	getRequest().setAttribute("currentNodeId", currentNodeId);
    	return "/back/backSystem/dataDictionary/addDataDictionary";
    }
    
    @RequestMapping(value="/displayAdd1.vti", method=RequestMethod.GET) 
    public String displayAdd1(@ModelAttribute("dataDictionary") DataDictionary dataDictionary){
    	Integer currentNodeId = getIntegerParameter("currentNodeId");
    	Integer levelId = getIntegerParameter("levelId");
    	if (currentNodeId == null || currentNodeId == 0) {
    		currentNodeId = dataDictionary.getParentId();
    	}
    	
    	if (levelId != null && levelId != GlobalConstant.FALSE) {
    		dataDictionary.setLevelId(levelId);
    	}
    	
    	ResponseData rd = service.displayAdd(dataDictionary, currentNodeId);
    	getRequest().setAttribute("dataDictionary", rd.get("dataDictionary"));
    	return "/back/backSystem/dataDictionary/addDataDictionary";
    }
    
    @RequestMapping(value="/displayEdit.vti", method=RequestMethod.GET) 
	public String displayEdit(@RequestParam("currentNodeId") Integer currentNodeId){
    	DataDictionary editedNode = service.displayEdit(currentNodeId);
		getRequest().setAttribute("editedNode", editedNode);
		getRequest().setAttribute("currentModificatorName", editedNode.getCurrentModificatorName());
		return "/back/backSystem/dataDictionary/editDataDictionary";
	}
    
    //TODO:继续探索SpringMVC组装对象参数

/*    @RequestMapping(value="/edit.vti", method=RequestMethod.POST)
    @ModelAttribute("editedNode")
	public String edit(DataDictionary editedNode) {
    	Integer currentOperatorId = SessionManager.getCurrentOperatorId(getRequest());
		int result = service.edit(currentOperatorId, editedNode);
		if (result == 1) {
			return list(111);
		}
		return displayEdit(222);		
	}*/

    @RequestMapping(value="/edit.vti", method=RequestMethod.POST)
	public String edit(@RequestParam("editedNodeId") Integer editedNodeId,
			@RequestParam("name") String name,
			@RequestParam("rank") Integer rank,
			@RequestParam("nodeComment") String nodeComment){
    	Integer currentOperatorId = SessionManager.getCurrentOperatorId(getRequest());
    	
    	DataDictionary editedNode = new DataDictionary();
    	editedNode.setId(editedNodeId);
    	editedNode.setName(name);
    	editedNode.setRank(rank);
    	editedNode.setComment(nodeComment);
    	ResponseData rd = service.edit(currentOperatorId, editedNode);
		if (rd.getCode() == 1) {
			DataDictionary theNode = (DataDictionary) rd.getFirstItem();
			Integer sortId = theNode.getSortId();
			return displayTree(sortId);
		}
		return displayEdit(editedNodeId);		
	}
    
    @RequestMapping(value="/delete.vti", method=RequestMethod.GET) 
    public void delete(@RequestParam("currentNodeId") Integer currentNodeId){
    	ResponseData rd = service.delete(currentNodeId);
    	writeRd(rd); 
    }
    
}
