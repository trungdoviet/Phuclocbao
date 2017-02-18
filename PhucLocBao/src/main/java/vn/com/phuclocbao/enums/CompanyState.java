package vn.com.phuclocbao.enums;

public enum CompanyState {
	ACTIVE("ACTIVE"),
	INACTIVE("INACTIVE"),
	;
	private CompanyState(String name){
		this.name = name;
	}
	private String name;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

}
