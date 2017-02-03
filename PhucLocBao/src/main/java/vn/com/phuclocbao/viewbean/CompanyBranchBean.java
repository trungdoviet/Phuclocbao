package vn.com.phuclocbao.viewbean;

import java.util.List;

import vn.com.phuclocbao.bean.CityDto;
import vn.com.phuclocbao.dto.CompanyDto;
import vn.com.phuclocbao.dto.CompanyTypeDto;

public class CompanyBranchBean {
	private List<CompanyDto> companies;
	private CompanyDto company;
	private List<CompanyTypeDto> availableCompanyTypes;
	private List<CityDto> cities;
	public CompanyBranchBean(){
		company = new CompanyDto();
	}
	
	public List<CompanyDto> getCompanies() {
		return companies;
	}

	public void setCompanies(List<CompanyDto> companies) {
		this.companies = companies;
	}

	public CompanyDto getCompany() {
		return company;
	}

	public void setCompany(CompanyDto company) {
		this.company = company;
	}
	public List<CompanyTypeDto> getAvailableCompanyTypes() {
		return availableCompanyTypes;
	}
	public void setAvailableCompanyTypes(List<CompanyTypeDto> availableCompanyTypes) {
		this.availableCompanyTypes = availableCompanyTypes;
	}

	public List<CityDto> getCities() {
		return cities;
	}

	public void setCities(List<CityDto> cities) {
		this.cities = cities;
	}
	
	
}
