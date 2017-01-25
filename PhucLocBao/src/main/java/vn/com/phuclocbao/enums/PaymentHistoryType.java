package vn.com.phuclocbao.enums;

public enum PaymentHistoryType {
	INVEST_FUNDING("INVEST_FUNDING","Nhập vốn"),
	RENTING_NEW_MOTOBIKE("RENTING_NEW_MOTOBIKE","Cho thuê xe"),
	RENTING_COST("RENTING_COST","Phí thuê xe"),
	PAYOFF("PAYOFF","Thanh lý thuê xe"),
	CUSTOMER_DEBT("CUSTOMER_DEBT","Khách nợ phí"),
	CUSTOMER_PAY_DEBT("CUSTOMER_PAY_DEBT","Khách trả nợ phí"),
	COMPANY_DEBT("COMPANY_DEBT","Công ty trả nợ phí/Nợ phí"),
	COMPANY_PAY_DEBT("COMPANY_DEBT","Công ty trả nợ phí/Nợ phí"),
	OTHER("OTHER","Chi khác...")
	;
	private String type;
	private PaymentHistoryType(String type, String name){
		this.name = name;
		this.type = type;
	}
	private String name;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}

}
