package vn.com.phuclocbao.converter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;

import vn.com.phuclocbao.dto.CompanyTypeDto;
import vn.com.phuclocbao.entity.CompanyType;
import vn.com.phuclocbao.exception.BusinessException;
import vn.com.phuclocbao.util.LambdaExceptionUtil;

public class CompanyTypeConverter extends BaseConverter<CompanyTypeDto, CompanyType>{

	private static CompanyTypeConverter instance;
	
	private CompanyTypeConverter() {}
	
	public static synchronized  CompanyTypeConverter getInstance() {
		if (instance == null) {
			instance = new CompanyTypeConverter();
		}
		return instance;
	}

	@Override
	public String[] getIgnoredProperties() {
		return new String[]{"companies"};
	}
	public List<CompanyTypeDto> toDtos(List<CompanyType> entities) throws BusinessException{
		List<CompanyTypeDto> dtos = new ArrayList<>();
		if(CollectionUtils.isNotEmpty(entities)){
			dtos = entities.stream().map(LambdaExceptionUtil.rethrowFunction(item->this.toDto(item, new CompanyTypeDto())))
							.collect(Collectors.toList());
		}
		return dtos;
	}
	
}
