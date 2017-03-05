package vn.com.phuclocbao.util;

import java.text.MessageFormat;

import org.apache.commons.lang3.StringUtils;

import vn.com.phuclocbao.entity.Contract;
import vn.com.phuclocbao.entity.UserHistory;
import vn.com.phuclocbao.enums.UserActionHistoryType;

public class UserHistoryUtil {
	private static final String MSG_CONTRACT_ACTION = "{0} HDCT{1}. Khách hàng: {2} {3}.";
	private static final String MSG_CREATE_COMPANY="Tạo công ty {0}";
	public static UserHistory createNewHistory(Contract contract, Integer companyId, String companyName, String accountName, UserActionHistoryType type, String customMessage){
		UserHistory history = new UserHistory();
		history.setHappenTime(DateTimeUtil.getCurrentDate());
		history.setCompanyId(companyId);
		history.setActionType(type.getType());
		history.setAccountName(accountName);
		history.setCompanyName(companyName);
		String msg =  StringUtils.EMPTY;
		switch (type) {
		case UPDATE_CONTRACT:
		case COMPANY_DEBT:
		case COMPANY_PAY_DEBT:
		case CUSTOMER_DEBT:
		case CUSTOMER_PAY_DEBT:
		case NEW_CONTRACT:
		case PAYOFF_CONTRACT:
		case RENTING_COST:
			msg = MessageFormat.format(MSG_CONTRACT_ACTION, type.getName(), 
															String.valueOf(contract.getId()), 
															contract.getCustomer().getName(), 
															String.valueOf(contract.getCustomer().getBirthYear()));
			history.setDetail(msg);
			break;
		case UPDATE_COMPANY_BRANCH:
		case UPDATE_COMPANY_FINANCIAL:
		case UPDATE_COMPANY_INFO:
			history.setDetail(type.getName());
			break;
		case CREATE_COMPANY_BRANCH:
			msg = MessageFormat.format(MSG_CREATE_COMPANY, customMessage);
			history.setDetail(msg);
			break;
		case OTHER:
			history.setDetail(msg);
		default:
			break;
		
		}
		return history;
	}
}
