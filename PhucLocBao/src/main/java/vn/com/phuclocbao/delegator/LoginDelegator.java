package vn.com.phuclocbao.delegator;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import vn.com.phuclocbao.exception.BusinessException;
import vn.com.phuclocbao.service.UserService;

@Component
public class LoginDelegator
{
		@Autowired
		private UserService userService;

		public UserService getUserService(){
				return this.userService;
		}

		public void setUserService(UserService userService){
				this.userService = userService;
		}

		public boolean isValidUser(String username, String password) throws BusinessException   {
		    return userService.isValidUser(username, password);
    }
}
