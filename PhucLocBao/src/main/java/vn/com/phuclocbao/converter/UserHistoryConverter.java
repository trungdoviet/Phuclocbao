package vn.com.phuclocbao.converter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;

import vn.com.phuclocbao.dto.PaymentHistoryDto;
import vn.com.phuclocbao.dto.UserHistoryDto;
import vn.com.phuclocbao.entity.PaymentHistory;
import vn.com.phuclocbao.entity.UserHistory;
import vn.com.phuclocbao.exception.BusinessException;
import vn.com.phuclocbao.util.LambdaExceptionUtil;

public class UserHistoryConverter extends BaseConverter<UserHistoryDto, UserHistory>{

	private static UserHistoryConverter instance;
	
	private UserHistoryConverter() {}
	
	public static synchronized  UserHistoryConverter getInstance() {
		if (instance == null) {
			instance = new UserHistoryConverter();
		}
		return instance;
	}

	@Override
	public String[] getIgnoredProperties() {
		return new String[]{ "happenTime"};
	}

	@Override
	public UserHistoryDto toDtoExtraProps(UserHistory entity, UserHistoryDto dest) throws BusinessException {
		dest.setHappenTime(entity.getHappenTime());
		return super.toDtoExtraProps(entity, dest);
	}

	public List<UserHistoryDto> toDtos(List<UserHistory> entities) throws BusinessException {
		List<UserHistoryDto> dtos = new ArrayList<>();
		if(CollectionUtils.isNotEmpty(entities)){
			dtos = entities.stream()
					.map(LambdaExceptionUtil.rethrowFunction( item ->  this.toDto(item, new UserHistoryDto())))
					.sorted((o1,o2)->o2.getHappenTime().compareTo(o1.getHappenTime()))
					.collect(Collectors.toList());
					
		}
		return dtos;
	}
}
