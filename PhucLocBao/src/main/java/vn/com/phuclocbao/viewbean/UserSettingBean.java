package vn.com.phuclocbao.viewbean;

import vn.com.phuclocbao.dto.UserAccountDto;

public class UserSettingBean {
	private UserAccountDto user;
	private String oldPassword;
	private String newPassword;
	private String retypePassword;
	public UserAccountDto getUser() {
		return user;
	}
	public void setUser(UserAccountDto user) {
		this.user = user;
	}
	public String getOldPassword() {
		return oldPassword;
	}
	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}
	public String getNewPassword() {
		return newPassword;
	}
	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}
	public String getRetypePassword() {
		return retypePassword;
	}
	public void setRetypePassword(String retypePassword) {
		this.retypePassword = retypePassword;
	}
	
	
}
