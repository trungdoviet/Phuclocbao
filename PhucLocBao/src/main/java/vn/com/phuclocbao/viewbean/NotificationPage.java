package vn.com.phuclocbao.viewbean;

import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import vn.com.phuclocbao.dto.CompanyDto;
import vn.com.phuclocbao.util.DateTimeUtil;

public class NotificationPage {
	public NotificationPage(){
		selectedDate = DateTimeUtil.getCurrentDate();
	}
	private CompanyDto currentCompany;
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private Date selectedDate;
	private List<NotificationContractBean> contracts;
	public Date getSelectedDate() {
		return selectedDate;
	}
	public void setSelectedDate(Date selectedDate) {
		this.selectedDate = selectedDate;
	}
	public List<NotificationContractBean> getContracts() {
		return contracts;
	}
	public void setContracts(List<NotificationContractBean> contracts) {
		this.contracts = contracts;
	}
	public CompanyDto getCurrentCompany() {
		return currentCompany;
	}
	public void setCurrentCompany(CompanyDto currentCompany) {
		this.currentCompany = currentCompany;
	}
	
}
