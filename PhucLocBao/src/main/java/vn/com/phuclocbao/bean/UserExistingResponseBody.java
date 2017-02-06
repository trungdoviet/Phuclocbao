package vn.com.phuclocbao.bean;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonView;

import vn.com.phuclocbao.dto.CustomerDto;

public class UserExistingResponseBody {

	@JsonView(Views.User.class)
	String msg;

	@JsonView(Views.User.class)
	String code;

	
	@JsonView(Views.User.class)
	Boolean isExist;
	
	public Boolean getIsExist() {
		return isExist;
	}

	public void setIsExist(Boolean isExist) {
		this.isExist = isExist;
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