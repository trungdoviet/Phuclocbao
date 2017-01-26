package vn.com.phuclocbao.viewbean;

import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import vn.com.phuclocbao.dto.CompanyDto;
import vn.com.phuclocbao.dto.UserHistoryDto;
import vn.com.phuclocbao.util.DateTimeUtil;

public class UserActionHistoryView {
	public UserActionHistoryView(){
		startDate = DateTimeUtil.getCurrentDateWithoutTime();
		endDate = DateTimeUtil.addMoreDate(startDate, 7);
	}
	private CompanyDto currentCompany;
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private Date startDate;
	
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private Date endDate;
	
	private List<UserHistoryDto> userHistories;

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

	public List<UserHistoryDto> getUserHistories() {
		return userHistories;
	}

	public void setUserHistories(List<UserHistoryDto> userHistories) {
		this.userHistories = userHistories;
	}
}
