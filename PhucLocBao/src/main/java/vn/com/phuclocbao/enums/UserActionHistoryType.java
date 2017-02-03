package vn.com.phuclocbao.enums;

import java.util.Arrays;
import java.util.Optional;

public enum UserActionHistoryType {
	NEW_CONTRACT("NEW_CONTRACT","Thêm hợp đồng mới"),
	UPDATE_CONTRACT("UPDATE_CONTRACT","Cập nhật hợp đồng"),
	PAYOFF_CONTRACT("PAYOFF_CONTRACT","Thanh lý hợp đồng"),
	RENTING_COST("RENTING_COST","Trả phí"),
	CUSTOMER_DEBT("CUSTOMER_DEBT","Khách nợ phí"),
	CUSTOMER_PAY_DEBT("CUSTOMER_PAY_DEBT","Khách trả nợ phí"),
	COMPANY_DEBT("COMPANY_DEBT","Công ty nợ phí"),
	COMPANY_PAY_DEBT("COMPANY_DEBT","Công ty trả nợ phí"),
	UPDATE_COMPANY_FINANCIAL("UPDATE_COMPANY_FINANCIAL","Cập nhật tài chính công ty"),
	UPDATE_COMPANY_BRANCH("UPDATE_COMPANY_BRANCH","Cập nhật chi nhánh công ty"),
	CREATE_COMPANY_BRANCH("CREATE_COMPANY_BRANCH","Tạo công ty"),
	OTHER("OTHER","Chi khác...")
	
	;
	private String type;
	private UserActionHistoryType(String type, String name){
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
	public static UserActionHistoryType getByType(String type){
		Optional<UserActionHistoryType> result = Arrays.stream(UserActionHistoryType.values())
				.filter(item -> item.getType().equalsIgnoreCase(type)).findFirst();
		if(result.isPresent()){
			return result.get();
		}
		return UserActionHistoryType.OTHER;
	}

}
