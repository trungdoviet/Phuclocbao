package vn.com.phuclocbao.dao;

import javax.persistence.PersistenceException;

import vn.com.phuclocbao.entity.UserAccount;

public interface UserDao
{
		public boolean isValidUser(String username, String password);
		public UserAccount getUserByUsername(String username) throws PersistenceException;
}
