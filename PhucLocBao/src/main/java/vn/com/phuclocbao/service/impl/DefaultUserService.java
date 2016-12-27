package vn.com.phuclocbao.service.impl;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vn.com.phuclocbao.converter.UserAccountConverter;
import vn.com.phuclocbao.dao.UserDao;
import vn.com.phuclocbao.dto.UserAccountDto;
import vn.com.phuclocbao.entity.UserAccount;
import vn.com.phuclocbao.exception.BusinessException;
import vn.com.phuclocbao.exception.errorcode.PLBErrorCode;
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

	@Override
	public UserAccountDto getUserByUsername(String username) throws BusinessException {
		UserAccount entity = userDao.getUserByUsername(username);
		if(entity == null){
			throw new BusinessException(PLBErrorCode.USER_CAN_NOT_BE_FOUND.name());
		}
		return UserAccountConverter.getInstance().toDtoExtraObject(entity, new UserAccountDto());
	}

}
