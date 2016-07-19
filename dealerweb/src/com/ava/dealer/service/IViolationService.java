package com.ava.dealer.service;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.ava.base.dao.hibernate.TransMsg;
import com.ava.base.domain.ResponseData;
import com.ava.domain.entity.Violation;
import com.ava.domain.vo.TestNoDriveWeekVO;
import com.ava.domain.vo.ViolationLineTimeDetailVO;
import com.ava.domain.vo.ViolationLineTimeVO;
import com.ava.domain.vo.ViolationVO;

public interface IViolationService {
	
	public Violation getViolationByVehicle(Integer vehicleId,Integer type);
	
	public Integer saveViolation(Violation violation);
	
	public void updateViolation(Violation violation);
	
	public void handleNoEndViolation();
	
	public void handleNoEndViolation(Integer vehicleId,String startTime,String endTime, boolean hasFiling);
	
	/** 查询违规明细 ，其中companyId是当前登录者所属的组织ID*/
	public ResponseData listViolationOld(TransMsg transMsg ,Integer companyId, Violation violation,boolean isExport);
	
	/** 查询违规明细 ，其中companyId是当前登录者所属的组织ID*/
	public ResponseData listViolation(TransMsg transMsg ,Integer companyId, Violation violation,boolean isExport);
	
	/** 导出时使用 list*/
	//查询汇总 周
	public ResponseData listViolationForWeek(TransMsg transMsg ,Integer companyId, Violation violation,boolean isExport);
	
	//查询汇总 季度
	public ResponseData listViolationForSeason(TransMsg transMsg ,Integer companyId, Violation violation,boolean isExport);
	
	//查询汇总月
	public ResponseData listViolationForMonth( TransMsg transMsg ,Integer companyId,
			Violation violation,boolean isExport);
	
	//查询汇总 时间段
	public ResponseData listViolationForIntevel(TransMsg transMsg ,Integer companyId,
			Violation violation,boolean isExport);
	
	//查询明细 周 月 时间段都可以用
	public ResponseData listDetail(TransMsg transMsg, Integer vehicleId, Integer driveLineId,
			String beginTime, String endTime,boolean isExport);
	
	public List<ViolationLineTimeVO> listViolation(TransMsg transMsg, Integer currentCompanyId, Integer currentUserId,Integer selectSaleCenterId,Integer violationType,
			String vin,String plateNumber, String dealerCode, String violationYear,
			String violationWeek, String violationMonth, String violationQuarter,boolean isExport);
	public List<TestNoDriveWeekVO> listTestNoDriveWeek(TransMsg transMsg, Integer currentCompanyId, Integer currentUserId,Integer selectSaleCenterId,String vin,String plateNumber, String dealerCode, String violationYear,
			String violationWeek, String violationMonth, String violationQuarter,boolean isExport);
	public List<ViolationLineTimeDetailVO> viewViolationDetail(TransMsg transMsg, Integer vehicleId,Integer violationType,String violationYear,String violationWeek, String violationMonth, String violationQuarter,boolean isExport);
	
	public Violation getViolationByVehicle(Integer vehicleId,Integer type,Date startTime);
	
	public ResponseData listNoTestDetail(TransMsg transMsg ,Integer currentCompanyId, Integer currentUserId, ViolationVO violationVO, boolean isExport);
	
	/**
	 * 删除非活跃试驾并记录操作日志
	 * @author liuq 
	 * @version 0.1 
	 * @param testNoDriveWeekId
	 * @param userId
	 * @return
	 */
	public ResponseData deleteTestNoDriveWeek(Integer testNoDriveWeekId, Integer userId);
}
