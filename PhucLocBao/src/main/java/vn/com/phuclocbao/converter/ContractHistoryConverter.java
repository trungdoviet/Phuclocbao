package vn.com.phuclocbao.converter;
import vn.com.phuclocbao.dto.ContractHistoryDto;
import vn.com.phuclocbao.entity.ContractHistory;
import vn.com.phuclocbao.exception.BusinessException;

public class ContractHistoryConverter extends BaseConverter<ContractHistoryDto, ContractHistory>{

	private static ContractHistoryConverter instance;
	
	private ContractHistoryConverter() {}
	
	public static synchronized  ContractHistoryConverter getInstance() {
		if (instance == null) {
			instance = new ContractHistoryConverter();
		}
		return instance;
	}

	@Override
	public String[] getIgnoredProperties() {
		return new String[]{ "logDate","contract"};
	}

	@Override
	public ContractHistoryDto toDtoExtraProps(ContractHistory entity, ContractHistoryDto dest)
			throws BusinessException {
		dest.setLogDate(entity.getLogDate());
		return super.toDtoExtraProps(entity, dest);
	}



	
	
	
}
