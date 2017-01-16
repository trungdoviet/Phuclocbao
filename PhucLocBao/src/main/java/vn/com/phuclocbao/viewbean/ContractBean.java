package vn.com.phuclocbao.viewbean;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import vn.com.phuclocbao.bean.CityDto;
import vn.com.phuclocbao.bean.CustomerContract;
import vn.com.phuclocbao.bean.PropertyMetadata;
import vn.com.phuclocbao.dto.CompanyDto;
import vn.com.phuclocbao.dto.ContractDto;
import vn.com.phuclocbao.dto.CustomerDto;
import vn.com.phuclocbao.service.PropertyMetadataCheckingService;

public class ContractBean{
	private ContractDto contractDto;
	private CompanyDto currentCompany;
	private List<CityDto> cities;
	private String paidInfo;
	private CustomerContract searchedCustomerContract;
	private String processStaging;
	
	
	public boolean isReadOnly(String propKey){
		if(StringUtils.isNotEmpty(processStaging)){
			return PropertyMetadataCheckingService.getByStateAndKey(propKey, processStaging).isReadonly();
		}
		return true;
	}
	public PropertyMetadata getMeta(String propKey){
		return PropertyMetadataCheckingService.getByStateAndKey(propKey, processStaging);
	}
	public String getProcessStaging() {
		return processStaging;
	}
	public void setProcessStaging(String processStaging) {
		this.processStaging = processStaging;
	}
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
		paidInfo = StringUtils.EMPTY;
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
	public String getPaidInfo() {
		return paidInfo;
	}
	public void setPaidInfo(String paidInfo) {
		this.paidInfo = paidInfo;
	}
	public CustomerContract getSearchedCustomerContract() {
		return searchedCustomerContract;
	}
	public void setSearchedCustomerContract(CustomerContract searchedCustomerContract) {
		this.searchedCustomerContract = searchedCustomerContract;
	}
	
}
