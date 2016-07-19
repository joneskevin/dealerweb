package com.ava.baseSystem.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ava.base.dao.hibernate.TransMsg;
import com.ava.baseSystem.service.IUserRoleRelationService;
import com.ava.dao.IRoleDao;
import com.ava.dao.IUserRoleRelationDao;
import com.ava.domain.entity.Role;
import com.ava.domain.entity.User;
import com.ava.domain.entity.UserRoleRelation;

@Service
public class UserRoleRelationService implements IUserRoleRelationService{

	@Autowired
	private IUserRoleRelationDao userRoleRelationDao;
	@Autowired
	private IRoleDao  roleDao;
	
	@Override
	public Integer add(UserRoleRelation userRoleRelation) {
		return userRoleRelationDao.save(userRoleRelation);
	}

	@Override
	public void edit(UserRoleRelation userRoleRelation) {
		userRoleRelationDao.edit(userRoleRelation);
	}



	@Override
	public void delete(Integer userid) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List find(HashMap parameters, String additionalCondition) {
		ArrayList<UserRoleRelation> roleRelationlist = (ArrayList<UserRoleRelation>) userRoleRelationDao.find(parameters, null);
		for(UserRoleRelation roleRelation : roleRelationlist){
			HashMap parametersTemp = new HashMap();
			parametersTemp.put("id", roleRelation.getRoleId());
			Role role =(Role)(roleDao.find(parametersTemp, additionalCondition).get(0));
			roleRelation.setRole(role);
		}
		return roleRelationlist;
	}
	
	@Override
	public List findByPage(TransMsg msg, String additionalCondition) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void configRoleRelation(User userEdit) {
		HashMap parameters = new HashMap();
		parameters.put("userId", userEdit.getId());
		List userRoleRelationList = userRoleRelationDao.find(parameters, "");
		if (userRoleRelationList != null && userRoleRelationList.size() > 0) {
			for (int i = 0; i < userRoleRelationList.size(); i++) {
				UserRoleRelation userRoleRelation = (UserRoleRelation) userRoleRelationList.get(i);
				userRoleRelationDao.delete(userRoleRelation.getId());
			}
		}
		
		List<Integer> selectRoles = userEdit.getSelectRoles();
		if (selectRoles != null) {
			for (Integer roleId : selectRoles) {
				UserRoleRelation userRoleRelation = new UserRoleRelation();
				userRoleRelation.setUserId(userEdit.getId());
				userRoleRelation.setRoleId(roleId);
				userRoleRelationDao.save(userRoleRelation);
			}
		}
		
	}

}
