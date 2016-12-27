package vn.com.phuclocbao.viewbean;

import java.util.List;

import vn.com.phuclocbao.bean.CityDto;
import vn.com.phuclocbao.dto.CompanyDto;
import vn.com.phuclocbao.dto.ContractDto;
import vn.com.phuclocbao.dto.CustomerDto;

public class ContractBean{
	private ContractDto contractDto;
	private CompanyDto currentCompany;
	private List<CityDto> cities;
	
	public CompanyDto getCurrentCompany() {
		return currentCompany;
	}
	public void setCurrentCompany(CompanyDto currentCompany) {
		this.currentCompany = currentCompany;
	}
	public ContractBean(){
		contractDto = new ContractDto();
		contractDto.setCustomer(new CustomerDto());
		contractDto.setCompany(new CompanyDto());
	}
	public ContractDto getContractDto() {
		return contractDto;
	}

	public void setContractDto(ContractDto contractDto) {
		this.contractDto = contractDto;
	}
	public List<CityDto> getCities() {
		return cities;
	}
	public void setCities(List<CityDto> cities) {
		this.cities = cities;
	}
	
	
}
