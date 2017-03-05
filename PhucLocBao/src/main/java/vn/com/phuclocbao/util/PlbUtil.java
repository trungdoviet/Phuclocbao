package vn.com.phuclocbao.util;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;

import vn.com.phuclocbao.bean.PLBSession;
import vn.com.phuclocbao.dto.PaymentScheduleDto;
import vn.com.phuclocbao.entity.PaymentSchedule;

public class PlbUtil {
	public static PLBSession getPlbSession(HttpServletRequest request){
		return (PLBSession) request.getSession().getAttribute(PLBSession.SESSION_ATTRIBUTE_KEY);
	}
	public static PaymentScheduleDto getLatestPaid(List<PaymentScheduleDto> sortedPayments){
		if(CollectionUtils.isNotEmpty(sortedPayments)){
			List<PaymentScheduleDto> reverseOrderItems = sortedPayments.stream().sorted((x,y) -> y.getExpectedPayDate().compareTo(x.getExpectedPayDate())).collect(Collectors.toList());
			Optional<PaymentScheduleDto> result = reverseOrderItems.stream().filter(item -> item.getPayDate() != null && item.getFinish().equalsIgnoreCase(ConstantVariable.YES_OPTION)).findFirst();
			if(result.isPresent()){
				return result.get();
			}
		}
		return null;
	}
	
	public static PaymentSchedule getLatestPaid(Set<PaymentSchedule> sortedPayments){
		if(CollectionUtils.isNotEmpty(sortedPayments)){
			List<PaymentSchedule> reverseOrderItems = sortedPayments.stream().sorted((x,y) -> y.getExpectedPayDate().compareTo(x.getExpectedPayDate())).collect(Collectors.toList());
			Optional<PaymentSchedule> result = reverseOrderItems.stream().filter(item -> item.getPayDate() != null && item.getFinish().equalsIgnoreCase(ConstantVariable.YES_OPTION)).findFirst();
			if(result.isPresent()){
				return result.get();
			}
		}
		return null;
	}
	
	public static PaymentScheduleDto getNearliestUnpaid(List<PaymentScheduleDto> sortedPayments){
		if(CollectionUtils.isNotEmpty(sortedPayments)){
			List<PaymentScheduleDto> orderItems = sortedPayments.stream().sorted((x,y) -> x.getExpectedPayDate().compareTo(y.getExpectedPayDate())).collect(Collectors.toList());
			Optional<PaymentScheduleDto> result = orderItems.stream().filter(item -> item.getPayDate() == null || item.getFinish().equalsIgnoreCase(ConstantVariable.NO_OPTION)).findFirst();
			if(result.isPresent()){
				return result.get();
			}
		}
		return null;
	}
}
