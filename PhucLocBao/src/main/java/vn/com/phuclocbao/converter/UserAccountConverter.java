package vn.com.phuclocbao.converter;
import javax.inject.Inject;

import org.apache.log4j.Logger;

import vn.com.phuclocbao.dto.UserAccountDto;
import vn.com.phuclocbao.entity.UserAccount;

public class UserAccountConverter extends BaseConverter<UserAccountDto, UserAccount>{
	@Inject
	Logger logger;
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
	
}
