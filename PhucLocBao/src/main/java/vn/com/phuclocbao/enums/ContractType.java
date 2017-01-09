package vn.com.phuclocbao.enums;

public enum ContractType {
	RENTING_MOTOBIKE("RMB", "Cho thuê xe"),
	UNKNOWN("","")
	;
	private String desc;
	
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	private ContractType(String name, String desc){
		this.name = name;
		this.desc = desc;
	}
	private String name;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public static ContractType getValueByName(String name){
		for(int i = 0; i < ContractType.values().length; i++){
			if(ContractType.values()[i].getName().equalsIgnoreCase(name)){
				return ContractType.values()[i];
			}
		}
		return null;
	}

}
