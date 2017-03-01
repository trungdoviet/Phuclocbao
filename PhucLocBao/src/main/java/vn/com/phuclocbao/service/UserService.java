package vn.com.phuclocbao.service;

import java.util.List;

import vn.com.phuclocbao.dto.UserAccountDto;
import vn.com.phuclocbao.entity.UserAccount;
import vn.com.phuclocbao.exception.BusinessException;

public interface UserService {
	public boolean isValidUser(String username, String password) throws BusinessException ;
	public UserAccountDto getUserByUsername(String username) throws BusinessException;
	public UserAccountDto saveUser(UserAccountDto entity) throws BusinessException;
	public List<UserAccountDto> findAll() throws BusinessException;
	public boolean addNewUser(UserAccountDto dto) throws BusinessException;
	public boolean isUserExist(String username) throws BusinessException;
	public Boolean updatePassword(Integer id, String newPassword) throws BusinessException;
}
