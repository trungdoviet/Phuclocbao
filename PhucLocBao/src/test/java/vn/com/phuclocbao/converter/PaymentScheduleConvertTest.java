package vn.com.phuclocbao.converter;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.Test;

import vn.com.phuclocbao.dto.ContractDto;
import vn.com.phuclocbao.dto.PaymentScheduleDto;
import vn.com.phuclocbao.dto.UserAccountDto;
import vn.com.phuclocbao.entity.PaymentSchedule;
import vn.com.phuclocbao.entity.UserAccount;
import vn.com.phuclocbao.exception.BusinessException;
import vn.com.phuclocbao.util.DateTimeUtil;
public class PaymentScheduleConvertTest {

	@Test
	public void shouldConvertFromDtosToEntities() throws BusinessException, ParseException {
		List<PaymentScheduleDto> payments = createPaymentSchedules();
		Set<PaymentSchedule> result = PaymentScheduleConverter.getInstance().toEntities(payments);
		
		assertThat(result, is(not(nullValue())));
		assertThat(result.size(), is(equalTo(2)));
		List<PaymentSchedule> orderList = result.stream().sorted((o1,o2) -> {
												return o1.getId().compareTo(o2.getId());
											}).collect(Collectors.toList());

		assertThat(orderList.get(0).getFinish(), is(equalTo("Y")));
		assertThat(orderList.get(0).getPayDate(), is(not(nullValue())));
		assertThat(orderList.get(0).getNotifiedDate(), is(not(nullValue())));
		assertThat(orderList.get(0).getContract(), is((nullValue())));
	}
	

	private List<PaymentScheduleDto> createPaymentSchedules(){
		List<PaymentScheduleDto> result = new ArrayList<>();
		for(int i = 0; i < 2; i++) {
			result.add(createPaymentScheduleDto(i + 1));
		}
		return result;
	}
	private PaymentScheduleDto createPaymentScheduleDto(int id){
		PaymentScheduleDto dto = new PaymentScheduleDto();
		dto.setFinish("Y");
		dto.setId(id);
		Date now = DateTimeUtil.getCurrentDate();
		dto.setPayDate(now);
		dto.setNotifiedDate(DateTimeUtil.addMoreDate(now, 3));
		dto.setContract(createContract());
		
		return dto;
	}

	private ContractDto createContract() {
		ContractDto dto = new ContractDto();
		dto.setId(1);
		return dto;
	}
	
	

}
