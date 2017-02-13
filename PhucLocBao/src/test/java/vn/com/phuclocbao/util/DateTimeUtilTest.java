package vn.com.phuclocbao.util;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;

import java.util.Calendar;
import java.util.Date;

import org.junit.Test;

import vn.com.phuclocbao.exception.BusinessException;

public class DateTimeUtilTest {

	@Test
	public void testGetMaxDateOfYear() throws BusinessException {
		Date maxDate = DateTimeUtil.getLastDateOfYear(2017);
		assertThat(maxDate, is(not(nullValue())));
		Calendar cal = Calendar.getInstance();
		cal.setTime(maxDate);
		assertThat(cal.get(Calendar.YEAR), is(equalTo(2017)));
		assertThat(cal.get(Calendar.MONTH), is(equalTo(Calendar.DECEMBER)));
		assertThat(cal.get(Calendar.DAY_OF_MONTH), is(equalTo(31)));
		System.out.println(maxDate);
	}
	
	@Test
	public void testGetMinDateOfYear() throws BusinessException {
		Date minDate = DateTimeUtil.getFirstDateOfYear(2017);
		assertThat(minDate, is(not(nullValue())));
		Calendar cal = Calendar.getInstance();
		cal.setTime(minDate);
		assertThat(cal.get(Calendar.YEAR), is(equalTo(2017)));
		assertThat(cal.get(Calendar.MONTH), is(equalTo(Calendar.JANUARY)));
		assertThat(cal.get(Calendar.DAY_OF_MONTH), is(equalTo(1)));
		System.out.println(minDate);
	}

}
