package vn.com.phuclocbao.converter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;

import vn.com.phuclocbao.dto.CompanyDto;
import vn.com.phuclocbao.dto.CompanyTypeDto;
import vn.com.phuclocbao.dto.UserAccountDto;
import vn.com.phuclocbao.entity.CompanyEntity;
import vn.com.phuclocbao.enums.CompanyState;
import vn.com.phuclocbao.exception.BusinessException;
import vn.com.phuclocbao.util.ConstantVariable;
import vn.com.phuclocbao.util.LambdaExceptionUtil;
import vn.com.phuclocbao.util.PlbUtil;

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
		return new String[]{"userAccounts", "contracts", "type", "startDate","cityInString", "userAccountsInString"};
	}

	@Override
	public CompanyDto toDtoExtraProps(CompanyEntity entity, CompanyDto dest) throws BusinessException {
		dest.setStartDate(entity.getStartDate());
		return dest;
	}
	
	public CompanyEntity updateEntity(CompanyEntity entity, CompanyDto dto) throws BusinessException{
		entity = toEntity(dto, entity, "id");
		entity.setStartDate(dto.getStartDate());
		return entity;
	}
	
	public List<CompanyDto> toDtos(List<CompanyEntity> entities) throws BusinessException{
		List<CompanyDto> dtos = new ArrayList<>();
		if(CollectionUtils.isNotEmpty(entities)){
			dtos = entities.stream()
					.map(LambdaExceptionUtil.rethrowFunction(item -> {
						CompanyDto dto = this.toDto(item, new CompanyDto());
						dto.setType(CompanyTypeConverter.getInstance().toDto(item.getType(), new CompanyTypeDto()));
						dto.setUserAccounts(UserAccountConverter.getInstance().toDtos(item.getUserAccounts()));
						/*if(CollectionUtils.isNotEmpty(item.getContracts())) {
							dto.setNumberOfContract(item.getContracts().size());
						} else {
							dto.setNumberOfContract(0);
						}*/
						dto.setIsHeadOffice(PlbUtil.getCompanyTypeString(item));
						return dto;
					})).collect(Collectors.toList());
		}
		return dtos;
	}
	
	
	public CompanyEntity toNewEntity(CompanyDto dto) throws BusinessException{
		CompanyEntity entity = this.toEntity(dto, new CompanyEntity(),"id");
		entity.setState(CompanyState.ACTIVE.getName());
		return entity;
	}
}
