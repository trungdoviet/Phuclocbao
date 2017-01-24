package vn.com.phuclocbao.viewbean;

import java.util.Date;

import vn.com.phuclocbao.dto.ContractDto;

public class NotificationContractBean {
	private ContractDto contract;
	private String stage;
	private Date paidDate;
	private String severity;
	private long amountDays;
	public ContractDto getContract() {
		return contract;
	}
	public void setContract(ContractDto contract) {
		this.contract = contract;
	}
	public String getStage() {
		return stage;
	}
	public void setStage(String stage) {
		this.stage = stage;
	}

	public long getAmountDays() {
		return amountDays;
	}
	public void setAmountDays(long amountDays) {
		this.amountDays = amountDays;
	}
	public Date getPaidDate() {
		return paidDate;
	}
	public void setPaidDate(Date paidDate) {
		this.paidDate = paidDate;
	}
	public String getSeverity() {
		return severity;
	}
	public void setSeverity(String severity) {
		this.severity = severity;
	}
}
