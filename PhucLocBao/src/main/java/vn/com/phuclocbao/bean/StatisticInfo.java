package vn.com.phuclocbao.bean;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonView;

public class StatisticInfo {
	private Integer minYear = 2005;
	private Integer companyId;
	private String companyName;
	public StatisticInfo(Integer companyId){
		this.companyId = companyId;
		profitByMonth = new ArrayList<Double>();
		rentingCostByMonth = new ArrayList<>();
		initMoneyStatisticForYear(profitByMonth);
		initMoneyStatisticForYear(rentingCostByMonth);
	}
	public StatisticInfo(){
		year = LocalDate.now().getYear();
		profitByMonth = new ArrayList<Double>();
		rentingCostByMonth = new ArrayList<>();
		setYears(new ArrayList<>());
		initYears();
		initMoneyStatisticForYear(profitByMonth);
		initMoneyStatisticForYear(rentingCostByMonth);
		
	}
	private void initMoneyStatisticForYear(List<Double> moneyStatistic){
		for(int i = 0; i<12;i++){
			moneyStatistic.add(0D);
		}
	}
	
	private void initYears(){
		for(int i = year; i >= minYear; i--){
			years.add(i);
		}
	}
	
	private List<Integer> years;
	private Integer year;
	@JsonView(Views.ProfitStatistic.class)
	private List<Double> profitByMonth;
	@JsonView(Views.ProfitStatistic.class)
	private List<Double> rentingCostByMonth;
	
	public Integer getYear() {
		return year;
	}
	public void setYear(Integer year) {
		this.year = year;
	}
	public List<Double> getProfitByMonth() {
		return profitByMonth;
	}
	public void setProfitByMonth(List<Double> profitByMonth) {
		this.profitByMonth = profitByMonth;
	}
	public List<Integer> getYears() {
		return years;
	}
	public void setYears(List<Integer> years) {
		this.years = years;
	}

	public List<Double> getRentingCostByMonth() {
		return rentingCostByMonth;
	}

	public void setRentingCostByMonth(List<Double> rentingCostByMonth) {
		this.rentingCostByMonth = rentingCostByMonth;
	}
	public Integer getCompanyId() {
		return companyId;
	}
	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
}
