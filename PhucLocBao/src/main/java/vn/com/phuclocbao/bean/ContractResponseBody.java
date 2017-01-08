package vn.com.phuclocbao.bean;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonView;

import vn.com.phuclocbao.dto.CustomerDto;

public class ContractResponseBody {

	@JsonView(Views.Public.class)
	String msg;

	@JsonView(Views.Public.class)
	String code;

	
	@JsonView(Views.Public.class)
	List<CustomerDto> customers;
	@JsonView(Views.Public.class)
	List<CustomerContract> customerContracts;

	public List<CustomerContract> getCustomerContracts() {
		return customerContracts;
	}

	public void setCustomerContracts(List<CustomerContract> customerContracts) {
		this.customerContracts = customerContracts;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public List<CustomerDto> getCustomers() {
		return customers;
	}

	public void setCustomers(List<CustomerDto> customers) {
		this.customers = customers;
	}
}