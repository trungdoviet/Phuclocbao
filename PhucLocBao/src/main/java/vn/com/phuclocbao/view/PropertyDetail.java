package vn.com.phuclocbao.view;

import com.fasterxml.jackson.annotation.JsonView;

import vn.com.phuclocbao.bean.Views;

public class PropertyDetail {
	@JsonView(Views.Contract.class)
	private String type;
	@JsonView(Views.Contract.class)
	private String numberPlate;
	@JsonView(Views.Contract.class)
	private String chassisFrameNumber;
	@JsonView(Views.Contract.class)
	private String chassisNumber;
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getNumberPlate() {
		return numberPlate;
	}
	public void setNumberPlate(String numberPlate) {
		this.numberPlate = numberPlate;
	}
	public String getChassisFrameNumber() {
		return chassisFrameNumber;
	}
	public void setChassisFrameNumber(String chassisFrameNumber) {
		this.chassisFrameNumber = chassisFrameNumber;
	}
	public String getChassisNumber() {
		return chassisNumber;
	}
	public void setChassisNumber(String chassisNumber) {
		this.chassisNumber = chassisNumber;
	}
	
}
