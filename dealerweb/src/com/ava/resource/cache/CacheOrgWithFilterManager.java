package com.ava.resource.cache;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ava.dao.IUserRoleRelationDao;
import com.ava.domain.entity.Company;
import com.ava.domain.entity.Org;
import com.ava.domain.entity.User;
import com.ava.resource.GlobalConstant;
import com.ava.resource.SpringContext;

/**
 * 组织缓存，包括缓存所有组织除经销商外的所有组织
 */

public class CacheOrgWithFilterManager {
	
	/** 某个组织ID和其对应的所有可用企业用户列表-select控件专用 */
	private static Map<Integer, List<Org>> orgIdAndOrgList4SelectCache = new HashMap<Integer, List<Org>>();
	
	private static IUserRoleRelationDao userRoleRelationDao = SpringContext.getBean("userRoleRelationDao");

	/**
	 * 递归函数，递归生成下拉列表框使用的组织除经销商的所有下级组织列表
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
				orgs = CacheOrgManager.getAllChildOrg(org.getId(), GlobalConstant.FALSE);
			} else {
				orgs = CacheOrgManager.getAvailableChildOrg(org.getId(), orgIds);
			}
			if (orgs != null && orgs.size() > 0) {
				for (Org o : orgs) {
					if (o instanceof Company) {
						Company company = (Company) o;
						//判断网络形态是否为空
						if (company.getDealerCode() != null && company.getDealerCode().length() > 0) {
							continue;
						}
					} 
					
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

						Org tempOrg = CacheOrgManager.getColorOrg(o);
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

			Org org = CacheOrgManager.get(orgId);
			if (org == null) {
				return null;
			}
			orgList.add(org);

			String orgIds = CacheOrgManager.getAvailableChildrenOrgIdsStr(roleId, orgId);
			boolean isAll = true;

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
	public static List<Org> getOrgTree4Select(Integer userId, Integer orgId) {
		User user =  CacheUserManager.getUser(userId);
		if (user == null) {
			return null;
		}
		Boolean isUserCompriseRole = user.getIsAdminRole();
		if (isUserCompriseRole == true || orgId == GlobalConstant.DEFAULT_GROUP_ORG_ID ) {
			orgId = Integer.valueOf(GlobalConstant.NETWORK_DEVELOPMENT_ID);
		}
		List<Org> orgList = orgIdAndOrgList4SelectCache.get(orgId);
		if (orgList == null) {
			orgList = new ArrayList();
			
			Org org = CacheOrgManager.get(orgId);
			if (org == null) {
				return null;
			}
			//如果是4S店，则显示其分销中心
			Company company = CacheCompanyManager.getCompany(orgId);
			if (company != null) {
				org = CacheOrgManager.get(org.getParentId());
				if (org == null) {
					return null;
				}
			}
			
			orgList.add(org);
			
			String orgIds = CacheOrgManager.getAvailableChildrenOrgIdsStr(null, orgId);
			boolean isAll = true;
			
			iterativeOrgTree4Select(org, 1, orgList, orgIds, isAll);
			
			orgIdAndOrgList4SelectCache.put(orgId, orgList);
		}

	return orgList;
}
	
	public static void clear() {
		orgIdAndOrgList4SelectCache.clear();
	}

}
