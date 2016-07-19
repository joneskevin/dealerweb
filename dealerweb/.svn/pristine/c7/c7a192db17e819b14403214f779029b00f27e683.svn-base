package com.ava.dealer.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;

import com.ava.base.dao.hibernate.TransMsg;
import com.ava.base.domain.ResponseData;
import com.ava.domain.entity.ProposalAttachment;
import com.ava.domain.entity.Vehicle;
import com.ava.domain.vo.ProposalCompanyVehcileInfo;
import com.ava.domain.vo.VehicleVO;

public interface IProposalService {
	
	/**
	 * 查看新装申请列表【不启用】
	 * @param transMsg
	 * @param proposalCompanyVehcileInfo
	 * @param companyId 是当前登录者所属的组织ID
	 * @param isListOrExport 是否导出
	 * @return
	 */
	public ResponseData listProposalOld(TransMsg transMsg, ProposalCompanyVehcileInfo proposalCompanyVehcileInfo, Integer companyId,  boolean isExport);
	
	/**
	 * 查看新装申请列表
	 * @param transMsg
	 * @param proposalCompanyVehcileInfo
	 * @param seriesId 
	 * @param companyId
	 * @param userId 
	 * @param isListOrExport 是否导出
	 * @return
	 */
	public ResponseData listProposal(TransMsg transMsg, ProposalCompanyVehcileInfo proposalCompanyVehcileInfo, Integer seriesId,  Integer companyId, Integer userId, boolean isExport);
	
	/**
	 * 查询待拆除列表【不启用】
	 * @param transMsg
	 * @param proposalCompanyVehcileInfo
	 * @param companyId 是当前登录者所属的组织ID
	 * @return
	 */
	public ResponseData listDemolitionOld(TransMsg transMsg, ProposalCompanyVehcileInfo proposalCompanyVehcileInfo, Integer companyId, boolean isExport);
	
	/**
	 * 查询待拆除列表
	 * @param transMsg
	 * @param proposalCompanyVehcileInfo
	 * @param companyId 是当前登录者所属的组织ID
	 * @return
	 */
	public ResponseData listDemolition(TransMsg transMsg, ProposalCompanyVehcileInfo proposalCompanyVehcileInfo, Integer companyId, boolean isExport);
	
	/**
	 * 查询可换装列表
	 * @param transMsg
	 * @param vo
	 * @param companyId
	 * @param userId 
	 * @param isExport
	 * @return
	 */
	public ResponseData listReplace(TransMsg transMsg, ProposalCompanyVehcileInfo vo, Integer companyId, Integer userId, boolean isExport);
	
	/**
	 * 显示申请添加页面
	 * @param proposalCompanyVehcileInfo
	 * @param companyId
	 * @return
	 */
	public ResponseData displayAddProposal(ProposalCompanyVehcileInfo proposalCompanyVehcileInfo, Integer companyId);
	
	/**
	 * 显示换装添加页面
	 * @param companyId
	 * @param replaceVehicleId
	 * @param dessCarStyleId 
	 * @param proposalCompanyVehcileInfo
	 * @return
	 */
	public ResponseData displayAddReplace(ProposalCompanyVehcileInfo vo, Integer companyId, Integer replaceVehicleId, Integer dessCarStyleId);
	
	/**
	 * 显示拆除页面
	 * @author liuq 
	 * @version 0.1 
	 * @param vehicle
	 * @return
	 */
	public ResponseData displayVehicleDemolition(VehicleVO vehicle);
	
	/**
	 * 新增申请单信息
	 * @author liuq 
	 * @version 0.1 
	 * @param proposalCompanyVehcileInfo
	 * @param multipartFiles
	 * @param dbVehicle
	 * @param currentUserId
	 * @param companyId
	 * @return
	 */
	public ResponseData addProposal(ProposalCompanyVehcileInfo proposalCompanyVehcileInfo, List<MultipartFile> multipartFiles, Vehicle dbVehicle, Integer currentUserId, Integer companyId);
	
	/**
	 * 显示编辑申请单页面
	 * @param proposalId
	 * @return
	 */
	public ResponseData displayEditProposal(Integer proposalId);
	
	/**
	 * 显示拆除申请单页面
	 * @param proposalId
	 * @param companyId
	 * @return
	 */
	public ResponseData viewDemolitionProposal(Integer proposalId,Integer companyId, Integer vehicleId);
	
	/**
	 * 显示审批页面
	 * @param proposalId
	 * @param companyId
	 * @param request
	 * @return
	 */
	public ResponseData displayProposalApproval(Integer proposalId, Integer companyId, HttpServletRequest request);
	
	/**
	 * 显示安装完成页面
	 * @param proposalId
	 * @param companyId
	 * @param request
	 * @return
	 */
	public ResponseData displayInstallationComplete(ProposalCompanyVehcileInfo proposalCompanyVehcileInfo, Integer proposalId,
			Integer companyId, HttpServletRequest request);
	

	/**
	 * 查看申请附件信息
	 * @author liuq 
	 * @version 0.1 
	 * @param proposalId
	 * @param fileId
	 * @return
	 */
	public ProposalAttachment getProposalAttachment(Integer proposalId, Integer fileId);
	
}
