package vn.com.phuclocbao.enums;

import java.util.Arrays;
import java.util.Optional;

public enum ContractSeverity {
	GOOD("GOOD","state_good", 0, 5),
	WARNING("WARNING","state_warning", 6, 10),
	ALERT("ALERT","state_alert", 11, Long.MAX_VALUE),
	UNKNOWN("UNKNOWN","state_unknown", -1, -1),
	;
	private String type;
	private long minBarrierDays;
	private long maxBarrierDays;
	private ContractSeverity(String type, String name, long minBarrierDays, long maxBarrierDays){
		this.name = name;
		this.type = type;
		this.minBarrierDays = minBarrierDays;
		this.maxBarrierDays = maxBarrierDays;
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
	public long getMinBarrierDays() {
		return minBarrierDays;
	}
	public void setMinBarrierDays(long minBarrierDays) {
		this.minBarrierDays = minBarrierDays;
	}
	public long getMaxBarrierDays() {
		return maxBarrierDays;
	}
	public void setMaxBarrierDays(long maxBarrierDays) {
		this.maxBarrierDays = maxBarrierDays;
	}
	
	public static ContractSeverity getSeverityByAmountDays(long amountDays){
		
		Optional<ContractSeverity> result = Arrays.stream(ContractSeverity.values()).filter(item ->{
												return amountDays >= item.getMinBarrierDays() && amountDays <= item.getMaxBarrierDays();
											}).findFirst();
		if(result.isPresent()){
			return result.get();
		}
		return UNKNOWN;
	}
	
	

}
