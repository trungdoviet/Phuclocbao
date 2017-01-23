package vn.com.phuclocbao.util;

import java.text.MessageFormat;
import java.util.HashSet;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import vn.com.phuclocbao.entity.Contract;
import vn.com.phuclocbao.entity.ContractHistory;
import vn.com.phuclocbao.enums.ContractHistoryType;

public class ContractHistoryUtil {
	private static final String MSG_PAYOFF_CONTRACT = "Thanh lý hợp đồng số: HDCT{0}-{1} .Khách {2} {3}. Ngày thanh lý: {4}";
	private static final String MSG_RENTING_NEW_CONTRACT = "Đăng ký hợp đồng mới ngày {1} .Khách hàng: {2} {3}";
	private static final String MSG_CUSTOMER_DEBT = "Khách nợ phí hợp đồng số: HDCT{0} - {1} .Khách hàng: {2} {3}";
	private static final String MSG_CUSTOMER_PAY_DEBT = "Khách trả nợ phí hợp đồng số: HDCT{0} - {1} .Khách hàng: {2} {3}";
	private static final String MSG_COMPANY_DEBT = "Công ty nợ phí hợp đồng số: HDCT{0} - {1} .Khách hàng: {2} {3}";
	private static final String MSG_COMPANY_PAY_DEBT = "Công ty trả nợ phí hợp đồng số: HDCT{0} - {1} .Khách hàng: {2} {3}";
	private static final String MSG_RENTING_COST = "Trả phí định kỳ hợp đồng số: HDCT{0} - {1} .Khách hàng: {2} {3}";
	
	public static Contract createNewHistory(Contract contract, ContractHistoryType type, Double fee, String customMessage){
		ContractHistory history = new ContractHistory();
		history.setLogDate(DateTimeUtil.getCurrentDate());
		history.setContract(contract);
		history.setHistoryType(type.getType());
		String startDate = StringUtils.EMPTY;
		String payoffDate = StringUtils.EMPTY;
		String msg =  StringUtils.EMPTY;
		switch (type) {
		case CUSTOMER_DEBT:
			history.setFee(fee);
			startDate = DateTimeUtil.date2String(contract.getStartDate());
			msg = MessageFormat.format(MSG_CUSTOMER_DEBT, contract.getId(), startDate, contract.getCustomer().getName(), 
													contract.getCustomer().getBirthYear());
			history.setDetail(msg);
			break;
		case COMPANY_DEBT:
			history.setFee(fee);
			startDate = DateTimeUtil.date2String(contract.getStartDate());
			msg = MessageFormat.format(MSG_COMPANY_DEBT, contract.getId(), startDate, contract.getCustomer().getName(), 
													contract.getCustomer().getBirthYear(), payoffDate);
			history.setDetail(msg);
			break;
		case CUSTOMER_PAY_DEBT:
			history.setFee(fee);
			startDate = DateTimeUtil.date2String(contract.getStartDate());
			msg = MessageFormat.format(MSG_CUSTOMER_PAY_DEBT, contract.getId(), startDate, contract.getCustomer().getName(), 
													contract.getCustomer().getBirthYear(), payoffDate);
			history.setDetail(msg);
			break;
		case COMPANY_PAY_DEBT:
			history.setFee(fee);
			startDate = DateTimeUtil.date2String(contract.getStartDate());
			msg = MessageFormat.format(MSG_COMPANY_PAY_DEBT, contract.getId(), startDate, contract.getCustomer().getName(), 
													contract.getCustomer().getBirthYear(), payoffDate);
			history.setDetail(msg);
			break;
		case INVEST_FUNDING:
			break;
		case OTHER:
			history.setFee(fee);
			history.setDetail(customMessage);
			break;
		case PAYOFF:
			history.setPayoff(contract.getTotalAmount());
			startDate = DateTimeUtil.date2String(contract.getStartDate());
			payoffDate = DateTimeUtil.date2String(contract.getPayoffDate());
			msg = MessageFormat.format(MSG_PAYOFF_CONTRACT, contract.getId(), startDate, contract.getCustomer().getName(), 
													contract.getCustomer().getBirthYear(), payoffDate);
			history.setDetail(msg);
			break;
		case RENTING_COST:
			history.setFee(fee);
			startDate = DateTimeUtil.date2String(contract.getStartDate());
			msg = MessageFormat.format(MSG_RENTING_COST, contract.getId(), startDate, contract.getCustomer().getName(), 
													contract.getCustomer().getBirthYear(), payoffDate);
			history.setDetail(msg);
			break;
		case RENTING_NEW_MOTOBIKE:
			history.setRentingAmount(contract.getTotalAmount());
			startDate = DateTimeUtil.date2String(contract.getStartDate());
			msg = MessageFormat.format(MSG_RENTING_NEW_CONTRACT, contract.getId(), startDate, contract.getCustomer().getName(), 
													contract.getCustomer().getBirthYear());
			history.setDetail(msg);
			break;
		default:
			break;
		
		
		}
		if(StringUtils.isNotEmpty(history.getDetail())){
			if(CollectionUtils.isEmpty(contract.getHistories())){
				contract.setHistories(new HashSet<>());
			}
			contract.getHistories().add(history);
		}
		return contract;
	}
}
