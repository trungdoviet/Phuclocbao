package vn.com.phuclocbao.util;

import java.util.Calendar;
import java.util.Date;

public class DateTimeUtil {
	public static Date getCurrentDate(){
		Calendar cal = Calendar.getInstance();
		return cal.getTime();
	}
	
	public static Date addMoreDate(Date startDate, int date){
		Calendar cal = Calendar.getInstance();
		cal.setTime(startDate);
		cal.add(Calendar.DATE, date);
		return cal.getTime();
	}
}
