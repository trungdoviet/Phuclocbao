package vn.com.phuclocbao.dao.impl;

import vn.com.phuclocbao.dao.UserDao;
public class DefaultUserDao implements UserDao
{

		@Override
		public boolean isValidUser(String username, String password)
		{
				/*String query = "Select count(1) from user where username = ? and password = ?";
				PreparedStatement pstmt = dataSource.getConnection().prepareStatement(query);
				pstmt.setString(1, username);
				pstmt.setString(2, password);
				ResultSet resultSet = pstmt.executeQuery();
				if (resultSet.next())
						return (resultSet.getInt(1) > 0);
				else
						return false;*/
			return true;
		}

}