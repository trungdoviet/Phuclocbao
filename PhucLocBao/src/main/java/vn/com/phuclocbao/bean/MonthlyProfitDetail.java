package vn.com.phuclocbao.bean;

import com.fasterxml.jackson.annotation.JsonView;

public class MonthlyProfitDetail {
	@JsonView(Views.ProfitStatistic.class)
	private Double totalContractAmount;
	@JsonView(Views.ProfitStatistic.class)
	private Double totalBadContractAmount;
	@JsonView(Views.ProfitStatistic.class)
	private Double totalInProgressContractAmount;
	@JsonView(Views.ProfitStatistic.class)
	private Double totalFinishContractAmount;
	@JsonView(Views.ProfitStatistic.class)
	private Double totalRevenue;
	@JsonView(Views.ProfitStatistic.class)
	private Double totalInvest;
	@JsonView(Views.ProfitStatistic.class)
	private Double totalOtherCost;
	@JsonView(Views.ProfitStatistic.class)
	private Double totalOtherIncome;
	@JsonView(Views.ProfitStatistic.class)
	private Double totalProfit;
	@JsonView(Views.ProfitStatistic.class)
	private Double totalRentingNew;
	@JsonView(Views.ProfitStatistic.class)
	private Double totalPayOff;
	@JsonView(Views.ProfitStatistic.class)
	private Double totalRunningContractInDateRange;
	@JsonView(Views.ProfitStatistic.class)
	private Double totalTakeOffRefund;
	@JsonView(Views.ProfitStatistic.class)
	private Double totalRefundingOfBadContract;
	
	public MonthlyProfitDetail(){
		totalContractAmount = 0D;
		totalBadContractAmount = 0D;
		totalFinishContractAmount = 0D;
		totalInProgressContractAmount = 0D;
		totalInvest = 0D;
		totalRentingNew = 0D;
		totalPayOff = 0D;
		totalOtherCost = 0D;
		totalRevenue = 0D;
		totalOtherIncome = 0D;
		totalProfit = 0D;
		totalRunningContractInDateRange = 0D;
		totalTakeOffRefund = 0D;
		totalRefundingOfBadContract = 0D;
	}
	

	public Double getTotalTakeOffRefund() {
		return totalTakeOffRefund;
	}


	public Double getTotalRefundingOfBadContract() {
		return totalRefundingOfBadContract;
	}


	public void setTotalRefundingOfBadContract(Double totalRefundingOfBadContract) {
		this.totalRefundingOfBadContract = totalRefundingOfBadContract;
	}


	public void setTotalTakeOffRefund(Double totalTakeOffRefund) {
		this.totalTakeOffRefund = totalTakeOffRefund;
	}




	public Double getTotalRunningContractInDateRange() {
		return totalRunningContractInDateRange;
	}




	public void setTotalRunningContractInDateRange(Double totalRunningContractInDateRange) {
		this.totalRunningContractInDateRange = totalRunningContractInDateRange;
	}




	public Double getTotalRentingNew() {
		return totalRentingNew;
	}


	public void setTotalRentingNew(Double totalRentingNew) {
		this.totalRentingNew = totalRentingNew;
	}


	public Double getTotalPayOff() {
		return totalPayOff;
	}


	public void setTotalPayOff(Double totalPayOff) {
		this.totalPayOff = totalPayOff;
	}


	public void calculateTotalProfit(){
		totalProfit = totalRevenue + totalOtherIncome - totalOtherCost;
	}
	public Double getTotalContractAmount() {
		return totalContractAmount;
	}
	
	public Double getTotalOtherCost() {
		return totalOtherCost;
	}

	public void setTotalOtherCost(Double totalOtherCost) {
		this.totalOtherCost = totalOtherCost;
	}

	public Double getTotalOtherIncome() {
		return totalOtherIncome;
	}

	public void setTotalOtherIncome(Double totalOtherIncome) {
		this.totalOtherIncome = totalOtherIncome;
	}

	public void setTotalContractAmount(Double totalContractAmount) {
		this.totalContractAmount = totalContractAmount;
	}
	public Double getTotalBadContractAmount() {
		return totalBadContractAmount;
	}
	public void setTotalBadContractAmount(Double totalBadContractAmount) {
		this.totalBadContractAmount = totalBadContractAmount;
	}
	public Double getTotalInProgressContractAmount() {
		return totalInProgressContractAmount;
	}
	public void setTotalInProgressContractAmount(Double totalInProgressContractAmount) {
		this.totalInProgressContractAmount = totalInProgressContractAmount;
	}
	public Double getTotalFinishContractAmount() {
		return totalFinishContractAmount;
	}
	public void setTotalFinishContractAmount(Double totalFinishContractAmount) {
		this.totalFinishContractAmount = totalFinishContractAmount;
	}
	public Double getTotalRevenue() {
		return totalRevenue;
	}
	public void setTotalRevenue(Double totalRevenue) {
		this.totalRevenue = totalRevenue;
	}
	public Double getTotalInvest() {
		return totalInvest;
	}
	public void setTotalInvest(Double totalInvest) {
		this.totalInvest = totalInvest;
	}
	public Double getTotalProfit() {
		return totalProfit;
	}
	public void setTotalProfit(Double totalProfit) {
		this.totalProfit = totalProfit;
	}
}
