package vn.com.phuclocbao.util;

import java.text.MessageFormat;

import org.apache.commons.lang3.StringUtils;

import vn.com.phuclocbao.entity.Contract;
import vn.com.phuclocbao.entity.PaymentHistory;
import vn.com.phuclocbao.enums.PaymentHistoryType;

public class PaymentHistoryUtil {
	private static final String MSG_PAYOFF_CONTRACT = "Thanh lý hợp đồng số: HDCT{0}-{1} .Khách {2} {3}. Ngày thanh lý: {4}";
	private static final String MSG_RENTING_NEW_CONTRACT = "Đăng ký hợp đồng mới ngày {1} .Khách hàng: {2} {3}";
	private static final String MSG_CUSTOMER_DEBT = "Khách nợ phí hợp đồng số: HDCT{0} - {1} .Khách hàng: {2} {3}";
	private static final String MSG_CUSTOMER_PAY_DEBT = "Khách trả nợ phí hợp đồng số: HDCT{0} - {1} .Khách hàng: {2} {3}";
	private static final String MSG_COMPANY_DEBT = "Công ty nợ phí hợp đồng số: HDCT{0} - {1} .Khách hàng: {2} {3}";
	private static final String MSG_COMPANY_PAY_DEBT = "Công ty trả nợ phí hợp đồng số: HDCT{0} - {1} .Khách hàng: {2} {3}";
	private static final String MSG_RENTING_COST = "Trả phí định kỳ hợp đồng số: HDCT{0} - {1} .Khách hàng: {2} {3}";
	
	public static PaymentHistory createNewHistory(Contract contract, Integer companyId, PaymentHistoryType type, Double fee, String customMessage){
		PaymentHistory history = new PaymentHistory();
		history.setLogDate(DateTimeUtil.getCurrentDate());
		history.setCompanyId(companyId);
		history.setHistoryType(type.getType());
		if(contract != null){
			history.setContractId(contract.getId());
		}
		String startDate = StringUtils.EMPTY;
		String payoffDate = StringUtils.EMPTY;
		String msg =  StringUtils.EMPTY;
		switch (type) {
		case CUSTOMER_DEBT:
			history.setFee(fee);
			startDate = DateTimeUtil.date2String(contract.getStartDate());
			msg = MessageFormat.format(MSG_CUSTOMER_DEBT, contract.getId(), startDate, contract.getCustomer().getName(), 
													String.valueOf(contract.getCustomer().getBirthYear()));
			history.setDetail(msg);
			break;
		case COMPANY_DEBT:
			history.setFee(fee);
			startDate = DateTimeUtil.date2String(contract.getStartDate());
			msg = MessageFormat.format(MSG_COMPANY_DEBT, contract.getId(), startDate, contract.getCustomer().getName(), 
														String.valueOf(contract.getCustomer().getBirthYear()), payoffDate);
			history.setDetail(msg);
			break;
		case CUSTOMER_PAY_DEBT:
			history.setFee(fee);
			startDate = DateTimeUtil.date2String(contract.getStartDate());
			msg = MessageFormat.format(MSG_CUSTOMER_PAY_DEBT, contract.getId(), startDate, contract.getCustomer().getName(), 
																String.valueOf(contract.getCustomer().getBirthYear()), payoffDate);
			history.setDetail(msg);
			break;
		case COMPANY_PAY_DEBT:
			history.setFee(fee);
			startDate = DateTimeUtil.date2String(contract.getStartDate());
			msg = MessageFormat.format(MSG_COMPANY_PAY_DEBT, contract.getId(), startDate, contract.getCustomer().getName(), 
															String.valueOf(contract.getCustomer().getBirthYear()), payoffDate);
			history.setDetail(msg);
			break;
		case INVEST_FUNDING:
			history.setFee(fee);
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
														String.valueOf(contract.getCustomer().getBirthYear()), payoffDate);
			history.setDetail(msg);
			break;
		case RENTING_COST:
			history.setFee(fee);
			startDate = DateTimeUtil.date2String(contract.getStartDate());
			msg = MessageFormat.format(MSG_RENTING_COST, contract.getId(), startDate, contract.getCustomer().getName(), 
															String.valueOf(contract.getCustomer().getBirthYear()), payoffDate);
			history.setDetail(msg);
			break;
		case RENTING_NEW_MOTOBIKE:
			history.setRentingAmount(contract.getTotalAmount());
			startDate = DateTimeUtil.date2String(contract.getStartDate());
			msg = MessageFormat.format(MSG_RENTING_NEW_CONTRACT, contract.getId(), startDate, contract.getCustomer().getName(), 
																String.valueOf(contract.getCustomer().getBirthYear()));
			history.setDetail(msg);
			break;
		default:
			break;
		
		
		}
		return history;
	}
}
