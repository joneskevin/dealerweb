package com.ava.domain.vo;

import com.ava.domain.entity.Org;
import com.ava.domain.entity.User;
import com.ava.domain.vo.AbstractLog.OperactionObjProperty;


public interface ILog {
	public void setId(String id);
	public void setUserId(Integer userId);
	public void setActionTime(Long actionTime);
	public String getActionId();
	public void setActionId(String actionId);
	public void setActionName(String actionName);
	public void setRemark(String remark);

	/*------------操作对象常量定义end----------*/

	/*------------组织模块常量定义start----------*/
	public static final String[] addOrg = new String[]{"add_Org", "增加组织"}; 

	public static final String[] deleteOrg = new String[]{"delete_Org", "删除组织"};
	
	public static final String[] updateOrg = new String[]{"update_Org", "修改组织"};
	/*------------组织模块常量定义end----------*/

	/*------------用户模块常量定义start----------*/
	public static final String[] addUser = new String[]{"add_User", "增加用户"}; 

	public static final String[] deleteUser = new String[]{"delete_User", "删除用户"}; 

	public static final String[] updateUser = new String[]{"update_User", "修改用户"};  
	
	public static final String[] loginUser = new String[]{"void_User", "用户登陆"};  
	
	public static final String[] logoutUser = new String[]{"void_User", "用户退出"}; 
	/*------------用户模块常量定义end----------*/

	/*------------岗位模块常量定义start----------*/
	public static final String[] addUserAccount = new String[]{"add_UserAccount", "增加岗位"}; 

	public static final String[] deleteUserAccount = new String[]{"delete_UserAccount", "删除岗位"}; 

	public static final String[] updateUserAccount = new String[]{"update_UserAccount", "修改岗位"};
	/*------------岗位模块常量定义end----------*/

	/*------------操作对象常量定义start----------*/
	public static enum EOperationObject {
		org("1", Org.class, "组织", new OperactionObjProperty[]{
			new OperactionObjProperty("id", "ID"),
			new OperactionObjProperty("name", "名称"),
			new OperactionObjProperty("orgType", "类型")}
		),
		user("2", User.class, "用户", new OperactionObjProperty[]{
			new OperactionObjProperty("id", "ID"),
			new OperactionObjProperty("orgId", "组织ID"),
			new OperactionObjProperty("realName", "真实姓名"),
			new OperactionObjProperty("userName", "用户名"),
			new OperactionObjProperty("email", "Email"),
			new OperactionObjProperty("mobile", "手机号")}
		);
		

		private String id;

		private Class clazz;

		private String nick;

		private OperactionObjProperty[] displayableProperties;
		
		private EOperationObject(String id, Class clazz, String nick, OperactionObjProperty[] displayableProperties) {
			this.id = id;
			this.clazz = clazz;
			this.nick = nick;
			this.displayableProperties = displayableProperties;
		}

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}
		
		public Class getClazz() {
			return clazz;
		}

		public void setClazz(Class clazz) {
			this.clazz = clazz;
		}

		public String getNick() {
			return nick;
		}

		public void setNick(String nick) {
			this.nick = nick;
		}

		public OperactionObjProperty[] getDisplayableProperties() {
			return displayableProperties;
		}

		public void setDisplayableProperties(OperactionObjProperty[] displayableProperties) {
			this.displayableProperties = displayableProperties;
		}
	}
}
