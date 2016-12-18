package vn.com.phuclocbao.converter;

import org.junit.Test;

import vn.com.phuclocbao.dto.UserAccountDto;
import vn.com.phuclocbao.entity.UserAccount;
import vn.com.phuclocbao.exception.BusinessException;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.nullValue;
public class UserAccountConvertTest {

	@Test
	public void shouldConvertFromEntityToDto() throws BusinessException {
		UserAccount account = createUserAccount();
		UserAccountDto dto = UserAccountConverter.getInstance().toDto(account, new UserAccountDto());
		assertThat(dto, is(not(nullValue())));
		assertThat(dto.getUsername(), is(equalTo("phuclocbao")));
		assertThat(dto.getEmail(), is(equalTo("trung@dv.com")));
		assertThat(dto.getPassword(), is((nullValue())));
	}

	private UserAccount createUserAccount() {
		UserAccount account = new UserAccount();
		account.setUsername("phuclocbao");
		account.setPassword("111");
		account.setEmail("trung@dv.com");
		account.setFullname("Trung Do");
		return account;
	}

}
