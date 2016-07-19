package com.ava.resource.cache;

import java.util.HashMap;
import java.util.List;

import com.ava.baseSystem.service.IUserMenuService;
import com.ava.domain.entity.Menu;
import com.ava.domain.entity.User;
import com.ava.domain.vo.UserMenuRelationVO;
import com.ava.resource.GlobalConfig;

public class CacheUserMenuManager {
	/** 用户设置菜单集合缓存 */
	private static HashMap<Integer, List<UserMenuRelationVO>> userMenuListCache = new HashMap<Integer, List<UserMenuRelationVO>>(10000);
	
	/** 有权限的菜单集合缓存 */
	private static HashMap<Integer, List<Menu>> roleMenuListCach = new HashMap<Integer, List<Menu>>(10000);

	private static IUserMenuService userMenuService = (IUserMenuService) GlobalConfig.getBean("userMenuService");

	private CacheUserMenuManager() {
		;
	}

	/**
	 * 从缓存中根据userId获得所有有权限的菜单
	 * @param userId
	 * @return
	 */
	public static List<Menu> getRoleMenuList(Integer userId) {
		if (userId == null) {
			return null;
		}
		//个人菜单列表项
		List<Menu> roleMenuList = roleMenuListCach.get(userId);
		if (roleMenuList == null) {
			User curentUser = CacheUserManager.getUser(userId);
			roleMenuList = userMenuService.roleMenuList(curentUser);
			roleMenuListCach.put(userId, roleMenuList);
		}
		return roleMenuList;
		
	}
	
	/**
	 * 从缓存中根据userId获得用户设置的菜单
	 * @param userId
	 * @return
	 */
	public static List<UserMenuRelationVO> getUserMenuRelationVOList(Integer userId) {
		if (userId == null) {
			return null;
		}
		//个人菜单列表项
		List<UserMenuRelationVO> userMenuRelationVOList = userMenuListCache.get(userId);
		if (userMenuRelationVOList == null) {
			userMenuRelationVOList = userMenuService.listUserMenuRelationVO(userId);
			userMenuListCache.put(userId, userMenuRelationVOList);
		}
		return userMenuRelationVOList;
		
	}
	
	public static void clear() {
		userMenuListCache.clear();
	}

}
