package vn.com.phuclocbao.enums;

public enum ProcessStaging {
	NEW("new"),
	PAYOFF("payoff"),
	PAID("paid"),
	UPDATE("update"),
	;
	private ProcessStaging(String name){
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
