package vn.com.phuclocbao.enums;

public enum UserAccountState {
	ACTIVE("ACTIVE"),
	INACTIVE("INACTIVE"),
	;
	private UserAccountState(String name){
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
