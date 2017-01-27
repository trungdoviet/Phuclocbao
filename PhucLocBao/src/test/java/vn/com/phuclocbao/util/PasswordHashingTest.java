package vn.com.phuclocbao.util;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;

import org.junit.Test;

import vn.com.phuclocbao.exception.BusinessException;

public class PasswordHashingTest {

	@Test
	public void testHashingPassword() throws BusinessException {
		String result = null;
		result = PasswordHashing.hashMD5("123456");
		assertThat(result, is(not(nullValue())));
		assertThat(result.length(), is(equalTo(32)));
		System.out.println(result.length());
	}

}
