package com.ava.facade;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;

import com.ava.base.domain.ResponseData;
import com.ava.domain.vo.ProposalCompanyVehcileInfo;
import com.ava.domain.vo.VehicleVO;

public interface IPoposalFacade {

	/**
	 * 添加新装
	 * @author liuq 
	 * @version 0.1 
	 * @param vo
	 * @param multipartFiles 上传文件列表
	 * @param currentUserId
	 * @param companyId
	 * @return
	 */
	public ResponseData addProposal(ProposalCompanyVehcileInfo vo, List<MultipartFile> multipartFiles, Integer currentUserId, Integer companyId);
	
	/**
	 * 添加换装
	 * @author liuq 
	 * @version 0.1 
	 * @param vo
	 * @param multipartFiles 上传文件列表
	 * @param currentUserId
	 * @param companyId
	 * @return
	 */
	public ResponseData addReplace(ProposalCompanyVehcileInfo vo, List<MultipartFile> multipartFiles, Integer currentUserId, Integer companyId);
	
	/**
	 * 编辑车辆信息
	 * @author liuq 
	 * @version 0.1 
	 * @param vo
	 * @return
	 */
	public ResponseData editProposal(ProposalCompanyVehcileInfo vo);
	
	/**
	 * 根据车型拆除车辆
	 * @author liuq 
	 * @version 0.1 
	 * @param vehicleVO
	 * @param userName 
	 * @return
	 */
	public ResponseData demolitionVehicle(VehicleVO vehicleVO, String userName);
	
	/**
	 * 确认安装结果
	 * @author liuq 
	 * @version 0.1 
	 * @param vo
	 * @return
	 */
	public ResponseData confirmInstallationResult(ProposalCompanyVehcileInfo vo);
	
	
	public ResponseData proposalApproval(ProposalCompanyVehcileInfo vo, Integer companyId, HttpServletRequest request);
	
	public ResponseData installationComplete(ProposalCompanyVehcileInfo vo, Integer companyId, HttpServletRequest request);
	
	public ResponseData cancelProposal(Integer proposalId, Integer proposalType);
	
	public ResponseData demolitionComplete(Integer vehicleId, Integer companyId, Integer currentUserId, Integer proposalType);
	
	public ResponseData addDemolitionProposal(Integer vehicleId, Integer currentUserId, Integer companyId, Integer proposalType);
	
}
