package vn.com.phuclocbao.enums;

public enum AtomicCounterOption {
	SYNCHRONIZE_BAD_CONTRACT("SYNCHRONIZE_BAD_CONTRACT"),
	;
	private AtomicCounterOption(String name){
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
