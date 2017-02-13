package vn.com.phuclocbao.bean;
import com.fasterxml.jackson.annotation.JsonView;

public class ProfitStatisticResponseBody {

	@JsonView(Views.ProfitStatistic.class)
	String msg;

	@JsonView(Views.ProfitStatistic.class)
	String code;

	
	@JsonView(Views.ProfitStatistic.class)
	StatisticInfo statistic;
	
	

	public StatisticInfo getStatistic() {
		return statistic;
	}

	public void setStatistic(StatisticInfo statistic) {
		this.statistic = statistic;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	
}