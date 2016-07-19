package com.ava.resource;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;

/**系统配置类*/
public class GlobalConfig {

	public static String defaultAppPath = null;

	public static String specialAppPath = null;

	private static Configuration config;

	static {
		try {
			config = new PropertiesConfiguration("globalConfig.properties");
		} catch (ConfigurationException ce) {
			throw new RuntimeException(ce.getMessage());
		}
	}

	public static Object getBean(String beanName) {
		return SpringContext.getBean(beanName);
	}
	
	public static void reloadProperties(){
		try {
			config = new PropertiesConfiguration("globalConfig.properties");
		} catch (ConfigurationException ce) {
			throw new RuntimeException(ce.getMessage());
		}
	}

	/**
	 * return config info as string
	 * 
	 * @param key
	 * @return
	 */
	public static String getString(String key) {
		return config.getString(key);
	}

	public static int getInt(String key) {
		return config.getInt(key);
	}

	public static int getInt(String key, int value) {
		return config.getInt(key, value);
	}

	/** 返回程序的默认路径，注意最后没有"/"，示例：/usr/local/tomcat/webapps/demosite */
	public static String getDefaultAppPath() {
		if (defaultAppPath == null) {
			defaultAppPath = new InitAppPath().getDefaultAppPath();
		}
		defaultAppPath = defaultAppPath.replace("%20", " ");
		return defaultAppPath;
	}

	/** 返回配置文件中的程序特别指定路径，注意最后没有"/"，示例：/usr/local/tomcat/webapps/demosite */
	public static String getSpecialAppPath() {
		if (specialAppPath == null) {
			config.getString("APPPATH", null);
		}
		return specialAppPath;
	}

	/** 得到组织默认的上传文件存放目录（是虚拟地址，比如：/upload/org/12） */
	public static String getOrgUploadPath(HttpServletRequest request) {
		return GlobalConstant.UploadPathName_Org
				+ GlobalConstant.FILE_SEPARATOR
				+ SessionManager.getCurrentCompanyId(request);
	}

	/**
	 * 得到配置文件的域名，包括协议，最后不包括分隔符
	 * 
	 * @return http://www.kinsh.com
	 */
	public static String getDomain() {
		return config.getString("DOMAIN");
	}
	
	/**
	 * 得到总部email
	 * 
	 * @return 243@qq.com
	 */
	public static String getHeadquartersEmail() {
		return config.getString("HEADQUARTERS_EMAIL");
	}

	/**
	 * 得到网站的名称
	 * 
	 * @return 上海王者归来信息技术有限公司
	 */
	public static String getDomainName() {
		return config.getString("DOMAINNAME");
	}

	/**
	 * 得到配置文件的域名，包括协议，最后不包括分隔符
	 */
	public static String getFingerprintRestDomain() {
		return config.getString("FINGERPRINT_REST_DOMAIN");
	}

	/** 得到配置文件的email发送smtp主机名 */
	public static String getMail_mailhost() {
		return config.getString("MAIL_MAILHOST", "mail.vti-cn.com");
	}

	/** 得到配置文件的email发送用户名 */
	public static String getMail_fromuser() {
		return config.getString("MAIL_FROMUSER", "liuqiong@vti-cn.com");
	}

	/** 得到配置文件的email发送密码 */
	public static String getMail_password() {
		return config.getString("MAIL_PASSWORD", "123456");
	}

	/** 得到配置文件的email默认邮件主题 */
	public static String getMail_subject() {
		return config.getString("MAIL_SUBJECT", "邮件主题");
	}

	/** 得到配置文件的密码加密密钥 */
	public static String getPasswordKey() {
		return config.getString("CIPHER_PASSWORD_KEY", "kingsh");
	}

	public static void main(String[] args) {
		// System.out.println("content = "+config.getString("URL_GETWHOLEOPERATORS"));
	}
}

class InitAppPath {
	// 返回程序的默认路径，注意最后没有"/"，示例：/usr/local/tomcat/webapps/demosite
	public String getDefaultAppPath() {
		String tempAppPath = null;
		if (tempAppPath == null || "".equals(tempAppPath)) {
			tempAppPath = getClass().getClassLoader().getResource(
					GlobalConstant.FILE_SEPARATOR).getPath();
			tempAppPath = tempAppPath.substring(0, tempAppPath
					.indexOf(GlobalConstant.FILE_SEPARATOR + "WEB-INF"
							+ GlobalConstant.FILE_SEPARATOR));
			System.out.println("Reading default appPath......");
		}
		System.out.println("======================> defaultAppPath="
				+ tempAppPath);
		return tempAppPath;
	}

}
