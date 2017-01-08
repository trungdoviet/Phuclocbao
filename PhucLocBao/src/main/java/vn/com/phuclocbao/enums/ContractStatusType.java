package vn.com.phuclocbao.enums;

public enum ContractStatusType {
	FINISH("FINISH"),
	IN_PROGRESS("IN_PROGRESS"),
	BAD("BAD")
	;
	private ContractStatusType(String name){
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
