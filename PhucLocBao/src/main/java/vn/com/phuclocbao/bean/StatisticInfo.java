package vn.com.phuclocbao.bean;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class StatisticInfo {
	private Integer minYear = 2005;
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
	private List<Double> profitByMonth;
	
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
	
}
