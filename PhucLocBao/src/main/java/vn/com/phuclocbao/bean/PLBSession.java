package vn.com.phuclocbao.bean;

import org.springframework.stereotype.Component;

import vn.com.phuclocbao.dto.UserAccountDto;

@Component
public class PLBSession {
	public static final String SESSION_ATTRIBUTE_KEY = PLBSession.class.getCanonicalName() + "_" + System.identityHashCode(PLBSession.class);
	private UserAccountDto userAccount;

	public UserAccountDto getUserAccount() {
		return userAccount;
	}

	public void setUserAccount(UserAccountDto userAccount) {
		this.userAccount = userAccount;
	}
	
}
