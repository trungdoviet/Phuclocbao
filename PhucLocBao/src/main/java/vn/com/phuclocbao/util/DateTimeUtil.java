package vn.com.phuclocbao.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class DateTimeUtil {
	public static Date getCurrentDate(){
		Calendar cal = Calendar.getInstance();
		return cal.getTime();
	}
	
	public static Date getCurrentDateWithoutTime(){
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}
	public static Date getCurrentDateMaxTime(){
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 24);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
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
	public static String date2String(Date date){
		DateFormat df = new SimpleDateFormat(ConstantVariable.DATE_FORMAT);
		return df.format(date);
	}
	public static long daysBetweenDates(Date first, Date last){
		long diff = last.getTime() - first.getTime();
		return TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
	}
	public static int calculateLocalDates(LocalDate birthDate, LocalDate currentDate) {
        if ((birthDate != null) && (currentDate != null)) {
            return Period.between(birthDate, currentDate).getDays();
        } else {
            return 0;
        }
    }
	public static LocalDate convertToLocalDate(Date input){
		return input.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
	}
	public static int calculateDayBetween(Date firstDate, Date currentDate) {
		return calculateLocalDates(convertToLocalDate(firstDate), convertToLocalDate(currentDate));
	}
	
}
