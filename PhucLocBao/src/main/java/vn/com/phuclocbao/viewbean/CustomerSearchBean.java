package vn.com.phuclocbao.viewbean;

import java.util.List;

import vn.com.phuclocbao.dto.CustomerDto;

public class CustomerSearchBean {
	public CustomerSearchBean(){
		isSearchAllCompany = false;
	}
	private String name;
	private List<CustomerDto> customers;
	private boolean isSearchAllCompany;
	
	public boolean getIsSearchAllCompany() {
		return isSearchAllCompany;
	}
	
	public boolean isSearchAllCompany() {
		return isSearchAllCompany;
	}
	
	public void setIsSearchAllCompany(boolean isSearchAllCompany) {
		this.isSearchAllCompany = isSearchAllCompany;
	}

	public void setSearchAllCompany(boolean isSearchAllCompany) {
		this.isSearchAllCompany = isSearchAllCompany;
	}
	private List<CustomerDto> customersInOtherCompany;
	
	public List<CustomerDto> getCustomers() {
		return customers;
	}

	public void setCustomers(List<CustomerDto> customers) {
		this.customers = customers;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<CustomerDto> getCustomersInOtherCompany() {
		return customersInOtherCompany;
	}

	public void setCustomersInOtherCompany(List<CustomerDto> customersInOtherCompany) {
		this.customersInOtherCompany = customersInOtherCompany;
	}
	
	
}
