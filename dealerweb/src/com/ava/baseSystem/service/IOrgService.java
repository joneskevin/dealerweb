package com.ava.baseSystem.service;

import java.util.List;
import java.util.Map;

import com.ava.base.domain.ResponseData;
import com.ava.domain.entity.Org;
import com.ava.resource.SelectOption;

public interface IOrgService {

	/**
	 * 显示组织树
	 * 
	 * @param orgId
	 * @return ResponseData
	 */
	public ResponseData displayOrgTree(Integer orgId);
	
	/**
	 * 查询
	 * 
	 * @param parameters
	 * @param additionalCondition
	 * @return List
	 */
	public List findOrgDepartment(Map parameters, String additionalCondition);
	

	/**
	 * 显示添加组织页面
	 * 
	 * @param org
	 * @param companyId
	 * @param parentId
	 * @return ResponseData
	 */
	public ResponseData displayAddOrg(Org org, Integer companyId, Integer parentId);


	/**
	 * 显示编辑组织页面
	 * 
	 * @param companyId
	 * @param orgId
	 * @return ResponseData
	 */
	public ResponseData displayEditOrg(Integer companyId, Integer orgId);

	/**
	 * 编辑组织
	 * 
	 * @param form
	 * @param request
	 * @return ResponseData
	 */
	public ResponseData editOrg(Org org, Integer companyId);
	/**
	
	 * 删除功能只适合测试用，正式上线取消删除功能
	 * 
	 * @param companyId
	 * @param orgId
	 * @param obName
	 * @return ResponseData
	 */
	 public ResponseData  deleteOrg(Integer companyId, Integer orgId, String obName);

	
	/**
	 * 判断email是否存在
	 * 
	 * @param email
	 * @return
	 */
	public boolean emailIsExistence(String email);
}
