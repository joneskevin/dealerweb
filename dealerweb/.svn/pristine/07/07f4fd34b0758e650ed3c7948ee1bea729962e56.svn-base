package com.ava.baseSystem.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.ava.base.domain.ResponseData;
import com.ava.domain.entity.Menu;
import com.ava.domain.entity.User;
import com.ava.domain.vo.UserMenuRelationVO;
import com.ava.resource.GlobalConstant;
import com.ava.resource.SessionManager;
import com.ava.resource.cache.CacheUserMenuManager;

public interface IUserMenuService {

	
	public Menu findUserMenu(int menuId);
	
	/**
	 * 查询菜单列表
	 * @param user
	 */
	public ResponseData listUserMenu(User user);
	
	/**
	 * 有权限的菜单列表
	 * @param user
	 * @return
	 */
	public List<Menu> roleMenuList(User user);
	
	/**
	 * 查询用户主界面快捷菜单中显示的列表
	 * @return
	 */
	public List<UserMenuRelationVO> listUserMenuRelationVO(Integer userId);

	/**
	 * 添加菜单
	 * 
	 * @param menuAdd
	 * @return menuIds
	 */
	public ResponseData addMenu(Menu menuAdd);
	
	/**
	 * 添加用户设置菜单
	 * 
	 * @param user
	 * @return menuIds
	 */
	public ResponseData addUserMenu(User user, String userMenus);
	
	/**
	 * 修改菜单
	 * 
	 * @param menuEdit
	 * @return ResponseData
	 */
	public ResponseData editMenu(Menu menuEdit);
	

	/**
	 * 删除一个菜单
	 * 
	 * @param menuId
	 * @return ResponseData
	 */
	public ResponseData deleteMenu(Integer menuId);
	
	/**
	 * 设置菜单来源类型
	 * @param request
	 */
	public ResponseData siteMenuType(HttpServletRequest request);
	

}
