package vn.com.phuclocbao.viewbean;

import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import vn.com.phuclocbao.dto.CompanyDto;
import vn.com.phuclocbao.dto.PaymentHistoryDto;
import vn.com.phuclocbao.util.DateTimeUtil;

public class PaymentHistoryView {
	public PaymentHistoryView(){
		startDate = DateTimeUtil.getCurrentDateWithoutTime();
		endDate = DateTimeUtil.addMoreDate(startDate, 30);
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