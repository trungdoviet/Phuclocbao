package vn.com.phuclocbao.converter;
import java.util.HashSet;

import vn.com.phuclocbao.dto.ContractDto;
import vn.com.phuclocbao.entity.Contract;
import vn.com.phuclocbao.entity.Customer;
import vn.com.phuclocbao.entity.TransportOwner;
import vn.com.phuclocbao.enums.ContractStatusType;
import vn.com.phuclocbao.exception.BusinessException;

public class ContractConverter extends BaseConverter<ContractDto, Contract>{

	private static ContractConverter instance;
	
	private ContractConverter() {}
	
	public static synchronized  ContractConverter getInstance() {
		if (instance == null) {
			instance = new ContractConverter();
		}
		return instance;
	}

	@Override
	public String[] getIgnoredProperties() {
		return new String[]{"paymentSchedules", "customer", "owner", "company", "startDate", "expireDate"};
	}

	@Override
	public ContractDto toDtoExtraProps(Contract entity, ContractDto dest) throws BusinessException {
		dest.setStartDate(entity.getStartDate());
		dest.setExpireDate(entity.getExpireDate());
		return dest;
	}
	
	@Override
	public Contract toEntityExtraProps(ContractDto dto, Contract dest) throws BusinessException {
		dest.setStartDate(dto.getStartDate());
		dest.setExpireDate(dto.getExpireDate());
		return dest;
	}

	public Contract toNewContract(ContractDto dto, Contract entity) throws BusinessException {
		entity = this.toEntityExtra(dto, entity, "id");
		entity.setState(ContractStatusType.IN_PROGRESS.getName());
		entity.setCustomer(new Customer());
		entity.setOwner(new TransportOwner());
		entity.setPaymentSchedules(new HashSet<>());
		entity.setCustomer(CustomerConverter.getInstance().toEntity(dto.getCustomer(), entity.getCustomer(),"id"));
		entity.setOwner(TransportOwnerConverter.getInstance().toEntity(dto.getOwner(), entity.getOwner(),"id"));
		entity.setPaymentSchedules(PaymentScheduleConverter.getInstance().toEntities(dto.getPaymentSchedules(),"id"));
		return entity;
	}
	

	
	
	
}
