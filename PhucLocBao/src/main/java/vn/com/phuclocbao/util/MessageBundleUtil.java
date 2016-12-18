package vn.com.phuclocbao.util;

import java.util.ResourceBundle;

public class MessageBundleUtil {
	private static final ResourceBundle messageBundle = ResourceBundle.getBundle("messages");
	public static String getMessage(String key){
		return messageBundle.getString(key);
	}
}
