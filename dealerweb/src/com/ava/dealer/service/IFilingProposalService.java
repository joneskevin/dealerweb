package com.ava.dealer.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;

import com.ava.base.dao.hibernate.TransMsg;
import com.ava.domain.entity.FilingProposal;
import com.ava.domain.entity.FilingProposalAttachment;
import com.ava.domain.vo.FilingProposalAttachmentVO;
import com.ava.domain.vo.FilingProposalHis;
import com.ava.domain.vo.FilingProposalVO;


public interface IFilingProposalService {

	/**
	 * 
	* Description: 根据经销商ID、报备类型、开始结束时间查询待审核的报备信息
	* @author henggh 
	* @version 0.1 
	* @param transMsg
	* @param companyId
	* @param type
	* @param startTime
	* @param endTime
	* @return
	 */
	public List<FilingProposal> getFilingProposalList(TransMsg transMsg,Integer companyId,Integer type,String startTime,String endTime,boolean isExport);
	
	/**
	 * 市场活动、维修报被列表
	 * @author liuq 
	 * @version 0.1 
	 * @param transMsg
	 * @param companyId
	 * @param userId
	 * @param type
	 * @param startTime
	 * @param endTime
	 * @param isExport
	 * @param dealerCode
	 * @return
	 */
	public List<FilingProposalVO> getFilingProposalVoList(TransMsg transMsg,Integer companyId,Integer userId,Integer type,String startTime,String endTime, boolean isExport, String dealerCode);
	
	
	public List<FilingProposal> getFilingProposalByVehicle(Integer vehicleId,String workTime);
	
	/**
	 * 
	* Description: 查询当前经销商直属车辆信息
	* @author henggh 
	* @version 0.1 
	* @param companyId
	* @return
	 */
	public Map getAddFilingProposalInfo(Integer companyId,String selectedVehicles);
	
	/**
	 * 
	* Description: 添加报备
	* @author henggh 
	* @version 0.1 
	* @param request
	* @param filingProposalType
	* @param filingProposal
	* @param selectedVehicles
	* @return
	 */
	public List<FilingProposal> addFilingProposal(HttpServletRequest request,Integer filingProposalType,FilingProposal filingProposal,String selectedVehicles);
	
	public List<FilingProposal> addFilingProposalNew( MultipartFile[] files,Integer filingProposalType,FilingProposal filingProposal,String selectedVehicles, Integer userId, Integer companyId);
	
	/**
	 * 
	* Description: 编辑报备
	* @author henggh 
	* @version 0.1 
	* @param request
	* @param filingProposalType
	* @param filingProposal
	* @param selectedVehicles
	* @return
	 */
	public List<FilingProposal> editFilingProposal(HttpServletRequest request,Integer filingProposalType,FilingProposal filingProposal,String selectedVehicles);
	
	/**
	 * 
	* Description: 显示编辑报备页面
	* @author henggh 
	* @version 0.1 
	 * @param filingProposalId
	 * @param filingProposalType
	* @return
	 */
	public Map editFilingProposalView(Integer filingProposalId, Integer filingProposalType);
	
	/**
	 * 
	* Description: 查看报备详情
	* @author henggh 
	* @version 0.1 
	* @param filingProposalId
	* @param filingProposalType
	* @return
	 */
	public Map getFilingProposalDetail(Integer filingProposalId,Integer filingProposalType);
	/**
	 * 
	* Description: 取消报备
	* @author henggh 
	* @version 0.1 
	* @param transMsg
	* @param filingProposalId
	* @param companyId
	* @param type
	* @return
	 */
	public void cancelFilingProposal(Integer filingProposalId,Integer companyId,Integer type);
	
	/**
	 * 
	* Description: 附件列表
	* @author henggh 
	* @version 0.1 
	* @param filingProposalId
	* @return
	 */
	public List<FilingProposalAttachmentVO> getFilingProposalAttachList(Integer filingProposalId);
	
	/**
	 * 
	* Description: 删除附件
	* @author henggh 
	* @version 0.1 
	* @param filingProposalId
	* @param delFiles
	 */
	public void delFilingProposalFile(Integer filingProposalId,String delFiles);
	
	/**
	 * 
	* Description: 附件管理上传附件
	* @author henggh 
	* @version 0.1 
	* @param request
	* @param filingProposalId
	* @return
	 */
	public List<FilingProposalAttachmentVO> uploadFile(HttpServletRequest request,Integer filingProposalId);
	
	/**
	 * 
	* Description: 报备审批列表
	* @author henggh 
	* @version 0.1 
	* @param currentCompanyId
	* @param currentUserId
	* @param selectSaleCenterId
	* @param beginTime
	* @param endTime
	* @return
	 */
	public List<FilingProposalVO> approveFilingProposalList(TransMsg transMsg,Integer currentCompanyId, Integer currentUserId,Integer selectSaleCenterId,String dealerCode,String beginTime, String endTime,boolean isExport);
	
	/**
	 * 
	* Description: 历史报备列表
	* @author henggh 
	* @version 0.1 
	* @param currentCompanyId
	* @param currentUserId
	* @param selectSaleCenterId
	* @param dealerCode
	* @param beginTime
	* @param endTime
	* @return
	 */
	public List<FilingProposalHis> getFilingProposalHisList(TransMsg transMsg,Integer currentCompanyId, Integer currentUserId,Integer selectSaleCenterId,String dealerCode,String beginTime, String endTime,boolean isExport);
	
	/**
	 * 
	* Description: 查询当前报备的车辆信息
	* @author henggh 
	* @version 0.1 
	* @param companyId
	* @param filingProposalId
	* @return
	 */
	public Map getFilingProposalInfo(Integer companyId, Integer filingProposalId);
	
	/**
	 * 
	* Description: 报备审核页面
	* @author henggh 
	* @version 0.1 
	* @param filingProposalId
	* @return
	 */
	public Map approveFilingProposalView(Integer filingProposalId);
	
	/**
	 * 
	* Description: 获取报备具体附件信息
	* @author henggh 
	* @version 0.1 
	* @param filingProposalId
	* @param fileId
	* @return
	 */
	public FilingProposalAttachment getFilingProposalAttachment(Integer filingProposalId,Integer fileId);
	
	/**
	 * 
	* Description: 报备审核
	* @author henggh 
	* @version 0.1 
	* @param filingProposal
	* @param filingProposalId
	* @param approvalId
	* @param currentCompanyId
	* @param currentUserId
	* @return
	 */
	public List<FilingProposalVO> approveFilingProposal(FilingProposal filingProposal,Integer filingProposalId,Integer currentCompanyId, Integer currentUserId);
	
	/**
	 * 判定车辆在指定工作时间内是否存在指定状态的报备记录
	 *
	 * @author wangchao
	 * @version 
	 * @param vehicleId 车辆id 不能为空
	 * @param startTime 区间开始时间yyyy-mm-dd hh:MM:ss 不能为空
	 * @param endTime 区间结束时间yyyy-mm-dd hh:MM:ss 可为空
	 * @param status 状态 1 已审核 0 未审核 可为空
	 * @return
	 */
	public boolean hasApprovedFilingProposal(Integer vehicleId, String startTime, String endTime, String status);
}
