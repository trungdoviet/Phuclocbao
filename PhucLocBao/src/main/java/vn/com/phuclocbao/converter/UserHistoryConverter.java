package vn.com.phuclocbao.converter;
import vn.com.phuclocbao.dto.ContractHistoryDto;
import vn.com.phuclocbao.dto.UserHistoryDto;
import vn.com.phuclocbao.entity.ContractHistory;
import vn.com.phuclocbao.entity.UserHistory;
import vn.com.phuclocbao.exception.BusinessException;

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





	
	
	
}
