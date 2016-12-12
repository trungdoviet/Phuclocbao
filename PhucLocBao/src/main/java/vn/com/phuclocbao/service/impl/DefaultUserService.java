package vn.com.phuclocbao.service.impl;

import vn.com.phuclocbao.service.UserService;

public class DefaultUserService implements UserService {

	@Override
	public boolean isValidUser(String username, String password) {
		
		return true;
	}

}
