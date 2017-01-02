package vn.com.phuclocbao.converter;
import vn.com.phuclocbao.dto.CustomerDto;
import vn.com.phuclocbao.entity.Customer;

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



	
	
	
}
