package vn.com.phuclocbao.viewbean;

import java.util.List;

import vn.com.phuclocbao.dto.ContractDto;

public class ManageContractBean {
	private int totalContract;
	private int inProgressContract;
	private Double totalFeeADay;
	private Double totalPayoffAmmount;
	private List<ContractDto> contracts;
	public ManageContractBean(){
		this.inProgressContract = 0;
		this.totalContract = 0;
	}
	public List<ContractDto> getContracts() {
		return contracts;
	}
	public void setContracts(List<ContractDto> contracts) {
		this.contracts = contracts;
	}
	public int getTotalContract() {
		return totalContract;
	}
	public void setTotalContract(int totalContract) {
		this.totalContract = totalContract;
	}
	public int getInProgressContract() {
		return inProgressContract;
	}
	public void setInProgressContract(int inProgressContract) {
		this.inProgressContract = inProgressContract;
	}
	public Double getTotalFeeADay() {
		return totalFeeADay;
	}
	public void setTotalFeeADay(Double totalFeeADay) {
		this.totalFeeADay = totalFeeADay;
	}
	public Double getTotalPayoffAmmount() {
		return totalPayoffAmmount;
	}
	public void setTotalPayoffAmmount(Double totalPayoffAmmount) {
		this.totalPayoffAmmount = totalPayoffAmmount;
	}
	
}
