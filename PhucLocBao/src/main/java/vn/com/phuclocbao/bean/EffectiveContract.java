package vn.com.phuclocbao.bean;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonView;

public class EffectiveContract {
	@JsonView(Views.Public.class)
	private Integer id;
	@JsonView(Views.Public.class)
	private String state;
	@JsonView(Views.Public.class)
	private String startDate;
	public EffectiveContract(Integer id){
		this.id = id;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
}
