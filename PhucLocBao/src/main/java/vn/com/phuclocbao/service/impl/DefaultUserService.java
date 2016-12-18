package vn.com.phuclocbao.service.impl;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vn.com.phuclocbao.dao.UserDao;
import vn.com.phuclocbao.service.UserService;
@Service
public class DefaultUserService implements UserService {
	@Autowired
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
