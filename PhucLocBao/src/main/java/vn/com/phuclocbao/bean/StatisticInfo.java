package vn.com.phuclocbao.bean;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonView;

import vn.com.phuclocbao.util.DateTimeUtil;

public class StatisticInfo {
	private Integer minYear = 2005;
	private Integer companyId;
	private String companyName;
	
	
	public StatisticInfo(Integer companyId){
		this.companyId = companyId;
		profitByMonth = new ArrayList<Double>();
		rentingCostByMonth = new ArrayList<>();
		rentingNewMotobikeByMonth = new ArrayList<>();
		otherCostByMonth = new ArrayList<>();
		payoffProfitByMonth = new ArrayList<>();
		actualProfitByMonth = new ArrayList<>();
		initMoneyStatisticForYear(profitByMonth);
		initMoneyStatisticForYear(rentingCostByMonth);
		initMoneyStatisticForYear(rentingNewMotobikeByMonth);
		initMoneyStatisticForYear(actualProfitByMonth);
		initMoneyStatisticForYear(payoffProfitByMonth);
		initMoneyStatisticForYear(otherCostByMonth);
		curMonthRentingNew = 0D;
		curMonthActualProfit = 0D;
		curMonthOtherCost = 0D;
		curMonthPayoffProfit = 0D;
	}
	public StatisticInfo(){
		year = LocalDate.now().getYear();
		profitByMonth = new ArrayList<Double>();
		rentingCostByMonth = new ArrayList<>();
		setYears(new ArrayList<>());
		initYears();
		rentingNewMotobikeByMonth = new ArrayList<>();
		otherCostByMonth = new ArrayList<>();
		payoffProfitByMonth = new ArrayList<>();
		actualProfitByMonth = new ArrayList<>();
		initMoneyStatisticForYear(profitByMonth);
		initMoneyStatisticForYear(rentingCostByMonth);
		initMoneyStatisticForYear(rentingNewMotobikeByMonth);
		initMoneyStatisticForYear(actualProfitByMonth);
		initMoneyStatisticForYear(payoffProfitByMonth);
		initMoneyStatisticForYear(otherCostByMonth);
		curMonthRentingNew = 0D;
		curMonthActualProfit = 0D;
		curMonthOtherCost = 0D;
		curMonthPayoffProfit = 0D;
		
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
	
	private List<Double> rentingNewMotobikeByMonth;
	private List<Double> otherCostByMonth;
	private List<Double> actualProfitByMonth;
	private List<Double> payoffProfitByMonth;
	
	@JsonView(Views.ProfitStatistic.class)
	private Double curMonthRentingNew;
	@JsonView(Views.ProfitStatistic.class)
	private Double curMonthOtherCost;
	@JsonView(Views.ProfitStatistic.class)
	private Double curMonthActualProfit;
	@JsonView(Views.ProfitStatistic.class)
	private Double curMonthPayoffProfit;
	
	public void buildProfitAndCostForCurrentMonth(){
		if(year == DateTimeUtil.getCurrentYear()){
		int currentMonth = DateTimeUtil.getCurrentMonth();
			curMonthActualProfit = actualProfitByMonth.get(currentMonth);
			curMonthOtherCost = otherCostByMonth.get(currentMonth);
			curMonthPayoffProfit = payoffProfitByMonth.get(currentMonth);
			curMonthRentingNew = rentingNewMotobikeByMonth.get(currentMonth);
		}
	}
	public Double getCurMonthRentingNew() {
		return curMonthRentingNew;
	}
	public void setCurMonthRentingNew(Double curMonthRentingNew) {
		this.curMonthRentingNew = curMonthRentingNew;
	}
	public Double getCurMonthOtherCost() {
		return curMonthOtherCost;
	}
	public void setCurMonthOtherCost(Double curMonthOtherCost) {
		this.curMonthOtherCost = curMonthOtherCost;
	}
	public Double getCurMonthActualProfit() {
		return curMonthActualProfit;
	}
	public void setCurMonthActualProfit(Double curMonthActualProfit) {
		this.curMonthActualProfit = curMonthActualProfit;
	}
	public Double getCurMonthPayoffProfit() {
		return curMonthPayoffProfit;
	}
	public void setCurMonthPayoffProfit(Double curMonthPayoffProfit) {
		this.curMonthPayoffProfit = curMonthPayoffProfit;
	}
	public List<Double> getRentingNewMotobikeByMonth() {
		return rentingNewMotobikeByMonth;
	}
	public void setRentingNewMotobikeByMonth(List<Double> rentingNewMotobikeByMonth) {
		this.rentingNewMotobikeByMonth = rentingNewMotobikeByMonth;
	}
	public List<Double> getOtherCostByMonth() {
		return otherCostByMonth;
	}
	public void setOtherCostByMonth(List<Double> otherCostByMonth) {
		this.otherCostByMonth = otherCostByMonth;
	}
	
	public List<Double> getActualProfitByMonth() {
		return actualProfitByMonth;
	}
	public void setActualProfitByMonth(List<Double> actualProfitByMonth) {
		this.actualProfitByMonth = actualProfitByMonth;
	}
	public List<Double> getPayoffProfitByMonth() {
		return payoffProfitByMonth;
	}
	public void setPayoffProfitByMonth(List<Double> payoffProfitByMonth) {
		this.payoffProfitByMonth = payoffProfitByMonth;
	}
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
