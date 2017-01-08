package vn.com.phuclocbao.enums;

public enum AlertType {
	SUCCESS("success"),
	WARNING("warning"),
	INFO("info"),
	DANGER("danger")
	;
	private AlertType(String name){
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
