package com.ava.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ava.base.dao.IHibernateDao;
import com.ava.base.dao.hibernate.TransMsg;
import com.ava.dao.IUserDao;
import com.ava.domain.entity.Company;
import com.ava.domain.entity.User;
import com.ava.resource.GlobalConstant;
import com.ava.util.TypeConverter;
import com.ava.util.ValidationCodeUtil;
import com.ava.util.ValidatorUtil;
import com.ava.util.encrypt.EncryptUtil;

@Repository
public class UserDao implements IUserDao {
	@Autowired
	private IHibernateDao hibernateDao;

	public void delete(Integer id) {
		hibernateDao.delete(User.class, id);
	}

	public void deleteByCompanyId(Integer companyId) {
		HashMap<Object, Object> parameters = new HashMap<Object, Object>();
		parameters.put("companyId", companyId);
		List users = find(parameters, "");
		if (users != null && users.size() > 0) {
			Iterator itor = users.iterator();
			while (itor.hasNext()) {
				User user = (User) itor.next();
				delete(user.getId());
			}
		}
	}

	/**如果adminPassword不传值，则随后应该通过邮件发送临时密码*/
	public User createAdmin(Company company, String adminEmail, String adminPassword, String fpTemplate){
		User admin = new User();
		admin.setIsFirstLogin(GlobalConstant.TRUE);
		admin.setIsModifyPassword(GlobalConstant.FALSE);// 首次登录是否修改了密码
		admin.init();
		admin.setLoginName("admin@" + company.getObName());
		admin.setCompanyId(company.getId());
		admin.setDepartmentId(company.getId());
		admin.setTempPassword(ValidationCodeUtil.nextPassword());
		admin.setPrefixLoginName("admin");
		admin.setNickName(company.getName() + "配置管理员");
		admin.setPositionId(GlobalConstant.DEFAULT_ADMIN_POSITION_ID);
		admin.setEmail(adminEmail);
		admin.setIsInOffice(GlobalConstant.TRUE);
		admin.setIsInPosition(GlobalConstant.TRUE);
		
		if (ValidatorUtil.isValidSize4Password(adminPassword)) {
			admin.setEncryptedPassword(EncryptUtil.encryptPassword(adminPassword));
		}
		//保存指纹
		if(!TypeConverter.isEmpty(fpTemplate)){
			admin.setHasFingerprint(GlobalConstant.TRUE);
		}
		// 配置管理员的权限
		admin.setRightsCode("Menu1L4,Menu2L41,4101,Menu1L7,Menu2L71,7100,Menu2L72,7200");
		admin.setSex(1);//性别默认为男
		
		return admin;
	}
	
	public int save(User user) {
		if (user != null) {
			if (user.getAvatar() == null || user.getAvatar().length() < 1) {
				user.setAvatar("/images/noAvatar.gif");
			}
			if (user.getDeletionFlag() == null) {
				user.setDeletionFlag(0);
			}
			if (user.getIsDisable() == null) {
				user.setIsDisable(0);
			}
			user.setModificationDate(Long.toString(System.currentTimeMillis()));
			try {
				return (Integer) hibernateDao.save(user);
			} catch (Exception e) {
				return -1;
			}
		}
		return -1;
	}

	public void edit(User user) {
		if (user != null) {
			if (user.getAvatar() == null || user.getAvatar().length() < 1) {
				user.setAvatar("/images/noAvatar.gif");
			}
			user.setModificationDate(Long.toString(System.currentTimeMillis()));
			hibernateDao.edit(user);
		}
	}

	public User get(Integer userId) {
		if (userId == null) {
			return null;
		}
		User user = (User) hibernateDao.get(User.class, userId);
		return user;
	}

	public List<User> getByOrg(Integer orgId) {
		if (null ==orgId) {
			return null;
		}
		HashMap<Object, Object> parameters = new HashMap<Object, Object>();
		parameters.put("companyId", orgId);
		parameters.put("deletionFlag", 0);
		List<User> userList = find(parameters, "");
		if (userList != null && userList.size() > 0) {
			return userList;
		}
		return null;
	}
	
	public User getByLoginName(String loginName) {
		if (loginName == null || loginName.length() < 1) {
			return null;
		}
		HashMap<Object, Object> parameters = new HashMap<Object, Object>();
		parameters.put("loginName", loginName);
		List users = find(parameters, "");
		if (users != null && users.size() > 0) {
			return (User) users.get(0);
		}
		return null;
	}

	public User getByEmail(String email) {
		if (email == null || email.length() < 1) {
			return null;
		}
		HashMap<Object, Object> parameters = new HashMap<Object, Object>();
		parameters.put("email", email);
		List users = find(parameters, "");
		if (users != null && users.size() > 0) {
			return (User) users.get(0);
		}
		return null;
	}

	public User getByMobile(String mobile) {
		if (mobile == null || mobile.length() < 1) {
			return null;
		}
		HashMap<Object, Object> parameters = new HashMap<Object, Object>();
		parameters.put("mobile", mobile);
		List users = find(parameters, "");
		if (users != null && users.size() > 0) {
			return (User) users.get(0);
		}
		return null;
	}

	public List<User> getByPositionId(Integer positionId) {
		return null;
	}

	public List<Integer> findIdsByCompanyId(Integer companyId){
		if (companyId == null) {
			return null;
		}
		HashMap<Object, Object> parameters = new HashMap<Object, Object>();
		parameters.put("deletionFlag", 0);
		parameters.put("companyId", companyId);

		List<String> fields = new ArrayList<String>();
		fields.add("id");
		
		return hibernateDao.find(fields, "User", parameters, "order by id asc");
	}
	
	public String[] getNicksByPositionId(Integer positionId){
		String[] nickArray = null;
		List<User> users = getByPositionId(positionId);
		if (users != null && users.size() > 0) {
			nickArray = new String[users.size()];
			int i = 0;
			Iterator itor = users.iterator();
			while(itor.hasNext()){
				User user = (User) itor.next();
				nickArray[i++] = user.getNickName();
			}
		}
		return nickArray;
	}

	public List findByPage(TransMsg transMsg, String additionalCondition) {
		transMsg.put("deletionFlag", 0);
		List objList = hibernateDao.findByPage("User", transMsg, additionalCondition);//.findByPage("User", msg, additionalCondition);
		return objList;
	}

	public List find(Map parameters, String additionalCondition) {
		parameters.put("deletionFlag", 0);
		List<Object> objList = (List<Object>) hibernateDao.find("User", parameters,
				additionalCondition);
		return objList;
	}
	
	public List findAll(Map parameters, String additionalCondition) {
		List<Object> objList = (List<Object>) hibernateDao.find("User", parameters,
				additionalCondition);
		return objList;
	}

	public List executeQueryList(String hql){
		return hibernateDao.executeQueryList(hql);
	}
	
	public String executeQueryString(String hql){
		return hibernateDao.executeQueryString(hql);
	}

	public int executeUpdate(String hql) {
		return hibernateDao.executeUpdate(hql);
	}

	@Override
	public int executeQueryInt(String hql) {
		return hibernateDao.executeQueryInt(hql);
	}
}
