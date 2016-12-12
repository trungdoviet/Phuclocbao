package vn.com.phuclocbao.delegator;

import java.sql.SQLException;

import vn.com.phuclocbao.service.UserService;


public class LoginDelegator
{
		private UserService userService;

		public UserService getUserService()
		{
				return this.userService;
		}

		public void setUserService(UserService userService)
		{
				this.userService = userService;
		}

		public boolean isValidUser(String username, String password) 
    {
		    return userService.isValidUser(username, password);
    }
}
