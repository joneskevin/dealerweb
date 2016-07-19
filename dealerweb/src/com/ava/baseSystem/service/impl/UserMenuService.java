package com.ava.baseSystem.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ava.base.domain.ResponseData;
import com.ava.baseSystem.service.IUserMenuService;
import com.ava.dao.IMenuDao;
import com.ava.dao.IRoleDao;
import com.ava.dao.IUserMenuRelationDao;
import com.ava.dao.IUserRoleRelationDao;
import com.ava.domain.entity.Menu;
import com.ava.domain.entity.Role;
import com.ava.domain.entity.User;
import com.ava.domain.entity.UserMenuRelation;
import com.ava.domain.entity.UserRoleRelation;
import com.ava.domain.vo.MenuVO;
import com.ava.domain.vo.UserMenuRelationVO;
import com.ava.resource.GlobalConstant;
import com.ava.resource.SessionManager;
import com.ava.resource.cache.CacheUserMenuManager;
import com.ava.util.MyBeanUtils;

@Service
public class UserMenuService implements IUserMenuService{
	
	@Autowired
	private IRoleDao roleDao;
	
	@Autowired
	private IUserRoleRelationDao userRoleRelationDao;
	
	@Autowired
	private IMenuDao menuDao;
	
	@Autowired
	private IUserMenuRelationDao userMenuRelationDao;
	

	@Override
	public Menu findUserMenu(int menuId) {
		HashMap parameters = new HashMap();
		parameters.put("id", menuId);
		ArrayList menuList = (ArrayList)menuDao.find(parameters, null);
		return (Menu)menuList.get(0);
		
	}

	@Override
	public ResponseData listUserMenu(User user) {
		ResponseData rd = new ResponseData(0);
		
		List<Menu> roleMenuList = roleMenuList(user);
		rd.put("roleMenuList", roleMenuList);
		
		List<UserMenuRelationVO> userMenuRelationVOList = CacheUserMenuManager.getUserMenuRelationVOList(user.getId());
		rd.put("userMenuRelationVOList", userMenuRelationVOList);
		
        rd.setCode(1);
		return rd;
	}
	
	/**
	 * 查询用户主界面快捷菜单中显示的列表
	 * @return
	 */
	public List<UserMenuRelationVO> listUserMenuRelationVO(Integer userId) {
		HashMap parameters = new HashMap();
		parameters.put("userId", userId);
		String adduserMenuRelationCondition = " order by id asc";
		List<UserMenuRelation> userMenuRelationList = userMenuRelationDao.find(parameters, adduserMenuRelationCondition);
		List<UserMenuRelationVO> userMenuRelationVOList = new ArrayList<UserMenuRelationVO>();
		if (userMenuRelationList != null && userMenuRelationList.size() > 0) {
			for (UserMenuRelation userMenuRelation : userMenuRelationList) {
				if (userMenuRelation != null) {
					UserMenuRelationVO userMenuRelationVO = new UserMenuRelationVO(userMenuRelation);
					Menu menu = menuDao.get(userMenuRelation.getMenuId());
					MenuVO menuVO = new MenuVO(menu);
					String links = menuVO.getLinks();
					if (links.indexOf("?") > -1) {
						//links +="&menuType=1";
						menuVO.setIsParameter(GlobalConstant.TRUE);
					} else {
						//links +="?menuType=1";
						menuVO.setIsParameter(GlobalConstant.FALSE);
					}
					userMenuRelationVO.setMenu(menuVO);
					userMenuRelationVOList.add(userMenuRelationVO);
				}
			}
		}
		return userMenuRelationVOList;
	}
	
	public List<Menu> roleMenuList(User user) {
		//查询所有的菜单列表
		HashMap parameters = new HashMap();
		String addMenuCondition = " order by sortNo asc";
		List<Menu> menuList = menuDao.find(parameters, addMenuCondition);
		
		String rightsCodes = getRightsCode(user.getId()).toString();
		String loginName = user.getLoginName();
		
		//有权限的菜单列表
		List<Menu> roleMenuList = new ArrayList();
		if (menuList != null && menuList.size() > 0) {
			for (Menu menu : menuList) {
				String rightsCode = menu.getRightsCode();
				if (rightsCode != null) {
					if (rightsCodes.indexOf(rightsCode) > -1 || "admin@group".equalsIgnoreCase(loginName)) {
						//如果rightsCode存在于用户设置的菜单列表中，则过滤
						 parameters.clear();
						 parameters.put("menuId", menu.getId());
						 parameters.put("userId", user.getId());
						 List userMenuRelationList = userMenuRelationDao.find(parameters, null);
						 UserMenuRelation userMenuRelation = userMenuRelationDao.get(menu.getId());
						 if (userMenuRelationList != null) {
							 continue;
						 }
						 roleMenuList.add(menu);
					}
				}
			}	
		}
		return roleMenuList;
	}
	

	@Override
	public ResponseData addUserMenu(User user, String userMenus) {
		ResponseData rd = new ResponseData(0);
		//先删除用户菜单的所有菜单
		List<UserMenuRelationVO> userMenuRelationVOList = CacheUserMenuManager.getUserMenuRelationVOList(user.getId());
		if (userMenuRelationVOList != null && userMenuRelationVOList.size() > 0) {
			for (UserMenuRelationVO userMenuRelationVO : userMenuRelationVOList) {
				userMenuRelationDao.delete(userMenuRelationVO.getId());
			}
		}
		
		//重新添加
		if (userMenus != null && userMenus.length() > 0) {
			String[] userMenuArray = userMenus.split(",");
			for (int i = 0; i < userMenuArray.length; i++) {
				UserMenuRelation userMenuRelation = new UserMenuRelation();
				userMenuRelation.setUserId(user.getId());
				//如果左侧菜单设置为空
				if (Integer.valueOf(userMenuArray[i]) == 0) {
					continue;
				}
				userMenuRelation.setMenuId(Integer.valueOf(userMenuArray[i]));
				userMenuRelation.setCompanyId(0);
				userMenuRelation.setSort(i);
				userMenuRelationDao.save(userMenuRelation);
			}
		}
		
		//清除用户设置快捷菜单缓存
		CacheUserMenuManager.clear();
		
		rd.setCode(1);
		rd.setMessage("菜单设置成功");
		return rd;
	}
	
	

	@Override
	public ResponseData editMenu(Menu menuEdit) {
		ResponseData rd = new ResponseData(0);

		Integer menuId = menuEdit.getId();
		if ((menuId == null) || (menuId != null && menuId.intValue() <= 0)) {
			menuId = menuEdit.getId();
		}

		Menu dbMenu = menuDao.get(menuId);

		if (menuEdit.getName() != null
				&& !menuEdit.getName().equalsIgnoreCase(dbMenu.getName())) {
			// 如果用户要修改菜单名，则判断新的菜单名是否已经存在
			if (menuDao.getByName(menuEdit.getName()) != null) {
				rd.setCode(-1);
				rd.setMessage("菜单信息保存失败：菜单名已重复！");
				return rd;
			}
		}

		MyBeanUtils.copyPropertiesContainsDate(dbMenu, menuEdit);
		menuDao.edit(dbMenu);

		rd.setFirstItem(dbMenu);
		rd.setCode(1);
		rd.setMessage("菜单信息修改成功！");
		return rd;
	}

	
	@Override
	public ResponseData deleteMenu(Integer menuId) {
		ResponseData rd = new ResponseData(0);
         
		if (menuId == null) {
			rd.setCode(-1);
			rd.setMessage("menuId为空！");
			return rd;
		}
           
		HashMap parameters = new HashMap();
		parameters.put("menuId", menuId);
		
		ArrayList<UserMenuRelation> menuList = (ArrayList<UserMenuRelation>) userMenuRelationDao.find(parameters, null);
		if (menuList != null && menuList.size() > 0) {
			rd.setCode(-2);
			rd.setMessage("菜单已被用户引用，不允许删除！");
			return rd;
		}
        menuDao.delete(menuId);
		
		rd.setCode(1);
		rd.setMessage("菜单信息删除成功！");
		return rd;
	}

	@Override
	public ResponseData addMenu(Menu menuAdd) {
		ResponseData rd = new ResponseData(0);
		if (menuDao.getByName(menuAdd.getName()) != null) {
			rd.setCode(-1);
			rd.setMessage("菜单已经存在！");
			return rd;
		}

		Integer newMenuId = (Integer) menuDao.save(menuAdd);

		if (newMenuId == null) {
			rd.setCode(-2);
			rd.setMessage("菜单新增失败！");
			return rd;

		}

		rd.setCode(1);
		rd.setMessage("菜单新增成功！");
		rd.setFirstItem(menuAdd);
		return rd;
	}
	
  /** 根据userId从用户和菜单关系表中获取菜单中rightsCode(权限码)*/
	public  StringBuffer getRightsCode(Integer userId) {
    	if (userId == null) {
    		return null;
    	}
    	
    	StringBuffer rightsCode = new StringBuffer();
    		rightsCode = new StringBuffer();
    		
    		HashMap parameters = new HashMap();
    		parameters.put("userId", userId);
    		
    		List userRoleRelationList = userRoleRelationDao.find(parameters, "");
    		if(userRoleRelationList != null && userRoleRelationList.size() >0 ) {
    			for (int i = 0; i < userRoleRelationList.size(); i++) {
    				UserRoleRelation userRoleRelation = (UserRoleRelation) userRoleRelationList.get(i);
    				Role role = roleDao.get(userRoleRelation.getRoleId());
    				rightsCode.append(role.getRightsCode());
    		}
    		
    	}
    	return rightsCode;
    }

	@Override
	public ResponseData siteMenuType(HttpServletRequest request) {
		ResponseData rd = new ResponseData();
		Integer userId = SessionManager.getCurrentUserId(request);
		String menuType = String.valueOf(GlobalConstant.FALSE);
		String menuId = String.valueOf(GlobalConstant.FALSE);
		
		if (request.getParameter("menuId") != null && request.getParameter("menuId").length() > 0) {
			menuId = request.getParameter("menuId");
		} 
		
		if (request.getParameter("menuType") != null && request.getParameter("menuType").length() > 0) {
			menuType = request.getParameter("menuType");
			if (menuType.equals("1.jsp")) {
				menuType = menuType.substring(0,menuType.indexOf(".jsp"));
			}
			rd.put("userMenuRelationVOList", CacheUserMenuManager.getUserMenuRelationVOList(userId));
		}
		
		rd.put("menuId", menuId);
		rd.put("menuType", menuType);
		return rd;
	}
}
