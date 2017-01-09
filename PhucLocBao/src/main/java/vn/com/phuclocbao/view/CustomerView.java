package vn.com.phuclocbao.view;

import com.fasterxml.jackson.annotation.JsonView;

import vn.com.phuclocbao.bean.Views;

public class CustomerView {
	@JsonView(Views.Contract.class)
	private String idNo;
	@JsonView(Views.Contract.class)
	private String name;
	@JsonView(Views.Contract.class)
	private Integer birthYear;
	@JsonView(Views.Contract.class)
	private String address;
	@JsonView(Views.Contract.class)
	private String province;
	@JsonView(Views.Contract.class)
	private String phone;
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getIdNo() {
		return idNo;
	}
	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getBirthYear() {
		return birthYear;
	}
	public void setBirthYear(Integer birthYear) {
		this.birthYear = birthYear;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	
}
