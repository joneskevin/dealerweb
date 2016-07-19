package com.ava.dao;

import java.util.List;
import java.util.Map;
import com.ava.base.dao.hibernate.TransMsg;
import com.ava.domain.entity.FilingApproval;
import com.ava.domain.entity.FilingProposal;
import com.ava.domain.entity.FilingProposalAttachment;


public interface IDeclareManagerDao {
	
	public FilingProposal getFilingProposal(Integer id);
	
	public FilingProposalAttachment getFilingProposalAttachment(Integer id);
	
	public Integer save(FilingApproval filingApproval);
	
	public Integer save(FilingProposal filingProposal);
	
	public void saveFilingProposalAttachment(FilingProposalAttachment filingProposalAttachment);
	
	public void save(FilingProposal filingProposal,List<FilingProposalAttachment> filingProposalAttachmentList);
	
	public void delete(FilingProposal filingProposal);
	
	public void delete(FilingProposalAttachment filingProposalAttachment);
	
	public void edit(FilingProposal filingProposal,List<FilingProposalAttachment> filingProposalAttachmentList);
	
	public void merge(FilingProposal filingProposal,
			List<FilingProposalAttachment> filingProposalAttachmentList);
		
	public List<FilingProposalAttachment> getFilingProposalAttachment(FilingProposal filingProposal);
		
	public List<FilingProposal> find(Map parameters, String additionalCondition);
	
	public List<FilingProposal> findByPage(TransMsg msg, String additionalCondition);
	
	public List<FilingProposal> findByVehicleId(String hql);
}
