package vn.com.phuclocbao.service;

import vn.com.phuclocbao.dto.UserAccountDto;
import vn.com.phuclocbao.exception.BusinessException;

public interface UserService {
	public boolean isValidUser(String username, String password) ;
	public UserAccountDto getUserByUsername(String username) throws BusinessException;
}
