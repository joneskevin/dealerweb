package com.ava.baseSystem.service;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import com.ava.base.dao.hibernate.TransMsg;
import com.ava.base.domain.ResponseData;
import com.ava.domain.entity.Company;

public interface ICompanyService {
	
	/**
	 * 查询组织信息
	 * @param companyId
	 * @return
	 */
	public ResponseData displayEditQrgInfo(Integer companyId);
	
	/**
	 * 查询经销商列表
	 * 
	 * @param transMsg
	 * @param company
	 * @param currentCompanyId
	 * @param request
	 * @return ResponseData
	 */
	public ResponseData listCompany(TransMsg transMsg, Company company, Integer currentCompanyId, HttpServletRequest request, boolean isExport);
	
	/**
	 * 查看经销商明细
	 * @param userId
	 * @return
	 */
	public ResponseData viewCompany(Integer companyId);
	
	/**
	 * 显示经销商添加页面
	 * 
	 * @param CompanyAdd
	 * @return ResponseData
	 */
	public ResponseData displayAddCompany(Company companyAdd);
	
	/** 添加经销商*/
	public ResponseData addDealer(Company company) throws IOException;
	
	/**
	 * 编辑经销商
	 * @param company
	 * @return
	 */
	public ResponseData editCompany(Company company);
	
	
	/**
	 * 删除经销商(逻辑删除)
	 * @param company
	 * @return
	 */
	public ResponseData deleteCompany( Integer companyId);
	
	/**
	 * 显示编辑经销商页面
	 * 
	 * @param companyId
	 * @return ResponseData
	 */
	public ResponseData displayEditCompany(Integer companyId);
	
	/**
	 * 显示配置车型页面
	 * 
	 * @param companyId
	 * @return ResponseData
	 */
	public ResponseData displayConfigCarStyle(Integer companyId);
	
	/**
	 * 编辑组织信息
	 * @param companyId
	 * @return
	 */
	public ResponseData editOrgInfo(Company company);

	/** 根据ID获得组织对象 */
	public Company getOrgById(Integer orgId);
	
	/**
	 * 根据网络代码获得经销商
	 * @author liuq 
	 * @version 0.1 
	 * @param dealerCode
	 * @return
	 */
	public Company getCompanyByDealerCode(String dealerCode);

	/** 编辑组织 */
	public void editOrg(Company org);

	/**
	 * 判断cName是否存在
	 * 
	 * @param cName
	 * @return
	 */
	public boolean cNameIsExistence(String cnName);
	
	/**
	 * 判断email是否存在
	 * 
	 * @param email
	 * @return
	 */
	public boolean emailIsExistence(String email);
	
	/**
	 * 根据车辆ID返回经销商信息
	 * @param vehicleId
	 * @return
	 */
	public Company getCompanyByVehicleId(Integer vehicleId);
	
	/**
	 * 批量初始化经销商坐标点
	 * @throws IOException 
	 */
	public void initDealerPoints() throws IOException;
	
	/**
	 * 经销商退网处理(逻辑删除)
	 * @param company
	 * @return
	 */
	public ResponseData retreatCompany( Integer companyId);
	
	/**
	 * 经销商退网恢复处理
	 * @param company
	 * @return
	 */
	public ResponseData rollbackRetreatCompany(Integer companyId);
	
}
