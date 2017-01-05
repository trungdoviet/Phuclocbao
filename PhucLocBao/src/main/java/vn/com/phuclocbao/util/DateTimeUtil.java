package vn.com.phuclocbao.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

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
	public static Date parseDate(String target) throws ParseException{
	    DateFormat df = new SimpleDateFormat(ConstantVariable.DATE_FORMAT, Locale.ENGLISH);
	    Date result =  df.parse(target);
		return result;  
	}
	
}
