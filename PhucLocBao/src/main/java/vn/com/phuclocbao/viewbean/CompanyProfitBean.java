package vn.com.phuclocbao.viewbean;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import vn.com.phuclocbao.bean.StatisticInfo;

public class CompanyProfitBean {
	private Integer minYear = 2005;
	private List<Integer> years;
	private Integer year;
	public CompanyProfitBean(){
		year = LocalDate.now().getYear();
		statistics = new ArrayList<>();
		setYears(new ArrayList<>());
		initYears();
	}
	private List<StatisticInfo> statistics;
	public void setYears(List<Integer> years) {
		this.years = years;
	}
	public List<StatisticInfo> getStatistics() {
		return statistics;
	}
	
	public List<Integer> getYears() {
		return years;
	}
	private void initYears(){
		for(int i = year; i >= minYear; i--){
			years.add(i);
		}
	}
	public void setStatistics(List<StatisticInfo> statistics) {
		this.statistics = statistics;
	}
	public Integer getYear() {
		return year;
	}
	public void setYear(Integer year) {
		this.year = year;
	}
	
	
}
