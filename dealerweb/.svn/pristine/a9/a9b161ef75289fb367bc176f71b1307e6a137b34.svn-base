package com.ava.back.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.ava.base.dao.hibernate.TransMsg;
import com.ava.domain.entity.Operator;

public interface IOperatorService {
	public int login(HttpServletRequest request, String loginName, String loginPassword);

	public int addOperator(Operator frmOperator);
	
	public int deleteOperator(Integer operatorId);
	
	public int editPassword(String oldPassword, String newPassword, Operator currentUser);
	
	public Operator getOperator(Integer operatorId);
	
	public int editOperator(Operator frmOperator);
	
	public String displayGrant(HttpServletRequest request);
	
	public List searchOperator(TransMsg transMsg, Operator currentOperator);
}
