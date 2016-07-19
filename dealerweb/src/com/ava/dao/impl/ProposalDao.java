package com.ava.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ava.base.dao.IHibernateDao;
import com.ava.base.dao.hibernate.TransMsg;
import com.ava.dao.IProposalDao;
import com.ava.domain.entity.Approval;
import com.ava.domain.entity.FilingProposalAttachment;
import com.ava.domain.entity.Proposal;
import com.ava.domain.entity.ProposalAttachment;

@Repository
public class ProposalDao implements IProposalDao{
	@Autowired
	private IHibernateDao hibernateDao;

	public Integer save(Proposal proposal){
		return (Integer) hibernateDao.save(proposal);
	}
	
	/**	删除申请单后还会自动删除关联的审批项	*/
	public void delete(Integer proposalId){
		hibernateDao.delete(Proposal.class, proposalId);
		//删除申请单后还会自动删除关联的审批项
		HashMap parameters = new HashMap();
		parameters.put("proposalId", proposalId);
		
		List<Object> itemList = (List<Object>) hibernateDao.find("Approval", parameters);
		if (itemList != null) {
			Iterator itor = itemList.iterator();
			while (itor.hasNext()) {
				Approval item = (Approval) itor.next();
				hibernateDao.delete(item);
			}
		}
		
	}

	public void deleteByCompanyId(Integer companyId){
		HashMap<Object, Object> parameters = new HashMap<Object, Object>();
		parameters.put("orgId", companyId);
		List objList = hibernateDao.find("Proposal", parameters, "");
		if (objList != null && objList.size() > 0) {
			Iterator itor = objList.iterator();
			while(itor.hasNext()){
				Proposal approProposal = (Proposal) itor.next();
				delete(approProposal.getId());
			}
		}
	}
	
	public void edit(Proposal proposal){
		hibernateDao.edit(proposal);
	}

	public List<Proposal> find(HashMap parameters, String additionalCondition){
		List objList = hibernateDao.find("Proposal", parameters, additionalCondition);
		return objList;
	}
	
	public List findByPage(TransMsg msg, String additionalCondition){
		List<Object> objList = (List<Object>) hibernateDao.findByPage("Proposal", msg,
				additionalCondition);
		return objList;
		
	}
	public Proposal get(Integer proposalId){
		if (proposalId == null) {
			return null;
		}
		Proposal approProposal = (Proposal) hibernateDao.get(Proposal.class, proposalId);
		return approProposal;
	}
	
	public List<Proposal> getUsingByFlowPathId(Integer flowPathId){
		if (flowPathId == null) {
			return null;
		}
		HashMap parameters = new HashMap();
		parameters.put("currentFlowPathId", flowPathId);
		String hql = "";
		//String hql = "and approvalStatus in(" + GlobalConstant.PROPOSAL_STATUS_REJECTED + "," + GlobalConstant.PROPOSAL_STATUS_PROCESSING + ")";
		List<Proposal> proposals = find(parameters, hql);
		return proposals;
	}
	
	@Override
	public List<Proposal> findByVehicleId(String hql) {
		List<Proposal> proposalList = new ArrayList<Proposal>();
		if (null != hql && !"".equals(hql)) {
			proposalList = hibernateDao.excuteQuerystring(hql);
		}
		return proposalList;
	}

	@Override
	public List<ProposalAttachment> getProposalAttachmentList(Integer proposalId) {
		List<ProposalAttachment> proposalAttachmentList = null;
		if(proposalId != null){
			HashMap<String,Integer> proposalAttachmentMap = new HashMap<String,Integer>();
			proposalAttachmentMap.put("proposalId", proposalId);
			proposalAttachmentList=hibernateDao.find("ProposalAttachment", proposalAttachmentMap);
		}
		return proposalAttachmentList;
	}

	@Override
	public Integer saveProposalAttachment(ProposalAttachment proposalAttachment) {
		return (Integer) hibernateDao.save(proposalAttachment);
	}

	@Override
	public void deleteProposalFile(Integer proposalId, String delFiles) {
		StringBuffer stringBuffer=new StringBuffer("");
		stringBuffer.append(" delete from t_proposal_attachment  where proposal_id = ").append(proposalId);
		stringBuffer.append(" and id in(").append(delFiles).append(")");
		hibernateDao.executeSQLUpdate(stringBuffer.toString());
		
	}

	@Override
	public ProposalAttachment getProposalAttachment(HashMap<Object, Object> parameters) {
		return hibernateDao.get("ProposalAttachment", parameters);
	}
}
