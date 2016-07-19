package com.ava.system;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.ava.base.TestBase;
import com.ava.baseSystem.service.IUserRegisterLoginService;
import com.ava.domain.entity.User;
import com.ava.util.HttpUtil;

public class TestUser extends TestBase {
	@Autowired
	public IUserRegisterLoginService service;

	@Test
	public void testLogin() {
		String targetUrl = "http://127.0.0.1:9090/base/registerLogin/login.vti?r="
				+ Math.random();
		Map parameters = new HashMap();
		parameters.put("loginName", "admin@group");
		parameters.put("pseudoPassword", "000000");
		for (int i = 0; i < 100000; i++) {
			HttpUtil.doPost(targetUrl, parameters);
		}
	}
	
	public <T> T getTest() {
		T t = null;
		List<? extends User> userList = new ArrayList<User>();
		return t;
	}

	public static void main(String[] args) {
	        List list = new ArrayList();
	        list.add("qqyumidi");
	        list.add("corn");
	        list.add(100);

	        for (int i = 0; i < list.size(); i++) {
	            String name = (String) list.get(i); // 1
	            System.out.println("name:" + name);
	        }

	}

}