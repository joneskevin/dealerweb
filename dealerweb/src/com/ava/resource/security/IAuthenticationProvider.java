package com.ava.resource.security;

import com.ava.resource.security.param.AuthenticationParam;



public interface IAuthenticationProvider {

	/**用户登录认证*/
	public UserToken authenticate(AuthenticationParam param);

}
