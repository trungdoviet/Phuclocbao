package vn.com.phuclocbao.service;

import vn.com.phuclocbao.dto.UserAccountDto;
import vn.com.phuclocbao.exception.BusinessException;

public interface UserService {
	public boolean isValidUser(String username, String password) throws BusinessException ;
	public UserAccountDto getUserByUsername(String username) throws BusinessException;
	public UserAccountDto saveUser(UserAccountDto entity) throws BusinessException;
}
