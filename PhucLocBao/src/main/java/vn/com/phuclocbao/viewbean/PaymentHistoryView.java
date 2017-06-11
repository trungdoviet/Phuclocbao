package vn.com.phuclocbao.viewbean;

import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.format.annotation.DateTimeFormat;

import vn.com.phuclocbao.dto.CompanyDto;
import vn.com.phuclocbao.dto.PaymentHistoryDto;
import vn.com.phuclocbao.enums.PaymentHistoryType;
import vn.com.phuclocbao.util.DateTimeUtil;

public class PaymentHistoryView {
	public PaymentHistoryView(){
		
		endDate = DateTimeUtil.getCurrentDateMaxTime();
		//endDate = DateTimeUtil.addMoreDate(endDate, 1);
		startDate = DateTimeUtil.getFirstDateOfMonth(DateTimeUtil.getCurrentYear(), DateTimeUtil.getCurrentMonth());
		createdPaymentDate = DateTimeUtil.getCurrentDate();
		paymentType = PaymentHistoryType.INVEST_FUNDING.getType();
		
	}
	private Double totalPayment;
	private String paymentType;
	private Double paymentAmount;
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private Date createdPaymentDate;
	private String additionalInfo;
	private List<PaymentHistoryType> paymentTypes;
	
	public void calculateTotalPayment(){
		if(StringUtils.isNoneBlank(this.paymentType) && CollectionUtils.isNotEmpty(paymentHistories)){
			PaymentHistoryType type = PaymentHistoryType.getByType(paymentType);
			switch(type){
				case OTHER_PAY:
				case OTHER_PROFIT:
				case REFUNDING_FOR_COMPANY:
				case REFUNDING_FOR_CUSTOMER:
				case TAKE_OUT_FUNDING:
				case OTHER:
				case UPDATE_FUNDING:
				case INVEST_FUNDING:
				case COMPANY_PAY_DEBT:
				case CUSTOMER_DEBT:
				case CUSTOMER_PAY_DEBT:
				case COMPANY_DEBT:
				case RENTING_COST:
					this.totalPayment = this.paymentHistories.stream().map(PaymentHistoryDto::getFee).reduce(0D, (x,y) -> x+y);
					break;
				case RENTING_NEW_MOTOBIKE:
					this.totalPayment = this.paymentHistories.stream().map(PaymentHistoryDto::getRentingAmount).reduce(0D, (x,y) -> x+y);
					break;
				case PAYOFF:
					this.totalPayment = this.paymentHistories.stream().map(PaymentHistoryDto::getPayoff).reduce(0D, (x,y) -> x+y);
					break;
				default:
					break;
			
			}
		}
	}
	public String getPaymentType() {
		return paymentType;
	}

	public List<PaymentHistoryType> getPaymentTypes() {
		return paymentTypes;
	}

	public void setPaymentTypes(List<PaymentHistoryType> paymentTypes) {
		this.paymentTypes = paymentTypes;
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

	public Double getTotalPayment() {
		return totalPayment;
	}

	public void setTotalPayment(Double totalPayment) {
		this.totalPayment = totalPayment;
	}
}
