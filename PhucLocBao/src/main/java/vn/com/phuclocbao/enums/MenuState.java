package vn.com.phuclocbao.enums;

public enum MenuState {
	ACTIVE("ACTIVE", "active"),
	INACTIVE("INACTIVE", "")
	;
	private MenuState(String name, String className){
		this.name = name;
		this.className = className;
	}
	private String name;
	private String className;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	
}
