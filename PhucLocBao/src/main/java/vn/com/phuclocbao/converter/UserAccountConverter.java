package vn.com.phuclocbao.converter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;

import vn.com.phuclocbao.bean.UserCompanyGrouping;
import vn.com.phuclocbao.dto.CompanyDto;
import vn.com.phuclocbao.dto.CompanyTypeDto;
import vn.com.phuclocbao.dto.UserAccountDto;
import vn.com.phuclocbao.entity.UserAccount;
import vn.com.phuclocbao.enums.UserAccountState;
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
		CompanyTypeDto type = CompanyTypeConverter.getInstance().toDto(entity.getCompanyEntity().getType(), new CompanyTypeDto());
		dest.getCompanyEntity().setType(type);
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
					.collect(Collectors.toList());
			
			List<UserCompanyGrouping> usersInGroup = new ArrayList<>();
			Map<Integer,List<UserAccountDto>>usersInMap = new HashMap<>();
			dtos.forEach(item -> {
				if(!usersInMap.containsKey(item.getCompanyEntity().getId())){
					usersInMap.put(item.getCompanyEntity().getId(), new ArrayList<>());
				}
				usersInMap.get(item.getCompanyEntity().getId()).add(item);
			});
			
			if(MapUtils.isNotEmpty(usersInMap)){
				usersInMap.forEach((k,v) -> {
					if(CollectionUtils.isNotEmpty(v)){
						UserCompanyGrouping ucg = new UserCompanyGrouping();
						ucg.setCompanyName(v.get(0).getCompanyEntity().getName());
						ucg.setUsers(v.stream().sorted((o1,o2) -> o2.getUsername().compareTo(o1.getUsername())).collect(Collectors.toList()));
						usersInGroup.add(ucg);
					}
				});
			}
			
			if(CollectionUtils.isNotEmpty(usersInGroup)){
				List<UserCompanyGrouping> sortedUsersInGroup = usersInGroup.stream().sorted((o1,o2) -> o2.getCompanyName().compareTo(o1.getCompanyName())).collect(Collectors.toList());
				List<UserAccountDto> sorteddtos = new ArrayList<>();
				sortedUsersInGroup.stream().forEachOrdered(item -> sorteddtos.addAll(item.getUsers()));
				return sorteddtos;
			}
		}
		return dtos;
	}
	
	
	public UserAccount toNewEntity(UserAccountDto dto) throws BusinessException{
		UserAccount entity = this.toEntity(dto, new UserAccount(), "id");
		String passwordHashing = PasswordHashing.hashMD5(dto.getPassword());
		entity.setPassword(passwordHashing);
		entity.setState(UserAccountState.ACTIVE.getName());
		return entity;
		
	}
}
