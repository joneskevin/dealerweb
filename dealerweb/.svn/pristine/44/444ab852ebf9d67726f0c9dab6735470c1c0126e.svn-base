package com.ava.facade.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ava.base.dao.hibernate.TransMsg;
import com.ava.base.domain.ResponseData;
import com.ava.baseSystem.service.IUserMenuService;
import com.ava.dao.ICompanyDao;
import com.ava.dao.IUserDao;
import com.ava.domain.entity.Company;
import com.ava.domain.entity.User;
import com.ava.facade.IHomepage4BaseFacade;

@Service
public class Homepage4BaseFacade implements IHomepage4BaseFacade {
	
	@Autowired
	private ICompanyDao companyDao;
	
	@Autowired
	private IUserDao userDao;

	public ResponseData displayMain(TransMsg transMsg, Integer companyId, Integer userId, HttpServletRequest request) {
		ResponseData rd = new ResponseData(0);
		Map data = new HashMap();
		rd.setData(data);
		User currentUser = userDao.get(userId);
		
		//所在组织
		Company company = companyDao.get(companyId);
		data.put("company", company);
		
				
		List proposals = null;
		
		//待审批事项
    	List unapprovalList = null;//proposalService.listUnapprovalProposal(new TransMsg(), companyId, userId);
		data.put("unapprovalList", unapprovalList);
		
		return rd;
	}
}
