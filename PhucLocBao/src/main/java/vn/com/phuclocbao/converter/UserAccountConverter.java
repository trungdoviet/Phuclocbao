package vn.com.phuclocbao.converter;
import vn.com.phuclocbao.dto.CompanyDto;
import vn.com.phuclocbao.dto.UserAccountDto;
import vn.com.phuclocbao.entity.UserAccount;
import vn.com.phuclocbao.exception.BusinessException;

public class UserAccountConverter extends BaseConverter<UserAccountDto, UserAccount>{
	private static UserAccountConverter instance;
	
	private UserAccountConverter() {}
	
	public static synchronized  UserAccountConverter getInstance() {
		if (instance == null) {
			instance = new UserAccountConverter();
		}
		return instance;
	}

	@Override
	public String[] getIgnoredProperties() {
		return new String[]{"password", "companyEntity"};
	}

	@Override
	public UserAccountDto doExtra(UserAccount entity, UserAccountDto dest) throws BusinessException {
		CompanyDto companyDto = new CompanyDto();
		CompanyConverter.getInstance().toDto(entity.getCompanyEntity(), companyDto);
		dest.setCompanyEntity(companyDto);
		return dest;
	}
	
	
}
