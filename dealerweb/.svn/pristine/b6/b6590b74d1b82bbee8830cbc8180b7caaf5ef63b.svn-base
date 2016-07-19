package com.ava.back.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.ava.back.service.IOperatorService;
import com.ava.base.dao.hibernate.TransMsg;
import com.ava.base.controller.Base4MvcController;
import com.ava.domain.entity.Operator;
import com.ava.resource.GlobalConstant;
import com.ava.resource.SessionManager;
import com.ava.util.TypeConverter;

@Controller
@RequestMapping("/back/operator")
public class OperatorController extends Base4MvcController{
	@Autowired
	private IOperatorService service;
	
    @RequestMapping(value="/login.vti", method=RequestMethod.POST)  
	public String login(@RequestParam("loginName") String loginName, @RequestParam("loginPassword") String loginPassword) {
		int result = service.login(getRequest(), loginName, loginPassword);
		if (result == 1){
			return "/back/backHome";
		}else{
			message("登录名或密码不正确");
		}
		return "/back/login";
	}
    
    @RequestMapping(value="/logout.vti", method=RequestMethod.GET)  
	public String logout() {
    	getRequest().getSession().removeAttribute(GlobalConstant.SESSION_OPERATOR);
		return "/back/login";
	}

    @RequestMapping(value="/delete.vti", method=RequestMethod.GET) 
	public String delete(@RequestParam("operatorId") Integer operatorId){
		int result = service.deleteOperator(operatorId);
		if(result == -1){
			message("不能删除保留的操作员！");

			return search(new TransMsg());
		}
		message("删除操作员成功！");

		return search(new TransMsg());

	}

    @RequestMapping(value="/displayAdd.vti", method=RequestMethod.GET)  
	public String displayAdd() {
    	Operator operator = new Operator();
		getRequest().setAttribute("frmOperator", operator);
		
		return "/back/backSystem/operator/addOperator";
	}

    @RequestMapping(value="/add.vti", method=RequestMethod.POST)
	public String add(@ModelAttribute("frmOperator") Operator frmOperator) {
		if (!checkFormForEdit(frmOperator)) {
			return displayAdd();
		}
		int result = service.addOperator(frmOperator);
		if(result == 2){
			message("该登录名已经存在，请使用其它登录名注册！");
			return displayAdd();
		}
		message("新增操作员成功！");

		return search(new TransMsg());
	}

    @RequestMapping(value="/displayEdit.vti", method=RequestMethod.GET) 
	public String displayEdit(@RequestParam("operatorId") Integer operatorId){
    	Operator operator = service.getOperator(operatorId);
    	getRequest().setAttribute("frmOperator", operator);

		return "/back/backSystem/operator/editOperator";
	}

    @RequestMapping(value="/edit.vti", method=RequestMethod.POST)
	public String edit(Operator frmOperator) {
		if (!checkFormForEdit(frmOperator)) {
			return displayEdit(frmOperator.getId());
		}
		int result = service.editOperator(frmOperator);

		return search(new TransMsg());
	}

    @RequestMapping(value="/displayEditPassword.vti", method=RequestMethod.GET)
	public String displayEditPassword(){
		return "/back/backSystem/operator/editPassword";
	}

    @RequestMapping(value="/editPassword.vti", method=RequestMethod.POST)
	public String editPassword(@RequestParam("oldPassword") String oldPassword, 
			@RequestParam("newPassword1") String newPassword1, 
			@RequestParam("newPassword2") String newPassword2){
		if (!checkFormForEditPassword(oldPassword, newPassword1, newPassword2)) {
			return displayEditPassword();
		}
		Operator currentUser = SessionManager.getCurrentOperator(getRequest());
		int result = service.editPassword(oldPassword, newPassword1, currentUser);
		if(result == -1){
			message("输入的原密码不对！");			
		}
		message("修改密码成功！");
		return displayEditPassword();
	}

    @RequestMapping(value="/displayGrant.vti", method=RequestMethod.GET) 
	public String displayGrant(@RequestParam("operatorId") Integer operatorId){
    	Operator operator = service.getOperator(operatorId);
    	getRequest().setAttribute("operator", operator);
    	
    	String rightsTreeXml = service.displayGrant(getRequest());
    	getRequest().setAttribute("rightsTreeXml", rightsTreeXml);
    	
		return "/back/backSystem/operator/grantRight";
	}

    @RequestMapping(value="/grant.vti", method=RequestMethod.POST)
	public String grant(@RequestParam("operatorId") Integer operatorId, @RequestParam("permissionCode") String permissionCode){

		Operator frmOperator = new Operator();
		frmOperator.setId(operatorId);
		frmOperator.setPermissionCode(permissionCode);
		
		int result = service.editOperator(frmOperator);
		
		TransMsg transMsg = new TransMsg();
		return search(transMsg);
	}

    @RequestMapping(value="/search.vti", method=RequestMethod.GET)  
	public String search(TransMsg transMsg){
    	HttpServletRequest request = getRequest();
    	transMsg.setPageSize(10);
    	Operator currentOperator = SessionManager.getCurrentOperator(request);
		List operators = service.searchOperator(transMsg, currentOperator);
		request.setAttribute("operators", operators);
		return "/back/backSystem/operator/listOperator";
	}

	/**	注册页面的表单检查	 */
	protected boolean checkFormForEdit(Operator operator) {
		if (!TypeConverter.sizeLagerZero(operator.getLoginName())) {
			message("登录名没有填写！");
			return false;
		}
		if (!TypeConverter.sizeLagerZero(operator.getLoginPassword())) {
			message("登录密码没有填写！");
			return false;
		}
		if (!TypeConverter.sizeLagerZero(operator.getName())) {
			message("真实姓名没有填写！");
			return false;
		}
		return true;
	}

	/**	注册页面的表单检查	 */
	protected boolean checkFormForEditPassword(String oldPassword, String newPassword1, String newPassword2) {
		if (!TypeConverter.sizeLagerZero(oldPassword)) {
			message("原密码没有填写！");
			return false;
		} 

		if (!TypeConverter.sizeLagerZero(newPassword1)) {
			message("新密码没有填写！");
			return false;
		} 

		if (!TypeConverter.sizeLagerZero(newPassword2)) {
			message("确认密码没有填写！");
			return false;
		} 
		if (!newPassword1.equalsIgnoreCase(newPassword2)) {
			message("新密码和确认密码不一致！");
			return false;
		} 
		return true;
	}
}
