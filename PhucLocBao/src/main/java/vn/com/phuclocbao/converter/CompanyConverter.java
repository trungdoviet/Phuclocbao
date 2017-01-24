package vn.com.phuclocbao.converter;
import vn.com.phuclocbao.dto.CompanyDto;
import vn.com.phuclocbao.entity.CompanyEntity;
import vn.com.phuclocbao.exception.BusinessException;

public class CompanyConverter extends BaseConverter<CompanyDto, CompanyEntity>{

	private static CompanyConverter instance;
	
	private CompanyConverter() {}
	
	public static synchronized  CompanyConverter getInstance() {
		if (instance == null) {
			instance = new CompanyConverter();
		}
		return instance;
	}

	@Override
	public String[] getIgnoredProperties() {
		return new String[]{"userAccounts", "contracts", "type", "startDate"};
	}

	@Override
	public CompanyDto toDtoExtraProps(CompanyEntity entity, CompanyDto dest) throws BusinessException {
		dest.setStartDate(entity.getStartDate());
		return dest;
	}
}
