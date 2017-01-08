package vn.com.phuclocbao.converter;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import vn.com.phuclocbao.dto.ContractDto;
import vn.com.phuclocbao.dto.CustomerDto;
import vn.com.phuclocbao.entity.Customer;
import vn.com.phuclocbao.exception.BusinessException;

public class CustomerConverter extends BaseConverter<CustomerDto, Customer>{

	private static CustomerConverter instance;
	
	private CustomerConverter() {}
	
	public static synchronized  CustomerConverter getInstance() {
		if (instance == null) {
			instance = new CustomerConverter();
		}
		return instance;
	}

	@Override
	public String[] getIgnoredProperties() {
		return new String[]{ "contract"};
	}


	public List<CustomerDto> toDtosOnSearchingCustomer(List<Customer> entities) throws BusinessException{
		List<CustomerDto> dtos = new ArrayList<>();
		if(CollectionUtils.isNotEmpty(entities)){
			for (Customer customer : entities) {
				CustomerDto dto = new CustomerDto();
				this.toDto(customer, dto);
				dto.setContract(ContractConverter.getInstance().toDto(customer.getContract(), new ContractDto()));
				dtos.add(dto);
			}
		}
		return  dtos;
	}

	
	
	
}
