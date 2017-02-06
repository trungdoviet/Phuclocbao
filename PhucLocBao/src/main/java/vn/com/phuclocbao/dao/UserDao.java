package vn.com.phuclocbao.dao;

import java.util.List;

import javax.persistence.PersistenceException;

import vn.com.phuclocbao.entity.UserAccount;
import vn.com.phuclocbao.exception.BusinessException;

public interface UserDao
{
		public boolean isValidUser(String username, String password) throws BusinessException;
		public UserAccount merge(UserAccount entity) throws BusinessException;
		public UserAccount getUserByUsername(String username) throws PersistenceException;
		public List<UserAccount> findAll() throws BusinessException;
		public UserAccount persist(UserAccount ua) throws BusinessException;
		public boolean isUserExist(String username) throws BusinessException;
}
