package vn.com.phuclocbao.enums;

public enum MenuDefinition {
	HOME("home"),
	NEW_CONTRACT("newContract"),
	CONTRACT("contracts"),
	;
	private MenuDefinition(String name){
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
