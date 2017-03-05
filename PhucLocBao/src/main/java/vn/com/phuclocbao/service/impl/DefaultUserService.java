package vn.com.phuclocbao.service.impl;

import java.io.IOException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vn.com.phuclocbao.converter.UserAccountConverter;
import vn.com.phuclocbao.dao.CompanyDao;
import vn.com.phuclocbao.dao.PersistenceExecutable;
import vn.com.phuclocbao.dao.UserDao;
import vn.com.phuclocbao.dto.UserAccountDto;
import vn.com.phuclocbao.entity.CompanyEntity;
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
	@Autowired
	private CompanyDao companyDao;
	private static org.apache.log4j.Logger logger = Logger.getLogger(DefaultUserService.class);
	public CompanyDao getCompanyDao() {
		return companyDao;
	}

	public void setCompanyDao(CompanyDao companyDao) {
		this.companyDao = companyDao;
	}

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
	@Transactional(rollbackFor=BusinessException.class)
	@Override
	public UserAccountDto saveUser(UserAccountDto dto) throws BusinessException {
		
		return transactionWrapper(new PersistenceExecutable<UserAccountDto>() {
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

	@Override
	public List<UserAccountDto> findAll() throws BusinessException {
		return methodWrapper(new PersistenceExecutable<List<UserAccountDto>>() {
			@Override
			public List<UserAccountDto> execute() throws BusinessException, ClassNotFoundException, IOException {
				List<UserAccount> users= userDao.findAll();
				return UserAccountConverter.getInstance().toDtosExtra(users);
			}
		});
	}

	@Transactional(rollbackFor=BusinessException.class)
	@Override
	public boolean addNewUser(UserAccountDto dto) throws BusinessException {
		return transactionWrapper(new PersistenceExecutable<Boolean>() {
			@Override
			public Boolean execute() throws BusinessException, ClassNotFoundException, IOException {
				if(userDao.isUserExist(dto.getUsername())){
					throw new BusinessException(PLBErrorCode.OBJECT_ALREADY_EXIST.name());
				}
				UserAccount entity = UserAccountConverter.getInstance().toNewEntity(dto);
				CompanyEntity company = companyDao.findById(dto.getCompanyEntity().getId());
				if(entity == null || company == null){
					logger.error("Can not convert user or not find company with id " + dto.getCompanyEntity().getId());
					throw new BusinessException(PLBErrorCode.OBJECT_NOT_FOUND.name());
				}
				company.getUserAccounts().add(entity);
				entity.setCompanyEntity(company);
				return userDao.persist(entity) != null;
			}
		});
	}

	@Override
	public boolean isUserExist(String username) throws BusinessException {
		return methodWrapper(new PersistenceExecutable<Boolean>() {
			@Override
			public Boolean execute() throws BusinessException, ClassNotFoundException, IOException {
				return userDao.isUserExist(username);
			}
		});
	}
	@Transactional(rollbackFor=BusinessException.class)
	@Override
	public Boolean updatePassword(Integer id, String newPassword) throws BusinessException {
		return transactionWrapper(new PersistenceExecutable<Boolean>() {
			@Override
			public Boolean execute() throws BusinessException, ClassNotFoundException, IOException {
				UserAccount entity = userDao.findById(id);
				if(entity == null){
					throw new BusinessException(PLBErrorCode.USER_CAN_NOT_BE_FOUND.name());
				}
				String hashingPassword = PasswordHashing.hashMD5(newPassword);
				entity.setPassword(hashingPassword);
				userDao.merge(entity);
				return true;
			}
		});
	}

}
