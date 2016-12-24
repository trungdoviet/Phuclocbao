package vn.com.phuclocbao.converter;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Test;

import vn.com.phuclocbao.dto.UserAccountDto;
import vn.com.phuclocbao.entity.CompanyEntity;
import vn.com.phuclocbao.entity.UserAccount;
import vn.com.phuclocbao.exception.BusinessException;
public class UserAccountConvertTest {

	@Test
	public void shouldConvertFromEntityToDto() throws BusinessException, ParseException {
		UserAccount account = createUserAccount();
		UserAccountDto dto = UserAccountConverter.getInstance().toDto(account, new UserAccountDto());
		assertThat(dto, is(not(nullValue())));
		assertThat(dto.getUsername(), is(equalTo("phuclocbao")));
		assertThat(dto.getEmail(), is(equalTo("trung@dv.com")));
		assertThat(dto.getPassword(), is((nullValue())));
		assertThat(dto.getCompanyEntity(), is((nullValue())));
	}
	
	@Test
	public void shouldConvertFromEntityToDtoExtra() throws BusinessException, ParseException {
		UserAccount account = createUserAccount();
		UserAccountDto dto = UserAccountConverter.getInstance().toDtoExtraObject(account, new UserAccountDto());
		assertThat(dto, is(not(nullValue())));
		assertThat(dto.getUsername(), is(equalTo("phuclocbao")));
		assertThat(dto.getEmail(), is(equalTo("trung@dv.com")));
		assertThat(dto.getPassword(), is((nullValue())));
		assertThat(dto.getCompanyEntity(), is(not(nullValue())));
		assertThat(dto.getCompanyEntity().getName(), is(equalTo("Axon Active")));
	}

	private UserAccount createUserAccount() throws ParseException {
		UserAccount account = new UserAccount();
		account.setUsername("phuclocbao");
		account.setPassword("111");
		account.setEmail("trung@dv.com");
		account.setFullname("Trung Do");
		account.setCompanyEntity(createCompanyEntity());
		return account;
	}

	private CompanyEntity createCompanyEntity() throws ParseException {
		CompanyEntity entity = new CompanyEntity();
		entity.setAddress("39B Truong son");
		entity.setName("Axon Active");
		entity.setId(1);
		SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");  
		String dateString = "20.12.2006";
		Date date = sdf.parse(dateString);
		entity.setStartDate(date);
		return entity;
	}
	
	

}
