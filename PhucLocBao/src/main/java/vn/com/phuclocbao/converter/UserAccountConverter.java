package vn.com.phuclocbao.converter;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;

import vn.com.phuclocbao.dto.CompanyDto;
import vn.com.phuclocbao.dto.UserAccountDto;
import vn.com.phuclocbao.entity.UserAccount;
import vn.com.phuclocbao.exception.BusinessException;
import vn.com.phuclocbao.util.LambdaExceptionUtil;
import vn.com.phuclocbao.util.PasswordHashing;

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
		return new String[]{"companyEntity"};
	}

	@Override
	public UserAccountDto doExtra(UserAccount entity, UserAccountDto dest) throws BusinessException {
		CompanyDto companyDto = new CompanyDto();
		CompanyConverter.getInstance().toDto(entity.getCompanyEntity(), companyDto);
		dest.setCompanyEntity(companyDto);
		return dest;
	}
	
	public List<UserAccountDto> toDtos(Set<UserAccount> entities) throws BusinessException{
		List<UserAccountDto> dtos = new ArrayList<>();
		if(CollectionUtils.isNotEmpty(entities)){
			dtos = entities.stream().map(LambdaExceptionUtil.rethrowFunction(item -> this.toDto(item, new UserAccountDto())))
					.collect(Collectors.toList());
		}
		return dtos;
	}
	
	public List<UserAccountDto> toDtosExtra(List<UserAccount> entities) throws BusinessException{
		List<UserAccountDto> dtos = new ArrayList<>();
		if(CollectionUtils.isNotEmpty(entities)){
			dtos = entities.stream().map(LambdaExceptionUtil.rethrowFunction(item -> this.toDtoExtraObject(item, new UserAccountDto())))
					.sorted((o1,o2) -> o1.getUsername().compareTo(o2.getUsername()))
					.collect(Collectors.toList());
		}
		return dtos;
	}
	
	
	public UserAccount toNewEntity(UserAccountDto dto) throws BusinessException{
		UserAccount entity = this.toEntity(dto, new UserAccount(), "id");
		String passwordHashing = PasswordHashing.hashMD5(dto.getPassword());
		entity.setPassword(passwordHashing);
		return entity;
		
	}
}
