package vn.com.phuclocbao.view;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonView;

import vn.com.phuclocbao.bean.Views;

public class ContractView {
	@JsonView(Views.Contract.class)
	private String companyName;
	
	@JsonView(Views.Contract.class)
	private String contractType;
	@JsonView(Views.Contract.class)
	private Double totalAmount;
	@JsonView(Views.Contract.class)
	private String startDate;
	@JsonView(Views.Contract.class)
	private CustomerView customerView;
	@JsonView(Views.Contract.class)
	private List<String> paymentScheduleDetails;
	@JsonView(Views.Contract.class)
	private String expireDate;
	@JsonView(Views.Contract.class)
	private PropertyDetail propertyDetail;
	
	public PropertyDetail getPropertyDetail() {
		return propertyDetail;
	}
	public void setPropertyDetail(PropertyDetail propertyDetail) {
		this.propertyDetail = propertyDetail;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	
	public String getContractType() {
		return contractType;
	}
	public void setContractType(String contractType) {
		this.contractType = contractType;
	}
	public Double getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(Double totalAmount) {
		this.totalAmount = totalAmount;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public CustomerView getCustomerView() {
		return customerView;
	}
	public void setCustomerView(CustomerView customerView) {
		this.customerView = customerView;
	}
	public List<String> getPaymentScheduleDetails() {
		return paymentScheduleDetails;
	}
	public void setPaymentScheduleDetails(List<String> paymentScheduleDetails) {
		this.paymentScheduleDetails = paymentScheduleDetails;
	}
	public String getExpireDate() {
		return expireDate;
	}
	public void setExpireDate(String expireDate) {
		this.expireDate = expireDate;
	}
	
}