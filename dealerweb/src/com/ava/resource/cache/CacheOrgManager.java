package com.ava.resource.cache;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ava.dao.IOrgDao;
import com.ava.dao.IUserDao;
import com.ava.dao.IUserRoleRelationDao;
import com.ava.domain.entity.User;
import com.ava.domain.entity.Company;
import com.ava.domain.entity.Department;
import com.ava.domain.entity.Org;
import com.ava.resource.GlobalConstant;
import com.ava.resource.SpringContext;
import com.ava.util.MyBeanUtils;
import com.ava.util.MyStringUtils;

/**
 * 组织缓存，包括缓存所有组织和每个管理员能够查看到的所有组织
 */

public class CacheOrgManager {

	public static final Log logger = LogFactory.getLog(CacheOrgManager.class);

	private static Object initLock = new Object();

	/** Map<Integer, Org> 所有组织的缓存，增删改组织时一定要重新加载 */
	private static Map<Integer, Org> allOrgCache = new HashMap();
	/** List<org> 所有组织的缓存，增删改组织时一定要重新加载 */
	private static List<Org> sortedAllOrgCache = null;

	/** 某个组织ID和其对应的所有可用企业用户列表-xml */
	private static Map<Integer, String> orgIdAndOrg4XmlCache = new HashMap<Integer, String>();
	/** 某个组织ID和其对应的所有可用企业用户列表-select控件专用 */
	private static Map<Integer, List<Org>> orgIdAndOrgList4SelectCache = new HashMap<Integer, List<Org>>();
	
	/** 某个组织ID和其下属的所有用户ID集合 */
	private static Map<Integer, List<Integer>> orgIdAndUserIdsCache = new HashMap<Integer, List<Integer>>();
	
	/** 某个组织ID下的直营店缓存[目前一个4s对应多个直营店]*/
	private static HashMap<Integer, List<Org>> companyIdAndOrgCache = new HashMap<Integer,  List<Org>>();

	private static IOrgDao orgDao = SpringContext.getBean("orgDao");

	private static IUserDao userDao = SpringContext.getBean("userDao");
	
	private static IUserRoleRelationDao userRoleRelationDao = SpringContext.getBean("userRoleRelationDao");

	private static List<Org> getSortedAllOrg() {
		if (sortedAllOrgCache == null) {
			synchronized (initLock) {
				if (sortedAllOrgCache == null) {
					sortedAllOrgCache = orgDao.getAllWithoutNickInvoke();
					if (sortedAllOrgCache != null && sortedAllOrgCache.size() > 0) {
						//暂时不排序
						//Collections.sort(sortedAllOrgCache);
					}
				}
			}
		}
		return sortedAllOrgCache;
	}

	private static Map getAllOrg() {
		if (allOrgCache == null || allOrgCache.size() == 0) {
			List<Org> orgList = getSortedAllOrg();
			if (orgList != null && orgList.size() > 0) {
				for(Org org : orgList){
					allOrgCache.put(org.getId(), org);
				}
			}
		}
		return allOrgCache;
	}

	public static void clear() {
		allOrgCache.clear();
		sortedAllOrgCache = null;
		orgIdAndOrg4XmlCache.clear();
		orgIdAndOrgList4SelectCache.clear();
		orgIdAndUserIdsCache.clear();
	}

	/***获得companyId直属的及其非直属的所有子节点所属的用户*/
	public static List<User> findAllUsersByCompanyId(Integer companyId) {
		List<User> allUsers = new ArrayList();
		List<Org> orgs = getAllChildOrg(companyId, GlobalConstant.FALSE);
		if(orgs != null && orgs.size() > 0){
			for (Iterator iterator = orgs.iterator(); iterator.hasNext();) {
				Org org = (Org) iterator.next();
				allUsers.addAll(findUsersByCompanyId(org.getId()));
			}
		}
		return allUsers;
	}

	/***获得companyId直属的用户*/
	public static List<User> findUsersByCompanyId(Integer companyId) {
		List<Integer> userIds = null;
		
		userIds = orgIdAndUserIdsCache.get(companyId);
		if(userIds == null){
			userIds = userDao.findIdsByCompanyId(companyId);
			
			orgIdAndUserIdsCache.put(companyId, userIds);
		}

		List<User> users = new ArrayList();
		if(userIds != null && userIds.size() > 0){
			Iterator itor = userIds.iterator();
			while(itor.hasNext()){
				Integer userId = (Integer) itor.next();
				users.add(CacheUserManager.getUser(userId));
			}
		}
		
		return users;
	}

	public static Org get(Integer orgId) {
		if (orgId == null){
			return null;
		}
		
		Map orgs = getAllOrg();
		if(orgs != null){
			return (Org) orgs.get(orgId);
		}
		return null;
	}

	public static String getOrgName(Integer orgId) {
		Org org = get(orgId);
		if(org != null){
			return org.getName();
		}
		return null;
	}

	public static String getCompanyNameByUserId(Integer userId) {
		User user = CacheUserManager.getUser(userId);
		if(user != null){
			return getOrgName(user.getCompanyId());
		}
		return null;
	}

	/**
	 * 递归方法，查找组织下的所有的子组织
	 * 
	 * @param id
	 *            当前组织id
	 * @param childList
	 *            子组织列表
	 * @param orgIds
	 *            允许操作的组织ID集合
	 * @param isAll
	 *            表示所有组织
	 */
	private static void iterativeSearchChildOrg(Integer id, List<Org> childList, String orgIds, boolean isAll) {
		Iterator<Org> it = getSortedAllOrg().iterator();
		while (it.hasNext()) {
			Org org = (Org) it.next();
			if (org.getParentId().intValue() == id.intValue()) {
				// 进行权限判断
				if (isAll) {
				} else {
					if (MyStringUtils.isBlank(orgIds)) {
						continue;// 没有权限
					}
					if (orgIds.contains(org.getId().toString())) {// 有权限
					} else {
						continue;// 没有权限
					}
				}
				childList.add(org);
				// 如果是叶节点则不再递归
				if (org.getIsLeaf().intValue() != 1) {
					iterativeSearchChildOrg(org.getId(), childList, orgIds, isAll);
				}
			}
		}
	}

	/**
	 * 获取一个组织的下属所有组织,包括当前组织
	 * 
	 * @param parentId
	 * @param branchFlag 是否需要查看查看直营店内容
	 * @return
	 */
	public static List<Org> getAllChildOrg(Integer parentId, Integer branchFlag) {
		if (parentId == null) {
			return null;
		}
		
		if (branchFlag == null) {
			branchFlag = GlobalConstant.FALSE;
		}

		List orgList = new ArrayList();
		// 把当前组织也添加到列表中
		Org parent = get(parentId);
		if (parent == null) {
			return null;
		}
		orgList.add(parent);
		
		if (branchFlag == GlobalConstant.TRUE) {
			//直营店
			List<Org> branchOrgList = getBranchOrgList(parentId);
			if (branchOrgList != null && branchOrgList.size() > 0) {
				for (Org branchOrg : branchOrgList) {
					if (branchOrg != null) {
						orgList.add(branchOrg);
					}
				}
			}
		}
		
		// 调用递归方法查询组织的子组织
		iterativeSearchChildOrg(parentId, orgList, null, true);

		return orgList;
	}
	
	/**
	 * 获得分支机构列表
	 * @param companyId
	 * @return
	 */
	public static List<Org> getBranchOrgList(Integer companyId) {
		if (companyId == null) {
            return null;
        }
		List<Org> branchOrgList = companyIdAndOrgCache.get(companyId);
		if (branchOrgList == null) {
			Map<Object, Object> parameters = new HashMap<Object, Object>();
			parameters.put("companyId", companyId);
			List<Org> currentBranchOrgList = orgDao.find(parameters, "");
			if (currentBranchOrgList != null) {
				companyIdAndOrgCache.put(companyId, currentBranchOrgList);
				branchOrgList = currentBranchOrgList;
			}
		}

		return branchOrgList;
	}
	
	/**
	 * 获取一个组织的下属所有组织,包括当前组织
	 * 
	 * @param parentId
	 * @return
	 */
	public static List<Org> getSelfAndNextChildOrg(Integer parentId) {
		if (parentId == null) {
			return null;
		}

		List orgList = new ArrayList();
		// 把当前组织也添加到列表中
		Org parent = get(parentId);
		if (parent == null) {
			return null;
		}
		orgList.add(parent);

		Iterator<Org> it = getSortedAllOrg().iterator();
		while (it.hasNext()) {
			Org org = (Org) it.next();
			if (org.getParentId().intValue() == parentId.intValue()) {
				orgList.add(org);
			}
		}
		return orgList;
	}
	
	/**
	 * 获取一个组织的下属所有组织,不包括当前组织
	 * 
	 * @param parentId
	 * @return
	 */
	public static List<Org> getNextChildOrg(Integer parentId) {
		if (parentId == null) {
			return null;
		}

		List orgList = new ArrayList();
		// 把当前组织也添加到列表中
		Org parent = get(parentId);
		if (parent == null) {
			return null;
		}
		Iterator<Org> it = getSortedAllOrg().iterator();
		while (it.hasNext()) {
			Org org = (Org) it.next();
			if (org.getParentId().intValue() == parentId.intValue()) {
				orgList.add(org);
			}
		}
		return orgList;
	}

	/**
	 * 获取一个组织的下属的可得组织,包括当前组织
	 * 
	 * @param id
	 *            当前组织id
	 * @param orgIds
	 *            允许操作的组织ID集合
	 * @return
	 */
	public static List<Org> getAvailableChildOrg(Integer id, String orgIds) {
		if (id == null) {
			return null;// 没有权限
		}
		if (MyStringUtils.isBlank(orgIds)) {
			return null;// 没有权限
		}

		// 从缓存在获取所有下级组织
		List<Org> orgList = new ArrayList();
		// 把当前组织也添加到列表中
		Org org = get(id);
		if (org == null) {
			return null;
		}

		orgList.add(org);
		// 调用递归方法查询组织的子组织
		iterativeSearchChildOrg(id, orgList, orgIds, false);

		return orgList;
	}

	/**
	 * 获取整个系统的组织id字符串，不进行权限判断。
	 * 
	 * @return 返回所有组织的id字符串，如：“1001,1002,1003”，英文逗号分割
	 */
	public static String getAllOrgIdsStr() {
		String orgIds = "";
		StringBuffer orgIdsBuff = new StringBuffer();

		Iterator<Org> it = getSortedAllOrg().iterator();
		while (it.hasNext()) {
			Org org = (Org) it.next();
			orgIdsBuff.append(org.getId()).append(",");
		}
		if (orgIdsBuff.length() > 0) {
			orgIds = orgIdsBuff.substring(0, orgIdsBuff.length() - 1);
		}

		return orgIds;
	}

	/**
	 * 获取一个组织的下属所有组织的id字符串,包括当前组织的id
	 * @param branchFlag 是否需要查看查看直营店内容
	 * @param roleId
	 * 
	 * @return 返回所有子组织的id字符串，如：“1001,1002,1003”，英文逗号分割
	 */
	public static String getChildrenOrgIdsStr(Integer orgId, Integer branchFlag) {
		String orgIds = null;
		if (branchFlag == null) {
			branchFlag = GlobalConstant.FALSE;
		}
		List<Org> orgList = getAllChildOrg(orgId, branchFlag);
		if (orgList == null) {
			logger.warn("没有获取到任何组织ID，orgId=" + orgId);
			return null;
		}
		StringBuffer orgIdsBuff = new StringBuffer();
		for (Org org : orgList) {
			orgIdsBuff.append(org.getId()).append(",");
		}
		if (orgIdsBuff.length() > 0) {
			orgIds = orgIdsBuff.substring(0, orgIdsBuff.length() - 1);
		}
		return orgIds;
	}
	
	/**
	 * 获取一个组织的下属所有组织的id字符串,包括当前组织的id
	 * 
	 * @param roleId
	 * @return 返回所有子组织的id字符串list，如：“1001,1002,1003”，英文逗号分割
	 */
	public static List getChildrenOrgIdsList(Integer orgId) {
		List<Org> orgList = getAllChildOrg(orgId, GlobalConstant.FALSE);
		if (orgList == null) {
			logger.warn("没有获取到任何组织ID，orgId=" + orgId);
			return null;
		}
		List ids = new ArrayList();
		if (orgList != null && orgList.size() > 0) {
			for(int i= 0;i<orgList.size();i++)
			{
				ids.add(((Org)orgList.get(i)).getId());
			}
			return ids;
		}
		return orgList;
	}

	/**
	 * 获取一个组织下属的所有可得组织的id字符串,包括当前组织的id
	 * 
	 * @param roleId  暂时没有用到该字段，可以传null。
	 * @param orgId
	 * @return 返回所有子组织的id字符串，如：“1001,1002,1003”，英文逗号分割
	 */
	public static String getAvailableChildrenOrgIdsStr(Integer roleId, Integer orgId) {
		String availableOrgIds = getChildrenOrgIdsStr(orgId, GlobalConstant.TRUE);
		/**
		if (roleId == null)
			return null;

		String orgIds = roleIdAndOrgIdsCache.get(roleId);
		if (MyStringUtils.isBlank(orgIds)) {
			Role role = CacheRoleManager.getByRoleId(roleId);
			if (role == null) {
				return null;// 没有权限
			}

			List<Org> orgList = null;
			boolean isAll = (role.getIsAllChildren() != null && role.getIsAllChildren().intValue() == 1) ? true : false;
			if (isAll) {
				// 获取所有子节点
				orgList = getAllChildOrg(role.getUserId());
			} else {
				// 获取所有可得子节点
				orgList = getAvailableChildOrg(role.getUserId(), role.getUserIds());
			}
			if (orgList == null) {
				logger.warn("没有获取到任何组织ID，roleId=" + roleId);
				return null;
			}

			StringBuffer orgIdsBuff = new StringBuffer();
			for (Org org : orgList) {
				orgIdsBuff.append(org.getId()).append(",");
			}
			if (orgIdsBuff.length() > 0) {
				orgIds = orgIdsBuff.substring(0, orgIdsBuff.length() - 1);
			}

			roleIdAndOrgIdsCache.put(roleId, orgIds);
		}
		return orgIds;
		**/
		
		List<Org> orgList = getAvailableChildOrg(orgId, availableOrgIds);
		if(orgList == null){
			return null;
		}
		StringBuffer orgIdsBuff = new StringBuffer();
		for (Org org : orgList) {
			orgIdsBuff.append(org.getId()).append(",");
		}
		
		String availableChildrenOrgIds = "";
		if (orgIdsBuff.length() > 0) {
			availableChildrenOrgIds = orgIdsBuff.substring(0, orgIdsBuff.length() - 1);
		}
		return availableChildrenOrgIds;
	}
	
	/**
	 * 获取一个组织下属的所有可得组织的id字符串,包括当前组织的id
	 * 
	 * @param roleId  暂时没有用到该字段，可以传null。
	 * @param orgId
	 * @return 返回所有子组织的id整型数组
	 */
	public static int[] getAvailableChildrenOrgIds(Integer roleId, Integer orgId) {
		String availableOrgIds = getAvailableChildrenOrgIdsStr(roleId, orgId);
		List<Org> orgList = getAvailableChildOrg(orgId, availableOrgIds);
		int[] userIds = new int[orgList.size()];
		for (int i=0; i<orgList.size(); i++) {
			Org org = orgList.get(i);
			userIds[i] = org.getId();
		}
		return userIds;
	}

	/**
	 * 递归函数，获得树形结构
	 * 
	 * @param org
	 * @param strBuff
	 * @param orgIds
	 *            用了进行权限过滤的组织ID集合,逗号分隔
	 * @param isAll
	 *            表示所有组织
	 */
	private static void iterativeOrgTree4Xml(Org org, StringBuffer strBuff, String orgIds, boolean isAll) {
		if (org == null) {
			return;// 没有权限
		}

		// 如果是叶节点
		if (org.getIsLeaf().intValue() == 1) {
			strBuff.append("<item text='").append(org.getName()).append("' id='").append(org.getId()).append("' im0='book.gif' im1='books_open.gif' im2='books_close.gif'/>");
		} else {
			// 不是叶节点
			strBuff.append("<item text='").append(org.getName()).append("' id='").append(org.getId()).append("' open='1' im0='books_close.gif' im1='tombs.gif' im2='tombs.gif' call='1'>");

			List<Org> orgList = null;
			if (isAll) {
				orgList = getAllChildOrg(org.getId(), GlobalConstant.FALSE);
			} else {
				orgList = getAvailableChildOrg(org.getId(), orgIds);
			}
			if (orgList != null && orgList.size() > 0) {
				for (Org o : orgList) {
					if (o.getParentId().intValue() == org.getId().intValue()) {
						iterativeOrgTree4Xml(o, strBuff, orgIds, isAll);
					}
				}
			}
			strBuff.append("</item>");
		}
	}

	/**
	 * 获取一个组织的树形组织
	 * 
	 * @param roleId  暂时没有用到该字段，可以传null。
	 * @param orgId
	 * @return 树形结构字符串
	 */
	public static String getChildrenOrgTree4Xml(Integer roleId, Integer orgId) {
		String orgXml = orgIdAndOrg4XmlCache.get(orgId);
		if (MyStringUtils.isBlank(orgXml)) {

			String orgIds = getAvailableChildrenOrgIdsStr(roleId, orgId);
			Org org = get(orgId);//get(role.getUserId());
			boolean isAll = true;//(role.getIsAllChildren() != null && role.getIsAllChildren().intValue() == 1) ? true : false;

			StringBuffer currOrgBuff = new StringBuffer("<?xml version='1.0' encoding='UTF-8'?><tree id='0'>");
			iterativeOrgTree4Xml(org, currOrgBuff, orgIds, isAll);
			currOrgBuff.append("</tree>");

			orgXml = currOrgBuff.toString();
			orgIdAndOrg4XmlCache.put(orgId, orgXml);
		}

		return orgXml;
	}

	/**
	 * 递归函数，递归生成下拉列表框使用的组织所有下级组织列表
	 * 
	 * @param org
	 *            当前递归到的组织
	 * @param level
	 *            第几级节点
	 * @param orgList
	 *            所有组织列表
	 */
	private static void iterativeOrgTree4Select(Org org, int level, List<Org> orgList, String orgIds, boolean isAll) {
		if (org == null) {
			return;// 没有权限
		}

		// 如果当前节点不是叶节点
		if (org.getIsLeaf().intValue() != 1) {
			List<Org> orgs = null;
			if (isAll) {
				orgs = getAllChildOrg(org.getId(), GlobalConstant.FALSE);
			} else {
				orgs = getAvailableChildOrg(org.getId(), orgIds);
			}
			if (orgs != null && orgs.size() > 0) {
				for (Org o : orgs) {
					int tempLevel = level;
					if (o.getParentId().intValue() == org.getId().intValue()) {
						// 进行权限判断
						if (orgIds.contains(o.getId().toString())) {// 有权限
						} else {// 无权限
							return;
						}
						// 计算空格数
						StringBuffer strBuff = new StringBuffer();
						for (int i = 0; i < level; i++) {
							strBuff.append("&nbsp;&nbsp;&nbsp;&nbsp;");
						}

						Org tempOrg = getColorOrg(o);
						// 在组织名称前加上“|--”符号
						tempOrg.setName(strBuff.toString() + "|--" + tempOrg.getName());
						orgList.add(tempOrg);
						// 递归调用
						iterativeOrgTree4Select(o, tempLevel + 1, orgList, orgIds, isAll);
					}
				}
			}
		}
	}
	
	public static Org getColorOrg(Org o){
		Org tempOrg = null;
		String colorOrgName = o.getName();
		if(o instanceof Company){
			tempOrg = new Company();
			colorOrgName = "<font color=red>" + colorOrgName + "</font>";
		}
		if(o instanceof Department){
			tempOrg = new Department();
		}
		if(tempOrg != null) {
			MyBeanUtils.copyPropertiesContainsDate(tempOrg, o);
			tempOrg.setName(colorOrgName);
		}
		
		return tempOrg;
	}

	/**
	 * 获取当前组织的所有下级组织列表，包括当前组织 该方法专门为下拉列表用的
	 * 
	 * @param roleId  暂时没有用到该字段，可以传null。
	 * @param orgId
	 * @return 组织列表
	 */
	public static List<Org> getChildrenOrgTree4Select(Integer roleId, Integer orgId) {
		List<Org> orgList = orgIdAndOrgList4SelectCache.get(orgId);
		if (orgList == null) {
			orgList = new ArrayList();
			/**
			Role role = CacheRoleManager.getByRoleId(roleId);
			if (role == null) {
				return null;// 没有权限
			}
			**/
			Org org = get(orgId);//get(role.getUserId());
			if (org == null) {
				return null;
			}
			orgList.add(org);

			String orgIds = getAvailableChildrenOrgIdsStr(roleId, orgId);
			boolean isAll = true;//(role.getIsAllChildren() != null && role.getIsAllChildren().intValue() == 1) ? true : false;

			iterativeOrgTree4Select(org, 1, orgList, orgIds, isAll);

			orgIdAndOrgList4SelectCache.put(orgId, orgList);
			
		}
		return orgList;
	}
		
		/**
		 * 获得所属单位
		 * @param userId
		 * @param orgId
		 * @return 组织列表
		 */
		public static List<Org> getOrgTree4Select(Integer userId, Integer orgId, Integer roleId) {
			Boolean isUserCompriseRole = userRoleRelationDao.isUserCompriseRole(userId, roleId);
			if (isUserCompriseRole) {
				orgId = GlobalConstant.DEFAULT_GROUP_ORG_ID;
			}
			List<Org> orgList = orgIdAndOrgList4SelectCache.get(orgId);
			if (orgList == null) {
				orgList = new ArrayList();
				
				Org org = get(orgId);
				if (org == null) {
					return null;
				}
				orgList.add(org);
				
				String orgIds = getAvailableChildrenOrgIdsStr(null, orgId);
				boolean isAll = true;
				
				iterativeOrgTree4Select(org, 1, orgList, orgIds, isAll);
				
				orgIdAndOrgList4SelectCache.put(orgId, orgList);
			}

		return orgList;
	}

	/**
	 * 获取当前组织的所有下级组织列表，包括当前组织 该方法专门为下拉列表用的
	 * 
	 * @param positionId  如果是admin岗位，则可获取整个集团的岗位。
	 * @return 组织列表
	 */
	public static List<Org> getChildrenOrgTree4Select(Integer positionId) {
		if(positionId == null || positionId.intValue() != GlobalConstant.DEFAULT_ADMIN_POSITION_ID){
			return null;
		}
		Integer orgId = GlobalConstant.DEFAULT_GROUP_ORG_ID;
		List<Org> orgList = orgIdAndOrgList4SelectCache.get(orgId);
		if (orgList == null) {
			orgList = new ArrayList();
			Org org = get(orgId);
			if (org == null) {
				return null;
			}
			orgList.add(org);

			String orgIds = getAvailableChildrenOrgIdsStr(null, orgId);
			boolean isAll = true;//(role.getIsAllChildren() != null && role.getIsAllChildren().intValue() == 1) ? true : false;

			iterativeOrgTree4Select(org, 1, orgList, orgIds, isAll);

			orgIdAndOrgList4SelectCache.put(orgId, orgList);
		}

		return orgList;
	}

	/**
	 * 获取当前组织的所有直属公司的列表
	 * 注意：包括集团
	 * @return 公司列表
	 */
	public static List<Company> getCompanyList() {
		List<Company> companyList = null;
		
		List<Org> orgList = getChildrenOrgTree4Select(null, GlobalConstant.DEFAULT_GROUP_ORG_ID);
		if (orgList != null && orgList.size() > 0) {
			companyList = new ArrayList();
			Iterator itor = orgList.iterator();
			while (itor.hasNext()) {
				Org org = (Org)itor.next();
				if(org.getOrgType().intValue() == GlobalConstant.ABSTRACT_ORG_TYPE_COMPANY){
					companyList.add((Company)org);
				}
			}
		}

		return companyList;
	}
}
