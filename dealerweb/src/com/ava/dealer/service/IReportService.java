package com.ava.dealer.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;

import org.springframework.web.multipart.MultipartFile;

import com.ava.base.dao.hibernate.TransMsg;
import com.ava.base.domain.ResponseData;
import com.ava.domain.vo.CompanyCarStyleVO;
import com.ava.domain.vo.CompanyFilingProposalVO;
import com.ava.domain.vo.CompanyProposalVO;
import com.ava.domain.vo.TestDriveInfoVO;

public interface IReportService {
	

	public ResponseData listViolationInfo(TransMsg transMsg,CompanyCarStyleVO companyCarStyleVO,Integer companyId,
			boolean isExport);
	/*
	 * 试驾率报表
	 */
	ResponseData listTestDrivePercent(TransMsg transMsg,
			CompanyCarStyleVO companyCarStyleVO,Integer companyId,boolean isExport);
	/*
	 * 试驾明细
	 */
	public ResponseData listTestDriveInfo(TransMsg transMsg,
			TestDriveInfoVO testDriveInfoVO, Integer companyId,boolean isExport);

	/*
	 * 报备明细报表
	 */
	public ResponseData listFillingProposalInfo(TransMsg transMsg,
			CompanyFilingProposalVO companyFilingProposalVO, Integer companyId);

	/*
	 * 申请明细报表
	 */
	public   ResponseData listProposalInfo(TransMsg transMsg,
			CompanyProposalVO companyProposalVO, Integer companyId);	
	
	/*
	 * 测试报表导入报表
	 */
	public   ResponseData excelExport(MultipartFile file) throws FileNotFoundException, IOException, ParseException;

	/*配置报表 修改版本 SK*/
	ResponseData listConfigureInfoForSK(TransMsg transMsg,
			CompanyCarStyleVO companyCarStyleVO, Integer companyId,
			boolean isExport);
	
	/**
	 * 配置报表
	 * @author liuq 
	 * @version 0.1 
	 * @param transMsg
	 * @param companyCarStyleVO
	 * @param companyId
	 * @param userId
	 * @param isExport
	 * @return
	 */
	ResponseData listConfigureInfo(TransMsg transMsg, CompanyCarStyleVO companyCarStyleVO, Integer companyId, Integer userId, boolean isExport);
}
