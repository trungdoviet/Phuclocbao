package vn.com.phuclocbao.service;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.time.DateUtils;
import org.junit.Test;

import vn.com.phuclocbao.bean.ContractResponseBody;
import vn.com.phuclocbao.bean.CustomerContract;
import vn.com.phuclocbao.dto.ContractDto;
import vn.com.phuclocbao.dto.CustomerDto;
import vn.com.phuclocbao.dto.PaymentScheduleDto;
import vn.com.phuclocbao.enums.ContractStatusType;
import vn.com.phuclocbao.exception.BusinessException;
import vn.com.phuclocbao.service.impl.DefaultContractService;
import vn.com.phuclocbao.service.impl.DefaultCustomerService;
import vn.com.phuclocbao.util.ConstantVariable;
import vn.com.phuclocbao.util.DateTimeUtil;
import vn.com.phuclocbao.util.PaymentScheduleParser;

public class ContractServiceTest {
	@Test
	public void shouldCreateContractResponse() throws BusinessException, ParseException {
		CustomerService service = new DefaultCustomerService();
		ContractResponseBody result = service.buildContractResponse(createCustomers());
		
		assertThat(result, is(not(nullValue())));
		assertThat(result.getCustomers().size(), is(equalTo(4)));
		assertThat(result.getCustomerContracts().size(), is(equalTo(4)));
		List<CustomerDto> customers = result.getCustomers();
		List<CustomerContract> contracts = result.getCustomerContracts();
		
		Collections.sort(customers, new Comparator<CustomerDto>() {
			@Override
			public int compare(CustomerDto o1, CustomerDto o2) {
				return o1.getIdNo().compareTo(o2.getIdNo());
			}
		});
		
		Collections.sort(contracts, new Comparator<CustomerContract>() {
			@Override
			public int compare(CustomerContract o1, CustomerContract o2) {
				return o1.getIdNo().compareTo(o2.getIdNo());
			}
		});
		assertThat(customers.get(0).getIdNo(), is(equalTo("0001")));
		assertThat(customers.get(0).getIdNo(), is(equalTo(contracts.get(0).getIdNo())));
		assertThat(customers.get(0).getId(), is(equalTo(12)));
		assertThat(contracts.get(0).getContracts().size(), is(equalTo(2)));
		
		assertThat(contracts.get(3).getContracts().size(), is(equalTo(1)));
	}
	
	private List<CustomerDto> createCustomers(){
		List<CustomerDto> result = new ArrayList<>();
		CustomerDto dto1 = createCustomer(11, "0001", ContractStatusType.IN_PROGRESS);
		CustomerDto dto11 = createCustomer(12, "0001", ContractStatusType.IN_PROGRESS);
		CustomerDto dto2 = createCustomer(2, "0002", ContractStatusType.FINISH);
		CustomerDto dto22 = createCustomer(22, "0002", ContractStatusType.FINISH);
		CustomerDto dto3 = createCustomer(1, "0003", ContractStatusType.BAD);
		CustomerDto dto33 = createCustomer(3, "0003", ContractStatusType.IN_PROGRESS);
		CustomerDto dto4 = createCustomer(4, "0004", ContractStatusType.IN_PROGRESS);
		result.add(dto1);
		result.add(dto11);
		result.add(dto2);
		result.add(dto22);
		result.add(dto3);
		result.add(dto33);
		result.add(dto4);
		return result;
	}
	
	private CustomerDto createCustomer(Integer id, String idNo,ContractStatusType state ){
		CustomerDto customer = new CustomerDto();
		customer.setIdNo(idNo);
		customer.setId(id);
		customer.setContract(createContract(id, state));
		return customer;
	}
	
	private ContractDto createContract(Integer id, ContractStatusType state){
		ContractDto dto = new ContractDto();
		dto.setId(id);
		dto.setState(state.getName());
		dto.setStartDate(DateTimeUtil.getCurrentDate());
		return dto;
	}
}
