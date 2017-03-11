package vn.com.phuclocbao.bean;
import com.fasterxml.jackson.annotation.JsonView;

public class MonthlyProfitStatisticResponseBody {

	@JsonView(Views.ProfitStatistic.class)
	String msg;

	@JsonView(Views.ProfitStatistic.class)
	String code;
	
	@JsonView(Views.ProfitStatistic.class)
	MonthlyProfitDetail monthlyProfitDetail;

	public MonthlyProfitDetail getMonthlyProfitDetail() {
		return monthlyProfitDetail;
	}

	public void setMonthlyProfitDetail(MonthlyProfitDetail monthlyProfitDetail) {
		this.monthlyProfitDetail = monthlyProfitDetail;
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