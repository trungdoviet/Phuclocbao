package vn.com.phuclocbao.enums;

public enum MenuDefinition {
	HOME("home"),
	NEW_CONTRACT("newContract"),
	MANAGE_CONTRACT("mngContracts"),
	OLD_CONTRACT("oldContracts"),
	DAILY_WORK("dailyWorks"),
	NOTIFICATION("notificationContract"),
	HISTORY("history"),
	BAD_CONTRACT("badContract")
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
	public String getRedirectCommand(){
		return "redirect:/"+this.name;
	}

}
