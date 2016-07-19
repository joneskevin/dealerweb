package com.ava.baseSystem.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ava.base.domain.ResponseData;
import com.ava.baseSystem.service.IOrgService;
import com.ava.dao.ICompanyDao;
import com.ava.dao.IDepartmentDao;
import com.ava.dao.IOrgDao;
import com.ava.dao.IProposalDao;
import com.ava.dao.IUserDao;
import com.ava.domain.entity.Company;
import com.ava.domain.entity.Department;
import com.ava.domain.entity.Org;
import com.ava.resource.GlobalConstant;
import com.ava.resource.cache.CacheOrgManager;
import com.ava.util.MyBeanUtils;

@Service
public class OrgService implements IOrgService {

	@Autowired
	private IOrgDao orgDao;

	@Autowired
	private IUserDao userDao;

	@Autowired
	private ICompanyDao companyDao;

	@Autowired
	private IDepartmentDao departmentDao;

	@Autowired
	private IProposalDao proposalDao;

	public ResponseData displayOrgTree(Integer orgId) {
		ResponseData rd = new ResponseData(0);
		String dhtmlXtreeXML = CacheOrgManager.getChildrenOrgTree4Xml(null,
				orgId);
		rd.setFirstItem(dhtmlXtreeXML);
		return rd;
	}

	public ResponseData displayAddOrg(Org org, Integer companyId, Integer parentId) {
		ResponseData rd = new ResponseData(0);

		Org parentOrg = orgDao.get(parentId);
		if (parentOrg == null) {
			rd.setCode(-1);
			rd.setMessage("未选择上级组织，请重试！");
			return rd;
		}
		
        org.init();
		org.setParentId(parentOrg.getId());
		org.setParentName(CacheOrgManager.getOrgName(org.getParentId()));
		
		// 不允许集团操作分公司的部门
		if (companyId == GlobalConstant.DEFAULT_GROUP_ORG_ID) {
			rd = orgDao.organizationCompetence(parentId);

			if (rd.getCode() == -10) {
				rd.setMessage("不能添加分公司的下级组织！");
				return rd;

			} else if (rd.getCode() == -11) {
				return rd;
			}
			
		}
        
		rd.put("org", org);
		rd.setCode(1);
		return rd;
	}

	public ResponseData displayEditOrg(Integer companyId, Integer orgId) {
		ResponseData rd = new ResponseData(0);
		
		Org org = orgDao.get(orgId);
		org.setParentName(CacheOrgManager.getOrgName(org.getParentId()));
		
		// 不允许集团操作分公司的部门
		if (companyId == GlobalConstant.DEFAULT_GROUP_ORG_ID && org.getOrgType().equals(GlobalConstant.ABSTRACT_ORG_TYPE_DEPARTMENT)){
			rd = orgDao.organizationCompetence(orgId);

			if (rd.getCode() == -10) {
				rd.setMessage("不能修改分公司的下级组织！");
				return rd;

			} else if (rd.getCode() == -11) {
				return rd;
			}
		}
		
		rd.setFirstItem(org);
		rd.setCode(1);
		return rd;
	}

	public ResponseData editOrg(Org org, Integer companyId) {
		ResponseData rd=new ResponseData(0);
		
		Org dbOrg = orgDao.get(org.getId());

		if (org.getName() != null
				&& !org.getName().equalsIgnoreCase(dbOrg.getName())) {
			// 如果用户要修改组织名称，则判断新的部门名称是否已经存在
			if (orgNameIsExistence(companyId, org.getName())) {
				rd.setCode(-1);
				rd.setMessage("组织名称已经存在");
				return rd;
				
			}
		}

		if (dbOrg instanceof Company) {
			MyBeanUtils.copyPropertiesContainsDate(dbOrg, org);
			companyDao.edit((Company) dbOrg);

		}
		if (dbOrg instanceof Department) {
			MyBeanUtils.copyPropertiesContainsDate(dbOrg, org);
			departmentDao.edit((Department) dbOrg);

		}

		CacheOrgManager.clear();// 清除部门缓存
		
		rd.setFirstItem(org);
		rd.setCode(1);
		rd.setMessage("组织信息修改");
		return rd;
	}

	public ResponseData  deleteOrg(Integer companyId, Integer orgId, String obName) {
		ResponseData rd=new ResponseData(0);
		
		Org dbOrg = orgDao.get(orgId);
		if (dbOrg == null) {
			rd.setCode(0);
			rd.setMessage("组织信息为空");
			return rd;
		}
		
		//不能删除自己的公司
		Company company = companyDao.get(orgId);
		if (company != null && company.getObName().equals(obName) && company.getOrgType() == GlobalConstant.ABSTRACT_ORG_TYPE_COMPANY) {
			rd.setCode(-3);
			rd.setMessage("不允许删除本公司！");
			return rd;
		}
		
		//  不允许集团操作分公司的组织
		if (companyId == GlobalConstant.DEFAULT_GROUP_ORG_ID&&dbOrg.getOrgType()==GlobalConstant.ABSTRACT_ORG_TYPE_DEPARTMENT) {
			rd = orgDao.organizationCompetence(dbOrg.getId());

			if (rd.getCode() == -10) {
				rd.setMessage("不能删除分公司的组织！");
				return rd;

			} else if (rd.getCode() == -11) {
				return rd;

			}
		}
		
		Map parameters = new HashMap();
		parameters.put("parentId", orgId);
		List orgdepartmentList = orgDao.find(parameters, "");
		if (orgdepartmentList != null && orgdepartmentList.size() > 0) {
			rd.setCode(-1);
			rd.setMessage("有下级组织，不能删除！");
			return rd;
		}

		parameters.clear();
		parameters.put("departmentId", orgId);

		// 删除
		if (dbOrg instanceof Company) {
			companyDao.delete(orgId);

			departmentDao.deleteByCompanyId(orgId);

			userDao.deleteByCompanyId(orgId);

			proposalDao.deleteByCompanyId(orgId);
		}
		if (dbOrg instanceof Department) {
			departmentDao.delete(orgId);
		}

		CacheOrgManager.clear();// 清除部门缓存
		
		rd.setCode(1);
		rd.setMessage("组织删除成功！");
		return rd;
	}

	/**
	 * 查找email是否存在
	 * 
	 * @param email
	 * @return Boolean
	 */
	public boolean emailIsExistence(String email) {
		if (email == null || email.length() <= 0) {
			return false;
		}
		HashMap parameters = new HashMap();
		parameters.put("email", email);
		List result = orgDao.find(parameters, "");
		if (result != null && result.size() > 0) {
			return true;
		}
		return false;
	}

	public List findOrgDepartment(Map parameters, String additionalCondition) {
		List orgdepartmentList = orgDao.find(parameters, additionalCondition);

		return orgdepartmentList;
	}

	/**
	 * 判断名称是否重复
	 * 
	 * @param companyId
	 * @param orgName
	 * @return
	 */
	private boolean orgNameIsExistence(int companyId, String orgName) {
		if (orgName == null || orgName.length() <= 0) {
			return false;
		}
		HashMap parameters = new HashMap();
		parameters.put("companyId", companyId);// 公司编号
		parameters.put("name", orgName);// 组织名称
		List result = orgDao.find(parameters, "");
		if (result != null && result.size() > 0) {
			return true;
		}
		return false;
	}
}
