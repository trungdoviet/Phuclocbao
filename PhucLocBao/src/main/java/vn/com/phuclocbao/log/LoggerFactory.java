package vn.com.phuclocbao.log;

import org.apache.log4j.Logger;

import com.google.inject.Provides;
import com.google.inject.spi.InjectionPoint;

public class LoggerFactory {
	 @Provides 
	  public Logger getLogger(InjectionPoint caller){
	        return Logger.getLogger(caller.getMember().getDeclaringClass().getName());
	  }
}
