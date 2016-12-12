package vn.com.phuclocbao.service.impl;

import java.sql.SQLException;

import vn.com.phuclocbao.dao.UserDao;
import vn.com.phuclocbao.service.UserService;

public class DefaultUserService implements UserService {
	private UserDao userDao;

	public UserDao getUserDao()
	{
			return this.userDao;
	}

	public void setUserDao(UserDao userDao)
	{
			this.userDao = userDao;
	}
	@Override
	public boolean isValidUser(String username, String password) {
		
		return userDao.isValidUser(username, password);

	}

}
