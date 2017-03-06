package vn.com.phuclocbao.bean;

import java.util.List;

import org.springframework.stereotype.Component;

import vn.com.phuclocbao.dto.CompanyDto;
import vn.com.phuclocbao.dto.UserAccountDto;

@Component
public class PLBSession {
	public static final String SESSION_ATTRIBUTE_KEY = PLBSession.class.getCanonicalName() + "_" + System.identityHashCode(PLBSession.class);
	private UserAccountDto userAccount;
	private MenuBean menuBean;
	private ContractResponseBody contractResponseBody;
	private CompanyDto currentCompany;
	private List<CompanyDto> availableCompanies;
	private CompanyDto workingCompany;
	
	public List<CompanyDto> getAvailableCompanies() {
		return availableCompanies;
	}
	public void setAvailableCompanies(List<CompanyDto> availableCompanies) {
		this.availableCompanies = availableCompanies;
	}
	public Integer getCompanyId() {
		return currentCompany.getId();
	}
	public Integer getWorkingCompanyId() {
		if(workingCompany == null){
			return getCompanyId();
		}
		return workingCompany.getId();
	} 
	
	public static String getSessionAttributeKey() {
		return SESSION_ATTRIBUTE_KEY;
	}
	public PLBSession(){
		menuBean = new MenuBean();
	}
	public UserAccountDto getUserAccount() {
		return userAccount;
	}

	public CompanyDto getWorkingCompany() {
		if(workingCompany == null){
			return getCurrentCompany();
		}
		return workingCompany;
	}
	public void setWorkingCompany(CompanyDto workingCompany) {
		this.workingCompany = workingCompany;
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
	public CompanyDto getCurrentCompany() {
		return currentCompany;
	}
	public void setCurrentCompany(CompanyDto currentCompany) {
		this.currentCompany = currentCompany;
	}
	
}
