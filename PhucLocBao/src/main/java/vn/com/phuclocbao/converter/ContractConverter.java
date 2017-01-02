package vn.com.phuclocbao.converter;
import vn.com.phuclocbao.dto.ContractDto;
import vn.com.phuclocbao.entity.Contract;
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
		entity = this.toEntity(dto, entity, getIgnoredProperties());
		//entity.setCompany(CompanyConverter.getInstance().toEntity(dto.getCompany(),entity.getCompany()));
		entity.setCustomer(CustomerConverter.getInstance().toEntity(dto.getCustomer(), entity.getCustomer()));
		entity.setOwner(TransportOwnerConverter.getInstance().toEntity(dto.getOwner(), entity.getOwner()));
		entity.setPaymentSchedules(PaymentScheduleConverter.getInstance().toEntities(dto.getPaymentSchedules()));
		return entity;
	}
	

	
	
	
}
