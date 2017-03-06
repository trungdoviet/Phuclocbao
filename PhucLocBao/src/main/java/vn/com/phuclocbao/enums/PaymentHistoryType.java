package vn.com.phuclocbao.enums;

import java.util.Arrays;
import java.util.Optional;

public enum PaymentHistoryType {
	INVEST_FUNDING("INVEST_FUNDING","Nhập vốn"),
	UPDATE_FUNDING("UPDATE_FUNDING","Sửa vốn"),
	TAKE_OUT_FUNDING("TAKE_OUT_FUNDING","Xuất vốn"),
	RENTING_NEW_MOTOBIKE("RENTING_NEW_MOTOBIKE","Cho thuê xe"),
	RENTING_COST("RENTING_COST","Phí thuê xe"),
	PAYOFF("PAYOFF","Thanh lý thuê xe"),
	CUSTOMER_DEBT("CUSTOMER_DEBT","Khách nợ phí"),
	CUSTOMER_PAY_DEBT("CUSTOMER_PAY_DEBT","Khách trả nợ phí"),
	COMPANY_DEBT("COMPANY_DEBT","Công ty nợ phí"),
	COMPANY_PAY_DEBT("COMPANY_DEBT","Công ty trả nợ phí"),
	REFUNDING_FOR_CUSTOMER("REFUNDING_FOR_CUSTOMER","Trả thừa phí khách"),
	REFUNDING_FOR_COMPANY("REFUNDING_FOR_COMPANY","Khách trả phí thanh lý"),
	
	OTHER_PAY("OTHER_PAY","Chi khác..."),
	OTHER_PROFIT("OTHER_INCOME","Thu khác..."),
	OTHER("OTHER","Loại khác")
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
	public static PaymentHistoryType getByType(String type){
		Optional<PaymentHistoryType> result = Arrays.stream(PaymentHistoryType.values())
				.filter(item -> item.getType().equalsIgnoreCase(type)).findFirst();
		if(result.isPresent()){
			return result.get();
		}
		return PaymentHistoryType.OTHER;
	}

}
