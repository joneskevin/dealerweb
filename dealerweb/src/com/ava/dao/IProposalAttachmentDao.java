package com.ava.dao;

import java.util.List;

import com.ava.base.dao.hibernate.TransMsg;
import com.ava.domain.entity.ProposalAttachment;

public interface IProposalAttachmentDao {
	
	public void edit(ProposalAttachment attachement);
	
	/**	删除附件后还会自动删除硬盘上的文件	*/
	public void delete(Integer id);

	public ProposalAttachment get(Integer id);
	
	public List getByProposerIdAndCreatorId(Integer proposalId, Integer creatorId);
	
	public List getByProposalId(Integer proposalId);
	
	public List getByProposalIdAndType(Integer proposalId, Integer type);
	/**	根据主题ID和要过滤的附件类型获得相应附件	*/
	public List getByProposalIdAndFilteredType(Integer proposalId, Integer filteredType);
	
	public List findByPage(TransMsg msg);
	
	public List findByPage(TransMsg msg, String additionalCondition);

}
