package vn.com.phuclocbao.bean;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonView;

public class CustomerContract {
	@JsonView(Views.Public.class)
	private String idNo;
	@JsonView(Views.Public.class)
	private List<EffectiveContract> contracts;

	public String getIdNo() {
		return idNo;
	}

	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}

	public List<EffectiveContract> getContracts() {
		return contracts;
	}

	public void setContracts(List<EffectiveContract> contracts) {
		this.contracts = contracts;
	}
}
