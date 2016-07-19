package com.ava.facade.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ava.api.service.IUserApiService;
import com.ava.base.domain.ResponseData;
import com.ava.baseSystem.service.IUserService;
import com.ava.dao.ICompanyDao;
import com.ava.dao.IDepartmentDao;
import com.ava.dao.IOrgDao;
import com.ava.dao.IUserDao;
import com.ava.domain.entity.Company;
import com.ava.domain.entity.Department;
import com.ava.domain.entity.Org;
import com.ava.domain.entity.User;
import com.ava.facade.IOrgFacade;
import com.ava.resource.GlobalConstant;
import com.ava.resource.cache.CacheOrgManager;
import com.ava.util.DateTime;
import com.ava.util.MyBeanUtils;

@Service
public class OrgFacade implements IOrgFacade {

	@Autowired
	private IOrgDao orgDao;

	@Autowired
	private IUserDao userDao;

	@Autowired
	private ICompanyDao companyDao;

	@Autowired
	private IDepartmentDao departmentDao;
	
	@Autowired
	private IUserService userService;

	@Autowired
	private IUserApiService userApiService;

	@Override
	public ResponseData addOrg(Org org, Integer companyId, String obName, String adminEmail) {
		ResponseData rd = new ResponseData(0);
		
		Integer parentId = org.getParentId();
		Org parentOrg = orgDao.get(parentId);
		if (parentOrg == null) {
			rd.setCode(-1);
			rd.setMessage("父节点不存在！");
			return rd;
		} else {
			if (parentOrg.getIsLeaf() == null
					|| GlobalConstant.TRUE == parentOrg.getIsLeaf().intValue()) {
				parentOrg.setIsLeaf(GlobalConstant.FALSE);
				orgDao.edit(parentOrg);
			}
		}

		Map parameters = new HashMap();
		parameters.put("name", org.getName());
		parameters.put("companyId", companyId);
		List aOrgList = orgDao.find(parameters, "");
		if (aOrgList != null && aOrgList.size() > 0) {
			// 在登录者所在公司的组织结构中，该名称已存在
			rd.setCode(-2);
			rd.setMessage("中文名称已经存在！");
			return rd;
		}

		Integer levelId = 0;
		// 如果父节点为0，则层级为1
		if (parentId == 0 || "0".equals(parentId)) {
			levelId = 1;
		} else {
			// 如果父节点不为0，则层级为上级的层级+1
			levelId = parentOrg.getLevelId() + 1;
		}

		org.init();
		org.setCompanyId(companyId);
		org.setLevelId(levelId);

		Integer newId = null;// 抽象组织对象持久化后产生的ID
		if (GlobalConstant.ABSTRACT_ORG_TYPE_COMPANY == org.getOrgType()) {// 公司类型
			Company company = new Company();
			MyBeanUtils.copyPropertiesContainsDate(company, org);
			company.init();
			company.setCreationTime(DateTime.getTimestamp());
			company.setCnName(org.getName());
			company.setObName(obName);
			newId = (Integer) companyDao.save(company);
            
			User user = userDao.createAdmin(company, adminEmail, null, null);
			rd = userService.addUser(user,companyId, obName);
			if (rd.getCode() == 1) {
				User admin = (User) rd.getFirstItem();
				userApiService.sendTempPassowrd(admin.getId(), admin.getTempPassword(), null, adminEmail);
			} else {
				rd.setCode(-3);
				rd.setMessage("新增管理员失败！");
				return rd;

			}
		} else if (GlobalConstant.ABSTRACT_ORG_TYPE_DEPARTMENT == org.getOrgType()) {// 部门类型
			Department department = new Department();

			MyBeanUtils.copyPropertiesContainsDate(department, org);
			department.setCreationTime(DateTime.getTimestamp());
			newId = (Integer) departmentDao.save(department);

		} else {
			rd.setCode(-4);
			rd.setMessage("要添加的节点类型未知！");
			return rd;
		}

		if (newId != null) {
			// 新增部门成功
			rd.setFirstItem(org);
			CacheOrgManager.clear();// 清除部门缓存

			rd.setCode(1);
			rd.setMessage("组织新增成功");
			return rd;
		}

		rd.setCode(0);
		rd.setMessage("组织新增失败");
		return rd;
	}

}
