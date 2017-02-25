package vn.com.phuclocbao.viewbean;

import java.util.List;

import vn.com.phuclocbao.dto.CompanyDto;
import vn.com.phuclocbao.dto.UserAccountDto;
import vn.com.phuclocbao.util.ConstantVariable;

public class ManageUserBean {
	private List<UserAccountDto> users;
	private UserAccountDto user;
	private List<CompanyDto> companies;
	public ManageUserBean(){
		this.user = new UserAccountDto();
		this.user.setIsAdmin(ConstantVariable.YES_OPTION);
	}
	public List<UserAccountDto> getUsers() {
		return users;
	}
	public void setUsers(List<UserAccountDto> users) {
		this.users = users;
	}
	public UserAccountDto getUser() {
		return user;
	}
	public void setUser(UserAccountDto user) {
		this.user = user;
	}
	public List<CompanyDto> getCompanies() {
		return companies;
	}
	public void setCompanies(List<CompanyDto> companies) {
		this.companies = companies;
	}
	
}
