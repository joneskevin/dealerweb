package com.ava.back.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ava.back.service.IOperatorService;
import com.ava.base.dao.hibernate.TransMsg;
import com.ava.base.dao.util.ParameterUtil;
import com.ava.dao.IOperatorDao;
import com.ava.domain.entity.Operator;
import com.ava.resource.DbCacheResource;
import com.ava.resource.GlobalConstant;
import com.ava.util.MyBeanUtils;
import com.ava.util.ReadTemplate;
import com.ava.util.TextUtil;

@Service
public class OperatorService implements IOperatorService {
	private static String[] protectedNames = new String[]{"administrator","admin"};
	
	@Autowired
	private IOperatorDao operatorDao;

	public int login(HttpServletRequest request, String loginName, String loginPassword){
		Operator operator = operatorDao.getByLoginNameAndPassword(loginName, loginPassword);
		if (operator == null) {
			//登录名或密码不正确
			return -1;
		} else {
			operator.processPermissionCode();
			request.getSession().setAttribute(GlobalConstant.SESSION_OPERATOR, operator);
			operator.setLastLoginTime(new java.util.Date());
			operatorDao.editOperator(operator);
			return 1;
		}
	}

	public int addOperator(Operator frmOperator){
		Map<Object,Object> parameters = new HashMap<Object,Object>();
		parameters.put("loginName", frmOperator.getLoginName());
		List list = operatorDao.find(parameters,"");
		if (!list.isEmpty()){
			return 2;
		}
		operatorDao.editOperator(frmOperator);
		//清空缓存中的管理员列表，让他重新读取
		DbCacheResource.setWholeOperators(null);
		return 1;
	}
	
	public int deleteOperator(Integer operatorId){
		Operator operator = operatorDao.getOperator(operatorId);
		if (operator != null && operator.getLoginName() !=null) {
			boolean hasConflict = false;
			for(int i=0;i<protectedNames.length;i++){
				String name = protectedNames[i];
				if (name.equalsIgnoreCase(operator.getLoginName())) {
					hasConflict = true;
				}
			}
			if (!hasConflict) {
				operatorDao.delete(operator);
			}else{
				return -1;//冲突
			}
		}		
		return 1;
	}
	
	public int editPassword(String oldPassword, String newPassword, Operator currentUser){
		if(!oldPassword.equalsIgnoreCase(currentUser.getLoginPassword())){
			//info:旧密码不对
			return -1;
		}
		currentUser.setLoginPassword(newPassword);
		operatorDao.editOperator(currentUser);
		
		return 1;
	}
	
	public Operator getOperator(Integer operatorId){
		Operator operator = operatorDao.getOperator(operatorId);
		return operator;
	}	
	
	public int editOperator(Operator frmOperator){
		Operator operator = operatorDao.getOperator(frmOperator.getId());
		MyBeanUtils.copyPropertiesContainsDate(operator, frmOperator);
		
		operatorDao.editOperator(operator);

		//清空缓存中的管理员列表，让他重新读取
		DbCacheResource.setWholeOperators(null);
		return 1;
	}
	
	public String displayGrant(HttpServletRequest request){
		String xml = TextUtil.disableBreakLineTag(ReadTemplate
				.getOperatorRightsCodeContent(""));
		return xml;
	}
	
	public List searchOperator(TransMsg transMsg, Operator currentOperator){
		if(!"administrator".equalsIgnoreCase(currentOperator.getLoginName())){//登录名不为“administrator”的看不到超级管理员记录
			ParameterUtil.appendNotEqualQueryCondition(transMsg.getParameters(), "loginName", "administrator");
		}
		List operators = operatorDao.findByPage(transMsg,"order by id asc");
		return operators;
	}
}
