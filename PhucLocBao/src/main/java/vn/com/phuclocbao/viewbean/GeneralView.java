package vn.com.phuclocbao.viewbean;

import vn.com.phuclocbao.bean.StatisticInfo;

public class GeneralView {
	private Long totalNotification;
	private Long totalFinishContract;
	private Long totalInProgressContract;
	private Long totalBadContract;
	private StatisticInfo statistic;
	
	
	public Long getTotalFinishContract() {
		return totalFinishContract;
	}
	public void setTotalFinishContract(Long totalFinishContract) {
		this.totalFinishContract = totalFinishContract;
	}
	public Long getTotalNotification() {
		return totalNotification;
	}
	public void setTotalNotification(Long totalNotification) {
		this.totalNotification = totalNotification;
	}
	public Long getTotalInProgressContract() {
		return totalInProgressContract;
	}
	public void setTotalInProgressContract(Long totalInProgressContract) {
		this.totalInProgressContract = totalInProgressContract;
	}
	public Long getTotalBadContract() {
		return totalBadContract;
	}
	public void setTotalBadContract(Long totalBadContract) {
		this.totalBadContract = totalBadContract;
	}
	public StatisticInfo getStatistic() {
		return statistic;
	}
	public void setStatistic(StatisticInfo statistic) {
		this.statistic = statistic;
	}
}
