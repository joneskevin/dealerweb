package com.ava.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ava.base.dao.IHibernateDao;
import com.ava.dao.IApprovalDao;
import com.ava.domain.entity.Approval;

@Repository
public class ApprovalDao implements IApprovalDao{
	@Autowired
	private IHibernateDao hibernateDao;
	
	public void save(Approval approval){
		hibernateDao.save(approval);
	}
	
	public Approval get(Integer itemId){
		if (itemId == null) {
			return null;
		}
		Approval approval = (Approval) hibernateDao.get(Approval.class, itemId);
		return approval;
	}
	
	public Approval getLastByProposalId(Integer proposalId){
		if (proposalId == null) {
			return null;
		}
		HashMap<Object, Object> parameters = new HashMap<Object, Object>();
		parameters.put("proposalId", proposalId);
		List approvals = find(parameters, "order by id desc");
		if (approvals != null && approvals.size() > 0) {
			return (Approval) approvals.get(0);
		}
		return null;
	}
	
	public List<Approval> getByProposalId(Integer proposalId){
		Map parameters = new HashMap();
		parameters.put("proposalId", proposalId);
		List approvalList = find(parameters,
				" ORDER BY proposalId ASC");
		return approvalList;
	}
	
	public List find(Map parameters, String additionalCondition) {
		List<Object> objList = (List<Object>) hibernateDao.find("Approval", parameters, additionalCondition);
		return objList;
	}
}
