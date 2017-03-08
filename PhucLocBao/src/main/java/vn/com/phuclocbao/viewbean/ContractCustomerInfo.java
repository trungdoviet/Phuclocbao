package vn.com.phuclocbao.viewbean;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

public class ContractCustomerInfo {
	public ContractCustomerInfo(Integer contractId, Date startDate, String state){
		this.contractId = contractId;
		this.startDate = startDate;
		this.contractState = state;
	}
	private Integer contractId;
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private Date startDate;
	private String contractState;
	public Integer getContractId() {
		return contractId;
	}
	public void setContractId(Integer contractId) {
		this.contractId = contractId;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public String getContractState() {
		return contractState;
	}
	public void setContractState(String contractState) {
		this.contractState = contractState;
	}
	
	
}
