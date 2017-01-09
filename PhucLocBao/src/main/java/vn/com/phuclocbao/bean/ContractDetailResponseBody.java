package vn.com.phuclocbao.bean;
import com.fasterxml.jackson.annotation.JsonView;

import vn.com.phuclocbao.view.ContractView;

public class ContractDetailResponseBody {

	@JsonView(Views.Contract.class)
	String msg;

	@JsonView(Views.Contract.class)
	String code;
	@JsonView(Views.Contract.class)
	ContractView contract;

	public ContractView getContract() {
		return contract;
	}

	public void setContract(ContractView contract) {
		this.contract = contract;
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