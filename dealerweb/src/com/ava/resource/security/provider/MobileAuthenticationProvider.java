package com.ava.resource.security.provider;

import com.ava.dao.IUserDao;
import com.ava.domain.entity.User;
import com.ava.resource.GlobalConfig;
import com.ava.resource.security.IAuthenticationProvider;
import com.ava.resource.security.UserToken;
import com.ava.resource.security.UserTokenManager;
import com.ava.resource.security.param.AuthenticationParam;
import com.ava.resource.security.param.MobileAuthenticationParam;
import com.ava.util.MyStringUtils;
import com.ava.util.ValidatorUtil;
import com.ava.util.encrypt.EncryptUtil;

public class MobileAuthenticationProvider implements IAuthenticationProvider {

	private static IUserDao userDao = (IUserDao) GlobalConfig.getBean("userDao");

	public UserToken authenticate(AuthenticationParam param) {
		if(param == null){
			return null;
		}
		MobileAuthenticationParam accountMobileParam = (MobileAuthenticationParam) param;
		String mobile = accountMobileParam.getMobile();
		String password = accountMobileParam.getPassword();
		if (MyStringUtils.isBlank(mobile) || MyStringUtils.isBlank(password)) {
			return null;
		}

		if (! ValidatorUtil.isMobile(mobile)){
			return null;
		}
		User dbUser = userDao.getByMobile(mobile);
		if(dbUser != null){
			if (dbUser.getDeletionFlag() != null && dbUser.getDeletionFlag().intValue() == 0){
				//通过解密的方式判断明文密码是否相等，因为密文可能不等
				if(EncryptUtil.decryptPassword(dbUser.getEncryptedPassword()).equals(password)){
					return UserTokenManager.regUserAccountInfo(dbUser.getId());
				}
			}
		}
		return null;
	}

}
