package com.ava.dao;

import java.util.List;
import java.util.Map;

import com.ava.domain.entity.Approval;

public interface IApprovalDao {
	
	public void save(Approval approval);
	
	public Approval get(Integer itemId);
	
	/**返回最新创建的那条记录**/
	public Approval getLastByProposalId(Integer proposalId);
	
	public List<Approval> getByProposalId(Integer proposalId);
	
	public List find(Map parameters, String additionalCondition);

	
}
