package com.ava.dao;

import java.util.HashMap;
import java.util.List;

import com.ava.base.dao.hibernate.TransMsg;
import com.ava.domain.entity.Proposal;
import com.ava.domain.entity.ProposalAttachment;

public interface IProposalDao {
	
	public Integer save(Proposal proposal);
	
	/**	删除申请单后还会自动删除关联的审批项	*/
	public void delete(Integer id);
	
	public void deleteByCompanyId(Integer companyId);
	
	public void edit(Proposal proposal);
	
	public List<Proposal> find(HashMap parameters, String additionalCondition);
	
	public List findByPage(TransMsg msg, String additionalCondition);
	
	public Proposal get(Integer proposalId);

	/**	获取正在使用（“处理中”，“驳回”）的申请单	*/
	public List<Proposal> getUsingByFlowPathId(Integer flowPathId);
	
	public List<Proposal> findByVehicleId(String hql);
	
	/**
	 * 获取附件信息
	 * @author liuq 
	 * @version 0.1 
	 * @param parameters
	 * @return
	 */
	public ProposalAttachment getProposalAttachment(HashMap<Object, Object> parameters);
	
	/**
	 * 通过申请ID获得附件列表
	 * @author liuq 
	 * @version 0.1 
	 * @param proposalId
	 * @return
	 */
	public List<ProposalAttachment> getProposalAttachmentList(Integer proposalId);
	
	/**
	 * 保存申请附件
	 * @author liuq 
	 * @version 0.1 
	 * @param proposalAttachment
	 * @return
	 */
	public Integer saveProposalAttachment(ProposalAttachment proposalAttachment);
	
	/**
	 * 删除附件
	 * @author liuq 
	 * @version 0.1 
	 * @param proposalId
	 * @param delFiles
	 */
	public void deleteProposalFile(Integer proposalId, String delFiles);
}
