package com.ava.dealer.service;

import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;

import com.ava.base.dao.hibernate.TransMsg;
import com.ava.base.domain.ResponseData;
import com.ava.domain.entity.CarStyle;
import com.ava.domain.entity.FilingProposal;
import com.ava.domain.vo.FilingApprovalVO;
import com.ava.domain.vo.FilingProposalAttachmentVO;
import com.ava.domain.vo.FilingProposalCompanyVehcileInfo;
import com.ava.domain.vo.FilingProposalVO;
import com.ava.domain.vo.VehicleVO;

public interface IDeclareManagerService {

public FilingProposalVO getFilingProposal(Integer id);
	
	public FilingProposalVO getFilingProposalByVehicle(Integer vehicleId,Integer declareType);
	
	public List<FilingProposalCompanyVehcileInfo> saveFilingProposal(HttpServletRequest request,MultipartFile multipartFile,FilingProposalCompanyVehcileInfo filingProposalCVInfo,Integer vehicleId);
			
	public FilingProposalAttachmentVO getFilingProposalAttachment(Integer id);
	
	public List<FilingProposalVO> getFilingProposalList(TransMsg transMsg,Integer vehicleId,Integer currentCompanyId,Integer declareType,String startTime,String endTime);
	
	public List<FilingProposalVO> saveFilingApproval(FilingApprovalVO filingApprovalReq,TransMsg transMsg,Integer userId,Integer companyId);
	
	public void save(FilingProposalCompanyVehcileInfo filingProposalCompanyVehcileInfo);
	
	public ResponseData delFilingProposalAttachment(Integer filingProposalId,Integer filingProposalAttachmentId);
	
	public void saveFilingProposalFile(FilingProposalAttachmentVO filingProposalAttachmentVO);
	
	public void delete(FilingProposalCompanyVehcileInfo filingProposalCompanyVehcileInfo);
	
	public void edit(FilingProposalCompanyVehcileInfo filingProposalCompanyVehcileInfo);
		
	public List<FilingProposalAttachmentVO> getFilingProposalAttachment(FilingProposalVO filingProposalVO);
	
	public FilingProposalCompanyVehcileInfo getFilingProposalCompanyVehcileInfo(Integer filingProposalId) throws IllegalAccessException, InvocationTargetException;
	
	public FilingProposalCompanyVehcileInfo getFilingProposalCompanyVehcileInfo(Integer vehicleId, Integer companyId) throws IllegalAccessException, InvocationTargetException;
		
	public List<FilingProposalCompanyVehcileInfo> find(Map parameters, String additionalCondition);
		
	public List<FilingProposalCompanyVehcileInfo> findByPage(TransMsg transMsg,VehicleVO vehicle, Integer currentCompanyId,int declareType,String dealerCode,String startTime,String endTime,String declareStatus);
	
	public ResponseData getFilingProposalAttachList(Integer filingProposalId);
	
	public ResponseData uploadFile(HttpServletRequest request,Integer filingProposalId,String uploadFile);
	
	public List<FilingProposalCompanyVehcileInfo> getFilingProposalCVInfoList(Integer companyId,VehicleVO vehicle,TransMsg transMsg,Integer declareType,String dealerCode,String startTime,String endTime,String declareStatus,boolean isExport);
	
	/**
	 * 
	* Description: 查询报表详情
	* @author henggh 
	* @version 0.1 
	* @param filingProposalId
	* @param vehicleId
	* @param declareType
	* @param companyId
	* @return
	 */
	public List queryDeclareInfo(Integer filingProposalId,Integer vehicleId,Integer declareType,Integer companyId);
	
	
	public ResponseData findVehiclePlateNumber(String plateNumber,Integer declareType);
	
	public List<FilingProposalCompanyVehcileInfo> submitCancelDeclare(Integer userId,Integer companyId,Integer filingProposalId,Integer declareType,Integer declareStatus);
	
	public List<FilingProposalCompanyVehcileInfo> editDeclare(FilingProposalCompanyVehcileInfo filingProposalCVInfo,Integer userId,Integer companyId);
	
	public Map findDealerCarStyle(Integer companyId);
	
	public Map findVehicleByCarStyleId(Integer companyId,Integer carStyleId);
}
