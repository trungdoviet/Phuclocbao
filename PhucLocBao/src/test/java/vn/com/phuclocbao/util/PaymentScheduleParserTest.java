package vn.com.phuclocbao.util;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;

import java.text.ParseException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.lang3.time.DateUtils;
import org.junit.Test;

import vn.com.phuclocbao.dto.PaymentScheduleDto;
import vn.com.phuclocbao.exception.BusinessException;

public class PaymentScheduleParserTest {
	@Test
	public void shouldParsePaymentScheduleFromString() throws BusinessException, ParseException {
		String paymentInfo = "16/01/2017:N,26/01/2017:Y,05/02/2017:N,";
		List<PaymentScheduleDto> result = PaymentScheduleParser.parsePaymentSchedule(paymentInfo);
		
		assertThat(result, is(not(nullValue())));
		assertThat(result.size(), is(equalTo(3)));
		List<PaymentScheduleDto> orderList = result.stream().sorted((o1,o2) -> {
												return o1.getPayDate().compareTo(o2.getPayDate());
											}).collect(Collectors.toList());

		assertThat(orderList.get(0).getFinish(), is(equalTo("N")));
		assertThat(orderList.get(0).getPayDate(), is(equalTo(DateUtils.parseDate("16/01/2017", ConstantVariable.DATE_FORMAT))));
		
		assertThat(orderList.get(1).getFinish(), is(equalTo("Y")));
		assertThat(orderList.get(1).getPayDate(), is(equalTo(DateUtils.parseDate("26/01/2017", ConstantVariable.DATE_FORMAT))));
		
		assertThat(orderList.get(2).getFinish(), is(equalTo("N")));
		assertThat(orderList.get(2).getPayDate(), is(equalTo(DateUtils.parseDate("05/02/2017", ConstantVariable.DATE_FORMAT))));
	}
}
