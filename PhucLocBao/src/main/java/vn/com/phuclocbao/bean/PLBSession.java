package vn.com.phuclocbao.bean;

import org.springframework.stereotype.Component;

import vn.com.phuclocbao.dto.UserAccountDto;

@Component
public class PLBSession {
	public static final String SESSION_ATTRIBUTE_KEY = PLBSession.class.getCanonicalName() + "_" + System.identityHashCode(PLBSession.class);
	private UserAccountDto userAccount;
	private MenuBean menuBean;
	private ContractResponseBody contractResponseBody;
	public PLBSession(){
		menuBean = new MenuBean();
	}
	public UserAccountDto getUserAccount() {
		return userAccount;
	}

	public void setUserAccount(UserAccountDto userAccount) {
		this.userAccount = userAccount;
	}

	public MenuBean getMenuBean() {
		return menuBean;
	}

	public void setMenuBean(MenuBean menuBean) {
		this.menuBean = menuBean;
	}
	public ContractResponseBody getContractResponseBody() {
		return contractResponseBody;
	}
	public void setContractResponseBody(ContractResponseBody contractResponseBody) {
		this.contractResponseBody = contractResponseBody;
	}
	
}
