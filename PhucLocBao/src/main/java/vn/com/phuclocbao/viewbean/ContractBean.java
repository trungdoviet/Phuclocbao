package vn.com.phuclocbao.viewbean;

import vn.com.phuclocbao.dto.ContractDto;
import vn.com.phuclocbao.dto.CustomerDto;

public class ContractBean{
	private ContractDto contractDto;
	public ContractBean(){
		contractDto = new ContractDto();
		contractDto.setCustomer(new CustomerDto());
	}
	public ContractDto getContractDto() {
		return contractDto;
	}

	public void setContractDto(ContractDto contractDto) {
		this.contractDto = contractDto;
	}
	
}
