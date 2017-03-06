package vn.com.phuclocbao.viewbean;

import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import vn.com.phuclocbao.dto.CompanyDto;
import vn.com.phuclocbao.dto.PaymentHistoryDto;
import vn.com.phuclocbao.enums.PaymentHistoryType;
import vn.com.phuclocbao.util.DateTimeUtil;

public class PaymentHistoryView {
	public PaymentHistoryView(){
		endDate = DateTimeUtil.getCurrentDateWithoutTime();
		endDate = DateTimeUtil.addMoreDate(endDate, 1);
		startDate = DateTimeUtil.addMoreDate(endDate, -15);
		createdPaymentDate = DateTimeUtil.getCurrentDate();
		paymentType = PaymentHistoryType.INVEST_FUNDING.getType();
	}
	
	private String paymentType;
	private Double paymentAmount;
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private Date createdPaymentDate;
	private String additionalInfo;
	
	
	public String getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

	public Double getPaymentAmount() {
		return paymentAmount;
	}

	public void setPaymentAmount(Double paymentAmount) {
		this.paymentAmount = paymentAmount;
	}

	public Date getCreatedPaymentDate() {
		return createdPaymentDate;
	}

	public void setCreatedPaymentDate(Date createdPaymentDate) {
		this.createdPaymentDate = createdPaymentDate;
	}

	public String getAdditionalInfo() {
		return additionalInfo;
	}

	public void setAdditionalInfo(String additionalInfo) {
		this.additionalInfo = additionalInfo;
	}

	private CompanyDto currentCompany;
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private Date startDate;
	
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private Date endDate;
	
	private List<PaymentHistoryDto> paymentHistories;

	public CompanyDto getCurrentCompany() {
		return currentCompany;
	}

	public void setCurrentCompany(CompanyDto currentCompany) {
		this.currentCompany = currentCompany;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public List<PaymentHistoryDto> getPaymentHistories() {
		return paymentHistories;
	}

	public void setPaymentHistories(List<PaymentHistoryDto> paymentHistories) {
		this.paymentHistories = paymentHistories;
	}
}
