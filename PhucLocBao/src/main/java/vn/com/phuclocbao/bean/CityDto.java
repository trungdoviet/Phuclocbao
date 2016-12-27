package vn.com.phuclocbao.bean;

public class CityDto {
	private String code;
	private String name;
	public CityDto(String code, String name){
		this.code = code;
		this.name =name;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
}
