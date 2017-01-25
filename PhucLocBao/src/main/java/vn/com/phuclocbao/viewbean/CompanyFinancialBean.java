package vn.com.phuclocbao.viewbean;

import java.util.List;

import vn.com.phuclocbao.bean.CityDto;
import vn.com.phuclocbao.dto.CompanyDto;

public class CompanyFinancialBean {
	private CompanyDto company;
	private List<CityDto> cities;
	public CompanyDto getCompany() {
		return company;
	}
	public void setCompany(CompanyDto company) {
		this.company = company;
	}
	public List<CityDto> getCities() {
		return cities;
	}
	public void setCities(List<CityDto> cities) {
		this.cities = cities;
	}
	
}
