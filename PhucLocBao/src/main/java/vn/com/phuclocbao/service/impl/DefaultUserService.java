package vn.com.phuclocbao.service.impl;

import java.io.IOException;
import java.sql.SQLException;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vn.com.phuclocbao.converter.UserAccountConverter;
import vn.com.phuclocbao.dao.PersistenceExecutable;
import vn.com.phuclocbao.dao.UserDao;
import vn.com.phuclocbao.dto.UserAccountDto;
import vn.com.phuclocbao.entity.UserAccount;
import vn.com.phuclocbao.exception.BusinessException;
import vn.com.phuclocbao.exception.errorcode.PLBErrorCode;
import vn.com.phuclocbao.service.BaseService;
import vn.com.phuclocbao.service.UserService;
import vn.com.phuclocbao.util.PasswordHashing;
@Service
public class DefaultUserService extends BaseService implements UserService {
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
	public boolean isValidUser(String username, String password) throws BusinessException {
		
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
	
	@Transactional
	@Override
	public UserAccountDto saveUser(UserAccountDto dto) throws BusinessException {
		
		return methodWrapper(new PersistenceExecutable<UserAccountDto>() {
			@Override
			public UserAccountDto execute() throws BusinessException, ClassNotFoundException, IOException {
				UserAccount user = userDao.getUserByUsername(dto.getUsername());
				if(user != null){
					user.setEmail(dto.getEmail());
					if(StringUtils.isNotEmpty(dto.getPassword())){
						String hashingPassword = PasswordHashing.hashMD5(dto.getPassword());
						user.setPassword(hashingPassword);
					}
					user.setFullname(dto.getFullname());
					userDao.merge(user);
				}
				return UserAccountConverter.getInstance().toDtoExtraObject(userDao.getUserByUsername(dto.getUsername()), new UserAccountDto());
			}
			
		});
	}

	@PersistenceContext
	private EntityManager manager;
	@Override
	public EntityManager getEm() {
		return manager;
	}

}
