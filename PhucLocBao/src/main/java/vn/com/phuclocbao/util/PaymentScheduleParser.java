package vn.com.phuclocbao.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import vn.com.phuclocbao.dto.PaymentScheduleDto;

public class PaymentScheduleParser {
	public static List<PaymentScheduleDto> parsePaymentSchedule(String paymentInfo){
		List<PaymentScheduleDto> dtos = new ArrayList<>();
		if(StringUtils.isNotEmpty(paymentInfo)){
			if(paymentInfo.endsWith(",")){
				paymentInfo = paymentInfo.substring(0, paymentInfo.length()-1);
			}
			String[] dates = paymentInfo.split(",");
			if(ArrayUtils.isNotEmpty(dates)){
				dtos = Arrays.asList(dates).stream()
					.filter(item -> item.split(":").length == 3)
					.map(LambdaExceptionUtil.rethrowFunction( item -> {
						String temp[] = item.split(":");
						PaymentScheduleDto dto = new PaymentScheduleDto();
						dto.setExpectedPayDate(DateTimeUtil.parseDate(temp[0]));
						dto.setFinish(temp[1]);
						if(StringUtils.isNotEmpty(temp[2]) && !temp[2].equalsIgnoreCase(ConstantVariable.VALUE_NIL)){
							dto.setPayDate(DateTimeUtil.parseDate(temp[2]));
						}
						return dto;
					})).collect(Collectors.toList());
			}
		}
		return dtos;
	}
	
	public static String parsePaymentInfoString(List<PaymentScheduleDto> payments){
		String paymentInfo = StringUtils.EMPTY;
		if(CollectionUtils.isNotEmpty(payments)){
			List<String> paymentStrings = new ArrayList<>();
			List<PaymentScheduleDto> sortedPayments = payments.stream().sorted((x,y) -> x.getExpectedPayDate().compareTo(y.getExpectedPayDate())).collect(Collectors.toList());
			sortedPayments.stream().forEach(item ->{
				String expectedDateString = DateTimeUtil.date2String(item.getExpectedPayDate());
				String finishString = item.getFinish();
				String paidDateString = ConstantVariable.VALUE_NIL;
				if(ConstantVariable.YES_OPTION.equalsIgnoreCase(finishString) && item.getPayDate() != null){
					paidDateString = DateTimeUtil.date2String(item.getPayDate());
				}
				paymentStrings.add(StringUtils.joinWith(ConstantVariable.COLON, expectedDateString, finishString, paidDateString));
			});
			paymentInfo = StringUtils.join(paymentStrings, ConstantVariable.COMMA);
		}
		return paymentInfo;
	}
	

}
