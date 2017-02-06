package vn.com.phuclocbao.bean;

import java.util.List;

import vn.com.phuclocbao.dto.UserAccountDto;

public class UserCompanyGrouping {
	private String companyName;
	private List<UserAccountDto> users;
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public List<UserAccountDto> getUsers() {
		return users;
	}
	public void setUsers(List<UserAccountDto> users) {
		this.users = users;
	}
	
	
}
