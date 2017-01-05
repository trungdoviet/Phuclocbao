package vn.com.phuclocbao.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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
					.filter(item -> item.split(":").length == 2)
					.map(LambdaExceptionUtil.rethrowFunction( item -> {
						String temp[] = item.split(":");
						PaymentScheduleDto dto = new PaymentScheduleDto();
						dto.setPayDate(DateTimeUtil.parseDate(temp[0]));
						dto.setFinish(temp[1]);
						return dto;
					})).collect(Collectors.toList());
			}
		}
		return dtos;
	}
	
	

}
