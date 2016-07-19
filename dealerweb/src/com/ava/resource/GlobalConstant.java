package com.ava.resource;

import java.util.HashMap;
import java.util.Map;

import com.ava.util.DateTime;
import com.ava.util.FileUtil;

/**系统常量类*/
//说明1：按模块定义资源，把各自的资源定义在各自的模块里
//说明2：注释一律用/**	xxx	*/格式
public class GlobalConstant {
	
	public final static String SYSTEM_NAME_VW="VW";
	
	public final static String SYSTEM_NAME_SK="SK";
	
	public final static String BASE_SYSTEM_NAME="/dealerweb";

	// ---------------------global start----------------------------------
	/**================================== system const start ==================================*/
	/** 当前系统分隔符 */
	public final static String FILE_SEPARATOR = "/";

	public static final String REQUEST_CURRENT_URL = "requestCurrentUrl";

	public static final String API_USER_LOGIN_TOKEN = "apiUserLoginToken";//api接口登录的UserToken对象

	public final static String SESSION_OPERATOR = "operator";

	public static final String SESSION_USER_ID = "sessionUserId";

	public static final String SESSION_USER_LAST_OPERATION_TIME = "lastOperationTime";

	public static final String SESSION_PROJECT_ID = "projectId";
	
	public static final String SESSION_TASK_ID = "taskId";

	public static final String SESSION_DEFAULT_PROJECT_ID = "defaultProjectId";

	public static final String SESSION_DEFAULT_TASK_ID = "defaultTaskId";
	
	public static final String SESSION_FORUM_ID = "forumId";
	
	public static final String SESSION_GATE_URL = "gateUrl";

	public static final String SESSION_HTML_TAG_INDEX = "sessionHtmlTagIndex";

	public static final String COOKIE_USER_REMEMBER_ME = "rememberMe";

	public static final String COOKIE_USER_LOGIN_NAME = "loginName";

	public static final String COOKIE_USER_PSEUDO_PASSWORD = "pseudoPassword";
	
	public static final String SESSION_USER_PSEUDO_PASSWORD = "sessionPseudoPassword";

	public static final String TOTAL_RECORDS = "totalRecords";

	public static final String TOTAL_PAGE = "TOTAL_PAGE";

	public static final String PAGE_NO = "pageNo";

	public static final String PAGE_SIZE = "pageSize";

	public static final String PAGE_START_INDEX = "startIndex";

	public static final String ORDERBY_FIELD = "orderbyField";

	public static final String ORDERBY_SORT = "orderbySort";

	public static final String DEFAULT_AVATAR = "/images/noAvatar.gif";
	
	/** 百度地图 KEY */
	public static final String BAIDU_MAP_AK = "91a73a33c8964682fdba3f89dc96f279";
	
	/** 根节点ID */
	public static final int DEFAULT_GROUP_ORG_ID = 1;
	/** 根节点名称 */
	public static final String DEFAULT_GROUP_ORG_NAME = "上汽大众";
	/** 运营中心根节点ID */
	public static final String OPERATIONS_CENTER_ID="10";
	/** 网络发展部根节点ID */
	public static final String NETWORK_DEVELOPMENT_ID="11";
	
	/** ORG层级ID--分销中心 */
	public static final int ORG_LEVEL_ID_SALE_CENTER = 4;
	/** ORG层级ID--经销商 */
	public static final int ORG_LEVEL_ID_DEALER = 5;
	
	/** 系统管理员岗位ID[没用到] */
	public static final int DEFAULT_ADMIN_POSITION_ID = 1;
	
	/** 总部管理员角色ID */
	public static final int HEADQUARTERS_ROLE_ID = 1;
	/** 运营管理员角色ID */
	public static final int OPERATIONS_ADMIN_ROLE_ID = 2;
	/** 分销中心角色ID */
	public static final int DISTRIBUTION_CENTER_ADMIN_ROLE_ID = 3;
	/** 经销商角色ID */
	public static final int DEFAULT_DEALER_ROLE_ID = 4;
	
	/** 查询列表默认行数 */
	public static final int DEFAULT_TABLE_ROWS = 16;
	
	/** 系统处理结果消息MAP的ID */
	public final static String MSG_RESULT_MAP_ID = "msgResultMapId";
	/** 系统处理结果消息内容标志 */
	public final static String MSG_RESULT_CONTENT = "msgResultContent";
	/** 系统处理结果消息级别 */
	public final static String MSG_RESULT_LEVEL = "msgResultLevel";
	/** 系统处理结果消息级别---成功标志 */
	public static final String MSG_RESULT_LEVEL_SUCCESS = "1";
	/** 系统处理结果消息级别---警告标志 */
	public static final String MSG_RESULT_LEVEL_WARNING = "2";
	/** 系统处理结果消息级别---错误标志 */
	public static final String MSG_RESULT_LEVEL_ERROR = "3";

	/** 系统中用到的数据字典中的常量---中国 */
	public static final Integer CHINA_DEFAULT_ID = 34;//由数据字典表决定
	
	/**用户临时密码有效期---值代表小时数*/
	public static final Integer TEMP_PASSWORD_EXPIRATION= 3;//由数据字典表决定

	
	/** 系统中用到的常量---true */
	public static final int TRUE = 1;
	/** 系统中用到的常量---false */
	public static final int FALSE = 0;
	/**================================== system const end ==================================*/

	/** 检查是否重新构建搜索实例的时间间隔 */
	public static final long SEARCHER_REFRESH_INTERVAL = 60 * 1000;	//60 seconds
	
	
	/**================================== 系统处理常用返回值 start ==================================*/
	/**	用户未登录：-101	*/
	public final static int RETURN_RESULT_USER_NOT_LOGIN = -101;
	/**	用户没有该操作权限：-102	*/
	public final static int RETURN_RESULT_USER_NO_RIGHT = -102;
	/**	组织海星币不足：-151	*/
	public final static int RETURN_RESULT_ORG_NOT_ENOUGH_CASH = -151;
	/**	组织空间总容量超限：-152	*/
	public final static int RETURN_RESULT_ORG_NOT_ENOUGH_USAGE = -152;
	/**	数据库该记录不存在：-404	*/
	public final static int RETURN_RESULT_RECORD_NOT_EXIST = -404;
	/**================================== 系统处理常用返回值 end ==================================*/
	
	
	/**================================== 上传文件的相对路径定义 start ==================================*/
	/** 通用的上传文件的相对路径名，例如：/upload */
	public final static String UploadPathName = FILE_SEPARATOR + "upload";

	/** 通用的上传文件的时间路径片段，格式： /2008/06/01 */
	public final static String UploadPathName_Date = GlobalConstant.FILE_SEPARATOR + DateTime.getYear()
			+ GlobalConstant.FILE_SEPARATOR + DateTime.getMonth()
			+ GlobalConstant.FILE_SEPARATOR + DateTime.getDay();

	/** 上传文件的用户图标（头像）的相对路径名，例如：/upload/avatar */
	public final static String UploadPathName_Avatar = UploadPathName + FILE_SEPARATOR + "avatar";

	/** 组织上传文件的相对路径名，例如：/upload/org */
	public final static String UploadPathName_Org = UploadPathName + FILE_SEPARATOR + "org";

	/** 组织上传文件的临时地址的路径名，例如：/upload/temp */
	public final static String UploadPathName_Temp = UploadPathName + FILE_SEPARATOR + "temp";

	/** 通用的上传图片的相对路径名，例如：/upload/picture */
	public final static String UploadPathName_Picture = UploadPathName + FILE_SEPARATOR + "picture";

	/** 上传文件的附件的相对路径名，例如：/upload/post */
	public final static String UploadPathName_Attachment = UploadPathName + FILE_SEPARATOR + "attachment";

	/** 上传文件的消息吧附件的相对路径名，例如：/upload/post */
	public final static String UploadPathName_Post = UploadPathName + FILE_SEPARATOR + "post";

	/** 文件上传大小限制，单位：byte */
	public final static Integer UPLOAD_FILE_MAX_SIZE = 1024 * 1024 * 10;
	public final static String UPLOAD_FILE_MAX_SIZE_NICK = FileUtil.toMbSize(UPLOAD_FILE_MAX_SIZE) + "M";

	/** 上传类型：图片类型(jpg,gif,...) */
	public final static int UPLOAD_TYPE_GROUP_PICTURE = 10;
	/** 上传类型：视频动画类型(MP4,swf,...) */
	public final static int UPLOAD_TYPE_GROUP_VEDIO = 12;
	/** 上传类型：文档类型(doc,pdf,...) */
	public final static int UPLOAD_TYPE_GROUP_DOCUMENT = 14;
	/** 上传类型：压缩文件类型(rar,zip) */
	public final static int UPLOAD_TYPE_GROUP_COMPRESSION = 20;
	/** 上传类型：其它类型 */
	public final static int UPLOAD_TYPE_GROUP_OTHER = 99;

	/** 上传类型：文件类型(doc) */
	public final static Integer UPLOAD_TYPE_WORD = 30;
	/** 上传类型：文件类型(xls) */
	public final static Integer UPLOAD_TYPE_XLS = 32;
	/** 上传类型：文件类型(ppt) */
	public final static Integer UPLOAD_TYPE_PPT = 31;
	/** 上传类型：文件类型(pdf) */
	public final static Integer UPLOAD_TYPE_PDF = 40;

	/**================================== 上传文件的相对路径定义 end ==================================*/

	// ---------------------global end----------------------------------
	
	
	//	 ---------------------systemManagement
	// //----operator start--------------------
	
	/** BASE帮助设置	*/
	public static final Integer HELP_BASE_QUESTION_LEVEL3 = new Integer(50); // BASE帮助字典
	
	/** 帮助类别--base */
	public static final Integer HELP_BASE_QUESTION_TYPE = new Integer(0);
	////----operator end--------------------
	
	// //----common start--------------------
	/** 登陆后跳转类型 start */
	/** 登陆后跳转类型	*/
	public static final String SESSION_LOGIN_FORWARD_TYPE = "JUMP_TYPE";
	/** 登陆后需要跳转回的页面	*/
	public static final String SESSION_LOGIN_FORWARD_URL = "FORWARD_URL";

	public static final String SESSION_LOGIN_FORWARD_DATA = "FORWARD_DATA";

	// ----forward type start--------------------
	/** 访问私有页面，未登陆情况下需要跳转到登陆页面，登陆后跳回该页面 */
	public static final Integer SESSION_LOGIN_FORWARD_TYPE_COMMON = new Integer(
			10);

	/** 访问私有页面，未登陆情况下需要跳转到登陆页面，登陆后跳回该页面,并保存数据 */
	public static final Integer SESSION_LOGIN_FORWARD_TYPE_COMMON_PRODUCT_REMARK = new Integer(
			11);

	/** 访问私有页面，未登陆情况下需要跳转到登陆页面，登陆后关闭当前页面并跳回父页面，不需要保存数据 */
	public static final Integer SESSION_LOGIN_FORWARD_TYPE_CLOSE_RELOAD = new Integer(
			20);
	// ----forward type end--------------------
	/** 登陆后跳转类型 end */

	/** 产品浏览历史的Cookie保存记 */
	public static final String COOKIE_PRODUCT_HISTORY = "PRODUCT_HISTORY";

	/** 产品浏览历史记录保存数量 */
	public final static int PRODUCT_HISTORY_LENGTH = 10;
	// //----common end--------------------

	// //----backContent start--------------------
	/** 资讯（Info）表的sortId定义 start */
	/** 资讯类别---产品推荐资讯 */
	public static final Integer INFO_SORT_01 = new Integer(80);

	/** 资讯类别---最新热点资讯 */
	public static final Integer INFO_SORT_02 = new Integer(82);

	/** 资讯（Info）表的sortId定义 end */
	// //----backContent end--------------------
	
	// //----dataDictionary start--------------------
	/** 操作员角色 */
	public static final Integer DICTIONARY_OPERATOR_ROLE = new Integer(1);
	/** 客户级别 */
	public static final Integer DICTIONARY_CUSTOMER_RANK = new Integer(2);
	/** 产品种类 */
	public static final Integer DICTIONARY_PRODUCT_SORT = new Integer(10);
	/** 帮助中心 */
	public static final Integer DICTIONARY_BASE_HELP_CENTER = new Integer(50);
	/** 新闻类别 */
	public static final Integer DICTIONARY_NEWS_SORT = new Integer(100);
	/** 行政地区 */
	public static final Integer DICTIONARY_AREA = new Integer(101);
	/** 行业分类 */
	public static final Integer DICTIONARY_INDUSTRY_SORT = new Integer(102);
	// //----dataDictionary end--------------------
	
	//	 //----help type start--------------------
	
	/** 帮助类别--base */
	public static final Integer HELPCENTER_TYPE_BASE = new Integer(1);
	/** 帮助类别--search */
	public static final Integer HELPCENTER_TYPE_SEARCH = new Integer(4);
	/** 帮助类别--admin */
	public static final Integer HELPCENTER_TYPE_ADMIN = new Integer(5);
	/** 帮助类别--register */
	public static final Integer HELPCENTER_TYPE_REGISTER = new Integer(6);
	//	 //----help type end--------------------
	
	// //---- abstract org start--------------------
	/** 抽象组织--类型--公司 */
	public static final int ABSTRACT_ORG_TYPE_COMPANY = 10;
	/** 抽象组织--类型--部门 */
	public static final int ABSTRACT_ORG_TYPE_DEPARTMENT = 20;	

	/** 抽象组织--职能类型--集团 */
	public static final int ABSTRACT_ORG_FUNCTION_TYPE_GROUP = 10;
	/** 抽象组织--职能类型--业务部门*/
	public static final int ABSTRACT_ORG_FUNCTION_TYPE_BIZ = 20;
	/** 抽象组织--职能类型--后勤部门 */
	public static final int ABSTRACT_ORG_FUNCTION_TYPE_BACK = 30;
	/** 抽象组织--职能类型--工厂 */
	public static final int ABSTRACT_ORG_FUNCTION_TYPE_FACTORTY = 40;
	
	// //----abstract or end--------------------

	/** 通用申请--用来暂时附件 */
	public static final String APPRO_PROPOSAL_ATTACHMENT_MAP = "APPRO_PROPOSAL_ATTACHMENT_MAP";
	//	//----approApproval base type start--------------------
	/** 申请类型--请假 */
	public static final Integer APPRO_PROPOSAL_BASE_TYPE_LEAVE = new Integer(10);
	/** 申请类型--外出 */
	public static final Integer APPRO_PROPOSAL_BASE_TYPE_OUT = new Integer(11);
	//	//----approApproval base type end--------------------
	
	//	//----请假类型 start--------------------
	/** 请假类型--年休 */
	public static final int APPRO_LEAVE_TYPE_YEARREST = 1010;
	/** 请假类型--病假 */
	public static final int APPRO_LEAVE_TYPE_ILLNESS = 1020;
	/** 请假类型--事假 */
	public static final int APPRO_LEAVE_TYPE_AFFAIR = 1030;
	/** 请假类型--其他*/
	public static final int APPRO_LEAVE_TYPE_OTHER = 1090;
	//	//----请假类型 end--------------------
	
	//	//----外出类型 start--------------------
	/** 外出类型--公出 */
	public static final int APPRO_OUT_TYPE_AWAY = 1110;
	/** 外出类型--出差 */
	public static final int APPRO_OUT_TYPE_EVECTION = 1120;
	/** 外出类型--其他*/
	public static final int APPRO_OUT_TYPE_OTHER = 1190;
	//	//----外出类型 end--------------------
	
	//	//----申请单类型 start--------------------
	/** 申请单类型--新装 */
	public static final int PROPOSAL_TYPE_INSTALLATION = 1;
	/** 申请单类型--拆除 */
	public static final int PROPOSAL_TYPE_DEMOLITION = 2;
	/** 申请单类型--新装审批 */
	public static final int PROPOSAL_TYPE_INSTALLATION_PENDING = 3;
	/** 申请单类型--拆除审批 */
	public static final int PROPOSAL_TYPE_DEMOLITION_PENDING = 4;
	/** 申请单类型--换装 */
	public static final int PROPOSAL_TYPE_REPLACE= 5;
	/** 申请单类型--解绑 */
	public static final int PROPOSAL_TYPE_UNBIND = 6;
	
	//	//----申请单类型 end--------------------
	
	//	//----proposal status start--------------------
	/** 申请状态--无申请 */
	public static final int PROPOSAL_STATUS_ZERO = 5;
	/** 申请状态--待审核 */
	public static final int PROPOSAL_STATUS_PROCESSING = 10;
	/** 申请状态--已通过 */
	public static final int PROPOSAL_STATUS_PASSED = 20;
	/** 申请状态--未通过 */
	public static final int PROPOSAL_STATUS_UNPASSED = 30;
	/** 申请状态--重点城市不安装 */
	public static final int PROPOSAL_STATUS_IMPORTANT_NO = 40;
	/** 申请状态--非重点城市不安装 */
	public static final int PROPOSAL_STATUS_NO_IMPORTANT_NO = 50;
	/** 申请状态--已安装 */
	public static final int PROPOSAL_STATUS_INSTALLED = 60;
	/** 申请状态--已拆除 */
	public static final int PROPOSAL_STATUS_UNINSTALLED = 70;
	//	//----proposal status end--------------------
	
	//	//----approval status start--------------------
	/** 审批状态--通过 */
	public static final int APPROVAL_STATUS_PASSED = 20;
	/** 审批状态--不通过 */
	public static final int APPROVAL_STATUS_UNPASSED = 30;
	//	//----approval status end--------------------
	
	//	//----attachment status start--------------------
	/** 申请单附件状态--只读*/
	public static final int APPRO_ATTACHMENT_STATUS_READONLY = 10;
	/** 申请单附件状态--可维护（新增，删除，编辑） */
	public static final int APPRO_ATTACHMENT_STATUS_MAINTAIN = 20;
	//	//----attachment status end--------------------

	// //----message's moduleId start--------------------
	/***
	 * 消息中的模块ID共6位：abcdef
	 * ab：子系统区分ID（由于在首位，故00~09不能用）, PC端：10; iOS端:20; Android端:30
	 * cd：模块区分ID,共用模块（比如：登录过滤器<00>;日志模块<01>，上传模块<02>）区分ID范围：00~19；业务模块区分ID范围：20~99；
	 * ef：消息分类ID
	 */
	/** 上传模块 */
	public static final int MESSAGE_MODULE_ID_UPLOAD = -100200;
	/** 审批模块 */
	public static final int MESSAGE_MODULE_ID_APPRO_PROPOSAL = -102100;
	/** 会议模块 */
	public static final int MESSAGE_MODULE_ID_CONFERENCE = -102200;
	// //----message's moduleId end--------------------

	//----car start--------------------
	
	// //----brand defination start---------------------------
	/** 品牌---斯柯达 */
	public static final int CAR_BRAND_SKODA = 1;
	/** 品牌---大众 */
	public static final int CAR_BRAND_VM = 2;
	// //----brand defination end--------------------
	
	// //----configure type defination start------------------
	/** 配置类型--不配 */
	public static final int CONFIGURE_TYPE_NO = 0;
	/** 配置类型--选配 */
	public static final int CONFIGURE_TYPE_MAY = 1;
	/** 配置类型--必配 */
	public static final int CONFIGURE_TYPE_MUST = 2;
	// //----configure type defination end--------------------
	
	// //----configure status defination start------------------
	/** 配置状态--初始 */
	public static final int CONFIGURE_STATUS_INIT = 1;
	/** 配置状态--新装待审核*/
	public static final int CONFIGURE_STATUS_PROCESSING = 10;
	/** 配置状态--待安装 */
	public static final int CONFIGURE_STATUS_WATTING = 20;
	/** 配置状态--已安装 */
	public static final int CONFIGURE_STATUS_INSTALLED = 30;
	/** 配置状态--拆装待审核 */
	public static final int CONFIGURE_STATUS_DISASSEMBLY_PENDING = 40;
	/** 配置状态--待拆除 */
	public static final int CONFIGURE_STATUS_DEMOLISHING = 50;
	/** 配置状态--锁定 */
	public static final int CONFIGURE_STATUS_UNINSTALLED = 60;
	/** 配置状态--重点不安装 */
	public static final int CONFIGURE_STATUS_IMPORTANT_NO = 70;
	/** 配置状态--非重点不安装*/
	public static final int CONFIGURE_STATUS_NO_IMPORTANT_NO = 80;
	/** 配置状态--不安装 */
	public static final int CONFIGURE_STATUS_NO = 90;
	// //----configure status defination end--------------------
	
	//----car end--------------------
	
	//----system start--------------------
	// //----driveLine type defination start--------------------
	/** 行驶路线类型---加油 */
	public static final int DRIVE_LINE_TYPE_OIL = 1;
	/** 行驶路线类型---试驾 */
	public static final int DRIVE_LINE_TYPE_TEST_DRIVE = 2;
	// //----driveLine type defination end--------------------
	
	// //----driveLine color start--------------------
	/** 加油路线--背景颜色 */
	public static final String OIL_FILL_COLOR = "#8B864E";
	/** 加油路线--边框颜色 */
	public static final String OIL_STROKE_COLOR = "#8B795E";
	
	/** 试驾路线A--背景颜色 */
	public static final String TEST_DRIVE_A_FILL_COLOR = "#84C1FF";
	/** 试驾路线A--边框颜色 */
	public static final String TEST_DRIVE_A_STROKE_COLOR = "#004B97";
	
	/** 试驾路线B--背景颜色 */
	public static final String TEST_DRIVE_B_FILL_COLOR = "#CD853F";
	/** 试驾路线B--边框颜色 */
	public static final String TEST_DRIVE_B_STROKE_COLOR = "#CD6600";
	
	/** 试驾路线D--背景颜色 */
	public static final String TEST_DRIVE_D_FILL_COLOR = "#104E8B";
	/** 试驾路线D--边框颜色 */
	public static final String TEST_DRIVE_D_STROKE_COLOR = "#191970";
	
	/** 试驾路线E--背景颜色 */
	public static final String TEST_DRIVE_E_FILL_COLOR = "#EE6363";
	/** 试驾路线E--边框颜色 */
	public static final String TEST_DRIVE_E_STROKE_COLOR = "#FF0000";
	
	/** 试驾路线F--背景颜色 */
	public static final String TEST_DRIVE_F_FILL_COLOR = "#CECEFF";
	/** 试驾路线F--边框颜色 */
	public static final String TEST_DRIVE_F_STROKE_COLOR = "#2828FF";
	// //----driveLine color end--------------------
	
	// //----driveLine fenceType defination end--------------------
	/** 行驶路线---围栏类型---单围栏 */
	public static final int DRIVE_LINE_FENCE_TYPE_SINGLE = 1;
	/** 行驶路线---围栏类型---双围栏 */
	public static final int DRIVE_LINE_FENCE_TYPE_DOUBLE = 2;
	// //----driveLine fenceType defination end--------------------

	// //----fence type defination end--------------------
	/** 围栏类型---圆*/
	public static final int FENCE_TYPE_CIRCLE = 0;
	/** 围栏类型---矩形 */
	public static final int FENCE_TYPE_RECTANGLE = 1;
	/** 围栏类型---多边形 */
	public static final int FENCE_TYPE_POLYGON = 2;
	// //----fence type defination end--------------------

	// //----fence ban type defination end--------------------
	/** 禁止类型---禁止驶出*/
	public static final int BAN_TYPE_OUT = 0;
	/** 禁止类型---禁止驶入 */
	public static final int BAN_TYPE_IN = 1;
	// //----fence ban type defination end--------------------
	
	//----system end--------------------
	
	// //----经销商模块 start--------------------
	
	//----经销商上下班时间  start--------------------
	/** 经销商--上班时间*/
	public static final int DEALER_START_WORK_TIME = 7;
	/** 经销商--下班时间*/
	public static final int DEALER_END_WORK_TIME = 19;
	//----经销商上下班时间 end--------------------
	
	//----网络形态  start--------------------
	/** 网络形态--4s店*/
	public static final int DEALER_TYPE_4S_SHOP = 1;
	/** 网络形态--城市展厅*/
	public static final int DEALER_TYPE_CITY_SHOWROOM = 2;
	/** 网络形态--直管直营店*/
	public static final int DEALER_TYPE_STRAIGHT_DIRECT_SHOP = 3;
	/** 网络形态--直营店*/
	public static final int DEALER_TYPE_NON_STRAIGHT_DIRECT_SHOP = 4;
	/** 网络形态【运维需求才会启用该属性】--维修站*/
	public static final int DEALER_TYPE_MAINTENANCE_STATION = 5;
	//----网络形态 end--------------------
	
	//----经销商状态  start--------------------
	/** 经销商状态--重点城市*/
	public static final int KEY_CITY = 1;
	/** 经销商状态--非重点城市*/
	public static final int NON_KEY_CITY = 0;
	//----经销商状态 end--------------------
	
	//----经销商时间段  start--------------------
	public static final int HOUR_1= 1;
	public static final int HOUR_2= 2;
	public static final int HOUR_3= 3;
	public static final int HOUR_4= 4;
	public static final int HOUR_5= 5;
	public static final int HOUR_6= 6;
	public static final int HOUR_7= 7;
	public static final int HOUR_8= 8;
	public static final int HOUR_9= 9;
	public static final int HOUR_10= 10;
	public static final int HOUR_11= 11;
	public static final int HOUR_12= 12;
	public static final int HOUR_13= 13;
	public static final int HOUR_14= 14;
	public static final int HOUR_15= 15;
	public static final int HOUR_16= 16;
	public static final int HOUR_17= 17;
	public static final int HOUR_18= 18;
	public static final int HOUR_19= 19;
	public static final int HOUR_20= 20;
	public static final int HOUR_21= 21;
	public static final int HOUR_22= 22;
	public static final int HOUR_23= 23;
	public static final int HOUR_24= 24;
	//----经销商时间段 end--------------------
	
	// //----经销商模块 end--------------------
	
	// //----设备模块 start--------------------
	
	//----设备状态  start--------------------
	/** 设备状态--激活*/
	public static final int BOX_STATUS_ACTIVATION = 1;
	/** 设备状态--未激活*/
	public static final int BOX_STATUS_INACTIVE = 0;
	//----经销商状态 end--------------------
	
	// //----设备模块 end--------------------
	
	// 违规模块 start
	/** 违规类型--路线违规*/
	public static final int LINE_VIOLATION_TYPE = 1;
	/** 时间违规*/
	public static final int TIME_VIOLATION_TYPE = 2;
	// 违规月统计 小年  eg 2013
	public static final int LESS_VIOLATION_YEAR = 1;
	// 违规月统计 大年  eg 2014
	public static final int BIGGER_VIOLATION_YEAR = 2;
	
	public static final String MONTH_1= "01";
	public static final String MONTH_2= "02";
	public static final String MONTH_3= "03";
	public static final String MONTH_4= "04";
	public static final String MONTH_5= "05";
	public static final String MONTH_6= "06";
	public static final String MONTH_7= "07";
	public static final String MONTH_8= "08";
	public static final String MONTH_9= "09";
	public static final String MONTH_10= "10";
	public static final String MONTH_11= "11";
	public static final String MONTH_12= "12";
	
	public static final int SEASON_1= 1;
	public static final int SEASON_2= 2;
	public static final int SEASON_3= 3;
	public static final int SEASON_4= 4;
	// 违规模块 end
	
	/** 报备申请 start*/
	public static final int FILING_PROPOSAL_OUT_TYPE=1;
	public static final int FILING_PROPOSAL_REPAIR_TYPE=2;
	
	public static final int FILING_PROPOSAL_STATUS_INIT=0;
	public static final int FILING_PROPOSAL_STATUS_YES=1;
	public static final int FILING_PROPOSAL_STATUS_NO=2;
	public static final int FILING_PROPOSAL_STATUS_CANCEL=3;
	/** 报备申请 end*/
	
	/** 网关协议解析消息编码 start*/
	public static final int PROTOCOL_GATEWAY_MESSAGE_CODE_GET_COMMON=257;  //获取指令 
	public static final int PROTOCOL_GATEWAY_MESSAGE_CODE_UPDATE_STATUS=514; //状态变更
	public static final int PROTOCOL_GATEWAY_MESSAGE_CODE_SET_CONFIG=769;   //参数配置
	public static final int PROTOCOL_GATEWAY_MESSAGE_CODE_IGNITE_FLAMEOUT=1281; //点火熄火信息
	public static final int PROTOCOL_GATEWAY_MESSAGE_CODE_QUERY_VEHICLE_STATUS=1282; //车辆状态查询
	public static final int PROTOCOL_GATEWAY_MESSAGE_CODE_TEST_MESSAGE_UP=1283;  //测试信息上报
	public static final int PROTOCOL_GATEWAY_MESSAGE_CODE_UP_GSP=1284;           //车辆GPS数据
	public static final int PROTOCOL_GATEWAY_MESSAGE_CODE_VEHICLE_ALARM=1537;    //车辆状态异常报警
	public static final int PROTOCOL_GATEWAY_MESSAGE_CODE_DRIVING_ERROR_REMIND=1538;  //错误驾驶行为提醒
	public static final int PROTOCOL_GATEWAY_MESSAGE_CODE_OTA_UPDATE=2561;            //OTA升级
	public static final int PROTOCOL_GATEWAY_MESSAGE_CODE_OBD_DISMANTLE_ALERT=2562;   //上传拆除报警
	public static final int SemicyclePointTimeLen=150;   //监测点时间范围内150S
	/** 网关协议解析消息编码 end*/
	
	/** 网关协议解析异常定义 start*/
	public static final int PROTOCOL_GATEWAY_SUCCESS=0; //成功
	public static final int PROTOCOL_GATEWAY_ERROR_CODE_10000=10000; //10000  此应答码是针对查询处理类的请求，根据请求参数查无记录导致无法处理业务则返回此码
	public static final int PROTOCOL_GATEWAY_ERROR_CODE_10001=10001; //10001 请求协议体不符合协议规范-协议体 长度不符合规范或是协议中必添项未填写
	public static final int PROTOCOL_GATEWAY_ERROR_CODE_10002=10002; //10002 请求协议体不符合协议规范-请求参数值的格式错误（例如：协议要求为数值，但实际传输为汉字）
	public static final int PROTOCOL_GATEWAY_ERROR_CODE_10003=10003; //10003  用户身份验证错误
	public static final int PROTOCOL_GATEWAY_ERROR_CODE_10004=10004; //10004  用户服务已停用，无法使用服务
	public static final int PROTOCOL_GATEWAY_ERROR_CODE_10005=10005; //10005  用户未激活 
	public static final int PROTOCOL_GATEWAY_ERROR_CODE_10006=10006; //10006  服务器内部错误 
	public static final int PROTOCOL_GATEWAY_ERROR_CODE_10007=10007; //10007  其它(消息编码不存在)
	public static final int PROTOCOL_GATEWAY_ERROR_CODE_10008=10008; //10008  VIN错误
	public static final int PROTOCOL_GATEWAY_ERROR_CODE_10009=10009; //10009  IMSI错误
	public static final int PROTOCOL_GATEWAY_ERROR_CODE_10010=10010; //10010  TboxID错误
	public static final int PROTOCOL_GATEWAY_ERROR_CODE_10011=10011; //10011  SIM卡电话号码不存在
	public static final int PROTOCOL_GATEWAY_ERROR_CODE_10012=10012; //10012  SIM与销售数据不一致 
	public static final int PROTOCOL_GATEWAY_ERROR_CODE_10013=10013; //10013  Token错误
	/** 网关协议解析异常定义 end*/
	
	//----违规明细 start--------------------
	/** 试驾状态--有效 合规 */
	public static final int TEST_DRIVE_STATUS_VALID  = 1;
	/** 试驾状态--无效 */
	public static final int TEST_DRIVE_STATUS_INVALID  = 0;
	/** 试驾状态--违规 */
	public static final int TEST_DRIVE_STATUS_UNLAW =2 ;
	/** 试驾状态--加油 */
	public static final int TEST_DRIVE_STATUS_FUEL  = 3;
	/** 试驾状态-- 4S店内试跑  */
	public static final int TEST_DRIVE_COMPANY_INNER  = 4;
	
	/** 非工作时间试驾状态*/
	public static final int TEST_DRIVE_CURRENT_STATUS_NOT_WORK_TIME  = 4;

	//----违规明细 end--------------------
	
	/**报警类型 start*/
	public static final int ALERT_TIME=1;/**非工作时间报警*/
	public static final int ALERT_LINE=2;/**越界报警*/
	public static final int ALERT_STORIGE_BATT=3;/**断电报警*/
	public static final int ALERT_BSK_LAMPE=4;/**灯缺失报警*/
	public static final int ALERT_KUEH_MITTEL=5;/**冷却液液位报警*/
	public static final int ALERT_HEISSL=6;/**冷却液温度报警*/
	public static final int ALERT_WASCH_WASSER=7;/**风窗清洗液缺少报警*/
	public static final int ALERT_STA_OELDR=8;/**机油报警*/
	/**报警类型 end*/
	
	
	//----数据抽取 start--------------------
	/** 抽取天数 */
	public static final int EXTRACTION_DAYS = 5;
	/** 抽取类型--所有 */
	public static final int EXTRACTION_TYPE_ALL = 1;
	/** 抽取类型--基础数据 */
	public static final int EXTRACTION_TYPE_BASE = 2;
	/** 抽取类型--试驾数据 */
	public static final int EXTRACTION_TYPE_TEST_DRIVE = 3;
	/** 抽取类型--违规数据 */
	public static final int EXTRACTION_TYPE_VIOLATION = 4;
	//----数据抽取 end--------------------
	
	/** 缓存默认过期时间 为24小时*/
	public static final int CACHE_EXPIRE_TIME = 172800;
	
	/** 重跑CODE */
	public static final String TEST_DRIVE_REHANDLE_CODE="1010_";
	
	/** 重跑任务状态-准备运行 */
	public static final String TASK_STAUTS_READY="10";
	
	/** 重跑任务状态-正在运行 */
	public static final String TASK_STAUTS_RUNNING="20";
	
	/** 重跑任务状态-运行失败 */
	public static final String TASK_STAUTS_FAIL="30";
	
	/** 重跑任务状态-完成 */
	public static final String TASK_STAUTS_COMPLETE="40";
	
	/** 重跑任务状态-运行失败 */
	public static final String TASK_STAUTS_ERROR="50";
	
	/** 操作日志类型-删除非活跃 */
	public static final int OPERATION_TYPE_DEL_NO_TEST = 1;
	
	/** 操作日志类型-删除车型配置调研 */
	public static final int OPERATION_TYPE_DEL_CAR_STYLE_SURVEY = 2;
	
	/** GPS、点火、熄火都缺失*/  
	public static String LOG_DEFECT_EMPTY="20001";
	
	/** 点火缺失*/  
	public static String LOG_DEFECT_ON="20002";
	
	/** 熄火缺失*/  
	public static String LOG_DEFECT_OUT="20003";
	
	/** 点火、熄火缺失*/  
	public static String LOG_DEFECT_ON_OUT="20004";
	
	/** GPS缺失*/  
	public static String LOG_DEFECT_GPS="20005";
	
	/** 报文上报延迟*/  
	public static String LOG_REQUEST_TIME_OUT="20006";
	
	/** 报文上报延迟*/  
	public static String LOG_REQUEST_TIME_ERROR="20007";
	
	/** 用户身份验证错误*/  
	public static String LOG_USER_NOT_EXIST="10003";
	
	
	/** 无报文缺失*/  
	public static String LOG_DEFECT_NOT="20007";
	
	/** 未处理状态*/  
	public static int LOG_HANDLE_NORMAL=10;
	/** 已开始重跑状态*/  
	public static int LOG_HANDLE_BEGIN=11;
	/** 已重跑状态*/  
	public static int LOG_HANDLE_END=12;
	
	/** 错误VIN码列表*/  
	public static Map<String,String> ERROR_VIN_LIST = new HashMap<>();
	static{
		ERROR_VIN_LIST.put("EEPR0M-READ-ERR0R", "EEPR0M-READ-ERR0R");
		ERROR_VIN_LIST.put("#################", "#################");
	}
}
